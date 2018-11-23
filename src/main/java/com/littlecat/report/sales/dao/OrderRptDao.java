package com.littlecat.report.sales.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.littlecat.cbb.utils.DateTimeUtil;
import com.littlecat.cbb.utils.StringUtil;
import com.littlecat.common.consts.TableName;
import com.littlecat.report.sales.model.GoodsSalesCountByDayRptMO;
import com.littlecat.report.sales.model.OrderSummaryByDayRptMO;

@Component
public class OrderRptDao
{
	@Autowired
	protected JdbcTemplate jdbcTemplate;

	private final String TABLE_NAME_GOODS = TableName.Goods.getName();
	private final String TABLE_NAME_ORDERDETAIL = TableName.OrderDetail.getName();
	private final String TABLE_NAME_ORDER = TableName.Order.getName();

	public OrderSummaryByDayRptMO getOrderSummaryByDay(String day)
	{
		if (StringUtil.isEmpty(day))
		{
			day = DateTimeUtil.getCurrentTimeForDisplay();
		}
		
//		CallableStatement cs = conn.prepareCall("{call insert_user(?,?)}");
		jdbcTemplate.
//		return jdbcTemplate.queryForObject(sql.toString(), new RowMapper<GoodsSalesCountByDayRptMO>()
		{

			@Override
			public GoodsSalesCountByDayRptMO mapRow(ResultSet rs, int num) throws SQLException
			{
				GoodsSalesCountByDayRptMO mo = new GoodsSalesCountByDayRptMO();
				return mo;
			}
		});
	}

}
