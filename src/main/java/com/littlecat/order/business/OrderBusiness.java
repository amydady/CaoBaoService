package com.littlecat.order.business;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.littlecat.cbb.common.Consts;
import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.query.ConditionItem;
import com.littlecat.cbb.query.ConditionOperatorType;
import com.littlecat.cbb.query.QueryCondition;
import com.littlecat.cbb.query.QueryParam;
import com.littlecat.cbb.utils.CollectionUtil;
import com.littlecat.cbb.utils.DateTimeUtil;
import com.littlecat.cbb.utils.StringUtil;
import com.littlecat.cbb.utils.UUIDUtil;
import com.littlecat.commission.business.CommissionCalcBusiness;
import com.littlecat.common.consts.BuyType;
import com.littlecat.common.consts.ErrorCode;
import com.littlecat.common.consts.InventoryChangeType;
import com.littlecat.common.consts.OrderState;
import com.littlecat.common.consts.ServiceConsts;
import com.littlecat.common.utils.WxPayUtil;
import com.littlecat.goods.business.GoodsBusiness;
import com.littlecat.goods.model.GoodsMO;
import com.littlecat.groupbuy.business.GroupBuyPlanBusiness;
import com.littlecat.groupbuy.business.GroupBuyTaskBusiness;
import com.littlecat.groupbuy.model.GroupBuyPlanMO;
import com.littlecat.groupbuy.model.GroupBuyTaskMO;
import com.littlecat.inventory.business.GoodsInventoryBusiness;
import com.littlecat.inventory.business.SecKillInventoryBusiness;
import com.littlecat.inventory.model.GoodsInventoryMO;
import com.littlecat.inventory.model.SecKillInventoryMO;
import com.littlecat.order.dao.OrderDao;
import com.littlecat.order.model.GoodsSaleRspMO;
import com.littlecat.order.model.OrderDetailMO;
import com.littlecat.order.model.OrderInventoryCheckRspMO;
import com.littlecat.order.model.OrderMO;
import com.littlecat.order.model.OrderReturnReqInfo;
import com.littlecat.quanzi.business.TuanBusiness;
import com.littlecat.quanzi.business.TuanMemberBusiness;
import com.littlecat.quanzi.model.TuanMemberMO;
import com.littlecat.seckill.business.SecKillPlanBusiness;
import com.littlecat.seckill.model.SecKillPlanMO;

@Component
@Transactional
public class OrderBusiness
{
	@Autowired
	private OrderDao orderDao;

	@Autowired
	private OrderDetailBusiness orderDetailBusiness;

	@Autowired
	private GoodsBusiness goodsBusiness;

	@Autowired
	private GroupBuyTaskBusiness groupBuyTaskBusiness;

	@Autowired
	private GroupBuyPlanBusiness groupBuyPlanBusiness;

	@Autowired
	private GoodsInventoryBusiness goodsInventoryBusiness;

	@Autowired
	private SecKillInventoryBusiness secKillInventoryBusiness;

	// @Autowired
	// private ResLockBusiness resLockBusiness;

	@Autowired
	private SecKillPlanBusiness secKillPlanBusiness;

	@Autowired
	private TuanMemberBusiness tuanMemberBusiness;

	@Autowired
	private TuanBusiness tuanBusiness;

	@Autowired
	private CommissionCalcBusiness commissionCalcBusiness;

	private static final String MODEL_NAME_ORDERDETAIL = OrderDetailMO.class.getSimpleName();
	private static final String MODEL_NAME_GROUPBUYTASK = GroupBuyTaskMO.class.getSimpleName();
	private static final String MODEL_NAME_GROUPBUYPLAN = GroupBuyPlanMO.class.getSimpleName();

	// 资源锁失效时间（秒）
	// private static final long RESLOCK_DISABLE_TIME =
	// ResLockMO.DEFAULT_DISABLE_TIME;

	// 获取资源锁超时时间（秒）
	// private static final long RESLOCK_TIMEOUTSECS =
	// ResLockMO.DEFAULT_TIMEOUTSECS;

	// 获取资源锁重试时间间隔（毫秒）
	// private static final long RESLOCK_RETRYTIMESTEP =
	// ResLockMO.DEFAULT_RETRYTIMESTEP;

	// order中团购任务字段名称
	private static final String FIELD_NAME_GROUPBUYTASKID = "groupBuyTaskId";

