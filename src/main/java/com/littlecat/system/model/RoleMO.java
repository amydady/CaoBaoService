package com.littlecat.system.model;

import com.littlecat.cbb.common.BaseMO;

/**
 * 角色MO
 * 
 * @author amydady
 *
 */
public class RoleMO extends BaseMO
{
	private String name;

	public RoleMO()
	{
		super();
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
}
