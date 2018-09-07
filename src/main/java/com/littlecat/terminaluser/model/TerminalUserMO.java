package com.littlecat.terminaluser.model;

import java.util.ArrayList;
import java.util.List;

import com.littlecat.cbb.base.BaseMO;
import com.littlecat.tuanzhang.model.TuanMO;

/**
 * 终端用户
 * amydady
 *
 */
public class TerminalUserMO extends BaseMO
{
	private String wxCode;
	private String name;
	private String mobile;
	private String refereeWxCode;//推荐人微信
	private List<TuanMO> tuanList = new ArrayList<TuanMO>();
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

	public String getRefereeWxCode()
	{
		return refereeWxCode;
	}

	public void setRefereeWxCode(String refereeWxCode)
	{
		this.refereeWxCode = refereeWxCode;
	}

	public List<TuanMO> getTuanList()
	{
		return tuanList;
	}

	public void setTuanList(List<TuanMO> tuanList)
	{
		this.tuanList = tuanList;
	}

	public String getMobile()
	{
		return mobile;
	}

	public void setMobile(String mobile)
	{
		this.mobile = mobile;
	}
	
	
}
