package com.littlecat.terminaluser.rest;

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
import org.springframework.web.bind.annotation.RestController;

import com.littlecat.cbb.common.Consts;
import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.query.QueryParam;
import com.littlecat.cbb.rest.RestRsp;
import com.littlecat.cbb.rest.RestSimpleRsp;
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
		RestRsp<TerminalUserMO> result = new RestRsp<TerminalUserMO>();

		try
		{
			TerminalUserMO mo = terminalUserBusiness.getByWXCode(wxCode);
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

	@GetMapping(value = "/getbyid/{id}")
	public RestRsp<TerminalUserMO> getById(@PathVariable String id)
	{
		RestRsp<TerminalUserMO> result = new RestRsp<TerminalUserMO>();

		try
		{
			TerminalUserMO mo = terminalUserBusiness.getById(id);
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

	@PutMapping(value = "/modify")
	public RestSimpleRsp modify(@RequestBody TerminalUserMO mo)
	{
		RestSimpleRsp result = new RestSimpleRsp();

		try
		{
			terminalUserBusiness.modify(mo);
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
	public RestRsp<String> add(@RequestBody TerminalUserMO mo)
	{
		RestRsp<String> result = new RestRsp<String>();

		try
		{
			result.getData().add(terminalUserBusiness.add(mo));
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
	public RestRsp<TerminalUserMO> getList(@RequestBody QueryParam queryParam)
	{
		RestRsp<TerminalUserMO> result = new RestRsp<TerminalUserMO>();

		try
		{
			List<TerminalUserMO> mos = new ArrayList<TerminalUserMO>();
			int totalNum = terminalUserBusiness.getList(queryParam, mos);
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