package com.littlecat.common.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.littlecat.cbb.common.Consts;
import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.query.QueryParam;
import com.littlecat.cbb.utils.CollectionUtil;
import com.littlecat.cbb.utils.StringUtil;
import com.littlecat.common.consts.ErrorCode;

public final class DaoUtil
{
	private final static String FIELD_NAME_ENABLE = "enable";

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

	public static void delete(String tableName, String id, JdbcTemplate jdbcTemplate) throws LittleCatException
	{
		if (StringUtil.isEmpty(id))
		{
			throw new LittleCatException(ErrorCode.DeleteObjectWithEmptyId.getCode(), ErrorCode.DeleteObjectWithEmptyId.getMsg().replace("{INFO_NAME}", tableName));
		}

		String sql = "delete from " + tableName + " where id = ?";

		try
		{
			int ret = jdbcTemplate.update(sql, new Object[] { id });
			if (ret > 1)
			{
				throw new LittleCatException(ErrorCode.DeleteObjectWithIdError.getCode(), ErrorCode.DeleteObjectWithIdError.getMsg().replace("{INFO_NAME}", tableName) + "id=" + id);
			}
		}
		catch (DataAccessException e)
		{
			throw new LittleCatException(ErrorCode.DataAccessException.getCode(), ErrorCode.DataAccessException.getMsg(), e);
		}
	}

	public static void delete(String tableName, List<String> ids, JdbcTemplate jdbcTemplate) throws LittleCatException
	{
		if (CollectionUtil.isEmpty(ids))
		{
			throw new LittleCatException(ErrorCode.DeleteObjectWithEmptyId.getCode(), ErrorCode.DeleteObjectWithEmptyId.getMsg().replace("{INFO_NAME}", tableName));
		}

		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);

		String sql = "delete from " + tableName + " where id in (:ids)";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("ids", ids);

