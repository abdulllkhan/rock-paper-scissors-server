-- MySQL dump 10.13  Distrib 8.3.0, for macos14.2 (arm64)
--
-- Host: localhost    Database: stone
-- ------------------------------------------------------
-- Server version	8.3.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `databasechangelog`
--

DROP TABLE IF EXISTS `databasechangelog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `databasechangelog` (
  `ID` varchar(255) NOT NULL,
  `AUTHOR` varchar(255) NOT NULL,
  `FILENAME` varchar(255) NOT NULL,
  `DATEEXECUTED` datetime NOT NULL,
  `ORDEREXECUTED` int NOT NULL,
  `EXECTYPE` varchar(10) NOT NULL,
  `MD5SUM` varchar(35) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `COMMENTS` varchar(255) DEFAULT NULL,
  `TAG` varchar(255) DEFAULT NULL,
  `LIQUIBASE` varchar(20) DEFAULT NULL,
  `CONTEXTS` varchar(255) DEFAULT NULL,
  `LABELS` varchar(255) DEFAULT NULL,
  `DEPLOYMENT_ID` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `databasechangelog`
--

LOCK TABLES `databasechangelog` WRITE;
/*!40000 ALTER TABLE `databasechangelog` DISABLE KEYS */;
/*!40000 ALTER TABLE `databasechangelog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `databasechangeloglock`
--

DROP TABLE IF EXISTS `databasechangeloglock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `databasechangeloglock` (
  `ID` int NOT NULL,
  `LOCKED` tinyint(1) NOT NULL,
  `LOCKGRANTED` datetime DEFAULT NULL,
  `LOCKEDBY` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `databasechangeloglock`
--

LOCK TABLES `databasechangeloglock` WRITE;
/*!40000 ALTER TABLE `databasechangeloglock` DISABLE KEYS */;
INSERT INTO `databasechangeloglock` VALUES (1,0,NULL,NULL);
/*!40000 ALTER TABLE `databasechangeloglock` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `game`
--

DROP TABLE IF EXISTS `game`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `game` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `session_id` int NOT NULL,
  `round_id` int DEFAULT NULL,
  `winner_id` int DEFAULT NULL,
  `is_played` tinyint(1) NOT NULL DEFAULT '0',
  `user_id` int DEFAULT NULL,
  `user_choice` varchar(16) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `session_id` (`session_id`),
  KEY `winner_id` (`winner_id`),
  CONSTRAINT `game_ibfk_1` FOREIGN KEY (`session_id`) REFERENCES `rooms` (`id`),
  CONSTRAINT `game_ibfk_2` FOREIGN KEY (`winner_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `game`
--

