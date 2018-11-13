package com.littlecat.inventory.business;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.goods.business.GoodsBusiness;
import com.littlecat.goods.model.GoodsMO;
import com.littlecat.inventory.dao.GoodsInventoryDao;
import com.littlecat.inventory.model.GoodsInventoryMO;

@Component
@Transactional
public class GoodsInventoryBusiness
{
	@Autowired
	private GoodsInventoryDao goodsInventoryDao;
	@Autowired
	private GoodsBusiness goodsBusiness;

	public String add(GoodsInventoryMO mo) throws LittleCatException
	{
		String id = goodsInventoryDao.add(mo);

		// 刷新产品最新可用库存
		GoodsMO goodsMO = goodsBusiness.getById(mo.getGoodsId());
		goodsMO.setCurrentInventory(goodsInventoryDao.getCurrentValueByGoodsId(mo.getGoodsId()));
		goodsBusiness.modify(goodsMO);

		return id;
	}

	public BigDecimal getCurrentValueByGoodsId(String goodsId) throws LittleCatException
	{
		return goodsInventoryDao.getCurrentValueByGoodsId(goodsId);
	}

	public List<GoodsInventoryMO> getListByGoodsId(String goodsId) throws LittleCatException
	{
		return goodsInventoryDao.getListByGoodsId(goodsId);
	}
}
