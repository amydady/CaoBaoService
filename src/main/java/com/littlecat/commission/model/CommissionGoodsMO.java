package com.littlecat.commission.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.littlecat.cbb.common.BaseMO;
import com.littlecat.cbb.utils.StringUtil;

/**
 * 商品佣金规则设置MO
 * 
 * @author amydady
 *
 */
public class CommissionGoodsMO extends BaseMO
{
	private String goodsId;
	private String commissionTypeId;
	private BigDecimal commissionRate;
	private String createTime;
	private String enable;

	// just fow view
	private String commissionTypeName;

	public String getGoodsId()
	{
		return goodsId;
	}

	public void setGoodsId(String goodsId)
	{
		this.goodsId = goodsId;
	}

	public String getCommissionTypeId()
	{
		return commissionTypeId;
	}

	public void setCommissionTypeId(String commissionTypeId)
	{
		this.commissionTypeId = commissionTypeId;
	}

	public BigDecimal getCommissionRate()
	{
		return commissionRate;
	}

	public void setCommissionRate(BigDecimal commissionRate)
	{
		this.commissionRate = commissionRate;
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

	public String getCommissionTypeName()
	{
		return commissionTypeName;
	}

	public void setCommissionTypeName(String commissionTypeName)
	{
		this.commissionTypeName = commissionTypeName;
	}

	public static class MOMapper implements RowMapper<CommissionGoodsMO>
	{
		@Override
		public CommissionGoodsMO mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			CommissionGoodsMO mo = new CommissionGoodsMO();

			mo.setId(rs.getString("id"));
			mo.setGoodsId(rs.getString("goodsId"));
			mo.setCommissionTypeId(rs.getString("commissionTypeId"));
			mo.setCommissionRate(rs.getBigDecimal("commissionRate"));
			mo.setCreateTime(StringUtil.replace(rs.getString("createTime"), ".0", ""));
			mo.setEnable(rs.getString("enable"));

			try
			{
				mo.setCommissionTypeName(rs.getString("commissionTypeName"));
			}
			catch (Exception e)
			{

			}

			return mo;
		}
	}
}
