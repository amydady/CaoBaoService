package com.littlecat.commission.rest;

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
import com.littlecat.cbb.rest.RestRsp;
import com.littlecat.cbb.rest.RestSimpleRsp;
import com.littlecat.commission.business.CommissionApplyAccountBusiness;
import com.littlecat.commission.business.CommissionCalcBusiness;
import com.littlecat.commission.model.CommissionApplyMO;
import com.littlecat.commission.model.CommissionCalcMO;
import com.littlecat.commission.model.CommissionReport;

@RestController
@RequestMapping("/rest/littlecat/caobao/commission/calc")
public class CommissionCalcController
{
	@Autowired
	private CommissionCalcBusiness commissionCalcBusiness;
	
	@Autowired
	private CommissionApplyAccountBusiness commissionApplyAccountBusiness;

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

	@PostMapping(value = "/apply")
	public RestSimpleRsp apply(@RequestBody CommissionApplyMO reqInfo)
	{
		RestSimpleRsp result = new RestSimpleRsp();

		try
		{
			commissionCalcBusiness.apply(reqInfo);
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

	@PutMapping(value = "/pay")
	public RestSimpleRsp pay(@RequestBody List<String> ids)
	{
		RestSimpleRsp result = new RestSimpleRsp();

		try
		{
			commissionCalcBusiness.pay(ids);
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

	@GetMapping(value = "/getList")
	public RestRsp<CommissionCalcMO> getList(@RequestParam @Nullable String tuanZhangId, @RequestParam @Nullable String state)
	{
		RestRsp<CommissionCalcMO> result = new RestRsp<CommissionCalcMO>();

		try
		{
			result.getData().addAll(commissionCalcBusiness.getList(tuanZhangId, state));
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
	
	@GetMapping(value = "/getApplyInfoByApplyTime")
	public RestRsp<CommissionApplyMO> getApplyInfoByApplyTime(@RequestParam String tuanZhangId, @RequestParam String applyTime)
	{
		RestRsp<CommissionApplyMO> result = new RestRsp<CommissionApplyMO>();

		try
		{
			result.getData().add(commissionApplyAccountBusiness.getByApplyTime(tuanZhangId, applyTime));
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
	
	@GetMapping(value = "/getLatestApplyInfo")
	public RestRsp<CommissionApplyMO> getLatestApplyInfo(@RequestParam String tuanZhangId)
	{
		RestRsp<CommissionApplyMO> result = new RestRsp<CommissionApplyMO>();

		try
		{
			result.getData().add(commissionApplyAccountBusiness.getLatest(tuanZhangId));
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

	@GetMapping(value = "/getCommissionReport")
	public RestRsp<CommissionReport> getCommissionReport(@RequestParam String tuanZhangId)
	{
		RestRsp<CommissionReport> result = new RestRsp<CommissionReport>();

		try
		{
			result.getData().add(commissionCalcBusiness.getCommissionReport(tuanZhangId));
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
