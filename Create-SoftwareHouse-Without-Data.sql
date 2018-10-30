start transaction;

create database `SoftwareHouse`;

use `SoftwareHouse`;

create user 'acme-user'@'%' identified by password '*4F10007AADA9EE3DBB2CC36575DFC6F4FDE27577';

create user 'acme-manager'@'%' identified by password '*FDB8CD304EB2317D10C95D797A4BD7492560F55F';

grant select, insert, update, delete 
	on `SoftwareHouse`.* to 'acme-user'@'%';

grant select, insert, update, delete, create, drop, references, index, alter, 
        create temporary tables, lock tables, create view, create routine, 
        alter routine, execute, trigger, show view
    on `SoftwareHouse`.* to 'acme-manager'@'%';



-- MySQL dump 10.13  Distrib 5.5.29, for Win64 (x86)
--
-- Host: localhost    Database: SoftwareHouse
-- ------------------------------------------------------
-- Server version	5.5.29-log

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
-- Table structure for table `administrator`
--

DROP TABLE IF EXISTS `administrator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `administrator` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `linkPhoto` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `postalAddress` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `userAccount_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_idt4b4u259p6vs4pyr9lax4eg` (`userAccount_id`),
  CONSTRAINT `FK_idt4b4u259p6vs4pyr9lax4eg` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `administrator`
--

LOCK TABLES `administrator` WRITE;
/*!40000 ALTER TABLE `administrator` DISABLE KEYS */;
INSERT INTO `administrator` VALUES (30,0,'angeles@gmail.es','https://static.nova.bg/public/pics/anysize/1473611687.png','Ángeles',NULL,NULL,'Hidalgo Perea ',29);
/*!40000 ALTER TABLE `administrator` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `answer`
--

DROP TABLE IF EXISTS `answer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `answer` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `mark` double DEFAULT NULL,
  `number` int(11) NOT NULL,
  `text` varchar(255) DEFAULT NULL,
  `problem_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_rae03pu0los1id9f91p6oh41t` (`problem_id`),
  CONSTRAINT `FK_rae03pu0los1id9f91p6oh41t` FOREIGN KEY (`problem_id`) REFERENCES `problem` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `answer`
--

