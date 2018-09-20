package com.littlecat.order.model;

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
	private String resId;
	private long price;
	private long goodsNum;
	private long fee;

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

	public long getFee()
	{
		return fee;
	}

	public void setFee(long fee)
	{
		this.fee = fee;
	}
}
