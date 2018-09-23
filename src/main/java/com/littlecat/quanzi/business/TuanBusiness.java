package com.littlecat.quanzi.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.quanzi.dao.TuanDao;
import com.littlecat.quanzi.model.TuanMO;

@Component
@Transactional
public class TuanBusiness
{
	@Autowired
	private TuanDao tuanDao;
	
	public TuanMO getByTuanZhangId(String tuanZhangId) throws LittleCatException
	{
		return tuanDao.getByTuanZhangId(tuanZhangId);
	}
	
	public String add(TuanMO mo) throws LittleCatException
	{
		return tuanDao.add(mo);
	}
	
	public boolean delete(String id) throws LittleCatException
	{
		return tuanDao.delete(id);
	}
	
	public boolean delete(List<String> ids) throws LittleCatException
	{
		return tuanDao.delete(ids);
	}
	
	public boolean enable(String id) throws LittleCatException
	{
		return tuanDao.enable(id);
	}
	
	public boolean enable(List<String> ids) throws LittleCatException
	{
		return tuanDao.enable(ids);
	}
	
	public boolean disable(String id) throws LittleCatException
	{
		return tuanDao.disable(id);
	}
	
	public boolean disable(List<String> ids) throws LittleCatException
	{
		return tuanDao.disable(ids);
	}
	
	public boolean modify(TuanMO mo) throws LittleCatException
	{
		return tuanDao.modify(mo);
	}
}
