package com.littlecat.basicinfo.rest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.littlecat.basicinfo.business.BasicInfoBusiness;
import com.littlecat.basicinfo.model.AboutUsImgsMO;
import com.littlecat.basicinfo.model.AreaMO;
import com.littlecat.basicinfo.model.CityMO;
import com.littlecat.basicinfo.model.ProvinceMO;
import com.littlecat.basicinfo.model.QuanZiXieYiImgsMO;
import com.littlecat.cbb.common.Consts;
import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.query.QueryParam;
import com.littlecat.cbb.rest.RestRsp;
import com.littlecat.cbb.rest.RestSimpleRsp;
import com.littlecat.cbb.utils.CollectionUtil;
import com.littlecat.cbb.utils.StringUtil;
import com.littlecat.cbb.utils.UUIDUtil;
import com.littlecat.common.consts.ServiceConsts;

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

	////////////////////////// AboutUsImgs
	@GetMapping(value = "/getAboutUsImgsByid")
	public RestRsp<AboutUsImgsMO> getAboutUsImgById(@RequestParam String id)
	{
		RestRsp<AboutUsImgsMO> result = new RestRsp<AboutUsImgsMO>();

		try
		{
			result.getData().add(basicInfoBusiness.getAboutUsImgById(id));
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

	@PutMapping(value = "/deleteAboutUsImgs")
	public RestSimpleRsp deleteAboutUsImgs(@RequestBody List<String> ids)
	{
		RestSimpleRsp result = new RestSimpleRsp();

		try
		{
			basicInfoBusiness.deleteAboutUsImgs(ids);
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

	@PostMapping(value = "/uploadAboutUsImgs")
	public RestRsp<String> uploadAboutUsImgs(HttpServletRequest request, @RequestParam @Nullable int sortNum, @RequestParam @Nullable String id) throws Exception
	{
		RestRsp<String> result = new RestRsp<String>();
		AboutUsImgsMO mo = null;

		List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("homeImg");

		if (StringUtil.isNotEmpty(id))
		{
			mo = basicInfoBusiness.getAboutUsImgById(id);
			mo.setSortNum(sortNum);

			if (CollectionUtil.isNotEmpty(files))
			{
				files.get(0).transferTo(new File(ServiceConsts.IMG_path + id));
				mo.setImgData(ServiceConsts.IMG_URL_BASE + mo.getId());
				basicInfoBusiness.modifyAboutUsImgs(mo);
			}
		}
		else
		{
			if (CollectionUtil.isEmpty(files))
			{
				throw new LittleCatException("upload home img:img is null.");
			}

			mo = new AboutUsImgsMO();
			mo.setId(UUIDUtil.createUUID());
			mo.setSortNum(sortNum);

			files.get(0).transferTo(new File(ServiceConsts.IMG_path + mo.getId()));
			mo.setImgData(ServiceConsts.IMG_URL_BASE + mo.getId());

			result.getData().add(basicInfoBusiness.addAboutUsImgs(mo));
		}

		return result;
	}

	@PostMapping(value = "/getAboutUsImgsList")
	public RestRsp<AboutUsImgsMO> getAboutUsImgsList(@RequestBody QueryParam queryParam)
	{
		RestRsp<AboutUsImgsMO> result = new RestRsp<AboutUsImgsMO>();

		try
		{
			List<AboutUsImgsMO> mos = new ArrayList<AboutUsImgsMO>();
			int totalNum = basicInfoBusiness.getAboutUsImgsList(queryParam, mos);
			result.setTotalNum(totalNum);
			result.getData().addAll(mos);
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

	////////////////////////// QuanZiXieYiImgs
	@GetMapping(value = "/getQuanZiXieYiImgsByid")
	public RestRsp<QuanZiXieYiImgsMO> getQuanZiXieYiImgById(@RequestParam String id)
	{
		RestRsp<QuanZiXieYiImgsMO> result = new RestRsp<QuanZiXieYiImgsMO>();

		try
		{
			result.getData().add(basicInfoBusiness.getQuanZiXieYiImgById(id));
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

	@PutMapping(value = "/deleteQuanZiXieYiImgs")
	public RestSimpleRsp deleteQuanZiXieYiImgs(@RequestBody List<String> ids)
	{
		RestSimpleRsp result = new RestSimpleRsp();

		try
		{
			basicInfoBusiness.deleteQuanZiXieYiImgs(ids);
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

	@PostMapping(value = "/uploadQuanZiXieYi")
	public RestRsp<String> uploadQuanZiXieYi(HttpServletRequest request, @RequestParam @Nullable int sortNum, @RequestParam @Nullable String id) throws Exception
	{
		RestRsp<String> result = new RestRsp<String>();
		QuanZiXieYiImgsMO mo = null;

		List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("homeImg");

		if (StringUtil.isNotEmpty(id))
		{
			mo = basicInfoBusiness.getQuanZiXieYiImgById(id);
			mo.setSortNum(sortNum);

			if (CollectionUtil.isNotEmpty(files))
			{
				files.get(0).transferTo(new File(ServiceConsts.IMG_path + id));
				mo.setImgData(ServiceConsts.IMG_URL_BASE + mo.getId());
				basicInfoBusiness.modifyQuanZiXieYiImgs(mo);
			}
		}
		else
		{
			if (CollectionUtil.isEmpty(files))
			{
				throw new LittleCatException("upload home img:img is null.");
			}

			mo = new QuanZiXieYiImgsMO();
			mo.setId(UUIDUtil.createUUID());
			mo.setSortNum(sortNum);

			files.get(0).transferTo(new File(ServiceConsts.IMG_path + mo.getId()));
			mo.setImgData(ServiceConsts.IMG_URL_BASE + mo.getId());

			result.getData().add(basicInfoBusiness.addQuanZiXieYiImgs(mo));
		}

		return result;
	}

	@PostMapping(value = "/getQuanZiXieYiImgsList")
	public RestRsp<QuanZiXieYiImgsMO> getQuanZiXieYiImgsList(@RequestBody QueryParam queryParam)
	{
		RestRsp<QuanZiXieYiImgsMO> result = new RestRsp<QuanZiXieYiImgsMO>();

		try
		{
			List<QuanZiXieYiImgsMO> mos = new ArrayList<QuanZiXieYiImgsMO>();
			int totalNum = basicInfoBusiness.getQuanZiXieYiImgsList(queryParam, mos);
			result.setTotalNum(totalNum);
			result.getData().addAll(mos);
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
