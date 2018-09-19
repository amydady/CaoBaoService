package com.littlecat.basicinfo.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

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
	
	public static class MOMapper implements RowMapper<AreaMO>
	{
		@Override
		public AreaMO mapRow(ResultSet rs, int num) throws SQLException
		{
			AreaMO mo = new AreaMO();
			mo.setId(rs.getString("id"));
			mo.setName(rs.getString("name"));
			mo.setCityId(rs.getString("cityId"));
			
			return mo;
		}

	}
}
