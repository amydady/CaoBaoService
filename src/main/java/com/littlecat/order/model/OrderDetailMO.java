package com.littlecat.order.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.littlecat.cbb.common.BaseMO;
import com.littlecat.common.consts.BuyType;

/**
 * 订单明细MO
 * 
 * @author amydady
 *
 */
public class OrderDetailMO extends BaseMO
{
	private String orderId;
	private BuyType buyType;
	private String resId; // 根据butType类型区分，代表商品ID、秒杀计划ID、团购任务实例ID
	private String goodsId;
	private long price;
	private long goodsNum;

	private String goodsName;
	private String goodsMainImgData = "";

	public String getOrderId()
	{
		return orderId;
	}

	public void setOrderId(String orderId)
	{
		this.orderId = orderId;
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

	public long getPrice()
	{
		return price;
	}

	public void setPrice(long price)
	{
		this.price = price;
	}

	public String getGoodsId()
	{
		return goodsId;
	}

	public void setGoodsId(String goodsId)
	{
		this.goodsId = goodsId;
	}

	public String getGoodsName()
	{
		return goodsName;
	}

	public void setGoodsName(String goodsName)
	{
		this.goodsName = goodsName;
	}

	public String getGoodsMainImgData()
	{
		return goodsMainImgData;
	}

	public void setGoodsMainImgData(String goodsMainImgData)
	{
		this.goodsMainImgData = goodsMainImgData;
	}

	public static class MOMapper implements RowMapper<OrderDetailMO>
	{
		@Override
		public OrderDetailMO mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			OrderDetailMO mo = new OrderDetailMO();

			mo.setId(rs.getString("id"));
			mo.setOrderId(rs.getString("orderId"));
			mo.setBuyType(BuyType.valueOf(rs.getString("buyType")));
			mo.setResId(rs.getString("resId"));
			mo.setGoodsId(rs.getString("goodsId"));
			mo.setPrice(rs.getLong("price"));
			mo.setGoodsNum(rs.getLong("goodsNum"));
			
			
			mo.setGoodsName(rs.getString("goodsName"));
			mo.setGoodsMainImgData(rs.getString("goodsMainImgData"));

			return mo;
		}
	}
}
