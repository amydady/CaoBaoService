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
	miaoshaguihuakouchu, // 秒杀规划扣除
	miaoshaguihuatuihuan, // 秒杀规划退还
	miaoshaguihuachexiao, // 秒杀规划撤销
	tuangouguihuakouchu,// 团购规划扣除
	tuangouguihuatuihuan,// 团购规划退还
}