	public Map<String, String> addOrder(OrderMO orderMO, List<OrderDetailMO> orderDetailMOs) throws LittleCatException
	{
		if (CollectionUtil.isEmpty(orderDetailMOs))
		{
			throw new LittleCatException(ErrorCode.RequestObjectIsNull.getCode(), ErrorCode.RequestObjectIsNull.getMsg().replace("{INFO_NAME}", MODEL_NAME_ORDERDETAIL));
		}

		Map<String, String> ret = new HashMap<String, String>();

		orderMO.setState(OrderState.daifukuan);

		// 创建订单
		String orderId = orderDao.add(orderMO);

		for (OrderDetailMO orderDetail : orderDetailMOs)
		{
			orderDetail.setOrderId(orderId);
		}

		// 创建订单明细
		orderDetailBusiness.add(orderDetailMOs);

		orderMO.setId(orderId);

		try
		{
			// 调用微信统一下单接口
			String prePayId = unifiedOrderToWx(orderMO);

			if (StringUtil.isEmpty(prePayId))
			{
				throw new LittleCatException("UNI_ORDER_ERR", "调用微信统一下单接口失败");
			}

			ret.put("orderId", orderId);
			ret.put("prePayId", prePayId);
		}
		catch (Exception e)
		{
			throw new LittleCatException("UNI_ORDER_ERR", "调用微信统一下单接口失败");
		}

		return ret;
	}

	/**
	 * 基于订单的商品库存可用性检测
	 * 
	 * @param orderId
	 * @return
	 */
	public List<OrderInventoryCheckRspMO> checkInventory(String orderId)
	{
		List<OrderDetailMO> orderDetails = orderDetailBusiness.getByOrderId(orderId);
		List<OrderInventoryCheckRspMO> ret = new ArrayList<OrderInventoryCheckRspMO>();

		for (OrderDetailMO detail : orderDetails)
		{
			BigDecimal currentInventory = goodsBusiness.getById(detail.getGoodsId()).getCurrentInventory();

			if (currentInventory.compareTo(detail.getGoodsNum()) < 0)
			{
				OrderInventoryCheckRspMO rspMO = new OrderInventoryCheckRspMO();

				rspMO.setGoodsId(detail.getGoodsId());
				rspMO.setGoodsName(detail.getGoodsName());
				rspMO.setGoodsMainImgData(detail.getGoodsMainImgData());
				rspMO.setGoodsCurrentInventory(String.valueOf(currentInventory));

				ret.add(rspMO);
			}
		}

		if (CollectionUtil.isNotEmpty(ret))
		{// 库存不足，自动关闭订单
			cancel(orderId);
		}

		return ret;
	}

	/**
	 * 调用微信统一下单接口
	 * 
	 * @param id
	 * @return
	 * @throws LittleCatException
	 */
	public String unifiedOrder(String id) throws LittleCatException
	{
		try
		{
			String prePayId = unifiedOrderToWx(orderDao.getById(id));

			if (StringUtil.isEmpty(prePayId))
			{
				throw new LittleCatException("UNI_ORDER_ERR", "调用微信统一下单接口失败");
			}

			return prePayId;
		}
		catch (Exception e)
		{
			throw new LittleCatException("UNI_ORDER_ERR", "调用微信统一下单接口失败");
		}
	}

