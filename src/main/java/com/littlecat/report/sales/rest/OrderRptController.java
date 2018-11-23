package com.littlecat.report.sales.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.littlecat.cbb.common.Consts;
import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.rest.RestRsp;
import com.littlecat.report.sales.dao.OrderRptDao;
import com.littlecat.report.sales.model.OrderSummaryByDayRptMO;

@RestController
@RequestMapping("/rest/littlecat/caobao/orderrpt")
public class OrderRptController
{
	@Autowired
	private OrderRptDao orderRptDao;

	private static final Logger logger = LoggerFactory.getLogger(OrderRptController.class);

	@GetMapping(value = "/getOrderSummaryByDay")
	public RestRsp<OrderSummaryByDayRptMO> getOrderSummaryByDay(@RequestParam @Nullable String day)
	{
		RestRsp<OrderSummaryByDayRptMO> result = new RestRsp<OrderSummaryByDayRptMO>();

		try
		{
			result.getData().add(orderRptDao.getOrderSummaryByDay(day));
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
