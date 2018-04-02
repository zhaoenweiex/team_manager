USE `team_manager`;

DROP TABLE IF EXISTS `workitem`;
CREATE TABLE `workitem` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `createtime` date(15) NOT NULL COMMENT '创建时间',
  `content` varchar(511) DEFAULT NULL COMMENT '工作内容',
  `status` varchar(11) DEFAULT NULL COMMENT '状态',
  `content` varchar(511) DEFAULT NULL COMMENT '备注',
  `planflag` boolean(1) DEFAULT NULL COMMENT '是否计划中',
  `userid` bigint(20) DEFAULT NULL COMMENT '所属用户',
  `belongprojectid` bigint(20) DEFAULT NULL COMMENT '所属项目',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='工作项信息';

DROP TABLE IF EXISTS `project`;
CREATE TABLE `project` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '项目名称',
  `members` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '团队成员',
  `targets` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '项目目标',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='项目信息';
DROP TABLE IF EXISTS `projecttarget`;
CREATE TABLE `projecttarget` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '目标内容',
  `time` date(63) NOT NULL COMMENT '时间节点',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='项目目标表';
DROP TABLE IF EXISTS `project_user`;
CREATE TABLE `project_user` (
  `projectid` bigint(20) REFERENCES project(`id`),
  `userid` bigint(255) REFERENCES user(`id`),
  PRIMARY KEY (`projectid`,`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='项目成员关联表';
DROP TABLE IF EXISTS `project_projecttarget`;
CREATE TABLE `project_projecttarget` (
  `projectid` bigint(20) REFERENCES project(`id`),
  `targetid` bigint(255) REFERENCES projecttarget(`id`),
  PRIMARY KEY (`projectid`,`targetid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='项目目标关联表';

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL DEFAULT '' COMMENT '用户名',
  `password` varchar(32) DEFAULT NULL COMMENT '密码',
  `type` varchar(2) DEFAULT NULL COMMENT '用户类型',
  `enabled` int(2) DEFAULT NULL COMMENT '是否可用',
  `trueName` varchar(32) DEFAULT NULL COMMENT '真实姓名',
  `email` varchar(100) DEFAULT NULL COMMENT '邮件',
  `phone` varchar(255) DEFAULT NULL COMMENT '联系电话',
  `organizationid` bigint(20) DEFAULT NULL COMMENT '组织机构',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='用户信息表';
DROP TABLE IF EXISTS `organization`;
CREATE TABLE `organization` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL DEFAULT '' COMMENT '组织机构名称',
  `parentid` bigint(20) DEFAULT NULL COMMENT '上级组织机构',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='组织机构表';
ALTER TABLE workitem ADD CONSTRAINT userid FOREIGN KEY (userid) REFERENCES user;
ALTER TABLE workitem ADD CONSTRAINT belongprojectid FOREIGN KEY (belongprojectid) REFERENCES project;
ALTER TABLE user ADD CONSTRAINT organizationid FOREIGN KEY (organizationid) REFERENCES organization;
ALTER TABLE organization ADD CONSTRAINT parentid KEY (parentid) REFERENCES organization;