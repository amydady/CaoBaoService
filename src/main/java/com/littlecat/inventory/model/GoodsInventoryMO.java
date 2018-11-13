package com.littlecat.inventory.model;

import java.math.BigDecimal;

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
	private BigDecimal changeValue;
	private InventoryChangeType changeType;
	private String operatorId;
	private String description;
	private String createTime;

	// just for view
	private String operatorName;

	public String getGoodsId()
	{
		return goodsId;
	}

	public void setGoodsId(String goodsId)
	{
		this.goodsId = goodsId;
	}

	public BigDecimal getChangeValue()
	{
		return changeValue;
	}

	public void setChangeValue(BigDecimal changeValue)
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

	public String getOperatorName()
	{
		return operatorName;
	}

	public void setOperatorName(String operatorName)
	{
		this.operatorName = operatorName;
	}
}
