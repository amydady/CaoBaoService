package com.littlecat.order.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.query.QueryParam;
import com.littlecat.cbb.utils.CollectionUtil;
import com.littlecat.cbb.utils.DateTimeUtil;
import com.littlecat.common.consts.ErrorCode;
import com.littlecat.common.consts.OrderState;
import com.littlecat.goods.business.GoodsBusiness;
import com.littlecat.goods.model.GoodsMO;
import com.littlecat.inventory.business.GoodsInventoryBusiness;
import com.littlecat.order.dao.OrderDao;
import com.littlecat.order.model.OrderDetailMO;
import com.littlecat.order.model.OrderMO;

@Component
@Transactional
public class OrderBusiness
{
	@Autowired
	private OrderDao orderDao;

	@Autowired
	private OrderDetailBusiness orderDetailBusiness;

	@Autowired
	private GoodsBusiness goodsBusiness;

	@Autowired
	private GoodsInventoryBusiness goodsInventoryBusiness;

	private static final String MODEL_NAME = OrderMO.class.getSimpleName();
	private static final String MODEL_NAME_ORDERDETAIL = OrderDetailMO.class.getSimpleName();
	private static final String MODEL_NAME_GOODS = GoodsMO.class.getSimpleName();

	public String addOrder(OrderMO orderMO, List<OrderDetailMO> orderDetailMOs) throws LittleCatException
	{
		validateReqData(orderMO, orderDetailMOs);

		for (OrderDetailMO orderDetail : orderDetailMOs)
		{
			orderMO.setFee(orderMO.getFee() + orderDetail.getFee());
		}

		// 创建订单
		String orderId = orderDao.add(orderMO);

		for (OrderDetailMO orderDetail : orderDetailMOs)
		{
			orderDetail.setOrderId(orderId);
		}

		// 创建订单明细
		orderDetailBusiness.add(orderDetailMOs);

		return orderId;
	}

	/**
	 * 订单支付操作
	 * 
	 * @param id
	 * @throws LittleCatException
	 */
	public void payOrder(String id) throws LittleCatException
	{
		OrderMO mo = orderDao.getById(id);

		if (mo == null)
		{
			throw new LittleCatException(ErrorCode.GetInfoFromDBReturnEmpty.getCode(), ErrorCode.GetInfoFromDBReturnEmpty.getMsg().replace("{INFO_NAME}", MODEL_NAME));
		}

		List<OrderDetailMO> detailMOs = orderDetailBusiness.getByOrderId(mo.getId());

		if (CollectionUtil.isEmpty(detailMOs))
		{
			throw new LittleCatException(ErrorCode.GetInfoFromDBReturnEmpty.getCode(), ErrorCode.GetInfoFromDBReturnEmpty.getMsg().replace("{INFO_NAME}", MODEL_NAME_ORDERDETAIL));
		}

		Map<String, List<String>> lockInfo = getResLockInfo(detailMOs);

		if (!lock(lockInfo))
		{
			return;
		}

		mo.setState(OrderState.daiqianshou);
		mo.setPayTime(DateTimeUtil.getCurrentTimeForDisplay());
		orderDao.modify(mo);

		setInventoryInfoByOrderDetail(detailMOs);

		unLock(lockInfo);

	}

	public void setOrderState2YiShouHuo(String id) throws LittleCatException
	{
		OrderMO mo = orderDao.getById(id);

		if (mo == null)
		{
			throw new LittleCatException(ErrorCode.GetInfoFromDBReturnEmpty.getCode(), ErrorCode.GetInfoFromDBReturnEmpty.getMsg().replace("{INFO_NAME}", MODEL_NAME));
		}
		mo.setState(OrderState.yishouhuo);
		mo.setReceiveTime(DateTimeUtil.getCurrentTimeForDisplay());

		orderDao.modify(mo);
	}

