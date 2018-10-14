package com.littlecat.upload.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.rest.RestSimpleRsp;
import com.littlecat.upload.business.GoodsResBusiness;

@RestController
@RequestMapping("/rest/littlecat/caobao/upload")
public class UploadController
{
	@Autowired
	GoodsResBusiness goodsResBusiness;

	@PostMapping(value = "/goodsDetailImg")
	public RestSimpleRsp uploadGoodsDetailImg(HttpServletRequest request)
	{
		RestSimpleRsp rsp = new RestSimpleRsp();

		List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("goodsDetailImg");
		
		try
		{
			goodsResBusiness.uploadGoodsDetailImgFile(files);
		}
		catch (LittleCatException e)
		{
			rsp.setCode(e.getErrorCode());
			rsp.setMessage(e.getMessage());
		}
		
		return rsp;
	}
}
