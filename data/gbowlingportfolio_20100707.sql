-- MySQL dump 10.13  Distrib 5.1.48, for apple-darwin10.3.0 (i386)
--
-- Host: localhost    Database: gillianbowling
-- ------------------------------------------------------
-- Server version	5.1.48-log

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
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `categories` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `code` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `FK4D47461CCEF0B888` (`parent_id`)
) ENGINE=MyISAM AUTO_INCREMENT=17 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (1,'Photography',NULL,''),(8,'IL Faut Soufrir Pour Etre Bele',3,'collaboration.etrebele'),(3,'Collaboration',NULL,NULL),(4,'Vintage Inspired',6,'collections.vintageinspired'),(5,'about',NULL,NULL),(6,'Collections',NULL,'collections'),(7,'Birds of a Feather',6,'collections.birdsofafeather'),(9,'Angeldustrial',3,'collaboration.angeldustrial'),(10,'Fashion',1,'photography.fashion'),(11,'Events',1,'photography.events'),(12,'Food',1,'photography.food'),(13,'Beauty',1,'photography.beauty'),(14,'Modern Classic',6,'collections.modernclassic'),(15,'Knitwear',6,'collections.knitwear');
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `configuration_property`
--

DROP TABLE IF EXISTS `configuration_property`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `configuration_property` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(100) NOT NULL,
  `default_value` varchar(255) DEFAULT NULL,
  `type` varchar(100) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  `version` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  UNIQUE KEY `code` (`code`),
  UNIQUE KEY `code_2` (`code`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `configuration_property`
--

LOCK TABLES `configuration_property` WRITE;
/*!40000 ALTER TABLE `configuration_property` DISABLE KEYS */;
INSERT INTO `configuration_property` VALUES (1,'media.dir','/Users/tea/dev/ApproachingPi/gillianbowling.com/images\r\n','String','/Users/tea/dev/ApproachingPi/gillianbowling.com/images',0),(2,'media.cache.dir','/Users/tea/dev/ApproachingPi/gillianbowling.com/images_cache','String','/Users/tea/dev/ApproachingPi/gillianbowling.com/images_cache',0);
/*!40000 ALTER TABLE `configuration_property` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `photos`
--

DROP TABLE IF EXISTS `photos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `photos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date_modified` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `file_name` varchar(100) DEFAULT NULL,
  `height` int(11) DEFAULT NULL,
  `rank` int(11) DEFAULT NULL,
  `width` int(11) DEFAULT NULL,
  `category_id` int(11) NOT NULL,
  `file_extension` varchar(5) DEFAULT NULL,
  `date_created` datetime DEFAULT NULL,
  `orientation` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `FKC50C8881AF32B3B4` (`category_id`)
) ENGINE=MyISAM AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `photos`
--

LOCK TABLES `photos` WRITE;
/*!40000 ALTER TABLE `photos` DISABLE KEYS */;
INSERT INTO `photos` VALUES (1,'2010-06-06 23:22:52','','photos/1/DSC_2794.jpg',425,NULL,640,4,'jpg',NULL,NULL),(2,'2010-06-06 23:23:08','','photos/2/DSC_2802.jpg',640,NULL,416,4,'jpg',NULL,NULL),(3,'2010-06-06 23:23:26','','photos/3/DSC_2833.jpg',640,NULL,416,4,'jpg',NULL,NULL),(4,'2010-06-06 23:23:42','','photos/4/DSC_2889.jpg',425,NULL,640,4,'jpg',NULL,NULL),(5,'2010-06-06 23:31:13','','photos/5/DSC_3009.jpg',425,NULL,640,4,'jpg',NULL,NULL),(6,'2010-06-06 23:31:40','','photos/6/DSC_2879.jpg',425,NULL,640,4,'jpg',NULL,NULL),(7,'2010-06-06 23:32:07','','photos/7/DSC_2816.jpg',425,NULL,640,4,'jpg',NULL,NULL),(8,'2010-06-06 23:32:55','','photos/8/DSC_2798.jpg',640,NULL,416,4,'jpg','2010-06-06 22:06:30',NULL);
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

-- Dump completed on 2010-07-07  1:50:08
