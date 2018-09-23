package com.littlecat.quanzi.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.query.ConditionItem;
import com.littlecat.cbb.query.ConditionOperatorType;
import com.littlecat.cbb.query.QueryCondition;
import com.littlecat.cbb.query.QueryParam;
import com.littlecat.quanzi.dao.TuanGoodsDao;
import com.littlecat.quanzi.model.TuanGoodsMO;

@Component
@Transactional
public class TuanGoodsBusiness
{
	private static String FIELDS_NAME_TUANID = "tuanId";
	
	@Autowired
	private TuanGoodsDao tuanGoodsDao;

	public boolean delete(String id) throws LittleCatException
	{
		return tuanGoodsDao.delete(id);
	}

	public boolean delete(List<String> ids) throws LittleCatException
	{
		return tuanGoodsDao.delete(ids);
	}

	public String add(TuanGoodsMO mo) throws LittleCatException
	{
		return tuanGoodsDao.add(mo);
	}
	
	public List<String> batchAdd(List<TuanGoodsMO> mos) throws LittleCatException
	{
		return tuanGoodsDao.batchAdd(mos);
	}

	public int getListByTuanId(String tuanId, List<TuanGoodsMO> mos) throws LittleCatException
	{
		QueryParam queryParam = new QueryParam();
		QueryCondition condition = new QueryCondition();

		condition.getCondItems().add(new ConditionItem(FIELDS_NAME_TUANID, tuanId, ConditionOperatorType.equal));
		queryParam.setCondition(condition);

		return getList(queryParam, mos);
	}
	
	public int getList(QueryParam queryParam, List<TuanGoodsMO> mos) throws LittleCatException
	{
		return tuanGoodsDao.getList(queryParam, mos);
	}
}
