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
import com.littlecat.delivery.model.TuanZhangReceiveMO;

@Component
public class TuanZhangReceiveDao
{
	@Autowired
	protected JdbcTemplate jdbcTemplate;

	private final String TABLE_NAME = TableName.DeliveryTuanZhangReceive.getName();
	private final String TABLE_NAME_GOODS = TableName.Goods.getName();
	private final String TABLE_NAME_SYSOPERATOR = TableName.SysOperator.getName();
	private final String TABLE_NAME_ORDERDETAIL = TableName.OrderDetail.getName();
	private final String TABLE_NAME_ORDER = TableName.Order.getName();
	private final String TABLE_NAME_TUAN = TableName.Tuan.getName();

	public boolean exist(String orderDate) throws LittleCatException
	{
		String sql = "select count(1) from " + TABLE_NAME + " where orderDate = ?";
		return jdbcTemplate.queryForObject(sql, Integer.class) > 0;
	}

	public List<TuanZhangReceiveMO> genData(String orderDate) throws LittleCatException
	{
		StringBuilder sql = new StringBuilder()
				.append("select b.deliveryTuanZhangId,a.goodsId,sum(a.goodsNum) goodsNum from ").append(TABLE_NAME_ORDERDETAIL).append(" a ")
				.append(" inner join ").append(TABLE_NAME_ORDER).append(" b on a.orderId=b.id")
				.append(" group by b.deliveryTuanZhangId,a.goodsId")
				.append(" where DATE(b.payTime)=").append("DATE('").append(orderDate).append("'")
				.append(" and b.state='").append("daifahuo'")
				.append(" and b.deliveryTuanZhangId is not null");

		try
		{
			return jdbcTemplate.query(sql.toString(), new RowMapper<TuanZhangReceiveMO>()
			{

				@Override
				public TuanZhangReceiveMO mapRow(ResultSet rs, int rowNum) throws SQLException
				{
					TuanZhangReceiveMO mo = new TuanZhangReceiveMO();

					mo.setOrderDate(orderDate);
					mo.setTuanZhangId(rs.getString("deliveryTuanZhangId"));
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

	public void add(List<TuanZhangReceiveMO> mos) throws LittleCatException
	{
		if (CollectionUtil.isEmpty(mos))
		{
			return;
		}

		String sql = "insert into " + TABLE_NAME + "(id,orderDate,tuanZhangId,tuanZhangMobile,goodsId,goodsNum,receiveOperatorId) values(?,?,?,?,?,?,?)";

		List<Object[]> batchParams = new ArrayList<Object[]>();

		for (TuanZhangReceiveMO mo : mos)
		{
			if (StringUtil.isEmpty(mo.getId()))
			{
				mo.setId(UUIDUtil.createUUID());
			}

			batchParams.add(new Object[] { mo.getId(), mo.getOrderDate(), mo.getTuanZhangId(), mo.getTuanZhangMobile(), mo.getGoodsId(), mo.getGoodsNum(), mo.getReceiveOperatorId() });
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

	public void delete(String tuanZhangId, String orderDate) throws LittleCatException
	{

		String sql = "delete from  " + TABLE_NAME + "  where orderDate = ? and tuanZhangId=?";

		try
		{
			jdbcTemplate.update(sql, new Object[] { orderDate, tuanZhangId });
		}
		catch (DataAccessException e)
		{
			throw new LittleCatException(ErrorCode.DataAccessException.getCode(), ErrorCode.DataAccessException.getMsg(), e);
		}
	}

	public void receive(List<String> ids) throws LittleCatException
	{
		if (CollectionUtil.isEmpty(ids))
		{
			return;
		}

		String sql = "update " + TABLE_NAME + " set state = 'Y',receiveTime = CURRENT_TIMESTAMP where id in (:ids)";

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

	public List<TuanZhangReceiveMO> getList(String orderDate,String tuanZhangName, String tuanZhangMobile, String state) throws LittleCatException
	{

		StringBuilder sql = new StringBuilder()
				.append("select a.*,b.name receiveOperatorName,c.name goodsName,d.name tuanZhangName,d.mobile  tuanZhangMobile  from  ").append(TABLE_NAME).append(" a ")
				.append(" left join ").append(TABLE_NAME_SYSOPERATOR).append(" b ").append(" on a.outOperatorId = b.id")
				.append(" inner join ").append(TABLE_NAME_GOODS).append(" c ").append(" on a.goodsId=c.id")
				.append(" inner join ").append(TABLE_NAME_TUAN).append(" d on a.tuanZhangId=d.id")
				.append(" where a.orderDate='" + orderDate + "'");

		if (StringUtil.isNotEmpty(tuanZhangName))
		{
			sql.append(" and d.name like '%" + tuanZhangName + "%' ");
		}
		
		if (StringUtil.isNotEmpty(tuanZhangMobile))
		{
			sql.append(" and d.mobile like '%" + tuanZhangMobile + "%' ");
		}
		
		if (StringUtil.isNotEmpty(state))
		{
			sql.append(" and a.state = '" + state + "' ");
		}

		try
		{
			return jdbcTemplate.query(sql.toString(), new RowMapper<TuanZhangReceiveMO>()
			{

				@Override
				public TuanZhangReceiveMO mapRow(ResultSet rs, int rowNum) throws SQLException
				{
					TuanZhangReceiveMO mo = new TuanZhangReceiveMO();

					mo.setId(rs.getString("id"));
					mo.setOrderDate(rs.getString("orderDate"));
					mo.setTuanZhangId(rs.getString("tuanZhangId"));
					mo.setGoodsId(rs.getString("goodsId"));
					mo.setGoodsNum(rs.getBigDecimal("goodsNum"));
					mo.setReceiveOperatorId(rs.getString("receiveOperatorId"));
					mo.setCreateTime(StringUtil.replace(rs.getString("createTime"), ".0", ""));
					mo.setReceiveTime(StringUtil.replace(rs.getString("receiveTime"), ".0", ""));
					mo.setState(rs.getString("state"));

					mo.setTuanZhangName(rs.getString("tuanZhangName"));
					mo.setTuanZhangMobile(rs.getString("tuanZhangMobile"));
					mo.setReceiveOperatorName(rs.getString("receiveOperatorName"));
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
