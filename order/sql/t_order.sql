/*
Navicat MySQL Data Transfer

Source Server         : seckill_user
Source Server Version : 80030
Source Host           : localhost:3306
Source Database       : seckill

Target Server Type    : MYSQL
Target Server Version : 80030
File Encoding         : 65001

Date: 2023-03-27 19:41:55
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_order
-- ----------------------------
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '订单id',
  `user_id` bigint DEFAULT NULL,
  `goods_id` bigint DEFAULT NULL COMMENT '购买商品id',
  `goods_name` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '商品名称',
  `goods_price` decimal(10,2) DEFAULT '0.00' COMMENT '商品价格',
  `goods_count` int DEFAULT '1' COMMENT '商品数量',
  `channel` tinyint DEFAULT '0' COMMENT '支付渠道, 0 pc  1 android 2 ios',
  `status` tinyint DEFAULT '0' COMMENT '订单状态0 新建未支付 1 已支付 2 已付款 3 已完成 4 已退款',
  `delivery_addr_id` bigint DEFAULT NULL COMMENT '送货地址id',
  `create_date` datetime DEFAULT NULL COMMENT '订单创建时间',
  `pay_date` datetime DEFAULT NULL COMMENT '订单支付时间',
  `seckill_id` bigint DEFAULT NULL COMMENT '秒杀活动主键',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_goods` (`user_id`,`goods_id`) USING BTREE,
  KEY `goods_id` (`goods_id`),
  KEY `seckill_id` (`seckill_id`)
) ENGINE=InnoDB AUTO_INCREMENT=112747 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
