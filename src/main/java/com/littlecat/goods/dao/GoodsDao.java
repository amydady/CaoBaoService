package com.littlecat.goods.dao;

import java.io.UnsupportedEncodingException;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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

	/**
	 * 获取指定商品的概要信息
	 * 
	 * @param id
	 * @return
	 * @throws LittleCatException
	 */
	public GoodsMO getSummayInfoById(String id) throws LittleCatException
	{
		String sql = "select name,price,currentInventory from " + TABLE_NAME + " where id = ?";

		try
		{
			return jdbcTemplate.queryForObject(sql, new Object[] { id }, new RowMapper<GoodsMO>()
			{
				@Override
				public GoodsMO mapRow(ResultSet rs, int rowNum) throws SQLException
				{
					GoodsMO mo = new GoodsMO();
					mo.setName(rs.getString("name"));
					mo.setPrice(rs.getBigDecimal("price"));
					mo.setCurrentInventory(rs.getBigDecimal("currentInventory"));

					return mo;
				}
			});
		}
		catch (Exception e)
		{
			throw new LittleCatException(e.getMessage(), e);
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
			Blob mainImgData = null;

			if (StringUtil.isNotEmpty(mo.getMainImgData()))
			{
				mainImgData = new SerialBlob(mo.getMainImgData().getBytes(Consts.CHARSET_NAME));
			}

			int ret = jdbcTemplate.update(sql, new Object[] { mo.getClassifyId(), mo.getSupplierId(), mo.getName(), mo.getSummaryDescription(), mainImgData, mo.getPrice(), mo.getCurrentInventory(), mo.getDeliveryAreaId(), mo.getDeliveryFeeRuleId(), mo.getHasSecKillPlan(), mo.getHasGroupBuyPlan(), mo.getId() });

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

	/**
	 * 查询商品列表（微信小程序，展示普通商品）
	 * 
	 * @return
	 */
	public List<GoodsMO> getList4WxApp()
	{
		List<GoodsMO> mos = new ArrayList<GoodsMO>();

		String sql = new StringBuilder()
				.append("select a.id,a.name,a.summaryDescription,a.currentInventory,a.price,a.mainImgData")
				.append(" from ").append(TABLE_NAME).append(" a ")
				.append(" where a.enable='Y'")
				.append(" and a.hasSecKillPlan='N'")
				.append(" and a.hasGroupBuyPlan='N'")
				.toString();

		try
		{
			mos.addAll(jdbcTemplate.query(sql, new RowMapper<GoodsMO>()
			{

				@Override
				public GoodsMO mapRow(ResultSet rs, int rowNum) throws SQLException
				{
					GoodsMO mo = new GoodsMO();

					mo.setId(rs.getString("id"));
					mo.setName(rs.getString("name"));
					mo.setSummaryDescription(rs.getString("summaryDescription"));
					mo.setCurrentInventory(rs.getBigDecimal("currentInventory"));
					mo.setPrice(rs.getBigDecimal("price"));
					mo.setMainImgData(rs.getString("mainImgData"));

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

	/**
	 * 查询商品列表（web管理端）
	 * 
	 * @return
	 */
	public List<GoodsMO> getList4WebApp(String name,String enable)
	{
		List<GoodsMO> mos = new ArrayList<GoodsMO>();

		StringBuilder sql = new StringBuilder()
				.append("select a.id,a.name,a.summaryDescription,a.price,a.currentInventory,a.enable,date_format(a.createTime,'%Y-%m-%d %T') createTime")
				.append(" from ").append(TABLE_NAME).append(" a ")
				.append(" where 1=1 ");

		if (StringUtil.isNotEmpty(name))
		{
			sql.append(" and a.name like '%").append(name).append("%'");
		}
		
		if (StringUtil.isNotEmpty(enable))
		{
			sql.append(" and a.enable = '").append(enable).append("'");
		}


		sql.append(" order by a.enable desc,a.createTime asc");

		try
		{
			mos.addAll(jdbcTemplate.query(sql.toString(), new RowMapper<GoodsMO>()
			{

				@Override
				public GoodsMO mapRow(ResultSet rs, int rowNum) throws SQLException
				{
					GoodsMO mo = new GoodsMO();

					mo.setId(rs.getString("id"));
					mo.setName(rs.getString("name"));
					mo.setSummaryDescription(rs.getString("summaryDescription"));
					mo.setPrice(rs.getBigDecimal("price"));
					mo.setEnable(rs.getString("enable"));
					mo.setCreateTime(rs.getString("createTime"));
					mo.setCurrentInventory(rs.getBigDecimal("currentInventory"));

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
