package com.littlecat.basicinfo.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.littlecat.cbb.common.BaseMO;

/**
 * 关于我们图片
 * 
 * @author amydady
 *
 */
public class AboutUsImgsMO extends BaseMO
{
	private int sortNum;
	private String imgData;

	public String getImgData()
	{
		return imgData;
	}

	public void setImgData(String imgData)
	{
		this.imgData = imgData;
	}
	
	public int getSortNum()
	{
		return sortNum;
	}

	public void setSortNum(int sortNum)
	{
		this.sortNum = sortNum;
	}

	public static class MOMapper implements RowMapper<AboutUsImgsMO>
	{
		@Override
		public AboutUsImgsMO mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			AboutUsImgsMO mo = new AboutUsImgsMO();

			mo.setId(rs.getString("id"));
			mo.setSortNum(rs.getInt("sortNum"));
			mo.setImgData(rs.getString("imgData"));

			return mo;
		}
	}
}
