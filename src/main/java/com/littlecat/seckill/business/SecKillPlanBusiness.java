package com.littlecat.seckill.business;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.query.QueryParam;
import com.littlecat.cbb.utils.DateTimeUtil;
import com.littlecat.common.consts.BooleanTag;
import com.littlecat.common.consts.ErrorCode;
import com.littlecat.goods.dao.GoodsDao;
import com.littlecat.goods.model.GoodsMO;
import com.littlecat.order.dao.OrderDao;
import com.littlecat.seckill.dao.SecKillPlanDao;
import com.littlecat.seckill.model.SecKillPlanMO;

@Component
@Transactional
public class SecKillPlanBusiness
{
	private static final String MODEL_NAME = SecKillPlanMO.class.getSimpleName();
	private static final String MODEL_NAME_GOODS = GoodsMO.class.getSimpleName();

	@Autowired
	private SecKillPlanDao secKillPlanDao;

	@Autowired
	private GoodsDao goodsDao;

	@Autowired
	private OrderDao orderDao;

	public String add(SecKillPlanMO mo) throws LittleCatException
	{
		validateReqData(mo);

		String secKillPlanId = secKillPlanDao.add(mo);

		GoodsMO goodsMO = goodsDao.getById(mo.getGoodsId());
		goodsMO.setHasSecKillPlan(BooleanTag.Y.name());
		goodsDao.modify(goodsMO);

		return secKillPlanId;
	}

	public void modify(SecKillPlanMO mo) throws LittleCatException
	{
		validateReqData(mo);

		secKillPlanDao.modify(mo);
	}

	public void delete(String id) throws LittleCatException
	{
		secKillPlanDao.delete(id);
	}

	public void delete(List<String> ids) throws LittleCatException
	{
		secKillPlanDao.delete(ids);
	}

	public SecKillPlanMO getById(String id) throws LittleCatException
	{
		return secKillPlanDao.getById(id);
	}

	public int getList(QueryParam queryParam, List<SecKillPlanMO> mos) throws LittleCatException
	{
		return secKillPlanDao.getList(queryParam, mos);
	}

	/**
	 * 请求参数校验
	 * 
	 * @param reqData
	 * @throws LittleCatException
	 */
	private void validateReqData(SecKillPlanMO reqData) throws LittleCatException
	{
		if (reqData == null)
		{
			throw new LittleCatException(ErrorCode.RequestObjectIsNull.getCode(), ErrorCode.RequestObjectIsNull.getMsg().replace("{INFO_NAME}", MODEL_NAME));
		}

		// 时间窗口校验
		try
		{
			long startTime = DateTimeUtil.defaultDateFormat.parse(reqData.getStartTime()).getTime();
			long endTime = DateTimeUtil.defaultDateFormat.parse(reqData.getEndTime()).getTime();
			long now = DateTimeUtil.defaultDateFormat.parse(DateTimeUtil.getCurrentTimeForDisplay()).getTime();

			if (startTime >= endTime)
			{
				throw new LittleCatException(ErrorCode.RequestObjectInvalidate.getCode(), ErrorCode.RequestObjectInvalidate.getMsg().replace("{INFO_NAME}", MODEL_NAME).replace("{DETAILINFO}", "the start time of the seckillplan must be less than the end time."));
			}

			if (endTime <= now)
			{
				throw new LittleCatException(ErrorCode.RequestObjectInvalidate.getCode(), ErrorCode.RequestObjectInvalidate.getMsg().replace("{INFO_NAME}", MODEL_NAME).replace("{DETAILINFO}", "the end time of the seckillplan must be grater than now."));
			}
		}
		catch (ParseException e)
		{
			throw new LittleCatException(e.getMessage(), e);
		}

		GoodsMO goodsMO = goodsDao.getById(reqData.getGoodsId());

		if (goodsMO == null)
		{
			throw new LittleCatException(ErrorCode.GetInfoFromDBReturnEmpty.getCode(), ErrorCode.GetInfoFromDBReturnEmpty.getMsg().replace("{INFO_NAME}", MODEL_NAME_GOODS).replace("{DETAILINFO}", "goodsid:" + reqData.getGoodsId()));
		}

		if (reqData.getPrice() > goodsMO.getPrice())
		{
			throw new LittleCatException(ErrorCode.RequestObjectInvalidate.getCode(), ErrorCode.RequestObjectInvalidate.getMsg().replace("{INFO_NAME}", MODEL_NAME).replace("{DETAILINFO}", "the price of the seckillplan can not be grater than the goods's."));
		}

		// TODO:库存校验

		// TODO:limitBuyNum校验

		// TODO:deliveryAreaId校验
	}


	/**
	 * 查询某个消费者在某个秒杀计划下已经购买的商品数量
	 * 
	 * @param planId
	 * @param terminalUserId
	 * @return
	 */
	public int getBuyedNum(String planId, String terminalUserId) throws LittleCatException
	{
		return orderDao.getBuyedNumOfSecKillPlan(planId, terminalUserId);
	}
	
	/**
	 * 秒杀计划列表，用于微信小程序（展示秒杀商品列表）
	 * 
	 * @return
	 */
	public List<SecKillPlanMO> getList4WxApp()
	{
		return secKillPlanDao.getList4WxApp();
	}
	
	public List<SecKillPlanMO> getList4WebApp(String goodsId)
	{
		return secKillPlanDao.getList4WebApp(goodsId);
	}
}
