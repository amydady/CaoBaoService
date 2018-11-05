package com.littlecat.terminaluser.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.littlecat.cbb.common.BaseMO;
import com.littlecat.cbb.utils.StringUtil;

public class TerminalUserMO extends BaseMO
{
	private String name;// 昵称
	private String image;// 头像
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

	public String getImage()
	{
		return image;
	}

	public void setImage(String image)
	{
		this.image = image;
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

	public static class MOMapper implements RowMapper<TerminalUserMO>
	{
		@Override
		public TerminalUserMO mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			TerminalUserMO mo = new TerminalUserMO();

			mo.setId(rs.getString("id"));
			mo.setName(rs.getString("name"));
			mo.setImage(rs.getString("image"));
			mo.setCreateTime(StringUtil.replace(rs.getString("createTime"), ".0", ""));
			mo.setEnable(rs.getString("enable"));

			return mo;
		}
	}

}
