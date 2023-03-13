/*
Navicat MySQL Data Transfer
Source Database       : seckill

Target Server Type    : MYSQL
Target Server Version : 80030
File Encoding         : 65001

Date: 2023-03-11 20:20:39
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` bigint NOT NULL COMMENT '用户id, 默认手机号',
  `nickname` varchar(255) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '用户昵称',
  `password` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'MD5(MD5(pass明文+salt)+salt)',
  `salt` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `head` varchar(128) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '头像',
  `register_date` datetime DEFAULT NULL COMMENT '注册时间',
  `last_login_date` datetime DEFAULT NULL COMMENT '最后一次登录时间',
  `login_count` int DEFAULT '0' COMMENT '登录次数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
