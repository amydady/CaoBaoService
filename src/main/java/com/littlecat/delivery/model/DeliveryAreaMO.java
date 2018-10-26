package com.littlecat.delivery.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.littlecat.cbb.common.BaseMO;
import com.littlecat.cbb.utils.StringUtil;

/**
 * 配送区域MO
 * 
 * @author amydady
 *
 */
public class DeliveryAreaMO extends BaseMO
{
	private String name;
	private String cityInfo;
	private String createTime;
	private String enable;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(String createTime)
	{
		this.createTime = createTime;
	}

	public String getEnable()
	{
		return enable;
	}

	public void setEnable(String enable)
	{
		this.enable = enable;
	}

	public String getCityInfo()
	{
		return cityInfo;
	}

	public void setCityInfo(String cityInfo)
	{
		this.cityInfo = cityInfo;
	}

	public static class MOMapper implements RowMapper<DeliveryAreaMO>
	{
		@Override
		public DeliveryAreaMO mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			DeliveryAreaMO mo = new DeliveryAreaMO();

			mo.setId(rs.getString("id"));
			mo.setName(rs.getString("name"));
			mo.setCityInfo(rs.getString("cityInfo"));
			mo.setCreateTime(StringUtil.replace(rs.getString("createTime"), ".0", ""));
			mo.setEnable(rs.getString("enable"));

			return mo;
		}
	}
}
