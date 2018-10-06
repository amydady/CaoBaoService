package com.littlecat.terminaluser.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.query.QueryParam;
import com.littlecat.common.consts.BooleanTag;
import com.littlecat.common.consts.ErrorCode;
import com.littlecat.common.consts.TableName;
import com.littlecat.terminaluser.dao.DeliveryAddressDao;
import com.littlecat.terminaluser.model.DeliveryAddressMO;

@Component
@Transactional
public class DeliveryAddressBusiness
{
	private static final String TABLE_NAME = TableName.DeliveryAddress.getName();
	private static final String MODEL_NAME = "DeliveryAddressMO";

	@Autowired
	private DeliveryAddressDao deliveryAddressDao;

	public DeliveryAddressMO getById(String id) throws LittleCatException
	{
		return deliveryAddressDao.getById(id);
	}

	public void delete(String id) throws LittleCatException
	{
		deliveryAddressDao.delete(id);
	}

	public void delete(List<String> ids) throws LittleCatException
	{
		deliveryAddressDao.delete(ids);
	}

	public String add(DeliveryAddressMO mo) throws LittleCatException
	{
		if (mo == null)
		{
			throw new LittleCatException(ErrorCode.RequestObjectIsNull.getCode(), ErrorCode.RequestObjectIsNull.getMsg().replace("{INFO_NAME}", MODEL_NAME));
		}

		if (BooleanTag.Y.name().equals(mo.getIsDefault()))
		{
			deliveryAddressDao.setDefaultNo(mo.getTerminalUserId());
		}

		return deliveryAddressDao.add(mo);
	}

	public void modify(DeliveryAddressMO mo) throws LittleCatException
	{
		if (mo == null)
		{
			throw new LittleCatException(ErrorCode.RequestObjectIsNull.getCode(), ErrorCode.RequestObjectIsNull.getMsg().replace("{INFO_NAME}", MODEL_NAME));
		}

		if (BooleanTag.Y.name().equals(mo.getIsDefault()))
		{
			deliveryAddressDao.setDefaultNo(mo.getTerminalUserId());
		}

		deliveryAddressDao.modify(mo);
	}

	public int getList(QueryParam queryParam, List<DeliveryAddressMO> mos) throws LittleCatException
	{
		return deliveryAddressDao.getList(queryParam, mos);
	}

	public void setDefaultYes(String id) throws LittleCatException
	{
		DeliveryAddressMO mo = getById(id);

		if (mo == null)
		{
			throw new LittleCatException(ErrorCode.GetInfoFromDBReturnEmpty.getCode(), ErrorCode.GetInfoFromDBReturnEmpty.getMsg().replace("{INFO_NAME}", TABLE_NAME));
		}

		deliveryAddressDao.setDefaultNo(mo.getTerminalUserId());
		deliveryAddressDao.setDefaultYes(id);
	}
}
