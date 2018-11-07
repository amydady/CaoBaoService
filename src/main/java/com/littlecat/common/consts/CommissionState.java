package com.littlecat.common.consts;

/**
 * 佣金状态
 * 
 * @author amydady
 *
 */
public enum CommissionState
{
	calced, // 计算完毕，等待签收后X天中，冻结状态
	canapply, // 可申请
	applyed, // 已申请
	payed, // 已发放
	disabled// 已失效
}
