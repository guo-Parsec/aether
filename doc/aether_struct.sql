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
-- Table structure for table `sys_dict`
--

DROP TABLE IF EXISTS `sys_dict`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_dict` (
  `id` bigint NOT NULL COMMENT '主键',
  `dict_type_code` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '字典类别码值',
  `dict_type_name` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '字典类别名称',
  `dict_name` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '字典名称',
  `dict_code` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '字典码值',
  `dict_sort` int NOT NULL DEFAULT '10' COMMENT '字典排序',
  `delete_at` bigint NOT NULL DEFAULT '0' COMMENT '数据删除时间(未删除时为0)',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modify` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sys_dict_dict_type_code_dict_code_delete_at_uindex` (`dict_type_code`,`dict_code`,`delete_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统字典表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_menu`
--

DROP TABLE IF EXISTS `sys_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_menu` (
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
  `delete_at` bigint NOT NULL DEFAULT '0' COMMENT '数据删除时间(未删除时为0)',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modify` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sys_menu_menu_code_delete_at_uindex` (`menu_code`,`delete_at`),
  KEY `sys_menu_parent_id_menu_level_index` (`parent_id`,`menu_level`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统菜单表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_param`
--

DROP TABLE IF EXISTS `sys_param`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_param` (
  `id` bigint NOT NULL COMMENT '主键',
  `param_type_code` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '参数类别码值',
  `param_type_name` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '参数类别名称',
  `param_name` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '参数名称',
  `param_code` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '参数码值',
  `param_value` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '参数值',
  `param_sort` int NOT NULL DEFAULT '10' COMMENT '参数排序',
  `delete_at` bigint NOT NULL DEFAULT '0' COMMENT '数据删除时间(未删除时为0)',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modify` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sys_param_param_code_delete_at_uindex` (`param_code`,`delete_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统参数表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_resource`
--

DROP TABLE IF EXISTS `sys_resource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_resource` (
  `id` bigint NOT NULL COMMENT '主键',
  `resource_type_code` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '资源类别码值',
  `resource_type_name` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '资源类别名称',
  `resource_code` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '权限码值',
  `resource_name` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '权限名称',
  `resource_url` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '资源路径',
  `resource_sort` int NOT NULL DEFAULT '10' COMMENT '资源排序',
  `resource_desc` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '资源描述',
  `delete_at` bigint NOT NULL DEFAULT '0' COMMENT '数据删除时间(未删除时为0)',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modify` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sys_resource_resource_code_resource_url_delete_at_uindex` (`resource_code`,`resource_url`,`delete_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统资源表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role` (
  `id` bigint NOT NULL COMMENT '主键',
  `role_code` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '角色编码',
  `role_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '角色名称',
  `role_sort` int NOT NULL DEFAULT '10' COMMENT '角色排序',
  `role_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '角色描述信息',
  `delete_at` bigint NOT NULL DEFAULT '0' COMMENT '数据删除时间(未删除时为0)',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modify` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `sys_role_role_code_delete_at_uindex` (`role_code`,`delete_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='系统角色表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_role_menu`
--

DROP TABLE IF EXISTS `sys_role_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role_menu` (
  `role_id` bigint NOT NULL COMMENT '角色id',
  `menu_id` bigint NOT NULL COMMENT '菜单id',
  PRIMARY KEY (`role_id`,`menu_id`),
  KEY `sys_role_menu_sys_menu_id_fk` (`menu_id`),
  CONSTRAINT `sys_role_menu_sys_menu_id_fk` FOREIGN KEY (`menu_id`) REFERENCES `sys_menu` (`id`),
  CONSTRAINT `sys_role_menu_sys_role_id_fk` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色菜单关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_role_resource`
--

DROP TABLE IF EXISTS `sys_role_resource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role_resource` (
  `role_id` bigint NOT NULL COMMENT '角色id',
  `resource_id` bigint NOT NULL COMMENT '资源id',
  PRIMARY KEY (`role_id`,`resource_id`),
  KEY `sys_role_resource_sys_resource_id_fk` (`resource_id`),
  CONSTRAINT `sys_role_resource_sys_resource_id_fk` FOREIGN KEY (`resource_id`) REFERENCES `sys_resource` (`id`),
  CONSTRAINT `sys_role_resource_sys_role_id_fk` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色资源关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user` (
  `id` bigint NOT NULL COMMENT '主键',
  `account` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '登录账户',
  `password` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '登陆密码',
  `nickname` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '昵称',
  `sex` int NOT NULL DEFAULT '0' COMMENT '用户性别',
  `avatar_url` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '头像地址',
  `birthday` date NOT NULL COMMENT '出生日期',
  `user_type` int NOT NULL DEFAULT '0' COMMENT '用户类型',
  `enable_status` int NOT NULL DEFAULT '0' COMMENT '启用状态',
  `delete_at` bigint NOT NULL DEFAULT '0' COMMENT '数据删除时间(未删除时为0)',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '数据创建时间',
  `gmt_modify` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '数据修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sys_user_account_delete_at_uindex` (`account`,`delete_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_user_role`
--

DROP TABLE IF EXISTS `sys_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user_role` (
  `user_id` bigint NOT NULL COMMENT '用户id',
  `role_id` bigint NOT NULL COMMENT '角色id',
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `sys_user_role_sys_role_id_fk` (`role_id`),
  CONSTRAINT `sys_user_role_sys_role_id_fk` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`),
  CONSTRAINT `sys_user_role_sys_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户角色关联表';
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-01-12  9:33:32
