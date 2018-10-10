package com.littlecat.groupbuy.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.littlecat.cbb.common.BaseMO;

/**
 * 团购任务实例MO
 * 
 * @author amydady
 *
 */
public class GroupBuyTaskMO extends BaseMO
{
	private String planId;
	private String createTime;
	private String createOperatorId; // 开团的消费者
	private String completeTime;// 成团时间

	public String getPlanId()
	{
		return planId;
	}

	public void setPlanId(String planId)
	{
		this.planId = planId;
	}

	public String getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(String createTime)
	{
		this.createTime = createTime;
	}

	public String getCreateOperatorId()
	{
		return createOperatorId;
	}

	public void setCreateOperatorId(String createOperatorId)
	{
		this.createOperatorId = createOperatorId;
	}
	
	public String getCompleteTime()
	{
		return completeTime;
	}

	public void setCompleteTime(String completeTime)
	{
		this.completeTime = completeTime;
	}

	public static class MOMapper implements RowMapper<GroupBuyTaskMO>
	{
		@Override
		public GroupBuyTaskMO mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			GroupBuyTaskMO mo = new GroupBuyTaskMO();

			mo.setId(rs.getString("id"));
			mo.setPlanId(rs.getString("planId"));
			mo.setCreateTime(rs.getString("createTime"));
			mo.setCreateOperatorId(rs.getString("createOperatorId"));
			mo.setCompleteTime(rs.getString("completeTime"));

			return mo;
		}
	}
}
