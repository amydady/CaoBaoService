CREATE DATABASE `caobaodb` ;

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

CREATE TABLE `t_terminaluser` (
	`id` VARCHAR(255) NOT NULL,
	`name` VARCHAR(255) NOT NULL,
	`wxCode` VARCHAR(255) NOT NULL,
	`mobile` VARCHAR(255) NOT NULL,
	`isTuanZhang` VARCHAR(1) NOT NULL DEFAULT 'N',
	`isMaiShou` VARCHAR(1) NOT NULL DEFAULT 'N',
	`createTime` VARCHAR(255) NOT NULL,
	
	PRIMARY KEY (`id`),
	UNIQUE KEY `wxCode` (`wxCode`),
	UNIQUE KEY `mobile` (`mobile`)
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
	`createTime` VARCHAR(255) NOT NULL,
	`createYear` INT NOT NULL,
	`createMonth` INT NOT NULL,
	
	PRIMARY KEY (`id`)
);

