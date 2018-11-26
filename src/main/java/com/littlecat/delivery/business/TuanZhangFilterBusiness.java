package com.littlecat.delivery.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.delivery.dao.TuanZhangFilterDao;
import com.littlecat.delivery.model.TuanZhangFilterMO;
import com.littlecat.order.business.OrderBusiness;

@Component
@Transactional
public class TuanZhangFilterBusiness
{
	@Autowired
	private TuanZhangFilterDao tuanZhangFilterDao;

	@Autowired
	private OrderBusiness orderBusiness;


	public void add(List<TuanZhangFilterMO> mos) throws LittleCatException
	{
		tuanZhangFilterDao.add(mos);
	}

	public void receive(List<String> ids) throws LittleCatException
	{
		tuanZhangFilterDao.receive(ids);
		orderBusiness.terminalUserReceive(tuanZhangFilterDao.getOrderIdList(ids));
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
	public List<TuanZhangFilterMO> getList(String orderDate, String tuanZhangName, String tuanZhangMobile, String terminalUserName, String terminalUserMobile, String state) throws LittleCatException
	{

		if (tuanZhangFilterDao.exist(orderDate))
		{
			return tuanZhangFilterDao.getList(orderDate, tuanZhangName, tuanZhangMobile, terminalUserName, terminalUserMobile, state);
		}

		tuanZhangFilterDao.add(tuanZhangFilterDao.genData(orderDate));

		return tuanZhangFilterDao.getList(orderDate, tuanZhangName, tuanZhangMobile, terminalUserName, terminalUserMobile, state);
	}

	public void delete(String tuanZhangId, String orderDate) throws LittleCatException
	{
		tuanZhangFilterDao.delete(tuanZhangId, orderDate);
	}
}
