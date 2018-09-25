package com.littlecat.order.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.query.ConditionItem;
import com.littlecat.cbb.query.ConditionOperatorType;
import com.littlecat.cbb.query.QueryCondition;
import com.littlecat.cbb.query.QueryParam;
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
	private static final String FIELDNAME_ORDERID = "orderId";

	@Autowired
    protected JdbcTemplate jdbcTemplate;
	
	private final String TABLE_NAME = TableName.OrderDetail.getName();
	private final String MODEL_NAME = "OrderDetailMO";
	
	public List<String> add(List<OrderDetailMO> mos) throws LittleCatException
	{
		if(CollectionUtil.isEmpty(mos))
		{
			throw new LittleCatException(ErrorCode.GiveNullObjectToCreate.getCode(),ErrorCode.GiveNullObjectToCreate.getMsg().replace("{INFO_NAME}",MODEL_NAME));
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
			
			batchParam.add(new Object[] { mo.getId(), mo.getOrderId(), mo.getBuyType().name(), mo.getResId(), mo.getPrice(), mo.getGoodsNum(), mo.getFee() });
		}
		
		String sql = "insert into " + TABLE_NAME + "(id,orderId,buyType,resId,price,goodsNum,fee) values(?,?,?,?,?,?,?)";
		
		try
		{
			jdbcTemplate.batchUpdate(sql, batchParam);
		}
		catch(DataAccessException e)
		{
			throw new LittleCatException(ErrorCode.DataAccessException.getCode(),ErrorCode.DataAccessException.getMsg(),e);
		}

		return ids;
	}
	
	public OrderDetailMO getById(String id) throws LittleCatException
	{
		return DaoUtil.getById(TABLE_NAME, id, jdbcTemplate, new OrderDetailMO.MOMapper());
	}
	
	public List<OrderDetailMO> getByOrderId(String orderId) throws LittleCatException
	{
		List<OrderDetailMO> mos = new ArrayList<OrderDetailMO>();

		QueryParam queryParam = new QueryParam();
		QueryCondition condition = new QueryCondition();
		condition.getCondItems().add(new ConditionItem(FIELDNAME_ORDERID, orderId, ConditionOperatorType.equal));
		queryParam.setCondition(condition);

		getList(queryParam, mos);

		if (CollectionUtil.isEmpty(mos))
		{
			throw new LittleCatException(ErrorCode.GetInfoFromDBReturnEmpty.getCode(), ErrorCode.GetInfoFromDBReturnEmpty.getMsg().replace("{INFO_NAME}", MODEL_NAME));
		}

		return mos;
	}

	public int getList(QueryParam queryParam,List<OrderDetailMO> mos) throws LittleCatException
	{
		return DaoUtil.getList(TABLE_NAME, queryParam, mos, jdbcTemplate, new OrderDetailMO.MOMapper());
	}
}