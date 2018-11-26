package com.littlecat.quanzi.dao;

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
import com.littlecat.cbb.utils.StringUtil;
import com.littlecat.cbb.utils.UUIDUtil;
import com.littlecat.common.consts.ErrorCode;
import com.littlecat.common.consts.TableName;
import com.littlecat.common.model.AddressMO;
import com.littlecat.common.utils.DaoUtil;
import com.littlecat.quanzi.model.TuanMO;
import com.littlecat.quanzi.model.TuanShenheReqInfo;

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

		String sql = "insert into " + TABLE_NAME + "(id,tuanZhangName,name,province,city,area,detailInfo,mobile,isDeliverySite) values(?,?,?,?,?,?,?,?,?)";

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
							mo.getMobile(),
							mo.getIsDeliverySite()
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

	public void modify(TuanMO mo) throws LittleCatException
	{
		String sql = "update " + TABLE_NAME + " set name=?,tuanZhangName=?,idCardImgDataFront=?,idCardImgDataBack=?,province=?,city=?,area=?,detailInfo=?,mobile=?,isDeliverySite=? where id = ?";

		try
		{
			jdbcTemplate.update(sql, new Object[] {
					mo.getName(),
					mo.getTuanZhangName(),
					mo.getIdCardImgDataFront(), mo.getIdCardImgDataBack(),
					mo.getAddressInfo().getProvince(),
					mo.getAddressInfo().getCity(),
					mo.getAddressInfo().getArea(),
					mo.getAddressInfo().getDetailInfo(),
					mo.getMobile(),
					mo.getIsDeliverySite(),
					mo.getId()
			});
		}
		catch (DataAccessException e)
		{
			throw new LittleCatException(ErrorCode.DataAccessException.getCode(), ErrorCode.DataAccessException.getMsg(), e);
		}
	}

	public void shenHe(TuanShenheReqInfo tuanShenheReqInfo) throws LittleCatException
	{
		String sql = "update " + TABLE_NAME + " set  approveTime=CURRENT_TIMESTAMP,approveRemark=?,enable=? where id = ?";

		try
		{
			jdbcTemplate.update(sql, new Object[] {
					tuanShenheReqInfo.getApproveRemark(),
					tuanShenheReqInfo.getEnable(),
					tuanShenheReqInfo.getId()
			});
		}
		catch (DataAccessException e)
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

	/**
	 * 是否为团长
	 * 
	 * @param id
	 * @return
	 */
	public boolean isTuanZhang(String id)
	{
		StringBuilder sql = new StringBuilder()
				.append("select count(1) ")
				.append(" from ").append(TABLE_NAME).append(" a ")
				.append(" where a.id = ? and a.enable = 'Y'");

		return jdbcTemplate.queryForObject(sql.toString(), new Object[] { id }, Integer.class) > 0;
	}

	/**
	 * 通过地市信息获取自提点
	 * 
	 * @param addressInfo
	 * @return
	 */
	public List<TuanMO> getDeliverySiteList(String terminalUserId, String province, String city, String area) throws LittleCatException
	{
		List<TuanMO> mos = new ArrayList<TuanMO>();

		StringBuilder sql = new StringBuilder()
				.append("select a.* ")
				.append(" from ").append(TABLE_NAME + " a ")
				.append(" where a.enable='Y' and a.isDeliverySite='Y' ");

		if (isTuanZhang(terminalUserId))
		{// 如果是团长，只能选择自己的站点地址
			sql.append(" and a.id='" + terminalUserId + "'");
		}
		else
		{
			sql.append(" and a.province='" + province + "' ")
					.append(" and a.city='" + city + "' ")
					.append(" and a.area='" + area + "' ");
		}

		sql.append(" order by a.detailInfo");

		try
		{
			mos.addAll(jdbcTemplate.query(sql.toString(), new RowMapper<TuanMO>()
			{
				@Override
				public TuanMO mapRow(ResultSet rs, int rowNum) throws SQLException
				{
					TuanMO mo = new TuanMO();

					mo.setId(rs.getString("id"));
					mo.setTuanZhangName(rs.getString("tuanZhangName"));
					mo.setName(rs.getString("name"));
					mo.setAddressInfo(new AddressMO(province, city, area, rs.getString("detailInfo")));
					mo.setMobile(rs.getString("mobile"));

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
