package com.littlecat.system.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.system.dao.SysParamDao;
import com.littlecat.system.model.SysParamMO;

@Component
@Transactional
public class SysParamBusiness
{
	@Autowired
	private SysParamDao sysParamDao;
	
	public void modify(SysParamMO mo) throws LittleCatException
	{
		sysParamDao.modify(mo);
	}

	public List<SysParamMO> getList() throws LittleCatException
	{
		return sysParamDao.getList();
	}
}
