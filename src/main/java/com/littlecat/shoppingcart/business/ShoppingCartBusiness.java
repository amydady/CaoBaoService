package com.littlecat.shoppingcart.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.query.QueryParam;
import com.littlecat.shoppingcart.dao.ShoppingCartDao;
import com.littlecat.shoppingcart.model.ShoppingCartMO;

@Component
@Transactional
public class ShoppingCartBusiness
{
	@Autowired
	private ShoppingCartDao shoppingCartDao;

	public void delete(String id) throws LittleCatException
	{
		shoppingCartDao.delete(id);
	}

	public void delete(List<String> ids) throws LittleCatException
	{
		shoppingCartDao.delete(ids);
	}

	public String add(ShoppingCartMO mo) throws LittleCatException
	{
		return shoppingCartDao.add(mo);
	}

	public int getList(QueryParam queryParam, List<ShoppingCartMO> mos) throws LittleCatException
	{
		return shoppingCartDao.getList(queryParam, mos);
	}

	public void modify(ShoppingCartMO mo) throws LittleCatException
	{
		shoppingCartDao.modify(mo);
	}

	public void modify(List<ShoppingCartMO> mos) throws LittleCatException
	{
		shoppingCartDao.modify(mos);
	}
}
