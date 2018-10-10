package com.littlecat.groupbuy.business;

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
import com.littlecat.groupbuy.dao.GroupBuyPlanDao;
import com.littlecat.groupbuy.model.GroupBuyPlanMO;

@Component
@Transactional
public class GroupBuyPlanBusiness
{
	private static final String MODEL_NAME = GroupBuyPlanMO.class.getSimpleName();
	private static final String MODEL_NAME_GOODS = GoodsMO.class.getSimpleName();

	@Autowired
	private GroupBuyPlanDao groupBuyPlanDao;

	@Autowired
	private GoodsDao goodsDao;

	public String add(GroupBuyPlanMO mo) throws LittleCatException
	{
		validateReqData(mo);

		String secKillPlanId = groupBuyPlanDao.add(mo);

		GoodsMO goodsMO = goodsDao.getById(mo.getGoodsId());
		goodsMO.setHasSecKillPlan(BooleanTag.Y.name());
		goodsDao.modify(goodsMO);

		return secKillPlanId;
	}

	public void modify(GroupBuyPlanMO mo) throws LittleCatException
	{
		validateReqData(mo);

		groupBuyPlanDao.modify(mo);
	}

	public void delete(String id) throws LittleCatException
	{
		groupBuyPlanDao.delete(id);
	}

	public void delete(List<String> ids) throws LittleCatException
	{
		groupBuyPlanDao.delete(ids);
	}

	public GroupBuyPlanMO getById(String id) throws LittleCatException
	{
		return groupBuyPlanDao.getById(id);
	}

	public int getList(QueryParam queryParam, List<GroupBuyPlanMO> mos) throws LittleCatException
	{
		return groupBuyPlanDao.getList(queryParam, mos);
	}

	/**
	 * 请求参数校验
	 * 
	 * @param reqData
	 * @throws LittleCatException
	 */
	private void validateReqData(GroupBuyPlanMO reqData) throws LittleCatException
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
}
