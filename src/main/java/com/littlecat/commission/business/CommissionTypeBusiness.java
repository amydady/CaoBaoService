package com.littlecat.commission.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.query.QueryParam;
import com.littlecat.commission.dao.CommissionTypeDao;
import com.littlecat.commission.model.CommissionTypeMO;

@Component
@Transactional
public class CommissionTypeBusiness
{
	@Autowired
	private CommissionTypeDao commissionTypeDao;

	public CommissionTypeMO getById(String id) throws LittleCatException
	{
		return commissionTypeDao.getById(id);
	}

	public void modify(CommissionTypeMO mo) throws LittleCatException
	{
		commissionTypeDao.modify(mo);
	}

	public int getList(QueryParam queryParam, List<CommissionTypeMO> mos) throws LittleCatException
	{
		return commissionTypeDao.getList(queryParam, mos);
	}
}
