package com.littlecat.groupbuy.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.query.QueryParam;
import com.littlecat.goods.dao.GoodsDao;
import com.littlecat.goods.model.GoodsMO;
import com.littlecat.groupbuy.dao.GroupBuyTaskDao;
import com.littlecat.groupbuy.model.GroupBuyTaskMO;

@Component
@Transactional
public class GroupBuyTaskBusiness
{
	private static final String MODEL_NAME = GroupBuyTaskMO.class.getSimpleName();
	private static final String MODEL_NAME_GOODS = GoodsMO.class.getSimpleName();

	@Autowired
	private GroupBuyTaskDao groupBuyTaskDao;

	@Autowired
	private GoodsDao goodsDao;

	public String add(GroupBuyTaskMO mo) throws LittleCatException
	{
		// TODO:对应的订单信息处理
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
}
