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
-- Server version	5.5.29

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
INSERT INTO `administrator` VALUES (5780,0,'angeles@gmail.es','https://static.nova.bg/public/pics/anysize/1473611687.png','Ángeles',NULL,NULL,'Hidalgo Perea ',5762);
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
INSERT INTO `answer` VALUES (5903,0,2.5,1,'This is answer 1.',5880),(5904,0,1.75,2,'This is answer 2.',5881),(5905,0,4,3,'This is answer 3.',5882),(5906,0,2,1,'This is answer 1.',5880),(5907,0,1,2,'This is answer 2.',5881),(5908,0,1,3,'This is answer 3.',5882),(5909,0,0,1,NULL,5880),(5910,0,1.25,2,'This is answer 2.',5881),(5911,0,2,3,'This is answer 3.',5882),(5912,0,NULL,1,'This is answer 1.',5884),(5913,0,NULL,2,'This is answer 2.',5885);
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
INSERT INTO `application` VALUES (5893,0,701,'MasterCard',7,19,'Juan Arias Pérez','370821082394306','2018-04-16 21:01:00',NULL,'ACCEPTED','16042018-21:01-3-009-cxyt',5781,5879),(5894,0,761,'MasterCard',8,19,'Iván','343090425255004','2018-04-19 14:45:00',NULL,'ACCEPTED','19042018-14:45-3-008-porw',5782,5879),(5895,0,712,'AmericanExpress',4,21,'Fátima','343731491569451','2018-04-20 15:31:00','This is motivation letter','ACCEPTED','20042018-15:31-3-007-hjqa',5783,5879),(5896,0,692,'AmericanExpress',6,22,'Rafael','375202218580040','2018-05-03 19:25:00',NULL,'ACCEPTED','03052018-19:25-5-001-mxwo',5784,5883),(5897,0,100,'FICTICIA',1,10,'FICTICIA','0000000000000000','2018-05-04 10:38:00',NULL,'REJECTED','04052018-10:38-5-001-kwps',5782,5883),(5898,0,113,'AmericanExpress',1,20,'Francisco','340897080824954','2018-05-05 17:39:00','This is motivation letter','ACCEPTED','05052018-17:39-5-000-yhql',5785,5883),(5899,0,100,'FICTICIA',1,10,'FICTICIA','0000000000000000','2018-05-04 23:58:00',NULL,'DUE','04052018-23:58-2-002-jwxi',5781,5886),(5900,0,113,'AmericanExpress',1,20,'Francisco','340897080824954','2018-05-07 11:57:00',NULL,'ACCEPTED','07052018-11:57-2-001-jwxi',5785,5886),(5901,0,100,'FICTICIA',1,10,'FICTICIA','0000000000000000','2018-05-08 12:04:00',NULL,'DUE','08052018-12:04-2-001-rolv',5784,5886),(5902,0,100,'FICTICIA',1,10,'FICTICIA','0000000000000000','2018-05-10 15:28:00',NULL,'PENDING','10052018-15:28-2-001-aoka',5782,5886);
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
INSERT INTO `apprentice` VALUES (5781,0,'francisco@gmail.es','https://as01.epimg.net/img/comunes/fotos/fichas/deportistas/n/nol/large/19080.png','Francisco',NULL,'Sevilla, C/ Guadalimar 9','Rivero Rodríguez',5763,65),(5782,0,'francisco@gmail.es','https://as01.epimg.net/img/comunes/fotos/fichas/deportistas/e/esc/large/19617.png','Antonio','941623956',NULL,'Sáez Romero',5764,40),(5783,0,'joseangel@gmail.es','https://as01.epimg.net/img/comunes/fotos/fichas/deportistas/f/fra/large/19892.png','José Ángel','643900511',NULL,'Garrido Cordero',5765,15),(5784,0,'oliva@gmail.es','https://static.nova.bg/public/pics/anysize/1473609279.png','Oliva','945122729',NULL,'Rodríguez Gutiérrez',5766,40),(5785,0,'edu7@gmail.es','https://as01.epimg.net/img/comunes/fotos/fichas/deportistas/i/ill/large/19124.png','Eduardo','934122622',NULL,'Ferrer Albelda',5767,20);
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
INSERT INTO `contest` VALUES (5879,0,7,'This is Contest 1',3,'2018-04-28 18:20:00','\0',50,600,200,'2018-04-28 16:00:00','Contest 1',5818,5789,5786),(5883,0,0,'This is Contest 2',5,'2018-05-11 16:00:00','\0',80,1500,500,'2018-05-11 12:00:00','Contest 2',5819,5793,5787),(5886,0,1,'This is Contest 3',2,'2018-08-01 18:50:00','\0',30,750,50,'2018-08-01 17:20:00','Contest 3',5824,5790,5786),(5891,0,10,'This is Contest 4',5,'2018-08-31 22:00:00','',90,2000,1000,'2018-08-31 15:00:00','Contest 4',5823,5789,5788);
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
INSERT INTO `contest_expert` VALUES (5879,5789),(5879,5791),(5879,5793),(5883,5789),(5883,5791),(5886,5790),(5886,5793),(5891,5790),(5891,5793),(5891,5794);
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
INSERT INTO `contest_rules` VALUES (5879,'Rule 1'),(5879,'Rule 2'),(5879,'Rule 3'),(5879,'Rule 4'),(5883,'Rule 1'),(5883,'Rule 2'),(5883,'Rule 3'),(5883,'Rule 4'),(5883,'Rule 5'),(5883,'Rule 6'),(5886,'Rule 1'),(5886,'Rule 2'),(5886,'Rule 3'),(5891,'Rule 1');
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
INSERT INTO `discipline` VALUES (5809,0,'Java'),(5810,0,'Python'),(5811,0,'C++'),(5812,0,'C#'),(5813,0,'MySQL'),(5814,0,'Oracle'),(5815,0,'Javascript'),(5816,0,'jQuery'),(5817,0,'UML'),(5818,0,'HTML'),(5819,0,'CSS'),(5820,0,'JSTL'),(5821,0,'Spring'),(5822,0,'Hibernate'),(5823,0,'PHP'),(5824,0,'Android');
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
INSERT INTO `expert` VALUES (5789,0,'adrian7@gmail.es','https://as01.epimg.net/img/comunes/fotos/fichas/deportistas/d/dav/large/24307.png','Adrián',NULL,NULL,'López Postigo',5771),(5790,0,'victor@gmail.es','https://as01.epimg.net/img/comunes/fotos/fichas/deportistas/w/wil/large/24667.png','Víctor',NULL,NULL,'Hurtado Pérez',5772),(5791,0,'manuel@gmail.es','https://as01.epimg.net/img/comunes/fotos/fichas/deportistas/a/agi/large/15255.png','Manuel','812614943','C/ Castillo de Alcalá de Guadaíra 9, 3A','Pellicer Catalán',5773),(5792,0,'leonardo@gmail.es','https://as01.epimg.net/img/comunes/fotos/fichas/deportistas/r/rul/large/25626.png','Leonardo','943136843',NULL,'García Sanjuán',5774),(5793,0,'beatriz5@gmail.es','http://video.novatv.bg/novatv/public/pics/anysize/1439585574.png','Beatriz','610317117',NULL,'Campuzano Díaz',5775),(5794,0,'pabloFS@gmail.es','https://as01.epimg.net/img/comunes/fotos/fichas/deportistas/b/bal/large/17294.png','Pablo Antonio','912303445',NULL,'Fernández Sánchez',5776);
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
INSERT INTO `expert_discipline` VALUES (5789,5814),(5789,5818),(5789,5819),(5789,5823),(5790,5809),(5790,5811),(5790,5812),(5790,5815),(5790,5816),(5790,5820),(5790,5823),(5790,5824),(5791,5810),(5791,5813),(5791,5817),(5791,5818),(5791,5819),(5791,5821),(5791,5822),(5792,5809),(5792,5814),(5792,5817),(5792,5821),(5792,5822),(5793,5809),(5793,5812),(5793,5813),(5793,5818),(5793,5819),(5793,5823),(5793,5824),(5794,5809),(5794,5816),(5794,5820),(5794,5823);
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
INSERT INTO `expert_research` VALUES (5789,5799),(5789,5800),(5789,5801),(5790,5798),(5790,5801),(5791,5799),(5792,5798),(5792,5799),(5792,5800),(5793,5799),(5794,5798);
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
INSERT INTO `forum` VALUES (5825,1,'Introduce yourselves to the SoftwareHouse community','http://www3.gobiernodecanarias.org/medusa/edublog/ceipisabellacatolica/wp-content/uploads/sites/176/2017/11/welcome-png-transparent.png','New members',5,5832),(5833,1,'Important news will be posted here regularly','https://www.mabonline.net/wp-content/uploads/2018/02/NEWS.png','SoftwareHouse News',4,5838),(5839,1,'Ask your questions related to JAVA here','https://www.seeklogo.net/wp-content/uploads/2011/06/java-logo-vector.png','JAVA Chat',17,5861),(5862,0,'Ask your questions related to Python here','https://upload.wikimedia.org/wikipedia/commons/thumb/0/0a/Python.svg/2000px-Python.svg.png','Python Chat',0,NULL),(5863,0,'Ask your questions related to C++ here','https://upload.wikimedia.org/wikipedia/commons/thumb/1/18/ISO_C%2B%2B_Logo.svg/200px-ISO_C%2B%2B_Logo.svg.png','C++ Chat',0,NULL),(5864,1,'Ask your questions related to C# here','https://seeklogo.com/images/C/csharp-logo-58C6C6F67A-seeklogo.com.png','C# Chat',2,5867),(5868,0,'Ask your questions related to MySQL here','https://www.seeklogo.net/wp-content/uploads/2017/05/mysql-logo.png','MySQL Chat',0,NULL),(5869,0,'Ask your questions related to Javascript here','https://seeklogo.com/images/J/javascript-logo-E967E87D74-seeklogo.com.png','Javascript Chat',0,NULL),(5870,1,'Ask your questions related to PHP here','https://upload.wikimedia.org/wikipedia/commons/thumb/2/27/PHP-logo.svg/1024px-PHP-logo.svg.png','PHP Chat',5,5876),(5877,0,'SoftwareHouse-related discussions. Including research and management','https://upload.wikimedia.org/wikipedia/commons/thumb/9/93/Talk_%28Google%29.svg/400px-Talk_%28Google%29.svg.png','General talk',0,NULL);
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
INSERT INTO `investment` VALUES (5802,0,2000,199,'AmericanExpress',7,18,'Javier','377036052811469',5795,5798),(5803,0,4500,981,'MasterCard',12,21,'Irina','5469701091420048',5797,5798),(5804,0,3000,199,'AmericanExpress',7,18,'Javier','377036052811469',5795,5799),(5805,0,9000,144,'MasterCard',6,19,'Raúl','5133115538023099',5796,5799),(5806,0,6000,981,'MasterCard',12,21,'Irina','5469701091420048',5797,5799),(5807,0,1500,981,'MasterCard',12,21,'Irina','5469701091420048',5797,5800),(5808,0,2100,199,'AmericanExpress',7,18,'Javier','377036052811469',5795,5801);
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
INSERT INTO `investor` VALUES (5795,0,'mario88FS@gmail.es','https://as01.epimg.net/img/comunes/fotos/fichas/deportistas/s/sol/large/19134.png','Mario de Jesús','844129043',NULL,'Pérez Jiménez',5777),(5796,0,'miguel7@gmail.es','https://as01.epimg.net/img/comunes/fotos/fichas/deportistas/l/lek/large/30771.png','Miguel Angel',NULL,NULL,'Gutiérrez Naranjo',5778),(5797,0,'mjhd0@gmail.es','https://static.nova.bg/public/pics/anysize/1473613024.png','María José',NULL,NULL,'Hidalgo Doblado',5779);
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
INSERT INTO `manager` VALUES (5786,0,'pilar@gmail.es','http://video.novatv.bg/novatv/public/pics/anysize/1439584671.png','Pilar','912333510',NULL,'León Alonso',5768),(5787,0,'mercedes@gmail.es','http://free-webcambg.com/working/Big-Brother-2016-BG/images/vip/part-Irina-Tencheva-Hristova.png','Mercedes',NULL,NULL,'Oria Segura',5769),(5788,0,'escardiel@gmail.es','http://video.novatv.bg/novatv/public/pics/anysize/1439585426.png','Ana',NULL,NULL,'Cabanillas Mora',5770);
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
INSERT INTO `post` VALUES (5827,0,'\0','\0','\0',0,'2017-11-20 10:12:00','Hello. Welcome to our community.',NULL,5826,5826,5781),(5828,0,'\0','\0','\0',0,'2017-11-20 13:42:00','Welcome!',NULL,5826,5826,5782),(5829,0,'\0','\0','\0',1,'2017-11-21 18:12:00','Welcome Oliva. I am a PHP developer too and I am willing to help you with whatever you need.',NULL,5826,5826,5783),(5830,0,'\0','\0','\0',0,'2017-11-21 19:56:00','Thank you!!!',5829,NULL,5826,5784),(5832,0,'\0','\0','\0',0,'2017-12-30 23:10:00','Hi and welcome to the SoftwareHouse Community.',NULL,5831,5831,5781),(5835,0,'\0','\0','\0',1,'2018-05-06 19:04:00','What will be the durarion of the contest?',NULL,5834,5834,5781),(5836,0,'\0','\0','\0',0,'2018-05-06 20:41:00','The duration will be aproximately 90  minutes.',5835,NULL,5834,5786),(5837,0,'\0','\0','\0',1,'2018-05-08 14:27:00','What prize will be given to the winner?',NULL,5834,5834,5782),(5838,0,'\0','\0','\0',0,'2018-05-08 19:32:00','750.00 euros.',5837,NULL,5834,5786),(5841,0,'\0','\0','\0',0,'2017-12-01 09:26:00','Hashcode is a unique code which is generated by the JVM for every object creation..we use hashcode to perform some operation on hashing related algorithm like hashtable,hashmap etc..the advantages of hashcode is it makes searching operation easy bcoz when we search for an object that unique code helps to find out that obj. But we can\'t say hashcode is the address of an obj. It z a unique code generated by JVM for every object..that\'s y now the day hashing algorithm z most popular search algorithm',NULL,5840,5840,5781),(5842,0,'\0','\0','',3,'2017-12-03 12:37:00','hashCode() is used for bucketing in Hash implementations like HashMap, HashTable, HashSet, etc. The value received from hashCode() is used as the bucket number for storing elements of the set/map. This bucket number is the address of the element inside the set/map. When you do contains() it will take the hash code of the element, then look for the bucket where hash code points to. If more than 1 element is found in the same bucket (multiple objects can have the same hash code), then it uses the equals() method to evaluate if the objects are equal, and then decide if contains() is true or false, or decide if element could be added in the set or not.',NULL,5840,5840,5794),(5843,0,'\0','\0','\0',2,'2017-12-03 19:59:00','So what if there is only one hash code matched element found, it returns true directly? but since multiple objects can have the same hash code so it has to run equals() to evaluate if the matched element is equal, else it may give you the unexpected result, am I right?',5842,NULL,5840,5782),(5844,0,'\0','\0','',1,'2017-12-07 14:21:00','Buckets aside, hashcode is a method that the object calls to determine the order to store each object in memory. If objects are equal, then their hashcode must also be equal.',5843,NULL,5840,5794),(5845,0,'\0','\0','\0',0,'2017-12-08 22:11:00','Ok. Thanks.',5844,NULL,5840,5782),(5846,0,'','\0','',0,'2017-12-20 15:04:00','A hashcode() is a function that takes an object and outputs a numeric value. The hashcode for an object is always the same if the object doesn\'t change. Functions like HashMap, HashTable, HashSet, etc that need to store objects, will use a hashcode modulo the size of their internal array to choose in what \'memory position\' (i.e. array position) to store the object. There are some cases where collisions may occur (two objects end up with the same hashcode, and that, of course, need to be solved carefully).',NULL,5840,5840,5793),(5847,0,'\0','\0','\0',4,'2018-02-02 16:20:00','Hello. Everyone. I have a question related to this topic. Can I use the hashcode method to test object equality in a certain class?',NULL,5840,5840,5784),(5848,0,'\0','\0','\0',2,'2018-02-06 11:52:00','The hashCode method for a given class can be used to test for object inequality, but NOT object equality, for that class. The hashCode method is used by the java.util.HashSet collection class to group the elements within that set into hash buckets for swift retrieval.',5847,NULL,5840,5782),(5849,0,'\0','\0','\0',0,'2018-02-07 12:36:00','Thanks.',5848,NULL,5840,5784),(5850,0,'\0','\0','',0,'2018-02-12 18:18:00','I suggest either correcting or deleting this answer.',5848,NULL,5840,5794),(5851,0,'\0','\0','',0,'2018-02-12 18:34:00','No, you can\'t. Two unequal objects can have the same hash code, so using hash codes to test for inequality will give false positives. ',5847,NULL,5840,5794),(5852,0,'\0','\0','\0',0,'2018-02-15 13:41:00','Ok. Thank you so much. Now I understand it.',NULL,5840,5840,5784),(5854,0,'\0','\0','\0',0,'2018-02-04 20:09:00','Mutable objects have fields that can be changed, immutable objects have no fields that can be changed after the object is created. A very simple immutable object is a object without any field. (For example a simple Comparator Implementation).',NULL,5853,5853,5794),(5855,0,'\0','\0','\0',1,'2018-04-10 13:08:00','They are not different from the point of view of JVM. Immutable objects don\'t have methods that can change the instance variables. And the instance variables are private; therefore you can\'t change it after you create it. A famous example would be String. You don\'t have methods like setString, or setCharAt. And s1 = s1 + \'w\' will create a new string, with the original one abandoned. That\'s my understanding.',NULL,5853,5853,5785),(5856,0,'\0','\0','\0',0,'2018-04-12 10:29:00','... and all instance variables are private! (otherwise -> mutable)',5855,NULL,5853,5790),(5857,0,'\0','','\0',0,'2018-04-12 18:50:00','DELETEDBYUSER',5856,NULL,5853,5785),(5859,0,'','\0','\0',0,'2018-03-15 22:23:00','You are \'resetting\' a previously initialized Random instance and causing an NPE to be thrown on the first attempted method call using that instance.',NULL,5858,5858,5781),(5861,0,'\0','\0','',0,'2018-04-24 18:25:00','LinkedList allows for constant-time insertions or removals using iterators, but only sequential access of elements. In other words, you can walk the list forwards or backwards, but finding a position in the list takes time proportional to the size of the list. Javadoc says \'operations that index into the list will traverse the list from the beginning or the end, whichever is closer\', so those methods are O(n) (n/4 steps) on average, though O(1) for index = 0. ArrayList, on the other hand, allow fast random read access, so you can grab any element in constant time. But adding or removing from anywhere but the end requires shifting all the latter elements over, either to make an opening or fill the gap. Also, if you add more elements than the capacity of the underlying array, a new array (1.5 times the size) is allocated, and the old array is copied to the new one, so adding to an ArrayList is O(n) in the worst case but constant on average.',NULL,5860,5860,5792),(5866,0,'','\0','\0',1,'2018-01-20 11:28:00','Hello, Eduardo. Here\'s an example:',NULL,5865,5865,5791),(5867,0,'\0','\0','\0',0,'2018-01-20 18:31:00','I\'d pay for such help! :-) Thank you.',5866,NULL,5865,5785),(5872,0,'\0','\0','\0',3,'2018-03-02 20:00:00','I\'d recommend using PDO (PHP Data Objects) to run parameterized SQL queries. Not only does this protect against SQL injection, it also speeds up queries.',NULL,5871,5871,5782),(5873,0,'\0','\0','\0',2,'2018-03-02 20:19:00','doesn\'t PDO wrap MySQLi for MySQL DBs? In which case surely it can\'t be any quicker than MySQLi.',5872,NULL,5871,5783),(5874,0,'\0','\0','\0',1,'2018-03-02 23:38:00','Using parameterized queries is what speeds up the queries. Technically mysqli might be even faster by a very small margin. The actual amount of time the server takes to respond the the query eclipses any difference in timing that might happen because you are using a wrapper. But mysqli is tied to the database. If you want to use a different database engine, you have to change all the calls that use mysqli. Not so for PDO.',5873,NULL,5871,5782),(5875,0,'\0','\0','\0',0,'2018-03-02 23:59:00','Ok. Thanks.',5874,NULL,5871,5783),(5876,0,'','\0','',0,'2018-03-04 20:35:00','Use prepared statements and parameterized queries. These are SQL statements that are sent to and parsed by the database server separately from any parameters. This way it is impossible for an attacker to inject malicious SQL. You basically have two options to achieve this: 1) Using PDO (for any supported database driver). 2) Using MySQLi (for MySQL). I\'d say that PDO is the universal option.',NULL,5871,5871,5790);
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
INSERT INTO `post_linksattachments` VALUES (5866,'http://www.tutorialsteacher.com/Content/images/csharp/csharp-class.png');
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
INSERT INTO `problem` VALUES (5880,0,'https://www.e-reading.club/illustrations/1010/1010507-_4.jpg',2.5,1,'This is problem 1',5879),(5881,0,'http://media.indiedb.com/images/articles/1/187/186772/work1.png',2.5,2,'This is problem 2',5879),(5882,0,'http://boredomtherapy.com/wp-content/uploads/2017/04/logic-puzzles-1.png',5,3,'This is problem 3',5879),(5884,0,NULL,7.5,1,'This is problem 1',5883),(5885,0,'https://i2.wp.com/headsup.boyslife.org/files/2016/05/p6ytm2ocql7odpgondow.gif',2.5,2,'This is problem 2',5883),(5887,0,NULL,2,1,'This is problem 1',5886),(5888,0,NULL,2,2,'This is problem 2',5886),(5889,0,NULL,2,3,'This is problem 3',5886),(5890,0,NULL,4,4,'This is problem 4',5886),(5892,0,NULL,5,1,'This is problem 1',5891);
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
INSERT INTO `research` VALUES (5798,0,6500,'This is Research 1','2018-05-04','\0',1200,'https://investigacion.us.es/sisius/sis_proyecto.php?idproy=16037','2018-03-27','Arquitectura para la eficiencia energética y sostenibilidad en entornos residenciales','Research 1'),(5799,0,18000,'This is Research 2',NULL,'\0',3000,'https://investigacion.us.es/sisius/sis_proyecto.php?idproy=24407','2018-04-13','Healthy and Efficient Routes in Massive Open-Data Based Smart Cities-Citizen','Research 2'),(5800,0,1500,'This is Research 3',NULL,'\0',4000,'https://investigacion.us.es/sisius/sis_proyecto.php?idproy=28418',NULL,'Vision and Crowdsensing Technology for an Optimal Response in Physical-Security','Research 3'),(5801,0,2100,'This is Research 4',NULL,'',2000,'https://investigacion.us.es/sisius/sis_proyecto.php?idproy=20734','2018-04-14','La Formación a Través de Dispositivos Móviles. Diseño y Evaluación de Contenidos y Actividades Formativas a Través de M-Learning ','Research 4');
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
INSERT INTO `solution` VALUES (5914,0,8.25,5893),(5915,0,4,5894),(5916,0,3.25,5895),(5917,0,NULL,5896);
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
INSERT INTO `solution_answer` VALUES (5914,5903),(5914,5904),(5914,5905),(5915,5906),(5915,5907),(5915,5908),(5916,5909),(5916,5910),(5916,5911),(5917,5912),(5917,5913);
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
INSERT INTO `thread` VALUES (5826,1,'','2017-11-21 19:56:00',4,'2017-11-19 21:46:00','My name is Oliva. I am new to this forum. I am a novice PHP deleloper. I am here to learn new things.','Hello everyone!',5825,5830,5784),(5831,1,'','2017-12-30 23:10:00',1,'2017-12-27 14:18:00','Hello. I am a novice JAVA developer. I have just signed up as an APPRENTICE in SoftwareHouse. I\'m currently studying at ETSII, Seville. I have many questions related to JAVA programming and I will need help.','Greetings from Seville!',5825,5832,5785),(5834,1,'\0','2018-05-08 19:32:00',4,'2018-05-01 16:00:00','Hello! I\'d like to inform you that a new contest for Android developers will take place on 1st of August, 2018 at 17:20. You need to have at least 50 points in order to participate. If you don\'t have 50 points, you will have to pay 30.00 euros. If you want to participate in the contest, send your application as soon as possible. It\'s highly recommended that you send us a motivation letter.','New contest available',5833,5838,5786),(5840,1,'\0','2018-02-15 13:41:00',12,'2017-11-29 06:41:00','In Java obj.hashcode() returns some value. What is the use of this hash code in programming?','What is the use of hashcode in Java?',5839,5852,5782),(5853,1,'','2018-04-12 19:11:00',3,'2018-01-31 09:54:00','Anyone, please, explain me the difference between Mutable objects and Immutable objects with example.','Difference between Mutable objects and Immutable objects',5839,5857,5782),(5858,1,'\0','2018-03-15 22:23:00',1,'2018-03-12 07:43:00','I\'m working on a project for a programming class and after attempting to run this program I\'m getting NullPointerExceptions on the four lines listed in the code here. I know a NullPointerException comes from trying to access something that\'s null, but I\'m not sure of how to fix it.','How to fix this NullPointerException?',5839,5859,5784),(5860,1,'','2018-04-24 18:25:00',1,'2018-04-19 16:25:00','When should LinkedList be used over ArrayList and vice-versa?','When to use LinkedList over ArrayList?',5839,5861,5781),(5865,1,'\0','2018-01-20 18:31:00',2,'2018-01-18 10:02:00','This is basically my first attempt to understand the classes in C#. I\'ve went through several tutorials on the internet, but the thing I\'m missing the most and what I haven\'t found anywhere yet, is a simple good example.','C# classes - basic example',5864,5867,5785),(5871,1,'\0','2018-03-04 20:35:00',5,'2018-03-02 17:08:00','If user input is inserted without modification into an SQL query, then the application becomes vulnerable to SQL injection. What can be done to prevent this from happening?','How can I prevent SQL injection in PHP?',5870,5876,5783),(5878,0,'\0','2018-05-10 17:04:00',0,'2018-05-10 17:04:00','Hello, everyone. Is there any project that needs funding?','Funding offer',5877,NULL,5796);
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
INSERT INTO `thread_discipline` VALUES (5834,5824),(5840,5809),(5853,5809),(5853,5810),(5853,5816),(5858,5809),(5865,5812),(5871,5823);
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
INSERT INTO `thread_linksattachments` VALUES (5858,'https://i.stack.imgur.com/nWCRz.png');
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
INSERT INTO `useraccount` VALUES (5762,0,'21232f297a57a5a743894a0e4a801fc3','admin'),(5763,0,'5009a3df8ff30a4d01c1d36b75bbdd3e','apprentice1'),(5764,0,'0f708b1c89b256e4981cad0a90a3dbf3','apprentice2'),(5765,0,'a1fba0d7679865783d7402afdeae4f50','apprentice3'),(5766,0,'19a5fcc2c4f5bafa1f7f630b5a0129d5','apprentice4'),(5767,0,'ed499dcf8f72df6cdefd6c32fdc5498a','apprentice5'),(5768,0,'c240642ddef994358c96da82c0361a58','manager1'),(5769,0,'8df5127cd164b5bc2d2b78410a7eea0c','manager2'),(5770,0,'2d3a5db4a2a9717b43698520a8de57d0','manager3'),(5771,0,'136ec678bb71fd8a732b445b76451c45','expert1'),(5772,0,'b311cf25c64d6a363377e81bcbdd4148','expert2'),(5773,0,'9be8837621dc8a83b607658bcbc9ae3e','expert3'),(5774,0,'cf2aa988506b7ce5024e75a98dcffd1e','expert4'),(5775,0,'b332a67d37064ca95e28b626e23fa6eb','expert5'),(5776,0,'ac432f2a8b3e41ed76899e05c54ac8ee','expert6'),(5777,0,'23152e35710ddbb79ccdc7b36502e849','investor1'),(5778,0,'aad4a13e9dd1348158da63ce62982140','investor2'),(5779,0,'ffd0fff96eef59f261298fd88e874424','investor3');
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
INSERT INTO `useraccount_authorities` VALUES (5762,'ADMIN'),(5763,'APPRENTICE'),(5764,'APPRENTICE'),(5765,'APPRENTICE'),(5766,'APPRENTICE'),(5767,'APPRENTICE'),(5768,'MANAGER'),(5769,'MANAGER'),(5770,'MANAGER'),(5771,'EXPERT'),(5772,'EXPERT'),(5773,'EXPERT'),(5774,'EXPERT'),(5775,'EXPERT'),(5776,'EXPERT'),(5777,'INVESTOR'),(5778,'INVESTOR'),(5779,'INVESTOR');
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

-- Dump completed on 2018-06-08 20:27:46


commit;
