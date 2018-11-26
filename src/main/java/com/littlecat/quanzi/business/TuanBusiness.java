package com.littlecat.quanzi.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.common.consts.BooleanTag;
import com.littlecat.quanzi.dao.TuanDao;
import com.littlecat.quanzi.model.TuanMO;
import com.littlecat.quanzi.model.TuanShenheReqInfo;

@Component
@Transactional
public class TuanBusiness
{
	@Autowired
	private TuanDao tuanDao;
	
	@Autowired
	private TuanMemberBusiness tuanMemberBusiness;

	public TuanMO getById(String tuanZhangOpenId) throws LittleCatException
	{
		return tuanDao.getById(tuanZhangOpenId);
	}

	public String add(TuanMO mo) throws LittleCatException
	{
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

	public List<TuanMO> getList(String enable, String name)
	{
		return tuanDao.getList(enable, name);
	}

	public boolean isTuanZhang(String id)
	{
		return tuanDao.isTuanZhang(id);
	}

	public void modify(TuanMO mo) throws LittleCatException
	{
		tuanDao.modify(mo);
	}

	public void shenHe(TuanShenheReqInfo tuanShenheReqInfo) throws LittleCatException
	{
		tuanDao.shenHe(tuanShenheReqInfo);
		if (BooleanTag.Y.name().equals(tuanShenheReqInfo.getEnable()))
		{// 审核通过，成为团长
			// 自动解除与其他团长的粉丝关系
			tuanMemberBusiness.delete(tuanShenheReqInfo.getId());
		}
	}

	public List<TuanMO> getDeliverySiteList(String terminalUserId, String province, String city, String area) throws LittleCatException
	{
		return tuanDao.getDeliverySiteList(terminalUserId,province, city, area);
	}
}
