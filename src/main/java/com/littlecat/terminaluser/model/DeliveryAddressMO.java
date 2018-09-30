package com.littlecat.terminaluser.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.littlecat.cbb.common.BaseMO;
import com.littlecat.common.model.AddressMO;

public class DeliveryAddressMO extends BaseMO
{
	private String terminalUserId;
	private String name;
	private AddressMO addressInfo;
	private String isDefault;

	public DeliveryAddressMO()
	{
		
	}

	public String getTerminalUserId()
	{
		return terminalUserId;
	}

	public void setTerminalUserId(String terminalUserId)
	{
		this.terminalUserId = terminalUserId;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getIsDefault()
	{
		return isDefault;
	}

	public void setIsDefault(String isDefault)
	{
		this.isDefault = isDefault;
	}

	public AddressMO getAddressInfo()
	{
		return addressInfo;
	}

	public void setAddressInfo(AddressMO addressInfo)
	{
		this.addressInfo = addressInfo;
	}

	public static class MOMapper implements RowMapper<DeliveryAddressMO>
	{
		@Override
		public DeliveryAddressMO mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			DeliveryAddressMO mo = new DeliveryAddressMO();

			mo.setId(rs.getString("id"));
			mo.setTerminalUserId(rs.getString("terminalUserId"));
			mo.setName(rs.getString("name"));
			mo.setAddressInfo(new AddressMO(rs.getString("provinceId"), rs.getString("cityId"), rs.getString("areaId"), rs.getString("detailInfo")));
			mo.setIsDefault(rs.getString("isDefault"));

			return mo;
		}
	}
}
