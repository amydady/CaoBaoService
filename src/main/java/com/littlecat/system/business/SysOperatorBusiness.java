package com.littlecat.system.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.query.QueryParam;
import com.littlecat.system.dao.SysOperatorDao;
import com.littlecat.system.model.SysOperatorMO;

@Component
@Transactional
public class SysOperatorBusiness
{
	@Autowired
	private SysOperatorDao sysOperatorDao;
	
	public SysOperatorMO login(String id,String pwd) throws LittleCatException
	{
		return sysOperatorDao.login(id,pwd);
	}
	
	public boolean changePassword(String id,String pwd) throws LittleCatException
	{
		return sysOperatorDao.changePassword(id, pwd);
	}

	public SysOperatorMO getById(String id) throws LittleCatException
	{
		return sysOperatorDao.getById(id);
	}

	public void deleteById(String id) throws LittleCatException
	{
		sysOperatorDao.delete(id);
	}
	
	public void deleteByIdList(List<String> ids) throws LittleCatException
	{
		sysOperatorDao.delete(ids);
	}

	public boolean modify(SysOperatorMO mo) throws LittleCatException
	{
		return sysOperatorDao.modify(mo);
	}

	public String add(SysOperatorMO mo) throws LittleCatException
	{
		return sysOperatorDao.add(mo);
	}

	public int getList(QueryParam queryParam,List<SysOperatorMO> mos) throws LittleCatException
	{
		return sysOperatorDao.getList(queryParam, mos);
	}
}
