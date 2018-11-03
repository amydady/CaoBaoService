package com.littlecat.commission.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.query.QueryParam;
import com.littlecat.commission.dao.CommissionCalcDetailDao;
import com.littlecat.commission.model.CommissionCalcDetailMO;

@Component
@Transactional
public class CommissionCalcDetailBusiness
{
	@Autowired
	private CommissionCalcDetailDao commissionCalcDetailDao;

	public CommissionCalcDetailMO getById(String id) throws LittleCatException
	{
		return commissionCalcDetailDao.getById(id);
	}

	public void add(List<CommissionCalcDetailMO> mos) throws LittleCatException
	{
		commissionCalcDetailDao.add(mos);
	}

	public int getList(QueryParam queryParam, List<CommissionCalcDetailMO> mos) throws LittleCatException
	{
		return commissionCalcDetailDao.getList(queryParam, mos);
	}
}
