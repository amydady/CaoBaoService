package com.littlecat.commission.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.commission.dao.CommissionApplyDao;
import com.littlecat.commission.model.CommissionApplyMO;

@Component
@Transactional
public class CommissionApplyBusiness
{
	@Autowired
	private CommissionApplyDao commissionApplyDao;

	public void add(CommissionApplyMO mo) throws LittleCatException
	{
		commissionApplyDao.add(mo);
	}

	public CommissionApplyMO getByApplyTime(String tuanZhangId, String applyTime) throws LittleCatException
	{
		return commissionApplyDao.getByApplyTime(tuanZhangId, applyTime);
	}

	public CommissionApplyMO getLatest(String tuanZhangId) throws LittleCatException
	{
		return commissionApplyDao.getLatest(tuanZhangId);
	}
}
