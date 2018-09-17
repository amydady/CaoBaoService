package com.littlecat.supplier.dao;

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
import com.littlecat.common.consts.ErrorCode;
import com.littlecat.common.consts.TableName;
import com.littlecat.common.utils.DaoUtil;
import com.littlecat.supplier.model.SupplierMO;

@Component
public class SupplierDao
{	
	@Autowired
    protected JdbcTemplate jdbcTemplate;
	
	private final String TABLE_NAME = TableName.Supplier.getName();
	private final String MODEL_NAME = "SupplierMO";
	
	public SupplierMO getById(String id) throws LittleCatException
	{
		return DaoUtil.getById(TABLE_NAME, id, jdbcTemplate, new SupplierMO.MOMapper());
	}

	public boolean delete(String id) throws LittleCatException
	{
		return DaoUtil.delete(TABLE_NAME, id, jdbcTemplate);
	}
	
	public boolean delete(List<String> ids) throws LittleCatException
	{
		return DaoUtil.delete(TABLE_NAME, ids, jdbcTemplate);
	}

	public String add(SupplierMO mo) throws LittleCatException
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
		
		String sql = "insert into " + TABLE_NAME
				+ "(id,name,remark,createTime) values(?,?,?,?)";
		
		try
		{
			int ret = jdbcTemplate.update(sql, new Object[] { mo.getId(), mo.getName(), mo.getRemark(), mo.getCreateTime() });

			if (ret != 1)
			{
				throw new LittleCatException(ErrorCode.InsertObjectToDBError.getCode(),
						ErrorCode.InsertObjectToDBError.getMsg().replace("{INFO_NAME}", MODEL_NAME));
			}
		}
		catch(DataAccessException e)
		{
			throw new LittleCatException(ErrorCode.DataAccessException.getCode(),ErrorCode.DataAccessException.getMsg(),e);
		}

		return mo.getId();
	}
	
	public boolean modify(SupplierMO mo) throws LittleCatException
	{
		if(mo == null)
		{
			throw new LittleCatException(ErrorCode.GiveNullObjectToModify.getCode(),ErrorCode.GiveNullObjectToModify.getMsg().replace("{INFO_NAME}",MODEL_NAME));
		}
		
		String sql = "update " + TABLE_NAME + " set name = ?,remak = ? where id = ?";
		
		try
		{
			int ret = jdbcTemplate.update(sql,new Object[] { mo.getName(), mo.getRemark(), mo.getId() });
			
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


	public int getList(QueryParam queryParam,List<SupplierMO> mos) throws LittleCatException
	{
		return DaoUtil.getList(TABLE_NAME, queryParam, mos, jdbcTemplate, new SupplierMO.MOMapper());
	}
}
