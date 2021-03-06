package com.littlecat.quanzi.rest;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.littlecat.cbb.common.Consts;
import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.rest.RestRsp;
import com.littlecat.cbb.rest.RestSimpleRsp;
import com.littlecat.goods.model.GoodsMO;
import com.littlecat.quanzi.business.TuanGoodsBusiness;
import com.littlecat.quanzi.model.TuanGoodsMO;

@RestController
@RequestMapping("/rest/littlecat/caobao/tuangoods")
public class TuanGoodsController
{
	private static final Logger logger = LoggerFactory.getLogger(TuanGoodsController.class);

	@Autowired
	private TuanGoodsBusiness tuanGoodsBusiness;

	@PostMapping(value = "/add")
	public RestRsp<String> add(@RequestBody TuanGoodsMO mo)
	{
		RestRsp<String> result = new RestRsp<String>();

		try
		{
			result.getData().add(tuanGoodsBusiness.add(mo));
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

	@PostMapping(value = "/batchadd")
	public RestRsp<String> batchAdd(@RequestBody List<TuanGoodsMO> mos)
	{
		RestRsp<String> result = new RestRsp<String>();

		try
		{
			result.getData().addAll(tuanGoodsBusiness.batchAdd(mos));
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

	@GetMapping(value = "/getbytuanid")
	public RestRsp<TuanGoodsMO> getByTuanId(@RequestParam String tuanId)
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

	@GetMapping(value = "/getunputongoodslist")
	public RestRsp<GoodsMO> getUnPutOnGoodsList(@RequestParam String tuanId)
	{
		RestRsp<GoodsMO> result = new RestRsp<GoodsMO>();

		try
		{
			result.getData().addAll(tuanGoodsBusiness.getUnPutOnGoodsList(tuanId));
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

	@PutMapping(value = "/batchdelete")
	public RestSimpleRsp batchDelete(@RequestBody List<String> idList)
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
