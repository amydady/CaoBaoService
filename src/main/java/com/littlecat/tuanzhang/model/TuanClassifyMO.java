package com.littlecat.tuanzhang.model;

import java.util.ArrayList;
import java.util.List;

import com.littlecat.cbb.base.BaseMO;

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
	private List<TuanMO> tuanList = new ArrayList<TuanMO>();

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

	public List<TuanMO> getTuanList()
	{
		return tuanList;
	}

	public void setTuanList(List<TuanMO> tuanList)
	{
		this.tuanList = tuanList;
	}

}
