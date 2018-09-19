package com.littlecat.basicinfo.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.littlecat.basicinfo.model.ProvinceMO;
import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.query.QueryParam;
import com.littlecat.common.consts.TableName;
import com.littlecat.common.utils.DaoUtil;

@Component
public class ProvinceDao
{
	private final String TABLE_NAME = TableName.Province.getName();
	
	@Autowired
    protected JdbcTemplate jdbcTemplate;
	
	public int getList(List<ProvinceMO> mos) throws LittleCatException
	{
		return DaoUtil.getList(TABLE_NAME, new QueryParam(), mos, jdbcTemplate, new ProvinceMO.MOMapper());
	}
}
