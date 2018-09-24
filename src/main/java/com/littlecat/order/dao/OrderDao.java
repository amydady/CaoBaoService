package com.littlecat.order.dao;

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
import com.littlecat.order.model.OrderMO;

@Component
public class OrderDao
{	
	@Autowired
    protected JdbcTemplate jdbcTemplate;
	
	private final String TABLE_NAME = TableName.Order.getName();
	private final String MODEL_NAME = "OrderMO";
	
	public String add(OrderMO mo) throws LittleCatException
	{
		if(mo == null)
		{
			throw new LittleCatException(ErrorCode.GiveNullObjectToCreate.getCode(),ErrorCode.GiveNullObjectToCreate.getMsg().replace("{INFO_NAME}",MODEL_NAME));
		}
		
		if(StringUtil.isEmpty(mo.getId()))
		{
			mo.setId(UUIDUtil.createUUID());
		}
		
		if(StringUtil.isEmpty(mo.getCreateTime()))
		{
			long now = DateTimeUtil.getCurrentTime();
			mo.setCreateTime(String.valueOf(now));
			mo.setCreateYear(DateTimeUtil.getYear(now));
			mo.setCreateMonth(DateTimeUtil.getMonth(now));
		}
		
		
		String sql = "insert into " + TABLE_NAME
				+ "(id,terminalUserId,createTime,createYear,createMonth,fee,state,provinceId,cityId,areaId,detailInfo) values(?,?,?,?,?,?,?,?,?,?,?)";
		
		try
		{
			int ret = jdbcTemplate.update(sql, new Object[] { mo.getId(), mo.getTerminalUserId(),mo.getCreateTime(),mo.getCreateYear(),mo.getCreateMonth(),mo.getFee(),mo.getState().name(),mo.getDeliveryAddress().getProvinceId(),mo.getDeliveryAddress().getCityId(),mo.getDeliveryAddress().getAreaId(),mo.getDeliveryAddress().getDetailInfo() });

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
	
	public boolean modify(OrderMO mo) throws LittleCatException
	{
		if(mo == null)
		{
			throw new LittleCatException(ErrorCode.GiveNullObjectToModify.getCode(),ErrorCode.GiveNullObjectToModify.getMsg().replace("{INFO_NAME}",MODEL_NAME));
		}
		
		String sql = "update " + TABLE_NAME + " set state = ? where id = ?";
		
		try
		{
			int ret = jdbcTemplate.update(sql,new Object[] { mo.getState().name(), mo.getId() });
			
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


	public OrderMO getById(String id) throws LittleCatException
	{
		return DaoUtil.getById(TABLE_NAME, id, jdbcTemplate, new OrderMO.MOMapper());
	}


	public int getList(QueryParam queryParam,List<OrderMO> mos) throws LittleCatException
	{
		return DaoUtil.getList(TABLE_NAME, queryParam, mos, jdbcTemplate, new OrderMO.MOMapper());
	}
}
