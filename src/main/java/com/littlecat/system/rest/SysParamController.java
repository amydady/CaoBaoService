package com.littlecat.system.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.littlecat.cbb.common.Consts;
import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.rest.RestRsp;
import com.littlecat.cbb.rest.RestSimpleRsp;
import com.littlecat.system.business.SysParamBusiness;
import com.littlecat.system.model.SysParamMO;

@RestController
@RequestMapping("/rest/littlecat/caobao/sys/param")
public class SysParamController
{
	@Autowired
	private SysParamBusiness sysParamBusiness;

	private static final Logger logger = LoggerFactory.getLogger(SysParamController.class);

	@PutMapping(value = "/modify")
	public RestSimpleRsp modify(@RequestBody SysParamMO mo)
	{
		RestSimpleRsp result = new RestSimpleRsp();

		try
		{
			sysParamBusiness.modify(mo);
		}
		catch (LittleCatException e)
		{
			result.setCode(e.getErrorCode());
			result.setMessage(e.getMessage());
			logger.error(e.getMessage(), e);
		}
		catch (Exception e)
		{
			result.setCode(Consts.ERROR_CODE_UNKNOW);
			result.setMessage(e.getMessage());
			logger.error(e.getMessage(), e);
		}

		return result;
	}

	@GetMapping(value = "/getlist")
	public RestRsp<SysParamMO> getList()
	{
		RestRsp<SysParamMO> result = new RestRsp<SysParamMO>();

		try
		{
			result.getData().addAll(sysParamBusiness.getList());
		}
		catch (LittleCatException e)
		{
			result.setCode(e.getErrorCode());
			result.setMessage(e.getMessage());
			logger.error(e.getMessage(), e);
		}
		catch (Exception e)
		{
			result.setCode(Consts.ERROR_CODE_UNKNOW);
			result.setMessage(e.getMessage());
			logger.error(e.getMessage(), e);
		}

		return result;
	}
}
