/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.0.27-community-nt : Database - happy-code
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`happy-code` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `happy-code`;

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL auto_increment,
  `username` varchar(30) NOT NULL,
  `password` varchar(30) NOT NULL,
  `email` varchar(30) NOT NULL,
  `head` varchar(30) default NULL,
  `gender` tinyint(1) NOT NULL,
  `phone` varchar(20) NOT NULL,
  `userInfo` text,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `user` */

insert  into `user`(`id`,`username`,`password`,`email`,`head`,`gender`,`phone`,`userInfo`) values (1,'lh','4QrcOUm6Wau+VuBX8g+IPg==','1074345116@qq.com',NULL,1,'',NULL),(2,'admin','AZICOnu9cyUFFvBp3xi1AA==','1074345116@qq.com',NULL,1,'13298309928','						'),(3,'admin123','AZICOnu9cyUFFvBp3xi1AA==','1074345116@qq.com',NULL,1,'13298309928','	111					');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
