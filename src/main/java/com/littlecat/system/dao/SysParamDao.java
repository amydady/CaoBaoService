package com.littlecat.system.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.common.consts.ErrorCode;
import com.littlecat.common.consts.TableName;
import com.littlecat.system.model.SysParamMO;

@Component
public class SysParamDao
{
	private final String TABLE_NAME = TableName.SysParam.getName();
	private final String MODEL_NAME = "SysParamMO";

	@Autowired
	protected JdbcTemplate jdbcTemplate;

	public void modify(SysParamMO mo) throws LittleCatException
	{
		if (mo == null)
		{
			throw new LittleCatException(ErrorCode.RequestObjectIsNull.getCode(), ErrorCode.RequestObjectIsNull.getMsg().replace("{INFO_NAME}", MODEL_NAME));
		}

		String sql = "update " + TABLE_NAME + " set value = ? where name = ?";

		try
		{
			int ret = jdbcTemplate.update(sql, new Object[] { mo.getValue(), mo.getName() });

			if (ret != 1)
			{
				throw new LittleCatException(ErrorCode.UpdateObjectToDBError.getCode(), ErrorCode.UpdateObjectToDBError.getMsg().replace("{INFO_NAME}", MODEL_NAME));
			}
		}
		catch (DataAccessException e)
		{
			throw new LittleCatException(ErrorCode.DataAccessException.getCode(), ErrorCode.DataAccessException.getMsg(), e);
		}
	}

	public SysParamMO getValueByName(String name) throws LittleCatException
	{
		String sql = "select * from " + TABLE_NAME + " where name = ?";

		try
		{
			return jdbcTemplate.queryForObject(sql, new Object[] { name }, new SysParamMO.MOMapper());
		}
		catch (DataAccessException e)
		{
			throw new LittleCatException(ErrorCode.DataAccessException.getCode(), ErrorCode.DataAccessException.getMsg(), e);
		}
	}

	public List<SysParamMO> getList() throws LittleCatException
	{
		String sql = "select * from " + TABLE_NAME;

		try
		{
			return jdbcTemplate.query(sql, new SysParamMO.MOMapper());
		}
		catch (DataAccessException e)
		{
			throw new LittleCatException(ErrorCode.DataAccessException.getCode(), ErrorCode.DataAccessException.getMsg(), e);
		}
	}
}
