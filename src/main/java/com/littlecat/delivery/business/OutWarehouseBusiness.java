package com.littlecat.delivery.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.delivery.dao.OutWarehouseDao;
import com.littlecat.delivery.model.OutWarehouseMO;

@Component
@Transactional
public class OutWarehouseBusiness
{
	@Autowired
	private OutWarehouseDao outWarehouseDao;

	public void add(List<OutWarehouseMO> mos) throws LittleCatException
	{
		outWarehouseDao.add(mos);
	}

	public void out(List<String> ids) throws LittleCatException
	{
		outWarehouseDao.out(ids);
	}

	/**
	 * 查询某个订单日期对应的出仓单信息
	 * <br/>
	 * 如果没有，则自动生成
	 * @param orderDate
	 * @param state
	 * @return
	 * @throws LittleCatException
	 */
	public List<OutWarehouseMO> getList(String orderDate, String state) throws LittleCatException
	{
		if (outWarehouseDao.exist(orderDate))
		{
			return outWarehouseDao.getList(orderDate, state);
		}
		
		outWarehouseDao.add(outWarehouseDao.genData(orderDate));
		
		return outWarehouseDao.getList(orderDate, state);
	}

	public void delete(String orderDate) throws LittleCatException
	{
		outWarehouseDao.delete(orderDate);
	}
}
