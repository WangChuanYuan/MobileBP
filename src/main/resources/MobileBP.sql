-- MySQL dump 10.13  Distrib 5.7.18, for linux-glibc2.5 (x86_64)
--
-- Host: localhost    Database: MobileBP
-- ------------------------------------------------------
-- Server version	5.7.18

/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE = @@TIME_ZONE */;
/*!40103 SET TIME_ZONE = '+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES = @@SQL_NOTES, SQL_NOTES = 0 */;

--
-- Table structure for table `Client`
--

DROP TABLE IF EXISTS `Client`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Client` (
  `phoneNo` VARCHAR(20) NOT NULL,
  `name`    VARCHAR(60) DEFAULT NULL,
  `remain`  DOUBLE      DEFAULT NULL,
  PRIMARY KEY (`phoneNo`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Client`
--

LOCK TABLES `Client` WRITE;
/*!40000 ALTER TABLE `Client`
  DISABLE KEYS */;
INSERT INTO `Client` VALUES ('123456789', 'wcy', 8280);
/*!40000 ALTER TABLE `Client`
  ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Operation`
--

DROP TABLE IF EXISTS `Operation`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Operation` (
  `oid`       BIGINT(20) NOT NULL AUTO_INCREMENT,
  `phoneNo`   VARCHAR(20)         DEFAULT NULL,
  `startTime` DATETIME            DEFAULT NULL,
  `endTime`   DATETIME            DEFAULT NULL,
  `useLen`    DOUBLE              DEFAULT NULL,
  `fee`       DOUBLE              DEFAULT NULL,
  `type`      VARCHAR(20)         DEFAULT NULL,
  PRIMARY KEY (`oid`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 51
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Operation`
--

LOCK TABLES `Operation` WRITE;
/*!40000 ALTER TABLE `Operation`
  DISABLE KEYS */;
