package com.littlecat.report.sales.model;

import java.math.BigDecimal;

public class OrderSummaryByDayRptMO
{
	private BigDecimal total_count;
	private BigDecimal payed_count;
	private BigDecimal payed_fee_sum;

	public BigDecimal getTotal_count()
	{
		return total_count;
	}

	public void setTotal_count(BigDecimal total_count)
	{
		this.total_count = total_count;
	}

	public BigDecimal getPayed_count()
	{
		return payed_count;
	}

	public void setPayed_count(BigDecimal payed_count)
	{
		this.payed_count = payed_count;
	}

	public BigDecimal getPayed_fee_sum()
	{
		return payed_fee_sum;
	}

	public void setPayed_fee_sum(BigDecimal payed_fee_sum)
	{
		this.payed_fee_sum = payed_fee_sum;
	}

}
