-- MySQL dump 10.11
--
-- Host: localhost    Database: gbowlingportfolio
-- ------------------------------------------------------
-- Server version	5.0.45-log

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
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
CREATE TABLE `categories` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(100) default NULL,
  `parent_id` int(11) default NULL,
  `code` varchar(100) default NULL,
  `rank` int(11) default NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `FK4D47461CCEF0B888` (`parent_id`)
) ENGINE=MyISAM AUTO_INCREMENT=19 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (1,'Photography',NULL,'photography',1),(18,'DISTRO.Y',6,'collections.DISTRO.Y',0),(5,'about',NULL,'about',0),(6,'Collections',NULL,'collections',2),(7,'Void Above',6,'collections.void above',0),(10,'Fashion',1,'photography.fashion',0),(11,'Events',1,'photography.events',3),(12,'Portraits ',1,'photography.portraits',0);
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `configuration_property`
--

DROP TABLE IF EXISTS `configuration_property`;
CREATE TABLE `configuration_property` (
  `id` int(11) NOT NULL auto_increment,
  `code` varchar(100) NOT NULL,
  `default_value` varchar(255) default NULL,
  `type` varchar(100) default NULL,
  `value` varchar(255) default NULL,
  `version` bigint(20) NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `id` (`id`),
  UNIQUE KEY `code` (`code`),
  UNIQUE KEY `code_2` (`code`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `configuration_property`
--

LOCK TABLES `configuration_property` WRITE;
/*!40000 ALTER TABLE `configuration_property` DISABLE KEYS */;
INSERT INTO `configuration_property` VALUES (1,'media.dir','/Users/tea/dev/ApproachingPi/gillianbowling.com/images\r\n','String','/home/api/gillianbowling.com/media/images',1),(2,'media.cache.dir','/Users/tea/dev/ApproachingPi/gillianbowling.com/images_cache','String','/home/api/gillianbowling.com/media/images_cache',1);
/*!40000 ALTER TABLE `configuration_property` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `photos`
--

DROP TABLE IF EXISTS `photos`;
CREATE TABLE `photos` (
  `id` int(11) NOT NULL auto_increment,
  `date_modified` datetime default NULL,
  `description` longtext,
  `file_name` varchar(100) default NULL,
  `height` int(11) default NULL,
  `rank` int(11) default NULL,
  `width` int(11) default NULL,
  `category_id` int(11) NOT NULL,
  `file_extension` varchar(5) default NULL,
  `date_created` datetime default NULL,
  `orientation` int(11) default NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `FKC50C8881AF32B3B4` (`category_id`)
) ENGINE=MyISAM AUTO_INCREMENT=138 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `photos`
--

LOCK TABLES `photos` WRITE;
/*!40000 ALTER TABLE `photos` DISABLE KEYS */;
INSERT INTO `photos` VALUES (1,'2010-07-13 01:57:41','','photos/1/DSC_2803_ps.jpg',979,12,650,7,'jpg',NULL,2),(43,'2010-08-10 00:36:11','','photos/43/DSC_1631.JPG',465,14,700,11,'JPG','2010-08-10 00:36:11',1),(5,'2010-07-13 21:33:49','','photos/5/DSC_3008_ps.jpg',432,13,650,7,'jpg',NULL,2),(14,'2010-07-13 22:52:33','','photos/14/VoidAbove_ps.jpg',652,9,433,7,'jpg','2010-07-13 22:50:01',2),(15,'2010-07-13 22:54:29','','photos/15/VoidAbove_2_ps.jpg',979,10,650,7,'jpg','2010-07-13 22:54:29',2),(10,'2010-09-16 03:47:51','','photos/10/photo.JPG',1000,NULL,1000,5,'JPG','2010-07-08 03:40:57',2),(11,'2010-09-16 03:07:44','<p>Obsessed with form in an exaggerated sense, Gillian Bowling has reinvented versatile pieces reminiscent of Victorian and Gilded ages. Gillian expresses detail in a minimal way, focusing on quality and construction. Self taught through the processes of deconstruction and reconstruction, she has developed a love for executing a product from concept to consumer that is evident in her attention to form and fit.</p>\r\n<p style=\"margin-bottom: 0in;\">Starting with a t-shirt line, DISTRO.Y, she co-founded in 2004, Gillian had the urge to break away to focus on more tailored clothing. Her ability to reformulate a beloved piece of clothing into a whole new idea allowed Gillian to launch Void Above in 2006. The core of her work remains on playing with proportions and expressing ornate details through stitching rather than appliqu&eacute;.</p>\r\n<p style=\"margin-bottom: 0in;\">The center of Gillian&rsquo;s work revolves around the composition of each piece. Her aesthetic is sweet and rustic yet deterioration plays a large role in the final product. Her clothes are for a woman who knows her body and needs cultivated pieces to be paired any which way.</p>\r\n<p style=\"margin-bottom: 0in;\">Gillian lives in New York City after relocating from Boston, where she ran her business for over 6 years. In addition to designing and managing DISTRO.Y and Void Above, Gillian designs knitwear, handles product development for other companies and is a photographer, working with both event and editorial clients.</p>\r\n<p style=\"margin-bottom: 0in;\">&nbsp;Gillian currently sells her designs at <span style=\"color: #0000ff;\"><span style=\"text-decoration: underline;\">www.voidabove.com</span></span></p>',NULL,NULL,NULL,NULL,5,NULL,'2010-07-08 03:45:08',1),(41,'2010-08-10 00:34:37','','photos/41/Picture 200.jpg',2848,6,4288,11,'jpg','2010-08-10 00:34:37',1),(13,'2010-07-13 22:39:24','','photos/13/DSC_2889_ps.jpg',432,15,650,7,'jpg','2010-07-13 22:39:24',1),(42,'2010-08-10 00:35:08','','photos/42/DSC_4162.JPG',465,21,700,11,'JPG','2010-08-10 00:35:08',1),(12,'2010-07-13 21:35:29','','photos/12/DSC_2881_ps.jpg',432,16,650,7,'jpg','2010-07-13 21:35:29',1),(20,'2010-07-14 19:46:23','','photos/20/Pixie_AquaTint_sahra_ps.jpg',867,5,650,7,'jpg','2010-07-14 19:46:23',2),(132,'2010-09-21 20:35:11','','photos/132/lightroom_portfolio-3.jpg',680,NULL,1024,7,'jpg','2010-09-21 20:35:11',1),(21,'2010-07-14 19:49:48','','photos/21/Pixie_LavanderIce_sahra_ps.jpg',867,6,650,7,'jpg','2010-07-14 19:49:48',2),(114,'2010-09-16 19:11:11','','photos/114/DSC06680_portfolio.jpg',1333,1,1000,7,'jpg','2010-09-16 19:11:11',2),(23,'2010-07-14 20:29:18','','photos/23/Galladora_blk_ari_ps.jpg',867,7,650,7,'jpg','2010-07-14 20:28:48',2),(26,'2010-08-06 21:07:52','','photos/26/lightroom_portfolio_ps.jpg',979,11,650,7,'jpg','2010-08-06 21:07:52',2),(27,'2010-08-09 22:39:30','','photos/27/DSC_0639.jpg',465,19,700,11,'jpg','2010-08-09 22:39:30',1),(28,'2010-08-09 22:40:07','','photos/28/DSC_4887.JPG',465,32,700,11,'JPG','2010-08-09 22:40:07',1),(29,'2010-08-09 22:40:41','','photos/29/DSC_1645_ps.jpg',1054,13,700,11,'jpg','2010-08-09 22:40:41',2),(30,'2010-08-09 22:42:45','','photos/30/DSC_3093.JPG',465,29,700,11,'JPG','2010-08-09 22:42:45',1),(31,'2010-08-11 00:34:25','','photos/31/DSC_2073.JPG',465,1,700,11,'JPG','2010-08-09 22:43:23',1),(32,'2010-08-09 22:44:38','','photos/32/DSC_5876.JPG',1054,33,700,11,'JPG','2010-08-09 22:44:38',2),(33,'2010-08-09 22:45:28','','photos/33/DSC_7738.jpg',425,23,640,11,'jpg','2010-08-09 22:45:28',1),(64,'2010-08-11 00:59:34','','photos/64/DSC_8359.JPG',4288,3,2848,11,'JPG','2010-08-11 00:59:34',2),(35,'2010-08-09 22:47:24','','photos/35/DSC_3860.JPG',465,2,700,11,'JPG','2010-08-09 22:47:24',1),(36,'2010-08-09 22:48:10','','photos/36/DSC_3066.JPG',465,34,700,11,'JPG','2010-08-09 22:48:10',1),(37,'2010-08-09 22:48:32','<p>Hearthrob&nbsp;</p>','photos/37/DSC_3631.JPG',1054,25,700,11,'JPG','2010-08-09 22:48:32',2),(38,'2010-08-09 23:00:07','','photos/38/DSC_3191.jpg',465,35,700,11,'jpg','2010-08-09 23:00:07',1),(39,'2010-08-10 00:32:56','','photos/39/DSC_3850.JPG',465,36,700,11,'JPG','2010-08-10 00:32:56',1),(62,'2010-08-11 00:30:26','','photos/62/DSC_8446.JPG',4288,38,2848,11,'JPG','2010-08-11 00:30:26',2),(70,'2010-08-11 15:10:03','','photos/70/DSC_1472.JPG',1054,47,700,11,'JPG','2010-08-11 15:10:03',2),(45,'2010-08-10 00:39:50','','photos/45/DSC_3125.jpg',465,39,700,11,'jpg','2010-08-10 00:39:50',1),(46,'2010-08-10 00:40:55','','photos/46/DSC_6862.JPG',2848,40,4288,11,'JPG','2010-08-10 00:40:55',1),(47,'2010-08-10 00:41:43','','photos/47/DSC_7542.jpg',425,37,640,11,'jpg','2010-08-10 00:41:43',1),(48,'2010-08-10 00:42:34','','photos/48/DSC_9221.jpg',425,10,640,11,'jpg','2010-08-10 00:42:34',1),(49,'2010-08-10 00:43:12','','photos/49/DSC_4581.JPG',1054,18,700,11,'JPG','2010-08-10 00:43:12',2),(50,'2010-08-10 00:44:08','','photos/50/DSC_8419.JPG',2848,43,4288,11,'JPG','2010-08-10 00:44:08',1),(66,'2010-08-11 01:45:00','','photos/66/Picture 513.jpg',2848,41,4288,11,'jpg','2010-08-11 01:45:00',1),(63,'2010-08-11 00:32:29','','photos/63/DSC_9087.JPG',4288,42,2848,11,'JPG','2010-08-11 00:32:29',2),(53,'2010-08-10 00:47:40','','photos/53/Picture 095.jpg',2848,24,4288,11,'jpg','2010-08-10 00:47:40',1),(54,'2010-08-10 00:57:59','','photos/54/Mommy_O_100801_2_portfolio.jpg',1054,12,700,10,'jpg','2010-08-10 00:57:59',2),(55,'2010-08-10 00:58:28','','photos/55/Mommy_O_100801-6_portfolio.jpg',531,13,700,10,'jpg','2010-08-10 00:58:28',1),(56,'2010-08-10 00:58:55','','photos/56/Mommy_O_100801-10_portfolio.jpg',975,14,700,10,'jpg','2010-08-10 00:58:55',2),(57,'2010-08-10 01:00:02','','photos/57/Mommy_O_100801_portfolio.jpg',1054,15,700,10,'jpg','2010-08-10 01:00:02',2),(58,'2010-08-10 01:00:28','','photos/58/Mommy_O_100801_1_portfolio.jpg',465,16,700,10,'jpg','2010-08-10 01:00:28',1),(59,'2010-08-10 01:00:56','','photos/59/Mommy_O_100801_4_portfolio.jpg',1054,17,700,10,'jpg','2010-08-10 01:00:56',2),(60,'2010-08-10 20:59:37','','photos/60/lightroom_portfolio-7.jpg',680,17,1024,7,'jpg','2010-08-10 20:59:37',1),(65,'2010-08-11 01:40:59','','photos/65/DSC_0140.JPG',680,46,1024,11,'JPG','2010-08-11 01:40:59',1),(68,'2010-08-11 01:51:38','','photos/68/DSC_0147.JPG',1542,7,1024,11,'JPG','2010-08-11 01:51:38',2),(69,'2010-08-11 01:52:13','','photos/69/DSC_0236.JPG',680,5,1024,11,'JPG','2010-08-11 01:52:13',1),(71,'2010-08-11 15:10:34','','photos/71/DSC_4211.JPG',465,8,700,11,'JPG','2010-08-11 15:10:34',1),(72,'2010-08-11 15:11:48','','photos/72/DSC_6794.JPG',2848,9,4288,11,'JPG','2010-08-11 15:11:48',1),(74,'2010-08-11 15:14:09','','photos/74/DSC_6747.JPG',2848,12,4288,11,'JPG','2010-08-11 15:14:09',1),(76,'2010-08-11 15:16:21','','photos/76/DSC_3617.JPG',465,15,700,11,'JPG','2010-08-11 15:16:21',1),(77,'2010-08-11 15:18:49','','photos/77/DSC_6990.JPG',2848,20,4288,11,'JPG','2010-08-11 15:18:49',1),(78,'2010-08-11 15:23:07','','photos/78/DSC_7705.JPG',2848,17,4288,11,'JPG','2010-08-11 15:23:07',1),(79,'2010-08-11 15:23:33','','photos/79/DSC_1641.JPG',465,27,700,11,'JPG','2010-08-11 15:23:33',1),(80,'2010-08-11 15:24:05','','photos/80/DSC_3565.JPG',1054,22,700,11,'JPG','2010-08-11 15:24:05',2),(95,'2010-09-02 16:52:58','','photos/95/Lyndsey_100824_1-5_portfolio.jpg',681,1,1000,12,'jpg','2010-09-02 16:52:58',1),(82,'2010-08-11 17:26:00','','photos/82/DSC_3684.JPG',465,45,700,11,'JPG','2010-08-11 17:26:00',1),(83,'2010-08-11 17:27:42','','photos/83/DSC_0332_ps.jpg',465,11,700,11,'jpg','2010-08-11 17:27:42',1),(84,'2010-08-11 17:38:32','','photos/84/DSC_0812.JPG',680,4,1024,11,'JPG','2010-08-11 17:38:32',1),(85,'2010-08-11 17:38:55','','photos/85/DSC_0735.JPG',1542,28,1024,11,'JPG','2010-08-11 17:38:55',2),(86,'2010-08-11 17:40:11','','photos/86/DSC_8094.JPG',680,26,1024,11,'JPG','2010-08-11 17:40:11',1),(87,'2010-08-11 17:41:51','','photos/87/Picture 589.jpg',2848,31,4288,11,'jpg','2010-08-11 17:41:51',1),(91,'2010-08-11 18:05:26','','photos/91/DSC_2152.JPG',465,16,700,11,'JPG','2010-08-11 18:05:26',1),(92,'2010-08-11 19:38:01','','photos/92/DSC_6348.jpg',465,30,700,11,'jpg','2010-08-11 19:38:01',1),(89,'2010-08-11 18:00:13','','photos/89/DSC_9209.JPG',2848,44,4007,11,'JPG','2010-08-11 18:00:13',1),(94,'2010-09-02 16:49:45','','photos/94/Lyndsey_100824_1-2_portfolio.jpg',1506,2,1000,12,'jpg','2010-09-02 16:49:45',2),(97,'2010-09-02 16:54:21','','photos/97/Lyndsey_100824_1-9_portfolio.jpg',1506,4,1000,12,'jpg','2010-09-02 16:54:21',2),(98,'2010-09-02 16:55:41','','photos/98/Lyndsey_100824_1-3_portfolio.jpg',659,3,1000,12,'jpg','2010-09-02 16:55:41',1),(99,'2010-09-02 16:56:22','','photos/99/Lyndsey_100824_1-8_portfolio.jpg',664,5,1000,12,'jpg','2010-09-02 16:56:22',1),(100,'2010-09-02 20:02:48','<p>This photo series is called : Manifestation of Me</p>\r\n<p>My goal for this series was to do portraits of a person in the room that was a true manifestation of who they are. In almost every instance it was the persons bedroom.&nbsp;</p>','photos/100/Jessica_Portraits_100825_1_pss.jpg',664,8,1000,12,'jpg','2010-09-02 17:05:25',1),(103,'2010-09-02 20:04:15','','photos/103/Jessica_Portraits_100825_1-2.jpg',664,9,1000,12,'jpg','2010-09-02 20:04:15',1),(104,'2010-09-02 20:05:26','','photos/104/Jessica_Portraits_100825_1-4_pss.jpg',1506,10,1000,12,'jpg','2010-09-02 20:05:26',2),(107,'2010-09-02 20:08:07','','photos/107/Jessica_Portraits_100825_1-7_pss.jpg',664,11,1000,12,'jpg','2010-09-02 20:08:07',1),(110,'2010-09-09 22:35:15','','photos/110/Casey_ShowGirl_1-14_portfoliosite.jpg',1506,1,1000,10,'jpg','2010-09-09 22:35:15',2),(109,'2010-09-09 21:47:41','','photos/109/Casey_ShowGirl_1-2_portfoliosite.jpg',1506,2,1000,10,'jpg','2010-09-09 21:47:41',2),(108,'2010-09-09 21:35:49','','photos/108/Casey_ShowGirl_1_portfoliosite.jpg',1506,3,1000,10,'jpg','2010-09-09 21:35:49',2),(111,'2010-09-09 22:37:10','','photos/111/Casey_ShowGirl_1-13_portfoliosite.jpg',1506,4,1000,10,'jpg','2010-09-09 22:37:10',2),(112,'2010-09-16 17:22:21','','photos/112/Bradley_Portrait_1-8_portfolio.jpg',1506,6,1000,12,'jpg','2010-09-16 17:22:21',2),(113,'2010-09-16 17:23:20','','photos/113/Bradley_Portrait_1-5_portfolio.jpg',664,7,1000,12,'jpg','2010-09-16 17:23:20',1),(115,'2010-09-16 19:11:39','','photos/115/DSC07733_portfolio.jpg',1333,2,1000,7,'jpg','2010-09-16 19:11:39',2),(116,'2010-09-16 19:14:30','','photos/116/DSC07692_portfolio.jpg',1333,3,1000,7,'jpg','2010-09-16 19:14:30',2),(117,'2010-09-16 19:17:52','','photos/117/DSC07061_portfolio.jpg',1333,4,1000,7,'jpg','2010-09-16 19:17:52',2),(118,'2010-09-16 20:58:35','','photos/118/DSC_3691.jpg',664,5,1000,10,'jpg','2010-09-16 20:58:35',1),(119,'2010-09-16 20:59:04','','photos/119/DSC_3823.jpg',1506,6,1000,10,'jpg','2010-09-16 20:59:04',2),(120,'2010-09-16 20:59:46','','photos/120/DSC_3948.jpg',664,7,1000,10,'jpg','2010-09-16 20:59:46',1),(121,'2010-09-16 21:00:41','','photos/121/DSC_3991.jpg',1506,8,1000,10,'jpg','2010-09-16 21:00:41',2),(122,'2010-09-16 21:01:08','','photos/122/DSC_4011.jpg',664,9,1000,10,'jpg','2010-09-16 21:01:08',1),(123,'2010-09-16 21:01:55','','photos/123/DSC_3879.jpg',1506,10,1000,10,'jpg','2010-09-16 21:01:55',2),(124,'2010-09-16 21:02:20','','photos/124/DSC_3904.jpg',664,11,1000,10,'jpg','2010-09-16 21:02:20',1),(134,'2010-09-21 22:12:45','','photos/134/DSC_6261_portfolio.jpg',664,NULL,1000,7,'jpg','2010-09-21 22:12:45',1),(135,'2010-09-21 22:13:10','','photos/135/DSC_6284_portfolio.jpg',664,NULL,1000,7,'jpg','2010-09-21 22:13:10',1),(133,'2010-09-21 22:12:18','','photos/133/DSC_6184_portfolio.jpg',664,NULL,1000,7,'jpg','2010-09-21 22:12:18',1),(136,'2010-09-21 22:13:54','','photos/136/DSC_1354_portfolio.jpg',664,NULL,1000,7,'jpg','2010-09-21 22:13:54',1),(137,NULL,'',NULL,NULL,NULL,NULL,7,NULL,'2010-09-21 22:14:18',1);
/*!40000 ALTER TABLE `photos` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2010-09-21 22:16:26
