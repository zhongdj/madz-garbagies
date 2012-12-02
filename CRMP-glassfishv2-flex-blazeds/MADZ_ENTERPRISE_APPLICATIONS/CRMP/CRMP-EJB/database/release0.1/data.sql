ALTER TABLE crmp_tenant MODIFY created_by varchar(32) DEFAULT NULL;

INSERT INTO `crmp_tenant` (id, SERVICE_DAYS_LEFT, MATURITY_DATE, ADDRESS, ARREARAGE, LOCKED, EVALUATED, PAYMENT_DATE, HISTORY_SERVICE_DAYS, UPDATED_ON, NAME, CREATED_ON, 
FREEZEN, DELETED, SERVICE_DAYS_PAID, PAYMENT,ARTIFICIAL_PERSON_NAME, CREATED_BY, PARENT_TENANT_ID) 
VALUES ('1',65535,'9999-06-11 19:01:53',  '',0,0,0, '2011-06-11 19:01:53',0,NULL, 'Madz Technologies', '2011-06-11 19:01:53', 0,0,65535,0,'Barry',NULL,NULL);

INSERT INTO `crmp_user` (ID, USERNAME, PASSWORD, EMAIL, FULLNAME, TENANT_ID, CREATED_BY) VALUES('1', 'zhongdj@gmail.com','4a6dfef787c0e3d8a5ccacb67e709060','zhongdj@gmail.com','Barry Zhong','1','1');
INSERT INTO `crmp_user` (ID, USERNAME, PASSWORD, EMAIL, FULLNAME, TENANT_ID, CREATED_BY) VALUES('2', 'administrator','4a6dfef787c0e3d8a5ccacb67e709060','zhongdj@gmail.com','Barry Zhong','1','1');

UPDATE `crmp_tenant` SET CREATED_BY = '1';

ALTER TABLE crmp_tenant modify created_by varchar(32) NOT NULL;

INSERT INTO `crmp_role` (id, NAME, tenant_Id, created_by, created_on)
                 VALUES (1,'SA','1','1',now()),
                        (2,'ADMIN','1','1',now()),
                        (3,'OP','1','1',now());

INSERT INTO `crmp_user_group` (id, NAME, tenant_Id, created_by, created_on) 
                       VALUES ('1', 'ADMINGroup','1','1',now()),
                              ('2', 'OPGroup','1','1',now());

INSERT INTO `crmp_user_crmp_role`(users_id, roles_id) VALUES (1,1), (2,1);

ALTER TABLE crmp_user_group_crmp_role ADD COLUMN GROUP_NAME varchar(40) NOT NULL;
INSERT INTO `crmp_user_group_crmp_role` (roles_id, groups_id, GROUP_NAME)VALUES ('1','1','ADMINGroup');

INSERT INTO `crmp_user_user_group` (GROUP_NAME, USERNAME) 
                            VALUES ('ADMINGroup','administrator'), 
                                   ('ADMINGroup','zhongdj@gmail.com');

INSERT INTO `crmp_menu_item` (id,                                       VIEWID,            NAME,ICON,PARENT_id, tenant_Id,created_by,created_on)
                      VALUES (1,'net.vicp.madz.framework.views.SystemSettings','System Setting','application',NULL,'1','1',now()),
                             (2,                                            '','Common Data','',NULL,'1','1',now()),
                             (3,'net.vicp.madz.biz.common.views.CategoryView','Category','dataGrid',2,'1','1',now()),
                             (4,'net.vicp.madz.biz.common.views.MerchandiseView','Merchandise','dataGrid',2,'1','1',now());
                             
INSERT INTO `crmp_role_menu_item` (ROLE_ID, MENU_ITEM_ID) VALUES (1,1),(1,2),(1,3),(1,4);


