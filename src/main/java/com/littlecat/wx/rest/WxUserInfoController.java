package com.littlecat.wx.rest;

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
import com.littlecat.wx.business.WxUserInfoBusiness;

@RestController
@RequestMapping("/rest/littlecat/caobao/wxuserinfo")
public class WxUserInfoController
{
	@Autowired
	private WxUserInfoBusiness wxUserInfoBusiness;

	private static final Logger logger = LoggerFactory.getLogger(WxUserInfoController.class);

	@GetMapping(value = "/login")
	public RestRsp<String> getById(@RequestParam String code)
	{
		RestRsp<String> result = new RestRsp<String>();

		try
		{
			result.getData().add(wxUserInfoBusiness.login(code));
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
