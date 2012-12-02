-- MySQL dump 10.13  Distrib 5.5.13, for osx10.6 (i386)
--
-- Host: localhost    Database: crmp
-- ------------------------------------------------------
-- Server version	5.5.13-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `ACCOUNTRUNTASK`
--

DROP TABLE IF EXISTS `ACCOUNTRUNTASK`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ACCOUNTRUNTASK` (
  `ID` varchar(64) NOT NULL,
  `DISC` varchar(20) DEFAULT NULL,
  `SCANNEDPRODUCTIONRECORDQUANTITY` int(11) DEFAULT NULL,
  `STARTDATE` date DEFAULT NULL,
  `SCANNEDPRODUCTIVITY` double DEFAULT NULL,
  `EXECUTEDATE` date DEFAULT NULL,
  `CREATED_ON` datetime DEFAULT NULL,
  `EXECUTEENDTIME` datetime DEFAULT NULL,
  `DELETED` tinyint(1) DEFAULT '0',
  `GENERATEDINVOICEQUANTITY` int(11) DEFAULT NULL,
  `UPDATED_ON` datetime DEFAULT NULL,
  `ENDDATE` date DEFAULT NULL,
  `STATE` varchar(255) DEFAULT NULL,
  `EXECUTESTATETIME` datetime DEFAULT NULL,
  `GENERATEDINVOICEAMOUNT` double DEFAULT NULL,
  `TENANT_ID` varchar(64) NOT NULL,
  `CREATED_BY` varchar(64) NOT NULL,
  `UPDATED_BY` varchar(64) DEFAULT NULL,
  `SCANNEDACCOUNTQUANTITY` int(11) DEFAULT NULL,
  `SPECIFIEDSINGLEACCOUNT_ID` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_ACCOUNTRUNTASK_SPECIFIEDSINGLEACCOUNT_ID` (`SPECIFIEDSINGLEACCOUNT_ID`),
  KEY `FK_ACCOUNTRUNTASK_TENANT_ID` (`TENANT_ID`),
  KEY `FK_ACCOUNTRUNTASK_UPDATED_BY` (`UPDATED_BY`),
  KEY `FK_ACCOUNTRUNTASK_CREATED_BY` (`CREATED_BY`),
  CONSTRAINT `FK_ACCOUNTRUNTASK_CREATED_BY` FOREIGN KEY (`CREATED_BY`) REFERENCES `CRMP_User` (`ID`),
  CONSTRAINT `FK_ACCOUNTRUNTASK_SPECIFIEDSINGLEACCOUNT_ID` FOREIGN KEY (`SPECIFIEDSINGLEACCOUNT_ID`) REFERENCES `crmp_account` (`ID`),
  CONSTRAINT `FK_ACCOUNTRUNTASK_TENANT_ID` FOREIGN KEY (`TENANT_ID`) REFERENCES `CRMP_Tenant` (`ID`),
  CONSTRAINT `FK_ACCOUNTRUNTASK_UPDATED_BY` FOREIGN KEY (`UPDATED_BY`) REFERENCES `CRMP_User` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ACCOUNTRUNTASK`
--

LOCK TABLES `ACCOUNTRUNTASK` WRITE;
/*!40000 ALTER TABLE `ACCOUNTRUNTASK` DISABLE KEYS */;
/*!40000 ALTER TABLE `ACCOUNTRUNTASK` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CRMP_Menu_Item`
--

