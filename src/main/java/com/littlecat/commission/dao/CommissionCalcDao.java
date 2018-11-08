package com.littlecat.commission.dao;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.utils.CollectionUtil;
import com.littlecat.cbb.utils.StringUtil;
import com.littlecat.cbb.utils.UUIDUtil;
import com.littlecat.commission.model.CommissionCalcMO;
import com.littlecat.common.consts.CommissionState;
import com.littlecat.common.consts.ErrorCode;
import com.littlecat.common.consts.TableName;

@Component
public class CommissionCalcDao
{
	private static final Logger logger = LoggerFactory.getLogger(CommissionCalcDao.class);

	@Autowired
	protected JdbcTemplate jdbcTemplate;

	private final String TABLE_NAME = TableName.CommissionCalc.getName();
	private final String TABLE_NAME_TUAN = TableName.Tuan.getName();
	private final String TABLE_NAME_GOODS = TableName.Goods.getName();
	private final String TABLE_NAME_COMMISSIONTYPE = TableName.CommissionType.getName();
	private final String TABLE_NAME_ORDER = TableName.Order.getName();
	private final String TABLE_NAME_TERMINALUSER = TableName.TerminalUser.getName();
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
			return jdbcTemplate.queryForObject(sql.toString(), new Object[] { id }, new CommissionCalcMO.MOMapper());
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

		String sql = "insert into " + TABLE_NAME + "(id,orderId,tuanZhangId,goodsId,goodsFee,commissionTypeId,calcFee,state) values(?,?,?,?,?,?,?,?)";

		List<Object[]> batchParams = new ArrayList<Object[]>();

		for (CommissionCalcMO mo : mos)
		{
			if (StringUtil.isEmpty(mo.getId()))
			{
				mo.setId(UUIDUtil.createUUID());
			}

			batchParams.add(new Object[] { mo.getId(), mo.getOrderId(), mo.getTuanZhangId(), mo.getGoodsId(), mo.getGoodsFee(), mo.getCommissionTypeId(), mo.getCalcFee(), CommissionState.calced.name() });
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
		String sql = "update " + TABLE_NAME + " set applyTime=?,payTime = ?,state=?,disableTime=? where id = ?";
		List<Object[]> batchParams = new ArrayList<Object[]>();

		for (CommissionCalcMO mo : mos)
		{
			batchParams.add(new Object[] { mo.getApplyTime(), mo.getPayTime(), mo.getState().name(), mo.getDisableTime(), mo.getId() });
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
		String sql = "update " + TABLE_NAME + " set applyTime=?,payTime = ?,state=?,disableTime=? where id = ?";

		try
		{
			int ret = jdbcTemplate.update(sql, new Object[] { mo.getApplyTime(), mo.getPayTime(), mo.getState().name(), mo.getDisableTime(), mo.getId() });

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

	public List<CommissionCalcMO> getList(String tuanZhangId, String state) throws LittleCatException
	{
		StringBuilder sql = new StringBuilder()
				.append(" select a.*,b.name tuanZhangName,c.name goodsName,c.mainImgData goodsMainImgData,d.name commissionTypeName,f.name terminalUserName,f.image terminalUserImg ")
				.append(" from ").append(TABLE_NAME).append(" a ")
				.append(" inner join ").append(TABLE_NAME_TUAN).append(" b  on a.tuanZhangId=b.id ")
				.append(" inner join ").append(TABLE_NAME_GOODS).append(" c on a.goodsId=c.id ")
				.append(" left join ").append(TABLE_NAME_COMMISSIONTYPE).append(" d on a.commissionTypeId=d.id")
				.append(" inner join ").append(TABLE_NAME_ORDER).append(" e on a.orderId=e.id")
				.append(" inner join ").append(TABLE_NAME_TERMINALUSER).append(" f on e.terminalUserId=f.id")
				.append(" where 1=1 ");

		if (StringUtil.isNotEmpty(tuanZhangId))
		{
			sql.append(" and a.tuanZhangId='").append(tuanZhangId).append("' ");
		}

		if (StringUtil.isNotEmpty(state))
		{
			sql.append(" and a.state = '").append(state).append("' ");
		}

		sql.append(" order by tuanZhangName,state,commissionTypeName,goodsName ");

		return jdbcTemplate.query(sql.toString(), new CommissionCalcMO.MOMapper());
	}

	public List<String> getNeedEnableList() throws LittleCatException
	{
		StringBuilder sql = new StringBuilder()
				.append("select id from  ").append(TABLE_NAME)
				.append(" where state=").append(CommissionState.calced);

		return jdbcTemplate.queryForList(sql.toString(), String.class);
	}

	/**
	 * 查询累计发放佣金
	 * 
	 * @param tuanZhangId
	 * @return
	 */
	public long getTotalPayedFee(String tuanZhangId)
	{
		StringBuilder sql = new StringBuilder()
				.append("select sum(calcFee) totalPayedFee from  ").append(TABLE_NAME)
				.append(" where tuanZhangId='").append(tuanZhangId).append("'")
				.append(" and state='").append(CommissionState.payed).append("'");
		try
		{
			return jdbcTemplate.queryForObject(sql.toString(), Long.class);
		}
		catch (Exception e)
		{
			logger.error("getTotalPayedFee error:" + e.getMessage());
			return 0;
		}
	}

	public long getTotalCanApplyFee(String tuanZhangId)
	{
		StringBuilder sql = new StringBuilder()
				.append("select sum(calcFee) totalPayedFee from  ").append(TABLE_NAME)
				.append(" where tuanZhangId='").append(tuanZhangId).append("'")
				.append(" and state='").append(CommissionState.canapply).append("'");
		try
		{
			return jdbcTemplate.queryForObject(sql.toString(), Long.class);
		}
		catch (Exception e)
		{
			logger.error("getTotalCanApplyFee error:" + e.getMessage());
			return 0;
		}
	}

	public List<String> getApplyHis(String tuanZhangId)
	{
		StringBuilder sql = new StringBuilder()
				.append("select concat(applyTime,',',calcFee) from  ").append(TABLE_NAME)
				.append(" where tuanZhangId='").append(tuanZhangId).append("'")
				.append(" and applyTime is not null");
		try
		{
			return jdbcTemplate.queryForList(sql.toString(), String.class);
		}
		catch (Exception e)
		{
			logger.error("getTotalCanApplyFee error:" + e.getMessage());
			return null;
		}
	}

}
