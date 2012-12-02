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

DROP DATABASE crmp;
CREATE DATABASE crmp;
use crmp;
/*SET NAMES 'gbk';*/
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
  CONSTRAINT `FK_CRMP_Menu_Item_CREATED_BY` FOREIGN KEY (`CREATED_BY`) REFERENCES `CRMP_User` (`ID`),
  CONSTRAINT `FK_CRMP_Menu_Item_PARENT_ID` FOREIGN KEY (`PARENT_ID`) REFERENCES `CRMP_Menu_Item` (`ID`),
  CONSTRAINT `FK_CRMP_Menu_Item_TENANT_ID` FOREIGN KEY (`TENANT_ID`) REFERENCES `CRMP_Tenant` (`ID`),
  CONSTRAINT `FK_CRMP_Menu_Item_UPDATED_BY` FOREIGN KEY (`UPDATED_BY`) REFERENCES `CRMP_User` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CRMP_Menu_Item`
--

LOCK TABLES `CRMP_Menu_Item` WRITE;
/*!40000 ALTER TABLE `CRMP_Menu_Item` DISABLE KEYS */;
INSERT INTO `CRMP_Menu_Item` VALUES ('1','net.vicp.madz.framework.views.SystemSettings','2011-06-22 01:35:26','System Setting',NULL,'application',0,'1',NULL,'1',NULL),('2','','2011-06-22 01:35:26','Common Data',NULL,'',0,'1',NULL,'1',NULL),('3','net.vicp.madz.biz.common.views.CategoryView','2011-06-22 01:35:26','Category',NULL,'dataGrid',0,'1','2','1',NULL),('4','net.vicp.madz.biz.common.views.MerchandiseView','2011-06-22 01:35:26','Merchandise',NULL,'dataGrid',0,'1','2','1',NULL),('5','net.vicp.madz.biz.common.views.UnitOfMeasureView','2011-06-22 01:35:26','UnitOfMeasure',NULL,'dataGrid',0,'1','2','1',NULL);
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
INSERT INTO `CRMP_ROLE_MENU_ITEM` VALUES ('1','1'),('2','1'),('3','1'),('4','1'),('5','1');
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
-- Table structure for table `CRMP_Tenant`
--

DROP TABLE IF EXISTS `CRMP_Tenant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CRMP_Tenant` (
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
  `CREATED_BY` varchar(64) DEFAULT NULL,
  `PARENT_TENANT_ID` varchar(64) DEFAULT NULL,
  `UPDATED_BY` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_CRMP_Tenant_PARENT_TENANT_ID` (`PARENT_TENANT_ID`),
  KEY `FK_CRMP_Tenant_CREATED_BY` (`CREATED_BY`),
  KEY `FK_CRMP_Tenant_UPDATED_BY` (`UPDATED_BY`),
  CONSTRAINT `FK_CRMP_Tenant_CREATED_BY` FOREIGN KEY (`CREATED_BY`) REFERENCES `CRMP_User` (`ID`),
  CONSTRAINT `FK_CRMP_Tenant_PARENT_TENANT_ID` FOREIGN KEY (`PARENT_TENANT_ID`) REFERENCES `CRMP_Tenant` (`ID`),
  CONSTRAINT `FK_CRMP_Tenant_UPDATED_BY` FOREIGN KEY (`UPDATED_BY`) REFERENCES `CRMP_User` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CRMP_Tenant`
--

LOCK TABLES `CRMP_Tenant` WRITE;
/*!40000 ALTER TABLE `CRMP_Tenant` DISABLE KEYS */;
INSERT INTO `CRMP_Tenant` VALUES ('1',65535,65535,'Madz Technologies','9999-06-11 19:01:53','Barry',0,0,0,0,NULL,'2011-06-11 19:01:53',0,0,0,'2011-06-11 19:01:53','','1',NULL,NULL);
/*!40000 ALTER TABLE `CRMP_Tenant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CRMP_USER_GROUP`
--

DROP TABLE IF EXISTS `CRMP_USER_GROUP`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CRMP_USER_GROUP` (
  `ID` varchar(64) NOT NULL,
  `NAME` varchar(255) DEFAULT NULL,
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
  CONSTRAINT `FK_CRMP_USER_GROUP_CREATED_BY` FOREIGN KEY (`CREATED_BY`) REFERENCES `CRMP_User` (`ID`),
  CONSTRAINT `FK_CRMP_USER_GROUP_TENANT_ID` FOREIGN KEY (`TENANT_ID`) REFERENCES `CRMP_Tenant` (`ID`),
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
  `GROUP_NAME` varchar(255) NOT NULL,
  `USERNAME` varchar(255) NOT NULL,
  PRIMARY KEY (`GROUP_NAME`,`USERNAME`),
  KEY `FK_CRMP_USER_USER_GROUP_USERNAME` (`USERNAME`),
  CONSTRAINT `FK_CRMP_USER_USER_GROUP_GROUP_NAME` FOREIGN KEY (`GROUP_NAME`) REFERENCES `CRMP_USER_GROUP` (`NAME`),
  CONSTRAINT `FK_CRMP_USER_USER_GROUP_USERNAME` FOREIGN KEY (`USERNAME`) REFERENCES `CRMP_User` (`USERNAME`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CRMP_USER_USER_GROUP`
--

LOCK TABLES `CRMP_USER_USER_GROUP` WRITE;
/*!40000 ALTER TABLE `CRMP_USER_USER_GROUP` DISABLE KEYS */;
INSERT INTO `CRMP_USER_USER_GROUP` VALUES ('ADMINGroup','administrator'),('ADMINGroup','zhongdj@gmail.com');
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
  `USERNAME` varchar(255) DEFAULT NULL,
  `LOGINFAILEDTIMES` int(11) DEFAULT NULL,
  `UPDATED_ON` datetime DEFAULT NULL,
  `LOCKFLAG` tinyint(1) DEFAULT '0',
  `EMAIL` varchar(255) DEFAULT NULL,
  `TENANT_ID` varchar(64) NOT NULL,
  `CREATED_BY` varchar(64) NOT NULL,
  `UPDATED_BY` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `USERNAME` (`USERNAME`),
  KEY `FK_CRMP_User_CREATED_BY` (`CREATED_BY`),
  KEY `FK_CRMP_User_UPDATED_BY` (`UPDATED_BY`),
  KEY `FK_CRMP_User_TENANT_ID` (`TENANT_ID`),
  CONSTRAINT `FK_CRMP_User_CREATED_BY` FOREIGN KEY (`CREATED_BY`) REFERENCES `CRMP_User` (`ID`),
  CONSTRAINT `FK_CRMP_User_TENANT_ID` FOREIGN KEY (`TENANT_ID`) REFERENCES `CRMP_Tenant` (`ID`),
  CONSTRAINT `FK_CRMP_User_UPDATED_BY` FOREIGN KEY (`UPDATED_BY`) REFERENCES `CRMP_User` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CRMP_User`
--

LOCK TABLES `CRMP_User` WRITE;
/*!40000 ALTER TABLE `CRMP_User` DISABLE KEYS */;
INSERT INTO `CRMP_User` VALUES ('1',NULL,0,0,0,NULL,'4a6dfef787c0e3d8a5ccacb67e709060',NULL,'Barry Zhong',NULL,NULL,NULL,NULL,NULL,'zhongdj@gmail.com',NULL,NULL,0,'zhongdj@gmail.com','1','1',NULL),('2',0,0,0,0,'2011-07-02 17:13:48','4a6dfef787c0e3d8a5ccacb67e709060','2011-07-02 17:13:17','Barry Zhong','2011-06-22 01:33:27',4,'2011-06-22 01:33:27',NULL,'','administrator',0,NULL,0,'zhongdj@gmail.com','1','1',NULL);
/*!40000 ALTER TABLE `CRMP_User` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CRMP_User_CRMP_Role`
--

DROP TABLE IF EXISTS `CRMP_User_CRMP_Role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CRMP_User_CRMP_Role` (
  `roles_ID` varchar(64) NOT NULL,
  `users_ID` varchar(64) NOT NULL,
  PRIMARY KEY (`roles_ID`,`users_ID`),
  KEY `FK_CRMP_User_CRMP_Role_users_ID` (`users_ID`),
  CONSTRAINT `FK_CRMP_User_CRMP_Role_roles_ID` FOREIGN KEY (`roles_ID`) REFERENCES `CRMP_Role` (`ID`),
  CONSTRAINT `FK_CRMP_User_CRMP_Role_users_ID` FOREIGN KEY (`users_ID`) REFERENCES `CRMP_User` (`ID`)
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
INSERT INTO `ESSENTIAL_ID_GEN` VALUES ('OPLOG_ID',0),('PURCHASE_ORDER_ID',0),('PURCHASE_ORDER_ITEM_ID',0),('SUPPLIER_ID',0),('WAREHOUSE_ID',0),('WAREHOUSE_TYPE_ID',0);
/*!40000 ALTER TABLE `ESSENTIAL_ID_GEN` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PURCHASEORDER`
--

DROP TABLE IF EXISTS `PURCHASEORDER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PURCHASEORDER` (
  `ID` bigint(20) NOT NULL,
  `RECEIVINGNOTECODE` varchar(255) DEFAULT NULL,
  `COMMENT` varchar(255) DEFAULT NULL,
  `PURCHASERNAME` varchar(255) DEFAULT NULL,
  `TOTALAMOUNT` double DEFAULT NULL,
  `INVOICECODE` varchar(255) DEFAULT NULL,
  `ORDERCODE` varchar(255) DEFAULT NULL,
  `ORDERDATE` datetime DEFAULT NULL,
  `COMPANY_ID` varchar(64) DEFAULT NULL,
  `SUPPLIER_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_PURCHASEORDER_COMPANY_ID` (`COMPANY_ID`),
  KEY `FK_PURCHASEORDER_SUPPLIER_ID` (`SUPPLIER_ID`),
  CONSTRAINT `FK_PURCHASEORDER_COMPANY_ID` FOREIGN KEY (`COMPANY_ID`) REFERENCES `CRMP_Tenant` (`ID`),
  CONSTRAINT `FK_PURCHASEORDER_SUPPLIER_ID` FOREIGN KEY (`SUPPLIER_ID`) REFERENCES `SUPPLIER` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PURCHASEORDER`
--

LOCK TABLES `PURCHASEORDER` WRITE;
/*!40000 ALTER TABLE `PURCHASEORDER` DISABLE KEYS */;
/*!40000 ALTER TABLE `PURCHASEORDER` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PURCHASEORDERITEM`
--

DROP TABLE IF EXISTS `PURCHASEORDERITEM`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PURCHASEORDERITEM` (
  `ID` bigint(20) NOT NULL,
  `DISCOUNTAMOUNT` double DEFAULT NULL,
  `TAX` double DEFAULT NULL,
  `QUANTITY` double DEFAULT NULL,
  `AMOUNT` double DEFAULT NULL,
  `DISCOUNTRATE` double DEFAULT NULL,
  `UNITPRICE` double DEFAULT NULL,
  `MERCHANDISE_ID` varchar(64) DEFAULT NULL,
  `COMPANY_ID` varchar(64) DEFAULT NULL,
  `PURCHASEORDER_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_PURCHASEORDERITEM_PURCHASEORDER_ID` (`PURCHASEORDER_ID`),
  KEY `FK_PURCHASEORDERITEM_MERCHANDISE_ID` (`MERCHANDISE_ID`),
  KEY `FK_PURCHASEORDERITEM_COMPANY_ID` (`COMPANY_ID`),
  CONSTRAINT `FK_PURCHASEORDERITEM_COMPANY_ID` FOREIGN KEY (`COMPANY_ID`) REFERENCES `CRMP_Tenant` (`ID`),
  CONSTRAINT `FK_PURCHASEORDERITEM_MERCHANDISE_ID` FOREIGN KEY (`MERCHANDISE_ID`) REFERENCES `crmp_product` (`ID`),
  CONSTRAINT `FK_PURCHASEORDERITEM_PURCHASEORDER_ID` FOREIGN KEY (`PURCHASEORDER_ID`) REFERENCES `PURCHASEORDER` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PURCHASEORDERITEM`
--

LOCK TABLES `PURCHASEORDERITEM` WRITE;
/*!40000 ALTER TABLE `PURCHASEORDERITEM` DISABLE KEYS */;
/*!40000 ALTER TABLE `PURCHASEORDERITEM` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SUPPLIER`
--

DROP TABLE IF EXISTS `SUPPLIER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SUPPLIER` (
  `ID` bigint(20) NOT NULL,
  `TELEPHONE` varchar(255) DEFAULT NULL,
  `MOBILE` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `FAX` varchar(255) DEFAULT NULL,
  `COMMENT` varchar(255) DEFAULT NULL,
  `ADDRESS` varchar(255) DEFAULT NULL,
  `COMPANY_ID` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_SUPPLIER_COMPANY_ID` (`COMPANY_ID`),
  CONSTRAINT `FK_SUPPLIER_COMPANY_ID` FOREIGN KEY (`COMPANY_ID`) REFERENCES `CRMP_Tenant` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SUPPLIER`
--

LOCK TABLES `SUPPLIER` WRITE;
/*!40000 ALTER TABLE `SUPPLIER` DISABLE KEYS */;
/*!40000 ALTER TABLE `SUPPLIER` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `WAREHOUSE`
--

DROP TABLE IF EXISTS `WAREHOUSE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `WAREHOUSE` (
  `ID` bigint(20) NOT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `LOCATION` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) NOT NULL,
  `TYPE_ID` bigint(20) DEFAULT NULL,
  `COMPANY_ID` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_WAREHOUSE_TYPE_ID` (`TYPE_ID`),
  KEY `FK_WAREHOUSE_COMPANY_ID` (`COMPANY_ID`),
  CONSTRAINT `FK_WAREHOUSE_COMPANY_ID` FOREIGN KEY (`COMPANY_ID`) REFERENCES `CRMP_Tenant` (`ID`),
  CONSTRAINT `FK_WAREHOUSE_TYPE_ID` FOREIGN KEY (`TYPE_ID`) REFERENCES `WAREHOUSETYPE` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `WAREHOUSE`
--

LOCK TABLES `WAREHOUSE` WRITE;
/*!40000 ALTER TABLE `WAREHOUSE` DISABLE KEYS */;
/*!40000 ALTER TABLE `WAREHOUSE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `WAREHOUSETYPE`
--

DROP TABLE IF EXISTS `WAREHOUSETYPE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `WAREHOUSETYPE` (
  `ID` bigint(20) NOT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) NOT NULL,
  `COMPANY_ID` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_WAREHOUSETYPE_COMPANY_ID` (`COMPANY_ID`),
  CONSTRAINT `FK_WAREHOUSETYPE_COMPANY_ID` FOREIGN KEY (`COMPANY_ID`) REFERENCES `CRMP_Tenant` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `WAREHOUSETYPE`
--

LOCK TABLES `WAREHOUSETYPE` WRITE;
/*!40000 ALTER TABLE `WAREHOUSETYPE` DISABLE KEYS */;
/*!40000 ALTER TABLE `WAREHOUSETYPE` ENABLE KEYS */;
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
  CONSTRAINT `FK_crmp_address_CREATED_BY` FOREIGN KEY (`CREATED_BY`) REFERENCES `CRMP_User` (`ID`),
  CONSTRAINT `FK_crmp_address_STREET_ID` FOREIGN KEY (`STREET_ID`) REFERENCES `crmp_street` (`ID`),
  CONSTRAINT `FK_crmp_address_TENANT_ID` FOREIGN KEY (`TENANT_ID`) REFERENCES `CRMP_Tenant` (`ID`),
  CONSTRAINT `FK_crmp_address_UPDATED_BY` FOREIGN KEY (`UPDATED_BY`) REFERENCES `CRMP_User` (`ID`),
  CONSTRAINT `FK_crmp_address_zip_code_id` FOREIGN KEY (`zip_code_id`) REFERENCES `crmp_zip_code` (`ID`)
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
  `adjustment_id` varchar(64) NOT NULL,
  `TENANT_ID` varchar(64) NOT NULL,
  `CREATED_BY` varchar(64) NOT NULL,
  `invoice_id` varchar(64) NOT NULL,
  `UPDATED_BY` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_crmp_adjustment_invoice_invoice_id` (`invoice_id`),
  KEY `FK_crmp_adjustment_invoice_UPDATED_BY` (`UPDATED_BY`),
  KEY `FK_crmp_adjustment_invoice_CREATED_BY` (`CREATED_BY`),
  KEY `FK_crmp_adjustment_invoice_adjustment_id` (`adjustment_id`),
  KEY `FK_crmp_adjustment_invoice_TENANT_ID` (`TENANT_ID`),
  CONSTRAINT `FK_crmp_adjustment_invoice_adjustment_id` FOREIGN KEY (`adjustment_id`) REFERENCES `crmp_adjustment` (`ID`),
  CONSTRAINT `FK_crmp_adjustment_invoice_CREATED_BY` FOREIGN KEY (`CREATED_BY`) REFERENCES `CRMP_User` (`ID`),
  CONSTRAINT `FK_crmp_adjustment_invoice_invoice_id` FOREIGN KEY (`invoice_id`) REFERENCES `crmp_invoice` (`ID`),
  CONSTRAINT `FK_crmp_adjustment_invoice_TENANT_ID` FOREIGN KEY (`TENANT_ID`) REFERENCES `CRMP_Tenant` (`ID`),
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
  `contract_Id` varchar(64) NOT NULL,
  `CREATED_BY` varchar(64) NOT NULL,
  `TENANT_ID` varchar(64) NOT NULL,
  `UPDATED_BY` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_crmp_amendment_contract_Id` (`contract_Id`),
  KEY `FK_crmp_amendment_TENANT_ID` (`TENANT_ID`),
  KEY `FK_crmp_amendment_CREATED_BY` (`CREATED_BY`),
  KEY `FK_crmp_amendment_UPDATED_BY` (`UPDATED_BY`),
  CONSTRAINT `FK_crmp_amendment_contract_Id` FOREIGN KEY (`contract_Id`) REFERENCES `crmp_contract` (`ID`),
  CONSTRAINT `FK_crmp_amendment_CREATED_BY` FOREIGN KEY (`CREATED_BY`) REFERENCES `CRMP_User` (`ID`),
  CONSTRAINT `FK_crmp_amendment_TENANT_ID` FOREIGN KEY (`TENANT_ID`) REFERENCES `CRMP_Tenant` (`ID`),
  CONSTRAINT `FK_crmp_amendment_UPDATED_BY` FOREIGN KEY (`UPDATED_BY`) REFERENCES `CRMP_User` (`ID`)
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
  CONSTRAINT `FK_crmp_ar_payment_CREATED_BY` FOREIGN KEY (`CREATED_BY`) REFERENCES `CRMP_User` (`ID`),
  CONSTRAINT `FK_crmp_ar_payment_payer_account_id` FOREIGN KEY (`payer_account_id`) REFERENCES `crmp_account` (`ID`),
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
  `PAYMENTID_ID` varchar(64) DEFAULT NULL,
  `CREATED_BY` varchar(64) NOT NULL,
  `invoice_id` varchar(64) NOT NULL,
  `payment_method_id` varchar(64) NOT NULL,
  `TENANT_ID` varchar(64) NOT NULL,
  `UPDATED_BY` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_crmp_artransaction_payment_method_id` (`payment_method_id`),
  KEY `FK_crmp_artransaction_UPDATED_BY` (`UPDATED_BY`),
  KEY `FK_crmp_artransaction_invoice_id` (`invoice_id`),
  KEY `FK_crmp_artransaction_CREATED_BY` (`CREATED_BY`),
  KEY `FK_crmp_artransaction_PAYMENTID_ID` (`PAYMENTID_ID`),
  KEY `FK_crmp_artransaction_TENANT_ID` (`TENANT_ID`),
  CONSTRAINT `FK_crmp_artransaction_CREATED_BY` FOREIGN KEY (`CREATED_BY`) REFERENCES `CRMP_User` (`ID`),
  CONSTRAINT `FK_crmp_artransaction_invoice_id` FOREIGN KEY (`invoice_id`) REFERENCES `crmp_invoice` (`ID`),
  CONSTRAINT `FK_crmp_artransaction_PAYMENTID_ID` FOREIGN KEY (`PAYMENTID_ID`) REFERENCES `crmp_ar_payment` (`ID`),
  CONSTRAINT `FK_crmp_artransaction_payment_method_id` FOREIGN KEY (`payment_method_id`) REFERENCES `crmp_payment_method` (`ID`),
  CONSTRAINT `FK_crmp_artransaction_TENANT_ID` FOREIGN KEY (`TENANT_ID`) REFERENCES `CRMP_Tenant` (`ID`),
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
-- Table structure for table `crmp_category`
--

DROP TABLE IF EXISTS `crmp_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crmp_category` (
  `ID` varchar(64) NOT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `CREATED_ON` datetime DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `UPDATED_ON` datetime DEFAULT NULL,
  `DELETED` tinyint(1) DEFAULT '0',
  `CREATED_BY` varchar(64) NOT NULL,
  `PARENT_ID` varchar(64) DEFAULT NULL,
  `TENANT_ID` varchar(64) NOT NULL,
  `UPDATED_BY` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_crmp_category_TENANT_ID` (`TENANT_ID`),
  KEY `FK_crmp_category_PARENT_ID` (`PARENT_ID`),
  KEY `FK_crmp_category_CREATED_BY` (`CREATED_BY`),
  KEY `FK_crmp_category_UPDATED_BY` (`UPDATED_BY`),
  CONSTRAINT `FK_crmp_category_CREATED_BY` FOREIGN KEY (`CREATED_BY`) REFERENCES `CRMP_User` (`ID`),
  CONSTRAINT `FK_crmp_category_PARENT_ID` FOREIGN KEY (`PARENT_ID`) REFERENCES `crmp_category` (`ID`),
  CONSTRAINT `FK_crmp_category_TENANT_ID` FOREIGN KEY (`TENANT_ID`) REFERENCES `CRMP_Tenant` (`ID`),
  CONSTRAINT `FK_crmp_category_UPDATED_BY` FOREIGN KEY (`UPDATED_BY`) REFERENCES `CRMP_User` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crmp_category`
--

LOCK TABLES `crmp_category` WRITE;
/*!40000 ALTER TABLE `crmp_category` DISABLE KEYS */;
INSERT INTO `crmp_category` VALUES ('81aabc6d-af4e-4bd3-a369-ff57870f049c','如C10,C15','2011-06-22 01:37:59','产品规格','2011-07-02 17:30:00',0,'2',NULL,'1','2'),('fdb10b7f-d3d7-4be2-b51a-52a3adeb2e46','如缓凝','2011-06-22 01:39:07','特殊技术要求','2011-07-02 17:30:00',0,'2',NULL,'1','2');
/*!40000 ALTER TABLE `crmp_category` ENABLE KEYS */;
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
  CONSTRAINT `FK_crmp_city_CREATED_BY` FOREIGN KEY (`CREATED_BY`) REFERENCES `CRMP_User` (`ID`),
  CONSTRAINT `FK_crmp_city_province_id` FOREIGN KEY (`province_id`) REFERENCES `crmp_province` (`ID`),
  CONSTRAINT `FK_crmp_city_TENANT_ID` FOREIGN KEY (`TENANT_ID`) REFERENCES `CRMP_Tenant` (`ID`),
  CONSTRAINT `FK_crmp_city_UPDATED_BY` FOREIGN KEY (`UPDATED_BY`) REFERENCES `CRMP_User` (`ID`)
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
  `operator_id` varchar(64) NOT NULL,
  `CREATED_BY` varchar(64) NOT NULL,
  `TENANT_ID` varchar(64) NOT NULL,
  `UPDATED_BY` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_crmp_concrete_mixing_plant_TENANT_ID` (`TENANT_ID`),
  KEY `FK_crmp_concrete_mixing_plant_UPDATED_BY` (`UPDATED_BY`),
  KEY `FK_crmp_concrete_mixing_plant_CREATED_BY` (`CREATED_BY`),
  KEY `FK_crmp_concrete_mixing_plant_operator_id` (`operator_id`),
  CONSTRAINT `FK_crmp_concrete_mixing_plant_CREATED_BY` FOREIGN KEY (`CREATED_BY`) REFERENCES `CRMP_User` (`ID`),
  CONSTRAINT `FK_crmp_concrete_mixing_plant_operator_id` FOREIGN KEY (`operator_id`) REFERENCES `CRMP_User` (`ID`),
  CONSTRAINT `FK_crmp_concrete_mixing_plant_TENANT_ID` FOREIGN KEY (`TENANT_ID`) REFERENCES `CRMP_Tenant` (`ID`),
  CONSTRAINT `FK_crmp_concrete_mixing_plant_UPDATED_BY` FOREIGN KEY (`UPDATED_BY`) REFERENCES `CRMP_User` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crmp_concrete_mixing_plant`
--

LOCK TABLES `crmp_concrete_mixing_plant` WRITE;
/*!40000 ALTER TABLE `crmp_concrete_mixing_plant` DISABLE KEYS */;
/*!40000 ALTER TABLE `crmp_concrete_mixing_plant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crmp_contact`
--

DROP TABLE IF EXISTS `crmp_contact`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crmp_contact` (
  `ID` varchar(64) NOT NULL,
  `mobile` varchar(20) DEFAULT NULL,
  `fax` varchar(20) DEFAULT NULL,
  `DELETED` tinyint(1) DEFAULT '0',
  `sex` tinyint(1) DEFAULT '0',
  `contact_type` varchar(10) DEFAULT NULL,
  `email` varchar(60) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `UPDATED_ON` datetime DEFAULT NULL,
  `CREATED_ON` datetime DEFAULT NULL,
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
  `name` varchar(50) NOT NULL,
  `state` varchar(20) NOT NULL,
  `DELETED` tinyint(1) DEFAULT '0',
  `effective_start_date` datetime DEFAULT NULL,
  `effective_end_date` datetime DEFAULT NULL,
  `UPDATED_ON` datetime DEFAULT NULL,
  `billMethod` varchar(20) NOT NULL,
  `CREATED_ON` datetime DEFAULT NULL,
  `active` tinyint(1) NOT NULL DEFAULT '0',
  `CREATED_BY` varchar(64) NOT NULL,
  `PROJECT_ID` varchar(64) DEFAULT NULL,
  `sold_to_account_id` varchar(64) NOT NULL,
  `TENANT_ID` varchar(64) NOT NULL,
  `payer_account_id` varchar(64) NOT NULL,
  `ship_to_account_id` varchar(64) NOT NULL,
  `bill_to_account_id` varchar(64) NOT NULL,
  `original_contract_id` varchar(64) DEFAULT NULL,
  `UPDATED_BY` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_crmp_contract_sold_to_account_id` (`sold_to_account_id`),
  KEY `FK_crmp_contract_CREATED_BY` (`CREATED_BY`),
  KEY `FK_crmp_contract_bill_to_account_id` (`bill_to_account_id`),
  KEY `FK_crmp_contract_payer_account_id` (`payer_account_id`),
  KEY `FK_crmp_contract_original_contract_id` (`original_contract_id`),
  KEY `FK_crmp_contract_PROJECT_ID` (`PROJECT_ID`),
  KEY `FK_crmp_contract_ship_to_account_id` (`ship_to_account_id`),
  KEY `FK_crmp_contract_UPDATED_BY` (`UPDATED_BY`),
  KEY `FK_crmp_contract_TENANT_ID` (`TENANT_ID`),
  CONSTRAINT `FK_crmp_contract_bill_to_account_id` FOREIGN KEY (`bill_to_account_id`) REFERENCES `crmp_account` (`ID`),
  CONSTRAINT `FK_crmp_contract_CREATED_BY` FOREIGN KEY (`CREATED_BY`) REFERENCES `CRMP_User` (`ID`),
  CONSTRAINT `FK_crmp_contract_original_contract_id` FOREIGN KEY (`original_contract_id`) REFERENCES `crmp_contract` (`ID`),
  CONSTRAINT `FK_crmp_contract_payer_account_id` FOREIGN KEY (`payer_account_id`) REFERENCES `crmp_account` (`ID`),
  CONSTRAINT `FK_crmp_contract_PROJECT_ID` FOREIGN KEY (`PROJECT_ID`) REFERENCES `crmp_project` (`ID`),
  CONSTRAINT `FK_crmp_contract_ship_to_account_id` FOREIGN KEY (`ship_to_account_id`) REFERENCES `crmp_account` (`ID`),
  CONSTRAINT `FK_crmp_contract_sold_to_account_id` FOREIGN KEY (`sold_to_account_id`) REFERENCES `crmp_account` (`ID`),
  CONSTRAINT `FK_crmp_contract_TENANT_ID` FOREIGN KEY (`TENANT_ID`) REFERENCES `CRMP_Tenant` (`ID`),
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
  `charge_rate` double DEFAULT NULL,
  `active` tinyint(1) NOT NULL DEFAULT '0',
  `DELETED` tinyint(1) DEFAULT '0',
  `charge_model` varchar(20) NOT NULL,
  `CREATED_ON` datetime DEFAULT NULL,
  `charge_type` varchar(20) NOT NULL,
  `UPDATED_ON` datetime DEFAULT NULL,
  `contract_id` varchar(64) NOT NULL,
  `CREATED_BY` varchar(64) NOT NULL,
  `unit_of_measure_id` varchar(64) NOT NULL,
  `TENANT_ID` varchar(64) NOT NULL,
  `UPDATED_BY` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_crmp_contract_rate_plan_component_CREATED_BY` (`CREATED_BY`),
  KEY `crmpcontract_rate_plan_componentunit_of_measure_id` (`unit_of_measure_id`),
  KEY `FK_crmp_contract_rate_plan_component_TENANT_ID` (`TENANT_ID`),
  KEY `FK_crmp_contract_rate_plan_component_UPDATED_BY` (`UPDATED_BY`),
  KEY `FK_crmp_contract_rate_plan_component_contract_id` (`contract_id`),
  CONSTRAINT `crmpcontract_rate_plan_componentunit_of_measure_id` FOREIGN KEY (`unit_of_measure_id`) REFERENCES `crmp_unit_of_measure` (`ID`),
  CONSTRAINT `FK_crmp_contract_rate_plan_component_contract_id` FOREIGN KEY (`contract_id`) REFERENCES `crmp_contract` (`ID`),
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
  CONSTRAINT `FK_crmp_district_city_id` FOREIGN KEY (`city_id`) REFERENCES `crmp_city` (`ID`),
  CONSTRAINT `FK_crmp_district_CREATED_BY` FOREIGN KEY (`CREATED_BY`) REFERENCES `CRMP_User` (`ID`),
  CONSTRAINT `FK_crmp_district_TENANT_ID` FOREIGN KEY (`TENANT_ID`) REFERENCES `CRMP_Tenant` (`ID`),
  CONSTRAINT `FK_crmp_district_UPDATED_BY` FOREIGN KEY (`UPDATED_BY`) REFERENCES `CRMP_User` (`ID`)
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
  `invoice_number` varchar(12) NOT NULL,
  `DELETED` tinyint(1) DEFAULT '0',
  `reference_number` varchar(60) DEFAULT NULL,
  `CREATED_ON` datetime DEFAULT NULL,
  `state` varchar(20) NOT NULL,
  `UPDATED_ON` datetime DEFAULT NULL,
  `dueDate` datetime DEFAULT NULL,
  `ship_to_account_id` varchar(64) NOT NULL,
  `sold_to_account_id` varchar(64) NOT NULL,
  `CREATED_BY` varchar(64) NOT NULL,
  `payer_account_id` varchar(64) NOT NULL,
  `bill_to_account_id` varchar(64) NOT NULL,
  `production_record_id` varchar(64) NOT NULL,
  `TENANT_ID` varchar(64) NOT NULL,
  `UPDATED_BY` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_crmp_invoice_production_record_id` (`production_record_id`),
  KEY `FK_crmp_invoice_ship_to_account_id` (`ship_to_account_id`),
  KEY `FK_crmp_invoice_bill_to_account_id` (`bill_to_account_id`),
  KEY `FK_crmp_invoice_sold_to_account_id` (`sold_to_account_id`),
  KEY `FK_crmp_invoice_TENANT_ID` (`TENANT_ID`),
  KEY `FK_crmp_invoice_CREATED_BY` (`CREATED_BY`),
  KEY `FK_crmp_invoice_payer_account_id` (`payer_account_id`),
  KEY `FK_crmp_invoice_UPDATED_BY` (`UPDATED_BY`),
  CONSTRAINT `FK_crmp_invoice_bill_to_account_id` FOREIGN KEY (`bill_to_account_id`) REFERENCES `crmp_account` (`ID`),
  CONSTRAINT `FK_crmp_invoice_CREATED_BY` FOREIGN KEY (`CREATED_BY`) REFERENCES `CRMP_User` (`ID`),
  CONSTRAINT `FK_crmp_invoice_payer_account_id` FOREIGN KEY (`payer_account_id`) REFERENCES `crmp_account` (`ID`),
  CONSTRAINT `FK_crmp_invoice_production_record_id` FOREIGN KEY (`production_record_id`) REFERENCES `crmp_production_record` (`ID`),
  CONSTRAINT `FK_crmp_invoice_ship_to_account_id` FOREIGN KEY (`ship_to_account_id`) REFERENCES `crmp_account` (`ID`),
  CONSTRAINT `FK_crmp_invoice_sold_to_account_id` FOREIGN KEY (`sold_to_account_id`) REFERENCES `crmp_account` (`ID`),
  CONSTRAINT `FK_crmp_invoice_TENANT_ID` FOREIGN KEY (`TENANT_ID`) REFERENCES `CRMP_Tenant` (`ID`),
  CONSTRAINT `FK_crmp_invoice_UPDATED_BY` FOREIGN KEY (`UPDATED_BY`) REFERENCES `CRMP_User` (`ID`)
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
  `quantity` int(11) NOT NULL,
  `amount` double NOT NULL,
  `DELETED` tinyint(1) DEFAULT '0',
  `state` varchar(20) NOT NULL,
  `CREATED_ON` datetime DEFAULT NULL,
  `UPDATED_ON` datetime DEFAULT NULL,
  `unit_of_measure_id` varchar(64) NOT NULL,
  `CREATED_BY` varchar(64) NOT NULL,
  `invoice_id` varchar(64) NOT NULL,
  `TENANT_ID` varchar(64) NOT NULL,
  `UPDATED_BY` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_crmp_invoice_item_UPDATED_BY` (`UPDATED_BY`),
  KEY `FK_crmp_invoice_item_CREATED_BY` (`CREATED_BY`),
  KEY `FK_crmp_invoice_item_TENANT_ID` (`TENANT_ID`),
  KEY `FK_crmp_invoice_item_unit_of_measure_id` (`unit_of_measure_id`),
  KEY `FK_crmp_invoice_item_invoice_id` (`invoice_id`),
  CONSTRAINT `FK_crmp_invoice_item_CREATED_BY` FOREIGN KEY (`CREATED_BY`) REFERENCES `CRMP_User` (`ID`),
  CONSTRAINT `FK_crmp_invoice_item_invoice_id` FOREIGN KEY (`invoice_id`) REFERENCES `crmp_invoice` (`ID`),
  CONSTRAINT `FK_crmp_invoice_item_TENANT_ID` FOREIGN KEY (`TENANT_ID`) REFERENCES `CRMP_Tenant` (`ID`),
  CONSTRAINT `FK_crmp_invoice_item_unit_of_measure_id` FOREIGN KEY (`unit_of_measure_id`) REFERENCES `crmp_unit_of_measure` (`ID`),
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
  `COLUMNTYPENAME` varchar(255) DEFAULT NULL,
  `COLUMNDISPLAYSIZE` int(11) DEFAULT NULL,
  `DELETED` tinyint(1) DEFAULT '0',
  `COLUMNPRECISION` int(11) DEFAULT NULL,
  `MINVERSION` int(11) DEFAULT NULL,
  `COLUMNLABEL` varchar(255) DEFAULT NULL,
  `MAXVERSION` int(11) DEFAULT NULL,
  `CREATED_ON` datetime DEFAULT NULL,
  `COLUMNTYPE` varchar(255) DEFAULT NULL,
  `COLUMNNAME` varchar(255) DEFAULT NULL,
  `UPDATED_ON` datetime DEFAULT NULL,
  `TABLE_ID` varchar(64) DEFAULT NULL,
  `CREATED_BY` varchar(64) NOT NULL,
  `TENANT_ID` varchar(64) NOT NULL,
  `UPDATED_BY` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_crmp_meta_column_descriptor_UPDATED_BY` (`UPDATED_BY`),
  KEY `FK_crmp_meta_column_descriptor_TENANT_ID` (`TENANT_ID`),
  KEY `FK_crmp_meta_column_descriptor_CREATED_BY` (`CREATED_BY`),
  KEY `FK_crmp_meta_column_descriptor_TABLE_ID` (`TABLE_ID`),
  CONSTRAINT `FK_crmp_meta_column_descriptor_CREATED_BY` FOREIGN KEY (`CREATED_BY`) REFERENCES `CRMP_User` (`ID`),
  CONSTRAINT `FK_crmp_meta_column_descriptor_TABLE_ID` FOREIGN KEY (`TABLE_ID`) REFERENCES `crmp_meta_table_descriptor` (`ID`),
  CONSTRAINT `FK_crmp_meta_column_descriptor_TENANT_ID` FOREIGN KEY (`TENANT_ID`) REFERENCES `CRMP_Tenant` (`ID`),
  CONSTRAINT `FK_crmp_meta_column_descriptor_UPDATED_BY` FOREIGN KEY (`UPDATED_BY`) REFERENCES `CRMP_User` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crmp_meta_column_descriptor`
--

LOCK TABLES `crmp_meta_column_descriptor` WRITE;
/*!40000 ALTER TABLE `crmp_meta_column_descriptor` DISABLE KEYS */;
/*!40000 ALTER TABLE `crmp_meta_column_descriptor` ENABLE KEYS */;
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
  `CREATED_ON` datetime DEFAULT NULL,
  `UPDATED_ON` datetime DEFAULT NULL,
  `DELETED` tinyint(1) DEFAULT '0',
  `CREATED_BY` varchar(64) NOT NULL,
  `TENANT_ID` varchar(64) NOT NULL,
  `UPDATED_BY` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_crmp_meta_db_descriptor_CREATED_BY` (`CREATED_BY`),
  KEY `FK_crmp_meta_db_descriptor_TENANT_ID` (`TENANT_ID`),
  KEY `FK_crmp_meta_db_descriptor_UPDATED_BY` (`UPDATED_BY`),
  CONSTRAINT `FK_crmp_meta_db_descriptor_CREATED_BY` FOREIGN KEY (`CREATED_BY`) REFERENCES `CRMP_User` (`ID`),
  CONSTRAINT `FK_crmp_meta_db_descriptor_TENANT_ID` FOREIGN KEY (`TENANT_ID`) REFERENCES `CRMP_Tenant` (`ID`),
  CONSTRAINT `FK_crmp_meta_db_descriptor_UPDATED_BY` FOREIGN KEY (`UPDATED_BY`) REFERENCES `CRMP_User` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crmp_meta_db_descriptor`
--

LOCK TABLES `crmp_meta_db_descriptor` WRITE;
/*!40000 ALTER TABLE `crmp_meta_db_descriptor` DISABLE KEYS */;
/*!40000 ALTER TABLE `crmp_meta_db_descriptor` ENABLE KEYS */;
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
  `TENANT_ID` varchar(64) NOT NULL,
  `DATABASE_ID` varchar(64) DEFAULT NULL,
  `CREATED_BY` varchar(64) NOT NULL,
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
/*!40000 ALTER TABLE `crmp_meta_table_descriptor` ENABLE KEYS */;
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
  `payment_id` varchar(64) NOT NULL,
  `CREATED_BY` varchar(64) NOT NULL,
  `invoice_id` varchar(64) NOT NULL,
  `TENANT_ID` varchar(64) NOT NULL,
  `UPDATED_BY` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_crmp_payment_invoice_TENANT_ID` (`TENANT_ID`),
  KEY `FK_crmp_payment_invoice_UPDATED_BY` (`UPDATED_BY`),
  KEY `FK_crmp_payment_invoice_CREATED_BY` (`CREATED_BY`),
  KEY `FK_crmp_payment_invoice_invoice_id` (`invoice_id`),
  KEY `FK_crmp_payment_invoice_payment_id` (`payment_id`),
  CONSTRAINT `FK_crmp_payment_invoice_CREATED_BY` FOREIGN KEY (`CREATED_BY`) REFERENCES `CRMP_User` (`ID`),
  CONSTRAINT `FK_crmp_payment_invoice_invoice_id` FOREIGN KEY (`invoice_id`) REFERENCES `crmp_invoice` (`ID`),
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
  `CODE` varchar(255) NOT NULL,
  `DELETED` tinyint(1) DEFAULT '0',
  `NAME` varchar(255) NOT NULL,
  `CATEGORY_ID` varchar(64) DEFAULT NULL,
  `CREATED_BY` varchar(64) NOT NULL,
  `UOM_ID` varchar(64) DEFAULT NULL,
  `TENANT_ID` varchar(64) NOT NULL,
  `UPDATED_BY` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_crmp_product_CATEGORY_ID` (`CATEGORY_ID`),
  KEY `FK_crmp_product_UPDATED_BY` (`UPDATED_BY`),
  KEY `FK_crmp_product_CREATED_BY` (`CREATED_BY`),
  KEY `FK_crmp_product_TENANT_ID` (`TENANT_ID`),
  KEY `FK_crmp_product_UOM_ID` (`UOM_ID`),
  CONSTRAINT `FK_crmp_product_CATEGORY_ID` FOREIGN KEY (`CATEGORY_ID`) REFERENCES `crmp_category` (`ID`),
  CONSTRAINT `FK_crmp_product_CREATED_BY` FOREIGN KEY (`CREATED_BY`) REFERENCES `CRMP_User` (`ID`),
  CONSTRAINT `FK_crmp_product_TENANT_ID` FOREIGN KEY (`TENANT_ID`) REFERENCES `CRMP_Tenant` (`ID`),
  CONSTRAINT `FK_crmp_product_UOM_ID` FOREIGN KEY (`UOM_ID`) REFERENCES `crmp_unit_of_measure` (`ID`),
  CONSTRAINT `FK_crmp_product_UPDATED_BY` FOREIGN KEY (`UPDATED_BY`) REFERENCES `CRMP_User` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crmp_product`
--

LOCK TABLES `crmp_product` WRITE;
/*!40000 ALTER TABLE `crmp_product` DISABLE KEYS */;
INSERT INTO `crmp_product` VALUES ('5de357f3-5601-4bd2-86b8-097b1ac5f1c6',100,'2011-06-22 01:44:46','','2011-07-02 17:31:04','缓凝',0,'缓凝','fdb10b7f-d3d7-4be2-b51a-52a3adeb2e46','2','87abb1f6-aa72-4f62-a220-00f2e4306cbf','1','2'),('9f5d6bbe-06e3-4bcc-aca7-2adf4a5fec01',300,'2011-06-22 01:41:07','适用于...','2011-07-02 17:31:22','C15',0,'C15','81aabc6d-af4e-4bd3-a369-ff57870f049c','2','87abb1f6-aa72-4f62-a220-00f2e4306cbf','1','2'),('ab5b63d2-ee71-4574-bf36-69b010135c04',30,'2011-06-22 01:43:42','','2011-07-02 17:31:04','抗冻',0,'抗冻','fdb10b7f-d3d7-4be2-b51a-52a3adeb2e46','2','87abb1f6-aa72-4f62-a220-00f2e4306cbf','1','2'),('b55d4ef0-5280-4ff4-96c4-4263ea3ef636',200,'2011-06-22 01:40:08','适用于...','2011-07-02 17:31:04','C10',0,'C10','81aabc6d-af4e-4bd3-a369-ff57870f049c','2','87abb1f6-aa72-4f62-a220-00f2e4306cbf','1','2'),('f09aa38f-9eb2-4a8e-86ea-ecbc8c7f76a0',400,'2011-06-22 01:41:30','适用于...','2011-07-02 17:31:22','C20',0,'C20','81aabc6d-af4e-4bd3-a369-ff57870f049c','2','87abb1f6-aa72-4f62-a220-00f2e4306cbf','1','2');
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
  `billed` tinyint(1) NOT NULL DEFAULT '0',
  `referenceNumber` varchar(32) DEFAULT NULL,
  `DELETED` tinyint(1) DEFAULT '0',
  `CREATED_ON` datetime DEFAULT NULL,
  `UPDATED_ON` datetime DEFAULT NULL,
  `mixing_plant_id` varchar(64) DEFAULT NULL,
  `CREATED_BY` varchar(64) NOT NULL,
  `TENANT_ID` varchar(64) NOT NULL,
  `UPDATED_BY` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_crmp_production_record_TENANT_ID` (`TENANT_ID`),
  KEY `FK_crmp_production_record_mixing_plant_id` (`mixing_plant_id`),
  KEY `FK_crmp_production_record_CREATED_BY` (`CREATED_BY`),
  KEY `FK_crmp_production_record_UPDATED_BY` (`UPDATED_BY`),
  CONSTRAINT `FK_crmp_production_record_CREATED_BY` FOREIGN KEY (`CREATED_BY`) REFERENCES `CRMP_User` (`ID`),
  CONSTRAINT `FK_crmp_production_record_mixing_plant_id` FOREIGN KEY (`mixing_plant_id`) REFERENCES `crmp_concrete_mixing_plant` (`ID`),
  CONSTRAINT `FK_crmp_production_record_TENANT_ID` FOREIGN KEY (`TENANT_ID`) REFERENCES `CRMP_Tenant` (`ID`),
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
  `name` varchar(60) DEFAULT NULL,
  `CREATED_BY` varchar(64) NOT NULL,
  `sold_to_contact_id` varchar(64) DEFAULT NULL,
  `TENANT_ID` varchar(64) NOT NULL,
  `payer_contact_id` varchar(64) DEFAULT NULL,
  `ship_to_contact_id` varchar(64) DEFAULT NULL,
  `bill_to_contact_id` varchar(64) DEFAULT NULL,
  `address_id` varchar(64) DEFAULT NULL,
  `UPDATED_BY` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_crmp_project_UPDATED_BY` (`UPDATED_BY`),
  KEY `FK_crmp_project_payer_contact_id` (`payer_contact_id`),
  KEY `FK_crmp_project_sold_to_contact_id` (`sold_to_contact_id`),
  KEY `FK_crmp_project_CREATED_BY` (`CREATED_BY`),
  KEY `FK_crmp_project_ship_to_contact_id` (`ship_to_contact_id`),
  KEY `FK_crmp_project_address_id` (`address_id`),
  KEY `FK_crmp_project_TENANT_ID` (`TENANT_ID`),
  KEY `FK_crmp_project_bill_to_contact_id` (`bill_to_contact_id`),
  CONSTRAINT `FK_crmp_project_address_id` FOREIGN KEY (`address_id`) REFERENCES `crmp_address` (`ID`),
  CONSTRAINT `FK_crmp_project_bill_to_contact_id` FOREIGN KEY (`bill_to_contact_id`) REFERENCES `crmp_contact` (`ID`),
  CONSTRAINT `FK_crmp_project_CREATED_BY` FOREIGN KEY (`CREATED_BY`) REFERENCES `CRMP_User` (`ID`),
  CONSTRAINT `FK_crmp_project_payer_contact_id` FOREIGN KEY (`payer_contact_id`) REFERENCES `crmp_contact` (`ID`),
  CONSTRAINT `FK_crmp_project_ship_to_contact_id` FOREIGN KEY (`ship_to_contact_id`) REFERENCES `crmp_contact` (`ID`),
  CONSTRAINT `FK_crmp_project_sold_to_contact_id` FOREIGN KEY (`sold_to_contact_id`) REFERENCES `crmp_contact` (`ID`),
  CONSTRAINT `FK_crmp_project_TENANT_ID` FOREIGN KEY (`TENANT_ID`) REFERENCES `CRMP_Tenant` (`ID`),
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
  KEY `FK_crmp_refund_payment_invoice_refund_id` (`refund_id`),
  KEY `FK_crmp_refund_payment_invoice_payment_invoice_id` (`payment_invoice_id`),
  KEY `FK_crmp_refund_payment_invoice_TENANT_ID` (`TENANT_ID`),
  KEY `FK_crmp_refund_payment_invoice_UPDATED_BY` (`UPDATED_BY`),
  CONSTRAINT `FK_crmp_refund_payment_invoice_CREATED_BY` FOREIGN KEY (`CREATED_BY`) REFERENCES `CRMP_User` (`ID`),
  CONSTRAINT `FK_crmp_refund_payment_invoice_payment_invoice_id` FOREIGN KEY (`payment_invoice_id`) REFERENCES `crmp_payment_invoice` (`ID`),
  CONSTRAINT `FK_crmp_refund_payment_invoice_refund_id` FOREIGN KEY (`refund_id`) REFERENCES `crmp_refund` (`ID`),
  CONSTRAINT `FK_crmp_refund_payment_invoice_TENANT_ID` FOREIGN KEY (`TENANT_ID`) REFERENCES `CRMP_Tenant` (`ID`),
  CONSTRAINT `FK_crmp_refund_payment_invoice_UPDATED_BY` FOREIGN KEY (`UPDATED_BY`) REFERENCES `CRMP_User` (`ID`)
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
  CONSTRAINT `FK_crmp_street_CREATED_BY` FOREIGN KEY (`CREATED_BY`) REFERENCES `CRMP_User` (`ID`),
  CONSTRAINT `FK_crmp_street_district_id` FOREIGN KEY (`district_id`) REFERENCES `crmp_district` (`ID`),
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
  `NAME` varchar(10) DEFAULT NULL,
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
INSERT INTO `crmp_unit_of_measure` VALUES ('87abb1f6-aa72-4f62-a220-00f2e4306cbf','方','2011-06-22 01:39:35','立方米','2011-07-02 17:30:14',0,'1','2','2');
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
  `UPDATED_ON` datetime DEFAULT NULL,
  `CREATED_ON` datetime DEFAULT NULL,
  `DELETED` tinyint(1) DEFAULT '0',
  `name` varchar(60) DEFAULT NULL,
  `TENANT_ID` varchar(64) NOT NULL,
  `sold_to_contact_id` varchar(64) DEFAULT NULL,
  `PROJECT_ID` varchar(64) DEFAULT NULL,
  `payer_contact_id` varchar(64) DEFAULT NULL,
  `ship_to_contact_id` varchar(64) DEFAULT NULL,
  `bill_to_contact_id` varchar(64) DEFAULT NULL,
  `CREATED_BY` varchar(64) NOT NULL,
  `UPDATED_BY` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_crmp_unit_of_project_CREATED_BY` (`CREATED_BY`),
  KEY `FK_crmp_unit_of_project_PROJECT_ID` (`PROJECT_ID`),
  KEY `FK_crmp_unit_of_project_UPDATED_BY` (`UPDATED_BY`),
  KEY `FK_crmp_unit_of_project_bill_to_contact_id` (`bill_to_contact_id`),
  KEY `FK_crmp_unit_of_project_sold_to_contact_id` (`sold_to_contact_id`),
  KEY `FK_crmp_unit_of_project_ship_to_contact_id` (`ship_to_contact_id`),
  KEY `FK_crmp_unit_of_project_TENANT_ID` (`TENANT_ID`),
  KEY `FK_crmp_unit_of_project_payer_contact_id` (`payer_contact_id`),
  CONSTRAINT `FK_crmp_unit_of_project_bill_to_contact_id` FOREIGN KEY (`bill_to_contact_id`) REFERENCES `crmp_contact` (`ID`),
  CONSTRAINT `FK_crmp_unit_of_project_CREATED_BY` FOREIGN KEY (`CREATED_BY`) REFERENCES `CRMP_User` (`ID`),
  CONSTRAINT `FK_crmp_unit_of_project_payer_contact_id` FOREIGN KEY (`payer_contact_id`) REFERENCES `crmp_contact` (`ID`),
  CONSTRAINT `FK_crmp_unit_of_project_PROJECT_ID` FOREIGN KEY (`PROJECT_ID`) REFERENCES `crmp_project` (`ID`),
  CONSTRAINT `FK_crmp_unit_of_project_ship_to_contact_id` FOREIGN KEY (`ship_to_contact_id`) REFERENCES `crmp_contact` (`ID`),
  CONSTRAINT `FK_crmp_unit_of_project_sold_to_contact_id` FOREIGN KEY (`sold_to_contact_id`) REFERENCES `crmp_contact` (`ID`),
  CONSTRAINT `FK_crmp_unit_of_project_TENANT_ID` FOREIGN KEY (`TENANT_ID`) REFERENCES `CRMP_Tenant` (`ID`),
  CONSTRAINT `FK_crmp_unit_of_project_UPDATED_BY` FOREIGN KEY (`UPDATED_BY`) REFERENCES `CRMP_User` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crmp_unit_of_project`
--

LOCK TABLES `crmp_unit_of_project` WRITE;
/*!40000 ALTER TABLE `crmp_unit_of_project` DISABLE KEYS */;
/*!40000 ALTER TABLE `crmp_unit_of_project` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crmp_user_group_crmp_role`
--

DROP TABLE IF EXISTS `crmp_user_group_crmp_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crmp_user_group_crmp_role` (
  `roles_ID` varchar(64) NOT NULL,
  `groups_ID` varchar(64) NOT NULL,
  `GROUP_NAME` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`roles_ID`,`groups_ID`),
  KEY `FK_CRMP_USER_GROUP_CRMP_Role_groups_ID` (`groups_ID`),
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

-- Dump completed on 2011-07-02 17:34:17
