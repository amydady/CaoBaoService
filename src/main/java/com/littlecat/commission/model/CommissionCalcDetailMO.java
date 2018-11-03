package com.littlecat.commission.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.littlecat.cbb.common.BaseMO;

/**
 * 佣金结算明细信息MO
 * 
 * @author amydady
 *
 */
public class CommissionCalcDetailMO extends BaseMO
{
	private String calcId;
	private String goodsId; // 团长ID（结算的目标对象）
	private BigDecimal goodsFee; // 结算的佣金总额
	private String commissionTypeId;
	private BigDecimal calcFee;

	public String getGoodsId()
	{
		return goodsId;
	}

	public void setGoodsId(String goodsId)
	{
		this.goodsId = goodsId;
	}

	public BigDecimal getGoodsFee()
	{
		return goodsFee;
	}

	public void setGoodsFee(BigDecimal goodsFee)
	{
		this.goodsFee = goodsFee;
	}

	public String getCommissionTypeId()
	{
		return commissionTypeId;
	}

	public void setCommissionTypeId(String commissionTypeId)
	{
		this.commissionTypeId = commissionTypeId;
	}

	public BigDecimal getCalcFee()
	{
		return calcFee;
	}

	public void setCalcFee(BigDecimal calcFee)
	{
		this.calcFee = calcFee;
	}

	public String getCalcId()
	{
		return calcId;
	}

	public void setCalcId(String calcId)
	{
		this.calcId = calcId;
	}

	public static class MOMapper implements RowMapper<CommissionCalcDetailMO>
	{
		@Override
		public CommissionCalcDetailMO mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			CommissionCalcDetailMO mo = new CommissionCalcDetailMO();

			mo.setId(rs.getString("id"));
			mo.setCalcId(rs.getString("calcId"));
			mo.setGoodsId(rs.getString("goodsId"));
			mo.setGoodsFee(rs.getBigDecimal("goodsFee"));
			mo.setCommissionTypeId(rs.getString("commissionTypeId"));
			mo.setCalcFee(rs.getBigDecimal("calcFee"));

			return mo;
		}
	}
}
