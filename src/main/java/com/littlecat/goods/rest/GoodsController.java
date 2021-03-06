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
import org.springframework.web.bind.annotation.PathVariable;
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
import com.littlecat.cbb.utils.UUIDUtil;
import com.littlecat.common.consts.ServiceConsts;
import com.littlecat.goods.business.GoodsBusiness;
import com.littlecat.goods.business.GoodsDetailImgsBusiness;
import com.littlecat.goods.model.GoodsDetailImgsMO;
import com.littlecat.goods.model.GoodsMO;

/**
 * 普通商品Rest接口
 * 
 * @author amydady
 *
 */
@RestController
@RequestMapping("/rest/littlecat/caobao/goods")
public class GoodsController
{
	@Autowired
	private GoodsBusiness goodsBusiness;

	@Autowired
	private GoodsDetailImgsBusiness goodsDetailImgsBusiness;

	private static final Logger logger = LoggerFactory.getLogger(GoodsController.class);

	@GetMapping(value = "/getbyid")
	public RestRsp<GoodsMO> getById(@RequestParam String id)
	{
		RestRsp<GoodsMO> result = new RestRsp<GoodsMO>();

		try
		{
			GoodsMO mo = goodsBusiness.getById(id);
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

	@GetMapping(value = "/getSummayInfoById")
	public RestRsp<GoodsMO> getSummayInfoById(@RequestParam String id)
	{
		RestRsp<GoodsMO> result = new RestRsp<GoodsMO>();

		try
		{
			GoodsMO mo = goodsBusiness.getSummayInfoById(id);
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
	public RestSimpleRsp modify(@RequestBody GoodsMO mo)
	{
		RestSimpleRsp result = new RestSimpleRsp();

		try
		{
			goodsBusiness.modify(mo);
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
	public RestRsp<String> add(@RequestBody GoodsMO mo)
	{
		RestRsp<String> result = new RestRsp<String>();

		try
		{
			result.getData().add(goodsBusiness.add(mo));
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
	public RestRsp<GoodsMO> getList(@RequestBody QueryParam queryParam)
	{
		RestRsp<GoodsMO> result = new RestRsp<GoodsMO>();

		try
		{
			List<GoodsMO> mos = new ArrayList<GoodsMO>();
			int totalNum = goodsBusiness.getList(queryParam, mos);
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

	@PostMapping(value = "/getDetailImgList")
	public RestRsp<GoodsDetailImgsMO> getDetailImgList(@RequestBody QueryParam queryParam)
	{
		RestRsp<GoodsDetailImgsMO> result = new RestRsp<GoodsDetailImgsMO>();

		try
		{
			List<GoodsDetailImgsMO> mos = new ArrayList<GoodsDetailImgsMO>();
			int totalNum = goodsDetailImgsBusiness.getList(queryParam, mos);
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

	@PutMapping(value = "/disable/{id}")
	public RestSimpleRsp disable(@PathVariable String id)
	{
		RestSimpleRsp result = new RestSimpleRsp();

		try
		{
			goodsBusiness.disable(id);
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

	@PutMapping(value = "/batchdisable")
	public RestSimpleRsp batchDisable(@RequestBody List<String> ids)
	{
		RestSimpleRsp result = new RestSimpleRsp();

		try
		{
			goodsBusiness.disable(ids);
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
			goodsBusiness.delete(ids);
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

	@PutMapping(value = "/enable/{id}")
	public RestSimpleRsp enable(@PathVariable String id)
	{
		RestSimpleRsp result = new RestSimpleRsp();

		try
		{
			goodsBusiness.enable(id);
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

	@PutMapping(value = "/batchenable")
	public RestSimpleRsp batchEable(@RequestBody List<String> ids)
	{
		RestSimpleRsp result = new RestSimpleRsp();

		try
		{
			goodsBusiness.enable(ids);
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

	/**
	 * 上传商品的主图片
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@PostMapping(value = "/uploadmainimg/{id}")
	public RestSimpleRsp uploadMainImg(@PathVariable String id, HttpServletRequest request) throws Exception
	{
		RestSimpleRsp result = new RestSimpleRsp();
		GoodsMO mo = goodsBusiness.getById(id);

		// 上传的产品图片（主图）
		List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("goodsMainImg");

		if (CollectionUtil.isEmpty(files))
		{
			throw new LittleCatException("GoodsController:uploadmainimg:files is null.");
		}

		files.get(0).transferTo(new File(ServiceConsts.IMG_path + id));
		mo.setMainImgData(ServiceConsts.IMG_URL_BASE + id);
		goodsBusiness.modify(mo);

		return result;
	}

	@PostMapping(value = "/detailimgs/add/{goodsId}")
	public RestSimpleRsp uploadDetailImgs(@PathVariable String goodsId, @RequestParam @Nullable String title, @RequestParam @Nullable int sortNum, HttpServletRequest request)
			throws Exception
	{
		RestSimpleRsp result = new RestSimpleRsp();
		GoodsDetailImgsMO mo = new GoodsDetailImgsMO();

		mo.setId(UUIDUtil.createUUID());
		mo.setGoodsId(goodsId);
		mo.setTitle(title);
		mo.setSortNum(sortNum);

		// 上传的产品图片（详细信息图片）
		List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("goodsDetailImg");

		if (CollectionUtil.isEmpty(files))
		{
			throw new LittleCatException("GoodsController:uploadmainimg:files is null.");
		}

		files.get(0).transferTo(new File(ServiceConsts.IMG_path + mo.getId()));
		mo.setImgData(ServiceConsts.IMG_URL_BASE + mo.getId());

		goodsDetailImgsBusiness.add(mo);

		return result;
	}

	@PutMapping(value = "/detailimgs/modify/{id}")
	public RestSimpleRsp modifyDetailImgsInfo(@PathVariable String id, @RequestParam @Nullable String title, @RequestParam @Nullable int sortNum)
	{
		RestSimpleRsp result = new RestSimpleRsp();
		GoodsDetailImgsMO mo = goodsDetailImgsBusiness.getById(id);

		if (mo == null)
		{
			throw new LittleCatException("GoodsController:modifyDetailImgsInfo:get goods detailimgsmo by id return null.");
		}

		mo.setTitle(title);
		mo.setSortNum(sortNum);

		try
		{
			goodsDetailImgsBusiness.modify(mo);
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

	@PutMapping(value = "/detailimgs/batchdelete")
	public RestSimpleRsp batchDeleteDetailImgs(@RequestBody List<String> ids)
	{
		RestSimpleRsp result = new RestSimpleRsp();

		try
		{
			goodsDetailImgsBusiness.delete(ids);
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

	/**
	 * 用于微信小程序（展示普通商品列表）
	 * 
	 * @return
	 */
	@GetMapping(value = "/getList4WxApp")
	public RestRsp<GoodsMO> getList4WxApp()
	{
		RestRsp<GoodsMO> result = new RestRsp<GoodsMO>();

		try
		{
			result.getData().addAll(goodsBusiness.getList4WxApp());
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

	@GetMapping(value = "/getList4WebApp")
	public RestRsp<GoodsMO> getList4WebApp(@RequestParam @Nullable String name, @RequestParam @Nullable String enable)
	{
		RestRsp<GoodsMO> result = new RestRsp<GoodsMO>();

		try
		{
			result.getData().addAll(goodsBusiness.getList4WebApp(name, enable));
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
