package com.littlecat.basicinfo.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.littlecat.basicinfo.dao.AreaDao;
import com.littlecat.basicinfo.dao.CityDao;
import com.littlecat.basicinfo.dao.ProvinceDao;
import com.littlecat.basicinfo.model.AreaMO;
import com.littlecat.basicinfo.model.CityMO;
import com.littlecat.basicinfo.model.ProvinceMO;
import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.query.ConditionItem;
import com.littlecat.cbb.query.ConditionOperatorType;
import com.littlecat.cbb.query.QueryCondition;
import com.littlecat.cbb.query.QueryParam;

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
}
