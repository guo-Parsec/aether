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
-- Dumping data for table `sys_dict`
--

LOCK TABLES `sys_dict` WRITE;
/*!40000 ALTER TABLE `sys_dict` DISABLE KEYS */;
INSERT INTO `sys_dict` (`id`, `dict_type_code`, `dict_type_name`, `dict_name`, `dict_code`, `dict_sort`, `delete_at`, `gmt_create`, `gmt_modify`) VALUES (50247825258450944,'sex','性别值','未知','0',10,0,'2022-12-29 15:46:55','2022-12-29 15:54:32'),(50247872180129792,'sex','性别值','男性','1',20,0,'2022-12-29 15:47:06','2022-12-29 15:54:32'),(50247913401749504,'sex','性别值','女性','2',30,0,'2022-12-29 15:47:16','2022-12-29 15:54:32'),(50249611734159360,'user_type','用户类型','内部用户','0',10,0,'2022-12-29 15:54:01','2022-12-29 15:54:01'),(50249797453746176,'user_type','用户类型','注册用户','1',20,0,'2022-12-29 15:54:45','2022-12-29 15:54:45'),(50576573363130368,'menu_display','菜单显示状态','显示','0',10,0,'2022-12-30 13:33:15','2022-12-30 13:33:15'),(50576948040306688,'menu_display','菜单显示状态','隐藏','1',20,0,'2022-12-30 13:34:44','2022-12-30 13:34:44'),(51974717992865792,'menu_display','菜单显示状态','测试','3',40,1672728452135,'2023-01-03 10:08:58','2023-01-03 14:47:32'),(55609616649949184,'enable_status','启用状态','启用','0',10,0,'2023-01-13 10:52:46','2023-01-13 10:52:46'),(55609727010476032,'enable_status','启用状态','禁用','1',20,0,'2023-01-13 10:53:12','2023-01-13 10:53:12'),(55610206025158656,'operate_result','操作结果','成功','0',10,0,'2023-01-13 10:55:06','2023-01-13 10:55:06'),(55612206741721088,'operate_result','操作结果','失败','1',10,0,'2023-01-13 11:03:03','2023-01-13 11:03:03');
/*!40000 ALTER TABLE `sys_dict` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_menu`
--

DROP TABLE IF EXISTS `sys_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_menu` (
  `id` bigint NOT NULL COMMENT '主键',
  `parent_id` bigint DEFAULT NULL COMMENT '上级菜单id',
  `absolute_path` text COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '层级路径',
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
-- Dumping data for table `sys_menu`
--

LOCK TABLES `sys_menu` WRITE;
/*!40000 ALTER TABLE `sys_menu` DISABLE KEYS */;
INSERT INTO `sys_menu` (`id`, `parent_id`, `absolute_path`, `menu_code`, `menu_title`, `menu_icon`, `menu_level`, `menu_sort`, `menu_url`, `menu_component`, `menu_display`, `menu_desc`, `delete_at`, `gmt_create`, `gmt_modify`) VALUES (1,NULL,'/','root','根目录','',0,0,'/','',1,'根目录',0,'2023-01-19 09:14:46','2023-01-19 09:14:46'),(57777179060342784,1,'/1','workstation','工作台','',1,10,'/workstation','views/workstation.vue',0,'首页工作台',0,'2023-01-19 10:25:53','2023-01-19 10:35:52'),(57777869245648896,1,'/1','system_management','系统管理','',1,100,'/system/user-management','views/system/UserManagement.vue',0,'系统管理',0,'2023-01-19 10:28:37','2023-01-19 10:35:52'),(57778194971103232,57777869245648896,'/1/57777869245648896','user_management','用户管理','',2,10,'/system/user-management','views/system/UserManagement.vue',0,'用户管理',0,'2023-01-19 10:29:55','2023-01-19 10:35:52'),(57778575037960192,57777869245648896,'/1/57777869245648896','role_management','角色管理','',2,20,'/system/role-management','views/system/RoleManagement.vue',0,'角色管理',0,'2023-01-19 10:31:26','2023-01-19 10:35:52'),(57778701492031488,57777869245648896,'/1/57777869245648896','resource_management','资源管理','',2,30,'/system/resource-management','views/system/ResourceManagement.vue',0,'资源管理',0,'2023-01-19 10:31:56','2023-01-19 10:35:52'),(57778800163033088,57777869245648896,'/1/57777869245648896','menu_management','菜单管理','',2,40,'/system/menu-management','views/system/MenuManagement.vue',0,'菜单管理',0,'2023-01-19 10:32:19','2023-01-19 10:35:52'),(57778936230449152,57777869245648896,'/1/57777869245648896','dict_management','字典管理','',2,50,'/system/dict-management','views/system/DictManagement.vue',0,'字典管理',0,'2023-01-19 10:32:52','2023-01-19 10:35:52'),(57779196390543360,57777869245648896,'/1/57777869245648896','operate_record_management','操作记录','',2,60,'/system/operate-record-management','views/system/OperateRecordManagement.vue',0,'操作记录',0,'2023-01-19 10:33:54','2023-01-19 11:37:01');
/*!40000 ALTER TABLE `sys_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_operate_record`
--

DROP TABLE IF EXISTS `sys_operate_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_operate_record` (
  `id` bigint NOT NULL COMMENT '主键',
  `operate_id` bigint DEFAULT NULL COMMENT '操作人id',
  `operate_account` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '操作人账户',
  `operate_result` int NOT NULL DEFAULT '0' COMMENT '操作结果',
  `operate_code` int NOT NULL DEFAULT '200' COMMENT '操作结果码',
  `error_reason` longtext COLLATE utf8mb4_unicode_ci COMMENT '错误原因',
  `operate_ip` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '操作ip地址',
  `operate_time` datetime NOT NULL COMMENT '操作时间',
  `time_spent` bigint NOT NULL COMMENT '操作耗时(毫秒)',
  `operate_uri` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '操作uri',
  `operate_method` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '0' COMMENT '操作类型',
  PRIMARY KEY (`id`),
  KEY `sys_operate_record_sys_user_id_fk` (`operate_id`),
  KEY `sys_operate_record_sys_user_account_fk` (`operate_account`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统操作记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_operate_record`
--

LOCK TABLES `sys_operate_record` WRITE;
/*!40000 ALTER TABLE `sys_operate_record` DISABLE KEYS */;
INSERT INTO `sys_operate_record` (`id`, `operate_id`, `operate_account`, `operate_result`, `operate_code`, `error_reason`, `operate_ip`, `operate_time`, `time_spent`, `operate_uri`, `operate_method`) VALUES (57772349365424128,52793893988864000,'admin',1,400,'上级菜单[parentId=1]不存在，新增失败','127.0.0.1','2023-01-19 10:06:41',154,'/system/menu/create.do','POST'),(57772525480054784,52793893988864000,'admin',1,400,'上级菜单[parentId=0]不存在，新增失败','127.0.0.1','2023-01-19 10:07:23',7,'/system/menu/create.do','POST'),(57772646699634688,52793893988864000,'admin',1,400,'上级菜单[parentId=1]不存在，新增失败','127.0.0.1','2023-01-19 10:07:52',10,'/system/menu/create.do','POST'),(57774113212862464,52793893988864000,'admin',1,500,'Cache \'system:dict:optional:holder:single\' does not allow \'null\' values. Avoid storing null via \'@Cacheable(unless=\"#result == null\")\' or configure RedisCache to allow \'null\' via RedisCacheConfiguration.','127.0.0.1','2023-01-19 10:13:42',353,'/system/menu/create.do','POST'),(57774849749422080,52793893988864000,'admin',1,500,'Cache \'system:dict:optional:holder:single\' does not allow \'null\' values. Avoid storing null via \'@Cacheable(unless=\"#result == null\")\' or configure RedisCache to allow \'null\' via RedisCacheConfiguration.','127.0.0.1','2023-01-19 10:15:27',70859,'/system/menu/create.do','POST'),(57775239517704192,52793893988864000,'admin',1,500,'EL1011E: Method call: Attempted to call method isPresent() on null context object','127.0.0.1','2023-01-19 10:18:10',185,'/system/menu/create.do','POST'),(57776745524498432,52793893988864000,'admin',1,500,'Cache \'system:dict:optional:holder:single\' does not allow \'null\' values. Avoid storing null via \'@Cacheable(unless=\"#result == null\")\' or configure RedisCache to allow \'null\' via RedisCacheConfiguration.','127.0.0.1','2023-01-19 10:24:09',225,'/system/menu/create.do','POST'),(57777119014686720,52793893988864000,'admin',1,500,'字段[fieldName=menuDisplay,dictTypeCode=menu_display,dictCode=33]找不到对应的字典值校验失败','127.0.0.1','2023-01-19 10:25:38',273,'/system/menu/create.do','POST'),(57777179140034560,52793893988864000,'admin',0,200,'-','127.0.0.1','2023-01-19 10:25:53',73,'/system/menu/create.do','POST'),(57777366885470208,52793893988864000,'admin',1,400,'菜单码值为[menuCode=workstation]的数据已存在，不能重复新增','127.0.0.1','2023-01-19 10:26:38',9,'/system/menu/create.do','POST'),(57777869337923584,52793893988864000,'admin',0,200,'-','127.0.0.1','2023-01-19 10:28:37',56,'/system/menu/create.do','POST'),(57778195029823488,52793893988864000,'admin',0,200,'-','127.0.0.1','2023-01-19 10:29:55',24,'/system/menu/create.do','POST'),(57778575117651968,52793893988864000,'admin',0,200,'-','127.0.0.1','2023-01-19 10:31:26',25,'/system/menu/create.do','POST'),(57778701546557440,52793893988864000,'admin',0,200,'-','127.0.0.1','2023-01-19 10:31:56',23,'/system/menu/create.do','POST'),(57778800188198912,52793893988864000,'admin',0,200,'-','127.0.0.1','2023-01-19 10:32:19',18,'/system/menu/create.do','POST'),(57778936280780800,52793893988864000,'admin',0,200,'-','127.0.0.1','2023-01-19 10:32:52',36,'/system/menu/create.do','POST'),(57779196436680704,52793893988864000,'admin',0,200,'-','127.0.0.1','2023-01-19 10:33:54',22,'/system/menu/create.do','POST'),(57783826524672000,52793893988864000,'admin',0,200,'-','127.0.0.1','2023-01-19 10:52:18',177,'/system/resource/create.do','POST'),(57784096516214784,52793893988864000,'admin',0,200,'-','127.0.0.1','2023-01-19 10:53:22',26,'/system/resource/create.do','POST'),(57784668568948736,52793893988864000,'admin',0,200,'-','127.0.0.1','2023-01-19 10:55:39',17,'/system/resource/create.do','POST'),(57784709505355776,52793893988864000,'admin',1,400,'资源编码为[resourceCode=system:user:write]的数据已存在，不能重复新增','127.0.0.1','2023-01-19 10:55:48',15,'/system/resource/create.do','POST'),(57786122847391744,52793893988864000,'admin',0,200,'-','127.0.0.1','2023-01-19 11:01:25',170,'/system/resource/create.do','POST'),(57786737451339776,52793893988864000,'admin',0,200,'-','127.0.0.1','2023-01-19 11:03:52',24,'/system/resource/create.do','POST'),(57787149826920448,52793893988864000,'admin',0,200,'-','127.0.0.1','2023-01-19 11:05:30',21,'/system/resource/create.do','POST'),(57787215216119808,52793893988864000,'admin',0,200,'-','127.0.0.1','2023-01-19 11:05:46',26,'/system/resource/create.do','POST'),(57787502324617216,52793893988864000,'admin',0,200,'-','127.0.0.1','2023-01-19 11:06:54',14,'/system/resource/create.do','POST'),(57787860790808576,52793893988864000,'admin',0,200,'-','127.0.0.1','2023-01-19 11:08:20',26,'/system/resource/create.do','POST'),(57787968211128320,52793893988864000,'admin',0,200,'-','127.0.0.1','2023-01-19 11:08:45',20,'/system/resource/create.do','POST'),(57788033994592256,52793893988864000,'admin',0,200,'-','127.0.0.1','2023-01-19 11:09:01',13,'/system/resource/create.do','POST'),(57788270565920768,52793893988864000,'admin',0,200,'-','127.0.0.1','2023-01-19 11:09:57',29,'/system/resource/create.do','POST'),(57788416389287936,52793893988864000,'admin',0,200,'-','127.0.0.1','2023-01-19 11:10:32',14,'/system/resource/create.do','POST'),(57788803003453440,52793893988864000,'admin',0,200,'-','127.0.0.1','2023-01-19 11:12:04',15,'/system/resource/create.do','POST'),(57788862612901888,52793893988864000,'admin',0,200,'-','127.0.0.1','2023-01-19 11:12:18',12,'/system/resource/create.do','POST'),(57789068255432704,52793893988864000,'admin',0,200,'-','127.0.0.1','2023-01-19 11:13:08',16,'/system/resource/create.do','POST'),(57789114225004544,52793893988864000,'admin',0,200,'-','127.0.0.1','2023-01-19 11:13:18',13,'/system/resource/create.do','POST'),(57789297939714048,52793893988864000,'admin',0,200,'-','127.0.0.1','2023-01-19 11:14:02',11,'/system/resource/create.do','POST'),(57789390952599552,52793893988864000,'admin',0,200,'-','127.0.0.1','2023-01-19 11:14:24',18,'/system/resource/create.do','POST'),(57789461471432704,52793893988864000,'admin',0,200,'-','127.0.0.1','2023-01-19 11:14:41',12,'/system/resource/create.do','POST'),(57789579205545984,52793893988864000,'admin',0,200,'-','127.0.0.1','2023-01-19 11:15:09',16,'/system/resource/create.do','POST'),(57789638370398208,52793893988864000,'admin',0,200,'-','127.0.0.1','2023-01-19 11:15:23',23,'/system/resource/create.do','POST'),(57789760181374976,52793893988864000,'admin',0,200,'-','127.0.0.1','2023-01-19 11:15:52',73,'/system/resource/create.do','POST'),(57789852099547136,52793893988864000,'admin',1,400,'资源编码为[resourceCode=system:param:read]以及资源路径为[resourceUrl=/sysetm/param/page/query]的数据已存在，不能重复新增','127.0.0.1','2023-01-19 11:16:14',15,'/system/resource/create.do','POST'),(57790077270757376,52793893988864000,'admin',1,400,'资源编码为[resourceCode=system:param:read]以及资源路径为[resourceUrl=/sysetm/param/list]的数据已存在，不能重复新增','127.0.0.1','2023-01-19 11:17:08',5,'/system/resource/create.do','POST'),(57790109671755776,52793893988864000,'admin',0,200,'-','127.0.0.1','2023-01-19 11:17:16',13,'/system/resource/create.do','POST'),(57790269403435008,52793893988864000,'admin',0,200,'-','127.0.0.1','2023-01-19 11:17:54',14,'/system/resource/create.do','POST'),(57790408637550592,52793893988864000,'admin',0,200,'-','127.0.0.1','2023-01-19 11:18:27',12,'/system/resource/create.do','POST'),(57792688581513216,52793893988864000,'admin',0,200,'-','127.0.0.1','2023-01-19 11:27:31',123,'/system/role/grant-resource.do','POST'),(57792873588068352,52793893988864000,'admin',0,200,'-','127.0.0.1','2023-01-19 11:28:15',84,'/system/user/grant-role.do','PUT');
/*!40000 ALTER TABLE `sys_operate_record` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `sys_param`
--

LOCK TABLES `sys_param` WRITE;
/*!40000 ALTER TABLE `sys_param` DISABLE KEYS */;
INSERT INTO `sys_param` (`id`, `param_type_code`, `param_type_name`, `param_name`, `param_code`, `param_value`, `param_sort`, `delete_at`, `gmt_create`, `gmt_modify`) VALUES (54215521759137792,NULL,NULL,'默认初始密码','DEFAULT_PASSWORD','abc123456',10,0,'2023-01-09 14:33:08','2023-01-09 14:33:08'),(54215782363828224,NULL,NULL,'默认令牌过期时间(小时)','DEFAULT_TOKEN_EXPIRE_TIME','6',20,0,'2023-01-09 14:34:10','2023-01-09 14:34:10'),(54227247938408448,NULL,NULL,'默认用户密码加解密策略','DEFAULT_USER_PASSWORD_CRYPTO_STRATEGY','md5SaltCrypto',30,0,'2023-01-09 15:19:43','2023-01-09 15:19:43');
/*!40000 ALTER TABLE `sys_param` ENABLE KEYS */;
UNLOCK TABLES;

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
  `resource_desc` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '资源描述',
  `delete_at` bigint NOT NULL DEFAULT '0' COMMENT '数据删除时间(未删除时为0)',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modify` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sys_resource_resource_code_resource_url_delete_at_uindex` (`resource_code`,`resource_url`,`delete_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统资源表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_resource`
--

LOCK TABLES `sys_resource` WRITE;
/*!40000 ALTER TABLE `sys_resource` DISABLE KEYS */;
INSERT INTO `sys_resource` (`id`, `resource_type_code`, `resource_type_name`, `resource_code`, `resource_name`, `resource_url`, `resource_desc`, `delete_at`, `gmt_create`, `gmt_modify`) VALUES (57783826176544768,'auth','认证权限可访问资源','system:*:*','系统管理','/system/**','系统管理全资源',0,'2023-01-19 10:52:18','2023-01-19 11:19:47'),(57784096453300224,'auth','认证权限可访问资源','system:user:*','用户管理','/system/user/**','用户管理全资源',0,'2023-01-19 10:53:22','2023-01-19 11:19:47'),(57787502270091264,'auth','认证权限可访问资源','system:user:write','用户写入资源','/system/user/*.do','针对用户信息的新增、删除、修改等写入操作',0,'2023-01-19 11:06:54','2023-01-19 11:19:47'),(57787860736282624,'auth','认证权限可访问资源','system:user:read','用户读取资源','/system/user/page/query','针对用户信息的查询、分页查询等读取操作',0,'2023-01-19 11:08:20','2023-01-19 11:19:47'),(57787968123047936,'auth','认证权限可访问资源','system:role:read','角色读取资源','/system/role/page/query','针对角色信息的分页查询等读取操作',0,'2023-01-19 11:08:45','2023-01-19 11:19:47'),(57788033948454912,'auth','认证权限可访问资源','system:role:read','角色读取资源','/system/role/list','针对角色信息的查询等读取操作',0,'2023-01-19 11:09:01','2023-01-19 11:19:47'),(57788270498811904,'auth','认证权限可访问资源','system:role:write','角色写入资源','/system/role/*.do','针对角色信息的新增、修改、和删除等写入操作',0,'2023-01-19 11:09:57','2023-01-19 11:19:47'),(57788416338956288,'auth','认证权限可访问资源','system:resource:write','资源写入资源','/system/resource/*.do','针对资源信息的新增、修改、和删除等写入操作',0,'2023-01-19 11:10:32','2023-01-19 11:19:47'),(57788802969899008,'auth','认证权限可访问资源','system:resource:read','资源读取资源','/system/resource/list','针对资源信息的查询等读取操作',0,'2023-01-19 11:12:04','2023-01-19 11:19:47'),(57788862570958848,'auth','认证权限可访问资源','system:resource:read','资源读取资源','/system/resource/page/query','针对资源信息的分页查询等读取操作',0,'2023-01-19 11:12:19','2023-01-19 11:19:47'),(57789068209295360,'auth','认证权限可访问资源','system:param:read','参数读取资源','/system/param/page/query','针对参数信息的分页查询等读取操作',0,'2023-01-19 11:13:08','2023-01-19 11:19:47'),(57789114187255808,'auth','认证权限可访问资源','system:param:read','参数读取资源','/system/param/list','针对参数信息的查询等读取操作',0,'2023-01-19 11:13:18','2023-01-19 11:19:47'),(57789297914548224,'auth','认证权限可访问资源','system:param:write','参数写入资源','/system/param/*.do','针对参数信息的新增、修改和删除等写入操作',0,'2023-01-19 11:14:02','2023-01-19 11:19:47'),(57789390910656512,'auth','认证权限可访问资源','system:menu:write','菜单写入资源','/system/menu/*.do','针对菜单信息的新增、修改和删除等写入操作',0,'2023-01-19 11:14:24','2023-01-19 11:19:47'),(57789461437878272,'auth','认证权限可访问资源','system:dict:write','字典写入资源','/system/dict/*.do','针对字典信息的新增、修改和删除等写入操作',0,'2023-01-19 11:14:41','2023-01-19 11:19:47'),(57789579171991552,'auth','认证权限可访问资源','system:dict:read','字典读取资源','/system/dict/page/query','针对字典信息的分页查询等读取操作',0,'2023-01-19 11:15:09','2023-01-19 11:19:47'),(57789638311677952,'auth','认证权限可访问资源','system:menu:read','菜单读取资源','/system/menu/page/query','针对菜单信息的分页查询等读取操作',0,'2023-01-19 11:15:23','2023-01-19 11:19:47'),(57789759917133824,'auth','认证权限可访问资源','system:operate-record:read','操作记录读取资源','/system/operate/record/page/query','针对操作记录信息的分页查询等读取操作',0,'2023-01-19 11:15:52','2023-01-19 11:19:47'),(57790109646589952,'auth','认证权限可访问资源','system:menu:read','菜单读取资源','/system/menu/list','针对菜单信息的查询等读取操作',0,'2023-01-19 11:17:16','2023-01-19 11:19:47'),(57790269361491968,'auth','认证权限可访问资源','system:operate-record:read','操作日志读取资源','/system/operate/record/list','针对操作日志信息的查询等读取操作',0,'2023-01-19 11:17:54','2023-01-19 11:19:47'),(57790408603996160,'auth','认证权限可访问资源','system:dict:read','字典读取资源','/system/dict/list','针对字典信息的查询等读取操作',0,'2023-01-19 11:18:27','2023-01-19 11:19:47');
/*!40000 ALTER TABLE `sys_resource` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `sys_role`
--

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` (`id`, `role_code`, `role_name`, `role_sort`, `role_desc`, `delete_at`, `gmt_create`, `gmt_modify`) VALUES (49465151442784256,'admin','超级管理员',10,'超级管理员角色',0,'2022-12-27 11:56:51','2022-12-27 11:56:51'),(52807568615280640,'default','默认角色',20,'默认角色',0,'2023-01-05 17:18:25','2023-01-05 17:18:25'),(52807657781989376,'student','学生角色',30,'学生角色',0,'2023-01-05 17:18:47','2023-01-05 17:18:47');
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `sys_role_menu`
--

LOCK TABLES `sys_role_menu` WRITE;
/*!40000 ALTER TABLE `sys_role_menu` DISABLE KEYS */;
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES (49465151442784256,1),(49465151442784256,57777179060342784),(49465151442784256,57777869245648896),(49465151442784256,57778194971103232),(49465151442784256,57778575037960192),(49465151442784256,57778701492031488),(49465151442784256,57778800163033088),(49465151442784256,57778936230449152),(49465151442784256,57779196390543360);
/*!40000 ALTER TABLE `sys_role_menu` ENABLE KEYS */;
UNLOCK TABLES;

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
  CONSTRAINT `sys_role_resource_sys_resource_id_fk` FOREIGN KEY (`resource_id`) REFERENCES `sys_resource` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `sys_role_resource_sys_role_id_fk` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色资源关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_resource`
--

LOCK TABLES `sys_role_resource` WRITE;
/*!40000 ALTER TABLE `sys_role_resource` DISABLE KEYS */;
INSERT INTO `sys_role_resource` (`role_id`, `resource_id`) VALUES (49465151442784256,57783826176544768);
/*!40000 ALTER TABLE `sys_role_resource` ENABLE KEYS */;
UNLOCK TABLES;

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
  `birthday` date DEFAULT NULL COMMENT '出生日期',
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
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` (`id`, `account`, `password`, `nickname`, `sex`, `avatar_url`, `birthday`, `user_type`, `enable_status`, `delete_at`, `gmt_create`, `gmt_modify`) VALUES (52793893988864000,'admin','baa779b59017ac3ffa8852fb87a2375e','超级管理员',0,'','2023-01-05',0,0,0,'2023-01-05 16:24:05','2023-01-05 16:24:05'),(52794395153666048,'guochengqiang','1ca3219819b76ba4295f5d77dfa94fc4','一郭菠萝炖不下',1,'','1998-11-18',1,0,0,'2023-01-05 16:26:05','2023-01-05 16:26:05'),(52800078766936064,'admin1','669b8726c48be63e7a1acc53f6c8e775','超级管理员',0,'','2023-01-05',0,0,1672974752000,'2023-01-05 16:48:40','2023-01-05 16:48:40'),(53077342075228160,'test2222','d6725d86611d91fd17ec7645213fe364','test2222',2,'','2023-01-06',1,0,1672974752000,'2023-01-06 11:10:24','2023-01-06 11:10:24'),(53077698683342848,'test3332','f4ad4439953b011c3638fe278a75c5e7','test3332',2,'','2023-01-06',0,0,1672974752000,'2023-01-06 11:11:50','2023-01-06 11:11:50'),(53077951469850624,'test3332','f4ad4439953b011c3638fe278a75c5e7','test3332',2,'','2023-01-06',0,0,1672974786132,'2023-01-06 11:12:50','2023-01-06 11:12:50'),(55666781439922176,'testUser','f05e556d6d470f4025068d7dd481f316',NULL,0,'',NULL,1,0,1673594448653,'2023-01-13 14:39:55','2023-01-13 14:39:55');
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;

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

--
-- Dumping data for table `sys_user_role`
--

LOCK TABLES `sys_user_role` WRITE;
/*!40000 ALTER TABLE `sys_user_role` DISABLE KEYS */;
INSERT INTO `sys_user_role` (`user_id`, `role_id`) VALUES (52793893988864000,49465151442784256);
/*!40000 ALTER TABLE `sys_user_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-01-19 15:33:37
