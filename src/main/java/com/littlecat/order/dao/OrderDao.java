package com.littlecat.order.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.query.QueryParam;
import com.littlecat.cbb.utils.StringUtil;
import com.littlecat.cbb.utils.UUIDUtil;
import com.littlecat.common.consts.BuyType;
import com.littlecat.common.consts.ErrorCode;
import com.littlecat.common.consts.OrderState;
import com.littlecat.common.consts.TableName;
import com.littlecat.common.utils.DaoUtil;
import com.littlecat.order.model.OrderMO;

@Component
public class OrderDao
{
	@Autowired
	protected JdbcTemplate jdbcTemplate;

	private final String TABLE_NAME = TableName.Order.getName();
	private static final String MODEL_NAME = OrderMO.class.getSimpleName();
	private static final String TABLE_NAME_TUAN = TableName.Tuan.getName();
	private static final String TABLE_NAME_ORDERDETAIL = TableName.OrderDetail.getName();
	private static final String TABLE_NAME_TERMINALUSER = TableName.TerminalUser.getName();

	public String add(OrderMO mo) throws LittleCatException
	{
		if (StringUtil.isEmpty(mo.getId()))
		{
			mo.setId(UUIDUtil.createUUID());
		}

		String sql = "insert into " + TABLE_NAME + "(id,terminalUserId,fee,state,province,city,area,detailInfo,siteprovince,sitecity,sitearea,sitedetailInfo,contactName,contactMobile,groupBuyPlanId,groupBuyTaskId,deliveryFee,deliveryTuanZhangId,shareTuanZhangId) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		try
		{
			int ret = jdbcTemplate.update(sql, new Object[] { mo.getId(), mo.getTerminalUserId(), mo.getFee(), mo.getState().name(), mo.getDeliveryAddress().getProvince(), mo.getDeliveryAddress().getCity(), mo.getDeliveryAddress().getArea(), mo.getDeliveryAddress().getDetailInfo(), mo.getDeliverySiteAddress().getProvince(), mo.getDeliverySiteAddress().getCity(), mo.getDeliverySiteAddress().getArea(), mo.getDeliverySiteAddress().getDetailInfo(), mo.getContactName(), mo.getContactMobile(), mo.getGroupBuyPlanId(), mo.getGroupBuyTaskId(), mo.getDeliveryFee(), mo.getDeliveryTuanZhangId(), mo.getShareTuanZhangId() });

			if (ret != 1)
			{
				throw new LittleCatException(ErrorCode.InsertObjectToDBError.getCode(), ErrorCode.InsertObjectToDBError.getMsg().replace("{INFO_NAME}", MODEL_NAME));
			}
		}
		catch (DataAccessException e)
		{
			throw new LittleCatException(ErrorCode.DataAccessException.getCode(), ErrorCode.DataAccessException.getMsg(), e);
		}

		return mo.getId();
	}

	public void modify(OrderMO mo) throws LittleCatException
	{
		String sql = "update " + TABLE_NAME + " set state = ?,payTime = ?,receiveTime = ?,returnApplyTime = ?,returnCompleteTime = ?,groupCompleteTime=?,groupCancelTime=?,deliveryTime=?,deliverySiteReceiveTime=?,commissionCalcTime=?,cancelTime=?  where id = ?";

		try
		{
			jdbcTemplate.update(sql, new Object[] { mo.getState().name(), mo.getPayTime(), mo.getReceiveTime(), mo.getReturnApplyTime(), mo.getReturnCompleteTime(), mo.getGroupCompleteTime(), mo.getGroupCancelTime(), mo.getDeliveryTime(), mo.getDeliverySiteReceiveTime(), mo.getCommissionCalcTime(), mo.getCancelTime(), mo.getId() });
		}
		catch (DataAccessException e)
		{
			throw new LittleCatException(ErrorCode.DataAccessException.getCode(), ErrorCode.DataAccessException.getMsg(), e);
		}
	}

	public void completeCommissionCalc(String id) throws LittleCatException
	{
		String sql = "update " + TABLE_NAME + " set commissionCalcTime = now() where id = ?";

		try
		{
			jdbcTemplate.update(sql, new Object[] { id });
		}
		catch (DataAccessException e)
		{
			throw new LittleCatException(ErrorCode.DataAccessException.getCode(), ErrorCode.DataAccessException.getMsg(), e);
		}
	}

