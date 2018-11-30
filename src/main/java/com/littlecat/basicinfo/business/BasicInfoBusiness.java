package com.littlecat.basicinfo.business;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.littlecat.basicinfo.dao.AboutUsImgsDao;
import com.littlecat.basicinfo.dao.AreaDao;
import com.littlecat.basicinfo.dao.CityDao;
import com.littlecat.basicinfo.dao.ProvinceDao;
import com.littlecat.basicinfo.dao.QuanZiXieYiImgsDao;
import com.littlecat.basicinfo.model.AboutUsImgsMO;
import com.littlecat.basicinfo.model.AreaMO;
import com.littlecat.basicinfo.model.CityMO;
import com.littlecat.basicinfo.model.ProvinceMO;
import com.littlecat.basicinfo.model.QuanZiXieYiImgsMO;
import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.query.ConditionItem;
import com.littlecat.cbb.query.ConditionOperatorType;
import com.littlecat.cbb.query.QueryCondition;
import com.littlecat.cbb.query.QueryParam;
import com.littlecat.common.consts.ServiceConsts;

@Component
@Transactional
public class BasicInfoBusiness
{
	private final String FIELD_NAME_PROVINCE = "province";
	private final String FIELD_NAME_CITY = "city";

	@Autowired
	private ProvinceDao provinceDao;

	@Autowired
	private CityDao cityDao;

	@Autowired
	private AreaDao areaDao;

	@Autowired
	private AboutUsImgsDao aboutUsImgsDao;

	@Autowired
	private QuanZiXieYiImgsDao quanZiXieYiImgsDao;

	////////////////////////// 省、市、县
	public List<ProvinceMO> getProvinceList() throws LittleCatException
	{
		List<ProvinceMO> moList = new ArrayList<ProvinceMO>();
		provinceDao.getList(moList);
		return moList;
	}

	public List<CityMO> getCityByProvince(String province) throws LittleCatException
	{
		List<CityMO> moList = new ArrayList<CityMO>();

		QueryParam queryParam = new QueryParam();
		QueryCondition queryCondition = new QueryCondition();
		queryCondition.getCondItems()
				.add(new ConditionItem(FIELD_NAME_PROVINCE, province, ConditionOperatorType.equal));
		queryParam.setCondition(queryCondition);

		cityDao.getList(queryParam, moList);
		return moList;
	}

	public List<AreaMO> getAreaByCity(String city) throws LittleCatException
	{
		List<AreaMO> moList = new ArrayList<AreaMO>();

		QueryParam queryParam = new QueryParam();
		QueryCondition queryCondition = new QueryCondition();
		queryCondition.getCondItems()
				.add(new ConditionItem(FIELD_NAME_CITY, city, ConditionOperatorType.equal));
		queryParam.setCondition(queryCondition);

		areaDao.getList(queryParam, moList);
		return moList;
	}

	////////////////////////// AboutUsImgs
	public AboutUsImgsMO getAboutUsImgById(String id) throws LittleCatException
	{
		return aboutUsImgsDao.getById(id);
	}

	public void deleteAboutUsImgs(String id) throws LittleCatException
	{
		aboutUsImgsDao.delete(id);
		
		File file = new File(ServiceConsts.IMG_path + id);
		file.delete();
	}

	public void deleteAboutUsImgs(List<String> ids) throws LittleCatException
	{
		aboutUsImgsDao.delete(ids);
		
		for(String id:ids)
		{
			File file = new File(ServiceConsts.IMG_path + id);
			file.delete();
		}
	}

	public String addAboutUsImgs(AboutUsImgsMO mo) throws LittleCatException
	{
		return aboutUsImgsDao.add(mo);
	}

	public void modifyAboutUsImgs(AboutUsImgsMO mo) throws LittleCatException
	{
		aboutUsImgsDao.modify(mo);
	}

	public int getAboutUsImgsList(QueryParam queryParam, List<AboutUsImgsMO> mos) throws LittleCatException
	{
		return aboutUsImgsDao.getList(queryParam, mos);
	}

	////////////////////////// QuanZiXieYiImgs
	public QuanZiXieYiImgsMO getQuanZiXieYiImgById(String id) throws LittleCatException
	{
		return quanZiXieYiImgsDao.getById(id);
	}

	public void deleteQuanZiXieYiImgs(String id) throws LittleCatException
	{
		quanZiXieYiImgsDao.delete(id);
		
		File file = new File(ServiceConsts.IMG_path + id);
		file.delete();
	}

	public void deleteQuanZiXieYiImgs(List<String> ids) throws LittleCatException
	{
		quanZiXieYiImgsDao.delete(ids);
		
		for(String id:ids)
		{
			File file = new File(ServiceConsts.IMG_path + id);
			file.delete();
		}
	}

	public String addQuanZiXieYiImgs(QuanZiXieYiImgsMO mo) throws LittleCatException
	{
		return quanZiXieYiImgsDao.add(mo);
	}

	public void modifyQuanZiXieYiImgs(QuanZiXieYiImgsMO mo) throws LittleCatException
	{
		quanZiXieYiImgsDao.modify(mo);
	}

	public int getQuanZiXieYiImgsList(QueryParam queryParam, List<QuanZiXieYiImgsMO> mos) throws LittleCatException
	{
		return quanZiXieYiImgsDao.getList(queryParam, mos);
	}

}
