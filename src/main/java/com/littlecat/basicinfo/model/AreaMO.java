package com.littlecat.basicinfo.model;

import com.littlecat.cbb.common.BaseMO;

public class AreaMO extends BaseMO
{
	private String name;
	private String cityId;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getCityId()
	{
		return cityId;
	}

	public void setCityId(String cityId)
	{
		this.cityId = cityId;
	}
}
