package com.littlecat.inventory.model;

import com.littlecat.cbb.common.BaseMO;

/**
 * 商品库存MO
 * 
 * @author amydady
 *
 */
public class GoodsInventoryMO extends BaseMO
{
	private String goodsId;
	private long changeValue;
	private String changeType;
	private String operatorId;
	private String description;
	private String createTime;
	private int createYear;
	private int createMonth;

	public String getGoodsId()
	{
		return goodsId;
	}

	public void setGoodsId(String goodsId)
	{
		this.goodsId = goodsId;
	}

	public long getChangeValue()
	{
		return changeValue;
	}

	public void setChangeValue(long changeValue)
	{
		this.changeValue = changeValue;
	}

	public String getChangeType()
	{
		return changeType;
	}

	public void setChangeType(String changeType)
	{
		this.changeType = changeType;
	}

	public String getOperatorId()
	{
		return operatorId;
	}

	public void setOperatorId(String operatorId)
	{
		this.operatorId = operatorId;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(String createTime)
	{
		this.createTime = createTime;
	}

	public int getCreateYear()
	{
		return createYear;
	}

	public void setCreateYear(int createYear)
	{
		this.createYear = createYear;
	}

	public int getCreateMonth()
	{
		return createMonth;
	}

	public void setCreateMonth(int createMonth)
	{
		this.createMonth = createMonth;
	}

}
