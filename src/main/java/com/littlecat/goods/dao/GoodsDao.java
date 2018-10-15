package com.littlecat.goods.dao;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.littlecat.cbb.common.Consts;
import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.query.QueryParam;
import com.littlecat.cbb.utils.StringUtil;
import com.littlecat.cbb.utils.UUIDUtil;
import com.littlecat.common.consts.ErrorCode;
import com.littlecat.common.consts.TableName;
import com.littlecat.common.utils.DaoUtil;
import com.littlecat.goods.model.GoodsMO;

@Component
public class GoodsDao
{
	@Autowired
	protected JdbcTemplate jdbcTemplate;

	private final String TABLE_NAME = TableName.Goods.getName();
	private final String MODEL_NAME = GoodsMO.class.getSimpleName();

	public GoodsMO getById(String id) throws LittleCatException
	{
		return DaoUtil.getById(TABLE_NAME, id, jdbcTemplate, new GoodsMO.MOMapper());
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

	public String add(GoodsMO mo) throws LittleCatException
	{
		if (mo == null)
		{
			throw new LittleCatException(ErrorCode.RequestObjectIsNull.getCode(), ErrorCode.RequestObjectIsNull.getMsg().replace("{INFO_NAME}", MODEL_NAME));
		}

		if (StringUtil.isEmpty(mo.getId()))
		{
			mo.setId(UUIDUtil.createUUID());
		}

		String sql = "insert into " + TABLE_NAME + "(id,classifyId,supplierId,name,summaryDescription,price,createOperatorId,deliveryAreaId,deliveryFeeRuleId) values(?,?,?,?,?,?,?,?,?)";

		try
		{
			int ret = jdbcTemplate.update(sql, new Object[] { mo.getId(), mo.getClassifyId(), mo.getSupplierId(), mo.getName(), mo.getSummaryDescription(), mo.getPrice(), mo.getCreateOperatorId(), mo.getDeliveryAreaId(), mo.getDeliveryFeeRuleId() });

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

	public void modify(GoodsMO mo) throws LittleCatException
	{
		if (mo == null)
		{
			throw new LittleCatException(ErrorCode.RequestObjectIsNull.getCode(), ErrorCode.RequestObjectIsNull.getMsg().replace("{INFO_NAME}", MODEL_NAME));
		}

		String sql = "update " + TABLE_NAME + " set classifyId=?,supplierId=?,name=?,summaryDescription=?,mainImgData=?,price=?,currentInventory = ?,deliveryAreaId=?,deliveryFeeRuleId=?,hasSecKillPlan=?,hasGroupBuyPlan=? where id = ?";

		try
		{
			int ret = jdbcTemplate.update(sql, new Object[] { mo.getClassifyId(), mo.getSupplierId(), mo.getName(), mo.getSummaryDescription(), new SerialBlob(mo.getMainImgData().getBytes(Consts.CHARSET_NAME)), mo.getPrice(), mo.getCurrentInventory(), mo.getDeliveryAreaId(), mo.getDeliveryFeeRuleId(), mo.getHasSecKillPlan(), mo.getHasGroupBuyPlan(), mo.getId() });

			if (ret != 1)
			{
				throw new LittleCatException(ErrorCode.UpdateObjectToDBError.getCode(), ErrorCode.UpdateObjectToDBError.getMsg().replace("{INFO_NAME}", MODEL_NAME));
			}
		}
		catch (DataAccessException | UnsupportedEncodingException | SQLException e)
		{
			throw new LittleCatException(ErrorCode.DataAccessException.getCode(), ErrorCode.DataAccessException.getMsg(), e);
		}
	}

	public int getList(QueryParam queryParam, List<GoodsMO> mos) throws LittleCatException
	{
		return DaoUtil.getList(TABLE_NAME, queryParam, mos, jdbcTemplate, new GoodsMO.MOMapper());
	}
}
