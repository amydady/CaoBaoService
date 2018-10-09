package com.littlecat.common.consts;

/**
 * 错误码信息
 * 
 * @author amydady
 *
 */
public enum ErrorCode
{
	DataAccessException("DataAccessException","Occur an exception when access DB."),
	QueryParamIsNull("QueryParamIsNull","Query param is null."),
	RequestObjectIsNull("RequestObjectIsNull","Request object is null.infoname:{INFO_NAME}"),
	RequestObjectInvalidate("RequestObjectInvalidate","Request object is invalidate.infoname:{INFO_NAME},detailinfo:{DETAILINFO}"),
	GetInfoFromDBReturnEmpty("GetInfoFromDBReturnEmpty","get {INFO_NAME} from db return empty,detailinfo:{DETAILINFO}."),
	InsertObjectToDBError("InsertObjectToDBError","insert {INFO_NAME} to DB error. "),
	UpdateObjectToDBError("UpdateObjectToDBError","update {INFO_NAME} to DB error. "),
	UpdateObjectWithEmptyId("UpdateObjectWithEmptyId","update {INFO_NAME} with empty id. "),
	DeleteObjectWithEmptyId("DeleteObjectWithEmptyId","delete {INFO_NAME} with empty id. "),
	DeleteObjectWithIdError("DeleteObjectWithIdError","delete {INFO_NAME} with id error. "),
	DisableObjectWithEmptyId("DisableObjectWithEmptyId","disable {INFO_NAME} with empty id. "),
	DisableObjectWithIdError("DisableObjectWithIdError","disable {INFO_NAME} with id error. "),
	EnableObjectWithEmptyId("EnableObjectWithEmptyId","enable {INFO_NAME} with empty id. "),
	EnableObjectWithIdError("EnableObjectWithIdError","enable {INFO_NAME} with id error. "),
	

	LockResError("LockResError","lock resource error. "),
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
