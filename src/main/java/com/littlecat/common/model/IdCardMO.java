package com.littlecat.common.model;

import com.littlecat.common.consts.IdCardType;

/**
 * 身份证件信息
 * 
 * @author amydady
 *
 */
public class IdCardMO
{
	private IdCardType type = IdCardType.juminshenfenzheng;
	private String code; // 证件号码
	private String imgUrlFront;
	private String imgUrlBack;

	public IdCardType getType()
	{
		return type;
	}

	public void setType(IdCardType type)
	{
		this.type = type;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public String getImgUrlFront()
	{
		return imgUrlFront;
	}

	public void setImgUrlFront(String imgUrlFront)
	{
		this.imgUrlFront = imgUrlFront;
	}

	public String getImgUrlBack()
	{
		return imgUrlBack;
	}

	public void setImgUrlBack(String imgUrlBack)
	{
		this.imgUrlBack = imgUrlBack;
	}

}
