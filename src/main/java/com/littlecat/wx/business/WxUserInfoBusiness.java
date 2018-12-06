package com.littlecat.wx.business;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.littlecat.cbb.rest.RestClient;
import com.littlecat.common.consts.ServiceConsts;

@Component
@Transactional
public class WxUserInfoBusiness
{
	public String login(String code)
	{
		return RestClient.get("https://api.weixin.qq.com/sns/jscode2session?appid="+ServiceConsts.WX_APPID+"&secret="+ServiceConsts.WX_SECRET+"&js_code=" + code + "&grant_type=authorization_code");
	}
}
