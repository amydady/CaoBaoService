package com.littlecat.delivery.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.query.QueryParam;
import com.littlecat.delivery.dao.DeliveryAreaDao;
import com.littlecat.delivery.model.DeliveryAreaMO;

@Component
@Transactional
public class DeliveryAreaBusiness
{
	@Autowired
	private DeliveryAreaDao deliveryAreaDao;

	public DeliveryAreaMO getById(String id) throws LittleCatException
	{
		return deliveryAreaDao.getById(id);
	}

	public void enable(String id) throws LittleCatException
	{
		deliveryAreaDao.enable(id);
	}

	public void enable(List<String> ids) throws LittleCatException
	{
		deliveryAreaDao.enable(ids);
	}

	public void disable(String id) throws LittleCatException
	{
		deliveryAreaDao.disable(id);
	}

	public void disable(List<String> ids) throws LittleCatException
	{
		deliveryAreaDao.disable(ids);
	}

	public String add(DeliveryAreaMO mo) throws LittleCatException
	{
		return deliveryAreaDao.add(mo);
	}

	public void modify(DeliveryAreaMO mo) throws LittleCatException
	{
		deliveryAreaDao.modify(mo);
	}

	public int getList(QueryParam queryParam, List<DeliveryAreaMO> mos) throws LittleCatException
	{
		return deliveryAreaDao.getList(queryParam, mos);
	}
}
