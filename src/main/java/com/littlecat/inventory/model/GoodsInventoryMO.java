package com.littlecat.inventory.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

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

	public static class MOMapper implements RowMapper<GoodsInventoryMO>
	{
		@Override
		public GoodsInventoryMO mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			GoodsInventoryMO mo = new GoodsInventoryMO();

			mo.setId(rs.getString("id"));
			mo.setGoodsId(rs.getString("goodsId"));
			mo.setChangeValue(rs.getBigDecimal("changeValue"));
			mo.setChangeType(InventoryChangeType.valueOf(rs.getString("changeType")));
			mo.setOperatorId(rs.getString("operatorId"));
			mo.setDescription(rs.getString("description"));
			mo.setCreateTime(rs.getString("createTime"));

			return mo;
		}
	}

}
