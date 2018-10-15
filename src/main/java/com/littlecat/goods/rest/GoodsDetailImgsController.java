package com.littlecat.goods.rest;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.littlecat.cbb.common.Consts;
import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.query.QueryParam;
import com.littlecat.cbb.rest.RestRsp;
import com.littlecat.cbb.utils.CollectionUtil;
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
public class GoodsDetailImgsController
{
	@Autowired
	private HomeImgsBusiness homeImgsBusiness;

	private static final Logger logger = LoggerFactory.getLogger(GoodsDetailImgsController.class);

	@PostMapping(value = "/upload")
	public RestRsp<String> upload(HttpServletRequest request)
	{
		RestRsp<String> result = new RestRsp<String>();
		HomeImgsMO mo = new HomeImgsMO();

		List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("goodsDetailImg");

		if (CollectionUtil.isEmpty(files))
		{
			logger.error("HomeImgsController:upload:files is null.");
		}

		try
		{
			mo.setImgData(Base64.encodeBase64String(files.get(0).getBytes()));
			result.getData().add(homeImgsBusiness.add(mo));
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
