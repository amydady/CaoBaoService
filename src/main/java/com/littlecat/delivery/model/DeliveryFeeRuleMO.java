package com.littlecat.delivery.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.littlecat.cbb.common.BaseMO;
import com.littlecat.cbb.utils.StringUtil;

/**
 * 物流费用规则MO
 * 
 * @author amydady
 *
 */
public class DeliveryFeeRuleMO extends BaseMO
{
	private String name;
	private String deliveryAreaId;
	private String calcType;
	private BigDecimal beginValue;
	private BigDecimal endValue;
	private long fee;
	private String createOperatorId;
	private String createTime;
	private String enable;
	
	//just for view

	private String deliveryAreaName;
	private String calcTypeName;
	private String createOperatorName;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getDeliveryAreaId()
	{
		return deliveryAreaId;
	}

	public void setDeliveryAreaId(String deliveryAreaId)
	{
		this.deliveryAreaId = deliveryAreaId;
	}

	public String getCalcType()
	{
		return calcType;
	}

	public void setCalcType(String calcType)
	{
		this.calcType = calcType;
	}

	public BigDecimal getBeginValue()
	{
		return beginValue;
	}

	public void setBeginValue(BigDecimal beginValue)
	{
		this.beginValue = beginValue;
	}

	public BigDecimal getEndValue()
	{
		return endValue;
	}

	public void setEndValue(BigDecimal endValue)
	{
		this.endValue = endValue;
	}

	public long getFee()
	{
		return fee;
	}

	public void setFee(long fee)
	{
		this.fee = fee;
	}

	public String getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(String createTime)
	{
		this.createTime = createTime;
	}

	public String getEnable()
	{
		return enable;
	}

	public void setEnable(String enable)
	{
		this.enable = enable;
	}

	public String getCreateOperatorId()
	{
		return createOperatorId;
	}

	public void setCreateOperatorId(String createOperatorId)
	{
		this.createOperatorId = createOperatorId;
	}

	public String getCreateOperatorName()
	{
		return createOperatorName;
	}

	public void setCreateOperatorName(String createOperatorName)
	{
		this.createOperatorName = createOperatorName;
	}

	public String getCalcTypeName()
	{
		return calcTypeName;
	}

	public void setCalcTypeName(String calcTypeName)
	{
		this.calcTypeName = calcTypeName;
	}

	public String getDeliveryAreaName()
	{
		return deliveryAreaName;
	}

	public void setDeliveryAreaName(String deliveryAreaName)
	{
		this.deliveryAreaName = deliveryAreaName;
	}

	public static class MOMapper implements RowMapper<DeliveryFeeRuleMO>
	{
		@Override
		public DeliveryFeeRuleMO mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			DeliveryFeeRuleMO mo = new DeliveryFeeRuleMO();

			mo.setId(rs.getString("id"));
			mo.setName(rs.getString("name"));
			mo.setDeliveryAreaId(rs.getString("deliveryAreaId"));
			mo.setCalcType(rs.getString("calcType"));
			mo.setBeginValue(rs.getBigDecimal("beginValue"));
			mo.setEndValue(rs.getBigDecimal("endValue"));
			mo.setFee(rs.getLong("fee"));
			mo.setCreateOperatorId(rs.getString("createOperatorId"));
			mo.setCreateTime(StringUtil.replace(rs.getString("createTime"), ".0", ""));
			mo.setEnable(rs.getString("enable"));
			

			mo.setCreateOperatorName(rs.getString("createOperatorName"));
			
			return mo;
		}
	}
}
