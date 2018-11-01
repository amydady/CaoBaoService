package com.littlecat.quanzi.dao;

import java.io.UnsupportedEncodingException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
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
import com.littlecat.quanzi.model.TuanMO;

@Component
public class TuanDao
{
	@Autowired
	protected JdbcTemplate jdbcTemplate;

	private final String TABLE_NAME = TableName.Tuan.getName();
	private final String MODEL_NAME = TuanMO.class.getSimpleName();

	public TuanMO getById(String tuanZhangOpenId) throws LittleCatException
	{
		try
		{
			return DaoUtil.getById(TABLE_NAME, tuanZhangOpenId, jdbcTemplate, new TuanMO.MOMapper());
		}
		catch (Exception e)
		{
			return null;
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

	public String add(TuanMO mo) throws LittleCatException
	{
		if (StringUtil.isEmpty(mo.getId()))
		{
			mo.setId(UUIDUtil.createUUID());
		}

		String sql = "insert into " + TABLE_NAME + "(id,tuanZhangName,name,province,city,area,detailInfo,mobile) values(?,?,?,?,?,?,?,?)";

		try
		{
			int ret = jdbcTemplate.update(sql,
					new Object[] {
							mo.getId(),
							mo.getTuanZhangName(),
							mo.getName(),
							mo.getAddressInfo().getProvince(),
							mo.getAddressInfo().getCity(),
							mo.getAddressInfo().getArea(),
							mo.getAddressInfo().getDetailInfo(),
							mo.getMobile()
					});

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

	public int getList(QueryParam queryParam, List<TuanMO> mos) throws LittleCatException
	{
		return DaoUtil.getList(TABLE_NAME, queryParam, mos, jdbcTemplate, new TuanMO.MOMapper());
	}

	public void modify(TuanMO mo) throws LittleCatException
	{
		String sql = "update " + TABLE_NAME + " set name=?,tuanZhangName=?,idCardImgDataFront=?,idCardImgDataBack=?,province=?,city=?,area=?,detailInfo=?,mobile=?,enable=?,approveTime=?,approveRemark=?,isDeliverySite=? where id = ?";

		try
		{
			Blob imgDataFront = null;
			if (StringUtil.isNotEmpty(mo.getIdCardImgDataFront()))
			{
				imgDataFront = new SerialBlob(mo.getIdCardImgDataFront().getBytes(Consts.CHARSET_NAME));
			}
			
			Blob imgDataBack = null;
			
			if (StringUtil.isNotEmpty(mo.getIdCardImgDataBack()))
			{
				imgDataBack = new SerialBlob(mo.getIdCardImgDataBack().getBytes(Consts.CHARSET_NAME));
			}
			
			int ret = jdbcTemplate.update(sql, new Object[] {
					mo.getName(),
					mo.getTuanZhangName(),
					imgDataFront, imgDataBack,
					mo.getAddressInfo().getProvince(),
					mo.getAddressInfo().getCity(),
					mo.getAddressInfo().getArea(),
					mo.getAddressInfo().getDetailInfo(),
					mo.getMobile(),
					mo.getEnable(),
					mo.getApproveTime(),
					mo.getApproveRemark(),
					mo.getIsDeliverySite(),
					mo.getId()
			});

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
	
	public List<TuanMO> getList(String enable, String name)
	{
		List<TuanMO> mos = new ArrayList<TuanMO>();
		
		StringBuilder sql = new StringBuilder()
				.append("select a.* ")
				.append(" from ").append(TABLE_NAME).append(" a ")
				.append(" where 1=1 ");

		
		if (StringUtil.isNotEmpty(enable))
		{
			sql.append(" and a.enable = '").append(enable).append("'");
		}
		
		if (StringUtil.isNotEmpty(name))
		{
			sql.append(" and (a.name like '%").append(name).append("%' or a.tuanZhangName like '").append(name).append("%')");
		}

		sql.append(" order by a.name,a.tuanZhangName");
		
		try
		{
			mos.addAll(jdbcTemplate.query(sql.toString(), new TuanMO.MOMapper()));
		}
		catch (DataAccessException e)
		{
			throw new LittleCatException(ErrorCode.DataAccessException.getCode(), ErrorCode.DataAccessException.getMsg(), e);
		}
		
		return mos;
	}
	
	public List<TuanMO> getDeliveryList(String name)
	{
		List<TuanMO> mos = new ArrayList<TuanMO>();
		
		StringBuilder sql = new StringBuilder()
				.append("select a.* ")
				.append(" from ").append(TABLE_NAME).append(" a ")
				.append(" where a.enable='Y' and a.isDeliverySite='Y' ");
		
		if (StringUtil.isNotEmpty(name))
		{
			sql.append(" and (a.name like '%").append(name).append("%' or a.tuanZhangName like '").append(name).append("%')");
		}

		sql.append(" order by a.name,a.tuanZhangName");
		
		try
		{
			mos.addAll(jdbcTemplate.query(sql.toString(), new TuanMO.MOMapper()));
		}
		catch (DataAccessException e)
		{
			throw new LittleCatException(ErrorCode.DataAccessException.getCode(), ErrorCode.DataAccessException.getMsg(), e);
		}
		
		return mos;
	}
}
