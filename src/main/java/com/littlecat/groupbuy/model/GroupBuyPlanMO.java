package com.littlecat.groupbuy.model;

import com.littlecat.cbb.common.BaseMO;

/**
 * 团购计划MO
 * 
 * @author amydady
 *
 */
public class GroupBuyPlanMO extends BaseMO
{
	private String goodsId;
	private String startTime;
	private String endTime;
	private long price;
	private long inventory;
	private int memberNum;
	private int limitBuyNum;
	private String enable;
	private String createTime;
	private String createOperatorId;

	// 配送范围ID
	// 快递费用规则ID

	public String getGoodsId()
	{
		return goodsId;
	}

	public void setGoodsId(String goodsId)
	{
		this.goodsId = goodsId;
	}

	public String getStartTime()
	{
		return startTime;
	}

	public void setStartTime(String startTime)
	{
		this.startTime = startTime;
	}

	public String getEndTime()
	{
		return endTime;
	}

	public void setEndTime(String endTime)
	{
		this.endTime = endTime;
	}

	public long getPrice()
	{
		return price;
	}

	public void setPrice(long price)
	{
		this.price = price;
	}

	public long getInventory()
	{
		return inventory;
	}

	public void setInventory(long inventory)
	{
		this.inventory = inventory;
	}

	public int getMemberNum()
	{
		return memberNum;
	}

	public void setMemberNum(int memberNum)
	{
		this.memberNum = memberNum;
	}

	public int getLimitBuyNum()
	{
		return limitBuyNum;
	}

	public void setLimitBuyNum(int limitBuyNum)
	{
		this.limitBuyNum = limitBuyNum;
	}

	public String getEnable()
	{
		return enable;
	}

	public void setEnable(String enable)
	{
		this.enable = enable;
	}

	public String getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(String createTime)
	{
		this.createTime = createTime;
	}

	public String getCreateOperatorId()
	{
		return createOperatorId;
	}

	public void setCreateOperatorId(String createOperatorId)
	{
		this.createOperatorId = createOperatorId;
	}

}
