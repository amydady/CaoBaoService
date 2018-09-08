package com.littlecat.system.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.littlecat.cbb.query.QueryParam;
import com.littlecat.cbb.rest.RestRsp;
import com.littlecat.cbb.rest.RestSimpleRsp;
import com.littlecat.system.dao.SysOperatorDao;
import com.littlecat.system.model.SysOperatorMO;

@Component
@Transactional
public class SysOperatorBusiness
{
	@Autowired
	private SysOperatorDao sysOperatorDao;
	
	public SysOperatorMO login(String id,String pwd)
	{
		return sysOperatorDao.login(id,pwd);
	}

	public RestRsp<SysOperatorMO> getById(String id)
	{
		return null;
	}

	public RestSimpleRsp deleteById(String id)
	{
		return null;
	}

	public RestRsp<SysOperatorMO> modify(SysOperatorMO account)
	{
		return null;
	}

	public RestRsp<SysOperatorMO> add(SysOperatorMO account)
	{
		return null;
	}

	public RestRsp<SysOperatorMO> getList(QueryParam queryParam)
	{
		return null;
	}
}
