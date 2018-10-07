package com.littlecat.lock.model;

/**
 * 业务资源锁
 * 
 * @author amydady
 *
 */
public class ResLockMO
{
	private String type;
	private String key;
	private String createTime;

	public String getType()
	{
		return type;
	}

	public void setType(String type)
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

}
