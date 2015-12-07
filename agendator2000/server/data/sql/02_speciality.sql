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
-- Table structure for table `speciality`
--

DROP TABLE IF EXISTS `speciality`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `speciality` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(40) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `speciality`
--

LOCK TABLES `speciality` WRITE;
/*!40000 ALTER TABLE `speciality` DISABLE KEYS */;
INSERT INTO `speciality` VALUES (1,'Broncopulmonar - Adulto'),(2,'Broncopulmonar - Infantil'),(3,'Cardiología - Adulto'),(4,'Cardiología - Infantil'),(5,'Cirugía - Cabeza Y Cuello'),(6,'Cirugía - Cardiovascular'),(7,'Cirugía - Digestiva'),(8,'Cirugía - General'),(9,'Cirugía - General Y Oncológica'),(10,'Cirugía - General Y Vascular Periférica'),(11,'Cirugía - Ginecológica Endoscópica'),(12,'Cirugía - Infantil'),(13,'Cirugía - Mamas'),(14,'Cirugía - Maxilofacial'),(15,'Cirugía - Plástica Adultos'),(16,'Cirugía - Plástica Infantil'),(17,'Cirugía - Tórax'),(18,'Cirugía - Vascular Periférica'),(19,'Coloproctología'),(20,'Dermatología - Adulto'),(21,'Diabetología'),(22,'Endocrinología - Adulto'),(23,'Endocrinología - Infantil'),(24,'Enfermerades Mamarias'),(25,'Fisiatría - Adulto'),(26,'Fisiatría - Infantil'),(27,'Fonoaudiología'),(28,'Gastroenterología - Adulto'),(29,'Gastroenterología - Infantil'),(30,'Genética'),(31,'Geriatría'),(32,'Ginecología - Adulto'),(33,'Ginecología - Infantil'),(34,'Ginecología Y Obstetricia'),(35,'Infectología - Adulto'),(36,'Infertilidad Conyugal Y Cirugía Endos'),(37,'Infertilidad Masculina Y Andrología'),(38,'Inmunología - Adulto'),(39,'Inmunología - Adulto Y Niños'),(40,'Inmunología - Infantil'),(41,'Medicina Del Dolor Y Paleativa'),(42,'Medicina Del Sueño'),(43,'Medicina Familiar'),(44,'Medicina Física Y Rehabilitación'),(45,'Medicina General'),(46,'Medicina General Del Niño'),(47,'Medicina Interna'),(48,'Medicina Interna - Flebología Linfología'),(49,'Medicina Interna - Infectología'),(50,'Medicina Interna Y Hematología'),(51,'Nefrología - Adulto'),(52,'Nefrología - Infantil'),(53,'Neurocirugía'),(54,'Neurología - Adulto'),(55,'Neurología - Infantil'),(56,'Nutrición Y Enf. Metabólicas - Adulto'),(57,'Nutrición Y Enf. Metabólicas - Infantil'),(58,'Nutricionista - Adulto'),(59,'Nutricionista - Infantil'),(60,'Odontología - General'),(61,'Odontología - Odontopediatría'),(62,'Odontología - Rehabilitación Oral'),(63,'Oftalmología - General'),(64,'Oftalmología - Glaucoma'),(65,'Oftalmología - Infantil'),(66,'Oftalmología - Ocuplastía'),(67,'Oftalmología - Polo Anterior'),(68,'Oftalmología - Retina'),(69,'Oncología'),(70,'Oncología - Ginecológica'),(71,'Oncología - Tumores Óseos'),(72,'Otorrinolaringología'),(73,'Pediatría'),(74,'Pediatría - Hematología Oncológica'),(75,'Pediatría - Infectología'),(76,'Pediatría - Inmunología Infantil'),(77,'Pediatría - Nefrología'),(78,'Pediatría - Reumatología Infantil'),(79,'Psicología - Adulto'),(80,'Psicología - Adulto-infantil'),(81,'Psicología - Infantil'),(82,'Psicopedagogía'),(83,'Psiquiatría - Adulto'),(84,'Psiquiatría - Infantil'),(85,'Reumatología - Adulto'),(86,'Reumatología - Infantil'),(87,'Terapia Ocupacional'),(88,'Traumatología - Cadera'),(89,'Traumatología - Codo'),(90,'Traumatología - Columna'),(91,'Traumatología - Hombro'),(92,'Traumatología - Mano Microcirugía'),(93,'Traumatología - Mano Muñeca'),(94,'Traumatología - Ortopedia'),(95,'Traumatología - Ortopedia Infantil'),(96,'Traumatología - Rodilla'),(97,'Traumatología - Tobillo -  Pie'),(98,'Urología - Adulto'),(99,'Urología - Infantil');
/*!40000 ALTER TABLE `speciality` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-12-07  8:59:59
