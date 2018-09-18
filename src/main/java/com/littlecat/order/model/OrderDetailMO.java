package com.littlecat.order.model;

import com.littlecat.cbb.common.BaseMO;
import com.littlecat.common.consts.BuyType;

/**
 * 订单MO
 * 
 * @author amydady
 *
 */
public class OrderDetailMO extends BaseMO
{
	private String orderId;
	private String goodsId;
	private long price;
	private long goodsNum;
	private long fee;
	private BuyType buyType;
}
