package com.littlecat.commission.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.littlecat.cbb.common.BaseMO;
import com.littlecat.cbb.utils.StringUtil;
import com.littlecat.common.consts.CommissionState;

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
	private BigDecimal goodsFee;
	private String commissionTypeId;
	private BigDecimal calcFee; // 结算的佣金总额
	private String payOperatorId;// 佣金支付人员ID
	private CommissionState state;
	private String applyTime;
	private String payTime;
	private String disableTime;
	
	//佣金申请的账户信息
	private String bankHolderName;
	private String bankName;
	private String bankAccount;
	private String zfbName;
	private String zfbAccount;

	// just for view
	private String terminalUserName;
	private String terminalUserImg;
	private String tuanZhangName;
	private String goodsName;
	private String goodsMainImgData;
	private String commissionTypeName;

	public String getTuanZhangName()
	{
		return tuanZhangName;
	}

	public void setTuanZhangName(String tuanZhangName)
	{
		this.tuanZhangName = tuanZhangName;
	}

	public String getGoodsName()
	{
		return goodsName;
	}

	public void setGoodsName(String goodsName)
	{
		this.goodsName = goodsName;
	}

	public String getCommissionTypeName()
	{
		return commissionTypeName;
	}

	public void setCommissionTypeName(String commissionTypeName)
	{
		this.commissionTypeName = commissionTypeName;
	}

	public String getPayOperatorName()
	{
		return payOperatorName;
	}

	public void setPayOperatorName(String payOperatorName)
	{
		this.payOperatorName = payOperatorName;
	}

	private String payOperatorName;

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

	public String getPayOperatorId()
	{
		return payOperatorId;
	}

	public void setPayOperatorId(String payOperatorId)
	{
		this.payOperatorId = payOperatorId;
	}

	public String getDisableTime()
	{
		return disableTime;
	}

	public void setDisableTime(String disableTime)
	{
		this.disableTime = disableTime;
	}

	public CommissionState getState()
	{
		return state;
	}

	public void setState(CommissionState state)
	{
		this.state = state;
	}

	public String getApplyTime()
	{
		return applyTime;
	}

	public void setApplyTime(String applyTime)
	{
		this.applyTime = applyTime;
	}

	public String getTerminalUserName()
	{
		return terminalUserName;
	}

	public void setTerminalUserName(String terminalUserName)
	{
		this.terminalUserName = terminalUserName;
	}

	public String getTerminalUserImg()
	{
		return terminalUserImg;
	}

	public void setTerminalUserImg(String terminalUserImg)
	{
		this.terminalUserImg = terminalUserImg;
	}

	public String getGoodsMainImgData()
	{
		return goodsMainImgData;
	}

	public void setGoodsMainImgData(String goodsMainImgData)
	{
		this.goodsMainImgData = goodsMainImgData;
	}
	
	

	public String getBankHolderName()
	{
		return bankHolderName;
	}

	public void setBankHolderName(String bankHolderName)
	{
		this.bankHolderName = bankHolderName;
	}

	public String getBankName()
	{
		return bankName;
	}

	public void setBankName(String bankName)
	{
		this.bankName = bankName;
	}

	public String getBankAccount()
	{
		return bankAccount;
	}

	public void setBankAccount(String bankAccount)
	{
		this.bankAccount = bankAccount;
	}

	public String getZfbName()
	{
		return zfbName;
	}

	public void setZfbName(String zfbName)
	{
		this.zfbName = zfbName;
	}

	public String getZfbAccount()
	{
		return zfbAccount;
	}

	public void setZfbAccount(String zfbAccount)
	{
		this.zfbAccount = zfbAccount;
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
			mo.setGoodsFee(rs.getBigDecimal("goodsFee"));
			mo.setCommissionTypeId(rs.getString("commissionTypeId"));
			mo.setCalcFee(rs.getBigDecimal("calcFee"));
			mo.setPayOperatorId(rs.getString("payOperatorId"));
			mo.setApplyTime(StringUtil.replace(rs.getString("applyTime"), ".0", ""));
			mo.setPayTime(StringUtil.replace(rs.getString("payTime"), ".0", ""));
			mo.setDisableTime(StringUtil.replace(rs.getString("disableTime"), ".0", ""));
			mo.setState(CommissionState.valueOf(rs.getString("state")));
			

			mo.setBankHolderName(rs.getString("bankHolderName"));
			mo.setBankName(rs.getString("bankName"));
			mo.setBankAccount(rs.getString("bankAccount"));
			mo.setZfbName(rs.getString("zfbName"));
			mo.setZfbAccount(rs.getString("zfbAccount"));

			// relation info
			try
			{
				mo.setTerminalUserName(rs.getString("terminalUserName"));
				mo.setTerminalUserImg(rs.getString("terminalUserImg"));
				mo.setTuanZhangName(rs.getString("tuanZhangName"));
				mo.setGoodsName(rs.getString("goodsName"));
				mo.setCommissionTypeName(rs.getString("commissionTypeName"));
				mo.setGoodsMainImgData(rs.getString("goodsMainImgData"));
			}
			catch (Exception e)
			{

			}

			return mo;
		}
	}
}
