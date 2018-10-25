package com.littlecat.inventory.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.query.QueryParam;
import com.littlecat.common.consts.InventoryChangeType;
import com.littlecat.inventory.dao.SecKillInventoryDao;
import com.littlecat.inventory.model.GoodsInventoryMO;
import com.littlecat.inventory.model.SecKillInventoryMO;
import com.littlecat.seckill.business.SecKillPlanBusiness;
import com.littlecat.seckill.model.SecKillPlanMO;

@Component
@Transactional
public class SecKillInventoryBusiness
{
	@Autowired
	private SecKillInventoryDao secKillInventoryDao;

	@Autowired
	private GoodsInventoryBusiness goodsInventoryBusiness;

	@Autowired
	private SecKillPlanBusiness secKillPlanBusiness;

	public String add(SecKillInventoryMO mo) throws LittleCatException
	{
		String id = secKillInventoryDao.add(mo);

		if (mo.getChangeType() == InventoryChangeType.rengongzengjia || mo.getChangeType() == InventoryChangeType.rengongjianshao || mo.getChangeType() == InventoryChangeType.miaoshaguihuachexiao)
		{// 人工规划库存
			GoodsInventoryMO goodsInventoryMO = new GoodsInventoryMO();

			if (mo.getChangeType() == InventoryChangeType.rengongzengjia)
			{
				goodsInventoryMO.setChangeType(InventoryChangeType.miaoshaguihuakouchu);
			}
			
			if(mo.getChangeType() == InventoryChangeType.rengongjianshao)
			{
				goodsInventoryMO.setChangeType(InventoryChangeType.miaoshaguihuatuihuan);
			}
			
			if(mo.getChangeType() == InventoryChangeType.miaoshaguihuachexiao)
			{
				goodsInventoryMO.setChangeType(InventoryChangeType.miaoshaguihuachexiao);
			}
			
			goodsInventoryMO.setChangeValue(0-mo.getChangeValue());
			goodsInventoryMO.setDescription("SecKillPlanID:"+id);
			goodsInventoryMO.setGoodsId(secKillPlanBusiness.getById(mo.getPlanId()).getGoodsId());
			goodsInventoryMO.setOperatorId(mo.getOperatorId());

			goodsInventoryBusiness.add(goodsInventoryMO);

			SecKillPlanMO secKillPlanMO = secKillPlanBusiness.getById(mo.getPlanId());
			secKillPlanMO.setPlanInventory(secKillInventoryDao.getPlanValueByPlanId(mo.getPlanId()));
			secKillPlanMO.setCurrentInventory(secKillInventoryDao.getCurrentValueByPlanId(mo.getPlanId()));

			secKillPlanBusiness.modify(secKillPlanMO);
		}

		return id;
	}

	public long getCurrentValueByPlanId(String planId) throws LittleCatException
	{
		return secKillInventoryDao.getCurrentValueByPlanId(planId);
	}

	public long getPlanValueByPlanId(String planId) throws LittleCatException
	{
		return secKillInventoryDao.getPlanValueByPlanId(planId);
	}

	public int getList(QueryParam queryParam, List<SecKillInventoryMO> mos) throws LittleCatException
	{
		return secKillInventoryDao.getList(queryParam, mos);
	}

	public List<SecKillInventoryMO> getListByPlanId(String planId) throws LittleCatException
	{
		return secKillInventoryDao.getListByPlanId(planId);
	}
}
