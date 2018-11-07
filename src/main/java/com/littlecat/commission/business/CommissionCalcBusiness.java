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
import com.littlecat.commission.model.CommissionGoodsMO;
import com.littlecat.commission.model.CommissionTypeMO;
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

	@Autowired
	private CommissionGoodsBusiness commissionGoodsBusiness;

	public CommissionCalcMO getById(String id) throws LittleCatException
	{
		return commissionCalcDao.getById(id);
	}

	public void add(List<CommissionCalcMO> mos) throws LittleCatException
	{
		commissionCalcDao.add(mos);
	}

	public void modify(List<CommissionCalcMO> mos) throws LittleCatException
	{
		commissionCalcDao.modify(mos);
	}

	public void modify(CommissionCalcMO mo) throws LittleCatException
	{
		commissionCalcDao.modify(mo);
	}

	/**
	 * 佣金计算 订单支付后开始计算，计算后佣金状态为冻结，待用户签收X天后置为可发放状态；
	 * 
	 * @throws LittleCatException
	 */
	public void doCalc() throws LittleCatException
	{
		doCalc(orderBusiness.getNeedCommissionCalcOrderList());
	}
	
	public void doCalc(OrderMO order) throws LittleCatException
	{
		List<OrderMO> orderList = new ArrayList<OrderMO>();
		orderList.add(order);
		doCalc(orderList);
	}

	public void doCalc(List<OrderMO> orderList) throws LittleCatException
	{
		if (CollectionUtil.isEmpty(orderList))
		{
			return;
		}
		
		CommissionTypeMO commissionTypeShare = commissionTypeBusiness.getById(CommissionType.share.name());
		if (commissionTypeShare == null || commissionTypeShare.getCommissionRate() == null)
		{
			logger.error("CommissionCalcBusiness:doCalc:get commissionTypeShare error.");
			return;
		}

		CommissionTypeMO commissionTypeDelivery = commissionTypeBusiness.getById(CommissionType.deliverysite.name());
		if (commissionTypeDelivery == null || commissionTypeDelivery.getCommissionRate() == null)
		{
			logger.error("CommissionCalcBusiness:doCalc:get commissionTypeDelivery error.");
			return;
		}
		
		for (OrderMO order : orderList)
		{
			List<OrderDetailMO> orderDetailList = order.getDetails();
			if (CollectionUtil.isEmpty(orderDetailList))
			{
				logger.error("CommissionCalcBusiness:doCalc:orderDetailList is empty,orderid={}", order.getId());
				return;
			}

			// 自提点团长ID
			String deliverySiteTuanZhangId = order.getDeliveryTuanZhangId();

			// 计算佣金=========================
			List<CommissionCalcMO> commissionCalcMOList = new ArrayList<CommissionCalcMO>();

			// 本商品推荐团长
			String shareTuanZhangId = null;

			if (StringUtil.isNotEmpty(order.getShareTuanZhangId()))
			{// 本商品有推荐团长
				shareTuanZhangId = order.getShareTuanZhangId();
			}
			else
			{// 本商品没有推荐的团长
				// 查找该用户当前归属的团长
				TuanMemberMO tuanMemberMo = tuanMemberBusiness.getCurrentEnableTuan(order.getTerminalUserId());
				if (tuanMemberMo != null)
				{
					shareTuanZhangId = tuanMemberMo.getTuanId();
				}
			}

			for (OrderDetailMO orderDetail : orderDetailList)
			{
				List<CommissionGoodsMO> commissionGoodsMOList = commissionGoodsBusiness.getListByGoodsId(orderDetail.getGoodsId());

				double commissionRateShare = commissionTypeShare.getCommissionRate().doubleValue();
				double commissionRateDelivery = commissionTypeDelivery.getCommissionRate().doubleValue();

				if (CollectionUtil.isNotEmpty(commissionGoodsMOList))
				{
					for (CommissionGoodsMO commissionGoodsMO : commissionGoodsMOList)
					{
						if (commissionGoodsMO.getCommissionTypeId() == CommissionType.share.name())
						{
							commissionRateShare = commissionGoodsMO.getCommissionRate().doubleValue();
						}

						if (commissionGoodsMO.getCommissionTypeId() == CommissionType.deliverysite.name())
						{
							commissionRateDelivery = commissionGoodsMO.getCommissionRate().doubleValue();
						}
					}
				}

				if (StringUtil.isNotEmpty(shareTuanZhangId))
				{// 推广佣金

					CommissionCalcMO shareCommissionCalcMO = new CommissionCalcMO();

					shareCommissionCalcMO.setOrderId(order.getId());
					shareCommissionCalcMO.setTuanZhangId(shareTuanZhangId);
					shareCommissionCalcMO.setGoodsId(orderDetail.getGoodsId());
					shareCommissionCalcMO.setGoodsFee(orderDetail.getGoodsNum() * orderDetail.getPrice());
					shareCommissionCalcMO.setCommissionTypeId(CommissionType.share.name());
					shareCommissionCalcMO.setCalcFee((long) (shareCommissionCalcMO.getGoodsFee() * commissionRateShare / 100));

					commissionCalcMOList.add(shareCommissionCalcMO);
				}

				if (StringUtil.isNotEmpty(deliverySiteTuanZhangId))
				{// 自提点佣金
					CommissionCalcMO deliveryCommissionCalcMO = new CommissionCalcMO();

					deliveryCommissionCalcMO.setOrderId(order.getId());
					deliveryCommissionCalcMO.setTuanZhangId(deliverySiteTuanZhangId);
					deliveryCommissionCalcMO.setGoodsId(orderDetail.getGoodsId());
					deliveryCommissionCalcMO.setGoodsFee(orderDetail.getGoodsNum() * orderDetail.getPrice());
					deliveryCommissionCalcMO.setCommissionTypeId(CommissionType.deliverysite.name());
					deliveryCommissionCalcMO.setCalcFee((long) (deliveryCommissionCalcMO.getGoodsFee() * commissionRateDelivery / 100));

					commissionCalcMOList.add(deliveryCommissionCalcMO);
				}
			}

			// 批量插入本订单的佣金信息
			commissionCalcDao.add(commissionCalcMOList);

			// 设置订单的佣金计算时间（表示已经计算过佣金）
			orderBusiness.completeCommissionCalc(order.getId());
		}
	}

	public void batchPay(List<String> ids) throws LittleCatException
	{
		List<CommissionCalcMO> mos = new ArrayList<CommissionCalcMO>();
		for (String id : ids)
		{
			CommissionCalcMO mo = commissionCalcDao.getById(id);
			mo.setPayTime(DateTimeUtil.getCurrentTimeForDisplay());

			mos.add(mo);
		}

		commissionCalcDao.modify(mos);
	}

	public List<CommissionCalcMO> getList(String tuanZhangId, Boolean hasPayed) throws LittleCatException
	{
		return commissionCalcDao.getList(tuanZhangId, hasPayed);
	}

	public int getList(QueryParam queryParam, List<CommissionCalcMO> mos) throws LittleCatException
	{
		return commissionCalcDao.getList(queryParam, mos);
	}
}
