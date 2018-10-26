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
	private String imgDataFront;
	private String imgDataBack;

	
	public IdCardMO(IdCardType type, String code, String imgDataFront, String imgDataBack)
	{
		super();
		this.type = type;
		this.code = code;
		this.setImgDataFront(imgDataFront);
		this.setImgDataBack(imgDataBack);
	}

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

	public String getImgDataFront()
	{
		return imgDataFront;
	}

	public void setImgDataFront(String imgDataFront)
	{
		this.imgDataFront = imgDataFront;
	}

	public String getImgDataBack()
	{
		return imgDataBack;
	}

	public void setImgDataBack(String imgDataBack)
	{
		this.imgDataBack = imgDataBack;
	}

}
