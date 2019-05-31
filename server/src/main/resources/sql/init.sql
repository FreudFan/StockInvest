# MySql运行在safe-updates模式下，该模式会导致非主键条件下无法执行update或者delete命令。
show variables like 'SQL_SAFE_UPDATES';
SET SQL_SAFE_UPDATES = 0;

CREATE DATABASE `StockInvest` CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_general_ci';
USE StockInvest;

DROP TABLE IF EXISTS SYS_AREA;
CREATE TABLE `SYS_AREA` (
  `objid` int NOT NULL AUTO_INCREMENT,
  `code` int DEFAULT NULL,
  `name` varchar(90) DEFAULT NULL,
  `parentCode` varchar(60) DEFAULT NULL,
  `level` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`objid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS EMPLOYEE;
CREATE TABLE `EMPLOYEE` (
  `objid` int NOT NULL AUTO_INCREMENT,
  `name` varchar(60) DEFAULT NULL COMMENT '姓名',
  `user_name` varchar(60) DEFAULT NULL COMMENT '昵称',
  `birthday` datetime DEFAULT NULL COMMENT '生日',
  `email` varchar(60) DEFAULT NULL COMMENT '邮件',
  `phone` bigint DEFAULT NULL COMMENT '电话号码',
  `gender` int DEFAULT NULL COMMENT '性别',
  `password` varchar(60) DEFAULT NULL COMMENT '密码',
  `province` varchar(60) DEFAULT NULL COMMENT '省',
  `city` varchar(60) DEFAULT NULL COMMENT '市',
  `district` varchar(60) DEFAULT NULL COMMENT '区/县',
  `area` varchar(90) DEFAULT NULL COMMENT '具体地址',
  `posId` int(11) DEFAULT NULL COMMENT '职位ID',
  `enabled` int(11) DEFAULT '1' COMMENT '是否在职(1:在职，0:离职)',
  PRIMARY KEY (`objid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

# DROP TABLE IF EXISTS ADDRESS;
# CREATE TABLE `ADDRESS` (
#   `objid` int NOT NULL AUTO_INCREMENT,
#   `area` varchar(90) DEFAULT NULL COMMENT '具体地址',
#   `city` varchar(60) DEFAULT NULL COMMENT '市',
#   `district` varchar(60) DEFAULT NULL COMMENT '区/县',
#   `emp_id` int DEFAULT NULL COMMENT '用户表id',
#   `province` varchar(60) DEFAULT NULL COMMENT '省',
#   PRIMARY KEY (`objid`)
# ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `MENU`;
CREATE TABLE `MENU` (
  `objid` int(11) NOT NULL AUTO_INCREMENT,
  `url` varchar(64) DEFAULT NULL,
  `path` varchar(64) DEFAULT NULL,
  `component` varchar(64) DEFAULT NULL,
  `name` varchar(64) DEFAULT NULL,
  `iconCls` varchar(64) DEFAULT NULL,
  `keepAlive` tinyint(1) DEFAULT NULL,
  `requireAuth` tinyint(1) DEFAULT NULL,
  `parentId` int(11) DEFAULT NULL,
  `enabled` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`objid`),
  KEY `parentId` (`parentId`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `DEPARTMENT`;
CREATE TABLE `DEPARTMENT` (
  `objid` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL COMMENT '部门名称',
  `parentId` int(11) DEFAULT NULL COMMENT '上级部门id',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`objid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `MENU_ROLE`;
CREATE TABLE `MENU_ROLE` (
  `objid` int(11) NOT NULL AUTO_INCREMENT,
  `menuId` int(11) DEFAULT NULL COMMENT '目录id',
  `roleId` int(11) DEFAULT NULL COMMENT '对应角色id',
  `roleType` int(11) DEFAULT NULL COMMENT '角色类型{1:用户id、2:部门id、3:岗位id}',
  PRIMARY KEY (`objid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `POSITION`;
CREATE TABLE `POSITION` (
  `objid` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL COMMENT '职位',
  `deptId` int(11) NOT NULL comment '所属部门id',
  `enabled` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`objid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

