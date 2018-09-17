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
	private TerminalUserDao terminalUser;
	
	public TerminalUserMO getByWXCode(String wxCode) throws LittleCatException
	{
		return terminalUser.getByWXCode(wxCode);
	}
	
	public TerminalUserMO getById(String id) throws LittleCatException
	{
		return terminalUser.getById(id);
	}

	public boolean modify(TerminalUserMO mo) throws LittleCatException
	{
		return terminalUser.modify(mo);
	}

	public String add(TerminalUserMO mo) throws LittleCatException
	{
		return terminalUser.add(mo);
	}

	public int getList(QueryParam queryParam,List<TerminalUserMO> mos) throws LittleCatException
	{
		return terminalUser.getList(queryParam, mos);
	}
}