package com.littlecat.commission.rest;

import java.util.ArrayList;
import java.util.List;

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

import com.littlecat.cbb.common.Consts;
import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.query.QueryParam;
import com.littlecat.cbb.rest.RestRsp;
import com.littlecat.cbb.rest.RestSimpleRsp;
import com.littlecat.commission.business.CommissionCalcBusiness;
import com.littlecat.commission.model.CommissionCalcMO;

@RestController
@RequestMapping("/rest/littlecat/caobao/commission/calc")
public class CommissionCalcController
{
	@Autowired
	private CommissionCalcBusiness commissionCalcBusiness;

	private static final Logger logger = LoggerFactory.getLogger(CommissionCalcController.class);

	@GetMapping(value = "/getbyid")
	public RestRsp<CommissionCalcMO> getById(@RequestParam String id)
	{
		RestRsp<CommissionCalcMO> result = new RestRsp<CommissionCalcMO>();

		try
		{
			result.getData().add(commissionCalcBusiness.getById(id));
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

	@GetMapping(value = "/doCalc")
	public RestSimpleRsp doCalc()
	{
		RestSimpleRsp result = new RestSimpleRsp();

		try
		{
			commissionCalcBusiness.doCalc();
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
	public RestSimpleRsp add(@RequestBody List<CommissionCalcMO> mos)
	{
		RestSimpleRsp result = new RestSimpleRsp();

		try
		{
			commissionCalcBusiness.add(mos);
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

	@PutMapping(value = "/batchPay")
	public RestSimpleRsp batchPay(@RequestBody List<String> ids)
	{
		RestSimpleRsp result = new RestSimpleRsp();

		try
		{
			commissionCalcBusiness.batchPay(ids);
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

	@PutMapping(value = "/modify")
	public RestSimpleRsp modify(@RequestBody CommissionCalcMO mo)
	{
		RestSimpleRsp result = new RestSimpleRsp();

		try
		{
			commissionCalcBusiness.modify(mo);
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

	@GetMapping(value = "/getListByTuanZhangId")
	public RestRsp<CommissionCalcMO> getListByTuanZhangId(@RequestParam @Nullable String tuanZhangId, @RequestParam @Nullable Boolean hasPayed)
	{
		RestRsp<CommissionCalcMO> result = new RestRsp<CommissionCalcMO>();

		try
		{
			result.getData().addAll(commissionCalcBusiness.getList(tuanZhangId, hasPayed));
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
	public RestRsp<CommissionCalcMO> getList(@RequestBody QueryParam queryParam)
	{
		RestRsp<CommissionCalcMO> result = new RestRsp<CommissionCalcMO>();

		try
		{
			List<CommissionCalcMO> mos = new ArrayList<CommissionCalcMO>();
			int totalNum = commissionCalcBusiness.getList(queryParam, mos);
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
