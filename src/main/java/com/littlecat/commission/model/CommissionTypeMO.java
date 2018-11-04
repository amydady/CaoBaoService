package com.littlecat.commission.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.littlecat.cbb.common.BaseMO;

/**
 * 佣金类别MO
 * 
 * @author amydady
 *
 */
public class CommissionTypeMO extends BaseMO
{
	private String name;
	private BigDecimal commissionRate; // 默认的结算比例

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public BigDecimal getCommissionRate()
	{
		return commissionRate;
	}

	public void setCommissionRate(BigDecimal commissionRate)
	{
		this.commissionRate = commissionRate;
	}

	public static class MOMapper implements RowMapper<CommissionTypeMO>
	{
		@Override
		public CommissionTypeMO mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			CommissionTypeMO mo = new CommissionTypeMO();

			mo.setId(rs.getString("id"));
			mo.setName(rs.getString("name"));
			mo.setCommissionRate(rs.getBigDecimal("commissionRate"));

			return mo;
		}
	}
}
