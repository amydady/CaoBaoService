package com.littlecat.lock.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.littlecat.cbb.common.Consts;
import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.rest.RestSimpleRsp;
import com.littlecat.lock.business.ResLockBusiness;

@RestController
@RequestMapping("/rest/littlecat/caobao/reslock")
public class ResLockController
{
	@Autowired
	private ResLockBusiness resLockBusiness;

	private static final Logger logger = LoggerFactory.getLogger(ResLockController.class);

	@GetMapping(value = "/clear")
	public RestSimpleRsp clear()
	{
		RestSimpleRsp result = new RestSimpleRsp();

		try
		{
			resLockBusiness.clearLock();
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
