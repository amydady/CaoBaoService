package com.littlecat.system.rest;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.littlecat.cbb.rest.QueryParam;
import com.littlecat.cbb.rest.RestRsp;
import com.littlecat.cbb.rest.RestSimpleRsp;
import com.littlecat.system.model.AccountMO;

@RestController
@RequestMapping("/rest/test/2")
public class TestController2
{
	@RestController
	@RequestMapping("/haha")
	public class TestController21
	{
		@PostMapping(value = "/b")
		public String test21(@RequestBody String account)
		{
			return "test21:post:" + account;
		}
	}
	@PostMapping(value = "/b")
	public String test21(@RequestBody String account)
	{
		return "test21:post:" + account;
	}

	@GetMapping(value = "/b")
	public String test22()
	{
		return "test22:get:";
	}
	
	@PutMapping(value = "/b")
	public String test23(@RequestBody String account)
	{
		return "test23:post:" + account;
	}
	
	@GetMapping(value = "/")
	public String test3()
	{
		return "test3:get:";
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
