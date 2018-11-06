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

	public void enable(String id) throws LittleCatException
	{
		sysOperatorDao.enable(id);
	}

	public void enable(List<String> ids) throws LittleCatException
	{
		sysOperatorDao.enable(ids);
	}

	public void disable(String id) throws LittleCatException
	{
		sysOperatorDao.disable(id);
	}

	public void disable(List<String> ids) throws LittleCatException
	{
		sysOperatorDao.disable(ids);
	}

	public SysOperatorMO login(String identity, String pwd) throws LittleCatException
	{
		return sysOperatorDao.login(identity, pwd);
	}
	
	public void resetPassword(String id) throws LittleCatException
	{
		sysOperatorDao.setPassword(id, sysOperatorDao.getById(id).getUsername());
	}

	public void changePassword(String id, String oldPwd, String pwd) throws LittleCatException
	{
		sysOperatorDao.changePassword(id, oldPwd,pwd);
	}

	public SysOperatorMO getById(String id) throws LittleCatException
	{
		return sysOperatorDao.getById(id);
	}

	public void modify(SysOperatorMO mo) throws LittleCatException
	{
		sysOperatorDao.modify(mo);
	}

	public String add(SysOperatorMO mo) throws LittleCatException
	{
		return sysOperatorDao.add(mo);
	}

	public int getList(QueryParam queryParam, List<SysOperatorMO> mos) throws LittleCatException
	{
		return sysOperatorDao.getList(queryParam, mos);
	}
}
