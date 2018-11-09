CREATE DATABASE `caobaodb` ;

# 系统
CREATE TABLE `t_sys_menu` (
	`id` VARCHAR(255) NOT NULL,
	`name` VARCHAR(255) NOT NULL,
	`targetUrl` VARCHAR(255) NULL,
	`pid` VARCHAR(255) NOT NULL DEFAULT '-1',
	`sortNum` VARCHAR(255) NOT NULL,
	`enable` VARCHAR(1) NOT NULL DEFAULT 'Y',
	`isDefault` VARCHAR(1) NULL DEFAULT 'N',
	
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
	`name` VARCHAR(255) NULL,
	`wxCode` VARCHAR(255) NULL,
	`email` VARCHAR(255) NULL,
	`mobile` VARCHAR(255) NULL,
	`createTime` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
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
	`remark` VARCHAR(255) NULL,
	`createTime` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`enable` VARCHAR(1) NOT NULL DEFAULT 'Y',
	
	PRIMARY KEY (`id`),
	UNIQUE KEY `name` (`name`)
);

# 消费者
CREATE TABLE `t_terminaluser` (
	`id` VARCHAR(255) NOT NULL,
	`name` VARCHAR(255) NOT NULL,
	`image` VARCHAR(255) NULL,
	`createTime` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`enable` VARCHAR(1) NOT NULL DEFAULT 'Y',
	
	PRIMARY KEY (`id`)
);

#物流
CREATE TABLE `t_delivery_area` (
	`id` VARCHAR(255) NOT NULL,
	`name` VARCHAR(255) NOT NULL,
	`cityInfo` VARCHAR(255) NULL,
	`createTime` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`enable` VARCHAR(1) NOT NULL DEFAULT 'Y',
	
	PRIMARY KEY (`id`),
	UNIQUE KEY `name` (`name`)
);

CREATE TABLE `t_delivery_feerule` (
	`id` VARCHAR(255) NOT NULL,
	`name` VARCHAR(255) NOT NULL,
	`deliveryAreaId` VARCHAR(255) NOT NULL,
	`calcType` VARCHAR(255) NOT NULL,
	`beginValue` decimal(10,2) NOT NULL,
	`endValue` decimal(10,2) NOT NULL,
	`fee` INT NOT NULL,
	`createOperatorId` VARCHAR(255) NULL,
	`createTime` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`enable` VARCHAR(1) NOT NULL DEFAULT 'Y',
	
	PRIMARY KEY (`id`),
	UNIQUE KEY `name` (`name`)
);

###############物流费用计算类型
CREATE TABLE `t_delivery_feecalctype` (
	`id` VARCHAR(255) NOT NULL,
	`name` VARCHAR(255) NOT NULL,
	`sortNum` VARCHAR(3) NOT NULL DEFAULT '0',
	
	PRIMARY KEY (`id`)
);

#佣金
CREATE TABLE `t_commission_type` (
	`id` VARCHAR(255) NOT NULL,
	`name` VARCHAR(255) NOT NULL,
	`commissionRate` decimal(5,2) NOT NULL,
	
	PRIMARY KEY (`id`)
);

CREATE TABLE `t_commission_goods` (
	`id` VARCHAR(255) NOT NULL,
	`goodsId` VARCHAR(255) NOT NULL,
	`commissionTypeId` VARCHAR(255) NOT NULL DEFAULT '-1',
	`commissionRate` decimal(5,2) NOT NULL,
	`createTime` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`enable` VARCHAR(1) NOT NULL DEFAULT 'Y',
	
	PRIMARY KEY (`id`),
	UNIQUE KEY `commissionType` (`goodsId`,`commissionTypeId`)
);

CREATE TABLE `t_commission_calc` (
	`id` VARCHAR(255) NOT NULL,
	`orderId` VARCHAR(255) NOT NULL,
	`tuanZhangId` VARCHAR(255) NOT NULL,
	`createTime` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`goodsId` VARCHAR(255) NOT NULL,
	`goodsFee` INT NOT NULL,
	`commissionTypeId` VARCHAR(255) NOT NULL,
	`calcFee` INT NOT NULL,
	`state` VARCHAR(255) NOT NULL,
	`payOperatorId` VARCHAR(255) NULL,
	`applyTime` DATETIME NULL,
	`payTime` DATETIME NULL,
	`disableTime` DATETIME NULL,
	
	PRIMARY KEY (`id`)
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
	`name` VARCHAR(50) NOT NULL,
	
	PRIMARY KEY (`name`)
);

CREATE TABLE `t_basicinfo_city` (
	`name` VARCHAR(50) NOT NULL,
	`province` VARCHAR(50) NOT NULL,
	UNIQUE KEY `name` (`name`,`province`)
);

