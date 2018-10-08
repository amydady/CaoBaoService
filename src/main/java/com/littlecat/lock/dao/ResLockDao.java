package com.littlecat.lock.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.utils.DateTimeUtil;
import com.littlecat.common.consts.ErrorCode;
import com.littlecat.common.consts.TableName;
import com.littlecat.lock.model.ResLockMO;

@Component
public class ResLockDao
{
	@Autowired
	protected JdbcTemplate jdbcTemplate;

	private final String TABLE_NAME = TableName.ResLock.getName();
	private final String MODEL_NAME = ResLockMO.class.getSimpleName();

	public void add(ResLockMO mo) throws LittleCatException
	{
		String sql = "insert into " + TABLE_NAME + "(type,key,disableTime) values(?,?)";

		try
		{
			int ret = jdbcTemplate.update(sql, new Object[] { mo.getType(), mo.getKey(), mo.getDisableTime() });

			if (ret != 1)
			{
				throw new LittleCatException(ErrorCode.InsertObjectToDBError.getCode(), ErrorCode.InsertObjectToDBError.getMsg().replace("{INFO_NAME}", MODEL_NAME));
			}
		}
		catch (DataAccessException e)
		{
			throw new LittleCatException(ErrorCode.DataAccessException.getCode(), ErrorCode.DataAccessException.getMsg(), e);
		}
	}

	public void add(List<ResLockMO> mos) throws LittleCatException
	{
		String sql = "insert into " + TABLE_NAME + "(type,key,disableTime) values(?,?)";

		List<Object[]> params = new ArrayList<Object[]>();

		for (ResLockMO mo : mos)
		{
			params.add(new Object[] { mo.getType(), mo.getKey(), mo.getDisableTime() });
		}

		try
		{
			jdbcTemplate.batchUpdate(sql, params);
		}
		catch (DataAccessException e)
		{
			throw new LittleCatException(ErrorCode.DataAccessException.getCode(), ErrorCode.DataAccessException.getMsg(), e);
		}
	}

	public void delete(ResLockMO mo) throws LittleCatException
	{
		String sql = "delete from " + TABLE_NAME + " where type = ? and key = ?";

		try
		{
			jdbcTemplate.update(sql, new Object[] { mo.getType(), mo.getKey() });
		}
		catch (DataAccessException e)
		{
			throw new LittleCatException(ErrorCode.DataAccessException.getCode(), ErrorCode.DataAccessException.getMsg(), e);
		}
	}

	public void deleteByDisableTime() throws LittleCatException
	{
		String sql = "delete from " + TABLE_NAME + " where disableTime < ?";

		try
		{
			jdbcTemplate.update(sql, new Object[] { DateTimeUtil.getCurrentTimeForDisplay() });
		}
		catch (DataAccessException e)
		{
			throw new LittleCatException(ErrorCode.DataAccessException.getCode(), ErrorCode.DataAccessException.getMsg(), e);
		}
	}
}
