package com.littlecat.system.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.littlecat.cbb.query.QueryParam;
import com.littlecat.system.model.SysOperatorMO;

@Component
public class SysOperatorDao
{
	public SysOperatorMO addSysOperator(SysOperatorMO mo)
	{
		return null;
	}

	public SysOperatorMO modifySysOperator(SysOperatorMO mo)
	{
		return null;
	}

	public void deleteSysOperator(String id)
	{
	}

	/**
	 * 查询系统操作人员列表
	 * 
	 * @param queryParam
	 *            查询参数
	 * @param mos
	 *            返回列表信息（当前这一批，不是全部数据）
	 * @return 总记录数
	 */
	public int getSysOperatorList(QueryParam queryParam, List<SysOperatorMO> mos)
	{
		return -1;
	}
}
