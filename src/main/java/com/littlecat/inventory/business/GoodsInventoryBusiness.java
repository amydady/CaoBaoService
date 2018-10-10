package com.littlecat.inventory.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.query.QueryParam;
import com.littlecat.inventory.dao.GoodsInventoryDao;
import com.littlecat.inventory.model.GoodsInventoryMO;

@Component
@Transactional
public class GoodsInventoryBusiness
{
	@Autowired
	private GoodsInventoryDao goodsInventoryDao;

	public String add(GoodsInventoryMO mo) throws LittleCatException
	{
		return goodsInventoryDao.add(mo);
	}

	public long getCurrentValueByGoodsId(String goodsId) throws LittleCatException
	{
		return goodsInventoryDao.getCurrentValueByGoodsId(goodsId);
	}

	public int getList(QueryParam queryParam, List<GoodsInventoryMO> mos) throws LittleCatException
	{
		return goodsInventoryDao.getList(queryParam, mos);
	}
}
