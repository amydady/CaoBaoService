package com.littlecat.goods.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.littlecat.cbb.common.BaseMO;

public class GoodsDetailMO extends BaseMO
{
	
	public static class GoodsDetailMapper implements RowMapper<GoodsDetailMO>
	{
		@Override
		public GoodsDetailMO mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			GoodsDetailMO mo = new GoodsDetailMO();
			
			
			
			return mo;
		}
	}

}
