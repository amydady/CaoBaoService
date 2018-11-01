###

`id` VARCHAR(255) NOT NULL,
	`tuanZhangName` VARCHAR(255) NOT NULL,
	`name` VARCHAR(255) NOT NULL,
	`idCardImgDataFront` MediumBlob NULL,
	`idCardImgDataBack` MediumBlob NULL,
	`province` VARCHAR(255) NOT NULL,
	`city` VARCHAR(255) NOT NULL,
	`area` VARCHAR(255) NOT NULL,
	`detailInfo` VARCHAR(255) NOT NULL,
	`mobile` VARCHAR(15) NOT NULL,
	`createTime` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`enable` VARCHAR(1) NOT NULL DEFAULT 'I',
	`approveTime` DATETIME NULL,
	`approveRemark` VARCHAR(255) NULL,
	
insert into t_quanzi_tuan(id,tuanZhangName,name,province,city,area,detailInfo,mobile)
values('1','wangerxiao','MyOO','江苏省','南京市','江宁区','这是什么地方','18888888888');

insert into t_quanzi_tuan(id,tuanZhangName,name,province,city,area,detailInfo,mobile)
values('2','wangerxiao2','MyOO2','江苏省','南京市','江宁区','这是什么地方','18888888888');

insert into t_quanzi_tuan(id,tuanZhangName,name,province,city,area,detailInfo,mobile)
values('3','wangerxiao3','MyOO3','江苏省','南京市','江宁区','这是什么地方','18888888888');


insert into t_quanzi_tuan(id,tuanZhangName,name,province,city,area,detailInfo,mobile)
values('4','wangerxiao4','MyOO4','江苏省','南京市','江宁区','这是什么地方','18888888888');






