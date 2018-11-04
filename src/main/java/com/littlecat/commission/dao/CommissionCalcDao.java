package com.littlecat.commission.dao;

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
import com.littlecat.commission.model.CommissionCalcMO;
import com.littlecat.common.consts.ErrorCode;
import com.littlecat.common.consts.TableName;
import com.littlecat.common.utils.DaoUtil;

@Component
public class CommissionCalcDao
{
	@Autowired
	protected JdbcTemplate jdbcTemplate;

	private final String TABLE_NAME = TableName.CommissionCalc.getName();
	private final String MODEL_NAME = CommissionCalcMO.class.getSimpleName();

	public CommissionCalcMO getById(String id) throws LittleCatException
	{
		return DaoUtil.getById(TABLE_NAME, id, jdbcTemplate, new CommissionCalcMO.MOMapper());
	}

	public void add(List<CommissionCalcMO> mos) throws LittleCatException
	{
		if (CollectionUtil.isEmpty(mos))
		{
			throw new LittleCatException("CommissionCalcDao:add:CommissionCalcMO list is empty.");
		}

		String sql = "insert into " + TABLE_NAME + "(id,orderId,tuanZhangId,goodsId,goodsFee,commissionTypeId,calcFee) values(?,?,?,?)";

		List<Object[]> batchParams = new ArrayList<Object[]>();

		for (CommissionCalcMO mo : mos)
		{
			if (StringUtil.isEmpty(mo.getId()))
			{
				mo.setId(UUIDUtil.createUUID());
			}

			batchParams.add(new Object[] { mo.getId(), mo.getOrderId(), mo.getTuanZhangId(), mo.getGoodsId(), mo.getGoodsFee(), mo.getCommissionTypeId(), mo.getCalcFee() });
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

	public void modify(CommissionCalcMO mo) throws LittleCatException
	{
		if (mo == null)
		{
			throw new LittleCatException(ErrorCode.RequestObjectIsNull.getCode(), ErrorCode.RequestObjectIsNull.getMsg().replace("{INFO_NAME}", MODEL_NAME));
		}

		String sql = "update " + TABLE_NAME + " set payTime = ?,remark=?,payOperatorId=? where id = ?";

		try
		{
			int ret = jdbcTemplate.update(sql, new Object[] { mo.getPayTime(), mo.getRemark(),mo.getPayOperatorId(), mo.getId() });

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

	public int getList(QueryParam queryParam, List<CommissionCalcMO> mos) throws LittleCatException
	{
		return DaoUtil.getList(TABLE_NAME, queryParam, mos, jdbcTemplate, new CommissionCalcMO.MOMapper());
	}
}
