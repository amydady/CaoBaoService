package com.littlecat.tuanzhang.model;

import java.util.ArrayList;
import java.util.List;

import com.littlecat.cbb.base.BaseMO;
import com.littlecat.terminaluser.model.TerminalUserMO;

/**
 * 团MO
 * amydady
 *
 */
public class TuanMO extends BaseMO
{
	private String name;
	private String remark;
	private TuanClassifyMO tuanClassify;
	private String createTime;
	
	private List<TerminalUserMO> terminalUserMembers = new ArrayList<TerminalUserMO>();

	
	public TuanMO()
	{
		super();
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getRemark()
	{
		return remark;
	}

	public void setRemark(String remark)
	{
		this.remark = remark;
	}

	public TuanClassifyMO getTuanClassify()
	{
		return tuanClassify;
	}

	public void setTuanClassify(TuanClassifyMO tuanClassify)
	{
		this.tuanClassify = tuanClassify;
	}

	public String getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(String createTime)
	{
		this.createTime = createTime;
	}

	public List<TerminalUserMO> getTerminalUserMembers()
	{
		return terminalUserMembers;
	}

	public void setTerminalUserMembers(List<TerminalUserMO> terminalUserMembers)
	{
		this.terminalUserMembers = terminalUserMembers;
	}
	
	
}
