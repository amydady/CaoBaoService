package com.littlecat.supplier.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

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
	
	public static class MOMapper implements RowMapper<SupplierMO>
	{
		@Override
		public SupplierMO mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			SupplierMO mo = new SupplierMO();
			
			mo.setId(rs.getString("id"));
			mo.setName(rs.getString("name"));
			mo.setRemark(rs.getString("remark"));
			mo.setCreateTime(rs.getString("createTime"));
			
			return mo;
		}
	}
}
