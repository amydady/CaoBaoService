package com.littlecat.common.consts;

/**
 * 数据库表名
 * 
 * @author amydady
 *
 */
public enum TableName
{
	Menu("t_sys_menu"),
	Action("t_sys_action"),
	Role("t_sys_role"),
	SysOperator("t_sys_sysoperator"),
	
	TerminalUser("t_terminaluser"),
	
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

	Order("t_order"),
	OrderDetail("t_order_detail"),
	OrderTrail("t_order_trail"),
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
