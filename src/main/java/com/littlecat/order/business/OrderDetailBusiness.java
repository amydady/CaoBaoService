package com.littlecat.order.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.query.QueryParam;
import com.littlecat.order.dao.OrderDetailDao;
import com.littlecat.order.model.OrderDetailMO;

@Component
@Transactional
public class OrderDetailBusiness
{
	@Autowired
	private OrderDetailDao orderDetailDao;

	public List<String> add(List<OrderDetailMO> mos) throws LittleCatException
	{
		return orderDetailDao.add(mos);
	}

	public OrderDetailMO getById(String id) throws LittleCatException
	{
		return orderDetailDao.getById(id);
	}

	public List<OrderDetailMO> getByOrderId(String orderId) throws LittleCatException
	{
		return orderDetailDao.getByOrderId(orderId);
	}

	public void deleteByOrderId(String orderId) throws LittleCatException
	{
		orderDetailDao.deleteByOrderId(orderId);
	}

	public int getList(QueryParam queryParam, List<OrderDetailMO> mos) throws LittleCatException
	{
		return orderDetailDao.getList(queryParam, mos);
	}
}
