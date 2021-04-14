/*
 Navicat Premium Data Transfer

 Source Server         : 本机
 Source Server Type    : MySQL
 Source Server Version : 50527
 Source Host           : localhost:3306
 Source Schema         : activiti

 Target Server Type    : MySQL
 Target Server Version : 50527
 File Encoding         : 65001

 Date: 14/04/2021 10:47:05
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_qingjia
-- ----------------------------
DROP TABLE IF EXISTS `t_qingjia`;
CREATE TABLE `t_qingjia`  (
  `uuid` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `cause` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请假原因',
  `day` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '天数',
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `times` datetime NULL DEFAULT NULL COMMENT '请假时间',
  PRIMARY KEY (`uuid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_qingjia
-- ----------------------------
INSERT INTO `t_qingjia` VALUES ('130961ec452c4c96ba34e00f68c8025e', '曹猛22', '创4成团夜内场观众', '3', '18136711321', '2021-04-13 14:19:20');
INSERT INTO `t_qingjia` VALUES ('296e67070c0f46a484649faa6e1596bf', '曹猛33', '创4成团夜内场观众', '3', '18136711321', '2021-04-13 14:19:24');
INSERT INTO `t_qingjia` VALUES ('324c1e83583c492991222666d40341b6', '利路修请假条9', '想逃离海花岛', '7', '18136711321', '2021-04-14 10:26:54');
INSERT INTO `t_qingjia` VALUES ('43ae02f0875246c5a88330c85926f66d', '曹猛', '创4成团夜内场观众', '3', '18136711321', '2021-04-13 14:09:19');
INSERT INTO `t_qingjia` VALUES ('538a078e877545b9b8970e6c46d0118c', '利路修请假条10', '想逃离海花岛', '7', '18136711321', '2021-04-14 10:35:40');
INSERT INTO `t_qingjia` VALUES ('5a181f19d9604dfc91face327a4b7fc4', '利路修请假条3', '想逃离海花岛', '7', '18136711321', '2021-04-14 10:26:44');
INSERT INTO `t_qingjia` VALUES ('5bcde2588ada406ca87150a9fc666daa', '曹猛11', '创4成团夜内场观众', '3', '18136711321', '2021-04-13 14:19:16');
INSERT INTO `t_qingjia` VALUES ('68e54c8c8323411ab9f1f3836b009d31', '曹猛44', '创4成团夜内场观众', '3', '18136711321', '2021-04-13 14:20:12');
INSERT INTO `t_qingjia` VALUES ('98799d1730c2468d9379a31aac5a25f1', '刘旭磊', '吃翔', '30', '18136711321', '2021-04-13 13:50:40');
INSERT INTO `t_qingjia` VALUES ('9a02c84cafbc4b528bc74c7ae2d87917', '利路修请假条2', '想逃离海花岛', '3', '18136711321', '2021-04-14 10:18:01');
INSERT INTO `t_qingjia` VALUES ('a639cc6b01144fb8a0cc864818209a97', '利路修请假条1', '想逃离海花岛', '3', '18136711321', '2021-04-14 10:17:54');
INSERT INTO `t_qingjia` VALUES ('b40508e1633f4bc2bf9a9de2aead8518', '利路修请假条', '想逃离海花岛', '7', '18136711321', '2021-04-14 09:28:13');
INSERT INTO `t_qingjia` VALUES ('d2b2bb7a02574dd486497078e7323038', '利路修请假条3', '想逃离海花岛', '6', '18136711321', '2021-04-14 10:18:09');
INSERT INTO `t_qingjia` VALUES ('e71c4283a7924d418abbefc06232c86e', '束长睿', '整容', '30', '18136711321', '2021-04-13 10:42:34');

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `uuid` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `real_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`uuid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('0dbf020a-3196-4e7e-88d6-f592fd45b195', 'wulei', '组长1');
INSERT INTO `t_user` VALUES ('2d15f621-93ae-47c5-a65d-5fadf6e45b1b', 'ganwangxing', '总经理');
INSERT INTO `t_user` VALUES ('460d1e63-f932-4973-80c6-58d5a1c3d58f', 'liuhaoran', '组长2');
INSERT INTO `t_user` VALUES ('49206bf4-3232-4eaa-a1c5-65dba3e236e2', 'xiaozhang', '小张');
INSERT INTO `t_user` VALUES ('943nf9eut89oijwp299j', 'caoguang', '辅导员');
INSERT INTO `t_user` VALUES ('ba387f8a-9699-4f20-9e7e-ef01f96ad919', 'gongjun', '部门经理');
INSERT INTO `t_user` VALUES ('oighhqnqn8298929r', 'caomeng', '班主任');

SET FOREIGN_KEY_CHECKS = 1;
