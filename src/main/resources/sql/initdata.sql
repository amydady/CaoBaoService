#系统参数==========================================================
#===============圈子成员有效期（天）
insert into t_sys_param(name,value) values('member_enable_days','30');

#===============资源锁定时清理周期（秒）
insert into t_sys_param(name,value) values('clear_reslock_process_cyc','60');



#系统菜单==========================================================

insert into t_sys_menu(id,name,pid,sortNum) values('1','供应商管理','-1','1');
insert into t_sys_menu(id,name,targeTurl,pid,sortNum,isDefault) values('101','供应商列表','../Supplier/SupplierList.html','1','1','Y');

insert into t_sys_menu(id,name,pid,sortNum,isDefault) values('2','商品管理','-1','2','Y');
insert into t_sys_menu(id,name,targeTurl,pid,sortNum,isDefault) values('201','商品列表','../Goods/GoodsList.html','2','1','Y');

insert into t_sys_menu(id,name,pid,sortNum) values('3','物流管理','-1','3');
insert into t_sys_menu(id,name,targeTurl,pid,sortNum,isDefault) values('301','配送范围管理','../delivery/DeliveryAreaMgr.html','3','1','Y');
insert into t_sys_menu(id,name,targeTurl,pid,sortNum) values('302','快递费用管理','../Delivery/DeliveryFeeRuleMgr.html','3','2');

insert into t_sys_menu(id,name,pid,sortNum) values('4','佣金管理','-1','4');
insert into t_sys_menu(id,name,targeTurl,pid,sortNum,isDefault) values('401','佣金类目管理','../commission/CommissionTypeMgr.html','4','1','Y');
insert into t_sys_menu(id,name,targeTurl,pid,sortNum) values('402','商品佣金设置','../commission/GoodsCommissionMgr.html','4','2');



insert into t_sys_menu(id,name,pid,sortNum) values('5','消费者管理','-1','5');
insert into t_sys_menu(id,name,targeTurl,pid,sortNum,isDefault) values('501','消费者列表','../terminaluser/TerminalUserList.html','5','1','Y');

insert into t_sys_menu(id,name,pid,sortNum) values('99','系统管理','-1','99');



insert into t_sys_sysoperator(id,username,password,name,wxCode,email,mobile)
values ('test001','test001',password('123'),'test001','test001','test001','test001');



//TODO:按照微信小程序的内容修改
insert into t_basicinfo_province(name) values('江苏省');
insert into t_basicinfo_city(name,province) values('镇江市','江苏省');
insert into t_basicinfo_area(name,city) values('京口区','镇江市');
insert into t_basicinfo_area(name,city) values('润州区','镇江市');
insert into t_basicinfo_area(name,city) values('丹徒区','镇江市');
insert into t_basicinfo_area(name,city) values('丹阳市','镇江市');
insert into t_basicinfo_area(name,city) values('扬中市','镇江市');
insert into t_basicinfo_area(name,city) values('句容市','镇江市');
insert into t_basicinfo_area(name,city) values('丹徒新区','镇江市');
insert into t_basicinfo_area(name,city) values('镇江新区','镇江市');



