
INSERT INTO `crmp_category` VALUES ('81aabc6d-af4e-4bd3-a369-ff57870f049c','¿C10,C15¿','2011-06-22 01:37:59','¿¿¿¿',NULL,0,'2',NULL,'1',NULL),('fdb10b7f-d3d7-4be2-b51a-52a3adeb2e46','¿¿¿¿,¿¿¿¿','2011-06-22 01:39:07','¿¿¿¿¿¿',NULL,0,'2',NULL,'1',NULL);
UNLOCK TABLES;


LOCK TABLES `crmp_menu_item` WRITE;
INSERT INTO `crmp_menu_item` VALUES 
 ('1','net.vicp.madz.framework.views.SystemSettings','2011-06-22 01:35:26','System Setting',NULL,'application',0,'1',NULL,'1',NULL)
,('2','','2011-06-22 01:35:26','Common Data',NULL,'',0,'1',NULL,'1',NULL)
,('3','net.vicp.madz.biz.common.views.CategoryView','2011-06-22 01:35:26','Category',NULL,'dataGrid',0,'1','2','1',NULL)
,('4','net.vicp.madz.biz.common.views.MerchandiseView','2011-06-22 01:35:26','Merchandise',NULL,'dataGrid',0,'1','2','1',NULL)
,('5','net.vicp.madz.biz.common.views.UnitOfMeasureView','2011-06-22 01:35:26','UnitOfMeasure',NULL,'dataGrid',0,'1','2','1',NULL)
,('6','','2011-08-02 13:19:20', 'Admin',NULL,'',0,'1',NULL,'1',NULL)
,('7','net.vicp.madz.admin.views.MenuItemView','2011-08-02 13:19:20','MenuItem',NULL,'dataGrid',0,'1','6','1',NULL)

;
UNLOCK TABLES;


LOCK TABLES `crmp_product` WRITE;
INSERT INTO `crmp_product` VALUES ('5de357f3-5601-4bd2-86b8-097b1ac5f1c6',100,'2011-06-22 01:44:46','',NULL,'·À¶³',0,'·À¶³','fdb10b7f-d3d7-4be2-b51a-52a3adeb2e46','2','87abb1f6-aa72-4f62-a220-00f2e4306cbf','1',NULL),('9f5d6bbe-06e3-4bcc-aca7-2adf4a5fec01',300,'2011-06-22 01:41:07','½ÏµÍ¼¼Êõ¹æ¸ñ','2011-06-22 01:43:04','C15',0,'C15','81aabc6d-af4e-4bd3-a369-ff57870f049c','2','87abb1f6-aa72-4f62-a220-00f2e4306cbf','1','2'),('ab5b63d2-ee71-4574-bf36-69b010135c04',30,'2011-06-22 01:43:42','',NULL,'ÀäÄý',0,'ÀäÄý','fdb10b7f-d3d7-4be2-b51a-52a3adeb2e46','2','87abb1f6-aa72-4f62-a220-00f2e4306cbf','1',NULL),('b55d4ef0-5280-4ff4-96c4-4263ea3ef636',200,'2011-06-22 01:40:08','×îµÍ¼¼Êõ¹æ¸ñ','2011-06-22 01:43:04','C10',0,'C10','81aabc6d-af4e-4bd3-a369-ff57870f049c','2','87abb1f6-aa72-4f62-a220-00f2e4306cbf','1','2'),('f09aa38f-9eb2-4a8e-86ea-ecbc8c7f76a0',400,'2011-06-22 01:41:30','³£ÓÃ¼¼Êõ¹æ¸ñ','2011-06-22 01:43:04','C20',0,'C20','81aabc6d-af4e-4bd3-a369-ff57870f049c','2','87abb1f6-aa72-4f62-a220-00f2e4306cbf','1','2');
UNLOCK TABLES;


LOCK TABLES `crmp_role` WRITE;
INSERT INTO `crmp_role` VALUES ('1',NULL,'2011-06-22 01:32:49',0,'SA','1','1',NULL),('2',NULL,'2011-06-22 01:32:49',0,'ADMIN','1','1',NULL),('3',NULL,'2011-06-22 01:32:49',0,'OP','1','1',NULL);
UNLOCK TABLES;

LOCK TABLES `crmp_role_menu_item` WRITE;
INSERT INTO `crmp_role_menu_item` (ROLE_ID, MENU_ITEM_ID) VALUES ('1','1'),('1','2'),('1','3'),('1','4'),('1','5'),('1','6'),('1','7');
UNLOCK TABLES;


LOCK TABLES `crmp_tenant` WRITE;
INSERT INTO `crmp_tenant` VALUES ('1',65535,65535,'Madz Technologies','9999-06-11 19:01:53','Barry',0,0,0,0,NULL,'2011-06-11 19:01:53',0,0,0,'2011-06-11 19:01:53','','1',NULL,NULL);
UNLOCK TABLES;

LOCK TABLES `crmp_unit_of_measure` WRITE;
INSERT INTO `crmp_unit_of_measure` VALUES ('87abb1f6-aa72-4f62-a220-00f2e4306cbf','¿','2011-06-22 01:39:35','¿¿¿',NULL,0,'1','2',NULL);
UNLOCK TABLES;

LOCK TABLES `crmp_user` WRITE;
INSERT INTO `crmp_user` VALUES ('1',NULL,0,0,0,NULL,'4a6dfef787c0e3d8a5ccacb67e709060',NULL,'Barry Zhong',NULL,NULL,NULL,NULL,NULL,'zhongdj@gmail.com',NULL,NULL,0,'zhongdj@gmail.com','1','1',NULL),('2',0,0,0,0,'2011-06-22 01:36:58','4a6dfef787c0e3d8a5ccacb67e709060','2011-06-22 01:33:27','Barry Zhong','2011-06-22 01:33:27',2,'2011-06-22 01:33:27',NULL,'','administrator',0,NULL,0,'zhongdj@gmail.com','1','1',NULL);
UNLOCK TABLES;

LOCK TABLES `crmp_user_crmp_role` WRITE;
INSERT INTO `crmp_user_crmp_role` VALUES ('1','1'),('1','2');
UNLOCK TABLES;


LOCK TABLES `crmp_user_group` WRITE;
INSERT INTO `crmp_user_group` VALUES ('1','ADMINGroup',NULL,0,'2011-06-22 01:32:49','1','1',NULL),('2','OPGroup',NULL,0,'2011-06-22 01:32:49','1','1',NULL);
UNLOCK TABLES;


ALTER TABLE `crmp_user_group_crmp_role` ADD COLUMN GROUP_NAME varchar(40);
LOCK TABLES `crmp_user_group_crmp_role` WRITE;
INSERT INTO `crmp_user_group_crmp_role` VALUES ('1','1','ADMINGroup');
UNLOCK TABLES;


LOCK TABLES `crmp_user_user_group` WRITE;
INSERT INTO `crmp_user_user_group`(GROUP_NAME, USERNAME) VALUES ('ADMINGroup','administrator'),('ADMINGroup','zhongdj@gmail.com');
UNLOCK TABLES;
