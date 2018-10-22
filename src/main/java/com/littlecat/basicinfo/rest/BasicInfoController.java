package com.littlecat.basicinfo.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.littlecat.basicinfo.business.BasicInfoBusiness;
import com.littlecat.basicinfo.model.AreaMO;
import com.littlecat.basicinfo.model.CityMO;
import com.littlecat.basicinfo.model.ProvinceMO;
import com.littlecat.cbb.common.Consts;
import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.rest.RestRsp;

@RestController
@RequestMapping("/rest/littlecat/caobao/basicinfo")
public class BasicInfoController
{
	@Autowired
	private BasicInfoBusiness basicInfoBusiness;

	private static final Logger logger = LoggerFactory.getLogger(BasicInfoController.class);

	@GetMapping(value = "/provinces")
	public RestRsp<ProvinceMO> getProvinceList()
	{
		RestRsp<ProvinceMO> result = new RestRsp<ProvinceMO>();

		try
		{
			result.getData().addAll(basicInfoBusiness.getProvinceList());
		}
		catch (LittleCatException e)
		{
			result.setCode(e.getErrorCode());
			result.setMessage(e.getMessage());
			logger.error(e.getMessage(), e);
		}
		catch (Exception e)
		{
			result.setCode(Consts.ERROR_CODE_UNKNOW);
			result.setMessage(e.getMessage());
			logger.error(e.getMessage(), e);
		}

		return result;
	}

	@GetMapping(value = "/citys")
	public RestRsp<CityMO> getCityList(@RequestParam("province") String province)
	{
		RestRsp<CityMO> result = new RestRsp<CityMO>();

		try
		{
			result.getData().addAll(basicInfoBusiness.getCityByProvince(province));
		}
		catch (LittleCatException e)
		{
			result.setCode(e.getErrorCode());
			result.setMessage(e.getMessage());
			logger.error(e.getMessage(), e);
		}
		catch (Exception e)
		{
			result.setCode(Consts.ERROR_CODE_UNKNOW);
			result.setMessage(e.getMessage());
			logger.error(e.getMessage(), e);
		}

		return result;
	}

	@GetMapping(value = "/areas")
	public RestRsp<AreaMO> getAreaList(@RequestParam("city") String city)
	{
		RestRsp<AreaMO> result = new RestRsp<AreaMO>();

		try
		{
			result.getData().addAll(basicInfoBusiness.getAreaByCity(city));
		}
		catch (LittleCatException e)
		{
			result.setCode(e.getErrorCode());
			result.setMessage(e.getMessage());
			logger.error(e.getMessage(), e);
		}
		catch (Exception e)
		{
			result.setCode(Consts.ERROR_CODE_UNKNOW);
			result.setMessage(e.getMessage());
			logger.error(e.getMessage(), e);
		}

		return result;
	}

}
