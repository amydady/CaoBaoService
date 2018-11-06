package com.littlecat.system.dao;

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
import com.littlecat.system.model.SysOperatorMO;

@Component
public class SysOperatorDao
{
	private final String TABLE_NAME = TableName.SysOperator.getName();
	private final String MODEL_NAME = SysOperatorMO.class.getSimpleName();

	@Autowired
	protected JdbcTemplate jdbcTemplate;

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

	public SysOperatorMO login(String identity, String pwd) throws LittleCatException
	{
		String sql = "select * from " + TABLE_NAME + " where (wxCode =? or username=? or email=? or mobile=?) and password=password(?)";

		try
		{
			List<SysOperatorMO> mos = jdbcTemplate.query(sql, new Object[] { identity, identity, identity, identity, pwd }, new SysOperatorMO.MOMapper());

			if (CollectionUtil.isEmpty(mos))
			{
				throw new LittleCatException(ErrorCode.GetInfoFromDBReturnEmpty.getCode(), ErrorCode.GetInfoFromDBReturnEmpty.getMsg().replace("{INFO_NAME}", MODEL_NAME) + " identity=" + identity);
			}

			return mos.get(0);
		}
		catch (DataAccessException e)
		{
			throw new LittleCatException(ErrorCode.DataAccessException.getCode(), ErrorCode.DataAccessException.getMsg(), e);
		}
	}
	
	public void setPassword(String id,String pwd) throws LittleCatException
	{
		String sql = "update " + TABLE_NAME + " set password = password(?) where id = ?";

		try
		{
			int ret = jdbcTemplate.update(sql, new Object[] { pwd, id });

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

	public void changePassword(String id, String oldPwd, String pwd) throws LittleCatException
	{
		String sql = "select count(1) from " + TABLE_NAME + " where password = password(?) and id = ?";

		int count = jdbcTemplate.queryForObject(sql, new Object[] { oldPwd, id }, Integer.class);

		if (count < 1)
		{
			throw new LittleCatException(ErrorCode.OldPwdIsError.getCode(), ErrorCode.OldPwdIsError.getMsg());
		}

		sql = "update " + TABLE_NAME + " set password = password(?) where id = ?";

		try
		{
			int ret = jdbcTemplate.update(sql, new Object[] { pwd, id });

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

	public String add(SysOperatorMO mo) throws LittleCatException
	{
		if (StringUtil.isEmpty(mo.getId()))
		{
			mo.setId(UUIDUtil.createUUID());
		}

		String sql = "insert into " + TABLE_NAME + "(id,username,password,name,wxCode,email,mobile) values(?,?,password(?),?,?,?,?)";

		try
		{
			int ret = jdbcTemplate.update(sql, new Object[] { mo.getId(), mo.getUsername(), mo.getPassword(), mo.getName(), mo.getWxCode(), mo.getEmail(), mo.getMobile() });

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

	public void modify(SysOperatorMO mo) throws LittleCatException
	{
		String sql = "update " + TABLE_NAME + " set name = ?,wxCode = ?,email = ?,mobile = ? where id = ?";

		try
		{
			int ret = jdbcTemplate.update(sql, new Object[] { mo.getName(), mo.getWxCode(), mo.getEmail(), mo.getMobile(), mo.getId() });

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

	public SysOperatorMO getById(String id) throws LittleCatException
	{
		return DaoUtil.getById(TABLE_NAME, id, jdbcTemplate, new SysOperatorMO.MOMapper());
	}

	public int getList(QueryParam queryParam, List<SysOperatorMO> mos) throws LittleCatException
	{
		return DaoUtil.getList(TABLE_NAME, queryParam, mos, jdbcTemplate, new SysOperatorMO.MOMapper());
	}
}
