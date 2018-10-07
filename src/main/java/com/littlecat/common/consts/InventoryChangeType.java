package com.littlecat.common.consts;

/**
 * 库存变更类型
 * 
 * @author amydady
 *
 */
public enum InventoryChangeType
{
	rengongzengjia, // 人工增加
	rengongjianshao, // 人工减少
	dingdankoujian, // 订单扣减
	tuihuozengjia, // 退货增加
	miaoshaguihuakouchu, // 秒杀规划扣除
	tuangouguihuakouchu,// 团购规划扣除
}
