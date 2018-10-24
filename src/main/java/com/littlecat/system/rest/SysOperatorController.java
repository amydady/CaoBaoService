package com.littlecat.system.rest;

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
import com.littlecat.cbb.query.QueryParam;
import com.littlecat.cbb.rest.RestRsp;
import com.littlecat.cbb.rest.RestSimpleRsp;
import com.littlecat.system.business.SysOperatorBusiness;
import com.littlecat.system.model.ChangePasswordReqInfo;
import com.littlecat.system.model.LoginReqInfo;
import com.littlecat.system.model.SysOperatorMO;

@RestController
@RequestMapping("/rest/littlecat/caobao/sys/operator")
public class SysOperatorController
{
	@Autowired
	private SysOperatorBusiness sysOperatorBusiness;

	private static final Logger logger = LoggerFactory.getLogger(SysOperatorController.class);

	@PostMapping(value = "/login")
	public RestRsp<SysOperatorMO> login(@RequestBody LoginReqInfo loginReqInfo)
	{
		RestRsp<SysOperatorMO> result = new RestRsp<SysOperatorMO>();

		try
		{
			SysOperatorMO sysOperatorMO = sysOperatorBusiness.login(loginReqInfo.getId(), loginReqInfo.getPwd());
			result.getData().add(sysOperatorMO);
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

	@PostMapping(value = "/changepassword")
	public RestSimpleRsp changePassword(@RequestBody ChangePasswordReqInfo changePasswordReqInfo)
	{
		RestSimpleRsp result = new RestSimpleRsp();

		try
		{
			sysOperatorBusiness.changePassword(changePasswordReqInfo.getId(), changePasswordReqInfo.getPwd());
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

	@GetMapping(value = "/getbyid")
	public RestRsp<SysOperatorMO> getById(@RequestParam String id)
	{
		RestRsp<SysOperatorMO> result = new RestRsp<SysOperatorMO>();

		try
		{
			SysOperatorMO sysOperatorMO = sysOperatorBusiness.getById(id);
			result.getData().add(sysOperatorMO);
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
	public RestSimpleRsp deleteById(@PathVariable String id)
	{
		RestSimpleRsp result = new RestSimpleRsp();

		try
		{
			sysOperatorBusiness.deleteById(id);
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

	@PostMapping(value = "/add")
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

	@PostMapping(value = "/getlist")
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
	public RestSimpleRsp batchDelete(@RequestBody List<String> ids)
	{
		RestSimpleRsp result = new RestSimpleRsp();

		try
		{
			sysOperatorBusiness.deleteByIdList(ids);
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
