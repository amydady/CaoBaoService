package com.littlecat.order.model;

/**
 * 订单退款相关操作的请求MO
 * 
 * @author amydady
 *
 */
public class OrderReturnReqInfo
{
	// 订单ID
	private String id;
	
	// 退款申请、撤销申请、完成退款操作的备注信息
	private String remark;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getRemark()
	{
		return remark;
	}

	public void setRemark(String remark)
	{
		this.remark = remark;
	}

}
