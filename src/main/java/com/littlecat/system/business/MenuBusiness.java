package com.littlecat.system.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.query.ConditionItem;
import com.littlecat.cbb.query.ConditionOperatorType;
import com.littlecat.cbb.query.QueryCondition;
import com.littlecat.cbb.query.QueryParam;
import com.littlecat.system.dao.MenuDao;
import com.littlecat.system.model.MenuMO;

@Component
@Transactional
public class MenuBusiness
{
	@Autowired
	private MenuDao menuDao;

	private static final String FIELD_NAME_PID = "pid";
	private static final String FIELD_NAME_SORTNUM = "sortNum";

	public List<MenuMO> getByPid(String pid) throws LittleCatException
	{
		List<MenuMO> menus = new ArrayList<MenuMO>();

		QueryParam queryParam = new QueryParam();
		QueryCondition condition = new QueryCondition();
		condition.getCondItems().add(new ConditionItem(FIELD_NAME_PID, pid, ConditionOperatorType.equal));
		queryParam.setCondition(condition);

		queryParam.setSortFields(FIELD_NAME_SORTNUM);

		menuDao.getList(queryParam, menus);

		return menus;
	}
}
