package com.littlecat.goods.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.littlecat.cbb.common.BaseMO;
import com.littlecat.cbb.query.ConditionItem;
import com.littlecat.cbb.query.ConditionOperatorType;
import com.littlecat.cbb.query.QueryCondition;
import com.littlecat.cbb.query.QueryParam;
import com.littlecat.cbb.utils.SpringUtil;
import com.littlecat.goods.business.GoodsDetailImgsBusiness;

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
	private String mainImgData = "";
	private BigDecimal price; // 单位为厘
	private BigDecimal currentInventory;
	private String enable;
	private String hasSecKillPlan; // 当前是否有秒杀计划
	private String hasGroupBuyPlan; // 当前是否有拼购计划
	private String createOperatorId;
	private String deliveryAreaId; // 配送区域ID
	private String deliveryFeeRuleId; // 运费规则ID
	private String createTime;

	// for view
	private List<GoodsDetailImgsMO> detailImgs;

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

	public BigDecimal getPrice()
	{
		return price;
	}

	public void setPrice(BigDecimal price)
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

	public BigDecimal getCurrentInventory()
	{
		return currentInventory;
	}

	public void setCurrentInventory(BigDecimal currentInventory)
	{
		this.currentInventory = currentInventory;
	}

	public String getMainImgData()
	{
		return mainImgData;
	}

	public void setMainImgData(String mainImgData)
	{
		this.mainImgData = mainImgData;
	}

	public List<GoodsDetailImgsMO> getDetailImgs()
	{
		return detailImgs;
	}

	public void setDetailImgs(List<GoodsDetailImgsMO> detailImgs)
	{
		this.detailImgs = detailImgs;
	}

	public static class MOMapper implements RowMapper<GoodsMO>
	{
		private static final GoodsDetailImgsBusiness goodsDetailImgsBusiness = SpringUtil.getBean(GoodsDetailImgsBusiness.class);

		@Override
		public GoodsMO mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			GoodsMO mo = new GoodsMO();

			mo.setId(rs.getString("id"));
			mo.setClassifyId(rs.getString("classifyId"));
			mo.setSupplierId(rs.getString("supplierId"));
			mo.setName(rs.getString("name"));
			mo.setSummaryDescription(rs.getString("summaryDescription"));
			mo.setMainImgData(rs.getString("mainImgData"));
			mo.setPrice(rs.getBigDecimal("price"));
			mo.setCurrentInventory(rs.getBigDecimal("currentInventory"));
			mo.setEnable(rs.getString("enable"));
			mo.setHasSecKillPlan(rs.getString("hasSecKillPlan"));
			mo.setHasGroupBuyPlan(rs.getString("hasGroupBuyPlan"));
			mo.setCreateOperatorId(rs.getString("createOperatorId"));
			mo.setDeliveryAreaId(rs.getString("deliveryAreaId"));
			mo.setDeliveryFeeRuleId(rs.getString("deliveryFeeRuleId"));
			mo.setCreateTime(rs.getString("createTime"));

			// 查询明细图片信息
			QueryParam queryParam = new QueryParam();
			QueryCondition condition = new QueryCondition();
			condition.getCondItems().add(new ConditionItem("goodsId", mo.getId(), ConditionOperatorType.equal));
			queryParam.setCondition(condition);
			
			queryParam.setSortFields("sortNum");
			List<GoodsDetailImgsMO> mos = new ArrayList<GoodsDetailImgsMO>();
			goodsDetailImgsBusiness.getList(queryParam, mos);
			mo.setDetailImgs(mos);

			return mo;
		}
	}

}
