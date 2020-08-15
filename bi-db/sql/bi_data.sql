
LOCK TABLES `sys_bi_user` WRITE;
/*!40000 ALTER TABLE `bi_user` DISABLE KEYS */;
INSERT INTO `sys_bi_user`(id,username,password,last_login_ip,last_login_time,avatar,email,telphone,
role_ids,mobile_role_ids,dept_id,dept_id_string,dept_name,data_dept_ids,data_dept_string,stop_flag,
tenant_id,add_time,add_by,update_time,update_by,deleted)
VALUES (1,'admin123','$2a$10$.rEfyBb/GURD9P2p0fRg/OAJGloGNDkJS4lY0cQHeqDgsbdTylBpu',
NULL,NULL,'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif',null,null,'[1]',null,null,
null,null,null,null,0,0,
'2018-02-01 00:00:00','XiTong','2018-02-01 00:00:00','XiTong',0);
/*!40000 ALTER TABLE `bi_user` ENABLE KEYS */;
UNLOCK TABLES;



LOCK TABLES `sys_bi_role` WRITE;
/*!40000 ALTER TABLE `litemall_role` DISABLE KEYS */;
INSERT INTO `sys_bi_role` VALUES (1,'超级管理员','所有模块的权限',1,
'2019-01-01 00:00:00','XiTong','2019-01-01 00:00:00','XiTong',0);
/*!40000 ALTER TABLE `litemall_role` ENABLE KEYS */;
UNLOCK TABLES;


