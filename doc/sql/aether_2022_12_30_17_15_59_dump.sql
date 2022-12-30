-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: aether
-- ------------------------------------------------------
-- Server version	8.0.31

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `ams_dict`
--

DROP TABLE IF EXISTS `ams_dict`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ams_dict` (
  `id` bigint NOT NULL COMMENT '主键',
  `dict_type_code` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '字典类别码值',
  `dict_type_name` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '字典类别名称',
  `dict_name` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '字典名称',
  `dict_code` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '字典码值',
  `dict_sort` int NOT NULL DEFAULT '10' COMMENT '字典排序',
  `data_status` int NOT NULL DEFAULT '0' COMMENT '数据状态',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modify` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `ams_dict_dict_type_code_dict_code_uindex` (`dict_type_code`,`dict_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统字典表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ams_dict`
--

/*!40000 ALTER TABLE `ams_dict` DISABLE KEYS */;
INSERT INTO `ams_dict` (`id`, `dict_type_code`, `dict_type_name`, `dict_name`, `dict_code`, `dict_sort`, `data_status`, `gmt_create`, `gmt_modify`) VALUES (50247825258450944,'sex','性别值','未知','0',10,0,'2022-12-29 15:46:55','2022-12-29 15:54:32'),(50247872180129792,'sex','性别值','男性','1',20,0,'2022-12-29 15:47:06','2022-12-29 15:54:32'),(50247913401749504,'sex','性别值','女性','2',30,0,'2022-12-29 15:47:16','2022-12-29 15:54:32'),(50249611734159360,'user_type','用户类型','内部用户','0',10,0,'2022-12-29 15:54:01','2022-12-29 15:54:01'),(50249797453746176,'user_type','用户类型','注册用户','1',20,0,'2022-12-29 15:54:45','2022-12-29 15:54:45'),(50576573363130368,'menu_display','菜单显示状态','显示','0',10,0,'2022-12-30 13:33:15','2022-12-30 13:33:15'),(50576948040306688,'menu_display','菜单显示状态','隐藏','1',20,0,'2022-12-30 13:34:44','2022-12-30 13:34:44');
/*!40000 ALTER TABLE `ams_dict` ENABLE KEYS */;

--
-- Table structure for table `ams_hi_login`
--

DROP TABLE IF EXISTS `ams_hi_login`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ams_hi_login` (
  `id` bigint NOT NULL COMMENT '主键',
  `login_ip` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '登录ip',
  `login_account` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '登录账户',
  `login_result` tinyint NOT NULL DEFAULT '0' COMMENT '登录是否成功 0-成功 1-失败',
  `device_name` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '登录设备',
  `time_consuming` bigint NOT NULL DEFAULT '0' COMMENT '耗时时间(ms)',
  `error_code` int DEFAULT NULL COMMENT '错误码',
  `error_message` longtext COLLATE utf8mb4_unicode_ci COMMENT '错误信息',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='登录历史记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ams_hi_login`
--

/*!40000 ALTER TABLE `ams_hi_login` DISABLE KEYS */;
/*!40000 ALTER TABLE `ams_hi_login` ENABLE KEYS */;

--
-- Table structure for table `ams_hi_operate`
--

DROP TABLE IF EXISTS `ams_hi_operate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ams_hi_operate` (
  `id` bigint NOT NULL COMMENT '主键',
  `access_url` text COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '访问路径',
  `access_app` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '访问应用',
  `operate_user_id` bigint NOT NULL COMMENT '操作人id',
  `operate_user_account` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '操作用户账户',
  `operate_ip` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '操作ip',
  `device_name` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '设备名称',
  `time_consuming` bigint NOT NULL DEFAULT '0' COMMENT '耗时时间(ms)',
  `method_type` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'GET' COMMENT '访问类型',
  `access_result` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'SUCCESS' COMMENT '访问结果',
  `error_code` int DEFAULT NULL COMMENT '错误码',
  `error_message` longtext COLLATE utf8mb4_unicode_ci COMMENT '错误信息',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='操作历史记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ams_hi_operate`
--

/*!40000 ALTER TABLE `ams_hi_operate` DISABLE KEYS */;
/*!40000 ALTER TABLE `ams_hi_operate` ENABLE KEYS */;

--
-- Table structure for table `ams_menu`
--

DROP TABLE IF EXISTS `ams_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ams_menu` (
  `id` bigint NOT NULL COMMENT '主键',
  `parent_id` bigint DEFAULT NULL COMMENT '上级菜单id',
  `absolute_path` varchar(225) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '/' COMMENT '层级路径',
  `menu_code` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '菜单码值',
  `menu_title` varchar(76) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '菜单标题',
  `menu_icon` varchar(80) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '菜单图标',
  `menu_level` int NOT NULL DEFAULT '0' COMMENT '菜单级别',
  `menu_sort` int NOT NULL DEFAULT '10' COMMENT '菜单排序',
  `menu_url` varchar(225) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '菜单路径',
  `menu_component` varchar(225) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '前端组件',
  `menu_display` int NOT NULL DEFAULT '0' COMMENT '显示状态',
  `menu_desc` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '菜单描述',
  `data_status` int NOT NULL DEFAULT '0' COMMENT '数据状态',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modify` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `ams_menu_menu_code_uindex` (`menu_code`),
  KEY `ams_menu_parent_id_menu_level_index` (`parent_id`,`menu_level`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统菜单表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ams_menu`
--

/*!40000 ALTER TABLE `ams_menu` DISABLE KEYS */;
/*!40000 ALTER TABLE `ams_menu` ENABLE KEYS */;

--
-- Table structure for table `ams_resource`
--

DROP TABLE IF EXISTS `ams_resource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ams_resource` (
  `id` bigint NOT NULL COMMENT '主键',
  `resource_type_code` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '资源类别码值',
  `resource_type_name` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '资源类别名称',
  `resource_code` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '权限码值',
  `resource_name` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '权限名称',
  `resource_url` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '资源路径',
  `resource_sort` int NOT NULL DEFAULT '10' COMMENT '资源排序',
  `resource_desc` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '资源描述',
  `data_status` int NOT NULL DEFAULT '0' COMMENT '数据状态',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modify` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `ams_resource_resource_type_code_resource_code_uindex` (`resource_type_code`,`resource_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统资源表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ams_resource`
--

/*!40000 ALTER TABLE `ams_resource` DISABLE KEYS */;
/*!40000 ALTER TABLE `ams_resource` ENABLE KEYS */;

--
-- Table structure for table `ams_role`
--

DROP TABLE IF EXISTS `ams_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ams_role` (
  `id` bigint NOT NULL COMMENT '主键',
  `role_code` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '角色编码',
  `role_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '角色名称',
  `role_sort` int NOT NULL DEFAULT '10' COMMENT '角色排序',
  `role_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '角色描述信息',
  `data_status` int NOT NULL DEFAULT '0' COMMENT '数据状态',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modify` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `ams_role_role_code_uindex` (`role_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='系统角色表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ams_role`
--

/*!40000 ALTER TABLE `ams_role` DISABLE KEYS */;
INSERT INTO `ams_role` (`id`, `role_code`, `role_name`, `role_sort`, `role_desc`, `data_status`, `gmt_create`, `gmt_modify`) VALUES (49465151442784256,'admin','超级管理员',10,'超级管理员角色',0,'2022-12-27 11:56:51','2022-12-27 11:56:51');
/*!40000 ALTER TABLE `ams_role` ENABLE KEYS */;

--
-- Table structure for table `ams_role_menu`
--

DROP TABLE IF EXISTS `ams_role_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ams_role_menu` (
  `role_id` bigint NOT NULL COMMENT '角色id',
  `menu_id` bigint NOT NULL COMMENT '菜单id',
  PRIMARY KEY (`role_id`,`menu_id`),
  KEY `ams_role_menu_ams_menu_id_fk` (`menu_id`),
  CONSTRAINT `ams_role_menu_ams_menu_id_fk` FOREIGN KEY (`menu_id`) REFERENCES `ams_menu` (`id`),
  CONSTRAINT `ams_role_menu_ams_role_id_fk` FOREIGN KEY (`role_id`) REFERENCES `ams_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色菜单关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ams_role_menu`
--

/*!40000 ALTER TABLE `ams_role_menu` DISABLE KEYS */;
/*!40000 ALTER TABLE `ams_role_menu` ENABLE KEYS */;

--
-- Table structure for table `ams_role_resource`
--

DROP TABLE IF EXISTS `ams_role_resource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ams_role_resource` (
  `role_id` bigint NOT NULL COMMENT '角色id',
  `resource_id` bigint NOT NULL COMMENT '资源id',
  PRIMARY KEY (`role_id`,`resource_id`),
  KEY `ams_role_resource_ams_resource_id_fk` (`resource_id`),
  CONSTRAINT `ams_role_resource_ams_resource_id_fk` FOREIGN KEY (`resource_id`) REFERENCES `ams_resource` (`id`),
  CONSTRAINT `ams_role_resource_ams_role_id_fk` FOREIGN KEY (`role_id`) REFERENCES `ams_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色资源关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ams_role_resource`
--

/*!40000 ALTER TABLE `ams_role_resource` DISABLE KEYS */;
/*!40000 ALTER TABLE `ams_role_resource` ENABLE KEYS */;

--
-- Table structure for table `ams_user`
--

DROP TABLE IF EXISTS `ams_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ams_user` (
  `id` bigint NOT NULL COMMENT '主键',
  `account` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '登录账户',
  `password` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '登陆密码',
  `nickname` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '昵称',
  `sex` int NOT NULL DEFAULT '0' COMMENT '用户性别',
  `avatar_url` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '头像地址',
  `birthday` date NOT NULL COMMENT '出生日期',
  `user_type` int NOT NULL DEFAULT '0' COMMENT '用户类型',
  `data_status` int NOT NULL DEFAULT '0' COMMENT '数据状态',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '数据创建时间',
  `gmt_modify` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '数据修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `ams_user_account_uindex` (`account`) COMMENT '登陆账户唯一索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ams_user`
--

/*!40000 ALTER TABLE `ams_user` DISABLE KEYS */;
INSERT INTO `ams_user` (`id`, `account`, `password`, `nickname`, `sex`, `avatar_url`, `birthday`, `user_type`, `data_status`, `gmt_create`, `gmt_modify`) VALUES (1,'admin','a66abb5684c45962d887564f08346e8d','admin',0,'','1998-11-18',0,0,'2022-12-14 09:39:07','2022-12-14 09:39:07');
/*!40000 ALTER TABLE `ams_user` ENABLE KEYS */;

--
-- Table structure for table `ams_user_role`
--

DROP TABLE IF EXISTS `ams_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ams_user_role` (
  `user_id` bigint NOT NULL COMMENT '用户id',
  `role_id` bigint NOT NULL COMMENT '角色id',
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `ams_user_role_ams_role_id_fk` (`role_id`),
  CONSTRAINT `ams_user_role_ams_role_id_fk` FOREIGN KEY (`role_id`) REFERENCES `ams_role` (`id`),
  CONSTRAINT `ams_user_role_ams_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `ams_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户角色关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ams_user_role`
--

/*!40000 ALTER TABLE `ams_user_role` DISABLE KEYS */;
/*!40000 ALTER TABLE `ams_user_role` ENABLE KEYS */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-12-30 17:15:59
