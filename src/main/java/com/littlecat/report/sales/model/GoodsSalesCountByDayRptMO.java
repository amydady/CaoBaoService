package com.littlecat.report.sales.model;

import java.math.BigDecimal;
import java.util.List;

/**
 * 商品销售数量报表（日，TOP3商品）
 * 
 * @author amydady
 *
 */
public class GoodsSalesCountByDayRptMO
{
	private List<String> goodsNames;
	private List<BigDecimal> saleCounts;

	public List<String> getGoodsNames()
	{
		return goodsNames;
	}

	public void setGoodsNames(List<String> goodsNames)
	{
		this.goodsNames = goodsNames;
	}

	public List<BigDecimal> getSaleCounts()
	{
		return saleCounts;
	}

	public void setSaleCounts(List<BigDecimal> saleCounts)
	{
		this.saleCounts = saleCounts;
	}

}
