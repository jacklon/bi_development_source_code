/*
 Navicat Premium Data Transfer

 Source Server         : 本机MySql
 Source Server Type    : MySQL
 Source Server Version : 80015
 Source Host           : localhost:3306
 Source Schema         : bi

 Target Server Type    : MySQL
 Target Server Version : 80015
 File Encoding         : 65001

 Date: 14/09/2020 13:01:13
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for b_corp_info
-- ----------------------------
DROP TABLE IF EXISTS `b_corp_info`;
CREATE TABLE `b_corp_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '组织名称',
  `desc` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '组织简介',
  `cit_id` int(11) NULL DEFAULT NULL COMMENT '组织类型ID',
  `cit_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '组织类型名称',
  `dep_type_id` int(11) NULL DEFAULT NULL COMMENT '部门属性ID',
  `dep_type_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '部门属性名称',
  `charge_person` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '负责人',
  `cw_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '财务核算代码',
  `pid` int(11) NOT NULL DEFAULT 0 COMMENT '上一级组织Id',
  `dept_id_string` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所属部门字符串',
  `level` int(11) NOT NULL DEFAULT 1 COMMENT '当前层级',
  `enabled` tinyint(1) NULL DEFAULT 0 COMMENT '是否启用',
  `ordernumber` int(11) NULL DEFAULT 0 COMMENT '排序',
  `tenant_id` int(11) NOT NULL DEFAULT 0 COMMENT '租户ID',
  `add_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `add_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最后修改人',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `enabled`(`enabled`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统管理_公司组织架构' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of b_corp_info
-- ----------------------------
INSERT INTO `b_corp_info` VALUES (1, 'a', 'a', NULL, '', NULL, '', 'a', '', 0, '[]', 1, 0, 0, 0, '2020-08-31 06:20:40', 'AdminSys', '2020-08-31 07:51:06', 'AdminSys', 0);
INSERT INTO `b_corp_info` VALUES (2, 'b', 'b', NULL, '', NULL, '', '', '', 0, '[]', 1, 0, 0, 0, '2020-08-31 06:48:26', 'AdminSys', '2020-08-31 06:48:26', 'AdminSys', 0);

-- ----------------------------
-- Table structure for b_dic_code
-- ----------------------------
DROP TABLE IF EXISTS `b_dic_code`;
CREATE TABLE `b_dic_code`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `mainid` int(11) NULL DEFAULT NULL COMMENT '字典主表ID',
  `mainname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '主表名称',
  `code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '代码编码',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '代码名称',
  `ordernumber` int(11) NULL DEFAULT NULL COMMENT '排序',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '代码备注',
  `systemed` tinyint(1) NULL DEFAULT 0 COMMENT '系统标记 0-正常 1-系统',
  `enabled` tinyint(1) NULL DEFAULT 0 COMMENT '停用标记 0-正常 1-停用',
  `attr1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '代码属性1',
  `attr2` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '代码属性2',
  `attr3` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '代码属性3',
  `attr4` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '代码属性4',
  `attr5` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '代码属性5',
  `attr6` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '代码属性6',
  `attr7` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '代码属性7',
  `attr8` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '代码属性8',
  `attr9` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '代码属性9',
  `tenant_id` int(11) NOT NULL DEFAULT 0 COMMENT '租户ID',
  `add_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `add_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最后修改人',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `enabled`(`enabled`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '基础数据_字典_代码表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of b_dic_code
-- ----------------------------
INSERT INTO `b_dic_code` VALUES (1, 1, 'a', 'a', 'a', NULL, '', 0, 1, '', '', '', '', '', '', '', '', '', 0, '2020-08-31 18:42:09', 'AdminSys', '2020-08-31 18:42:09', 'AdminSys', 1);
INSERT INTO `b_dic_code` VALUES (2, 2, '系统_组织机构类型', '总公司', '总公司', 10, '', 0, 1, '', '', '', '', '', '', '', '', '', 0, '2020-09-03 06:24:07', 'AdminSys', '2020-09-03 06:24:07', 'AdminSys', 0);
INSERT INTO `b_dic_code` VALUES (3, 2, '系统_组织机构类型', '分公司', '分公司', 20, '', 0, 1, '', '', '', '', '', '', '', '', '', 0, '2020-09-03 06:24:07', 'AdminSys', '2020-09-03 06:24:07', 'AdminSys', 0);
INSERT INTO `b_dic_code` VALUES (4, 2, '系统_组织机构类型', '部门', '部门', 30, '', 0, 1, '', '', '', '', '', '', '', '', '', 0, '2020-09-03 06:24:07', 'AdminSys', '2020-09-03 06:24:07', 'AdminSys', 0);
INSERT INTO `b_dic_code` VALUES (5, 3, '系统_部门属性', '总编室', '总编室', 10, '', 0, 1, '', '', '', '', '', '', '', '', '', 0, '2020-09-03 06:26:12', 'AdminSys', '2020-09-03 06:26:12', 'AdminSys', 0);
INSERT INTO `b_dic_code` VALUES (6, 3, '系统_部门属性', '事业部', '事业部', 20, '', 0, 1, '', '', '', '', '', '', '', '', '', 0, '2020-09-03 06:26:12', 'AdminSys', '2020-09-03 06:26:12', 'AdminSys', 0);
INSERT INTO `b_dic_code` VALUES (7, 3, '系统_部门属性', '财务部', '财务部', 30, '', 0, 1, '', '', '', '', '', '', '', '', '', 0, '2020-09-03 06:26:12', 'AdminSys', '2020-09-03 06:26:12', 'AdminSys', 0);
INSERT INTO `b_dic_code` VALUES (8, 4, '后端消息_类型', '任务提醒', '任务提醒', 10, '', 0, 1, '', '', '', '', '', '', '', '', '', 0, '2020-09-03 06:27:11', 'AdminSys', '2020-09-03 06:27:11', 'AdminSys', 0);
INSERT INTO `b_dic_code` VALUES (9, 4, '后端消息_类型', '短信提醒', '短信提醒', 20, '', 0, 1, '', '', '', '', '', '', '', '', '', 0, '2020-09-03 06:27:11', 'AdminSys', '2020-09-03 06:27:11', 'AdminSys', 0);

-- ----------------------------
-- Table structure for b_dic_main
-- ----------------------------
DROP TABLE IF EXISTS `b_dic_main`;
CREATE TABLE `b_dic_main`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '字典列表名称',
  `desp` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典表描述',
  `systemed` tinyint(1) NULL DEFAULT 0 COMMENT '系统标记 0-正常 1-系统',
  `enabled` tinyint(1) NULL DEFAULT 0 COMMENT '停用标记 0-正常 1-停用',
  `ordernumber` int(11) NULL DEFAULT NULL COMMENT '排序',
  `tenant_id` int(11) NOT NULL DEFAULT 0 COMMENT '租户ID',
  `add_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `add_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最后修改人',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `enabled`(`enabled`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '基础数据_字典_主表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of b_dic_main
-- ----------------------------
INSERT INTO `b_dic_main` VALUES (1, 'a', 'a', 0, 1, NULL, 0, '2020-08-31 18:42:09', 'AdminSys', '2020-08-31 18:42:09', 'AdminSys', 1);
INSERT INTO `b_dic_main` VALUES (2, '系统_组织机构类型', '组织机构类型', 0, 1, 100, 0, '2020-09-03 06:24:07', 'AdminSys', '2020-09-03 06:24:07', 'AdminSys', 0);
INSERT INTO `b_dic_main` VALUES (3, '系统_部门属性', '部门属性', 0, 1, 200, 0, '2020-09-03 06:26:12', 'AdminSys', '2020-09-03 06:26:12', 'AdminSys', 0);
INSERT INTO `b_dic_main` VALUES (4, '后端消息_类型', '后端消息_类型', 0, 1, 300, 0, '2020-09-03 06:27:11', 'AdminSys', '2020-09-03 06:27:11', 'AdminSys', 0);

-- ----------------------------
-- Table structure for b_dic_tree_code
-- ----------------------------
DROP TABLE IF EXISTS `b_dic_tree_code`;
CREATE TABLE `b_dic_tree_code`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `main_id` int(11) NULL DEFAULT NULL COMMENT '字典主表ID',
  `main_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '主表名称',
  `code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '代码编码',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '代码名称',
  `ordernumber` int(11) NULL DEFAULT NULL COMMENT '排序',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '代码备注',
  `systemed` tinyint(1) NULL DEFAULT 0 COMMENT '系统标记 0-正常 1-系统',
  `enabled` tinyint(1) NULL DEFAULT 0 COMMENT '停用标记 0-正常 1-停用',
  `attr1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '代码属性1',
  `attr2` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '代码属性2',
  `attr3` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '代码属性3',
  `attr4` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '代码属性4',
  `attr5` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '代码属性5',
  `attr6` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '代码属性6',
  `attr7` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '代码属性7',
  `attr8` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '代码属性8',
  `attr9` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '代码属性9',
  `attr10` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '代码属性10',
  `level` int(11) NULL DEFAULT NULL COMMENT '节点所在层级',
  `parent_id` int(11) NULL DEFAULT 0 COMMENT '上一级节点ID',
  `if_leaf` tinyint(1) NULL DEFAULT 0 COMMENT '是否叶子节点',
  `if_analyze` tinyint(1) NULL DEFAULT 1 COMMENT '是否参与分析 0-否 1-是',
  `tenant_id` int(11) NOT NULL DEFAULT 0 COMMENT '租户ID',
  `add_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `add_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最后修改人',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '基础数据_树形字典_代码' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of b_dic_tree_code
-- ----------------------------
INSERT INTO `b_dic_tree_code` VALUES (1, 1, 'a', 'a', 'a', NULL, '', 0, 1, '', '', '', '', '', '', '', '', '', '', 1, 0, 0, 1, 0, '2020-08-31 18:34:34', 'AdminSys', '2020-08-31 18:34:34', 'AdminSys', 0);
INSERT INTO `b_dic_tree_code` VALUES (2, 1, 'a', 'b', 'b', NULL, '', 0, 1, '', '', '', '', '', '', '', '', '', '', 2, 1, 0, 1, 0, '2020-08-31 18:34:43', 'AdminSys', '2020-08-31 18:34:43', 'AdminSys', 0);

-- ----------------------------
-- Table structure for b_dic_tree_main
-- ----------------------------
DROP TABLE IF EXISTS `b_dic_tree_main`;
CREATE TABLE `b_dic_tree_main`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '字典列表名称',
  `desp` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典表描述',
  `systemed` tinyint(1) NULL DEFAULT 0 COMMENT '系统标记 0-正常 1-系统',
  `enabled` tinyint(1) NULL DEFAULT 0 COMMENT '停用标记 0-正常 1-停用',
  `ordernumber` int(11) NULL DEFAULT NULL COMMENT '排序',
  `tenant_id` int(11) NOT NULL DEFAULT 0 COMMENT '租户ID',
  `add_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `add_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最后修改人',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `enabled`(`enabled`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '基础数据_树形字典_主表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of b_dic_tree_main
-- ----------------------------
INSERT INTO `b_dic_tree_main` VALUES (1, 'a', '', 0, 1, 1, 0, '2020-08-31 18:23:44', 'AdminSys', '2020-08-31 18:23:44', 'AdminSys', 0);

-- ----------------------------
-- Table structure for bi_data_connection
-- ----------------------------
DROP TABLE IF EXISTS `bi_data_connection`;
CREATE TABLE `bi_data_connection`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `datasource_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '数据源名称',
  `datasource` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '数据源',
  `datasource_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'Default' COMMENT '数据源分组',
  `database_name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '数据库名',
  `jdbc_username` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户名',
  `jdbc_password` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '密码',
  `jdbc_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'jdbc url',
  `jdbc_driver_class` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'jdbc驱动类',
  `zk_adress` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'zookeeper地址',
  `status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '状态：0删除 1启用 2禁用',
  `tenant_id` int(11) NOT NULL DEFAULT 0 COMMENT '租户ID',
  `add_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `add_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '最后修改人',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'jdbc数据源配置' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bi_data_connection
-- ----------------------------
INSERT INTO `bi_data_connection` VALUES (1, 'a', 'mysql', 'Default', NULL, 'a', 'a', 'jdbc:mysql://{host}:{port}/{database}', 'com.mysql.jdbc.Driver', NULL, 1, 0, '2020-09-04 10:02:50', NULL, '2020-09-04 10:02:50', NULL, 0);

-- ----------------------------
-- Table structure for sys_bi_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_bi_menu`;
CREATE TABLE `sys_bi_menu`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '编码',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分类名称',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  `icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图标',
  `href` varchar(1023) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '链接路径',
  `path` varchar(1023) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '链接全路径',
  `level` int(11) NULL DEFAULT NULL COMMENT '当前层级',
  `parent_id` int(11) NULL DEFAULT 0 COMMENT '父ID',
  `remarks` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述备注',
  `component` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '加载组件',
  `add_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `add_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最后修改人',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 60 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统管理_菜单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_bi_menu
-- ----------------------------
INSERT INTO `sys_bi_menu` VALUES (22, 'projectCreate', '设计', 20, '', '', '/project-create', 1, 0, '', '', '2020-08-17 07:16:20', 'AdminSys', '2020-08-17 07:28:45', 'AdminSys', 0);
INSERT INTO `sys_bi_menu` VALUES (23, 'deploy', '发布', 30, '', '', NULL, 1, 0, '', NULL, '2020-08-17 07:17:06', 'AdminSys', '2020-08-17 07:17:06', 'AdminSys', 0);
INSERT INTO `sys_bi_menu` VALUES (24, 'monitor', '智能', 40, '', '', NULL, 1, 0, '', NULL, '2020-08-17 07:17:39', 'AdminSys', '2020-08-17 07:17:39', 'AdminSys', 0);
INSERT INTO `sys_bi_menu` VALUES (25, 'data', '数据', 35, '', '', '', 1, 0, '', '', '2020-08-27 09:25:29', 'AdminSys', '2020-09-01 05:53:37', 'AdminSys', 0);
INSERT INTO `sys_bi_menu` VALUES (26, 'dataConnection', '数据连接', 10, '', '', '/dataConnection', 3, 27, '', '', '2020-08-27 09:27:34', 'AdminSys', '2020-09-04 09:26:01', 'AdminSys', 0);
INSERT INTO `sys_bi_menu` VALUES (27, 'dataImport', '同步', 20, '', '', '', 2, 25, '', '', '2020-08-27 09:28:09', 'AdminSys', '2020-09-04 06:32:00', 'AdminSys', 0);
INSERT INTO `sys_bi_menu` VALUES (28, 'tabledesp', '视图', 30, '', '', '/tabledesp', 2, 25, '', '', '2020-08-27 09:30:57', 'AdminSys', '2020-09-02 19:01:27', 'AdminSys', 0);
INSERT INTO `sys_bi_menu` VALUES (40, 'manage', '管理', 60, '', '', '', 1, 0, '', '', '2020-08-29 20:44:03', 'AdminSys', '2020-08-29 20:44:22', 'AdminSys', 0);
INSERT INTO `sys_bi_menu` VALUES (41, 'menuSet', '系统菜单', 10, '', '', '/menuSet', 3, 49, '', '', '2020-08-29 20:45:27', 'AdminSys', '2020-08-31 17:26:41', 'AdminSys', 0);
INSERT INTO `sys_bi_menu` VALUES (42, 'system', '系统', 20, '', '', NULL, 2, 40, '', NULL, '2020-08-29 20:46:04', 'AdminSys', '2020-08-29 20:46:04', 'AdminSys', 0);
INSERT INTO `sys_bi_menu` VALUES (43, 'tenant', '租户管理', 10, '', '', '', 3, 42, '', '', '2020-08-29 20:46:50', 'AdminSys', '2020-09-04 06:35:09', 'AdminSys', 0);
INSERT INTO `sys_bi_menu` VALUES (44, 'bcorpinfo', '组织机构', 20, '', '', '/bcorpinfo', 3, 42, '', '', '2020-08-29 20:47:24', 'AdminSys', '2020-09-04 06:35:41', 'AdminSys', 0);
INSERT INTO `sys_bi_menu` VALUES (45, 'role', '角色管理', 30, '', '', '/role', 3, 42, '', '', '2020-08-29 20:47:52', 'AdminSys', '2020-09-04 06:35:52', 'AdminSys', 0);
INSERT INTO `sys_bi_menu` VALUES (46, 'user', '人员管理', 40, '', '', '/user', 3, 42, '', '', '2020-08-29 20:48:28', 'AdminSys', '2020-09-04 06:36:06', 'AdminSys', 0);
INSERT INTO `sys_bi_menu` VALUES (47, 'base', '基础', 10, '', '', '', 2, 40, '', '', '2020-08-31 16:39:33', 'AdminSys', '2020-08-31 16:39:53', 'AdminSys', 0);
INSERT INTO `sys_bi_menu` VALUES (48, '50', '消息', 50, '', '', '', 2, 40, '', '', '2020-08-31 16:40:39', 'AdminSys', '2020-08-31 16:40:46', 'AdminSys', 0);
INSERT INTO `sys_bi_menu` VALUES (49, 'maintain', '运维', NULL, '', '', NULL, 2, 40, '', NULL, '2020-08-31 16:41:31', 'AdminSys', '2020-08-31 16:41:31', 'AdminSys', 0);
INSERT INTO `sys_bi_menu` VALUES (50, 'treedic', '树形代码', NULL, '', '', '/treedic', 3, 47, '', '', '2020-08-31 16:44:29', 'AdminSys', '2020-08-31 18:17:20', 'AdminSys', 0);
INSERT INTO `sys_bi_menu` VALUES (51, 'dicmain', '代码字典', NULL, '', '', '/dicmain', 3, 47, '', '', '2020-08-31 16:45:17', 'AdminSys', '2020-08-31 18:35:44', 'AdminSys', 0);
INSERT INTO `sys_bi_menu` VALUES (52, 'storage', '对象存储', 20, '', '', '/storage', 3, 49, '', '', '2020-08-31 16:47:09', 'AdminSys', '2020-08-31 18:47:37', 'AdminSys', 0);
INSERT INTO `sys_bi_menu` VALUES (53, 'opadmindef', '消息定义', NULL, '', '', '/opadmindef', 3, 48, '', '', '2020-08-31 16:54:39', 'AdminSys', '2020-08-31 19:02:22', 'AdminSys', 0);
INSERT INTO `sys_bi_menu` VALUES (54, 'opadmininfo', '消息列表', NULL, '', '', '/opadmininfo', 3, 48, '', '', '2020-08-31 16:54:58', 'AdminSys', '2020-08-31 19:02:39', 'AdminSys', 0);
INSERT INTO `sys_bi_menu` VALUES (55, 'log', '登陆日志', 30, '', '', '/log', 3, 49, '', '', '2020-08-31 16:58:06', 'AdminSys', '2020-08-31 18:55:32', 'AdminSys', 0);
INSERT INTO `sys_bi_menu` VALUES (56, 'sysOperationLog', '操作日志', 40, '', '', '/sysOperationLog', 3, 49, '', '', '2020-08-31 16:59:14', 'AdminSys', '2020-08-31 18:55:12', 'AdminSys', 0);
INSERT INTO `sys_bi_menu` VALUES (57, 'synctask', '同步任务', 20, '', '', '', 3, 27, '', '', '2020-09-04 06:26:11', 'AdminSys', '2020-09-04 06:33:09', 'AdminSys', 0);
INSERT INTO `sys_bi_menu` VALUES (58, 'monitor', '预警', NULL, '', '', '', 2, 24, '', '', '2020-09-04 06:27:52', 'AdminSys', '2020-09-08 18:15:45', 'AdminSys', 0);
INSERT INTO `sys_bi_menu` VALUES (59, 'future', '预测', NULL, '', '', NULL, 2, 24, '', NULL, '2020-09-04 06:30:17', 'AdminSys', '2020-09-04 06:30:17', 'AdminSys', 0);

-- ----------------------------
-- Table structure for sys_bi_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_bi_role`;
CREATE TABLE `sys_bi_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名称',
  `desc` varchar(1023) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色描述',
  `enabled` tinyint(1) NULL DEFAULT 1 COMMENT '是否启用',
  `tenant_id` int(11) NOT NULL DEFAULT 0 COMMENT '租户ID',
  `add_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `add_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最后修改人',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name_UNIQUE`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统管理_角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_bi_role
-- ----------------------------
INSERT INTO `sys_bi_role` VALUES (1, 'a', 'b', 1, 0, '2020-08-31 09:29:22', 'AdminSys', '2020-08-31 09:29:22', 'AdminSys', 0);

-- ----------------------------
-- Table structure for sys_bi_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_bi_user`;
CREATE TABLE `sys_bi_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '管理员名称',
  `password` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '管理员密码',
  `last_login_ip` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '最近一次登录IP地址',
  `last_login_time` datetime(0) NULL DEFAULT NULL COMMENT '最近一次登录时间',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '头像图片',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '邮箱',
  `telphone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '手机',
  `role_ids` varchar(1023) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '[]' COMMENT '角色列表',
  `mobile_role_ids` varchar(1023) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '[]' COMMENT '称动端角色列表',
  `dept_id` int(11) NULL DEFAULT NULL COMMENT '所属部门',
  `dept_id_string` varchar(4000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所属部门字符串',
  `dept_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所属部门名称',
  `stop_flag` tinyint(1) NULL DEFAULT 0 COMMENT '停用标志',
  `data_dept_ids` varchar(4000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '[]' COMMENT '公司数据权限',
  `data_dept_string` varchar(4000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '[]' COMMENT '公司数据权限字符串',
  `tenant_id` int(11) NOT NULL DEFAULT 0 COMMENT '租户ID',
  `add_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `add_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最后修改人',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统管理_管理员表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_bi_user
-- ----------------------------
INSERT INTO `sys_bi_user` VALUES (1, 'a', '$2a$10$yBmBxrHwfqJsXFh5FB9LDO0f2.Ejg22Gj7wi9tcU2HjaSwSAErg0i', '', NULL, '', 'a', 'a', '[1]', '[]', 1, '[\"1\"]', 'a', 0, '[]', '[\"1\"]', 0, '2020-08-31 15:43:17', 'AdminSys', '2020-08-31 16:25:27', 'AdminSys', 0);

-- ----------------------------
-- Table structure for sys_interface_monitor
-- ----------------------------
DROP TABLE IF EXISTS `sys_interface_monitor`;
CREATE TABLE `sys_interface_monitor`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `log_ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '来源IP',
  `log_source_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '数据来源类型代码',
  `log_source` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '数据来源类型',
  `log_direction_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '接口调用类型代码',
  `log_direction` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '接口调用类型',
  `log_type_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '日志类型代码',
  `log_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '日志类型',
  `log_content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '同步内容描述',
  `log_state_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '传输状态描述',
  `log_start_time` datetime(0) NULL DEFAULT NULL COMMENT '同步起始时间',
  `log_end_time` datetime(0) NULL DEFAULT NULL COMMENT '同步截止时间',
  `log_sync_type` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '同步方式',
  `log_insert_sum` int(11) NULL DEFAULT NULL COMMENT '本次插入N条',
  `log_update_sum` int(11) NULL DEFAULT NULL COMMENT '本次更新N条',
  `log_deal_sum` int(11) NULL DEFAULT NULL COMMENT '本次传输总数N条',
  `log_right_sum` int(11) NULL DEFAULT NULL COMMENT '本次传输正确N条',
  `log_error_sum` int(11) NULL DEFAULT NULL COMMENT '本次传输失败N条',
  `error_date` datetime(0) NULL DEFAULT NULL COMMENT '错误发生时间',
  `error_id_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '错误主键描述',
  `error_msg` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '错误消息',
  `error_flag` tinyint(1) NULL DEFAULT 0 COMMENT '错误标识',
  `tenant_id` int(11) NOT NULL DEFAULT 0 COMMENT '租户ID',
  `add_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `add_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最后修改人',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统管理_数据传输日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `admin` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '管理员',
  `ip` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '管理员地址',
  `type` int(11) NULL DEFAULT NULL COMMENT '操作分类',
  `action` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '操作动作',
  `status` tinyint(1) NULL DEFAULT NULL COMMENT '操作状态',
  `result` varchar(127) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '操作结果，或者成功消息，或者失败消息',
  `comment` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '补充信息',
  `tenant_id` int(11) NOT NULL DEFAULT 0 COMMENT '租户ID',
  `add_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `add_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '最后修改人',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 593 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '系统管理_操作日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_log
-- ----------------------------
INSERT INTO `sys_log` VALUES (1, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-14 22:25:38', NULL, '2020-08-14 22:25:38', NULL, 0);
INSERT INTO `sys_log` VALUES (2, '匿名用户', '0:0:0:0:0:0:0:1', 1, '登录', 0, '用户帐号或密码不正确', '', 0, '2020-08-14 22:26:24', NULL, '2020-08-14 22:26:24', NULL, 0);
INSERT INTO `sys_log` VALUES (3, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-14 22:34:08', NULL, '2020-08-14 22:34:08', NULL, 0);
INSERT INTO `sys_log` VALUES (4, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-14 22:34:14', NULL, '2020-08-14 22:34:14', NULL, 0);
INSERT INTO `sys_log` VALUES (5, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-15 06:27:51', NULL, '2020-08-15 06:27:51', NULL, 0);
INSERT INTO `sys_log` VALUES (6, '匿名用户', '0:0:0:0:0:0:0:1', 1, '登录', 0, '用户帐号或密码不正确', '', 0, '2020-08-15 06:30:32', NULL, '2020-08-15 06:30:32', NULL, 0);
INSERT INTO `sys_log` VALUES (7, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-15 06:30:37', NULL, '2020-08-15 06:30:37', NULL, 0);
INSERT INTO `sys_log` VALUES (8, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-15 06:32:30', NULL, '2020-08-15 06:32:30', NULL, 0);
INSERT INTO `sys_log` VALUES (9, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-15 06:33:48', NULL, '2020-08-15 06:33:48', NULL, 0);
INSERT INTO `sys_log` VALUES (10, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-15 06:37:02', NULL, '2020-08-15 06:37:02', NULL, 0);
INSERT INTO `sys_log` VALUES (11, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-15 06:37:30', NULL, '2020-08-15 06:37:30', NULL, 0);
INSERT INTO `sys_log` VALUES (12, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-15 06:40:03', NULL, '2020-08-15 06:40:03', NULL, 0);
INSERT INTO `sys_log` VALUES (13, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-15 06:44:13', NULL, '2020-08-15 06:44:13', NULL, 0);
INSERT INTO `sys_log` VALUES (14, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-15 06:44:20', NULL, '2020-08-15 06:44:20', NULL, 0);
INSERT INTO `sys_log` VALUES (15, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-15 06:44:57', NULL, '2020-08-15 06:44:57', NULL, 0);
INSERT INTO `sys_log` VALUES (16, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-15 07:04:18', NULL, '2020-08-15 07:04:18', NULL, 0);
INSERT INTO `sys_log` VALUES (17, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-15 07:04:23', NULL, '2020-08-15 07:04:23', NULL, 0);
INSERT INTO `sys_log` VALUES (18, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-15 07:04:35', NULL, '2020-08-15 07:04:35', NULL, 0);
INSERT INTO `sys_log` VALUES (19, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-15 07:04:49', NULL, '2020-08-15 07:04:49', NULL, 0);
INSERT INTO `sys_log` VALUES (20, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-15 07:04:50', NULL, '2020-08-15 07:04:50', NULL, 0);
INSERT INTO `sys_log` VALUES (21, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-15 07:05:10', NULL, '2020-08-15 07:05:10', NULL, 0);
INSERT INTO `sys_log` VALUES (22, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-15 07:05:17', NULL, '2020-08-15 07:05:17', NULL, 0);
INSERT INTO `sys_log` VALUES (23, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-15 07:05:20', NULL, '2020-08-15 07:05:20', NULL, 0);
INSERT INTO `sys_log` VALUES (24, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-15 07:06:12', NULL, '2020-08-15 07:06:12', NULL, 0);
INSERT INTO `sys_log` VALUES (25, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-15 07:08:21', NULL, '2020-08-15 07:08:21', NULL, 0);
INSERT INTO `sys_log` VALUES (26, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-15 07:09:31', NULL, '2020-08-15 07:09:31', NULL, 0);
INSERT INTO `sys_log` VALUES (27, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-15 08:27:16', NULL, '2020-08-15 08:27:16', NULL, 0);
INSERT INTO `sys_log` VALUES (28, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-15 08:27:46', NULL, '2020-08-15 08:27:46', NULL, 0);
INSERT INTO `sys_log` VALUES (29, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-15 08:28:03', NULL, '2020-08-15 08:28:03', NULL, 0);
INSERT INTO `sys_log` VALUES (30, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-15 08:28:14', NULL, '2020-08-15 08:28:14', NULL, 0);
INSERT INTO `sys_log` VALUES (31, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-15 08:28:21', NULL, '2020-08-15 08:28:21', NULL, 0);
INSERT INTO `sys_log` VALUES (32, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-15 08:28:57', NULL, '2020-08-15 08:28:57', NULL, 0);
INSERT INTO `sys_log` VALUES (33, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-15 08:29:46', NULL, '2020-08-15 08:29:46', NULL, 0);
INSERT INTO `sys_log` VALUES (34, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-15 09:02:36', NULL, '2020-08-15 09:02:36', NULL, 0);
INSERT INTO `sys_log` VALUES (35, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-15 09:40:45', NULL, '2020-08-15 09:40:45', NULL, 0);
INSERT INTO `sys_log` VALUES (36, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-15 09:43:14', NULL, '2020-08-15 09:43:14', NULL, 0);
INSERT INTO `sys_log` VALUES (37, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-15 09:44:26', NULL, '2020-08-15 09:44:26', NULL, 0);
INSERT INTO `sys_log` VALUES (38, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-15 10:08:13', NULL, '2020-08-15 10:08:13', NULL, 0);
INSERT INTO `sys_log` VALUES (39, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-15 10:08:21', NULL, '2020-08-15 10:08:21', NULL, 0);
INSERT INTO `sys_log` VALUES (40, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-15 10:08:53', NULL, '2020-08-15 10:08:53', NULL, 0);
INSERT INTO `sys_log` VALUES (41, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-15 10:11:23', NULL, '2020-08-15 10:11:23', NULL, 0);
INSERT INTO `sys_log` VALUES (42, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-15 10:11:57', NULL, '2020-08-15 10:11:57', NULL, 0);
INSERT INTO `sys_log` VALUES (43, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-15 10:13:22', NULL, '2020-08-15 10:13:22', NULL, 0);
INSERT INTO `sys_log` VALUES (44, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-15 10:22:34', NULL, '2020-08-15 10:22:34', NULL, 0);
INSERT INTO `sys_log` VALUES (45, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-15 10:24:05', NULL, '2020-08-15 10:24:05', NULL, 0);
INSERT INTO `sys_log` VALUES (46, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-15 10:27:40', NULL, '2020-08-15 10:27:40', NULL, 0);
INSERT INTO `sys_log` VALUES (47, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-15 10:32:12', NULL, '2020-08-15 10:32:12', NULL, 0);
INSERT INTO `sys_log` VALUES (48, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-15 10:32:56', NULL, '2020-08-15 10:32:56', NULL, 0);
INSERT INTO `sys_log` VALUES (49, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-15 10:33:40', NULL, '2020-08-15 10:33:40', NULL, 0);
INSERT INTO `sys_log` VALUES (50, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-15 10:34:48', NULL, '2020-08-15 10:34:48', NULL, 0);
INSERT INTO `sys_log` VALUES (51, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-15 10:35:56', NULL, '2020-08-15 10:35:56', NULL, 0);
INSERT INTO `sys_log` VALUES (52, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-15 10:36:23', NULL, '2020-08-15 10:36:23', NULL, 0);
INSERT INTO `sys_log` VALUES (53, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-15 10:37:04', NULL, '2020-08-15 10:37:04', NULL, 0);
INSERT INTO `sys_log` VALUES (54, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-15 10:37:28', NULL, '2020-08-15 10:37:28', NULL, 0);
INSERT INTO `sys_log` VALUES (55, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-15 10:38:02', NULL, '2020-08-15 10:38:02', NULL, 0);
INSERT INTO `sys_log` VALUES (56, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-15 10:38:22', NULL, '2020-08-15 10:38:22', NULL, 0);
INSERT INTO `sys_log` VALUES (57, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-15 10:39:24', NULL, '2020-08-15 10:39:24', NULL, 0);
INSERT INTO `sys_log` VALUES (58, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 0, '用户帐号或密码不正确', '', 0, '2020-08-15 10:39:38', NULL, '2020-08-15 10:39:38', NULL, 0);
INSERT INTO `sys_log` VALUES (59, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 0, '用户帐号或密码不正确', '', 0, '2020-08-15 10:40:23', NULL, '2020-08-15 10:40:23', NULL, 0);
INSERT INTO `sys_log` VALUES (60, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-15 10:40:29', NULL, '2020-08-15 10:40:29', NULL, 0);
INSERT INTO `sys_log` VALUES (61, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-15 10:42:07', NULL, '2020-08-15 10:42:07', NULL, 0);
INSERT INTO `sys_log` VALUES (62, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-15 10:43:31', NULL, '2020-08-15 10:43:31', NULL, 0);
INSERT INTO `sys_log` VALUES (63, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '退出', 1, '', '', 0, '2020-08-15 10:43:35', NULL, '2020-08-15 10:43:35', NULL, 0);
INSERT INTO `sys_log` VALUES (64, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-15 10:43:48', NULL, '2020-08-15 10:43:48', NULL, 0);
INSERT INTO `sys_log` VALUES (65, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '退出', 1, '', '', 0, '2020-08-15 10:43:51', NULL, '2020-08-15 10:43:51', NULL, 0);
INSERT INTO `sys_log` VALUES (66, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-15 10:43:53', NULL, '2020-08-15 10:43:53', NULL, 0);
INSERT INTO `sys_log` VALUES (67, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '退出', 1, '', '', 0, '2020-08-15 10:43:55', NULL, '2020-08-15 10:43:55', NULL, 0);
INSERT INTO `sys_log` VALUES (68, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-15 10:44:37', NULL, '2020-08-15 10:44:37', NULL, 0);
INSERT INTO `sys_log` VALUES (69, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '退出', 1, '', '', 0, '2020-08-15 10:45:29', NULL, '2020-08-15 10:45:29', NULL, 0);
INSERT INTO `sys_log` VALUES (70, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-15 10:45:31', NULL, '2020-08-15 10:45:31', NULL, 0);
INSERT INTO `sys_log` VALUES (71, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-15 10:49:34', NULL, '2020-08-15 10:49:34', NULL, 0);
INSERT INTO `sys_log` VALUES (72, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '退出', 1, '', '', 0, '2020-08-15 10:49:39', NULL, '2020-08-15 10:49:39', NULL, 0);
INSERT INTO `sys_log` VALUES (73, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-15 10:49:50', NULL, '2020-08-15 10:49:50', NULL, 0);
INSERT INTO `sys_log` VALUES (74, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-15 10:59:18', NULL, '2020-08-15 10:59:18', NULL, 0);
INSERT INTO `sys_log` VALUES (75, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-15 11:05:17', NULL, '2020-08-15 11:05:17', NULL, 0);
INSERT INTO `sys_log` VALUES (76, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '退出', 1, '', '', 0, '2020-08-15 11:05:47', NULL, '2020-08-15 11:05:47', NULL, 0);
INSERT INTO `sys_log` VALUES (77, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-15 11:05:49', NULL, '2020-08-15 11:05:49', NULL, 0);
INSERT INTO `sys_log` VALUES (78, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '退出', 1, '', '', 0, '2020-08-15 11:06:47', NULL, '2020-08-15 11:06:47', NULL, 0);
INSERT INTO `sys_log` VALUES (79, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-15 11:06:49', NULL, '2020-08-15 11:06:49', NULL, 0);
INSERT INTO `sys_log` VALUES (80, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-15 11:08:17', NULL, '2020-08-15 11:08:17', NULL, 0);
INSERT INTO `sys_log` VALUES (81, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '退出', 1, '', '', 0, '2020-08-15 11:08:47', NULL, '2020-08-15 11:08:47', NULL, 0);
INSERT INTO `sys_log` VALUES (82, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-15 11:08:50', NULL, '2020-08-15 11:08:50', NULL, 0);
INSERT INTO `sys_log` VALUES (83, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '退出', 1, '', '', 0, '2020-08-15 11:08:58', NULL, '2020-08-15 11:08:58', NULL, 0);
INSERT INTO `sys_log` VALUES (84, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-15 11:09:00', NULL, '2020-08-15 11:09:00', NULL, 0);
INSERT INTO `sys_log` VALUES (85, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-15 11:10:52', NULL, '2020-08-15 11:10:52', NULL, 0);
INSERT INTO `sys_log` VALUES (86, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-15 11:14:41', NULL, '2020-08-15 11:14:41', NULL, 0);
INSERT INTO `sys_log` VALUES (87, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-15 11:17:24', NULL, '2020-08-15 11:17:24', NULL, 0);
INSERT INTO `sys_log` VALUES (88, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-15 11:27:46', NULL, '2020-08-15 11:27:46', NULL, 0);
INSERT INTO `sys_log` VALUES (89, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-15 11:34:33', NULL, '2020-08-15 11:34:33', NULL, 0);
INSERT INTO `sys_log` VALUES (90, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-15 11:36:01', NULL, '2020-08-15 11:36:01', NULL, 0);
INSERT INTO `sys_log` VALUES (91, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '退出', 1, '', '', 0, '2020-08-15 11:36:34', NULL, '2020-08-15 11:36:34', NULL, 0);
INSERT INTO `sys_log` VALUES (92, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-15 11:36:46', NULL, '2020-08-15 11:36:46', NULL, 0);
INSERT INTO `sys_log` VALUES (93, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '退出', 1, '', '', 0, '2020-08-15 11:37:22', NULL, '2020-08-15 11:37:22', NULL, 0);
INSERT INTO `sys_log` VALUES (94, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-15 11:37:24', NULL, '2020-08-15 11:37:24', NULL, 0);
INSERT INTO `sys_log` VALUES (95, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-15 11:38:28', NULL, '2020-08-15 11:38:28', NULL, 0);
INSERT INTO `sys_log` VALUES (96, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-15 11:39:56', NULL, '2020-08-15 11:39:56', NULL, 0);
INSERT INTO `sys_log` VALUES (97, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-15 11:55:00', NULL, '2020-08-15 11:55:00', NULL, 0);
INSERT INTO `sys_log` VALUES (98, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-15 12:00:40', NULL, '2020-08-15 12:00:40', NULL, 0);
INSERT INTO `sys_log` VALUES (99, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-15 15:56:39', NULL, '2020-08-15 15:56:39', NULL, 0);
INSERT INTO `sys_log` VALUES (100, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-15 16:00:40', NULL, '2020-08-15 16:00:40', NULL, 0);
INSERT INTO `sys_log` VALUES (101, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-15 16:15:15', NULL, '2020-08-15 16:15:15', NULL, 0);
INSERT INTO `sys_log` VALUES (102, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-15 16:30:34', NULL, '2020-08-15 16:30:34', NULL, 0);
INSERT INTO `sys_log` VALUES (103, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-15 16:45:53', NULL, '2020-08-15 16:45:53', NULL, 0);
INSERT INTO `sys_log` VALUES (104, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-15 16:47:24', NULL, '2020-08-15 16:47:24', NULL, 0);
INSERT INTO `sys_log` VALUES (105, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-15 17:37:25', NULL, '2020-08-15 17:37:25', NULL, 0);
INSERT INTO `sys_log` VALUES (106, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-16 06:28:37', NULL, '2020-08-16 06:28:37', NULL, 0);
INSERT INTO `sys_log` VALUES (107, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-16 06:32:07', NULL, '2020-08-16 06:32:07', NULL, 0);
INSERT INTO `sys_log` VALUES (108, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-16 06:34:29', NULL, '2020-08-16 06:34:29', NULL, 0);
INSERT INTO `sys_log` VALUES (109, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-16 06:36:41', NULL, '2020-08-16 06:36:41', NULL, 0);
INSERT INTO `sys_log` VALUES (110, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-16 06:38:48', NULL, '2020-08-16 06:38:48', NULL, 0);
INSERT INTO `sys_log` VALUES (111, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-16 06:44:19', NULL, '2020-08-16 06:44:19', NULL, 0);
INSERT INTO `sys_log` VALUES (112, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-16 06:57:13', NULL, '2020-08-16 06:57:13', NULL, 0);
INSERT INTO `sys_log` VALUES (113, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-16 06:59:44', NULL, '2020-08-16 06:59:44', NULL, 0);
INSERT INTO `sys_log` VALUES (114, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-16 07:02:04', NULL, '2020-08-16 07:02:04', NULL, 0);
INSERT INTO `sys_log` VALUES (115, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-16 07:03:30', NULL, '2020-08-16 07:03:30', NULL, 0);
INSERT INTO `sys_log` VALUES (116, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-16 07:04:35', NULL, '2020-08-16 07:04:35', NULL, 0);
INSERT INTO `sys_log` VALUES (117, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-16 07:06:16', NULL, '2020-08-16 07:06:16', NULL, 0);
INSERT INTO `sys_log` VALUES (118, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-16 07:10:41', NULL, '2020-08-16 07:10:41', NULL, 0);
INSERT INTO `sys_log` VALUES (119, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-16 07:11:15', NULL, '2020-08-16 07:11:15', NULL, 0);
INSERT INTO `sys_log` VALUES (120, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-16 07:13:30', NULL, '2020-08-16 07:13:30', NULL, 0);
INSERT INTO `sys_log` VALUES (121, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-16 07:17:31', NULL, '2020-08-16 07:17:31', NULL, 0);
INSERT INTO `sys_log` VALUES (122, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-16 07:31:00', NULL, '2020-08-16 07:31:00', NULL, 0);
INSERT INTO `sys_log` VALUES (123, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-16 07:36:40', NULL, '2020-08-16 07:36:40', NULL, 0);
INSERT INTO `sys_log` VALUES (124, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-16 07:42:48', NULL, '2020-08-16 07:42:48', NULL, 0);
INSERT INTO `sys_log` VALUES (125, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-16 07:45:13', NULL, '2020-08-16 07:45:13', NULL, 0);
INSERT INTO `sys_log` VALUES (126, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-16 07:49:03', NULL, '2020-08-16 07:49:03', NULL, 0);
INSERT INTO `sys_log` VALUES (127, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '退出', 1, '', '', 0, '2020-08-16 07:49:47', NULL, '2020-08-16 07:49:47', NULL, 0);
INSERT INTO `sys_log` VALUES (128, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-16 07:49:49', NULL, '2020-08-16 07:49:49', NULL, 0);
INSERT INTO `sys_log` VALUES (129, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-16 08:13:50', NULL, '2020-08-16 08:13:50', NULL, 0);
INSERT INTO `sys_log` VALUES (130, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-16 08:20:13', NULL, '2020-08-16 08:20:13', NULL, 0);
INSERT INTO `sys_log` VALUES (131, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-16 08:21:32', NULL, '2020-08-16 08:21:32', NULL, 0);
INSERT INTO `sys_log` VALUES (132, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-16 08:27:36', NULL, '2020-08-16 08:27:36', NULL, 0);
INSERT INTO `sys_log` VALUES (133, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-16 08:30:03', NULL, '2020-08-16 08:30:03', NULL, 0);
INSERT INTO `sys_log` VALUES (134, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-16 08:31:28', NULL, '2020-08-16 08:31:28', NULL, 0);
INSERT INTO `sys_log` VALUES (135, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-16 08:41:14', NULL, '2020-08-16 08:41:14', NULL, 0);
INSERT INTO `sys_log` VALUES (136, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-16 08:47:24', NULL, '2020-08-16 08:47:24', NULL, 0);
INSERT INTO `sys_log` VALUES (137, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-16 08:48:42', NULL, '2020-08-16 08:48:42', NULL, 0);
INSERT INTO `sys_log` VALUES (138, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-16 08:51:41', NULL, '2020-08-16 08:51:41', NULL, 0);
INSERT INTO `sys_log` VALUES (139, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-16 08:53:30', NULL, '2020-08-16 08:53:30', NULL, 0);
INSERT INTO `sys_log` VALUES (140, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-16 08:55:10', NULL, '2020-08-16 08:55:10', NULL, 0);
INSERT INTO `sys_log` VALUES (141, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-16 08:56:31', NULL, '2020-08-16 08:56:31', NULL, 0);
INSERT INTO `sys_log` VALUES (142, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-16 09:00:25', NULL, '2020-08-16 09:00:25', NULL, 0);
INSERT INTO `sys_log` VALUES (143, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-16 09:06:50', NULL, '2020-08-16 09:06:50', NULL, 0);
INSERT INTO `sys_log` VALUES (144, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-16 09:08:46', NULL, '2020-08-16 09:08:46', NULL, 0);
INSERT INTO `sys_log` VALUES (145, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-16 09:09:19', NULL, '2020-08-16 09:09:19', NULL, 0);
INSERT INTO `sys_log` VALUES (146, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-16 09:14:27', NULL, '2020-08-16 09:14:27', NULL, 0);
INSERT INTO `sys_log` VALUES (147, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-16 09:14:51', NULL, '2020-08-16 09:14:51', NULL, 0);
INSERT INTO `sys_log` VALUES (148, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-16 09:15:59', NULL, '2020-08-16 09:15:59', NULL, 0);
INSERT INTO `sys_log` VALUES (149, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-16 10:42:54', NULL, '2020-08-16 10:42:54', NULL, 0);
INSERT INTO `sys_log` VALUES (150, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '退出', 1, '', '', 0, '2020-08-16 10:43:28', NULL, '2020-08-16 10:43:28', NULL, 0);
INSERT INTO `sys_log` VALUES (151, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-16 10:43:29', NULL, '2020-08-16 10:43:29', NULL, 0);
INSERT INTO `sys_log` VALUES (152, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-16 10:51:37', NULL, '2020-08-16 10:51:37', NULL, 0);
INSERT INTO `sys_log` VALUES (153, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-16 10:53:28', NULL, '2020-08-16 10:53:28', NULL, 0);
INSERT INTO `sys_log` VALUES (154, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-17 06:08:24', NULL, '2020-08-17 06:08:24', NULL, 0);
INSERT INTO `sys_log` VALUES (155, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-17 06:13:34', NULL, '2020-08-17 06:13:34', NULL, 0);
INSERT INTO `sys_log` VALUES (156, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '退出', 1, '', '', 0, '2020-08-17 06:13:43', NULL, '2020-08-17 06:13:43', NULL, 0);
INSERT INTO `sys_log` VALUES (157, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-17 06:13:45', NULL, '2020-08-17 06:13:45', NULL, 0);
INSERT INTO `sys_log` VALUES (158, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-17 06:16:09', NULL, '2020-08-17 06:16:09', NULL, 0);
INSERT INTO `sys_log` VALUES (159, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '退出', 1, '', '', 0, '2020-08-17 06:16:30', NULL, '2020-08-17 06:16:30', NULL, 0);
INSERT INTO `sys_log` VALUES (160, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-17 06:16:31', NULL, '2020-08-17 06:16:31', NULL, 0);
INSERT INTO `sys_log` VALUES (161, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-17 06:18:48', NULL, '2020-08-17 06:18:48', NULL, 0);
INSERT INTO `sys_log` VALUES (162, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-17 06:21:10', NULL, '2020-08-17 06:21:10', NULL, 0);
INSERT INTO `sys_log` VALUES (163, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '退出', 1, '', '', 0, '2020-08-17 06:21:24', NULL, '2020-08-17 06:21:24', NULL, 0);
INSERT INTO `sys_log` VALUES (164, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-17 06:21:26', NULL, '2020-08-17 06:21:26', NULL, 0);
INSERT INTO `sys_log` VALUES (165, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-17 06:30:18', NULL, '2020-08-17 06:30:18', NULL, 0);
INSERT INTO `sys_log` VALUES (166, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '退出', 1, '', '', 0, '2020-08-17 06:31:01', NULL, '2020-08-17 06:31:01', NULL, 0);
INSERT INTO `sys_log` VALUES (167, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-17 06:31:03', NULL, '2020-08-17 06:31:03', NULL, 0);
INSERT INTO `sys_log` VALUES (168, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-17 06:37:43', NULL, '2020-08-17 06:37:43', NULL, 0);
INSERT INTO `sys_log` VALUES (169, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '退出', 1, '', '', 0, '2020-08-17 06:39:16', NULL, '2020-08-17 06:39:16', NULL, 0);
INSERT INTO `sys_log` VALUES (170, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-17 06:39:17', NULL, '2020-08-17 06:39:17', NULL, 0);
INSERT INTO `sys_log` VALUES (171, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-17 06:47:01', NULL, '2020-08-17 06:47:01', NULL, 0);
INSERT INTO `sys_log` VALUES (172, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-17 06:51:12', NULL, '2020-08-17 06:51:12', NULL, 0);
INSERT INTO `sys_log` VALUES (173, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-17 06:53:54', NULL, '2020-08-17 06:53:54', NULL, 0);
INSERT INTO `sys_log` VALUES (174, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-17 06:55:09', NULL, '2020-08-17 06:55:09', NULL, 0);
INSERT INTO `sys_log` VALUES (175, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-17 06:58:29', NULL, '2020-08-17 06:58:29', NULL, 0);
INSERT INTO `sys_log` VALUES (176, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-17 07:11:14', NULL, '2020-08-17 07:11:14', NULL, 0);
INSERT INTO `sys_log` VALUES (177, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '退出', 1, '', '', 0, '2020-08-17 07:12:23', NULL, '2020-08-17 07:12:23', NULL, 0);
INSERT INTO `sys_log` VALUES (178, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-17 07:12:24', NULL, '2020-08-17 07:12:24', NULL, 0);
INSERT INTO `sys_log` VALUES (179, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-17 07:15:27', NULL, '2020-08-17 07:15:27', NULL, 0);
INSERT INTO `sys_log` VALUES (180, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '退出', 1, '', '', 0, '2020-08-17 07:17:49', NULL, '2020-08-17 07:17:49', NULL, 0);
INSERT INTO `sys_log` VALUES (181, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-17 07:17:50', NULL, '2020-08-17 07:17:50', NULL, 0);
INSERT INTO `sys_log` VALUES (182, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '退出', 1, '', '', 0, '2020-08-17 07:20:03', NULL, '2020-08-17 07:20:03', NULL, 0);
INSERT INTO `sys_log` VALUES (183, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-17 07:20:04', NULL, '2020-08-17 07:20:04', NULL, 0);
INSERT INTO `sys_log` VALUES (184, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-17 07:27:14', NULL, '2020-08-17 07:27:14', NULL, 0);
INSERT INTO `sys_log` VALUES (185, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '退出', 1, '', '', 0, '2020-08-17 07:27:27', NULL, '2020-08-17 07:27:27', NULL, 0);
INSERT INTO `sys_log` VALUES (186, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-17 07:27:29', NULL, '2020-08-17 07:27:29', NULL, 0);
INSERT INTO `sys_log` VALUES (187, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '退出', 1, '', '', 0, '2020-08-17 07:29:04', NULL, '2020-08-17 07:29:04', NULL, 0);
INSERT INTO `sys_log` VALUES (188, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-17 07:29:05', NULL, '2020-08-17 07:29:05', NULL, 0);
INSERT INTO `sys_log` VALUES (189, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-17 11:21:26', NULL, '2020-08-17 11:21:26', NULL, 0);
INSERT INTO `sys_log` VALUES (190, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-18 06:13:47', NULL, '2020-08-18 06:13:47', NULL, 0);
INSERT INTO `sys_log` VALUES (191, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-18 06:14:02', NULL, '2020-08-18 06:14:02', NULL, 0);
INSERT INTO `sys_log` VALUES (192, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-18 06:29:15', NULL, '2020-08-18 06:29:15', NULL, 0);
INSERT INTO `sys_log` VALUES (193, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-18 06:32:10', NULL, '2020-08-18 06:32:10', NULL, 0);
INSERT INTO `sys_log` VALUES (194, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-18 06:32:14', NULL, '2020-08-18 06:32:14', NULL, 0);
INSERT INTO `sys_log` VALUES (195, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-18 06:32:19', NULL, '2020-08-18 06:32:19', NULL, 0);
INSERT INTO `sys_log` VALUES (196, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-18 06:35:24', NULL, '2020-08-18 06:35:24', NULL, 0);
INSERT INTO `sys_log` VALUES (197, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-18 06:35:33', NULL, '2020-08-18 06:35:33', NULL, 0);
INSERT INTO `sys_log` VALUES (198, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-18 06:35:53', NULL, '2020-08-18 06:35:53', NULL, 0);
INSERT INTO `sys_log` VALUES (199, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-18 06:50:42', NULL, '2020-08-18 06:50:42', NULL, 0);
INSERT INTO `sys_log` VALUES (200, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-18 06:51:13', NULL, '2020-08-18 06:51:13', NULL, 0);
INSERT INTO `sys_log` VALUES (201, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-18 07:03:05', NULL, '2020-08-18 07:03:05', NULL, 0);
INSERT INTO `sys_log` VALUES (202, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-18 07:23:12', NULL, '2020-08-18 07:23:12', NULL, 0);
INSERT INTO `sys_log` VALUES (203, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-18 07:29:17', NULL, '2020-08-18 07:29:17', NULL, 0);
INSERT INTO `sys_log` VALUES (204, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-18 07:33:36', NULL, '2020-08-18 07:33:36', NULL, 0);
INSERT INTO `sys_log` VALUES (205, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-18 07:39:27', NULL, '2020-08-18 07:39:27', NULL, 0);
INSERT INTO `sys_log` VALUES (206, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-18 07:44:50', NULL, '2020-08-18 07:44:50', NULL, 0);
INSERT INTO `sys_log` VALUES (207, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-18 07:45:08', NULL, '2020-08-18 07:45:08', NULL, 0);
INSERT INTO `sys_log` VALUES (208, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-18 07:46:35', NULL, '2020-08-18 07:46:35', NULL, 0);
INSERT INTO `sys_log` VALUES (209, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-18 07:49:43', NULL, '2020-08-18 07:49:43', NULL, 0);
INSERT INTO `sys_log` VALUES (210, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-18 07:50:02', NULL, '2020-08-18 07:50:02', NULL, 0);
INSERT INTO `sys_log` VALUES (211, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-18 07:55:03', NULL, '2020-08-18 07:55:03', NULL, 0);
INSERT INTO `sys_log` VALUES (212, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-18 07:57:41', NULL, '2020-08-18 07:57:41', NULL, 0);
INSERT INTO `sys_log` VALUES (213, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-19 08:39:02', NULL, '2020-08-19 08:39:02', NULL, 0);
INSERT INTO `sys_log` VALUES (214, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-19 08:43:30', NULL, '2020-08-19 08:43:30', NULL, 0);
INSERT INTO `sys_log` VALUES (215, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-19 08:47:33', NULL, '2020-08-19 08:47:33', NULL, 0);
INSERT INTO `sys_log` VALUES (216, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '退出', 1, '', '', 0, '2020-08-19 08:48:08', NULL, '2020-08-19 08:48:08', NULL, 0);
INSERT INTO `sys_log` VALUES (217, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-19 08:48:10', NULL, '2020-08-19 08:48:10', NULL, 0);
INSERT INTO `sys_log` VALUES (218, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-19 08:51:25', NULL, '2020-08-19 08:51:25', NULL, 0);
INSERT INTO `sys_log` VALUES (219, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-19 09:35:49', NULL, '2020-08-19 09:35:49', NULL, 0);
INSERT INTO `sys_log` VALUES (220, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-19 09:39:34', NULL, '2020-08-19 09:39:34', NULL, 0);
INSERT INTO `sys_log` VALUES (221, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-19 10:16:29', NULL, '2020-08-19 10:16:29', NULL, 0);
INSERT INTO `sys_log` VALUES (222, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-19 13:09:35', NULL, '2020-08-19 13:09:35', NULL, 0);
INSERT INTO `sys_log` VALUES (223, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-19 14:05:27', NULL, '2020-08-19 14:05:27', NULL, 0);
INSERT INTO `sys_log` VALUES (224, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-19 14:07:32', NULL, '2020-08-19 14:07:32', NULL, 0);
INSERT INTO `sys_log` VALUES (225, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-19 14:09:38', NULL, '2020-08-19 14:09:38', NULL, 0);
INSERT INTO `sys_log` VALUES (226, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-19 14:12:02', NULL, '2020-08-19 14:12:02', NULL, 0);
INSERT INTO `sys_log` VALUES (227, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-19 14:33:04', NULL, '2020-08-19 14:33:04', NULL, 0);
INSERT INTO `sys_log` VALUES (228, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-19 14:36:52', NULL, '2020-08-19 14:36:52', NULL, 0);
INSERT INTO `sys_log` VALUES (229, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-19 14:39:42', NULL, '2020-08-19 14:39:42', NULL, 0);
INSERT INTO `sys_log` VALUES (230, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-19 14:42:16', NULL, '2020-08-19 14:42:16', NULL, 0);
INSERT INTO `sys_log` VALUES (231, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-19 14:43:44', NULL, '2020-08-19 14:43:44', NULL, 0);
INSERT INTO `sys_log` VALUES (232, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-19 14:46:28', NULL, '2020-08-19 14:46:28', NULL, 0);
INSERT INTO `sys_log` VALUES (233, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-19 14:47:39', NULL, '2020-08-19 14:47:39', NULL, 0);
INSERT INTO `sys_log` VALUES (234, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-19 14:50:56', NULL, '2020-08-19 14:50:56', NULL, 0);
INSERT INTO `sys_log` VALUES (235, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-19 14:53:25', NULL, '2020-08-19 14:53:25', NULL, 0);
INSERT INTO `sys_log` VALUES (236, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-19 14:55:45', NULL, '2020-08-19 14:55:45', NULL, 0);
INSERT INTO `sys_log` VALUES (237, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-19 15:02:27', NULL, '2020-08-19 15:02:27', NULL, 0);
INSERT INTO `sys_log` VALUES (238, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-19 15:04:20', NULL, '2020-08-19 15:04:20', NULL, 0);
INSERT INTO `sys_log` VALUES (239, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-19 15:06:24', NULL, '2020-08-19 15:06:24', NULL, 0);
INSERT INTO `sys_log` VALUES (240, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-19 15:11:51', NULL, '2020-08-19 15:11:51', NULL, 0);
INSERT INTO `sys_log` VALUES (241, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-19 15:13:28', NULL, '2020-08-19 15:13:28', NULL, 0);
INSERT INTO `sys_log` VALUES (242, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-19 15:45:42', NULL, '2020-08-19 15:45:42', NULL, 0);
INSERT INTO `sys_log` VALUES (243, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-19 15:49:38', NULL, '2020-08-19 15:49:38', NULL, 0);
INSERT INTO `sys_log` VALUES (244, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-19 15:53:04', NULL, '2020-08-19 15:53:04', NULL, 0);
INSERT INTO `sys_log` VALUES (245, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-19 15:55:36', NULL, '2020-08-19 15:55:36', NULL, 0);
INSERT INTO `sys_log` VALUES (246, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-19 17:50:25', NULL, '2020-08-19 17:50:25', NULL, 0);
INSERT INTO `sys_log` VALUES (247, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-19 17:53:59', NULL, '2020-08-19 17:53:59', NULL, 0);
INSERT INTO `sys_log` VALUES (248, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-19 17:55:40', NULL, '2020-08-19 17:55:40', NULL, 0);
INSERT INTO `sys_log` VALUES (249, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-21 08:09:27', NULL, '2020-08-21 08:09:27', NULL, 0);
INSERT INTO `sys_log` VALUES (250, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-22 12:39:50', NULL, '2020-08-22 12:39:50', NULL, 0);
INSERT INTO `sys_log` VALUES (251, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-23 10:56:43', NULL, '2020-08-23 10:56:43', NULL, 0);
INSERT INTO `sys_log` VALUES (252, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-27 08:59:33', NULL, '2020-08-27 08:59:33', NULL, 0);
INSERT INTO `sys_log` VALUES (253, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-27 09:21:13', NULL, '2020-08-27 09:21:13', NULL, 0);
INSERT INTO `sys_log` VALUES (254, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-27 09:24:08', NULL, '2020-08-27 09:24:08', NULL, 0);
INSERT INTO `sys_log` VALUES (255, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-27 09:30:26', NULL, '2020-08-27 09:30:26', NULL, 0);
INSERT INTO `sys_log` VALUES (256, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-27 09:35:11', NULL, '2020-08-27 09:35:11', NULL, 0);
INSERT INTO `sys_log` VALUES (257, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-27 11:59:39', NULL, '2020-08-27 11:59:39', NULL, 0);
INSERT INTO `sys_log` VALUES (258, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-27 12:15:33', NULL, '2020-08-27 12:15:33', NULL, 0);
INSERT INTO `sys_log` VALUES (259, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-27 12:20:36', NULL, '2020-08-27 12:20:36', NULL, 0);
INSERT INTO `sys_log` VALUES (260, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-27 12:22:38', NULL, '2020-08-27 12:22:38', NULL, 0);
INSERT INTO `sys_log` VALUES (261, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 06:40:16', NULL, '2020-08-29 06:40:16', NULL, 0);
INSERT INTO `sys_log` VALUES (262, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 06:49:08', NULL, '2020-08-29 06:49:08', NULL, 0);
INSERT INTO `sys_log` VALUES (263, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 06:50:41', NULL, '2020-08-29 06:50:41', NULL, 0);
INSERT INTO `sys_log` VALUES (264, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '退出', 1, '', '', 0, '2020-08-29 06:52:07', NULL, '2020-08-29 06:52:07', NULL, 0);
INSERT INTO `sys_log` VALUES (265, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 06:52:09', NULL, '2020-08-29 06:52:09', NULL, 0);
INSERT INTO `sys_log` VALUES (266, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 06:55:18', NULL, '2020-08-29 06:55:18', NULL, 0);
INSERT INTO `sys_log` VALUES (267, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 09:52:51', NULL, '2020-08-29 09:52:51', NULL, 0);
INSERT INTO `sys_log` VALUES (268, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 10:01:58', NULL, '2020-08-29 10:01:58', NULL, 0);
INSERT INTO `sys_log` VALUES (269, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 10:21:51', NULL, '2020-08-29 10:21:51', NULL, 0);
INSERT INTO `sys_log` VALUES (270, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 10:22:59', NULL, '2020-08-29 10:22:59', NULL, 0);
INSERT INTO `sys_log` VALUES (271, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 10:24:40', NULL, '2020-08-29 10:24:40', NULL, 0);
INSERT INTO `sys_log` VALUES (272, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '退出', 1, '', '', 0, '2020-08-29 10:24:52', NULL, '2020-08-29 10:24:52', NULL, 0);
INSERT INTO `sys_log` VALUES (273, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 10:24:54', NULL, '2020-08-29 10:24:54', NULL, 0);
INSERT INTO `sys_log` VALUES (274, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 10:46:00', NULL, '2020-08-29 10:46:00', NULL, 0);
INSERT INTO `sys_log` VALUES (275, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 10:48:09', NULL, '2020-08-29 10:48:09', NULL, 0);
INSERT INTO `sys_log` VALUES (276, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 10:53:53', NULL, '2020-08-29 10:53:53', NULL, 0);
INSERT INTO `sys_log` VALUES (277, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 10:57:38', NULL, '2020-08-29 10:57:38', NULL, 0);
INSERT INTO `sys_log` VALUES (278, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 10:58:48', NULL, '2020-08-29 10:58:48', NULL, 0);
INSERT INTO `sys_log` VALUES (279, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 11:00:16', NULL, '2020-08-29 11:00:16', NULL, 0);
INSERT INTO `sys_log` VALUES (280, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 11:02:38', NULL, '2020-08-29 11:02:38', NULL, 0);
INSERT INTO `sys_log` VALUES (281, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 11:03:42', NULL, '2020-08-29 11:03:42', NULL, 0);
INSERT INTO `sys_log` VALUES (282, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 11:09:10', NULL, '2020-08-29 11:09:10', NULL, 0);
INSERT INTO `sys_log` VALUES (283, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 11:11:58', NULL, '2020-08-29 11:11:58', NULL, 0);
INSERT INTO `sys_log` VALUES (284, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 11:14:06', NULL, '2020-08-29 11:14:06', NULL, 0);
INSERT INTO `sys_log` VALUES (285, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 11:15:11', NULL, '2020-08-29 11:15:11', NULL, 0);
INSERT INTO `sys_log` VALUES (286, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 11:18:50', NULL, '2020-08-29 11:18:50', NULL, 0);
INSERT INTO `sys_log` VALUES (287, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 11:23:43', NULL, '2020-08-29 11:23:43', NULL, 0);
INSERT INTO `sys_log` VALUES (288, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 11:26:53', NULL, '2020-08-29 11:26:53', NULL, 0);
INSERT INTO `sys_log` VALUES (289, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 12:08:49', NULL, '2020-08-29 12:08:49', NULL, 0);
INSERT INTO `sys_log` VALUES (290, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 12:13:06', NULL, '2020-08-29 12:13:06', NULL, 0);
INSERT INTO `sys_log` VALUES (291, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 16:04:50', NULL, '2020-08-29 16:04:50', NULL, 0);
INSERT INTO `sys_log` VALUES (292, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 16:06:49', NULL, '2020-08-29 16:06:49', NULL, 0);
INSERT INTO `sys_log` VALUES (293, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 16:10:08', NULL, '2020-08-29 16:10:08', NULL, 0);
INSERT INTO `sys_log` VALUES (294, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 16:27:29', NULL, '2020-08-29 16:27:29', NULL, 0);
INSERT INTO `sys_log` VALUES (295, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 16:30:35', NULL, '2020-08-29 16:30:35', NULL, 0);
INSERT INTO `sys_log` VALUES (296, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 16:32:07', NULL, '2020-08-29 16:32:07', NULL, 0);
INSERT INTO `sys_log` VALUES (297, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 16:37:19', NULL, '2020-08-29 16:37:19', NULL, 0);
INSERT INTO `sys_log` VALUES (298, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 16:40:29', NULL, '2020-08-29 16:40:29', NULL, 0);
INSERT INTO `sys_log` VALUES (299, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 16:42:21', NULL, '2020-08-29 16:42:21', NULL, 0);
INSERT INTO `sys_log` VALUES (300, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 16:45:47', NULL, '2020-08-29 16:45:47', NULL, 0);
INSERT INTO `sys_log` VALUES (301, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 16:47:50', NULL, '2020-08-29 16:47:50', NULL, 0);
INSERT INTO `sys_log` VALUES (302, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 16:52:48', NULL, '2020-08-29 16:52:48', NULL, 0);
INSERT INTO `sys_log` VALUES (303, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 16:57:15', NULL, '2020-08-29 16:57:15', NULL, 0);
INSERT INTO `sys_log` VALUES (304, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 16:58:25', NULL, '2020-08-29 16:58:25', NULL, 0);
INSERT INTO `sys_log` VALUES (305, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 17:01:40', NULL, '2020-08-29 17:01:40', NULL, 0);
INSERT INTO `sys_log` VALUES (306, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 17:03:19', NULL, '2020-08-29 17:03:19', NULL, 0);
INSERT INTO `sys_log` VALUES (307, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 17:08:57', NULL, '2020-08-29 17:08:57', NULL, 0);
INSERT INTO `sys_log` VALUES (308, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 17:10:14', NULL, '2020-08-29 17:10:14', NULL, 0);
INSERT INTO `sys_log` VALUES (309, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 17:13:04', NULL, '2020-08-29 17:13:04', NULL, 0);
INSERT INTO `sys_log` VALUES (310, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 19:49:50', NULL, '2020-08-29 19:49:50', NULL, 0);
INSERT INTO `sys_log` VALUES (311, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 19:59:11', NULL, '2020-08-29 19:59:11', NULL, 0);
INSERT INTO `sys_log` VALUES (312, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 20:03:44', NULL, '2020-08-29 20:03:44', NULL, 0);
INSERT INTO `sys_log` VALUES (313, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 20:05:42', NULL, '2020-08-29 20:05:42', NULL, 0);
INSERT INTO `sys_log` VALUES (314, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 20:10:02', NULL, '2020-08-29 20:10:02', NULL, 0);
INSERT INTO `sys_log` VALUES (315, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 20:15:56', NULL, '2020-08-29 20:15:56', NULL, 0);
INSERT INTO `sys_log` VALUES (316, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 20:22:55', NULL, '2020-08-29 20:22:55', NULL, 0);
INSERT INTO `sys_log` VALUES (317, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 20:24:15', NULL, '2020-08-29 20:24:15', NULL, 0);
INSERT INTO `sys_log` VALUES (318, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 20:32:25', NULL, '2020-08-29 20:32:25', NULL, 0);
INSERT INTO `sys_log` VALUES (319, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 20:34:58', NULL, '2020-08-29 20:34:58', NULL, 0);
INSERT INTO `sys_log` VALUES (320, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 20:36:35', NULL, '2020-08-29 20:36:35', NULL, 0);
INSERT INTO `sys_log` VALUES (321, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 20:42:57', NULL, '2020-08-29 20:42:57', NULL, 0);
INSERT INTO `sys_log` VALUES (322, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '退出', 1, '', '', 0, '2020-08-29 20:49:07', NULL, '2020-08-29 20:49:07', NULL, 0);
INSERT INTO `sys_log` VALUES (323, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 20:49:09', NULL, '2020-08-29 20:49:09', NULL, 0);
INSERT INTO `sys_log` VALUES (324, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 20:51:27', NULL, '2020-08-29 20:51:27', NULL, 0);
INSERT INTO `sys_log` VALUES (325, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 20:57:49', NULL, '2020-08-29 20:57:49', NULL, 0);
INSERT INTO `sys_log` VALUES (326, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '退出', 1, '', '', 0, '2020-08-29 20:58:36', NULL, '2020-08-29 20:58:36', NULL, 0);
INSERT INTO `sys_log` VALUES (327, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 20:58:37', NULL, '2020-08-29 20:58:37', NULL, 0);
INSERT INTO `sys_log` VALUES (328, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 21:03:17', NULL, '2020-08-29 21:03:17', NULL, 0);
INSERT INTO `sys_log` VALUES (329, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 21:04:29', NULL, '2020-08-29 21:04:29', NULL, 0);
INSERT INTO `sys_log` VALUES (330, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 21:10:03', NULL, '2020-08-29 21:10:03', NULL, 0);
INSERT INTO `sys_log` VALUES (331, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 21:14:10', NULL, '2020-08-29 21:14:10', NULL, 0);
INSERT INTO `sys_log` VALUES (332, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 21:18:01', NULL, '2020-08-29 21:18:01', NULL, 0);
INSERT INTO `sys_log` VALUES (333, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 21:22:38', NULL, '2020-08-29 21:22:38', NULL, 0);
INSERT INTO `sys_log` VALUES (334, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 21:24:15', NULL, '2020-08-29 21:24:15', NULL, 0);
INSERT INTO `sys_log` VALUES (335, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 21:34:04', NULL, '2020-08-29 21:34:04', NULL, 0);
INSERT INTO `sys_log` VALUES (336, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 21:35:27', NULL, '2020-08-29 21:35:27', NULL, 0);
INSERT INTO `sys_log` VALUES (337, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 21:39:39', NULL, '2020-08-29 21:39:39', NULL, 0);
INSERT INTO `sys_log` VALUES (338, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 21:42:44', NULL, '2020-08-29 21:42:44', NULL, 0);
INSERT INTO `sys_log` VALUES (339, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 21:46:30', NULL, '2020-08-29 21:46:30', NULL, 0);
INSERT INTO `sys_log` VALUES (340, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 21:48:07', NULL, '2020-08-29 21:48:07', NULL, 0);
INSERT INTO `sys_log` VALUES (341, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 21:49:27', NULL, '2020-08-29 21:49:27', NULL, 0);
INSERT INTO `sys_log` VALUES (342, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 21:58:49', NULL, '2020-08-29 21:58:49', NULL, 0);
INSERT INTO `sys_log` VALUES (343, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 22:01:15', NULL, '2020-08-29 22:01:15', NULL, 0);
INSERT INTO `sys_log` VALUES (344, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 22:04:22', NULL, '2020-08-29 22:04:22', NULL, 0);
INSERT INTO `sys_log` VALUES (345, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 22:09:00', NULL, '2020-08-29 22:09:00', NULL, 0);
INSERT INTO `sys_log` VALUES (346, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 22:15:50', NULL, '2020-08-29 22:15:50', NULL, 0);
INSERT INTO `sys_log` VALUES (347, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 22:18:33', NULL, '2020-08-29 22:18:33', NULL, 0);
INSERT INTO `sys_log` VALUES (348, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 22:19:44', NULL, '2020-08-29 22:19:44', NULL, 0);
INSERT INTO `sys_log` VALUES (349, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 22:21:00', NULL, '2020-08-29 22:21:00', NULL, 0);
INSERT INTO `sys_log` VALUES (350, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 22:25:31', NULL, '2020-08-29 22:25:31', NULL, 0);
INSERT INTO `sys_log` VALUES (351, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 22:36:04', NULL, '2020-08-29 22:36:04', NULL, 0);
INSERT INTO `sys_log` VALUES (352, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 22:39:25', NULL, '2020-08-29 22:39:25', NULL, 0);
INSERT INTO `sys_log` VALUES (353, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 22:54:35', NULL, '2020-08-29 22:54:35', NULL, 0);
INSERT INTO `sys_log` VALUES (354, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 22:56:37', NULL, '2020-08-29 22:56:37', NULL, 0);
INSERT INTO `sys_log` VALUES (355, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-29 23:00:45', NULL, '2020-08-29 23:00:45', NULL, 0);
INSERT INTO `sys_log` VALUES (356, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-30 06:45:25', NULL, '2020-08-30 06:45:25', NULL, 0);
INSERT INTO `sys_log` VALUES (357, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-30 06:53:00', NULL, '2020-08-30 06:53:00', NULL, 0);
INSERT INTO `sys_log` VALUES (358, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-30 06:54:58', NULL, '2020-08-30 06:54:58', NULL, 0);
INSERT INTO `sys_log` VALUES (359, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-30 07:01:39', NULL, '2020-08-30 07:01:39', NULL, 0);
INSERT INTO `sys_log` VALUES (360, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-30 07:06:05', NULL, '2020-08-30 07:06:05', NULL, 0);
INSERT INTO `sys_log` VALUES (361, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-30 07:11:01', NULL, '2020-08-30 07:11:01', NULL, 0);
INSERT INTO `sys_log` VALUES (362, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-30 07:18:16', NULL, '2020-08-30 07:18:16', NULL, 0);
INSERT INTO `sys_log` VALUES (363, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-30 07:22:47', NULL, '2020-08-30 07:22:47', NULL, 0);
INSERT INTO `sys_log` VALUES (364, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-30 07:24:05', NULL, '2020-08-30 07:24:05', NULL, 0);
INSERT INTO `sys_log` VALUES (365, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-30 07:28:21', NULL, '2020-08-30 07:28:21', NULL, 0);
INSERT INTO `sys_log` VALUES (366, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-30 07:30:23', NULL, '2020-08-30 07:30:23', NULL, 0);
INSERT INTO `sys_log` VALUES (367, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-30 07:32:08', NULL, '2020-08-30 07:32:08', NULL, 0);
INSERT INTO `sys_log` VALUES (368, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-30 07:33:51', NULL, '2020-08-30 07:33:51', NULL, 0);
INSERT INTO `sys_log` VALUES (369, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-30 07:52:01', NULL, '2020-08-30 07:52:01', NULL, 0);
INSERT INTO `sys_log` VALUES (370, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-30 07:54:05', NULL, '2020-08-30 07:54:05', NULL, 0);
INSERT INTO `sys_log` VALUES (371, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-30 07:57:42', NULL, '2020-08-30 07:57:42', NULL, 0);
INSERT INTO `sys_log` VALUES (372, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-30 08:04:01', NULL, '2020-08-30 08:04:01', NULL, 0);
INSERT INTO `sys_log` VALUES (373, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-30 08:08:21', NULL, '2020-08-30 08:08:21', NULL, 0);
INSERT INTO `sys_log` VALUES (374, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-30 08:10:27', NULL, '2020-08-30 08:10:27', NULL, 0);
INSERT INTO `sys_log` VALUES (375, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-30 08:13:00', NULL, '2020-08-30 08:13:00', NULL, 0);
INSERT INTO `sys_log` VALUES (376, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-30 08:16:37', NULL, '2020-08-30 08:16:37', NULL, 0);
INSERT INTO `sys_log` VALUES (377, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-30 08:21:19', NULL, '2020-08-30 08:21:19', NULL, 0);
INSERT INTO `sys_log` VALUES (378, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-30 08:33:18', NULL, '2020-08-30 08:33:18', NULL, 0);
INSERT INTO `sys_log` VALUES (379, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-30 08:34:43', NULL, '2020-08-30 08:34:43', NULL, 0);
INSERT INTO `sys_log` VALUES (380, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-30 08:39:42', NULL, '2020-08-30 08:39:42', NULL, 0);
INSERT INTO `sys_log` VALUES (381, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-30 08:41:11', NULL, '2020-08-30 08:41:11', NULL, 0);
INSERT INTO `sys_log` VALUES (382, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-30 08:46:55', NULL, '2020-08-30 08:46:55', NULL, 0);
INSERT INTO `sys_log` VALUES (383, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-30 08:48:11', NULL, '2020-08-30 08:48:11', NULL, 0);
INSERT INTO `sys_log` VALUES (384, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-30 08:56:53', NULL, '2020-08-30 08:56:53', NULL, 0);
INSERT INTO `sys_log` VALUES (385, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-30 08:58:32', NULL, '2020-08-30 08:58:32', NULL, 0);
INSERT INTO `sys_log` VALUES (386, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-30 09:02:24', NULL, '2020-08-30 09:02:24', NULL, 0);
INSERT INTO `sys_log` VALUES (387, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-30 09:04:57', NULL, '2020-08-30 09:04:57', NULL, 0);
INSERT INTO `sys_log` VALUES (388, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-30 09:11:17', NULL, '2020-08-30 09:11:17', NULL, 0);
INSERT INTO `sys_log` VALUES (389, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-30 09:12:39', NULL, '2020-08-30 09:12:39', NULL, 0);
INSERT INTO `sys_log` VALUES (390, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-30 09:17:13', NULL, '2020-08-30 09:17:13', NULL, 0);
INSERT INTO `sys_log` VALUES (391, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-30 09:18:52', NULL, '2020-08-30 09:18:52', NULL, 0);
INSERT INTO `sys_log` VALUES (392, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-30 09:29:22', NULL, '2020-08-30 09:29:22', NULL, 0);
INSERT INTO `sys_log` VALUES (393, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-30 09:33:28', NULL, '2020-08-30 09:33:28', NULL, 0);
INSERT INTO `sys_log` VALUES (394, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-30 09:44:54', NULL, '2020-08-30 09:44:54', NULL, 0);
INSERT INTO `sys_log` VALUES (395, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-30 09:48:42', NULL, '2020-08-30 09:48:42', NULL, 0);
INSERT INTO `sys_log` VALUES (396, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-30 09:50:03', NULL, '2020-08-30 09:50:03', NULL, 0);
INSERT INTO `sys_log` VALUES (397, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-30 09:52:32', NULL, '2020-08-30 09:52:32', NULL, 0);
INSERT INTO `sys_log` VALUES (398, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-30 09:53:56', NULL, '2020-08-30 09:53:56', NULL, 0);
INSERT INTO `sys_log` VALUES (399, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-30 10:16:44', NULL, '2020-08-30 10:16:44', NULL, 0);
INSERT INTO `sys_log` VALUES (400, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-30 10:19:01', NULL, '2020-08-30 10:19:01', NULL, 0);
INSERT INTO `sys_log` VALUES (401, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 06:18:18', NULL, '2020-08-31 06:18:18', NULL, 0);
INSERT INTO `sys_log` VALUES (402, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 06:20:01', NULL, '2020-08-31 06:20:01', NULL, 0);
INSERT INTO `sys_log` VALUES (403, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 06:23:40', NULL, '2020-08-31 06:23:40', NULL, 0);
INSERT INTO `sys_log` VALUES (404, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 06:27:24', NULL, '2020-08-31 06:27:24', NULL, 0);
INSERT INTO `sys_log` VALUES (405, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 06:37:49', NULL, '2020-08-31 06:37:49', NULL, 0);
INSERT INTO `sys_log` VALUES (406, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 06:42:49', NULL, '2020-08-31 06:42:49', NULL, 0);
INSERT INTO `sys_log` VALUES (407, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 06:46:48', NULL, '2020-08-31 06:46:48', NULL, 0);
INSERT INTO `sys_log` VALUES (408, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 06:48:15', NULL, '2020-08-31 06:48:15', NULL, 0);
INSERT INTO `sys_log` VALUES (409, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 06:51:26', NULL, '2020-08-31 06:51:26', NULL, 0);
INSERT INTO `sys_log` VALUES (410, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 07:09:02', NULL, '2020-08-31 07:09:02', NULL, 0);
INSERT INTO `sys_log` VALUES (411, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 07:09:28', NULL, '2020-08-31 07:09:28', NULL, 0);
INSERT INTO `sys_log` VALUES (412, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 07:14:47', NULL, '2020-08-31 07:14:47', NULL, 0);
INSERT INTO `sys_log` VALUES (413, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 07:16:43', NULL, '2020-08-31 07:16:43', NULL, 0);
INSERT INTO `sys_log` VALUES (414, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 07:19:28', NULL, '2020-08-31 07:19:28', NULL, 0);
INSERT INTO `sys_log` VALUES (415, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 07:24:49', NULL, '2020-08-31 07:24:49', NULL, 0);
INSERT INTO `sys_log` VALUES (416, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 07:28:12', NULL, '2020-08-31 07:28:12', NULL, 0);
INSERT INTO `sys_log` VALUES (417, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 07:35:08', NULL, '2020-08-31 07:35:08', NULL, 0);
INSERT INTO `sys_log` VALUES (418, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 07:37:59', NULL, '2020-08-31 07:37:59', NULL, 0);
INSERT INTO `sys_log` VALUES (419, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 07:45:34', NULL, '2020-08-31 07:45:34', NULL, 0);
INSERT INTO `sys_log` VALUES (420, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 07:47:28', NULL, '2020-08-31 07:47:28', NULL, 0);
INSERT INTO `sys_log` VALUES (421, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 07:50:52', NULL, '2020-08-31 07:50:52', NULL, 0);
INSERT INTO `sys_log` VALUES (422, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 07:53:04', NULL, '2020-08-31 07:53:04', NULL, 0);
INSERT INTO `sys_log` VALUES (423, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 07:55:03', NULL, '2020-08-31 07:55:03', NULL, 0);
INSERT INTO `sys_log` VALUES (424, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 07:57:28', NULL, '2020-08-31 07:57:28', NULL, 0);
INSERT INTO `sys_log` VALUES (425, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 08:00:36', NULL, '2020-08-31 08:00:36', NULL, 0);
INSERT INTO `sys_log` VALUES (426, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 08:03:57', NULL, '2020-08-31 08:03:57', NULL, 0);
INSERT INTO `sys_log` VALUES (427, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 08:07:36', NULL, '2020-08-31 08:07:36', NULL, 0);
INSERT INTO `sys_log` VALUES (428, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 08:11:04', NULL, '2020-08-31 08:11:04', NULL, 0);
INSERT INTO `sys_log` VALUES (429, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 08:12:41', NULL, '2020-08-31 08:12:41', NULL, 0);
INSERT INTO `sys_log` VALUES (430, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 08:14:39', NULL, '2020-08-31 08:14:39', NULL, 0);
INSERT INTO `sys_log` VALUES (431, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 08:23:43', NULL, '2020-08-31 08:23:43', NULL, 0);
INSERT INTO `sys_log` VALUES (432, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 08:37:57', NULL, '2020-08-31 08:37:57', NULL, 0);
INSERT INTO `sys_log` VALUES (433, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 08:41:53', NULL, '2020-08-31 08:41:53', NULL, 0);
INSERT INTO `sys_log` VALUES (434, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 08:48:18', NULL, '2020-08-31 08:48:18', NULL, 0);
INSERT INTO `sys_log` VALUES (435, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 08:48:29', NULL, '2020-08-31 08:48:29', NULL, 0);
INSERT INTO `sys_log` VALUES (436, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 08:52:14', NULL, '2020-08-31 08:52:14', NULL, 0);
INSERT INTO `sys_log` VALUES (437, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 08:57:18', NULL, '2020-08-31 08:57:18', NULL, 0);
INSERT INTO `sys_log` VALUES (438, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 09:06:57', NULL, '2020-08-31 09:06:57', NULL, 0);
INSERT INTO `sys_log` VALUES (439, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 09:11:15', NULL, '2020-08-31 09:11:15', NULL, 0);
INSERT INTO `sys_log` VALUES (440, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 09:20:30', NULL, '2020-08-31 09:20:30', NULL, 0);
INSERT INTO `sys_log` VALUES (441, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 09:26:18', NULL, '2020-08-31 09:26:18', NULL, 0);
INSERT INTO `sys_log` VALUES (442, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 09:29:07', NULL, '2020-08-31 09:29:07', NULL, 0);
INSERT INTO `sys_log` VALUES (443, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 09:30:48', NULL, '2020-08-31 09:30:48', NULL, 0);
INSERT INTO `sys_log` VALUES (444, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 09:34:00', NULL, '2020-08-31 09:34:00', NULL, 0);
INSERT INTO `sys_log` VALUES (445, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 09:36:07', NULL, '2020-08-31 09:36:07', NULL, 0);
INSERT INTO `sys_log` VALUES (446, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 09:40:13', NULL, '2020-08-31 09:40:13', NULL, 0);
INSERT INTO `sys_log` VALUES (447, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 09:43:45', NULL, '2020-08-31 09:43:45', NULL, 0);
INSERT INTO `sys_log` VALUES (448, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 09:45:15', NULL, '2020-08-31 09:45:15', NULL, 0);
INSERT INTO `sys_log` VALUES (449, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 09:48:16', NULL, '2020-08-31 09:48:16', NULL, 0);
INSERT INTO `sys_log` VALUES (450, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 09:51:40', NULL, '2020-08-31 09:51:40', NULL, 0);
INSERT INTO `sys_log` VALUES (451, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 09:56:15', NULL, '2020-08-31 09:56:15', NULL, 0);
INSERT INTO `sys_log` VALUES (452, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 09:59:48', NULL, '2020-08-31 09:59:48', NULL, 0);
INSERT INTO `sys_log` VALUES (453, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 10:03:50', NULL, '2020-08-31 10:03:50', NULL, 0);
INSERT INTO `sys_log` VALUES (454, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 10:20:22', NULL, '2020-08-31 10:20:22', NULL, 0);
INSERT INTO `sys_log` VALUES (455, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 10:23:03', NULL, '2020-08-31 10:23:03', NULL, 0);
INSERT INTO `sys_log` VALUES (456, 'AdminSys', '192.168.88.161', 1, '登录', 1, '', '', 0, '2020-08-31 10:25:03', NULL, '2020-08-31 10:25:03', NULL, 0);
INSERT INTO `sys_log` VALUES (457, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 10:57:51', NULL, '2020-08-31 10:57:51', NULL, 0);
INSERT INTO `sys_log` VALUES (458, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 11:13:42', NULL, '2020-08-31 11:13:42', NULL, 0);
INSERT INTO `sys_log` VALUES (459, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 11:58:02', NULL, '2020-08-31 11:58:02', NULL, 0);
INSERT INTO `sys_log` VALUES (460, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 14:38:46', NULL, '2020-08-31 14:38:46', NULL, 0);
INSERT INTO `sys_log` VALUES (461, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 14:41:56', NULL, '2020-08-31 14:41:56', NULL, 0);
INSERT INTO `sys_log` VALUES (462, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 14:46:55', NULL, '2020-08-31 14:46:55', NULL, 0);
INSERT INTO `sys_log` VALUES (463, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 14:51:10', NULL, '2020-08-31 14:51:10', NULL, 0);
INSERT INTO `sys_log` VALUES (464, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 14:52:58', NULL, '2020-08-31 14:52:58', NULL, 0);
INSERT INTO `sys_log` VALUES (465, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 14:58:13', NULL, '2020-08-31 14:58:13', NULL, 0);
INSERT INTO `sys_log` VALUES (466, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 15:02:27', NULL, '2020-08-31 15:02:27', NULL, 0);
INSERT INTO `sys_log` VALUES (467, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 15:05:52', NULL, '2020-08-31 15:05:52', NULL, 0);
INSERT INTO `sys_log` VALUES (468, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 15:08:19', NULL, '2020-08-31 15:08:19', NULL, 0);
INSERT INTO `sys_log` VALUES (469, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 15:11:53', NULL, '2020-08-31 15:11:53', NULL, 0);
INSERT INTO `sys_log` VALUES (470, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 15:13:58', NULL, '2020-08-31 15:13:58', NULL, 0);
INSERT INTO `sys_log` VALUES (471, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 15:17:29', NULL, '2020-08-31 15:17:29', NULL, 0);
INSERT INTO `sys_log` VALUES (472, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 15:19:37', NULL, '2020-08-31 15:19:37', NULL, 0);
INSERT INTO `sys_log` VALUES (473, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 15:23:27', NULL, '2020-08-31 15:23:27', NULL, 0);
INSERT INTO `sys_log` VALUES (474, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 15:26:17', NULL, '2020-08-31 15:26:17', NULL, 0);
INSERT INTO `sys_log` VALUES (475, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 15:38:36', NULL, '2020-08-31 15:38:36', NULL, 0);
INSERT INTO `sys_log` VALUES (476, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 15:42:53', NULL, '2020-08-31 15:42:53', NULL, 0);
INSERT INTO `sys_log` VALUES (477, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '添加管理员', 1, 'a', '', 0, '2020-08-31 15:43:17', NULL, '2020-08-31 15:43:17', NULL, 0);
INSERT INTO `sys_log` VALUES (478, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 15:46:39', NULL, '2020-08-31 15:46:39', NULL, 0);
INSERT INTO `sys_log` VALUES (479, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 15:50:34', NULL, '2020-08-31 15:50:34', NULL, 0);
INSERT INTO `sys_log` VALUES (480, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 15:54:43', NULL, '2020-08-31 15:54:43', NULL, 0);
INSERT INTO `sys_log` VALUES (481, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 15:58:24', NULL, '2020-08-31 15:58:24', NULL, 0);
INSERT INTO `sys_log` VALUES (482, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 16:00:05', NULL, '2020-08-31 16:00:05', NULL, 0);
INSERT INTO `sys_log` VALUES (483, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 16:02:32', NULL, '2020-08-31 16:02:32', NULL, 0);
INSERT INTO `sys_log` VALUES (484, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 16:07:09', NULL, '2020-08-31 16:07:09', NULL, 0);
INSERT INTO `sys_log` VALUES (485, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 16:09:44', NULL, '2020-08-31 16:09:44', NULL, 0);
INSERT INTO `sys_log` VALUES (486, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 16:12:52', NULL, '2020-08-31 16:12:52', NULL, 0);
INSERT INTO `sys_log` VALUES (487, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 16:17:25', NULL, '2020-08-31 16:17:25', NULL, 0);
INSERT INTO `sys_log` VALUES (488, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 16:25:12', NULL, '2020-08-31 16:25:12', NULL, 0);
INSERT INTO `sys_log` VALUES (489, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '编辑管理员', 1, 'a', '', 0, '2020-08-31 16:25:27', NULL, '2020-08-31 16:25:27', NULL, 0);
INSERT INTO `sys_log` VALUES (490, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 16:30:40', NULL, '2020-08-31 16:30:40', NULL, 0);
INSERT INTO `sys_log` VALUES (491, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 16:37:22', NULL, '2020-08-31 16:37:22', NULL, 0);
INSERT INTO `sys_log` VALUES (492, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 16:42:59', NULL, '2020-08-31 16:42:59', NULL, 0);
INSERT INTO `sys_log` VALUES (493, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '退出', 1, '', '', 0, '2020-08-31 16:43:41', NULL, '2020-08-31 16:43:41', NULL, 0);
INSERT INTO `sys_log` VALUES (494, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 16:43:42', NULL, '2020-08-31 16:43:42', NULL, 0);
INSERT INTO `sys_log` VALUES (495, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 16:50:26', NULL, '2020-08-31 16:50:26', NULL, 0);
INSERT INTO `sys_log` VALUES (496, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 16:57:39', NULL, '2020-08-31 16:57:39', NULL, 0);
INSERT INTO `sys_log` VALUES (497, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 17:05:42', NULL, '2020-08-31 17:05:42', NULL, 0);
INSERT INTO `sys_log` VALUES (498, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 17:24:24', NULL, '2020-08-31 17:24:24', NULL, 0);
INSERT INTO `sys_log` VALUES (499, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 17:26:29', NULL, '2020-08-31 17:26:29', NULL, 0);
INSERT INTO `sys_log` VALUES (500, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 17:29:59', NULL, '2020-08-31 17:29:59', NULL, 0);
INSERT INTO `sys_log` VALUES (501, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 17:35:10', NULL, '2020-08-31 17:35:10', NULL, 0);
INSERT INTO `sys_log` VALUES (502, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 17:36:54', NULL, '2020-08-31 17:36:54', NULL, 0);
INSERT INTO `sys_log` VALUES (503, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 17:51:26', NULL, '2020-08-31 17:51:26', NULL, 0);
INSERT INTO `sys_log` VALUES (504, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 18:02:25', NULL, '2020-08-31 18:02:25', NULL, 0);
INSERT INTO `sys_log` VALUES (505, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 18:07:07', NULL, '2020-08-31 18:07:07', NULL, 0);
INSERT INTO `sys_log` VALUES (506, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 18:16:40', NULL, '2020-08-31 18:16:40', NULL, 0);
INSERT INTO `sys_log` VALUES (507, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 18:18:34', NULL, '2020-08-31 18:18:34', NULL, 0);
INSERT INTO `sys_log` VALUES (508, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 18:20:34', NULL, '2020-08-31 18:20:34', NULL, 0);
INSERT INTO `sys_log` VALUES (509, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 18:23:36', NULL, '2020-08-31 18:23:36', NULL, 0);
INSERT INTO `sys_log` VALUES (510, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 18:27:50', NULL, '2020-08-31 18:27:50', NULL, 0);
INSERT INTO `sys_log` VALUES (511, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 18:30:04', NULL, '2020-08-31 18:30:04', NULL, 0);
INSERT INTO `sys_log` VALUES (512, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 18:34:19', NULL, '2020-08-31 18:34:19', NULL, 0);
INSERT INTO `sys_log` VALUES (513, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 18:41:33', NULL, '2020-08-31 18:41:33', NULL, 0);
INSERT INTO `sys_log` VALUES (514, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 18:43:58', NULL, '2020-08-31 18:43:58', NULL, 0);
INSERT INTO `sys_log` VALUES (515, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 18:47:14', NULL, '2020-08-31 18:47:14', NULL, 0);
INSERT INTO `sys_log` VALUES (516, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 18:51:42', NULL, '2020-08-31 18:51:42', NULL, 0);
INSERT INTO `sys_log` VALUES (517, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 18:54:42', NULL, '2020-08-31 18:54:42', NULL, 0);
INSERT INTO `sys_log` VALUES (518, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 19:01:53', NULL, '2020-08-31 19:01:53', NULL, 0);
INSERT INTO `sys_log` VALUES (519, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 19:06:42', NULL, '2020-08-31 19:06:42', NULL, 0);
INSERT INTO `sys_log` VALUES (520, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 19:09:26', NULL, '2020-08-31 19:09:26', NULL, 0);
INSERT INTO `sys_log` VALUES (521, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 19:11:13', NULL, '2020-08-31 19:11:13', NULL, 0);
INSERT INTO `sys_log` VALUES (522, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 19:14:40', NULL, '2020-08-31 19:14:40', NULL, 0);
INSERT INTO `sys_log` VALUES (523, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 19:16:06', NULL, '2020-08-31 19:16:06', NULL, 0);
INSERT INTO `sys_log` VALUES (524, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 19:18:41', NULL, '2020-08-31 19:18:41', NULL, 0);
INSERT INTO `sys_log` VALUES (525, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-08-31 20:28:36', NULL, '2020-08-31 20:28:36', NULL, 0);
INSERT INTO `sys_log` VALUES (526, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-09-01 05:53:15', NULL, '2020-09-01 05:53:15', NULL, 0);
INSERT INTO `sys_log` VALUES (527, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-09-01 09:28:51', NULL, '2020-09-01 09:28:51', NULL, 0);
INSERT INTO `sys_log` VALUES (528, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-09-01 15:52:57', NULL, '2020-09-01 15:52:57', NULL, 0);
INSERT INTO `sys_log` VALUES (529, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-09-02 18:57:19', NULL, '2020-09-02 18:57:19', NULL, 0);
INSERT INTO `sys_log` VALUES (530, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-09-02 19:00:47', NULL, '2020-09-02 19:00:47', NULL, 0);
INSERT INTO `sys_log` VALUES (531, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-09-02 19:12:49', NULL, '2020-09-02 19:12:49', NULL, 0);
INSERT INTO `sys_log` VALUES (532, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-09-02 19:19:38', NULL, '2020-09-02 19:19:38', NULL, 0);
INSERT INTO `sys_log` VALUES (533, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-09-02 21:03:33', NULL, '2020-09-02 21:03:33', NULL, 0);
INSERT INTO `sys_log` VALUES (534, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-09-03 04:44:04', NULL, '2020-09-03 04:44:04', NULL, 0);
INSERT INTO `sys_log` VALUES (535, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-09-03 05:36:21', NULL, '2020-09-03 05:36:21', NULL, 0);
INSERT INTO `sys_log` VALUES (536, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-09-03 05:41:22', NULL, '2020-09-03 05:41:22', NULL, 0);
INSERT INTO `sys_log` VALUES (537, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-09-03 05:46:35', NULL, '2020-09-03 05:46:35', NULL, 0);
INSERT INTO `sys_log` VALUES (538, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-09-03 05:50:29', NULL, '2020-09-03 05:50:29', NULL, 0);
INSERT INTO `sys_log` VALUES (539, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-09-03 05:55:26', NULL, '2020-09-03 05:55:26', NULL, 0);
INSERT INTO `sys_log` VALUES (540, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-09-03 05:59:56', NULL, '2020-09-03 05:59:56', NULL, 0);
INSERT INTO `sys_log` VALUES (541, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-09-03 06:02:55', NULL, '2020-09-03 06:02:55', NULL, 0);
INSERT INTO `sys_log` VALUES (542, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-09-03 06:04:38', NULL, '2020-09-03 06:04:38', NULL, 0);
INSERT INTO `sys_log` VALUES (543, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-09-03 06:13:44', NULL, '2020-09-03 06:13:44', NULL, 0);
INSERT INTO `sys_log` VALUES (544, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-09-03 06:18:32', NULL, '2020-09-03 06:18:32', NULL, 0);
INSERT INTO `sys_log` VALUES (545, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-09-03 06:20:48', NULL, '2020-09-03 06:20:48', NULL, 0);
INSERT INTO `sys_log` VALUES (546, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-09-03 06:23:02', NULL, '2020-09-03 06:23:02', NULL, 0);
INSERT INTO `sys_log` VALUES (547, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-09-03 06:28:42', NULL, '2020-09-03 06:28:42', NULL, 0);
INSERT INTO `sys_log` VALUES (548, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-09-03 06:31:14', NULL, '2020-09-03 06:31:14', NULL, 0);
INSERT INTO `sys_log` VALUES (549, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-09-03 06:33:35', NULL, '2020-09-03 06:33:35', NULL, 0);
INSERT INTO `sys_log` VALUES (550, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-09-03 06:36:56', NULL, '2020-09-03 06:36:56', NULL, 0);
INSERT INTO `sys_log` VALUES (551, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-09-03 07:13:59', NULL, '2020-09-03 07:13:59', NULL, 0);
INSERT INTO `sys_log` VALUES (552, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-09-03 07:19:15', NULL, '2020-09-03 07:19:15', NULL, 0);
INSERT INTO `sys_log` VALUES (553, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-09-04 05:21:33', NULL, '2020-09-04 05:21:33', NULL, 0);
INSERT INTO `sys_log` VALUES (554, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-09-04 06:21:33', NULL, '2020-09-04 06:21:33', NULL, 0);
INSERT INTO `sys_log` VALUES (555, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-09-04 06:25:49', NULL, '2020-09-04 06:25:49', NULL, 0);
INSERT INTO `sys_log` VALUES (556, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-09-04 06:29:58', NULL, '2020-09-04 06:29:58', NULL, 0);
INSERT INTO `sys_log` VALUES (557, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '退出', 1, '', '', 0, '2020-09-04 06:30:26', NULL, '2020-09-04 06:30:26', NULL, 0);
INSERT INTO `sys_log` VALUES (558, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-09-04 06:30:27', NULL, '2020-09-04 06:30:27', NULL, 0);
INSERT INTO `sys_log` VALUES (559, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '退出', 1, '', '', 0, '2020-09-04 06:32:09', NULL, '2020-09-04 06:32:09', NULL, 0);
INSERT INTO `sys_log` VALUES (560, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-09-04 06:32:11', NULL, '2020-09-04 06:32:11', NULL, 0);
INSERT INTO `sys_log` VALUES (561, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '退出', 1, '', '', 0, '2020-09-04 06:33:20', NULL, '2020-09-04 06:33:20', NULL, 0);
INSERT INTO `sys_log` VALUES (562, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-09-04 06:33:21', NULL, '2020-09-04 06:33:21', NULL, 0);
INSERT INTO `sys_log` VALUES (563, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '退出', 1, '', '', 0, '2020-09-04 06:36:15', NULL, '2020-09-04 06:36:15', NULL, 0);
INSERT INTO `sys_log` VALUES (564, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-09-04 06:36:16', NULL, '2020-09-04 06:36:16', NULL, 0);
INSERT INTO `sys_log` VALUES (565, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-09-04 09:25:36', NULL, '2020-09-04 09:25:36', NULL, 0);
INSERT INTO `sys_log` VALUES (566, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '退出', 1, '', '', 0, '2020-09-04 09:26:07', NULL, '2020-09-04 09:26:07', NULL, 0);
INSERT INTO `sys_log` VALUES (567, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-09-04 09:26:08', NULL, '2020-09-04 09:26:08', NULL, 0);
INSERT INTO `sys_log` VALUES (568, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-09-04 09:29:21', NULL, '2020-09-04 09:29:21', NULL, 0);
INSERT INTO `sys_log` VALUES (569, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-09-04 09:30:45', NULL, '2020-09-04 09:30:45', NULL, 0);
INSERT INTO `sys_log` VALUES (570, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-09-04 09:32:30', NULL, '2020-09-04 09:32:30', NULL, 0);
INSERT INTO `sys_log` VALUES (571, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-09-04 09:33:44', NULL, '2020-09-04 09:33:44', NULL, 0);
INSERT INTO `sys_log` VALUES (572, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-09-04 09:34:58', NULL, '2020-09-04 09:34:58', NULL, 0);
INSERT INTO `sys_log` VALUES (573, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-09-04 09:37:06', NULL, '2020-09-04 09:37:06', NULL, 0);
INSERT INTO `sys_log` VALUES (574, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-09-04 09:39:44', NULL, '2020-09-04 09:39:44', NULL, 0);
INSERT INTO `sys_log` VALUES (575, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-09-04 09:44:18', NULL, '2020-09-04 09:44:18', NULL, 0);
INSERT INTO `sys_log` VALUES (576, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-09-04 09:47:06', NULL, '2020-09-04 09:47:06', NULL, 0);
INSERT INTO `sys_log` VALUES (577, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-09-04 09:49:57', NULL, '2020-09-04 09:49:57', NULL, 0);
INSERT INTO `sys_log` VALUES (578, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-09-04 10:02:25', NULL, '2020-09-04 10:02:25', NULL, 0);
INSERT INTO `sys_log` VALUES (579, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-09-07 13:27:57', NULL, '2020-09-07 13:27:57', NULL, 0);
INSERT INTO `sys_log` VALUES (580, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-09-08 13:57:52', NULL, '2020-09-08 13:57:52', NULL, 0);
INSERT INTO `sys_log` VALUES (581, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-09-08 14:00:29', NULL, '2020-09-08 14:00:29', NULL, 0);
INSERT INTO `sys_log` VALUES (582, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-09-08 18:11:50', NULL, '2020-09-08 18:11:50', NULL, 0);
INSERT INTO `sys_log` VALUES (583, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-09-08 18:14:19', NULL, '2020-09-08 18:14:19', NULL, 0);
INSERT INTO `sys_log` VALUES (584, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-09-08 19:06:20', NULL, '2020-09-08 19:06:20', NULL, 0);
INSERT INTO `sys_log` VALUES (585, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-09-08 20:15:59', NULL, '2020-09-08 20:15:59', NULL, 0);
INSERT INTO `sys_log` VALUES (586, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-09-09 09:47:22', NULL, '2020-09-09 09:47:22', NULL, 0);
INSERT INTO `sys_log` VALUES (587, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-09-09 10:59:27', NULL, '2020-09-09 10:59:27', NULL, 0);
INSERT INTO `sys_log` VALUES (588, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-09-13 06:31:11', NULL, '2020-09-13 06:31:11', NULL, 0);
INSERT INTO `sys_log` VALUES (589, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-09-13 06:34:28', NULL, '2020-09-13 06:34:28', NULL, 0);
INSERT INTO `sys_log` VALUES (590, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-09-13 06:46:21', NULL, '2020-09-13 06:46:21', NULL, 0);
INSERT INTO `sys_log` VALUES (591, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-09-14 06:46:25', NULL, '2020-09-14 06:46:25', NULL, 0);
INSERT INTO `sys_log` VALUES (592, 'AdminSys', '0:0:0:0:0:0:0:1', 1, '登录', 1, '', '', 0, '2020-09-14 10:48:29', NULL, '2020-09-14 10:48:29', NULL, 0);

-- ----------------------------
-- Table structure for sys_opadmin_def
-- ----------------------------
DROP TABLE IF EXISTS `sys_opadmin_def`;
CREATE TABLE `sys_opadmin_def`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `type_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '消息类型ID',
  `type_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '消息类型名称',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '消息主题',
  `content` varchar(1023) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '消息内容',
  `begin_date` datetime(0) NULL DEFAULT NULL COMMENT '消息生效时间',
  `end_date` datetime(0) NULL DEFAULT NULL COMMENT '消息失效时间',
  `expire_flag` tinyint(1) NULL DEFAULT 0 COMMENT '消息过期标志',
  `web_hint` tinyint(1) NULL DEFAULT 0 COMMENT '站内提醒',
  `sms_hint` tinyint(1) NULL DEFAULT NULL COMMENT '短信标志',
  `mail_hint` tinyint(1) NULL DEFAULT 0 COMMENT '邮件标志',
  `pop_hint` tinyint(1) NULL DEFAULT 0 COMMENT '弹窗提醒',
  `deal_web_link` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '处理链接网址',
  `tenant_id` int(11) NOT NULL DEFAULT 0 COMMENT '租户ID',
  `add_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `add_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最后修改人',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统管理_后端消息_定义表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_opadmin_info
-- ----------------------------
DROP TABLE IF EXISTS `sys_opadmin_info`;
CREATE TABLE `sys_opadmin_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `opadmin_id` int(11) NULL DEFAULT NULL COMMENT '操作用户ID',
  `opadmin_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作用户名称',
  `telephone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '接收手机号',
  `info_id` int(11) NULL DEFAULT NULL COMMENT '定义消息ID',
  `type_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '消息类型编码',
  `type_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '消息类型名称',
  `source_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '消息来源编码',
  `source_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '消息来源名称',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '消息主题',
  `content` varchar(1023) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '消息内容',
  `web_hint` tinyint(1) NULL DEFAULT 0 COMMENT '站内提醒',
  `sms_hint` tinyint(1) NULL DEFAULT NULL COMMENT '短信标志',
  `mail_hint` tinyint(1) NULL DEFAULT 0 COMMENT '邮件标志',
  `pop_hint` tinyint(1) NULL DEFAULT 0 COMMENT '弹窗提醒',
  `display_flag` tinyint(1) NULL DEFAULT 0 COMMENT '停显标志',
  `if_viewed` tinyint(1) NULL DEFAULT 0 COMMENT '查看标志',
  `flow_start_id` int(11) NULL DEFAULT NULL COMMENT '流程主表ID',
  `flow_instance_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '流程实例ID',
  `flow_start_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '流程名称',
  `flow_model_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '流程定义Key',
  `flow_start_detail_id` int(11) NULL DEFAULT NULL COMMENT '流程节点表ID',
  `flow_node_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '流程节点ID',
  `flow_node_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '流程节点名称',
  `tenant_id` int(11) NOT NULL DEFAULT 0 COMMENT '租户ID',
  `add_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `add_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最后修改人',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统管理_后端消息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_opadmin_pub
-- ----------------------------
DROP TABLE IF EXISTS `sys_opadmin_pub`;
CREATE TABLE `sys_opadmin_pub`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `info_id` int(11) NULL DEFAULT NULL COMMENT '消息ID',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '管理员ID',
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '管理员名称',
  `tenant_id` int(11) NOT NULL DEFAULT 0 COMMENT '租户ID',
  `add_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `add_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最后修改人',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统管理_后端消息_通知用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_operation_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_operation_log`;
CREATE TABLE `sys_operation_log`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `log_type_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '日志类型名称',
  `op_time` datetime(0) NULL DEFAULT NULL COMMENT '操作时间',
  `computer` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '计算机',
  `ip_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'IP地址',
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作用户',
  `table_id` int(11) NULL DEFAULT NULL COMMENT '操作表ID',
  `table_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作表名',
  `table_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作表描述',
  `data_id` int(11) NULL DEFAULT NULL COMMENT '操作数据主键ID',
  `log_remark` varchar(4000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `node_remark` varchar(4000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '节点描述',
  `parent_table_id` int(11) NULL DEFAULT NULL COMMENT '父表ID',
  `parent_table_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '父表表名',
  `parent_table_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '父表描述',
  `parent_data_id` int(11) NULL DEFAULT NULL COMMENT '父表数据主键',
  `flow_start_id` int(11) NULL DEFAULT NULL COMMENT '流程申请ID',
  `flow_instance_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '流程实例ID',
  `task_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '任务ID',
  `tenant_id` int(11) NOT NULL DEFAULT 0 COMMENT '租户ID',
  `add_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `add_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最后修改人',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '数据操作日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_quartz_task_manage
-- ----------------------------
DROP TABLE IF EXISTS `sys_quartz_task_manage`;
CREATE TABLE `sys_quartz_task_manage`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '任务名称',
  `job_group` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '任务组',
  `job_status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '任务状态',
  `cron` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '定时器',
  `class_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '类名称',
  `method_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '方法名称',
  `parameters` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '参数列表',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '任务描述',
  `error_log` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '错误日志',
  `tenant_id` int(11) NOT NULL DEFAULT 0 COMMENT '租户ID',
  `add_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `add_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最后修改人',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '定时任务管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `menu_id` int(11) NULL DEFAULT NULL COMMENT '菜单ID',
  `menu_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单Code',
  `tenant_id` int(11) NOT NULL DEFAULT 0 COMMENT '租户ID',
  `add_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `add_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最后修改人',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统管理_菜单_角色权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_set
-- ----------------------------
DROP TABLE IF EXISTS `sys_set`;
CREATE TABLE `sys_set`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `key_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '系统配置名',
  `key_value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '系统配置值',
  `tenant_id` int(11) NOT NULL DEFAULT 0 COMMENT '租户ID',
  `add_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `add_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最后修改人',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 36 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统管理_系统配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_set
-- ----------------------------
INSERT INTO `sys_set` VALUES (1, 'a', 'a', 0, NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_set` VALUES (2, 'litemall_order_tihuo_beihuo_hour', '12', 0, '2020-08-13 21:30:50', NULL, '2020-08-13 21:30:50', NULL, 0);
INSERT INTO `sys_set` VALUES (3, 'litemall_order_yuyue_second_hour', '1', 0, '2020-08-13 21:30:50', NULL, '2020-08-13 21:30:50', NULL, 0);
INSERT INTO `sys_set` VALUES (4, 'litemall_wx_index_new', '6', 0, '2020-08-13 21:30:50', NULL, '2020-08-13 21:30:50', NULL, 0);
INSERT INTO `sys_set` VALUES (5, 'litemall_order_tihuo_max_days', '1', 0, '2020-08-13 21:30:50', NULL, '2020-08-13 21:30:50', NULL, 0);
INSERT INTO `sys_set` VALUES (6, 'litemall_wx_share', 'false', 0, '2020-08-13 21:30:50', NULL, '2020-08-13 21:30:50', NULL, 0);
INSERT INTO `sys_set` VALUES (7, 'litemall_weishang_index_tuan', '2', 0, '2020-08-13 21:30:50', NULL, '2020-08-13 21:30:50', NULL, 0);
INSERT INTO `sys_set` VALUES (8, 'litemall_weishang_index_actstore', '4', 0, '2020-08-13 21:30:50', NULL, '2020-08-13 21:30:50', NULL, 0);
INSERT INTO `sys_set` VALUES (9, 'litemall_mall_qq', '738696120', 0, '2020-08-13 21:30:50', NULL, '2020-08-13 21:30:50', NULL, 0);
INSERT INTO `sys_set` VALUES (10, 'litemall_order_tihuo_hint_first_hour', '4', 0, '2020-08-13 21:30:50', NULL, '2020-08-13 21:30:50', NULL, 0);
INSERT INTO `sys_set` VALUES (11, 'litemall_order_yuyue_hint_first_hour', '4', 0, '2020-08-13 21:30:50', NULL, '2020-08-13 21:30:50', NULL, 0);
INSERT INTO `sys_set` VALUES (12, 'litemall_wx_catlog_list', '4', 0, '2020-08-13 21:30:50', NULL, '2020-08-13 21:30:50', NULL, 0);
INSERT INTO `sys_set` VALUES (13, 'litemall_order_yuyue_delay_hour', '2', 0, '2020-08-13 21:30:50', NULL, '2020-08-13 21:30:50', NULL, 0);
INSERT INTO `sys_set` VALUES (14, 'litemall_wx_index_topic', '4', 0, '2020-08-13 21:30:50', NULL, '2020-08-13 21:30:50', NULL, 0);
INSERT INTO `sys_set` VALUES (15, 'litemall_order_unpaid', '30', 0, '2020-08-13 21:30:50', NULL, '2020-08-13 21:30:50', NULL, 0);
INSERT INTO `sys_set` VALUES (16, 'litemall_order_unconfirm', '7', 0, '2020-08-13 21:30:50', NULL, '2020-08-13 21:30:50', NULL, 0);
INSERT INTO `sys_set` VALUES (17, 'litemall_order_yuyue_beihuo_hour', '24', 0, '2020-08-13 21:30:50', NULL, '2020-08-13 21:30:50', NULL, 0);
INSERT INTO `sys_set` VALUES (18, 'litemall_order_yuyue_max_days', '7', 0, '2020-08-13 21:30:50', NULL, '2020-08-13 21:30:50', NULL, 0);
INSERT INTO `sys_set` VALUES (19, 'litemall_weishang_index_huiyuan', '2', 0, '2020-08-13 21:30:50', NULL, '2020-08-13 21:30:50', NULL, 0);
INSERT INTO `sys_set` VALUES (20, 'litemall_weishang_index_miaosha', '2', 0, '2020-08-13 21:30:50', NULL, '2020-08-13 21:30:50', NULL, 0);
INSERT INTO `sys_set` VALUES (21, 'litemall_express_freight_min', '88', 0, '2020-08-13 21:30:50', NULL, '2020-08-13 21:30:50', NULL, 0);
INSERT INTO `sys_set` VALUES (22, 'litemall_mall_name', 'litemall', 0, '2020-08-13 21:30:50', NULL, '2020-08-13 21:30:50', NULL, 0);
INSERT INTO `sys_set` VALUES (23, 'litemall_express_beijingcity_min', '10', 0, '2020-08-13 21:30:50', NULL, '2020-08-13 21:30:50', NULL, 0);
INSERT INTO `sys_set` VALUES (24, 'litemall_express_freight_value', '8', 0, '2020-08-13 21:30:50', NULL, '2020-08-13 21:30:50', NULL, 0);
INSERT INTO `sys_set` VALUES (25, 'litemall_wx_index_hot', '6', 0, '2020-08-13 21:30:50', NULL, '2020-08-13 21:30:50', NULL, 0);
INSERT INTO `sys_set` VALUES (26, 'litemall_order_comment', '7', 0, '2020-08-13 21:30:50', NULL, '2020-08-13 21:30:50', NULL, 0);
INSERT INTO `sys_set` VALUES (27, 'litemall_wx_catlog_goods', '4', 0, '2020-08-13 21:30:50', NULL, '2020-08-13 21:30:50', NULL, 0);
INSERT INTO `sys_set` VALUES (28, 'litemall_weishang_index_guessyoulike', '4', 0, '2020-08-13 21:30:50', NULL, '2020-08-13 21:30:50', NULL, 0);
INSERT INTO `sys_set` VALUES (29, 'litemall_express_othercity_min', '18', 0, '2020-08-13 21:30:50', NULL, '2020-08-13 21:30:50', NULL, 0);
INSERT INTO `sys_set` VALUES (30, 'litemall_mall_phone', '010-xxxx-xxxx', 0, '2020-08-13 21:30:50', NULL, '2020-08-13 21:30:50', NULL, 0);
INSERT INTO `sys_set` VALUES (31, 'litemall_weishang_index_daxingstore', '4', 0, '2020-08-13 21:30:50', NULL, '2020-08-13 21:30:50', NULL, 0);
INSERT INTO `sys_set` VALUES (32, 'litemall_mall_address', '北京', 0, '2020-08-13 21:30:50', NULL, '2020-08-13 21:30:50', NULL, 0);
INSERT INTO `sys_set` VALUES (33, 'litemall_wx_index_brand', '4', 0, '2020-08-13 21:30:50', NULL, '2020-08-13 21:30:50', NULL, 0);
INSERT INTO `sys_set` VALUES (34, 'litemall_order_tihuo_second_hour', '1', 0, '2020-08-13 21:30:50', NULL, '2020-08-13 21:30:50', NULL, 0);
INSERT INTO `sys_set` VALUES (35, 'litemall_weishang_index_buy', '6', 0, '2020-08-13 21:30:50', NULL, '2020-08-13 21:30:50', NULL, 0);

-- ----------------------------
-- Table structure for sys_storage
-- ----------------------------
DROP TABLE IF EXISTS `sys_storage`;
CREATE TABLE `sys_storage`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `key` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文件的唯一索引',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文件名',
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文件类型',
  `size` int(11) NOT NULL COMMENT '文件大小',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件访问链接',
  `tenant_id` int(11) NOT NULL DEFAULT 0 COMMENT '租户ID',
  `add_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `add_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最后修改人',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统管理_文件存储表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_table_desp
-- ----------------------------
DROP TABLE IF EXISTS `sys_table_desp`;
CREATE TABLE `sys_table_desp`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `db_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '数据库名',
  `db_table_or_view_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'DB表名或视图名',
  `chinese_desc` varchar(1023) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '中文含义',
  `primary_field_name` varchar(4000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '主键字段名',
  `ordernumber` int(11) NULL DEFAULT NULL COMMENT '排序',
  `tenant_id` int(11) NOT NULL DEFAULT 0 COMMENT '租户ID',
  `add_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `add_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '最后修改人',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '数据_视图' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_table_desp
-- ----------------------------
INSERT INTO `sys_table_desp` VALUES (1, '', 'test02', 'a', '', NULL, 0, '2020-09-03 06:07:46', 'AdminSys', '2020-09-03 06:14:14', 'AdminSys', 0);

-- ----------------------------
-- Table structure for sys_table_field_desp
-- ----------------------------
DROP TABLE IF EXISTS `sys_table_field_desp`;
CREATE TABLE `sys_table_field_desp`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `std_id` int(11) NULL DEFAULT NULL COMMENT '操作表ID',
  `std_chinese_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '主表描述',
  `field_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '操作字段名',
  `field_chinese_desp` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '中文含义',
  `field_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '数据库字段类型',
  `field_remark` varchar(1023) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `ordernumber` int(11) NULL DEFAULT NULL COMMENT '排序',
  `tenant_id` int(11) NOT NULL DEFAULT 0 COMMENT '租户ID',
  `add_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `add_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '最后修改人',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '数据_视图_字段中文描述' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_table_field_desp
-- ----------------------------
INSERT INTO `sys_table_field_desp` VALUES (1, 1, 'a', 'id', 'b', '整型', '', 1, 0, '2020-09-03 06:07:46', 'AdminSys', '2020-09-03 06:14:14', 'AdminSys', 0);
INSERT INTO `sys_table_field_desp` VALUES (2, 1, 'a', 'col1', 'c', '字符串', '', 2, 0, '2020-09-03 06:07:46', 'AdminSys', '2020-09-03 06:13:58', 'AdminSys', 1);
INSERT INTO `sys_table_field_desp` VALUES (3, 1, 'a', 'col2', 'd', '字符串', '', 3, 0, '2020-09-03 06:07:46', 'AdminSys', '2020-09-03 06:13:58', 'AdminSys', 1);
INSERT INTO `sys_table_field_desp` VALUES (4, 1, 'a', 'create_date', 'e', '日期型', '', 4, 0, '2020-09-03 06:07:46', 'AdminSys', '2020-09-03 06:14:14', 'AdminSys', 0);
INSERT INTO `sys_table_field_desp` VALUES (5, 1, 'a', 'col1', 'a', '字符串', NULL, NULL, 0, '2020-09-03 06:14:15', 'AdminSys', '2020-09-03 06:14:15', 'AdminSys', 0);
INSERT INTO `sys_table_field_desp` VALUES (6, 1, 'a', 'col2', 'c', '字符串', NULL, NULL, 0, '2020-09-03 06:14:15', 'AdminSys', '2020-09-03 06:14:15', 'AdminSys', 0);

-- ----------------------------
-- Table structure for sys_user_field_priv
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_field_priv`;
CREATE TABLE `sys_user_field_priv`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '管理员ID',
  `username` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '管理员名称',
  `sys_table_desp_id` int(11) NULL DEFAULT NULL COMMENT '表描述ID',
  `table_view_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '表名',
  `table_view_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '表描述',
  `field_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '表字段名',
  `field_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '表字段描述',
  `field_oper` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作符',
  `field_value` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字段取值',
  `tenant_id` int(11) NOT NULL DEFAULT 0 COMMENT '租户ID',
  `add_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `add_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最后修改人',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统管理员表_字段权限' ROW_FORMAT = Dynamic;

-- ----------------------------
-- View structure for view_role_menu
-- ----------------------------
DROP VIEW IF EXISTS `view_role_menu`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `view_role_menu` AS select `b`.`id` AS `id`,`b`.`role_id` AS `role_id`,`b`.`menu_id` AS `menu_id`,`b`.`menu_code` AS `menu_code`,`b`.`add_time` AS `add_time`,`b`.`add_by` AS `add_by`,`b`.`update_time` AS `update_time`,`b`.`update_by` AS `update_by`,`b`.`deleted` AS `deleted`,`c`.`level` AS `level`,`c`.`path` AS `path`,`c`.`component` AS `component`,`c`.`name` AS `name`,`c`.`icon` AS `icon`,`d`.`code` AS `parent_code`,`a`.`name` AS `role_name` from (((`sys_bi_role` `a` join `sys_role_menu` `b` on((`a`.`id` = `b`.`role_id`))) join `sys_bi_menu` `c` on((`b`.`menu_id` = `c`.`id`))) left join `sys_bi_menu` `d` on((`c`.`parent_id` = `d`.`id`))) order by `c`.`level`,`c`.`parent_id`,`c`.`sort`;

-- ----------------------------
-- Procedure structure for proc_deleteMenu
-- ----------------------------
DROP PROCEDURE IF EXISTS `proc_deleteMenu`;
delimiter ;;
CREATE PROCEDURE `proc_deleteMenu`(in rootId int)
BEGIN

    DECLARE sTemp VARCHAR(1000);/*定义一个临时字段来存放所有的类别与子类别*/
    DECLARE sTempChd VARCHAR(1000);/*定义一个临时字段，来得到当前类别的子类别*/
    SET sTemp = '$';
    SET sTempChd =CAST(rootId as CHAR);

    WHILE sTempChd is not null DO
        SET sTemp = CONCAT(sTemp,',',sTempChd);/*将以前类别与现在查询类别进行合并*/
        /*将每次查到的子id形成一个字符组，放到sTempChd里，如果sTempChd为null就停止循环*/
        SELECT GROUP_CONCAT(id) INTO sTempChd FROM sys_bi_menu where FIND_IN_SET(parent_id,sTempChd)>0;
    END WHILE;
    DELETE FROM sys_bi_menu WHERE FIND_IN_SET(id,sTemp);/*删除本菜单与所有子菜单*/

