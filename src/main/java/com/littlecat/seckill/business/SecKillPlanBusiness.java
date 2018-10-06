package com.littlecat.seckill.business;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.littlecat.seckill.dao.SecKillPlanDao;
import com.littlecat.seckill.model.SecKillPlanMO;

@Component
@Transactional
public class SecKillPlanBusiness
{
	private static final Logger logger = LoggerFactory.getLogger(SecKillPlanBusiness.class);

	private static final String MODEL_NAME = SecKillPlanMO.class.getSimpleName();
	private static final String MODEL_NAME_GOODS = GoodsMO.class.getSimpleName();

	@Autowired
	private SecKillPlanDao secKillPlanDao;

	@Autowired
	private GoodsDao goodsDao;

	public String add(SecKillPlanMO mo) throws LittleCatException
	{
		validateReqData(mo);
		initSettingsForReqData(mo);

		String secKillPlanId = secKillPlanDao.add(mo);

		GoodsMO goodsMO = goodsDao.getById(mo.getGoodsId());
		goodsMO.setHasSecKillPlan(BooleanTag.Y.name());
		goodsDao.modify(goodsMO);

		return secKillPlanId;
	}

	public void modify(SecKillPlanMO mo) throws LittleCatException
	{
		validateReqData(mo);
		initSettingsForReqData(mo);
		
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

	public void enable(String id) throws LittleCatException
	{
		secKillPlanDao.enable(id);
	}

	public void enable(List<String> ids) throws LittleCatException
	{
		secKillPlanDao.enable(ids);
	}

	public void disable(String id) throws LittleCatException
	{
		secKillPlanDao.disable(id);
	}

	public void disable(List<String> ids) throws LittleCatException
	{
		secKillPlanDao.disable(ids);
	}

	/**
	 * 根据时间自动将秒杀计划失效（由系统定时调用）
	 * 
	 * @throws LittleCatException
	 */
	public void disable()
	{
		try
		{
			secKillPlanDao.disable();
		}
		catch (LittleCatException e)
		{
			logger.error("SecKillPlanBusiness:disable() run error", e);
		}
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
		long startTime = Long.valueOf(reqData.getStartTime());
		long endTime = Long.valueOf(reqData.getEndTime());
		long now = Long.valueOf(DateTimeUtil.getCurrentTimeForDisplay());

		if (startTime >= endTime)
		{
			throw new LittleCatException(ErrorCode.RequestObjectInvalidate.getCode(), ErrorCode.RequestObjectInvalidate.getMsg().replace("{INFO_NAME}", MODEL_NAME).replace("{DETAILINFO}", "the start time of the seckillplan must be less than the end time."));
		}

		if (endTime <= now)
		{
			throw new LittleCatException(ErrorCode.RequestObjectInvalidate.getCode(), ErrorCode.RequestObjectInvalidate.getMsg().replace("{INFO_NAME}", MODEL_NAME).replace("{DETAILINFO}", "the end time of the seckillplan must be grater than now."));
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
	 * 请求对象的一些初始化设置
	 * 
	 * @param reqData
	 */
	private void initSettingsForReqData(SecKillPlanMO reqData)
	{
		// 根据时间窗口设置Enable标记
		long startTime = Long.valueOf(reqData.getStartTime());
		long endTime = Long.valueOf(reqData.getEndTime());
		long now = Long.valueOf(DateTimeUtil.getCurrentTimeForDisplay());

		if (now >= startTime && now < endTime)
		{
			reqData.setEnable(BooleanTag.Y.name());
		}
		else
		{
			reqData.setEnable(BooleanTag.N.name());
		}
	}
}
