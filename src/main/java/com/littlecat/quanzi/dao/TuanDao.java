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
	private final String MODEL_NAME = TuanMO.class.getSimpleName();

	public TuanMO getByTuanZhangId(String tuanZhangId) throws LittleCatException
	{
		QueryParam queryParam = new QueryParam();
		QueryCondition condition = new QueryCondition();

		condition.getCondItems()
				.add(new ConditionItem(FIELDS_NAME_TUANZHANGID, tuanZhangId, ConditionOperatorType.equal));
		queryParam.setCondition(condition);

		return DaoUtil.getObject(TABLE_NAME, queryParam, jdbcTemplate, new TuanMO.MOMapper());
	}

	public void delete(String id) throws LittleCatException
	{
		DaoUtil.delete(TABLE_NAME, id, jdbcTemplate);
	}

	public void delete(List<String> ids) throws LittleCatException
	{
		DaoUtil.delete(TABLE_NAME, ids, jdbcTemplate);
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

	public String add(TuanMO mo) throws LittleCatException
	{
		if (StringUtil.isEmpty(mo.getId()))
		{
			mo.setId(UUIDUtil.createUUID());
		}

		String sql = "insert into " + TABLE_NAME + "(id,tuanZhangId,name,remark,idCardType,idCardCode,idCardImgUrlFront,idCardImgUrlBack,provinceId,cityId,areaId,detailInfo,labels) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";

		try
		{
			int ret = jdbcTemplate.update(sql,
					new Object[] { mo.getId(), mo.getTuanZhangId(), mo.getName(), mo.getRemark(),
							mo.getIdCard().getType().name(), mo.getIdCard().getCode(), mo.getIdCard().getImgUrlFront(),
							mo.getIdCard().getImgUrlBack(), mo.getAddressInfo().getProvinceId(),
							mo.getAddressInfo().getCityId(), mo.getAddressInfo().getAreaId(),
							mo.getAddressInfo().getAreaId(), mo.getAddressInfo().getDetailInfo(),
							ListUtil.join2String(mo.getLabels()) });

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

	public void modify(TuanMO mo) throws LittleCatException
	{
		String sql = "update " + TABLE_NAME + " set tuanZhangId=?,name=?,remark=?,idCardType=?,idCardCode=?,idCardImgUrlFront=?,idCardImgUrlBack=?,provinceId=?,cityId=?,areaId=?,detailInfo=?,labels=? where id = ?";

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
				throw new LittleCatException(ErrorCode.UpdateObjectToDBError.getCode(), ErrorCode.UpdateObjectToDBError.getMsg().replace("{INFO_NAME}", MODEL_NAME));
			}
		}
		catch (DataAccessException e)
		{
			throw new LittleCatException(ErrorCode.DataAccessException.getCode(), ErrorCode.DataAccessException.getMsg(), e);
		}
	}

}
