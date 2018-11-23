package com.littlecat.report.sales.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.littlecat.report.sales.model.OrderSummaryByDayRptMO;

@Component
public class OrderRptDao
{
	@Autowired
	protected JdbcTemplate jdbcTemplate;

	public OrderSummaryByDayRptMO getOrderSummaryByDay(final String day)
	{
		return jdbcTemplate.execute(new CallableStatementCreator()
		{
			@Override
			public CallableStatement createCallableStatement(Connection con) throws SQLException
			{
				String storedProc = "{call sp_rpt_order_day(?)}";// 调用的sql
				CallableStatement cs = con.prepareCall(storedProc);
				cs.setString(1, day);// 设置输入参数的值
				return cs;
			}
		}, new CallableStatementCallback<OrderSummaryByDayRptMO>()
		{
			@Override
			public OrderSummaryByDayRptMO doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException
			{
				OrderSummaryByDayRptMO mo = new OrderSummaryByDayRptMO();
				cs.execute();
				ResultSet rs = cs.getResultSet();
				rs.next();
				mo.setTotal_count(rs.getBigDecimal("total_count"));
				mo.setPayed_count(rs.getBigDecimal("payed_count"));
				mo.setPayed_fee_sum(rs.getBigDecimal("payed_fee_sum"));

				rs.close();

				return mo;
			}
		});
	}
}
