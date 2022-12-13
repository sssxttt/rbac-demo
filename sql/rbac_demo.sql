/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Host           : localhost:3306
 Source Schema         : rbac_demo

 Target Server Type    : MySQL
 Target Server Version : 80018
 File Encoding         : 65001

 Date: 14/12/2022 07:02:39
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` bigint(20) UNSIGNED NOT NULL COMMENT '菜单ID',
  `label` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单名称',
  `parent_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '父菜单ID',
  `parent_label` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父菜单名称',
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单权限标识',
  `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '路由组件路径',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '路由名称',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求路径（授权地址）',
  `type` tinyint(3) UNSIGNED NULL DEFAULT NULL COMMENT '菜单类型：0 目录、1 菜单、2 按钮',
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单图标',
  `sort` int(255) UNSIGNED NULL DEFAULT NULL COMMENT '排序',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `is_delete` tinyint(3) UNSIGNED NULL DEFAULT 0 COMMENT '逻辑删除标志：0 已删除、1 未删除',
  `create_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_user` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '创建人（用户ID）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单表（权限表）' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, '系统管理', 0, '顶级菜单', 'system:manager', '/system', 'system', '/system', 0, 'el-icon-menu', NULL, NULL, 0, '2022-12-14 06:57:12', '2022-12-14 06:57:12', NULL);
INSERT INTO `sys_menu` VALUES (2, '菜单管理', 1, '系统管理', 'system:menu', '/system/menu', 'menu', '/system/menu', 0, 'el-icon-menu', NULL, NULL, 0, '2022-12-14 06:56:54', '2022-12-14 06:56:54', NULL);
INSERT INTO `sys_menu` VALUES (3, '角色管理', 1, '系统管理', 'system:role', '/system/role', 'role', '/system/role', 0, 'el-icon-menu', NULL, NULL, 0, '2022-12-14 06:57:15', '2022-12-14 06:57:15', NULL);
INSERT INTO `sys_menu` VALUES (4, '用户管理', 1, '系统管理', 'system:user', '/system/user', 'user', '/system/user', 0, 'el-icon-menu', NULL, NULL, 0, '2022-12-14 06:57:22', '2022-12-14 06:57:22', NULL);
INSERT INTO `sys_menu` VALUES (5, '示例目录', 0, '顶级菜单', 'eg', '/eg', 'eg', '/eg', 0, 'el-icon-menu', NULL, NULL, 0, '2022-12-14 07:01:11', '2022-12-14 07:01:11', NULL);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint(20) UNSIGNED NOT NULL COMMENT '角色ID（雪花算法）',
  `role_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `role_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色编码',
  `create_user` bigint(255) UNSIGNED NULL DEFAULT NULL COMMENT '角色创建者（账号的ID）',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `is_delete` tinyint(3) UNSIGNED NULL DEFAULT 0 COMMENT '逻辑删除标志：0 未删除、1 已删除',
  `create_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '超级管理员', 'ROLE_ADMIN', NULL, NULL, 0, '2022-12-14 06:42:25', '2022-12-14 06:42:25');
INSERT INTO `sys_role` VALUES (2, '系统管理员', 'ROLE_SYSTEM', NULL, NULL, 0, '2022-12-14 06:41:50', '2022-12-14 06:41:50');
INSERT INTO `sys_role` VALUES (3, '普通用户', 'ROLE_USER', NULL, NULL, 0, NULL, NULL);

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `role_id` bigint(20) UNSIGNED NOT NULL COMMENT '角色ID',
  `menu_id` bigint(20) UNSIGNED NOT NULL COMMENT '菜单ID'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色菜单关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (1, 1);
INSERT INTO `sys_role_menu` VALUES (1, 2);
INSERT INTO `sys_role_menu` VALUES (1, 3);
INSERT INTO `sys_role_menu` VALUES (1, 4);
INSERT INTO `sys_role_menu` VALUES (3, 5);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint(20) UNSIGNED NOT NULL COMMENT '用户ID（雪花算法）',
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录名（用户名）',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录密码',
  `nick_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `real_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `gender` tinyint(3) NULL DEFAULT NULL COMMENT '性别：0 女、1 男',
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  `is_account_non_expired` tinyint(3) UNSIGNED NULL DEFAULT 1 COMMENT '账号是否过期：0 已过期、1 未过期',
  `is_account_non_locked` tinyint(3) UNSIGNED NULL DEFAULT 1 COMMENT '账号是否被锁定：0 已锁定、1 未锁定',
  `is_credentials_non_expired` tinyint(3) UNSIGNED NULL DEFAULT 1 COMMENT '账号密码是否过期：0 已过期、1 未过期',
  `is_enabled` tinyint(3) UNSIGNED NULL DEFAULT 1 COMMENT '账号是否是可用：0 禁用、1 可用',
  `is_delete` tinyint(3) UNSIGNED NULL DEFAULT 0 COMMENT '逻辑删除标志：0 未删除、1 已删除',
  `create_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', '$2a$10$pQidCqrvY6um8AlHwj0wtuLz/exRYXHEoTQsCW8619Y7mVXO2e3I2', '超级管理员', '超级管理员', 1, NULL, NULL, NULL, 1, 1, 1, 1, 0, '2022-12-14 06:43:51', '2022-12-14 06:43:51');
INSERT INTO `sys_user` VALUES (2, 'ls', '$2a$10$pQidCqrvY6um8AlHwj0wtuLz/exRYXHEoTQsCW8619Y7mVXO2e3I2', '普通用户', '普通用户', NULL, NULL, NULL, NULL, 1, 1, 1, 1, 0, '2022-12-14 06:44:01', '2022-12-14 06:44:01');
INSERT INTO `sys_user` VALUES (3, 'zs', '$2a$10$pQidCqrvY6um8AlHwj0wtuLz/exRYXHEoTQsCW8619Y7mVXO2e3I2', '系统管理员', '系统管理员', NULL, NULL, NULL, NULL, 1, 1, 1, 1, 0, '2022-12-14 06:43:33', '2022-12-14 06:43:33');
INSERT INTO `sys_user` VALUES (1602774035647533058, 'wmbrikx', '$2a$10$LxwEMqF5C8IaP.kr2OOZK.VuEMOEDYdHYk/9RzcrEQo4PqmGBc2cm', 'Nancy Jones', '卢磊', 1, '18631667694', 'u.tofpgggh@jvrlnpm.nr', 'http://dummyimage.com/300x600', 1, 1, 1, 1, 0, '2022-12-14 06:35:23', NULL);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `user_id` bigint(20) UNSIGNED NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) UNSIGNED NOT NULL COMMENT '角色ID'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户角色关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1);
INSERT INTO `sys_user_role` VALUES (2, 3);
INSERT INTO `sys_user_role` VALUES (3, 2);

SET FOREIGN_KEY_CHECKS = 1;
