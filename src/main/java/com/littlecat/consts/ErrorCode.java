package com.littlecat.consts;

/**
 * 错误码信息
 * 
 * @author amydady
 *
 */
public enum ErrorCode
{
	DataAccessException("DataAccessException","Occur an exception when access DB."),
	GetInfoFromDBReturnEmpty("GetInfoFromDBReturnEmpty","get {INFO_NAME} from db return empty."),
	GiveNullObjectToCreate("GiveNullObjectToCreate","give null object to create for {INFO_NAME}."),
	InsertObjectToDBError("InsertObjectToDBError","insert {INFO_NAME} to DB error. "),
	;
	
	private String code;
	private String msg;
	
	ErrorCode(String code,String msg)
	{
		this.code = code;
		this.msg = msg;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public String getMsg()
	{
		return msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}
	
	
}
