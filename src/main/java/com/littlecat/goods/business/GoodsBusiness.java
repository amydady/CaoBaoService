package com.littlecat.goods.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.query.QueryParam;
import com.littlecat.common.consts.ErrorCode;
import com.littlecat.goods.dao.GoodsDao;
import com.littlecat.goods.model.GoodsMO;

@Component
@Transactional
public class GoodsBusiness
{
	private static final String MODEL_NAME = GoodsMO.class.getSimpleName();

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
		validateReqData(mo);
		return goodsDao.add(mo);
	}

	public void modify(GoodsMO mo) throws LittleCatException
	{
		validateReqData(mo);
		goodsDao.modify(mo);
	}

	public int getList(QueryParam queryParam, List<GoodsMO> mos) throws LittleCatException
	{
		return goodsDao.getList(queryParam, mos);
	}

	private void validateReqData(GoodsMO reqData) throws LittleCatException
	{
		if (reqData == null)
		{
			throw new LittleCatException(ErrorCode.RequestObjectIsNull.getCode(), ErrorCode.RequestObjectIsNull.getMsg().replace("{INFO_NAME}", MODEL_NAME));
		}
	}
}
