package com.littlecat.supplier.model;

import com.littlecat.cbb.common.BaseMO;

/**
 * 供应商MO
 * 
 * @author amydady
 *
 */
public class SupplierMO extends BaseMO
{
	private String name;
	private String remark;
	private String createTime;
	
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
}
