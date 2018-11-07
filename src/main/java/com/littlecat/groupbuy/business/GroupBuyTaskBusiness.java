package com.littlecat.groupbuy.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.query.QueryParam;
import com.littlecat.cbb.utils.CollectionUtil;
import com.littlecat.common.consts.ErrorCode;
import com.littlecat.common.consts.OrderState;
import com.littlecat.groupbuy.dao.GroupBuyTaskDao;
import com.littlecat.groupbuy.model.GroupBuyTaskMO;
import com.littlecat.order.business.OrderBusiness;
import com.littlecat.order.model.OrderMO;

@Component
@Transactional
public class GroupBuyTaskBusiness
{
	private static final String MODEL_NAME_ORDER = OrderMO.class.getSimpleName();

	@Autowired
	private GroupBuyTaskDao groupBuyTaskDao;

	@Autowired
	private OrderBusiness orderBusiness;

	public String add(GroupBuyTaskMO mo) throws LittleCatException
	{
		return groupBuyTaskDao.add(mo);
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

	/**
	 * 成团时的相关操作
	 */
	public void completeTask(String taskId, String completeTime) throws LittleCatException
	{
		List<OrderMO> mos = orderBusiness.getOrderListByGroupBuyTaskId(taskId);

		if (CollectionUtil.isEmpty(mos))
		{
			throw new LittleCatException(ErrorCode.RequestObjectIsNull.getCode(), ErrorCode.RequestObjectIsNull.getMsg().replace("{INFO_NAME}", MODEL_NAME_ORDER));
		}

		for (OrderMO orderMO : mos)
		{
			orderMO.setGroupCompleteTime(completeTime);
			orderMO.setState(OrderState.daiqianshou);
		}

		orderBusiness.modify(mos);

	}
}
