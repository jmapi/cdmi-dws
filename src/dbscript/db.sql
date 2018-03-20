create database if not exists `msm`;

use `msm`;

-- ----------------------------
-- Table structure for `t_complain`
-- ----------------------------
DROP TABLE IF EXISTS `t_complain`;
CREATE TABLE `t_complain` (
  `id` varchar(255) NOT NULL,
  `app_id` varchar(255) DEFAULT NULL,
  `complainant_id` varchar(255) DEFAULT NULL,
  `complainant_sid` varchar(255) DEFAULT NULL,
  `content` varchar(255) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `deal_reply` varchar(255) DEFAULT NULL,
  `finish_time` datetime DEFAULT NULL,
  `liableman_id` varchar(255) DEFAULT NULL,
  `liableman_name` varchar(255) DEFAULT NULL,
  `question_type` varchar(255) NOT NULL,
  `receiver_id` varchar(255) DEFAULT NULL,
  `receiver_name` varchar(255) DEFAULT NULL,
  `receiver_type` varchar(255) DEFAULT NULL,
  `site_id` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `target_id` varchar(255) NOT NULL,
  `target_sid` varchar(255) DEFAULT NULL,
  `target_type` varchar(255) NOT NULL,
  `tenant_id` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for `t_praise`
-- ----------------------------
DROP TABLE IF EXISTS `t_praise`;
CREATE TABLE `t_praise` (
  `id` varchar(255) NOT NULL,
  `app_id` varchar(255) NOT NULL,
  `create_time` datetime NOT NULL,
  `site_id` varchar(255) DEFAULT NULL,
  `target_id` varchar(255) NOT NULL,
  `target_type` varchar(255) NOT NULL,
  `tenant_id` varchar(255) DEFAULT NULL,
  `account_aid` varchar(255) DEFAULT NULL,
  `account_tid` varchar(255) DEFAULT NULL,
  `account_id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for `t_file`
-- ----------------------------
DROP TABLE IF EXISTS `t_file`;
CREATE TABLE `t_file` (
  `id` varchar(255) NOT NULL,
  `target_id` varchar(255) NOT NULL,
  `type` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for `t_comment`
-- ----------------------------
DROP TABLE IF EXISTS `t_comment`;
CREATE TABLE `t_comment` (
  `id` varchar(255) NOT NULL,
  `app_id` varchar(255) DEFAULT NULL,
  `commentator_id` varchar(255) NOT NULL,
  `commentator_sid` varchar(255) DEFAULT NULL,
  `content` varchar(255) NOT NULL,
  `create_time` datetime NOT NULL,
  `site_id` varchar(255) DEFAULT NULL,
  `target_id` varchar(255) NOT NULL,
  `terget_sid` varchar(255) DEFAULT NULL,
  `target_type` varchar(255) DEFAULT NULL,
  `tenant_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
