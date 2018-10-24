package com.littlecat.seckill.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.query.QueryParam;
import com.littlecat.cbb.utils.StringUtil;
import com.littlecat.cbb.utils.UUIDUtil;
import com.littlecat.common.consts.ErrorCode;
import com.littlecat.common.consts.TableName;
import com.littlecat.common.utils.DaoUtil;
import com.littlecat.seckill.model.SecKillPlanMO;

@Component
public class SecKillPlanDao
{
	@Autowired
	protected JdbcTemplate jdbcTemplate;

	private final String TABLE_NAME = TableName.SecKillPlan.getName();
	private final String TABLE_NAME_GOODS = TableName.Goods.getName();

	private static final String MODEL_NAME = SecKillPlanMO.class.getSimpleName();

	public String add(SecKillPlanMO mo) throws LittleCatException
	{
		if (StringUtil.isEmpty(mo.getId()))
		{
			mo.setId(UUIDUtil.createUUID());
		}

		String sql = "insert into " + TABLE_NAME + "(id,goodsId,startTime,endTime,price,limitBuyNum,createOperatorId,deliveryAreaId,deliveryFeeRuleId) values(?,?,?,?,?,?,?,?,?)";

		try
		{
			int ret = jdbcTemplate.update(sql, new Object[] { mo.getId(), mo.getGoodsId(), mo.getStartTime(), mo.getEndTime(), mo.getPrice(), mo.getLimitBuyNum(), mo.getCreateOperatorId(), mo.getDeliveryAreaId(), mo.getDeliveryFeeRuleId() });

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
		String sql = "update " + TABLE_NAME + " set startTime = ?,endTime = ?,price = ?,currentInventory = ?,limitBuyNum = ?,deliveryAreaId = ?,deliveryFeeRuleId = ? where id = ?";

		try
		{
			int ret = jdbcTemplate.update(sql, new Object[] { mo.getStartTime(), mo.getEndTime(), mo.getPrice(), mo.getCurrentInventory(), mo.getLimitBuyNum(), mo.getDeliveryAreaId(), mo.getDeliveryFeeRuleId(), mo.getId() });

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

	public SecKillPlanMO getById(String id) throws LittleCatException
	{
		return DaoUtil.getById(TABLE_NAME, id, jdbcTemplate, new SecKillPlanMO.MOMapper());
	}

	public int getList(QueryParam queryParam, List<SecKillPlanMO> mos) throws LittleCatException
	{
		return DaoUtil.getList(TABLE_NAME, queryParam, mos, jdbcTemplate, new SecKillPlanMO.MOMapper());
	}

	/**
	 * 秒杀计划列表，用于微信小程序（展示秒杀商品列表）
	 * 
	 * @return
	 */
	public List<SecKillPlanMO> getList4WxApp()
	{
		List<SecKillPlanMO> mos = new ArrayList<SecKillPlanMO>();

		String sql = new StringBuilder()
				.append("select a.id,a.goodsId,a.currentInventory,a.price,b.name goodsName,b.price goodsPrice,b.mainImgData goodsMainImgData")
				.append(" from ").append(TABLE_NAME).append(" a ")
				.append(" inner join ").append(TABLE_NAME_GOODS).append(" b on a.goodsId=b.id")
				.append(" where CURRENT_TIMESTAMP between a.startTime and a.endTime")
				.append(" and a.enable='Y'")
				.append(" and b.enable='Y'")
				.toString();

		try
		{
			mos.addAll(jdbcTemplate.query(sql, new RowMapper<SecKillPlanMO>()
			{

				@Override
				public SecKillPlanMO mapRow(ResultSet rs, int rowNum) throws SQLException
				{
					SecKillPlanMO mo = new SecKillPlanMO();

					mo.setId(rs.getString("id"));
					mo.setGoodsId(rs.getString("goodsId"));
					mo.setPrice(rs.getLong("price"));
					mo.setCurrentInventory(rs.getLong("currentInventory"));
					mo.setGoodsName(rs.getString("goodsName"));
					mo.setGoodsPrice(rs.getLong("goodsPrice"));
					mo.setGoodsMainImgData(rs.getString("goodsMainImgData"));

					return mo;
				}

			}));
		}
		catch (DataAccessException e)
		{
			throw new LittleCatException(ErrorCode.DataAccessException.getCode(), ErrorCode.DataAccessException.getMsg(), e);
		}

		return mos;
	}

	public List<SecKillPlanMO> getList4WebApp()
	{
		List<SecKillPlanMO> mos = new ArrayList<SecKillPlanMO>();

		String sql = new StringBuilder()
				.append("select a.id,a.goodsId,a,startTime,a.endTime,a.limitBuyNum,a.currentInventory,a.price,a.createTime,b.name goodsName,b.price goodsPrice,b.mainImgData goodsMainImgData")
				.append(" from ").append(TABLE_NAME).append(" a ")
				.append(" inner join ").append(TABLE_NAME_GOODS).append(" b on a.goodsId=b.id")
				.append(" where CURRENT_TIMESTAMP <= a.endTime")
				.append(" and b.enable='Y'")
				.append(" order by enable desc,startTime asc ")
				.toString();

		try
		{
			mos.addAll(jdbcTemplate.query(sql, new SecKillPlanMO.MOMapper4WebList()));
		}
		catch (DataAccessException e)
		{
			throw new LittleCatException(ErrorCode.DataAccessException.getCode(), ErrorCode.DataAccessException.getMsg(), e);
		}

		return mos;
	}

}
