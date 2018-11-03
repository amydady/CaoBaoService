package com.littlecat.commission.dao;

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
import com.littlecat.commission.model.CommissionCalcDetailMO;
import com.littlecat.common.consts.ErrorCode;
import com.littlecat.common.consts.TableName;
import com.littlecat.common.utils.DaoUtil;

@Component
public class CommissionCalcDetailDao
{
	@Autowired
	protected JdbcTemplate jdbcTemplate;

	private final String TABLE_NAME = TableName.CommissionCalcDetail.getName();

	public CommissionCalcDetailMO getById(String id) throws LittleCatException
	{
		return DaoUtil.getById(TABLE_NAME, id, jdbcTemplate, new CommissionCalcDetailMO.MOMapper());
	}

	public void add(List<CommissionCalcDetailMO> mos) throws LittleCatException
	{

		String sql = "insert into " + TABLE_NAME + "(id,calcId,goodsId,goodsFee,commissionTypeId,calcFee) values(?,?,?,?,?,?)";

		try
		{
			List<Object[]> batchParams = new ArrayList<Object[]>();

			for (CommissionCalcDetailMO mo : mos)
			{
				if (StringUtil.isEmpty(mo.getId()))
				{
					mo.setId(UUIDUtil.createUUID());
				}
			}

			jdbcTemplate.batchUpdate(sql, batchParams);
		}
		catch (DataAccessException e)
		{
			throw new LittleCatException(ErrorCode.DataAccessException.getCode(), ErrorCode.DataAccessException.getMsg(), e);
		}
	}

	public int getList(QueryParam queryParam, List<CommissionCalcDetailMO> mos) throws LittleCatException
	{
		return DaoUtil.getList(TABLE_NAME, queryParam, mos, jdbcTemplate, new CommissionCalcDetailMO.MOMapper());
	}
}
