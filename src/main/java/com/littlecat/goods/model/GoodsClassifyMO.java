package com.littlecat.goods.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

/**
 * 商品分类
 * 
 * @author amydady
 *
 */
public class GoodsClassifyMO
{
	private final String NO_PARENT_ID = "-1";

	private String id;
	private String name;
	private int sortNum;
	private String parentId = NO_PARENT_ID;
	private String remark;
	private String enable;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int getSortNum()
	{
		return sortNum;
	}

	public void setSortNum(int sortNum)
	{
		this.sortNum = sortNum;
	}

	public String getParentId()
	{
		return parentId;
	}

	public void setParentId(String parentId)
	{
		this.parentId = parentId;
	}

	public String getRemark()
	{
		return remark;
	}

	public void setRemark(String remark)
	{
		this.remark = remark;
	}

	public String getEnable()
	{
		return enable;
	}

	public void setEnable(String enable)
	{
		this.enable = enable;
	}

	public static class GoodsClassifyMapper implements RowMapper<GoodsClassifyMO>
	{
		@Override
		public GoodsClassifyMO mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			GoodsClassifyMO mo = new GoodsClassifyMO();

			mo.setId(rs.getString("id"));
			mo.setName(rs.getString("name"));
			mo.setSortNum(rs.getInt("sortNum"));
			mo.setParentId(rs.getString("parentId"));
			mo.setRemark(rs.getString("remark"));
			mo.setEnable(rs.getString("enable"));

			return mo;
		}
	}

}
