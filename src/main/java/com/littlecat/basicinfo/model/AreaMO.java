package com.littlecat.basicinfo.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AreaMO
{
	private String name;
	private String city;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getCity()
	{
		return city;
	}

	public void setCity(String city)
	{
		this.city = city;
	}

	public static class MOMapper implements RowMapper<AreaMO>
	{
		@Override
		public AreaMO mapRow(ResultSet rs, int num) throws SQLException
		{
			AreaMO mo = new AreaMO();
			mo.setName(rs.getString("name"));
			mo.setCity(rs.getString("city"));

			return mo;
		}

	}
}
