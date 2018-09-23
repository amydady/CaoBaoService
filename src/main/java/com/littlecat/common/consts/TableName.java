package com.littlecat.common.consts;

/**
 * 数据库表名
 * @author amydady
 *
 */
public enum TableName
{
	Menu("t_menu"),
	Action("t_action"),
	Role("t_role"),
	
	SysOperator("t_sysoperator"),
	TerminalUser("t_terminaluser"),
	TuanZhang("t_tuanzhang"),
	TuanClassify("t_tuanclassify"),
	
	MaiShou("t_maishou"),
	
	Supplier("t_supplier"),
	
	Province("t_basicinfo_province"),
	City("t_basicinfo_city"),
	Area("t_basicinfo_area"),
	
	Tuan("t_quanzi_tuan"),
	TuanGoods("t_quanzi_tuangoods"),
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
