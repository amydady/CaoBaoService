package com.littlecat.groupbuy.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.query.QueryParam;
import com.littlecat.goods.business.GoodsBusiness;
import com.littlecat.goods.model.GoodsMO;
import com.littlecat.groupbuy.dao.GroupBuyTaskDao;
import com.littlecat.groupbuy.model.GroupBuyPlanMO;
import com.littlecat.groupbuy.model.GroupBuyTaskMO;
import com.littlecat.inventory.business.GroupBuyInventoryBusiness;
import com.littlecat.order.business.OrderBusiness;
import com.littlecat.order.model.OrderDetailMO;
import com.littlecat.order.model.OrderMO;

@Component
@Transactional
public class GroupBuyTaskBusiness
{
	private static final String MODEL_NAME = GroupBuyTaskMO.class.getSimpleName();
	private static final String MODEL_NAME_GOODS = GoodsMO.class.getSimpleName();

	@Autowired
	private GroupBuyTaskDao groupBuyTaskDao;
	
	@Autowired
	private GroupBuyPlanBusiness groupBuyPlanBusiness;

	@Autowired
	private GoodsBusiness goodsBusiness;
	
	@Autowired
	private GroupBuyInventoryBusiness groupBuyInventoryBusiness;
	
	@Autowired
	private OrderBusiness orderBusiness;

	public String add(GroupBuyTaskMO mo) throws LittleCatException
	{
		String id = groupBuyTaskDao.add(mo);
		
		GroupBuyPlanMO groupBuyPlanMO = groupBuyPlanBusiness.getById(mo.getPlanId());
		
		if(groupBuyPlanMO == null)
		{
			
		}
		
		OrderMO orderMO = new OrderMO();
		orderMO.setTerminalUserId(mo.getCreateOperatorId());
		
		List<OrderDetailMO> orderDetailMOs = new ArrayList<OrderDetailMO>();
		
		orderBusiness.addOrder(orderMO, orderDetailMOs);
		
		
		
		return id;
	}

	public void modify(GroupBuyTaskMO mo) throws LittleCatException
	{
		groupBuyTaskDao.modify(mo);
	}

	public void delete(String id) throws LittleCatException
	{
		groupBuyTaskDao.delete(id);
	}

	public void delete(List<String> ids) throws LittleCatException
	{
		groupBuyTaskDao.delete(ids);
	}

	public GroupBuyTaskMO getById(String id) throws LittleCatException
	{
		return groupBuyTaskDao.getById(id);
	}

	public int getList(QueryParam queryParam, List<GroupBuyTaskMO> mos) throws LittleCatException
	{
		return groupBuyTaskDao.getList(queryParam, mos);
	}
}
