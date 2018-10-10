package com.littlecat.order.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.littlecat.cbb.common.BaseMO;
import com.littlecat.common.consts.OrderState;
import com.littlecat.common.model.AddressMO;

/**
 * 团购订单MO
 * 
 * @author amydady
 *
 */
public class GroupBuyOrderMO extends BaseMO
{
	private String terminalUserId;
	private OrderState state;
	private AddressMO deliveryAddress; // 发货地址信息

	private String groupBuyTaskId; // 团购任务实例ID
	private String goodsId;
	private long price;
	private long goodsNum;
	private long fee;

	private String createTime;
	private String payTime; // 付款时间
	private String groupCompleteTime; // 成团时间
	private String receiveTime;// 收货时间
	private String returnApplyTime;// 退款申请时间
	private String returnCompleteTime;// 退款完成时间
	private String groupCancelTime;// 团购解散退款时间

	public String getGroupBuyTaskId()
	{
		return groupBuyTaskId;
	}

	public void setGroupBuyTaskId(String groupBuyTaskId)
	{
		this.groupBuyTaskId = groupBuyTaskId;
	}

	public String getGoodsId()
	{
		return goodsId;
	}

	public void setGoodsId(String goodsId)
	{
		this.goodsId = goodsId;
	}

	public long getPrice()
	{
		return price;
	}

	public void setPrice(long price)
	{
		this.price = price;
	}

	public long getGoodsNum()
	{
		return goodsNum;
	}

	public void setGoodsNum(long goodsNum)
	{
		this.goodsNum = goodsNum;
	}

	public String getTerminalUserId()
	{
		return terminalUserId;
	}

	public void setTerminalUserId(String terminalUserId)
	{
		this.terminalUserId = terminalUserId;
	}

	public String getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(String createTime)
	{
		this.createTime = createTime;
	}

	public long getFee()
	{
		return fee;
	}

	public void setFee(long fee)
	{
		this.fee = fee;
	}

	public OrderState getState()
	{
		return state;
	}

	public void setState(OrderState state)
	{
		this.state = state;
	}

	public AddressMO getDeliveryAddress()
	{
		return deliveryAddress;
	}

	public void setDeliveryAddress(AddressMO deliveryAddress)
	{
		this.deliveryAddress = deliveryAddress;
	}

	public String getPayTime()
	{
		return payTime;
	}

	public void setPayTime(String payTime)
	{
		this.payTime = payTime;
	}

	public String getReceiveTime()
	{
		return receiveTime;
	}

	public void setReceiveTime(String receiveTime)
	{
		this.receiveTime = receiveTime;
	}

	public String getReturnApplyTime()
	{
		return returnApplyTime;
	}

	public void setReturnApplyTime(String returnApplyTime)
	{
		this.returnApplyTime = returnApplyTime;
	}

	public String getReturnCompleteTime()
	{
		return returnCompleteTime;
	}

	public void setReturnCompleteTime(String returnCompleteTime)
	{
		this.returnCompleteTime = returnCompleteTime;
	}

	public String getGroupCompleteTime()
	{
		return groupCompleteTime;
	}

	public void setGroupCompleteTime(String groupCompleteTime)
	{
		this.groupCompleteTime = groupCompleteTime;
	}

	public String getGroupCancelTime()
	{
		return groupCancelTime;
	}

	public void setGroupCancelTime(String groupCancelTime)
	{
		this.groupCancelTime = groupCancelTime;
	}

	public static class MOMapper implements RowMapper<GroupBuyOrderMO>
	{
		@Override
		public GroupBuyOrderMO mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			GroupBuyOrderMO mo = new GroupBuyOrderMO();

			mo.setId(rs.getString("id"));
			mo.setTerminalUserId(rs.getString("terminalUserId"));
			mo.setGroupBuyTaskId(rs.getString("groupBuyTaskId"));
			mo.setGoodsId(rs.getString("goodsId"));
			mo.setPrice(rs.getLong("price"));
			mo.setGoodsNum(rs.getLong("goodsNum"));
			mo.setFee(rs.getLong("fee"));
			mo.setCreateTime(rs.getString("createTime"));
			mo.setState(OrderState.valueOf(rs.getString("state")));

			AddressMO deliveryaddress = new AddressMO(rs.getString("provinceId"), rs.getString("cityId"), rs.getString("areaId"), rs.getString("detailInfo"));

			mo.setDeliveryAddress(deliveryaddress);
			mo.setPayTime(rs.getString("payTime"));
			mo.setGroupCompleteTime(rs.getString("groupCompleteTime"));
			mo.setReceiveTime(rs.getString("receiveTime"));
			mo.setReturnApplyTime(rs.getString("returnApplyTime"));
			mo.setReturnCompleteTime(rs.getString("returnCompleteTime"));
			mo.setGroupCancelTime(rs.getString("groupCancelTime"));

			return mo;
		}
	}

}
