CREATE DATABASE `caobaodb` ;

CREATE TABLE `t_sysoperator` (
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
