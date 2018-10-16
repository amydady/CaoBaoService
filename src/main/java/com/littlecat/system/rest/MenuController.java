package com.littlecat.system.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.littlecat.cbb.common.Consts;
import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.rest.RestRsp;
import com.littlecat.system.business.MenuBusiness;
import com.littlecat.system.model.MenuMO;

@RestController
@RequestMapping("/rest/littlecat/caobao/sys/menu")
public class MenuController
{
	@Autowired
	private MenuBusiness menuBusiness;

	private static final Logger logger = LoggerFactory.getLogger(MenuController.class);


	@GetMapping(value = "/getbypid")
	public RestRsp<MenuMO> getByPid(@RequestParam String pid)
	{
		RestRsp<MenuMO> result = new RestRsp<MenuMO>();

		try
		{
			result.getData().addAll(menuBusiness.getByPid(pid));
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
