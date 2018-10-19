#系统参数==========================================================
#===============圈子成员有效期（天）
insert into t_sys_param(name,value) values('member_enable_days','30');

#===============资源锁定时清理周期（秒）
insert into t_sys_param(name,value) values('clear_reslock_process_cyc','60');



#系统菜单==========================================================

insert into t_sys_menu(id,name,targeTurl,pid,sortNum) values('1','供应商管理','../Supplier/SupplierList.html','-1','1');
insert into t_sys_menu(id,name,targeTurl,pid,sortNum) values('2','商品管理','../Supplier/SupplierList.html','-1','2');
insert into t_sys_menu(id,name,targeTurl,pid,sortNum) values('3','系统管理','../Supplier/SupplierList.html','-1','3');

insert into t_sys_menu(id,name,targeTurl,pid,sortNum) values('101','供应商列表','../Supplier/SupplierList.html','1','1');
insert into t_sys_menu(id,name,targeTurl,pid,sortNum) values('102','百度','https://www.baidu.com','1','1');

	