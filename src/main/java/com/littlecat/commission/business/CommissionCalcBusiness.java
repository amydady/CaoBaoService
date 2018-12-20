package com.littlecat.commission.business;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.utils.CollectionUtil;
import com.littlecat.cbb.utils.DateTimeUtil;
import com.littlecat.cbb.utils.StringUtil;
import com.littlecat.commission.dao.CommissionCalcDao;
import com.littlecat.commission.model.CommissionApplyMO;
import com.littlecat.commission.model.CommissionCalcMO;
import com.littlecat.commission.model.CommissionGoodsMO;
import com.littlecat.commission.model.CommissionReport;
import com.littlecat.commission.model.CommissionTypeMO;
import com.littlecat.common.consts.CommissionState;
import com.littlecat.common.consts.CommissionType;
import com.littlecat.common.consts.SysParamName;
import com.littlecat.common.utils.SysParamUtil;
import com.littlecat.order.business.OrderBusiness;
import com.littlecat.order.model.OrderDetailMO;
import com.littlecat.order.model.OrderMO;
import com.littlecat.quanzi.business.TuanBusiness;
import com.littlecat.quanzi.business.TuanMemberBusiness;
import com.littlecat.quanzi.model.TuanMemberMO;

@Component
@Transactional
public class CommissionCalcBusiness
{
	private static final Logger logger = LoggerFactory.getLogger(CommissionCalcBusiness.class);

	private static final int ENABLE_COMMISSION_DELAY_DAY_DEFAULT = 7;

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

	@Autowired
	private TuanBusiness tuanBusiness;

	@Autowired
	private CommissionApplyBusiness commissionApplyBusiness;

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
	 * 批量计算，有效性处理() <br/>
	 * 由定时任务服务通过Rest接口调用
	 * 
	 * @throws LittleCatException
	 */
	public void doCalc() throws LittleCatException
	{
		doCalc(orderBusiness.getNeedCommissionCalcOrderList());
		enable(commissionCalcDao.getNeedEnableList());
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
			if (tuanBusiness.isTuanZhang(order.getTerminalUserId()))
			{// 团长自购，物流佣金结算给自己
				deliverySiteTuanZhangId = order.getTerminalUserId();
			}

			// 计算佣金=========================
			List<CommissionCalcMO> commissionCalcMOList = new ArrayList<CommissionCalcMO>();

			// 本商品推荐团长
			String shareTuanZhangId = null;

			if (tuanBusiness.isTuanZhang(order.getTerminalUserId()))
			{// 团长自购，推广佣金结算给自己
				shareTuanZhangId = order.getTerminalUserId();
			}
			else if (StringUtil.isNotEmpty(order.getShareTuanZhangId()))
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

				BigDecimal commissionRateShare = commissionTypeShare.getCommissionRate();
				BigDecimal commissionRateDelivery = commissionTypeDelivery.getCommissionRate();

				if (CollectionUtil.isNotEmpty(commissionGoodsMOList))
				{
					for (CommissionGoodsMO commissionGoodsMO : commissionGoodsMOList)
					{
						if (CommissionType.share.name().equals(commissionGoodsMO.getCommissionTypeId()))
						{
							commissionRateShare = commissionGoodsMO.getCommissionRate();
						}

						if (CommissionType.deliverysite.name().equals(commissionGoodsMO.getCommissionTypeId()))
						{
							commissionRateDelivery = commissionGoodsMO.getCommissionRate();
						}
					}
				}

				if (StringUtil.isNotEmpty(shareTuanZhangId))
				{// 推广佣金

					CommissionCalcMO shareCommissionCalcMO = new CommissionCalcMO();

					shareCommissionCalcMO.setOrderId(order.getId());
					shareCommissionCalcMO.setTuanZhangId(shareTuanZhangId);
					shareCommissionCalcMO.setGoodsId(orderDetail.getGoodsId());
					shareCommissionCalcMO.setGoodsFee((orderDetail.getGoodsNum().multiply(orderDetail.getPrice())).setScale(2, java.math.BigDecimal.ROUND_DOWN));
					shareCommissionCalcMO.setCommissionTypeId(CommissionType.share.name());
					shareCommissionCalcMO.setCalcFee((orderDetail.getGoodsNum().multiply(orderDetail.getPrice()).multiply(commissionRateShare).multiply(new BigDecimal("0.01"))).setScale(2, java.math.BigDecimal.ROUND_DOWN));

					commissionCalcMOList.add(shareCommissionCalcMO);
				}

				if (StringUtil.isNotEmpty(deliverySiteTuanZhangId))
				{// 自提点佣金
					CommissionCalcMO deliveryCommissionCalcMO = new CommissionCalcMO();

					deliveryCommissionCalcMO.setOrderId(order.getId());
					deliveryCommissionCalcMO.setTuanZhangId(deliverySiteTuanZhangId);
					deliveryCommissionCalcMO.setGoodsId(orderDetail.getGoodsId());
					deliveryCommissionCalcMO.setGoodsFee((orderDetail.getGoodsNum().multiply(orderDetail.getPrice())).setScale(2, java.math.BigDecimal.ROUND_DOWN));
					deliveryCommissionCalcMO.setCommissionTypeId(CommissionType.deliverysite.name());
					deliveryCommissionCalcMO.setCalcFee((orderDetail.getGoodsNum().multiply(orderDetail.getPrice()).multiply(commissionRateDelivery).multiply(new BigDecimal("0.01"))).setScale(2, java.math.BigDecimal.ROUND_DOWN));

					commissionCalcMOList.add(deliveryCommissionCalcMO);
				}
			}

