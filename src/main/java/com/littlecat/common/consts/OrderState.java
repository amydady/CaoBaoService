package com.littlecat.common.consts;

/**
 * 订单状态（与消费者相关的订单状态）
 * 
 * @author amydady
 *
 */
public enum OrderState
{
	daifukuan, // 待付款
	daichengtuan, // 待成团
	daiqianshou, // 待签收
	yishouhuo, // 已收货
	tuikuanzhong, // 退款中
	yituikuan// 已退款
}
