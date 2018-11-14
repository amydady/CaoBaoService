package com.littlecat.common.model;

public class AddressMO
{
	private String province;
	private String city;
	private String area;
	private String detailInfo;
	
	public AddressMO()
	{
		
	}

	public AddressMO(String province, String city, String area, String detailInfo)
	{
		super();
		this.province = province;
		this.city = city;
		this.area = area;
		this.detailInfo = detailInfo;
	}

	public String getDetailInfo()
	{
		return detailInfo;
	}

	public void setDetailInfo(String detailInfo)
	{
		this.detailInfo = detailInfo;
	}

	public String getProvince()
	{
		return province;
	}

	public void setProvince(String province)
	{
		this.province = province;
	}

	public String getCity()
	{
		return city;
	}

	public void setCity(String city)
	{
		this.city = city;
	}

	public String getArea()
	{
		return area;
	}

	public void setArea(String area)
	{
		this.area = area;
	}

}
