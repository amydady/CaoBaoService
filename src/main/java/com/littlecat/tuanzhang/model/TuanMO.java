package com.littlecat.tuanzhang.model;

import java.util.ArrayList;
import java.util.List;

import com.littlecat.cbb.common.BaseMO;
import com.littlecat.terminaluser.model.TerminalUserMO;

/**
 * 团MO
 * 
 * @author amydady
 *
 */
public class TuanMO extends BaseMO
{
	private String name;
	private String remark;
	private String createTime;
	private String tuanClassifyId;
	private String tuanZhangId;
	
	public TuanMO()
	{
		super();
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getRemark()
	{
		return remark;
	}

	public void setRemark(String remark)
	{
		this.remark = remark;
	}

	public String getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(String createTime)
	{
		this.createTime = createTime;
	}

	public String getTuanClassifyId()
	{
		return tuanClassifyId;
	}

	public void setTuanClassifyId(String tuanClassifyId)
	{
		this.tuanClassifyId = tuanClassifyId;
	}

	public String getTuanZhangId()
	{
		return tuanZhangId;
	}

	public void setTuanZhangId(String tuanZhangId)
	{
		this.tuanZhangId = tuanZhangId;
	}

	
	
}
