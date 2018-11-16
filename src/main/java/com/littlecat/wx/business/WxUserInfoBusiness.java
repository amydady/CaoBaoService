package com.littlecat.wx.business;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.littlecat.cbb.rest.RestClient;

@Component
@Transactional
public class WxUserInfoBusiness
{
	public String login(String code)
	{
		return RestClient.get("https://api.weixin.qq.com/sns/jscode2session?appid=wx59e6873e9161c795&secret=4dc7b0ee2cb613eeb79faa811b998d25&js_code=" + code + "&grant_type=authorization_code");
	}
}