	public void setOrderState2TuiKuanZhong(String id) throws LittleCatException
	{
		OrderMO mo = orderDao.getById(id);

		if (mo == null)
		{
			throw new LittleCatException(ErrorCode.GetInfoFromDBReturnEmpty.getCode(), ErrorCode.GetInfoFromDBReturnEmpty.getMsg().replace("{INFO_NAME}", MODEL_NAME));
		}
		mo.setState(OrderState.tuikuanzhong);
		mo.setReturnApplyTime(DateTimeUtil.getCurrentTimeForDisplay());

		orderDao.modify(mo);
	}

	public void setOrderState2YiTuiKuan(String id) throws LittleCatException
	{
		OrderMO mo = orderDao.getById(id);

		if (mo == null)
		{
			throw new LittleCatException(ErrorCode.GetInfoFromDBReturnEmpty.getCode(), ErrorCode.GetInfoFromDBReturnEmpty.getMsg().replace("{INFO_NAME}", MODEL_NAME));
		}
		mo.setState(OrderState.yituikuan);
		mo.setReturnCompleteTime(DateTimeUtil.getCurrentTimeForDisplay());

		orderDao.modify(mo);
	}

	/**
	 * 修改订单信息
	 * 
	 * @param mo
	 * @return
	 * @throws LittleCatException
	 */
	public void modifyOrder(OrderMO mo) throws LittleCatException
	{
		orderDao.modify(mo);
	}

	public void deleteOrderById(String id) throws LittleCatException
	{
		orderDetailBusiness.deleteByOrderId(id);
		orderDao.delete(id);
	}

	public OrderMO getOrderById(String id) throws LittleCatException
	{
		return orderDao.getById(id);
	}

	public int getOrderList(QueryParam queryParam, List<OrderMO> mos) throws LittleCatException
	{
		return orderDao.getList(queryParam, mos);
	}

	public OrderDetailMO getOrderDetailById(String id) throws LittleCatException
	{
		return orderDetailBusiness.getById(id);
	}

	public int getOrderDetailList(QueryParam queryParam, List<OrderDetailMO> mos) throws LittleCatException
	{
		return orderDetailBusiness.getList(queryParam, mos);
	}

	private void validateReqData(OrderMO orderMO, List<OrderDetailMO> orderDetailMOs) throws LittleCatException
	{
		if (orderMO == null)
		{
			throw new LittleCatException(ErrorCode.RequestObjectIsNull.getCode(), ErrorCode.RequestObjectIsNull.getMsg().replace("{INFO_NAME}", MODEL_NAME));
		}

		if (CollectionUtil.isEmpty(orderDetailMOs))
		{
			throw new LittleCatException(ErrorCode.RequestObjectIsNull.getCode(), ErrorCode.RequestObjectIsNull.getMsg().replace("{INFO_NAME}", MODEL_NAME_ORDERDETAIL));
		}
	}

	/**
	 * 获取订单明细对应的需要锁定的资源
	 * 
	 * @param detailMOs
	 * @return
	 */
	private Map<String, List<String>> getResLockInfo(List<OrderDetailMO> detailMOs)
	{
		// 待锁定的资源，key：锁类型；value：锁资源的key
		Map<String, List<String>> toBeLocked = new HashMap<String, List<String>>();

		for (OrderDetailMO orderDetailMO : detailMOs)
		{
			String lockType = orderDetailMO.getBuyType().getResLockType();
			String lockKey = orderDetailMO.getResId();

			if (toBeLocked.containsKey(lockType))
			{
				toBeLocked.get(lockType).add(lockKey);
			}
			else
			{
				List<String> lockKeys = new ArrayList<String>();
				lockKeys.add(lockKey);
				toBeLocked.put(lockType, lockKeys);
			}
		}

		return toBeLocked;
	}

	private boolean lock(Map<String, List<String>> lockInfo)
	{
		return true;
	}

	private void unLock(Map<String, List<String>> lockInfo) throws LittleCatException
	{

	}

	private void setInventoryInfoByOrderDetail(List<OrderDetailMO> detailMOs) throws LittleCatException
	{

	}
}
