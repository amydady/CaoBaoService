#系统参数==========================================================
#===============圈子成员有效期（天）
insert into t_sys_param(name,value) values('member_enable_days','30');

#===============资源锁定时清理周期（秒）
insert into t_sys_param(name,value) values('clear_reslock_process_cyc','60');

#===============佣金计算周期（秒）（秒）
insert into t_sys_param(name,value) values('calc_commission_process_cyc','60');

#佣金计算冻结期天数（相对于用户签收时间）
insert into t_sys_param(name,value) values('enable_commission_delay_day','7');


#系统菜单==========================================================

insert into t_sys_menu(id,name,pid,sortNum,isDefault) values('1','运营管理','-1','1','Y');
insert into t_sys_menu(id,name,targeTurl,pid,sortNum,isDefault) values('101','商品管理','../Goods/GoodsList.html','1','1','Y');
insert into t_sys_menu(id,name,targeTurl,pid,sortNum,isDefault) values('102','供应商管理','../Supplier/SupplierList.html','1','2','N');
insert into t_sys_menu(id,name,targeTurl,pid,sortNum,isDefault) values('103','合作伙伴管理','../quanzi/QuanziList.html','1','3','N');
insert into t_sys_menu(id,name,targeTurl,pid,sortNum,isDefault) values('104','佣金发放','../commission/CommissionPayMgr.html','1','4','N');
insert into t_sys_menu(id,name,targeTurl,pid,sortNum,isDefault) values('105','订单管理','../order/OrderList.html','1','5','N');
insert into t_sys_menu(id,name,targeTurl,pid,sortNum,isDefault) values('106','商品销量','../order/GoodsSaleCount.html','1','6','N');

insert into t_sys_menu(id,name,pid,sortNum) values('2','物流管理','-1','2');
insert into t_sys_menu(id,name,targeTurl,pid,sortNum,isDefault) values('201','商品出仓单','../delivery/OutWarehouseMgr.html','2','1','Y');
insert into t_sys_menu(id,name,targeTurl,pid,sortNum,isDefault) values('202','团长收货单','../delivery/TuanZhangReceive.html','2','2','N');
insert into t_sys_menu(id,name,targeTurl,pid,sortNum,isDefault) values('203','团长分拣单','../delivery/TuanZhangFilter.html','2','3','N');

insert into t_sys_menu(id,name,pid,sortNum) values('3','基础配置','-1','3');
insert into t_sys_menu(id,name,targeTurl,pid,sortNum,isDefault) values('301','配送区域管理','../delivery/DeliveryAreaMgr.html','3','1','Y');
insert into t_sys_menu(id,name,targeTurl,pid,sortNum) values('302','物流费用管理','../delivery/DeliveryFeeRuleMgr.html','3','2');
insert into t_sys_menu(id,name,targeTurl,pid,sortNum) values('303','佣金类目管理','../commission/CommissionTypeMgr.html','3','3','N');
insert into t_sys_menu(id,name,targeTurl,pid,sortNum) values('304','横幅图片管理','../main/HomeImgMgr.html','3','4');
insert into t_sys_menu(id,name,targeTurl,pid,sortNum) values('305','关于信息管理','../main/AboutUsImgMgr.html','3','5');
insert into t_sys_menu(id,name,targeTurl,pid,sortNum) values('306','合作协议管理','../main/QuanZiXieYiImgMgr.html','3','6');


insert into t_sys_menu(id,name,pid,sortNum) values('88','统计分析','-1','88');
insert into t_sys_menu(id,name,targeTurl,pid,sortNum,isDefault) values('8801','销售报表','../Report/SalesRpt.html','88','1','Y');

insert into t_sys_menu(id,name,pid,sortNum) values('99','系统管理','-1','99');
insert into t_sys_menu(id,name,targeTurl,pid,sortNum,isDefault) values('9901','个人信息维护','../sys/PersonalInfoMgr.html','99','1','Y');
insert into t_sys_menu(id,name,targeTurl,pid,sortNum,isDefault) values('9902','用户管理','../sys/UserList.html','99','2','N');



#系统操作用户
insert into t_sys_sysoperator(id,username,password,name,wxCode,email,mobile)
values ('test001','test001',password('123'),'test001','test001','test001@qq.com','18888888888');

insert into t_sys_sysoperator(id,username,password,name,wxCode,email,mobile)
values ('admin','admin',password('admin'),'系统管理员','admin','admin@qq.com','18888888889');



#地市信息
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


insert into t_basicinfo_city(name,province) values('南京市','江苏省');
insert into t_basicinfo_area(name,city) values('江宁区','南京市');

#字典类信息
insert into t_delivery_feecalctype(id,name,sortNum) values('1','按订单金额','1');
insert into t_delivery_feecalctype(id,name,sortNum) values('2','按订单重量','2');

INSERT INTO `t_commission_type` (`id`, `name`, `commissionRate`) VALUES ('deliverysite', '物流佣金', 11);
INSERT INTO `t_commission_type` (`id`, `name`, `commissionRate`) VALUES ('share', '推广佣金', 12);