INSERT INTO `Operation` VALUES (1, '123456789', '2018-10-28 11:33:39', '2018-10-28 11:40:39', 7, 0, 'CALL'),
  (2, '123456789', '2018-10-28 11:43:39', '2018-10-28 11:45:39', 2, 0, 'CALL'),
  (3, '123456789', '2018-10-28 11:48:39', '2018-10-28 11:54:39', 6, 0, 'CALL'),
  (4, '123456789', '2018-10-28 11:57:39', '2018-10-28 11:59:39', 2, 0, 'CALL'),
  (5, '123456789', '2018-10-28 12:02:39', '2018-10-28 12:05:39', 3, 0, 'CALL'),
  (6, '123456789', '2018-10-28 12:08:39', '2018-10-28 12:09:39', 1, 0, 'CALL'),
  (7, '123456789', '2018-10-28 12:12:39', '2018-10-28 12:18:39', 6, 0, 'CALL'),
  (8, '123456789', '2018-10-28 12:21:39', '2018-10-28 12:22:39', 1, 0, 'CALL'),
  (9, '123456789', '2018-10-28 12:25:39', '2018-10-28 12:27:39', 2, 0, 'CALL'),
  (10, '123456789', '2018-10-28 12:30:39', '2018-10-28 12:32:39', 2, 0, 'CALL'),
  (11, '123456789', '2018-10-28 12:35:39', '2018-10-28 12:43:39', 8, 0, 'CALL'),
  (12, '123456789', '2018-10-28 12:46:39', '2018-10-28 12:52:39', 6, 0, 'CALL'),
  (13, '123456789', '2018-10-28 12:55:39', '2018-10-28 12:59:39', 4, 0, 'CALL'),
  (14, '123456789', '2018-10-28 13:02:39', '2018-10-28 13:03:39', 1, 0, 'CALL'),
  (15, '123456789', '2018-10-28 13:06:39', '2018-10-28 13:11:39', 5, 0, 'CALL'),
  (16, '123456789', '2018-10-28 13:14:39', '2018-10-28 13:21:39', 7, 0, 'CALL'),
  (17, '123456789', '2018-10-28 13:24:39', '2018-10-28 13:25:39', 1, 0, 'CALL'),
  (18, '123456789', '2018-10-28 13:28:39', '2018-10-28 13:32:39', 4, 0, 'CALL'),
  (19, '123456789', '2018-10-28 13:35:39', '2018-10-28 13:39:39', 4, 0, 'CALL'),
  (20, '123456789', '2018-10-28 13:42:39', '2018-10-28 13:46:39', 4, 0, 'CALL'),
  (21, '123456789', '2018-10-28 13:49:39', '2018-10-28 13:55:39', 6, 0, 'CALL'),
  (22, '123456789', '2018-10-28 13:58:39', '2018-10-28 14:05:39', 7, 0, 'CALL'),
  (23, '123456789', '2018-10-28 14:08:39', '2018-10-28 14:09:39', 1, 0, 'CALL'),
  (24, '123456789', '2018-10-28 14:12:39', '2018-10-28 14:17:39', 5, 0, 'CALL'),
  (25, '123456789', '2018-10-28 14:20:39', '2018-10-28 14:25:39', 5, 0, 'CALL'),
  (26, '123456789', '2018-10-28 14:28:39', '2018-10-28 14:30:39', 2, 0, 'CALL'),
  (27, '123456789', '2018-10-28 14:33:39', '2018-10-28 14:34:39', 1, 0, 'CALL'),
  (28, '123456789', '2018-10-28 14:37:39', '2018-10-28 14:39:39', 2, 0, 'CALL'),
  (29, '123456789', '2018-10-28 14:42:39', '2018-10-28 14:48:39', 6, 0, 'CALL'),
  (30, '123456789', '2018-10-28 14:51:39', '2018-10-28 14:58:39', 7, 0, 'CALL'),
  (31, '123456789', '2018-10-28 12:01:06', '2018-10-28 12:01:06', 1, 0, 'MESSAGE'),
  (32, '123456789', '2018-10-28 12:19:06', '2018-10-28 12:19:06', 1, 0, 'MESSAGE'),
  (33, '123456789', '2018-10-28 12:35:06', '2018-10-28 12:35:06', 1, 0, 'MESSAGE'),
  (34, '123456789', '2018-10-28 13:21:06', '2018-10-28 13:21:06', 1, 0, 'MESSAGE'),
  (35, '123456789', '2018-10-28 13:49:06', '2018-10-28 13:49:06', 1, 0, 'MESSAGE'),
  (36, '123456789', '2018-10-28 14:20:06', '2018-10-28 14:20:06', 1, 0, 'MESSAGE'),
  (37, '123456789', '2018-10-28 14:29:06', '2018-10-28 14:29:06', 1, 0, 'MESSAGE'),
  (38, '123456789', '2018-10-28 15:14:06', '2018-10-28 15:14:06', 1, 0, 'MESSAGE'),
  (39, '123456789', '2018-10-28 15:52:06', '2018-10-28 15:52:06', 1, 0, 'MESSAGE'),
  (40, '123456789', '2018-10-28 16:03:06', '2018-10-28 16:03:06', 1, 0, 'MESSAGE'),
  (41, '123456789', '2018-10-28 11:40:51', '2018-10-28 11:57:51', 228, 0, 'LOCAL_DATA'),
  (42, '123456789', '2018-10-28 12:05:51', '2018-10-28 12:13:51', 179, 0, 'LOCAL_DATA'),
  (43, '123456789', '2018-10-28 12:21:51', '2018-10-28 12:23:51', 122, 0, 'LOCAL_DATA'),
  (44, '123456789', '2018-10-28 12:31:51', '2018-10-28 12:49:51', 109, 76, 'LOCAL_DATA'),
  (45, '123456789', '2018-10-28 12:57:51', '2018-10-28 13:04:51', 137, 274, 'LOCAL_DATA'),
  (46, '123456789', '2018-10-28 11:41:41', '2018-10-28 11:46:41', 126, 0, 'GEN_DATA'),
  (47, '123456789', '2018-10-28 11:56:41', '2018-10-28 12:15:41', 124, 0, 'GEN_DATA'),
  (48, '123456789', '2018-10-28 12:25:41', '2018-10-28 12:28:41', 121, 0, 'GEN_DATA'),
  (49, '123456789', '2018-10-28 12:38:41', '2018-10-28 12:52:41', 144, 575, 'GEN_DATA'),
  (50, '123456789', '2018-10-28 13:02:41', '2018-10-28 13:14:41', 140, 700, 'GEN_DATA');
