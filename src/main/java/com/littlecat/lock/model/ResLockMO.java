package com.littlecat.lock.model;

/**
 * 业务资源锁
 * 
 * @author amydady
 *
 */
public class ResLockMO
{
	// 资源锁默认的失效时间（秒）
	public static final long DEFAULT_DISABLE_TIME = 5;

	// 获取资源锁时默认的超时时间（秒）
	public static final long DEFAULT_TIMEOUTSECS = 5;

	// 获取资源锁时默认的重试时间间隔（毫秒）
	public static final long DEFAULT_RETRYTIMESTEP = 100;

	/**
	 * 资源锁的类型
	 * 
	 * @author amydady
	 *
	 */
	public static enum ResLockType
	{
		goodsinventory, seckillinventory, groupbuyinventory
	}

	private ResLockType type;
	private String key;
	private String createTime;
	private String disableTime; // 锁失效的时间

	public ResLockMO()
	{

	}

	public ResLockMO(ResLockType type, String key, String disableTime)
	{
		this.type = type;
		this.key = key;
		this.disableTime = disableTime;
	}
	
	public ResLockMO(ResLockType type, String key)
	{
		this.type = type;
		this.key = key;
	}

	public ResLockType getType()
	{
		return type;
	}

	public void setType(ResLockType type)
	{
		this.type = type;
	}

	public String getKey()
	{
		return key;
	}

	public void setKey(String key)
	{
		this.key = key;
	}

	public String getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(String createTime)
	{
		this.createTime = createTime;
	}

	public String getDisableTime()
	{
		return disableTime;
	}

	public void setDisableTime(String disableTime)
	{
		this.disableTime = disableTime;
	}

}
