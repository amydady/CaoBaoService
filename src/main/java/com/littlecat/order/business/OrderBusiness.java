package com.littlecat.order.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.query.QueryParam;
import com.littlecat.order.dao.OrderDao;
import com.littlecat.order.model.OrderMO;

@Component
@Transactional
public class OrderBusiness
{
	@Autowired
	private OrderDao orderDao;

	public String addOrder(OrderMO mo) throws LittleCatException
	{
		return orderDao.add(mo);
	}

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
}
