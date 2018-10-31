package com.littlecat.quanzi.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.littlecat.cbb.common.BaseMO;
import com.littlecat.cbb.utils.SpringUtil;
import com.littlecat.cbb.utils.StringUtil;
import com.littlecat.common.consts.IdCardType;
import com.littlecat.common.model.AddressMO;
import com.littlecat.common.model.IdCardMO;
import com.littlecat.terminaluser.business.TerminalUserBusiness;
import com.littlecat.terminaluser.model.TerminalUserMO;

/**
 * 团MO
 * 
 * @author amydady
 *
 */
public class TuanMO extends BaseMO
{
	private String tuanZhangId; // 消费者ID
	private String name;
	private IdCardMO idCard;
	private AddressMO addressInfo;
	private String createTime;
	private List<String> labels = new ArrayList<String>(); // 团标签
	private String enable;
	private String approveTime;
	private String approveResult;
	private String approveRemark;

	// just for view

	private String tuanZhangMobile;

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

	public String getTuanZhangMobile()
	{
		return tuanZhangMobile;
	}

	public void setTuanZhangMobile(String tuanZhangMobile)
	{
		this.tuanZhangMobile = tuanZhangMobile;
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

	public String getApproveResult()
	{
		return approveResult;
	}

	public void setApproveResult(String approveResult)
	{
		this.approveResult = approveResult;
	}

	public static class MOMapper implements RowMapper<TuanMO>
	{
		private static TerminalUserBusiness terminalUserBusiness = SpringUtil.getBean(TerminalUserBusiness.class);

		@Override
		public TuanMO mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			TuanMO mo = new TuanMO();

			mo.setId(rs.getString("id"));
			mo.setTuanZhangId(rs.getString("tuanZhangId"));
			mo.setName(rs.getString("name"));
			mo.setIdCard(new IdCardMO(IdCardType.valueOf(rs.getString("idCardType")), rs.getString("idCardCode"), rs.getString("idCardImgDataFront"), rs.getString("idCardImgDataBack")));
			mo.setAddressInfo(new AddressMO(rs.getString("province"), rs.getString("city"), rs.getString("area"), rs.getString("detailInfo")));
			mo.setLabels(StringUtil.split2List(rs.getString("labels")));
			mo.setCreateTime(rs.getString("createTime"));
			mo.setEnable(rs.getString("enable"));
			mo.setApproveTime(rs.getString("approveTime"));
			mo.setApproveResult(rs.getString("approveResult"));
			mo.setApproveRemark(rs.getString("approveRemark"));


			TerminalUserMO terminalUserMO = terminalUserBusiness.getById(mo.getTuanZhangId());
			mo.setTuanZhangMobile(terminalUserMO.getMobile());

			return mo;
		}
	}

}
