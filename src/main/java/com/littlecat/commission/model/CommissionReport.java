package com.littlecat.commission.model;

import java.util.List;


public class CommissionReport
{
	private long totalPayedFee;
	private long totalCanApplyFee;
	private List<String> applyHistory;

	public long getTotalPayedFee()
	{
		return totalPayedFee;
	}

	public void setTotalPayedFee(long totalPayedFee)
	{
		this.totalPayedFee = totalPayedFee;
	}

	public long getTotalCanApplyFee()
	{
		return totalCanApplyFee;
	}

	public void setTotalCanApplyFee(long totalCanApplyFee)
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
