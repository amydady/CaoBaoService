package com.littlecat.delivery.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.littlecat.cbb.common.Consts;
import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.rest.RestRsp;
import com.littlecat.cbb.rest.RestSimpleRsp;
import com.littlecat.delivery.business.TuanZhangFilterBusiness;
import com.littlecat.delivery.model.TuanZhangFilterMO;

@RestController
@RequestMapping("/rest/littlecat/caobao/delivery/tuanzhangfilter")
public class TuanZhangFilterController
{
	@Autowired
	private TuanZhangFilterBusiness tuanZhangFilterBusiness;

	private static final Logger logger = LoggerFactory.getLogger(TuanZhangFilterController.class);

	@GetMapping(value = "/getList")
	public RestRsp<TuanZhangFilterMO> getList(@RequestParam String orderDate,@RequestParam @Nullable String tuanZhangMobile,@RequestParam @Nullable String terminalUserMobile,  @RequestParam String state)
	{
		RestRsp<TuanZhangFilterMO> result = new RestRsp<TuanZhangFilterMO>();

		try
		{
			result.getData().addAll(tuanZhangFilterBusiness.getList(orderDate, tuanZhangMobile, terminalUserMobile, state));
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

	@PutMapping(value = "/receive")
	public RestSimpleRsp receive(@RequestBody List<String> ids)
	{
		RestSimpleRsp result = new RestSimpleRsp();

		try
		{
			tuanZhangFilterBusiness.receive(ids);
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
