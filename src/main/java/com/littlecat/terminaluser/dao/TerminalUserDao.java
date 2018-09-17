package com.littlecat.terminaluser.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.query.QueryParam;
import com.littlecat.common.DaoUtil;
import com.littlecat.consts.ErrorCode;
import com.littlecat.consts.TableName;
import com.littlecat.terminaluser.model.TerminalUserMO;

@Component
public class TerminalUserDao
{	
	@Autowired
    protected JdbcTemplate jdbcTemplate;
	
	public TerminalUserMO getByWXCode(String wxCode)
	{
		return null;
	}
	
	public TerminalUserMO getById(String id)
	{
		return null;
	}
	
	
	
	
}
