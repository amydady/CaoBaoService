package com.littlecat.delivery.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.utils.CollectionUtil;
import com.littlecat.cbb.utils.StringUtil;
import com.littlecat.cbb.utils.UUIDUtil;
import com.littlecat.common.consts.ErrorCode;
import com.littlecat.common.consts.TableName;
import com.littlecat.delivery.model.OutWarehouseMO;

@Component
public class OutWarehouseDao
{
	@Autowired
	protected JdbcTemplate jdbcTemplate;

	private final String TABLE_NAME = TableName.DeliveryOutWarehouse.getName();
	private final String TABLE_NAME_GOODS = TableName.Goods.getName();
	private final String TABLE_NAME_SYSOPERATOR = TableName.SysOperator.getName();
	private final String TABLE_NAME_ORDERDETAIL = TableName.OrderDetail.getName();
	private final String TABLE_NAME_ORDER = TableName.Order.getName();

	public void add(List<OutWarehouseMO> mos) throws LittleCatException
	{
		if (CollectionUtil.isEmpty(mos))
		{
			return;
		}

		String sql = "insert into " + TABLE_NAME + "(id,orderDate,goodsId,goodsNum,outOperatorId) values(?,?,?,?,?)";

		List<Object[]> batchParams = new ArrayList<Object[]>();
		for (OutWarehouseMO mo : mos)
		{
			if (StringUtil.isEmpty(mo.getId()))
			{
				mo.setId(UUIDUtil.createUUID());
			}

			batchParams.add(new Object[] { mo.getId(), mo.getOrderDate(), mo.getGoodsId(), mo.getGoodsNum(), mo.getOutOperatorId() });
		}

		try
		{
			jdbcTemplate.batchUpdate(sql, batchParams);
		}
		catch (DataAccessException e)
		{
			throw new LittleCatException(ErrorCode.DataAccessException.getCode(), ErrorCode.DataAccessException.getMsg(), e);
		}
	}

	public void delete(String orderDate) throws LittleCatException
	{

		String sql = "delete from  " + TABLE_NAME + "  where orderDate = ?";

		try
		{
			jdbcTemplate.update(sql, new Object[] { orderDate });
		}
		catch (DataAccessException e)
		{
			throw new LittleCatException(ErrorCode.DataAccessException.getCode(), ErrorCode.DataAccessException.getMsg(), e);
		}
	}

	public void out(List<String> ids) throws LittleCatException
	{
		if (CollectionUtil.isEmpty(ids))
		{
			return;
		}

		String sql = "update " + TABLE_NAME + " set state = 'Y',outTime = CURRENT_TIMESTAMP where id in (:ids)";

		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);

		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("ids", ids);

		try
		{
			namedParameterJdbcTemplate.update(sql, parameters);
		}
		catch (DataAccessException e)
		{
			throw new LittleCatException(ErrorCode.DataAccessException.getCode(), ErrorCode.DataAccessException.getMsg(), e);
		}
	}

	/**
	 * 检测某个订单日是否已经存在出仓单信息
	 * 
	 * @param orderDate
	 * @param state
	 * @return
	 * @throws LittleCatException
	 */
	public boolean exist(String orderDate) throws LittleCatException
	{
		String sql = "select count(1) from " + TABLE_NAME + " where orderDate = ?";
		return jdbcTemplate.queryForObject(sql, new Object[] { orderDate }, Integer.class) > 0;
	}

	public List<OutWarehouseMO> genData(String orderDate) throws LittleCatException
	{
		StringBuilder sql = new StringBuilder()
				.append("select a.goodsId,sum(a.goodsNum) goodsNum from ").append(TABLE_NAME_ORDERDETAIL).append(" a ")
				.append(" where a.orderId in (")
				.append("select id from ").append(TABLE_NAME_ORDER)
				.append(" where DATE(payTime)=").append("DATE('").append(orderDate).append("')")
				.append(" and state='").append("daifahuo'")
				.append(")")
				.append(" group by a.goodsId");

		try
		{
			return jdbcTemplate.query(sql.toString(), new RowMapper<OutWarehouseMO>()
			{

				@Override
				public OutWarehouseMO mapRow(ResultSet rs, int rowNum) throws SQLException
				{
					OutWarehouseMO mo = new OutWarehouseMO();

					mo.setOrderDate(orderDate);
					mo.setGoodsId(rs.getString("goodsId"));
					mo.setGoodsNum(rs.getBigDecimal("goodsNum"));

					return mo;
				}
			});
		}
		catch (DataAccessException e)
		{
			throw new LittleCatException(ErrorCode.DataAccessException.getCode(), ErrorCode.DataAccessException.getMsg(), e);
		}

	}

	public List<OutWarehouseMO> getList(String orderDate, String state) throws LittleCatException
	{

		StringBuilder sql = new StringBuilder()
				.append("select a.*,b.name outOperatorName,c.name goodsName  from  ").append(TABLE_NAME).append(" a ")
				.append(" left join ").append(TABLE_NAME_SYSOPERATOR).append(" b ").append(" on a.outOperatorId = b.id")
				.append(" inner join ").append(TABLE_NAME_GOODS).append(" c ").append(" on a.goodsId=c.id")
				.append(" where a.orderDate='" + orderDate + "'");

		if (StringUtil.isNotEmpty(state))
		{
			sql.append(" and a.state = '" + state + "' ");
		}

		try
		{
			return jdbcTemplate.query(sql.toString(), new RowMapper<OutWarehouseMO>()
			{

				@Override
				public OutWarehouseMO mapRow(ResultSet rs, int rowNum) throws SQLException
				{
					OutWarehouseMO mo = new OutWarehouseMO();
					mo.setId(rs.getString("id"));
					mo.setOrderDate(rs.getString("orderDate"));
					mo.setGoodsId(rs.getString("goodsId"));
					mo.setGoodsNum(rs.getBigDecimal("goodsNum"));
					mo.setOutOperatorId(rs.getString("outOperatorId"));
					mo.setCreateTime(StringUtil.replace(rs.getString("createTime"), ".0", ""));
					mo.setOutTime(StringUtil.replace(rs.getString("outTime"), ".0", ""));
					mo.setState(rs.getString("state"));

					mo.setOutOperatorName(rs.getString("outOperatorName"));
					mo.setGoodsName(rs.getString("goodsName"));

					return mo;
				}
			});
		}
		catch (DataAccessException e)
		{
			throw new LittleCatException(ErrorCode.DataAccessException.getCode(), ErrorCode.DataAccessException.getMsg(), e);
		}
	}
}
