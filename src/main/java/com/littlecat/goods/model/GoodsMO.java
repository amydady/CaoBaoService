package com.littlecat.goods.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.littlecat.cbb.common.BaseMO;

public class GoodsMO extends BaseMO
{
	private String classifyId;
	private String supplierId;
	private String name;
	private String remark;
	private long price; // 单位为厘
	private String enable;
	private String createOperatorid;
	private String createTime;
	private int createYear;
	private int createMonth;
	private long totalInventory;	//当前总库存
	private long secKillInventory;	//当前秒杀库存
	private long groupBuyInventory;	//当前团购库存

	public String getClassifyId()
	{
		return classifyId;
	}

	public void setClassifyId(String classifyId)
	{
		this.classifyId = classifyId;
	}

	public String getSupplierId()
	{
		return supplierId;
	}

	public void setSupplierId(String supplierId)
	{
		this.supplierId = supplierId;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public long getPrice()
	{
		return price;
	}

	public void setPrice(long price)
	{
		this.price = price;
	}

	public String getEnable()
	{
		return enable;
	}

	public void setEnable(String enable)
	{
		this.enable = enable;
	}

	public String getRemark()
	{
		return remark;
	}

	public void setRemark(String remark)
	{
		this.remark = remark;
	}

	public String getCreateOperatorid()
	{
		return createOperatorid;
	}

	public void setCreateOperatorid(String createOperatorid)
	{
		this.createOperatorid = createOperatorid;
	}

	public String getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(String createTime)
	{
		this.createTime = createTime;
	}

	public int getCreateYear()
	{
		return createYear;
	}

	public void setCreateYear(int createYear)
	{
		this.createYear = createYear;
	}

	public int getCreateMonth()
	{
		return createMonth;
	}

	public void setCreateMonth(int createMonth)
	{
		this.createMonth = createMonth;
	}

	public long getCurrentTotalInventory()
	{
		return totalInventory;
	}

	public void setCurrentTotalInventory(long currentTotalInventory)
	{
		this.totalInventory = currentTotalInventory;
	}

	public long getCurrentSecKillInventory()
	{
		return secKillInventory;
	}

	public void setCurrentSecKillInventory(long currentSecKillInventory)
	{
		this.secKillInventory = currentSecKillInventory;
	}

	public long getCurrentGroupBuyInventory()
	{
		return groupBuyInventory;
	}

	public void setCurrentGroupBuyInventory(long currentGroupBuyInventory)
	{
		this.groupBuyInventory = currentGroupBuyInventory;
	}

	public static class GoodsMapper implements RowMapper<GoodsMO>
	{
		@Override
		public GoodsMO mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			GoodsMO mo = new GoodsMO();
		

			return mo;
		}
	}

}