	/**
	 * 订单支付成功后的操作
	 * 
	 * @param id
	 * @throws LittleCatException
	 */
	public void payOrder(String id) throws LittleCatException
	{
		OrderMO orderMO = orderDao.getById(id);

		List<OrderDetailMO> detailMOs = orderDetailBusiness.getByOrderId(orderMO.getId());

		if (CollectionUtil.isEmpty(detailMOs))
		{
			throw new LittleCatException(ErrorCode.GetInfoFromDBReturnEmpty.getCode(), ErrorCode.GetInfoFromDBReturnEmpty.getMsg().replace("{INFO_NAME}", MODEL_NAME_ORDERDETAIL));
		}

		String currentTime = DateTimeUtil.getCurrentTimeForDisplay();
		// Map<String, List<String>> lockInfo = getResLockInfo(detailMOs);
		//
		// if (!lock(lockInfo))
		// {
		// throw new LittleCatException(ErrorCode.LockResError.getCode(),
		// ErrorCode.LockResError.getMsg());
		// }

		String groupBuyPlanId = orderMO.getGroupBuyPlanId();
		String groupBuyTaskId = orderMO.getGroupBuyTaskId();

		if (StringUtil.isNotEmpty(groupBuyTaskId))
		{// 团购订单
			GroupBuyTaskMO groupBuyTaskMO = groupBuyTaskBusiness.getById(groupBuyTaskId);
			GroupBuyPlanMO groupBuyPlanMO = groupBuyPlanBusiness.getById(groupBuyPlanId);

			if (groupBuyTaskMO == null)
			{
				throw new LittleCatException(ErrorCode.GetInfoFromDBReturnEmpty.getCode(), ErrorCode.GetInfoFromDBReturnEmpty.getMsg().replace("{INFO_NAME}", MODEL_NAME_GROUPBUYTASK));
			}

			if (groupBuyPlanMO == null)
			{
				throw new LittleCatException(ErrorCode.GetInfoFromDBReturnEmpty.getCode(), ErrorCode.GetInfoFromDBReturnEmpty.getMsg().replace("{INFO_NAME}", MODEL_NAME_GROUPBUYPLAN));
			}

			if (groupBuyTaskMO.getCurrentMemberNum() + 1 == groupBuyPlanMO.getMemberNum())
			{// 加上此单，已成团
				groupBuyTaskBusiness.completeTask(groupBuyTaskId, currentTime);
				groupBuyTaskMO.setCompleteTime(currentTime);
			}
			else
			{
				orderMO.setState(OrderState.daichengtuan);
			}

			groupBuyTaskMO.setCurrentMemberNum(groupBuyTaskMO.getCurrentMemberNum() + 1);

			groupBuyTaskBusiness.modify(groupBuyTaskMO);
		}
		else
		{// 普通商品、秒杀商品订单
			orderMO.setState(OrderState.daifahuo);
			setInventoryInfoByOrderDetail(detailMOs);
		}

		// 修改订单支付时间
		orderMO.setPayTime(currentTime);
		orderDao.modify(orderMO);

		// 设置粉丝信息（团长自购不设置）
		if (!tuanBusiness.isTuanZhang(orderMO.getTerminalUserId()))
		{
			setTuanMemberInfo(orderMO.getTerminalUserId(), orderMO.getShareTuanZhangId());
		}

		// 计算佣金
		commissionCalcBusiness.doCalc(orderMO);

		// unLock(lockInfo);
	}

	/**
	 * 用户签收
	 * 
	 * @param id
	 * @throws LittleCatException
	 */
	public void terminalUserReceive(String id) throws LittleCatException
	{
		orderDao.terminalUserReceive(id);
	}

	public void terminalUserReceive(List<String> ids) throws LittleCatException
	{
		orderDao.terminalUserReceive(ids);
	}

	/**
	 * 退款申请
	 * 
	 * @param req
	 * @throws LittleCatException
	 */
	public void tuiKuanShenqing(OrderReturnReqInfo req) throws LittleCatException
	{
		orderDao.tuiKuanShenqing(req.getId(), req.getRemark());
	}

	/**
	 * 撤销退款
	 * 
	 * @param req
	 * @throws LittleCatException
	 */
	public void cancelTuiKuan(OrderReturnReqInfo req) throws LittleCatException
	{
		orderDao.cancelTuiKuan(req.getId(), req.getRemark());
	}

	/**
	 * 完成退款
	 * 
	 * @param req
	 * @throws LittleCatException
	 */
	public void completeTuiKuan(OrderReturnReqInfo req) throws LittleCatException
	{
		orderDao.completeTuiKuan(req.getId(), req.getRemark());
	}

	/**
	 * 标记佣金计算完成（设置佣金计算时间）
	 * 
	 * @param id
	 * @throws LittleCatException
	 */
	public void completeCommissionCalc(String id) throws LittleCatException
	{
		orderDao.completeCommissionCalc(id);
	}

	/**
	 * 修改订单信息
	 * 
	 * @param mo
	 * @return
	 * @throws LittleCatException
	 */
	public void modify(OrderMO mo) throws LittleCatException
	{
		orderDao.modify(mo);
	}

	public void modify(List<OrderMO> mos) throws LittleCatException
	{
		orderDao.modify(mos);
	}

	public void afterDeliverySiteReceive(String orderDate) throws LittleCatException
	{
		orderDao.afterDeliverySiteReceive(orderDate);
	}

	/**
	 * 撤销订单
	 * 
	 * @param id
	 * @throws LittleCatException
	 */
	public void cancel(String id) throws LittleCatException
	{
		orderDao.cancel(id);
	}

	public OrderMO getOrderById(String id) throws LittleCatException
	{
		return orderDao.getById(id);
	}

	public int getOrderList(QueryParam queryParam, List<OrderMO> mos) throws LittleCatException
	{
		return orderDao.getList(queryParam, mos);
	}

	public List<OrderMO> getNeedCommissionCalcOrderList()
	{
		return orderDao.getNeedCommissionCalcList();
	}

