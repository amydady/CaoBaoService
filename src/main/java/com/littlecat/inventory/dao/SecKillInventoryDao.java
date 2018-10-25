package com.littlecat.inventory.dao;

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
import com.littlecat.common.consts.InventoryChangeType;
import com.littlecat.common.consts.TableName;
import com.littlecat.common.utils.DaoUtil;
import com.littlecat.inventory.model.SecKillInventoryMO;

@Component
public class SecKillInventoryDao
{
	@Autowired
	protected JdbcTemplate jdbcTemplate;

	private final String TABLE_NAME = TableName.SecKillInventory.getName();
	private static final String MODEL_NAME = SecKillInventoryMO.class.getSimpleName();

	public String add(SecKillInventoryMO mo) throws LittleCatException
	{
		if (mo == null)
		{
			throw new LittleCatException(ErrorCode.RequestObjectIsNull.getCode(), ErrorCode.RequestObjectIsNull.getMsg().replace("{INFO_NAME}", MODEL_NAME));
		}

		if (StringUtil.isEmpty(mo.getId()))
		{
			mo.setId(UUIDUtil.createUUID());
		}

		String sql = "insert into " + TABLE_NAME + "(id,planId,changeValue,changeType,operatorId,description) values(?,?,?,?,?,?)";

		try
		{
			int ret = jdbcTemplate.update(sql, new Object[] { mo.getId(), mo.getPlanId(), mo.getChangeValue(), mo.getChangeType().name(), mo.getOperatorId(), mo.getDescription() });

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

	public int getList(QueryParam queryParam, List<SecKillInventoryMO> mos) throws LittleCatException
	{
		return DaoUtil.getList(TABLE_NAME, queryParam, mos, jdbcTemplate, new SecKillInventoryMO.MOMapper());
	}

	/**
	 * 获取某个秒杀计划的实际可用库存
	 * 
	 * @param planId
	 * @return
	 * @throws LittleCatException
	 */
	public long getCurrentValueByPlanId(String planId) throws LittleCatException
	{
		String sql = "select sum(changeValue) from " + TABLE_NAME + " where planId=?";

		try
		{
			return jdbcTemplate.queryForObject(sql, new Object[] { planId }, Long.class);
		}
		catch (DataAccessException e)
		{
			throw new LittleCatException(ErrorCode.DataAccessException.getCode(), ErrorCode.DataAccessException.getMsg(), e);
		}
	}

	/**
	 * 获取某个秒杀计划的规划库存
	 * 
	 * @param planId
	 * @return
	 * @throws LittleCatException
	 */
	public long getPlanValueByPlanId(String planId) throws LittleCatException
	{
		String sql = "select sum(changeValue) from " + TABLE_NAME + " where planId=? and changeType in ('rengongzengjia','rengongjianshao') ";

		try
		{
			return jdbcTemplate.queryForObject(sql, new Object[] { planId }, Long.class);
		}
		catch (DataAccessException e)
		{
			throw new LittleCatException(ErrorCode.DataAccessException.getCode(), ErrorCode.DataAccessException.getMsg(), e);
		}
	}

	public List<SecKillInventoryMO> getListByPlanId(String planId) throws LittleCatException
	{
		List<SecKillInventoryMO> mos = new ArrayList<SecKillInventoryMO>();

		String sql = new StringBuilder()
				.append("select a.id,a.planId,a.changeValue,a.changeType,a.operatorId,a.description,a.createTime")
				.append(" from ").append(TABLE_NAME).append(" a ")
				.append("where a.planId=?")
				.append(" order by createtime")
				.toString();

		try
		{
			mos.addAll(jdbcTemplate.query(sql, new Object[] { planId }, new RowMapper<SecKillInventoryMO>()
			{

				@Override
				public SecKillInventoryMO mapRow(ResultSet rs, int rowNum) throws SQLException
				{
					SecKillInventoryMO mo = new SecKillInventoryMO();

					mo.setId(rs.getString("id"));
					mo.setPlanId(rs.getString("planId"));
					mo.setChangeValue(rs.getLong("changeValue"));
					mo.setChangeType(InventoryChangeType.valueOf(rs.getString("changeType")));
					mo.setOperatorId(rs.getString("operatorId"));
					mo.setDescription(rs.getString("description"));
					mo.setCreateTime(rs.getString("createTime"));

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
}
