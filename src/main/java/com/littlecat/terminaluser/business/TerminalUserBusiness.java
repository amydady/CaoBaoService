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

	public void delete(String id) throws LittleCatException
	{
		terminalUserDao.delete(id);
	}

	public void delete(List<String> ids) throws LittleCatException
	{
		terminalUserDao.delete(ids);
	}

	public void enable(String id) throws LittleCatException
	{
		terminalUserDao.enable(id);
	}

	public void enable(List<String> ids) throws LittleCatException
	{
		terminalUserDao.enable(ids);
	}

	public void disable(String id) throws LittleCatException
	{
		terminalUserDao.disable(id);
	}

	public void disable(List<String> ids) throws LittleCatException
	{
		terminalUserDao.disable(ids);
	}

	public String add(TerminalUserMO mo) throws LittleCatException
	{
		return terminalUserDao.add(mo);
	}

	public int getList(QueryParam queryParam, List<TerminalUserMO> mos) throws LittleCatException
	{
		return terminalUserDao.getList(queryParam, mos);
	}
}
