package com.littlecat.delivery.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.littlecat.delivery.business.OutWarehouseBusiness;
import com.littlecat.delivery.model.OutWarehouseMO;

@RestController
@RequestMapping("/rest/littlecat/caobao/delivery/outwarehouse")
public class OutWarehouseController
{
	@Autowired
	private OutWarehouseBusiness outWarehouseBusiness;

	private static final Logger logger = LoggerFactory.getLogger(OutWarehouseController.class);

	@GetMapping(value = "/getList")
	public RestRsp<OutWarehouseMO> getList(@RequestParam String orderDate, @RequestParam String state)
	{
		RestRsp<OutWarehouseMO> result = new RestRsp<OutWarehouseMO>();

		try
		{
			result.getData().addAll(outWarehouseBusiness.getList(orderDate, state));
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

	@PutMapping(value = "/out")
	public RestSimpleRsp out(@RequestBody List<String> ids)
	{
		RestSimpleRsp result = new RestSimpleRsp();

		try
		{
			outWarehouseBusiness.out(ids);
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
