package com.littlecat.commission.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.commission.model.CommissionApplyMO;
import com.littlecat.common.consts.ErrorCode;
import com.littlecat.common.consts.TableName;

@Component
public class CommissionApplyDao
{
	@Autowired
	protected JdbcTemplate jdbcTemplate;

	private final String TABLE_NAME = TableName.CommissionApply.getName();

	public void add(CommissionApplyMO mo) throws LittleCatException
	{
		String sql = "insert into " + TABLE_NAME + "(tuanZhangId,applyTime,bankHolderName,bankName,bankAccount,zfbName,zfbAccount) values(?,?,?,?,?,?,?)";

		try
		{
			jdbcTemplate.update(sql, new Object[] { mo.getTuanZhangId(), mo.getApplyTime(), mo.getBankHolderName(), mo.getBankName(), mo.getBankAccount(), mo.getZfbName(), mo.getZfbAccount() });
		}
		catch (DataAccessException e)
		{
			throw new LittleCatException(ErrorCode.DataAccessException.getCode(), ErrorCode.DataAccessException.getMsg(), e);
		}
	}

	public CommissionApplyMO getByApplyTime(String tuanZhangId, String applyTime) throws LittleCatException
	{
		StringBuilder sql = new StringBuilder()
				.append(" select *  from ").append(TABLE_NAME)
				.append(" where tuanZhangId=? and date_format(applyTime,'%Y-%m-%d %T') = date_format(?,'%Y-%m-%d %T') ");

		return jdbcTemplate.queryForObject(sql.toString(), new Object[] { tuanZhangId, applyTime }, new CommissionApplyMO.MOMapper());
	}

	public CommissionApplyMO getLatest(String tuanZhangId) throws LittleCatException
	{
		StringBuilder sql = new StringBuilder()
				.append(" select *  from ").append(TABLE_NAME)
				.append(" where tuanZhangId=? order by applyTime desc limit 1");

		try
		{
			return jdbcTemplate.queryForObject(sql.toString(), new Object[] { tuanZhangId }, new CommissionApplyMO.MOMapper());
		}
		catch (Exception e)
		{
			return null;
		}
	}
}