DROP TABLE IF EXISTS `CRMP_Menu_Item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CRMP_Menu_Item` (
  `ID` varchar(64) NOT NULL,
  `VIEWID` varchar(255) DEFAULT NULL,
  `CREATED_ON` datetime DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `UPDATED_ON` datetime DEFAULT NULL,
  `ICON` varchar(255) DEFAULT NULL,
  `DELETED` tinyint(1) DEFAULT '0',
  `TENANT_ID` varchar(64) NOT NULL,
  `PARENT_ID` varchar(64) DEFAULT NULL,
  `CREATED_BY` varchar(64) NOT NULL,
  `UPDATED_BY` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_CRMP_Menu_Item_PARENT_ID` (`PARENT_ID`),
  KEY `FK_CRMP_Menu_Item_CREATED_BY` (`CREATED_BY`),
  KEY `FK_CRMP_Menu_Item_TENANT_ID` (`TENANT_ID`),
  KEY `FK_CRMP_Menu_Item_UPDATED_BY` (`UPDATED_BY`),
  CONSTRAINT `FK_CRMP_Menu_Item_UPDATED_BY` FOREIGN KEY (`UPDATED_BY`) REFERENCES `CRMP_User` (`ID`),
  CONSTRAINT `FK_CRMP_Menu_Item_CREATED_BY` FOREIGN KEY (`CREATED_BY`) REFERENCES `CRMP_User` (`ID`),
  CONSTRAINT `FK_CRMP_Menu_Item_PARENT_ID` FOREIGN KEY (`PARENT_ID`) REFERENCES `CRMP_Menu_Item` (`ID`),
  CONSTRAINT `FK_CRMP_Menu_Item_TENANT_ID` FOREIGN KEY (`TENANT_ID`) REFERENCES `CRMP_Tenant` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CRMP_Menu_Item`
--

LOCK TABLES `CRMP_Menu_Item` WRITE;
/*!40000 ALTER TABLE `CRMP_Menu_Item` DISABLE KEYS */;
INSERT INTO `CRMP_Menu_Item` VALUES ('1','net.vicp.madz.framework.views.SystemSettings','2011-06-22 01:35:26','外观偏好',NULL,'application',0,'1',NULL,'1',NULL),('10','','2011-08-08 22:13:26','原始生产数据导入服务',NULL,'',0,'1',NULL,'2',NULL),('11','net.vicp.madz.etl.RelationshipMappingView','2011-08-08 22:31:30','关系字段映射',NULL,'dataGrid',0,'1','10','2',NULL),('12','net.vicp.madz.etl.views.KeySettingView','2011-08-08 22:24:49','原始数据表键设定',NULL,'dataGrid',0,'1','10','2',NULL),('13','net.vicp.madz.etl.views.SchemaSettingView','2011-08-08 22:29:31','生产数据模式映射',NULL,'dataGrid',0,'1','10','2',NULL),('14','','2011-08-08 22:29:30','账户管理',NULL,'',0,'1',NULL,'2',NULL),('15','net.vicp.madz.account.views.AccountView','2011-08-20 00:00:00','账户',NULL,'dataGrid',0,'1','14','2',NULL),('16','net.vicp.madz.account.views.ContactView','2011-08-20 00:00:00','联系人',NULL,'dataGrid',0,'1','14','2',NULL),('17','','2011-08-08 22:29:30','合同管理',NULL,'',0,'1',NULL,'2',NULL),('18','net.vicp.madz.contract.views.UnitOfProjectView','2011-08-20 00:00:00','单位工程',NULL,'dataGrid',0,'1','17','2',NULL),('19','net.vicp.madz.contract.views.ContractView','2011-08-20 00:00:00','合同',NULL,'dataGrid',0,'1','17','2',NULL),('2','','2011-06-22 01:35:26','基础数据',NULL,'',0,'1',NULL,'1',NULL),('20','net.vicp.madz.contract.views.ProjectView','2011-08-20 00:00:00','工程',NULL,'dataGrid',0,'1','17','2',NULL),('21','','2011-08-08 22:29:30','生产管理',NULL,'',0,'1',NULL,'2',NULL),('22','net.vicp.madz.production.views.ProductionRecordView','2011-08-20 00:00:00','生产记录',NULL,'dataGrid',0,'1','21','2',NULL),('23','net.vicp.madz.production.views.PlantView','2011-08-20 00:00:00','生产线',NULL,'dataGrid',0,'1','21','2',NULL),('3','net.vicp.madz.biz.common.views.CategoryView','2011-06-22 01:35:26','产品分类',NULL,'dataGrid',0,'1','2','1',NULL),('4','net.vicp.madz.biz.common.views.MerchandiseView','2011-06-22 01:35:26','产品',NULL,'dataGrid',0,'1','2','1',NULL),('5','net.vicp.madz.biz.common.views.UnitOfMeasureView','2011-06-22 01:35:26','计量单位',NULL,'dataGrid',0,'1','2','1',NULL),('6','','2011-08-02 13:19:20','系统管理',NULL,'',0,'1',NULL,'1',NULL),('7','net.vicp.madz.admin.views.MenuItemView','2011-08-02 13:19:20','功能',NULL,'dataGrid',0,'1','6','1',NULL),('8','net.vicp.madz.admin.views.RoleView','2011-08-08 18:50:00','角色',NULL,'dataGrid',0,'1','6','2',NULL);
/*!40000 ALTER TABLE `CRMP_Menu_Item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CRMP_OPERATION_LOG`
--

DROP TABLE IF EXISTS `CRMP_OPERATION_LOG`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CRMP_OPERATION_LOG` (
  `ID` bigint(20) NOT NULL,
  `DTYPE` varchar(31) DEFAULT NULL,
  `SOURCETYPE` varchar(255) DEFAULT NULL,
  `SOURCENAME` varchar(255) DEFAULT NULL,
  `USERNAME` varchar(255) DEFAULT NULL,
  `FAILEDCAUSE` varchar(255) DEFAULT NULL,
  `START` datetime DEFAULT NULL,
  `COMPLETE` datetime DEFAULT NULL,
  `SERVICENAME` varchar(255) DEFAULT NULL,
  `SUCCEED` tinyint(1) DEFAULT '0',
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `TIMECOST` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CRMP_OPERATION_LOG`
--

LOCK TABLES `CRMP_OPERATION_LOG` WRITE;
/*!40000 ALTER TABLE `CRMP_OPERATION_LOG` DISABLE KEYS */;
/*!40000 ALTER TABLE `CRMP_OPERATION_LOG` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CRMP_ROLE_MENU_ITEM`
--

DROP TABLE IF EXISTS `CRMP_ROLE_MENU_ITEM`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CRMP_ROLE_MENU_ITEM` (
  `MENU_ITEM_ID` varchar(64) NOT NULL,
  `ROLE_ID` varchar(64) NOT NULL,
  PRIMARY KEY (`MENU_ITEM_ID`,`ROLE_ID`),
  KEY `FK_CRMP_ROLE_MENU_ITEM_ROLE_ID` (`ROLE_ID`),
  CONSTRAINT `FK_CRMP_ROLE_MENU_ITEM_MENU_ITEM_ID` FOREIGN KEY (`MENU_ITEM_ID`) REFERENCES `CRMP_Menu_Item` (`ID`),
  CONSTRAINT `FK_CRMP_ROLE_MENU_ITEM_ROLE_ID` FOREIGN KEY (`ROLE_ID`) REFERENCES `CRMP_Role` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CRMP_ROLE_MENU_ITEM`
--

LOCK TABLES `CRMP_ROLE_MENU_ITEM` WRITE;
/*!40000 ALTER TABLE `CRMP_ROLE_MENU_ITEM` DISABLE KEYS */;
INSERT INTO `CRMP_ROLE_MENU_ITEM` VALUES ('1','1'),('10','1'),('11','1'),('12','1'),('13','1'),('14','1'),('15','1'),('16','1'),('17','1'),('18','1'),('19','1'),('2','1'),('20','1'),('21','1'),('22','1'),('23','1'),('3','1'),('4','1'),('5','1'),('6','1'),('7','1'),('8','1'),('2','2'),('3','2'),('4','2'),('5','2');
/*!40000 ALTER TABLE `CRMP_ROLE_MENU_ITEM` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CRMP_Role`
--

DROP TABLE IF EXISTS `CRMP_Role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CRMP_Role` (
  `ID` varchar(64) NOT NULL,
  `UPDATED_ON` datetime DEFAULT NULL,
  `CREATED_ON` datetime DEFAULT NULL,
  `DELETED` tinyint(1) DEFAULT '0',
  `NAME` varchar(255) DEFAULT NULL,
  `CREATED_BY` varchar(64) NOT NULL,
  `TENANT_ID` varchar(64) NOT NULL,
  `UPDATED_BY` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_CRMP_Role_UPDATED_BY` (`UPDATED_BY`),
  KEY `FK_CRMP_Role_TENANT_ID` (`TENANT_ID`),
  KEY `FK_CRMP_Role_CREATED_BY` (`CREATED_BY`),
  CONSTRAINT `FK_CRMP_Role_CREATED_BY` FOREIGN KEY (`CREATED_BY`) REFERENCES `CRMP_User` (`ID`),
  CONSTRAINT `FK_CRMP_Role_TENANT_ID` FOREIGN KEY (`TENANT_ID`) REFERENCES `CRMP_Tenant` (`ID`),
  CONSTRAINT `FK_CRMP_Role_UPDATED_BY` FOREIGN KEY (`UPDATED_BY`) REFERENCES `CRMP_User` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CRMP_Role`
--

LOCK TABLES `CRMP_Role` WRITE;
/*!40000 ALTER TABLE `CRMP_Role` DISABLE KEYS */;
INSERT INTO `CRMP_Role` VALUES ('1',NULL,'2011-06-22 01:32:49',0,'SA','1','1',NULL),('2',NULL,'2011-06-22 01:32:49',0,'ADMIN','1','1',NULL),('3',NULL,'2011-06-22 01:32:49',0,'OP','1','1',NULL);
/*!40000 ALTER TABLE `CRMP_Role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CRMP_USER_GROUP`
--

DROP TABLE IF EXISTS `CRMP_USER_GROUP`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CRMP_USER_GROUP` (
  `ID` varchar(64) NOT NULL,
  `NAME` varchar(40) DEFAULT NULL,
  `UPDATED_ON` datetime DEFAULT NULL,
  `DELETED` tinyint(1) DEFAULT '0',
  `CREATED_ON` datetime DEFAULT NULL,
  `CREATED_BY` varchar(64) NOT NULL,
  `TENANT_ID` varchar(64) NOT NULL,
  `UPDATED_BY` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `NAME` (`NAME`),
  KEY `FK_CRMP_USER_GROUP_CREATED_BY` (`CREATED_BY`),
  KEY `FK_CRMP_USER_GROUP_UPDATED_BY` (`UPDATED_BY`),
  KEY `FK_CRMP_USER_GROUP_TENANT_ID` (`TENANT_ID`),
  CONSTRAINT `FK_CRMP_USER_GROUP_TENANT_ID` FOREIGN KEY (`TENANT_ID`) REFERENCES `CRMP_Tenant` (`ID`),
  CONSTRAINT `FK_CRMP_USER_GROUP_CREATED_BY` FOREIGN KEY (`CREATED_BY`) REFERENCES `CRMP_User` (`ID`),
  CONSTRAINT `FK_CRMP_USER_GROUP_UPDATED_BY` FOREIGN KEY (`UPDATED_BY`) REFERENCES `CRMP_User` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CRMP_USER_GROUP`
--

LOCK TABLES `CRMP_USER_GROUP` WRITE;
/*!40000 ALTER TABLE `CRMP_USER_GROUP` DISABLE KEYS */;
INSERT INTO `CRMP_USER_GROUP` VALUES ('1','ADMINGroup',NULL,0,'2011-06-22 01:32:49','1','1',NULL),('2','OPGroup',NULL,0,'2011-06-22 01:32:49','1','1',NULL);
/*!40000 ALTER TABLE `CRMP_USER_GROUP` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CRMP_USER_USER_GROUP`
--

DROP TABLE IF EXISTS `CRMP_USER_USER_GROUP`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CRMP_USER_USER_GROUP` (
  `USERNAME` varchar(40) NOT NULL,
  `GROUP_NAME` varchar(40) NOT NULL,
  PRIMARY KEY (`USERNAME`,`GROUP_NAME`),
  KEY `FK_CRMP_USER_USER_GROUP_GROUP_NAME` (`GROUP_NAME`),
  CONSTRAINT `FK_CRMP_USER_USER_GROUP_USERNAME` FOREIGN KEY (`USERNAME`) REFERENCES `CRMP_User` (`USERNAME`),
  CONSTRAINT `FK_CRMP_USER_USER_GROUP_GROUP_NAME` FOREIGN KEY (`GROUP_NAME`) REFERENCES `CRMP_USER_GROUP` (`NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CRMP_USER_USER_GROUP`
--

LOCK TABLES `CRMP_USER_USER_GROUP` WRITE;
/*!40000 ALTER TABLE `CRMP_USER_USER_GROUP` DISABLE KEYS */;
INSERT INTO `CRMP_USER_USER_GROUP` VALUES ('administrator','ADMINGroup'),('zhongdj@gmail.com','ADMINGroup');
/*!40000 ALTER TABLE `CRMP_USER_USER_GROUP` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CRMP_User`
--

DROP TABLE IF EXISTS `CRMP_User`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CRMP_User` (
  `ID` varchar(64) NOT NULL,
  `ACCESSDENIEDTIMES` int(11) DEFAULT NULL,
  `FREEZENFLAG` tinyint(1) DEFAULT '0',
  `DELETED` tinyint(1) DEFAULT '0',
  `NEEDRESETPWD` tinyint(1) DEFAULT '0',
  `LOGINDATE` datetime DEFAULT NULL,
  `PASSWORD` varchar(255) DEFAULT NULL,
  `LASTLOGINDATE` datetime DEFAULT NULL,
  `FULLNAME` varchar(255) DEFAULT NULL,
  `LASTFAILEDTIME` datetime DEFAULT NULL,
  `LOGINTIMES` int(11) DEFAULT NULL,
  `LASTCHANGEPWDTIME` datetime DEFAULT NULL,
  `CREATED_ON` datetime DEFAULT NULL,
  `OLDPASSWORDS` varchar(255) DEFAULT NULL,
  `USERNAME` varchar(40) DEFAULT NULL,
  `LOGINFAILEDTIMES` int(11) DEFAULT NULL,
  `UPDATED_ON` datetime DEFAULT NULL,
  `LOCKFLAG` tinyint(1) DEFAULT '0',
  `EMAIL` varchar(255) DEFAULT NULL,
  `CREATED_BY` varchar(64) NOT NULL,
  `TENANT_ID` varchar(64) NOT NULL,
  `UPDATED_BY` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `USERNAME` (`USERNAME`),
  KEY `FK_CRMP_User_CREATED_BY` (`CREATED_BY`),
  KEY `FK_CRMP_User_UPDATED_BY` (`UPDATED_BY`),
  KEY `FK_CRMP_User_TENANT_ID` (`TENANT_ID`),
  CONSTRAINT `FK_CRMP_User_TENANT_ID` FOREIGN KEY (`TENANT_ID`) REFERENCES `CRMP_Tenant` (`ID`),
  CONSTRAINT `FK_CRMP_User_CREATED_BY` FOREIGN KEY (`CREATED_BY`) REFERENCES `CRMP_User` (`ID`),
  CONSTRAINT `FK_CRMP_User_UPDATED_BY` FOREIGN KEY (`UPDATED_BY`) REFERENCES `CRMP_User` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CRMP_User`
--

LOCK TABLES `CRMP_User` WRITE;
/*!40000 ALTER TABLE `CRMP_User` DISABLE KEYS */;
INSERT INTO `CRMP_User` VALUES ('1',NULL,0,0,0,NULL,'4a6dfef787c0e3d8a5ccacb67e709060',NULL,'Barry Zhong',NULL,NULL,NULL,NULL,NULL,'zhongdj@gmail.com',NULL,NULL,0,'zhongdj@gmail.com','1','1',NULL),('2',0,0,0,0,'2011-10-17 11:10:10','4a6dfef787c0e3d8a5ccacb67e709060','2011-10-17 11:05:32','Barry Zhong','2011-06-22 01:33:27',45,'2011-06-22 01:33:27',NULL,'','administrator',0,NULL,0,'zhongdj@gmail.com','1','1',NULL);
/*!40000 ALTER TABLE `CRMP_User` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CRMP_User_CRMP_Role`
--

DROP TABLE IF EXISTS `CRMP_User_CRMP_Role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CRMP_User_CRMP_Role` (
  `users_ID` varchar(64) NOT NULL,
  `roles_ID` varchar(64) NOT NULL,
  PRIMARY KEY (`users_ID`,`roles_ID`),
  KEY `FK_CRMP_User_CRMP_Role_roles_ID` (`roles_ID`),
  CONSTRAINT `FK_CRMP_User_CRMP_Role_users_ID` FOREIGN KEY (`users_ID`) REFERENCES `CRMP_User` (`ID`),
  CONSTRAINT `FK_CRMP_User_CRMP_Role_roles_ID` FOREIGN KEY (`roles_ID`) REFERENCES `CRMP_Role` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CRMP_User_CRMP_Role`
--

LOCK TABLES `CRMP_User_CRMP_Role` WRITE;
/*!40000 ALTER TABLE `CRMP_User_CRMP_Role` DISABLE KEYS */;
INSERT INTO `CRMP_User_CRMP_Role` VALUES ('1','1'),('1','2');
/*!40000 ALTER TABLE `CRMP_User_CRMP_Role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ESSENTIAL_ID_GEN`
--

DROP TABLE IF EXISTS `ESSENTIAL_ID_GEN`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ESSENTIAL_ID_GEN` (
  `GEN_KEY` varchar(50) NOT NULL,
  `GEN_VALUE` decimal(38,0) DEFAULT NULL,
  PRIMARY KEY (`GEN_KEY`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ESSENTIAL_ID_GEN`
--

LOCK TABLES `ESSENTIAL_ID_GEN` WRITE;
/*!40000 ALTER TABLE `ESSENTIAL_ID_GEN` DISABLE KEYS */;
INSERT INTO `ESSENTIAL_ID_GEN` VALUES ('OPLOG_ID',0);
/*!40000 ALTER TABLE `ESSENTIAL_ID_GEN` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crmp_account`
--

DROP TABLE IF EXISTS `crmp_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crmp_account` (
  `ID` varchar(64) NOT NULL,
  `name` varchar(100) NOT NULL,
  `CREATED_ON` datetime DEFAULT NULL,
  `short_name` varchar(100) NOT NULL,
  `UPDATED_ON` datetime DEFAULT NULL,
  `DELETED` tinyint(1) DEFAULT '0',
  `TENANT_ID` varchar(64) NOT NULL,
  `CREATED_BY` varchar(64) NOT NULL,
  `UPDATED_BY` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_crmp_account_UPDATED_BY` (`UPDATED_BY`),
  KEY `FK_crmp_account_TENANT_ID` (`TENANT_ID`),
  KEY `FK_crmp_account_CREATED_BY` (`CREATED_BY`),
  CONSTRAINT `FK_crmp_account_CREATED_BY` FOREIGN KEY (`CREATED_BY`) REFERENCES `CRMP_User` (`ID`),
  CONSTRAINT `FK_crmp_account_TENANT_ID` FOREIGN KEY (`TENANT_ID`) REFERENCES `CRMP_Tenant` (`ID`),
  CONSTRAINT `FK_crmp_account_UPDATED_BY` FOREIGN KEY (`UPDATED_BY`) REFERENCES `CRMP_User` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crmp_account`
--

LOCK TABLES `crmp_account` WRITE;
/*!40000 ALTER TABLE `crmp_account` DISABLE KEYS */;
INSERT INTO `crmp_account` VALUES ('c8b24d44-e25a-4a9a-a65e-2a49e0444962','UNSPECIFIED','2011-10-17 10:24:14','UNSPECIFIED',NULL,0,'1','2',NULL);
/*!40000 ALTER TABLE `crmp_account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crmp_address`
--

DROP TABLE IF EXISTS `crmp_address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crmp_address` (
  `ID` varchar(64) NOT NULL,
  `LINETWO` varchar(255) DEFAULT NULL,
  `LINETHREE` varchar(255) DEFAULT NULL,
  `DELETED` tinyint(1) DEFAULT '0',
  `LINEONE` varchar(255) DEFAULT NULL,
  `CREATED_ON` datetime DEFAULT NULL,
  `UPDATED_ON` datetime DEFAULT NULL,
  `TENANT_ID` varchar(64) NOT NULL,
  `STREET_ID` varchar(64) DEFAULT NULL,
  `zip_code_id` varchar(64) DEFAULT NULL,
  `CREATED_BY` varchar(64) NOT NULL,
  `UPDATED_BY` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_crmp_address_TENANT_ID` (`TENANT_ID`),
  KEY `FK_crmp_address_UPDATED_BY` (`UPDATED_BY`),
  KEY `FK_crmp_address_CREATED_BY` (`CREATED_BY`),
  KEY `FK_crmp_address_STREET_ID` (`STREET_ID`),
  KEY `FK_crmp_address_zip_code_id` (`zip_code_id`),
  CONSTRAINT `FK_crmp_address_zip_code_id` FOREIGN KEY (`zip_code_id`) REFERENCES `crmp_zip_code` (`ID`),
  CONSTRAINT `FK_crmp_address_CREATED_BY` FOREIGN KEY (`CREATED_BY`) REFERENCES `CRMP_User` (`ID`),
  CONSTRAINT `FK_crmp_address_STREET_ID` FOREIGN KEY (`STREET_ID`) REFERENCES `crmp_street` (`ID`),
  CONSTRAINT `FK_crmp_address_TENANT_ID` FOREIGN KEY (`TENANT_ID`) REFERENCES `CRMP_Tenant` (`ID`),
  CONSTRAINT `FK_crmp_address_UPDATED_BY` FOREIGN KEY (`UPDATED_BY`) REFERENCES `CRMP_User` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crmp_address`
--

LOCK TABLES `crmp_address` WRITE;
/*!40000 ALTER TABLE `crmp_address` DISABLE KEYS */;
/*!40000 ALTER TABLE `crmp_address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crmp_adjustment`
--

DROP TABLE IF EXISTS `crmp_adjustment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crmp_adjustment` (
  `ID` varchar(64) NOT NULL,
  `state` varchar(20) DEFAULT NULL,
  `CREATED_ON` datetime DEFAULT NULL,
  `UPDATED_ON` datetime DEFAULT NULL,
  `DELETED` tinyint(1) DEFAULT '0',
  `CREATED_BY` varchar(64) NOT NULL,
  `TENANT_ID` varchar(64) NOT NULL,
  `UPDATED_BY` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_crmp_adjustment_TENANT_ID` (`TENANT_ID`),
  KEY `FK_crmp_adjustment_UPDATED_BY` (`UPDATED_BY`),
  KEY `FK_crmp_adjustment_CREATED_BY` (`CREATED_BY`),
  CONSTRAINT `FK_crmp_adjustment_CREATED_BY` FOREIGN KEY (`CREATED_BY`) REFERENCES `CRMP_User` (`ID`),
  CONSTRAINT `FK_crmp_adjustment_TENANT_ID` FOREIGN KEY (`TENANT_ID`) REFERENCES `CRMP_Tenant` (`ID`),
  CONSTRAINT `FK_crmp_adjustment_UPDATED_BY` FOREIGN KEY (`UPDATED_BY`) REFERENCES `CRMP_User` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crmp_adjustment`
--

LOCK TABLES `crmp_adjustment` WRITE;
/*!40000 ALTER TABLE `crmp_adjustment` DISABLE KEYS */;
/*!40000 ALTER TABLE `crmp_adjustment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crmp_adjustment_invoice`
--

DROP TABLE IF EXISTS `crmp_adjustment_invoice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crmp_adjustment_invoice` (
  `ID` varchar(64) NOT NULL,
  `accounting_code` varchar(100) DEFAULT NULL,
  `CREATED_ON` datetime DEFAULT NULL,
  `apply_Amount` double DEFAULT NULL,
  `UPDATED_ON` datetime DEFAULT NULL,
  `DELETED` tinyint(1) DEFAULT '0',
  `TENANT_ID` varchar(64) NOT NULL,
  `invoice_id` varchar(64) NOT NULL,
  `adjustment_id` varchar(64) NOT NULL,
  `CREATED_BY` varchar(64) NOT NULL,
  `UPDATED_BY` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_crmp_adjustment_invoice_invoice_id` (`invoice_id`),
  KEY `FK_crmp_adjustment_invoice_UPDATED_BY` (`UPDATED_BY`),
  KEY `FK_crmp_adjustment_invoice_CREATED_BY` (`CREATED_BY`),
  KEY `FK_crmp_adjustment_invoice_adjustment_id` (`adjustment_id`),
  KEY `FK_crmp_adjustment_invoice_TENANT_ID` (`TENANT_ID`),
  CONSTRAINT `FK_crmp_adjustment_invoice_TENANT_ID` FOREIGN KEY (`TENANT_ID`) REFERENCES `CRMP_Tenant` (`ID`),
  CONSTRAINT `FK_crmp_adjustment_invoice_adjustment_id` FOREIGN KEY (`adjustment_id`) REFERENCES `crmp_adjustment` (`ID`),
  CONSTRAINT `FK_crmp_adjustment_invoice_CREATED_BY` FOREIGN KEY (`CREATED_BY`) REFERENCES `CRMP_User` (`ID`),
  CONSTRAINT `FK_crmp_adjustment_invoice_invoice_id` FOREIGN KEY (`invoice_id`) REFERENCES `crmp_invoice` (`ID`),
  CONSTRAINT `FK_crmp_adjustment_invoice_UPDATED_BY` FOREIGN KEY (`UPDATED_BY`) REFERENCES `CRMP_User` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crmp_adjustment_invoice`
--

LOCK TABLES `crmp_adjustment_invoice` WRITE;
/*!40000 ALTER TABLE `crmp_adjustment_invoice` DISABLE KEYS */;
/*!40000 ALTER TABLE `crmp_adjustment_invoice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crmp_amendment`
--

DROP TABLE IF EXISTS `crmp_amendment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crmp_amendment` (
  `ID` varchar(64) NOT NULL,
  `old_bill_method` varchar(20) NOT NULL,
  `new_bill_method` varchar(20) NOT NULL,
  `DELETED` tinyint(1) DEFAULT '0',
  `effective_start_date` datetime DEFAULT NULL,
  `effective_end_date` datetime DEFAULT NULL,
  `state` varchar(20) NOT NULL,
  `amendment_type` varchar(20) NOT NULL,
  `UPDATED_ON` datetime DEFAULT NULL,
  `CREATED_ON` datetime DEFAULT NULL,
  `TENANT_ID` varchar(64) NOT NULL,
  `contract_Id` varchar(64) NOT NULL,
  `CREATED_BY` varchar(64) NOT NULL,
  `UPDATED_BY` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_crmp_amendment_contract_Id` (`contract_Id`),
  KEY `FK_crmp_amendment_TENANT_ID` (`TENANT_ID`),
  KEY `FK_crmp_amendment_CREATED_BY` (`CREATED_BY`),
  KEY `FK_crmp_amendment_UPDATED_BY` (`UPDATED_BY`),
  CONSTRAINT `FK_crmp_amendment_UPDATED_BY` FOREIGN KEY (`UPDATED_BY`) REFERENCES `CRMP_User` (`ID`),
  CONSTRAINT `FK_crmp_amendment_contract_Id` FOREIGN KEY (`contract_Id`) REFERENCES `crmp_contract` (`ID`),
  CONSTRAINT `FK_crmp_amendment_CREATED_BY` FOREIGN KEY (`CREATED_BY`) REFERENCES `CRMP_User` (`ID`),
  CONSTRAINT `FK_crmp_amendment_TENANT_ID` FOREIGN KEY (`TENANT_ID`) REFERENCES `CRMP_Tenant` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crmp_amendment`
--

LOCK TABLES `crmp_amendment` WRITE;
/*!40000 ALTER TABLE `crmp_amendment` DISABLE KEYS */;
/*!40000 ALTER TABLE `crmp_amendment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crmp_amendment_item`
--

DROP TABLE IF EXISTS `crmp_amendment_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crmp_amendment_item` (
  `ID` varchar(64) NOT NULL,
  `old_charge_rate` double DEFAULT NULL,
  `CREATED_ON` datetime DEFAULT NULL,
  `new_charge_type` varchar(20) DEFAULT NULL,
  `UPDATED_ON` datetime DEFAULT NULL,
  `new_charge_model` varchar(20) DEFAULT NULL,
  `state` varchar(20) DEFAULT NULL,
  `new_charge_rate` double DEFAULT NULL,
  `old_charge_model` varchar(20) DEFAULT NULL,
  `old_charge_type` varchar(20) DEFAULT NULL,
  `DELETED` tinyint(1) DEFAULT '0',
  `TENANT_ID` varchar(64) NOT NULL,
  `amended_rate_plan_component_id` varchar(64) NOT NULL,
  `CREATED_BY` varchar(64) NOT NULL,
  `UPDATED_BY` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_crmp_amendment_item_UPDATED_BY` (`UPDATED_BY`),
  KEY `FK_crmp_amendment_item_CREATED_BY` (`CREATED_BY`),
  KEY `FK_crmp_amendment_item_TENANT_ID` (`TENANT_ID`),
  KEY `crmp_amendment_item_amended_rate_plan_component_id` (`amended_rate_plan_component_id`),
  CONSTRAINT `crmp_amendment_item_amended_rate_plan_component_id` FOREIGN KEY (`amended_rate_plan_component_id`) REFERENCES `crmp_contract_rate_plan_component` (`ID`),
  CONSTRAINT `FK_crmp_amendment_item_CREATED_BY` FOREIGN KEY (`CREATED_BY`) REFERENCES `CRMP_User` (`ID`),
  CONSTRAINT `FK_crmp_amendment_item_TENANT_ID` FOREIGN KEY (`TENANT_ID`) REFERENCES `CRMP_Tenant` (`ID`),
  CONSTRAINT `FK_crmp_amendment_item_UPDATED_BY` FOREIGN KEY (`UPDATED_BY`) REFERENCES `CRMP_User` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crmp_amendment_item`
--

LOCK TABLES `crmp_amendment_item` WRITE;
/*!40000 ALTER TABLE `crmp_amendment_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `crmp_amendment_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crmp_ar_payment`
--

DROP TABLE IF EXISTS `crmp_ar_payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crmp_ar_payment` (
  `ID` varchar(64) NOT NULL,
  `amount` double NOT NULL,
  `refundAmount` double DEFAULT NULL,
  `DELETED` tinyint(1) DEFAULT '0',
  `status` varchar(20) DEFAULT NULL,
  `CREATED_ON` datetime DEFAULT NULL,
  `payment_Number` varchar(32) NOT NULL,
  `UPDATED_ON` datetime DEFAULT NULL,
  `TENANT_ID` varchar(64) NOT NULL,
  `payer_account_id` varchar(64) NOT NULL,
  `CREATED_BY` varchar(64) NOT NULL,
  `UPDATED_BY` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_crmp_ar_payment_CREATED_BY` (`CREATED_BY`),
  KEY `FK_crmp_ar_payment_UPDATED_BY` (`UPDATED_BY`),
  KEY `FK_crmp_ar_payment_TENANT_ID` (`TENANT_ID`),
  KEY `FK_crmp_ar_payment_payer_account_id` (`payer_account_id`),
  CONSTRAINT `FK_crmp_ar_payment_payer_account_id` FOREIGN KEY (`payer_account_id`) REFERENCES `crmp_account` (`ID`),
  CONSTRAINT `FK_crmp_ar_payment_CREATED_BY` FOREIGN KEY (`CREATED_BY`) REFERENCES `CRMP_User` (`ID`),
  CONSTRAINT `FK_crmp_ar_payment_TENANT_ID` FOREIGN KEY (`TENANT_ID`) REFERENCES `CRMP_Tenant` (`ID`),
  CONSTRAINT `FK_crmp_ar_payment_UPDATED_BY` FOREIGN KEY (`UPDATED_BY`) REFERENCES `CRMP_User` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crmp_ar_payment`
--

LOCK TABLES `crmp_ar_payment` WRITE;
/*!40000 ALTER TABLE `crmp_ar_payment` DISABLE KEYS */;
/*!40000 ALTER TABLE `crmp_ar_payment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crmp_artransaction`
--

DROP TABLE IF EXISTS `crmp_artransaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crmp_artransaction` (
  `ID` varchar(64) NOT NULL,
  `age` int(11) DEFAULT NULL,
  `DELETED` tinyint(1) DEFAULT '0',
  `amount` double DEFAULT NULL,
  `UPDATED_ON` datetime DEFAULT NULL,
  `CREATED_ON` datetime DEFAULT NULL,
  `TENANT_ID` varchar(64) NOT NULL,
  `PAYMENTID_ID` varchar(64) DEFAULT NULL,
  `CREATED_BY` varchar(64) NOT NULL,
  `payment_method_id` varchar(64) NOT NULL,
  `invoice_id` varchar(64) NOT NULL,
  `UPDATED_BY` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_crmp_artransaction_payment_method_id` (`payment_method_id`),
  KEY `FK_crmp_artransaction_UPDATED_BY` (`UPDATED_BY`),
  KEY `FK_crmp_artransaction_invoice_id` (`invoice_id`),
  KEY `FK_crmp_artransaction_CREATED_BY` (`CREATED_BY`),
  KEY `FK_crmp_artransaction_PAYMENTID_ID` (`PAYMENTID_ID`),
  KEY `FK_crmp_artransaction_TENANT_ID` (`TENANT_ID`),
  CONSTRAINT `FK_crmp_artransaction_TENANT_ID` FOREIGN KEY (`TENANT_ID`) REFERENCES `CRMP_Tenant` (`ID`),
  CONSTRAINT `FK_crmp_artransaction_CREATED_BY` FOREIGN KEY (`CREATED_BY`) REFERENCES `CRMP_User` (`ID`),
  CONSTRAINT `FK_crmp_artransaction_invoice_id` FOREIGN KEY (`invoice_id`) REFERENCES `crmp_invoice` (`ID`),
  CONSTRAINT `FK_crmp_artransaction_PAYMENTID_ID` FOREIGN KEY (`PAYMENTID_ID`) REFERENCES `crmp_ar_payment` (`ID`),
  CONSTRAINT `FK_crmp_artransaction_payment_method_id` FOREIGN KEY (`payment_method_id`) REFERENCES `crmp_payment_method` (`ID`),
  CONSTRAINT `FK_crmp_artransaction_UPDATED_BY` FOREIGN KEY (`UPDATED_BY`) REFERENCES `CRMP_User` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crmp_artransaction`
--

LOCK TABLES `crmp_artransaction` WRITE;
/*!40000 ALTER TABLE `crmp_artransaction` DISABLE KEYS */;
/*!40000 ALTER TABLE `crmp_artransaction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crmp_city`
--

DROP TABLE IF EXISTS `crmp_city`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crmp_city` (
  `ID` varchar(64) NOT NULL,
  `name` varchar(100) NOT NULL,
  `CREATED_ON` datetime DEFAULT NULL,
  `UPDATED_ON` datetime DEFAULT NULL,
  `DELETED` tinyint(1) DEFAULT '0',
  `TENANT_ID` varchar(64) NOT NULL,
  `province_id` varchar(64) DEFAULT NULL,
  `CREATED_BY` varchar(64) NOT NULL,
  `UPDATED_BY` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_crmp_city_TENANT_ID` (`TENANT_ID`),
  KEY `FK_crmp_city_CREATED_BY` (`CREATED_BY`),
  KEY `FK_crmp_city_province_id` (`province_id`),
  KEY `FK_crmp_city_UPDATED_BY` (`UPDATED_BY`),
  CONSTRAINT `FK_crmp_city_UPDATED_BY` FOREIGN KEY (`UPDATED_BY`) REFERENCES `CRMP_User` (`ID`),
  CONSTRAINT `FK_crmp_city_CREATED_BY` FOREIGN KEY (`CREATED_BY`) REFERENCES `CRMP_User` (`ID`),
  CONSTRAINT `FK_crmp_city_province_id` FOREIGN KEY (`province_id`) REFERENCES `crmp_province` (`ID`),
  CONSTRAINT `FK_crmp_city_TENANT_ID` FOREIGN KEY (`TENANT_ID`) REFERENCES `CRMP_Tenant` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crmp_city`
--

LOCK TABLES `crmp_city` WRITE;
/*!40000 ALTER TABLE `crmp_city` DISABLE KEYS */;
/*!40000 ALTER TABLE `crmp_city` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crmp_concrete_mixing_plant`
--

DROP TABLE IF EXISTS `crmp_concrete_mixing_plant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crmp_concrete_mixing_plant` (
  `ID` varchar(64) NOT NULL,
  `name` varchar(40) NOT NULL,
  `UPDATED_ON` datetime DEFAULT NULL,
  `DELETED` tinyint(1) DEFAULT '0',
  `CREATED_ON` datetime DEFAULT NULL,
  `TENANT_ID` varchar(64) NOT NULL,
  `OPERATOR_ID` varchar(64) DEFAULT NULL,
  `CREATED_BY` varchar(64) NOT NULL,
  `UPDATED_BY` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_crmp_concrete_mixing_plant_TENANT_ID` (`TENANT_ID`),
  KEY `FK_crmp_concrete_mixing_plant_UPDATED_BY` (`UPDATED_BY`),
  KEY `FK_crmp_concrete_mixing_plant_CREATED_BY` (`CREATED_BY`),
  KEY `FK_crmp_concrete_mixing_plant_OPERATOR_ID` (`OPERATOR_ID`),
  CONSTRAINT `FK_crmp_concrete_mixing_plant_OPERATOR_ID` FOREIGN KEY (`OPERATOR_ID`) REFERENCES `CRMP_User` (`ID`),
  CONSTRAINT `FK_crmp_concrete_mixing_plant_CREATED_BY` FOREIGN KEY (`CREATED_BY`) REFERENCES `CRMP_User` (`ID`),
  CONSTRAINT `FK_crmp_concrete_mixing_plant_TENANT_ID` FOREIGN KEY (`TENANT_ID`) REFERENCES `CRMP_Tenant` (`ID`),
  CONSTRAINT `FK_crmp_concrete_mixing_plant_UPDATED_BY` FOREIGN KEY (`UPDATED_BY`) REFERENCES `CRMP_User` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crmp_concrete_mixing_plant`
--

LOCK TABLES `crmp_concrete_mixing_plant` WRITE;
/*!40000 ALTER TABLE `crmp_concrete_mixing_plant` DISABLE KEYS */;
INSERT INTO `crmp_concrete_mixing_plant` VALUES ('efdfa381-cc24-488d-b8c4-2714635e73fb','3#锟斤拷锟斤拷站',NULL,0,'2011-10-17 10:16:05','1',NULL,'2',NULL);
/*!40000 ALTER TABLE `crmp_concrete_mixing_plant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crmp_construction_part`
--

DROP TABLE IF EXISTS `crmp_construction_part`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crmp_construction_part` (
  `ID` varchar(64) NOT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `CREATED_ON` datetime DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `UPDATED_ON` datetime DEFAULT NULL,
  `DELETED` tinyint(1) DEFAULT '0',
  `TENANT_ID` varchar(64) NOT NULL,
  `CREATED_BY` varchar(64) NOT NULL,
  `UPDATED_BY` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_crmp_construction_part_UPDATED_BY` (`UPDATED_BY`),
  KEY `FK_crmp_construction_part_TENANT_ID` (`TENANT_ID`),
  KEY `FK_crmp_construction_part_CREATED_BY` (`CREATED_BY`),
  CONSTRAINT `FK_crmp_construction_part_CREATED_BY` FOREIGN KEY (`CREATED_BY`) REFERENCES `CRMP_User` (`ID`),
  CONSTRAINT `FK_crmp_construction_part_TENANT_ID` FOREIGN KEY (`TENANT_ID`) REFERENCES `CRMP_Tenant` (`ID`),
  CONSTRAINT `FK_crmp_construction_part_UPDATED_BY` FOREIGN KEY (`UPDATED_BY`) REFERENCES `CRMP_User` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crmp_construction_part`
--

LOCK TABLES `crmp_construction_part` WRITE;
/*!40000 ALTER TABLE `crmp_construction_part` DISABLE KEYS */;
/*!40000 ALTER TABLE `crmp_construction_part` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crmp_contact`
--

DROP TABLE IF EXISTS `crmp_contact`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crmp_contact` (
  `ID` varchar(64) NOT NULL,
  `telephone` varchar(20) DEFAULT NULL,
  `CREATED_ON` datetime DEFAULT NULL,
  `fax` varchar(20) DEFAULT NULL,
  `UPDATED_ON` datetime DEFAULT NULL,
  `sex` tinyint(1) DEFAULT '0',
  `name` varchar(50) DEFAULT NULL,
  `contact_type` varchar(10) DEFAULT NULL,
  `mobile` varchar(20) DEFAULT NULL,
  `email` varchar(60) DEFAULT NULL,
  `DELETED` tinyint(1) DEFAULT '0',
  `TENANT_ID` varchar(64) NOT NULL,
  `ACCOUNT_ID` varchar(64) DEFAULT NULL,
  `CREATED_BY` varchar(64) NOT NULL,
  `UPDATED_BY` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_crmp_contact_TENANT_ID` (`TENANT_ID`),
  KEY `FK_crmp_contact_UPDATED_BY` (`UPDATED_BY`),
  KEY `FK_crmp_contact_CREATED_BY` (`CREATED_BY`),
  KEY `FK_crmp_contact_ACCOUNT_ID` (`ACCOUNT_ID`),
  CONSTRAINT `FK_crmp_contact_ACCOUNT_ID` FOREIGN KEY (`ACCOUNT_ID`) REFERENCES `crmp_account` (`ID`),
  CONSTRAINT `FK_crmp_contact_CREATED_BY` FOREIGN KEY (`CREATED_BY`) REFERENCES `CRMP_User` (`ID`),
  CONSTRAINT `FK_crmp_contact_TENANT_ID` FOREIGN KEY (`TENANT_ID`) REFERENCES `CRMP_Tenant` (`ID`),
  CONSTRAINT `FK_crmp_contact_UPDATED_BY` FOREIGN KEY (`UPDATED_BY`) REFERENCES `CRMP_User` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crmp_contact`
--

LOCK TABLES `crmp_contact` WRITE;
/*!40000 ALTER TABLE `crmp_contact` DISABLE KEYS */;
INSERT INTO `crmp_contact` VALUES ('ee09ec0c-e317-489d-a7be-ea1d40110bfa',NULL,'2011-10-17 10:24:14',NULL,NULL,NULL,'UNSPECIFIED',NULL,NULL,NULL,0,'1','c8b24d44-e25a-4a9a-a65e-2a49e0444962','2',NULL);
/*!40000 ALTER TABLE `crmp_contact` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crmp_contract`
--

DROP TABLE IF EXISTS `crmp_contract`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crmp_contract` (
  `ID` varchar(64) NOT NULL,
  `UPDATED_ON` datetime DEFAULT NULL,
  `STATE` varchar(20) NOT NULL,
  `ACTIVE` tinyint(1) NOT NULL DEFAULT '0',
  `EFFECTIVE_END_DATE` datetime DEFAULT NULL,
  `VERSION` int(11) NOT NULL,
  `BILL_VOLUMN_BASELINE` int(11) DEFAULT NULL,
  `CREATED_ON` datetime DEFAULT NULL,
  `BILL_PERIOD_BASELINE_UNIT` varchar(20) DEFAULT NULL,
  `DELETED` tinyint(1) DEFAULT '0',
  `BILL_PERIOD_BASELINE` int(11) DEFAULT NULL,
  `NAME` varchar(50) NOT NULL,
  `PAYMENT_TERM` varchar(20) NOT NULL,
  `EFFECTIVE_START_DATE` datetime DEFAULT NULL,
  `TENANT_ID` varchar(64) NOT NULL,
  `SOLD_TO_ACCOUNT_ID` varchar(64) NOT NULL,
  `BILL_TO_ACCOUNT_ID` varchar(64) NOT NULL,
  `SHIP_TO_ACCOUNT_ID` varchar(64) NOT NULL,
  `PAYER_ACCOUNT_ID` varchar(64) NOT NULL,
  `BILL_VOLUMN_BASELINE_UNIT` varchar(64) DEFAULT NULL,
  `CREATED_BY` varchar(64) NOT NULL,
  `UPDATED_BY` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_crmp_contract_BILL_VOLUMN_BASELINE_UNIT` (`BILL_VOLUMN_BASELINE_UNIT`),
  KEY `FK_crmp_contract_PAYER_ACCOUNT_ID` (`PAYER_ACCOUNT_ID`),
  KEY `FK_crmp_contract_CREATED_BY` (`CREATED_BY`),
  KEY `FK_crmp_contract_SHIP_TO_ACCOUNT_ID` (`SHIP_TO_ACCOUNT_ID`),
  KEY `FK_crmp_contract_BILL_TO_ACCOUNT_ID` (`BILL_TO_ACCOUNT_ID`),
  KEY `FK_crmp_contract_SOLD_TO_ACCOUNT_ID` (`SOLD_TO_ACCOUNT_ID`),
  KEY `FK_crmp_contract_UPDATED_BY` (`UPDATED_BY`),
  KEY `FK_crmp_contract_TENANT_ID` (`TENANT_ID`),
  CONSTRAINT `FK_crmp_contract_TENANT_ID` FOREIGN KEY (`TENANT_ID`) REFERENCES `CRMP_Tenant` (`ID`),
  CONSTRAINT `FK_crmp_contract_BILL_TO_ACCOUNT_ID` FOREIGN KEY (`BILL_TO_ACCOUNT_ID`) REFERENCES `crmp_account` (`ID`),
  CONSTRAINT `FK_crmp_contract_BILL_VOLUMN_BASELINE_UNIT` FOREIGN KEY (`BILL_VOLUMN_BASELINE_UNIT`) REFERENCES `crmp_unit_of_measure` (`ID`),
  CONSTRAINT `FK_crmp_contract_CREATED_BY` FOREIGN KEY (`CREATED_BY`) REFERENCES `CRMP_User` (`ID`),
  CONSTRAINT `FK_crmp_contract_PAYER_ACCOUNT_ID` FOREIGN KEY (`PAYER_ACCOUNT_ID`) REFERENCES `crmp_account` (`ID`),
  CONSTRAINT `FK_crmp_contract_SHIP_TO_ACCOUNT_ID` FOREIGN KEY (`SHIP_TO_ACCOUNT_ID`) REFERENCES `crmp_account` (`ID`),
  CONSTRAINT `FK_crmp_contract_SOLD_TO_ACCOUNT_ID` FOREIGN KEY (`SOLD_TO_ACCOUNT_ID`) REFERENCES `crmp_account` (`ID`),
  CONSTRAINT `FK_crmp_contract_UPDATED_BY` FOREIGN KEY (`UPDATED_BY`) REFERENCES `CRMP_User` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crmp_contract`
--

LOCK TABLES `crmp_contract` WRITE;
/*!40000 ALTER TABLE `crmp_contract` DISABLE KEYS */;
/*!40000 ALTER TABLE `crmp_contract` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crmp_contract_rate_plan_component`
--

DROP TABLE IF EXISTS `crmp_contract_rate_plan_component`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crmp_contract_rate_plan_component` (
  `ID` varchar(64) NOT NULL,
  `ACTIVE` tinyint(1) NOT NULL DEFAULT '0',
  `CREATED_ON` datetime DEFAULT NULL,
  `CHARGE_TYPE` varchar(20) NOT NULL,
  `CHARGE_MODEL` varchar(20) NOT NULL,
  `CHARGE_RATE` double DEFAULT NULL,
  `UPDATED_ON` datetime DEFAULT NULL,
  `DELETED` tinyint(1) DEFAULT '0',
  `MERCHANDISE_ID` varchar(64) NOT NULL,
  `CONTRACT_ID` varchar(64) NOT NULL,
  `CREATED_BY` varchar(64) NOT NULL,
  `UNIT_OF_MEASURE_ID` varchar(64) NOT NULL,
  `TENANT_ID` varchar(64) NOT NULL,
  `UPDATED_BY` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_crmp_contract_rate_plan_component_CREATED_BY` (`CREATED_BY`),
  KEY `crmpcontract_rate_plan_componentUNIT_OF_MEASURE_ID` (`UNIT_OF_MEASURE_ID`),
  KEY `FK_crmp_contract_rate_plan_component_CONTRACT_ID` (`CONTRACT_ID`),
  KEY `FK_crmp_contract_rate_plan_component_TENANT_ID` (`TENANT_ID`),
  KEY `FK_crmp_contract_rate_plan_component_UPDATED_BY` (`UPDATED_BY`),
  KEY `crmp_contract_rate_plan_component_MERCHANDISE_ID` (`MERCHANDISE_ID`),
  CONSTRAINT `crmp_contract_rate_plan_component_MERCHANDISE_ID` FOREIGN KEY (`MERCHANDISE_ID`) REFERENCES `crmp_product` (`ID`),
  CONSTRAINT `crmpcontract_rate_plan_componentUNIT_OF_MEASURE_ID` FOREIGN KEY (`UNIT_OF_MEASURE_ID`) REFERENCES `crmp_unit_of_measure` (`ID`),
  CONSTRAINT `FK_crmp_contract_rate_plan_component_CONTRACT_ID` FOREIGN KEY (`CONTRACT_ID`) REFERENCES `crmp_contract` (`ID`),
  CONSTRAINT `FK_crmp_contract_rate_plan_component_CREATED_BY` FOREIGN KEY (`CREATED_BY`) REFERENCES `CRMP_User` (`ID`),
  CONSTRAINT `FK_crmp_contract_rate_plan_component_TENANT_ID` FOREIGN KEY (`TENANT_ID`) REFERENCES `CRMP_Tenant` (`ID`),
  CONSTRAINT `FK_crmp_contract_rate_plan_component_UPDATED_BY` FOREIGN KEY (`UPDATED_BY`) REFERENCES `CRMP_User` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crmp_contract_rate_plan_component`
--

LOCK TABLES `crmp_contract_rate_plan_component` WRITE;
/*!40000 ALTER TABLE `crmp_contract_rate_plan_component` DISABLE KEYS */;
/*!40000 ALTER TABLE `crmp_contract_rate_plan_component` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crmp_district`
--

DROP TABLE IF EXISTS `crmp_district`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crmp_district` (
  `ID` varchar(64) NOT NULL,
  `name` varchar(100) NOT NULL,
  `CREATED_ON` datetime DEFAULT NULL,
  `UPDATED_ON` datetime DEFAULT NULL,
  `DELETED` tinyint(1) DEFAULT '0',
  `TENANT_ID` varchar(64) NOT NULL,
  `city_id` varchar(64) DEFAULT NULL,
  `CREATED_BY` varchar(64) NOT NULL,
  `UPDATED_BY` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_crmp_district_CREATED_BY` (`CREATED_BY`),
  KEY `FK_crmp_district_TENANT_ID` (`TENANT_ID`),
  KEY `FK_crmp_district_city_id` (`city_id`),
  KEY `FK_crmp_district_UPDATED_BY` (`UPDATED_BY`),
  CONSTRAINT `FK_crmp_district_UPDATED_BY` FOREIGN KEY (`UPDATED_BY`) REFERENCES `CRMP_User` (`ID`),
  CONSTRAINT `FK_crmp_district_city_id` FOREIGN KEY (`city_id`) REFERENCES `crmp_city` (`ID`),
  CONSTRAINT `FK_crmp_district_CREATED_BY` FOREIGN KEY (`CREATED_BY`) REFERENCES `CRMP_User` (`ID`),
  CONSTRAINT `FK_crmp_district_TENANT_ID` FOREIGN KEY (`TENANT_ID`) REFERENCES `CRMP_Tenant` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crmp_district`
--

LOCK TABLES `crmp_district` WRITE;
/*!40000 ALTER TABLE `crmp_district` DISABLE KEYS */;
/*!40000 ALTER TABLE `crmp_district` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crmp_invoice`
--

DROP TABLE IF EXISTS `crmp_invoice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crmp_invoice` (
  `ID` varchar(64) NOT NULL,
  `INVOICE_NUMBER` varchar(12) NOT NULL,
  `TOTAL_AMOUNT` double DEFAULT NULL,
  `UNPAID_AMOUNT` double DEFAULT NULL,
  `DUEDATE` datetime DEFAULT NULL,
  `CREATED_ON` datetime DEFAULT NULL,
  `TOTAL_QUANTITY` double DEFAULT NULL,
  `DELETED` tinyint(1) DEFAULT '0',
  `POSTDATE` datetime DEFAULT NULL,
  `UPDATED_ON` datetime DEFAULT NULL,
  `STATE` varchar(20) NOT NULL,
  `REFERENCE_NUMBER` varchar(60) DEFAULT NULL,
  `PAYER_ACCOUNT_ID` varchar(64) NOT NULL,
  `CREATED_BY` varchar(64) NOT NULL,
  `SHIP_TO_ACCOUNT_ID` varchar(64) NOT NULL,
  `SOLD_TO_ACCOUNT_ID` varchar(64) NOT NULL,
  `BILL_TO_ACCOUNT_ID` varchar(64) NOT NULL,
  `TENANT_ID` varchar(64) NOT NULL,
  `UPDATED_BY` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_crmp_invoice_SOLD_TO_ACCOUNT_ID` (`SOLD_TO_ACCOUNT_ID`),
  KEY `FK_crmp_invoice_SHIP_TO_ACCOUNT_ID` (`SHIP_TO_ACCOUNT_ID`),
  KEY `FK_crmp_invoice_TENANT_ID` (`TENANT_ID`),
  KEY `FK_crmp_invoice_BILL_TO_ACCOUNT_ID` (`BILL_TO_ACCOUNT_ID`),
  KEY `FK_crmp_invoice_PAYER_ACCOUNT_ID` (`PAYER_ACCOUNT_ID`),
  KEY `FK_crmp_invoice_CREATED_BY` (`CREATED_BY`),
  KEY `FK_crmp_invoice_UPDATED_BY` (`UPDATED_BY`),
  CONSTRAINT `FK_crmp_invoice_UPDATED_BY` FOREIGN KEY (`UPDATED_BY`) REFERENCES `CRMP_User` (`ID`),
  CONSTRAINT `FK_crmp_invoice_BILL_TO_ACCOUNT_ID` FOREIGN KEY (`BILL_TO_ACCOUNT_ID`) REFERENCES `crmp_account` (`ID`),
  CONSTRAINT `FK_crmp_invoice_CREATED_BY` FOREIGN KEY (`CREATED_BY`) REFERENCES `CRMP_User` (`ID`),
  CONSTRAINT `FK_crmp_invoice_PAYER_ACCOUNT_ID` FOREIGN KEY (`PAYER_ACCOUNT_ID`) REFERENCES `crmp_account` (`ID`),
  CONSTRAINT `FK_crmp_invoice_SHIP_TO_ACCOUNT_ID` FOREIGN KEY (`SHIP_TO_ACCOUNT_ID`) REFERENCES `crmp_account` (`ID`),
  CONSTRAINT `FK_crmp_invoice_SOLD_TO_ACCOUNT_ID` FOREIGN KEY (`SOLD_TO_ACCOUNT_ID`) REFERENCES `crmp_account` (`ID`),
  CONSTRAINT `FK_crmp_invoice_TENANT_ID` FOREIGN KEY (`TENANT_ID`) REFERENCES `CRMP_Tenant` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crmp_invoice`
--

LOCK TABLES `crmp_invoice` WRITE;
/*!40000 ALTER TABLE `crmp_invoice` DISABLE KEYS */;
/*!40000 ALTER TABLE `crmp_invoice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crmp_invoice_item`
--

DROP TABLE IF EXISTS `crmp_invoice_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crmp_invoice_item` (
  `ID` varchar(64) NOT NULL,
  `AMOUNT` double NOT NULL,
  `CREATED_ON` datetime DEFAULT NULL,
  `STATE` tinyint(1) NOT NULL DEFAULT '0',
  `UPDATED_ON` datetime DEFAULT NULL,
  `QUANTITY` double NOT NULL,
  `DELETED` tinyint(1) DEFAULT '0',
  `MERCHANDISE_ID` varchar(64) NOT NULL,
  `INVOICE_ID` varchar(64) NOT NULL,
  `TENANT_ID` varchar(64) NOT NULL,
  `UNIT_OF_MEASURE_ID` varchar(64) NOT NULL,
  `CREATED_BY` varchar(64) NOT NULL,
  `UPDATED_BY` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_crmp_invoice_item_UPDATED_BY` (`UPDATED_BY`),
  KEY `FK_crmp_invoice_item_CREATED_BY` (`CREATED_BY`),
  KEY `FK_crmp_invoice_item_TENANT_ID` (`TENANT_ID`),
  KEY `FK_crmp_invoice_item_UNIT_OF_MEASURE_ID` (`UNIT_OF_MEASURE_ID`),
  KEY `FK_crmp_invoice_item_INVOICE_ID` (`INVOICE_ID`),
  KEY `FK_crmp_invoice_item_MERCHANDISE_ID` (`MERCHANDISE_ID`),
  CONSTRAINT `FK_crmp_invoice_item_MERCHANDISE_ID` FOREIGN KEY (`MERCHANDISE_ID`) REFERENCES `crmp_product` (`ID`),
  CONSTRAINT `FK_crmp_invoice_item_CREATED_BY` FOREIGN KEY (`CREATED_BY`) REFERENCES `CRMP_User` (`ID`),
  CONSTRAINT `FK_crmp_invoice_item_INVOICE_ID` FOREIGN KEY (`INVOICE_ID`) REFERENCES `crmp_invoice` (`ID`),
  CONSTRAINT `FK_crmp_invoice_item_TENANT_ID` FOREIGN KEY (`TENANT_ID`) REFERENCES `CRMP_Tenant` (`ID`),
  CONSTRAINT `FK_crmp_invoice_item_UNIT_OF_MEASURE_ID` FOREIGN KEY (`UNIT_OF_MEASURE_ID`) REFERENCES `crmp_unit_of_measure` (`ID`),
  CONSTRAINT `FK_crmp_invoice_item_UPDATED_BY` FOREIGN KEY (`UPDATED_BY`) REFERENCES `CRMP_User` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crmp_invoice_item`
--

LOCK TABLES `crmp_invoice_item` WRITE;
/*!40000 ALTER TABLE `crmp_invoice_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `crmp_invoice_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crmp_meta_column_descriptor`
--

DROP TABLE IF EXISTS `crmp_meta_column_descriptor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crmp_meta_column_descriptor` (
  `ID` varchar(64) NOT NULL,
  `COLUMN_PRECISION` int(11) DEFAULT NULL,
  `CREATED_ON` datetime DEFAULT NULL,
  `MIN_VERSION` int(11) DEFAULT NULL,
  `UPDATED_ON` datetime DEFAULT NULL,
  `MAX_VERSION` int(11) DEFAULT NULL,
  `COLUMN_NAME` varchar(255) DEFAULT NULL,
  `AUTO_INCREMENTAL` tinyint(1) DEFAULT '0',
  `COLUMN_TYPE` varchar(255) DEFAULT NULL,
  `KEY_COLUMN` tinyint(1) DEFAULT '0',
  `COLUMN_DISPLAY_SIZE` int(11) DEFAULT NULL,
  `PRIMARY_KEY_COLUMN` tinyint(1) DEFAULT '0',
  `DELETED` tinyint(1) DEFAULT '0',
  `COLUMN_TYPE_NAME` varchar(255) DEFAULT NULL,
  `COLUMN_LABEL` varchar(255) DEFAULT NULL,
  `TABLE_ID` varchar(64) DEFAULT NULL,
  `CREATED_BY` varchar(64) NOT NULL,
  `TENANT_ID` varchar(64) NOT NULL,
  `UPDATED_BY` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_crmp_meta_column_descriptor_UPDATED_BY` (`UPDATED_BY`),
  KEY `FK_crmp_meta_column_descriptor_TENANT_ID` (`TENANT_ID`),
  KEY `FK_crmp_meta_column_descriptor_CREATED_BY` (`CREATED_BY`),
  KEY `FK_crmp_meta_column_descriptor_TABLE_ID` (`TABLE_ID`),
  CONSTRAINT `FK_crmp_meta_column_descriptor_TABLE_ID` FOREIGN KEY (`TABLE_ID`) REFERENCES `crmp_meta_table_descriptor` (`ID`),
  CONSTRAINT `FK_crmp_meta_column_descriptor_CREATED_BY` FOREIGN KEY (`CREATED_BY`) REFERENCES `CRMP_User` (`ID`),
  CONSTRAINT `FK_crmp_meta_column_descriptor_TENANT_ID` FOREIGN KEY (`TENANT_ID`) REFERENCES `CRMP_Tenant` (`ID`),
  CONSTRAINT `FK_crmp_meta_column_descriptor_UPDATED_BY` FOREIGN KEY (`UPDATED_BY`) REFERENCES `CRMP_User` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crmp_meta_column_descriptor`
--

LOCK TABLES `crmp_meta_column_descriptor` WRITE;
/*!40000 ALTER TABLE `crmp_meta_column_descriptor` DISABLE KEYS */;
INSERT INTO `crmp_meta_column_descriptor` VALUES ('00124c57-cad2-4a1b-ba3e-f95feacda2cc',15,'2011-10-17 10:16:05',0,NULL,0,'JDWC5',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('0017d337-75f7-4db2-b56c-11de1986c5cf',50,'2011-10-17 10:16:05',0,NULL,0,'Str16',0,NULL,0,50,0,0,'VARCHAR',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('0142f0cd-fa2a-4636-baec-686b134d1a7b',15,'2011-10-17 10:16:05',0,NULL,0,'CLZ_WJB1',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('01543c5a-abad-400f-bf34-3c06626a7474',15,'2011-10-17 10:16:05',0,NULL,0,'CLZ_WJC1',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('0157185d-ae89-49c2-a17c-202a2d2096d3',15,'2011-10-17 10:16:05',0,NULL,0,'MBZ14',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('01a0e45f-2e42-4d3b-aec0-9e841dd01256',15,'2011-10-17 10:16:05',0,NULL,0,'JDZ_WJC2',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('03008b28-db87-4c54-8e28-fd16af50e670',15,'2011-10-17 10:16:05',0,NULL,0,'JDZ_WJA4',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('032b9de7-9ed2-4e49-a8bb-0a4127e85a90',15,'2011-10-17 10:16:05',0,NULL,0,'JDWC12',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('032e0a61-217a-4487-8376-d1a391226e1b',15,'2011-10-17 10:16:05',0,NULL,0,'MBZ_GL7',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('03c20e18-c621-44d9-8bef-c76ccd18d09c',15,'2011-10-17 10:16:05',0,NULL,0,'CLZ22',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('03ecbea6-c529-4aba-b246-c73be86a97f4',30,'2011-10-17 10:16:05',0,NULL,0,'XiangDuiValue',0,NULL,0,30,0,0,'VARCHAR',NULL,'8b8974af-28fd-4201-bfb8-e1a3ae9524ae','2','1',NULL),('05c269d1-6dac-492c-8a65-ecbe76d447ef',60,'2011-10-17 10:16:05',0,NULL,0,'NAM_GL8',0,NULL,0,60,0,0,'VARCHAR',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('060fe828-8427-4554-b80d-437261bf54df',15,'2011-10-17 10:16:05',0,NULL,0,'XDZ_Shui1',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('07f4d50d-65a7-4b63-9b11-ff6f0c99bfe2',15,'2011-10-17 10:16:05',0,NULL,0,'XDZ_WJB3',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('09225e07-d828-41fa-82e3-9d126bfb190c',60,'2011-10-17 10:16:05',0,NULL,0,'NAM5',0,NULL,0,60,0,0,'VARCHAR',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('0a7c2556-e22e-4cd7-9b94-c9c82d953230',15,'2011-10-17 10:16:05',0,NULL,0,'ShiJiValue',0,NULL,0,22,0,0,'DOUBLE',NULL,'8b8974af-28fd-4201-bfb8-e1a3ae9524ae','2','1',NULL),('0bf76d1a-8a5d-44ab-a8b4-6a39f9037b4b',60,'2011-10-17 10:16:05',0,NULL,0,'NAM1',0,NULL,0,60,0,0,'VARCHAR',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('0cf5d106-d602-4c1f-8f3d-09179284355c',50,'2011-10-17 10:16:05',0,NULL,0,'Str11',0,NULL,0,50,0,0,'VARCHAR',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('0d4a9535-bc28-46f5-a64e-61fdb5098707',15,'2011-10-17 10:16:05',0,NULL,0,'XDZ_WJB4',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('0e1e363d-5c8d-44fd-8ba1-1361131617ea',10,'2011-10-17 10:16:05',0,NULL,0,'SaJiangSymbol',0,NULL,0,11,0,0,'INTEGER',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('101e9201-c2ce-43aa-9713-27020b50aede',15,'2011-10-17 10:16:05',0,NULL,0,'JDZ_WJC4',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('11d9c524-9bfd-455e-8ce0-74e4b36748e0',60,'2011-10-17 10:16:05',0,NULL,0,'NAM11',0,NULL,0,60,0,0,'VARCHAR',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('1266b159-4688-4377-92b0-5a654b3100c4',15,'2011-10-17 10:16:05',0,NULL,0,'XDWC17',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('131fe08d-1a17-4aa5-bc6c-9beb96d7648c',60,'2011-10-17 10:16:05',0,NULL,0,'NAM20',0,NULL,0,60,0,0,'VARCHAR',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('133dda3d-5fa8-4e7e-b0d0-4f5e7b5f2a08',15,'2011-10-17 10:16:05',0,NULL,0,'JDWC1',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('16398924-295e-4ba3-a792-80dbf6a428d0',15,'2011-10-17 10:16:05',0,NULL,0,'MBZ_WJC1',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('17bb83eb-b29d-478f-b7c2-d51d175636c1',15,'2011-10-17 10:16:05',0,NULL,0,'XDWC2',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('17c4292a-c220-4d30-af2e-a7a297a4e7c7',15,'2011-10-17 10:16:05',0,NULL,0,'XDWC15',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('1a20e46b-9eb9-434f-9a39-b96388e2cf9d',60,'2011-10-17 10:16:05',0,NULL,0,'NAM12',0,NULL,0,60,0,0,'VARCHAR',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('1a32f7ab-2305-44f0-b450-219d96a75cde',15,'2011-10-17 10:16:05',0,NULL,0,'HSL_GL4',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('1afc65cf-8395-4d2e-9c78-9905a4031ade',15,'2011-10-17 10:16:05',0,NULL,0,'XDWC4',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('1ccdf2c2-fd85-4fa3-bf72-9546b14a0d8a',15,'2011-10-17 10:16:05',0,NULL,0,'MBZ17',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('1e56e0bd-52b0-4695-b97b-b539d9361942',15,'2011-10-17 10:16:05',0,NULL,0,'JDWC14',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('1e84fdb2-5167-4369-9c6a-9f0c9bc2ff49',15,'2011-10-17 10:16:05',0,NULL,0,'CLZ_WJA4',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('1ec0be0e-56e3-414d-9f6d-b56cd3070b07',15,'2011-10-17 10:16:05',0,NULL,0,'HGL_WJA1',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('1f9b215f-ff9c-4019-bb22-71523f607bc9',15,'2011-10-17 10:16:05',0,NULL,0,'MBZ21',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('20c38590-b288-4fb3-af4b-82fd3e1d96e5',50,'2011-10-17 10:16:05',0,NULL,0,'Str7',0,NULL,0,50,0,0,'VARCHAR',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('20d0513d-685a-427b-ab9e-1a069f7913a4',15,'2011-10-17 10:16:05',0,NULL,0,'HGL_WJB2',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('211c29f0-3243-46bf-ba84-fb4a90c2ed05',15,'2011-10-17 10:16:05',0,NULL,0,'HGL_WJA4',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('21f950cf-e229-40bc-afad-8ea13cdd12fd',15,'2011-10-17 10:16:05',0,NULL,0,'CLZ_FLC1',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('22fa53a3-7b2e-40df-9ab1-1eebace6ae86',15,'2011-10-17 10:16:05',0,NULL,0,'JDWC9',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('231f0d43-9a44-4736-b415-8025238c0992',15,'2011-10-17 10:16:05',0,NULL,0,'CLZ7',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('2342f089-d110-4fac-b4ae-1a6c4fbba3f0',15,'2011-10-17 10:16:05',0,NULL,0,'JDWC8',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('2379f7b9-bfab-4e39-b0c8-ae99949283bb',15,'2011-10-17 10:16:05',0,NULL,0,'JDZ_WJB2',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('244e7764-bbf6-4ff0-8ed8-37ef96e95097',15,'2011-10-17 10:16:05',0,NULL,0,'CLZ15',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('249c8484-5db7-47f5-87c2-c65508fe1cb6',60,'2011-10-17 10:16:05',0,NULL,0,'NAM_WJC3',0,NULL,0,60,0,0,'VARCHAR',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('25190927-e1e0-499b-bddd-ee87210704d7',15,'2011-10-17 10:16:05',0,NULL,0,'XDZ_GL6',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('25b37d68-198d-4780-92d7-ef36e85f068c',60,'2011-10-17 10:16:05',0,NULL,0,'NAM19',0,NULL,0,60,0,0,'VARCHAR',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('2787b090-a9a4-4b9e-9eac-c46da78a0e0c',15,'2011-10-17 10:16:05',0,NULL,0,'MBZ11',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('28463063-b482-45c4-9f73-7d5a302cab95',50,'2011-10-17 10:16:05',0,NULL,0,'Tech_Req',0,NULL,0,50,0,0,'VARCHAR',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('28a75283-94f0-46e3-82e9-ede139b5974b',15,'2011-10-17 10:16:05',0,NULL,0,'HSL_GL5',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('2b30708d-0767-4161-bf3e-9cd1829256eb',15,'2011-10-17 10:16:05',0,NULL,0,'JDWC10',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('2c5c641e-cc34-415f-95b9-7e8e4d171395',15,'2011-10-17 10:16:05',0,NULL,0,'MBZ_GL5',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('2d4da342-8721-42be-b9e2-e8fc47b28caf',60,'2011-10-17 10:16:05',0,NULL,0,'NAM_GL5',0,NULL,0,60,0,0,'VARCHAR',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('2dafff18-02f7-4aba-bf86-5b6a73466058',15,'2011-10-17 10:16:05',0,NULL,0,'MBZ10',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('2dbfb85d-b4e2-490c-a80f-e0343ffb64ec',50,'2011-10-17 10:16:05',0,NULL,0,'KangShenLevel',0,NULL,0,50,0,0,'VARCHAR',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('2ea6457c-9286-4020-814f-9d4999cc68cb',15,'2011-10-17 10:16:05',0,NULL,0,'CLZ4',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('333ef3e3-6ba5-4f83-a321-e552e217c222',15,'2011-10-17 10:16:05',0,NULL,0,'JDZ_WJC3',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('3349b705-8470-4543-a353-184a9fb0c02b',15,'2011-10-17 10:16:05',0,NULL,0,'XDZ_GL7',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('33a27236-f78c-43ec-9367-fa2d4e7e67d2',60,'2011-10-17 10:16:05',0,NULL,0,'NAM_Shui2',0,NULL,0,60,0,0,'VARCHAR',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('33b3d315-126a-4507-b4a1-55c15033f0b2',15,'2011-10-17 10:16:05',0,NULL,0,'JDZ_FLA3',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('33c65d3f-9dd0-408a-8796-018437ad90e2',15,'2011-10-17 10:16:05',0,NULL,0,'HSL_GL7',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('346595e1-ebfc-43d2-bd29-7651a1144f49',15,'2011-10-17 10:16:05',0,NULL,0,'MBZ_FLA2',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('362d8919-61e8-4f93-a1f7-af2d7c4a1fdc',60,'2011-10-17 10:16:05',0,NULL,0,'NAM_WJA2',0,NULL,0,60,0,0,'VARCHAR',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('37c665b0-07a2-4e96-a04b-5d241701b0a3',60,'2011-10-17 10:16:05',0,NULL,0,'NAM4',0,NULL,0,60,0,0,'VARCHAR',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('383eff86-57ee-4c8a-88a1-f6e174a53f26',15,'2011-10-17 10:16:05',0,NULL,0,'JDWC16',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('3a97ecc2-3848-48e2-9d94-5a2e07986d7e',15,'2011-10-17 10:16:05',0,NULL,0,'XDZ_WJC1',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('3b186d53-17fe-4dba-9c58-e5de3525d645',15,'2011-10-17 10:16:05',0,NULL,0,'XDZ_FLB3',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('3b8818f3-37bf-4b0e-af83-d20b80e78f9f',15,'2011-10-17 10:16:05',0,NULL,0,'CLZ11',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('3c24496e-27ff-4991-84c3-dbfd0196f41e',15,'2011-10-17 10:16:05',0,NULL,0,'CLZ5',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('3d136e65-1cf2-430c-9dde-1693a08908f1',15,'2011-10-17 10:16:05',0,NULL,0,'HGL_WJC3',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('3dd193c6-69f0-4777-8dac-648a7a3b64fa',15,'2011-10-17 10:16:05',0,NULL,0,'JDWC11',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('3e5a0269-68bc-41b6-9841-caa625348e96',15,'2011-10-17 10:16:05',0,NULL,0,'XDZ_FLA2',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('3ea9a9c2-90c8-4505-a8cb-7bca9316b4f8',15,'2011-10-17 10:16:05',0,NULL,0,'JDWC13',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('3fcd6f6b-6cf9-44bd-86ac-d79e95f04275',50,'2011-10-17 10:16:05',0,NULL,0,'ProduceLine',0,NULL,0,50,0,0,'VARCHAR',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('3fe47ead-074f-4b33-84b1-0ed37da6b597',15,'2011-10-17 10:16:05',0,NULL,0,'CLZ2',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('402df218-cd50-4817-8f45-997f315a44d9',15,'2011-10-17 10:16:05',0,NULL,0,'HSL_GL3',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('405a327e-690c-4324-a9d8-38d306531b73',15,'2011-10-17 10:16:05',0,NULL,0,'HGL_WJC2',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('41a1997e-4265-4e38-bd21-5fa6e8432f77',15,'2011-10-17 10:16:05',0,NULL,0,'XDWC5',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('41b46d6b-41b1-4dbf-8a1f-d2afb8c69739',15,'2011-10-17 10:16:05',0,NULL,0,'XDWC18',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('42c0fdc1-7e55-4471-a77b-e231fd94d852',15,'2011-10-17 10:16:05',0,NULL,0,'XDZ_GL5',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('434bfb60-72fb-4716-9fa5-e5455687c2a7',15,'2011-10-17 10:16:05',0,NULL,0,'XDWC7',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('45555590-4fea-4a56-8657-59453ca458fc',15,'2011-10-17 10:16:05',0,NULL,0,'OperationState',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('4556dee2-08c6-4373-bcfd-0f5b0ee05de4',60,'2011-10-17 10:16:05',0,NULL,0,'NAM21',0,NULL,0,60,0,0,'VARCHAR',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('467d3be3-e385-4b08-963a-2002e3807c8d',10,'2011-10-17 10:16:05',0,NULL,0,'BJ2',0,NULL,0,11,0,0,'INTEGER',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('473ff4e9-aad2-4f6f-a255-3858afcddaf0',15,'2011-10-17 10:16:05',0,NULL,0,'MBZ_WJB4',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('476ac9d1-3c59-4789-b080-4b4e836f1924',15,'2011-10-17 10:16:05',0,NULL,0,'JDZ_WJB3',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('4847ef57-154d-4e02-9be2-a37b63cb6e1d',60,'2011-10-17 10:16:05',0,NULL,0,'NAM_WJB2',0,NULL,0,60,0,0,'VARCHAR',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('48608d06-fea0-40d2-9883-867b124b9ecc',15,'2011-10-17 10:16:05',0,NULL,0,'CLZ_GL7',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('48df7dc7-b515-4b28-a9cc-0189f982ae57',50,'2011-10-17 10:16:05',0,NULL,0,'Str4',0,NULL,0,50,0,0,'VARCHAR',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('4cda92da-fa0e-47bd-93dc-c5e9bcc2df00',15,'2011-10-17 10:16:05',0,NULL,0,'MBZ19',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('4d6d7809-d985-42b9-b9b4-ce42cc85d5e3',60,'2011-10-17 10:16:05',0,NULL,0,'NAM16',0,NULL,0,60,0,0,'VARCHAR',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('4e431b21-a996-4483-8508-18c781a2959e',15,'2011-10-17 10:16:05',0,NULL,0,'MBZ_FLB2',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('4e899d5f-0811-4c0f-a28a-a556d61d486e',60,'2011-10-17 10:16:05',0,NULL,0,'NAM10',0,NULL,0,60,0,0,'VARCHAR',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('4e8bbf04-beb0-407c-92bb-baf2da1d1ea3',50,'2011-10-17 10:16:05',0,NULL,0,'Operator',0,NULL,0,50,0,0,'VARCHAR',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('4f057586-59f7-412c-b563-0b4511048d1b',15,'2011-10-17 10:16:05',0,NULL,0,'MBZ_WJA3',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('4f325388-270a-456e-ade8-e577d01c51aa',15,'2011-10-17 10:16:05',0,NULL,0,'MBZ_GL4',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('4f5b1832-9614-4ea2-becb-16a2a36ee622',15,'2011-10-17 10:16:05',0,NULL,0,'CLZ_WJB3',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('4fccf92a-e60b-4e35-b24a-97bb1db2ed9c',60,'2011-10-17 10:16:05',0,NULL,0,'NAM_GL1',0,NULL,0,60,0,0,'VARCHAR',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('5015be2d-96d9-4e87-b5a7-e56385e7be87',15,'2011-10-17 10:16:05',0,NULL,0,'CLZ_GL2',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('5060fa2d-dd11-486a-b685-047cf358cd34',50,'2011-10-17 10:16:05',0,NULL,0,'Remark',0,NULL,0,50,0,0,'VARCHAR',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('50868311-0415-45f3-aaef-b9705f1c2ee5',60,'2011-10-17 10:16:05',0,NULL,0,'NAM14',0,NULL,0,60,0,0,'VARCHAR',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('53c5e100-672e-4341-a4ca-0e996ed0c224',15,'2011-10-17 10:16:05',0,NULL,0,'MBZ_FLA3',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('546c7872-f246-43cf-844d-5f05edcfd8df',15,'2011-10-17 10:16:05',0,NULL,0,'JDZ_FLA1',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('54b68aa0-2d9c-4746-a9a2-42fc60acf25b',15,'2011-10-17 10:16:05',0,NULL,0,'XDWC14',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('54f22d2c-2ff0-4507-a2e2-f3a7b666a964',10,'2011-10-17 10:16:05',0,NULL,0,'BJ7',0,NULL,0,11,0,0,'INTEGER',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('553e3a57-f46a-45ff-a1db-45bd1ceda984',60,'2011-10-17 10:16:05',0,NULL,0,'NAM_FLA2',0,NULL,0,60,0,0,'VARCHAR',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('5562dab3-e943-42ed-9cd3-70202a0f0bf3',60,'2011-10-17 10:16:05',0,NULL,0,'MaterialName',0,NULL,0,60,0,0,'VARCHAR',NULL,'8b8974af-28fd-4201-bfb8-e1a3ae9524ae','2','1',NULL),('560afc00-01ab-493e-ace7-dd343973a794',15,'2011-10-17 10:16:05',0,NULL,0,'MBZ_GL6',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('56cde39e-28b1-466e-87be-1e12441763c6',15,'2011-10-17 10:16:05',0,NULL,0,'CLZ_Shui1',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('56ce23ce-8345-46a1-aabd-344715dca467',60,'2011-10-17 10:16:05',0,NULL,0,'NAM_GL3',0,NULL,0,60,0,0,'VARCHAR',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('56d4a447-815f-4436-8404-f84c5fde63b3',15,'2011-10-17 10:16:05',0,NULL,0,'MBZ3',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('570b1a47-82de-457c-a681-4c9fa12d3337',15,'2011-10-17 10:16:05',0,NULL,0,'HSL_GL2',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('57d8aa98-833f-47ef-ab7d-1729b6988d35',15,'2011-10-17 10:16:05',0,NULL,0,'XDZ_WJC3',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('5863ddd7-e15b-4e69-bfd2-4cf063b70303',60,'2011-10-17 10:16:05',0,NULL,0,'NAM15',0,NULL,0,60,0,0,'VARCHAR',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('58a20ac7-2586-4c35-b5b6-0a6d63794d96',15,'2011-10-17 10:16:05',0,NULL,0,'JieDuiValue',0,NULL,0,22,0,0,'DOUBLE',NULL,'8b8974af-28fd-4201-bfb8-e1a3ae9524ae','2','1',NULL),('5ae68ade-24d6-4e50-b0c4-20e142c9aae9',60,'2011-10-17 10:16:05',0,NULL,0,'NAM_WJC4',0,NULL,0,60,0,0,'VARCHAR',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('5bf76db8-c351-4010-a1a6-d5d410f47680',15,'2011-10-17 10:16:05',0,NULL,0,'MBZ_FLA4',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('5c9ca4ea-53a9-4ca8-a2ac-921ee8592bbb',15,'2011-10-17 10:16:05',0,NULL,0,'CLZ_FLA4',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('5d7452c8-43c0-4e64-8f56-1616c3c034bf',60,'2011-10-17 10:16:05',0,NULL,0,'NAM_WJA1',0,NULL,0,60,0,0,'VARCHAR',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('5fbb78b0-eabe-4bbc-a8c5-c57942b8324e',15,'2011-10-17 10:16:05',0,NULL,0,'Truck_vol',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('6357c896-2ba8-4b73-a7c4-053144053867',15,'2011-10-17 10:16:05',0,NULL,0,'XDWC22',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('63a5490c-af64-4bfc-b0bd-434b0ef3fff5',15,'2011-10-17 10:16:05',0,NULL,0,'XDZ_GL4',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('64ead6b4-7c4c-45da-942c-0bcf57f1d1b3',60,'2011-10-17 10:16:05',0,NULL,0,'NAM_WJB1',0,NULL,0,60,0,0,'VARCHAR',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('652fa22f-a256-41ed-815e-02e77d3d770b',15,'2011-10-17 10:16:05',0,NULL,0,'CLZ_FLB1',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('65db957d-ad0c-4c3e-89fa-f4d4e9ec1d28',15,'2011-10-17 10:16:05',0,NULL,0,'JDZ_GL3',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('662614d7-1404-431e-bea5-762dc9fcb0f1',60,'2011-10-17 10:16:05',0,NULL,0,'NAM_FLC2',0,NULL,0,60,0,0,'VARCHAR',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('66991a37-156d-4289-9a29-c6c35b3c34fe',15,'2011-10-17 10:16:05',0,NULL,0,'CLZ_WJC3',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('66eef36e-4ee1-411b-af5b-e5a600338f5d',15,'2011-10-17 10:16:05',0,NULL,0,'XDZ_FLA1',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('676c9cff-d2cf-4411-a943-ec105ac307ba',15,'2011-10-17 10:16:05',0,NULL,0,'XDZ_GL8',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('681e31c6-2520-4c68-a66e-cc06a9d14de1',15,'2011-10-17 10:16:05',0,NULL,0,'MBZ18',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('68de8722-1620-41bd-aa65-0a20d2130142',15,'2011-10-17 10:16:05',0,NULL,0,'MBZ_WJA4',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('68e221cb-4f70-4f88-be1f-512aace0e07e',60,'2011-10-17 10:16:05',0,NULL,0,'NAM_GL2',0,NULL,0,60,0,0,'VARCHAR',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('6933236b-c85c-4aac-9025-3c12e9f7e426',60,'2011-10-17 10:16:05',0,NULL,0,'NAM9',0,NULL,0,60,0,0,'VARCHAR',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('699790d9-0d8f-4ebf-b24b-a3fbf126a92e',15,'2011-10-17 10:16:05',0,NULL,0,'XDZ_Shui2',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('6b717af0-fc83-457e-b84d-af020bfa2d23',15,'2011-10-17 10:16:05',0,NULL,0,'HSL_GL6',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('6c836250-c8ea-4067-9a1d-67d49c9f2de4',15,'2011-10-17 10:16:05',0,NULL,0,'MBZ_WJB1',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('6eade78d-6013-4d8f-9fa4-ed634e7bc65f',15,'2011-10-17 10:16:05',0,NULL,0,'MBZ_GL2',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('6eec802a-da97-4d09-b7d1-dc34fb52858d',15,'2011-10-17 10:16:05',0,NULL,0,'XDZ_WJC2',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('6f1c5398-38c1-49cb-b85f-87718c4520b0',15,'2011-10-17 10:16:05',0,NULL,0,'MBZ_WJA2',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('70769b16-2787-4d19-afd3-ca14387f673e',15,'2011-10-17 10:16:05',0,NULL,0,'HGL_WJA2',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('71cea062-619a-46dd-8e5b-7c76c24ce45b',15,'2011-10-17 10:16:05',0,NULL,0,'MuBiaoValue',0,NULL,0,22,0,0,'DOUBLE',NULL,'8b8974af-28fd-4201-bfb8-e1a3ae9524ae','2','1',NULL),('72298c42-4903-45bf-8a5f-ac2c97bfc6fa',15,'2011-10-17 10:16:05',0,NULL,0,'MBZ1',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('7248acff-83fb-4855-96cc-0e9464db8b69',15,'2011-10-17 10:16:05',0,NULL,0,'MBZ_WJC2',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('725d761f-9985-496a-9f4d-f92d5db4e1f1',15,'2011-10-17 10:16:05',0,NULL,0,'XDZ_GL1',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('72913f64-2fcb-4e97-8688-459a9493173b',15,'2011-10-17 10:16:05',0,NULL,0,'JDZ_GL4',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('742426bd-d4c5-41e9-ac6a-1f5b7d0f5112',60,'2011-10-17 10:16:05',0,NULL,0,'NAM_FLA1',0,NULL,0,60,0,0,'VARCHAR',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('74c0f657-8e3c-4a3f-84bd-f9a8ce91a05c',15,'2011-10-17 10:16:05',0,NULL,0,'MBZ_GL3',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('77a49708-b9aa-4018-ad6f-654fc13b247a',15,'2011-10-17 10:16:05',0,NULL,0,'MBZ20',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('77e2c3ac-58f7-4700-a4e8-7f48b93c6d1d',50,'2011-10-17 10:16:05',0,NULL,0,'Str20',0,NULL,0,50,0,0,'VARCHAR',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('780f2112-7aa3-4d66-804d-a973bbbd7378',50,'2011-10-17 10:16:05',0,NULL,0,'Str18',0,NULL,0,50,0,0,'VARCHAR',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('7813cb32-31b0-4621-9a3f-35c3a2432844',60,'2011-10-17 10:16:05',0,NULL,0,'NAM_WJA4',0,NULL,0,60,0,0,'VARCHAR',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('786b11b9-75ce-46df-ad16-2884e54461f5',50,'2011-10-17 10:16:05',0,NULL,0,'Str1',0,NULL,0,50,0,0,'VARCHAR',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('79361df2-eabe-42c7-9551-ba5a9ddbe236',50,'2011-10-17 10:16:05',0,NULL,0,'Str12',0,NULL,0,50,0,0,'VARCHAR',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('79d6b39a-e8c2-4d84-a415-f13566c5d891',10,'2011-10-17 10:16:05',0,NULL,0,'ID',1,NULL,1,11,0,0,'COUNTER',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('79ef538a-e320-4b3a-a4ae-0c69da981a42',15,'2011-10-17 10:16:05',0,NULL,0,'JDZ_GL2',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('7ae69fd3-29e2-4161-a45a-09e6680e284c',15,'2011-10-17 10:16:05',0,NULL,0,'JDZ_FLC1',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('7c4a591d-e840-4711-a36c-6a3e33e450f0',15,'2011-10-17 10:16:05',0,NULL,0,'CLZ_FLB3',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('7c5cbf89-fb07-4214-b800-add48bcfa54b',19,'2011-10-17 10:16:05',0,NULL,0,'JRTime',0,NULL,0,19,0,0,'DATETIME',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('7c6802df-fe43-4ef7-8b86-b91dbf2dbf1d',50,'2011-10-17 10:16:05',0,NULL,0,'Str8',0,NULL,0,50,0,0,'VARCHAR',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('7cc5c97d-f3f1-4fc5-a04b-a14a0587aa94',60,'2011-10-17 10:16:05',0,NULL,0,'NAM_FLC1',0,NULL,0,60,0,0,'VARCHAR',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('7e2190af-dc66-4af2-9c45-667490929195',15,'2011-10-17 10:16:05',0,NULL,0,'JDZ_GL5',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('7e46c06c-cf10-49fd-8a65-042cff1f362b',15,'2011-10-17 10:16:05',0,NULL,0,'CLZSUM',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('7e6613b2-4b55-4679-8045-6cbf4a24784d',15,'2011-10-17 10:16:05',0,NULL,0,'CLZ_WJB2',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('7fb94538-45e1-4886-b2a4-2a882253b801',15,'2011-10-17 10:16:05',0,NULL,0,'CLZ_WJA3',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('7ffcaa9c-ecf6-4ca0-a901-c23edc8bf4e5',15,'2011-10-17 10:16:05',0,NULL,0,'CLZ14',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('81216ca7-0829-41aa-9463-decf1f000e16',15,'2011-10-17 10:16:05',0,NULL,0,'XDWC20',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('818ecd9a-f10e-4853-b602-90079defddae',15,'2011-10-17 10:16:05',0,NULL,0,'XDWC6',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('82ff72d0-8bef-4233-9bce-92be5f59ce3b',15,'2011-10-17 10:16:05',0,NULL,0,'MBZ2',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('83cfc32e-cc0f-4f09-947f-febde76a8d5a',15,'2011-10-17 10:16:05',0,NULL,0,'CLZ17',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('83f4a927-3b1f-4943-9797-baed387cb36e',15,'2011-10-17 10:16:05',0,NULL,0,'JDZ_GL1',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('8466a655-7bd0-4cb2-9538-5b5020ba2825',15,'2011-10-17 10:16:05',0,NULL,0,'CLZ20',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('858a6906-d37d-4118-9c3e-a122d390fd7e',15,'2011-10-17 10:16:05',0,NULL,0,'CLZ8',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('8662c944-351e-48cb-861a-77fcb80e6d60',15,'2011-10-17 10:16:05',0,NULL,0,'JDWC20',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('8665ccaf-c4e4-4fdc-9a03-756e053e6ecb',50,'2011-10-17 10:16:05',0,NULL,0,'Str10',0,NULL,0,50,0,0,'VARCHAR',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('866d6979-c813-4ff4-a2df-868f6964bfdb',10,'2011-10-17 10:16:05',0,NULL,0,'ID',1,NULL,1,11,0,0,'COUNTER',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('875f9270-544b-4e11-bc5c-ce2ed46400a0',15,'2011-10-17 10:16:05',0,NULL,0,'XDWC21',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('87e4e8b6-7b44-4ecb-9704-9beb71cb0f20',15,'2011-10-17 10:16:05',0,NULL,0,'XDZ_FLB2',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('881a900d-64b3-4f79-a6eb-b7652444ac3e',15,'2011-10-17 10:16:05',0,NULL,0,'XDZ_WJA3',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('88abc4d8-cbd8-44c1-a679-53331b9b6de5',15,'2011-10-17 10:16:05',0,NULL,0,'MBZ13',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('893c5b6c-6708-4fac-aaf1-e9bec7224cc9',50,'2011-10-17 10:16:05',0,NULL,0,'Str23',0,NULL,0,50,0,0,'VARCHAR',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('89455658-205c-4d93-afcb-b8d2508ab146',15,'2011-10-17 10:16:05',0,NULL,0,'JDWC7',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('89ccfe4b-7f98-4004-8a03-366de3935654',15,'2011-10-17 10:16:05',0,NULL,0,'CLZ_FLA3',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('8a6e2685-9366-4342-9dfd-634546076083',15,'2011-10-17 10:16:05',0,NULL,0,'JDZ_FLC2',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('8a7e108a-b3c9-43d1-9ad5-0d1cda8618cd',10,'2011-10-17 10:16:05',0,NULL,0,'BJ5',0,NULL,0,11,0,0,'INTEGER',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('8b5a444e-af12-4fd2-88b5-10e98dc5c5d3',50,'2011-10-17 10:16:05',0,NULL,0,'Job_No',0,NULL,0,50,0,0,'VARCHAR',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('8b643fe8-39e4-401b-bf6a-07ed4304005e',10,'2011-10-17 10:16:05',0,NULL,0,'BJ10',0,NULL,0,11,0,0,'INTEGER',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('8d2c5b93-502d-429a-a2d3-579a5cef6c65',60,'2011-10-17 10:16:05',0,NULL,0,'NAM_GL6',0,NULL,0,60,0,0,'VARCHAR',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('8dba7bbb-2bb6-46de-b7b3-d67e832e2e88',60,'2011-10-17 10:16:05',0,NULL,0,'NAM_FLB1',0,NULL,0,60,0,0,'VARCHAR',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('8ed95093-6046-4db1-8110-824bcbcdc9fc',50,'2011-10-17 10:16:05',0,NULL,0,'Location',0,NULL,0,50,0,0,'VARCHAR',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('8ee7b8f0-5ff8-4166-8add-44f1ba6a94f3',15,'2011-10-17 10:16:05',0,NULL,0,'HGL_WJB4',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('8f2376d6-2642-4b67-aa0a-408a06a7e86e',60,'2011-10-17 10:16:05',0,NULL,0,'NAM_GL4',0,NULL,0,60,0,0,'VARCHAR',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('8f8cbd0e-409a-4040-8dc8-c09a10ac0d39',15,'2011-10-17 10:16:05',0,NULL,0,'MBZ_Shui2',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('8fa94db0-8c8a-408f-bb0b-3c9d9438b1fc',50,'2011-10-17 10:16:05',0,NULL,0,'AttemperCode',0,NULL,0,50,0,0,'VARCHAR',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('8fc06d8d-7227-4113-a9e3-c43912d7e704',10,'2011-10-17 10:16:05',0,NULL,0,'BJ8',0,NULL,0,11,0,0,'INTEGER',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('8fd08ee0-9076-4f5f-9760-6dbe76caf3b3',50,'2011-10-17 10:16:05',0,NULL,0,'Proj_Nm',0,NULL,0,50,0,0,'VARCHAR',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('8fff7c4e-d237-432c-9334-56d90869b89b',50,'2011-10-17 10:16:05',0,NULL,0,'Str25',0,NULL,0,50,0,0,'VARCHAR',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('907827eb-6090-4108-9e6e-4a54e44304ab',15,'2011-10-17 10:16:05',0,NULL,0,'XDWC16',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('9086e228-7a0e-4479-9775-c24ed0360869',15,'2011-10-17 10:16:05',0,NULL,0,'XDWC3',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('92b2e0c6-ba2c-4939-8d78-8ac46c479682',50,'2011-10-17 10:16:05',0,NULL,0,'Prop_No',0,NULL,0,50,0,0,'VARCHAR',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('93623799-086d-47a5-b73f-4fb0a79804a0',15,'2011-10-17 10:16:05',0,NULL,0,'XDZ_WJB2',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('93fb7dec-1dae-435f-b197-504224f76cbf',60,'2011-10-17 10:16:05',0,NULL,0,'NAM13',0,NULL,0,60,0,0,'VARCHAR',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('95d1d8a6-cab7-4964-84d0-d8de19765905',15,'2011-10-17 10:16:05',0,NULL,0,'JDWC22',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('96085144-cbbd-43e2-a38f-9bf19f743bea',15,'2011-10-17 10:16:05',0,NULL,0,'HGL_WJC1',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('96636983-31f5-4dec-9454-271d0f5c23d8',60,'2011-10-17 10:16:05',0,NULL,0,'NAM3',0,NULL,0,60,0,0,'VARCHAR',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('981e464d-a1de-4c5e-8a38-2a73e9c02735',15,'2011-10-17 10:16:05',0,NULL,0,'MPFS',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('98a2fa84-85fd-4b36-975f-5fc0fc9168de',60,'2011-10-17 10:16:05',0,NULL,0,'NAM_Shui1',0,NULL,0,60,0,0,'VARCHAR',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('999fe15f-4b87-4c1a-99d2-7a91038f2659',60,'2011-10-17 10:16:05',0,NULL,0,'NAM17',0,NULL,0,60,0,0,'VARCHAR',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('9b4b1839-45da-47b1-b768-6d92c2ed6628',15,'2011-10-17 10:16:05',0,NULL,0,'CLZ_GL3',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('9cf4bdfa-9520-4ebb-8b57-e00c66fd20f9',50,'2011-10-17 10:16:05',0,NULL,0,'Const_Unit',0,NULL,0,50,0,0,'VARCHAR',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('9d1b63cd-f4b7-447e-884d-ee53bd486eb7',15,'2011-10-17 10:16:05',0,NULL,0,'XDZ_FLA4',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('9d85fd82-56d9-4f07-9e4e-228f8f8e5583',15,'2011-10-17 10:16:05',0,NULL,0,'CLZ21',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('9dadbe6b-0fe2-47d3-96b1-ad67869ee6cb',15,'2011-10-17 10:16:05',0,NULL,0,'CLZ_GL5',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('9ee669d3-5f63-4a1b-a2bb-c7022ff326c9',60,'2011-10-17 10:16:05',0,NULL,0,'NAM_FLA3',0,NULL,0,60,0,0,'VARCHAR',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('a18b0f0f-d82e-4b2e-b072-ba6958a337f3',60,'2011-10-17 10:16:05',0,NULL,0,'NAM_GL7',0,NULL,0,60,0,0,'VARCHAR',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('a1b0cc83-f2a7-42e6-acc2-a7cd3d642c18',15,'2011-10-17 10:16:05',0,NULL,0,'XDZ_WJA4',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('a23676ef-ca25-4621-9c4a-7a7a415f86f8',60,'2011-10-17 10:16:05',0,NULL,0,'NAM7',0,NULL,0,60,0,0,'VARCHAR',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('a34ca697-7ab5-4447-9503-75f3e14e8c11',15,'2011-10-17 10:16:05',0,NULL,0,'CLZ_GL4',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('a421d728-e3be-498e-bf0f-87face0a624f',15,'2011-10-17 10:16:05',0,NULL,0,'JDZ_WJC1',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('a46e8227-d530-4e5c-a7a1-212bd7ed556c',15,'2011-10-17 10:16:05',0,NULL,0,'JDWC21',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('a4934c95-39ce-4616-a54c-03ccbe0e3f62',15,'2011-10-17 10:16:05',0,NULL,0,'XDWC19',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('a4dd91ca-87b4-4ed6-86e0-793dcad8e5cb',15,'2011-10-17 10:16:05',0,NULL,0,'XDWC1',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('a579bfd3-4709-433c-a80c-e5e1be10b4ca',10,'2011-10-17 10:16:05',0,NULL,0,'BJ6',0,NULL,0,11,0,0,'INTEGER',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('a75b4435-6ab4-41d5-8c6b-423816e2fb6f',50,'2011-10-17 10:16:05',0,NULL,0,'Str5',0,NULL,0,50,0,0,'VARCHAR',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('a82efeb5-b05f-405d-9f5e-013900b37102',15,'2011-10-17 10:16:05',0,NULL,0,'XDZ_FLB1',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('a86903b7-2deb-4bfa-9bd4-5593bdf2d739',50,'2011-10-17 10:16:05',0,NULL,0,'Str24',0,NULL,0,50,0,0,'VARCHAR',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('aaac5baa-b0b8-413e-8472-2c18602bd7ca',50,'2011-10-17 10:16:05',0,NULL,0,'Str3',0,NULL,0,50,0,0,'VARCHAR',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('aaea44e5-9a71-4b64-bcb3-ed97d11d3501',15,'2011-10-17 10:16:05',0,NULL,0,'HGL_WJC4',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('ab8c4b55-cd46-4625-a502-23e075b5b6e1',15,'2011-10-17 10:16:05',0,NULL,0,'CLZ13',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('abbaf092-bec3-4a3c-930c-af20e4c236bf',60,'2011-10-17 10:16:05',0,NULL,0,'NAM6',0,NULL,0,60,0,0,'VARCHAR',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('abf0fc3f-7e4a-46c3-9578-a048197f1816',15,'2011-10-17 10:16:05',0,NULL,0,'CLZ9',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('acc11b30-7a1c-45f8-80c8-eb4fdcab4174',60,'2011-10-17 10:16:05',0,NULL,0,'NAM8',0,NULL,0,60,0,0,'VARCHAR',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('ad0eb4cf-1528-4e3d-934c-0edcf4d7f12f',15,'2011-10-17 10:16:05',0,NULL,0,'CLZ12',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('ad9e444e-acde-4aab-99b7-5a3e68dbe487',15,'2011-10-17 10:16:05',0,NULL,0,'CLZ1',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('adb79d05-2f88-4bc4-b904-917adadd98d5',15,'2011-10-17 10:16:05',0,NULL,0,'CLZ_FLA1',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('aeb3b446-2d99-4442-aef6-909aef1f8b4c',15,'2011-10-17 10:16:05',0,NULL,0,'JDZ_FLB1',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('aece4d13-dea5-417c-ab8b-1e41b55dd8ba',15,'2011-10-17 10:16:05',0,NULL,0,'MBZ_Shui1',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('b075de1b-052f-44a6-93f0-e37474b42a61',15,'2011-10-17 10:16:05',0,NULL,0,'CLZ_GL6',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('b0ec9242-75c5-4ee0-a5e8-29616faed389',15,'2011-10-17 10:16:05',0,NULL,0,'XDZ_WJA2',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('b0f7a649-6649-475c-a950-b5bd1f223d74',15,'2011-10-17 10:16:05',0,NULL,0,'HSL_GL8',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('b1b62466-77fa-4739-9032-c01c4342b56d',15,'2011-10-17 10:16:05',0,NULL,0,'JDZ_Shui1',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('b25252c4-4cd5-421c-9183-a17fe279fbc4',15,'2011-10-17 10:16:05',0,NULL,0,'XDZ_GL2',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('b25d4ff1-a0c7-4ed9-8c8a-c85ee5696e68',15,'2011-10-17 10:16:05',0,NULL,0,'JDWC6',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('b52b5934-fe24-456e-9e24-ee627d5d7d84',15,'2011-10-17 10:16:05',0,NULL,0,'JDWC18',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('b5a8f7b1-975b-4e90-8951-66ba7007c954',15,'2011-10-17 10:16:05',0,NULL,0,'HSL_GL1',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('b7650b95-5d45-449e-a024-822551ceeff5',60,'2011-10-17 10:16:05',0,NULL,0,'NAM_WJC1',0,NULL,0,60,0,0,'VARCHAR',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('b785afb2-16ad-409d-8114-405a0a632d7c',19,'2011-10-17 10:16:05',0,NULL,0,'ScTime',0,NULL,0,19,1,0,'DATETIME',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('b7d8ad17-9ba0-425c-aa89-9efc245525d4',50,'2011-10-17 10:16:05',0,NULL,0,'Driver',0,NULL,0,50,0,0,'VARCHAR',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('b867ebe1-42aa-4ebe-89c7-c3cc79f24ecc',50,'2011-10-17 10:16:05',0,NULL,0,'Strength',0,NULL,0,50,0,0,'VARCHAR',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('b8a2fb86-65b7-4202-87e6-07f681f4cdcb',60,'2011-10-17 10:16:05',0,NULL,0,'NAM2',0,NULL,0,60,0,0,'VARCHAR',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('b9a26295-8ecd-4d18-8d53-9e5a3393ff71',50,'2011-10-17 10:16:05',0,NULL,0,'RecordRemark',0,NULL,0,50,0,0,'VARCHAR',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('ba33eb5d-d3f8-49c3-aa01-d88e6d113fcd',50,'2011-10-17 10:16:05',0,NULL,0,'Contr_No',0,NULL,0,50,0,0,'VARCHAR',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('bb386bc4-4b72-4352-9fb9-939fa1e902b6',60,'2011-10-17 10:16:05',0,NULL,0,'ForType',0,NULL,0,60,0,0,'VARCHAR',NULL,'8b8974af-28fd-4201-bfb8-e1a3ae9524ae','2','1',NULL),('bbf6d82a-23ce-4427-b813-28267793d6fc',15,'2011-10-17 10:16:05',0,NULL,0,'XDWC8',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('be402b62-847b-4b3c-ba3d-55f17b2024fb',15,'2011-10-17 10:16:05',0,NULL,0,'XDZ_GL3',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('bef82c2c-1a1d-4bc1-9031-7ffdf18dc2f8',50,'2011-10-17 10:16:05',0,NULL,0,'Str21',0,NULL,0,50,0,0,'VARCHAR',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('bf5ac78d-b20c-47f4-85a4-13f4e6ee16c7',15,'2011-10-17 10:16:05',0,NULL,0,'MBZ16',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('bffcbbda-63cc-4c31-a19d-d17a5ebbf7be',60,'2011-10-17 10:16:05',0,NULL,0,'Remark',0,NULL,0,60,0,0,'VARCHAR',NULL,'8b8974af-28fd-4201-bfb8-e1a3ae9524ae','2','1',NULL),('c0a452ec-a7f4-4abb-aed0-1af7b71231b0',15,'2011-10-17 10:16:05',0,NULL,0,'JDWC3',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('c136bc96-348f-4596-bc82-c571665e367c',15,'2011-10-17 10:16:05',0,NULL,0,'JDZ_FLB2',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('c1619b7e-415a-48ae-892e-a7be6676af37',15,'2011-10-17 10:16:05',0,NULL,0,'JDZ_FLB3',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('c1ac604f-50cd-4ab1-973a-89cfd09bd537',50,'2011-10-17 10:16:05',0,NULL,0,'Cust_Nm',0,NULL,0,50,0,0,'VARCHAR',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('c457828b-1ff2-48b5-8205-dae6eac75a89',15,'2011-10-17 10:16:05',0,NULL,0,'CLZ3',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('c485ffab-f609-48f6-ab65-fb8ba2a3ebe6',15,'2011-10-17 10:16:05',0,NULL,0,'CLZ6',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('c4873f4c-bef0-400d-a957-50048aee2605',15,'2011-10-17 10:16:05',0,NULL,0,'JDWC2',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('c5d54fde-74c8-4d7a-8e6c-a9dd140781fb',15,'2011-10-17 10:16:05',0,NULL,0,'MBZ8',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('c7ca4d26-be01-4578-be1f-9329d1535f7a',15,'2011-10-17 10:16:05',0,NULL,0,'MBZ_WJC3',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('c9620a1b-99df-4b0a-a777-17510cbd26f4',15,'2011-10-17 10:16:05',0,NULL,0,'HGL_WJB1',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('c97ea0d1-9c62-445d-ae58-eee5f99c0c02',15,'2011-10-17 10:16:05',0,NULL,0,'CLZ_WJC4',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('ca2b9413-c338-4fae-ae1d-9fe591386ba7',50,'2011-10-17 10:16:05',0,NULL,0,'Delivery_Mode',0,NULL,0,50,0,0,'VARCHAR',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('ca4fb7b1-d62b-401e-88cc-a17588bdc283',15,'2011-10-17 10:16:05',0,NULL,0,'JDZ_FLA4',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('cc2b666b-516d-4b0d-8253-af6e2999fbe7',15,'2011-10-17 10:16:05',0,NULL,0,'XDWC12',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('cd41ee21-c9b6-45dc-81a7-f9fbaad305d5',15,'2011-10-17 10:16:05',0,NULL,0,'XDZ_FLA3',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('cdeac992-6d83-4a06-b470-73f4187902db',15,'2011-10-17 10:16:05',0,NULL,0,'JDWC17',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('cdf0483b-174d-466b-826b-b81d4967cec7',15,'2011-10-17 10:16:05',0,NULL,0,'MBZ6',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('ce32a7a7-eb31-48c6-b5d7-a4c9e046ae8d',15,'2011-10-17 10:16:05',0,NULL,0,'JDZ_WJA1',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('cfab47ab-0f08-4113-8af4-8144998ca8fe',15,'2011-10-17 10:16:05',0,NULL,0,'MBZ_WJB2',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('cfeccff1-5e81-45cc-ae4b-f43fbcb2d5f9',15,'2011-10-17 10:16:05',0,NULL,0,'XDWC9',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('d033554c-b1af-4221-9c98-89c5f6fff832',50,'2011-10-17 10:16:05',0,NULL,0,'Str17',0,NULL,0,50,0,0,'VARCHAR',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('d047211e-e81c-4b52-89a5-689965c72291',15,'2011-10-17 10:16:05',0,NULL,0,'MBZ4',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('d0702903-1a07-4cbf-89f7-ed884f28e3a9',15,'2011-10-17 10:16:05',0,NULL,0,'CLZ16',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('d12bcc4c-3c15-453c-a4c9-5ab820627786',15,'2011-10-17 10:16:05',0,NULL,0,'JDZ_GL8',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('d12fb8ec-0250-44ec-9740-6fc93f242b2c',50,'2011-10-17 10:16:05',0,NULL,0,'Str22',0,NULL,0,50,0,0,'VARCHAR',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('d2376165-2ad8-4944-a43f-7f3dc0f4701e',15,'2011-10-17 10:16:05',0,NULL,0,'CLZ_GL1',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('d3ae2972-c445-472b-a967-6028b3962290',50,'2011-10-17 10:16:05',0,NULL,0,'Str6',0,NULL,0,50,0,0,'VARCHAR',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('d44c3148-7101-472e-affe-6edcb74b59a3',60,'2011-10-17 10:16:05',0,NULL,0,'NAM22',0,NULL,0,60,0,0,'VARCHAR',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('d45249bc-3b11-42da-95c1-357b80a62e8f',15,'2011-10-17 10:16:05',0,NULL,0,'JDZ_WJB4',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('d4e089f3-2078-4890-9c0d-24e44e2d0174',50,'2011-10-17 10:16:05',0,NULL,0,'Str14',0,NULL,0,50,0,0,'VARCHAR',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('d5d76b97-635c-4fb7-9206-c4a34fd09d4b',15,'2011-10-17 10:16:05',0,NULL,0,'MBZ_FLC1',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('d68466be-00a7-4b56-8760-d0ba38e6447b',50,'2011-10-17 10:16:05',0,NULL,0,'Str15',0,NULL,0,50,0,0,'VARCHAR',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('d68a224b-923e-4bbd-b64f-3b81c5aa6592',15,'2011-10-17 10:16:05',0,NULL,0,'XDZ_WJB1',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('d7ee420b-e9bc-4a27-9475-6769ea7c3abc',10,'2011-10-17 10:16:05',0,NULL,0,'BJ4',0,NULL,0,11,0,0,'INTEGER',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('d84f9c13-ab68-4a15-a018-95aab8720d1c',15,'2011-10-17 10:16:05',0,NULL,0,'MBZ7',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('d95af019-41a4-442c-aa72-faec049cf025',50,'2011-10-17 10:16:05',0,NULL,0,'Truck_No',0,NULL,0,50,0,0,'VARCHAR',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('d95d7e12-4b36-4fdf-9c96-e180386e446f',15,'2011-10-17 10:16:05',0,NULL,0,'XDZ_WJC4',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('d9d52d9f-a174-4df3-9bfe-1505726d6cec',50,'2011-10-17 10:16:05',0,NULL,0,'TaLuoDu',0,NULL,0,50,0,0,'VARCHAR',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('da877fc5-2491-4e57-a8e6-0d2fc3525bfc',60,'2011-10-17 10:16:05',0,NULL,0,'NAM_FLA4',0,NULL,0,60,0,0,'VARCHAR',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('db576711-96aa-430d-9a8a-71767603ee6e',15,'2011-10-17 10:16:05',0,NULL,0,'MBZ_FLB1',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('dd0757f5-7463-423a-8bfd-75f67e650e9c',60,'2011-10-17 10:16:05',0,NULL,0,'NAM_WJA3',0,NULL,0,60,0,0,'VARCHAR',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('dd73f641-1453-4c0b-b1dd-c866e6469b56',15,'2011-10-17 10:16:05',0,NULL,0,'HGL_WJB3',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('dd7da60b-b13a-45bc-9bb7-a383498a2265',15,'2011-10-17 10:16:05',0,NULL,0,'CLZ_WJB4',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('ddfbcab9-a3f4-4675-ab72-b73551ec273f',60,'2011-10-17 10:16:05',0,NULL,0,'NAM_FLB3',0,NULL,0,60,0,0,'VARCHAR',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('de0d48e5-78ea-436a-8eb5-06946603c813',15,'2011-10-17 10:16:05',0,NULL,0,'XDWC13',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('de5ca998-b8d3-4799-96ee-45bedb1d5390',60,'2011-10-17 10:16:05',0,NULL,0,'NAM_WJB4',0,NULL,0,60,0,0,'VARCHAR',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('de9d3967-aad4-4673-bd93-1142c7249d02',15,'2011-10-17 10:16:05',0,NULL,0,'MBZSUM',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('def04ddc-a4f1-4d5a-a25f-941007f087ed',15,'2011-10-17 10:16:05',0,NULL,0,'JDZ_Shui2',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('e043207d-63aa-456d-9dee-dd63603c5e26',15,'2011-10-17 10:16:05',0,NULL,0,'CLZ_FLC2',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('e0d44c7e-dbd0-47ac-929e-86d45c962a32',15,'2011-10-17 10:16:05',0,NULL,0,'HGL_WJA3',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('e10a7099-8287-4e06-8438-ea8fc2373b34',50,'2011-10-17 10:16:05',0,NULL,0,'SynchRemark',0,NULL,0,50,0,0,'VARCHAR',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('e1bac872-f3a1-412f-b530-4e1a59b9e882',15,'2011-10-17 10:16:05',0,NULL,0,'JDWC19',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('e2e51663-46c9-4e39-8f93-ecdf5b4fa082',15,'2011-10-17 10:16:05',0,NULL,0,'JDZ_FLA2',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('e381fd70-41f9-479a-b560-2ffd4ee921a8',15,'2011-10-17 10:16:05',0,NULL,0,'JDZ_WJA3',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('e3cb718d-162f-4937-b0a8-3166cffea350',15,'2011-10-17 10:16:05',0,NULL,0,'MBZ_FLA1',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('e4030a1f-5b64-42d3-9158-e0686d0591ef',15,'2011-10-17 10:16:05',0,NULL,0,'CLZ_Shui2',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('e461632c-5284-4e3f-b5d8-4f5f3319a889',15,'2011-10-17 10:16:05',0,NULL,0,'MBZ_FLB3',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('e477c47f-6ed5-41b1-b4c6-2b2e9f50f154',60,'2011-10-17 10:16:05',0,NULL,0,'NAM_FLB2',0,NULL,0,60,0,0,'VARCHAR',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('e56edda7-6677-48d2-aa18-6d0774268b0e',15,'2011-10-17 10:16:05',0,NULL,0,'CLZ_FLA2',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('e5a99378-1b7a-4997-bec5-a95d08a2003a',15,'2011-10-17 10:16:05',0,NULL,0,'CLZ18',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('e71f5403-930d-42fb-b592-87ccd21c2f9d',50,'2011-10-17 10:16:05',0,NULL,0,'Str2',0,NULL,0,50,0,0,'VARCHAR',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('e92bba0b-e229-4e0a-832f-e08d4a37efd0',15,'2011-10-17 10:16:05',0,NULL,0,'CLZ10',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('eaf380fe-3c00-4bec-a63c-ed5d5ed0c543',15,'2011-10-17 10:16:05',0,NULL,0,'Output_vol',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('eb99f0d5-44b8-4679-833a-ef7d9820c61b',10,'2011-10-17 10:16:05',0,NULL,0,'ReadState',0,NULL,0,11,0,0,'INTEGER',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('ebe197d6-a470-4cea-bda9-4bcbd984c96b',15,'2011-10-17 10:16:05',0,NULL,0,'CLZ19',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('ec51555b-e43d-47d6-9568-35db9b504244',15,'2011-10-17 10:16:05',0,NULL,0,'MBZ9',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('ed1b2b66-d4ab-43df-81e3-35a5ef3536da',50,'2011-10-17 10:16:05',0,NULL,0,'Str9',0,NULL,0,50,0,0,'VARCHAR',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('ed58fb60-9c9a-4730-9a7f-1eb65f4f8b2f',60,'2011-10-17 10:16:05',0,NULL,0,'NAM_WJC2',0,NULL,0,60,0,0,'VARCHAR',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('ee533ae3-d1e2-4836-886a-7de4d6ea1589',15,'2011-10-17 10:16:05',0,NULL,0,'JDZ_GL6',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('ee5f0338-6dce-4347-911e-b08fa2741beb',15,'2011-10-17 10:16:05',0,NULL,0,'MBZ_GL8',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('ef458a8c-7226-4397-855b-825db7ce184e',15,'2011-10-17 10:16:05',0,NULL,0,'CLZ_FLB2',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('ef6d605b-f4ee-4ed7-934b-9f8c234c7143',10,'2011-10-17 10:16:05',0,NULL,0,'BJ9',0,NULL,0,11,0,0,'INTEGER',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('f0cf08ce-d27e-4676-8150-d8a06e0fe7cd',60,'2011-10-17 10:16:05',0,NULL,0,'NAM_WJB3',0,NULL,0,60,0,0,'VARCHAR',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('f14f7150-f951-4428-8376-5a778dac8d17',60,'2011-10-17 10:16:05',0,NULL,0,'NAM18',0,NULL,0,60,0,0,'VARCHAR',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('f1e07eec-04e3-4feb-b57a-9523e60eaaa7',15,'2011-10-17 10:16:05',0,NULL,0,'XDZ_FLC2',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('f335edbf-ff16-412e-9c03-753c1d0402ac',15,'2011-10-17 10:16:05',0,NULL,0,'MBZ22',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('f3382b48-c8f0-4a13-a9bb-3c683737c489',50,'2011-10-17 10:16:05',0,NULL,0,'Site_No',0,NULL,0,50,0,0,'VARCHAR',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('f4394cde-0694-4516-ba52-677e9df834cb',15,'2011-10-17 10:16:05',0,NULL,0,'JDWC15',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('f44a4305-95cd-47c1-98ea-edca1855be34',15,'2011-10-17 10:16:05',0,NULL,0,'MBZ5',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('f54894eb-8b62-4ec3-8b77-6d0703b96590',10,'2011-10-17 10:16:05',0,NULL,0,'BJ3',0,NULL,0,11,0,0,'INTEGER',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('f7198d00-62e9-49cd-b684-d5e3cb71dff4',15,'2011-10-17 10:16:05',0,NULL,0,'MBZ_WJB3',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('f78ec26e-a2ed-442b-8d44-71855ffbe27e',50,'2011-10-17 10:16:05',0,NULL,0,'Str13',0,NULL,0,50,0,0,'VARCHAR',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('f8396897-9b67-4ff1-b816-d8f6717f9907',15,'2011-10-17 10:16:05',0,NULL,0,'JDZ_WJA2',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('f8b763d0-87ca-4605-93dc-cd036e1469e2',15,'2011-10-17 10:16:05',0,NULL,0,'CLZ_WJA1',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('f8e0bc17-c353-436b-91ab-39ff8ea024f5',15,'2011-10-17 10:16:05',0,NULL,0,'XDWC10',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('f986b8e0-c274-4b74-b368-9ab6c2bd7ce3',15,'2011-10-17 10:16:05',0,NULL,0,'JDZ_WJB1',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('f9c2d36c-7943-490e-94f7-c3137126532a',15,'2011-10-17 10:16:05',0,NULL,0,'CLZ_GL8',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('f9f813bc-bd68-44ec-b8db-74f440e4134f',15,'2011-10-17 10:16:05',0,NULL,0,'MBZ_WJC4',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('fa241901-25fd-43a8-b4fa-a6a037dfd793',15,'2011-10-17 10:16:05',0,NULL,0,'MBZ_FLC2',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('fb16c3c2-b924-4074-bb3c-572653663ab6',15,'2011-10-17 10:16:05',0,NULL,0,'CLZ_WJC2',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('fb50a567-0b1f-4344-985a-bdec0a541962',15,'2011-10-17 10:16:05',0,NULL,0,'XDZ_WJA1',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('fb5de9d9-c4d3-454b-9ee6-f8b3a7f2d625',50,'2011-10-17 10:16:05',0,NULL,0,'Str19',0,NULL,0,50,0,0,'VARCHAR',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('fba5125d-fc31-444e-ac5e-aad693373b33',15,'2011-10-17 10:16:05',0,NULL,0,'CLZ_WJA2',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('fbcda2f0-a882-42b2-a8e0-8427514a2910',15,'2011-10-17 10:16:05',0,NULL,0,'XDZ_FLC1',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('fc6229c5-0206-438e-ab7d-3b91fd95f16d',15,'2011-10-17 10:16:05',0,NULL,0,'MBZ_WJA1',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('fccecf42-a99c-4345-8f59-fbf7213b1c5f',15,'2011-10-17 10:16:05',0,NULL,0,'MBZ12',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('fcfd2fb9-efd1-4bf9-8f0d-c1cd4b688626',15,'2011-10-17 10:16:05',0,NULL,0,'JDZ_GL7',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('fd662e6e-bf7e-4932-878b-6f20fba13cd3',15,'2011-10-17 10:16:05',0,NULL,0,'JDWC4',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('fdc8fe11-e107-45d8-8fe8-70bb6d883e71',15,'2011-10-17 10:16:05',0,NULL,0,'MBZ_GL1',0,NULL,0,22,0,0,'DOUBLE',NULL,'f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','2','1',NULL),('fe3e2ff4-3f00-45fa-8522-81316cbbc770',10,'2011-10-17 10:16:05',0,NULL,0,'BJ1',0,NULL,0,11,0,0,'INTEGER',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('ff8ebc54-24c3-4d3c-8f59-9ff971cf74b5',15,'2011-10-17 10:16:05',0,NULL,0,'MBZ15',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL),('ff8f51bf-ce54-4374-94ff-fd419c85a574',15,'2011-10-17 10:16:05',0,NULL,0,'XDWC11',0,NULL,0,22,0,0,'DOUBLE',NULL,'f3ef45ff-4242-492f-9b64-11b1c7c92c9a','2','1',NULL);
/*!40000 ALTER TABLE `crmp_meta_column_descriptor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crmp_meta_database_import_indicator`
--

DROP TABLE IF EXISTS `crmp_meta_database_import_indicator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crmp_meta_database_import_indicator` (
  `ID` varchar(64) NOT NULL,
  `UPDATED_ON` datetime DEFAULT NULL,
  `CREATED_ON` datetime DEFAULT NULL,
  `DELETED` tinyint(1) DEFAULT '0',
  `INDICATOR_VALUE` varchar(255) DEFAULT NULL,
  `TENANT_ID` varchar(64) NOT NULL,
  `DATABASE_ID` varchar(64) DEFAULT NULL,
  `CREATED_BY` varchar(64) NOT NULL,
  `INDICATORCOLUMN_ID` varchar(64) DEFAULT NULL,
  `TABLE_ID` varchar(64) DEFAULT NULL,
  `UPDATED_BY` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `crmpmetadatabaseimport_indicatorINDICATORCOLUMN_ID` (`INDICATORCOLUMN_ID`),
  KEY `FK_crmp_meta_database_import_indicator_CREATED_BY` (`CREATED_BY`),
  KEY `FK_crmp_meta_database_import_indicator_DATABASE_ID` (`DATABASE_ID`),
  KEY `FK_crmp_meta_database_import_indicator_TENANT_ID` (`TENANT_ID`),
  KEY `FK_crmp_meta_database_import_indicator_TABLE_ID` (`TABLE_ID`),
  KEY `FK_crmp_meta_database_import_indicator_UPDATED_BY` (`UPDATED_BY`),
  CONSTRAINT `FK_crmp_meta_database_import_indicator_UPDATED_BY` FOREIGN KEY (`UPDATED_BY`) REFERENCES `CRMP_User` (`ID`),
  CONSTRAINT `crmpmetadatabaseimport_indicatorINDICATORCOLUMN_ID` FOREIGN KEY (`INDICATORCOLUMN_ID`) REFERENCES `crmp_meta_column_descriptor` (`ID`),
  CONSTRAINT `FK_crmp_meta_database_import_indicator_CREATED_BY` FOREIGN KEY (`CREATED_BY`) REFERENCES `CRMP_User` (`ID`),
  CONSTRAINT `FK_crmp_meta_database_import_indicator_DATABASE_ID` FOREIGN KEY (`DATABASE_ID`) REFERENCES `crmp_meta_db_descriptor` (`ID`),
  CONSTRAINT `FK_crmp_meta_database_import_indicator_TABLE_ID` FOREIGN KEY (`TABLE_ID`) REFERENCES `crmp_meta_table_descriptor` (`ID`),
  CONSTRAINT `FK_crmp_meta_database_import_indicator_TENANT_ID` FOREIGN KEY (`TENANT_ID`) REFERENCES `CRMP_Tenant` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crmp_meta_database_import_indicator`
--

LOCK TABLES `crmp_meta_database_import_indicator` WRITE;
/*!40000 ALTER TABLE `crmp_meta_database_import_indicator` DISABLE KEYS */;
INSERT INTO `crmp_meta_database_import_indicator` VALUES ('1a512ac7-c09c-4ed0-a155-b1ac91747e97',NULL,'2011-10-17 10:16:11',0,'195707','1','5802403f-2a7f-423b-8d19-dd8f2cd52d9b','2','79d6b39a-e8c2-4d84-a415-f13566c5d891','f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed',NULL),('4e457e07-1121-4a74-b38a-20f2afb4b62f',NULL,'2011-10-17 10:16:11',0,'1044615','1','5802403f-2a7f-423b-8d19-dd8f2cd52d9b','2','866d6979-c813-4ff4-a2df-868f6964bfdb','f3ef45ff-4242-492f-9b64-11b1c7c92c9a',NULL);
/*!40000 ALTER TABLE `crmp_meta_database_import_indicator` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crmp_meta_database_sync_indicator`
--

DROP TABLE IF EXISTS `crmp_meta_database_sync_indicator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crmp_meta_database_sync_indicator` (
  `ID` varchar(64) NOT NULL,
  `UPDATED_ON` datetime DEFAULT NULL,
  `CREATED_ON` datetime DEFAULT NULL,
  `DELETED` tinyint(1) DEFAULT '0',
  `INDICATOR_VALUE` varchar(255) DEFAULT NULL,
  `TENANT_ID` varchar(64) NOT NULL,
  `DATABASE_ID` varchar(64) DEFAULT NULL,
  `CREATED_BY` varchar(64) NOT NULL,
  `INDICATORCOLUMN_ID` varchar(64) DEFAULT NULL,
  `TABLE_ID` varchar(64) DEFAULT NULL,
  `UPDATED_BY` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_crmp_meta_database_sync_indicator_TABLE_ID` (`TABLE_ID`),
  KEY `FK_crmp_meta_database_sync_indicator_CREATED_BY` (`CREATED_BY`),
  KEY `crmpmeta_database_sync_indicatorINDICATORCOLUMN_ID` (`INDICATORCOLUMN_ID`),
  KEY `FK_crmp_meta_database_sync_indicator_UPDATED_BY` (`UPDATED_BY`),
  KEY `FK_crmp_meta_database_sync_indicator_TENANT_ID` (`TENANT_ID`),
  KEY `FK_crmp_meta_database_sync_indicator_DATABASE_ID` (`DATABASE_ID`),
  CONSTRAINT `FK_crmp_meta_database_sync_indicator_DATABASE_ID` FOREIGN KEY (`DATABASE_ID`) REFERENCES `crmp_meta_db_descriptor` (`ID`),
  CONSTRAINT `crmpmeta_database_sync_indicatorINDICATORCOLUMN_ID` FOREIGN KEY (`INDICATORCOLUMN_ID`) REFERENCES `crmp_meta_column_descriptor` (`ID`),
  CONSTRAINT `FK_crmp_meta_database_sync_indicator_CREATED_BY` FOREIGN KEY (`CREATED_BY`) REFERENCES `CRMP_User` (`ID`),
  CONSTRAINT `FK_crmp_meta_database_sync_indicator_TABLE_ID` FOREIGN KEY (`TABLE_ID`) REFERENCES `crmp_meta_table_descriptor` (`ID`),
  CONSTRAINT `FK_crmp_meta_database_sync_indicator_TENANT_ID` FOREIGN KEY (`TENANT_ID`) REFERENCES `CRMP_Tenant` (`ID`),
  CONSTRAINT `FK_crmp_meta_database_sync_indicator_UPDATED_BY` FOREIGN KEY (`UPDATED_BY`) REFERENCES `CRMP_User` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crmp_meta_database_sync_indicator`
--

LOCK TABLES `crmp_meta_database_sync_indicator` WRITE;
/*!40000 ALTER TABLE `crmp_meta_database_sync_indicator` DISABLE KEYS */;
INSERT INTO `crmp_meta_database_sync_indicator` VALUES ('3c991a01-3b31-4cb3-b241-cdbcf784641b',NULL,'2011-10-17 10:25:21',0,NULL,'1','5802403f-2a7f-423b-8d19-dd8f2cd52d9b','2','79d6b39a-e8c2-4d84-a415-f13566c5d891','f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed',NULL),('d78eb9dc-f239-40e6-a746-31c8b44c9e2b',NULL,'2011-10-17 10:25:21',0,NULL,'1','5802403f-2a7f-423b-8d19-dd8f2cd52d9b','2','866d6979-c813-4ff4-a2df-868f6964bfdb','f3ef45ff-4242-492f-9b64-11b1c7c92c9a',NULL);
/*!40000 ALTER TABLE `crmp_meta_database_sync_indicator` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crmp_meta_db_descriptor`
--

DROP TABLE IF EXISTS `crmp_meta_db_descriptor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crmp_meta_db_descriptor` (
  `ID` varchar(64) NOT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `UPDATED_ON` datetime DEFAULT NULL,
  `DELETED` tinyint(1) DEFAULT '0',
  `CREATED_ON` datetime DEFAULT NULL,
  `TENANT_ID` varchar(64) NOT NULL,
  `CREATED_BY` varchar(64) NOT NULL,
  `PLANT_ID` varchar(64) DEFAULT NULL,
  `UPDATED_BY` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_crmp_meta_db_descriptor_CREATED_BY` (`CREATED_BY`),
  KEY `FK_crmp_meta_db_descriptor_PLANT_ID` (`PLANT_ID`),
  KEY `FK_crmp_meta_db_descriptor_TENANT_ID` (`TENANT_ID`),
  KEY `FK_crmp_meta_db_descriptor_UPDATED_BY` (`UPDATED_BY`),
  CONSTRAINT `FK_crmp_meta_db_descriptor_UPDATED_BY` FOREIGN KEY (`UPDATED_BY`) REFERENCES `CRMP_User` (`ID`),
  CONSTRAINT `FK_crmp_meta_db_descriptor_CREATED_BY` FOREIGN KEY (`CREATED_BY`) REFERENCES `CRMP_User` (`ID`),
  CONSTRAINT `FK_crmp_meta_db_descriptor_PLANT_ID` FOREIGN KEY (`PLANT_ID`) REFERENCES `crmp_concrete_mixing_plant` (`ID`),
  CONSTRAINT `FK_crmp_meta_db_descriptor_TENANT_ID` FOREIGN KEY (`TENANT_ID`) REFERENCES `CRMP_Tenant` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crmp_meta_db_descriptor`
--

LOCK TABLES `crmp_meta_db_descriptor` WRITE;
/*!40000 ALTER TABLE `crmp_meta_db_descriptor` DISABLE KEYS */;
INSERT INTO `crmp_meta_db_descriptor` VALUES ('5802403f-2a7f-423b-8d19-dd8f2cd52d9b','crmp_1_db',NULL,0,'2011-10-17 10:16:05','1','2','efdfa381-cc24-488d-b8c4-2714635e73fb',NULL);
/*!40000 ALTER TABLE `crmp_meta_db_descriptor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crmp_meta_etl_column_descriptor`
--

DROP TABLE IF EXISTS `crmp_meta_etl_column_descriptor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crmp_meta_etl_column_descriptor` (
  `ID` varchar(64) NOT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `CREATED_ON` datetime DEFAULT NULL,
  `TARGET_FIELD_NAME` varchar(255) DEFAULT NULL,
  `UPDATED_ON` datetime DEFAULT NULL,
  `DELETED` tinyint(1) DEFAULT '0',
  `RAW_COLUMN_NAME` varchar(255) DEFAULT NULL,
  `TENANT_ID` varchar(64) NOT NULL,
  `TABLEDESCRIPTOR_ID` varchar(64) DEFAULT NULL,
  `CREATED_BY` varchar(64) NOT NULL,
  `UPDATED_BY` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_crmp_meta_etl_column_descriptor_TENANT_ID` (`TENANT_ID`),
  KEY `crmp_meta_etl_column_descriptor_TABLEDESCRIPTOR_ID` (`TABLEDESCRIPTOR_ID`),
  KEY `FK_crmp_meta_etl_column_descriptor_CREATED_BY` (`CREATED_BY`),
  KEY `FK_crmp_meta_etl_column_descriptor_UPDATED_BY` (`UPDATED_BY`),
  CONSTRAINT `FK_crmp_meta_etl_column_descriptor_UPDATED_BY` FOREIGN KEY (`UPDATED_BY`) REFERENCES `CRMP_User` (`ID`),
  CONSTRAINT `crmp_meta_etl_column_descriptor_TABLEDESCRIPTOR_ID` FOREIGN KEY (`TABLEDESCRIPTOR_ID`) REFERENCES `crmp_meta_etl_table_descriptor` (`ID`),
  CONSTRAINT `FK_crmp_meta_etl_column_descriptor_CREATED_BY` FOREIGN KEY (`CREATED_BY`) REFERENCES `CRMP_User` (`ID`),
  CONSTRAINT `FK_crmp_meta_etl_column_descriptor_TENANT_ID` FOREIGN KEY (`TENANT_ID`) REFERENCES `CRMP_Tenant` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crmp_meta_etl_column_descriptor`
--

LOCK TABLES `crmp_meta_etl_column_descriptor` WRITE;
/*!40000 ALTER TABLE `crmp_meta_etl_column_descriptor` DISABLE KEYS */;
INSERT INTO `crmp_meta_etl_column_descriptor` VALUES ('0d9e3829-e70f-473e-b1d7-9efb539b998c',NULL,'2011-10-17 10:24:13','projectOwnerName',NULL,0,'Cust_Nm','1','d2e0dbd0-1bd4-41a8-9687-42ab89dc3e88','2',NULL),('1777a537-8d7a-47ff-a783-5f7ab5cde7c2',NULL,'2011-10-17 10:24:13','productionTime',NULL,0,'ScTime','1','d2e0dbd0-1bd4-41a8-9687-42ab89dc3e88','2',NULL),('3f82eccf-7468-44aa-b688-594b9e19adce',NULL,'2011-10-17 10:24:13','unitOfProjectName',NULL,0,'Proj_Nm','1','d2e0dbd0-1bd4-41a8-9687-42ab89dc3e88','2',NULL),('4fac638c-3ac7-4c3e-9e84-4d63f6afc619',NULL,'2011-10-17 10:24:13','mixingPlantName',NULL,0,NULL,'1','d2e0dbd0-1bd4-41a8-9687-42ab89dc3e88','2',NULL),('78ab838b-a4e5-46a4-a2a5-503b0d14811a',NULL,'2011-10-17 10:24:13','unitOfProjectOwnerName',NULL,0,NULL,'1','d2e0dbd0-1bd4-41a8-9687-42ab89dc3e88','2',NULL),('ae471809-9b18-4d3a-bc44-2fabc9cebcc0',NULL,'2011-10-17 10:24:13','referenceNumber',NULL,0,'ID','1','d2e0dbd0-1bd4-41a8-9687-42ab89dc3e88','2',NULL),('b9eb8b6f-cdc1-43ab-a12a-2ad6135d3533',NULL,'2011-10-17 10:24:13','recipeName',NULL,0,'Tech_req','1','d2e0dbd0-1bd4-41a8-9687-42ab89dc3e88','2',NULL),('cdecce93-0c47-4a81-a643-00f6267754be',NULL,'2011-10-17 10:24:13','outputVolumn',NULL,0,'Output_Vol','1','d2e0dbd0-1bd4-41a8-9687-42ab89dc3e88','2',NULL),('cea7707b-3bee-4849-a79d-3912d8c9d0a9',NULL,'2011-10-17 10:24:13','truckNumber',NULL,0,'Truck_No','1','d2e0dbd0-1bd4-41a8-9687-42ab89dc3e88','2',NULL),('d39c0b94-2b05-4371-8f3c-f84010edc793',NULL,'2011-10-17 10:24:13','constructionPartName',NULL,0,NULL,'1','d2e0dbd0-1bd4-41a8-9687-42ab89dc3e88','2',NULL);
/*!40000 ALTER TABLE `crmp_meta_etl_column_descriptor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crmp_meta_etl_relation_content_mapping`
--

DROP TABLE IF EXISTS `crmp_meta_etl_relation_content_mapping`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crmp_meta_etl_relation_content_mapping` (
  `ID` varchar(64) NOT NULL,
  `MAPPING_KEY` varchar(255) DEFAULT NULL,
  `MAPPED_DATA` varchar(255) DEFAULT NULL,
  `DELETED` tinyint(1) DEFAULT '0',
  `RAW_DATA` varchar(255) DEFAULT NULL,
  `CREATED_ON` datetime DEFAULT NULL,
  `UPDATED_ON` datetime DEFAULT NULL,
  `PLANT_ID` varchar(64) DEFAULT NULL,
  `CREATED_BY` varchar(64) NOT NULL,
  `COLUMNDESCRIPTOR_ID` varchar(64) DEFAULT NULL,
  `TENANT_ID` varchar(64) NOT NULL,
  `UPDATED_BY` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `crmp_meta_etl_relation_content_mapping_TENANT_ID` (`TENANT_ID`),
  KEY `crmp_meta_etl_relation_content_mapping_UPDATED_BY` (`UPDATED_BY`),
  KEY `FK_crmp_meta_etl_relation_content_mapping_PLANT_ID` (`PLANT_ID`),
  KEY `crmp_meta_etl_relation_content_mapping_CREATED_BY` (`CREATED_BY`),
  KEY `crmpmetaetlrelationcontentmappingCLUMNDESCRIPTORID` (`COLUMNDESCRIPTOR_ID`),
  CONSTRAINT `crmpmetaetlrelationcontentmappingCLUMNDESCRIPTORID` FOREIGN KEY (`COLUMNDESCRIPTOR_ID`) REFERENCES `crmp_meta_etl_column_descriptor` (`ID`),
  CONSTRAINT `crmp_meta_etl_relation_content_mapping_CREATED_BY` FOREIGN KEY (`CREATED_BY`) REFERENCES `CRMP_User` (`ID`),
  CONSTRAINT `crmp_meta_etl_relation_content_mapping_TENANT_ID` FOREIGN KEY (`TENANT_ID`) REFERENCES `CRMP_Tenant` (`ID`),
  CONSTRAINT `crmp_meta_etl_relation_content_mapping_UPDATED_BY` FOREIGN KEY (`UPDATED_BY`) REFERENCES `CRMP_User` (`ID`),
  CONSTRAINT `FK_crmp_meta_etl_relation_content_mapping_PLANT_ID` FOREIGN KEY (`PLANT_ID`) REFERENCES `crmp_concrete_mixing_plant` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crmp_meta_etl_relation_content_mapping`
--

LOCK TABLES `crmp_meta_etl_relation_content_mapping` WRITE;
/*!40000 ALTER TABLE `crmp_meta_etl_relation_content_mapping` DISABLE KEYS */;
INSERT INTO `crmp_meta_etl_relation_content_mapping` VALUES ('09d80804-24ef-43ed-bf87-c8c0fbdc419f','Production.ProductionRecord.unitOfProject.id','004845bd-90de-439c-8641-ec82b11b5fe8',0,'大学生公寓','2011-10-17 10:24:14',NULL,'efdfa381-cc24-488d-b8c4-2714635e73fb','2',NULL,'1',NULL),('105be02e-53c7-4029-9427-3b3b2b4dcdab','Production.ProductionRecord.unitOfProject.id','483dc6c9-458f-4d2e-a5dc-cc3871876ef1',0,'塞丽斯10#','2011-10-17 10:24:14',NULL,'efdfa381-cc24-488d-b8c4-2714635e73fb','2',NULL,'1',NULL),('15969ca7-9694-435a-b812-d5e8e184e7c9','Production.ProductionRecord.unitOfProject.id','17ccbd02-bb68-4e53-9929-34d097310fae',0,'恒大1#','2011-10-17 10:24:14',NULL,'efdfa381-cc24-488d-b8c4-2714635e73fb','2',NULL,'1',NULL),('1621da55-ed14-4398-9f02-f6843d46c865','Production.ProductionRecord.unitOfProject.id','6c62d8f4-9743-479b-b1ea-4d78181a85c8',0,'塞丽斯8#','2011-10-17 10:24:14',NULL,'efdfa381-cc24-488d-b8c4-2714635e73fb','2',NULL,'1',NULL),('1962df5b-657c-4540-875d-ec92b02cef93','Production.ProductionRecord.unitOfProject.id','8361baeb-cfab-40c9-8109-bdc0089924b1',0,'城北供热','2011-10-17 10:24:14',NULL,'efdfa381-cc24-488d-b8c4-2714635e73fb','2',NULL,'1',NULL),('19aefe54-46fe-4f94-a136-2ae8d18ba6d9','Production.ProductionRecord.unitOfProject.id','3fc4b117-2d5d-4c4b-bb98-668ba0dc2c1c',0,'金朝天下1#','2011-10-17 10:24:14',NULL,'efdfa381-cc24-488d-b8c4-2714635e73fb','2',NULL,'1',NULL),('1a87ade5-8daa-423a-a31a-6b1b6517905a','Production.ProductionRecord.unitOfProject.id','6666c818-c3bb-4e93-b201-e5c49b8bb204',0,'上京广场4#','2011-10-17 10:24:14',NULL,'efdfa381-cc24-488d-b8c4-2714635e73fb','2',NULL,'1',NULL),('2f60189c-4a17-4802-951b-57e3df233e9d','Production.ProductionRecord.unitOfProject.id','cb0d60d2-746a-40eb-8d89-269697aa1ab7',0,'哈飞50栋5#','2011-10-17 10:24:14',NULL,'efdfa381-cc24-488d-b8c4-2714635e73fb','2',NULL,'1',NULL),('4a1b265d-8731-442d-88b9-fb476d1b31ee','Production.ProductionRecord.unitOfProject.id','1903a41d-56ef-4da3-97ab-7cf015dca696',0,'个人','2011-10-17 10:24:14',NULL,'efdfa381-cc24-488d-b8c4-2714635e73fb','2',NULL,'1',NULL),('4dd3f774-e643-42ff-86ba-2f0d9049734d','Production.ProductionRecord.unitOfProject.id','f986c576-3504-452a-8c3a-8c4cb395064e',0,'新龙城11#','2011-10-17 10:24:14',NULL,'efdfa381-cc24-488d-b8c4-2714635e73fb','2',NULL,'1',NULL),('4fc82e12-74c9-4ac5-87ca-b89646f5d437','Production.ProductionRecord.unitOfProject.id','da351b28-edfa-45fe-a774-d2238a9081b9',0,'华龙金城','2011-10-17 10:24:14',NULL,'efdfa381-cc24-488d-b8c4-2714635e73fb','2',NULL,'1',NULL),('556cd787-5a6b-400b-a98f-e816e53664e6','Production.ProductionRecord.unitOfProject.id','c6acd1b0-d1ac-4ca0-9eec-5435ba69d862',0,'恒大综合楼','2011-10-17 10:24:14',NULL,'efdfa381-cc24-488d-b8c4-2714635e73fb','2',NULL,'1',NULL),('5cbb8d14-aa6a-4265-92f4-30e1913fe3d0','Production.ProductionRecord.unitOfProject.id','cbdfdf35-2e97-405c-b8d5-8fb10e9d0e44',0,'金朝天下2#','2011-10-17 10:24:14',NULL,'efdfa381-cc24-488d-b8c4-2714635e73fb','2',NULL,'1',NULL),('614648b2-1f45-4a4b-bdc7-ab25bc1903f0','Production.ProductionRecord.unitOfProject.id','d3c7c953-8450-4423-a6c9-44c5ff21d406',0,'铁精粉库','2011-10-17 10:24:14',NULL,'efdfa381-cc24-488d-b8c4-2714635e73fb','2',NULL,'1',NULL),('6274d46b-396d-4dd6-acc0-e3a4d324e86f','Production.ProductionRecord.unitOfProject.id','fef8496c-906c-4998-8697-0d13d0e25468',0,'恒大8#','2011-10-17 10:24:14',NULL,'efdfa381-cc24-488d-b8c4-2714635e73fb','2',NULL,'1',NULL),('7faa8ebf-e06c-4bec-a829-088fca0910dd','Production.ProductionRecord.unitOfProject.id','f5209f1d-3941-4522-a714-637ed8de3a0d',0,'永泰公寓','2011-10-17 10:24:14',NULL,'efdfa381-cc24-488d-b8c4-2714635e73fb','2',NULL,'1',NULL),('8845da47-e797-4d36-afbe-9ec1cea9c90b','Production.ProductionRecord.unitOfProject.id','68a93aaa-7437-43b1-a373-ccd0c8c6d883',0,'恒大3#','2011-10-17 10:24:14',NULL,'efdfa381-cc24-488d-b8c4-2714635e73fb','2',NULL,'1',NULL),('909ecb97-82fc-4f79-8b23-5d1d60f2f8da','Production.ProductionRecord.unitOfProject.id','2fea7b38-2170-48fb-a695-8268404ae511',0,'新龙城3#','2011-10-17 10:24:14',NULL,'efdfa381-cc24-488d-b8c4-2714635e73fb','2',NULL,'1',NULL),('918c0d9d-b8ba-4d69-9d1b-4f0d16f408d6','Production.ProductionRecord.unitOfProject.id','8ef73513-1f0c-4004-8364-e71ea984e34d',0,'哈飞50栋10#','2011-10-17 10:24:14',NULL,'efdfa381-cc24-488d-b8c4-2714635e73fb','2',NULL,'1',NULL),('97fd1c0c-5f60-48df-930c-8ba4ba9293bd','Production.ProductionRecord.unitOfProject.id','8b630aea-cce3-4e18-a71f-c91ee488f5c6',0,'华府家园','2011-10-17 10:24:14',NULL,'efdfa381-cc24-488d-b8c4-2714635e73fb','2',NULL,'1',NULL),('ac8c86c8-422d-4fe8-a415-398c4c0b53dd','Production.ProductionRecord.unitOfProject.id','7026e12f-a1c4-4211-88da-25bec69d129f',0,'上京广场B1#','2011-10-17 10:24:14',NULL,'efdfa381-cc24-488d-b8c4-2714635e73fb','2',NULL,'1',NULL),('b7f6cfd5-61e6-4908-a616-a604a11a93dc','Production.ProductionRecord.unitOfProject.id','3f0aa609-0810-41c8-bfbb-217486ff9011',0,'哈飞50栋8#','2011-10-17 10:24:14',NULL,'efdfa381-cc24-488d-b8c4-2714635e73fb','2',NULL,'1',NULL),('c52b2ab3-34c2-42cd-9cf9-f5737f57fa5f','Production.ProductionRecord.unitOfProject.id','956246aa-0c0a-4bbe-8c98-7489278b071c',0,'明达地面','2011-10-17 10:24:14',NULL,'efdfa381-cc24-488d-b8c4-2714635e73fb','2',NULL,'1',NULL),('dc2163fc-db52-489d-9b2a-7c4460ac69c6','Production.ProductionRecord.unitOfProject.id','75270bc4-03b4-48ab-b8fd-49f9c58cfc52',0,'个人地面','2011-10-17 10:24:14',NULL,'efdfa381-cc24-488d-b8c4-2714635e73fb','2',NULL,'1',NULL),('e34730ac-9c78-4827-b9b3-8fb26a143ce4','Production.ProductionRecord.unitOfProject.id','b6ae01b8-f8a5-4d86-bf0d-117f8e139a60',0,'奥林药业','2011-10-17 10:24:14',NULL,'efdfa381-cc24-488d-b8c4-2714635e73fb','2',NULL,'1',NULL),('f334f28f-71b4-4985-ae95-b347a2fe8243','Production.ProductionRecord.unitOfProject.id','f6d3cb80-37a3-42fc-b93e-da0c748fa8ce',0,'新龙城5#','2011-10-17 10:24:14',NULL,'efdfa381-cc24-488d-b8c4-2714635e73fb','2',NULL,'1',NULL),('f91abfb8-3f44-48a1-92ad-c42cbb51d99e','Production.ProductionRecord.unitOfProject.id','f66765af-637c-4c96-a1d0-f12cbc7616b8',0,'新龙城外网','2011-10-17 10:24:14',NULL,'efdfa381-cc24-488d-b8c4-2714635e73fb','2',NULL,'1',NULL);
/*!40000 ALTER TABLE `crmp_meta_etl_relation_content_mapping` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crmp_meta_etl_table_descriptor`
--

DROP TABLE IF EXISTS `crmp_meta_etl_table_descriptor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crmp_meta_etl_table_descriptor` (
  `ID` varchar(64) NOT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `TARGET_CLASS_NAME` varchar(255) DEFAULT NULL,
  `DELETED` tinyint(1) DEFAULT '0',
  `RAW_TABLE_NAME` varchar(255) DEFAULT NULL,
  `CREATED_ON` datetime DEFAULT NULL,
  `UPDATED_ON` datetime DEFAULT NULL,
  `TENANT_ID` varchar(64) NOT NULL,
  `DATABASE_ID` varchar(64) DEFAULT NULL,
  `CREATED_BY` varchar(64) NOT NULL,
  `UPDATED_BY` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_crmp_meta_etl_table_descriptor_CREATED_BY` (`CREATED_BY`),
  KEY `FK_crmp_meta_etl_table_descriptor_UPDATED_BY` (`UPDATED_BY`),
  KEY `FK_crmp_meta_etl_table_descriptor_TENANT_ID` (`TENANT_ID`),
  KEY `FK_crmp_meta_etl_table_descriptor_DATABASE_ID` (`DATABASE_ID`),
  CONSTRAINT `FK_crmp_meta_etl_table_descriptor_DATABASE_ID` FOREIGN KEY (`DATABASE_ID`) REFERENCES `crmp_meta_db_descriptor` (`ID`),
  CONSTRAINT `FK_crmp_meta_etl_table_descriptor_CREATED_BY` FOREIGN KEY (`CREATED_BY`) REFERENCES `CRMP_User` (`ID`),
  CONSTRAINT `FK_crmp_meta_etl_table_descriptor_TENANT_ID` FOREIGN KEY (`TENANT_ID`) REFERENCES `CRMP_Tenant` (`ID`),
  CONSTRAINT `FK_crmp_meta_etl_table_descriptor_UPDATED_BY` FOREIGN KEY (`UPDATED_BY`) REFERENCES `CRMP_User` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crmp_meta_etl_table_descriptor`
--

LOCK TABLES `crmp_meta_etl_table_descriptor` WRITE;
/*!40000 ALTER TABLE `crmp_meta_etl_table_descriptor` DISABLE KEYS */;
INSERT INTO `crmp_meta_etl_table_descriptor` VALUES ('d2e0dbd0-1bd4-41a8-9687-42ab89dc3e88',NULL,'net.madz.service.etl.to.RawProductionRecordTO',0,'srecords','2011-10-17 10:24:13',NULL,'1','5802403f-2a7f-423b-8d19-dd8f2cd52d9b','2',NULL);
/*!40000 ALTER TABLE `crmp_meta_etl_table_descriptor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crmp_meta_table_descriptor`
--

DROP TABLE IF EXISTS `crmp_meta_table_descriptor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crmp_meta_table_descriptor` (
  `ID` varchar(64) NOT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `UPDATED_ON` datetime DEFAULT NULL,
  `DELETED` tinyint(1) DEFAULT '0',
  `CREATED_ON` datetime DEFAULT NULL,
  `DATABASE_ID` varchar(64) DEFAULT NULL,
  `CREATED_BY` varchar(64) NOT NULL,
  `TENANT_ID` varchar(64) NOT NULL,
  `UPDATED_BY` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_crmp_meta_table_descriptor_UPDATED_BY` (`UPDATED_BY`),
  KEY `FK_crmp_meta_table_descriptor_DATABASE_ID` (`DATABASE_ID`),
  KEY `FK_crmp_meta_table_descriptor_TENANT_ID` (`TENANT_ID`),
  KEY `FK_crmp_meta_table_descriptor_CREATED_BY` (`CREATED_BY`),
  CONSTRAINT `FK_crmp_meta_table_descriptor_CREATED_BY` FOREIGN KEY (`CREATED_BY`) REFERENCES `CRMP_User` (`ID`),
  CONSTRAINT `FK_crmp_meta_table_descriptor_DATABASE_ID` FOREIGN KEY (`DATABASE_ID`) REFERENCES `crmp_meta_db_descriptor` (`ID`),
  CONSTRAINT `FK_crmp_meta_table_descriptor_TENANT_ID` FOREIGN KEY (`TENANT_ID`) REFERENCES `CRMP_Tenant` (`ID`),
  CONSTRAINT `FK_crmp_meta_table_descriptor_UPDATED_BY` FOREIGN KEY (`UPDATED_BY`) REFERENCES `CRMP_User` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crmp_meta_table_descriptor`
--

LOCK TABLES `crmp_meta_table_descriptor` WRITE;
/*!40000 ALTER TABLE `crmp_meta_table_descriptor` DISABLE KEYS */;
INSERT INTO `crmp_meta_table_descriptor` VALUES ('8b8974af-28fd-4201-bfb8-e1a3ae9524ae','HaoLiaoSum',NULL,0,'2011-10-17 10:16:05','5802403f-2a7f-423b-8d19-dd8f2cd52d9b','2','1',NULL),('f22b5fa0-8aa5-4fa0-b189-b2a4f39163ed','sRecords',NULL,0,'2011-10-17 10:16:05','5802403f-2a7f-423b-8d19-dd8f2cd52d9b','2','1',NULL),('f3ef45ff-4242-492f-9b64-11b1c7c92c9a','Hrh',NULL,0,'2011-10-17 10:16:05','5802403f-2a7f-423b-8d19-dd8f2cd52d9b','2','1',NULL);
/*!40000 ALTER TABLE `crmp_meta_table_descriptor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crmp_part_merchandise_pair`
--

DROP TABLE IF EXISTS `crmp_part_merchandise_pair`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crmp_part_merchandise_pair` (
  `ID` varchar(64) NOT NULL,
  `DELETED` tinyint(1) DEFAULT '0',
  `UPDATED_ON` datetime DEFAULT NULL,
  `CREATED_ON` datetime DEFAULT NULL,
  `MERCHANDISE_ID` varchar(64) DEFAULT NULL,
  `CONTRACT_ID` varchar(64) DEFAULT NULL,
  `TENANT_ID` varchar(64) NOT NULL,
  `PART_ID` varchar(64) DEFAULT NULL,
  `CREATED_BY` varchar(64) NOT NULL,
  `UPDATED_BY` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_crmp_part_merchandise_pair_PART_ID` (`PART_ID`),
  KEY `FK_crmp_part_merchandise_pair_CREATED_BY` (`CREATED_BY`),
  KEY `FK_crmp_part_merchandise_pair_TENANT_ID` (`TENANT_ID`),
  KEY `FK_crmp_part_merchandise_pair_CONTRACT_ID` (`CONTRACT_ID`),
  KEY `FK_crmp_part_merchandise_pair_UPDATED_BY` (`UPDATED_BY`),
  KEY `FK_crmp_part_merchandise_pair_MERCHANDISE_ID` (`MERCHANDISE_ID`),
  CONSTRAINT `FK_crmp_part_merchandise_pair_MERCHANDISE_ID` FOREIGN KEY (`MERCHANDISE_ID`) REFERENCES `crmp_product` (`ID`),
  CONSTRAINT `FK_crmp_part_merchandise_pair_CONTRACT_ID` FOREIGN KEY (`CONTRACT_ID`) REFERENCES `crmp_contract` (`ID`),
  CONSTRAINT `FK_crmp_part_merchandise_pair_CREATED_BY` FOREIGN KEY (`CREATED_BY`) REFERENCES `CRMP_User` (`ID`),
  CONSTRAINT `FK_crmp_part_merchandise_pair_PART_ID` FOREIGN KEY (`PART_ID`) REFERENCES `crmp_construction_part` (`ID`),
  CONSTRAINT `FK_crmp_part_merchandise_pair_TENANT_ID` FOREIGN KEY (`TENANT_ID`) REFERENCES `CRMP_Tenant` (`ID`),
  CONSTRAINT `FK_crmp_part_merchandise_pair_UPDATED_BY` FOREIGN KEY (`UPDATED_BY`) REFERENCES `CRMP_User` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crmp_part_merchandise_pair`
--

LOCK TABLES `crmp_part_merchandise_pair` WRITE;
/*!40000 ALTER TABLE `crmp_part_merchandise_pair` DISABLE KEYS */;
/*!40000 ALTER TABLE `crmp_part_merchandise_pair` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crmp_payment_invoice`
--

DROP TABLE IF EXISTS `crmp_payment_invoice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crmp_payment_invoice` (
  `ID` varchar(64) NOT NULL,
  `refund_amount` double DEFAULT NULL,
  `DELETED` tinyint(1) DEFAULT '0',
  `apply_amount` double DEFAULT NULL,
  `UPDATED_ON` datetime DEFAULT NULL,
  `CREATED_ON` datetime DEFAULT NULL,
  `invoice_id` varchar(64) NOT NULL,
  `TENANT_ID` varchar(64) NOT NULL,
  `payment_id` varchar(64) NOT NULL,
  `CREATED_BY` varchar(64) NOT NULL,
  `UPDATED_BY` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_crmp_payment_invoice_TENANT_ID` (`TENANT_ID`),
  KEY `FK_crmp_payment_invoice_UPDATED_BY` (`UPDATED_BY`),
  KEY `FK_crmp_payment_invoice_CREATED_BY` (`CREATED_BY`),
  KEY `FK_crmp_payment_invoice_payment_id` (`payment_id`),
  KEY `FK_crmp_payment_invoice_invoice_id` (`invoice_id`),
  CONSTRAINT `FK_crmp_payment_invoice_invoice_id` FOREIGN KEY (`invoice_id`) REFERENCES `crmp_invoice` (`ID`),
  CONSTRAINT `FK_crmp_payment_invoice_CREATED_BY` FOREIGN KEY (`CREATED_BY`) REFERENCES `CRMP_User` (`ID`),
  CONSTRAINT `FK_crmp_payment_invoice_payment_id` FOREIGN KEY (`payment_id`) REFERENCES `crmp_ar_payment` (`ID`),
  CONSTRAINT `FK_crmp_payment_invoice_TENANT_ID` FOREIGN KEY (`TENANT_ID`) REFERENCES `CRMP_Tenant` (`ID`),
  CONSTRAINT `FK_crmp_payment_invoice_UPDATED_BY` FOREIGN KEY (`UPDATED_BY`) REFERENCES `CRMP_User` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crmp_payment_invoice`
--

LOCK TABLES `crmp_payment_invoice` WRITE;
/*!40000 ALTER TABLE `crmp_payment_invoice` DISABLE KEYS */;
/*!40000 ALTER TABLE `crmp_payment_invoice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crmp_payment_method`
--

DROP TABLE IF EXISTS `crmp_payment_method`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crmp_payment_method` (
  `ID` varchar(64) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `DELETED` tinyint(1) DEFAULT '0',
  `CREATED_ON` datetime DEFAULT NULL,
  `UPDATED_ON` datetime DEFAULT NULL,
  `TENANT_ID` varchar(64) NOT NULL,
  `CREATED_BY` varchar(64) NOT NULL,
  `UPDATED_BY` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_crmp_payment_method_TENANT_ID` (`TENANT_ID`),
  KEY `FK_crmp_payment_method_UPDATED_BY` (`UPDATED_BY`),
  KEY `FK_crmp_payment_method_CREATED_BY` (`CREATED_BY`),
  CONSTRAINT `FK_crmp_payment_method_CREATED_BY` FOREIGN KEY (`CREATED_BY`) REFERENCES `CRMP_User` (`ID`),
  CONSTRAINT `FK_crmp_payment_method_TENANT_ID` FOREIGN KEY (`TENANT_ID`) REFERENCES `CRMP_Tenant` (`ID`),
  CONSTRAINT `FK_crmp_payment_method_UPDATED_BY` FOREIGN KEY (`UPDATED_BY`) REFERENCES `CRMP_User` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crmp_payment_method`
--

LOCK TABLES `crmp_payment_method` WRITE;
/*!40000 ALTER TABLE `crmp_payment_method` DISABLE KEYS */;
/*!40000 ALTER TABLE `crmp_payment_method` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crmp_product`
--

DROP TABLE IF EXISTS `crmp_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crmp_product` (
  `ID` varchar(64) NOT NULL,
  `SUGGESTPRICE` double DEFAULT NULL,
  `CREATED_ON` datetime DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `UPDATED_ON` datetime DEFAULT NULL,
  `CATEGORY` int(11) DEFAULT NULL,
  `NAME` varchar(255) NOT NULL,
  `DELETED` tinyint(1) DEFAULT '0',
  `CODE` varchar(255) NOT NULL,
  `TENANT_ID` varchar(64) NOT NULL,
  `UOM_ID` varchar(64) DEFAULT NULL,
  `CREATED_BY` varchar(64) NOT NULL,
  `UPDATED_BY` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_crmp_product_UPDATED_BY` (`UPDATED_BY`),
  KEY `FK_crmp_product_CREATED_BY` (`CREATED_BY`),
  KEY `FK_crmp_product_TENANT_ID` (`TENANT_ID`),
  KEY `FK_crmp_product_UOM_ID` (`UOM_ID`),
  CONSTRAINT `FK_crmp_product_UOM_ID` FOREIGN KEY (`UOM_ID`) REFERENCES `crmp_unit_of_measure` (`ID`),
  CONSTRAINT `FK_crmp_product_CREATED_BY` FOREIGN KEY (`CREATED_BY`) REFERENCES `CRMP_User` (`ID`),
  CONSTRAINT `FK_crmp_product_TENANT_ID` FOREIGN KEY (`TENANT_ID`) REFERENCES `CRMP_Tenant` (`ID`),
  CONSTRAINT `FK_crmp_product_UPDATED_BY` FOREIGN KEY (`UPDATED_BY`) REFERENCES `CRMP_User` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crmp_product`
--

LOCK TABLES `crmp_product` WRITE;
/*!40000 ALTER TABLE `crmp_product` DISABLE KEYS */;
INSERT INTO `crmp_product` VALUES ('13af8108-7492-4d18-80fd-ef944d7bfc40',20,'2011-10-17 11:19:09','外加剂','2011-10-17 11:32:08',0,'抗渗S6',0,'----6--','1','87abb1f6-aa72-4f62-a220-00f2e4306cbf','2','2'),('1bf6aa81-0248-4786-a372-2ab7120cc162',565,'2011-10-17 11:13:55','混凝土强度等级','2011-10-17 11:32:08',0,'C55',0,'C55----','1','87abb1f6-aa72-4f62-a220-00f2e4306cbf','2','2'),('1ed1bfd7-0a3b-4eaa-9a96-c27b28a011d8',40,'2011-10-17 11:19:52','外加剂','2011-10-17 11:32:08',0,'抗渗S10',0,'----0--','1','87abb1f6-aa72-4f62-a220-00f2e4306cbf','2','2'),('2cd77e7e-d8e2-4dac-b027-9455b2cdcda8',60,'2011-10-17 11:24:44','外加剂','2011-10-17 11:32:08',0,'防冻-15度',0,'-----3-','1','87abb1f6-aa72-4f62-a220-00f2e4306cbf','2','2'),('2d33b65f-ba57-4797-8689-993c35114cd5',365,'2011-10-17 11:12:27','混凝土强度等级','2011-10-17 11:32:08',0,'C30',0,'C30----','1','87abb1f6-aa72-4f62-a220-00f2e4306cbf','2','2'),('53e56be7-2963-476c-b8b1-6d58da3f2ca6',30,'2011-10-17 11:27:41','冬季施工费','2011-10-17 11:32:08',0,'冬季施工-10度以内',0,'------1','1','87abb1f6-aa72-4f62-a220-00f2e4306cbf','2','2'),('54c0a166-aa5c-4592-a753-82146e91b24d',340,'2011-10-17 11:16:07','砂浆强度等级','2011-10-17 11:32:08',0,'M15',0,'M15----','1','87abb1f6-aa72-4f62-a220-00f2e4306cbf','2','2'),('5de357f3-5601-4bd2-86b8-097b1ac5f1c6',20,'2011-06-22 01:44:46','外加剂','2011-10-17 11:32:08',0,'缓凝',0,'---H---','1','87abb1f6-aa72-4f62-a220-00f2e4306cbf','1','2'),('6448ee25-9b96-4c3d-b52b-a5676c761a8d',50,'2011-10-17 11:20:23','外加剂','2011-10-17 11:32:08',0,'抗渗S12',0,'----2--','1','87abb1f6-aa72-4f62-a220-00f2e4306cbf','2','2'),('6757e9c6-0f63-4c01-9355-98319e3d71cd',40,'2011-10-17 11:26:08','外加剂','2011-10-17 11:32:08',0,'路面',0,'------L','1','87abb1f6-aa72-4f62-a220-00f2e4306cbf','2','2'),('6a72fd5e-03b2-4284-b9cd-669458f84025',395,'2011-10-17 11:12:44','混凝土强度等级','2011-10-17 11:32:08',0,'C35',0,'C35----','1','87abb1f6-aa72-4f62-a220-00f2e4306cbf','2','2'),('7008cf24-af31-4b10-bc87-a2129dea62fe',40,'2011-10-17 11:24:21','外加剂','2011-10-17 11:32:08',0,'防冻-10度',0,'-----2-','1','87abb1f6-aa72-4f62-a220-00f2e4306cbf','2','2'),('75e26522-167e-4b03-a627-144195e337ed',355,'2011-10-17 11:12:14','混凝土强度等级','2011-10-17 11:32:08',0,'C25',0,'C25----','1','87abb1f6-aa72-4f62-a220-00f2e4306cbf','2','2'),('7a49cc20-b818-4de3-9dd1-a06eebc73eb4',320,'2011-10-17 11:15:35','砂浆强度等级','2011-10-17 11:32:08',0,'M7.5',0,'M75----','1','87abb1f6-aa72-4f62-a220-00f2e4306cbf','2','2'),('7bfefb68-0041-4fcb-bbb2-9960dbf61084',30,'2011-10-17 11:23:47','外加剂','2011-10-17 11:32:08',0,'微膨胀',0,'----W--','1','87abb1f6-aa72-4f62-a220-00f2e4306cbf','2','2'),('8082372b-f86c-4bc1-b0cb-49a5ea054d2f',515,'2011-10-17 11:13:39','混凝土强度等级','2011-10-17 11:32:08',0,'C50',0,'C50----','1','87abb1f6-aa72-4f62-a220-00f2e4306cbf','2','2'),('84355ae8-c84c-4a3b-ae4f-c0a524a731c0',310,'2011-10-17 11:15:17','砂浆强度等级','2011-10-17 11:32:08',0,'M5.0',0,'M50----','1','87abb1f6-aa72-4f62-a220-00f2e4306cbf','2','2'),('850e1729-4eab-4b3f-81c3-abd1a37ac7d4',475,'2011-10-17 11:13:16','混凝土强度等级','2011-10-17 11:32:08',0,'C45',0,'C45----','1','87abb1f6-aa72-4f62-a220-00f2e4306cbf','2','2'),('90eda1aa-3a38-4920-aadf-dc5013139f09',30,'2011-10-17 11:19:33','外加剂','2011-10-17 11:32:08',0,'抗渗S8',0,'----8--','1','87abb1f6-aa72-4f62-a220-00f2e4306cbf','2','2'),('96a772b7-644b-4636-b2fe-8c1341a94f83',625,'2011-10-17 11:14:19','混凝土强度等级','2011-10-17 11:32:08',0,'C60',0,'C60----','1','87abb1f6-aa72-4f62-a220-00f2e4306cbf','2','2'),('9f5d6bbe-06e3-4bcc-aca7-2adf4a5fec01',335,'2011-06-22 01:41:07','混凝土强度等级','2011-10-17 11:32:08',0,'C15',0,'C15----','1','87abb1f6-aa72-4f62-a220-00f2e4306cbf','1','2'),('a1d4cd20-224b-45ef-a6aa-f26643b68fa4',300,'2011-10-17 11:15:02','强度等级',NULL,0,'M2.5',0,'M25----','1','87abb1f6-aa72-4f62-a220-00f2e4306cbf','2',NULL),('ab5b63d2-ee71-4574-bf36-69b010135c04',30,'2011-06-22 01:43:42','外加剂','2011-10-17 11:32:08',0,'防冻-5度',0,'-----1-','1','87abb1f6-aa72-4f62-a220-00f2e4306cbf','1','2'),('b14f23e6-6f4f-41b6-90e0-9cd52e8bc1a7',330,'2011-10-17 11:15:50','砂浆强度等级','2011-10-17 11:32:08',0,'M10',0,'M10----','1','87abb1f6-aa72-4f62-a220-00f2e4306cbf','2','2'),('b55d4ef0-5280-4ff4-96c4-4263ea3ef636',325,'2011-06-22 01:40:08','混凝土强度等级','2011-10-17 11:32:08',0,'C10',0,'C10----','1','87abb1f6-aa72-4f62-a220-00f2e4306cbf','1','2'),('ba53a9eb-eba1-4885-90e0-881c298b2d36',20,'2011-10-17 11:25:50','外加剂','2011-10-17 11:32:08',0,'细石',0,'------X','1','87abb1f6-aa72-4f62-a220-00f2e4306cbf','2','2'),('bd169a7d-3c04-4511-be12-970909eb7565',80,'2011-10-17 11:25:01','外加剂','2011-10-17 11:32:08',0,'防冻-20度',0,'-----4-','1','87abb1f6-aa72-4f62-a220-00f2e4306cbf','2','2'),('c56283e6-ea3c-4058-9fc5-ca7bf0329855',400,'2011-10-17 11:16:24','砂浆强度等级','2011-10-17 11:32:08',0,'M20',0,'M20----','1','87abb1f6-aa72-4f62-a220-00f2e4306cbf','2','2'),('cb9672eb-a428-46fa-9d54-883fb0b8e1e1',20,'2011-10-17 11:17:11','外加剂','2011-10-17 11:32:08',0,'早强',0,'---Z---','1','87abb1f6-aa72-4f62-a220-00f2e4306cbf','2','2'),('db7f309c-64de-436f-b9f5-c325b0be79e1',435,'2011-10-17 11:12:57','混凝土强度等级','2011-10-17 11:32:08',0,'C40',0,'C40----','1','87abb1f6-aa72-4f62-a220-00f2e4306cbf','2','2'),('dbbe2536-1e84-4b4c-bbc5-7495af32a948',50,'2011-10-17 11:17:26','外加剂','2011-10-17 11:32:08',0,'超早强',0,'---C---','1','87abb1f6-aa72-4f62-a220-00f2e4306cbf','2','2'),('e4a3cb79-7b41-4e97-a211-44fc3234dfa8',20,'2011-10-17 11:25:18','外加剂','2011-10-17 11:32:08',0,'超流态',0,'------C','1','87abb1f6-aa72-4f62-a220-00f2e4306cbf','2','2'),('f09aa38f-9eb2-4a8e-86ea-ecbc8c7f76a0',345,'2011-06-22 01:41:30','混凝土强度等级','2011-10-17 11:32:08',0,'C20',0,'C20----','1','87abb1f6-aa72-4f62-a220-00f2e4306cbf','1','2'),('f2604691-0d18-4b57-8280-79384776c909',50,'2011-10-17 11:28:02','冬季施工费','2011-10-17 11:32:08',0,'冬季施工费-10度以上',0,'------2','1','87abb1f6-aa72-4f62-a220-00f2e4306cbf','2','2');
/*!40000 ALTER TABLE `crmp_product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crmp_production_record`
--

DROP TABLE IF EXISTS `crmp_production_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crmp_production_record` (
  `ID` varchar(64) NOT NULL,
  `DELETED` tinyint(1) DEFAULT '0',
  `UPDATED_ON` datetime DEFAULT NULL,
  `REFERENCE_NUMBER` varchar(32) DEFAULT NULL,
  `PRODUCTION_TIME` datetime NOT NULL,
  `CREATED_ON` datetime DEFAULT NULL,
  `TRUCK_NUMBER` varchar(20) DEFAULT NULL,
  `STATE` varchar(255) NOT NULL,
  `UNIT_OF_PROJECT_OWNER_NAME` varchar(40) DEFAULT NULL,
  `QUANTITY` double NOT NULL,
  `PROJECT_OWNER_NAME` varchar(40) DEFAULT NULL,
  `DESCRIPTION` varchar(2000) DEFAULT NULL,
  `UNITOFPROJECT_ID` varchar(64) DEFAULT NULL,
  `TENANT_ID` varchar(64) NOT NULL,
  `PART_ID` varchar(64) DEFAULT NULL,
  `UOM_ID` varchar(64) DEFAULT NULL,
  `MIXING_PLANT_ID` varchar(64) DEFAULT NULL,
  `CREATED_BY` varchar(64) NOT NULL,
  `UPDATED_BY` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_crmp_production_record_TENANT_ID` (`TENANT_ID`),
  KEY `FK_crmp_production_record_UOM_ID` (`UOM_ID`),
  KEY `FK_crmp_production_record_UNITOFPROJECT_ID` (`UNITOFPROJECT_ID`),
  KEY `FK_crmp_production_record_CREATED_BY` (`CREATED_BY`),
  KEY `FK_crmp_production_record_PART_ID` (`PART_ID`),
  KEY `FK_crmp_production_record_UPDATED_BY` (`UPDATED_BY`),
  KEY `FK_crmp_production_record_MIXING_PLANT_ID` (`MIXING_PLANT_ID`),
  CONSTRAINT `FK_crmp_production_record_MIXING_PLANT_ID` FOREIGN KEY (`MIXING_PLANT_ID`) REFERENCES `crmp_concrete_mixing_plant` (`ID`),
  CONSTRAINT `FK_crmp_production_record_CREATED_BY` FOREIGN KEY (`CREATED_BY`) REFERENCES `CRMP_User` (`ID`),
  CONSTRAINT `FK_crmp_production_record_PART_ID` FOREIGN KEY (`PART_ID`) REFERENCES `crmp_construction_part` (`ID`),
  CONSTRAINT `FK_crmp_production_record_TENANT_ID` FOREIGN KEY (`TENANT_ID`) REFERENCES `CRMP_Tenant` (`ID`),
  CONSTRAINT `FK_crmp_production_record_UNITOFPROJECT_ID` FOREIGN KEY (`UNITOFPROJECT_ID`) REFERENCES `crmp_unit_of_project` (`ID`),
  CONSTRAINT `FK_crmp_production_record_UOM_ID` FOREIGN KEY (`UOM_ID`) REFERENCES `crmp_unit_of_measure` (`ID`),
  CONSTRAINT `FK_crmp_production_record_UPDATED_BY` FOREIGN KEY (`UPDATED_BY`) REFERENCES `CRMP_User` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crmp_production_record`
--

LOCK TABLES `crmp_production_record` WRITE;
/*!40000 ALTER TABLE `crmp_production_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `crmp_production_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crmp_production_record_crmp_product`
--

DROP TABLE IF EXISTS `crmp_production_record_crmp_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crmp_production_record_crmp_product` (
  `ProductionRecordBO_ID` varchar(64) NOT NULL,
  `merchandises_ID` varchar(64) NOT NULL,
  PRIMARY KEY (`ProductionRecordBO_ID`,`merchandises_ID`),
  KEY `crmp_production_record_crmp_productmerchandises_ID` (`merchandises_ID`),
  CONSTRAINT `crmpproductionrecordcrmpproductPrductionRecordBOID` FOREIGN KEY (`ProductionRecordBO_ID`) REFERENCES `crmp_production_record` (`ID`),
  CONSTRAINT `crmp_production_record_crmp_productmerchandises_ID` FOREIGN KEY (`merchandises_ID`) REFERENCES `crmp_product` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crmp_production_record_crmp_product`
--

LOCK TABLES `crmp_production_record_crmp_product` WRITE;
/*!40000 ALTER TABLE `crmp_production_record_crmp_product` DISABLE KEYS */;
/*!40000 ALTER TABLE `crmp_production_record_crmp_product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crmp_project`
--

DROP TABLE IF EXISTS `crmp_project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crmp_project` (
  `ID` varchar(64) NOT NULL,
  `UPDATED_ON` datetime DEFAULT NULL,
  `CREATED_ON` datetime DEFAULT NULL,
  `DELETED` tinyint(1) DEFAULT '0',
  `NAME` varchar(60) DEFAULT NULL,
  `CREATED_BY` varchar(64) NOT NULL,
  `SOLD_TO_CONTACT_ID` varchar(64) DEFAULT NULL,
  `OWNER_ACCOUNT_ID` varchar(64) NOT NULL,
  `ADDRESS_ID` varchar(64) DEFAULT NULL,
  `PAYER_CONTACT_ID` varchar(64) DEFAULT NULL,
  `TENANT_ID` varchar(64) NOT NULL,
  `BILL_TO_CONTACT_ID` varchar(64) DEFAULT NULL,
  `SHIP_TO_CONTACT_ID` varchar(64) DEFAULT NULL,
  `UPDATED_BY` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_crmp_project_UPDATED_BY` (`UPDATED_BY`),
  KEY `FK_crmp_project_BILL_TO_CONTACT_ID` (`BILL_TO_CONTACT_ID`),
  KEY `FK_crmp_project_PAYER_CONTACT_ID` (`PAYER_CONTACT_ID`),
  KEY `FK_crmp_project_OWNER_ACCOUNT_ID` (`OWNER_ACCOUNT_ID`),
  KEY `FK_crmp_project_SHIP_TO_CONTACT_ID` (`SHIP_TO_CONTACT_ID`),
  KEY `FK_crmp_project_CREATED_BY` (`CREATED_BY`),
  KEY `FK_crmp_project_SOLD_TO_CONTACT_ID` (`SOLD_TO_CONTACT_ID`),
  KEY `FK_crmp_project_ADDRESS_ID` (`ADDRESS_ID`),
  KEY `FK_crmp_project_TENANT_ID` (`TENANT_ID`),
  CONSTRAINT `FK_crmp_project_TENANT_ID` FOREIGN KEY (`TENANT_ID`) REFERENCES `CRMP_Tenant` (`ID`),
  CONSTRAINT `FK_crmp_project_ADDRESS_ID` FOREIGN KEY (`ADDRESS_ID`) REFERENCES `crmp_address` (`ID`),
  CONSTRAINT `FK_crmp_project_BILL_TO_CONTACT_ID` FOREIGN KEY (`BILL_TO_CONTACT_ID`) REFERENCES `crmp_contact` (`ID`),
  CONSTRAINT `FK_crmp_project_CREATED_BY` FOREIGN KEY (`CREATED_BY`) REFERENCES `CRMP_User` (`ID`),
  CONSTRAINT `FK_crmp_project_OWNER_ACCOUNT_ID` FOREIGN KEY (`OWNER_ACCOUNT_ID`) REFERENCES `crmp_account` (`ID`),
  CONSTRAINT `FK_crmp_project_PAYER_CONTACT_ID` FOREIGN KEY (`PAYER_CONTACT_ID`) REFERENCES `crmp_contact` (`ID`),
  CONSTRAINT `FK_crmp_project_SHIP_TO_CONTACT_ID` FOREIGN KEY (`SHIP_TO_CONTACT_ID`) REFERENCES `crmp_contact` (`ID`),
  CONSTRAINT `FK_crmp_project_SOLD_TO_CONTACT_ID` FOREIGN KEY (`SOLD_TO_CONTACT_ID`) REFERENCES `crmp_contact` (`ID`),
  CONSTRAINT `FK_crmp_project_UPDATED_BY` FOREIGN KEY (`UPDATED_BY`) REFERENCES `CRMP_User` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crmp_project`
--

LOCK TABLES `crmp_project` WRITE;
/*!40000 ALTER TABLE `crmp_project` DISABLE KEYS */;
/*!40000 ALTER TABLE `crmp_project` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crmp_province`
--

DROP TABLE IF EXISTS `crmp_province`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crmp_province` (
  `ID` varchar(64) NOT NULL,
  `name` varchar(100) NOT NULL,
  `DELETED` tinyint(1) DEFAULT '0',
  `CREATED_ON` datetime DEFAULT NULL,
  `UPDATED_ON` datetime DEFAULT NULL,
  `TENANT_ID` varchar(64) NOT NULL,
  `CREATED_BY` varchar(64) NOT NULL,
  `UPDATED_BY` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_crmp_province_TENANT_ID` (`TENANT_ID`),
  KEY `FK_crmp_province_UPDATED_BY` (`UPDATED_BY`),
  KEY `FK_crmp_province_CREATED_BY` (`CREATED_BY`),
  CONSTRAINT `FK_crmp_province_CREATED_BY` FOREIGN KEY (`CREATED_BY`) REFERENCES `CRMP_User` (`ID`),
  CONSTRAINT `FK_crmp_province_TENANT_ID` FOREIGN KEY (`TENANT_ID`) REFERENCES `CRMP_Tenant` (`ID`),
  CONSTRAINT `FK_crmp_province_UPDATED_BY` FOREIGN KEY (`UPDATED_BY`) REFERENCES `CRMP_User` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crmp_province`
--

LOCK TABLES `crmp_province` WRITE;
/*!40000 ALTER TABLE `crmp_province` DISABLE KEYS */;
/*!40000 ALTER TABLE `crmp_province` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crmp_refund`
--

DROP TABLE IF EXISTS `crmp_refund`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crmp_refund` (
  `ID` varchar(64) NOT NULL,
  `state` varchar(20) NOT NULL,
  `total_amount` double DEFAULT NULL,
  `DELETED` tinyint(1) DEFAULT '0',
  `CREATED_ON` datetime DEFAULT NULL,
  `UPDATED_ON` datetime DEFAULT NULL,
  `CREATED_BY` varchar(64) NOT NULL,
  `TENANT_ID` varchar(64) NOT NULL,
  `UPDATED_BY` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_crmp_refund_TENANT_ID` (`TENANT_ID`),
  KEY `FK_crmp_refund_UPDATED_BY` (`UPDATED_BY`),
  KEY `FK_crmp_refund_CREATED_BY` (`CREATED_BY`),
  CONSTRAINT `FK_crmp_refund_CREATED_BY` FOREIGN KEY (`CREATED_BY`) REFERENCES `CRMP_User` (`ID`),
  CONSTRAINT `FK_crmp_refund_TENANT_ID` FOREIGN KEY (`TENANT_ID`) REFERENCES `CRMP_Tenant` (`ID`),
  CONSTRAINT `FK_crmp_refund_UPDATED_BY` FOREIGN KEY (`UPDATED_BY`) REFERENCES `CRMP_User` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crmp_refund`
--

LOCK TABLES `crmp_refund` WRITE;
/*!40000 ALTER TABLE `crmp_refund` DISABLE KEYS */;
/*!40000 ALTER TABLE `crmp_refund` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crmp_refund_payment`
--

DROP TABLE IF EXISTS `crmp_refund_payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crmp_refund_payment` (
  `ID` varchar(64) NOT NULL,
  `apply_amount` double DEFAULT NULL,
  `DELETED` tinyint(1) DEFAULT '0',
  `CREATED_ON` datetime DEFAULT NULL,
  `UPDATED_ON` datetime DEFAULT NULL,
  `TENANT_ID` varchar(64) NOT NULL,
  `CREATED_BY` varchar(64) NOT NULL,
  `UPDATED_BY` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_crmp_refund_payment_UPDATED_BY` (`UPDATED_BY`),
  KEY `FK_crmp_refund_payment_TENANT_ID` (`TENANT_ID`),
  KEY `FK_crmp_refund_payment_CREATED_BY` (`CREATED_BY`),
  CONSTRAINT `FK_crmp_refund_payment_CREATED_BY` FOREIGN KEY (`CREATED_BY`) REFERENCES `CRMP_User` (`ID`),
  CONSTRAINT `FK_crmp_refund_payment_TENANT_ID` FOREIGN KEY (`TENANT_ID`) REFERENCES `CRMP_Tenant` (`ID`),
  CONSTRAINT `FK_crmp_refund_payment_UPDATED_BY` FOREIGN KEY (`UPDATED_BY`) REFERENCES `CRMP_User` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crmp_refund_payment`
--

LOCK TABLES `crmp_refund_payment` WRITE;
/*!40000 ALTER TABLE `crmp_refund_payment` DISABLE KEYS */;
/*!40000 ALTER TABLE `crmp_refund_payment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crmp_refund_payment_invoice`
--

DROP TABLE IF EXISTS `crmp_refund_payment_invoice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crmp_refund_payment_invoice` (
  `ID` varchar(64) NOT NULL,
  `refund_amount` double NOT NULL,
  `UPDATED_ON` datetime DEFAULT NULL,
  `DELETED` tinyint(1) DEFAULT '0',
  `CREATED_ON` datetime DEFAULT NULL,
  `payment_invoice_id` varchar(64) NOT NULL,
  `TENANT_ID` varchar(64) NOT NULL,
  `CREATED_BY` varchar(64) NOT NULL,
  `refund_id` varchar(64) NOT NULL,
  `UPDATED_BY` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_crmp_refund_payment_invoice_CREATED_BY` (`CREATED_BY`),
  KEY `FK_crmp_refund_payment_invoice_payment_invoice_id` (`payment_invoice_id`),
  KEY `FK_crmp_refund_payment_invoice_refund_id` (`refund_id`),
  KEY `FK_crmp_refund_payment_invoice_TENANT_ID` (`TENANT_ID`),
  KEY `FK_crmp_refund_payment_invoice_UPDATED_BY` (`UPDATED_BY`),
  CONSTRAINT `FK_crmp_refund_payment_invoice_UPDATED_BY` FOREIGN KEY (`UPDATED_BY`) REFERENCES `CRMP_User` (`ID`),
  CONSTRAINT `FK_crmp_refund_payment_invoice_CREATED_BY` FOREIGN KEY (`CREATED_BY`) REFERENCES `CRMP_User` (`ID`),
  CONSTRAINT `FK_crmp_refund_payment_invoice_payment_invoice_id` FOREIGN KEY (`payment_invoice_id`) REFERENCES `crmp_payment_invoice` (`ID`),
  CONSTRAINT `FK_crmp_refund_payment_invoice_refund_id` FOREIGN KEY (`refund_id`) REFERENCES `crmp_refund` (`ID`),
  CONSTRAINT `FK_crmp_refund_payment_invoice_TENANT_ID` FOREIGN KEY (`TENANT_ID`) REFERENCES `CRMP_Tenant` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crmp_refund_payment_invoice`
--

LOCK TABLES `crmp_refund_payment_invoice` WRITE;
/*!40000 ALTER TABLE `crmp_refund_payment_invoice` DISABLE KEYS */;
/*!40000 ALTER TABLE `crmp_refund_payment_invoice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crmp_state_change_log`
--

DROP TABLE IF EXISTS `crmp_state_change_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crmp_state_change_log` (
  `ID` varchar(64) NOT NULL,
  `target_state_name` varchar(20) NOT NULL,
  `final_State` tinyint(1) DEFAULT '0',
  `data_type` varchar(40) NOT NULL,
  `create_on` datetime DEFAULT NULL,
  `data_id` varchar(32) NOT NULL,
  `source_state` varchar(20) NOT NULL,
  `tenant_id` varchar(64) NOT NULL,
  `created_by` varchar(64) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_crmp_state_change_log_tenant_id` (`tenant_id`),
  KEY `FK_crmp_state_change_log_created_by` (`created_by`),
  CONSTRAINT `FK_crmp_state_change_log_created_by` FOREIGN KEY (`created_by`) REFERENCES `CRMP_User` (`ID`),
  CONSTRAINT `FK_crmp_state_change_log_tenant_id` FOREIGN KEY (`tenant_id`) REFERENCES `CRMP_Tenant` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crmp_state_change_log`
--

LOCK TABLES `crmp_state_change_log` WRITE;
/*!40000 ALTER TABLE `crmp_state_change_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `crmp_state_change_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crmp_street`
--

DROP TABLE IF EXISTS `crmp_street`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crmp_street` (
  `ID` varchar(64) NOT NULL,
  `name` varchar(100) NOT NULL,
  `CREATED_ON` datetime DEFAULT NULL,
  `UPDATED_ON` datetime DEFAULT NULL,
  `DELETED` tinyint(1) DEFAULT '0',
  `TENANT_ID` varchar(64) NOT NULL,
  `district_id` varchar(64) DEFAULT NULL,
  `CREATED_BY` varchar(64) NOT NULL,
  `UPDATED_BY` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_crmp_street_TENANT_ID` (`TENANT_ID`),
  KEY `FK_crmp_street_CREATED_BY` (`CREATED_BY`),
  KEY `FK_crmp_street_UPDATED_BY` (`UPDATED_BY`),
  KEY `FK_crmp_street_district_id` (`district_id`),
  CONSTRAINT `FK_crmp_street_district_id` FOREIGN KEY (`district_id`) REFERENCES `crmp_district` (`ID`),
  CONSTRAINT `FK_crmp_street_CREATED_BY` FOREIGN KEY (`CREATED_BY`) REFERENCES `CRMP_User` (`ID`),
  CONSTRAINT `FK_crmp_street_TENANT_ID` FOREIGN KEY (`TENANT_ID`) REFERENCES `CRMP_Tenant` (`ID`),
  CONSTRAINT `FK_crmp_street_UPDATED_BY` FOREIGN KEY (`UPDATED_BY`) REFERENCES `CRMP_User` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crmp_street`
--

LOCK TABLES `crmp_street` WRITE;
/*!40000 ALTER TABLE `crmp_street` DISABLE KEYS */;
/*!40000 ALTER TABLE `crmp_street` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crmp_tenant`
--

DROP TABLE IF EXISTS `crmp_tenant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crmp_tenant` (
  `ID` varchar(64) NOT NULL,
  `SERVICE_DAYS_PAID` int(11) DEFAULT NULL,
  `SERVICE_DAYS_LEFT` int(11) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `MATURITY_DATE` datetime DEFAULT NULL,
  `ARTIFICIAL_PERSON_NAME` varchar(255) DEFAULT NULL,
  `ARREARAGE` tinyint(1) DEFAULT '0',
  `FREEZEN` tinyint(1) DEFAULT '0',
  `EVALUATED` tinyint(1) DEFAULT '0',
  `PAYMENT` double DEFAULT NULL,
  `UPDATED_ON` datetime DEFAULT NULL,
  `CREATED_ON` datetime DEFAULT NULL,
  `LOCKED` tinyint(1) DEFAULT '0',
  `DELETED` tinyint(1) DEFAULT '0',
  `HISTORY_SERVICE_DAYS` int(11) DEFAULT NULL,
  `PAYMENT_DATE` datetime DEFAULT NULL,
  `ADDRESS` varchar(255) DEFAULT NULL,
  `created_by` varchar(32) NOT NULL,
  `PARENT_TENANT_ID` varchar(64) DEFAULT NULL,
  `UPDATED_BY` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_CRMP_Tenant_PARENT_TENANT_ID` (`PARENT_TENANT_ID`),
  KEY `FK_CRMP_Tenant_CREATED_BY` (`created_by`),
  KEY `FK_CRMP_Tenant_UPDATED_BY` (`UPDATED_BY`),
  CONSTRAINT `FK_CRMP_Tenant_CREATED_BY` FOREIGN KEY (`CREATED_BY`) REFERENCES `CRMP_User` (`ID`),
  CONSTRAINT `FK_CRMP_Tenant_PARENT_TENANT_ID` FOREIGN KEY (`PARENT_TENANT_ID`) REFERENCES `CRMP_Tenant` (`ID`),
  CONSTRAINT `FK_CRMP_Tenant_UPDATED_BY` FOREIGN KEY (`UPDATED_BY`) REFERENCES `CRMP_User` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crmp_tenant`
--

LOCK TABLES `crmp_tenant` WRITE;
/*!40000 ALTER TABLE `crmp_tenant` DISABLE KEYS */;
INSERT INTO `crmp_tenant` VALUES ('1',65535,65535,'Madz Technologies','9999-06-11 19:01:53','Barry',0,0,0,0,NULL,'2011-06-11 19:01:53',0,0,0,'2011-06-11 19:01:53','','1',NULL,NULL);
/*!40000 ALTER TABLE `crmp_tenant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crmp_tenant_operation_log`
--

DROP TABLE IF EXISTS `crmp_tenant_operation_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crmp_tenant_operation_log` (
  `ID` bigint(20) NOT NULL,
  `TENANT_ID` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_crmp_tenant_operation_log_TENANT_ID` (`TENANT_ID`),
  CONSTRAINT `FK_crmp_tenant_operation_log_ID` FOREIGN KEY (`ID`) REFERENCES `CRMP_OPERATION_LOG` (`ID`),
  CONSTRAINT `FK_crmp_tenant_operation_log_TENANT_ID` FOREIGN KEY (`TENANT_ID`) REFERENCES `CRMP_Tenant` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crmp_tenant_operation_log`
--

LOCK TABLES `crmp_tenant_operation_log` WRITE;
/*!40000 ALTER TABLE `crmp_tenant_operation_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `crmp_tenant_operation_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crmp_unit_of_measure`
--

DROP TABLE IF EXISTS `crmp_unit_of_measure`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crmp_unit_of_measure` (
  `ID` varchar(64) NOT NULL,
  `NAME` varchar(40) DEFAULT NULL,
  `CREATED_ON` datetime DEFAULT NULL,
  `DESCRIPTION` varchar(80) DEFAULT NULL,
  `UPDATED_ON` datetime DEFAULT NULL,
  `DELETED` tinyint(1) DEFAULT '0',
  `TENANT_ID` varchar(64) NOT NULL,
  `CREATED_BY` varchar(64) NOT NULL,
  `UPDATED_BY` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_crmp_unit_of_measure_UPDATED_BY` (`UPDATED_BY`),
  KEY `FK_crmp_unit_of_measure_TENANT_ID` (`TENANT_ID`),
  KEY `FK_crmp_unit_of_measure_CREATED_BY` (`CREATED_BY`),
  CONSTRAINT `FK_crmp_unit_of_measure_CREATED_BY` FOREIGN KEY (`CREATED_BY`) REFERENCES `CRMP_User` (`ID`),
  CONSTRAINT `FK_crmp_unit_of_measure_TENANT_ID` FOREIGN KEY (`TENANT_ID`) REFERENCES `CRMP_Tenant` (`ID`),
  CONSTRAINT `FK_crmp_unit_of_measure_UPDATED_BY` FOREIGN KEY (`UPDATED_BY`) REFERENCES `CRMP_User` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crmp_unit_of_measure`
--

LOCK TABLES `crmp_unit_of_measure` WRITE;
/*!40000 ALTER TABLE `crmp_unit_of_measure` DISABLE KEYS */;
INSERT INTO `crmp_unit_of_measure` VALUES ('87abb1f6-aa72-4f62-a220-00f2e4306cbf','方','2011-06-22 01:39:35','立方米','2011-08-08 22:10:29',0,'1','2','2');
/*!40000 ALTER TABLE `crmp_unit_of_measure` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crmp_unit_of_project`
--

DROP TABLE IF EXISTS `crmp_unit_of_project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crmp_unit_of_project` (
  `ID` varchar(64) NOT NULL,
  `CREATED_ON` datetime DEFAULT NULL,
  `NAME` varchar(60) DEFAULT NULL,
  `UPDATED_ON` datetime DEFAULT NULL,
  `START_DATE` datetime DEFAULT NULL,
  `DELETED` tinyint(1) DEFAULT '0',
  `END_DATE` datetime DEFAULT NULL,
  `TENANT_ID` varchar(64) NOT NULL,
  `SOLD_TO_CONTACT_ID` varchar(64) DEFAULT NULL,
  `PROJECT_ID` varchar(64) DEFAULT NULL,
  `CONTRACT_ID` varchar(64) DEFAULT NULL,
  `PAYER_CONTACT_ID` varchar(64) DEFAULT NULL,
  `CREATED_BY` varchar(64) NOT NULL,
  `BILL_TO_CONTACT_ID` varchar(64) DEFAULT NULL,
  `SHIP_TO_CONTACT_ID` varchar(64) DEFAULT NULL,
  `UPDATED_BY` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_crmp_unit_of_project_CREATED_BY` (`CREATED_BY`),
  KEY `FK_crmp_unit_of_project_PROJECT_ID` (`PROJECT_ID`),
  KEY `FK_crmp_unit_of_project_UPDATED_BY` (`UPDATED_BY`),
  KEY `FK_crmp_unit_of_project_SOLD_TO_CONTACT_ID` (`SOLD_TO_CONTACT_ID`),
  KEY `FK_crmp_unit_of_project_SHIP_TO_CONTACT_ID` (`SHIP_TO_CONTACT_ID`),
  KEY `FK_crmp_unit_of_project_TENANT_ID` (`TENANT_ID`),
  KEY `FK_crmp_unit_of_project_CONTRACT_ID` (`CONTRACT_ID`),
  KEY `FK_crmp_unit_of_project_PAYER_CONTACT_ID` (`PAYER_CONTACT_ID`),
  KEY `FK_crmp_unit_of_project_BILL_TO_CONTACT_ID` (`BILL_TO_CONTACT_ID`),
  CONSTRAINT `FK_crmp_unit_of_project_BILL_TO_CONTACT_ID` FOREIGN KEY (`BILL_TO_CONTACT_ID`) REFERENCES `crmp_contact` (`ID`),
  CONSTRAINT `FK_crmp_unit_of_project_CONTRACT_ID` FOREIGN KEY (`CONTRACT_ID`) REFERENCES `crmp_contract` (`ID`),
  CONSTRAINT `FK_crmp_unit_of_project_CREATED_BY` FOREIGN KEY (`CREATED_BY`) REFERENCES `CRMP_User` (`ID`),
  CONSTRAINT `FK_crmp_unit_of_project_PAYER_CONTACT_ID` FOREIGN KEY (`PAYER_CONTACT_ID`) REFERENCES `crmp_contact` (`ID`),
  CONSTRAINT `FK_crmp_unit_of_project_PROJECT_ID` FOREIGN KEY (`PROJECT_ID`) REFERENCES `crmp_project` (`ID`),
  CONSTRAINT `FK_crmp_unit_of_project_SHIP_TO_CONTACT_ID` FOREIGN KEY (`SHIP_TO_CONTACT_ID`) REFERENCES `crmp_contact` (`ID`),
  CONSTRAINT `FK_crmp_unit_of_project_SOLD_TO_CONTACT_ID` FOREIGN KEY (`SOLD_TO_CONTACT_ID`) REFERENCES `crmp_contact` (`ID`),
  CONSTRAINT `FK_crmp_unit_of_project_TENANT_ID` FOREIGN KEY (`TENANT_ID`) REFERENCES `CRMP_Tenant` (`ID`),
  CONSTRAINT `FK_crmp_unit_of_project_UPDATED_BY` FOREIGN KEY (`UPDATED_BY`) REFERENCES `CRMP_User` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crmp_unit_of_project`
--

LOCK TABLES `crmp_unit_of_project` WRITE;
/*!40000 ALTER TABLE `crmp_unit_of_project` DISABLE KEYS */;
INSERT INTO `crmp_unit_of_project` VALUES ('004845bd-90de-439c-8641-ec82b11b5fe8','2011-10-17 10:24:14','大学生公寓',NULL,NULL,0,NULL,'1','ee09ec0c-e317-489d-a7be-ea1d40110bfa',NULL,NULL,'ee09ec0c-e317-489d-a7be-ea1d40110bfa','2','ee09ec0c-e317-489d-a7be-ea1d40110bfa','ee09ec0c-e317-489d-a7be-ea1d40110bfa',NULL),('17ccbd02-bb68-4e53-9929-34d097310fae','2011-10-17 10:24:14','恒大1#',NULL,NULL,0,NULL,'1','ee09ec0c-e317-489d-a7be-ea1d40110bfa',NULL,NULL,'ee09ec0c-e317-489d-a7be-ea1d40110bfa','2','ee09ec0c-e317-489d-a7be-ea1d40110bfa','ee09ec0c-e317-489d-a7be-ea1d40110bfa',NULL),('1903a41d-56ef-4da3-97ab-7cf015dca696','2011-10-17 10:24:14','个人',NULL,NULL,0,NULL,'1','ee09ec0c-e317-489d-a7be-ea1d40110bfa',NULL,NULL,'ee09ec0c-e317-489d-a7be-ea1d40110bfa','2','ee09ec0c-e317-489d-a7be-ea1d40110bfa','ee09ec0c-e317-489d-a7be-ea1d40110bfa',NULL),('2fea7b38-2170-48fb-a695-8268404ae511','2011-10-17 10:24:14','新龙城3#',NULL,NULL,0,NULL,'1','ee09ec0c-e317-489d-a7be-ea1d40110bfa',NULL,NULL,'ee09ec0c-e317-489d-a7be-ea1d40110bfa','2','ee09ec0c-e317-489d-a7be-ea1d40110bfa','ee09ec0c-e317-489d-a7be-ea1d40110bfa',NULL),('3f0aa609-0810-41c8-bfbb-217486ff9011','2011-10-17 10:24:14','哈飞50栋8#',NULL,NULL,0,NULL,'1','ee09ec0c-e317-489d-a7be-ea1d40110bfa',NULL,NULL,'ee09ec0c-e317-489d-a7be-ea1d40110bfa','2','ee09ec0c-e317-489d-a7be-ea1d40110bfa','ee09ec0c-e317-489d-a7be-ea1d40110bfa',NULL),('3fc4b117-2d5d-4c4b-bb98-668ba0dc2c1c','2011-10-17 10:24:14','金朝天下1#',NULL,NULL,0,NULL,'1','ee09ec0c-e317-489d-a7be-ea1d40110bfa',NULL,NULL,'ee09ec0c-e317-489d-a7be-ea1d40110bfa','2','ee09ec0c-e317-489d-a7be-ea1d40110bfa','ee09ec0c-e317-489d-a7be-ea1d40110bfa',NULL),('483dc6c9-458f-4d2e-a5dc-cc3871876ef1','2011-10-17 10:24:14','塞丽斯10#',NULL,NULL,0,NULL,'1','ee09ec0c-e317-489d-a7be-ea1d40110bfa',NULL,NULL,'ee09ec0c-e317-489d-a7be-ea1d40110bfa','2','ee09ec0c-e317-489d-a7be-ea1d40110bfa','ee09ec0c-e317-489d-a7be-ea1d40110bfa',NULL),('6666c818-c3bb-4e93-b201-e5c49b8bb204','2011-10-17 10:24:14','上京广场4#',NULL,NULL,0,NULL,'1','ee09ec0c-e317-489d-a7be-ea1d40110bfa',NULL,NULL,'ee09ec0c-e317-489d-a7be-ea1d40110bfa','2','ee09ec0c-e317-489d-a7be-ea1d40110bfa','ee09ec0c-e317-489d-a7be-ea1d40110bfa',NULL),('68a93aaa-7437-43b1-a373-ccd0c8c6d883','2011-10-17 10:24:14','恒大3#',NULL,NULL,0,NULL,'1','ee09ec0c-e317-489d-a7be-ea1d40110bfa',NULL,NULL,'ee09ec0c-e317-489d-a7be-ea1d40110bfa','2','ee09ec0c-e317-489d-a7be-ea1d40110bfa','ee09ec0c-e317-489d-a7be-ea1d40110bfa',NULL),('6c62d8f4-9743-479b-b1ea-4d78181a85c8','2011-10-17 10:24:14','塞丽斯8#',NULL,NULL,0,NULL,'1','ee09ec0c-e317-489d-a7be-ea1d40110bfa',NULL,NULL,'ee09ec0c-e317-489d-a7be-ea1d40110bfa','2','ee09ec0c-e317-489d-a7be-ea1d40110bfa','ee09ec0c-e317-489d-a7be-ea1d40110bfa',NULL),('7026e12f-a1c4-4211-88da-25bec69d129f','2011-10-17 10:24:14','上京广场B1#',NULL,NULL,0,NULL,'1','ee09ec0c-e317-489d-a7be-ea1d40110bfa',NULL,NULL,'ee09ec0c-e317-489d-a7be-ea1d40110bfa','2','ee09ec0c-e317-489d-a7be-ea1d40110bfa','ee09ec0c-e317-489d-a7be-ea1d40110bfa',NULL),('75270bc4-03b4-48ab-b8fd-49f9c58cfc52','2011-10-17 10:24:14','个人地面',NULL,NULL,0,NULL,'1','ee09ec0c-e317-489d-a7be-ea1d40110bfa',NULL,NULL,'ee09ec0c-e317-489d-a7be-ea1d40110bfa','2','ee09ec0c-e317-489d-a7be-ea1d40110bfa','ee09ec0c-e317-489d-a7be-ea1d40110bfa',NULL),('8361baeb-cfab-40c9-8109-bdc0089924b1','2011-10-17 10:24:14','城北供热',NULL,NULL,0,NULL,'1','ee09ec0c-e317-489d-a7be-ea1d40110bfa',NULL,NULL,'ee09ec0c-e317-489d-a7be-ea1d40110bfa','2','ee09ec0c-e317-489d-a7be-ea1d40110bfa','ee09ec0c-e317-489d-a7be-ea1d40110bfa',NULL),('8b630aea-cce3-4e18-a71f-c91ee488f5c6','2011-10-17 10:24:14','华府家园',NULL,NULL,0,NULL,'1','ee09ec0c-e317-489d-a7be-ea1d40110bfa',NULL,NULL,'ee09ec0c-e317-489d-a7be-ea1d40110bfa','2','ee09ec0c-e317-489d-a7be-ea1d40110bfa','ee09ec0c-e317-489d-a7be-ea1d40110bfa',NULL),('8ef73513-1f0c-4004-8364-e71ea984e34d','2011-10-17 10:24:14','哈飞50栋10#',NULL,NULL,0,NULL,'1','ee09ec0c-e317-489d-a7be-ea1d40110bfa',NULL,NULL,'ee09ec0c-e317-489d-a7be-ea1d40110bfa','2','ee09ec0c-e317-489d-a7be-ea1d40110bfa','ee09ec0c-e317-489d-a7be-ea1d40110bfa',NULL),('956246aa-0c0a-4bbe-8c98-7489278b071c','2011-10-17 10:24:14','明达地面',NULL,NULL,0,NULL,'1','ee09ec0c-e317-489d-a7be-ea1d40110bfa',NULL,NULL,'ee09ec0c-e317-489d-a7be-ea1d40110bfa','2','ee09ec0c-e317-489d-a7be-ea1d40110bfa','ee09ec0c-e317-489d-a7be-ea1d40110bfa',NULL),('b6ae01b8-f8a5-4d86-bf0d-117f8e139a60','2011-10-17 10:24:14','奥林药业',NULL,NULL,0,NULL,'1','ee09ec0c-e317-489d-a7be-ea1d40110bfa',NULL,NULL,'ee09ec0c-e317-489d-a7be-ea1d40110bfa','2','ee09ec0c-e317-489d-a7be-ea1d40110bfa','ee09ec0c-e317-489d-a7be-ea1d40110bfa',NULL),('c6acd1b0-d1ac-4ca0-9eec-5435ba69d862','2011-10-17 10:24:14','恒大综合楼',NULL,NULL,0,NULL,'1','ee09ec0c-e317-489d-a7be-ea1d40110bfa',NULL,NULL,'ee09ec0c-e317-489d-a7be-ea1d40110bfa','2','ee09ec0c-e317-489d-a7be-ea1d40110bfa','ee09ec0c-e317-489d-a7be-ea1d40110bfa',NULL),('cb0d60d2-746a-40eb-8d89-269697aa1ab7','2011-10-17 10:24:14','哈飞50栋5#',NULL,NULL,0,NULL,'1','ee09ec0c-e317-489d-a7be-ea1d40110bfa',NULL,NULL,'ee09ec0c-e317-489d-a7be-ea1d40110bfa','2','ee09ec0c-e317-489d-a7be-ea1d40110bfa','ee09ec0c-e317-489d-a7be-ea1d40110bfa',NULL),('cbdfdf35-2e97-405c-b8d5-8fb10e9d0e44','2011-10-17 10:24:14','金朝天下2#',NULL,NULL,0,NULL,'1','ee09ec0c-e317-489d-a7be-ea1d40110bfa',NULL,NULL,'ee09ec0c-e317-489d-a7be-ea1d40110bfa','2','ee09ec0c-e317-489d-a7be-ea1d40110bfa','ee09ec0c-e317-489d-a7be-ea1d40110bfa',NULL),('d3c7c953-8450-4423-a6c9-44c5ff21d406','2011-10-17 10:24:14','铁精粉库',NULL,NULL,0,NULL,'1','ee09ec0c-e317-489d-a7be-ea1d40110bfa',NULL,NULL,'ee09ec0c-e317-489d-a7be-ea1d40110bfa','2','ee09ec0c-e317-489d-a7be-ea1d40110bfa','ee09ec0c-e317-489d-a7be-ea1d40110bfa',NULL),('da351b28-edfa-45fe-a774-d2238a9081b9','2011-10-17 10:24:14','华龙金城',NULL,NULL,0,NULL,'1','ee09ec0c-e317-489d-a7be-ea1d40110bfa',NULL,NULL,'ee09ec0c-e317-489d-a7be-ea1d40110bfa','2','ee09ec0c-e317-489d-a7be-ea1d40110bfa','ee09ec0c-e317-489d-a7be-ea1d40110bfa',NULL),('f5209f1d-3941-4522-a714-637ed8de3a0d','2011-10-17 10:24:14','永泰公寓',NULL,NULL,0,NULL,'1','ee09ec0c-e317-489d-a7be-ea1d40110bfa',NULL,NULL,'ee09ec0c-e317-489d-a7be-ea1d40110bfa','2','ee09ec0c-e317-489d-a7be-ea1d40110bfa','ee09ec0c-e317-489d-a7be-ea1d40110bfa',NULL),('f66765af-637c-4c96-a1d0-f12cbc7616b8','2011-10-17 10:24:14','新龙城外网',NULL,NULL,0,NULL,'1','ee09ec0c-e317-489d-a7be-ea1d40110bfa',NULL,NULL,'ee09ec0c-e317-489d-a7be-ea1d40110bfa','2','ee09ec0c-e317-489d-a7be-ea1d40110bfa','ee09ec0c-e317-489d-a7be-ea1d40110bfa',NULL),('f6d3cb80-37a3-42fc-b93e-da0c748fa8ce','2011-10-17 10:24:14','新龙城5#',NULL,NULL,0,NULL,'1','ee09ec0c-e317-489d-a7be-ea1d40110bfa',NULL,NULL,'ee09ec0c-e317-489d-a7be-ea1d40110bfa','2','ee09ec0c-e317-489d-a7be-ea1d40110bfa','ee09ec0c-e317-489d-a7be-ea1d40110bfa',NULL),('f986c576-3504-452a-8c3a-8c4cb395064e','2011-10-17 10:24:14','新龙城11#',NULL,NULL,0,NULL,'1','ee09ec0c-e317-489d-a7be-ea1d40110bfa',NULL,NULL,'ee09ec0c-e317-489d-a7be-ea1d40110bfa','2','ee09ec0c-e317-489d-a7be-ea1d40110bfa','ee09ec0c-e317-489d-a7be-ea1d40110bfa',NULL),('fef8496c-906c-4998-8697-0d13d0e25468','2011-10-17 10:24:14','恒大8#',NULL,NULL,0,NULL,'1','ee09ec0c-e317-489d-a7be-ea1d40110bfa',NULL,NULL,'ee09ec0c-e317-489d-a7be-ea1d40110bfa','2','ee09ec0c-e317-489d-a7be-ea1d40110bfa','ee09ec0c-e317-489d-a7be-ea1d40110bfa',NULL);
/*!40000 ALTER TABLE `crmp_unit_of_project` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crmp_unitofproject_constructionpart`
--

DROP TABLE IF EXISTS `crmp_unitofproject_constructionpart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crmp_unitofproject_constructionpart` (
  `UNIT_OF_PROJECT_ID` varchar(64) NOT NULL,
  `CONSTRUCTION_ID` varchar(64) NOT NULL,
  PRIMARY KEY (`UNIT_OF_PROJECT_ID`,`CONSTRUCTION_ID`),
  KEY `crmp_unitofproject_constructionpartCONSTRUCTION_ID` (`CONSTRUCTION_ID`),
  CONSTRAINT `crmpunitofprojectconstructionpartUNITOF_PROJECT_ID` FOREIGN KEY (`UNIT_OF_PROJECT_ID`) REFERENCES `crmp_unit_of_project` (`ID`),
  CONSTRAINT `crmp_unitofproject_constructionpartCONSTRUCTION_ID` FOREIGN KEY (`CONSTRUCTION_ID`) REFERENCES `crmp_construction_part` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crmp_unitofproject_constructionpart`
--

LOCK TABLES `crmp_unitofproject_constructionpart` WRITE;
/*!40000 ALTER TABLE `crmp_unitofproject_constructionpart` DISABLE KEYS */;
/*!40000 ALTER TABLE `crmp_unitofproject_constructionpart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crmp_user_group_crmp_role`
--

DROP TABLE IF EXISTS `crmp_user_group_crmp_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crmp_user_group_crmp_role` (
  `groups_ID` varchar(64) NOT NULL,
  `roles_ID` varchar(64) NOT NULL,
  `GROUP_NAME` varchar(40) NOT NULL,
  PRIMARY KEY (`groups_ID`,`roles_ID`),
  KEY `FK_CRMP_USER_GROUP_CRMP_Role_roles_ID` (`roles_ID`),
  CONSTRAINT `FK_CRMP_USER_GROUP_CRMP_Role_groups_ID` FOREIGN KEY (`groups_ID`) REFERENCES `CRMP_USER_GROUP` (`ID`),
  CONSTRAINT `FK_CRMP_USER_GROUP_CRMP_Role_roles_ID` FOREIGN KEY (`roles_ID`) REFERENCES `CRMP_Role` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crmp_user_group_crmp_role`
--

LOCK TABLES `crmp_user_group_crmp_role` WRITE;
/*!40000 ALTER TABLE `crmp_user_group_crmp_role` DISABLE KEYS */;
INSERT INTO `crmp_user_group_crmp_role` VALUES ('1','1','ADMINGroup');
/*!40000 ALTER TABLE `crmp_user_group_crmp_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crmp_zip_code`
--

DROP TABLE IF EXISTS `crmp_zip_code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crmp_zip_code` (
  `ID` varchar(64) NOT NULL,
  `name` varchar(100) NOT NULL,
  `CREATED_ON` datetime DEFAULT NULL,
  `UPDATED_ON` datetime DEFAULT NULL,
  `DELETED` tinyint(1) DEFAULT '0',
  `TENANT_ID` varchar(64) NOT NULL,
  `district_id` varchar(64) DEFAULT NULL,
  `CREATED_BY` varchar(64) NOT NULL,
  `UPDATED_BY` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_crmp_zip_code_district_id` (`district_id`),
  KEY `FK_crmp_zip_code_UPDATED_BY` (`UPDATED_BY`),
  KEY `FK_crmp_zip_code_TENANT_ID` (`TENANT_ID`),
  KEY `FK_crmp_zip_code_CREATED_BY` (`CREATED_BY`),
  CONSTRAINT `FK_crmp_zip_code_CREATED_BY` FOREIGN KEY (`CREATED_BY`) REFERENCES `CRMP_User` (`ID`),
  CONSTRAINT `FK_crmp_zip_code_district_id` FOREIGN KEY (`district_id`) REFERENCES `crmp_district` (`ID`),
  CONSTRAINT `FK_crmp_zip_code_TENANT_ID` FOREIGN KEY (`TENANT_ID`) REFERENCES `CRMP_Tenant` (`ID`),
  CONSTRAINT `FK_crmp_zip_code_UPDATED_BY` FOREIGN KEY (`UPDATED_BY`) REFERENCES `CRMP_User` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crmp_zip_code`
--

LOCK TABLES `crmp_zip_code` WRITE;
/*!40000 ALTER TABLE `crmp_zip_code` DISABLE KEYS */;
/*!40000 ALTER TABLE `crmp_zip_code` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2011-10-17 11:35:16
