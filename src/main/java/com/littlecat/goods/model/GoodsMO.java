package com.littlecat.goods.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.littlecat.cbb.common.BaseMO;

/**
 * 商品MO
 * 
 * @author amydady
 *
 */
public class GoodsMO extends BaseMO
{
	private String classifyId;
	private String supplierId;
	private String name;
	private String summaryDescription; // 产品概要描述信息
	private String detailDescription; // 详细描述信息（支持富文本）
	private String mainImgUrl;
	private long price; // 单位为厘
	private String enable;
	private String hasSecKillPlan; // 当前是否有秒杀计划
	private String hasGroupBuyPlan; // 当前是否有拼购计划
	private String createOperatorId;
	private String deliveryAreaId; // 配送区域ID
	private String deliveryFeeRuleId; // 运费规则ID
	private String createTime;

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

	public String getSummaryDescription()
	{
		return summaryDescription;
	}

	public void setSummaryDescription(String summaryDescription)
	{
		this.summaryDescription = summaryDescription;
	}

	public String getMainImgUrl()
	{
		return mainImgUrl;
	}

	public void setMainImgUrl(String mainImgUrl)
	{
		this.mainImgUrl = mainImgUrl;
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

	public String getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(String createTime)
	{
		this.createTime = createTime;
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

	public String getDetailDescription()
	{
		return detailDescription;
	}

	public void setDetailDescription(String detailDescription)
	{
		this.detailDescription = detailDescription;
	}

	public String getCreateOperatorId()
	{
		return createOperatorId;
	}

	public void setCreateOperatorId(String createOperatorId)
	{
		this.createOperatorId = createOperatorId;
	}

	public String getHasSecKillPlan()
	{
		return hasSecKillPlan;
	}

	public void setHasSecKillPlan(String hasSecKillPlan)
	{
		this.hasSecKillPlan = hasSecKillPlan;
	}

	public String getHasGroupBuyPlan()
	{
		return hasGroupBuyPlan;
	}

	public void setHasGroupBuyPlan(String hasGroupBuyPlan)
	{
		this.hasGroupBuyPlan = hasGroupBuyPlan;
	}

	public static class MOMapper implements RowMapper<GoodsMO>
	{
		@Override
		public GoodsMO mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			GoodsMO mo = new GoodsMO();
			mo.setId(rs.getString("id"));
			mo.setClassifyId(rs.getString("classifyId"));
			mo.setName(rs.getString("name"));
			mo.setSummaryDescription(rs.getString("summaryDescription"));
			mo.setDetailDescription(rs.getString("detailDescription"));
			mo.setMainImgUrl(rs.getString("mainImgUrl"));
			mo.setPrice(rs.getLong("price"));
			mo.setEnable(rs.getString("enable"));
			mo.setHasSecKillPlan(rs.getString("hasSecKillPlan"));
			mo.setHasGroupBuyPlan(rs.getString("hasGroupBuyPlan"));
			mo.setCreateOperatorId(rs.getString("createOperatorId"));
			mo.setDeliveryAreaId(rs.getString("deliveryAreaId"));
			mo.setDeliveryFeeRuleId(rs.getString("deliveryFeeRuleId"));
			mo.setCreateTime(rs.getString("createTime"));

			return mo;
		}
	}

}
