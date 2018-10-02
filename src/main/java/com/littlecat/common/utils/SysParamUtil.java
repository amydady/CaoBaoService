package com.littlecat.common.utils;

import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.system.business.SysParamBusiness;

public class SysParamUtil
{
	public static String PARAM_NAME_MEMBER_ENABLE_DAYS = "member_enable_days";
	
	private static final SysParamBusiness sysParamBusiness = SpringUtil.getBean(SysParamBusiness.class);

	public static String getValueByName(String name) throws LittleCatException
	{
		return sysParamBusiness.getValueByName(name);
	}
}
