package com.littlecat.seckill.business;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.query.QueryParam;
import com.littlecat.common.consts.BooleanTag;
import com.littlecat.goods.dao.GoodsDao;
import com.littlecat.goods.model.GoodsMO;
import com.littlecat.seckill.dao.SecKillPlanDao;
import com.littlecat.seckill.model.SecKillPlanMO;

@Component
@Transactional
public class SecKillPlanBusiness
{
	private Logger logger = LoggerFactory.getLogger(SecKillPlanBusiness.class);

	@Autowired
	private SecKillPlanDao secKillPlanDao;

	@Autowired
	private GoodsDao goodsDao;

	public String add(SecKillPlanMO mo) throws LittleCatException
	{
		String secKillPlanId = secKillPlanDao.add(mo);

		GoodsMO goodsMO = goodsDao.getById(mo.getGoodsId());
		goodsMO.setHasSecKillPlan(BooleanTag.Y.name());
		goodsDao.modify(goodsMO);

		return secKillPlanId;
	}

	public void modify(SecKillPlanMO mo) throws LittleCatException
	{
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
}
