package com.littlecat.commission.business;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.query.QueryParam;
import com.littlecat.cbb.utils.CollectionUtil;
import com.littlecat.cbb.utils.DateTimeUtil;
import com.littlecat.cbb.utils.StringUtil;
import com.littlecat.commission.dao.CommissionCalcDao;
import com.littlecat.commission.model.CommissionCalcMO;
import com.littlecat.common.consts.CommissionType;
import com.littlecat.order.business.OrderBusiness;
import com.littlecat.order.model.OrderDetailMO;
import com.littlecat.order.model.OrderMO;
import com.littlecat.quanzi.business.TuanMemberBusiness;
import com.littlecat.quanzi.model.TuanMemberMO;

@Component
@Transactional
public class CommissionCalcBusiness
{
	private static final Logger logger = LoggerFactory.getLogger(CommissionCalcBusiness.class);

	@Autowired
	private CommissionCalcDao commissionCalcDao;


	@Autowired
	private TuanMemberBusiness tuanMemberBusiness;

	@Autowired
	private OrderBusiness orderBusiness;
	
	@Autowired
	private CommissionTypeBusiness commissionTypeBusiness;

	public CommissionCalcMO getById(String id) throws LittleCatException
	{
		return commissionCalcDao.getById(id);
	}

	public void add(List<CommissionCalcMO> mos) throws LittleCatException
	{
		commissionCalcDao.add(mos);
	}

	public void modify(CommissionCalcMO mo) throws LittleCatException
	{
		commissionCalcDao.modify(mo);
	}

	/**
	 * 佣金计算
	 * 
	 * @throws LittleCatException
	 */
	public void doCalc() throws LittleCatException
	{
		List<OrderMO> orderList = orderBusiness.getNeedCommissionCalcOrderList();

		if (CollectionUtil.isEmpty(orderList))
		{
			return;
		}

		for (OrderMO order : orderList)
		{
			List<OrderDetailMO> orderDetailList = order.getDetails();
			if (CollectionUtil.isEmpty(orderDetailList))
			{
				logger.error("CommissionCalcBusiness:doCalc:orderDetailList is empty,orderid={}", order.getId());
				continue;
			}

			// 本单推荐团长
			String shareTuanZhangId = null;

			if (StringUtil.isNotEmpty(order.getShareTuanZhangId()))
			{// 本单有推荐团长
				shareTuanZhangId = order.getShareTuanZhangId();
			}
			else
			{// 本单没有推荐的团长
				// 查找该用户当前归属的团长
				TuanMemberMO tuanMemberMo = tuanMemberBusiness.getCurrentEnableTuan(order.getTerminalUserId());
				if (tuanMemberMo != null)
				{
					shareTuanZhangId = tuanMemberMo.getTuanId();
				}
			}

			// 自提点团长ID
			String deliverySiteTuanZhangId = order.getDeliveryTuanZhangId();

			// 计算佣金=========================
			List<CommissionCalcMO> commissionCalcMOList = new ArrayList<CommissionCalcMO>();
			
			commissionTypeBusiness.getById(CommissionType.share.name());
			
			for (OrderDetailMO orderDetail : orderDetailList)
			{
				if(StringUtil.isNotEmpty(shareTuanZhangId))
				{// 推广佣金
					CommissionCalcMO shareCommissionCalcMO = new CommissionCalcMO();
					
					shareCommissionCalcMO.setOrderId(order.getId());
					shareCommissionCalcMO.setTuanZhangId(shareTuanZhangId);
					shareCommissionCalcMO.setGoodsId(orderDetail.getGoodsId());
					shareCommissionCalcMO.setGoodsFee(orderDetail.getGoodsNum()*orderDetail.getPrice());
					shareCommissionCalcMO.setCommissionTypeId(CommissionType.share.name());
					shareCommissionCalcMO.setCalcFee(shareCommissionCalcMO.getGoodsFee()*1);
					
					commissionCalcMOList.add(shareCommissionCalcMO);
				}
				if(StringUtil.isNotEmpty(deliverySiteTuanZhangId))
				{// 自提点佣金
					CommissionCalcMO deliveryCommissionCalcMO = new CommissionCalcMO();
					
					deliveryCommissionCalcMO.setOrderId(order.getId());
					deliveryCommissionCalcMO.setTuanZhangId(shareTuanZhangId);
					deliveryCommissionCalcMO.setGoodsId(orderDetail.getGoodsId());
					deliveryCommissionCalcMO.setGoodsFee(orderDetail.getGoodsNum()*orderDetail.getPrice());
					deliveryCommissionCalcMO.setCommissionTypeId(CommissionType.deliverysite.name());
					deliveryCommissionCalcMO.setCalcFee(deliveryCommissionCalcMO.getGoodsFee()*1);
					
					commissionCalcMOList.add(deliveryCommissionCalcMO);
				}
			}
			
			//批量插入本订单的佣金信息
			commissionCalcDao.add(commissionCalcMOList);
			
			//设置订单的佣金计算时间（表示已经计算过佣金）
			orderBusiness.setCommissionCalcTime(order.getId());
		}

	}

	public int getList(QueryParam queryParam, List<CommissionCalcMO> mos) throws LittleCatException
	{
		return commissionCalcDao.getList(queryParam, mos);
	}
}
