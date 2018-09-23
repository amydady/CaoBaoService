package com.littlecat.quanzi.model;

import com.littlecat.cbb.common.BaseMO;

/**
 * 团成员（粉丝）MO
 * 
 * @author amydady
 *
 */
public class TuanMemberMO extends BaseMO
{
	private String tuanId;
	private String terminalUserId;
	private String firstJoinTime; // 首次加入时间
	private String lastActiveTime; // 最后活跃时间
	private String enable;

	public TuanMemberMO()
	{
		super();
	}

	public String getTuanId()
	{
		return tuanId;
	}

	public void setTuanId(String tuanId)
	{
		this.tuanId = tuanId;
	}

	public String getTerminalUserId()
	{
		return terminalUserId;
	}

	public void setTerminalUserId(String terminalUserId)
	{
		this.terminalUserId = terminalUserId;
	}

	public String getFirstJoinTime()
	{
		return firstJoinTime;
	}

	public void setFirstJoinTime(String firstJoinTime)
	{
		this.firstJoinTime = firstJoinTime;
	}

	public String getLastActiveTime()
	{
		return lastActiveTime;
	}

	public void setLastActiveTime(String lastActiveTime)
	{
		this.lastActiveTime = lastActiveTime;
	}

	public String getEnable()
	{
		return enable;
	}

	public void setEnable(String enable)
	{
		this.enable = enable;
	}

}
