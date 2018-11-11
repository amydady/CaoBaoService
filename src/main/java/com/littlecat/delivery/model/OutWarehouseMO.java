package com.littlecat.delivery.model;

import java.math.BigDecimal;

import com.littlecat.cbb.common.BaseMO;

public class OutWarehouseMO extends BaseMO
{
	private String orderDate;
	private String goodsId;
	private BigDecimal goodsNum;
	private String outOperatorId;
	private String createTime;
	private String outTime;

	// just for view
	private String outOperatorName;
	private String goodsName;

}
