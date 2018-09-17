package com.littlecat.common;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.littlecat.cbb.common.Consts;
import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.query.QueryParam;
import com.littlecat.consts.ErrorCode;
import com.littlecat.consts.TableName;
import com.littlecat.terminaluser.model.TerminalUserMO;

public class DaoUtil
{
	private static class TotalNumMapper implements RowMapper<Integer>
	{
		@Override
		public Integer mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			return rs.getInt(Consts.COMMON_DB_RESULT_FIELDS_TOTALNUM);
		}
	}
	
	private DaoUtil()
	{
		
	}
	
	public static <T_MO> int getList(String tableName,QueryParam queryParam, List<T_MO> mos,JdbcTemplate jdbcTemplate,RowMapper<T_MO> rowMapper) throws LittleCatException
	{
		if(queryParam == null)
		{
			throw new LittleCatException(ErrorCode.QueryParamIsNull.getCode(),ErrorCode.QueryParamIsNull.getMsg().replace("{INFO_NAME}",tableName));
		}
		
		String sql = "select * from " + tableName + queryParam.getQueryDataConditionString();
		
		try
		{
			mos.addAll(jdbcTemplate.query(sql,rowMapper));
		}
		catch( DataAccessException e)
		{
			throw new LittleCatException(ErrorCode.DataAccessException.getCode(),ErrorCode.DataAccessException.getMsg(),e);
		}
		
		return DaoUtil.getTotalNum(TableName.TerminalUser.getName(),queryParam,jdbcTemplate);
	}
	
	
	public static int getTotalNum(String tableName,QueryParam queryParam,JdbcTemplate jdbcTemplate) throws LittleCatException
	{
		String sql = "select count(*) totalNum	from " + tableName;
		
		if(queryParam != null)
		{
			sql += queryParam.getQueryCountConditionString();
		}
		
		try
		{
			return jdbcTemplate.queryForObject(sql,new DaoUtil.TotalNumMapper());
		}
		catch( DataAccessException e)
		{
			throw new LittleCatException(ErrorCode.DataAccessException.getCode(),ErrorCode.DataAccessException.getMsg(),e);
		}
	}
	
	
}
