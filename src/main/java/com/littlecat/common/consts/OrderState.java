package com.littlecat.common.consts;

/**
 * 订单状态（与消费者相关的订单状态）
 * 
 * @author amydady
 *
 */
public enum OrderState
{
	daifukuan("待付款"), // 待付款
	daichengtuan("待成团"), // 待成团
	daifahuo("待发货"), // 待发货
	daiqianshou("待签收"), // 待签收
	yishouhuo("已收货"), // 已收货
	tuikuanzhong("退款中"), // 退款中
	yituikuan("已退款"), // 已退款
	tuangoujiesantuikuan("团购解散退款"), // 团购解散退款
	yiquxiao("已取消");// 已取消

	private String displayName;

	OrderState(String displayName)
	{
		this.setDisplayName(displayName);
	}

	public String getDisplayName()
	{
		return displayName;
	}

	public void setDisplayName(String displayName)
	{
		this.displayName = displayName;
	}
}
