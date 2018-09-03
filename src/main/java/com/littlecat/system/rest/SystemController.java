package com.littlecat.system.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.littlecat.cbb.query.QueryParam;
import com.littlecat.cbb.rest.RestRsp;
import com.littlecat.cbb.rest.RestSimpleRsp;
import com.littlecat.system.business.SysOperatorBusiness;
import com.littlecat.system.model.AccountMO;
import com.littlecat.system.model.SysOperatorMO;

@RestController
@RequestMapping("/rest/littlecat/caobao/sys")
public class SystemController
{
	@Autowired
	private SysOperatorBusiness sysOperatorBusiness;
	
	@PostMapping(value = "/sysoperator/login")
	public RestRsp<SysOperatorMO> login(@RequestBody AccountMO account)
	{
		return sysOperatorBusiness.login(account);
	}

	@GetMapping(value = "/sysoperator/{id}")
	public RestRsp<SysOperatorMO> getById(@PathVariable String id)
	{
		return sysOperatorBusiness.getById(id);
	}

	@DeleteMapping(value = "/sysoperator/{id}")
	public RestSimpleRsp deleteById(@PathVariable String id)
	{
		return sysOperatorBusiness.deleteById(id);
	}

	@PutMapping(value = "/sysoperator")
	public RestRsp<SysOperatorMO> modify(@RequestBody AccountMO account)
	{
		return sysOperatorBusiness.modify(account);
	}

	@PostMapping(value = "/sysoperator")
	public RestRsp<SysOperatorMO> add(@RequestBody AccountMO account)
	{
		return sysOperatorBusiness.add(account);
	}

	@PostMapping(value = "/sysoperators")
	public RestRsp<SysOperatorMO> getList(@RequestBody QueryParam queryParam)
	{
		return sysOperatorBusiness.getList(queryParam);
	}
}
