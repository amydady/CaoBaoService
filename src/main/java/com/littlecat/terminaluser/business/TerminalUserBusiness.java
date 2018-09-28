package com.littlecat.terminaluser.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.query.QueryParam;
import com.littlecat.terminaluser.dao.TerminalUserDao;
import com.littlecat.terminaluser.model.TerminalUserMO;

@Component
@Transactional
public class TerminalUserBusiness
{
	@Autowired
	private TerminalUserDao terminalUserDao;

	public TerminalUserMO getById(String id) throws LittleCatException
	{
		return terminalUserDao.getById(id);
	}

	public boolean enable(String id) throws LittleCatException
	{
		return terminalUserDao.enable(id);
	}

	public boolean enable(List<String> ids) throws LittleCatException
	{
		return terminalUserDao.enable(ids);
	}

	public boolean disable(String id) throws LittleCatException
	{
		return terminalUserDao.disable(id);
	}

	public boolean disable(List<String> ids) throws LittleCatException
	{
		return terminalUserDao.disable(ids);
	}

	public String add(TerminalUserMO mo) throws LittleCatException
	{
		return terminalUserDao.add(mo);
	}

	public int getList(QueryParam queryParam, List<TerminalUserMO> mos) throws LittleCatException
	{
		return terminalUserDao.getList(queryParam, mos);
	}

	public boolean setTuanZhangYes(String id) throws LittleCatException
	{
		return terminalUserDao.setTuanZhangYes(id);
	}

	public boolean setTuanZhangNo(String id) throws LittleCatException
	{
		return terminalUserDao.setTuanZhangNo(id);
	}

	public boolean setMaiShouYes(String id) throws LittleCatException
	{
		return terminalUserDao.setMaiShouYes(id);
	}

	public boolean setMaiShouNo(String id) throws LittleCatException
	{
		return terminalUserDao.setMaiShouNo(id);
	}
}