LOCK TABLES `answer` WRITE;
/*!40000 ALTER TABLE `answer` DISABLE KEYS */;
/*!40000 ALTER TABLE `answer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `application`
--

DROP TABLE IF EXISTS `application`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `application` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `CVV` int(11) NOT NULL,
  `brandName` varchar(255) DEFAULT NULL,
  `expirationMonth` int(11) NOT NULL,
  `expirationYear` int(11) NOT NULL,
  `holderName` varchar(255) DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  `moment` datetime DEFAULT NULL,
  `motivationLetter` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `ticker` varchar(255) DEFAULT NULL,
  `applicant_id` int(11) NOT NULL,
  `contest_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `UK_ixyjfwd2sw5x6iih1qr8x1612` (`status`),
  KEY `FK_5ho5khuj5b6xfls5l7h5969xo` (`applicant_id`),
  KEY `FK_h3r17e96jfrfmd8557ki4p08q` (`contest_id`),
  CONSTRAINT `FK_h3r17e96jfrfmd8557ki4p08q` FOREIGN KEY (`contest_id`) REFERENCES `contest` (`id`),
  CONSTRAINT `FK_5ho5khuj5b6xfls5l7h5969xo` FOREIGN KEY (`applicant_id`) REFERENCES `apprentice` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `application`
--

LOCK TABLES `application` WRITE;
/*!40000 ALTER TABLE `application` DISABLE KEYS */;
/*!40000 ALTER TABLE `application` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `apprentice`
--

DROP TABLE IF EXISTS `apprentice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `apprentice` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `linkPhoto` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `postalAddress` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `userAccount_id` int(11) NOT NULL,
  `points` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_hpg7apk6gvvrq5o4xw3h0st43` (`userAccount_id`),
  CONSTRAINT `FK_hpg7apk6gvvrq5o4xw3h0st43` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `apprentice`
--

LOCK TABLES `apprentice` WRITE;
/*!40000 ALTER TABLE `apprentice` DISABLE KEYS */;
/*!40000 ALTER TABLE `apprentice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contest`
--

DROP TABLE IF EXISTS `contest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contest` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `availablePlaces` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `difficultyGrade` int(11) NOT NULL,
  `endMoment` datetime DEFAULT NULL,
  `isDraft` bit(1) NOT NULL,
  `price` double NOT NULL,
  `prize` double NOT NULL,
  `requiredPoints` int(11) NOT NULL,
  `startMoment` datetime DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `discipline_id` int(11) NOT NULL,
  `editor_id` int(11) NOT NULL,
  `manager_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `UK_lyy4dt6bhslmob2thjw2jflsu` (`startMoment`,`endMoment`,`isDraft`),
  KEY `FK_kbt5s4kie6jca93nxubnoh7xg` (`discipline_id`),
  KEY `FK_k4wxq4vy0enu9df6qetnbyfe8` (`editor_id`),
  KEY `FK_2bmomyjq3uxge0tbr98wx8195` (`manager_id`),
  CONSTRAINT `FK_2bmomyjq3uxge0tbr98wx8195` FOREIGN KEY (`manager_id`) REFERENCES `manager` (`id`),
  CONSTRAINT `FK_k4wxq4vy0enu9df6qetnbyfe8` FOREIGN KEY (`editor_id`) REFERENCES `expert` (`id`),
  CONSTRAINT `FK_kbt5s4kie6jca93nxubnoh7xg` FOREIGN KEY (`discipline_id`) REFERENCES `discipline` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contest`
--

LOCK TABLES `contest` WRITE;
/*!40000 ALTER TABLE `contest` DISABLE KEYS */;
/*!40000 ALTER TABLE `contest` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contest_expert`
--

DROP TABLE IF EXISTS `contest_expert`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contest_expert` (
  `contestsForEvaluation_id` int(11) NOT NULL,
  `judges_id` int(11) NOT NULL,
  KEY `FK_1pw5q9aruwmrb4d985738kqk3` (`judges_id`),
  KEY `FK_1s4ydy7p8cm2ks0svuny138li` (`contestsForEvaluation_id`),
  CONSTRAINT `FK_1s4ydy7p8cm2ks0svuny138li` FOREIGN KEY (`contestsForEvaluation_id`) REFERENCES `contest` (`id`),
  CONSTRAINT `FK_1pw5q9aruwmrb4d985738kqk3` FOREIGN KEY (`judges_id`) REFERENCES `expert` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contest_expert`
--

LOCK TABLES `contest_expert` WRITE;
/*!40000 ALTER TABLE `contest_expert` DISABLE KEYS */;
/*!40000 ALTER TABLE `contest_expert` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contest_rules`
--

DROP TABLE IF EXISTS `contest_rules`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contest_rules` (
  `Contest_id` int(11) NOT NULL,
  `rules` varchar(255) DEFAULT NULL,
  KEY `FK_9aswtqk5x1d91wby8v0c4a0e0` (`Contest_id`),
  CONSTRAINT `FK_9aswtqk5x1d91wby8v0c4a0e0` FOREIGN KEY (`Contest_id`) REFERENCES `contest` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contest_rules`
--

LOCK TABLES `contest_rules` WRITE;
/*!40000 ALTER TABLE `contest_rules` DISABLE KEYS */;
/*!40000 ALTER TABLE `contest_rules` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `discipline`
--

DROP TABLE IF EXISTS `discipline`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `discipline` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `discipline`
--

LOCK TABLES `discipline` WRITE;
/*!40000 ALTER TABLE `discipline` DISABLE KEYS */;
INSERT INTO `discipline` VALUES (31,0,'Java'),(32,0,'Python'),(33,0,'C++'),(34,0,'C#'),(35,0,'MySQL'),(36,0,'Oracle'),(37,0,'Javascript'),(38,0,'jQuery'),(39,0,'UML'),(40,0,'HTML'),(41,0,'CSS'),(42,0,'JSTL'),(43,0,'Spring'),(44,0,'Hibernate'),(45,0,'PHP'),(46,0,'Android');
/*!40000 ALTER TABLE `discipline` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `expert`
--

DROP TABLE IF EXISTS `expert`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `expert` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `linkPhoto` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `postalAddress` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `userAccount_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_dh1gl1i03mlpsp1e04b7xhnjm` (`userAccount_id`),
  CONSTRAINT `FK_dh1gl1i03mlpsp1e04b7xhnjm` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `expert`
--

LOCK TABLES `expert` WRITE;
/*!40000 ALTER TABLE `expert` DISABLE KEYS */;
/*!40000 ALTER TABLE `expert` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `expert_discipline`
--

DROP TABLE IF EXISTS `expert_discipline`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `expert_discipline` (
  `Expert_id` int(11) NOT NULL,
  `disciplines_id` int(11) NOT NULL,
  KEY `FK_ga4dx68qhrwgungmyc9jdy71w` (`disciplines_id`),
  KEY `FK_p6yeei1v5rb9qp59ragaj4fjk` (`Expert_id`),
  CONSTRAINT `FK_p6yeei1v5rb9qp59ragaj4fjk` FOREIGN KEY (`Expert_id`) REFERENCES `expert` (`id`),
  CONSTRAINT `FK_ga4dx68qhrwgungmyc9jdy71w` FOREIGN KEY (`disciplines_id`) REFERENCES `discipline` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `expert_discipline`
--

LOCK TABLES `expert_discipline` WRITE;
/*!40000 ALTER TABLE `expert_discipline` DISABLE KEYS */;
/*!40000 ALTER TABLE `expert_discipline` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `expert_research`
--

DROP TABLE IF EXISTS `expert_research`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `expert_research` (
  `team_id` int(11) NOT NULL,
  `researches_id` int(11) NOT NULL,
  KEY `FK_4fpkck5amdy7q9fo4c4g8g8sf` (`researches_id`),
  KEY `FK_ojxvw2sdnpbax9ykvt2ase4a9` (`team_id`),
  CONSTRAINT `FK_ojxvw2sdnpbax9ykvt2ase4a9` FOREIGN KEY (`team_id`) REFERENCES `expert` (`id`),
  CONSTRAINT `FK_4fpkck5amdy7q9fo4c4g8g8sf` FOREIGN KEY (`researches_id`) REFERENCES `research` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `expert_research`
--

LOCK TABLES `expert_research` WRITE;
/*!40000 ALTER TABLE `expert_research` DISABLE KEYS */;
/*!40000 ALTER TABLE `expert_research` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `forum`
--

DROP TABLE IF EXISTS `forum`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `forum` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `linkPicture` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `numPosts` int(11) NOT NULL,
  `lastPost_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_gootup5p8m3siy4ojl0nlq38x` (`lastPost_id`),
  CONSTRAINT `FK_gootup5p8m3siy4ojl0nlq38x` FOREIGN KEY (`lastPost_id`) REFERENCES `post` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `forum`
--

LOCK TABLES `forum` WRITE;
/*!40000 ALTER TABLE `forum` DISABLE KEYS */;
INSERT INTO `forum` VALUES (47,0,'Introduce yourselves to the SoftwareHouse community','http://www3.gobiernodecanarias.org/medusa/edublog/ceipisabellacatolica/wp-content/uploads/sites/176/2017/11/welcome-png-transparent.png','New members',0,NULL),(48,0,'Important news will be posted here regularly','https://www.mabonline.net/wp-content/uploads/2018/02/NEWS.png','SoftwareHouse News',0,NULL),(49,0,'Ask your questions related to JAVA here','https://www.seeklogo.net/wp-content/uploads/2011/06/java-logo-vector.png','JAVA Chat',0,NULL),(50,0,'Ask your questions related to Python here','https://upload.wikimedia.org/wikipedia/commons/thumb/0/0a/Python.svg/2000px-Python.svg.png','Python Chat',0,NULL),(51,0,'Ask your questions related to C++ here','https://upload.wikimedia.org/wikipedia/commons/thumb/1/18/ISO_C%2B%2B_Logo.svg/200px-ISO_C%2B%2B_Logo.svg.png','C++ Chat',0,NULL),(52,0,'Ask your questions related to C# here','https://seeklogo.com/images/C/csharp-logo-58C6C6F67A-seeklogo.com.png','C# Chat',0,NULL),(53,0,'Ask your questions related to MySQL here','https://www.seeklogo.net/wp-content/uploads/2017/05/mysql-logo.png','MySQL Chat',0,NULL),(54,0,'Ask your questions related to Javascript here','https://seeklogo.com/images/J/javascript-logo-E967E87D74-seeklogo.com.png','Javascript Chat',0,NULL),(55,0,'Ask your questions related to PHP here','https://upload.wikimedia.org/wikipedia/commons/thumb/2/27/PHP-logo.svg/1024px-PHP-logo.svg.png','PHP Chat',0,NULL),(56,0,'SoftwareHouse-related discussions. Including research and management','https://upload.wikimedia.org/wikipedia/commons/thumb/9/93/Talk_%28Google%29.svg/400px-Talk_%28Google%29.svg.png','General talk',0,NULL);
/*!40000 ALTER TABLE `forum` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequences`
--

DROP TABLE IF EXISTS `hibernate_sequences`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hibernate_sequences` (
  `sequence_name` varchar(255) DEFAULT NULL,
  `sequence_next_hi_value` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequences`
--

LOCK TABLES `hibernate_sequences` WRITE;
/*!40000 ALTER TABLE `hibernate_sequences` DISABLE KEYS */;
INSERT INTO `hibernate_sequences` VALUES ('DomainEntity',1);
/*!40000 ALTER TABLE `hibernate_sequences` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `investment`
--

DROP TABLE IF EXISTS `investment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `investment` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `amount` double NOT NULL,
  `CVV` int(11) NOT NULL,
  `brandName` varchar(255) DEFAULT NULL,
  `expirationMonth` int(11) NOT NULL,
  `expirationYear` int(11) NOT NULL,
  `holderName` varchar(255) DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  `investor_id` int(11) NOT NULL,
  `research_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `UK_th8kjgp5bxqu08dwgn2afrf8o` (`amount`),
  KEY `FK_eos14oyryx0gis8ajwr7e3i9r` (`investor_id`),
  KEY `FK_alligq8qvweehh9b21juejn9r` (`research_id`),
  CONSTRAINT `FK_alligq8qvweehh9b21juejn9r` FOREIGN KEY (`research_id`) REFERENCES `research` (`id`),
  CONSTRAINT `FK_eos14oyryx0gis8ajwr7e3i9r` FOREIGN KEY (`investor_id`) REFERENCES `investor` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `investment`
--

LOCK TABLES `investment` WRITE;
/*!40000 ALTER TABLE `investment` DISABLE KEYS */;
/*!40000 ALTER TABLE `investment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `investor`
--

DROP TABLE IF EXISTS `investor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `investor` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `linkPhoto` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `postalAddress` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `userAccount_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_65nttpnp3t2uoqco1069h1t44` (`userAccount_id`),
  CONSTRAINT `FK_65nttpnp3t2uoqco1069h1t44` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `investor`
--

LOCK TABLES `investor` WRITE;
/*!40000 ALTER TABLE `investor` DISABLE KEYS */;
/*!40000 ALTER TABLE `investor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `manager`
--

DROP TABLE IF EXISTS `manager`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `manager` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `linkPhoto` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `postalAddress` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `userAccount_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_84bmmxlq61tiaoc7dy7kdcghh` (`userAccount_id`),
  CONSTRAINT `FK_84bmmxlq61tiaoc7dy7kdcghh` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `manager`
--

LOCK TABLES `manager` WRITE;
/*!40000 ALTER TABLE `manager` DISABLE KEYS */;
/*!40000 ALTER TABLE `manager` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post`
--

DROP TABLE IF EXISTS `post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `post` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `isBestAnswer` bit(1) NOT NULL,
  `isDeleted` bit(1) NOT NULL,
  `isReliable` bit(1) NOT NULL,
  `numPosts` int(11) NOT NULL,
  `publicationMoment` datetime DEFAULT NULL,
  `text` varchar(1000) DEFAULT NULL,
  `parentPost_id` int(11) DEFAULT NULL,
  `thread_id` int(11) DEFAULT NULL,
  `topic_id` int(11) NOT NULL,
  `writer_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `UK_sadqbifhq6xooip3ctqd2i5mq` (`publicationMoment`,`isDeleted`),
  KEY `FK_1j6te6nklxy5lx3cg4lwy0jwo` (`parentPost_id`),
  KEY `FK_er9cfwk2catx1myu9oem49ra8` (`thread_id`),
  KEY `FK_7v5uuskeuw1rjg7vcajsdxpfk` (`topic_id`),
  CONSTRAINT `FK_7v5uuskeuw1rjg7vcajsdxpfk` FOREIGN KEY (`topic_id`) REFERENCES `thread` (`id`),
  CONSTRAINT `FK_1j6te6nklxy5lx3cg4lwy0jwo` FOREIGN KEY (`parentPost_id`) REFERENCES `post` (`id`),
  CONSTRAINT `FK_er9cfwk2catx1myu9oem49ra8` FOREIGN KEY (`thread_id`) REFERENCES `thread` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post`
--

LOCK TABLES `post` WRITE;
/*!40000 ALTER TABLE `post` DISABLE KEYS */;
/*!40000 ALTER TABLE `post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post_linksattachments`
--

DROP TABLE IF EXISTS `post_linksattachments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `post_linksattachments` (
  `Post_id` int(11) NOT NULL,
  `linksAttachments` varchar(255) DEFAULT NULL,
  KEY `FK_58xbe18n4lrxrxqbtm2yd11c` (`Post_id`),
  CONSTRAINT `FK_58xbe18n4lrxrxqbtm2yd11c` FOREIGN KEY (`Post_id`) REFERENCES `post` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post_linksattachments`
--

LOCK TABLES `post_linksattachments` WRITE;
/*!40000 ALTER TABLE `post_linksattachments` DISABLE KEYS */;
/*!40000 ALTER TABLE `post_linksattachments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `problem`
--

DROP TABLE IF EXISTS `problem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `problem` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `linkPicture` varchar(255) DEFAULT NULL,
  `mark` double NOT NULL,
  `number` int(11) NOT NULL,
  `statement` varchar(255) DEFAULT NULL,
  `contest_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `UK_pgtdp9wti6nsuleeqn670tied` (`number`),
  KEY `FK_5m3f6j8ylwopr8nlsblr96slg` (`contest_id`),
  CONSTRAINT `FK_5m3f6j8ylwopr8nlsblr96slg` FOREIGN KEY (`contest_id`) REFERENCES `contest` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `problem`
--

LOCK TABLES `problem` WRITE;
/*!40000 ALTER TABLE `problem` DISABLE KEYS */;
/*!40000 ALTER TABLE `problem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `research`
--

DROP TABLE IF EXISTS `research`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `research` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `budget` double NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `endDate` date DEFAULT NULL,
  `isCancelled` bit(1) NOT NULL,
  `minCost` double NOT NULL,
  `projectWebpage` varchar(255) DEFAULT NULL,
  `startDate` date DEFAULT NULL,
  `subject` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `UK_kq9e2hxuj6646460i0jgsttuu` (`endDate`,`minCost`,`budget`,`isCancelled`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `research`
--

LOCK TABLES `research` WRITE;
/*!40000 ALTER TABLE `research` DISABLE KEYS */;
/*!40000 ALTER TABLE `research` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `solution`
--

DROP TABLE IF EXISTS `solution`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `solution` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `mark` double DEFAULT NULL,
  `application_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_a1yh6likknvedbgraaboqabm7` (`application_id`),
  KEY `UK_697yignjix4j1u6btr0qvhlbl` (`mark`),
  CONSTRAINT `FK_a1yh6likknvedbgraaboqabm7` FOREIGN KEY (`application_id`) REFERENCES `application` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `solution`
--

LOCK TABLES `solution` WRITE;
/*!40000 ALTER TABLE `solution` DISABLE KEYS */;
/*!40000 ALTER TABLE `solution` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `solution_answer`
--

DROP TABLE IF EXISTS `solution_answer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `solution_answer` (
  `Solution_id` int(11) NOT NULL,
  `answers_id` int(11) NOT NULL,
  UNIQUE KEY `UK_m8uxpdvul24mru90eo7fmcdcd` (`answers_id`),
  KEY `FK_925tdlng5njsv38msflg8v6r1` (`Solution_id`),
  CONSTRAINT `FK_925tdlng5njsv38msflg8v6r1` FOREIGN KEY (`Solution_id`) REFERENCES `solution` (`id`),
  CONSTRAINT `FK_m8uxpdvul24mru90eo7fmcdcd` FOREIGN KEY (`answers_id`) REFERENCES `answer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `solution_answer`
--

LOCK TABLES `solution_answer` WRITE;
/*!40000 ALTER TABLE `solution_answer` DISABLE KEYS */;
/*!40000 ALTER TABLE `solution_answer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `thread`
--

DROP TABLE IF EXISTS `thread`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `thread` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `isBestAnswerEnabled` bit(1) NOT NULL,
  `momentLastModification` datetime DEFAULT NULL,
  `numPosts` int(11) NOT NULL,
  `startMoment` datetime DEFAULT NULL,
  `text` varchar(1000) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `forum_id` int(11) NOT NULL,
  `lastPost_id` int(11) DEFAULT NULL,
  `writer_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `UK_mk0nyjepac6p30ijxmc8lm5w7` (`momentLastModification`,`numPosts`),
  KEY `FK_aupvcnjwygpq94q2lk39y5ymm` (`forum_id`),
  KEY `FK_p67pb71yqumv76l7ansymgohr` (`lastPost_id`),
  CONSTRAINT `FK_p67pb71yqumv76l7ansymgohr` FOREIGN KEY (`lastPost_id`) REFERENCES `post` (`id`),
  CONSTRAINT `FK_aupvcnjwygpq94q2lk39y5ymm` FOREIGN KEY (`forum_id`) REFERENCES `forum` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `thread`
--

LOCK TABLES `thread` WRITE;
/*!40000 ALTER TABLE `thread` DISABLE KEYS */;
/*!40000 ALTER TABLE `thread` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `thread_discipline`
--

DROP TABLE IF EXISTS `thread_discipline`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `thread_discipline` (
  `Thread_id` int(11) NOT NULL,
  `disciplines_id` int(11) NOT NULL,
  KEY `FK_a7phvlop8484a7wu4dey1gnkp` (`disciplines_id`),
  KEY `FK_7eg576d7tje9jii4xutih25sc` (`Thread_id`),
  CONSTRAINT `FK_7eg576d7tje9jii4xutih25sc` FOREIGN KEY (`Thread_id`) REFERENCES `thread` (`id`),
  CONSTRAINT `FK_a7phvlop8484a7wu4dey1gnkp` FOREIGN KEY (`disciplines_id`) REFERENCES `discipline` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `thread_discipline`
--

LOCK TABLES `thread_discipline` WRITE;
/*!40000 ALTER TABLE `thread_discipline` DISABLE KEYS */;
/*!40000 ALTER TABLE `thread_discipline` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `thread_linksattachments`
--

DROP TABLE IF EXISTS `thread_linksattachments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `thread_linksattachments` (
  `Thread_id` int(11) NOT NULL,
  `linksAttachments` varchar(255) DEFAULT NULL,
  KEY `FK_nwejr5tngd9a6aen4k61j2qt1` (`Thread_id`),
  CONSTRAINT `FK_nwejr5tngd9a6aen4k61j2qt1` FOREIGN KEY (`Thread_id`) REFERENCES `thread` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `thread_linksattachments`
--

LOCK TABLES `thread_linksattachments` WRITE;
/*!40000 ALTER TABLE `thread_linksattachments` DISABLE KEYS */;
/*!40000 ALTER TABLE `thread_linksattachments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `useraccount`
--

DROP TABLE IF EXISTS `useraccount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `useraccount` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_csivo9yqa08nrbkog71ycilh5` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `useraccount`
--

LOCK TABLES `useraccount` WRITE;
/*!40000 ALTER TABLE `useraccount` DISABLE KEYS */;
INSERT INTO `useraccount` VALUES (29,0,'21232f297a57a5a743894a0e4a801fc3','admin');
/*!40000 ALTER TABLE `useraccount` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `useraccount_authorities`
--

DROP TABLE IF EXISTS `useraccount_authorities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `useraccount_authorities` (
  `UserAccount_id` int(11) NOT NULL,
  `authority` varchar(255) DEFAULT NULL,
  KEY `FK_b63ua47r0u1m7ccc9lte2ui4r` (`UserAccount_id`),
  CONSTRAINT `FK_b63ua47r0u1m7ccc9lte2ui4r` FOREIGN KEY (`UserAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `useraccount_authorities`
--

LOCK TABLES `useraccount_authorities` WRITE;
/*!40000 ALTER TABLE `useraccount_authorities` DISABLE KEYS */;
INSERT INTO `useraccount_authorities` VALUES (29,'ADMIN');
/*!40000 ALTER TABLE `useraccount_authorities` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-06-08 20:02:44

commit;