	public void cancel(String id) throws LittleCatException
	{
		String sql = "update " + TABLE_NAME + " set state = ?,cancelTime = now() where id = ?";

		try
		{
			jdbcTemplate.update(sql, new Object[] { OrderState.yiquxiao.name(), id });
		}
		catch (DataAccessException e)
		{
			throw new LittleCatException(ErrorCode.DataAccessException.getCode(), ErrorCode.DataAccessException.getMsg(), e);
		}
	}

	public void modify(List<OrderMO> mos) throws LittleCatException
	{
		String sql = "update " + TABLE_NAME + " set state = ?,payTime = ?,receiveTime = ?,returnApplyTime = ?,returnCompleteTime = ?,groupCompleteTime=?,groupCancelTime=?,deliveryTime=?,deliverySiteReceiveTime=? where id = ?";
		List<Object[]> batchParam = new ArrayList<Object[]>();

		for (OrderMO mo : mos)
		{
			batchParam.add(new Object[] { mo.getState().name(), mo.getPayTime(), mo.getReceiveTime(), mo.getReturnApplyTime(), mo.getReturnCompleteTime(), mo.getGroupCompleteTime(), mo.getGroupCancelTime(), mo.getDeliveryTime(), mo.getDeliverySiteReceiveTime(), mo.getId() });
		}
		try
		{
			jdbcTemplate.batchUpdate(sql, batchParam);
		}
		catch (DataAccessException e)
		{
			throw new LittleCatException(ErrorCode.DataAccessException.getCode(), ErrorCode.DataAccessException.getMsg(), e);
		}
	}

	public void tuiKuanShenqing(String id, String remark) throws LittleCatException
	{
		String sql = "update " + TABLE_NAME + " set state = ?,returnApplyRemark=?,returnApplyTime = now() where id = ?";

		try
		{
			jdbcTemplate.update(sql, new Object[] { OrderState.tuikuanzhong.name(), remark, id });
		}
		catch (DataAccessException e)
		{
			throw new LittleCatException(ErrorCode.DataAccessException.getCode(), ErrorCode.DataAccessException.getMsg(), e);
		}
	}

	public void cancelTuiKuan(String id, String remark) throws LittleCatException
	{
		String sql = "update " + TABLE_NAME + " set state = ?,returnCancelRemark=?,returnCancelTime = now() where id = ?";

		try
		{
			jdbcTemplate.update(sql, new Object[] { OrderState.quxiaotuikuan.name(), remark, id });
		}
		catch (DataAccessException e)
		{
			throw new LittleCatException(ErrorCode.DataAccessException.getCode(), ErrorCode.DataAccessException.getMsg(), e);
		}
	}

	public void completeTuiKuan(String id, String remark) throws LittleCatException
	{
		String sql = "update " + TABLE_NAME + " set state = ?,returnCompleteRemark=?,returnCompleteTime = now() where id = ?";

		try
		{
			jdbcTemplate.update(sql, new Object[] { OrderState.yituikuan.name(), remark, id });
		}
		catch (DataAccessException e)
		{
			throw new LittleCatException(ErrorCode.DataAccessException.getCode(), ErrorCode.DataAccessException.getMsg(), e);
		}
	}

	public void terminalUserReceive(String id) throws LittleCatException
	{
		String sql = "update " + TABLE_NAME + " set state = '" + OrderState.yishouhuo.name() + "',receiveTime = now() where id = ?";

		try
		{
			jdbcTemplate.update(sql, new Object[] { id });
		}
		catch (DataAccessException e)
		{
			throw new LittleCatException(ErrorCode.DataAccessException.getCode(), ErrorCode.DataAccessException.getMsg(), e);
		}
	}

	public void terminalUserReceive(List<String> ids) throws LittleCatException
	{
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);

		String sql = "update " + TABLE_NAME + " set state = '" + OrderState.yishouhuo.name() + "',receiveTime = now() where id in (:ids)";

		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("ids", ids);

