CREATE DATABASE `caobaodb` ;

# 系统
CREATE TABLE `t_sys_menu` (
	`id` VARCHAR(255) NOT NULL,
	`name` VARCHAR(255) NOT NULL,
	`targetUrl` VARCHAR(255) NOT NULL,
	`pid` VARCHAR(255) NOT NULL DEFAULT '-1',
	`sortNum` VARCHAR(255) NOT NULL,
	`enable` VARCHAR(1) NOT NULL DEFAULT 'Y',
	`isDeault` VARCHAR(1) NULL,
	
	PRIMARY KEY (`id`)
);

CREATE TABLE `t_sys_param` (
	`name` VARCHAR(255) NOT NULL,
	`value` VARCHAR(255) NOT NULL,
	
	PRIMARY KEY (`name`)
);

CREATE TABLE `t_sys_reslock` (
	`type` VARCHAR(255) NOT NULL,
	`key` VARCHAR(255) NOT NULL,
	`createTime` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`disableTime` DATETIME NOT NULL,
	
	UNIQUE KEY `name` (`type`,`key`)
);

CREATE TABLE `t_sys_sysoperator` (
	`id` VARCHAR(255) NOT NULL,
	`username` VARCHAR(255) NOT NULL,
	`password` VARCHAR(255) NOT NULL,
	`name` VARCHAR(255) NOT NULL,
	`wxCode` VARCHAR(255) NOT NULL,
	`email` VARCHAR(255) NOT NULL,
	`mobile` VARCHAR(255) NOT NULL,
	`enable` VARCHAR(1) NOT NULL DEFAULT 'Y',
	
	PRIMARY KEY (`id`),
	UNIQUE KEY `username` (`username`),
	UNIQUE KEY `wxCode` (`wxCode`),
	UNIQUE KEY `email` (`email`),
	UNIQUE KEY `mobile` (`mobile`)
);

# 供应商
CREATE TABLE `t_supplier` (
	`id` VARCHAR(255) NOT NULL,
	`name` VARCHAR(255) NOT NULL,
	`remark` VARCHAR(255) NOT NULL,
	`createTime` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`enable` VARCHAR(1) NOT NULL DEFAULT 'Y',
	
	PRIMARY KEY (`id`),
	UNIQUE KEY `name` (`name`)
);

# 消费者

CREATE TABLE `t_terminaluser` (
	`id` VARCHAR(255) NOT NULL,
	`wxCode` VARCHAR(255) NOT NULL,
	`isTuanZhang` VARCHAR(1) NOT NULL DEFAULT 'N',
	`isMaiShou` VARCHAR(1) NOT NULL DEFAULT 'N',
	`createTime` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`enable` VARCHAR(1) NOT NULL DEFAULT 'Y',
	
	PRIMARY KEY (`id`),
	UNIQUE KEY `wxCode` (`wxCode`)
);

CREATE TABLE `t_terminaluser_deliveryaddress` (
	`id` VARCHAR(255) NOT NULL,
	`terminalUserId` VARCHAR(255) NOT NULL,
	`name` VARCHAR(255) NOT NULL,
	`provinceId` VARCHAR(255) NOT NULL,
	`cityId` VARCHAR(255) NOT NULL,
	`areaId` VARCHAR(255) NOT NULL,
	`detailInfo` VARCHAR(255) NOT NULL,
	`isDefault` VARCHAR(1) NOT NULL DEFAULT 'N',
	
	PRIMARY KEY (`id`),
	UNIQUE KEY `name` (`terminalUserId`,`name`)
);

# 基础信息

CREATE TABLE `t_basicinfo_province` (
	`id` VARCHAR(50) NOT NULL,
	`name` VARCHAR(50) NOT NULL,
	
	PRIMARY KEY (`id`)
);

CREATE TABLE `t_basicinfo_city` (
	`id` VARCHAR(50) NOT NULL,
	`name` VARCHAR(50) NOT NULL,
	`provinceId` VARCHAR(50) NOT NULL,
	
	PRIMARY KEY (`id`)
);

CREATE TABLE `t_basicinfo_area` (
	`id` VARCHAR(50) NOT NULL,
	`name` VARCHAR(50) NOT NULL,
	`cityId` VARCHAR(50) NOT NULL,
	
	PRIMARY KEY (`id`)
);

#商品 --
CREATE TABLE `t_goods_classify` (
	`id` VARCHAR(255) NOT NULL,
	`name` VARCHAR(255) NOT NULL,
	`sortNum` INT NOT NULL DEFAULT 0,
	`parentId` VARCHAR(255) NOT NULL DEFAULT '-1',
	`remark` VARCHAR(255) NULL,
	`enable` VARCHAR(1) NOT NULL DEFAULT 'Y',
	
	PRIMARY KEY (`id`),
	UNIQUE KEY `name` (`parentId`,`name`)
);

CREATE TABLE `t_goods` (
	`id` VARCHAR(255) NOT NULL,
	`classifyId` VARCHAR(255) NULL DEFAULT '-1',
	`supplierId` VARCHAR(255) NULL DEFAULT '-1',
	`name` VARCHAR(255) NOT NULL,
	`summaryDescription` VARCHAR(500) NULL,
	`mainImgData` MediumBlob NULL,
	`price` INT NOT NULL,
	`currentInventory` INT NOT NULL DEFAULT 0,
	`enable` VARCHAR(1) NOT NULL DEFAULT 'Y',
	`hasSecKillPlan` VARCHAR(1) NOT NULL DEFAULT 'N',
	`hasGroupBuyPlan` VARCHAR(1) NOT NULL DEFAULT 'N',
	`createOperatorId` VARCHAR(255) NOT NULL,
	`deliveryAreaId` VARCHAR(255) NOT NULL DEFAULT '-1',
	`deliveryFeeRuleId` VARCHAR(255) NOT NULL DEFAULT '-1',
	`createTime` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	
	PRIMARY KEY (`id`),
	UNIQUE KEY `name` (`name`)
);

