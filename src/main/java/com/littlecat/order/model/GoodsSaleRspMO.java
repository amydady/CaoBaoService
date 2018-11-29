package com.littlecat.order.model;

import java.math.BigDecimal;

/**
 * 商品销售情况查询返回MO
 * 
 * @author amydady
 *
 */
public class GoodsSaleRspMO
{
	private String goodsId;
	private String goodsName;
	private String goodsMainImgData = "";
	private BigDecimal goodsTotalNum;
	private BigDecimal goodsTotalFee;

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

	public BigDecimal getGoodsTotalNum()
	{
		return goodsTotalNum;
	}

	public void setGoodsTotalNum(BigDecimal goodsTotalNum)
	{
		this.goodsTotalNum = goodsTotalNum;
	}

	public BigDecimal getGoodsTotalFee()
	{
		return goodsTotalFee;
	}

	public void setGoodsTotalFee(BigDecimal goodsTotalFee)
	{
		this.goodsTotalFee = goodsTotalFee;
	}

}
