package com.littlecat.quanzi.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.query.QueryParam;
import com.littlecat.cbb.utils.CollectionUtil;
import com.littlecat.cbb.utils.StringUtil;
import com.littlecat.cbb.utils.UUIDUtil;
import com.littlecat.common.consts.ErrorCode;
import com.littlecat.common.consts.TableName;
import com.littlecat.common.utils.DaoUtil;
import com.littlecat.quanzi.model.TuanMemberMO;

@Component
public class TuanMemberDao
{
	@Autowired
	protected JdbcTemplate jdbcTemplate;

	private final String TABLE_NAME = TableName.TuanMember.getName();
	private static final String MODEL_NAME = TuanMemberMO.class.getSimpleName();

	public TuanMemberMO getById(String id) throws LittleCatException
	{
		return DaoUtil.getById(TABLE_NAME, id, jdbcTemplate, new TuanMemberMO.MOMapper());
	}

	public String add(TuanMemberMO mo) throws LittleCatException
	{
		if (mo == null)
		{
			throw new LittleCatException(ErrorCode.GiveNullObjectToCreate.getCode(), ErrorCode.GiveNullObjectToCreate.getMsg().replace("{INFO_NAME}", MODEL_NAME));
		}

		if (StringUtil.isEmpty(mo.getId()))
		{
			mo.setId(UUIDUtil.createUUID());
		}

		String sql = "insert into " + TABLE_NAME + "(id,tuanId,terminalUserId) values(?,?,?)";

		try
		{
			int ret = jdbcTemplate.update(sql, new Object[] { mo.getId(), mo.getTuanId(), mo.getTerminalUserId() });

			if (ret != 1)
			{
				throw new LittleCatException(ErrorCode.InsertObjectToDBError.getCode(), ErrorCode.InsertObjectToDBError.getMsg().replace("{INFO_NAME}", MODEL_NAME));
			}
		}
		catch (DataAccessException e)
		{
			throw new LittleCatException(ErrorCode.DataAccessException.getCode(), ErrorCode.DataAccessException.getMsg(), e);
		}

		return mo.getId();
	}

	public void setLastActiveTime(String id) throws LittleCatException
	{
		if (StringUtil.isEmpty(id))
		{
			throw new LittleCatException(ErrorCode.UpdateObjectWithEmptyId.getCode(), ErrorCode.UpdateObjectWithEmptyId.getMsg().replace("{INFO_NAME}", MODEL_NAME));
		}

		String sql = "update " + TABLE_NAME + " set lastActiveTime = CURRENT_TIMESTAMP where id = ?";

		try
		{
			int ret = jdbcTemplate.update(sql, new Object[] { id });

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

	public int getList(QueryParam queryParam, List<TuanMemberMO> mos) throws LittleCatException
	{
		return DaoUtil.getList(TABLE_NAME, queryParam, mos, jdbcTemplate, new TuanMemberMO.MOMapper());
	}

	public boolean isMember(String terminalUserId, String tuanId) throws LittleCatException
	{
		String sql = "select * from " + TABLE_NAME + " where terminalUserId = ? and tuanId = ?";

		try
		{
			List<TuanMemberMO> mos = jdbcTemplate.query(sql, new Object[] { terminalUserId, tuanId }, new TuanMemberMO.MOMapper());

			return CollectionUtil.isNotEmpty(mos);
		}
		catch (DataAccessException e)
		{
			throw new LittleCatException(ErrorCode.DataAccessException.getCode(), ErrorCode.DataAccessException.getMsg(), e);
		}
	}
}
