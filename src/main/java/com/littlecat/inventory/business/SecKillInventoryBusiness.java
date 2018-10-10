package com.littlecat.inventory.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.query.QueryParam;
import com.littlecat.inventory.dao.SecKillInventoryDao;
import com.littlecat.inventory.model.SecKillInventoryMO;

@Component
@Transactional
public class SecKillInventoryBusiness
{
	@Autowired
	private SecKillInventoryDao secKillInventoryDao;

	public String add(SecKillInventoryMO mo) throws LittleCatException
	{
		return secKillInventoryDao.add(mo);
	}

	public long getCurrentValueByPlanId(String planId) throws LittleCatException
	{
		return secKillInventoryDao.getCurrentValueByPlanId(planId);
	}

	public int getList(QueryParam queryParam, List<SecKillInventoryMO> mos) throws LittleCatException
	{
		return secKillInventoryDao.getList(queryParam, mos);
	}
}
