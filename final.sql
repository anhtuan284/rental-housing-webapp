-- MySQL dump 10.13  Distrib 8.0.28, for macos11 (x86_64)
--
-- Host: localhost    Database: rentaldb
-- ------------------------------------------------------
-- Server version	8.0.28

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment` (
  `comment_id` int NOT NULL AUTO_INCREMENT,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `user_id` int NOT NULL,
  `post_id` int NOT NULL,
  `created_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `positive` tinyint DEFAULT NULL,
  PRIMARY KEY (`comment_id`),
  KEY `user_id` (`user_id`),
  KEY `post_id` (`post_id`),
  CONSTRAINT `comment_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `comment_ibfk_2` FOREIGN KEY (`post_id`) REFERENCES `post` (`post_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` VALUES (4,'Great place, I would love to rent it!',3,1,'2024-06-11 08:40:00','2024-06-11 08:40:00',1),(5,'Is the price negotiable?',2,1,'2024-06-11 08:40:00','2024-06-11 08:40:00',NULL),(6,'Nội dung bình luận đã được cập nhật',1,1,'2024-06-11 08:40:00','2024-06-16 10:18:12',1),(7,'Nội dung bình luận mẫu',1,1,'2024-06-16 10:08:49','2024-06-16 10:08:49',NULL),(8,'Nội dung bình luận đã được cập nhật',1,1,'2024-06-16 10:14:16','2024-06-16 10:17:34',NULL),(9,'Nội dung bình luận mẫu',1,2,'2024-06-16 10:37:13','2024-06-16 10:37:13',NULL),(10,'Nội dung bình luận mẫu',1,1,'2024-06-17 04:10:12','2024-06-17 04:10:12',NULL);
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `follow`
--

DROP TABLE IF EXISTS `follow`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `follow` (
  `follow_id` int NOT NULL AUTO_INCREMENT,
  `follower_id` int NOT NULL,
  `followee_id` int NOT NULL,
  PRIMARY KEY (`follow_id`),
  UNIQUE KEY `unique_follow` (`follower_id`,`followee_id`),
  KEY `followee_id` (`followee_id`),
  CONSTRAINT `follow_ibfk_1` FOREIGN KEY (`follower_id`) REFERENCES `user` (`id`),
  CONSTRAINT `follow_ibfk_2` FOREIGN KEY (`followee_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `follow`
--

