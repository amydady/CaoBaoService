package com.littlecat.system.model;

/**
 * 登录请求信息
 * 
 * @author amydady
 *
 */
public class LoginReqInfo
{
	private String identity;
	private String pwd;

	public String getPwd()
	{
		return pwd;
	}

	public void setPwd(String pwd)
	{
		this.pwd = pwd;
	}

	public String getIdentity()
	{
		return identity;
	}

	public void setIdentity(String identity)
	{
		this.identity = identity;
	}

}
