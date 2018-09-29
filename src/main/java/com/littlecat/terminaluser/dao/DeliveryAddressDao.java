package com.littlecat.terminaluser.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.query.QueryParam;
import com.littlecat.cbb.utils.StringUtil;
import com.littlecat.cbb.utils.UUIDUtil;
import com.littlecat.common.consts.BooleanTag;
import com.littlecat.common.consts.ErrorCode;
import com.littlecat.common.consts.TableName;
import com.littlecat.common.utils.DaoUtil;
import com.littlecat.terminaluser.model.DeliveryAddressMO;

@Component
public class DeliveryAddressDao
{
	@Autowired
	protected JdbcTemplate jdbcTemplate;

	private static final String TABLE_NAME = TableName.DeliveryAddress.getName();
	private static final String MODEL_NAME = "DeliveryAddressMO";

	public DeliveryAddressMO getById(String id) throws LittleCatException
	{
		return DaoUtil.getById(TABLE_NAME, id, jdbcTemplate, new DeliveryAddressMO.MOMapper());
	}

	public String add(DeliveryAddressMO mo) throws LittleCatException
	{
		if (mo == null)
		{
			throw new LittleCatException(ErrorCode.GiveNullObjectToCreate.getCode(), ErrorCode.GiveNullObjectToCreate.getMsg().replace("{INFO_NAME}", MODEL_NAME));
		}

		if (StringUtil.isEmpty(mo.getId()))
		{
			mo.setId(UUIDUtil.createUUID());
		}

		String sql = "insert into " + TABLE_NAME + "(id,terminalUserId,name,provinceId,cityId,areaId,detailInfo,isDefault) values(?,?,?,?,?,?,?,?)";

		try
		{
			int ret = jdbcTemplate.update(sql, new Object[] { mo.getId(), mo.getTerminalUserId(), mo.getName(), mo.getAddressInfo().getProvinceId(), mo.getAddressInfo().getCityId(), mo.getAddressInfo().getAreaId(), mo.getAddressInfo().getDetailInfo(), mo.getIsDefault() });

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

	public void modify(DeliveryAddressMO mo) throws LittleCatException
	{
		if (mo == null)
		{
			throw new LittleCatException(ErrorCode.GiveNullObjectToModify.getCode(), ErrorCode.GiveNullObjectToModify.getMsg().replace("{INFO_NAME}", TABLE_NAME));
		}

		String sql = "update " + TABLE_NAME + " set name = ?,provinceId = ?,cityId = ?,areaId = ?,detailInfo = ?,isDefault = ? where id = ?";

		try
		{
			int ret = jdbcTemplate.update(sql, new Object[] { mo.getName(), mo.getAddressInfo().getProvinceId(), mo.getAddressInfo().getCityId(), mo.getAddressInfo().getAreaId(), mo.getAddressInfo().getDetailInfo(), mo.getIsDefault(), mo.getId() });

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

	public void setDefaultYes(String id) throws LittleCatException
	{
		if (StringUtil.isEmpty(id))
		{
			throw new LittleCatException(ErrorCode.UpdateObjectWithEmptyId.getCode(), ErrorCode.UpdateObjectWithEmptyId.getMsg().replace("{INFO_NAME}", TABLE_NAME));
		}

		String sql = "update " + TABLE_NAME + " set isDefault = ? where id = ?";

		try
		{
			int ret = jdbcTemplate.update(sql, new Object[] { BooleanTag.Y.name(), id });

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

	/**
	 * 将用户的所有地址信息都设置为非默认地址
	 * 
	 * @param terminalUserId
	 * @throws LittleCatException
	 */
	public void setDefaultNo(String terminalUserId) throws LittleCatException
	{
		if (StringUtil.isEmpty(terminalUserId))
		{
			throw new LittleCatException(ErrorCode.UpdateObjectWithEmptyId.getCode(), ErrorCode.UpdateObjectWithEmptyId.getMsg().replace("{INFO_NAME}", TABLE_NAME));
		}

		String sql = "update " + TABLE_NAME + " set isDefault = ? where terminalUserId = ? and isDefault = 'Y'";

		try
		{
			jdbcTemplate.update(sql, new Object[] { BooleanTag.N.name(), terminalUserId });
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

	public void delete(List<String> ids) throws LittleCatException
	{
		DaoUtil.delete(TABLE_NAME, ids, jdbcTemplate);
	}

	public int getList(QueryParam queryParam, List<DeliveryAddressMO> mos) throws LittleCatException
	{
		return DaoUtil.getList(TABLE_NAME, queryParam, mos, jdbcTemplate, new DeliveryAddressMO.MOMapper());
	}
}
