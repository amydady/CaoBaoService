package com.littlecat.quanzi.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import com.littlecat.cbb.common.BaseMO;
import com.littlecat.cbb.utils.DateTimeUtil;
import com.littlecat.cbb.utils.SpringUtil;
import com.littlecat.common.consts.BooleanTag;
import com.littlecat.common.utils.SysParamUtil;
import com.littlecat.terminaluser.business.TerminalUserBusiness;
import com.littlecat.terminaluser.model.TerminalUserMO;

/**
 * 团成员（粉丝）MO
 * 
 * @author amydady
 *
 */
public class TuanMemberMO extends BaseMO
{
	private String tuanId;
	private String terminalUserId;
	private String firstJoinTime; // 首次加入时间
	private String lastActiveTime; // 最后活跃时间
	private String enable;
	
	//just for view
	
	private TerminalUserMO terminalUser;

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

	public TerminalUserMO getTerminalUser()
	{
		return terminalUser;
	}

	public void setTerminalUser(TerminalUserMO terminalUser)
	{
		this.terminalUser = terminalUser;
	}

	public static class MOMapper implements RowMapper<TuanMemberMO>
	{
		private static final int DEFAULT_MEMBER_DISABLE_DAYS = 30;
		private static final Logger logger = LoggerFactory.getLogger(TuanMemberMO.MOMapper.class);
		private static final TerminalUserBusiness terminalUserBusiness = SpringUtil.getBean(TerminalUserBusiness.class);

		@Override
		public TuanMemberMO mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			TuanMemberMO mo = new TuanMemberMO();

			mo.setId(rs.getString("id"));
			mo.setTuanId(rs.getString("tuanId"));
			mo.setTerminalUserId(rs.getString("terminalUserId"));
			mo.setFirstJoinTime(rs.getString("firstJoinTime"));
			mo.setLastActiveTime(rs.getString("lastActiveTime"));

			// 非活跃的天数（当前时间与最后活跃时间相差的天数）
			int unActiveDays = DateTimeUtil.getDurationDays(mo.getLastActiveTime());

			// 系统配置的粉丝失效周期
			int memberDisableDays = DEFAULT_MEMBER_DISABLE_DAYS;
			try
			{
				memberDisableDays = Integer.valueOf(SysParamUtil.getValueByName(SysParamUtil.PARAM_NAME_MEMBER_ENABLE_DAYS));
			}
			catch (Exception e)
			{
				logger.error("get param from db error,paramname=" + SysParamUtil.PARAM_NAME_MEMBER_ENABLE_DAYS, e);
			}

			if (unActiveDays > memberDisableDays)
			{
				mo.setEnable(BooleanTag.N.name());
			}
			else
			{
				mo.setEnable(BooleanTag.Y.name());
			}
			
			mo.setTerminalUser(terminalUserBusiness.getById(mo.getTerminalUserId()));
			
			return mo;
		}
	}
}
