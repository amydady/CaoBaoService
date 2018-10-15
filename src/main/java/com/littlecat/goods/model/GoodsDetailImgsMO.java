package com.littlecat.goods.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.littlecat.cbb.common.BaseMO;

/**
 * 商品明细信息图片
 * 
 * @author amydady
 *
 */
public class GoodsDetailImgsMO extends BaseMO
{
	private String goodsId;
	private String title;
	private String sortNum;
	private String imgData;

	public String getImgData()
	{
		return imgData;
	}

	public void setImgData(String imgData)
	{
		this.imgData = imgData;
	}

	public String getGoodsId()
	{
		return goodsId;
	}

	public void setGoodsId(String goodsId)
	{
		this.goodsId = goodsId;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getSortNum()
	{
		return sortNum;
	}

	public void setSortNum(String sortNum)
	{
		this.sortNum = sortNum;
	}

	public static class MOMapper implements RowMapper<GoodsDetailImgsMO>
	{
		@Override
		public GoodsDetailImgsMO mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			GoodsDetailImgsMO mo = new GoodsDetailImgsMO();

			mo.setId(rs.getString("id"));
			mo.setGoodsId(rs.getString("goodsId"));
			mo.setTitle(rs.getString("title"));
			mo.setSortNum(rs.getString("sortNum"));
			mo.setImgData(rs.getString("imgData"));

			return mo;
		}
	}
}
