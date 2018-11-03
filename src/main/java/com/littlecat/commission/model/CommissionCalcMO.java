package com.littlecat.commission.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.littlecat.cbb.common.BaseMO;
import com.littlecat.cbb.utils.StringUtil;

/**
 * 佣金结算信息MO
 * 
 * @author amydady
 *
 */
public class CommissionCalcMO extends BaseMO
{
	private String orderId;
	private String tuanZhangId; // 团长ID（结算的目标对象）
	private BigDecimal calcFee; // 结算的佣金总额
	private String createTime;
	private BigDecimal payFee; // 实际支付金额
	private String payTime;
	private String remark;

	public String getOrderId()
	{
		return orderId;
	}

	public void setOrderId(String orderId)
	{
		this.orderId = orderId;
	}

	public String getTuanZhangId()
	{
		return tuanZhangId;
	}

	public void setTuanZhangId(String tuanZhangId)
	{
		this.tuanZhangId = tuanZhangId;
	}

	public BigDecimal getCalcFee()
	{
		return calcFee;
	}

	public void setCalcFee(BigDecimal calcFee)
	{
		this.calcFee = calcFee;
	}

	public String getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(String createTime)
	{
		this.createTime = createTime;
	}

	public String getPayTime()
	{
		return payTime;
	}

	public void setPayTime(String payTime)
	{
		this.payTime = payTime;
	}

	public String getRemark()
	{
		return remark;
	}

	public void setRemark(String remark)
	{
		this.remark = remark;
	}

	public BigDecimal getPayFee()
	{
		return payFee;
	}

	public void setPayFee(BigDecimal payFee)
	{
		this.payFee = payFee;
	}

	public static class MOMapper implements RowMapper<CommissionCalcMO>
	{
		@Override
		public CommissionCalcMO mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			CommissionCalcMO mo = new CommissionCalcMO();

			mo.setId(rs.getString("id"));
			mo.setOrderId(rs.getString("orderId"));
			mo.setTuanZhangId(rs.getString("tuanZhangId"));
			mo.setCalcFee(rs.getBigDecimal("calcFee"));
			mo.setCreateTime(StringUtil.replace(rs.getString("createTime"), ".0", ""));
			mo.setPayFee(rs.getBigDecimal("payFee"));
			mo.setPayTime(StringUtil.replace(rs.getString("payTime"), ".0", ""));
			mo.setRemark(rs.getString("remark"));

			return mo;
		}
	}
}
