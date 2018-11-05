package com.littlecat.order.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.littlecat.cbb.common.BaseMO;
import com.littlecat.cbb.utils.SpringUtil;
import com.littlecat.common.consts.OrderState;
import com.littlecat.common.model.AddressMO;
import com.littlecat.order.business.OrderDetailBusiness;

/**
 * 订单MO
 * 
 * @author amydady
 *
 */
public class OrderMO extends BaseMO
{
	private String terminalUserId;
	private long fee;// 订单总费用（商品费用+物流费用）
	private long deliveryFee; // 物流费用（非自提点收货时填写）
	private OrderState state;
	private String deliveryTuanZhangId; // 发货接收的自提点（填团长ID）
	private AddressMO deliveryAddress; // 发货地址信息
	private String contactName;
	private String contactMobile;

	private String createTime;	//订单创建时间
	private String payTime; // 付款时间
	private String deliveryTime;// 发货时间（发货至外部物流或自提点的时间）
	private String receiveTime;// 客户收货时间
	private String deliverySiteReceiveTime;// 自提点签收时间
	private String returnApplyTime;// 退款申请时间
	private String returnCompleteTime;// 退款完成时间
	private String commissionCalcTime;// 佣金计算时间
	
	// 团购业务专用
	private String groupBuyPlanId; // 团购计划ID
	private String groupBuyTaskId; // 团购任务实例ID
	private String groupCompleteTime; // 成团时间
	private String groupCancelTime;// 团购解散退款时间
	// 团购业务专用end

	// just for view
	private List<OrderDetailMO> details = new ArrayList<OrderDetailMO>();

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

	public String getGroupBuyPlanId()
	{
		return groupBuyPlanId;
	}

	public void setGroupBuyPlanId(String groupBuyPlanId)
	{
		this.groupBuyPlanId = groupBuyPlanId;
	}

	public String getGroupBuyTaskId()
	{
		return groupBuyTaskId;
	}

	public void setGroupBuyTaskId(String groupBuyTaskId)
	{
		this.groupBuyTaskId = groupBuyTaskId;
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

	public String getContactName()
	{
		return contactName;
	}

	public void setContactName(String contactName)
	{
		this.contactName = contactName;
	}

	public String getContactMobile()
	{
		return contactMobile;
	}

	public void setContactMobile(String contactMobile)
	{
		this.contactMobile = contactMobile;
	}

	public List<OrderDetailMO> getDetails()
	{
		return details;
	}

	public void setDetails(List<OrderDetailMO> details)
	{
		this.details = details;
	}

	public long getDeliveryFee()
	{
		return deliveryFee;
	}

	public void setDeliveryFee(long deliveryFee)
	{
		this.deliveryFee = deliveryFee;
	}

	public String getDeliveryTuanZhangId()
	{
		return deliveryTuanZhangId;
	}

	public void setDeliveryTuanZhangId(String deliveryTuanZhangId)
	{
		this.deliveryTuanZhangId = deliveryTuanZhangId;
	}

	public String getDeliverySiteReceiveTime()
	{
		return deliverySiteReceiveTime;
	}

	public void setDeliverySiteReceiveTime(String deliverySiteReceiveTime)
	{
		this.deliverySiteReceiveTime = deliverySiteReceiveTime;
	}

	public String getDeliveryTime()
	{
		return deliveryTime;
	}

	public void setDeliveryTime(String deliveryTime)
	{
		this.deliveryTime = deliveryTime;
	}

	public String getCommissionCalcTime()
	{
		return commissionCalcTime;
	}

	public void setCommissionCalcTime(String commissionCalcTime)
	{
		this.commissionCalcTime = commissionCalcTime;
	}

	/**
	 * 模型映射，不包含商品明细信息
	 * 
	 * @author amydady
	 *
	 */
	public static class MOMapper implements RowMapper<OrderMO>
	{
		private static final OrderDetailBusiness orderDetailBusiness = SpringUtil.getBean(OrderDetailBusiness.class);

		@Override
		public OrderMO mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			OrderMO mo = new OrderMO();

			mo.setId(rs.getString("id"));
			mo.setTerminalUserId(rs.getString("terminalUserId"));
			mo.setCreateTime(rs.getString("createTime"));
			mo.setFee(rs.getLong("fee"));
			mo.setDeliveryFee(rs.getLong("deliveryFee"));
			mo.setState(OrderState.valueOf(rs.getString("state")));

			mo.setDeliveryTuanZhangId(rs.getString("deliveryTuanZhangId"));

			AddressMO deliveryaddress = new AddressMO(rs.getString("province"), rs.getString("city"), rs.getString("area"), rs.getString("detailInfo"));

			mo.setDeliveryAddress(deliveryaddress);
			mo.setContactName(rs.getString("contactName"));
			mo.setContactMobile(rs.getString("contactMobile"));

			mo.setPayTime(rs.getString("payTime"));
			mo.setDeliveryTime(rs.getString("deliveryTime"));
			mo.setReceiveTime(rs.getString("receiveTime"));
			mo.setDeliverySiteReceiveTime(rs.getString("deliverySiteReceiveTime"));
			mo.setReturnApplyTime(rs.getString("returnApplyTime"));
			mo.setReturnCompleteTime(rs.getString("returnCompleteTime"));
			mo.setCommissionCalcTime(rs.getString("commissionCalcTime"));

			mo.setGroupBuyPlanId(rs.getString("groupBuyPlanId"));
			mo.setGroupBuyTaskId(rs.getString("groupBuyTaskId"));
			mo.setGroupCompleteTime(rs.getString("groupCompleteTime"));
			mo.setGroupCancelTime(rs.getString("groupCancelTime"));

			// get view info
			mo.setDetails(orderDetailBusiness.getByOrderId(mo.getId()));

			return mo;
		}
	}

