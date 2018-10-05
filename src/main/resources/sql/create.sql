CREATE DATABASE `caobaodb` ;

# system
CREATE TABLE `t_sys_param` (
	`name` VARCHAR(255) NOT NULL,
	`value` VARCHAR(255) NOT NULL,
	
	PRIMARY KEY (`name`)
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

# terminaluser

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

# basic info

CREATE TABLE `t_basicinfo_province` (
	`id` VARCHAR(255) NOT NULL,
	`name` VARCHAR(255) NOT NULL,
	
	PRIMARY KEY (`id`)
);

CREATE TABLE `t_basicinfo_city` (
	`id` VARCHAR(255) NOT NULL,
	`name` VARCHAR(255) NOT NULL,
	`provinceId` VARCHAR(255) NOT NULL,
	
	PRIMARY KEY (`id`)
);

CREATE TABLE `t_basicinfo_area` (
	`id` VARCHAR(255) NOT NULL,
	`name` VARCHAR(255) NOT NULL,
	`cityId` VARCHAR(255) NOT NULL,
	
	PRIMARY KEY (`id`)
);

#goods --
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
	`classifyId` VARCHAR(255) NOT NULL,
	`supplierId` VARCHAR(255) NOT NULL,
	`name` VARCHAR(255) NOT NULL,
	`summaryDescription` VARCHAR(255) NOT NULL,
	`detailDescription` VARCHAR(255) NOT NULL,
	`mainImgUrl` VARCHAR(255) NOT NULL,
	`price` INT NOT NULL,
	`enable` VARCHAR(1) NOT NULL DEFAULT 'Y',
	`hasSecKillPlan` VARCHAR(1) NOT NULL DEFAULT 'N',
	`hasGroupBuyPlan` VARCHAR(1) NOT NULL DEFAULT 'N',
	`createOperatorId` VARCHAR(255) NOT NULL,
	`deliveryAreaId` VARCHAR(255) NOT NULL,
	`deliveryFeeRuleId` VARCHAR(255) NOT NULL,
	`createTime` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	
	PRIMARY KEY (`id`)
);

CREATE TABLE `t_seckill_seckillplan` (
	`id` VARCHAR(255) NOT NULL,
	`goodsId` VARCHAR(255) NOT NULL,
	`startTime` DATETIME NOT NULL,
	`endTime` DATETIME NOT NULL,
	`price` INT NOT NULL,
	`initInventory` INT NOT NULL,
	`limitBuyNum` INT NOT NULL,
	`enable` VARCHAR(1) NOT NULL DEFAULT 'Y',
	`createTime` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`createOperatorId` VARCHAR(255) NOT NULL,
	`deliveryAreaId` VARCHAR(255) NOT NULL,
	`deliveryFeeRuleId` VARCHAR(255) NOT NULL,
	
	
	PRIMARY KEY (`id`)
);

#order
CREATE TABLE `t_order` (
	`id` VARCHAR(255) NOT NULL,
	`terminalUserId` VARCHAR(255) NOT NULL,
	`createTime` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`fee` INT NOT NULL,
	`state` VARCHAR(255) NOT NULL,
	`provinceId` VARCHAR(255) NOT NULL,
	`cityId` VARCHAR(255) NOT NULL,
	`areaId` VARCHAR(255) NOT NULL,
	`detailInfo` VARCHAR(255) NOT NULL,
	`payTime` VARCHAR(255) NULL,
	`receiveTime` VARCHAR(255) NULL,
	`returnApplyTime` VARCHAR(255) NULL,
	`returnCompleteTime` VARCHAR(255) NULL,
	
	PRIMARY KEY (`id`)
);

CREATE TABLE `t_order_detail` (
	`id` VARCHAR(255) NOT NULL,
	`orderId` VARCHAR(255) NOT NULL,
	`buyType` VARCHAR(255) NOT NULL,
	`resId` VARCHAR(255) NOT NULL,
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
	`id` VARCHAR(255) NOT NULL,
	`dateTime` DATETIME NULL,
	
	PRIMARY KEY (`id`)
);

