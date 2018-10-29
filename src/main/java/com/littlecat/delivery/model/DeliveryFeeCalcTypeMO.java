package com.littlecat.delivery.model;

import com.littlecat.cbb.common.BaseMO;

/**
 * 行为MO
 * 
 * @author amydady
 *
 */
public class DeliveryFeeCalcTypeMO extends BaseMO
{
	private String name;
	private String sortNum;

	public DeliveryFeeCalcTypeMO()
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

	public String getSortNum()
	{
		return sortNum;
	}

	public void setSortNum(String sortNum)
	{
		this.sortNum = sortNum;
	}
}
