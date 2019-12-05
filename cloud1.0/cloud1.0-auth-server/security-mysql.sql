DROP TABLE IF EXISTS `sys_permission`;
 
CREATE TABLE `sys_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(10) DEFAULT '',
  `descritpion` varchar(10) DEFAULT '',
  `url` varchar(10) DEFAULT '',
  `pid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;
 
/*Data for the table `sys_permission` */
 
insert  into `sys_permission`(`id`,`name`,`descritpion`,`url`,`pid`) values (3,'ADMIN','','',NULL),(4,'USER','','',NULL);
 
/*Table structure for table `sys_permission_role` */
 
DROP TABLE IF EXISTS `sys_permission_role`;
 
CREATE TABLE `sys_permission_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) DEFAULT NULL,
  `permission_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;
 
/*Data for the table `sys_permission_role` */
 
insert  into `sys_permission_role`(`id`,`role_id`,`permission_id`) values (1,2,4),(2,1,3);
 
/*Table structure for table `sys_role` */
 
DROP TABLE IF EXISTS `sys_role`;
 
CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(10) DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;
 
/*Data for the table `sys_role` */
 
insert  into `sys_role`(`id`,`name`) values (1,'ADMIN'),(2,'USER');
 
/*Table structure for table `sys_role_user` */
 
DROP TABLE IF EXISTS `sys_role_user`;
 
CREATE TABLE `sys_role_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Sys_user_id` int(11) DEFAULT NULL,
  `sys_role_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;
 
/*Data for the table `sys_role_user` */
 
insert  into `sys_role_user`(`id`,`Sys_user_id`,`sys_role_id`) values (1,2,1),(2,3,2);
 
/*Table structure for table `sys_user` */
 
DROP TABLE IF EXISTS `sys_user`;
 
CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(10) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;
 
/*Data for the table `sys_user` admin/admin xuweichao/xuweichao*/
 
insert  into `sys_user`(`id`,`name`,`password`) values (2,'admin','$2a$10$Yks2LoqzBUHEWjyLCnsdtepI4oCNip9yNdf67y19ewF8geORNAO5m'),(3,'xuweichao','$2a$10$kmFQOKZw8l776qXp00Lq9e2drL5MUSpG9YHnQtQwbVzyUjJQwHNha');
