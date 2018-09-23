package com.littlecat.quanzi.model;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.littlecat.cbb.common.BaseMO;
import com.littlecat.cbb.utils.StringUtil;
import com.littlecat.common.consts.IdCardType;
import com.littlecat.common.model.AddressMO;
import com.littlecat.common.model.IdCardMO;

/**
 * 团MO
 * 
 * @author amydady
 *
 */
public class TuanMO extends BaseMO
{
	private String tuanZhangId;
	private String name;
	private String remark;
	private IdCardMO idCard;
	private AddressMO addressInfo;
	private String createTime;
	private List<String> labels = new ArrayList<String>();	//团标签
	private String enable;

	public TuanMO()
	{
		super();
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getRemark()
	{
		return remark;
	}

	public void setRemark(String remark)
	{
		this.remark = remark;
	}

	public String getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(String createTime)
	{
		this.createTime = createTime;
	}

	public String getTuanZhangId()
	{
		return tuanZhangId;
	}

	public void setTuanZhangId(String tuanZhangId)
	{
		this.tuanZhangId = tuanZhangId;
	}

	public AddressMO getAddressInfo()
	{
		return addressInfo;
	}

	public void setAddressInfo(AddressMO addressInfo)
	{
		this.addressInfo = addressInfo;
	}

	public String getEnable()
	{
		return enable;
	}

	public void setEnable(String enable)
	{
		this.enable = enable;
	}

	public IdCardMO getIdCard()
	{
		return idCard;
	}

	public void setIdCard(IdCardMO idCard)
	{
		this.idCard = idCard;
	}

	public List<String> getLabels()
	{
		return labels;
	}

	public void setLabels(List<String> labels)
	{
		this.labels = labels;
	}
	
	public static class MOMapper implements RowMapper<TuanMO>
	{
		@Override
		public TuanMO mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			TuanMO mo = new TuanMO();
			
			mo.setId(rs.getString("id"));
			mo.setTuanZhangId(rs.getString("tuanZhangId"));
			mo.setName(rs.getString("name"));
			mo.setRemark(rs.getString("remark"));
			mo.setIdCard(new IdCardMO(IdCardType.valueOf(rs.getString("idCardType")), rs.getString("idCardCode"),
					rs.getString("idCardImgUrlFront"), rs.getString("idCardImgUrlBack")));
			mo.setAddressInfo(new AddressMO(rs.getString("provinceId"),rs.getString("cityId"),rs.getString("areaId"),rs.getString("detailInfo")));
			mo.setLabels(StringUtil.split2List(rs.getString("labels")));
			mo.setCreateTime(rs.getString("createTime"));
			mo.setEnable(rs.getString("enable"));
			
			return mo;
		}
	}

}
