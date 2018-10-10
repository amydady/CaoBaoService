package com.littlecat.groupbuy.dao;

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
import com.littlecat.groupbuy.model.GroupBuyPlanMO;

@Component
public class GroupBuyPlanDao
{
	@Autowired
	protected JdbcTemplate jdbcTemplate;

	private final String TABLE_NAME = TableName.GroupBuyPlan.getName();
	private static final String MODEL_NAME = GroupBuyPlanMO.class.getSimpleName();

	public String add(GroupBuyPlanMO mo) throws LittleCatException
	{
		if (StringUtil.isEmpty(mo.getId()))
		{
			mo.setId(UUIDUtil.createUUID());
		}

		String sql = "insert into " + TABLE_NAME + "(id,goodsId,startTime,endTime,price,memberNum,limitBuyNum,createOperatorId,deliveryAreaId,deliveryFeeRuleId) values(?,?,?,?,?,?,?,?,?,?)";

		try
		{
			int ret = jdbcTemplate.update(sql, new Object[] { mo.getId(), mo.getGoodsId(), mo.getStartTime(), mo.getEndTime(), mo.getPrice(), mo.getMemberNum(), mo.getLimitBuyNum(), mo.getCreateOperatorId(), mo.getDeliveryAreaId(), mo.getDeliveryFeeRuleId() });

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

	public void modify(GroupBuyPlanMO mo) throws LittleCatException
	{
		String sql = "update " + TABLE_NAME + " set startTime = ?,endTime = ?,price = ?,currentInventory = ?,memberNum = ?,limitBuyNum = ?,deliveryAreaId = ?,deliveryFeeRuleId = ? where id = ?";

		try
		{
			int ret = jdbcTemplate.update(sql, new Object[] { mo.getStartTime(), mo.getEndTime(), mo.getPrice(), mo.getCurrentInventory(), mo.getMemberNum(), mo.getLimitBuyNum(), mo.getDeliveryAreaId(), mo.getDeliveryFeeRuleId(), mo.getId() });

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

	public void delete(List<String> ids) throws LittleCatException
	{
		DaoUtil.delete(TABLE_NAME, ids, jdbcTemplate);
	}

	public GroupBuyPlanMO getById(String id) throws LittleCatException
	{
		return DaoUtil.getById(TABLE_NAME, id, jdbcTemplate, new GroupBuyPlanMO.MOMapper());
	}

	public int getList(QueryParam queryParam, List<GroupBuyPlanMO> mos) throws LittleCatException
	{
		return DaoUtil.getList(TABLE_NAME, queryParam, mos, jdbcTemplate, new GroupBuyPlanMO.MOMapper());
	}
}
