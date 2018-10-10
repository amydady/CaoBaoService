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
	
	Province("t_basicinfo_province"),
	City("t_basicinfo_city"),
	Area("t_basicinfo_area"),
	
	
	Tuan("t_quanzi_tuan"),
	TuanClassify("t_tuan_classify"),
	TuanGoods("t_quanzi_tuangoods"),
	TuanMember("t_quanzi_tuanmember"),
	
	Goods("t_goods"),
	GoodsClassify("t_goods_classify"),
	
	ShoppingCart("t_shoppingcart"),

	Order("t_order"),
	OrderDetail("t_order_detail"),
	
	SecKillPlan("t_seckill_seckillplan"),
	GroupBuyPlan("t_groupbuy_groupbuyplan"),
	
	

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
