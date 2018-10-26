package com.littlecat.commission.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.query.QueryParam;
import com.littlecat.commission.dao.CommissionGoodsDao;
import com.littlecat.commission.model.CommissionGoodsMO;

@Component
@Transactional
public class CommissionGoodsBusiness
{
	@Autowired
	private CommissionGoodsDao commissionGoodsDao;

	public CommissionGoodsMO getById(String id) throws LittleCatException
	{
		return commissionGoodsDao.getById(id);
	}

	public void delete(String id) throws LittleCatException
	{
		commissionGoodsDao.delete(id);
	}

	public void delete(List<String> ids) throws LittleCatException
	{
		commissionGoodsDao.delete(ids);
	}

	public void enable(String id) throws LittleCatException
	{
		commissionGoodsDao.enable(id);
	}

	public void enable(List<String> ids) throws LittleCatException
	{
		commissionGoodsDao.enable(ids);
	}

	public void disable(String id) throws LittleCatException
	{
		commissionGoodsDao.disable(id);
	}

	public void disable(List<String> ids) throws LittleCatException
	{
		commissionGoodsDao.disable(ids);
	}

	public String add(CommissionGoodsMO mo) throws LittleCatException
	{
		return commissionGoodsDao.add(mo);
	}

	public void modify(CommissionGoodsMO mo) throws LittleCatException
	{
		commissionGoodsDao.modify(mo);
	}

	public int getList(QueryParam queryParam, List<CommissionGoodsMO> mos) throws LittleCatException
	{
		return commissionGoodsDao.getList(queryParam, mos);
	}

	public List<CommissionGoodsMO> getListByGoodsId(String goodsId)
	{
		return commissionGoodsDao.getListByGoodsId(goodsId);
	}
}
