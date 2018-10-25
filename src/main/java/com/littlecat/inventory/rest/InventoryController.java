package com.littlecat.inventory.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.littlecat.cbb.common.Consts;
import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.rest.RestRsp;
import com.littlecat.inventory.business.GoodsInventoryBusiness;
import com.littlecat.inventory.business.SecKillInventoryBusiness;
import com.littlecat.inventory.model.GoodsInventoryMO;
import com.littlecat.inventory.model.SecKillInventoryMO;

@RestController
@RequestMapping("/rest/littlecat/caobao/inventory")
public class InventoryController
{
	@Autowired
	private GoodsInventoryBusiness goodsInventoryBusiness;
	
	@Autowired
	private SecKillInventoryBusiness secKillInventoryBusiness;

	private static final Logger logger = LoggerFactory.getLogger(InventoryController.class);

	@PostMapping(value = "/goods/add")
	public RestRsp<String> addGoodsInventory(@RequestBody GoodsInventoryMO mo)
	{
		RestRsp<String> result = new RestRsp<String>();

		try
		{
			result.getData().add(goodsInventoryBusiness.add(mo));
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
	

	@GetMapping(value = "/goods/getListByGoodsId")
	public RestRsp<GoodsInventoryMO> getListByGoodsId(@RequestParam String goodsId)
	{
		RestRsp<GoodsInventoryMO> result = new RestRsp<GoodsInventoryMO>();

		try
		{
			result.getData().addAll(goodsInventoryBusiness.getListByGoodsId(goodsId));
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
	
	@PostMapping(value = "/seckill/add")
	public RestRsp<String> addSecKillInventory(@RequestBody SecKillInventoryMO mo)
	{
		RestRsp<String> result = new RestRsp<String>();

		try
		{
			result.getData().add(secKillInventoryBusiness.add(mo));
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
	
	@GetMapping(value = "/seckill/getListByPlanId")
	public RestRsp<SecKillInventoryMO> getSecKillInventoryListByPlanId(@RequestParam String planId)
	{
		RestRsp<SecKillInventoryMO> result = new RestRsp<SecKillInventoryMO>();

		try
		{
			result.getData().addAll(secKillInventoryBusiness.getListByPlanId(planId));
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
