package com.littlecat.groupbuy.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.littlecat.cbb.common.BaseMO;

/**
 * 团购计划MO
 * 
 * @author amydady
 *
 */
public class GroupBuyPlanMO extends BaseMO
{
	private String goodsId;
	private String startTime;
	private String endTime;
	private long price;
	private long currentInventory;
	private int memberNum;
	private int limitBuyNum;
	private String enable;
	private String createTime;
	private String createOperatorId;

	private String deliveryAreaId; // 配送区域ID
	private String deliveryFeeRuleId; // 运费规则ID

	public String getGoodsId()
	{
		return goodsId;
	}

	public void setGoodsId(String goodsId)
	{
		this.goodsId = goodsId;
	}

	public String getStartTime()
	{
		return startTime;
	}

	public void setStartTime(String startTime)
	{
		this.startTime = startTime;
	}

	public String getEndTime()
	{
		return endTime;
	}

	public void setEndTime(String endTime)
	{
		this.endTime = endTime;
	}

	public long getPrice()
	{
		return price;
	}

	public void setPrice(long price)
	{
		this.price = price;
	}

	public int getMemberNum()
	{
		return memberNum;
	}

	public void setMemberNum(int memberNum)
	{
		this.memberNum = memberNum;
	}

	public int getLimitBuyNum()
	{
		return limitBuyNum;
	}

	public void setLimitBuyNum(int limitBuyNum)
	{
		this.limitBuyNum = limitBuyNum;
	}

	public String getEnable()
	{
		return enable;
	}

	public void setEnable(String enable)
	{
		this.enable = enable;
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

	public String getDeliveryAreaId()
	{
		return deliveryAreaId;
	}

	public void setDeliveryAreaId(String deliveryAreaId)
	{
		this.deliveryAreaId = deliveryAreaId;
	}

	public String getDeliveryFeeRuleId()
	{
		return deliveryFeeRuleId;
	}

	public void setDeliveryFeeRuleId(String deliveryFeeRuleId)
	{
		this.deliveryFeeRuleId = deliveryFeeRuleId;
	}

	public long getCurrentInventory()
	{
		return currentInventory;
	}

	public void setCurrentInventory(long currentInventory)
	{
		this.currentInventory = currentInventory;
	}

	public static class MOMapper implements RowMapper<GroupBuyPlanMO>
	{
		@Override
		public GroupBuyPlanMO mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			GroupBuyPlanMO mo = new GroupBuyPlanMO();

			mo.setId(rs.getString("id"));
			mo.setGoodsId(rs.getString("goodsId"));
			mo.setStartTime(rs.getString("startTime"));
			mo.setEndTime(rs.getString("endTime"));
			mo.setPrice(rs.getLong("price"));
			mo.setCurrentInventory(rs.getLong("currentInventory"));
			mo.setMemberNum(rs.getInt("memberNum"));
			mo.setLimitBuyNum(rs.getInt("limitBuyNum"));
			mo.setEnable(rs.getString("enable"));
			mo.setCreateTime(rs.getString("createTime"));
			mo.setCreateOperatorId(rs.getString("createOperatorId"));
			mo.setDeliveryAreaId(rs.getString("deliveryAreaId"));
			mo.setDeliveryFeeRuleId(rs.getString("deliveryFeeRuleId"));

			return mo;
		}
	}
}
