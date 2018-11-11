package com.littlecat.delivery.model;

import java.math.BigDecimal;

import com.littlecat.cbb.common.BaseMO;

/**
 * 商品出仓单信息MO
 * 
 * @author amydady
 *
 */
public class OutWarehouseMO extends BaseMO
{
	private String orderDate;
	private String goodsId;
	private BigDecimal goodsNum;
	private String outOperatorId;
	private String createTime;
	private String outTime;
	private String state;// 是否已出仓，Y、N

	// just for view
	private String outOperatorName;
	private String goodsName;

	public String getOrderDate()
	{
		return orderDate;
	}

	public void setOrderDate(String orderDate)
	{
		this.orderDate = orderDate;
	}

	public String getGoodsId()
	{
		return goodsId;
	}

	public void setGoodsId(String goodsId)
	{
		this.goodsId = goodsId;
	}

	public BigDecimal getGoodsNum()
	{
		return goodsNum;
	}

	public void setGoodsNum(BigDecimal goodsNum)
	{
		this.goodsNum = goodsNum;
	}

	public String getOutOperatorId()
	{
		return outOperatorId;
	}

	public void setOutOperatorId(String outOperatorId)
	{
		this.outOperatorId = outOperatorId;
	}

	public String getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(String createTime)
	{
		this.createTime = createTime;
	}

	public String getOutTime()
	{
		return outTime;
	}

	public void setOutTime(String outTime)
	{
		this.outTime = outTime;
	}

	public String getState()
	{
		return state;
	}

	public void setState(String state)
	{
		this.state = state;
	}

	public String getOutOperatorName()
	{
		return outOperatorName;
	}

	public void setOutOperatorName(String outOperatorName)
	{
		this.outOperatorName = outOperatorName;
	}

	public String getGoodsName()
	{
		return goodsName;
	}

	public void setGoodsName(String goodsName)
	{
		this.goodsName = goodsName;
	}

}