END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for proc_exec
-- ----------------------------
DROP PROCEDURE IF EXISTS `proc_exec`;
delimiter ;;
CREATE PROCEDURE `proc_exec`(IN sqlS varchar(16383))
BEGIN
	 set @sql = sqlS;
	 PREPARE stmt FROM @sql;         -- 预处理动态sql语句
	 EXECUTE stmt;
	 commit;

	 -- 执行sql语句
	 deallocate prepare stmt;      -- 释放prepare

END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for proc_insert
-- ----------------------------
DROP PROCEDURE IF EXISTS `proc_insert`;
delimiter ;;
CREATE PROCEDURE `proc_insert`(IN sqlS varchar(16383),
	OUT id INT)
BEGIN
	 set @sql = sqlS;
	 PREPARE stmt FROM @sql;         -- 预处理动态sql语句
	 EXECUTE stmt ;                        -- 执行sql语句
	 deallocate prepare stmt;      -- 释放prepare
   SELECT @@identity INTO id;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for proc_paging
-- ----------------------------
DROP PROCEDURE IF EXISTS `proc_paging`;
delimiter ;;
CREATE PROCEDURE `proc_paging`(IN sqlS varchar(16383),
	OUT total INT)
BEGIN
	 set @sql = sqlS;
	 PREPARE stmt FROM @sql;         -- 预处理动态sql语句
	 EXECUTE stmt ;                        -- 执行sql语句
	 deallocate prepare stmt;      -- 释放prepare
	 SELECT FOUND_ROWS() INTO total ;
-- 	  call proc_paging('
--  select sql_calc_found_rows a.*,flowData.flow_instance_id as 流程实例ID,flowData.flow_define_id as 流程定义ID,flowData.flow_model_key as 流程定义Key,flowData.flow_start_name as 流程名称  from ( select book_year as 年份,book_series as 系列,book_grade as 年级,book_kemu as 科目,book_version as 版本,book_cexu as 册序,id as 主键,book_pub_zebian as 社责编,book_if_xiushu as 修改书标识,book_kejian_corp as 课件分公司,book_if_new_feineirong as 新非内容,book_shenchanfangshi as 生产方式,book_publisher as 出版社,id from n_book_info where  deleted=0  limit 0,999999) a left join view_flow_start_main flowData on   a.id=flowData.main_id and flowData.olt_id=8',@total)
END
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
