package com.littlecat.goods.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.query.QueryParam;
import com.littlecat.goods.dao.GoodsDetailImgsDao;
import com.littlecat.goods.model.GoodsDetailImgsMO;

@Component
@Transactional
public class GoodsDetailImgsBusiness
{
	@Autowired
	private GoodsDetailImgsDao goodsDetailImgsDao;

	public GoodsDetailImgsMO getById(String id) throws LittleCatException
	{
		return goodsDetailImgsDao.getById(id);
	}

	public void delete(String id) throws LittleCatException
	{
		goodsDetailImgsDao.delete(id);
	}

	public void delete(List<String> ids) throws LittleCatException
	{
		goodsDetailImgsDao.delete(ids);
	}

	public String add(GoodsDetailImgsMO mo) throws LittleCatException
	{
		return goodsDetailImgsDao.add(mo);
	}

	public void modify(GoodsDetailImgsMO mo) throws LittleCatException
	{
		goodsDetailImgsDao.modify(mo);
	}

	public int getList(QueryParam queryParam, List<GoodsDetailImgsMO> mos) throws LittleCatException
	{
		return goodsDetailImgsDao.getList(queryParam, mos);
	}
}
