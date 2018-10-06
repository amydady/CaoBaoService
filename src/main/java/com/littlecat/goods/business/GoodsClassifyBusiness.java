package com.littlecat.goods.business;

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
import com.littlecat.common.consts.ErrorCode;
import com.littlecat.goods.dao.GoodsClassifyDao;
import com.littlecat.goods.model.GoodsClassifyMO;
import com.littlecat.goods.model.GoodsMO;

@Component
@Transactional
public class GoodsClassifyBusiness
{
	private static final String FIELDNAME_PARENTID = "parentId";
	private static final String MODEL_NAME = GoodsClassifyMO.class.getSimpleName();
	@Autowired
	private GoodsClassifyDao goodsClassifyDao;

	public GoodsClassifyMO getById(String id) throws LittleCatException
	{
		return goodsClassifyDao.getById(id);
	}

	public void enable(String id) throws LittleCatException
	{
		goodsClassifyDao.enable(id);
	}

	public void enable(List<String> ids) throws LittleCatException
	{
		goodsClassifyDao.enable(ids);
	}

	public void disable(String id) throws LittleCatException
	{
		goodsClassifyDao.disable(id);
	}

	public void disable(List<String> ids) throws LittleCatException
	{
		goodsClassifyDao.disable(ids);
	}

	public String add(GoodsClassifyMO mo) throws LittleCatException
	{
		validateReqData(mo);
		return goodsClassifyDao.add(mo);
	}

	public void modify(GoodsClassifyMO mo) throws LittleCatException
	{
		validateReqData(mo);
		goodsClassifyDao.modify(mo);
	}

	public int getList(QueryParam queryParam, List<GoodsClassifyMO> mos) throws LittleCatException
	{
		return goodsClassifyDao.getList(queryParam, mos);
	}

	public List<GoodsClassifyMO> getByParentId(String parentId)
	{
		List<GoodsClassifyMO> mos = new ArrayList<GoodsClassifyMO>();

		QueryParam queryParam = new QueryParam();

		QueryCondition condition = new QueryCondition();
		condition.getCondItems().add(new ConditionItem(FIELDNAME_PARENTID, parentId, ConditionOperatorType.equal));
		queryParam.setCondition(condition);

		goodsClassifyDao.getList(queryParam, mos);

		return mos;
	}
	
	private void validateReqData(GoodsClassifyMO reqData) throws LittleCatException
	{
		if (reqData == null)
		{
			throw new LittleCatException(ErrorCode.RequestObjectIsNull.getCode(), ErrorCode.RequestObjectIsNull.getMsg().replace("{INFO_NAME}", MODEL_NAME));
		}
	}
}
