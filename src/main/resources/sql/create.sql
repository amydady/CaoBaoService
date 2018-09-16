CREATE DATABASE `caobaodb` ;

CREATE TABLE `t_sysoperator` (
	`id` VARCHAR(255) NOT NULL,
	`username` VARCHAR(255) NOT NULL,
	`password` VARCHAR(255) NOT NULL,
	`name` VARCHAR(255) NOT NULL,
	`wxCode` VARCHAR(255) NOT NULL,
	`email` VARCHAR(255) NOT NULL,
	`mobile` VARCHAR(255) NOT NULL,
	`enable` VARCHAR(1) NOT NULL,
	
	PRIMARY KEY (`id`)
)
COLLATE='latin1_swedish_ci'
ENGINE=InnoDB;
