package com.littlecat.system.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.littlecat.cbb.query.QueryParam;
import com.littlecat.consts.TableName;
import com.littlecat.system.model.SysOperatorMO;

@Component
public class SysOperatorDao
{
	@Autowired
    protected JdbcTemplate jdbcTemplate;
	
	public SysOperatorMO login(String id,String pwd)
	{
		String sql = "select * from " + TableName.SysOperator.getName()
        + " where (wxCode =? or username=? or email=? or mobile=?) and password=password(?)";
		
		//jdbcTemplate.query(sql, new Object[] {id,id,id,id,pwd},)
		
		return null;
	}
	
	public String addSysOperator(SysOperatorMO mo)
	{
		String sql = "insert into " + TableName.SysOperator.getName()
		        + "(id,username,password,name,wxCode,email,mobile) values(?,?,?,?,?,?,?)";
		
		int ret = jdbcTemplate.update(sql, new Object[]
		{ mo.getId(), mo.getUsername(), mo.getPassword(), mo.getName(), mo.getWxCode(), mo.getEmail(),
		        mo.getMobile() });

		if (ret != 1)
		{
			return null;
		}

		return mo.getId();
	}

	public boolean modifySysOperator(SysOperatorMO mo)
	{
		String sql = "update " + TableName.SysOperator.getName() + " set name = ?,wxCode = ?,email = ?,mobile = ? where id = ?";
		int ret = jdbcTemplate.update(sql, new Object[]
		{ mo.getName(), mo.getWxCode(), mo.getEmail(), mo.getMobile(), mo.getId() });

		return ret == 1;
	}

	public boolean deleteSysOperator(String id)
	{
		String sql = "delete from " + TableName.SysOperator.getName() + " where id = ?";
		int ret = jdbcTemplate.update(sql, new Object[]
		{ id });

		return ret == 1;
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
		String sql = "select count(*) totalNum,* from "
		        + TableName.SysOperator.getName();
		
		return -1;
	}
}
