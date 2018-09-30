package com.littlecat.order.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.query.QueryParam;
import com.littlecat.cbb.utils.CollectionUtil;
import com.littlecat.cbb.utils.DateTimeUtil;
import com.littlecat.common.consts.ErrorCode;
import com.littlecat.common.consts.OrderState;
import com.littlecat.order.dao.OrderDao;
import com.littlecat.order.dao.OrderDetailDao;
import com.littlecat.order.model.OrderDetailMO;
import com.littlecat.order.model.OrderMO;

@Component
@Transactional
public class OrderBusiness
{
	@Autowired
	private OrderDao orderDao;

	@Autowired
	private OrderDetailDao orderDetailDao;

	private static final String MODEL_NAME = OrderMO.class.getSimpleName();
	private static final String MODEL_NAME_ORDERDETAIL = OrderDetailMO.class.getSimpleName();

	public String addOrder(OrderMO orderMO, List<OrderDetailMO> orderDetailMOs) throws LittleCatException
	{
		if (orderMO == null)
		{
			throw new LittleCatException(ErrorCode.GiveNullObjectToCreate.getCode(), ErrorCode.GiveNullObjectToCreate.getMsg().replace("{INFO_NAME}", MODEL_NAME));
		}

		if (CollectionUtil.isEmpty(orderDetailMOs))
		{
			throw new LittleCatException(ErrorCode.GiveNullObjectToCreate.getCode(), ErrorCode.GiveNullObjectToCreate.getMsg().replace("{INFO_NAME}", MODEL_NAME_ORDERDETAIL));
		}

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
		orderDetailDao.add(orderDetailMOs);

		return orderId;
	}

	public void setOrderState2DaiQianShou(String id) throws LittleCatException
	{
		OrderMO mo = orderDao.getById(id);

		if (mo == null)
		{
			throw new LittleCatException(ErrorCode.GetInfoFromDBReturnEmpty.getCode(), ErrorCode.GetInfoFromDBReturnEmpty.getMsg().replace("{INFO_NAME}", MODEL_NAME));
		}

		mo.setState(OrderState.daiqianshou);
		mo.setPayTime(String.valueOf(DateTimeUtil.getCurrentTime()));

		orderDao.modify(mo);
	}

	public void setOrderState2YiShouHuo(String id) throws LittleCatException
	{
		OrderMO mo = orderDao.getById(id);

		if (mo == null)
		{
			throw new LittleCatException(ErrorCode.GetInfoFromDBReturnEmpty.getCode(), ErrorCode.GetInfoFromDBReturnEmpty.getMsg().replace("{INFO_NAME}", MODEL_NAME));
		}
		mo.setState(OrderState.yishouhuo);
		mo.setReceiveTime(String.valueOf(DateTimeUtil.getCurrentTime()));

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
		mo.setReturnApplyTime(String.valueOf(DateTimeUtil.getCurrentTime()));

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
		mo.setReturnCompleteTime(String.valueOf(DateTimeUtil.getCurrentTime()));

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
		orderDetailDao.deleteByOrderId(id);
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
		return orderDetailDao.getById(id);
	}

	public int getOrderDetailList(QueryParam queryParam, List<OrderDetailMO> mos) throws LittleCatException
	{
		return orderDetailDao.getList(queryParam, mos);
	}
}
