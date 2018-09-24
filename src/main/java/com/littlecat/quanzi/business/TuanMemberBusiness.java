package com.littlecat.quanzi.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.query.QueryParam;
import com.littlecat.quanzi.dao.TuanMemberDao;
import com.littlecat.quanzi.model.TuanMemberMO;

@Component
@Transactional
public class TuanMemberBusiness
{
	@Autowired
	private TuanMemberDao tuanMemberDao;

	public String add(TuanMemberMO mo) throws LittleCatException
	{
		return tuanMemberDao.add(mo);
	}

	public boolean modify(TuanMemberMO mo) throws LittleCatException
	{
		return tuanMemberDao.modify(mo);
	}

	public int getList(QueryParam queryParam, List<TuanMemberMO> mos) throws LittleCatException
	{
		return tuanMemberDao.getList(queryParam, mos);
	}
	
	public boolean isMember(String terminalUserId, String tuanId) throws LittleCatException
	{
		return tuanMemberDao.isMember(terminalUserId, tuanId);
	}
}
