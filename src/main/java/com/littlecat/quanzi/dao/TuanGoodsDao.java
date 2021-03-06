package com.littlecat.quanzi.dao;

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
import com.littlecat.goods.model.GoodsMO;
import com.littlecat.quanzi.model.TuanGoodsMO;

@Component
public class TuanGoodsDao
{
	@Autowired
	protected JdbcTemplate jdbcTemplate;

	private final String TABLE_NAME = TableName.TuanGoods.getName();
	private final String TABLE_NAME_GOODS = TableName.Goods.getName();
	private final String MODEL_NAME = TuanGoodsMO.class.getSimpleName();

	public void delete(String id) throws LittleCatException
	{
		DaoUtil.delete(TABLE_NAME, id, jdbcTemplate);
	}

	public void delete(List<String> ids) throws LittleCatException
	{
		DaoUtil.delete(TABLE_NAME, ids, jdbcTemplate);
	}

	public String add(TuanGoodsMO mo) throws LittleCatException
	{
		if (mo == null)
		{
			throw new LittleCatException(ErrorCode.RequestObjectIsNull.getCode(), ErrorCode.RequestObjectIsNull.getMsg().replace("{INFO_NAME}", MODEL_NAME));
		}

		if (StringUtil.isEmpty(mo.getId()))
		{
			mo.setId(UUIDUtil.createUUID());
		}

		String sql = "insert into " + TABLE_NAME + "(id,tuanId,buyType,ressId) values(?,?,?,?)";

		try
		{
			int ret = jdbcTemplate.update(sql,
					new Object[] { mo.getId(), mo.getTuanId(), mo.getBuyType().name(), mo.getResId() });

			if (ret != 1)
			{
				throw new LittleCatException(ErrorCode.InsertObjectToDBError.getCode(),
						ErrorCode.InsertObjectToDBError.getMsg().replace("{INFO_NAME}", MODEL_NAME));
			}
		}
		catch (DataAccessException e)
		{
			throw new LittleCatException(ErrorCode.DataAccessException.getCode(),
					ErrorCode.DataAccessException.getMsg(), e);
		}

		return mo.getId();
	}

	public List<String> batchAdd(List<TuanGoodsMO> mos) throws LittleCatException
	{
		if (CollectionUtil.isEmpty(mos))
		{
			throw new LittleCatException(ErrorCode.RequestObjectIsNull.getCode(), ErrorCode.RequestObjectIsNull.getMsg().replace("{INFO_NAME}", MODEL_NAME));
		}

		List<String> ids = new ArrayList<String>();
		String sql = "insert into " + TABLE_NAME + "(id,tuanId,buyType,resId) values(?,?,?,?)";
		List<Object[]> sqlParams = new ArrayList<>();

		for (TuanGoodsMO mo : mos)
		{
			if (StringUtil.isEmpty(mo.getId()))
			{
				mo.setId(UUIDUtil.createUUID());
			}

			ids.add(mo.getId());

			sqlParams.add(new Object[] { mo.getId(), mo.getTuanId(), mo.getBuyType().name(), mo.getResId() });
		}

		try
		{
			jdbcTemplate.batchUpdate(sql, sqlParams);
		}
		catch (DataAccessException e)
		{
			throw new LittleCatException(ErrorCode.DataAccessException.getCode(), ErrorCode.DataAccessException.getMsg(), e);
		}

		return ids;
	}

	public int getList(QueryParam queryParam, List<TuanGoodsMO> mos) throws LittleCatException
	{
		return DaoUtil.getList(TABLE_NAME, queryParam, mos, jdbcTemplate, new TuanGoodsMO.MOMapper());
	}

	/**
	 * 获取该圈子尚未上架的商品（目前只实现一次性加载）
	 * 
	 * @param tuanId
	 * @return
	 * @throws LittleCatException
	 */
	public List<GoodsMO> getUnPutOnGoodsList(String tuanId) throws LittleCatException
	{
		List<GoodsMO> goodsList = null;

		String sql = "select * from " + TABLE_NAME_GOODS + " where id not in (select goodsId from " + TABLE_NAME + " where tuanId=?)";

		try
		{
			goodsList = jdbcTemplate.query(sql, new Object[] { tuanId }, new GoodsMO.MOMapper());
		}
		catch (DataAccessException e)
		{
			throw new LittleCatException(ErrorCode.DataAccessException.getCode(), ErrorCode.DataAccessException.getMsg(), e);
		}

		return goodsList;
	}
}