LOCK TABLES `follow` WRITE;
/*!40000 ALTER TABLE `follow` DISABLE KEYS */;
INSERT INTO `follow` VALUES (30,1,2),(32,1,3),(31,1,4),(45,2,1),(44,2,3),(17,3,1),(50,3,4),(48,4,1),(49,4,2);
/*!40000 ALTER TABLE `follow` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `image`
--

DROP TABLE IF EXISTS `image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `image` (
  `image_id` int NOT NULL AUTO_INCREMENT,
  `post_id` int NOT NULL,
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`image_id`),
  KEY `post_id` (`post_id`),
  CONSTRAINT `image_ibfk_1` FOREIGN KEY (`post_id`) REFERENCES `post` (`post_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=129 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `image`
--

LOCK TABLES `image` WRITE;
/*!40000 ALTER TABLE `image` DISABLE KEYS */;
INSERT INTO `image` VALUES (59,1,'https://example.com/apartment1.jpg','2024-06-11 08:40:00'),(60,2,'https://example.com/house1.jpg','2024-06-11 08:40:00'),(61,3,'https://example.com/condo1.jpg','2024-06-11 08:40:00'),(62,96,'http://res.cloudinary.com/dwvkjyixu/image/upload/v1718095628/zdmaxletgcdqjpliwpq5.png',NULL),(63,100,'http://res.cloudinary.com/dwvkjyixu/image/upload/v1718096553/pbwyazb2bqlehfxczreu.png','2024-06-11 09:02:38'),(64,100,'http://res.cloudinary.com/dwvkjyixu/image/upload/v1718096556/j50wnhsygdmd9pkfho3s.jpg','2024-06-11 09:02:38'),(65,101,'http://res.cloudinary.com/dwvkjyixu/image/upload/v1718126403/a3sa4xjfe6abagphl7a4.png','2024-06-11 17:20:13'),(66,101,'http://res.cloudinary.com/dwvkjyixu/image/upload/v1718126412/jchcwdokudhk2nigxl2z.jpg','2024-06-11 17:20:13'),(67,102,'http://res.cloudinary.com/dwvkjyixu/image/upload/v1718126557/maoqev0qobddpb0suuuw.png','2024-06-11 17:22:46'),(68,102,'http://res.cloudinary.com/dwvkjyixu/image/upload/v1718126564/tjjyiwydsx3wrrothhsd.jpg','2024-06-11 17:22:46'),(69,103,'http://res.cloudinary.com/dwvkjyixu/image/upload/v1718127051/k2ndkoupqpbubz5dzjus.png','2024-06-11 17:31:02'),(70,103,'http://res.cloudinary.com/dwvkjyixu/image/upload/v1718127061/ud4bdacwl9r6wsehfbsd.jpg','2024-06-11 17:31:02'),(75,116,'http://res.cloudinary.com/dwvkjyixu/image/upload/v1718132609/yxddwu93qupx4ytvypbi.png','2024-06-11 19:03:31'),(76,116,'http://res.cloudinary.com/dwvkjyixu/image/upload/v1718132614/hxajhhijshdjksgwykwp.jpg','2024-06-11 19:03:31'),(77,117,'http://res.cloudinary.com/dwvkjyixu/image/upload/v1718132668/snkyemkacj1jmuykdtcw.png','2024-06-11 19:04:30'),(78,117,'http://res.cloudinary.com/dwvkjyixu/image/upload/v1718132673/kgkqn2usuda2tedezeyb.jpg','2024-06-11 19:04:30'),(79,125,'http://res.cloudinary.com/dwvkjyixu/image/upload/v1718135925/mcciwowzpnglpoo4qupy.jpg','2024-06-11 19:58:47'),(80,125,'http://res.cloudinary.com/dwvkjyixu/image/upload/v1718135927/teo9pln7km6uwzrx6njz.jpg','2024-06-11 19:58:47'),(82,128,'http://res.cloudinary.com/dwvkjyixu/image/upload/v1718184294/gqjllrtwbz2p2ceafomu.jpg','2024-06-12 09:24:56'),(83,128,'http://res.cloudinary.com/dwvkjyixu/image/upload/v1718184299/hwzwroqdlgvql8gm6fpn.jpg','2024-06-12 09:24:56'),(84,129,'http://res.cloudinary.com/dwvkjyixu/image/upload/v1718215023/lvasg4nytspfp2jtidnf.jpg','2024-06-12 17:57:07'),(85,129,'http://res.cloudinary.com/dwvkjyixu/image/upload/v1718215026/vbphdpgtu0ohtpohbfoc.jpg','2024-06-12 17:57:07'),(86,130,'http://res.cloudinary.com/dwvkjyixu/image/upload/v1718215749/eggxwyufvxoamhxadnkg.jpg','2024-06-12 18:09:13'),(87,130,'http://res.cloudinary.com/dwvkjyixu/image/upload/v1718215752/yypjh9w5vkkhp1sl9xby.jpg','2024-06-12 18:09:13'),(88,131,'http://res.cloudinary.com/dwvkjyixu/image/upload/v1718216069/o5f5wju5vihrkz1eefml.jpg','2024-06-12 18:14:33'),(89,131,'http://res.cloudinary.com/dwvkjyixu/image/upload/v1718216072/ffu2hzdbsdfhctnt42ud.jpg','2024-06-12 18:14:33'),(90,132,'http://res.cloudinary.com/dwvkjyixu/image/upload/v1718216415/oou2xzsvr9axsfdgocqc.jpg','2024-06-12 18:20:20'),(91,132,'http://res.cloudinary.com/dwvkjyixu/image/upload/v1718216418/dmmynnhxexloq976sdui.jpg','2024-06-12 18:20:20'),(92,133,'http://res.cloudinary.com/dwvkjyixu/image/upload/v1718216537/uqkj2ogghzolwazjv5ng.jpg','2024-06-12 18:22:21'),(93,133,'http://res.cloudinary.com/dwvkjyixu/image/upload/v1718216540/rxqwuqe8cmzqt49sjp3t.jpg','2024-06-12 18:22:21'),(94,134,'http://res.cloudinary.com/dwvkjyixu/image/upload/v1718217648/osh0dr3m4p6nxmfrhua0.jpg','2024-06-12 18:40:52'),(95,134,'http://res.cloudinary.com/dwvkjyixu/image/upload/v1718217651/e1sxn4ohggv90rs1n7bn.jpg','2024-06-12 18:40:52'),(96,135,'http://res.cloudinary.com/dwvkjyixu/image/upload/v1718218164/ktyvtns2tybhgfbf6rre.jpg','2024-06-12 18:49:28'),(97,135,'http://res.cloudinary.com/dwvkjyixu/image/upload/v1718218166/podgr4hdbbn0eh7rc2ip.jpg','2024-06-12 18:49:28'),(98,136,'http://res.cloudinary.com/dwvkjyixu/image/upload/v1718218315/ypp0ohtvhgshxrdk38sx.jpg','2024-06-12 18:51:59'),(99,136,'http://res.cloudinary.com/dwvkjyixu/image/upload/v1718218318/b4ctm6jk9rfxlpfisu3b.jpg','2024-06-12 18:51:59'),(100,137,'http://res.cloudinary.com/dwvkjyixu/image/upload/v1718218405/g3kcvqktrkeezwd2itps.jpg','2024-06-12 18:53:34'),(101,137,'http://res.cloudinary.com/dwvkjyixu/image/upload/v1718218413/zz7kxzzudyzfaww1ihyl.jpg','2024-06-12 18:53:34'),(102,138,'http://res.cloudinary.com/dwvkjyixu/image/upload/v1718218620/warx3nmfieeeqanx0jyj.jpg','2024-06-12 18:57:04'),(103,138,'http://res.cloudinary.com/dwvkjyixu/image/upload/v1718218623/pk7vibuzkm5butbuokvs.jpg','2024-06-12 18:57:04'),(110,142,'http://res.cloudinary.com/dwvkjyixu/image/upload/v1718600579/fxaz7xhaxp4hjfjval47.png','2024-06-17 05:03:03'),(111,142,'http://res.cloudinary.com/dwvkjyixu/image/upload/v1718600582/xcclkmwvskeox0fac1bt.png','2024-06-17 05:03:03');
/*!40000 ALTER TABLE `image` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `location`
--

DROP TABLE IF EXISTS `location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `location` (
  `address` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `district` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `city` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `latitude` decimal(9,6) NOT NULL,
  `longitude` decimal(9,6) NOT NULL,
  `post_id` int NOT NULL,
  PRIMARY KEY (`post_id`),
  CONSTRAINT `fk_location_post1` FOREIGN KEY (`post_id`) REFERENCES `post` (`post_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `location`
--

LOCK TABLES `location` WRITE;
/*!40000 ALTER TABLE `location` DISABLE KEYS */;
INSERT INTO `location` VALUES ('123 Đường ABC','Quận 1','TP.HCM',10.823100,106.629700,100),('123 Đường ABC','Quận 1','TP.HCM',10.823100,106.629700,101),('123 Đường ABC','Quận 1','TP.HCM',10.823100,106.629700,102),('123 Đường ABC','Quận 1','TP.HCM',10.823100,106.629700,103),('123 Đường ABC','Quận 1','TP.HCM',10.823100,106.629700,116),('123 Đường ABC','Quận 1','TP.HCM',10.823100,106.629700,117),('123 Đường ABC','Quận 1','TP.HCM',10.823100,106.629700,118),('123 Đường ABC','Quận 1','TP.HCM',10.823100,106.629700,119),('123 Đường ABC','Quận 1','TP.HCM',10.823100,106.629700,120),('123 Đường ABC','Quận 1','TP.HCM',10.823100,106.629700,121),('123 Đường ABC','Quận 1','TP.HCM',10.823100,106.629700,122),('123 Đường ABC','Quận 1','TP.HCM',10.823100,106.629700,124),('123 Đường ABC','Quận 1','TP.HCM',10.823100,106.629700,125),('123 Đường ABC','Quận 1','TP.HCM',10.823100,106.629700,127),('123 Đường ABC','Quận 1','TP.HCM',10.823100,106.629700,128),('123 Đường ABC','Quận 1','TP.HCM',10.823100,106.629700,129),('123 Đường ABC','Quận 1','TP.HCM',10.823100,106.629700,130),('123 Đường ABC','Quận 1','TP.HCM',10.823100,106.629700,131),('123 Đường ABC','Quận 1','TP.HCM',10.823100,106.629700,132),('123 Đường ABC','Quận 1','TP.HCM',10.823100,106.629700,133),('123 Đường ABC','Quận 1','TP.HCM',10.823100,106.629700,134),('123 Đường ABC','Quận 1','TP.HCM',10.823100,106.629700,135),('123 Đường ABC','Quận 1','TP.HCM',10.823100,106.629700,136),('123 Đường ABC','Quận 1','TP.HCM',10.823100,106.629700,137),('123 Đường ABC','Quận 1','TP.HCM',10.823100,106.629700,138),('123 Đường ABC','Quận 1','TP.HCM',10.666996,106.724384,142);
/*!40000 ALTER TABLE `location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notification`
--

DROP TABLE IF EXISTS `notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notification` (
  `notification_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `post_id` int DEFAULT NULL,
  `message` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `is_read` tinyint(1) NOT NULL DEFAULT '0',
  `created_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`notification_id`),
  KEY `user_id` (`user_id`),
  KEY `post_id` (`post_id`),
  CONSTRAINT `notification_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `notification_ibfk_2` FOREIGN KEY (`post_id`) REFERENCES `post` (`post_id`)
) ENGINE=InnoDB AUTO_INCREMENT=143 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notification`
--

LOCK TABLES `notification` WRITE;
/*!40000 ALTER TABLE `notification` DISABLE KEYS */;
INSERT INTO `notification` VALUES (5,2,1,'Your comment has been replied to.',0,'2024-06-11 08:40:00'),(6,1,3,'You have a new follower.',0,'2024-06-11 08:40:00'),(7,1,102,'$(user.getname) đăng tin mới',0,'2024-06-11 17:22:46'),(8,1,103,'Huyđăng tin mới',0,'2024-06-11 17:31:02'),(9,2,115,'Huy đăng tin mới',0,'2024-06-11 18:58:45'),(10,2,116,'Huy đăng tin mới',0,'2024-06-11 19:03:31'),(11,3,116,'Huy đăng tin mới',0,'2024-06-11 19:03:31'),(12,2,117,'Huy đăng tin mới',0,'2024-06-11 19:04:30'),(13,3,117,'Huy đăng tin mới',0,'2024-06-11 19:04:30'),(14,2,118,'Huy đăng tin mới',0,'2024-06-11 19:35:48'),(15,3,118,'Huy đăng tin mới',0,'2024-06-11 19:35:48'),(16,2,119,'Huy đăng tin mới',0,'2024-06-11 19:37:59'),(17,3,119,'Huy đăng tin mới',0,'2024-06-11 19:37:59'),(18,2,120,'Huy đăng tin mới',0,'2024-06-11 19:40:28'),(19,3,120,'Huy đăng tin mới',0,'2024-06-11 19:40:28'),(20,2,125,'Huy đăng tin mới',0,'2024-06-11 19:58:47'),(21,3,125,'Huy đăng tin mới',0,'2024-06-11 19:58:47'),(25,2,128,'Huy đăng tin mới',0,'2024-06-12 09:25:17'),(26,3,128,'Huy đăng tin mới',0,'2024-06-12 09:25:17'),(27,3,127,'Huy đăng tin mới',0,'2024-06-12 10:58:09'),(28,2,127,'Huy đăng tin mới',0,'2024-06-12 10:58:09'),(32,3,117,'Huy đăng tin mới',0,'2024-06-12 10:58:29'),(33,2,124,'Huy đăng tin mới',0,'2024-06-12 11:01:49'),(34,3,124,'Huy đăng tin mới',0,'2024-06-12 11:01:54'),(35,2,124,'Huy đăng tin mới',0,'2024-06-12 11:02:24'),(36,3,124,'Huy đăng tin mới',0,'2024-06-12 11:02:29'),(37,2,124,'Huy đăng tin mới',0,'2024-06-12 11:02:34'),(38,3,124,'Huy đăng tin mới',0,'2024-06-12 11:02:39'),(39,2,124,'Huy đăng tin mới',0,'2024-06-12 11:04:06'),(40,3,124,'Huy đăng tin mới',0,'2024-06-12 11:04:12'),(49,2,129,'Huy đăng tin mới',0,'2024-06-12 17:57:12'),(50,3,129,'Huy đăng tin mới',0,'2024-06-12 17:57:18'),(51,2,129,'Huy đăng tin mới',0,'2024-06-12 17:57:48'),(52,3,129,'Huy đăng tin mới',0,'2024-06-12 17:57:52'),(58,3,131,'Huy đăng tin mới',0,'2024-06-12 18:15:45'),(61,2,131,'Huy đăng tin mới',0,'2024-06-12 18:17:21'),(62,3,131,'Huy đăng tin mới',0,'2024-06-12 18:17:27'),(69,3,135,'Huy đăng tin mới',0,'2024-06-12 18:49:33'),(75,2,138,'Huy đăng tin mới',0,'2024-06-12 18:57:12'),(76,3,138,'Huy đăng tin mới',0,'2024-06-12 18:57:12'),(77,2,118,'Huy đăng tin mới',0,'2024-06-12 18:58:07'),(78,3,118,'Huy đăng tin mới',0,'2024-06-12 18:58:13'),(79,2,116,'Huy đăng tin mới',0,'2024-06-12 18:58:24'),(80,3,116,'Huy đăng tin mới',0,'2024-06-12 18:58:30'),(87,3,142,'Huy đăng tin mới',0,'2024-06-17 08:40:48'),(88,3,115,'Huy đăng tin mới',0,'2024-06-17 08:40:56'),(89,3,114,'Huy đăng tin mới',0,'2024-06-17 08:41:02'),(90,3,103,'Huy đăng tin mới',0,'2024-06-17 08:41:08'),(91,3,102,'Huy đăng tin mới',0,'2024-06-17 08:41:12'),(92,3,101,'Huy đăng tin mới',0,'2024-06-17 08:41:15'),(102,3,138,'Huy đã đăng tin mới',0,'2024-06-21 08:23:09'),(103,2,138,'Huy đã đăng tin mới',0,'2024-06-21 08:23:09'),(104,4,138,'Huy đã đăng tin mới',0,'2024-06-21 08:23:09'),(105,3,131,'Huy đã đăng tin mới',0,'2024-06-21 08:23:15'),(106,2,131,'Huy đã đăng tin mới',0,'2024-06-21 08:23:15'),(107,4,131,'Huy đã đăng tin mới',0,'2024-06-21 08:23:15'),(111,2,120,'Huy đã đăng tin mới',0,'2024-06-21 08:23:26'),(112,3,120,'Huy đã đăng tin mới',0,'2024-06-21 08:23:26'),(113,4,120,'Huy đã đăng tin mới',0,'2024-06-21 08:23:26'),(117,3,NULL,'Huy Võ Quốc đã đăng tin mới',0,'2024-06-25 11:20:37'),(118,2,NULL,'Huy Võ Quốc đã đăng tin mới',0,'2024-06-25 11:20:37'),(119,4,NULL,'Huy Võ Quốc đã đăng tin mới',0,'2024-06-25 11:20:37'),(120,1,NULL,'Bài viết đã bị admin từ chối',0,'2024-06-25 11:20:45'),(121,1,NULL,'Bài viết đã bị admin từ chối',0,'2024-06-25 11:21:13'),(122,2,NULL,'Bài viết đã bị admin từ chối',0,'2024-06-25 11:32:09'),(123,3,NULL,'Huy Võ Quốc đã đăng tin mới',0,'2024-06-25 11:39:36'),(124,2,NULL,'Huy Võ Quốc đã đăng tin mới',0,'2024-06-25 11:39:36'),(125,4,NULL,'Huy Võ Quốc đã đăng tin mới',0,'2024-06-25 11:39:36'),(126,2,NULL,'Bài viết đã bị admin từ chối',0,'2024-06-26 11:47:12'),(127,3,NULL,'Huy Võ Quốc đã đăng tin mới',0,'2024-06-26 11:58:50'),(128,2,NULL,'Huy Võ Quốc đã đăng tin mới',0,'2024-06-26 11:58:50'),(129,4,NULL,'Huy Võ Quốc đã đăng tin mới',0,'2024-06-26 11:58:50'),(130,1,NULL,'Bài viết đã bị admin từ chối',0,'2024-06-26 11:58:58'),(131,3,NULL,'Huy Võ Quốc đã đăng tin mới',0,'2024-06-26 11:59:08'),(132,2,NULL,'Huy Võ Quốc đã đăng tin mới',0,'2024-06-26 11:59:08'),(133,4,NULL,'Huy Võ Quốc đã đăng tin mới',0,'2024-06-26 11:59:08'),(134,3,NULL,'Huy Võ Quốc đã đăng tin mới',0,'2024-06-26 11:59:18'),(135,2,NULL,'Huy Võ Quốc đã đăng tin mới',0,'2024-06-26 11:59:18'),(136,4,NULL,'Huy Võ Quốc đã đăng tin mới',0,'2024-06-26 11:59:18'),(137,3,NULL,'Huy Võ Quốc đã đăng tin mới',0,'2024-06-26 11:59:35'),(138,2,NULL,'Huy Võ Quốc đã đăng tin mới',0,'2024-06-26 11:59:35'),(139,4,NULL,'Huy Võ Quốc đã đăng tin mới',0,'2024-06-26 11:59:35'),(140,1,NULL,'Bài viết đã bị admin từ chối',0,'2024-06-26 11:59:41'),(141,2,NULL,'Bài viết đã bị admin từ chối',0,'2024-06-26 12:23:36'),(142,1,NULL,'Bài viết đã bị admin từ chối',0,'2024-06-26 12:27:03');
/*!40000 ALTER TABLE `notification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post`
--

DROP TABLE IF EXISTS `post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `post` (
  `post_id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `status` tinyint(1) NOT NULL DEFAULT '1',
  `type_id` int NOT NULL,
  `user_id` int NOT NULL,
  `created_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `actived` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`post_id`),
  KEY `user_id` (`user_id`),
  KEY `type_id` (`type_id`),
  CONSTRAINT `post_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `post_ibfk_2` FOREIGN KEY (`type_id`) REFERENCES `typeofpost` (`type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=149 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post`
--

LOCK TABLES `post` WRITE;
/*!40000 ALTER TABLE `post` DISABLE KEYS */;
INSERT INTO `post` VALUES (1,'Beautiful Apartment for Rent','This is a spacious apartment located in the heart of the city. Perfect for students and young professionals.',0,2,2,'2024-06-11 08:39:05','2024-06-21 08:02:01',1),(2,'Cozy House for Sale','Charming house with a garden in a quiet neighborhood. Ideal for families.',1,1,3,'2024-06-11 08:39:05','2024-06-21 08:02:01',1),(3,'Luxury Condo with Stunning Views','Modern condo with breathtaking views of the skyline. Amenities include a gym, pool, and concierge service.',0,2,4,'2024-06-11 08:39:05','2024-06-21 08:07:29',1),(93,'Beautiful Apartment for Rent','This is a spacious apartment located in the heart of the city. Perfect for students and young professionals.',0,2,2,'2024-06-11 08:40:00','2024-06-21 08:12:22',1),(94,'Cozy House for Sale','Charming house with a garden in a quiet neighborhood. Ideal for families.',1,1,3,'2024-06-11 08:40:00','2024-06-21 08:02:01',1),(95,'Luxury Condo with Stunning Views','Modern condo with breathtaking views of the skyline. Amenities include a gym, pool, and concierge service.',1,2,4,'2024-06-11 08:40:00','2024-06-21 08:02:01',1),(96,'123','123',0,2,1,NULL,NULL,0),(100,'Bài đăng cho thuê nhà','Nhà cho thuê giá rẻ',1,1,1,'2024-06-11 09:02:31','2024-06-21 08:02:01',1),(101,'Bài đăng cho thuê nhà','Nhà cho thuê giá rẻ',1,1,1,'2024-06-11 17:19:54','2024-06-21 08:02:01',1),(102,'Bài đăng cho thuê nhà','Nhà cho thuê giá rẻ',1,1,1,'2024-06-11 17:22:33','2024-06-21 08:02:01',1),(103,'Bài đăng cho thuê nhà','Nhà cho thuê giá rẻ',1,1,1,'2024-06-11 17:30:48','2024-06-25 10:53:13',1),(114,'Bài đăng cho thuê nhà','Nhà cho thuê giá rẻ',1,1,1,'2024-06-11 18:53:50','2024-06-21 08:02:01',1),(115,'Bài đăng cho thuê nhà','Nhà cho thuê giá rẻ',1,1,1,'2024-06-11 18:58:45','2024-06-21 08:02:01',1),(116,'Bài đăng cho thuê nhà','Nhà cho thuê giá rẻ',1,1,1,'2024-06-11 19:03:22','2024-06-21 08:02:01',1),(117,'Bài đăng cho thuê nhà','Nhà cho thuê giá rẻ',1,2,1,'2024-06-11 19:04:19','2024-06-26 13:52:43',1),(118,'Bài đăng cho thuê nhà','Nhà cho thuê giá rẻ',1,1,1,'2024-06-11 19:35:48','2024-06-21 08:02:42',1),(119,'Bài đăng cho thuê nhà','Nhà cho thuê giá rẻ',1,1,1,'2024-06-11 19:37:59','2024-06-21 08:02:01',1),(120,'aaaa','Nhà cho thuê giá rẻ',1,1,1,'2024-06-11 19:40:28','2024-06-21 08:23:22',1),(121,'aaaa','Nhà cho thuê giá rẻ',1,1,1,'2024-06-11 19:44:14','2024-06-21 08:02:01',1),(122,'aaaa','Nhà cho thuê giá rẻ',1,1,1,'2024-06-11 19:48:29','2024-06-26 11:50:12',1),(124,'aaaa','Nhà cho thuê giá rẻ',1,1,1,'2024-06-11 19:56:58','2024-06-21 08:02:01',1),(125,'aaaa','Nhà cho thuê giá rẻ',0,1,1,'2024-06-11 19:58:41','2024-06-26 11:59:38',0),(127,'aaaa','Nhà cho thuê giá rẻ',1,1,1,'2024-06-11 20:15:30','2024-06-21 08:02:01',1),(128,'aaaa','Nhà cho thuê giá rẻ',1,1,1,'2024-06-12 09:24:44','2024-06-21 08:02:01',1),(129,'aaaa','Nhà cho thuê giá rẻ',1,1,1,'2024-06-12 17:56:59','2024-06-21 08:02:01',1),(130,'aaaa','Nhà cho thuê giá rẻ',1,1,1,'2024-06-12 18:09:03','2024-06-21 08:02:01',1),(131,'aaaa','Nhà cho thuê giá rẻ',1,1,1,'2024-06-12 18:14:22','2024-06-26 11:59:35',1),(132,'aaaa','Nhà cho thuê giá rẻ',1,1,1,'2024-06-12 18:20:10','2024-06-21 08:02:01',1),(133,'aaaa','Nhà cho thuê giá rẻ',1,1,1,'2024-06-12 18:22:10','2024-06-21 08:02:01',1),(134,'aaaa','Nhà cho thuê giá rẻ',1,1,1,'2024-06-12 18:40:41','2024-06-26 11:59:18',1),(135,'aaaa','Nhà cho thuê giá rẻ',1,1,1,'2024-06-12 18:49:10','2024-06-21 08:02:01',1),(136,'aaaa','Nhà cho thuê giá rẻ',1,1,1,'2024-06-12 18:51:51','2024-06-26 11:59:08',1),(137,'aaaa','Nhà cho thuê giá rẻ',0,1,1,'2024-06-12 18:53:22','2024-06-26 11:58:54',0),(138,'aaaa','Nhà cho thuê giá rẻ',1,1,1,'2024-06-12 18:56:53','2024-06-26 11:47:16',1),(142,'aaaa','Nhà cho thuê giá rẻ',1,1,1,'2024-06-17 05:02:48','2024-06-25 11:20:33',1);
/*!40000 ALTER TABLE `post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `property_detail`
--

DROP TABLE IF EXISTS `property_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `property_detail` (
  `created_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `price` decimal(10,2) DEFAULT NULL,
  `acreage` int NOT NULL,
  `capacity` int NOT NULL,
  `post_id` int NOT NULL,
  PRIMARY KEY (`post_id`),
  KEY `fk_property_detail_post1_idx` (`post_id`),
  CONSTRAINT `fk_property_detail_post1` FOREIGN KEY (`post_id`) REFERENCES `post` (`post_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `property_detail`
--

LOCK TABLES `property_detail` WRITE;
/*!40000 ALTER TABLE `property_detail` DISABLE KEYS */;
INSERT INTO `property_detail` VALUES ('2024-06-11 08:40:00',100000.00,120,4,1),('2024-06-11 08:40:00',500000.00,300,6,2),('2024-06-11 08:40:00',800000.00,200,2,3),(NULL,1000000.00,50,5,100),(NULL,1000000.00,50,5,101),(NULL,1000000.00,50,5,102),(NULL,1000000.00,50,5,103),(NULL,1000000.00,50,5,116),(NULL,1000000.00,50,5,117),(NULL,1000000.00,50,5,118),(NULL,1000000.00,50,5,119),(NULL,1000000.00,50,5,120),(NULL,1000000.00,50,5,121),(NULL,1000000.00,50,5,122),(NULL,2213123.00,50,5,124),(NULL,2213123.00,50,5,125),(NULL,2213123.00,50,5,127),(NULL,2213123.00,50,5,128),(NULL,2213123.00,50,5,129),(NULL,2213123.00,50,5,130),(NULL,2213123.00,50,5,131),(NULL,2213123.00,50,5,132),(NULL,2213123.00,50,5,133),(NULL,2213123.00,50,5,134),(NULL,2213123.00,50,5,135),(NULL,2213123.00,50,5,136),(NULL,2213123.00,50,5,137),(NULL,2213123.00,50,5,138),(NULL,2213123.00,50,5,142);
/*!40000 ALTER TABLE `property_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ReportPost`
--

DROP TABLE IF EXISTS `ReportPost`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ReportPost` (
  `id` int NOT NULL AUTO_INCREMENT,
  `reporter_id` int NOT NULL,
  `post_id` int NOT NULL,
  `reason` text COLLATE utf8mb4_unicode_ci,
  `timestamp` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `reporter_id` (`reporter_id`),
  KEY `post_id` (`post_id`),
  CONSTRAINT `reportpost_ibfk_1` FOREIGN KEY (`reporter_id`) REFERENCES `User` (`id`),
  CONSTRAINT `reportpost_ibfk_2` FOREIGN KEY (`post_id`) REFERENCES `Post` (`post_id`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ReportPost`
--

LOCK TABLES `ReportPost` WRITE;
/*!40000 ALTER TABLE `ReportPost` DISABLE KEYS */;
INSERT INTO `ReportPost` VALUES (46,2,138,'ảo điên','2024-06-27 00:44:33'),(47,2,138,'ảo điên','2024-06-27 00:44:34'),(48,2,138,'ảo điên','2024-06-27 00:44:34'),(49,2,128,'ảo điên','2024-06-27 00:44:39'),(50,2,128,'ảo điên','2024-06-27 00:44:41'),(51,2,128,'ảo điên','2024-06-27 00:44:41'),(52,2,128,'ảo điên','2024-06-27 00:44:41'),(53,2,129,'ảo điên','2024-06-27 00:44:46'),(54,2,129,'ảo điên','2024-06-27 00:44:46'),(55,2,129,'ảo điên','2024-06-27 00:44:47'),(56,2,129,'ảo điên','2024-06-27 00:44:47'),(57,2,129,'ảo điên','2024-06-27 00:44:47'),(58,2,130,'ảo điên','2024-06-27 00:44:51'),(59,2,130,'ảo điên','2024-06-27 00:44:52'),(60,2,130,'ảo điên','2024-06-27 00:44:52');
/*!40000 ALTER TABLE `ReportPost` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'ROLE_ADMIN'),(2,'ROLE_LANDLORD'),(3,'ROLE_RENTER');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `typeofpost`
--

DROP TABLE IF EXISTS `typeofpost`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `typeofpost` (
  `type_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`type_id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `typeofpost`
--

LOCK TABLES `typeofpost` WRITE;
/*!40000 ALTER TABLE `typeofpost` DISABLE KEYS */;
INSERT INTO `typeofpost` VALUES (1,'CHO THUÊ'),(2,'TÌM TRỌ');
/*!40000 ALTER TABLE `typeofpost` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `username` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `password` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `email` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `date_of_birth` date DEFAULT NULL,
  `role_id` int NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `cccd` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `numberPhone` varchar(12) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `activated` tinyint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `numberPhone` (`numberPhone`),
  UNIQUE KEY `cccd` (`cccd`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `username` (`username`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'https://lh3.googleusercontent.com/a/ACg8ocJ0Kem2EoyPBjNUS1GsUE5fI1Y6Hqd6FPYflyT978re-uU_uIY=s96-c','admin','$2a$10$5X9k5N1sTc1/CjVH5XJoje3QMYijH3ETpgkox00R0MdPaJPPrf7wO','2151013029huy@ou.edu.vn','2024-05-14',1,'Huy Võ Quốc','23123123','23123123',NULL,'2024-06-11 08:37:06','2024-06-26 17:10:10',1),(2,'https://images.unsplash.com/photo-1511367461989-f85a21fda167','user1','$2a$10$5X9k5N1sTc1/CjVH5XJoje3QMYijH3ETpgkox00R0MdPaJPPrf7wO','baoempro2003@gmail.com','2024-05-14',2,'John','23456789','23456789',NULL,'2024-06-11 08:37:06','2024-06-21 08:06:05',1),(3,'https://lh3.googleusercontent.com/a/ACg8ocIrGQA8n4uh9KOX_bCdWUyqV4kLkkpmT-zWvEot_tNvS7JI7g=s96-c','user2','$2a$10$5X9k5N1sTc1/CjVH5XJoje3QMYijH3ETpgkox00R0MdPaJPPrf7wO','voquochuy3006@gmail.com','2024-05-15',2,'Huy Võ Quốc','34567890','34567890',NULL,'2024-06-11 08:37:06','2024-06-26 16:45:10',1),(4,'https://images.unsplash.com/photo-1506794778202-cad84cf45f1d','user3','$2a$10$5X9k5N1sTc1/CjVH5XJoje3QMYijH3ETpgkox00R0MdPaJPPrf7wO','2151013099tien@ou.edu.vn','2024-05-16',2,'Alice','45678901','45678901',NULL,'2024-06-11 08:37:06','2024-06-21 08:06:05',1),(25,'https://lh3.googleusercontent.com/a/ACg8ocIWcDWMThRLoINBBINtwGdCDovTkIW2NROGquuWDmE7puVgJbS_=s96-c','2151010421tuan@ou.edu.vn','$2a$10$R4IaXT2NWFrIj/p2Yet4HeM05ohXNiQXjBSLyGs84xbR9xloEm9Xi','2151010421tuan@ou.edu.vn',NULL,3,'Tuấn Trương Bùi Anh',NULL,NULL,NULL,'2024-06-25 06:01:28','2024-06-26 13:45:12',1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-06-27  0:56:42
