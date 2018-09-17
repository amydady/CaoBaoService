package com.littlecat.terminaluser.rest;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.littlecat.cbb.common.Consts;
import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.query.QueryParam;
import com.littlecat.cbb.rest.RestRsp;
import com.littlecat.cbb.rest.RestSimpleRsp;
import com.littlecat.system.model.SysOperatorMO;
import com.littlecat.terminaluser.business.TerminalUserBusiness;
import com.littlecat.terminaluser.model.TerminalUserMO;

@RestController
@RequestMapping("/rest/littlecat/caobao/terminaluser")
public class TerminalUserController
{
	@Autowired
	private TerminalUserBusiness terminalUserBusiness;
	
	private Logger logger = LoggerFactory.getLogger(TerminalUserController.class);
	

	@GetMapping(value = "/getbywxcode/{wxCode}")
	public RestRsp<TerminalUserMO> getByWXCode(@PathVariable String wxCode)
	{
		RestRsp<SysOperatorMO> result = new RestRsp<SysOperatorMO>();
		
		try
		{
			SysOperatorMO sysOperatorMO = sysOperatorBusiness.getById(id);
			result.getData().add(sysOperatorMO);
		}
		catch(LittleCatException e)
		{
			result.setCode(e.getErrorCode());
			result.setMessage(e.getMessage());
			logger.error(e.getMessage(),e);
		}
		catch (Exception e)
		{
			result.setCode(Consts.ERROR_CODE_UNKNOW);
			result.setMessage(e.getMessage());
			logger.error(e.getMessage(),e);
		}
		
		return result;
	}

	@PutMapping(value = "/sysoperator")
	public RestSimpleRsp modify(@RequestBody SysOperatorMO mo)
	{
		RestSimpleRsp result = new RestSimpleRsp();

		try
		{
			sysOperatorBusiness.modify(mo);
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

	@PostMapping(value = "/sysoperator")
	public RestRsp<String> add(@RequestBody SysOperatorMO mo)
	{
		RestRsp<String> result = new RestRsp<String>();

		try
		{
			result.getData().add(sysOperatorBusiness.add(mo));
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

	@PostMapping(value = "/sysoperators")
	public RestRsp<SysOperatorMO> getList(@RequestBody QueryParam queryParam)
	{
		RestRsp<SysOperatorMO> result = new RestRsp<SysOperatorMO>();
		
		try
		{
			List<SysOperatorMO> mos = new ArrayList<SysOperatorMO>();
			int totalNum = sysOperatorBusiness.getList(queryParam, mos);
			result.setTotalNum(totalNum);
			result.getData().addAll(mos);
		}
		catch(LittleCatException e)
		{
			result.setCode(e.getErrorCode());
			result.setMessage(e.getMessage());
			logger.error(e.getMessage(),e);
		}
		catch (Exception e)
		{
			result.setCode(Consts.ERROR_CODE_UNKNOW);
			result.setMessage(e.getMessage());
			logger.error(e.getMessage(),e);
		}
		
		return result;
	}
}