CREATE TABLE `t_basicinfo_area` (
	`name` VARCHAR(50) NOT NULL,
	`city` VARCHAR(50) NOT NULL,
	UNIQUE KEY `name` (`name`,`city`)
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
	`enable` VARCHAR(1) NOT NULL DEFAULT 'I',
	`hasSecKillPlan` VARCHAR(1) NULL DEFAULT 'N',
	`hasGroupBuyPlan` VARCHAR(1) NULL DEFAULT 'N',
	`createOperatorId` VARCHAR(255) NOT NULL,
	`deliveryAreaId` VARCHAR(255) NULL DEFAULT '-1',
	`deliveryFeeRuleId` VARCHAR(255) NULL DEFAULT '-1',
	`createTime` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	
	PRIMARY KEY (`id`),
	UNIQUE KEY `name` (`name`)
);

CREATE TABLE `t_goods_homeimgs` (
	`id` VARCHAR(255) NOT NULL,
	`sortNum` INT NULL DEFAULT 0,
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
	`planInventory` INT NOT NULL DEFAULT 0,
	`currentInventory` INT NOT NULL DEFAULT 0,
	`limitBuyNum` INT NOT NULL,
	`createTime` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`createOperatorId` VARCHAR(255) NOT NULL,
	`deliveryAreaId` VARCHAR(255) NOT NULL,
	`deliveryFeeRuleId` VARCHAR(255) NOT NULL,
	`enable` VARCHAR(1) NOT NULL DEFAULT 'Y',
	
	
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
	`shareTuanZhangId` VARCHAR(255) NULL,
	`goodsNum` INT NOT NULL DEFAULT 1,
	`createTime` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	
	
	PRIMARY KEY (`id`)
);

#商品库存
CREATE TABLE `t_inventory_goods` (
	`id` VARCHAR(255) NOT NULL,
	`goodsId` VARCHAR(255) NOT NULL,
	`changeValue` INT NOT NULL,
	`changeType` VARCHAR(255) NOT NULL,
	`operatorId` VARCHAR(255) NULL,
	`description` VARCHAR(255) NULL DEFAULT 0,
	`createTime` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	
	
	PRIMARY KEY (`id`)
);

CREATE TABLE `t_inventory_seckill` (
	`id` VARCHAR(255) NOT NULL,
	`planId` VARCHAR(255) NOT NULL,
	`changeValue` INT NOT NULL,
	`changeType` VARCHAR(255) NOT NULL,
	`operatorId` VARCHAR(255) NULL,
	`description` VARCHAR(255) NULL DEFAULT 0,
	`createTime` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	
	
	PRIMARY KEY (`id`)
);


#订单
CREATE TABLE `t_order` (
	`id` VARCHAR(255) NOT NULL,
	`terminalUserId` VARCHAR(255) NOT NULL,
	`fee` INT NOT NULL,
	`deliveryFee` INT NULL DEFAULT 0,
	`state` VARCHAR(255) NOT NULL,
	`shareTuanZhangId` VARCHAR(255) NULL,
	`deliveryTuanZhangId` VARCHAR(255) NULL,
	`contactName` VARCHAR(255) NOT NULL,
	`contactMobile` VARCHAR(255) NOT NULL,
	`province` VARCHAR(255) NOT NULL,
	`city` VARCHAR(255) NOT NULL,
	`area` VARCHAR(255) NOT NULL,
	`detailInfo` VARCHAR(255) NOT NULL,
	
	`createTime` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`payTime` DATETIME NULL,
	`deliveryTime` DATETIME NULL,
	`deliverySiteReceiveTime` DATETIME NULL,
	`receiveTime` DATETIME NULL,
	`returnApplyTime` DATETIME NULL,
	`returnCompleteTime` DATETIME NULL,
	`commissionCalcTime` DATETIME NULL,
	
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
	`goodsName` VARCHAR(255) NOT NULL,
	`goodsMainImgData` MediumBlob NULL,
	
	PRIMARY KEY (`id`)
);

#quanzi
CREATE TABLE `t_quanzi_tuan` (
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
	`isDeliverySite` VARCHAR(1) NULL DEFAULT 'N',
	
	
	PRIMARY KEY (`id`),
	UNIQUE KEY `name` (`name`)
);

CREATE TABLE `t_quanzi_tuangoods` (
	`id` VARCHAR(255) NOT NULL,
	`tuanId` VARCHAR(255) NOT NULL,
	`buyType` VARCHAR(255) NOT NULL,
	`resId` VARCHAR(255) NOT NULL,
	
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
	`imgData` MediumBlob NULL,
	
	PRIMARY KEY (`id`)
);

