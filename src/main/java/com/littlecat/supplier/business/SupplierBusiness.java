package com.littlecat.supplier.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.query.QueryParam;
import com.littlecat.supplier.dao.SupplierDao;
import com.littlecat.supplier.model.SupplierMO;

@Component
@Transactional
public class SupplierBusiness
{
	@Autowired
	private SupplierDao supplierDao;
	
	public SupplierMO getById(String id) throws LittleCatException
	{
		return supplierDao.getById(id);
	}
	
	public boolean enable(String id) throws LittleCatException
	{
		return supplierDao.enable(id);
	}
	
	public boolean enable(List<String> ids) throws LittleCatException
	{
		return supplierDao.enable(ids);
	}
	
	public boolean disable(String id) throws LittleCatException
	{
		return supplierDao.disable(id);
	}
	
	public boolean disable(List<String> ids) throws LittleCatException
	{
		return supplierDao.disable(ids);
	}
	
	public String add(SupplierMO mo) throws LittleCatException
	{
		return supplierDao.add(mo);
	}
	
	public boolean modify(SupplierMO mo) throws LittleCatException
	{
		return supplierDao.modify(mo);
	}

	public int getList(QueryParam queryParam,List<SupplierMO> mos) throws LittleCatException
	{
		return supplierDao.getList(queryParam, mos);
	}
}
