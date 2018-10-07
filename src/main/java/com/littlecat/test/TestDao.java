package com.littlecat.test;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.query.QueryParam;
import com.littlecat.cbb.utils.UUIDUtil;
import com.littlecat.common.consts.ErrorCode;
import com.littlecat.common.utils.DaoUtil;
import com.littlecat.order.model.OrderMO;

@Component
public class TestDao
{
	@Autowired
	protected JdbcTemplate jdbcTemplate;

	private final String TABLE_NAME = "t_test";
	private static final String MODEL_NAME = TestMO.class.getSimpleName();

	public void add(TestMO mo) throws LittleCatException
	{
		mo.setId(UUIDUtil.createUUID());

		String sql = "insert into " + TABLE_NAME + "(id,dateTime) values(?,?)";

		try
		{
			int ret = jdbcTemplate.update(sql, new Object[] { mo.getId(), mo.getDateTime() });

			if (ret != 1)
			{
				throw new LittleCatException(ErrorCode.InsertObjectToDBError.getCode(), ErrorCode.InsertObjectToDBError.getMsg().replace("{INFO_NAME}", MODEL_NAME));
			}
		}
		catch (DataAccessException e)
		{
			throw new LittleCatException(ErrorCode.DataAccessException.getCode(), ErrorCode.DataAccessException.getMsg(), e);
		}
	}

	public void modify(OrderMO mo) throws LittleCatException
	{
		if (mo == null)
		{
			throw new LittleCatException(ErrorCode.RequestObjectIsNull.getCode(), ErrorCode.RequestObjectIsNull.getMsg().replace("{INFO_NAME}", MODEL_NAME));
		}

		String sql = "update " + TABLE_NAME + " set state = ?,payTime = ?,receiveTime = ?,returnApplyTime = ?,returnCompleteTime = ? where id = ?";

		try
		{
			int ret = jdbcTemplate.update(sql, new Object[] { mo.getState().name(), mo.getPayTime(), mo.getReceiveTime(), mo.getReturnApplyTime(), mo.getReturnCompleteTime(), mo.getId() });

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

	public OrderMO getById(String id) throws LittleCatException
	{
		return DaoUtil.getById(TABLE_NAME, id, jdbcTemplate, new OrderMO.MOMapper());
	}

	public int getList(QueryParam queryParam, List<OrderMO> mos) throws LittleCatException
	{
		return DaoUtil.getList(TABLE_NAME, queryParam, mos, jdbcTemplate, new OrderMO.MOMapper());
	}
}
