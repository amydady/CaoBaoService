package com.littlecat.test;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.littlecat.system.business.SysParamBusiness;
import com.littlecat.system.model.SysParamMO;

@Component
public class TestMain
{
	@Autowired
	public static SysParamBusiness business;
	
	public static void main(String[] args)
	{
		List<SysParamMO> mos = business.getList();
		System.out.println(mos.size());
	}
}
