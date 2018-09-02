package com.littlecat.system.rest;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.littlecat.cbb.rest.QueryParam;
import com.littlecat.cbb.rest.RestRsp;
import com.littlecat.cbb.rest.RestSimpleRsp;
import com.littlecat.system.model.AccountMO;
import com.littlecat.system.model.SysOperatorMO;

@RestController
@RequestMapping("/rest/littlecat/caobao/sys")
public class SystemController
{
	@PostMapping(value = "/sysoperator/login")
	public RestRsp<SysOperatorMO> login(@RequestBody AccountMO account)
	{
		return null;
	}

	@GetMapping(value = "/sysoperator/{id}")
	public RestRsp<SysOperatorMO> getById(@PathVariable String id)
	{
		return null;
	}

	@DeleteMapping(value = "/sysoperator/{id}")
	public RestSimpleRsp deleteById(@PathVariable String id)
	{
		return null;
	}

	@PutMapping(value = "/sysoperator")
	public RestRsp<SysOperatorMO> modify(@RequestBody AccountMO account)
	{
		return null;
	}

	@PostMapping(value = "/sysoperator")
	public RestRsp<SysOperatorMO> add(@RequestBody AccountMO account)
	{
		return null;
	}

	@PostMapping(value = "/sysoperators")
	public RestRsp<SysOperatorMO> getList(@RequestBody QueryParam queryParam)
	{
		return null;
	}
}
