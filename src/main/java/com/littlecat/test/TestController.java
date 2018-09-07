package com.littlecat.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.littlecat.system.model.SysOperatorMO;

@RestController
@RequestMapping("/rest/test")
public class TestController
{
	private Logger logger = LoggerFactory.getLogger(TestController.class);
	
	@PostMapping(value = "/a")
	public String test1(@RequestBody String account)
	{
		logger.info("tihs is a info");
		logger.warn("tihs is a warn");
		logger.error("tihs is a error");
		
		return "test1:post:" + account;
	}

	@GetMapping(value = "/a")
	public String test2()
	{
		logger.info("tihs is a info");
		logger.warn("tihs is a warn");
		logger.error("tihs is a error");
		
		return "test2:get:";
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
	public RestRsp<SysOperatorMO> modify(@RequestBody SysOperatorMO account)
	{
		return null;
	}

	@PostMapping(value = "/account")
	public RestRsp<SysOperatorMO> add(@RequestBody SysOperatorMO account)
	{
		return null;
	}

	@PostMapping(value = "/accounts")
	public RestRsp<SysOperatorMO> getList(@RequestBody QueryParam queryParam)
	{
		return null;
	}
}
