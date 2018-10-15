package com.littlecat.order.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.query.QueryParam;
import com.littlecat.cbb.utils.StringUtil;
import com.littlecat.cbb.utils.UUIDUtil;
import com.littlecat.common.consts.BuyType;
import com.littlecat.common.consts.ErrorCode;
import com.littlecat.common.consts.TableName;
import com.littlecat.common.utils.DaoUtil;
import com.littlecat.order.model.OrderMO;

@Component
public class OrderDao
{
	@Autowired
	protected JdbcTemplate jdbcTemplate;

	private final String TABLE_NAME = TableName.Order.getName();
	private static final String MODEL_NAME = OrderMO.class.getSimpleName();
	private static final String TABLE_NAME_ORDERDETAIL = TableName.OrderDetail.getName();

	public String add(OrderMO mo) throws LittleCatException
	{
		if (mo == null)
		{
			throw new LittleCatException(ErrorCode.RequestObjectIsNull.getCode(), ErrorCode.RequestObjectIsNull.getMsg().replace("{INFO_NAME}", MODEL_NAME));
		}

		if (StringUtil.isEmpty(mo.getId()))
		{
			mo.setId(UUIDUtil.createUUID());
		}

		String sql = "insert into " + TABLE_NAME + "(id,terminalUserId,fee,state,provinceId,cityId,areaId,detailInfo,groupBuyPlanId,groupBuyTaskId) values(?,?,?,?,?,?,?,?,?,?)";

		try
		{
			int ret = jdbcTemplate.update(sql, new Object[] { mo.getId(), mo.getTerminalUserId(), mo.getFee(), mo.getState().name(), mo.getDeliveryAddress().getProvinceId(), mo.getDeliveryAddress().getCityId(), mo.getDeliveryAddress().getAreaId(), mo.getDeliveryAddress().getDetailInfo(), mo.getGroupBuyPlanId(), mo.getGroupBuyTaskId() });

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

	public void modify(OrderMO mo) throws LittleCatException
	{
		String sql = "update " + TABLE_NAME + " set state = ?,payTime = ?,receiveTime = ?,returnApplyTime = ?,returnCompleteTime = ?,groupCompleteTime=?,groupCancelTime=? where id = ?";

		try
		{
			int ret = jdbcTemplate.update(sql, new Object[] { mo.getState().name(), mo.getPayTime(), mo.getReceiveTime(), mo.getReturnApplyTime(), mo.getReturnCompleteTime(), mo.getGroupCompleteTime(), mo.getGroupCancelTime(), mo.getId() });

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

	public void modify(List<OrderMO> mos) throws LittleCatException
	{
		String sql = "update " + TABLE_NAME + " set state = ?,payTime = ?,receiveTime = ?,returnApplyTime = ?,returnCompleteTime = ?,groupCompleteTime=?,groupCancelTime=? where id = ?";
		List<Object[]> batchParam = new ArrayList<Object[]>();

		for (OrderMO mo : mos)
		{
			batchParam.add(new Object[] { mo.getState().name(), mo.getPayTime(), mo.getReceiveTime(), mo.getReturnApplyTime(), mo.getReturnCompleteTime(), mo.getGroupCompleteTime(), mo.getGroupCancelTime(), mo.getId() });
		}
		try
		{
			jdbcTemplate.batchUpdate(sql, batchParam);
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

	/**
	 * 查询某个消费者在某个秒杀计划下已经购买的商品数量
	 * 
	 * @param secKillPlanId
	 * @param terminalUserId
	 * @return
	 * @throws LittleCatException
	 */
	public int getBuyedNumOfSecKillPlan(String secKillPlanId, String terminalUserId) throws LittleCatException
	{
		String sql = new StringBuilder()
				.append("select sum(orderdetail.goodsNum) from ")
				.append(TABLE_NAME_ORDERDETAIL).append(" orderdetail,")
				.append(TABLE_NAME).append(" order ")
				.append(" where order.id = orderdetail.orderId")
				.append(" and order.terminalUserId = ?")
				.append(" and orderdetail.buyType=?")
				.append(" and orderdetail.resId = ?")
				.toString();

		try
		{
			return jdbcTemplate.queryForObject(sql, new Object[] { terminalUserId, BuyType.seckill.name(), secKillPlanId }, Integer.class);
		}
		catch (DataAccessException e)
		{
			throw new LittleCatException(ErrorCode.DataAccessException.getCode(), ErrorCode.DataAccessException.getMsg(), e);
		}
	}
}
