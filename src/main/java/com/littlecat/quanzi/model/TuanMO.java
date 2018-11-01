package com.littlecat.quanzi.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.littlecat.cbb.utils.StringUtil;
import com.littlecat.common.model.AddressMO;

/**
 * 团MO
 * 
 * @author amydady
 *
 */
public class TuanMO
{
	private String id; // 团长OPENID
	private String tuanZhangName; // 团长姓名
	private String name;
	private String idCardImgDataFront;
	private String idCardImgDataBack;
	private AddressMO addressInfo;
	private String mobile;
	private String createTime;
	private String enable;
	private String approveTime;
	private String approveRemark;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public AddressMO getAddressInfo()
	{
		return addressInfo;
	}

	public void setAddressInfo(AddressMO addressInfo)
	{
		this.addressInfo = addressInfo;
	}

	public String getMobile()
	{
		return mobile;
	}

	public void setMobile(String mobile)
	{
		this.mobile = mobile;
	}

	public String getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(String createTime)
	{
		this.createTime = createTime;
	}

	public String getEnable()
	{
		return enable;
	}

	public void setEnable(String enable)
	{
		this.enable = enable;
	}

	public String getApproveTime()
	{
		return approveTime;
	}

	public void setApproveTime(String approveTime)
	{
		this.approveTime = approveTime;
	}

	public String getApproveRemark()
	{
		return approveRemark;
	}

	public void setApproveRemark(String approveRemark)
	{
		this.approveRemark = approveRemark;
	}

	public String getIdCardImgDataFront()
	{
		return idCardImgDataFront;
	}

	public void setIdCardImgDataFront(String idCardImgDataFront)
	{
		this.idCardImgDataFront = idCardImgDataFront;
	}

	public String getIdCardImgDataBack()
	{
		return idCardImgDataBack;
	}

	public void setIdCardImgDataBack(String idCardImgDataBack)
	{
		this.idCardImgDataBack = idCardImgDataBack;
	}

	public String getTuanZhangName()
	{
		return tuanZhangName;
	}

	public void setTuanZhangName(String tuanZhangName)
	{
		this.tuanZhangName = tuanZhangName;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public static class MOMapper implements RowMapper<TuanMO>
	{
		@Override
		public TuanMO mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			TuanMO mo = new TuanMO();
			
			mo.setId(rs.getString("id"));
			mo.setTuanZhangName(rs.getString("tuanZhangName"));
			mo.setName(rs.getString("name"));
			mo.setIdCardImgDataFront(rs.getString("idCardImgDataFront"));
			mo.setIdCardImgDataBack(rs.getString("idCardImgDataBack"));
			mo.setAddressInfo(new AddressMO(rs.getString("province"), rs.getString("city"), rs.getString("area"), rs.getString("detailInfo")));
			mo.setMobile(rs.getString("mobile"));
			mo.setCreateTime(StringUtil.replace(rs.getString("createTime"),".0",""));
			mo.setEnable(rs.getString("enable"));
			mo.setApproveTime(StringUtil.replace(rs.getString("approveTime"),".0",""));
			mo.setApproveRemark(rs.getString("approveRemark"));

			return mo;
		}
	}

}
