package com.littlecat.basicinfo.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.littlecat.basicinfo.model.CityMO;
import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.query.QueryParam;
import com.littlecat.common.consts.TableName;
import com.littlecat.common.utils.DaoUtil;

@Component
public class CityDao
{
	private final String TABLE_NAME = TableName.City.getName();
	
	@Autowired
    protected JdbcTemplate jdbcTemplate;
	
	public int getList(QueryParam queryParam,List<CityMO> mos) throws LittleCatException
	{
		return DaoUtil.getList(TABLE_NAME, queryParam, mos, jdbcTemplate, new CityMO.MOMapper());
	}
}
