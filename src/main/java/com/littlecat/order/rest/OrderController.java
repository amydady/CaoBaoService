package com.littlecat.order.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

import com.littlecat.cbb.common.Consts;
import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.query.QueryParam;
import com.littlecat.cbb.rest.RestRsp;
import com.littlecat.cbb.rest.RestSimpleRsp;
import com.littlecat.order.business.OrderBusiness;
import com.littlecat.order.model.OrderCreateReqInfo;
import com.littlecat.order.model.OrderMO;
import com.littlecat.order.model.OrderReturnReqInfo;

@RestController
@RequestMapping("/rest/littlecat/caobao/order")
public class OrderController
{
	@Autowired
	private OrderBusiness orderBusiness;

	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

	@PostMapping(value = "/create")
	public RestRsp<Map<String,String>> create(@RequestBody OrderCreateReqInfo orderCreateReqInfo)
	{
		RestRsp<Map<String,String>> result = new RestRsp<Map<String,String>>();

		try
		{
			result.getData().add(orderBusiness.addOrder(orderCreateReqInfo.getOrderMO(), orderCreateReqInfo.getOrderDetailMOs()));
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

	@GetMapping(value = "/getById")
	public RestRsp<OrderMO> getById(@RequestParam String id)
	{
		RestRsp<OrderMO> result = new RestRsp<OrderMO>();

		try
		{
			OrderMO mo = orderBusiness.getOrderById(id);
			result.getData().add(mo);
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
	
	@GetMapping(value = "/unifiedOrder")
	public RestRsp<String> unifiedOrder(@RequestParam String id)
	{
		RestRsp<String> result = new RestRsp<String>();

		try
		{
			result.getData().add(orderBusiness.unifiedOrder(id));
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

	@PutMapping(value = "/payOrder/{id}")
	public RestSimpleRsp payOrder(@PathVariable String id)
	{
		RestSimpleRsp result = new RestSimpleRsp();

		try
		{
			orderBusiness.payOrder(id);
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

	@PutMapping(value = "/terminalUserReceive/{id}")
	public RestSimpleRsp terminalUserReceive(@PathVariable String id)
	{
		RestSimpleRsp result = new RestSimpleRsp();

		try
		{
			orderBusiness.terminalUserReceive(id);
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

	@PutMapping(value = "/tuiKuanShenqing")
	public RestSimpleRsp tuiKuanShenqing(@RequestBody OrderReturnReqInfo req)
	{
		RestSimpleRsp result = new RestSimpleRsp();

		try
		{
			orderBusiness.tuiKuanShenqing(req);
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

	@PutMapping(value = "/completeTuiKuan")
	public RestSimpleRsp completeTuiKuan(@RequestBody OrderReturnReqInfo req)
	{
		RestSimpleRsp result = new RestSimpleRsp();

		try
		{
			orderBusiness.completeTuiKuan(req);
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
	
	@PutMapping(value = "/cancelTuiKuan")
	public RestSimpleRsp cancelTuiKuan(@RequestBody OrderReturnReqInfo req)
	{
		RestSimpleRsp result = new RestSimpleRsp();

		try
		{
			orderBusiness.cancelTuiKuan(req);
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
	public RestRsp<OrderMO> getList(@RequestBody QueryParam queryParam)
	{
		RestRsp<OrderMO> result = new RestRsp<OrderMO>();

		try
		{
			List<OrderMO> mos = new ArrayList<OrderMO>();
			int totalNum = orderBusiness.getOrderList(queryParam, mos);
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
	
	@GetMapping(value = "/getListForWebApp")
	public RestRsp<OrderMO> getListForWebApp(@RequestParam @Nullable String id,@RequestParam @Nullable String shareTuanZhangName, @RequestParam @Nullable String deliveryTuanZhangName, @RequestParam @Nullable String terminalUserName, @RequestParam @Nullable String state, @RequestParam boolean curDay)
	{
		RestRsp<OrderMO> result = new RestRsp<OrderMO>();

		try
		{
			result.getData().addAll(orderBusiness.getList(id, shareTuanZhangName, deliveryTuanZhangName, terminalUserName, state, curDay));
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
	

	@PutMapping(value = "/cancel/{id}")
	public RestSimpleRsp cancel(@PathVariable String id)
	{
		RestSimpleRsp result = new RestSimpleRsp();

		try
		{
			orderBusiness.cancel(id);
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
