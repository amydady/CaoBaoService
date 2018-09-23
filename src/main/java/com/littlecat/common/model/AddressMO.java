package com.littlecat.common.model;

public class AddressMO
{
	private String provinceId;
	private String cityId;
	private String areaId;
	private String detailInfo;
	
	public AddressMO(String provinceId, String cityId, String areaId, String detailInfo)
	{
		super();
		this.provinceId = provinceId;
		this.cityId = cityId;
		this.areaId = areaId;
		this.detailInfo = detailInfo;
	}
	
	public String getProvinceId()
	{
		return provinceId;
	}
	public void setProvinceId(String provinceId)
	{
		this.provinceId = provinceId;
	}
	public String getCityId()
	{
		return cityId;
	}
	public void setCityId(String cityId)
	{
		this.cityId = cityId;
	}
	public String getAreaId()
	{
		return areaId;
	}
	public void setAreaId(String areaId)
	{
		this.areaId = areaId;
	}
	public String getDetailInfo()
	{
		return detailInfo;
	}
	public void setDetailInfo(String detailInfo)
	{
		this.detailInfo = detailInfo;
	}
	
	
}
