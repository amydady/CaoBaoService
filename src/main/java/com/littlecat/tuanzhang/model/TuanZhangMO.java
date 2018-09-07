package com.littlecat.tuanzhang.model;

import java.util.ArrayList;
import java.util.List;

import com.littlecat.terminaluser.model.TerminalUserMO;

/**
 * 团长MO
 * amydady
 *
 */
public class TuanZhangMO extends TerminalUserMO
{
	private List<TuanClassifyMO> TuanClassifyList = new ArrayList<TuanClassifyMO>();
	private List<TuanMO> tuanList = new ArrayList<TuanMO>();
	
	public TuanZhangMO()
	{
		super();
	}

	public List<TuanClassifyMO> getTuanClassifys()
	{
		return TuanClassifyList;
	}

	public void setTuanClassifys(List<TuanClassifyMO> tuanClassifys)
	{
		TuanClassifyList = tuanClassifys;
	}

	public List<TuanMO> getTuans()
	{
		return tuanList;
	}

	public void setTuans(List<TuanMO> tuans)
	{
		this.tuanList = tuans;
	}
}
