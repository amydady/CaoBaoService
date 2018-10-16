package com.littlecat.system.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.query.QueryParam;
import com.littlecat.common.consts.TableName;
import com.littlecat.common.utils.DaoUtil;
import com.littlecat.system.model.MenuMO;

@Component
public class MenuDao
{
	private final String TABLE_NAME = TableName.Menu.getName();

	@Autowired
	protected JdbcTemplate jdbcTemplate;

	public int getList(QueryParam queryParam, List<MenuMO> mos) throws LittleCatException
	{
		return DaoUtil.getList(TABLE_NAME, queryParam, mos, jdbcTemplate, new MenuMO.MOMapper());
	}
}
