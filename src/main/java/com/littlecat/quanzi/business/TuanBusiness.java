package com.littlecat.quanzi.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.common.consts.ErrorCode;
import com.littlecat.quanzi.dao.TuanDao;
import com.littlecat.quanzi.model.TuanMO;

@Component
@Transactional
public class TuanBusiness
{
	private static final String MODEL_NAME = TuanMO.class.getSimpleName();
	@Autowired
	private TuanDao tuanDao;

	public TuanMO getByTuanZhangId(String tuanZhangId) throws LittleCatException
	{
		return tuanDao.getByTuanZhangId(tuanZhangId);
	}

	public String add(TuanMO mo) throws LittleCatException
	{
		validateReqData(mo);
		return tuanDao.add(mo);
	}

	public void delete(String id) throws LittleCatException
	{
		tuanDao.delete(id);
	}

	public void delete(List<String> ids) throws LittleCatException
	{
		tuanDao.delete(ids);
	}

	public void enable(String id) throws LittleCatException
	{
		tuanDao.enable(id);
	}

	public void enable(List<String> ids) throws LittleCatException
	{
		tuanDao.enable(ids);
	}

	public void disable(String id) throws LittleCatException
	{
		tuanDao.disable(id);
	}

	public void disable(List<String> ids) throws LittleCatException
	{
		tuanDao.disable(ids);
	}

	public void modify(TuanMO mo) throws LittleCatException
	{
		validateReqData(mo);
		tuanDao.modify(mo);
	}

	private void validateReqData(TuanMO reqData) throws LittleCatException
	{
		if (reqData == null)
		{
			throw new LittleCatException(ErrorCode.RequestObjectIsNull.getCode(), ErrorCode.RequestObjectIsNull.getMsg().replace("{INFO_NAME}", MODEL_NAME));
		}
	}

}
