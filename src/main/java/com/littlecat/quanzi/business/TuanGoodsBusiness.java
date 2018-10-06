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
import com.littlecat.cbb.utils.CollectionUtil;
import com.littlecat.common.consts.ErrorCode;
import com.littlecat.goods.model.GoodsMO;
import com.littlecat.quanzi.dao.TuanGoodsDao;
import com.littlecat.quanzi.model.TuanGoodsMO;
import com.littlecat.quanzi.model.TuanMO;

@Component
@Transactional
public class TuanGoodsBusiness
{
	private static final String MODEL_NAME = TuanGoodsMO.class.getSimpleName();

	private static String FIELDS_NAME_TUANID = "tuanId";

	@Autowired
	private TuanGoodsDao tuanGoodsDao;

	public void delete(String id) throws LittleCatException
	{
		tuanGoodsDao.delete(id);
	}

	public void delete(List<String> ids) throws LittleCatException
	{
		tuanGoodsDao.delete(ids);
	}

	public String add(TuanGoodsMO mo) throws LittleCatException
	{
		validateReqData(mo);
		return tuanGoodsDao.add(mo);
	}

	public List<String> batchAdd(List<TuanGoodsMO> mos) throws LittleCatException
	{
		if (CollectionUtil.isEmpty(mos))
		{
			throw new LittleCatException(ErrorCode.RequestObjectIsNull.getCode(), ErrorCode.RequestObjectIsNull.getMsg().replace("{INFO_NAME}", MODEL_NAME));
		}

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
	
	public List<GoodsMO> getUnPutOnGoodsList(String tuanId) throws LittleCatException
	{
		return tuanGoodsDao.getUnPutOnGoodsList(tuanId);
	}
	
	private void validateReqData(TuanGoodsMO reqData) throws LittleCatException
	{
		if (reqData == null)
		{
			throw new LittleCatException(ErrorCode.RequestObjectIsNull.getCode(), ErrorCode.RequestObjectIsNull.getMsg().replace("{INFO_NAME}", MODEL_NAME));
		}
	}
}