			// 批量插入本订单的佣金信息
			commissionCalcDao.add(commissionCalcMOList);

			// 设置订单的佣金计算时间（表示已经计算过佣金）
			orderBusiness.completeCommissionCalc(order.getId());
		}
	}

	public void enable(List<String> ids) throws LittleCatException
	{
		// 收货后X天，佣金解除冻结（可申请发放）
		int enableCommissionDelayDay = ENABLE_COMMISSION_DELAY_DAY_DEFAULT;

		try
		{
			enableCommissionDelayDay = Integer.parseInt(SysParamUtil.getValueByName(SysParamName.enable_commission_delay_day.name()));
		}
		catch (Exception e)
		{
			logger.error("get PARAM_NAME_ENABLE_COMMISSION_DELAY_DAY from db error.", e.getMessage());
		}

		List<CommissionCalcMO> mos = new ArrayList<CommissionCalcMO>();
		for (String id : ids)
		{
			CommissionCalcMO mo = commissionCalcDao.getById(id);
			OrderMO order = orderBusiness.getOrderById(mo.getOrderId());
			if (StringUtil.isNotEmpty(order.getReceiveTime()) && StringUtil.isEmpty(order.getReturnApplyTime()))
			{
				if (DateTimeUtil.getDurationDays(order.getReceiveTime()) > enableCommissionDelayDay)
				{
					mo.setState(CommissionState.canapply);
					mos.add(mo);
				}
			}
		}

		commissionCalcDao.modify(mos);
	}

	public void apply(CommissionApplyMO mo) throws LittleCatException
	{
		mo.setApplyTime(DateTimeUtil.getCurrentTimeForDisplay());
		commissionCalcDao.apply(mo);

		commissionApplyBusiness.add(mo);
	}

	public void pay(List<String> ids) throws LittleCatException
	{
		List<CommissionCalcMO> mos = new ArrayList<CommissionCalcMO>();
		for (String id : ids)
		{
			CommissionCalcMO mo = commissionCalcDao.getById(id);
			mo.setPayTime(DateTimeUtil.getCurrentTimeForDisplay());
			mo.setState(CommissionState.payed);

			mos.add(mo);
		}

		commissionCalcDao.modify(mos);
	}

	public void disable(List<String> ids) throws LittleCatException
	{
		List<CommissionCalcMO> mos = new ArrayList<CommissionCalcMO>();
		for (String id : ids)
		{
			CommissionCalcMO mo = commissionCalcDao.getById(id);
			mo.setDisableTime(DateTimeUtil.getCurrentTimeForDisplay());
			mo.setState(CommissionState.disabled);

			mos.add(mo);
		}

		commissionCalcDao.modify(mos);
	}

	public List<CommissionCalcMO> getList(String tuanZhangId, String state) throws LittleCatException
	{
		return commissionCalcDao.getList(tuanZhangId, state);
	}

	public CommissionReport getCommissionReport(String tuanZhangId)
	{
		CommissionReport commissionReport = new CommissionReport();
		commissionReport.setTotalPayedFee(commissionCalcDao.getTotalPayedFee(tuanZhangId));
		commissionReport.setTotalCanApplyFee(commissionCalcDao.getTotalCanApplyFee(tuanZhangId));
		commissionReport.setApplyHistory(commissionCalcDao.getApplyHis(tuanZhangId));

		return commissionReport;
	}
}
