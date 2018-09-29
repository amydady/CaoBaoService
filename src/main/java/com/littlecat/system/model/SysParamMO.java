package com.littlecat.system.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

/**
 * 系统参数MO
 * 
 * @author amydady
 *
 */
public class SysParamMO
{
	private String name;
	private String value;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

	public static class MOMapper implements RowMapper<SysParamMO>
	{
		@Override
		public SysParamMO mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			SysParamMO mo = new SysParamMO();

			mo.setName(rs.getString("name"));
			mo.setValue(rs.getString("value"));

			return mo;
		}
	}

}
