package com.littlecat.test;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.utils.DateTimeUtil;

@RestController
@RequestMapping("/rest/test")
public class TestController
{
	private static final Logger logger = LoggerFactory.getLogger(TestController.class);

	@Autowired
	protected TestDao testDao;

	@PostMapping(value = "/add")
	public void test1(@RequestBody TestMO mo)
	{
		testDao.add(mo);
	}

	@GetMapping(value = "/a")
	public String test2()
	{
		logger.info("tihs is a info");
		logger.warn("tihs is a warn");
		logger.error("tihs is a error");

		return "test2:get:";
	}

	@PostMapping(value = "/goodsDetailImg")
	public void uploadGoodsDetailImg(HttpServletRequest request)
	{
		TestMO mo = new TestMO();
		List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("goodsDetailImg");

		try
		{
			mo.setDateTime(DateTimeUtil.getCurrentTimeForDisplay());
			mo.setImgData(Base64.encodeBase64String(files.get(0).getBytes()));
			testDao.add(mo);
		}
		catch (LittleCatException | IOException e)
		{

		}
	}

	@GetMapping(value = "/get")
	public TestMO get()
	{
		TestMO mo = new TestMO();

		return testDao.getById("3577912f-e5e4-4929-ba5a-6289c28a8062");
	}
}
