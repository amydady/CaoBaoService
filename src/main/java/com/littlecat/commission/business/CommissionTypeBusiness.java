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

	public void delete(String id) throws LittleCatException
	{
		commissionTypeDao.delete(id);
	}

	public void delete(List<String> ids) throws LittleCatException
	{
		commissionTypeDao.delete(ids);
	}

	public void enable(String id) throws LittleCatException
	{
		commissionTypeDao.enable(id);
	}

	public void enable(List<String> ids) throws LittleCatException
	{
		commissionTypeDao.enable(ids);
	}

	public void disable(String id) throws LittleCatException
	{
		commissionTypeDao.disable(id);
	}

	public void disable(List<String> ids) throws LittleCatException
	{
		commissionTypeDao.disable(ids);
	}

	public String add(CommissionTypeMO mo) throws LittleCatException
	{
		return commissionTypeDao.add(mo);
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
