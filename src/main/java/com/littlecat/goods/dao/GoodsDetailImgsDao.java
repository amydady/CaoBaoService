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
import com.littlecat.goods.model.GoodsDetailImgsMO;

@Component
public class GoodsDetailImgsDao
{
	@Autowired
	protected JdbcTemplate jdbcTemplate;

	private final String TABLE_NAME = TableName.GoodsDetailImgs.getName();
	private final String MODEL_NAME = GoodsDetailImgsMO.class.getSimpleName();

	public GoodsDetailImgsMO getById(String id) throws LittleCatException
	{
		return DaoUtil.getById(TABLE_NAME, id, jdbcTemplate, new GoodsDetailImgsMO.MOMapper());
	}

	public void delete(String id) throws LittleCatException
	{
		DaoUtil.delete(TABLE_NAME, id, jdbcTemplate);
	}

	public void delete(List<String> ids) throws LittleCatException
	{
		DaoUtil.delete(TABLE_NAME, ids, jdbcTemplate);
	}

	public String add(GoodsDetailImgsMO mo) throws LittleCatException
	{
		if (mo == null)
		{
			throw new LittleCatException(ErrorCode.RequestObjectIsNull.getCode(), ErrorCode.RequestObjectIsNull.getMsg().replace("{INFO_NAME}", MODEL_NAME));
		}

		if (StringUtil.isEmpty(mo.getId()))
		{
			mo.setId(UUIDUtil.createUUID());
		}

		String sql = "insert into " + TABLE_NAME + "(id,goodsId,title,sortNum,imgData) values(?,?,?,?,?,?)";

		try
		{
			int ret = jdbcTemplate.update(sql, new Object[] { mo.getId(), mo.getGoodsId(), mo.getTitle(), mo.getSortNum(), new SerialBlob(mo.getImgData().getBytes(Consts.CHARSET_NAME)) });

			if (ret != 1)
			{
				throw new LittleCatException(ErrorCode.InsertObjectToDBError.getCode(), ErrorCode.InsertObjectToDBError.getMsg().replace("{INFO_NAME}", MODEL_NAME));
			}
		}
		catch (DataAccessException | UnsupportedEncodingException | SQLException e)
		{
			throw new LittleCatException(ErrorCode.DataAccessException.getCode(), ErrorCode.DataAccessException.getMsg(), e);
		}

		return mo.getId();
	}

	public void modify(GoodsDetailImgsMO mo) throws LittleCatException
	{
		if (mo == null)
		{
			throw new LittleCatException(ErrorCode.RequestObjectIsNull.getCode(), ErrorCode.RequestObjectIsNull.getMsg().replace("{INFO_NAME}", MODEL_NAME));
		}

		String sql = "update " + TABLE_NAME + " set title=?,sortNum=? where id = ?";

		try
		{
			int ret = jdbcTemplate.update(sql, new Object[] { mo.getTitle(), mo.getSortNum(), mo.getId() });

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

	public int getList(QueryParam queryParam, List<GoodsDetailImgsMO> mos) throws LittleCatException
	{
		return DaoUtil.getList(TABLE_NAME, queryParam, mos, jdbcTemplate, new GoodsDetailImgsMO.MOMapper());
	}
}
