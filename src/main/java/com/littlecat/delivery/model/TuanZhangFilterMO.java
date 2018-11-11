package com.littlecat.delivery.model;

import java.math.BigDecimal;

import com.littlecat.cbb.common.BaseMO;

/**
 * 团长分拣单（用户签收单）MO
 * 
 * @author amydady
 *
 */
public class TuanZhangFilterMO extends BaseMO
{
	private String orderDate;
	private String tuanZhangId;//自提点团长

	private String orderId;
	private String terminalUserId;
	private String terminalUserMobile;//订单的用户收货地址信息中的手机号码
	private String goodsId;
	private BigDecimal goodsNum;
	private String receiveOperatorId;
	private String createTime;
	private String receiveTime;
	private String state;// 是否已签收，Y、N

	// just for view
	private String terminalUserName;
	private String receiveOperatorName;
	private String goodsName;

	public String getOrderDate()
	{
		return orderDate;
	}

	public void setOrderDate(String orderDate)
	{
		this.orderDate = orderDate;
	}

	public String getTuanZhangId()
	{
		return tuanZhangId;
	}

	public void setTuanZhangId(String tuanZhangId)
	{
		this.tuanZhangId = tuanZhangId;
	}

	public String getOrderId()
	{
		return orderId;
	}

	public void setOrderId(String orderId)
	{
		this.orderId = orderId;
	}

	public String getTerminalUserId()
	{
		return terminalUserId;
	}

	public void setTerminalUserId(String terminalUserId)
	{
		this.terminalUserId = terminalUserId;
	}

	public String getGoodsId()
	{
		return goodsId;
	}

	public void setGoodsId(String goodsId)
	{
		this.goodsId = goodsId;
	}

	public BigDecimal getGoodsNum()
	{
		return goodsNum;
	}

	public void setGoodsNum(BigDecimal goodsNum)
	{
		this.goodsNum = goodsNum;
	}

	public String getReceiveOperatorId()
	{
		return receiveOperatorId;
	}

	public void setReceiveOperatorId(String receiveOperatorId)
	{
		this.receiveOperatorId = receiveOperatorId;
	}

	public String getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(String createTime)
	{
		this.createTime = createTime;
	}

	public String getReceiveTime()
	{
		return receiveTime;
	}

	public void setReceiveTime(String receiveTime)
	{
		this.receiveTime = receiveTime;
	}

	public String getState()
	{
		return state;
	}

	public void setState(String state)
	{
		this.state = state;
	}

	public String getTerminalUserName()
	{
		return terminalUserName;
	}

	public void setTerminalUserName(String terminalUserName)
	{
		this.terminalUserName = terminalUserName;
	}

	public String getTerminalUserMobile()
	{
		return terminalUserMobile;
	}

	public void setTerminalUserMobile(String terminalUserMobile)
	{
		this.terminalUserMobile = terminalUserMobile;
	}

	public String getReceiveOperatorName()
	{
		return receiveOperatorName;
	}

	public void setReceiveOperatorName(String receiveOperatorName)
	{
		this.receiveOperatorName = receiveOperatorName;
	}

	public String getGoodsName()
	{
		return goodsName;
	}

	public void setGoodsName(String goodsName)
	{
		this.goodsName = goodsName;
	}

}
