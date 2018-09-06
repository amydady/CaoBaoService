package com.littlecat.system.model;

/**
 * 系统操作人员
 * amydady
 *
 */
public class SysOperatorMO extends AccountMO
{
	public SysOperatorMO()
	{
		super();
	}

	public static String getTableName()
	{
		return "t_sys_sysoperator";
	}

	public static String getDefaultQueryFields()
	{
		return "id,username,name,wxCode,email,mobile";
	}
}
