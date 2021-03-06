package com.littlecat.delivery.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.delivery.dao.TuanZhangReceiveDao;
import com.littlecat.delivery.model.TuanZhangReceiveMO;
import com.littlecat.order.business.OrderBusiness;

@Component
@Transactional
public class TuanZhangReceiveBusiness
{
	@Autowired
	private TuanZhangReceiveDao tuanZhangReceiveDao;

	@Autowired
	private OrderBusiness orderBusiness;

	public void add(List<TuanZhangReceiveMO> mos) throws LittleCatException
	{
		tuanZhangReceiveDao.add(mos);
	}

	/**
	 * 团长签收
	 * 
	 * @param ids
	 * @throws LittleCatException
	 */
	public void receive(String orderDate) throws LittleCatException
	{
		tuanZhangReceiveDao.receive(orderDate);
		orderBusiness.afterDeliverySiteReceive(orderDate);
	}

	/**
	 * 查询某个订单日期对应的出仓单信息 <br/>
	 * 如果没有，则自动生成
	 * 
	 * @param orderDate
	 * @param state
	 * @return
	 * @throws LittleCatException
	 */
	public List<TuanZhangReceiveMO> getList(String orderDate, String tuanZhangName, String tuanZhangMobile, String state) throws LittleCatException
	{
		if (tuanZhangReceiveDao.exist(orderDate))
		{
			return tuanZhangReceiveDao.getList(orderDate, tuanZhangName, tuanZhangMobile, state);
		}

		tuanZhangReceiveDao.add(tuanZhangReceiveDao.genData(orderDate));

		return tuanZhangReceiveDao.getList(orderDate, tuanZhangName, tuanZhangMobile, state);
	}

	public void delete(String tuanZhangId, String orderDate) throws LittleCatException
	{
		tuanZhangReceiveDao.delete(tuanZhangId, orderDate);
	}
}
