-- MySQL dump 10.10
--
-- Host: localhost    Database: crmp
-- ------------------------------------------------------
-- Server version	5.0.16-nt

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
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `ID` bigint(20) NOT NULL,
  `DESCRIPTION` varchar(255) default NULL,
  `NAME` varchar(255) default NULL,
  `COMPANY_ID` bigint(20) default NULL,
  `PARENT_ID` bigint(20) default NULL,
  PRIMARY KEY  (`ID`),
  KEY `FK_CATEGORY_COMPANY_ID` (`COMPANY_ID`),
  KEY `FK_CATEGORY_PARENT_ID` (`PARENT_ID`),
  CONSTRAINT `FK_CATEGORY_PARENT_ID` FOREIGN KEY (`PARENT_ID`) REFERENCES `category` (`ID`),
  CONSTRAINT `FK_CATEGORY_COMPANY_ID` FOREIGN KEY (`COMPANY_ID`) REFERENCES `crmp_tenant` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;



--
-- Table structure for table `crmp_menu_item`
--

DROP TABLE IF EXISTS `crmp_menu_item`;
CREATE TABLE `crmp_menu_item` (
  `ID` bigint(20) NOT NULL,
  `VIEWID` varchar(255) default NULL,
  `ICON` varchar(255) default NULL,
  `NAME` varchar(255) default NULL,
  `PARENT_ID` bigint(20) default NULL,
  PRIMARY KEY  (`ID`),
  KEY `FK_CRMP_Menu_Item_PARENT_ID` (`PARENT_ID`),
  CONSTRAINT `FK_CRMP_Menu_Item_PARENT_ID` FOREIGN KEY (`PARENT_ID`) REFERENCES `crmp_menu_item` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

--
-- Table structure for table `crmp_role`
--

DROP TABLE IF EXISTS `crmp_role`;
CREATE TABLE `crmp_role` (
  `ID` bigint(20) NOT NULL,
  `NAME` varchar(255) default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;


--
-- Table structure for table `crmp_role_menu_item`
--

DROP TABLE IF EXISTS `crmp_role_menu_item`;
CREATE TABLE `crmp_role_menu_item` (
  `ROLE_ID` bigint(20) NOT NULL,
  `MENU_ITEM_ID` bigint(20) NOT NULL,
  PRIMARY KEY  (`ROLE_ID`,`MENU_ITEM_ID`),
  KEY `FK_CRMP_ROLE_MENU_ITEM_MENU_ITEM_ID` (`MENU_ITEM_ID`),
  CONSTRAINT `FK_CRMP_ROLE_MENU_ITEM_MENU_ITEM_ID` FOREIGN KEY (`MENU_ITEM_ID`) REFERENCES `crmp_menu_item` (`ID`),
  CONSTRAINT `FK_CRMP_ROLE_MENU_ITEM_ROLE_ID` FOREIGN KEY (`ROLE_ID`) REFERENCES `crmp_role` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

--
-- Table structure for table `crmp_tenant`
--

DROP TABLE IF EXISTS `crmp_tenant`;
CREATE TABLE `crmp_tenant` (
  `ID` bigint(20) NOT NULL,
  `ARREARAGE` tinyint(1) default '0',
  `ADDRESS` varchar(255) default NULL,
  `HISTORYSERVICEDAYS` int(11) default NULL,
  `PAYMENT` double default NULL,
  `ARTIFICIALPERSONNAME` varchar(255) default NULL,
  `PAYMENTDATE` datetime default NULL,
  `FREEZEN` tinyint(1) default '0',
  `SERVICEDAYSLEFT` int(11) default NULL,
  `LOCKED` tinyint(1) default '0',
  `EVALUATED` tinyint(1) default '0',
  `NAME` varchar(255) default NULL,
  `SERVICEDAYSPAID` int(11) default NULL,
  `MATURITYDATE` datetime default NULL,
  `PARENTTENANT_ID` bigint(20) default NULL,
  PRIMARY KEY  (`ID`),
  KEY `FK_CRMP_Tenant_PARENTTENANT_ID` (`PARENTTENANT_ID`),
  CONSTRAINT `FK_CRMP_Tenant_PARENTTENANT_ID` FOREIGN KEY (`PARENTTENANT_ID`) REFERENCES `crmp_tenant` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;



--
-- Table structure for table `crmp_user`
--

DROP TABLE IF EXISTS `crmp_user`;
CREATE TABLE `crmp_user` (
  `ID` bigint(20) NOT NULL,
  `LOGINFAILEDTIMES` int(11) default NULL,
  `LOGINTIMES` int(11) default NULL,
  `PASSWORD` varchar(255) default NULL,
  `NEEDRESETPWD` tinyint(1) default '0',
  `ACCESSDENIEDTIMES` int(11) default NULL,
  `LASTFAILEDTIME` datetime default NULL,
  `LOGINDATE` datetime default NULL,
  `ACCOUNTCREATETIME` datetime default NULL,
  `LOCKFLAG` tinyint(1) default '0',
  `LASTCHANGEPWDTIME` datetime default NULL,
  `USERNAME` varchar(255) default NULL,
  `OLDPASSWORDS` varchar(255) default NULL,
  `LASTLOGINDATE` datetime default NULL,
  `FREEZENFLAG` tinyint(1) default '0',
  `TENANT_ID` bigint(20) default NULL,
  PRIMARY KEY  (`ID`),
  UNIQUE KEY `USERNAME` (`USERNAME`),
  KEY `FK_CRMP_User_TENANT_ID` (`TENANT_ID`),
  CONSTRAINT `FK_CRMP_User_TENANT_ID` FOREIGN KEY (`TENANT_ID`) REFERENCES `crmp_tenant` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;


--
-- Table structure for table `crmp_user_crmp_role`
--

DROP TABLE IF EXISTS `crmp_user_crmp_role`;
CREATE TABLE `crmp_user_crmp_role` (
  `roles_ID` bigint(20) NOT NULL,
  `users_ID` bigint(20) NOT NULL,
  PRIMARY KEY  (`roles_ID`,`users_ID`),
  KEY `FK_CRMP_User_CRMP_Role_users_ID` (`users_ID`),
  CONSTRAINT `FK_CRMP_User_CRMP_Role_users_ID` FOREIGN KEY (`users_ID`) REFERENCES `crmp_user` (`ID`),
  CONSTRAINT `FK_CRMP_User_CRMP_Role_roles_ID` FOREIGN KEY (`roles_ID`) REFERENCES `crmp_role` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;


--
-- Table structure for table `crmp_user_group`
--

DROP TABLE IF EXISTS `crmp_user_group`;
CREATE TABLE `crmp_user_group` (
  `NAME` varchar(255) NOT NULL,
  PRIMARY KEY  (`NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

--
-- Table structure for table `crmp_user_group_crmp_role`
--

DROP TABLE IF EXISTS `crmp_user_group_crmp_role`;
CREATE TABLE `crmp_user_group_crmp_role` (
  `roles_ID` bigint(20) NOT NULL,
  `groups_NAME` varchar(255) NOT NULL,
  PRIMARY KEY  (`roles_ID`,`groups_NAME`),
  KEY `FK_CRMP_USER_GROUP_CRMP_Role_groups_NAME` (`groups_NAME`),
  CONSTRAINT `FK_CRMP_USER_GROUP_CRMP_Role_groups_NAME` FOREIGN KEY (`groups_NAME`) REFERENCES `crmp_user_group` (`NAME`),
  CONSTRAINT `FK_CRMP_USER_GROUP_CRMP_Role_roles_ID` FOREIGN KEY (`roles_ID`) REFERENCES `crmp_role` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

--
-- Table structure for table `crmp_user_user_group`
--

DROP TABLE IF EXISTS `crmp_user_user_group`;
CREATE TABLE `crmp_user_user_group` (
  `GROUP_NAME` varchar(255) NOT NULL,
  `USERNAME` varchar(255) NOT NULL,
  PRIMARY KEY  (`GROUP_NAME`,`USERNAME`),
  KEY `FK_CRMP_USER_USER_GROUP_USERNAME` (`USERNAME`),
  CONSTRAINT `FK_CRMP_USER_USER_GROUP_USERNAME` FOREIGN KEY (`USERNAME`) REFERENCES `crmp_user` (`USERNAME`),
  CONSTRAINT `FK_CRMP_USER_USER_GROUP_GROUP_NAME` FOREIGN KEY (`GROUP_NAME`) REFERENCES `crmp_user_group` (`NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;


--
-- Table structure for table `essential_id_gen`
--

DROP TABLE IF EXISTS `essential_id_gen`;
CREATE TABLE `essential_id_gen` (
  `GEN_KEY` varchar(50) NOT NULL,
  `GEN_VALUE` decimal(38,0) default NULL,
  PRIMARY KEY  (`GEN_KEY`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

--
-- Table structure for table `measureunit`
--

DROP TABLE IF EXISTS `measureunit`;
CREATE TABLE `measureunit` (
  `ID` bigint(20) NOT NULL,
  `NAME` varchar(255) default NULL,
  `COMPANY_ID` bigint(20) default NULL,
  PRIMARY KEY  (`ID`),
  KEY `FK_MEASUREUNIT_COMPANY_ID` (`COMPANY_ID`),
  CONSTRAINT `FK_MEASUREUNIT_COMPANY_ID` FOREIGN KEY (`COMPANY_ID`) REFERENCES `crmp_tenant` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

--
-- Table structure for table `merchandise`
--

DROP TABLE IF EXISTS `merchandise`;
CREATE TABLE `merchandise` (
  `ID` bigint(20) NOT NULL,
  `DESCRIPTION` varchar(255) default NULL,
  `NAME` varchar(255) NOT NULL,
  `SUGGESTPRICE` double default NULL,
  `CODE` varchar(255) NOT NULL,
  `CATEGORY_ID` bigint(20) default NULL,
  `COMPANY_ID` bigint(20) default NULL,
  PRIMARY KEY  (`ID`),
  KEY `FK_MERCHANDISE_COMPANY_ID` (`COMPANY_ID`),
  KEY `FK_MERCHANDISE_CATEGORY_ID` (`CATEGORY_ID`),
  CONSTRAINT `FK_MERCHANDISE_CATEGORY_ID` FOREIGN KEY (`CATEGORY_ID`) REFERENCES `category` (`ID`),
  CONSTRAINT `FK_MERCHANDISE_COMPANY_ID` FOREIGN KEY (`COMPANY_ID`) REFERENCES `crmp_tenant` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

--
-- Dumping data for table `merchandise`
--


/*!40000 ALTER TABLE `merchandise` DISABLE KEYS */;
LOCK TABLES `merchandise` WRITE;
UNLOCK TABLES;
/*!40000 ALTER TABLE `merchandise` ENABLE KEYS */;

--
-- Table structure for table `merchandisestix`
--

DROP TABLE IF EXISTS `merchandisestix`;
CREATE TABLE `merchandisestix` (
  `ID` bigint(20) NOT NULL,
  `POSITIVEDEVIATIONQUANTITY` double default NULL,
  `KNOWNQUANTITY` double default NULL,
  `SOLDQUANTITY` double default NULL,
  `THRESHOLD` double default NULL,
  `DEVIATIONQUANTITY` double default NULL,
  `VERSION` int(11) default NULL,
  `STATENAME` varchar(255) default NULL,
  `NEGATIVEDEVIATIONQUANTITY` double default NULL,
  `PURCHANSEDQUANTITY` double default NULL,
  `UNIT_ID` bigint(20) default NULL,
  `MERCHANDISE_ID` bigint(20) default NULL,
  `COMPANY_ID` bigint(20) default NULL,
  PRIMARY KEY  (`ID`),
  KEY `FK_MERCHANDISESTIX_COMPANY_ID` (`COMPANY_ID`),
  KEY `FK_MERCHANDISESTIX_MERCHANDISE_ID` (`MERCHANDISE_ID`),
  KEY `FK_MERCHANDISESTIX_UNIT_ID` (`UNIT_ID`),
  CONSTRAINT `FK_MERCHANDISESTIX_UNIT_ID` FOREIGN KEY (`UNIT_ID`) REFERENCES `measureunit` (`ID`),
  CONSTRAINT `FK_MERCHANDISESTIX_COMPANY_ID` FOREIGN KEY (`COMPANY_ID`) REFERENCES `crmp_tenant` (`ID`),
  CONSTRAINT `FK_MERCHANDISESTIX_MERCHANDISE_ID` FOREIGN KEY (`MERCHANDISE_ID`) REFERENCES `merchandise` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

--
-- Dumping data for table `merchandisestix`
--


/*!40000 ALTER TABLE `merchandisestix` DISABLE KEYS */;
LOCK TABLES `merchandisestix` WRITE;
UNLOCK TABLES;
/*!40000 ALTER TABLE `merchandisestix` ENABLE KEYS */;

--
-- Table structure for table `operationlog`
--

DROP TABLE IF EXISTS `operationlog`;
CREATE TABLE `operationlog` (
  `ID` bigint(20) NOT NULL,
  `DTYPE` varchar(31) default NULL,
  `COMPLETE` datetime default NULL,
  `SOURCENAME` varchar(255) default NULL,
  `USERNAME` varchar(255) default NULL,
  `SOURCETYPE` varchar(255) default NULL,
  `START` datetime default NULL,
  `SUCCEED` tinyint(1) default '0',
  `SERVICENAME` varchar(255) default NULL,
  `TIMECOST` bigint(20) default NULL,
  `DESCRIPTION` varchar(255) default NULL,
  `FAILEDCAUSE` varchar(255) default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

--
-- Dumping data for table `operationlog`
--


/*!40000 ALTER TABLE `operationlog` DISABLE KEYS */;
LOCK TABLES `operationlog` WRITE;
INSERT INTO `operationlog` VALUES (1,'OperationLog','2011-06-12 00:45:07','???','administrator','CategoryCTO','2011-06-12 00:45:07',1,'CategoryCreateService',89,'Service: CategoryCreateService executed on ???:CategoryCTO Succeed',NULL),(51,'OperationLog','2011-06-12 00:51:59','??','administrator','CategoryUTO','2011-06-12 00:51:59',1,'CategoryUpdateBatchService',3,'Service: CategoryUpdateBatchService executed on ??:CategoryUTO Succeed',NULL),(101,'OperationLog','2011-06-12 01:00:10','æ··å‡�','administrator','CategoryUTO','2011-06-12 01:00:10',1,'CategoryUpdateBatchService',2,'Service: CategoryUpdateBatchService executed on æ··å‡�:CategoryUTO Succeed',NULL);
UNLOCK TABLES;
/*!40000 ALTER TABLE `operationlog` ENABLE KEYS */;

--
-- Table structure for table `purchaseorder`
--

DROP TABLE IF EXISTS `purchaseorder`;
CREATE TABLE `purchaseorder` (
  `ID` bigint(20) NOT NULL,
  `RECEIVINGNOTECODE` varchar(255) default NULL,
  `COMMENT` varchar(255) default NULL,
  `PURCHASERNAME` varchar(255) default NULL,
  `TOTALAMOUNT` double default NULL,
  `INVOICECODE` varchar(255) default NULL,
  `ORDERCODE` varchar(255) default NULL,
  `ORDERDATE` datetime default NULL,
  `COMPANY_ID` bigint(20) default NULL,
  `SUPPLIER_ID` bigint(20) default NULL,
  PRIMARY KEY  (`ID`),
  KEY `FK_PURCHASEORDER_COMPANY_ID` (`COMPANY_ID`),
  KEY `FK_PURCHASEORDER_SUPPLIER_ID` (`SUPPLIER_ID`),
  CONSTRAINT `FK_PURCHASEORDER_SUPPLIER_ID` FOREIGN KEY (`SUPPLIER_ID`) REFERENCES `supplier` (`ID`),
  CONSTRAINT `FK_PURCHASEORDER_COMPANY_ID` FOREIGN KEY (`COMPANY_ID`) REFERENCES `crmp_tenant` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

--
-- Dumping data for table `purchaseorder`
--


/*!40000 ALTER TABLE `purchaseorder` DISABLE KEYS */;
LOCK TABLES `purchaseorder` WRITE;
UNLOCK TABLES;
/*!40000 ALTER TABLE `purchaseorder` ENABLE KEYS */;

--
-- Table structure for table `purchaseorderitem`
--

DROP TABLE IF EXISTS `purchaseorderitem`;
CREATE TABLE `purchaseorderitem` (
  `ID` bigint(20) NOT NULL,
  `TAX` double default NULL,
  `AMOUNT` double default NULL,
  `UNITPRICE` double default NULL,
  `QUANTITY` double default NULL,
  `DISCOUNTAMOUNT` double default NULL,
  `DISCOUNTRATE` double default NULL,
  `PURCHASEORDER_ID` bigint(20) default NULL,
  `MERCHANDISE_ID` bigint(20) default NULL,
  `UNIT_ID` bigint(20) default NULL,
  `COMPANY_ID` bigint(20) default NULL,
  PRIMARY KEY  (`ID`),
  KEY `FK_PURCHASEORDERITEM_PURCHASEORDER_ID` (`PURCHASEORDER_ID`),
  KEY `FK_PURCHASEORDERITEM_MERCHANDISE_ID` (`MERCHANDISE_ID`),
  KEY `FK_PURCHASEORDERITEM_UNIT_ID` (`UNIT_ID`),
  KEY `FK_PURCHASEORDERITEM_COMPANY_ID` (`COMPANY_ID`),
  CONSTRAINT `FK_PURCHASEORDERITEM_COMPANY_ID` FOREIGN KEY (`COMPANY_ID`) REFERENCES `crmp_tenant` (`ID`),
  CONSTRAINT `FK_PURCHASEORDERITEM_MERCHANDISE_ID` FOREIGN KEY (`MERCHANDISE_ID`) REFERENCES `merchandise` (`ID`),
  CONSTRAINT `FK_PURCHASEORDERITEM_PURCHASEORDER_ID` FOREIGN KEY (`PURCHASEORDER_ID`) REFERENCES `purchaseorder` (`ID`),
  CONSTRAINT `FK_PURCHASEORDERITEM_UNIT_ID` FOREIGN KEY (`UNIT_ID`) REFERENCES `measureunit` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

--
-- Dumping data for table `purchaseorderitem`
--


/*!40000 ALTER TABLE `purchaseorderitem` DISABLE KEYS */;
LOCK TABLES `purchaseorderitem` WRITE;
UNLOCK TABLES;
/*!40000 ALTER TABLE `purchaseorderitem` ENABLE KEYS */;

--
-- Table structure for table `supplier`
--

DROP TABLE IF EXISTS `supplier`;
CREATE TABLE `supplier` (
  `ID` bigint(20) NOT NULL,
  `TELEPHONE` varchar(255) default NULL,
  `MOBILE` varchar(255) default NULL,
  `NAME` varchar(255) default NULL,
  `FAX` varchar(255) default NULL,
  `COMMENT` varchar(255) default NULL,
  `ADDRESS` varchar(255) default NULL,
  `COMPANY_ID` bigint(20) default NULL,
  PRIMARY KEY  (`ID`),
  KEY `FK_SUPPLIER_COMPANY_ID` (`COMPANY_ID`),
  CONSTRAINT `FK_SUPPLIER_COMPANY_ID` FOREIGN KEY (`COMPANY_ID`) REFERENCES `crmp_tenant` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

--
-- Dumping data for table `supplier`
--


/*!40000 ALTER TABLE `supplier` DISABLE KEYS */;
LOCK TABLES `supplier` WRITE;
UNLOCK TABLES;
/*!40000 ALTER TABLE `supplier` ENABLE KEYS */;

--
-- Table structure for table `warehouse`
--

DROP TABLE IF EXISTS `warehouse`;
CREATE TABLE `warehouse` (
  `ID` bigint(20) NOT NULL,
  `DESCRIPTION` varchar(255) default NULL,
  `LOCATION` varchar(255) default NULL,
  `NAME` varchar(255) NOT NULL,
  `COMPANY_ID` bigint(20) default NULL,
  `TYPE_ID` bigint(20) default NULL,
  PRIMARY KEY  (`ID`),
  KEY `FK_WAREHOUSE_TYPE_ID` (`TYPE_ID`),
  KEY `FK_WAREHOUSE_COMPANY_ID` (`COMPANY_ID`),
  CONSTRAINT `FK_WAREHOUSE_COMPANY_ID` FOREIGN KEY (`COMPANY_ID`) REFERENCES `crmp_tenant` (`ID`),
  CONSTRAINT `FK_WAREHOUSE_TYPE_ID` FOREIGN KEY (`TYPE_ID`) REFERENCES `warehousetype` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

--
-- Dumping data for table `warehouse`
--


/*!40000 ALTER TABLE `warehouse` DISABLE KEYS */;
LOCK TABLES `warehouse` WRITE;
UNLOCK TABLES;
/*!40000 ALTER TABLE `warehouse` ENABLE KEYS */;

--
-- Table structure for table `warehousetype`
--

DROP TABLE IF EXISTS `warehousetype`;
CREATE TABLE `warehousetype` (
  `ID` bigint(20) NOT NULL,
  `DESCRIPTION` varchar(255) default NULL,
  `NAME` varchar(255) NOT NULL,
  `COMPANY_ID` bigint(20) default NULL,
  PRIMARY KEY  (`ID`),
  KEY `FK_WAREHOUSETYPE_COMPANY_ID` (`COMPANY_ID`),
  CONSTRAINT `FK_WAREHOUSETYPE_COMPANY_ID` FOREIGN KEY (`COMPANY_ID`) REFERENCES `crmp_tenant` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

--
-- Dumping data for table `warehousetype`
--


/*!40000 ALTER TABLE `warehousetype` DISABLE KEYS */;
LOCK TABLES `warehousetype` WRITE;
UNLOCK TABLES;
/*!40000 ALTER TABLE `warehousetype` ENABLE KEYS */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

--
-- Dumping data for table `category`
--


/*!40000 ALTER TABLE `category` DISABLE KEYS */;
LOCK TABLES `category` WRITE;
INSERT INTO `category` VALUES (1,'','æ··å‡�åœŸ',1,NULL);
UNLOCK TABLES;
/*!40000 ALTER TABLE `category` ENABLE KEYS */;

--
-- Dumping data for table `crmp_role`
--


/*!40000 ALTER TABLE `crmp_role` DISABLE KEYS */;
LOCK TABLES `crmp_role` WRITE;
INSERT INTO `crmp_role` VALUES (1,'SA'),(2,'ADMIN'),(3,'OP');
UNLOCK TABLES;
/*!40000 ALTER TABLE `crmp_role` ENABLE KEYS */;


--
-- Dumping data for table `crmp_menu_item`
--


/*!40000 ALTER TABLE `crmp_menu_item` DISABLE KEYS */;
LOCK TABLES `crmp_menu_item` WRITE;
INSERT INTO `crmp_menu_item` VALUES (1,'','','ç§Ÿæˆ·ç®¡ç�†',NULL),(2,'net.vicp.madz.admin.views.RoleView','dataGrid','è§’è‰²ç®¡ç�†',1),(3,'net.vicp.madz.framework.views.SystemSettings','application','ç³»ç»Ÿè®¾å®š',NULL),(4,'','','åŸºç¡€æ•°æ�®ç®¡ç�†',NULL),(5,'net.vicp.madz.biz.common.views.CategoryView','dataGrid','å•†å“�åˆ†ç±»',4),(6,'net.vicp.madz.biz.common.views.MerchandiseView','dataGrid','äº§å“�åˆ—è¡¨',4);
UNLOCK TABLES;
/*!40000 ALTER TABLE `crmp_menu_item` ENABLE KEYS */;

--
-- Dumping data for table `crmp_role_menu_item`
--


/*!40000 ALTER TABLE `crmp_role_menu_item` DISABLE KEYS */;
LOCK TABLES `crmp_role_menu_item` WRITE;
INSERT INTO `crmp_role_menu_item` VALUES (1,1),(1,2),(1,3),(1,4),(1,5),(1,6);
UNLOCK TABLES;
/*!40000 ALTER TABLE `crmp_role_menu_item` ENABLE KEYS */;

--
-- Dumping data for table `crmp_tenant`
--

/*!40000 ALTER TABLE `crmp_tenant` DISABLE KEYS */;
LOCK TABLES `crmp_tenant` WRITE;
INSERT INTO `crmp_tenant` VALUES (1,0,'',0,0,'Barry',NULL,0,180,0,0,'Madz Technologies',0,'2011-06-11 19:01:53',NULL);
UNLOCK TABLES;
/*!40000 ALTER TABLE `crmp_tenant` ENABLE KEYS */;

--
-- Dumping data for table `crmp_user`
--


/*!40000 ALTER TABLE `crmp_user` DISABLE KEYS */;
LOCK TABLES `crmp_user` WRITE;
INSERT INTO `crmp_user` VALUES (1,0,3,'4a6dfef787c0e3d8a5ccacb67e709060',0,0,'2011-06-12 00:26:24','2011-06-12 00:44:48','2011-06-12 00:26:46',0,'2011-06-12 00:26:54','administrator','','2011-06-12 00:43:50',0,1);
UNLOCK TABLES;
/*!40000 ALTER TABLE `crmp_user` ENABLE KEYS */;


--
-- Dumping data for table `crmp_user_group`
--


/*!40000 ALTER TABLE `crmp_user_group` DISABLE KEYS */;
LOCK TABLES `crmp_user_group` WRITE;
INSERT INTO `crmp_user_group` VALUES ('ADMINGroup'),('OPGroup');
UNLOCK TABLES;
/*!40000 ALTER TABLE `crmp_user_group` ENABLE KEYS */;


--
-- Dumping data for table `crmp_user_group_crmp_role`
--


/*!40000 ALTER TABLE `crmp_user_group_crmp_role` DISABLE KEYS */;
LOCK TABLES `crmp_user_group_crmp_role` WRITE;
INSERT INTO `crmp_user_group_crmp_role` VALUES (2,'ADMINGroup'),(3,'OPGroup');
UNLOCK TABLES;
/*!40000 ALTER TABLE `crmp_user_group_crmp_role` ENABLE KEYS */;
--
-- Dumping data for table `crmp_user_crmp_role`
--


/*!40000 ALTER TABLE `crmp_user_crmp_role` DISABLE KEYS */;
LOCK TABLES `crmp_user_crmp_role` WRITE;
INSERT INTO `crmp_user_crmp_role` VALUES (1,1);
UNLOCK TABLES;
/*!40000 ALTER TABLE `crmp_user_crmp_role` ENABLE KEYS */;
--
-- Dumping data for table `crmp_user_user_group`
--


/*!40000 ALTER TABLE `crmp_user_user_group` DISABLE KEYS */;
LOCK TABLES `crmp_user_user_group` WRITE;
INSERT INTO `crmp_user_user_group` VALUES ('ADMINGroup','administrator');
UNLOCK TABLES;
/*!40000 ALTER TABLE `crmp_user_user_group` ENABLE KEYS */;

--
-- Dumping data for table `essential_id_gen`
--


/*!40000 ALTER TABLE `essential_id_gen` DISABLE KEYS */;
LOCK TABLES `essential_id_gen` WRITE;
INSERT INTO `essential_id_gen` VALUES ('ACCOUNT_ID','0'),('CATEGORY_ID','50'),('COMPANY_ID','0'),('MEASURE_UNIT_ID','0'),('MENUITEM_ID','0'),('MERCHANDISE_ID','0'),('MERCHANDISE_STIX_ID','0'),('OPLOG_ID','150'),('PURCHASE_ORDER_ID','0'),('PURCHASE_ORDER_ITEM_ID','0'),('ROLE_ID','0'),('SUPPLIER_ID','0'),('WAREHOUSE_ID','0'),('WAREHOUSE_TYPE_ID','0');
UNLOCK TABLES;
/*!40000 ALTER TABLE `essential_id_gen` ENABLE KEYS */;
