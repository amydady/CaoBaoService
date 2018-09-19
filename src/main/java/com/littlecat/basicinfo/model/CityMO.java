package com.littlecat.basicinfo.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

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
	
	public static class MOMapper implements RowMapper<CityMO>
	{
		@Override
		public CityMO mapRow(ResultSet rs, int num) throws SQLException
		{
			CityMO mo = new CityMO();
			mo.setId(rs.getString("id"));
			mo.setName(rs.getString("name"));
			mo.setProvinceId(rs.getString("provinceId"));
			
			return mo;
		}

	}

}