		try
		{
			namedParameterJdbcTemplate.update(sql, parameters);
		}
		catch (DataAccessException e)
		{
			throw new LittleCatException(ErrorCode.DataAccessException.getCode(), ErrorCode.DataAccessException.getMsg(), e);
		}

	}

	public void afterDeliverySiteReceive(String orderDate) throws LittleCatException
	{
		String sql = "update " + TABLE_NAME + " set state = '" + OrderState.daiqianshou.name() + "',deliverySiteReceiveTime=now() where date_format(payTime,'%Y%m%d') = date_format(?,'%Y%m%d') and state = '" + OrderState.daifahuo.name() + "'";

		try
		{
			jdbcTemplate.update(sql, new Object[] { orderDate });
		}
		catch (DataAccessException e)
		{
			throw new LittleCatException(ErrorCode.DataAccessException.getCode(), ErrorCode.DataAccessException.getMsg(), e);
		}
	}

	public void updateOutInventoryGenTime(List<String> ids) throws LittleCatException
	{
		String sql = "update " + TABLE_NAME + " set outInventoryGenTime = CURRENT_TIMESTAMP where id in (:ids)";
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);

		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("ids", ids);

		try
		{
			namedParameterJdbcTemplate.update(sql, parameters);
		}
		catch (DataAccessException e)
		{
			throw new LittleCatException(ErrorCode.DataAccessException.getCode(), ErrorCode.DataAccessException.getMsg(), e);
		}
	}

	public OrderMO getById(String id) throws LittleCatException
	{
		return DaoUtil.getById(TABLE_NAME, id, jdbcTemplate, new OrderMO.MOMapper());
	}

	public int getList(QueryParam queryParam, List<OrderMO> mos) throws LittleCatException
	{
		return DaoUtil.getList(TABLE_NAME, queryParam, mos, jdbcTemplate, new OrderMO.MOMapper());
	}

	/**
	 * 获取需要佣金计算的订单（已支付的订单）
	 * 
	 * @return
	 */
	public List<OrderMO> getNeedCommissionCalcList()
	{
		StringBuilder sql = new StringBuilder()
				.append("select * from ").append(TABLE_NAME)
				.append(" where payTime is not null")
				.append(" and commissionCalcTime is null");

		return jdbcTemplate.query(sql.toString(), new OrderMO.MOMapper());
	}

	/**
	 * 查询某个消费者在某个秒杀计划下已经购买的商品数量
	 * 
	 * @param secKillPlanId
	 * @param terminalUserId
	 * @return
	 * @throws LittleCatException
	 */
	public int getBuyedNumOfSecKillPlan(String secKillPlanId, String terminalUserId) throws LittleCatException
	{
		String sql = new StringBuilder()
				.append("select sum(orderdetail.goodsNum) from ")
				.append(TABLE_NAME_ORDERDETAIL).append(" orderdetail,")
				.append(TABLE_NAME).append(" order ")
				.append(" where order.id = orderdetail.orderId")
				.append(" and order.terminalUserId = ?")
				.append(" and orderdetail.buyType=?")
				.append(" and orderdetail.resId = ?")
				.toString();

		try
		{
			return jdbcTemplate.queryForObject(sql, new Object[] { terminalUserId, BuyType.seckill.name(), secKillPlanId }, Integer.class);
		}
		catch (DataAccessException e)
		{
			throw new LittleCatException(ErrorCode.DataAccessException.getCode(), ErrorCode.DataAccessException.getMsg(), e);
		}
	}

	public List<OrderMO> getList(String id, String shareTuanZhangName, String deliveryTuanZhangName, String terminalUserName, String state, String createDate)
	{
		StringBuilder sql = new StringBuilder()
				.append("select a.*,b.name shareTuanZhangName,c.name deliveryTuanZhangName, d.name terminalUserName from ").append(TABLE_NAME + " a ")
				.append(" left join ").append(TABLE_NAME_TUAN).append(" b on a.shareTuanZhangId =b.id ")
				.append(" left join ").append(TABLE_NAME_TUAN).append(" c on a.deliveryTuanZhangId =c.id ")
				.append(" left join ").append(TABLE_NAME_TERMINALUSER).append(" d on a.terminalUserId =d.id ")
				.append(" where 1=1 ");

		if (StringUtil.isNotEmpty(id))
		{
			sql.append(" and a.id = '" + id + "' ");
		}

		if (StringUtil.isNotEmpty(shareTuanZhangName))
		{
			sql.append(" and b.name like '%" + shareTuanZhangName + "%' ");
		}

		if (StringUtil.isNotEmpty(deliveryTuanZhangName))
		{
			sql.append(" and c.name like '%" + deliveryTuanZhangName + "%' ");
		}

		if (StringUtil.isNotEmpty(terminalUserName))
		{
			sql.append(" and d.name like '%" + terminalUserName + "%' ");
		}

		if (StringUtil.isNotEmpty(state))
		{
			sql.append(" and a.state = '" + state + "' ");
		}

		if (StringUtil.isNotEmpty(createDate))
		{
			sql.append(" and date_format(a.createTime,'%Y%m%d') = date_format('" + createDate + "','%Y%m%d')");
		}

		return jdbcTemplate.query(sql.toString(), new OrderMO.MOMapper());
	}

}
