package com.littlecat.tuanzhang.model;

import com.littlecat.cbb.common.BaseMO;

/**
 * 团分类MO
 * 
 * @author amydady
 *
 */
public class TuanClassifyMO extends BaseMO
{
	private String name;
	private String remark;
	private String tuanZhangId;

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

	public String getTuanZhangId()
	{
		return tuanZhangId;
	}

	public void setTuanZhangId(String tuanZhangId)
	{
		this.tuanZhangId = tuanZhangId;
	}
}
