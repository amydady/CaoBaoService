package com.littlecat.terminaluser.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.query.QueryParam;
import com.littlecat.common.consts.ErrorCode;
import com.littlecat.common.consts.TableName;
import com.littlecat.common.utils.DaoUtil;
import com.littlecat.terminaluser.model.TerminalUserMO;

@Component
public class TerminalUserDao
{
	@Autowired
	protected JdbcTemplate jdbcTemplate;

	private final String TABLE_NAME = TableName.TerminalUser.getName();

	public TerminalUserMO getById(String id) throws LittleCatException
	{
		return DaoUtil.getById(TABLE_NAME, id, jdbcTemplate, new TerminalUserMO.MOMapper());
	}

	public void delete(String id) throws LittleCatException
	{
		DaoUtil.delete(TABLE_NAME, id, jdbcTemplate);
	}

	public void delete(List<String> ids) throws LittleCatException
	{
		DaoUtil.delete(TABLE_NAME, ids, jdbcTemplate);
	}

	public void enable(String id) throws LittleCatException
	{
		DaoUtil.enable(TABLE_NAME, id, jdbcTemplate);
	}

	public void enable(List<String> ids) throws LittleCatException
	{
		DaoUtil.enable(TABLE_NAME, ids, jdbcTemplate);
	}

	public void disable(String id) throws LittleCatException
	{
		DaoUtil.disable(TABLE_NAME, id, jdbcTemplate);
	}

	public void disable(List<String> ids) throws LittleCatException
	{
		DaoUtil.disable(TABLE_NAME, ids, jdbcTemplate);
	}

	public String add(TerminalUserMO mo) throws LittleCatException
	{
		try
		{
			String sql = "select count(1) from  " + TABLE_NAME + " where id = ?";

			if (jdbcTemplate.queryForObject(sql, new Object[] { mo.getId() }, Integer.class) > 0)
			{// 已存在
				sql = "update " + TABLE_NAME + " set name=?,image=? where id = ?";
				jdbcTemplate.update(sql, new Object[] { mo.getName(), mo.getImage(), mo.getId() });
			}
			else
			{
				sql = "insert into " + TABLE_NAME + "(id,name,image) values(?,?,?)";

				jdbcTemplate.update(sql, new Object[] { mo.getId(), mo.getName(), mo.getImage() });
			}
		}
		catch (DataAccessException e)
		{
			throw new LittleCatException(ErrorCode.DataAccessException.getCode(), ErrorCode.DataAccessException.getMsg(), e);
		}

		return mo.getId();
	}

	public int getList(QueryParam queryParam, List<TerminalUserMO> mos) throws LittleCatException
	{
		return DaoUtil.getList(TABLE_NAME, queryParam, mos, jdbcTemplate, new TerminalUserMO.MOMapper());
	}
}
