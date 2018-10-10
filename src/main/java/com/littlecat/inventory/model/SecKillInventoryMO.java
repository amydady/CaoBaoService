package com.littlecat.inventory.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.littlecat.cbb.common.BaseMO;
import com.littlecat.common.consts.InventoryChangeType;

/**
 * 秒杀库存MO
 * 
 * @author amydady
 *
 */
public class SecKillInventoryMO extends BaseMO
{
	private String planId;
	private String goodsId;
	private long changeValue;
	private InventoryChangeType changeType;
	private String operatorId;
	private String description;
	private String createTime;
	private int createYear;
	private int createMonth;

	public String getPlanId()
	{
		return planId;
	}

	public void setPlanId(String planId)
	{
		this.planId = planId;
	}

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
	
	public static class MOMapper implements RowMapper<SecKillInventoryMO>
	{
		@Override
		public SecKillInventoryMO mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			SecKillInventoryMO mo = new SecKillInventoryMO();

			mo.setId(rs.getString("id"));
			mo.setPlanId(rs.getString("planId"));
			mo.setGoodsId(rs.getString("goodsId"));
			mo.setChangeValue(rs.getLong("changeValue"));
			mo.setChangeType(InventoryChangeType.valueOf(rs.getString("changeType")));
			mo.setOperatorId(rs.getString("operatorId"));
			mo.setDescription(rs.getString("description"));
			mo.setCreateTime(rs.getString("createTime"));

			return mo;
		}
	}

}
