package com.littlecat.commission.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 佣金结算创建请求信息
 * 
 * @author amydady
 *
 */
public class CommissionCalcCreateReqInfo
{
	private CommissionCalcMO calcInfo;
	private List<CommissionCalcDetailMO> detailsInfo = new ArrayList<CommissionCalcDetailMO>();

	public CommissionCalcMO getCalcInfo()
	{
		return calcInfo;
	}

	public void setCalcInfo(CommissionCalcMO calcInfo)
	{
		this.calcInfo = calcInfo;
	}

	public List<CommissionCalcDetailMO> getDetailsInfo()
	{
		return detailsInfo;
	}

	public void setDetailsInfo(List<CommissionCalcDetailMO> detailsInfo)
	{
		this.detailsInfo = detailsInfo;
	}

}
