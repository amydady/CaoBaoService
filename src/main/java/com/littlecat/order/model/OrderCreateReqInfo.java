package com.littlecat.order.model;

import java.util.List;

/**
 * 订单创建的接口Req信息
 * 
 * @author amydady
 *
 */
public class OrderCreateReqInfo
{
	private OrderMO orderMO;
	private List<OrderDetailMO> orderDetailMOs;

	public OrderMO getOrderMO()
	{
		return orderMO;
	}

	public void setOrderMO(OrderMO orderMO)
	{
		this.orderMO = orderMO;
	}

	public List<OrderDetailMO> getOrderDetailMOs()
	{
		return orderDetailMOs;
	}

	public void setOrderDetailMOs(List<OrderDetailMO> orderDetailMOs)
	{
		this.orderDetailMOs = orderDetailMOs;
	}

}
