package com.littlecat.system.model;

import com.littlecat.cbb.common.BaseMO;

/**
 * 行为MO
 * 
 * @author amydady
 *
 */
public class ActionMO extends BaseMO
{
	private String id;
	private String name;
	private int sortNum;
	private String enable;

	public ActionMO()
	{
		super();
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int getSortNum()
	{
		return sortNum;
	}

	public void setSortNum(int sortNum)
	{
		this.sortNum = sortNum;
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
