package com.littlecat.commission.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.littlecat.cbb.common.BaseMO;
import com.littlecat.cbb.utils.StringUtil;

/**
 * 供应商MO
 * 
 * @author amydady
 *
 */
public class CommissionTypeMO extends BaseMO
{
	private String name;
	private String createTime;
	private String enable;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
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

	public static class MOMapper implements RowMapper<CommissionTypeMO>
	{
		@Override
		public CommissionTypeMO mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			CommissionTypeMO mo = new CommissionTypeMO();

			mo.setId(rs.getString("id"));
			mo.setName(rs.getString("name"));
			mo.setCreateTime(StringUtil.replace(rs.getString("createTime"), ".0", ""));
			mo.setEnable(rs.getString("enable"));

			return mo;
		}
	}
}
