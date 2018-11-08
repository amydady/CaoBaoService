package com.littlecat.goods.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.littlecat.cbb.common.BaseMO;

/**
 * 首页滚动图片
 * 
 * @author amydady
 *
 */
public class HomeImgsMO extends BaseMO
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

	public static class MOMapper implements RowMapper<HomeImgsMO>
	{
		@Override
		public HomeImgsMO mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			HomeImgsMO mo = new HomeImgsMO();

			mo.setId(rs.getString("id"));
			mo.setSortNum(rs.getInt("sortNum"));
			mo.setImgData(rs.getString("imgData"));

			return mo;
		}
	}
}
