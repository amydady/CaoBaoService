package com.littlecat.shoppingcart.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.query.ConditionItem;
import com.littlecat.cbb.query.ConditionOperatorType;
import com.littlecat.cbb.query.QueryCondition;
import com.littlecat.cbb.query.QueryParam;
import com.littlecat.common.consts.BuyType;
import com.littlecat.common.consts.ErrorCode;
import com.littlecat.shoppingcart.dao.ShoppingCartDao;
import com.littlecat.shoppingcart.model.ShoppingCartMO;

@Component
@Transactional
public class ShoppingCartBusiness
{
	@Autowired
	private ShoppingCartDao shoppingCartDao;

	private static final String FIELD_NAME_TERMINALUSERID = "terminalUserId";
	private static final String FIELD_NAME_BUYTYPE = "buyType";
	private static final String FIELD_NAME_RESID = "resId";

	private static final String MODEL_NAME = ShoppingCartMO.class.getSimpleName();

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
		if (hasExists(mo.getTerminalUserId(), mo.getBuyType(), mo.getResId()))
		{
			throw new LittleCatException(ErrorCode.DuplicateAddError.getCode(), ErrorCode.DuplicateAddError.getMsg().replace("{INFO_NAME}", MODEL_NAME));
		}
		
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

	public boolean hasExists(String terminalUserId, BuyType buyType, String resId)
	{
		List<ShoppingCartMO> mos = new ArrayList<ShoppingCartMO>();

		QueryParam queryParam = new QueryParam();
		QueryCondition condition = new QueryCondition();

		condition.getCondItems().add(new ConditionItem(FIELD_NAME_TERMINALUSERID, terminalUserId, ConditionOperatorType.equal));
		condition.getCondItems().add(new ConditionItem(FIELD_NAME_BUYTYPE, buyType.name(), ConditionOperatorType.equal));
		condition.getCondItems().add(new ConditionItem(FIELD_NAME_RESID, resId, ConditionOperatorType.equal));

		queryParam.setCondition(condition);
		
		return shoppingCartDao.getList(queryParam, mos) > 0;
	}
}
