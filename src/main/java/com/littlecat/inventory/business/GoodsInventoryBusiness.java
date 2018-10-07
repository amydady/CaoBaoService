package com.littlecat.inventory.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.query.QueryParam;
import com.littlecat.goods.business.GoodsBusiness;
import com.littlecat.inventory.dao.GoodsInventoryDao;
import com.littlecat.inventory.model.GoodsInventoryMO;
import com.littlecat.lock.business.ResLockBusiness;

@Component
@Transactional
public class GoodsInventoryBusiness
{
	@Autowired
	private GoodsInventoryDao goodsInventoryDao;

	@Autowired
	private GoodsBusiness goodsBusiness;

	@Autowired
	private ResLockBusiness resLockBusiness;

	public String add(GoodsInventoryMO mo) throws LittleCatException
	{
		return goodsInventoryDao.add(mo);
	}

	public int getList(QueryParam queryParam, List<GoodsInventoryMO> mos) throws LittleCatException
	{
		return goodsInventoryDao.getList(queryParam, mos);
	}
}
