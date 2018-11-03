package com.littlecat.common.consts;

/**
 * 数据库表名
 * 
 * @author amydady
 *
 */
public enum TableName
{
	SysParam("t_sys_param"),
	Menu("t_sys_menu"),
	Action("t_sys_action"),
	Role("t_sys_role"),
	SysOperator("t_sys_sysoperator"),
	ResLock("t_sys_reslock"),
	
	TerminalUser("t_terminaluser"),
	DeliveryAddress("t_terminaluser_deliveryaddress"),
	
	Supplier("t_supplier"),
	
	DeliveryArea("t_delivery_area"),
	DeliveryFeeRule("t_delivery_feerule"),
	DeliveryFeeCalcType("t_delivery_feecalctype"),
	
	CommissionType("t_commission_type"),
	GoodsCommission("t_commission_goods"),
	CommissionCalc("t_commission_calc"),
	CommissionCalcDetail("t_commission_calcdetail"),
	

	
	Province("t_basicinfo_province"),
	City("t_basicinfo_city"),
	Area("t_basicinfo_area"),
	
	
	Tuan("t_quanzi_tuan"),
	TuanClassify("t_tuan_classify"),
	TuanGoods("t_quanzi_tuangoods"),
	TuanMember("t_quanzi_tuanmember"),

	HomeImgs("t_goods_homeimgs"),
	GoodsDetailImgs("t_goods_detailimgs"),
	Goods("t_goods"),
	GoodsClassify("t_goods_classify"),
	
	ShoppingCart("t_shoppingcart"),

	Order("t_order"),
	OrderDetail("t_order_detail"),
	
	SecKillPlan("t_seckill_seckillplan"),
	GroupBuyPlan("t_groupbuy_groupbuyplan"),
	GroupBuyTask("t_groupbuy_groupbuytask"),
	
	

	GoodsInventory("t_inventory_goods"),
	SecKillInventory("t_inventory_seckill"),
	GroupBuyInventory("t_inventory_groupbuy"),
	;
	
	private String name;
	
	TableName(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
	
}
