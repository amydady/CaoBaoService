package com.littlecat.goods.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.query.QueryParam;
import com.littlecat.goods.dao.GoodsDao;
import com.littlecat.goods.model.GoodsMO;

@Component
@Transactional
public class GoodsBusiness
{
	@Autowired
	private GoodsDao goodsDao;

	public GoodsMO getById(String id) throws LittleCatException
	{
		return goodsDao.getById(id);
	}

	public boolean delete(String id) throws LittleCatException
	{
		return goodsDao.delete(id);
	}

	public boolean delete(List<String> ids) throws LittleCatException
	{
		return goodsDao.delete(ids);
	}

	public boolean enable(String id) throws LittleCatException
	{
		return goodsDao.enable(id);
	}

	public boolean enable(List<String> ids) throws LittleCatException
	{
		return goodsDao.enable(ids);
	}

	public boolean disable(String id) throws LittleCatException
	{
		return goodsDao.disable(id);
	}

	public boolean disable(List<String> ids) throws LittleCatException
	{
		return goodsDao.disable(ids);
	}

	public String add(GoodsMO mo) throws LittleCatException
	{
		return goodsDao.add(mo);
	}

	public boolean modify(GoodsMO mo) throws LittleCatException
	{
		return goodsDao.modify(mo);
	}

	public int getList(QueryParam queryParam, List<GoodsMO> mos) throws LittleCatException
	{
		return goodsDao.getList(queryParam, mos);
	}
}
