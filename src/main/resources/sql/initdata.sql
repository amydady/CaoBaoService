#系统参数==========================================================
#圈子成员有效期（天）
insert into t_sys_param(name,value) values('member_enable_days','30');

#秒杀计划后台按秒杀时间窗口定时处理失效标记的周期（秒）
insert into t_sys_param(name,value) values('seckillplan_enabletag_process_cyc','30');