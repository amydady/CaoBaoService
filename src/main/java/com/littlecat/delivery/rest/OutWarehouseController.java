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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.littlecat.cbb.common.Consts;
import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.rest.RestRsp;
import com.littlecat.cbb.rest.RestSimpleRsp;
import com.littlecat.cbb.utils.StringUtil;
import com.littlecat.delivery.business.OutWarehouseBusiness;
import com.littlecat.delivery.model.OutWarehouseMO;

@RestController
@RequestMapping("/rest/littlecat/caobao/delivery/outwarehouse")
public class OutWarehouseController
{
	@Autowired
	private OutWarehouseBusiness outWarehouseBusiness;

	private static final Logger logger = LoggerFactory.getLogger(OutWarehouseController.class);

	@GetMapping(value = "/getList")
	public RestRsp<OutWarehouseMO> getList(@RequestParam String orderDate, @RequestParam @Nullable String state)
	{
		RestRsp<OutWarehouseMO> result = new RestRsp<OutWarehouseMO>();

		try
		{
			result.getData().addAll(outWarehouseBusiness.getList(orderDate, state));
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

	@PutMapping(value = "/out")
	public RestSimpleRsp out(@RequestBody List<String> ids)
	{
		RestSimpleRsp result = new RestSimpleRsp();

		try
		{
			outWarehouseBusiness.out(ids);
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
		HSSFSheet sheet = workbook.createSheet("商品出仓单");

		List<OutWarehouseMO> dataList = outWarehouseBusiness.getList(orderDate, "");

		String fileName = "export-outwarehouse" + ".xls";// 设置要导出的文件的名字
		// 新增数据行，并且设置单元格数据

		int rowNum = 1;

		String[] headers = { "订单日期", "商品名称", "商品数量", "出仓时间", "出仓人", "是否已出仓" };
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
		for (OutWarehouseMO data : dataList)
		{
			HSSFRow row1 = sheet.createRow(rowNum);
			row1.createCell(0).setCellValue(data.getOrderDate());
			row1.createCell(1).setCellValue(data.getGoodsName());
			row1.createCell(2).setCellValue(String.valueOf(data.getGoodsNum()));
			row1.createCell(3).setCellValue(StringUtil.replace(data.getOutTime(), ".0", ""));
			row1.createCell(4).setCellValue(data.getOutOperatorName());
			row1.createCell(5).setCellValue(data.getState().equals("Y") ? "是" : "");
			rowNum++;
		}

		response.setContentType("application/octet-stream");
		response.setHeader("Content-disposition", "attachment;filename=" + fileName);
		response.flushBuffer();
		workbook.write(response.getOutputStream());
		workbook.close();
	}

}
