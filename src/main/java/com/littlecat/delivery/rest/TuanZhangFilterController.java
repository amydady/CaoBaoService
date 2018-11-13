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
import com.littlecat.delivery.business.TuanZhangFilterBusiness;
import com.littlecat.delivery.model.TuanZhangFilterMO;

@RestController
@RequestMapping("/rest/littlecat/caobao/delivery/tuanzhangfilter")
public class TuanZhangFilterController
{
	@Autowired
	private TuanZhangFilterBusiness tuanZhangFilterBusiness;

	private static final Logger logger = LoggerFactory.getLogger(TuanZhangFilterController.class);

	@GetMapping(value = "/getList")
	public RestRsp<TuanZhangFilterMO> getList(@RequestParam String orderDate, @RequestParam @Nullable String tuanZhangName, @RequestParam @Nullable String tuanZhangMobile, @RequestParam @Nullable String terminalUserName, @RequestParam @Nullable String terminalUserMobile, @RequestParam String state)
	{
		RestRsp<TuanZhangFilterMO> result = new RestRsp<TuanZhangFilterMO>();

		try
		{
			result.getData().addAll(tuanZhangFilterBusiness.getList(orderDate, tuanZhangName, tuanZhangMobile, terminalUserName, terminalUserMobile, state));
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
	public RestSimpleRsp receive(@RequestBody List<String> ids)
	{
		RestSimpleRsp result = new RestSimpleRsp();

		try
		{
			tuanZhangFilterBusiness.receive(ids);
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
		HSSFSheet sheet = workbook.createSheet("团长分拣单");

		List<TuanZhangFilterMO> dataList = tuanZhangFilterBusiness.getList(orderDate, null, null, null, null, null);

		String fileName = "export-tuanzhangfilter" + ".xls";// 设置要导出的文件的名字
		// 新增数据行，并且设置单元格数据

		int rowNum = 1;

		String[] headers = { "订单日期", "团长名称", "团长手机", "用户名称", "用户手机", "订单号", "商品名称", "商品数量", "签收时间", "签收人", "是否已签收" };
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
		for (TuanZhangFilterMO data : dataList)
		{
			HSSFRow row1 = sheet.createRow(rowNum);
			row1.createCell(0).setCellValue(data.getOrderDate());
			row1.createCell(1).setCellValue(data.getTuanZhangName());
			row1.createCell(2).setCellValue(data.getTuanZhangMobile());
			row1.createCell(3).setCellValue(data.getTerminalUserName());
			row1.createCell(4).setCellValue(data.getTerminalUserMobile());
			row1.createCell(5).setCellValue(data.getOrderId());
			row1.createCell(6).setCellValue(data.getGoodsName());
			row1.createCell(7).setCellValue(String.valueOf(data.getGoodsNum()));
			row1.createCell(8).setCellValue(StringUtil.replace(data.getReceiveTime(), ".0", ""));
			row1.createCell(9).setCellValue(data.getReceiveOperatorName());
			row1.createCell(10).setCellValue(data.getState().equals("Y") ? "是" : "");
			rowNum++;
		}

		response.setContentType("application/octet-stream");
		response.setHeader("Content-disposition", "attachment;filename=" + fileName);
		response.flushBuffer();
		workbook.write(response.getOutputStream());
		workbook.close();
	}

}
