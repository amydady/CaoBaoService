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

	public void delete(String id) throws LittleCatException
	{
		goodsDao.delete(id);
	}

	public void delete(List<String> ids) throws LittleCatException
	{
		goodsDao.delete(ids);
	}

	public void enable(String id) throws LittleCatException
	{
		goodsDao.enable(id);
	}

	public void enable(List<String> ids) throws LittleCatException
	{
		goodsDao.enable(ids);
	}

	public void disable(String id) throws LittleCatException
	{
		goodsDao.disable(id);
	}

	public void disable(List<String> ids) throws LittleCatException
	{
		goodsDao.disable(ids);
	}

	public String add(GoodsMO mo) throws LittleCatException
	{
		return goodsDao.add(mo);
	}

	public void modify(GoodsMO mo) throws LittleCatException
	{
		goodsDao.modify(mo);
	}

	public int getList(QueryParam queryParam, List<GoodsMO> mos) throws LittleCatException
	{
		return goodsDao.getList(queryParam, mos);
	}
}
