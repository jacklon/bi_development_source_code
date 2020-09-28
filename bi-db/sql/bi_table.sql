--
-- Table structure for table `b_dic_main`
--
DROP TABLE IF EXISTS `b_dic_main`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
/*==============================================================*/
/* Table: litemall_dic_main                                    */
/*==============================================================*/
create table  `b_dic_main`
(
   id  int(11) not null auto_increment COMMENT '主键',
   name varchar(255) not null  DEFAULT '' COMMENT '字典列表名称',
   desp varchar(255) DEFAULT '' COMMENT '字典表描述',
   systemed tinyint(1) default 0 COMMENT '系统标记 0-正常 1-系统',
   enabled tinyint(1) default 0 COMMENT '停用标记 0-正常 1-停用',
   ordernumber int(11) default NULL COMMENT '排序',
   tenant_id  int(11) not null default 0 COMMENT '租户ID',
  `add_time` datetime DEFAULT NULL COMMENT '创建时间',
  `add_by` varchar(255) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(255) DEFAULT NULL COMMENT '最后修改人',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除',
   primary key (id),
   KEY `enabled` (`enabled`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='基础数据_字典_主表';

--
-- Table structure for table `bl_dic_code`
--
DROP TABLE IF EXISTS `b_dic_code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
/*==============================================================*/
/* Table: bl_dic_code                                    */
/*==============================================================*/
create table  `b_dic_code`
(
   id  int(11) not null auto_increment COMMENT '主键',
   mainid int(11)   DEFAULT NULL COMMENT '字典主表ID',
   mainname varchar(255)  default '' COMMENT '主表名称',
   code varchar(50)  not null DEFAULT '' COMMENT '代码编码',
   name varchar(255)  not null default '' COMMENT '代码名称',
   ordernumber int(11) default NULL COMMENT '排序',
   remark varchar(255) DEFAULT '' COMMENT '代码备注',
   systemed tinyint(1) default 0 COMMENT '系统标记 0-正常 1-系统',
   enabled tinyint(1) default 0 COMMENT '停用标记 0-正常 1-停用',
   attr1 varchar(255) DEFAULT '' COMMENT '代码属性1',
   attr2 varchar(255) DEFAULT '' COMMENT '代码属性2',
   attr3 varchar(255) DEFAULT '' COMMENT '代码属性3',
   attr4 varchar(255) DEFAULT '' COMMENT '代码属性4',
   attr5 varchar(255) DEFAULT '' COMMENT '代码属性5',
   attr6 varchar(255) DEFAULT '' COMMENT '代码属性6',
   attr7 varchar(255) DEFAULT '' COMMENT '代码属性7',
   attr8 varchar(255) DEFAULT '' COMMENT '代码属性8',
   attr9 varchar(255) DEFAULT '' COMMENT '代码属性9',
   tenant_id  int(11) not null default 0 COMMENT '租户ID',
  `add_time` datetime DEFAULT NULL COMMENT '创建时间',
  `add_by` varchar(255) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(255) DEFAULT NULL COMMENT '最后修改人',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除',
   primary key (id),
   KEY `enabled` (`enabled`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='基础数据_字典_代码表';


--
-- Table structure for table `b_dic_tree_main`
--
DROP TABLE IF EXISTS `b_dic_tree_main`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
/*==============================================================*/
/* Table: b_dic_tree_main                                    */
/*==============================================================*/
create table  `b_dic_tree_main`
(
   id  int(11) not null auto_increment COMMENT '主键',
   name varchar(255) not null  DEFAULT '' COMMENT '字典列表名称',
   desp varchar(255) DEFAULT '' COMMENT '字典表描述',
   systemed tinyint(1) default 0 COMMENT '系统标记 0-正常 1-系统',
   enabled tinyint(1) default 0 COMMENT '停用标记 0-正常 1-停用',
   ordernumber int(11) default NULL COMMENT '排序',
   tenant_id  int(11) not null default 0 COMMENT '租户ID',
  `add_time` datetime DEFAULT NULL COMMENT '创建时间',
  `add_by` varchar(255) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(255) DEFAULT NULL COMMENT '最后修改人',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除',
   primary key (id),
   KEY `enabled` (`enabled`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='基础数据_树形字典_主表';

--
-- Table structure for table `b_dic_tree_code`
--
DROP TABLE IF EXISTS `b_dic_tree_code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
/*==============================================================*/
/* Table: b_dic_tree_code                                    */
/*==============================================================*/
create table  `b_dic_tree_code`
(
   id  int(11) not null auto_increment COMMENT '主键',
   main_id int(11)   DEFAULT NULL COMMENT '字典主表ID',
   main_name varchar(255)  default '' COMMENT '主表名称',
   code varchar(50)  not null DEFAULT '' COMMENT '代码编码',
   name varchar(255)  not null default '' COMMENT '代码名称',
   ordernumber int(11) default NULL COMMENT '排序',
   remark varchar(255) DEFAULT '' COMMENT '代码备注',
   systemed tinyint(1) default 0 COMMENT '系统标记 0-正常 1-系统',
   enabled tinyint(1) default 0 COMMENT '停用标记 0-正常 1-停用',
   attr1 varchar(255) DEFAULT '' COMMENT '代码属性1',
   attr2 varchar(255) DEFAULT '' COMMENT '代码属性2',
   attr3 varchar(255) DEFAULT '' COMMENT '代码属性3',
   attr4 varchar(255) DEFAULT '' COMMENT '代码属性4',
   attr5 varchar(255) DEFAULT '' COMMENT '代码属性5',
   attr6 varchar(255) DEFAULT '' COMMENT '代码属性6',
   attr7 varchar(255) DEFAULT '' COMMENT '代码属性7',
   attr8 varchar(255) DEFAULT '' COMMENT '代码属性8',
   attr9 varchar(255) DEFAULT '' COMMENT '代码属性9',
   attr10 varchar(255) DEFAULT '' COMMENT '代码属性10',
   level int(11) DEFAULT NULL COMMENT '节点所在层级',
   parent_id int(11) DEFAULT '0' COMMENT '上一级节点ID',
   if_leaf tinyint(1) DEFAULT '0' COMMENT '是否叶子节点',
   if_analyze tinyint(1) DEFAULT '1' COMMENT '是否参与分析 0-否 1-是',
   tenant_id  int(11) not null default 0 COMMENT '租户ID',
  `add_time` datetime DEFAULT NULL COMMENT '创建时间',
  `add_by` varchar(255) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(255) DEFAULT NULL COMMENT '最后修改人',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除',
   primary key (id)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='基础数据_树形字典_代码';

--
-- Table structure for table `b_corp_info`
--
DROP TABLE IF EXISTS `b_corp_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
/*==============================================================*/
/* Table: b_corp_info                                    */
/*==============================================================*/
create table  `b_corp_info`
(
   id  int(11) not null auto_increment COMMENT '主键',
   name varchar(255) DEFAULT '' COMMENT '组织名称',
   `desc` text DEFAULT NULL COMMENT '组织简介',
   cit_id  int(11) DEFAULT NULL COMMENT '组织类型ID',
   cit_name varchar(255) DEFAULT '' COMMENT '组织类型名称',
   dep_type_id  int(11) DEFAULT NULL COMMENT '部门属性ID',
   dep_type_name varchar(255) DEFAULT '' COMMENT '部门属性名称',
   charge_person varchar(255) DEFAULT '' COMMENT '负责人',
   cw_code varchar(255) DEFAULT '' COMMENT '财务核算代码',
   pid int(11) not null default 0 COMMENT '上一级组织Id',
   dept_id_string varchar(1000) DEFAULT null COMMENT '所属部门字符串',
   level  int(11) not null default 1  COMMENT '当前层级',
   enabled tinyint(1) default 0 COMMENT '是否启用',
   ordernumber int(11) default 0 COMMENT '排序',
   tenant_id  int(11) not null default 0 COMMENT '租户ID',
  `add_time` datetime DEFAULT NULL COMMENT '创建时间',
  `add_by` varchar(255) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(255) DEFAULT NULL COMMENT '最后修改人',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除',
   primary key (id),
   KEY `enabled` (`enabled`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='系统管理_公司组织架构';

--
-- Table structure for table `sys_interface_monitor`
--

DROP TABLE IF EXISTS `sys_interface_monitor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_interface_monitor` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `log_ip` varchar(255) DEFAULT '' COMMENT '来源IP',
  `log_source_code` varchar(50) DEFAULT null COMMENT '数据来源类型代码',
  `log_source`  varchar(63)  DEFAULT '' COMMENT '数据来源类型',
  `log_direction_code` varchar(50) DEFAULT null COMMENT '接口调用类型代码',
  `log_direction` varchar(63) DEFAULT '' COMMENT '接口调用类型',
  `log_type_code` varchar(50) DEFAULT  null COMMENT '日志类型代码',
  `log_type` varchar(255) DEFAULT '' COMMENT '日志类型',
  `log_content` varchar(255) DEFAULT  '' COMMENT '同步内容描述',
  `log_state_desc` varchar(255) DEFAULT  '' COMMENT '传输状态描述',
  `log_start_time` datetime DEFAULT null COMMENT '同步起始时间',
  `log_end_time` datetime DEFAULT null COMMENT '同步截止时间',
  `log_sync_type` varchar(63) DEFAULT '' COMMENT '同步方式',
  `log_insert_sum` int(11) DEFAULT null COMMENT '本次插入N条',
  `log_update_sum` int(11) DEFAULT null COMMENT '本次更新N条',
  `log_deal_sum` int(11) DEFAULT null COMMENT '本次传输总数N条',
  `log_right_sum` int(11) DEFAULT null COMMENT '本次传输正确N条',
  `log_error_sum` int(11) DEFAULT null COMMENT '本次传输失败N条',
  `error_date` datetime DEFAULT null COMMENT '错误发生时间',
  `error_id_desc` varchar(255) DEFAULT '' COMMENT '错误主键描述',
  `error_msg` text DEFAULT NULL COMMENT '错误消息',
  `error_flag` tinyint(1) DEFAULT '0' COMMENT '错误标识',
  tenant_id  int(11) not null default 0 COMMENT '租户ID',
  `add_time` datetime DEFAULT NULL COMMENT '创建时间',
  `add_by` varchar(255) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(255) DEFAULT NULL COMMENT '最后修改人',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统管理_数据传输日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_opadmin_def`
--

DROP TABLE IF EXISTS `sys_opadmin_def`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_opadmin_def` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `type_code` varchar(50) DEFAULT NULL COMMENT '消息类型ID',
  `type_name` varchar(255)  DEFAULT NULL COMMENT '消息类型名称',
  `title` varchar(255) DEFAULT NULL COMMENT '消息主题',
  `content` varchar(1023) DEFAULT NULL COMMENT '消息内容',
  `begin_date` datetime  DEFAULT NULL COMMENT '消息生效时间',
  `end_date` datetime  DEFAULT NULL COMMENT '消息失效时间',
  `expire_flag` tinyint(1) DEFAULT '0' COMMENT '消息过期标志',
  `web_hint` tinyint(1) DEFAULT '0' COMMENT '站内提醒',
  `sms_hint` tinyint(1) DEFAULT '0' DEFAULT NULL COMMENT '短信标志',
  `mail_hint` tinyint(1) DEFAULT '0' COMMENT '邮件标志',
  `pop_hint` tinyint(1) DEFAULT '0' COMMENT '弹窗提醒',
  `deal_web_link` varchar(255) DEFAULT NULL COMMENT '处理链接网址',
   tenant_id  int(11) not null default 0 COMMENT '租户ID',
  `add_time` datetime DEFAULT NULL COMMENT '创建时间',
  `add_by` varchar(255) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(255) DEFAULT NULL COMMENT '最后修改人',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统管理_后端消息_定义表';
/*!40101 SET character_set_client = @saved_cs_client */;
--
-- Table structure for table `sys_opadmin_pub`
--

DROP TABLE IF EXISTS `sys_opadmin_pub`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_opadmin_pub` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `info_id` int(11) DEFAULT NULL COMMENT '消息ID',
  `user_id` int(11)  DEFAULT NULL COMMENT '管理员ID',
  `user_name` varchar(255)  DEFAULT '' COMMENT '管理员名称',
  tenant_id  int(11) not null default 0 COMMENT '租户ID',
  `add_time` datetime DEFAULT NULL COMMENT '创建时间',
  `add_by` varchar(255) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(255) DEFAULT NULL COMMENT '最后修改人',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统管理_后端消息_通知用户表';
/*!40101 SET character_set_client = @saved_cs_client */;
--
-- Table structure for table `sys_opadmin_info`
--

DROP TABLE IF EXISTS `sys_opadmin_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_opadmin_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `opadmin_id` int(11) DEFAULT NULL COMMENT '操作用户ID',
  `opadmin_name`  varchar(255) DEFAULT NULL COMMENT '操作用户名称',
  `telephone`  varchar(255) DEFAULT NULL COMMENT '接收手机号',
  `info_id` int(11) DEFAULT NULL COMMENT '定义消息ID',
  `type_code` varchar(50) DEFAULT NULL COMMENT '消息类型编码',
  `type_name` varchar(255)  DEFAULT NULL COMMENT '消息类型名称',
  `source_code` varchar(50) DEFAULT NULL COMMENT '消息来源编码',
  `source_name` varchar(255) DEFAULT NULL COMMENT '消息来源名称',
  `title` varchar(255) DEFAULT NULL COMMENT '消息主题',
  `content` varchar(1023) DEFAULT NULL COMMENT '消息内容',
  `web_hint` tinyint(1) DEFAULT '0' COMMENT '站内提醒',
  `sms_hint` tinyint(1) DEFAULT '0' DEFAULT NULL COMMENT '短信标志',
  `mail_hint` tinyint(1) DEFAULT '0' COMMENT '邮件标志',
  `pop_hint` tinyint(1) DEFAULT '0' COMMENT '弹窗提醒',
  `display_flag` tinyint(1) DEFAULT '0' COMMENT '停显标志',
  `if_viewed` tinyint(1) DEFAULT '0' COMMENT '查看标志',
  `flow_start_id` int(11) DEFAULT NULL COMMENT '流程主表ID',
  `flow_instance_id` varchar(255) DEFAULT NULL COMMENT '流程实例ID',
  `flow_start_name` varchar(255) DEFAULT NULL COMMENT '流程名称',
  `flow_model_key` varchar(255) DEFAULT NULL COMMENT '流程定义Key',
  `flow_start_detail_id` int(11) DEFAULT NULL COMMENT '流程节点表ID',
  `flow_node_id` varchar(255) DEFAULT NULL COMMENT '流程节点ID',
  `flow_node_name` varchar(255) DEFAULT NULL COMMENT '流程节点名称',
  tenant_id  int(11) not null default 0 COMMENT '租户ID',
  `add_time` datetime DEFAULT NULL COMMENT '创建时间',
  `add_by` varchar(255) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(255) DEFAULT NULL COMMENT '最后修改人',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统管理_后端消息表';
/*!40101 SET character_set_client = @saved_cs_client */;
--
-- Table structure for table `litemall_admin`
--

DROP TABLE IF EXISTS `sys_bi_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_bi_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(63) NOT NULL DEFAULT '' COMMENT '管理员名称',
  `password` varchar(63) NOT NULL DEFAULT '' COMMENT '管理员密码',
  `last_login_ip` varchar(63) DEFAULT '' COMMENT '最近一次登录IP地址',
  `last_login_time` datetime DEFAULT NULL COMMENT '最近一次登录时间',
  `avatar` varchar(255) DEFAULT '' COMMENT '头像图片',
  `email` varchar(255) DEFAULT NULL DEFAULT '' COMMENT '邮箱',
  `telphone` varchar(255) DEFAULT NULL DEFAULT '' COMMENT '手机',
  `role_ids` varchar(1023) DEFAULT '[]' COMMENT '角色列表',
  `mobile_role_ids` varchar(1023) DEFAULT '[]' COMMENT '称动端角色列表',
  `dept_id` int(11) DEFAULT NULL COMMENT '所属部门',
  `dept_id_string` varchar(4000) DEFAULT NULL COMMENT '所属部门字符串',
  `dept_name` varchar(255) DEFAULT NULL COMMENT '所属部门名称',
  `stop_flag` tinyint(1) DEFAULT '0' COMMENT '停用标志',
  `data_dept_ids` varchar(4000) DEFAULT '[]' COMMENT '公司数据权限',
  `data_dept_string` varchar(4000) DEFAULT '[]' COMMENT '公司数据权限字符串',
  tenant_id  int(11) not null default 0 COMMENT '租户ID',
  `add_time` datetime DEFAULT NULL COMMENT '创建时间',
  `add_by` varchar(255) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(255) DEFAULT NULL COMMENT '最后修改人',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='系统管理_管理员表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_user_field_priv`
--

DROP TABLE IF EXISTS `sys_user_field_priv`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user_field_priv` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) DEFAULT NULL COMMENT '管理员ID',
  `username` varchar(63)  DEFAULT NULL COMMENT '管理员名称',
  `sys_table_desp_id` int(11) DEFAULT NULL COMMENT '表描述ID',
  `table_view_name` varchar(255)  DEFAULT NULL COMMENT '表名',
  `table_view_desc` varchar(255)  DEFAULT NULL COMMENT '表描述',
  `field_name` varchar(255)  DEFAULT NULL COMMENT '表字段名',
  `field_desc` varchar(255)  DEFAULT NULL COMMENT '表字段描述',
  `field_oper` varchar(500)  DEFAULT NULL COMMENT '操作符',
  `field_value` varchar(500)  DEFAULT NULL COMMENT '字段取值',
   tenant_id  int(11) not null default 0 COMMENT '租户ID',
  `add_time` datetime DEFAULT NULL COMMENT '创建时间',
  `add_by` varchar(255) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(255) DEFAULT NULL COMMENT '最后修改人',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='系统管理员表_字段权限';
/*!40101 SET character_set_client = @saved_cs_client */;
--


--
-- Table structure for table `sys_set`
--

DROP TABLE IF EXISTS `sys_set`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_set` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `key_name` varchar(255) NOT NULL COMMENT '系统配置名',
  `key_value` varchar(255) NOT NULL COMMENT '系统配置值',
  tenant_id  int(11) not null default 0 COMMENT '租户ID',
  `add_time` datetime DEFAULT NULL COMMENT '创建时间',
  `add_by` varchar(255) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(255) DEFAULT NULL COMMENT '最后修改人',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='系统管理_系统配置表';
/*!40101 SET character_set_client = @saved_cs_client */;
--
-- Table structure for table `sys_role_menu`
--

DROP TABLE IF EXISTS `sys_role_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `menu_id` int(11) DEFAULT NULL COMMENT '菜单ID',
  `menu_code` varchar(255) DEFAULT NULL COMMENT '菜单Code',
   tenant_id  int(11) not null default 0 COMMENT '租户ID',
  `add_time` datetime DEFAULT NULL COMMENT '创建时间',
  `add_by` varchar(255) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(255) DEFAULT NULL COMMENT '最后修改人',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COMMENT='系统管理_菜单_角色权限表';
/*!40101 SET character_set_client = @saved_cs_client */;
--
-- Table structure for table `sys_bi_menu`
--

DROP TABLE IF EXISTS `sys_bi_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_bi_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `code` varchar(255) NOT NULL COMMENT '编码',
  `name` varchar(255) DEFAULT NULL COMMENT '分类名称',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `icon` varchar(255) DEFAULT NULL COMMENT '图标',
  `href` varchar(1023) DEFAULT NULL COMMENT '链接路径',
  `path` varchar(1023) DEFAULT NULL COMMENT '链接全路径',
  `level` int(11) DEFAULT NULL COMMENT '当前层级',
  `parent_id` int(11) DEFAULT 0 COMMENT '父ID',
  `remarks` varchar(255) DEFAULT NULL COMMENT '描述备注',
  `component` varchar(500) DEFAULT NULL COMMENT '加载组件',
  `add_time` datetime DEFAULT NULL COMMENT '创建时间',
  `add_by` varchar(255) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(255) DEFAULT NULL COMMENT '最后修改人',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COMMENT='系统管理_菜单表';
/*!40101 SET character_set_client = @saved_cs_client */;



DROP TABLE IF EXISTS `sys_bi_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_bi_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(63) NOT NULL COMMENT '角色名称',
  `desc` varchar(1023) DEFAULT NULL COMMENT '角色描述',
  `enabled` tinyint(1) DEFAULT '1' COMMENT '是否启用',
   tenant_id  int(11) not null default 0 COMMENT '租户ID',
  `add_time` datetime DEFAULT NULL COMMENT '创建时间',
  `add_by` varchar(255) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(255) DEFAULT NULL COMMENT '最后修改人',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COMMENT='系统管理_角色表';
/*!40101 SET character_set_client = @saved_cs_client */;
--
-- Table structure for table `litemall_permission`
--

--
-- Table structure for table `sys_storage`
--

DROP TABLE IF EXISTS `sys_storage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_storage` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `key` varchar(63) NOT NULL COMMENT '文件的唯一索引',
  `name` varchar(255) NOT NULL COMMENT '文件名',
  `type` varchar(255) NOT NULL COMMENT '文件类型',
  `size` int(11) NOT NULL COMMENT '文件大小',
  `url` varchar(255) DEFAULT NULL COMMENT '文件访问链接',
   tenant_id  int(11) not null default 0 COMMENT '租户ID',
  `add_time` datetime DEFAULT NULL COMMENT '创建时间',
  `add_by` varchar(255) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(255) DEFAULT NULL COMMENT '最后修改人',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统管理_文件存储表';
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `sys_log`
--

DROP TABLE IF EXISTS `sys_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `admin` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '管理员',
  `ip` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '管理员地址',
  `type` int(11) DEFAULT NULL COMMENT '操作分类',
  `action` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '操作动作',
  `status` tinyint(1) DEFAULT NULL COMMENT '操作状态',
  `result` varchar(127) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '操作结果，或者成功消息，或者失败消息',
  `comment` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '补充信息',
   tenant_id  int(11) not null default 0 COMMENT '租户ID',
  `add_time` datetime DEFAULT NULL COMMENT '创建时间',
  `add_by` varchar(255) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(255) DEFAULT NULL COMMENT '最后修改人',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统管理_操作日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_table_desp`
--
DROP TABLE IF EXISTS `sys_table_desp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_table_desp` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `db_name` varchar(255) DEFAULT NULL COMMENT '数据库名',
  `db_table_or_view_name` varchar(255) DEFAULT NULL COMMENT 'DB表名或视图名',
  `chinese_desc` varchar(1023)  DEFAULT NULL COMMENT '中文含义',
  `primary_field_name` varchar(4000) DEFAULT NULL COMMENT '主键字段名',
  `ordernumber` int(11) DEFAULT NULL COMMENT '排序',
  `tenant_id`  int(11) not null default 0 COMMENT '租户ID',
  `add_time` datetime DEFAULT NULL COMMENT '创建时间',
  `add_by` varchar(255) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(255) DEFAULT NULL COMMENT '最后修改人',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='数据_视图';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_table_field_desp`
--

DROP TABLE IF EXISTS `sys_table_field_desp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_table_field_desp` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `std_id` int(11) DEFAULT NULL COMMENT '操作表ID',
  `std_chinese_desc` varchar(255) DEFAULT NULL COMMENT '主表描述',
  `field_name` varchar(255) DEFAULT NULL COMMENT '操作字段名',
  `field_chinese_desp` varchar(255) DEFAULT NULL COMMENT '中文含义',
  `field_type` varchar(255)  DEFAULT NULL COMMENT '数据库字段类型',
  `field_remark` varchar(1023) DEFAULT NULL COMMENT '备注',
  `ordernumber` int(11) DEFAULT NULL COMMENT '排序',
  `tenant_id`  int(11) not null default 0 COMMENT '租户ID',
  `add_time` datetime DEFAULT NULL COMMENT '创建时间',
  `add_by` varchar(255) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(255) DEFAULT NULL COMMENT '最后修改人',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='数据_视图_字段中文描述';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_operation_log`
--

DROP TABLE IF EXISTS `sys_operation_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_operation_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `log_type_name` VARCHAR(255) DEFAULT NULL COMMENT '日志类型名称',
  `op_time` datetime DEFAULT NULL COMMENT '操作时间',
  `computer` VARCHAR(255) DEFAULT NULL COMMENT '计算机',
  `ip_address` VARCHAR(255) DEFAULT NULL COMMENT 'IP地址',
  `user_name` VARCHAR(255) DEFAULT NULL COMMENT '操作用户',
  `table_id` int(11) DEFAULT NULL COMMENT '操作表ID',
  `table_name` VARCHAR(255) DEFAULT NULL COMMENT '操作表名',
  `table_desc` VARCHAR(255) DEFAULT NULL COMMENT '操作表描述',
  `data_id` int(11) DEFAULT NULL COMMENT '操作数据主键ID',
  `log_remark` VARCHAR(4000) DEFAULT NULL COMMENT '描述',
  `node_remark` VARCHAR(4000) DEFAULT NULL COMMENT '节点描述',
  `parent_table_id` int(11) DEFAULT NULL COMMENT '父表ID',
  `parent_table_name` VARCHAR(255) DEFAULT NULL COMMENT '父表表名',
  `parent_table_desc` VARCHAR(255) DEFAULT NULL COMMENT '父表描述',
  `parent_data_id` int(11) DEFAULT NULL COMMENT '父表数据主键',
  `flow_start_id` int(11) DEFAULT NULL COMMENT '流程申请ID',
  `flow_instance_id` VARCHAR(255) DEFAULT NULL COMMENT '流程实例ID',
  `task_id` VARCHAR(255) DEFAULT NULL COMMENT '任务ID',
   tenant_id  int(11) not null default 0 COMMENT '租户ID',
  `add_time` datetime DEFAULT NULL COMMENT '创建时间',
  `add_by` varchar(255) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(255) DEFAULT NULL COMMENT '最后修改人',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC  COMMENT='数据操作日志表';
/*!40101 SET character_set_client = @saved_cs_client */;



-- ----------------------------
-- Table structure for t_job
-- ----------------------------
DROP TABLE IF EXISTS `sys_quartz_task_manage`;
CREATE TABLE `sys_quartz_task_manage` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_name` varchar(128) DEFAULT NULL COMMENT '任务名称',
  `job_group` varchar(128) DEFAULT NULL COMMENT '任务组',
  `job_status` varchar(255) DEFAULT NULL COMMENT '任务状态',
  `cron` varchar(64) DEFAULT NULL COMMENT '定时器',
  `class_name` varchar(255) DEFAULT NULL COMMENT '类名称',
  `method_name` varchar(64) DEFAULT NULL COMMENT '方法名称',
  `parameters` varchar(1024) DEFAULT NULL COMMENT '参数列表',
  `description` varchar(255) DEFAULT NULL COMMENT '任务描述',
  `error_log` text DEFAULT NULL COMMENT '错误日志',
  tenant_id  int(11) not null default 0 COMMENT '租户ID',
  `add_time` datetime DEFAULT NULL COMMENT '创建时间',
  `add_by` varchar(255) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(255) DEFAULT NULL COMMENT '最后修改人',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
)  ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='定时任务管理';


-- ----------------------------
-- Table structure for job_jdbc_datasource
-- ----------------------------
DROP TABLE IF EXISTS `bi_data_connection`;
CREATE TABLE `bi_data_connection`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `datasource_name` varchar(200) NOT NULL COMMENT '数据源名称',
  `datasource` varchar(45)  NOT NULL COMMENT '数据源',
  `datasource_group` varchar(200) DEFAULT 'Default' COMMENT '数据源分组',
  `database_name` varchar(45) DEFAULT NULL COMMENT '数据库名',
  `jdbc_username` varchar(100) DEFAULT NULL COMMENT '用户名',
  `jdbc_password` varchar(200) DEFAULT NULL COMMENT '密码',
  `jdbc_url` varchar(500) NOT NULL COMMENT 'jdbc url',
  `jdbc_driver_class` varchar(200) DEFAULT NULL COMMENT 'jdbc驱动类',
  `zk_adress` varchar(200) DEFAULT NULL COMMENT 'zookeeper地址',
  `status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '状态：0删除 1启用 2禁用',
  tenant_id  int(11) not null default 0 COMMENT '租户ID',
  `add_time` datetime DEFAULT NULL COMMENT '创建时间',
  `add_by` varchar(255) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(255) DEFAULT NULL COMMENT '最后修改人',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='jdbc数据源配置';
