package com.littlecat.order.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.query.ConditionItem;
import com.littlecat.cbb.query.ConditionOperatorType;
import com.littlecat.cbb.query.QueryCondition;
import com.littlecat.cbb.query.QueryParam;
import com.littlecat.cbb.utils.CollectionUtil;
import com.littlecat.cbb.utils.DateTimeUtil;
import com.littlecat.cbb.utils.StringUtil;
import com.littlecat.common.consts.BuyType;
import com.littlecat.common.consts.ErrorCode;
import com.littlecat.common.consts.InventoryChangeType;
import com.littlecat.common.consts.OrderState;
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
import com.littlecat.lock.business.ResLockBusiness;
import com.littlecat.lock.model.ResLockMO;
import com.littlecat.order.dao.OrderDao;
import com.littlecat.order.model.OrderDetailMO;
import com.littlecat.order.model.OrderMO;
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

	@Autowired
	private ResLockBusiness resLockBusiness;

	@Autowired
	private SecKillPlanBusiness secKillPlanBusiness;

	@Autowired
	private TuanMemberBusiness tuanMemberBusiness;

	private static final String MODEL_NAME = OrderMO.class.getSimpleName();
	private static final String MODEL_NAME_ORDERDETAIL = OrderDetailMO.class.getSimpleName();
	private static final String MODEL_NAME_GROUPBUYTASK = GroupBuyTaskMO.class.getSimpleName();
	private static final String MODEL_NAME_GROUPBUYPLAN = GroupBuyPlanMO.class.getSimpleName();

	// 资源锁失效时间（秒）
	public static final long RESLOCK_DISABLE_TIME = ResLockMO.DEFAULT_DISABLE_TIME;

	// 获取资源锁超时时间（秒）
	public static final long RESLOCK_TIMEOUTSECS = ResLockMO.DEFAULT_TIMEOUTSECS;

	// 获取资源锁重试时间间隔（毫秒）
	public static final long RESLOCK_RETRYTIMESTEP = ResLockMO.DEFAULT_RETRYTIMESTEP;

	// order中团购任务字段名称
	private static final String FIELD_NAME_GROUPBUYTASKID = "groupBuyTaskId";

	public String addOrder(OrderMO orderMO, List<OrderDetailMO> orderDetailMOs) throws LittleCatException
	{
		if (CollectionUtil.isEmpty(orderDetailMOs))
		{
			throw new LittleCatException(ErrorCode.RequestObjectIsNull.getCode(), ErrorCode.RequestObjectIsNull.getMsg().replace("{INFO_NAME}", MODEL_NAME_ORDERDETAIL));
		}

		orderMO.setState(OrderState.daifukuan);

		// 创建订单
		String orderId = orderDao.add(orderMO);

		for (OrderDetailMO orderDetail : orderDetailMOs)
		{
			orderDetail.setOrderId(orderId);
		}

		// 创建订单明细
		orderDetailBusiness.add(orderDetailMOs);

		return orderId;
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

		if (orderMO == null)
		{
			throw new LittleCatException(ErrorCode.GetInfoFromDBReturnEmpty.getCode(), ErrorCode.GetInfoFromDBReturnEmpty.getMsg().replace("{INFO_NAME}", MODEL_NAME));
		}

		List<OrderDetailMO> detailMOs = orderDetailBusiness.getByOrderId(orderMO.getId());

		if (CollectionUtil.isEmpty(detailMOs))
		{
			throw new LittleCatException(ErrorCode.GetInfoFromDBReturnEmpty.getCode(), ErrorCode.GetInfoFromDBReturnEmpty.getMsg().replace("{INFO_NAME}", MODEL_NAME_ORDERDETAIL));
		}

		String currentTime = DateTimeUtil.getCurrentTimeForDisplay();
		Map<String, List<String>> lockInfo = getResLockInfo(detailMOs);

		if (!lock(lockInfo))
		{
			throw new LittleCatException(ErrorCode.LockResError.getCode(), ErrorCode.LockResError.getMsg());
		}

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

		orderMO.setPayTime(currentTime);
		orderDao.modify(orderMO);

		// 设置粉丝信息
		setTuanMemberInfo(orderMO.getTerminalUserId(), detailMOs);

		unLock(lockInfo);
	}

	/**
	 * 用户签收
	 * 
	 * @param id
	 * @throws LittleCatException
	 */
	public void terminalUserReceive(String id) throws LittleCatException
	{
		OrderMO mo = orderDao.getById(id);

		if (mo == null)
		{
			throw new LittleCatException(ErrorCode.GetInfoFromDBReturnEmpty.getCode(), ErrorCode.GetInfoFromDBReturnEmpty.getMsg().replace("{INFO_NAME}", MODEL_NAME));
		}

		mo.setState(OrderState.yishouhuo);
		mo.setReceiveTime(DateTimeUtil.getCurrentTimeForDisplay());

		orderDao.modify(mo);
	}

	/**
	 * 退款申请
	 * @param id
	 * @throws LittleCatException
	 */
	public void tuiKuanShenqing(String id) throws LittleCatException
	{
		OrderMO mo = orderDao.getById(id);

		if (mo == null)
		{
			throw new LittleCatException(ErrorCode.GetInfoFromDBReturnEmpty.getCode(), ErrorCode.GetInfoFromDBReturnEmpty.getMsg().replace("{INFO_NAME}", MODEL_NAME));
		}

		mo.setState(OrderState.tuikuanzhong);
		mo.setReturnApplyTime(DateTimeUtil.getCurrentTimeForDisplay());

		orderDao.modify(mo);
	}

	/**
	 * 完成退款
	 * 
	 * @param id
	 * @throws LittleCatException
	 */
	public void completeTuiKuan(String id) throws LittleCatException
	{
		OrderMO mo = orderDao.getById(id);

		if (mo == null)
		{
			throw new LittleCatException(ErrorCode.GetInfoFromDBReturnEmpty.getCode(), ErrorCode.GetInfoFromDBReturnEmpty.getMsg().replace("{INFO_NAME}", MODEL_NAME));
		}

		mo.setState(OrderState.yituikuan);
		mo.setReturnCompleteTime(DateTimeUtil.getCurrentTimeForDisplay());

		orderDao.modify(mo);
	}

	/**
	 * 标记佣金计算完成（设置佣金计算时间）
	 * @param id
	 * @throws LittleCatException
	 */
	public void completeCommissionCalc(String id) throws LittleCatException
	{
		OrderMO mo = orderDao.getById(id);

		if (mo == null)
		{
			throw new LittleCatException(ErrorCode.GetInfoFromDBReturnEmpty.getCode(), ErrorCode.GetInfoFromDBReturnEmpty.getMsg().replace("{INFO_NAME}", MODEL_NAME));
		}

		mo.setCommissionCalcTime(DateTimeUtil.getCurrentTimeForDisplay());

		orderDao.modify(mo);
	}

	/**
	 * 修改订单信息
	 * 
	 * @param mo
	 * @return
	 * @throws LittleCatException
	 */
	public void modifyOrder(OrderMO mo) throws LittleCatException
	{
		orderDao.modify(mo);
	}

	public void modifyOrder(List<OrderMO> mos) throws LittleCatException
	{
		orderDao.modify(mos);
	}

	public void deleteOrderById(String id) throws LittleCatException
	{
		orderDetailBusiness.deleteByOrderId(id);
		orderDao.delete(id);
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

	/**
	 * 设置粉丝信息
	 * 
	 * @param terminalUserId
	 * @param detailMOs
	 */
	private void setTuanMemberInfo(String terminalUserId, List<OrderDetailMO> detailMOs)
	{
		// 当前归属的团
		TuanMemberMO ownerTuan = tuanMemberBusiness.getCurrentEnableTuan(terminalUserId);
	
		if (ownerTuan != null)
		{// 用户当前有归属的团长
			tuanMemberBusiness.updateLastActiveTime(ownerTuan.getId());
		}
		else
		{// 用户当前没有归属的团长
			/**
			 * 将当前订单明细中的推荐团长设置为归属团长,如果本次订单涉及多个推荐团长，使用第一个（先到先得）
			 */
	
			for (OrderDetailMO orderDetailMO : detailMOs)
			{
				String shareTuanZhangId = orderDetailMO.getShareTuanZhangId();
				if (StringUtil.isNotEmpty(shareTuanZhangId))
				{// 当前订单中有推荐团长
					ownerTuan = new TuanMemberMO();
					ownerTuan.setTuanId(shareTuanZhangId);
					ownerTuan.setTerminalUserId(terminalUserId);
					tuanMemberBusiness.add(ownerTuan);
	
					break;
				}
			}
		}
	}

	/**
	 * 获取订单明细对应的需要锁定的资源
	 * 
	 * @param detailMOs
	 * @return
	 */
	private Map<String, List<String>> getResLockInfo(List<OrderDetailMO> detailMOs) throws LittleCatException
	{
		// 待锁定的资源，key：锁类型；value：锁资源的key
		Map<String, List<String>> toBeLocked = new HashMap<String, List<String>>();

		for (OrderDetailMO orderDetailMO : detailMOs)
		{
			String lockType = orderDetailMO.getBuyType().getResLockType();
			String lockKey = orderDetailMO.getGoodsId(); // 使用商品ID作为库存资源锁的key

			if (toBeLocked.containsKey(lockType))
			{
				toBeLocked.get(lockType).add(lockKey);
			}
			else
			{
				List<String> lockKeys = new ArrayList<String>();
				lockKeys.add(lockKey);
				toBeLocked.put(lockType, lockKeys);
			}
		}

		return toBeLocked;
	}

	private boolean lock(Map<String, List<String>> lockInfo)
	{
		for (String lockType : lockInfo.keySet())
		{
			ResLockMO.ResLockType resLockType = ResLockMO.ResLockType.valueOf(lockType);
			List<ResLockMO> locks = new ArrayList<ResLockMO>();

			for (String lockKey : lockInfo.get(lockType))
			{
				locks.add(new ResLockMO(resLockType, lockKey, DateTimeUtil.getTimeForDisplay(DateTimeUtil.getCurrentTime() + RESLOCK_DISABLE_TIME * 1000, DateTimeUtil.defaultDateFormat)));
			}

			if (!resLockBusiness.lock(locks, RESLOCK_TIMEOUTSECS, RESLOCK_RETRYTIMESTEP))
			{
				return false;
			}
		}

		return true;
	}

	private void unLock(Map<String, List<String>> lockInfo) throws LittleCatException
	{
		for (String lockType : lockInfo.keySet())
		{
			ResLockMO.ResLockType resLockType = ResLockMO.ResLockType.valueOf(lockType);
			List<ResLockMO> locks = new ArrayList<ResLockMO>();

			for (String resId : lockInfo.get(lockType))
			{
				locks.add(new ResLockMO(resLockType, resId));
			}

			resLockBusiness.unLock(locks);
		}
	}

	private void setInventoryInfoByOrderDetail(List<OrderDetailMO> detailMOs) throws LittleCatException
	{
		for (OrderDetailMO orderDetailMO : detailMOs)
		{
			String goodsId = orderDetailMO.getGoodsId();

			if (orderDetailMO.getBuyType() == BuyType.normal)
			{
				GoodsInventoryMO inventoryMO = new GoodsInventoryMO();

				inventoryMO.setChangeType(InventoryChangeType.dingdankoujian);
				inventoryMO.setChangeValue(0 - orderDetailMO.getGoodsNum());
				inventoryMO.setGoodsId(goodsId);

				goodsInventoryBusiness.add(inventoryMO);

				// 重新获取商品最新的库存
				long currentGoodsInventory = goodsInventoryBusiness.getCurrentValueByGoodsId(goodsId);

				// 修改商品信息中的库存字段
				GoodsMO goodsMO = goodsBusiness.getById(goodsId);
				goodsMO.setCurrentInventory(currentGoodsInventory);
				goodsBusiness.modify(goodsMO);
			}

			if (orderDetailMO.getBuyType() == BuyType.seckill)
			{
				String planId = orderDetailMO.getResId();

				SecKillInventoryMO inventoryMO = new SecKillInventoryMO();

				inventoryMO.setPlanId(planId);
				inventoryMO.setChangeType(InventoryChangeType.dingdankoujian);
				inventoryMO.setChangeValue(0 - orderDetailMO.getGoodsNum());

				secKillInventoryBusiness.add(inventoryMO);

				// 重新获取商品秒杀计划最新的库存
				long currentGoodsInventory = secKillInventoryBusiness.getCurrentValueByPlanId(planId);

				// 修改商品秒杀计划信息中的库存字段
				SecKillPlanMO secKillPlanMO = secKillPlanBusiness.getById(planId);
				secKillPlanMO.setCurrentInventory(currentGoodsInventory);
				secKillPlanBusiness.modify(secKillPlanMO);
			}
		}
	}
}
