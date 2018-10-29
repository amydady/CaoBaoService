package com.littlecat.delivery.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.delivery.dao.DeliveryFeeRuleDao;
import com.littlecat.delivery.model.DeliveryFeeCalcTypeMO;
import com.littlecat.delivery.model.DeliveryFeeRuleMO;

@Component
@Transactional
public class DeliveryFeeRuleBusiness
{
	@Autowired
	private DeliveryFeeRuleDao deliveryFeeRuleDao;

	public DeliveryFeeRuleMO getById(String id) throws LittleCatException
	{
		return deliveryFeeRuleDao.getById(id);
	}

	public void enable(String id) throws LittleCatException
	{
		deliveryFeeRuleDao.enable(id);
	}

	public void enable(List<String> ids) throws LittleCatException
	{
		deliveryFeeRuleDao.enable(ids);
	}

	public void disable(String id) throws LittleCatException
	{
		deliveryFeeRuleDao.disable(id);
	}

	public void disable(List<String> ids) throws LittleCatException
	{
		deliveryFeeRuleDao.disable(ids);
	}

	public String add(DeliveryFeeRuleMO mo) throws LittleCatException
	{
		return deliveryFeeRuleDao.add(mo);
	}

	public void modify(DeliveryFeeRuleMO mo) throws LittleCatException
	{
		deliveryFeeRuleDao.modify(mo);
	}

	public List<DeliveryFeeRuleMO> getList()
	{
		return deliveryFeeRuleDao.getList();
	}
	
	public List<DeliveryFeeCalcTypeMO> getFeeCalcTypeList()
	{
		return deliveryFeeRuleDao.getFeeCalcTypeList();
	}
}
