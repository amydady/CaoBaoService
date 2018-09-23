package com.littlecat.quanzi.rest;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.littlecat.cbb.common.Consts;
import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.rest.RestRsp;
import com.littlecat.cbb.rest.RestSimpleRsp;
import com.littlecat.quanzi.business.TuanGoodsBusiness;
import com.littlecat.quanzi.model.TuanGoodsMO;

@RestController
@RequestMapping("/rest/littlecat/caobao/tuan")
public class TuanController
{
	private static Logger logger = LoggerFactory.getLogger(TuanController.class);

	@Autowired
	private TuanGoodsBusiness tuanGoodsBusiness;

	@GetMapping(value = "/tuangoods/{tuanId}")
	public RestRsp<TuanGoodsMO> getByTuanId(@PathVariable String tuanId)
	{
		RestRsp<TuanGoodsMO> result = new RestRsp<TuanGoodsMO>();

		try
		{
			List<TuanGoodsMO> mos = new ArrayList<TuanGoodsMO>();
			int totalNum = tuanGoodsBusiness.getListByTuanId(tuanId, mos);

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

	@DeleteMapping(value = "/tuangoods/{id}")
	public RestSimpleRsp deleteById(@PathVariable String id)
	{
		RestSimpleRsp result = new RestSimpleRsp();

		try
		{
			tuanGoodsBusiness.delete(id);
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

	@PostMapping(value = "/tuangoods/batchdelete")
	public RestSimpleRsp deleteByIds(@RequestBody List<String> idList)
	{
		RestSimpleRsp result = new RestSimpleRsp();

		try
		{
			tuanGoodsBusiness.delete(idList);
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
