package com.littlecat.inventory.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.littlecat.common.consts.TableName;
import com.littlecat.inventory.model.GoodsInventoryMO;

@Component
public class GoodsInventoryDao
{
	@Autowired
	protected JdbcTemplate jdbcTemplate;

	private final String TABLE_NAME = TableName.GoodsInventory.getName();
	private static final String MODEL_NAME = GoodsInventoryMO.class.getSimpleName();
}
