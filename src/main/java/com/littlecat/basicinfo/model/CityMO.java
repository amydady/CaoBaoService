package com.littlecat.basicinfo.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CityMO
{
	private String name;
	private String province;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getProvince()
	{
		return province;
	}

	public void setProvince(String province)
	{
		this.province = province;
	}

	public static class MOMapper implements RowMapper<CityMO>
	{
		@Override
		public CityMO mapRow(ResultSet rs, int num) throws SQLException
		{
			CityMO mo = new CityMO();
			mo.setName(rs.getString("name"));
			mo.setProvince(rs.getString("province"));

			return mo;
		}

	}

}
