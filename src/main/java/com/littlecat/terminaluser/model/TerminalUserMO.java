package com.littlecat.terminaluser.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.littlecat.cbb.common.BaseMO;

/**
 * 终端用户
 * 
 * @author amydady
 *
 */
public class TerminalUserMO extends BaseMO
{
	private String wxCode;
	private String name;
	private String mobile;
	private String createTime;
	private String isTuanZhang; // 是否为团长（社圈长）
	private String isMaiShou; // 是否为买手
	
	public static class TerminalUserMapper implements RowMapper<TerminalUserMO>
	{
		@Override
		public TerminalUserMO mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			TerminalUserMO mo = new TerminalUserMO();
			
			mo.setId(rs.getString("id"));
			mo.setName(rs.getString("name"));
			mo.setWxCode(rs.getString("wxCode"));
			mo.setMobile(rs.getString("mobile"));
			mo.setIsTuanZhang(rs.getString("isTuanZhang"));
			mo.setIsMaiShou(rs.getString("isMaiShou"));
			mo.setCreateTime(rs.getString("createTime"));
			
			return mo;
		}
	}

	public TerminalUserMO()
	{
		super();
	}

	public String getWxCode()
	{
		return wxCode;
	}

	public void setWxCode(String wxCode)
	{
		this.wxCode = wxCode;
	}

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

	public String getMobile()
	{
		return mobile;
	}

	public void setMobile(String mobile)
	{
		this.mobile = mobile;
	}

	public String getIsTuanZhang()
	{
		return isTuanZhang;
	}

	public void setIsTuanZhang(String isTuanZhang)
	{
		this.isTuanZhang = isTuanZhang;
	}

	public String getIsMaiShou()
	{
		return isMaiShou;
	}

	public void setIsMaiShou(String isMaiShou)
	{
		this.isMaiShou = isMaiShou;
	}
	
	

}
