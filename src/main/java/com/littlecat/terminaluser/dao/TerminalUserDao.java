package com.littlecat.terminaluser.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.query.QueryParam;
import com.littlecat.cbb.utils.DateTimeUtil;
import com.littlecat.cbb.utils.StringUtil;
import com.littlecat.cbb.utils.UUIDUtil;
import com.littlecat.common.consts.BooleanTag;
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
	private final String MODEL_NAME = "TerminalUserMO";
	
	public TerminalUserMO getById(String id) throws LittleCatException
	{
		return DaoUtil.getById(TABLE_NAME, id, jdbcTemplate, new TerminalUserMO.MOMapper());
	}

	public String add(TerminalUserMO mo) throws LittleCatException
	{
		if(mo == null)
		{
			throw new LittleCatException(ErrorCode.GiveNullObjectToCreate.getCode(),ErrorCode.GiveNullObjectToCreate.getMsg().replace("{INFO_NAME}",MODEL_NAME));
		}
		
		if(StringUtil.isEmpty(mo.getId()))
		{
			mo.setId(UUIDUtil.createUUID());
		}
		
		mo.setCreateTime(String.valueOf(DateTimeUtil.getCurrentTime()));
		
		String sql = "insert into " + TABLE_NAME + "(id,wxCode,createTime) values(?,?,?)";
		
		try
		{
			int ret = jdbcTemplate.update(sql, new Object[] { mo.getId(),  mo.getWxCode(), mo.getCreateTime() });

			if (ret != 1)
			{
				throw new LittleCatException(ErrorCode.InsertObjectToDBError.getCode(), ErrorCode.InsertObjectToDBError.getMsg().replace("{INFO_NAME}", MODEL_NAME));
			}
		}
		catch(DataAccessException e)
		{
			throw new LittleCatException(ErrorCode.DataAccessException.getCode(),ErrorCode.DataAccessException.getMsg(),e);
		}

		return mo.getId();
	}
	
	public boolean setTuanZhangYes(String id) throws LittleCatException
	{
		if(StringUtil.isEmpty(id))
		{
			throw new LittleCatException(ErrorCode.UpdateObjectWithEmptyId.getCode(),ErrorCode.UpdateObjectWithEmptyId.getMsg().replace("{INFO_NAME}",TABLE_NAME));
		}
		
		
		String sql = "update " + TABLE_NAME + " set isTuanZhang = ? where id = ?";
		
		try
		{
			int ret = jdbcTemplate.update(sql,new Object[] { BooleanTag.Y.name(),id });
			
			if (ret != 1)
			{
				throw new LittleCatException(ErrorCode.UpdateObjectToDBError.getCode(),ErrorCode.UpdateObjectToDBError.getMsg().replace("{INFO_NAME}",MODEL_NAME));
			}
		}
		catch (DataAccessException e)
		{
			throw new LittleCatException(ErrorCode.DataAccessException.getCode(),ErrorCode.DataAccessException.getMsg(),e);
		}
		
		return true;
	}
	
	public boolean setTuanZhangNo(String id) throws LittleCatException
	{
		if(StringUtil.isEmpty(id))
		{
			throw new LittleCatException(ErrorCode.UpdateObjectWithEmptyId.getCode(),ErrorCode.UpdateObjectWithEmptyId.getMsg().replace("{INFO_NAME}",TABLE_NAME));
		}
		
		
		String sql = "update " + TABLE_NAME + " set isTuanZhang = ? where id = ?";
		
		try
		{
			int ret = jdbcTemplate.update(sql,new Object[] { BooleanTag.N.name(),id });
			
			if (ret != 1)
			{
				throw new LittleCatException(ErrorCode.UpdateObjectToDBError.getCode(),ErrorCode.UpdateObjectToDBError.getMsg().replace("{INFO_NAME}",MODEL_NAME));
			}
		}
		catch (DataAccessException e)
		{
			throw new LittleCatException(ErrorCode.DataAccessException.getCode(),ErrorCode.DataAccessException.getMsg(),e);
		}
		
		return true;
	}
	
	public boolean setMaiShouYes(String id) throws LittleCatException
	{
		if(StringUtil.isEmpty(id))
		{
			throw new LittleCatException(ErrorCode.UpdateObjectWithEmptyId.getCode(),ErrorCode.UpdateObjectWithEmptyId.getMsg().replace("{INFO_NAME}",TABLE_NAME));
		}
		
		
		String sql = "update " + TABLE_NAME + " set isMaiShou = ? where id = ?";
		
		try
		{
			int ret = jdbcTemplate.update(sql,new Object[] { BooleanTag.Y.name(),id });
			
			if (ret != 1)
			{
				throw new LittleCatException(ErrorCode.UpdateObjectToDBError.getCode(),ErrorCode.UpdateObjectToDBError.getMsg().replace("{INFO_NAME}",MODEL_NAME));
			}
		}
		catch (DataAccessException e)
		{
			throw new LittleCatException(ErrorCode.DataAccessException.getCode(),ErrorCode.DataAccessException.getMsg(),e);
		}
		
		return true;
	}
	
	public boolean setMaiShouNo(String id) throws LittleCatException
	{
		if(StringUtil.isEmpty(id))
		{
			throw new LittleCatException(ErrorCode.UpdateObjectWithEmptyId.getCode(),ErrorCode.UpdateObjectWithEmptyId.getMsg().replace("{INFO_NAME}",TABLE_NAME));
		}
		
		String sql = "update " + TABLE_NAME + " set isMaiShou = ? where id = ?";
		
		try
		{
			int ret = jdbcTemplate.update(sql,new Object[] { BooleanTag.N.name(),id });
			
			if (ret != 1)
			{
				throw new LittleCatException(ErrorCode.UpdateObjectToDBError.getCode(),ErrorCode.UpdateObjectToDBError.getMsg().replace("{INFO_NAME}",MODEL_NAME));
			}
		}
		catch (DataAccessException e)
		{
			throw new LittleCatException(ErrorCode.DataAccessException.getCode(),ErrorCode.DataAccessException.getMsg(),e);
		}
		
		return true;
	}
	
	public boolean enable(String id) throws LittleCatException
	{
		return DaoUtil.enable(TABLE_NAME, id, jdbcTemplate);
	}

	public boolean enable(List<String> ids) throws LittleCatException
	{
		return DaoUtil.enable(TABLE_NAME, ids, jdbcTemplate);
	}

	public boolean disable(String id) throws LittleCatException
	{
		return DaoUtil.disable(TABLE_NAME, id, jdbcTemplate);
	}

	public boolean disable(List<String> ids) throws LittleCatException
	{
		return DaoUtil.disable(TABLE_NAME, ids, jdbcTemplate);
	}

	public int getList(QueryParam queryParam,List<TerminalUserMO> mos) throws LittleCatException
	{
		return DaoUtil.getList(TABLE_NAME, queryParam, mos, jdbcTemplate, new TerminalUserMO.MOMapper());
	}
}
