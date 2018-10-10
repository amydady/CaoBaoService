#系统参数==========================================================
#圈子成员有效期（天）
insert into t_sys_param(name,value) values('member_enable_days','30');

#资源锁定时清理周期（秒）
insert into t_sys_param(name,value) values('clear_reslock_process_cyc','60');