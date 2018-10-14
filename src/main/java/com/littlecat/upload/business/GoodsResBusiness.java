package com.littlecat.upload.business;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.utils.FileUtil;

@Component
public class GoodsResBusiness
{
	public static final String FILE_PATH_GOODSDETAILIMG = "d:\\temp";
	private static final Logger logger = LoggerFactory.getLogger(GoodsResBusiness.class);

	public void uploadGoodsDetailImgFile(List<MultipartFile> files)
	{
		for (MultipartFile file : files)
		{
			try
			{
				uploadGoodsDetailImgFile(file);
			}
			catch (LittleCatException e)
			{
				logger.error("uploadGoodsDetailImgFile:" + e.getMessage());
				continue;
			}
		}
	}

	public void uploadGoodsDetailImgFile(MultipartFile file) throws LittleCatException
	{
		if (file.isEmpty())
		{
			logger.error("uploadGoodsDetailImgFile:the file is empty.");
			return;
		}

		try
		{
			byte[] bytes = file.getBytes();
			FileUtil.uploadFile(bytes, FILE_PATH_GOODSDETAILIMG, file.getOriginalFilename());
		}
		catch (IOException e)
		{
			throw new LittleCatException(e.getMessage(), e);
		}
	}
}
