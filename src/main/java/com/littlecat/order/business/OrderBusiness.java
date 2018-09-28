package com.littlecat.order.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.query.QueryParam;
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

	public String addOrder(OrderMO orderMO, List<OrderDetailMO> orderDetailMOs) throws LittleCatException
	{
		// 创建订单
		String orderId = orderDao.add(orderMO);

		// 创建订单明细
		orderDetailDao.add(orderDetailMOs);

		return orderId;
	}

	/**
	 * 修改订单信息（可修改字段：状态）
	 * 
	 * @param mo
	 * @return
	 * @throws LittleCatException
	 */
	public boolean modifyOrder(OrderMO mo) throws LittleCatException
	{
		return orderDao.modify(mo);
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

	public List<OrderDetailMO> getOrderDetailByOrderId(String orderId) throws LittleCatException
	{
		return orderDetailDao.getByOrderId(orderId);
	}

	public int getOrderDetailList(QueryParam queryParam, List<OrderDetailMO> mos) throws LittleCatException
	{
		return orderDetailDao.getList(queryParam, mos);
	}
}
