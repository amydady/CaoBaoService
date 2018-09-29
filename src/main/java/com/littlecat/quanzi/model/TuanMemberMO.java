package com.littlecat.quanzi.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.littlecat.cbb.common.BaseMO;
import com.littlecat.cbb.utils.DateTimeUtil;
import com.littlecat.common.consts.BooleanTag;

/**
 * 团成员（粉丝）MO
 * 
 * @author amydady
 *
 */
public class TuanMemberMO extends BaseMO
{
	// 粉丝失效周期（当前时间与最后活跃时间相差的天数）
	private static final int MEMBER_DISABLE_DAYS = 30;

	private String tuanId;
	private String terminalUserId;
	private String firstJoinTime; // 首次加入时间
	private String lastActiveTime; // 最后活跃时间
	private String enable;

	public TuanMemberMO()
	{
		super();
	}

	public String getTuanId()
	{
		return tuanId;
	}

	public void setTuanId(String tuanId)
	{
		this.tuanId = tuanId;
	}

	public String getTerminalUserId()
	{
		return terminalUserId;
	}

	public void setTerminalUserId(String terminalUserId)
	{
		this.terminalUserId = terminalUserId;
	}

	public String getFirstJoinTime()
	{
		return firstJoinTime;
	}

	public void setFirstJoinTime(String firstJoinTime)
	{
		this.firstJoinTime = firstJoinTime;
	}

	public String getLastActiveTime()
	{
		return lastActiveTime;
	}

	public void setLastActiveTime(String lastActiveTime)
	{
		this.lastActiveTime = lastActiveTime;
	}

	public String getEnable()
	{
		return enable;
	}

	public void setEnable(String enable)
	{
		this.enable = enable;
	}

	public static class MOMapper implements RowMapper<TuanMemberMO>
	{
		@Override
		public TuanMemberMO mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			TuanMemberMO mo = new TuanMemberMO();

			mo.setId(rs.getString("id"));
			mo.setTuanId(rs.getString("tuanId"));
			mo.setTerminalUserId(rs.getString("terminalUserId"));
			mo.setFirstJoinTime(rs.getString("firstJoinTime"));
			mo.setLastActiveTime(rs.getString("lastActiveTime"));

			int unActiveDays = DateTimeUtil.getDurationDays(Long.valueOf(mo.getLastActiveTime()), DateTimeUtil.getCurrentTime());
			if (unActiveDays > MEMBER_DISABLE_DAYS)
			{
				mo.setEnable(BooleanTag.N.name());
			}
			else
			{
				mo.setEnable(BooleanTag.Y.name());
			}

			return mo;
		}
	}
}