	public static class MOMapperWithGoodsDetail implements RowMapper<OrderMO>
	{
		private static final OrderDetailBusiness orderDetailBusiness = SpringUtil.getBean(OrderDetailBusiness.class);

		@Override
		public OrderMO mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			OrderMO mo = new OrderMO();

			mo.setId(rs.getString("id"));
			mo.setTerminalUserId(rs.getString("terminalUserId"));
			mo.setCreateTime(rs.getString("createTime"));
			mo.setFee(rs.getLong("fee"));
			mo.setDeliveryFee(rs.getLong("deliveryFee"));
			mo.setState(OrderState.valueOf(rs.getString("state")));

			mo.setDeliveryTuanZhangId(rs.getString("deliveryTuanZhangId"));

			AddressMO deliveryaddress = new AddressMO(rs.getString("province"), rs.getString("city"), rs.getString("area"), rs.getString("detailInfo"));

			mo.setDeliveryAddress(deliveryaddress);
			mo.setContactName(rs.getString("contactName"));
			mo.setContactMobile(rs.getString("contactMobile"));

			mo.setPayTime(rs.getString("payTime"));
			mo.setDeliveryTime(rs.getString("deliveryTime"));
			mo.setReceiveTime(rs.getString("receiveTime"));
			mo.setDeliverySiteReceiveTime(rs.getString("deliverySiteReceiveTime"));
			mo.setReturnApplyTime(rs.getString("returnApplyTime"));
			mo.setReturnCompleteTime(rs.getString("returnCompleteTime"));
			mo.setCommissionCalcTime(rs.getString("commissionCalcTime"));

			mo.setGroupBuyPlanId(rs.getString("groupBuyPlanId"));
			mo.setGroupBuyTaskId(rs.getString("groupBuyTaskId"));
			mo.setGroupCompleteTime(rs.getString("groupCompleteTime"));
			mo.setGroupCancelTime(rs.getString("groupCancelTime"));

			// get view info
			mo.setDetails(orderDetailBusiness.getByOrderIdWithGoodsDetail(mo.getId()));

			return mo;
		}
	}

}
