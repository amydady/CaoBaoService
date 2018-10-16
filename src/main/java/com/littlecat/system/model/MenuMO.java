package com.littlecat.system.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.littlecat.cbb.common.BaseMO;

/**
 * 菜单MO
 * 
 * @author amydady
 *
 */
public class MenuMO extends BaseMO
{
	private String name;
	private String targeTurl;
	private String pid;
	private String sortNum;
	private String enable;

	public MenuMO()
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

	public String getPid()
	{
		return pid;
	}

	public void setPid(String pid)
	{
		this.pid = pid;
	}

	public String getSortNum()
	{
		return sortNum;
	}

	public void setSortNum(String sortNum)
	{
		this.sortNum = sortNum;
	}

	public String getEnable()
	{
		return enable;
	}

	public void setEnable(String enable)
	{
		this.enable = enable;
	}

	public String getTargeTurl()
	{
		return targeTurl;
	}

	public void setTargeTurl(String targeTurl)
	{
		this.targeTurl = targeTurl;
	}

	public static class MOMapper implements RowMapper<MenuMO>
	{
		@Override
		public MenuMO mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			MenuMO mo = new MenuMO();

			mo.setId(rs.getString("id"));
			mo.setName(rs.getString("name"));
			mo.setTargeTurl(rs.getString("targeTurl"));
			mo.setPid(rs.getString("pid"));
			mo.setSortNum(rs.getString("sortNum"));
			mo.setEnable(rs.getString("enable"));

			return mo;
		}
	}

}
