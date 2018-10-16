package com.littlecat.groupbuy.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import com.littlecat.cbb.common.BaseMO;
import com.littlecat.cbb.utils.DateTimeUtil;
import com.littlecat.common.consts.BooleanTag;

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
	private String enable;
	private long planInventory; // 规划用于团购的库存
	private long currentInventory;
	private int memberNum;// 成团人员数量
	private int limitBuyNum; // 每个人的限购数量
	private String createTime;
	private String createOperatorId;

	private String deliveryAreaId; // 配送区域ID
	private String deliveryFeeRuleId; // 运费规则ID

	// for view
	private String goodsName;
	private String goodsMainImgData;
	private long goodsPrice;

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

	public String getEnable()
	{
		return enable;
	}

	public void setEnable(String enable)
	{
		this.enable = enable;
	}

	public long getPlanInventory()
	{
		return planInventory;
	}

	public void setPlanInventory(long planInventory)
	{
		this.planInventory = planInventory;
	}

	public String getGoodsName()
	{
		return goodsName;
	}

	public void setGoodsName(String goodsName)
	{
		this.goodsName = goodsName;
	}

	public String getGoodsMainImgData()
	{
		return goodsMainImgData;
	}

	public void setGoodsMainImgData(String goodsMainImgData)
	{
		this.goodsMainImgData = goodsMainImgData;
	}

	public long getGoodsPrice()
	{
		return goodsPrice;
	}

	public void setGoodsPrice(long goodsPrice)
	{
		this.goodsPrice = goodsPrice;
	}

	public static class MOMapper implements RowMapper<GroupBuyPlanMO>
	{
		private static final Logger logger = LoggerFactory.getLogger(MOMapper.class);

		@Override
		public GroupBuyPlanMO mapRow(ResultSet rs, int rowNum) throws SQLException
		{

			GroupBuyPlanMO mo = new GroupBuyPlanMO();

			mo.setId(rs.getString("id"));
			mo.setGoodsId(rs.getString("goodsId"));
			mo.setStartTime(rs.getString("startTime"));
			mo.setEndTime(rs.getString("endTime"));
			mo.setPrice(rs.getLong("price"));

			try
			{
				long startTime = DateTimeUtil.defaultDateFormat.parse(mo.getStartTime()).getTime();
				long endTime = DateTimeUtil.defaultDateFormat.parse(mo.getEndTime()).getTime();
				long now = DateTimeUtil.defaultDateFormat.parse(DateTimeUtil.getCurrentTimeForDisplay()).getTime();

				if (now >= startTime && now <= endTime)
				{
					mo.setEnable(BooleanTag.Y.name());
				}
				else
				{
					mo.setEnable(BooleanTag.N.name());
				}
			}
			catch (ParseException e)
			{
				logger.error("Parse Time Exception,SecKillPlanId:{},startTime:{},endTime:{}", mo.getId(), mo.getStartTime(), mo.getEndTime());
				mo.setEnable(BooleanTag.N.name());
			}

			mo.setCurrentInventory(rs.getLong("currentInventory"));

			// TODO:planInventory

			mo.setMemberNum(rs.getInt("memberNum"));
			mo.setLimitBuyNum(rs.getInt("limitBuyNum"));
			mo.setCreateTime(rs.getString("createTime"));
			mo.setCreateOperatorId(rs.getString("createOperatorId"));
			mo.setDeliveryAreaId(rs.getString("deliveryAreaId"));
			mo.setDeliveryFeeRuleId(rs.getString("deliveryFeeRuleId"));
			mo.setGoodsName(rs.getString("goodsName"));
			mo.setGoodsMainImgData(rs.getString("goodsMainImgData"));
			mo.setGoodsPrice(rs.getLong("goodsPrice"));

			return mo;
		}
	}
}
