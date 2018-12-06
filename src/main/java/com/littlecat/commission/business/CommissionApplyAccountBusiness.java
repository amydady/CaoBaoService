package com.littlecat.commission.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.commission.dao.CommissionApplyAccountDao;
import com.littlecat.commission.model.CommissionApplyMO;

@Component
@Transactional
public class CommissionApplyAccountBusiness
{
	@Autowired
	private CommissionApplyAccountDao commissionApplyAccountDao;

	public void add(CommissionApplyMO mo) throws LittleCatException
	{
		commissionApplyAccountDao.add(mo);
	}

	public CommissionApplyMO getByApplyTime(String tuanZhangId, String applyTime) throws LittleCatException
	{
		return commissionApplyAccountDao.getByApplyTime(tuanZhangId, applyTime);
	}

	public CommissionApplyMO getLatest(String tuanZhangId) throws LittleCatException
	{
		return commissionApplyAccountDao.getLatest(tuanZhangId);
	}
}
