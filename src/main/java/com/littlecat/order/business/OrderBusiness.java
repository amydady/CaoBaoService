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
import com.littlecat.common.consts.BuyType;
import com.littlecat.common.consts.ErrorCode;
import com.littlecat.common.consts.OrderState;
import com.littlecat.goods.business.GoodsBusiness;
import com.littlecat.goods.model.GoodsMO;
import com.littlecat.inventory.business.GoodsInventoryBusiness;
import com.littlecat.lock.business.ResLockBusiness;
import com.littlecat.lock.model.ResLockMO;
import com.littlecat.order.dao.OrderDao;
import com.littlecat.order.model.OrderDetailMO;
import com.littlecat.order.model.OrderMO;
import com.littlecat.seckill.business.SecKillPlanBusiness;
import com.littlecat.seckill.model.SecKillPlanMO;

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

	@Autowired
	private ResLockBusiness resLockBusiness;

	@Autowired
	private SecKillPlanBusiness secKillPlanBusiness;

	private static final String MODEL_NAME = OrderMO.class.getSimpleName();
	private static final String MODEL_NAME_ORDERDETAIL = OrderDetailMO.class.getSimpleName();
	private static final String MODEL_NAME_GOODS = GoodsMO.class.getSimpleName();
	private static final String MODEL_NAME_SECKILLPLAN = SecKillPlanMO.class.getSimpleName();

	// 资源锁失效时间（秒）
	public static final long RESLOCK_DISABLE_TIME = ResLockMO.DEFAULT_DISABLE_TIME;

	// 获取资源锁超时时间（秒）
	public static final long RESLOCK_TIMEOUTSECS = ResLockMO.DEFAULT_TIMEOUTSECS;

	// 获取资源锁重试时间间隔（毫秒）
	public static final long RESLOCK_RETRYTIMESTEP = ResLockMO.DEFAULT_RETRYTIMESTEP;

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
			throw new LittleCatException(ErrorCode.LockResError.getCode(), ErrorCode.LockResError.getMsg());
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
	private Map<String, List<String>> getResLockInfo(List<OrderDetailMO> detailMOs) throws LittleCatException
	{
		// 待锁定的资源，key：锁类型；value：锁资源的key
		Map<String, List<String>> toBeLocked = new HashMap<String, List<String>>();

		for (OrderDetailMO orderDetailMO : detailMOs)
		{
			String lockType = orderDetailMO.getBuyType().getResLockType();
			String lockKey = orderDetailMO.getResId(); // 使用商品ID作为库存资源锁的key

			if (orderDetailMO.getBuyType() == BuyType.seckill)
			{
				SecKillPlanMO secKillPlanMO = secKillPlanBusiness.getById(lockKey);

				if (null == secKillPlanMO)
				{
					throw new LittleCatException(ErrorCode.GetInfoFromDBReturnEmpty.getCode(), ErrorCode.GetInfoFromDBReturnEmpty.getMsg().replace("{INFO_NAME}", MODEL_NAME_SECKILLPLAN).replace("{DETAILINFO}", "id:" + lockKey));
				}

				lockKey = secKillPlanMO.getGoodsId();
			}

			if (orderDetailMO.getBuyType() == BuyType.groupbuy)
			{
				// TODO:获取产品ID
			}

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
		for (String lockType : lockInfo.keySet())
		{
			ResLockMO.ResLockType resLockType = ResLockMO.ResLockType.valueOf(lockType);
			List<ResLockMO> locks = new ArrayList<ResLockMO>();

			for (String resId : lockInfo.get(lockType))
			{
				locks.add(new ResLockMO(resLockType, resId, DateTimeUtil.getTimeForDisplay(DateTimeUtil.getCurrentTime() + RESLOCK_DISABLE_TIME * 1000, DateTimeUtil.defaultDateFormat)));
			}

			if (!resLockBusiness.lock(locks, RESLOCK_TIMEOUTSECS, RESLOCK_RETRYTIMESTEP))
			{
				return false;
			}
		}

		return true;
	}

	private void unLock(Map<String, List<String>> lockInfo) throws LittleCatException
	{
		for (String lockType : lockInfo.keySet())
		{
			ResLockMO.ResLockType resLockType = ResLockMO.ResLockType.valueOf(lockType);
			List<ResLockMO> locks = new ArrayList<ResLockMO>();

			for (String resId : lockInfo.get(lockType))
			{
				locks.add(new ResLockMO(resLockType, resId));
			}

			resLockBusiness.unLock(locks);
		}
	}

	private void setInventoryInfoByOrderDetail(List<OrderDetailMO> detailMOs) throws LittleCatException
	{

	}
}
