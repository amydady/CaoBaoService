package com.littlecat.order.model;

/**
 * 订单库存校验响应结果MO
 * @author amydady
 *
 */
public class OrderInventoryCheckRspMO
{
	private String goodsId;
	private String goodsName;
	private String goodsMainImgData;
	private String goodsCurrentInventory;
	
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
	public String getGoodsCurrentInventory()
	{
		return goodsCurrentInventory;
	}
	public void setGoodsCurrentInventory(String goodsCurrentInventory)
	{
		this.goodsCurrentInventory = goodsCurrentInventory;
	}
	
	
	
}
