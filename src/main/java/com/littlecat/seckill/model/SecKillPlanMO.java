package com.littlecat.seckill.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import com.littlecat.cbb.common.BaseMO;
import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.utils.SpringUtil;
import com.littlecat.cbb.utils.StringUtil;
import com.littlecat.goods.business.GoodsBusiness;
import com.littlecat.goods.model.GoodsDetailImgsMO;
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
	private BigDecimal price;
	private BigDecimal planInventory; // 规划库存
	private BigDecimal currentInventory; // 实际剩余库存
	private int limitBuyNum;
	private String createTime;
	private String createOperatorId;
	private String enable;
	

	private String deliveryAreaId; // 配送区域ID
	private String deliveryFeeRuleId; // 运费规则ID

	// just for view
	private String goodsName;
	private String goodsSummaryDescription;
	private BigDecimal goodsPrice;
	private BigDecimal goodsCurrentInventory;
	private String goodsMainImgData;
	private List<GoodsDetailImgsMO> goodsDetailImgs;

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

	public BigDecimal getPrice()
	{
		return price;
	}

	public void setPrice(BigDecimal price)
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

	public BigDecimal getCurrentInventory()
	{
		return currentInventory;
	}

	public void setCurrentInventory(BigDecimal currentInventory)
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

	public BigDecimal getGoodsPrice()
	{
		return goodsPrice;
	}

	public void setGoodsPrice(BigDecimal goodsPrice)
	{
		this.goodsPrice = goodsPrice;
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

	public List<GoodsDetailImgsMO> getGoodsDetailImgs()
	{
		return goodsDetailImgs;
	}

	public void setGoodsDetailImgs(List<GoodsDetailImgsMO> goodsDetailImgs)
	{
		this.goodsDetailImgs = goodsDetailImgs;
	}

	public String getGoodsSummaryDescription()
	{
		return goodsSummaryDescription;
	}

	public void setGoodsSummaryDescription(String goodsSummaryDescription)
	{
		this.goodsSummaryDescription = goodsSummaryDescription;
	}

	public BigDecimal getGoodsCurrentInventory()
	{
		return goodsCurrentInventory;
	}

	public void setGoodsCurrentInventory(BigDecimal goodsCurrentInventory)
	{
		this.goodsCurrentInventory = goodsCurrentInventory;
	}

	public BigDecimal getPlanInventory()
	{
		return planInventory;
	}

	public void setPlanInventory(BigDecimal planInventory)
	{
		this.planInventory = planInventory;
	}

	public String getEnable()
	{
		return enable;
	}

	public void setEnable(String enable)
	{
		this.enable = enable;
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
			mo.setPrice(rs.getBigDecimal("price"));
			mo.setPlanInventory(rs.getBigDecimal("planInventory"));
			mo.setCurrentInventory(rs.getBigDecimal("currentInventory"));
			mo.setLimitBuyNum(rs.getInt("limitBuyNum"));
			mo.setEnable(rs.getString("enable"));
			
			mo.setGoodsName(rs.getString("goodsName"));
			mo.setGoodsPrice(rs.getBigDecimal("goodsPrice"));
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
			mo.setStartTime(StringUtil.replace(rs.getString("startTime"), ".0", ""));
			mo.setEndTime(StringUtil.replace(rs.getString("endTime"), ".0", ""));
			mo.setPrice(rs.getBigDecimal("price"));
			mo.setPlanInventory(rs.getBigDecimal("planInventory"));
			mo.setCurrentInventory(rs.getBigDecimal("currentInventory"));
			mo.setLimitBuyNum(rs.getInt("limitBuyNum"));
			mo.setCreateTime(StringUtil.replace(rs.getString("createTime"), ".0", ""));
			mo.setCreateOperatorId(rs.getString("createOperatorId"));
			mo.setDeliveryAreaId(rs.getString("deliveryAreaId"));
			mo.setDeliveryFeeRuleId(rs.getString("deliveryFeeRuleId"));

			GoodsMO goodsMo = goodsBusiness.getById(mo.getGoodsId());
			if (goodsMo != null)
			{
				mo.setGoodsName(goodsMo.getName());
				mo.setGoodsPrice(goodsMo.getPrice());
				mo.setGoodsCurrentInventory(goodsMo.getCurrentInventory());
				mo.setGoodsMainImgData(goodsMo.getMainImgData());
				mo.setGoodsDetailImgs(goodsMo.getDetailImgs());
				mo.setGoodsSummaryDescription(goodsMo.getSummaryDescription());
			}
			else
			{
				logger.error("SecKillPlanMO:MOMapper:get goods by id return empty.");
			}

			return mo;
		}
	}

}
