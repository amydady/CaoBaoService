package com.littlecat.lock.business;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.littlecat.cbb.exception.LittleCatException;
import com.littlecat.cbb.utils.DateTimeUtil;
import com.littlecat.lock.dao.ResLockDao;
import com.littlecat.lock.model.ResLockMO;

@Component
@Transactional
public class ResLockBusiness
{
	private static final Logger logger = LoggerFactory.getLogger(ResLockBusiness.class);

	@Autowired
	private ResLockDao resLockDao;

	/**
	 * 锁定指定资源
	 * 
	 * @param type
	 *            资源类型
	 * @param key
	 *            资源ID
	 * @param disableTime
	 *            锁自动失效的时间（精确到秒）yyyy-MM-dd hh:mm:ss
	 * @param timeOutSecs
	 *            超时时间（秒）：尝试获取锁的最长等待时间
	 * @param retryTimeStep
	 *            重试时间间隔
	 * @return
	 */
	public boolean lock(String type, String key, String disableTime, long timeOutSecs, long retryTimeStep)
	{
		long initTime = DateTimeUtil.getCurrentTime();

		do
		{
			try
			{
				ResLockMO lock = new ResLockMO(type, key, disableTime);
				resLockDao.add(lock);

				return true;
			}
			catch (Exception e)
			{
				logger.info("get lock error.");

				try
				{
					Thread.sleep(retryTimeStep);
				}
				catch (InterruptedException e1)
				{
					logger.info("InterruptedException when wait lock.");
				}
			}
		}
		while ((DateTimeUtil.getCurrentTime() - initTime) / 1000 <= timeOutSecs);

		return false;
	}

	/**
	 * 锁定指定资源（批量）
	 * 
	 * @param locks
	 * @param timeOutSecs
	 *            超时时间（秒）：尝试获取锁的最长等待时间
	 * @param retryTimeStep
	 *            重试时间间隔
	 * @return
	 */
	public boolean lock(List<ResLockMO> locks, long timeOutSecs, long retryTimeStep)
	{
		long initTime = DateTimeUtil.getCurrentTime();

		do
		{
			try
			{
				resLockDao.add(locks);
				return true;
			}
			catch (Exception e)
			{
				logger.info("get lock error.");

				try
				{
					Thread.sleep(retryTimeStep);
				}
				catch (InterruptedException e1)
				{
					logger.info("InterruptedException when wait lock.");
				}
			}
		}
		while ((DateTimeUtil.getCurrentTime() - initTime) / 1000 <= timeOutSecs);

		return false;
	}

	public void unLock(String type, String key) throws LittleCatException
	{
		ResLockMO mo = new ResLockMO();
		mo.setType(type);
		mo.setKey(key);

		resLockDao.delete(mo);
	}

	public void unLock(List<ResLockMO> locks) throws LittleCatException
	{
		resLockDao.delete(locks);
	}

	public void clearLock() throws LittleCatException
	{
		resLockDao.deleteByDisableTime();
	}
}
