package com.littlecat.consts;

/**
 * 数据库表名
 * @author amydady
 *
 */
public enum TableName
{
	SysOperator("t_sysoperator"),
	TerminalUser("t_terminaluser"),
	TuanZhang("t_tuanzhang"),
	TuanClassify("t_tuanclassify"),
	Tuan("t_tuan"),
	
	MaiShou("t_maishou"),
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
