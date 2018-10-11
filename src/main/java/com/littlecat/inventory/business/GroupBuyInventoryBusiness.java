package com.littlecat.inventory.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.query.QueryParam;
import com.littlecat.inventory.dao.GroupBuyInventoryDao;
import com.littlecat.inventory.model.GroupBuyInventoryMO;

@Component
@Transactional
public class GroupBuyInventoryBusiness
{
	@Autowired
	private GroupBuyInventoryDao groupBuyInventoryDao;

	public String add(GroupBuyInventoryMO mo) throws LittleCatException
	{
		return groupBuyInventoryDao.add(mo);
	}

	public long getCurrentValueByPlanId(String planId) throws LittleCatException
	{
		return groupBuyInventoryDao.getCurrentValueByPlanId(planId);
	}

	public int getList(QueryParam queryParam, List<GroupBuyInventoryMO> mos) throws LittleCatException
	{
		return groupBuyInventoryDao.getList(queryParam, mos);
	}
}
