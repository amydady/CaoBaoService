package com.littlecat.shoppingcart.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.littlecat.cbb.common.BaseMO;
import com.littlecat.common.consts.BuyType;

/**
 * 购物车MO
 * 
 * @author amydady
 *
 */
public class ShoppingCartMO extends BaseMO
{
	private String terminalUserId;
	private BuyType buyType;
	private String resId;
	private long goodsNum;
	private String createTime;

	public String getTerminalUserId()
	{
		return terminalUserId;
	}

	public void setTerminalUserId(String terminalUserId)
	{
		this.terminalUserId = terminalUserId;
	}

	public BuyType getBuyType()
	{
		return buyType;
	}

	public void setBuyType(BuyType buyType)
	{
		this.buyType = buyType;
	}

	public String getResId()
	{
		return resId;
	}

	public void setResId(String resId)
	{
		this.resId = resId;
	}

	public long getGoodsNum()
	{
		return goodsNum;
	}

	public void setGoodsNum(long goodsNum)
	{
		this.goodsNum = goodsNum;
	}

	public String getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(String createTime)
	{
		this.createTime = createTime;
	}

	public static class MOMapper implements RowMapper<ShoppingCartMO>
	{
		@Override
		public ShoppingCartMO mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			ShoppingCartMO mo = new ShoppingCartMO();

			mo.setId(rs.getString("id"));
			mo.setTerminalUserId(rs.getString("terminalUserId"));
			mo.setBuyType(BuyType.valueOf(rs.getString("buyType")));
			mo.setResId(rs.getString("resId"));
			mo.setGoodsNum(rs.getLong("goodsNum"));
			mo.setCreateTime(rs.getString("createTime"));

			return mo;
		}
	}

}
