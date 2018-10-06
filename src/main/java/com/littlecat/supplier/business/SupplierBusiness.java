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

	public void delete(String id) throws LittleCatException
	{
		supplierDao.delete(id);
	}

	public void delete(List<String> ids) throws LittleCatException
	{
		supplierDao.delete(ids);
	}

	public void enable(String id) throws LittleCatException
	{
		supplierDao.enable(id);
	}

	public void enable(List<String> ids) throws LittleCatException
	{
		supplierDao.enable(ids);
	}

	public void disable(String id) throws LittleCatException
	{
		supplierDao.disable(id);
	}

	public void disable(List<String> ids) throws LittleCatException
	{
		supplierDao.disable(ids);
	}

	public String add(SupplierMO mo) throws LittleCatException
	{
		return supplierDao.add(mo);
	}

	public void modify(SupplierMO mo) throws LittleCatException
	{
		supplierDao.modify(mo);
	}

	public int getList(QueryParam queryParam, List<SupplierMO> mos) throws LittleCatException
	{
		return supplierDao.getList(queryParam, mos);
	}
}
