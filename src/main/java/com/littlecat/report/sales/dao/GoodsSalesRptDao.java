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

@Component
public class GoodsSalesRptDao
{
	@Autowired
	protected JdbcTemplate jdbcTemplate;

	private final String TABLE_NAME_ORDERDETAIL = TableName.OrderDetail.getName();
	private final String TABLE_NAME_ORDER = TableName.Order.getName();

	public GoodsSalesCountByDayRptMO GoodsSalesCountByDayRpt(String day)
	{
		if (StringUtil.isEmpty(day))
		{
			day = DateTimeUtil.getCurrentTimeForDisplay();
		}
		
		StringBuilder sql = new StringBuilder()
				.append("select a.goodsName,sum(a.goodsNum) goodsCount from ").append(TABLE_NAME_ORDERDETAIL + " a ")
				.append(" inner join ").append(TABLE_NAME_ORDER + " b on a.orderId=b.id ")
				.append(" where date_format(b.payTime,'%Y-%m-%d') = date_format(?,'%Y-%m-%d') ")
				.append(" order by goodsCount desc limit 10")
				.append(" group by a.goodsName ");

		return jdbcTemplate.queryForObject(sql.toString(), new RowMapper<GoodsSalesCountByDayRptMO>()
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
