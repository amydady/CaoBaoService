package com.littlecat.system.business;

import org.springframework.stereotype.Component;

import com.littlecat.cbb.rest.QueryParam;
import com.littlecat.cbb.rest.RestRsp;
import com.littlecat.cbb.rest.RestSimpleRsp;
import com.littlecat.system.model.AccountMO;
import com.littlecat.system.model.SysOperatorMO;

@Component
public class SysOperatorBusiness
{
	public RestRsp<SysOperatorMO> login(AccountMO account)
	{
		return null;
	}

	public RestRsp<SysOperatorMO> getById(String id)
	{
		return null;
	}

	public RestSimpleRsp deleteById(String id)
	{
		return null;
	}

	public RestRsp<SysOperatorMO> modify(AccountMO account)
	{
		return null;
	}

	public RestRsp<SysOperatorMO> add(AccountMO account)
	{
		return null;
	}

	public RestRsp<SysOperatorMO> getList(QueryParam queryParam)
	{
		return null;
	}
}
