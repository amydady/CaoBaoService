package com.littlecat.goods.business;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.query.QueryParam;
import com.littlecat.common.consts.ServiceConsts;
import com.littlecat.goods.dao.HomeImgsDao;
import com.littlecat.goods.model.HomeImgsMO;

@Component
@Transactional
public class HomeImgsBusiness
{
	@Autowired
	private HomeImgsDao homeImgsDao;
	
	public HomeImgsMO getById(String id) throws LittleCatException
	{
		return homeImgsDao.getById(id);
	}

	public void delete(String id) throws LittleCatException
	{
		homeImgsDao.delete(id);
		File file = new File(ServiceConsts.IMG_path + id);
		file.delete();
	}

	public void delete(List<String> ids) throws LittleCatException
	{
		homeImgsDao.delete(ids);
		
		for(String id:ids)
		{
			File file = new File(ServiceConsts.IMG_path + id);
			file.delete();
		}
	}

	public String add(HomeImgsMO mo) throws LittleCatException
	{
		return homeImgsDao.add(mo);
	}
	
	public void modify(HomeImgsMO mo) throws LittleCatException
	{
		homeImgsDao.modify(mo);
	}

	public int getList(QueryParam queryParam, List<HomeImgsMO> mos) throws LittleCatException
	{
		return homeImgsDao.getList(queryParam, mos);
	}
}
