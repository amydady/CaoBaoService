package com.littlecat.delivery.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.query.QueryParam;
import com.littlecat.cbb.utils.StringUtil;
import com.littlecat.cbb.utils.UUIDUtil;
import com.littlecat.common.consts.ErrorCode;
import com.littlecat.common.consts.TableName;
import com.littlecat.common.utils.DaoUtil;
import com.littlecat.delivery.model.DeliveryFeeCalcTypeMO;
import com.littlecat.delivery.model.DeliveryFeeRuleMO;

@Component
public class DeliveryFeeRuleDao
{
	@Autowired
	protected JdbcTemplate jdbcTemplate;

	private final String TABLE_NAME = TableName.DeliveryFeeRule.getName();
	private final String MODEL_NAME = DeliveryFeeRuleMO.class.getSimpleName();

	private final String TABLE_NAME_FEECALCTYPE = TableName.DeliveryFeeCalcType.getName();

	public DeliveryFeeRuleMO getById(String id) throws LittleCatException
	{
		return DaoUtil.getById(TABLE_NAME, id, jdbcTemplate, new DeliveryFeeRuleMO.MOMapper());
	}

	public void enable(String id) throws LittleCatException
	{
		DaoUtil.enable(TABLE_NAME, id, jdbcTemplate);
	}

	public void enable(List<String> ids) throws LittleCatException
	{
		DaoUtil.enable(TABLE_NAME, ids, jdbcTemplate);
	}

	public void disable(String id) throws LittleCatException
	{
		DaoUtil.disable(TABLE_NAME, id, jdbcTemplate);
	}

	public void disable(List<String> ids) throws LittleCatException
	{
		DaoUtil.disable(TABLE_NAME, ids, jdbcTemplate);
	}

	public String add(DeliveryFeeRuleMO mo) throws LittleCatException
	{
		if (mo == null)
		{
			throw new LittleCatException(ErrorCode.RequestObjectIsNull.getCode(), ErrorCode.RequestObjectIsNull.getMsg().replace("{INFO_NAME}", MODEL_NAME));
		}

		if (StringUtil.isEmpty(mo.getId()))
		{
			mo.setId(UUIDUtil.createUUID());
		}

		String sql = "insert into " + TABLE_NAME + "(id,name,deliveryAreaId,calcType,beginValue,endValue,fee) values(?,?,?,?,?,?,?)";

		try
		{
			int ret = jdbcTemplate.update(sql, new Object[] { mo.getId(), mo.getName(), mo.getDeliveryAreaId(), mo.getCalcType(), mo.getBeginValue(), mo.getEndValue(), mo.getFee() });

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

	public void modify(DeliveryFeeRuleMO mo) throws LittleCatException
	{
		if (mo == null)
		{
			throw new LittleCatException(ErrorCode.RequestObjectIsNull.getCode(), ErrorCode.RequestObjectIsNull.getMsg().replace("{INFO_NAME}", MODEL_NAME));
		}

		String sql = "update " + TABLE_NAME + " set name= ?,deliveryAreaId= ?,calcType= ?,beginValue= ?,endValue= ?,fee = ?,enable=? where id = ?";

		try
		{
			int ret = jdbcTemplate.update(sql, new Object[] { mo.getName(), mo.getDeliveryAreaId(), mo.getCalcType(), mo.getBeginValue(), mo.getEndValue(), mo.getFee(), mo.getEnable(), mo.getId() });

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

	public int getList(QueryParam queryParam, List<DeliveryFeeRuleMO> mos) throws LittleCatException
	{
		return DaoUtil.getList(TABLE_NAME, queryParam, mos, jdbcTemplate, new DeliveryFeeRuleMO.MOMapper());
	}

	/**
	 * 查询物流费用计算类型列表
	 * 
	 * @return
	 */
	public List<DeliveryFeeCalcTypeMO> getFeeCalcTypeList()
	{
		List<DeliveryFeeCalcTypeMO> mos = new ArrayList<DeliveryFeeCalcTypeMO>();

		String sql = "select * from " + TABLE_NAME_FEECALCTYPE;

		try
		{
			mos.addAll(jdbcTemplate.query(sql, new RowMapper<DeliveryFeeCalcTypeMO>()
			{
				@Override
				public DeliveryFeeCalcTypeMO mapRow(ResultSet rs, int rowNum) throws SQLException
				{
					DeliveryFeeCalcTypeMO mo = new DeliveryFeeCalcTypeMO();

					mo.setId(rs.getString("id"));
					mo.setName(rs.getString("name"));
					mo.setSortNum(rs.getString("sortNum"));

					return mo;
				}
			}));
		}
		catch (DataAccessException e)
		{
			throw new LittleCatException(ErrorCode.DataAccessException.getCode(), ErrorCode.DataAccessException.getMsg(), e);
		}

		return mos;
	}
}
