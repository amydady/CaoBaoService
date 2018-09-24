package com.littlecat.order.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.littlecat.cbb.common.BaseMO;
import com.littlecat.common.consts.OrderState;
import com.littlecat.common.model.AddressMO;

/**
 * 订单MO
 * 
 * @author amydady
 *
 */
public class OrderMO extends BaseMO
{
	private String terminalUserId;
	private String createTime;
	private int createYear;
	private int createMonth;
	private long fee;
	private OrderState state;
	private AddressMO deliveryAddress; // 发货地址

	public String getTerminalUserId()
	{
		return terminalUserId;
	}

	public void setTerminalUserId(String terminalUserId)
	{
		this.terminalUserId = terminalUserId;
	}

	public String getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(String createTime)
	{
		this.createTime = createTime;
	}

	public int getCreateYear()
	{
		return createYear;
	}

	public void setCreateYear(int createYear)
	{
		this.createYear = createYear;
	}

	public int getCreateMonth()
	{
		return createMonth;
	}

	public void setCreateMonth(int createMonth)
	{
		this.createMonth = createMonth;
	}

	public long getFee()
	{
		return fee;
	}

	public void setFee(long fee)
	{
		this.fee = fee;
	}

	public OrderState getState()
	{
		return state;
	}

	public void setState(OrderState state)
	{
		this.state = state;
	}

	public AddressMO getDeliveryAddress()
	{
		return deliveryAddress;
	}

	public void setDeliveryAddress(AddressMO deliveryAddress)
	{
		this.deliveryAddress = deliveryAddress;
	}
	
	public static class MOMapper implements RowMapper<OrderMO>
	{
		@Override
		public OrderMO mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			OrderMO mo = new OrderMO();
			
			mo.setId(rs.getString("id"));
			mo.setTerminalUserId(rs.getString("terminalUserId"));
			mo.setCreateTime(rs.getString("createTime"));
			mo.setCreateYear(rs.getInt("createYear"));
			mo.setCreateMonth(rs.getInt("createMonth"));
			mo.setFee(rs.getLong("fee"));
			mo.setState(OrderState.valueOf(rs.getString("state")));
			
			AddressMO deliveryaddress = new AddressMO(rs.getString("provinceId"), rs.getString("cityId"),
					rs.getString("areaId"), rs.getString("detailInfo"));
			
			mo.setDeliveryAddress(deliveryaddress);
			
			return mo;
		}
	}
	
}