/*!40000 ALTER TABLE `Operation`
  ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Order`
--

DROP TABLE IF EXISTS `Order`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Order` (
  `phoneNo` VARCHAR(20) NOT NULL,
  `pid`     BIGINT(20)  NOT NULL,
  `time`    DATETIME    DEFAULT NULL,
  `status`  VARCHAR(20) DEFAULT NULL,
  PRIMARY KEY (`phoneNo`, `pid`),
  KEY `pid` (`pid`),
  CONSTRAINT `Order_ibfk_1` FOREIGN KEY (`phoneNo`) REFERENCES `Client` (`phoneNo`)
    ON DELETE CASCADE,
  CONSTRAINT `Order_ibfk_2` FOREIGN KEY (`pid`) REFERENCES `Pack` (`pid`)
    ON DELETE CASCADE
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Order`
--

LOCK TABLES `Order` WRITE;
/*!40000 ALTER TABLE `Order`
  DISABLE KEYS */;
INSERT INTO `Order`
VALUES ('123456789', 2, '2018-10-28 11:27:21', 'ORDER'), ('123456789', 3, '2018-10-28 11:27:21', 'ORDER'),
  ('123456789', 4, '2018-10-28 11:27:22', 'ORDER'), ('123456789', 5, '2018-10-28 11:27:22', 'ORDER');
/*!40000 ALTER TABLE `Order`
  ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Pack`
--

DROP TABLE IF EXISTS `Pack`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Pack` (
  `pid`  BIGINT(20) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(60)         DEFAULT NULL,
  `fee`  DOUBLE              DEFAULT NULL,
  PRIMARY KEY (`pid`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 6
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Pack`
--

LOCK TABLES `Pack` WRITE;
/*!40000 ALTER TABLE `Pack`
  DISABLE KEYS */;
INSERT INTO `Pack` VALUES (1, '基础套餐', 20), (2, '豪华套餐', 20), (3, '通话套餐', 40), (4, '短信套餐', 5), (5, '流量套餐', 30);
/*!40000 ALTER TABLE `Pack`
  ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Plan`
--

DROP TABLE IF EXISTS `Plan`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Plan` (
  `freeLen` DOUBLE      DEFAULT NULL,
  `type`    VARCHAR(20) DEFAULT NULL,
  `pack_id` BIGINT(20)  DEFAULT NULL,
  KEY `pack_id` (`pack_id`),
  CONSTRAINT `Plan_ibfk_1` FOREIGN KEY (`pack_id`) REFERENCES `Pack` (`pid`)
    ON DELETE CASCADE
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Plan`
--

LOCK TABLES `Plan` WRITE;
/*!40000 ALTER TABLE `Plan`
  DISABLE KEYS */;
INSERT INTO `Plan`
VALUES (100, 'CALL', 1), (20, 'MESSAGE', 1), (100, 'LOCAL_DATA', 1), (100, 'GEN_DATA', 1), (20, 'CALL', 2),
  (10, 'MESSAGE', 2), (300, 'LOCAL_DATA', 2), (200, 'GEN_DATA', 2), (200, 'CALL', 3), (50, 'MESSAGE', 4),
  (300, 'LOCAL_DATA', 5), (200, 'GEN_DATA', 5);
/*!40000 ALTER TABLE `Plan`
  ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE = @OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE = @OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES = @OLD_SQL_NOTES */;

-- Dump completed on 2018-10-28 11:56:20
