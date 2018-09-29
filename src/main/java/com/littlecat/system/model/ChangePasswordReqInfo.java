package com.littlecat.system.model;

/**
 * 修改密码请求信息
 * 
 * @author amydady
 *
 */
public class ChangePasswordReqInfo
{
	private String id;
	private String pwd;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getPwd()
	{
		return pwd;
	}

	public void setPwd(String pwd)
	{
		this.pwd = pwd;
	}

}