		try
		{
			namedParameterJdbcTemplate.update(sql, parameters);
		}
		catch (DataAccessException e)
		{
			throw new LittleCatException(ErrorCode.DataAccessException.getCode(), ErrorCode.DataAccessException.getMsg(), e);
		}
	}

	public static void disable(String tableName, String id, JdbcTemplate jdbcTemplate) throws LittleCatException
	{
		if (StringUtil.isEmpty(id))
		{
			throw new LittleCatException(ErrorCode.DisableObjectWithEmptyId.getCode(), ErrorCode.DisableObjectWithEmptyId.getMsg().replace("{INFO_NAME}", tableName));
		}

		String sql = "update " + tableName + " set " + FIELD_NAME_ENABLE + " = 'N' where id = ?";

		try
		{
			int ret = jdbcTemplate.update(sql, new Object[] { id });
			if (ret > 1)
			{
				throw new LittleCatException(ErrorCode.DisableObjectWithIdError.getCode(), ErrorCode.DisableObjectWithIdError.getMsg().replace("{INFO_NAME}", tableName) + "id=" + id);
			}
		}
		catch (DataAccessException e)
		{
			throw new LittleCatException(ErrorCode.DataAccessException.getCode(), ErrorCode.DataAccessException.getMsg(), e);
		}
	}

	public static void disable(String tableName, List<String> ids, JdbcTemplate jdbcTemplate) throws LittleCatException
	{
		if (CollectionUtil.isEmpty(ids))
		{
			throw new LittleCatException(ErrorCode.DisableObjectWithEmptyId.getCode(), ErrorCode.DisableObjectWithEmptyId.getMsg().replace("{INFO_NAME}", tableName));
		}

		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);

		String sql = "update " + tableName + " set " + FIELD_NAME_ENABLE + " = 'N' where id in (:ids)";

		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("ids", ids);

		try
		{
			namedParameterJdbcTemplate.update(sql, parameters);
		}
		catch (DataAccessException e)
		{
			throw new LittleCatException(ErrorCode.DataAccessException.getCode(), ErrorCode.DataAccessException.getMsg(), e);
		}
	}

	public static void enable(String tableName, String id, JdbcTemplate jdbcTemplate) throws LittleCatException
	{
		if (StringUtil.isEmpty(id))
		{
			throw new LittleCatException(ErrorCode.EnableObjectWithEmptyId.getCode(), ErrorCode.EnableObjectWithEmptyId.getMsg().replace("{INFO_NAME}", tableName));
		}

		String sql = "update " + tableName + " set " + FIELD_NAME_ENABLE + " = 'Y' where id = ?";

		try
		{
			int ret = jdbcTemplate.update(sql, new Object[] { id });
			if (ret > 1)
			{
				throw new LittleCatException(ErrorCode.EnableObjectWithIdError.getCode(), ErrorCode.EnableObjectWithIdError.getMsg().replace("{INFO_NAME}", tableName) + "id=" + id);
			}
		}
		catch (DataAccessException e)
		{
			throw new LittleCatException(ErrorCode.DataAccessException.getCode(), ErrorCode.DataAccessException.getMsg(), e);
		}
	}

	public static void enable(String tableName, List<String> ids, JdbcTemplate jdbcTemplate) throws LittleCatException
	{
		if (CollectionUtil.isEmpty(ids))
		{
			throw new LittleCatException(ErrorCode.EnableObjectWithEmptyId.getCode(), ErrorCode.EnableObjectWithEmptyId.getMsg().replace("{INFO_NAME}", tableName));
		}

		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);

		String sql = "update " + tableName + " set " + FIELD_NAME_ENABLE + " = 'Y' where id in (:ids)";

		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("ids", ids);

		try
		{
			namedParameterJdbcTemplate.update(sql, parameters);
		}
		catch (DataAccessException e)
		{
			throw new LittleCatException(ErrorCode.DataAccessException.getCode(), ErrorCode.DataAccessException.getMsg(), e);
		}
	}

	public static <T_MO> T_MO getById(String tableName, String id, JdbcTemplate jdbcTemplate, RowMapper<T_MO> rowMapper) throws LittleCatException
	{
		String sql = "select * from " + tableName + " where id = ?";

		try
		{
			return jdbcTemplate.queryForObject(sql, new Object[] { id }, rowMapper);
		}
		catch (DataAccessException e)
		{
			throw new LittleCatException(ErrorCode.DataAccessException.getCode(), ErrorCode.DataAccessException.getMsg(), e);
		}
	}

	public static <T_MO> int getList(String tableName, QueryParam queryParam, List<T_MO> mos, JdbcTemplate jdbcTemplate, RowMapper<T_MO> rowMapper) throws LittleCatException
	{
		if (queryParam == null)
		{
			throw new LittleCatException(ErrorCode.QueryParamIsNull.getCode(), ErrorCode.QueryParamIsNull.getMsg().replace("{INFO_NAME}", tableName));
		}
		
		if(mos == null)
		{
			throw new LittleCatException("the param mos for filling the data can not be null.");
		}

		String sql = "select * from " + tableName + queryParam.getQueryDataConditionString();

		try
		{
			mos.addAll(jdbcTemplate.query(sql, rowMapper));
		}
		catch (DataAccessException e)
		{
			throw new LittleCatException(ErrorCode.DataAccessException.getCode(), ErrorCode.DataAccessException.getMsg(), e);
		}

		return DaoUtil.getTotalNum(tableName, queryParam, jdbcTemplate);
	}

	public static <T_MO> T_MO getObject(String tableName, QueryParam queryParam, JdbcTemplate jdbcTemplate, RowMapper<T_MO> rowMapper) throws LittleCatException
	{
		if (queryParam == null)
		{
			throw new LittleCatException(ErrorCode.QueryParamIsNull.getCode(), ErrorCode.QueryParamIsNull.getMsg().replace("{INFO_NAME}", tableName));
		}

		List<T_MO> mos = new ArrayList<T_MO>();
		getList(tableName, queryParam, mos, jdbcTemplate, rowMapper);

		if (CollectionUtil.isEmpty(mos))
		{
			return null;
		}

		return mos.get(0);
	}

	public static int getTotalNum(String tableName, QueryParam queryParam, JdbcTemplate jdbcTemplate) throws LittleCatException
	{
		String sql = "select count(*) totalNum	from " + tableName;

		if (queryParam != null)
		{
			sql += queryParam.getQueryCountConditionString();
		}

		try
		{
			return jdbcTemplate.queryForObject(sql, new DaoUtil.TotalNumMapper());
		}
		catch (DataAccessException e)
		{
			throw new LittleCatException(ErrorCode.DataAccessException.getCode(), ErrorCode.DataAccessException.getMsg(), e);
		}
	}
}
