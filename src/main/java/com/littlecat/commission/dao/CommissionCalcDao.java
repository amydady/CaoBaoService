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
	private final String TABLE_NAME_TUAN = TableName.Tuan.getName();
	private final String TABLE_NAME_GOODS = TableName.Goods.getName();
	private final String TABLE_NAME_COMMISSIONTYPE = TableName.CommissionType.getName();
	private final String MODEL_NAME = CommissionCalcMO.class.getSimpleName();

	public CommissionCalcMO getById(String id) throws LittleCatException
	{
		StringBuilder sql = new StringBuilder()
				.append(" select a.*,b.name tuanZhangName,c.name goodsName,d.name commissionTypeName ")
				.append(" from ").append(TABLE_NAME).append(" a ")
				.append(" inner join ").append(TABLE_NAME_TUAN).append(" b  on a.tuanZhangId=b.id ")
				.append(" inner join ").append(TABLE_NAME_GOODS).append(" c on a.goodsId=c.id ")
				.append(" left join ").append(TABLE_NAME_COMMISSIONTYPE).append(" d on a.commissionTypeId=d.id")
				.append(" where a.id=? ");	
		
		try
		{
			return jdbcTemplate.queryForObject(sql.toString(), new Object[] { id },new CommissionCalcMO.MOMapper());
		}
		catch (DataAccessException e)
		{
			throw new LittleCatException(ErrorCode.DataAccessException.getCode(), ErrorCode.DataAccessException.getMsg(), e);
		}
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

	public void modify(List<CommissionCalcMO> mos) throws LittleCatException
	{
		String sql = "update " + TABLE_NAME + " set payTime = ? where id = ?";
		List<Object[]> batchParams = new ArrayList<Object[]>();
		
		for (CommissionCalcMO mo : mos)
		{
			batchParams.add(new Object[] { mo.getPayTime(), mo.getId() });
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
		String sql = "update " + TABLE_NAME + " set payTime = ? where id = ?";

		try
		{
			int ret = jdbcTemplate.update(sql, new Object[] { mo.getPayTime(), mo.getId() });

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

	public List<CommissionCalcMO> getList(String tuanZhangId, Boolean hasPayed) throws LittleCatException
	{
		StringBuilder sql = new StringBuilder()
				.append(" select a.*,b.name tuanZhangName,c.name goodsName,d.name commissionTypeName ")
				.append(" from ").append(TABLE_NAME).append(" a ")
				.append(" inner join ").append(TABLE_NAME_TUAN).append(" b  on a.tuanZhangId=b.id ")
				.append(" inner join ").append(TABLE_NAME_GOODS).append(" c on a.goodsId=c.id ")
				.append(" left join ").append(TABLE_NAME_COMMISSIONTYPE).append(" d on a.commissionTypeId=d.id")
				.append(" where 1=1 ");

		if (StringUtil.isNotEmpty(tuanZhangId))
		{
			sql.append(" and a.tuanZhangId='").append(tuanZhangId).append("' ");
		}

		if (hasPayed != null)
		{

			if (hasPayed)
			{
				sql.append(" and a.payTime is not null ");
			}
			else
			{
				sql.append(" and a.payTime is null ");
			}
		}

		sql.append(" order by tuanZhangName,commissionTypeName,goodsName ");

		return jdbcTemplate.query(sql.toString(), new CommissionCalcMO.MOMapper());
	}

	public int getList(QueryParam queryParam, List<CommissionCalcMO> mos) throws LittleCatException
	{
		return DaoUtil.getList(TABLE_NAME, queryParam, mos, jdbcTemplate, new CommissionCalcMO.MOMapper());
	}
}
