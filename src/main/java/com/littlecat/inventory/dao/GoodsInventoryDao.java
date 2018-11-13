package com.littlecat.inventory.dao;

import java.math.BigDecimal;
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
import com.littlecat.common.consts.InventoryChangeType;
import com.littlecat.common.consts.TableName;
import com.littlecat.inventory.model.GoodsInventoryMO;

@Component
public class GoodsInventoryDao
{
	@Autowired
	protected JdbcTemplate jdbcTemplate;

	private final String TABLE_NAME = TableName.GoodsInventory.getName();
	private static final String MODEL_NAME = GoodsInventoryMO.class.getSimpleName();
	private final String TABLE_NAME_SYSOPERATOR = TableName.SysOperator.getName();

	public String add(GoodsInventoryMO mo) throws LittleCatException
	{
		if (StringUtil.isEmpty(mo.getId()))
		{
			mo.setId(UUIDUtil.createUUID());
		}

		String sql = "insert into " + TABLE_NAME + "(id,goodsId,changeValue,changeType,operatorId,description) values(?,?,?,?,?,?)";

		try
		{
			int ret = jdbcTemplate.update(sql, new Object[] { mo.getId(), mo.getGoodsId(), mo.getChangeValue(), mo.getChangeType().name(), mo.getOperatorId(), mo.getDescription() });

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

	public BigDecimal getCurrentValueByGoodsId(String goodsId) throws LittleCatException
	{
		String sql = "select sum(changeValue) from " + TABLE_NAME + " where goodsId=?";

		try
		{
			return jdbcTemplate.queryForObject(sql, new Object[] { goodsId }, BigDecimal.class);
		}
		catch (DataAccessException e)
		{
			throw new LittleCatException(ErrorCode.DataAccessException.getCode(), ErrorCode.DataAccessException.getMsg(), e);
		}
	}

	public List<GoodsInventoryMO> getListByGoodsId(String goodsId) throws LittleCatException
	{
		List<GoodsInventoryMO> mos = new ArrayList<GoodsInventoryMO>();

		String sql = new StringBuilder()
				.append("select a.*,b.name operatorName")
				.append(" from ").append(TABLE_NAME).append(" a ")
				.append(" left join ").append(TABLE_NAME_SYSOPERATOR).append(" b on a.operatorId=b.id ")
				.append(" where a.goodsId=? ")
				.append(" order by createtime desc ")
				.toString();

		try
		{
			mos.addAll(jdbcTemplate.query(sql, new Object[] { goodsId }, new RowMapper<GoodsInventoryMO>()
			{

				@Override
				public GoodsInventoryMO mapRow(ResultSet rs, int rowNum) throws SQLException
				{
					GoodsInventoryMO mo = new GoodsInventoryMO();

					mo.setId(rs.getString("id"));
					mo.setGoodsId(rs.getString("goodsId"));
					mo.setChangeValue(rs.getBigDecimal("changeValue"));
					mo.setChangeType(InventoryChangeType.valueOf(rs.getString("changeType")));
					mo.setOperatorId(rs.getString("operatorId"));
					mo.setDescription(rs.getString("description"));
					mo.setCreateTime(StringUtil.replace(rs.getString("createTime"), ".0", ""));
					

					mo.setOperatorName(rs.getString("operatorName"));

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
