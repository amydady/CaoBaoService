package com.littlecat.shoppingcart.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.query.QueryParam;
import com.littlecat.cbb.utils.CollectionUtil;
import com.littlecat.cbb.utils.StringUtil;
import com.littlecat.cbb.utils.UUIDUtil;
import com.littlecat.common.consts.ErrorCode;
import com.littlecat.common.consts.TableName;
import com.littlecat.common.utils.DaoUtil;
import com.littlecat.shoppingcart.model.ShoppingCartMO;

@Component
public class ShoppingCartDao
{
	@Autowired
	protected JdbcTemplate jdbcTemplate;

	private final String TABLE_NAME = TableName.ShoppingCart.getName();
	private final String MODEL_NAME = ShoppingCartMO.class.getSimpleName();

	public void delete(String id) throws LittleCatException
	{
		DaoUtil.delete(TABLE_NAME, id, jdbcTemplate);
	}

	public void delete(List<String> ids) throws LittleCatException
	{
		DaoUtil.delete(TABLE_NAME, ids, jdbcTemplate);
	}

	public String add(ShoppingCartMO mo) throws LittleCatException
	{
		if (StringUtil.isEmpty(mo.getId()))
		{
			mo.setId(UUIDUtil.createUUID());
		}

		String sql = "insert into " + TABLE_NAME + "(id,terminalUserId,buyType,resId,shareTuanZhangId) values(?,?,?,?,?)";

		try
		{
			jdbcTemplate.update(sql, new Object[] { mo.getId(), mo.getTerminalUserId(), mo.getBuyType().name(), mo.getResId(), mo.getShareTuanZhangId() });
		}
		catch (DataAccessException e)
		{
			throw new LittleCatException(ErrorCode.DataAccessException.getCode(), ErrorCode.DataAccessException.getMsg(), e);
		}

		return mo.getId();
	}

	public void modify(ShoppingCartMO mo) throws LittleCatException
	{
		String sql = "update " + TABLE_NAME + " set goodsNum = ?,tuanZhangId=? where id = ?";

		try
		{
			jdbcTemplate.update(sql, new Object[] { mo.getGoodsNum(), mo.getShareTuanZhangId(), mo.getId() });
		}
		catch (DataAccessException e)
		{
			throw new LittleCatException(ErrorCode.DataAccessException.getCode(), ErrorCode.DataAccessException.getMsg(), e);
		}
	}

	public void modify(List<ShoppingCartMO> mos) throws LittleCatException
	{
		if (CollectionUtil.isEmpty(mos))
		{
			throw new LittleCatException(ErrorCode.RequestObjectIsNull.getCode(), ErrorCode.RequestObjectIsNull.getMsg().replace("{INFO_NAME}", MODEL_NAME));
		}

		List<Object[]> batchParam = new ArrayList<Object[]>();

		for (ShoppingCartMO mo : mos)
		{
			batchParam.add(new Object[] { mo.getGoodsNum(), mo.getShareTuanZhangId(), mo.getId() });
		}

		String sql = "update " + TABLE_NAME + " set goodsNum = ?,tuanZhangId=? where id = ?";

		try
		{
			jdbcTemplate.batchUpdate(sql, batchParam);
		}
		catch (DataAccessException e)
		{
			throw new LittleCatException(ErrorCode.DataAccessException.getCode(), ErrorCode.DataAccessException.getMsg(), e);
		}
	}

	public int getList(QueryParam queryParam, List<ShoppingCartMO> mos) throws LittleCatException
	{
		return DaoUtil.getList(TABLE_NAME, queryParam, mos, jdbcTemplate, new ShoppingCartMO.MOMapper());
	}
}
