package com.littlecat.common.utils;

import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.utils.SpringUtil;
import com.littlecat.system.business.SysParamBusiness;

public class SysParamUtil
{
	//圈子成员有效期（天）
	public static String PARAM_NAME_MEMBER_ENABLE_DAYS = "member_enable_days";
	
	//秒杀计划后台按秒杀时间窗口定时处理失效标记的周期（秒）
	public static String SECKILLPLAN_ENABLETAG_PROCESS_CYC = "seckillplan_enabletag_process_cyc";
	
	private static final SysParamBusiness sysParamBusiness = SpringUtil.getBean(SysParamBusiness.class);

	public static String getValueByName(String name) throws LittleCatException
	{
		return sysParamBusiness.getValueByName(name);
	}
}
