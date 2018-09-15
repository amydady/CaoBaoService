package com.littlecat.system.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.littlecat.cbb.common.Consts;
import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.query.QueryParam;
import com.littlecat.cbb.utils.CollectionUtil;
import com.littlecat.cbb.utils.StringUtil;
import com.littlecat.cbb.utils.UUIDUtil;
import com.littlecat.common.TotalNumMapper;
import com.littlecat.consts.ErrorCode;
import com.littlecat.consts.TableName;
import com.littlecat.system.model.SysOperatorMO;
import com.littlecat.system.model.SysOperatorMapper;

@Component
public class SysOperatorDao
{
	@Autowired
    protected JdbcTemplate jdbcTemplate;

	private Logger logger = LoggerFactory.getLogger(SysOperatorDao.class);
	
	public SysOperatorMO login(String id,String pwd) throws LittleCatException
	{
		String sql = "select * from " + TableName.SysOperator.getName()
        + " where (wxCode =? or username=? or email=? or mobile=?) and password=password(?)";
		
		List<SysOperatorMO> mos;
		try
		{
			mos = jdbcTemplate.query(sql, new Object[] {id,id,id,id,pwd},new SysOperatorMapper());
			
			if(CollectionUtil.isEmpty(mos))
			{
				logger.warn(ErrorCode.GetInfoFromDBReturnEmpty.getMsg().replaceAll("{INFO_NAME}", "SysOperatorMO") + " id=" + id);
				return null;
			}
			
			return mos.get(0);
		}
		catch(DataAccessException e)
		{
			logger.warn(ErrorCode.DataAccessException.getMsg() + " id=" + id,e);
			return null;
		}
	}
	
	public String addSysOperator(SysOperatorMO mo)
	{
		if(mo == null)
		{
			logger.warn(ErrorCode.GiveNullObjectToCreate.getMsg().replaceAll("{INFO_NAME}","SysOperatorMO"));
			return null;
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
				logger.warn(ErrorCode.InsertObjectToDBError.getMsg().replaceAll("{INFO_NAME}","SysOperatorMO"));
				return null;
			}
		}
		catch(DataAccessException e)
		{
			logger.warn(ErrorCode.DataAccessException.getMsg(),e);
			return null;
		}

		return mo.getId();
	}

	public boolean modifySysOperator(SysOperatorMO mo)
	{
		if(mo == null)
		{
			logger.warn(ErrorCode.GiveNullObjectToModify.getMsg().replaceAll("{INFO_NAME}","SysOperatorMO"));
			return false;
		}
		
		String sql = "update " + TableName.SysOperator.getName() + " set name = ?,wxCode = ?,email = ?,mobile = ? where id = ?";
		
		try
		{
			int ret = jdbcTemplate.update(sql,new Object[] { mo.getName(), mo.getWxCode(), mo.getEmail(), mo.getMobile(), mo.getId() });
			return ret == 1;
		}
		catch (DataAccessException e)
		{
			logger.warn(ErrorCode.DataAccessException.getMsg(),e);
			return false;
		}
	}

	public boolean deleteSysOperator(String id)
	{
		if(StringUtil.isEmpty(id))
		{
			logger.warn(ErrorCode.DeleteObjectWithEmptyId.getMsg().replaceAll("{INFO_NAME}","SysOperatorMO"));
			return false;
		}
		
		String sql = "delete from " + TableName.SysOperator.getName() + " where id = ?";
		
		try
		{
			int ret = jdbcTemplate.update(sql, new Object[] { id });
			return ret <=1;
		}
		catch (DataAccessException e)
		{
			logger.warn(ErrorCode.DataAccessException.getMsg(), e);
			return false;
		}
	}

	/**
	 * 查询系统操作人员列表
	 * 
	 * @param queryParam
	 *            查询参数
	 * @param mos
	 *            返回列表信息（当前这一批，不是全部数据）
	 * @return 总记录数
	 */
	public int getSysOperatorList(QueryParam queryParam, List<SysOperatorMO> mos)
	{
//		String sql = "select count(*) totalNum,* from " + TableName.SysOperator.getName();
		
		
//		mos = jdbcTemplate.query(sql, new Object[] {id,id,id,id,pwd},new SysOperatorMapper());
//		
//		int totalNum = jdbcTemplate.queryForObject("select count(*) " + Consts.COMMON_DB_RESULT_FIELDS_TOTALNUM + " from " +TableName.SysOperator.getName(), new TotalNumMapper());
		
		return 0;
	}
}