	public List<OrderMO> getOrderListByGroupBuyTaskId(String groupBuyTaskId) throws LittleCatException
	{
		List<OrderMO> mos = new ArrayList<OrderMO>();
		QueryParam queryParam = new QueryParam();
		QueryCondition condition = new QueryCondition();
		condition.getCondItems().add(new ConditionItem(FIELD_NAME_GROUPBUYTASKID, groupBuyTaskId, ConditionOperatorType.equal));

		queryParam.setCondition(condition);

		orderDao.getList(queryParam, mos);

		return mos;
	}

	public int getBuyedNumOfSecKillPlan(String secKillPlanId, String terminalUserId) throws LittleCatException
	{
		return orderDao.getBuyedNumOfSecKillPlan(secKillPlanId, terminalUserId);
	}

	public List<OrderMO> getList(String id, String shareTuanZhangName, String deliveryTuanZhangName, String terminalUserName, String state, String createDate)
	{
		return orderDao.getList(id, shareTuanZhangName, deliveryTuanZhangName, terminalUserName, state, createDate);
	}

	public List<GoodsSaleRspMO> getGoodsSaleCount(String name, String payDate)
	{
		return orderDetailBusiness.getGoodsSaleCount(name, payDate);
	}

	/**
	 * 设置粉丝信息
	 * 
	 * @param terminalUserId
	 * @param shareTuanZhangId
	 *            当前订单中分享的团长ID
	 */
	private void setTuanMemberInfo(String terminalUserId, String shareTuanZhangId)
	{
		// 当前归属的团
		TuanMemberMO ownerTuan = tuanMemberBusiness.getCurrentEnableTuan(terminalUserId);

		if (ownerTuan != null)
		{// 用户当前有归属的团长
			if (StringUtil.isEmpty(shareTuanZhangId) || shareTuanZhangId == ownerTuan.getTuanId())
			{
				tuanMemberBusiness.updateLastActiveTime(ownerTuan.getId());
			}
		}
		else
		{// 用户当前没有归属的团长
			if (StringUtil.isNotEmpty(shareTuanZhangId))
			{
				ownerTuan = new TuanMemberMO();
				ownerTuan.setTuanId(shareTuanZhangId);
				ownerTuan.setTerminalUserId(terminalUserId);
				tuanMemberBusiness.add(ownerTuan);
			}
		}
	}

	/**
	 * 获取订单明细对应的需要锁定的资源
	 * 
	 * @param detailMOs
	 * @return
	 */
	// private Map<String, List<String>> getResLockInfo(List<OrderDetailMO>
	// detailMOs) throws LittleCatException
	// {
	// // 待锁定的资源，key：锁类型；value：锁资源的key
	// Map<String, List<String>> toBeLocked = new HashMap<String, List<String>>();
	//
	// for (OrderDetailMO orderDetailMO : detailMOs)
	// {
	// String lockType = orderDetailMO.getBuyType().getResLockType();
	// String lockKey = orderDetailMO.getGoodsId(); // 使用商品ID作为库存资源锁的key
	//
	// if (toBeLocked.containsKey(lockType))
	// {
	// toBeLocked.get(lockType).add(lockKey);
	// }
	// else
	// {
	// List<String> lockKeys = new ArrayList<String>();
	// lockKeys.add(lockKey);
	// toBeLocked.put(lockType, lockKeys);
	// }
	// }
	//
	// return toBeLocked;
	// }

	// private boolean lock(Map<String, List<String>> lockInfo)
	// {
	// for (String lockType : lockInfo.keySet())
	// {
	// ResLockMO.ResLockType resLockType = ResLockMO.ResLockType.valueOf(lockType);
	// List<ResLockMO> locks = new ArrayList<ResLockMO>();
	//
	// for (String lockKey : lockInfo.get(lockType))
	// {
	// locks.add(new ResLockMO(resLockType, lockKey,
	// DateTimeUtil.getTimeForDisplay(DateTimeUtil.getCurrentTime() +
	// RESLOCK_DISABLE_TIME * 1000, DateTimeUtil.defaultDateFormat)));
	// }
	//
	// if (!resLockBusiness.lock(locks, RESLOCK_TIMEOUTSECS, RESLOCK_RETRYTIMESTEP))
	// {
	// return false;
	// }
	// }
	//
	// return true;
	// }

	// private void unLock(Map<String, List<String>> lockInfo) throws
	// LittleCatException
	// {
	// for (String lockType : lockInfo.keySet())
	// {
	// ResLockMO.ResLockType resLockType = ResLockMO.ResLockType.valueOf(lockType);
	// List<ResLockMO> locks = new ArrayList<ResLockMO>();
	//
	// for (String resId : lockInfo.get(lockType))
	// {
	// locks.add(new ResLockMO(resLockType, resId));
	// }
	//
	// resLockBusiness.unLock(locks);
	// }
	// }

