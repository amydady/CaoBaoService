package com.littlecat.lock.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.lock.dao.ResLockDao;
import com.littlecat.lock.model.ResLockMO;

@Component
@Transactional
public class ResLockBusiness
{
	private static final Logger logger = LoggerFactory.getLogger(ResLockBusiness.class);

	@Autowired
	private ResLockDao resLockDao;

	public boolean lock(String type, String key, String disableTime)
	{
		try
		{
			ResLockMO mo = new ResLockMO();
			mo.setType(type);
			mo.setKey(key);
			mo.setDisableTime(disableTime);

			resLockDao.add(mo);

			return true;
		}
		catch (Exception e)
		{
			logger.info("get lock error:type={},key={}", type, key);
			return false;
		}
	}

	public boolean unLock(String type, String key)
	{
		try
		{
			ResLockMO mo = new ResLockMO();
			mo.setType(type);
			mo.setKey(key);

			resLockDao.delete(mo);

			return true;
		}
		catch (Exception e)
		{
			logger.info("unlock error:type={},key={}", type, key);
			return false;
		}
	}

	public void clearLock() throws LittleCatException
	{
		resLockDao.deleteByDisableTime();
	}
}
