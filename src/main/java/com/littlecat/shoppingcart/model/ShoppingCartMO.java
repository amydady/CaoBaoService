package com.littlecat.shoppingcart.model;

import com.littlecat.cbb.common.BaseMO;

/**
 * 购物车MO
 * 
 * @author amydady
 *
 */
public class ShoppingCartMO extends BaseMO
{
	private String terminalUserId;
	private String goodsId;
	private long goodsNum;
	private long price;
	private String createTime;

	public String getTerminalUserId()
	{
		return terminalUserId;
	}

	public void setTerminalUserId(String terminalUserId)
	{
		this.terminalUserId = terminalUserId;
	}

	public String getGoodsId()
	{
		return goodsId;
	}

	public void setGoodsId(String goodsId)
	{
		this.goodsId = goodsId;
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

	public String getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(String createTime)
	{
		this.createTime = createTime;
	}

}
