package com.littlecat.shoppingcart.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.littlecat.cbb.common.BaseMO;
import com.littlecat.cbb.common.Consts;
import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.utils.SpringUtil;
import com.littlecat.common.consts.BuyType;
import com.littlecat.goods.business.GoodsBusiness;
import com.littlecat.goods.model.GoodsMO;
import com.littlecat.seckill.business.SecKillPlanBusiness;
import com.littlecat.seckill.model.SecKillPlanMO;

/**
 * 购物车MO
 * 
 * @author amydady
 *
 */
public class ShoppingCartMO extends BaseMO
{
	private String terminalUserId;
	private BuyType buyType;
	private String resId;
	private long goodsNum;
	private String createTime;

	// for view
	private String goodsName;
	private String goodsMainImgData;
	private long goodsPrice;

	public String getTerminalUserId()
	{
		return terminalUserId;
	}

	public void setTerminalUserId(String terminalUserId)
	{
		this.terminalUserId = terminalUserId;
	}

	public BuyType getBuyType()
	{
		return buyType;
	}

	public void setBuyType(BuyType buyType)
	{
		this.buyType = buyType;
	}

	public String getResId()
	{
		return resId;
	}

	public void setResId(String resId)
	{
		this.resId = resId;
	}

	public long getGoodsNum()
	{
		return goodsNum;
	}

	public void setGoodsNum(long goodsNum)
	{
		this.goodsNum = goodsNum;
	}

	public String getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(String createTime)
	{
		this.createTime = createTime;
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

	public static class MOMapper implements RowMapper<ShoppingCartMO>
	{
		private static final SecKillPlanBusiness secKillPlanBusiness = SpringUtil.getBean(SecKillPlanBusiness.class);
		private static final GoodsBusiness goodsBusiness = SpringUtil.getBean(GoodsBusiness.class);

		@Override
		public ShoppingCartMO mapRow(ResultSet rs, int rowNum) throws SQLException, LittleCatException
		{
			ShoppingCartMO mo = new ShoppingCartMO();

			mo.setId(rs.getString("id"));
			mo.setTerminalUserId(rs.getString("terminalUserId"));
			mo.setBuyType(BuyType.valueOf(rs.getString("buyType")));
			mo.setResId(rs.getString("resId"));
			mo.setGoodsNum(rs.getLong("goodsNum"));
			mo.setCreateTime(rs.getString("createTime"));

			String goodsId = mo.getResId();
			
			if(mo.getBuyType()==BuyType.seckill)
			{
				SecKillPlanMO secKillPlanMO = secKillPlanBusiness.getById(mo.getResId());
				
				if (secKillPlanMO == null)
				{
					throw new LittleCatException(Consts.ERROR_CODE_UNKNOW, "ShoppingCartMO:mapRow:get secKillPlan by id return null,planid=" + mo.getResId());
				}
				
				goodsId = secKillPlanMO.getGoodsId();
			}
			
			// 设置关联的产品信息
			GoodsMO goodsMo = goodsBusiness.getById(goodsId);
			if (goodsMo == null)
			{
				throw new LittleCatException(Consts.ERROR_CODE_UNKNOW, "ShoppingCartMO:mapRow:get goods by id return null,goodsid=" + mo.getResId());
			}
			mo.setGoodsName(goodsMo.getName());
			mo.setGoodsMainImgData(goodsMo.getMainImgData());
			mo.setGoodsPrice(goodsMo.getPrice());

			return mo;
		}
	}

}
