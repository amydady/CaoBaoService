package com.littlecat.tuanzhang.model;

import com.littlecat.cbb.base.BaseMO;

/**
 * 团分类MO
 * amydady
 *
 */
public class TuanClassifyMO extends BaseMO
{
	private String name;
	private String remark;
	
	public TuanClassifyMO()
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
	
	
}
