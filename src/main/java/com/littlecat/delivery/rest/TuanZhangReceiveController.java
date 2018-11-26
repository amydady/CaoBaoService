package com.littlecat.delivery.rest;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.littlecat.cbb.common.Consts;
import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.rest.RestRsp;
import com.littlecat.cbb.rest.RestSimpleRsp;
import com.littlecat.cbb.utils.StringUtil;
import com.littlecat.delivery.business.TuanZhangReceiveBusiness;
import com.littlecat.delivery.model.TuanZhangReceiveMO;

@RestController
@RequestMapping("/rest/littlecat/caobao/delivery/tuanzhangreceive")
public class TuanZhangReceiveController
{
	@Autowired
	private TuanZhangReceiveBusiness tuanZhangReceiveBusiness;

	private static final Logger logger = LoggerFactory.getLogger(TuanZhangReceiveController.class);

	@GetMapping(value = "/getList")
	public RestRsp<TuanZhangReceiveMO> getList(@RequestParam String orderDate, @RequestParam @Nullable String tuanZhangName, @RequestParam @Nullable String tuanZhangMobile, @RequestParam String state)
	{
		RestRsp<TuanZhangReceiveMO> result = new RestRsp<TuanZhangReceiveMO>();

		try
		{
			result.getData().addAll(tuanZhangReceiveBusiness.getList(orderDate, tuanZhangName, tuanZhangMobile, state));
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

	@PutMapping(value = "/receive")
	public RestSimpleRsp receive(@RequestParam String orderDate)
	{
		RestSimpleRsp result = new RestSimpleRsp();

		try
		{
			tuanZhangReceiveBusiness.receive(orderDate);
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

	@GetMapping(value = "/export")
	public void export(@RequestParam String orderDate, HttpServletResponse response) throws Exception
	{
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("团长收货单");

		List<TuanZhangReceiveMO> dataList = tuanZhangReceiveBusiness.getList(orderDate, null, null, null);

		String fileName = "export-tuanzhangreceive" + ".xls";// 设置要导出的文件的名字
		// 新增数据行，并且设置单元格数据

		int rowNum = 1;

		String[] headers = { "订单日期", "团长名称", "团长手机", "商品名称", "商品数量", "签收时间", "签收人", "是否已签收" };
		// headers表示excel表中第一行的表头

		HSSFRow row = sheet.createRow(0);
		// 在excel表中添加表头

		for (int i = 0; i < headers.length; i++)
		{
			HSSFCell cell = row.createCell(i);
			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(text);
		}

		// 在表中存放查询到的数据放入对应的列
		for (TuanZhangReceiveMO data : dataList)
		{
			HSSFRow row1 = sheet.createRow(rowNum);
			row1.createCell(0).setCellValue(data.getOrderDate());
			row1.createCell(1).setCellValue(data.getTuanZhangName());
			row1.createCell(2).setCellValue(data.getTuanZhangMobile());
			row1.createCell(3).setCellValue(data.getGoodsName());
			row1.createCell(4).setCellValue(String.valueOf(data.getGoodsNum()));
			row1.createCell(5).setCellValue(StringUtil.replace(data.getReceiveTime(), ".0", ""));
			row1.createCell(6).setCellValue(data.getReceiveOperatorName());
			row1.createCell(7).setCellValue(data.getState().equals("Y") ? "是" : "");
			rowNum++;
		}

		response.setContentType("application/octet-stream");
		response.setHeader("Content-disposition", "attachment;filename=" + fileName);
		response.flushBuffer();
		workbook.write(response.getOutputStream());
		workbook.close();
	}

}
