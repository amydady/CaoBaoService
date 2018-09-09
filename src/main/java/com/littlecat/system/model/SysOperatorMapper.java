package com.littlecat.system.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class SysOperatorMapper implements RowMapper<SysOperatorMO>
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
