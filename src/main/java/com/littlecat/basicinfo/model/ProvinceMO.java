package com.littlecat.basicinfo.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ProvinceMO
{
	private String name;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public static class MOMapper implements RowMapper<ProvinceMO>
	{

		@Override
		public ProvinceMO mapRow(ResultSet rs, int num) throws SQLException
		{
			ProvinceMO mo = new ProvinceMO();
			mo.setName(rs.getString("name"));
			
			return mo;
		}

	}
}
