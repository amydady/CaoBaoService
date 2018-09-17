package com.littlecat.system.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.littlecat.cbb.common.BaseMO;

/**
 * 系统操作人员
 * amydady
 *
 */
public class SysOperatorMO extends BaseMO
{
	private String username;
	private String password;
	private String name;
	private String wxCode;
	private String email;
	private String mobile;

	public SysOperatorMO()
	{
		super();
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getWxCode()
	{
		return wxCode;
	}

	public void setWxCode(String wxCode)
	{
		this.wxCode = wxCode;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getMobile()
	{
		return mobile;
	}

	public void setMobile(String mobile)
	{
		this.mobile = mobile;
	}
	
	public static class MOMapper implements RowMapper<SysOperatorMO>
	{
		@Override
		public SysOperatorMO mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			SysOperatorMO mo = new SysOperatorMO();
			
			mo.setId(rs.getString("id"));
			mo.setUsername(rs.getString("username"));
			mo.setName(rs.getString("name"));
			mo.setWxCode(rs.getString("wxCode"));
			mo.setEmail(rs.getString("email"));
			mo.setMobile(rs.getString("mobile"));
			
			return mo;
		}
	}

}
