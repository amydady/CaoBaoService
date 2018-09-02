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

@RestController
@RequestMapping("/rest/littlecat/caobao/sys")
public class SystemController
{
	@PostMapping(value = "/account/login")
	public RestRsp<AccountMO> login(@RequestBody AccountMO account)
	{
		return null;
	}

	@GetMapping(value = "/account/{id}")
	public RestRsp<AccountMO> getById(@PathVariable String id)
	{
		return null;
	}

	@DeleteMapping(value = "/account/{id}")
	public RestSimpleRsp deleteById(@PathVariable String id)
	{
		return null;
	}

	@PutMapping(value = "/account")
	public RestRsp<AccountMO> modify(@RequestBody AccountMO account)
	{
		return null;
	}

	@PostMapping(value = "/account")
	public RestRsp<AccountMO> add(@RequestBody AccountMO account)
	{
		return null;
	}

	@PostMapping(value = "/accounts")
	public RestRsp<AccountMO> getList(@RequestBody QueryParam queryParam)
	{
		return null;
	}
}
