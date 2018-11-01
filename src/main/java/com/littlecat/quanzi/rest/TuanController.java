package com.littlecat.quanzi.rest;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.littlecat.cbb.common.Consts;
import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.query.QueryParam;
import com.littlecat.cbb.rest.RestRsp;
import com.littlecat.cbb.rest.RestSimpleRsp;
import com.littlecat.cbb.utils.CollectionUtil;
import com.littlecat.quanzi.business.TuanBusiness;
import com.littlecat.quanzi.model.TuanMO;
import com.littlecat.quanzi.model.TuanShenheReqInfo;

@RestController
@RequestMapping("/rest/littlecat/caobao/tuan")
public class TuanController
{
	private static final Logger logger = LoggerFactory.getLogger(TuanController.class);

	@Autowired
	private TuanBusiness tuanBusiness;

	@GetMapping(value = "/getById")
	public RestRsp<TuanMO> getById(@RequestParam String id)
	{
		RestRsp<TuanMO> result = new RestRsp<TuanMO>();

		try
		{
			TuanMO mo = tuanBusiness.getById(id);
			if (mo != null)
			{
				result.getData().add(mo);
			}
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

	@PostMapping(value = "/add")
	public RestRsp<String> add(@RequestBody TuanMO mo)
	{
		RestRsp<String> result = new RestRsp<String>();

		try
		{
			result.getData().add(tuanBusiness.add(mo));
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

	@PostMapping(value = "/modify")
	public RestSimpleRsp modify(@RequestBody TuanMO mo)
	{
		RestSimpleRsp result = new RestSimpleRsp();

		try
		{
			tuanBusiness.modify(mo);
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

	@PutMapping(value = "/delete/{id}")
	public RestSimpleRsp delete(@PathVariable String id)
	{
		RestSimpleRsp result = new RestSimpleRsp();

		try
		{
			tuanBusiness.delete(id);
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

	@PutMapping(value = "/batchdelete")
	public RestSimpleRsp batchDelete(@RequestBody List<String> ids)
	{
		RestSimpleRsp result = new RestSimpleRsp();

		try
		{
			tuanBusiness.delete(ids);
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

	@PutMapping(value = "/disable/{id}")
	public RestSimpleRsp disable(@PathVariable String id)
	{
		RestSimpleRsp result = new RestSimpleRsp();

		try
		{
			tuanBusiness.disable(id);
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

	@PutMapping(value = "/batchdisable")
	public RestSimpleRsp batchDisable(@RequestBody List<String> ids)
	{
		RestSimpleRsp result = new RestSimpleRsp();

		try
		{
			tuanBusiness.disable(ids);
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

	@PutMapping(value = "/enable/{id}")
	public RestSimpleRsp enable(@PathVariable String id)
	{
		RestSimpleRsp result = new RestSimpleRsp();

		try
		{
			tuanBusiness.enable(id);
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

	@PutMapping(value = "/batchenable")
	public RestSimpleRsp batchEable(@RequestBody List<String> ids)
	{
		RestSimpleRsp result = new RestSimpleRsp();

		try
		{
			tuanBusiness.enable(ids);
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
	
	@PutMapping(value = "/shenhe")
	public RestSimpleRsp shenHe(@RequestBody TuanShenheReqInfo tuanShenheReqInfo)
	{
		RestSimpleRsp result = new RestSimpleRsp();

		try
		{
			tuanBusiness.shenHe(tuanShenheReqInfo);
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

	@PostMapping(value = "/getList")
	public RestRsp<TuanMO> getList(@RequestBody QueryParam queryParam)
	{
		RestRsp<TuanMO> result = new RestRsp<TuanMO>();

		try
		{
			List<TuanMO> mos = new ArrayList<TuanMO>();
			int totalNum = tuanBusiness.getList(queryParam, mos);
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

	@GetMapping(value = "/getList")
	public RestRsp<TuanMO> getList(@RequestParam @Nullable String enable, @RequestParam @Nullable String name)
	{
		RestRsp<TuanMO> result = new RestRsp<TuanMO>();

		try
		{
			result.getData().addAll(tuanBusiness.getList(enable, name));
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

	@PostMapping(value = "/uploadIdCardFrontImg/{id}")
	public RestRsp<String> uploadIdCardFrontImg(@PathVariable String id, HttpServletRequest request)
	{
		RestRsp<String> result = new RestRsp<String>();
		TuanMO mo = tuanBusiness.getById(id);

		if (mo == null)
		{
			throw new LittleCatException("TuanController:uploadIdCardFrontImg:get TuanMO by id return null.");
		}

		List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("idCardFrontImg");

		if (CollectionUtil.isEmpty(files))
		{
			throw new LittleCatException("TuanController:uploadIdCardFrontImg:files is null.");
		}

		try
		{
			mo.setIdCardImgDataFront(Base64.encodeBase64String(files.get(0).getBytes()));
			tuanBusiness.modify(mo);
			result.getData().add(mo.getIdCardImgDataFront());
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

	@PostMapping(value = "/uploadIdCardBackImg/{id}")
	public RestRsp<String> uploadIdCardBackImg(@PathVariable String id, HttpServletRequest request)
	{
		RestRsp<String> result = new RestRsp<String>();
		TuanMO mo = tuanBusiness.getById(id);

		if (mo == null)
		{
			throw new LittleCatException("TuanController:uploadIdCardBackImg:get TuanMO by id return null.");
		}

		List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("idCardBackImg");

		if (CollectionUtil.isEmpty(files))
		{
			throw new LittleCatException("TuanController:uploadIdCardBackImg:files is null.");
		}

		try
		{
			mo.setIdCardImgDataBack(Base64.encodeBase64String(files.get(0).getBytes()));
			tuanBusiness.modify(mo);
			result.getData().add(mo.getIdCardImgDataBack());
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
