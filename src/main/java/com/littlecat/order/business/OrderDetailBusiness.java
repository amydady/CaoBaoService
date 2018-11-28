package com.littlecat.order.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.goods.business.GoodsBusiness;
import com.littlecat.goods.model.GoodsMO;
import com.littlecat.order.dao.OrderDetailDao;
import com.littlecat.order.model.OrderDetailMO;

@Component
@Transactional
public class OrderDetailBusiness
{
	@Autowired
	private OrderDetailDao orderDetailDao;

	@Autowired
	private GoodsBusiness goodsBusiness;

	public List<String> add(List<OrderDetailMO> mos) throws LittleCatException
	{
		for (OrderDetailMO mo : mos)
		{
			GoodsMO goodsMo = goodsBusiness.getById(mo.getGoodsId());
			mo.setGoodsName(goodsMo.getName());
			mo.setGoodsMainImgData(goodsMo.getMainImgData());
		}

		return orderDetailDao.add(mos);
	}

	public OrderDetailMO getById(String id) throws LittleCatException
	{
		return orderDetailDao.getById(id);
	}

	public List<OrderDetailMO> getByOrderId(String orderId) throws LittleCatException
	{
		return orderDetailDao.getByOrderId(orderId);
	}
}
