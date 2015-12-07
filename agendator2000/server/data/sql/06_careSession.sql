-- MySQL dump 10.13  Distrib 5.6.26, for osx10.11 (x86_64)
--
-- Host: localhost    Database: agendator
-- ------------------------------------------------------
-- Server version	5.6.26

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
-- Table structure for table `careSession`
--

DROP TABLE IF EXISTS `careSession`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `careSession` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `endDate` datetime NOT NULL,
  `location` varchar(255) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `startDate` datetime NOT NULL,
  `valid` tinyint(1) NOT NULL,
  `ong` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1E3A453EA24641` (`ong`),
  CONSTRAINT `FK1E3A453EA24641` FOREIGN KEY (`ong`) REFERENCES `ong` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `careSession`
--

LOCK TABLES `careSession` WRITE;
/*!40000 ALTER TABLE `careSession` DISABLE KEYS */;
INSERT INTO `careSession` VALUES (1,'2016-01-10 18:00:00','Junta de Vecinos Población Balmaceda','San Martín 4850, San Bernardo','2016-01-08 08:00:01',1,1),(2,'2016-01-17 19:00:00','Junta de Vecinos Villa La Portada Nº 2','Av. América 432, San Bernardo','2016-01-15 09:00:00',1,1),(3,'2016-01-22 18:00:00','Junta de Vecinos N 16 / Territorio 3','General Lizardo Montero 2284, Lo Prado','2016-01-20 09:00:00',1,1),(4,'2016-02-14 18:00:00','Escuela Andes del Sur','Av. Los Toros Nº 1600, Puente Alto','2016-02-12 09:00:00',1,1);
/*!40000 ALTER TABLE `careSession` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-12-07 10:30:02
