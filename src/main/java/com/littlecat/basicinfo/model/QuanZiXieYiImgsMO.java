package com.littlecat.basicinfo.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.littlecat.cbb.common.BaseMO;

/**
 * 圈子图片
 * 
 * @author amydady
 *
 */
public class QuanZiXieYiImgsMO extends BaseMO
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

	public static class MOMapper implements RowMapper<QuanZiXieYiImgsMO>
	{
		@Override
		public QuanZiXieYiImgsMO mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			QuanZiXieYiImgsMO mo = new QuanZiXieYiImgsMO();

			mo.setId(rs.getString("id"));
			mo.setSortNum(rs.getInt("sortNum"));
			mo.setImgData(rs.getString("imgData"));

			return mo;
		}
	}
}
