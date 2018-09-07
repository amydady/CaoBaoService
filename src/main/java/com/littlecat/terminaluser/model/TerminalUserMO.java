package com.littlecat.terminaluser.model;

import com.littlecat.cbb.base.BaseMO;

public class TerminalUserMO extends BaseMO
{
	private String wxCode;
	private String name;
	private String createTime;
	
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
	
	
}
