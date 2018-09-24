package com.littlecat.order.model;

import com.littlecat.cbb.common.BaseMO;
import com.littlecat.common.consts.OrderEventType;

/**
 * 订单轨迹MO
 * 
 * @author amydady
 *
 */
public class OrderTrailMO extends BaseMO
{
	private String orderId;
	private String time;
	private OrderEventType eventType;

	public String getOrderId()
	{
		return orderId;
	}

	public void setOrderId(String orderId)
	{
		this.orderId = orderId;
	}

	public String getTime()
	{
		return time;
	}

	public void setTime(String time)
	{
		this.time = time;
	}

	public OrderEventType getEventType()
	{
		return eventType;
	}

	public void setEventType(OrderEventType eventType)
	{
		this.eventType = eventType;
	}

}
