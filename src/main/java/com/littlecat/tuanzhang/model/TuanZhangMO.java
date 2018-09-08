package com.littlecat.tuanzhang.model;

import java.util.ArrayList;
import java.util.List;

import com.littlecat.terminaluser.model.TerminalUserMO;

/**
 * 团长MO
 * 
 * @author amydady
 *
 */
public class TuanZhangMO extends TerminalUserMO
{
	private List<TuanClassifyMO> TuanClassifyList = new ArrayList<TuanClassifyMO>();

	public TuanZhangMO()
	{
		super();
	}

	public List<TuanClassifyMO> getTuanClassifyList()
	{
		return TuanClassifyList;
	}

	public void setTuanClassifyList(List<TuanClassifyMO> tuanClassifys)
	{
		TuanClassifyList = tuanClassifys;
	}
}
