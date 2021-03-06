package com.littlecat.goods.business;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.query.QueryParam;
import com.littlecat.common.consts.ServiceConsts;
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
	
	public GoodsMO getSummayInfoById(String id) throws LittleCatException
	{
		return goodsDao.getSummayInfoById(id);
	}

	public void delete(String id) throws LittleCatException
	{
		goodsDao.delete(id);
		
		File file = new File(ServiceConsts.IMG_path + id);
		file.delete();
	}

	public void delete(List<String> ids) throws LittleCatException
	{
		goodsDao.delete(ids);
		
		for(String id:ids)
		{
			File file = new File(ServiceConsts.IMG_path + id);
			file.delete();
		}
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
	
	/**
	 * 查询商品列表（微信小程序，展示普通商品）
	 * 
	 * @return
	 */
	public List<GoodsMO> getList4WxApp()
	{
		return goodsDao.getList4WxApp();
	}
	
	public List<GoodsMO> getList4WebApp(String name,String enable)
	{
		return goodsDao.getList4WebApp(name,enable);
	}
}
