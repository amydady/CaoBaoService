package com.littlecat.commission.model;

import java.math.BigDecimal;
import java.util.List;


public class CommissionReport
{
	private BigDecimal totalPayedFee;
	private BigDecimal totalCanApplyFee;
	private List<String> applyHistory;

	public BigDecimal getTotalPayedFee()
	{
		return totalPayedFee;
	}

	public void setTotalPayedFee(BigDecimal totalPayedFee)
	{
		this.totalPayedFee = totalPayedFee;
	}

	public BigDecimal getTotalCanApplyFee()
	{
		return totalCanApplyFee;
	}

	public void setTotalCanApplyFee(BigDecimal totalCanApplyFee)
	{
		this.totalCanApplyFee = totalCanApplyFee;
	}

	public List<String> getApplyHistory()
	{
		return applyHistory;
	}

	public void setApplyHistory(List<String> applyHistory)
	{
		this.applyHistory = applyHistory;
	}

}
