package com.littlecat.common.consts;

/**
 * 购买方式
 * 
 * @author amydady
 *
 */
public enum BuyType
{
	normal(ResLockType.goodsinventory.name()), seckill(ResLockType.seckillinventory.name()), groupbuy(ResLockType.groupbuyinventory.name());

	/**
	 * 购买方式对应的需要锁定的资源的锁类型
	 */
	private String resLockType;

	BuyType(String resLockType)
	{
		this.setResLockType(resLockType);
	}

	public String getResLockType()
	{
		return resLockType;
	}

	private void setResLockType(String resLockType)
	{
		this.resLockType = resLockType;
	}
}
