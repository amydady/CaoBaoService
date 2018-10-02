package com.littlecat.seckill.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.query.QueryParam;
import com.littlecat.cbb.utils.StringUtil;
import com.littlecat.cbb.utils.UUIDUtil;
import com.littlecat.common.consts.ErrorCode;
import com.littlecat.common.consts.TableName;
import com.littlecat.common.utils.DaoUtil;
import com.littlecat.order.model.OrderMO;
import com.littlecat.seckill.model.SecKillPlanMO;

@Component
public class SecKillPlanDao
{
	@Autowired
	protected JdbcTemplate jdbcTemplate;

	private final String TABLE_NAME = TableName.SecKillPlan.getName();
	private static final String MODEL_NAME = SecKillPlanMO.class.getSimpleName();

	public String add(SecKillPlanMO mo) throws LittleCatException
	{
		if (mo == null)
		{
			throw new LittleCatException(ErrorCode.GiveNullObjectToCreate.getCode(), ErrorCode.GiveNullObjectToCreate.getMsg().replace("{INFO_NAME}", MODEL_NAME));
		}

		if (StringUtil.isEmpty(mo.getId()))
		{
			mo.setId(UUIDUtil.createUUID());
		}

		String sql = "insert into " + TABLE_NAME + "(id,goodsId,startTime,endTime,price,initInventory,limitBuyNum,createOperatorId,deliveryAreaId,deliveryFeeRuleId) values(?,?,?,?,?,?,?,?,?,?)";

		try
		{
			int ret = jdbcTemplate.update(sql, new Object[] { mo.getId(), mo.getGoodsId(), mo.getStartTime(), mo.getEndTime(), mo.getPrice(), mo.getInitInventory(), mo.getLimitBuyNum(), mo.getCreateOperatorId(), mo.getDeliveryAreaId(), mo.getDeliveryFeeRuleId() });

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

	public void modify(SecKillPlanMO mo) throws LittleCatException
	{
		if (mo == null)
		{
			throw new LittleCatException(ErrorCode.GiveNullObjectToModify.getCode(), ErrorCode.GiveNullObjectToModify.getMsg().replace("{INFO_NAME}", MODEL_NAME));
		}

		String sql = "update " + TABLE_NAME + " set goodsId = ?,startTime = ?,endTime = ?,price = ?,initInventory = ?,limitBuyNum = ?,createOperatorId = ?,deliveryAreaId = ?,deliveryFeeRuleId = ? where id = ?";

		try
		{
			int ret = jdbcTemplate.update(sql, new Object[] { mo.getGoodsId(), mo.getStartTime(), mo.getEndTime(), mo.getPrice(), mo.getInitInventory(), mo.getLimitBuyNum(), mo.getCreateOperatorId(), mo.getDeliveryAreaId(), mo.getDeliveryFeeRuleId(), mo.getId() });

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

	public void delete(String id) throws LittleCatException
	{
		DaoUtil.delete(TABLE_NAME, id, jdbcTemplate);
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

	public OrderMO getById(String id) throws LittleCatException
	{
		return DaoUtil.getById(TABLE_NAME, id, jdbcTemplate, new OrderMO.MOMapper());
	}

	public int getList(QueryParam queryParam, List<SecKillPlanMO> mos) throws LittleCatException
	{
		return DaoUtil.getList(TABLE_NAME, queryParam, mos, jdbcTemplate, new SecKillPlanMO.MOMapper());
	}
}
