package com.littlecat.quanzi.model;

/**
 * 团审核请求信息
 * 
 * @author amydady
 *
 */
public class TuanShenheReqInfo
{
	private String id; // 团长OPENID
	private String enable;
	private String approveRemark;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getEnable()
	{
		return enable;
	}

	public void setEnable(String enable)
	{
		this.enable = enable;
	}

	public String getApproveRemark()
	{
		return approveRemark;
	}

	public void setApproveRemark(String approveRemark)
	{
		this.approveRemark = approveRemark;
	}
}
