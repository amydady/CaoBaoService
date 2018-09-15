package com.littlecat.terminaluser.model;

import com.littlecat.cbb.common.BaseMO;

/**
 * 终端用户
 * 
 * @author amydady
 *
 */
public class TerminalUserMO extends BaseMO
{
	private String wxCode;
	private String name;
	private String mobile;
	private String refereeWxCode;// 推荐人微信
	private String createTime;
	private String isTuanZhang; // 是否为团长（社圈长）
	private String isMaiShou; // 是否为买手

	public TerminalUserMO()
	{
		super();
	}

	public String getWxCode()
	{
		return wxCode;
	}

	public void setWxCode(String wxCode)
	{
		this.wxCode = wxCode;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(String createTime)
	{
		this.createTime = createTime;
	}

	public String getRefereeWxCode()
	{
		return refereeWxCode;
	}

	public void setRefereeWxCode(String refereeWxCode)
	{
		this.refereeWxCode = refereeWxCode;
	}

	public String getMobile()
	{
		return mobile;
	}

	public void setMobile(String mobile)
	{
		this.mobile = mobile;
	}

	public String getIsTuanZhang()
	{
		return isTuanZhang;
	}

	public void setIsTuanZhang(String isTuanZhang)
	{
		this.isTuanZhang = isTuanZhang;
	}

	public String getIsMaiShou()
	{
		return isMaiShou;
	}

	public void setIsMaiShou(String isMaiShou)
	{
		this.isMaiShou = isMaiShou;
	}

}
