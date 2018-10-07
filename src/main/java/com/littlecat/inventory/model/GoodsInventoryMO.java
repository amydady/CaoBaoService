package com.littlecat.inventory.model;

import com.littlecat.cbb.common.BaseMO;
import com.littlecat.common.consts.InventoryChangeType;

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
	private InventoryChangeType changeType;
	private String operatorId;
	private String description;
	private String createTime;

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

	public InventoryChangeType getChangeType()
	{
		return changeType;
	}

	public void setChangeType(InventoryChangeType changeType)
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
}
