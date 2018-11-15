package com.littlecat.goods.rest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.littlecat.cbb.common.Consts;
import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.query.QueryParam;
import com.littlecat.cbb.rest.RestRsp;
import com.littlecat.cbb.rest.RestSimpleRsp;
import com.littlecat.cbb.utils.CollectionUtil;
import com.littlecat.cbb.utils.StringUtil;
import com.littlecat.cbb.utils.UUIDUtil;
import com.littlecat.common.consts.ServiceConsts;
import com.littlecat.goods.business.HomeImgsBusiness;
import com.littlecat.goods.model.HomeImgsMO;

/**
 * 首页滚动图片操作接口
 * 
 * @author amydady
 *
 */
@RestController
@RequestMapping("/rest/littlecat/caobao/homeimgs")
public class HomeImgsController
{
	@Autowired
	private HomeImgsBusiness homeImgsBusiness;

	private static final Logger logger = LoggerFactory.getLogger(HomeImgsController.class);

	@GetMapping(value = "/getbyid")
	public RestRsp<HomeImgsMO> getById(@RequestParam String id)
	{
		RestRsp<HomeImgsMO> result = new RestRsp<HomeImgsMO>();

		try
		{
			HomeImgsMO mo = homeImgsBusiness.getById(id);
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

	@PutMapping(value = "/batchdelete")
	public RestSimpleRsp batchDelete(@RequestBody List<String> ids)
	{
		RestSimpleRsp result = new RestSimpleRsp();

		try
		{
			homeImgsBusiness.delete(ids);
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

	@PostMapping(value = "/upload")
	public RestRsp<String> upload(HttpServletRequest request, @RequestParam @Nullable int sortNum, @RequestParam @Nullable String id) throws Exception
	{
		RestRsp<String> result = new RestRsp<String>();
		HomeImgsMO mo = null;

		List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("homeImg");

		if (StringUtil.isNotEmpty(id))
		{
			mo = homeImgsBusiness.getById(id);
			mo.setSortNum(sortNum);

			if (CollectionUtil.isNotEmpty(files))
			{
				files.get(0).transferTo(new File(ServiceConsts.IMG_path + id));
				mo.setImgData(ServiceConsts.IMG_URL_BASE + mo.getId());
				homeImgsBusiness.modify(mo);
			}
		}
		else
		{
			if (CollectionUtil.isEmpty(files))
			{
				throw new LittleCatException("upload home img:img is null.");
			}

			mo = new HomeImgsMO();
			mo.setId(UUIDUtil.createUUID());
			mo.setSortNum(sortNum);

			files.get(0).transferTo(new File(ServiceConsts.IMG_path + mo.getId()));
			mo.setImgData(ServiceConsts.IMG_URL_BASE + mo.getId());

			result.getData().add(homeImgsBusiness.add(mo));
		}

		return result;
	}

	@PostMapping(value = "/getList")
	public RestRsp<HomeImgsMO> getList(@RequestBody QueryParam queryParam)
	{
		RestRsp<HomeImgsMO> result = new RestRsp<HomeImgsMO>();

		try
		{
			List<HomeImgsMO> mos = new ArrayList<HomeImgsMO>();
			int totalNum = homeImgsBusiness.getList(queryParam, mos);
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
