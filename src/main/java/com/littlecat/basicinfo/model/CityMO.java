package com.littlecat.basicinfo.model;

import com.littlecat.cbb.common.BaseMO;

public class CityMO extends BaseMO
{
	private String name;
	private String provinceId;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getProvinceId()
	{
		return provinceId;
	}

	public void setProvinceId(String provinceId)
	{
		this.provinceId = provinceId;
	}

}
