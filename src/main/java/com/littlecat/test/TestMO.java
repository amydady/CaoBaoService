package com.littlecat.test;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class TestMO
{
	private String id;
	private String dateTime;
	private String imgData;

	public String getDateTime()
	{
		return dateTime;
	}

	public void setDateTime(String dateTime)
	{
		this.dateTime = dateTime;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getImgData()
	{
		return imgData;
	}

	public void setImgData(String imgData)
	{
		this.imgData = imgData;
	}

	public static class MOMapper implements RowMapper<TestMO>
	{
		@Override
		public TestMO mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			TestMO mo = new TestMO();
			mo.setId(rs.getString("id"));
			mo.setDateTime(rs.getString("dateTime"));
			mo.setImgData(rs.getString("imgData"));

			return mo;
		}
	}

}
