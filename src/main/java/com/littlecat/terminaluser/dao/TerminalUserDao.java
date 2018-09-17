package com.littlecat.terminaluser.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.query.ConditionItem;
import com.littlecat.cbb.query.ConditionOperatorType;
import com.littlecat.cbb.query.QueryCondition;
import com.littlecat.cbb.query.QueryParam;
import com.littlecat.cbb.utils.CollectionUtil;
import com.littlecat.cbb.utils.DateTimeUtil;
import com.littlecat.cbb.utils.StringUtil;
import com.littlecat.cbb.utils.UUIDUtil;
import com.littlecat.common.DaoUtil;
import com.littlecat.consts.ErrorCode;
import com.littlecat.consts.TableName;
import com.littlecat.terminaluser.model.TerminalUserMO;

@Component
public class TerminalUserDao
{	
	@Autowired
    protected JdbcTemplate jdbcTemplate;
	
	private final String FIELDNAME_WXCODE = "wxCode";
	private final String TABLE_NAME = TableName.TerminalUser.getName();
	private final String MODEL_NAME = "TerminalUserMO";
	
	public TerminalUserMO getByWXCode(String wxCode) throws LittleCatException
	{
		List<TerminalUserMO> mos = new ArrayList<TerminalUserMO>();
		
		QueryParam queryParam = new QueryParam();
		QueryCondition condition = new QueryCondition();
		condition.getCondItems().add(new ConditionItem(FIELDNAME_WXCODE, wxCode, ConditionOperatorType.equal));
		queryParam.setCondition(condition );
		
		getList(queryParam,mos);
		
		if(CollectionUtil.isEmpty(mos))
		{
			throw new LittleCatException(ErrorCode.GetInfoFromDBReturnEmpty.getCode(),ErrorCode.GetInfoFromDBReturnEmpty.getMsg().replace("{INFO_NAME}", MODEL_NAME));
		}
		
		return mos.get(0);
	}
	
	public TerminalUserMO getById(String id) throws LittleCatException
	{
		return DaoUtil.getById(TABLE_NAME, id, jdbcTemplate, new TerminalUserMO.TerminalUserMapper());
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
		
		String sql = "insert into " + TABLE_NAME
				+ "(id,name,wxCode,mobile,createTime) values(?,?,?,?,?)";
		
		try
		{
			int ret = jdbcTemplate.update(sql, new Object[] { mo.getId(), mo.getName(), mo.getWxCode(), mo.getMobile(),
					mo.getCreateTime() });

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
	
	public boolean modify(TerminalUserMO mo) throws LittleCatException
	{
		if(mo == null)
		{
			throw new LittleCatException(ErrorCode.GiveNullObjectToModify.getCode(),ErrorCode.GiveNullObjectToModify.getMsg().replace("{INFO_NAME}",MODEL_NAME));
		}
		
		String sql = "update " + TABLE_NAME + " set name = ?,mobile = ? ,isTuanZhang = ? ,isMaiShou = ? where id = ?";
		
		try
		{
			int ret = jdbcTemplate.update(sql,new Object[] { mo.getName(), mo.getMobile(), mo.getIsTuanZhang(), mo.getIsMaiShou(), mo.getId() });
			
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


	public int getList(QueryParam queryParam,List<TerminalUserMO> mos) throws LittleCatException
	{
		return DaoUtil.getList(TABLE_NAME, queryParam, mos, jdbcTemplate, new TerminalUserMO.TerminalUserMapper());
	}
}
