package com.littlecat.quanzi.dao;

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
import com.littlecat.cbb.utils.DateTimeUtil;
import com.littlecat.cbb.utils.ListUtil;
import com.littlecat.cbb.utils.StringUtil;
import com.littlecat.cbb.utils.UUIDUtil;
import com.littlecat.common.consts.ErrorCode;
import com.littlecat.common.consts.TableName;
import com.littlecat.common.utils.DaoUtil;
import com.littlecat.quanzi.model.TuanMO;

@Component
public class TuanDao
{
	private static final String FIELDS_NAME_TUANZHANGID = "tuanZhangId";

	@Autowired
	protected JdbcTemplate jdbcTemplate;

	private final String TABLE_NAME = TableName.Tuan.getName();
	private final String MODEL_NAME = "TuanMO";

	public TuanMO getByTuanZhangId(String tuanZhangId) throws LittleCatException
	{
		QueryParam queryParam = new QueryParam();
		QueryCondition condition = new QueryCondition();

		condition.getCondItems()
				.add(new ConditionItem(FIELDS_NAME_TUANZHANGID, tuanZhangId, ConditionOperatorType.equal));
		queryParam.setCondition(condition);

		return DaoUtil.getObject(TABLE_NAME, queryParam, jdbcTemplate, new TuanMO.MOMapper());
	}

	public boolean delete(String id) throws LittleCatException
	{
		return DaoUtil.delete(TABLE_NAME, id, jdbcTemplate);
	}

	public boolean delete(List<String> ids) throws LittleCatException
	{
		return DaoUtil.delete(TABLE_NAME, ids, jdbcTemplate);
	}

	public boolean enable(String id) throws LittleCatException
	{
		return DaoUtil.enable(TABLE_NAME, id, jdbcTemplate);
	}

	public boolean enable(List<String> ids) throws LittleCatException
	{
		return DaoUtil.enable(TABLE_NAME, ids, jdbcTemplate);
	}

	public boolean disable(String id) throws LittleCatException
	{
		return DaoUtil.disable(TABLE_NAME, id, jdbcTemplate);
	}

	public boolean disable(List<String> ids) throws LittleCatException
	{
		return DaoUtil.disable(TABLE_NAME, ids, jdbcTemplate);
	}

	public String add(TuanMO mo) throws LittleCatException
	{
		if (mo == null)
		{
			throw new LittleCatException(ErrorCode.GiveNullObjectToCreate.getCode(),
					ErrorCode.GiveNullObjectToCreate.getMsg().replace("{INFO_NAME}", MODEL_NAME));
		}

		if (StringUtil.isEmpty(mo.getId()))
		{
			mo.setId(UUIDUtil.createUUID());
		}

		if (StringUtil.isEmpty(mo.getCreateTime()))
		{
			mo.setCreateTime(String.valueOf(DateTimeUtil.getCurrentTime()));
		}

		String sql = "insert into " + TABLE_NAME
				+ "(id,tuanZhangId,name,remark,idCardType,idCardCode,idCardImgUrlFront,idCardImgUrlBack,provinceId,cityId,areaId,detailInfo,createTime,labels) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		try
		{
			int ret = jdbcTemplate.update(sql,
					new Object[] { mo.getId(), mo.getTuanZhangId(), mo.getName(), mo.getRemark(),
							mo.getIdCard().getType().name(), mo.getIdCard().getCode(), mo.getIdCard().getImgUrlFront(),
							mo.getIdCard().getImgUrlBack(), mo.getAddressInfo().getProvinceId(),
							mo.getAddressInfo().getCityId(), mo.getAddressInfo().getAreaId(),
							mo.getAddressInfo().getAreaId(), mo.getAddressInfo().getDetailInfo(), mo.getCreateTime(),
							ListUtil.join2String(mo.getLabels()) });

			if (ret != 1)
			{
				throw new LittleCatException(ErrorCode.InsertObjectToDBError.getCode(),
						ErrorCode.InsertObjectToDBError.getMsg().replace("{INFO_NAME}", MODEL_NAME));
			}
		}
		catch (DataAccessException e)
		{
			throw new LittleCatException(ErrorCode.DataAccessException.getCode(),
					ErrorCode.DataAccessException.getMsg(), e);
		}

		return mo.getId();
	}

	public boolean modify(TuanMO mo) throws LittleCatException
	{
		if (mo == null)
		{
			throw new LittleCatException(ErrorCode.GiveNullObjectToModify.getCode(),
					ErrorCode.GiveNullObjectToModify.getMsg().replace("{INFO_NAME}", MODEL_NAME));
		}

		String sql = "update " + TABLE_NAME
				+ " set tuanZhangId=?,name=?,remark=?,idCardType=?,idCardCode=?,idCardImgUrlFront=?,idCardImgUrlBack=?,provinceId=?,cityId=?,areaId=?,detailInfo=?,labels=? where id = ?";

		try
		{
			int ret = jdbcTemplate.update(sql, new Object[] { mo.getTuanZhangId(), mo.getName(), mo.getRemark(),
					mo.getIdCard().getType().name(), mo.getIdCard().getCode(), mo.getIdCard().getImgUrlFront(),
					mo.getIdCard().getImgUrlBack(), mo.getAddressInfo().getProvinceId(),
					mo.getAddressInfo().getCityId(), mo.getAddressInfo().getAreaId(),
					mo.getAddressInfo().getAreaId(), mo.getAddressInfo().getDetailInfo(),
					ListUtil.join2String(mo.getLabels()), mo.getId() });

			if (ret != 1)
			{
				throw new LittleCatException(ErrorCode.UpdateObjectToDBError.getCode(),
						ErrorCode.UpdateObjectToDBError.getMsg().replace("{INFO_NAME}", MODEL_NAME));
			}
		}
		catch (DataAccessException e)
		{
			throw new LittleCatException(ErrorCode.DataAccessException.getCode(),
					ErrorCode.DataAccessException.getMsg(), e);
		}

		return true;
	}

}