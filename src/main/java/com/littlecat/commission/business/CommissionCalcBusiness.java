package com.littlecat.commission.business;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.query.QueryParam;
import com.littlecat.cbb.utils.CollectionUtil;
import com.littlecat.cbb.utils.StringUtil;
import com.littlecat.commission.dao.CommissionCalcDao;
import com.littlecat.commission.model.CommissionCalcCreateReqInfo;
import com.littlecat.commission.model.CommissionCalcDetailMO;
import com.littlecat.commission.model.CommissionCalcMO;
import com.littlecat.order.business.OrderBusiness;
import com.littlecat.order.model.OrderDetailMO;
import com.littlecat.order.model.OrderMO;

@Component
@Transactional
public class CommissionCalcBusiness
{
	private static final Logger logger = LoggerFactory.getLogger(CommissionCalcBusiness.class);

	@Autowired
	private CommissionCalcDao commissionCalcDao;

	@Autowired
	private CommissionCalcDetailBusiness commissionCalcDetailBusiness;
	

	@Autowired
	private OrderBusiness orderBusiness;

	public CommissionCalcMO getById(String id) throws LittleCatException
	{
		return commissionCalcDao.getById(id);
	}

	public String create(CommissionCalcCreateReqInfo reqInfo) throws LittleCatException
	{
		if (reqInfo.getCalcInfo() == null)
		{
			throw new LittleCatException("CommissionCalcBusiness:create:calc info is null.");
		}

		List<CommissionCalcDetailMO> details = reqInfo.getDetailsInfo();

		if (CollectionUtil.isEmpty(details))
		{
			throw new LittleCatException("CommissionCalcBusiness:create:detail info is empty.");
		}

		String calcId = add(reqInfo.getCalcInfo());

		for (CommissionCalcDetailMO detail : details)
		{
			detail.setCalcId(calcId);
		}

		commissionCalcDetailBusiness.add(details);

		return calcId;
	}

	public String add(CommissionCalcMO mo) throws LittleCatException
	{
		return commissionCalcDao.add(mo);
	}

	public void modify(CommissionCalcMO mo) throws LittleCatException
	{
		commissionCalcDao.modify(mo);
	}
	
	/**
	 * 佣金计算
	 * @throws LittleCatException
	 */
	public void doCalc() throws LittleCatException
	{
		List<OrderMO> orderList = orderBusiness.getNeedCommissionCalcOrderList();
		
		if(CollectionUtil.isEmpty(orderList))
		{
			return;
		}
		
		for(OrderMO order:orderList)
		{
			List<OrderDetailMO> orderDetailList = order.getDetails();
			if(CollectionUtil.isEmpty(orderDetailList))
			{
				logger.error("CommissionCalcBusiness:doCalc:orderDetailList is empty,orderid={}",order.getId());
				continue;
			}
			
			if(StringUtil.isEmpty(order.getShareTuanZhangId()))
			{//非团长推荐
				
			}
			else
			{//团长推荐
				
			}
		}
		
		
	}

	public int getList(QueryParam queryParam, List<CommissionCalcMO> mos) throws LittleCatException
	{
		return commissionCalcDao.getList(queryParam, mos);
	}
}
