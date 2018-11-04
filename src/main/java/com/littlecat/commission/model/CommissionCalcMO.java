package com.littlecat.commission.model;

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
	private String createTime;

	private String goodsId;
	private long goodsFee;
	private String commissionTypeId;
	private long calcFee; // 结算的佣金总额
	private String payOperatorId;//佣金支付人员ID
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

	public long getCalcFee()
	{
		return calcFee;
	}

	public void setCalcFee(long calcFee)
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

	public String getGoodsId()
	{
		return goodsId;
	}

	public void setGoodsId(String goodsId)
	{
		this.goodsId = goodsId;
	}

	public long getGoodsFee()
	{
		return goodsFee;
	}

	public void setGoodsFee(long goodsFee)
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

	public String getPayOperatorId()
	{
		return payOperatorId;
	}

	public void setPayOperatorId(String payOperatorId)
	{
		this.payOperatorId = payOperatorId;
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
			mo.setCreateTime(StringUtil.replace(rs.getString("createTime"), ".0", ""));
			mo.setGoodsId(rs.getString("goodsId"));
			mo.setGoodsFee(rs.getLong("goodsFee"));
			mo.setCommissionTypeId(rs.getString("commissionTypeId"));
			mo.setCalcFee(rs.getLong("calcFee"));
			mo.setPayOperatorId(rs.getString("payOperatorId"));
			mo.setPayTime(StringUtil.replace(rs.getString("payTime"), ".0", ""));
			mo.setRemark(rs.getString("remark"));

			return mo;
		}
	}
}
