package com.littlecat.task;

import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.littlecat.cbb.utils.StringUtil;
import com.littlecat.common.utils.SpringUtil;
import com.littlecat.common.utils.SysParamUtil;
import com.littlecat.seckill.business.SecKillPlanBusiness;

/**
 * 系统的任务管理器
 * 
 * @author amydady
 *
 */
@Component
public class TaskMgr
{
	private Logger logger = LoggerFactory.getLogger(TaskMgr.class);

	// 定时任务默认启动延时（毫秒）
	private static final long TASK_DELAY_DEFAULT = 1000;
	
	//秒杀计划按秒杀时间窗口定时处理失效标记的周期（秒）
	private static final long SECKILLPLAN_ENABLETAG_PROCESS_CYC_DEFAULT = 5 * 60;

	@PostConstruct
	public void init()
	{
		try
		{
			startTask4DisableSecKillPlan();
		}
		catch (Exception e)
		{
			logger.error("TaskMgr init error.", e);
		}
	}

	/**
	 * 定时将秒杀计划置失效
	 */
	private void startTask4DisableSecKillPlan()
	{

		TimerTask timerTask = new TimerTask()
		{
			@Override
			public void run()
			{
				SecKillPlanBusiness secKillPlanBusiness = SpringUtil.getBean(SecKillPlanBusiness.class);
				secKillPlanBusiness.disable();
			}
		};
		Timer timer = new Timer();

		long cyc = SECKILLPLAN_ENABLETAG_PROCESS_CYC_DEFAULT;
		String cycFromParam = SysParamUtil.getValueByName(SysParamUtil.SECKILLPLAN_ENABLETAG_PROCESS_CYC);
		if (StringUtil.isNotEmpty(cycFromParam))
		{
			cyc = Long.valueOf(cycFromParam);
		}

		// 安排指定的任务在指定的时间开始进行重复的固定周期执行。
		timer.schedule(timerTask, TASK_DELAY_DEFAULT, cyc * 1000);

	}
}
