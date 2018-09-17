package com.littlecat.goods.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.littlecat.cbb.common.BaseMO;

public class GoodsMO extends BaseMO
{
	private String classifyId;
	private String supplierId;
	private String name;
	private String price;
	private String enable;

	public String getClassifyId()
	{
		return classifyId;
	}

	public void setClassifyId(String classifyId)
	{
		this.classifyId = classifyId;
	}

	public String getSupplierId()
	{
		return supplierId;
	}

	public void setSupplierId(String supplierId)
	{
		this.supplierId = supplierId;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getPrice()
	{
		return price;
	}

	public void setPrice(String price)
	{
		this.price = price;
	}

	public String getEnable()
	{
		return enable;
	}

	public void setEnable(String enable)
	{
		this.enable = enable;
	}

	public static class GoodsMapper implements RowMapper<GoodsMO>
	{
		@Override
		public GoodsMO mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			GoodsMO mo = new GoodsMO();

			return mo;
		}
	}

}
