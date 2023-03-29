/*
Navicat MySQL Data Transfer

Source Server         : seckill_user
Source Server Version : 80030
Source Host           : localhost:3306
Source Database       : seckill

Target Server Type    : MYSQL
Target Server Version : 80030
File Encoding         : 65001

Date: 2023-03-16 11:49:16
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_seckill_goods
-- ----------------------------
DROP TABLE IF EXISTS `t_seckill_goods`;
CREATE TABLE `t_seckill_goods` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '秒杀商品主键',
  `goods_id` bigint NOT NULL COMMENT '原商品id',
  `seckill_price` decimal(10,2) DEFAULT '0.00' COMMENT '秒杀价',
  `seckill_stock` int DEFAULT '0' COMMENT '秒杀库存',
  `begin_time` datetime DEFAULT NULL COMMENT '秒杀开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '秒杀结束时间',
  PRIMARY KEY (`id`),
  KEY `goods_id` (`goods_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of t_seckill_goods
-- ----------------------------
INSERT INTO `t_seckill_goods` VALUES ('1', '2', '1500.00', '10', '2023-03-16 09:27:32', '2024-02-23 12:19:14');
INSERT INTO `t_seckill_goods` VALUES ('2', '3', '18.40', '100', '2023-03-16 01:10:06', '2023-11-11 23:14:02');
INSERT INTO `t_seckill_goods` VALUES ('3', '8', '280.90', '3000', '2023-03-15 19:14:07', '2023-06-29 13:11:38');
INSERT INTO `t_seckill_goods` VALUES ('4', '11', '45.50', '10000', '2023-03-15 16:31:59', '2023-05-27 21:11:41');
INSERT INTO `t_seckill_goods` VALUES ('5', '7', '178.20', '20000', '2023-03-16 03:13:04', '2024-02-21 18:06:51');
INSERT INTO `t_seckill_goods` VALUES ('6', '5', '4.25', '8000', '2023-03-15 12:48:56', '2024-01-30 22:03:02');
INSERT INTO `t_seckill_goods` VALUES ('7', '9', '4.20', '1600', '2023-03-16 01:48:22', '2023-12-29 00:21:53');
INSERT INTO `t_seckill_goods` VALUES ('8', '18', '79.00', '800', '2023-03-16 05:23:46', '2023-09-12 02:05:47');
INSERT INTO `t_seckill_goods` VALUES ('9', '22', '358.00', '900', '2023-03-16 10:16:33', '2023-05-04 00:15:30');
INSERT INTO `t_seckill_goods` VALUES ('10', '25', '15.80', '200', '2023-03-15 17:21:03', '2023-05-02 02:52:47');
INSERT INTO `t_seckill_goods` VALUES ('11', '34', '238.00', '1000', '2023-03-16 00:17:53', '2023-10-06 02:10:14');
INSERT INTO `t_seckill_goods` VALUES ('12', '66', '2.90', '1000', '2023-03-16 08:34:11', '2023-05-15 12:01:41');
