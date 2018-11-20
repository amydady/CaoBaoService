package com.littlecat.order.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.utils.CollectionUtil;
import com.littlecat.cbb.utils.StringUtil;
import com.littlecat.cbb.utils.UUIDUtil;
import com.littlecat.common.consts.ErrorCode;
import com.littlecat.common.consts.TableName;
import com.littlecat.common.utils.DaoUtil;
import com.littlecat.order.model.OrderDetailMO;

@Component
public class OrderDetailDao
{
	private static final String TABLE_NAME = TableName.OrderDetail.getName();
	private static final String MODEL_NAME = OrderDetailMO.class.getSimpleName();

	@Autowired
	protected JdbcTemplate jdbcTemplate;

	public List<String> add(List<OrderDetailMO> mos) throws LittleCatException
	{
		if (CollectionUtil.isEmpty(mos))
		{
			throw new LittleCatException(ErrorCode.RequestObjectIsNull.getCode(), ErrorCode.RequestObjectIsNull.getMsg().replace("{INFO_NAME}", MODEL_NAME));
		}

		List<Object[]> batchParam = new ArrayList<Object[]>();
		List<String> ids = new ArrayList<String>();
		for (OrderDetailMO mo : mos)
		{
			if (StringUtil.isEmpty(mo.getId()))
			{
				mo.setId(UUIDUtil.createUUID());
				ids.add(mo.getId());
			}

			batchParam.add(new Object[] { mo.getId(), mo.getOrderId(), mo.getBuyType().name(), mo.getResId(), mo.getGoodsId(), mo.getPrice(), mo.getGoodsNum(), mo.getGoodsName(), mo.getGoodsMainImgData() });
		}

		String sql = "insert into " + TABLE_NAME + "(id,orderId,buyType,resId,goodsId,price,goodsNum,goodsName,goodsMainImgData) values(?,?,?,?,?,?,?,?,?)";

		try
		{
			jdbcTemplate.batchUpdate(sql, batchParam);
		}
		catch (DataAccessException e)
		{
			throw new LittleCatException(ErrorCode.DataAccessException.getCode(), ErrorCode.DataAccessException.getMsg(), e);
		}

		return ids;
	}

	public OrderDetailMO getById(String id) throws LittleCatException
	{
		return DaoUtil.getById(TABLE_NAME, id, jdbcTemplate, new OrderDetailMO.MOMapperWithGoodsDetail());
	}

	/**
	 * 查询订单的明细信息（包含商品明细信息）
	 * 
	 * @param orderId
	 * @return
	 * @throws LittleCatException
	 */
	public List<OrderDetailMO> getByOrderIdWithGoodsDetail(String orderId) throws LittleCatException
	{
		String sql = "select * from " + TABLE_NAME + " where orderId = ?";

		try
		{
			return jdbcTemplate.query(sql, new Object[] { orderId }, new OrderDetailMO.MOMapperWithGoodsDetail());
		}
		catch (DataAccessException e)
		{
			throw new LittleCatException(ErrorCode.DataAccessException.getCode(), ErrorCode.DataAccessException.getMsg(), e);
		}
	}

	/**
	 * 查询订单的明细信息
	 * 
	 * @param orderId
	 * @return
	 * @throws LittleCatException
	 */
	public List<OrderDetailMO> getByOrderId(String orderId) throws LittleCatException
	{
		String sql = "select * from " + TABLE_NAME + " where orderId = ?";

		try
		{
			return jdbcTemplate.query(sql, new Object[] { orderId }, new OrderDetailMO.MOMapper());
		}
		catch (DataAccessException e)
		{
			throw new LittleCatException(ErrorCode.DataAccessException.getCode(), ErrorCode.DataAccessException.getMsg(), e);
		}
	}
}
