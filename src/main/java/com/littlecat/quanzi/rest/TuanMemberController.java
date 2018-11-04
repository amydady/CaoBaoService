package com.littlecat.quanzi.rest;

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
import com.littlecat.quanzi.business.TuanMemberBusiness;
import com.littlecat.quanzi.model.TuanMemberMO;

@RestController
@RequestMapping("/rest/littlecat/caobao/quanzi/tuanmember")
public class TuanMemberController
{// TOTO:此controller可能不需要，待定。
	private static final Logger logger = LoggerFactory.getLogger(TuanMemberController.class);

	@Autowired
	private TuanMemberBusiness tuanMemberBusiness;

	@PostMapping(value = "/add")
	public RestRsp<String> add(@RequestBody TuanMemberMO mo)
	{
		RestRsp<String> result = new RestRsp<String>();

		try
		{
			result.getData().add(tuanMemberBusiness.add(mo));
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

	@PutMapping(value = "/setLastActiveTime/{id}")
	public RestSimpleRsp setLastActiveTime(@PathVariable String id)
	{
		RestSimpleRsp result = new RestSimpleRsp();

		try
		{
			tuanMemberBusiness.setLastActiveTime(id);
			;
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
	public RestRsp<TuanMemberMO> getList(@RequestBody QueryParam queryParam)
	{
		RestRsp<TuanMemberMO> result = new RestRsp<TuanMemberMO>();

		try
		{
			List<TuanMemberMO> mos = new ArrayList<TuanMemberMO>();
			int totalNum = tuanMemberBusiness.getList(queryParam, mos);

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

	@GetMapping(value = "/getCurrentEnableTuan")
	public RestRsp<TuanMemberMO> getCurrentEnableTuan(@RequestParam String terminalUserId)
	{
		RestRsp<TuanMemberMO> result = new RestRsp<TuanMemberMO>();

		try
		{
			result.getData().add(tuanMemberBusiness.getCurrentEnableTuan(terminalUserId));
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