LOCK TABLES `game` WRITE;
/*!40000 ALTER TABLE `game` DISABLE KEYS */;
INSERT INTO `game` VALUES (6,14,1,NULL,0,10,'Rock'),(7,14,1,NULL,0,9,'Rock'),(8,14,2,NULL,0,10,'Rock'),(9,14,2,NULL,0,9,'Rock'),(10,14,3,NULL,0,10,'Rock'),(11,14,3,NULL,0,9,'Paper');
/*!40000 ALTER TABLE `game` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `messages`
--

DROP TABLE IF EXISTS `messages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `messages` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `message` varchar(512) DEFAULT NULL,
  `session_code` varchar(255) DEFAULT NULL,
  `created_at` bigint DEFAULT NULL,
  `is_fetched` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `fk_messages_username` (`username`),
  KEY `fk_messages_session_code` (`session_code`),
  CONSTRAINT `fk_messages_session_code` FOREIGN KEY (`session_code`) REFERENCES `rooms` (`session_code`),
  CONSTRAINT `fk_messages_username` FOREIGN KEY (`username`) REFERENCES `users` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `messages`
--

LOCK TABLES `messages` WRITE;
/*!40000 ALTER TABLE `messages` DISABLE KEYS */;
INSERT INTO `messages` VALUES (1,'siyan','ksdafsdfjs','NHUCT50RfA',NULL,0),(2,'siyan','ksdafsdfjs','NHUCT50RfA',NULL,0),(3,'siyan','ksdafsdfjs','NHUCT50RfA',NULL,0),(4,'siyan','ksdafsdfjs','NHUCT50RfA',NULL,0),(5,'siyan','ksdafsdfjs','NHUCT50RfA',1714362359063,0),(6,'abdul','ksdafsdfjs','RqsoqrKtPO',1714365239461,1),(7,'abdul','ksdafsdfjs','RqsoqrKtPO',1714365240757,1),(8,'siyan','ksdafsdfjs','RqsoqrKtPO',1714365246079,0),(9,'siyan','ksdafsdsdfdsfjs','RqsoqrKtPO',1714365249050,0);
/*!40000 ALTER TABLE `messages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `password`
--

DROP TABLE IF EXISTS `password`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `password` (
  `id` int NOT NULL AUTO_INCREMENT,
  `userId` int DEFAULT NULL,
  `digest` varchar(255) DEFAULT NULL,
  `salt` varchar(255) DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `userId` (`userId`),
  KEY `FKqp5403kox4hg3jhjqifd7dfpj` (`user_id`),
  CONSTRAINT `FKqp5403kox4hg3jhjqifd7dfpj` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `password_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `password`
--

LOCK TABLES `password` WRITE;
/*!40000 ALTER TABLE `password` DISABLE KEYS */;
/*!40000 ALTER TABLE `password` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rooms`
--

DROP TABLE IF EXISTS `rooms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rooms` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user1_id` int DEFAULT NULL,
  `user2_id` int DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT '1',
  `is_vacant` tinyint(1) DEFAULT '1',
  `session_code` varchar(20) DEFAULT NULL,
  `winner_id` int DEFAULT NULL,
  `user1_score` int DEFAULT '0',
  `user2_score` int DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_session_code` (`session_code`),
  KEY `user1_id` (`user1_id`),
  KEY `user2_id` (`user2_id`),
  KEY `fk_winner_id` (`winner_id`),
  CONSTRAINT `fk_winner_id` FOREIGN KEY (`winner_id`) REFERENCES `users` (`id`),
  CONSTRAINT `rooms_ibfk_1` FOREIGN KEY (`user1_id`) REFERENCES `users` (`id`),
  CONSTRAINT `rooms_ibfk_2` FOREIGN KEY (`user2_id`) REFERENCES `users` (`id`),
  CONSTRAINT `rooms_chk_1` CHECK ((`user1_id` <> `user2_id`))
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rooms`
--

LOCK TABLES `rooms` WRITE;
/*!40000 ALTER TABLE `rooms` DISABLE KEYS */;
INSERT INTO `rooms` VALUES (8,10,9,0,0,'E0KChwqGd5',NULL,0,0),(9,10,NULL,0,1,'9pGz3gL7Zt',NULL,0,0),(10,10,NULL,0,1,'51RTaWeX26',NULL,0,0),(11,10,NULL,0,1,'RDZ6TFrIGq',NULL,0,0),(12,10,NULL,0,1,'NHUCT50RfA',NULL,0,0),(13,10,9,0,0,'RqsoqrKtPO',NULL,0,0),(14,10,9,1,0,'Q2YeDYA09S',9,0,0);
/*!40000 ALTER TABLE `rooms` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `created_at` bigint DEFAULT NULL,
  `digest` varchar(512) NOT NULL,
  `salt` varchar(512) NOT NULL,
  `score` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (3,'sjndkndsgsdfdnsa',1713606487554,'','',0),(7,'sjndkndsgsdfdnsa1',1713607780969,'','',1),(9,'siyan',1714114032948,'0572d1df536585b67830b4b54da8b403d12214b8ebe3db1967a13020f7052b99','ZJHhNv5QGvmh9h8HSbUO07saCblFKlGl',0),(10,'abdul',1714185742956,'db7e1076014156207f30b7c76a0a347c546350b45b4f9f0db28ec7597a1bf43c','bxSZLQVkHLdRLRceYJmLC6uCiWt6rgQB',0),(11,'abdul1',1714193410394,'7cef4962e1fa7a3c75da1e6c268565c39a3c6286335ac3f6a7e781304bbdd336','F67XVLlVnHqSdoJ7EkNecL5tf0fRjYqH',0),(12,'srinath',1714345144263,'6bbd6f770b83163b2529269a72fb391d2bf636d4dcbbae529b147239e338d6b3','K00Cl93gQOrUkfThrpSaieAKjNxGw9MU',0);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-05-01  1:53:11