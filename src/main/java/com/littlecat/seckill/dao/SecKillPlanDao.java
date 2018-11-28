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
			jdbcTemplate.update(sql, new Object[] { mo.getId(), mo.getGoodsId(), mo.getStartTime(), mo.getEndTime(), mo.getPrice(), mo.getLimitBuyNum(), mo.getCreateOperatorId(), mo.getDeliveryAreaId(), mo.getDeliveryFeeRuleId() });
		}
		catch (DataAccessException e)
		{
			throw new LittleCatException(ErrorCode.DataAccessException.getCode(), ErrorCode.DataAccessException.getMsg(), e);
		}

		return mo.getId();
	}

	public void modify(SecKillPlanMO mo) throws LittleCatException
	{
		String sql = "update " + TABLE_NAME + " set startTime = ?,endTime = ?,price = ?,planInventory=?,currentInventory = ?,limitBuyNum = ?,deliveryAreaId = ?,deliveryFeeRuleId = ? where id = ?";

		try
		{
			int ret = jdbcTemplate.update(sql, new Object[] { mo.getStartTime(), mo.getEndTime(), mo.getPrice(), mo.getPlanInventory(), mo.getCurrentInventory(), mo.getLimitBuyNum(), mo.getDeliveryAreaId(), mo.getDeliveryFeeRuleId(), mo.getId() });

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

	public void disable(String id) throws LittleCatException
	{
		DaoUtil.disable(TABLE_NAME, id, jdbcTemplate);
	}

	public SecKillPlanMO getById(String id) throws LittleCatException
	{
		return DaoUtil.getById(TABLE_NAME, id, jdbcTemplate, new SecKillPlanMO.MOMapper());
	}

	public boolean isVariable(String id) throws LittleCatException
	{
		String sql = new StringBuilder()
				.append(" select count(*) from ").append(TABLE_NAME).append(" a ")
				.append(" where now() between a.startTime and a.endTime  ")
				.append(" and a.enable='Y'")
				.append(" and a.id = ? ")
				.toString();
		try
		{
			return jdbcTemplate.queryForObject(sql, new Object[] { id }, Integer.class) > 0;
		}
		catch (Exception e)
		{
			return false;
		}
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
				// 正在进行的秒杀
				.append("(select a.id,a.goodsId,a.currentInventory,a.price,a.endTime,b.name goodsName,b.price goodsPrice,b.mainImgData goodsMainImgData,b.summaryDescription goodsSummaryDescription")
				.append(" from ").append(TABLE_NAME).append(" a ")
				.append(" inner join ").append(TABLE_NAME_GOODS).append(" b on a.goodsId=b.id")
				.append(" where now() between a.startTime and a.endTime ")
				.append(" and a.enable='Y' ")
				.append(" and b.enable='Y' ")
				.append(" order by a.endTime limit 1000) ")

				.append(" UNION ")

				// 即将开始的秒杀
				.append("(select a.id,a.goodsId,a.currentInventory,a.price,a.endTime,b.name goodsName,b.price goodsPrice,b.mainImgData goodsMainImgData,b.summaryDescription goodsSummaryDescription")
				.append(" from ").append(TABLE_NAME).append(" a ")
				.append(" inner join ").append(TABLE_NAME_GOODS).append(" b on a.goodsId=b.id")
				.append(" where now() < a.startTime and now() + INTERVAL 15*60 SECOND > a.startTime ")
				.append(" and a.enable='Y' ")
				.append(" and b.enable='Y' ")
				.append(" order by a.startTime limit 1000) ")

				.append(" UNION ")

				// 刚刚结束的秒杀
				.append("(select a.id,a.goodsId,a.currentInventory,a.price,a.endTime,b.name goodsName,b.price goodsPrice,b.mainImgData goodsMainImgData,b.summaryDescription goodsSummaryDescription")
				.append(" from ").append(TABLE_NAME).append(" a ")
				.append(" inner join ").append(TABLE_NAME_GOODS).append(" b on a.goodsId=b.id")
				.append(" where now() > a.endTime and now() - INTERVAL 30*60 SECOND < a.endTime ")
				.append(" and a.enable='Y' ")
				.append(" and b.enable='Y' ")
				.append(" order by a.startTime limit 1000) ")

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
					mo.setPrice(rs.getBigDecimal("price"));
					mo.setEndTime(rs.getString("endTime"));
					mo.setCurrentInventory(rs.getBigDecimal("currentInventory"));
					mo.setGoodsName(rs.getString("goodsName"));
					mo.setGoodsPrice(rs.getBigDecimal("goodsPrice"));
					mo.setGoodsMainImgData(rs.getString("goodsMainImgData"));
					mo.setGoodsSummaryDescription(rs.getString("goodsSummaryDescription"));

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

	public List<SecKillPlanMO> getList4WebApp(String goodsId)
	{
		List<SecKillPlanMO> mos = new ArrayList<SecKillPlanMO>();

		StringBuilder sql = new StringBuilder()
				.append("select a.id,a.goodsId,date_format(a.startTime,'%Y-%m-%d %T') startTime,date_format(a.endTime,'%Y-%m-%d %T') endTime,a.limitBuyNum,a.planInventory,a.currentInventory,a.price,date_format(a.createTime,'%Y-%m-%d %T') createTime,a.enable,b.name goodsName,b.price goodsPrice,b.mainImgData goodsMainImgData")
				.append(" from ").append(TABLE_NAME).append(" a ")
				.append(" inner join ").append(TABLE_NAME_GOODS).append(" b on a.goodsId=b.id")
				.append(" where CURRENT_TIMESTAMP <= a.endTime");

		if (StringUtil.isNotEmpty(goodsId))
		{
			sql.append(" and a.goodsId='").append(goodsId).append("'");
		}

		sql.append(" order by a.enable desc,a.startTime asc ");

		try
		{
			mos.addAll(jdbcTemplate.query(sql.toString(), new SecKillPlanMO.MOMapper4WebList()));
		}
		catch (DataAccessException e)
		{
			throw new LittleCatException(ErrorCode.DataAccessException.getCode(), ErrorCode.DataAccessException.getMsg(), e);
		}

		return mos;
	}

}
