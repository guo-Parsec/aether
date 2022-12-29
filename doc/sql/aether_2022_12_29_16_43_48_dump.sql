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
INSERT INTO `ams_dict` (`id`, `dict_type_code`, `dict_type_name`, `dict_name`, `dict_code`, `dict_sort`, `data_status`, `gmt_create`, `gmt_modify`) VALUES (50247825258450944,'sex','性别值','未知','0',10,0,'2022-12-29 15:46:55','2022-12-29 15:54:32'),(50247872180129792,'sex','性别值','男性','1',20,0,'2022-12-29 15:47:06','2022-12-29 15:54:32'),(50247913401749504,'sex','性别值','女性','2',30,0,'2022-12-29 15:47:16','2022-12-29 15:54:32'),(50249611734159360,'user_type','用户类型','内部用户','0',10,0,'2022-12-29 15:54:01','2022-12-29 15:54:01'),(50249797453746176,'user_type','用户类型','注册用户','1',20,0,'2022-12-29 15:54:45','2022-12-29 15:54:45');
/*!40000 ALTER TABLE `ams_dict` ENABLE KEYS */;

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
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-12-29 16:43:48
