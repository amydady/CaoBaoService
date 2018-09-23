package com.littlecat.quanzi.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.littlecat.cbb.common.BaseMO;
import com.littlecat.common.consts.BuyType;

/**
 * 团商品MO
 * 
 * @author amydady
 *
 */
public class TuanGoodsMO extends BaseMO
{
	private String tuanId;
	private BuyType buyType;
	private String goodsId; // 商品ID，秒杀计划ID，团购计划ID，取决于buyType

	public TuanGoodsMO()
	{
		super();
	}

	public String getTuanId()
	{
		return tuanId;
	}

	public void setTuanId(String tuanId)
	{
		this.tuanId = tuanId;
	}

	public BuyType getBuyType()
	{
		return buyType;
	}

	public void setBuyType(BuyType buyType)
	{
		this.buyType = buyType;
	}

	public String getGoodsId()
	{
		return goodsId;
	}

	public void setGoodsId(String goodsId)
	{
		this.goodsId = goodsId;
	}
	
	public static class MOMapper implements RowMapper<TuanGoodsMO>
	{
		@Override
		public TuanGoodsMO mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			TuanGoodsMO mo = new TuanGoodsMO();
			
			mo.setId(rs.getString("id"));
			mo.setTuanId(rs.getString("tuanId"));
			mo.setBuyType(BuyType.valueOf(rs.getString("buyType")));
			mo.setGoodsId(rs.getString("goodsId"));
			
			return mo;
		}
	}

}
