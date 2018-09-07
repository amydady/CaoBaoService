package com.littlecat.system.model;

import java.util.ArrayList;
import java.util.List;

import com.littlecat.cbb.base.BaseMO;

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

	private List<RoleMO> roles = new ArrayList<RoleMO>();

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

	public List<RoleMO> getRoles()
	{
		return roles;
	}

	public void setRoles(List<RoleMO> roles)
	{
		this.roles = roles;
	}

	public static String getTableName()
	{
		return "t_sys_sysoperator";
	}
}
