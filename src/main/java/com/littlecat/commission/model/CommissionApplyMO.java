package com.littlecat.commission.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.littlecat.cbb.common.BaseMO;
import com.littlecat.cbb.utils.StringUtil;

/**
 * 佣金申请信息MO
 * 
 * @author amydady
 *
 */
public class CommissionApplyMO extends BaseMO
{
	private String tuanZhangId; // 团长ID（结算的目标对象）
	private String applyTime;

	// 佣金申请的账户信息
	private String bankHolderName;
	private String bankName;
	private String bankAccount;
	private String zfbName;
	private String zfbAccount;

	public String getTuanZhangId()
	{
		return tuanZhangId;
	}

	public void setTuanZhangId(String tuanZhangId)
	{
		this.tuanZhangId = tuanZhangId;
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

	public String getApplyTime()
	{
		return applyTime;
	}

	public void setApplyTime(String applyTime)
	{
		this.applyTime = applyTime;
	}

	public static class MOMapper implements RowMapper<CommissionApplyMO>
	{
		@Override
		public CommissionApplyMO mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			CommissionApplyMO mo = new CommissionApplyMO();

			mo.setTuanZhangId(rs.getString("tuanZhangId"));
			mo.setApplyTime(StringUtil.replace(rs.getString("applyTime"), ".0", ""));

			mo.setBankHolderName(rs.getString("bankHolderName"));
			mo.setBankName(rs.getString("bankName"));
			mo.setBankAccount(rs.getString("bankAccount"));
			mo.setZfbName(rs.getString("zfbName"));
			mo.setZfbAccount(rs.getString("zfbAccount"));

			return mo;
		}
	}

}