CREATE TABLE `t_goods_homeimgs` (
	`id` VARCHAR(255) NOT NULL,
	`imgData` MediumBlob NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `t_goods_detailimgs` (
	`id` VARCHAR(255) NOT NULL,
	`goodsId` VARCHAR(255) NOT NULL,
	`title` VARCHAR(255) NOT NULL,
	`sortNum` VARCHAR(255) NOT NULL,
	`imgData` MediumBlob NULL,
	
	PRIMARY KEY (`id`)
);

#秒杀
CREATE TABLE `t_seckill_seckillplan` (
	`id` VARCHAR(255) NOT NULL,
	`goodsId` VARCHAR(255) NOT NULL,
	`startTime` DATETIME NOT NULL,
	`endTime` DATETIME NOT NULL,
	`price` INT NOT NULL,
	`currentInventory` INT NOT NULL DEFAULT 0,
	`limitBuyNum` INT NOT NULL,
	`createTime` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`createOperatorId` VARCHAR(255) NOT NULL,
	`deliveryAreaId` VARCHAR(255) NOT NULL,
	`deliveryFeeRuleId` VARCHAR(255) NOT NULL,
	
	
	PRIMARY KEY (`id`)
);

#团购
CREATE TABLE `t_groupbuy_groupbuyplan` (
	`id` VARCHAR(255) NOT NULL,
	`goodsId` VARCHAR(255) NOT NULL,
	`startTime` DATETIME NOT NULL,
	`endTime` DATETIME NOT NULL,
	`price` INT NOT NULL,
	`currentInventory` INT NOT NULL DEFAULT 0,
	`memberNum` INT NOT NULL,
	`limitBuyNum` INT NOT NULL,
	`createTime` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`createOperatorId` VARCHAR(255) NOT NULL,
	`deliveryAreaId` VARCHAR(255) NOT NULL,
	`deliveryFeeRuleId` VARCHAR(255) NOT NULL,
	
	
	PRIMARY KEY (`id`)
);

#购物车
CREATE TABLE `t_shoppingcart` (
	`id` VARCHAR(255) NOT NULL,
	`terminalUserId` VARCHAR(255) NOT NULL,
	`buyType` VARCHAR(20) NOT NULL,
	`resId` VARCHAR(255) NOT NULL,
	`goodsNum` INT NOT NULL DEFAULT 1,
	`createTime` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	
	
	PRIMARY KEY (`id`)
);

#订单
CREATE TABLE `t_order` (
	`id` VARCHAR(255) NOT NULL,
	`terminalUserId` VARCHAR(255) NOT NULL,
	`fee` INT NOT NULL,
	`state` VARCHAR(255) NOT NULL,
	`provinceId` VARCHAR(255) NOT NULL,
	`cityId` VARCHAR(255) NOT NULL,
	`areaId` VARCHAR(255) NOT NULL,
	`detailInfo` VARCHAR(255) NOT NULL,
	
	`createTime` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`payTime` DATETIME NULL,
	`receiveTime` DATETIME NULL,
	`returnApplyTime` DATETIME NULL,
	`returnCompleteTime` DATETIME NULL,
	
	`groupBuyPlanId` VARCHAR(255) NULL,
	`groupBuyTaskId` VARCHAR(255) NULL,
	`groupCompleteTime` DATETIME NULL,
	`groupCancelTime` DATETIME NULL,
	
	PRIMARY KEY (`id`)
);

CREATE TABLE `t_order_detail` (
	`id` VARCHAR(255) NOT NULL,
	`orderId` VARCHAR(255) NOT NULL,
	`buyType` VARCHAR(255) NOT NULL,
	`resId` VARCHAR(255) NOT NULL,
	`goodsId` VARCHAR(255) NOT NULL,
	`price` INT NOT NULL,
	`goodsNum` INT NOT NULL,
	`fee` INT NOT NULL,
	
	PRIMARY KEY (`id`)
);

#quanzi
CREATE TABLE `t_quanzi_tuan` (
	`id` VARCHAR(255) NOT NULL,
	`tuanZhangId` VARCHAR(255) NOT NULL,
	`name` VARCHAR(255) NOT NULL,
	`remark` VARCHAR(255) NULL,
	`idCardType` VARCHAR(255) NOT NULL,
	`idCardCode` VARCHAR(255) NOT NULL,
	`idCardImgUrlFront` VARCHAR(255) NOT NULL,
	`idCardImgUrlBack` VARCHAR(255) NOT NULL,
	`provinceId` VARCHAR(255) NOT NULL,
	`cityId` VARCHAR(255) NOT NULL,
	`areaId` VARCHAR(255) NOT NULL,
	`detailInfo` VARCHAR(255) NOT NULL,
	`createTime` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`labels` VARCHAR(255) NOT NULL,
	`enable` VARCHAR(1) NOT NULL DEFAULT 'Y',
	
	PRIMARY KEY (`id`)
);

CREATE TABLE `t_quanzi_tuanmember` (
	`id` VARCHAR(255) NOT NULL,
	`tuanId` VARCHAR(255) NOT NULL,
	`terminalUserId` VARCHAR(255) NOT NULL,
	`firstJoinTime` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`lastActiveTime` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	
	PRIMARY KEY (`id`)
);

#test
CREATE TABLE `t_test` (
	`id` VARCHAR(255) NULL,
	`dateTime` DATETIME NULL,
	`imgData` MediumBlob NULL,
	
	PRIMARY KEY (`id`)
);

