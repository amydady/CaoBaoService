package com.littlecat.seckill.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import com.littlecat.cbb.common.BaseMO;
import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.utils.DateTimeUtil;
import com.littlecat.cbb.utils.SpringUtil;
import com.littlecat.common.consts.BooleanTag;
import com.littlecat.goods.business.GoodsBusiness;
import com.littlecat.goods.model.GoodsMO;

/**
 * 秒杀计划MO
 * 
 * @author amydady
 *
 */
public class SecKillPlanMO extends BaseMO
{
	private String goodsId;
	private String startTime;
	private String endTime;
	private long price;
	private long currentInventory;
	private int limitBuyNum;
	private String enable;
	private String timeIsValid;// 秒杀计划的时间窗口是否有效（Y、N）
	private String createTime;
	private String createOperatorId;

	private String deliveryAreaId; // 配送区域ID
	private String deliveryFeeRuleId; // 运费规则ID

	// just for view
	private String goodsName;
	private long goodsPrice;
	private String goodsMainImgData;
	private String createOperatorName;

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

	public String getGoodsName()
	{
		return goodsName;
	}

	public void setGoodsName(String goodsName)
	{
		this.goodsName = goodsName;
	}

	public long getGoodsPrice()
	{
		return goodsPrice;
	}

	public void setGoodsPrice(long goodsPrice)
	{
		this.goodsPrice = goodsPrice;
	}

	public String getTimeIsValid()
	{
		return timeIsValid;
	}

	public void setTimeIsValid(String timeIsValid)
	{
		this.timeIsValid = timeIsValid;
	}

	public String getCreateOperatorName()
	{
		return createOperatorName;
	}

	public void setCreateOperatorName(String createOperatorName)
	{
		this.createOperatorName = createOperatorName;
	}

	public String getGoodsMainImgData()
	{
		return goodsMainImgData;
	}

	public void setGoodsMainImgData(String goodsMainImgData)
	{
		this.goodsMainImgData = goodsMainImgData;
	}
	public static class MOMapper4WebList implements RowMapper<SecKillPlanMO>
	{
		@Override
		public SecKillPlanMO mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			SecKillPlanMO mo = new SecKillPlanMO();

			mo.setId(rs.getString("id"));
			mo.setGoodsId(rs.getString("goodsId"));
			mo.setStartTime(rs.getString("startTime"));
			mo.setEndTime(rs.getString("endTime"));
			mo.setCreateTime(rs.getString("createTime"));
			mo.setPrice(rs.getLong("price"));
			mo.setCurrentInventory(rs.getLong("currentInventory"));
			mo.setGoodsName(rs.getString("goodsName"));
			mo.setGoodsPrice(rs.getLong("goodsPrice"));
			mo.setGoodsMainImgData(rs.getString("goodsMainImgData"));

			return mo;
		}

	}

	public static class MOMapper implements RowMapper<SecKillPlanMO>
	{
		private static final Logger logger = LoggerFactory.getLogger(MOMapper.class);
		private static final GoodsBusiness goodsBusiness = SpringUtil.getBean(GoodsBusiness.class);

		@Override
		public SecKillPlanMO mapRow(ResultSet rs, int rowNum) throws SQLException, LittleCatException
		{
			SecKillPlanMO mo = new SecKillPlanMO();

			mo.setId(rs.getString("id"));
			mo.setGoodsId(rs.getString("goodsId"));
			mo.setStartTime(rs.getString("startTime"));
			mo.setEndTime(rs.getString("endTime"));
			mo.setPrice(rs.getLong("price"));
			mo.setCurrentInventory(rs.getLong("currentInventory"));
			mo.setLimitBuyNum(rs.getInt("limitBuyNum"));
			mo.setEnable(rs.getString("enable"));

			try
			{
				long startTime = DateTimeUtil.defaultDateFormat.parse(mo.getStartTime()).getTime();
				long endTime = DateTimeUtil.defaultDateFormat.parse(mo.getEndTime()).getTime();
				long now = DateTimeUtil.defaultDateFormat.parse(DateTimeUtil.getCurrentTimeForDisplay()).getTime();

				if (now >= startTime && now <= endTime)
				{
					mo.setTimeIsValid(BooleanTag.Y.name());
				}
				else
				{
					mo.setTimeIsValid(BooleanTag.N.name());
				}
			}
			catch (ParseException e)
			{
				logger.error("Parse Time Exception,SecKillPlanId:{},startTime:{},endTime:{}", mo.getId(), mo.getStartTime(), mo.getEndTime());
				mo.setTimeIsValid(BooleanTag.N.name());
			}

			mo.setCreateTime(rs.getString("createTime"));
			mo.setCreateOperatorId(rs.getString("createOperatorId"));
			mo.setDeliveryAreaId(rs.getString("deliveryAreaId"));
			mo.setDeliveryFeeRuleId(rs.getString("deliveryFeeRuleId"));

			GoodsMO goodsMo = goodsBusiness.getById(mo.getGoodsId());
			if (goodsMo != null)
			{
				mo.setGoodsName(goodsMo.getName());
				mo.setGoodsPrice(goodsMo.getPrice());
			}
			else
			{
				logger.error("SecKillPlanMO:MOMapper:get goods by id return empty.");
			}

			return mo;
		}
	}

}