	private void setInventoryInfoByOrderDetail(List<OrderDetailMO> detailMOs) throws LittleCatException
	{
		for (OrderDetailMO orderDetailMO : detailMOs)
		{
			String goodsId = orderDetailMO.getGoodsId();

			if (orderDetailMO.getBuyType() == BuyType.normal)
			{
				GoodsInventoryMO inventoryMO = new GoodsInventoryMO();

				inventoryMO.setChangeType(InventoryChangeType.dingdankoujian);
				inventoryMO.setChangeValue(orderDetailMO.getGoodsNum().multiply(new BigDecimal("-1")));
				inventoryMO.setGoodsId(goodsId);

				goodsInventoryBusiness.add(inventoryMO);

				// 修改商品信息中的库存字段
				GoodsMO goodsMO = goodsBusiness.getById(goodsId);
				goodsMO.setCurrentInventory(goodsInventoryBusiness.getCurrentValueByGoodsId(goodsId));
				goodsBusiness.modify(goodsMO);
			}

			if (orderDetailMO.getBuyType() == BuyType.seckill)
			{
				String planId = orderDetailMO.getResId();

				SecKillInventoryMO inventoryMO = new SecKillInventoryMO();

				inventoryMO.setPlanId(planId);
				inventoryMO.setChangeType(InventoryChangeType.dingdankoujian);
				inventoryMO.setChangeValue(orderDetailMO.getGoodsNum().multiply(new BigDecimal("-1")));

				secKillInventoryBusiness.add(inventoryMO);

				// 修改商品秒杀计划信息中的库存字段
				SecKillPlanMO secKillPlanMO = secKillPlanBusiness.getById(planId);
				secKillPlanMO.setCurrentInventory(secKillInventoryBusiness.getCurrentValueByPlanId(planId));
				secKillPlanBusiness.modify(secKillPlanMO);
			}
		}
	}

	private String unifiedOrderToWx(OrderMO order) throws Exception
	{
		String body = "[品源社订单]" + order.getId();
		String appid = ServiceConsts.WX_APPID;
		String mch_id = ServiceConsts.WX_MCH_ID;
		String nonce_str = UUIDUtil.createUUID();// 随机字符串
		String out_trade_no = order.getId();// 商户订单号
		String spbill_create_ip = "47.100.218.102";// 终端IP
		String notify_url = "NONE";// 通知地址
		String trade_type = "JSAPI";// 交易类型
		String openid = order.getTerminalUserId();// 用户标识
		String fee = String.valueOf(order.getFee().multiply(new BigDecimal("100")).intValue());// 订单费用

		String unifiedPayment = "appid=" + appid + "&body=" + body + "&mch_id=" + mch_id + "&nonce_str=" + nonce_str + "&notify_url=" + notify_url + "&openid=" + openid + "&out_trade_no=" + out_trade_no + "&spbill_create_ip=" + spbill_create_ip + "&total_fee=" + fee + "&trade_type=" + trade_type + "&key=" + ServiceConsts.WX_KEY;
		// MD5运算生成签名
		String mysign = WxPayUtil.sign(unifiedPayment, Consts.CHARSET_NAME).toUpperCase();

		StringBuilder respXml = new StringBuilder()
				.append("<xml>")
				.append("<appid>" + appid + "</appid>")
				.append("<body>" + body + "</body>")
				.append("<mch_id>" + mch_id + "</mch_id>")
				.append("<nonce_str>" + nonce_str + "</nonce_str>")
				.append("<notify_url>" + notify_url + "</notify_url>")
				.append("<openid>" + openid + "</openid>")
				.append("<out_trade_no>" + out_trade_no + "</out_trade_no>")
				.append("<spbill_create_ip>" + spbill_create_ip + "</spbill_create_ip>")
				.append("<total_fee>" + fee + "</total_fee>")
				.append("<trade_type>" + trade_type + "</trade_type>")
				.append("<sign>" + mysign + "</sign>")
				.append("</xml>");

		String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";// 统一下单API接口链接
		String result = WxPayUtil.httpRequest(url, "POST", respXml.toString());

		String result_code = result.split("<result_code>")[1].split("</result_code>")[0].split("\\[")[2].split("\\]")[0];

		if (result_code.equals("FAIL"))
		{
			return null;
		}

		return result.split("<prepay_id>")[1].split("</prepay_id>")[0].split("\\[")[2].split("\\]")[0];
	}
}
