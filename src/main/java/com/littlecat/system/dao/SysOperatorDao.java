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
import com.littlecat.common.DaoUtil;
import com.littlecat.consts.ErrorCode;
import com.littlecat.consts.TableName;
import com.littlecat.system.model.SysOperatorMO;

@Component
public class SysOperatorDao
{
	@Autowired
    protected JdbcTemplate jdbcTemplate;
	
	public SysOperatorMO login(String id,String pwd) throws LittleCatException
	{
		String sql = "select * from " + TableName.SysOperator.getName()
        + " where (wxCode =? or username=? or email=? or mobile=?) and password=password(?)";
		
		List<SysOperatorMO> mos;
		try
		{
			mos = jdbcTemplate.query(sql, new Object[] {id,id,id,id,pwd},new SysOperatorMO.SysOperatorMapper());
			
			if(CollectionUtil.isEmpty(mos))
			{
				throw new LittleCatException(ErrorCode.GetInfoFromDBReturnEmpty.getCode(),ErrorCode.GetInfoFromDBReturnEmpty.getMsg().replace("{INFO_NAME}", "SysOperatorMO") + " id=" + id);
			}
			
			return mos.get(0);
		}
		catch(DataAccessException e)
		{
			throw new LittleCatException(ErrorCode.DataAccessException.getCode(),ErrorCode.DataAccessException.getMsg(),e);
		}
	}
	
	public boolean changePassword(String id,String pwd) throws LittleCatException
	{
		if(StringUtil.isEmpty(id))
		{
			throw new LittleCatException(ErrorCode.UpdateObjectWithEmptyId.getCode(),ErrorCode.UpdateObjectWithEmptyId.getMsg().replace("{INFO_NAME}","SysOperatorMO"));
		}
		
		String sql = "update " + TableName.SysOperator.getName() + " set password = password(?) where id = ?";
		
		try
		{
			int ret = jdbcTemplate.update(sql,new Object[] { pwd,id });
			
			if (ret != 1)
			{
				throw new LittleCatException(ErrorCode.UpdateObjectToDBError.getCode(),ErrorCode.UpdateObjectToDBError.getMsg().replace("{INFO_NAME}","SysOperatorMO"));
			}
		}
		catch (DataAccessException e)
		{
			throw new LittleCatException(ErrorCode.DataAccessException.getCode(),ErrorCode.DataAccessException.getMsg(),e);
		}
		
		return true;
	}
	
	public String add(SysOperatorMO mo) throws LittleCatException
	{
		if(mo == null)
		{
			throw new LittleCatException(ErrorCode.GiveNullObjectToCreate.getCode(),ErrorCode.GiveNullObjectToCreate.getMsg().replace("{INFO_NAME}","SysOperatorMO"));
		}
		
		if(StringUtil.isEmpty(mo.getId()))
		{
			mo.setId(UUIDUtil.createUUID());
		}
		
		String sql = "insert into " + TableName.SysOperator.getName()
		        + "(id,username,password,name,wxCode,email,mobile) values(?,?,password(?),?,?,?,?)";
		
		try
		{
			int ret = jdbcTemplate.update(sql, new Object[] { mo.getId(), mo.getUsername(), mo.getPassword(), mo.getName(),
					mo.getWxCode(), mo.getEmail(), mo.getMobile() });

			if (ret != 1)
			{
				throw new LittleCatException(ErrorCode.InsertObjectToDBError.getCode(),ErrorCode.InsertObjectToDBError.getMsg().replace("{INFO_NAME}","SysOperatorMO"));
			}
		}
		catch(DataAccessException e)
		{
			throw new LittleCatException(ErrorCode.DataAccessException.getCode(),ErrorCode.DataAccessException.getMsg(),e);
		}

		return mo.getId();
	}

	public boolean modify(SysOperatorMO mo) throws LittleCatException
	{
		if(mo == null)
		{
			throw new LittleCatException(ErrorCode.GiveNullObjectToModify.getCode(),ErrorCode.GiveNullObjectToModify.getMsg().replace("{INFO_NAME}","SysOperatorMO"));
		}
		
		String sql = "update " + TableName.SysOperator.getName() + " set name = ?,wxCode = ?,email = ?,mobile = ? where id = ?";
		
		try
		{
			int ret = jdbcTemplate.update(sql,new Object[] { mo.getName(), mo.getWxCode(), mo.getEmail(), mo.getMobile(), mo.getId() });
			
			if (ret != 1)
			{
				throw new LittleCatException(ErrorCode.UpdateObjectToDBError.getCode(),ErrorCode.UpdateObjectToDBError.getMsg().replace("{INFO_NAME}","SysOperatorMO"));
			}
		}
		catch (DataAccessException e)
		{
			throw new LittleCatException(ErrorCode.DataAccessException.getCode(),ErrorCode.DataAccessException.getMsg(),e);
		}
		
		return true;
	}

	public boolean delete(String id) throws LittleCatException
	{
		return DaoUtil.delete(TableName.SysOperator.getName(), id, jdbcTemplate);
	}
	
	public boolean delete(List<String> ids) throws LittleCatException
	{
		return DaoUtil.delete(TableName.SysOperator.getName(), ids, jdbcTemplate);
	}
	
	public SysOperatorMO getById(String id) throws LittleCatException
	{
		return DaoUtil.getById(TableName.SysOperator.getName(), id, jdbcTemplate, new SysOperatorMO.SysOperatorMapper());
	}

	public int getList(QueryParam queryParam, List<SysOperatorMO> mos) throws LittleCatException
	{
		return DaoUtil.getList(TableName.SysOperator.getName(), queryParam, mos, jdbcTemplate, new SysOperatorMO.SysOperatorMapper());
	}
}
