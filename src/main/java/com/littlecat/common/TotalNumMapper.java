package com.littlecat.common;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.littlecat.cbb.common.Consts;

public class TotalNumMapper implements RowMapper<Integer>
{

	@Override
	public Integer mapRow(ResultSet rs, int rowNum) throws SQLException
	{
		return rs.getInt(Consts.COMMON_DB_RESULT_FIELDS_TOTALNUM);
	}

}
