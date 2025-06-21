-- MySQL dump 10.13  Distrib 8.0.29, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: ai_plan
-- ------------------------------------------------------
-- Server version	8.0.29

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
-- Table structure for table `ai_message`
--

DROP TABLE IF EXISTS `ai_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ai_message` (
  `id` varchar(36) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `creator_id` varchar(32) NOT NULL,
  `editor_id` varchar(32) NOT NULL,
  `type` varchar(32) NOT NULL COMMENT '消息类型(用户/助手/系统)',
  `text_content` text NOT NULL COMMENT '消息内容',
  `medias` json DEFAULT NULL COMMENT '媒体内容如图片链接、语音链接',
  `session_id` varchar(32) NOT NULL COMMENT '会话id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='AI消息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ai_message`
--

LOCK TABLES `ai_message` WRITE;
/*!40000 ALTER TABLE `ai_message` DISABLE KEYS */;
INSERT INTO `ai_message` VALUES ('28c461ac963e4e9aa33be2d880c9eeb0','2025-06-21 10:30:13.682285','2025-06-21 10:30:13.682285','5ee14b91481d4bc9a41a0c3d6c5b40c4','5ee14b91481d4bc9a41a0c3d6c5b40c4','ASSISTANT','学习Java是一个循序渐进的过程，下面是一些建议帮助你开始或继续你的Java学习之旅：\n\n### 1. 理解基础概念\n- **变量与数据类型**：了解如何声明变量、熟悉基本的数据类型（如int, double, boolean等）。\n- **控制结构**：掌握条件语句(if-else)和循环(for, while)的使用方法。\n- **函数/方法**：学会定义和调用方法，理解参数传递的概念。\n- **面向对象编程**：这是Java的核心之一。需要学习类(class)、对象(object)、继承(inheritance)、封装(encapsulation)以及多态(polymorphism)等概念。\n\n### 2. 实践编程\n- **编写代码**：理论知识很重要，但没有比实际动手写代码更好的学习方式了。可以从简单的程序做起，比如打印“Hello World”到屏幕。\n- **解决问题**：尝试解决一些小问题或者完成LeetCode、HackerRank上的挑战来提高自己的技能。\n- **项目实践**：随着水平的增长，可以参与到更复杂的项目中去，比如开发一个简易的图书管理系统或是个人博客网站。\n\n### 3. 学习资源\n- **在线课程**：Coursera、edX、Udemy等平台提供了许多优质的Java入门及进阶课程。\n- **书籍**：《Thinking in Java》(Bruce Eckel著)、《Effective Java》(Joshua Bloch著)都是不错的选择。\n- **官方文档**：阅读Oracle提供的Java官方文档对于深入理解语言特性非常有帮助。\n- **社区交流**：加入GitHub、Stack Overflow等技术社区，与其他开发者交流经验。\n\n### 4. 持续更新知识\n- 技术在不断进步，保持对新版本Java特性的关注是很重要的。同时也可以探索相关领域如Spring框架、微服务架构等。\n\n希望这些建议对你有所帮助！如果你有任何具体的问题或需要进一步指导，请随时告诉我。','[]','1796ba6ebef74dc0aa24394d505e265c'),('3932739fc3294439a05b454a53e985c3','2025-06-21 10:29:36.888204','2025-06-21 10:29:36.888204','5ee14b91481d4bc9a41a0c3d6c5b40c4','5ee14b91481d4bc9a41a0c3d6c5b40c4','ASSISTANT','你好！有什么可以帮助你的吗？','[]','1796ba6ebef74dc0aa24394d505e265c'),('6f3de7d1efb54077be1f1649d014cde4','2025-06-21 10:53:04.103871','2025-06-21 10:53:04.103871','5ee14b91481d4bc9a41a0c3d6c5b40c4','5ee14b91481d4bc9a41a0c3d6c5b40c4','USER','我目前的任务完成情况',NULL,'928c384040fe4f31955796e70c69b74c'),('87cd2aac89ae40fba4fc8fe0be89b3b2','2025-06-05 23:47:28.319105','2025-06-05 23:47:28.319105','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','USER','你好',NULL,'ea7ce7b1b5114745ad62c9b1536acc16'),('8d4ad8cc476f40b3aef18c5cfd6d361b','2025-06-05 23:47:28.362850','2025-06-05 23:47:28.362850','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','ASSISTANT','你好！有什么可以帮助你的吗？','[]','ea7ce7b1b5114745ad62c9b1536acc16'),('8fb66ffe0e774be39ea62a6c9247f27f','2025-06-21 10:53:04.123869','2025-06-21 10:53:04.123869','5ee14b91481d4bc9a41a0c3d6c5b40c4','5ee14b91481d4bc9a41a0c3d6c5b40c4','ASSISTANT','根据您提供的信息，您的任务完成情况如下：\n\n### 任务1：学习IO流\n- **截至时间**：2025-06-23T10:30\n- **是否完成**：是\n- **具体步骤及完成情况**：\n  - **步骤内容**：FileSteam实现文件独写\n    - **是否完成**：否\n  - **步骤内容**：ByteArraySteam实现读取二进制到内存再输出\n    - **是否完成**：否\n\n虽然整体任务标记为“是”，但其下的两个子任务都未完成。这可能意味着您认为已经掌握了关于IO流的基础知识，但具体的实践操作（如使用`FileInputStream`和`ByteArrayInputStream`）还未进行。\n\n### 任务2：学习多线程\n- **截至时间**：2025-06-28T10:45\n- **是否完成**：否\n- **具体步骤及完成情况**：\n  - **步骤内容**：看一些多线程的面试题\n    - **是否完成**：否\n  - **步骤内容**：学习Thread和线程池\n    - **是否完成**：否\n  - **步骤内容**：学习concurrent包相关的内容\n    - **是否完成**：否\n\n对于多线程的学习任务，目前还没有任何一部分被标记为已完成。看起来这个领域还有待深入探索。\n\n### 建议\n- 对于IO流部分，建议尽快通过实际编程练习来加深理解，比如尝试编写程序使用`FileInputStream`读取文件，并利用`ByteArrayOutputStream`将数据写入内存。\n- 多线程是一个复杂但非常重要的主题，特别是对于提升程序性能方面。可以先从基础概念开始，如什么是线程、如何创建线程等，然后逐步过渡到更高级的主题，例如线程间通信、同步机制以及并发工具类的应用。\n\n希望这些信息对您有所帮助！如果需要进一步指导或有其他问题，请随时告诉我。','[]','928c384040fe4f31955796e70c69b74c'),('b270f3086eda456fa9545ab57dafccad','2025-06-21 10:29:36.863686','2025-06-21 10:29:36.863686','5ee14b91481d4bc9a41a0c3d6c5b40c4','5ee14b91481d4bc9a41a0c3d6c5b40c4','USER','你好',NULL,'1796ba6ebef74dc0aa24394d505e265c'),('ca3dc6959ce945c8a93f0084fadfce71','2025-06-07 10:38:38.302926','2025-06-07 10:38:38.302926','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','ASSISTANT','好的，根据你提供的信息，我来帮你总结一下任务“测试1”的完成情况：\n\n### 任务名称：测试1\n- **截至时间**：2025-06-05T21:15\n- **是否完成**：否\n- **任务步骤及完成情况**：\n  1. **步骤内容**：测试步骤1\n     - **是否完成**：是\n  2. **步骤内容**：测试333\n     - **是否完成**：是\n  3. **步骤内容**：测试步骤2\n     - **是否完成**：是\n\n### 总结\n- 任务“测试1”共有3个步骤，所有步骤都已经完成。\n- 尽管所有步骤都已完成，但整个任务的完成状态仍然标记为“否”。这可能意味着还有其他未列出的步骤或最终确认工作需要完成。\n\n如果你有其他需要补充的信息或进一步的帮助，请告诉我！','[]','ea7ce7b1b5114745ad62c9b1536acc16'),('ede6afd89dda4518a3513ed90ce2cd21','2025-06-21 10:30:13.655771','2025-06-21 10:30:13.655771','5ee14b91481d4bc9a41a0c3d6c5b40c4','5ee14b91481d4bc9a41a0c3d6c5b40c4','USER','如何学习Java',NULL,'1796ba6ebef74dc0aa24394d505e265c'),('f0bf5ff70a8b4a4fa0bb4bf7a51eb574','2025-06-07 10:38:38.199405','2025-06-07 10:38:38.199405','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','USER','帮我总结以下我的任务完成情况',NULL,'ea7ce7b1b5114745ad62c9b1536acc16');
/*!40000 ALTER TABLE `ai_message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ai_session`
--

DROP TABLE IF EXISTS `ai_session`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ai_session` (
  `id` varchar(36) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `creator_id` varchar(32) NOT NULL,
  `editor_id` varchar(32) NOT NULL,
  `name` varchar(32) NOT NULL COMMENT '会话名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='AI会话';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ai_session`
--

LOCK TABLES `ai_session` WRITE;
/*!40000 ALTER TABLE `ai_session` DISABLE KEYS */;
INSERT INTO `ai_session` VALUES ('1796ba6ebef74dc0aa24394d505e265c','2025-06-21 10:07:32.437936','2025-06-21 10:07:32.437936','5ee14b91481d4bc9a41a0c3d6c5b40c4','5ee14b91481d4bc9a41a0c3d6c5b40c4','新的聊天'),('928c384040fe4f31955796e70c69b74c','2025-06-21 10:52:32.398342','2025-06-21 10:52:32.398342','5ee14b91481d4bc9a41a0c3d6c5b40c4','5ee14b91481d4bc9a41a0c3d6c5b40c4','新的聊天'),('ea7ce7b1b5114745ad62c9b1536acc16','2025-06-05 23:47:13.485088','2025-06-05 23:47:13.485088','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','新的聊天');
/*!40000 ALTER TABLE `ai_session` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dict`
--

DROP TABLE IF EXISTS `dict`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dict` (
  `id` varchar(36) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `creator_id` varchar(36) NOT NULL,
  `editor_id` varchar(36) NOT NULL,
  `key_id` int NOT NULL,
  `key_en_name` varchar(255) NOT NULL,
  `key_name` varchar(36) NOT NULL,
  `dict_id` int NOT NULL,
  `dict_name` varchar(36) NOT NULL,
  `dict_en_name` varchar(255) NOT NULL,
  `order_num` int NOT NULL,
  `visible` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否可见',
  PRIMARY KEY (`id`),
  UNIQUE KEY `dict_pk` (`key_id`,`dict_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dict`
--

LOCK TABLES `dict` WRITE;
/*!40000 ALTER TABLE `dict` DISABLE KEYS */;
INSERT INTO `dict` VALUES ('13059f87f7c84d319fd3f6038cedea9b','2024-02-22 15:58:22.896688','2024-02-22 15:58:34.067738','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7',0,'DISCOUNT','折扣券',1006,'优惠券类型','COUPON_TYPE',0,1),('1f01fa7bf1624376870d9207735f658d','2024-01-16 09:33:09.151337','2024-01-16 09:33:09.151337','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7',2,'BUTTON','按钮',1002,'菜单类型','MENU_TYPE',2,1),('29f91a9b6a1f4711b73f27a32de8d10e','2024-02-21 09:02:07.356526','2024-02-21 09:02:07.356526','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7',0,'REFUNDING','退款中',1010,'退款状态','REFUND_STATUS',0,1),('2a8a74279fb64ecb822c8b22fd493a93','2024-01-26 11:08:34.090534','2024-01-26 11:08:37.984915','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7',4,'CLOSED','已关闭',1011,'跑腿订单状态','TASK_ORDER_STATUS',0,1),('3860dff47f224dedbc3019cd1b4bc098','2024-01-16 09:30:39.144272','2024-01-16 09:33:15.663135','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7',0,'PAGE','页面',1002,'菜单类型','MENU_TYPE',1,1),('416c90b442e84af1a3f57e321c9c3437','2024-01-16 09:32:28.555205','2024-01-16 09:32:28.555205','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7',1,'DIRECTORY','目录',1002,'菜单类型','MENU_TYPE',0,1),('461a361d073c4574aed1c025e04a81a3','2024-01-26 11:09:32.434369','2024-01-26 11:13:54.428416','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7',5,'REFUNDED','已退款',1011,'跑腿订单状态','TASK_ORDER_STATUS',0,1),('5386795056ed459b82e1c8bdcff408fe','2025-04-11 15:37:35.805191','2025-04-11 15:37:35.805191','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7',2,'REFUSE','拒绝',2001,'审批状态','APPROVE_STATUS',2,1),('53e01943dce84eadab297d5186db2d90','2025-04-04 14:23:36.186875','2025-04-04 14:23:36.186875','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7',0,'WE_CHAT_PAY','微信支付',1014,'支付类型','PAY_TYPE',0,1),('56e8d93069534f6a875c34d5c26802a5','2024-01-26 11:03:49.162351','2024-01-26 11:04:00.418344','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7',2,'TO_BE_DELIVERED','待送达',1011,'跑腿订单状态','TASK_ORDER_STATUS',0,1),('5c820b53654545fd844222f7e486fc8e','2024-01-26 10:56:45.364997','2024-01-26 11:02:58.744868','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7',0,'TO_BE_PAID','待付款',1011,'跑腿订单状态','TASK_ORDER_STATUS',0,1),('5fba34ff760c453a9ce3284ed68710ca','2024-01-14 15:23:55.954376','2024-01-14 15:23:55.954376','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7',2,'PRIVATE','保密',1001,'性别','GENDER',0,1),('7d11e97eb4d7474ca2dab8cb50a32ca8','2024-02-22 15:58:22.896688','2024-02-22 15:58:34.067738','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7',2,'CATEGORY','品类券',1007,'优惠券使用范围','COUPON_SCOPE_TYPE',0,1),('7d11e97eb4d7474ca2dab8cb50c32ca8','2024-02-22 15:58:22.896688','2024-02-22 15:58:34.067738','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7',1,'PRODUCT','商品券',1007,'优惠券使用范围','COUPON_SCOPE_TYPE',0,1),('7d11e97eb4d7474ca2dab8cb50c34ca1','2024-02-21 09:05:02.936174','2024-02-21 09:05:13.494713','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7',1,'SUCCESS','退款成功',1010,'退款状态','REFUND_STATUS',0,1),('7d11e97eb4d7474ca2dab8cb50c34ca8','2024-02-21 09:01:02.962067','2024-02-21 09:01:02.962067','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7',2,'FAILED','退款失败',1010,'退款状态','REFUND_STATUS',0,1),('7d11e97eb4d7474ca2dab8cb50c34cab','2024-02-22 15:58:22.896688','2024-02-22 15:58:34.067738','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7',1,'USED','已使用',1008,'优惠券使用状态','COUPON_USE_STATUS',0,1),('7d11e97eb4d7474ca2dab8cb50c34cah','2024-02-22 15:58:22.896688','2024-02-22 15:58:34.067738','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7',0,'GIFT','系统赠送',1009,'优惠券获取方式','COUPON_RECEIVE_TYPE',0,1),('7d11e97eb4d7474ca2dab8cb50c34caz','2024-02-22 15:58:22.896688','2024-02-22 15:58:34.067738','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7',2,'EXPIRED','已过期',1008,'优惠券使用状态','COUPON_USE_STATUS',0,1),('7d11e97eb4d7474ca2dab8cb50c34cc8','2024-02-22 15:58:22.896688','2024-02-22 15:58:34.067738','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7',0,'GENERAL','通用券',1007,'优惠券使用范围','COUPON_SCOPE_TYPE',0,1),('7d11e97eb4d7474ca2dzb8cb50c34ca1','2024-02-22 15:58:22.896688','2024-02-22 15:58:34.067738','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7',0,'UNUSED','未使用',1008,'优惠券使用状态','COUPON_USE_STATUS',0,1),('832ddb8d99734338b83fae240100f06a','2025-04-11 15:37:35.793194','2025-04-11 15:37:35.793194','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7',1,'PASS','通过',2001,'审批状态','APPROVE_STATUS',1,1),('a1a13655732845c38cdddc0d41ef5792','2024-01-26 11:06:10.939935','2024-01-26 11:06:16.216645','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7',3,'TO_BE_EVALUATED','待评价',1011,'跑腿订单状态','TASK_ORDER_STATUS',0,1),('a3caf40ea1ef4a778096f467fb14060e','2024-01-10 10:50:18.555224','2024-01-11 15:49:22.959501','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7',0,'MALE','男',1001,'性别','GENDER',0,1),('b33660610be145f998de5a86753665ce','2024-01-10 13:55:26.468101','2024-01-10 13:55:26.468101','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7',1,'FEMALE','女',1001,'性别','GENDER',1,1),('bada6f12216d402bb18553fe0b777342','2025-04-11 15:37:35.760191','2025-04-11 15:37:35.760191','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7',0,'WAIT','待审批',2001,'审批状态','APPROVE_STATUS',0,1),('c88f9f06e956433d9bd738ec15e24cee','2025-04-04 14:13:43.138075','2025-04-04 14:13:43.138075','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7',1,'RECHARGE','充值',1013,'钱包变动类型','WALLET_RECORD_TYPE',1,1),('cc6547e8463143b98d2d2d0c885afa54','2025-04-04 14:13:43.160071','2025-04-04 14:13:43.160071','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7',2,'WITHDRAW','提现',1013,'钱包变动类型','WALLET_RECORD_TYPE',2,1),('ef24101d372a4df7b4deb96ef3758139','2025-04-04 14:13:43.189073','2025-04-04 14:13:43.189073','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7',3,'REFUND','退款',1013,'钱包变动类型','WALLET_RECORD_TYPE',3,1),('f0c3f8a2a2184d91a89a156dad9ea317','2025-04-04 14:13:43.039529','2025-04-04 14:13:43.039529','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7',0,'REBATE','返佣',1013,'钱包变动类型','WALLET_RECORD_TYPE',0,1),('f0e0c91e7ad249deb9359691d109bd8h','2024-02-21 09:05:02.936174','2024-02-21 09:05:13.494713','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7',0,'PRODUCT','商品',1004,'跳转类型','NAVIGATOR_TYPE',0,1),('f0e0c95e7ad249deb9359691d009fd7a','2024-02-18 15:08:27.511795','2024-02-18 15:10:19.268405','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7',6,'FINISHED','已完成',1011,'跑腿订单状态','TASK_ORDER_STATUS',0,1),('f0e0c95e7ad249deb9359691d009fd7c','2024-02-22 15:58:22.896688','2024-02-22 15:58:34.067738','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7',1,'REDUCE','满减券',1006,'优惠券类型','COUPON_TYPE',0,1),('f0e0c95e7ad249deb9359691d109bd8h','2024-02-21 09:05:02.936174','2024-02-21 09:05:13.494713','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7',1,'COURSE_ORDER','课程订单',1012,'订单类型','ORDER_TYPE',0,1),('f0e0c95e7ad249deb9359691d109dd8h','2024-02-21 09:05:02.936174','2024-02-21 09:05:13.494713','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7',0,'PRODUCT_ORDER','商品订单',1012,'订单类型','ORDER_TYPE',0,1),('f4e0c22e7ad249dzb2359691d109bd8h','2024-02-21 09:05:02.936174','2024-02-21 09:05:13.494713','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7',2,'SERVICE_ORDER','服务订单',1012,'订单类型','ORDER_TYPE',0,1),('fc930d380612121791ab809a5be03651','2024-01-26 11:02:08.987958','2024-01-26 11:02:22.277984','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7',0,'NORMAL','正常',1003,'用户状态','USER_STATUS',0,1),('fc930d380612421791ab809a5be03656','2024-01-26 11:02:08.987958','2024-01-26 11:02:22.277984','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7',1,'TO_BE_ACCEPT','待接单',1011,'跑腿订单状态','TASK_ORDER_STATUS',0,1),('fc930d380622121791ab809a5be03651','2024-01-26 11:02:08.987958','2024-01-26 11:02:22.277984','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7',1,'BANNED','封禁',1003,'用户状态','USER_STATUS',0,1);
/*!40000 ALTER TABLE `dict` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `feedback`
--

DROP TABLE IF EXISTS `feedback`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `feedback` (
  `id` varchar(36) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `creator_id` varchar(36) NOT NULL,
  `editor_id` varchar(36) NOT NULL,
  `content` varchar(2000) NOT NULL,
  `pictures` varchar(2000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `feedback`
--

LOCK TABLES `feedback` WRITE;
/*!40000 ALTER TABLE `feedback` DISABLE KEYS */;
INSERT INTO `feedback` VALUES ('2f8af146fe1b4fd6a792fdf2f6c57f38','2025-04-06 19:12:21.263513','2025-04-06 19:12:21.263513','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','123','[\"https://flash-help.oss-cn-beijing.aliyuncs.com/20250406191212XOkqsgCouH6t402956e7019e4f818f49f113b6e5eecc.jpg\",\"https://flash-help.oss-cn-beijing.aliyuncs.com/20250406191215nVzVS5qI9Ai66f579d73f8f34247c2880107661d5400.jpg\"]');
/*!40000 ALTER TABLE `feedback` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `listing`
--

DROP TABLE IF EXISTS `listing`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `listing` (
  `id` varchar(36) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `creator_id` varchar(36) NOT NULL,
  `editor_id` varchar(36) NOT NULL,
  `name` varchar(255) NOT NULL,
  `user_id` varchar(36) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='任务列表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `listing`
--

LOCK TABLES `listing` WRITE;
/*!40000 ALTER TABLE `listing` DISABLE KEYS */;
INSERT INTO `listing` VALUES ('2b1f819fc2064cceb38754d0ca0916a8','2025-06-04 21:53:03.819065','2025-06-04 21:53:03.819065','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','起凡测试','0f07d638f1bc401188d86dc650ab06a7'),('7c870f93e70d46eabc6c4226f09dade5','2025-06-04 21:59:10.584459','2025-06-05 21:40:22.766017','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','测试2222112','123'),('ab9a47cd4bb8439495f6eabe4d6808f5','2025-06-21 10:31:50.271076','2025-06-21 10:31:50.271076','5ee14b91481d4bc9a41a0c3d6c5b40c4','5ee14b91481d4bc9a41a0c3d6c5b40c4','Java后端学习计划','5ee14b91481d4bc9a41a0c3d6c5b40c4');
/*!40000 ALTER TABLE `listing` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `menu`
--

DROP TABLE IF EXISTS `menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `menu` (
  `id` varchar(36) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `creator_id` varchar(36) NOT NULL,
  `editor_id` varchar(36) NOT NULL,
  `name` varchar(20) NOT NULL,
  `path` varchar(2000) NOT NULL,
  `parent_id` varchar(36) DEFAULT NULL,
  `order_num` int DEFAULT NULL,
  `menu_type` varchar(36) NOT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `visible` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menu`
--

LOCK TABLES `menu` WRITE;
/*!40000 ALTER TABLE `menu` DISABLE KEYS */;
INSERT INTO `menu` VALUES ('00f2290a-cde5-4e95-9294-4e7599b1a3f5','2024-01-16 11:21:02.655367','2025-03-22 23:21:00.133578','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','权限管理','/permission',NULL,1,'DIRECTORY','Lock',1),('036981ee5b084fc3a5d2db7226a764bb','2025-06-07 17:07:43.312071','2025-06-07 17:13:09.309402','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','签到情况','/sign',NULL,9,'PAGE',NULL,1),('0c30e2f6-8cef-4ee6-999e-a18af97a3a26','2024-01-16 14:33:09.062999','2024-11-11 22:04:13.495447','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','用户','/user','00f2290a-cde5-4e95-9294-4e7599b1a3f5',1,'PAGE','',1),('1ffb0dc7920c4d8abf7b87acba894ad9','2025-06-07 17:10:51.039717','2025-06-07 17:12:44.584725','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','AI','/ai',NULL,7,'DIRECTORY','ChatDotRound',1),('349b39a5-9500-4c22-9972-f22b935aba42','2024-01-17 10:12:54.930459','2024-11-11 22:04:22.549994','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','角色','/role','00f2290a-cde5-4e95-9294-4e7599b1a3f5',2,'PAGE','',1),('4a043c90ac3c48b28fcebefc096af141','2025-04-06 18:50:30.937642','2025-04-06 18:50:30.937642','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','反馈','/feedback','a350090e-11ff-45d0-88ce-5c3c1b6f9ef4',0,'PAGE','Message',1),('5059d3a529f64cc594a11818b6fbeb21','2025-06-07 17:11:07.144492','2025-06-07 17:11:07.144492','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','会话','/ai-session','1ffb0dc7920c4d8abf7b87acba894ad9',0,'PAGE',NULL,1),('5b074c46544749a597004f04a8d9a82d','2025-06-07 17:11:19.888340','2025-06-07 17:11:19.888340','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','消息','/ai-message','1ffb0dc7920c4d8abf7b87acba894ad9',1,'PAGE',NULL,1),('787d129e4a7149f49f65194834289569','2025-06-07 17:07:14.854373','2025-06-07 17:13:14.439670','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','任务列表','/task',NULL,10,'PAGE',NULL,1),('876c1d44f85c441d83377127124a4662','2025-03-22 23:19:47.917415','2025-03-22 23:21:07.088061','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','首页','/home',NULL,0,'PAGE','House',1),('8ea32b3d-90e4-4d76-a777-413917166a32','2024-01-16 11:20:24.133270','2024-11-11 22:04:31.395846','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','菜单','/menu','00f2290a-cde5-4e95-9294-4e7599b1a3f5',3,'PAGE','',1),('9002b4faff164e2fb728afe89601a474','2024-07-24 21:26:26.408181','2025-04-06 18:51:24.845886','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','轮播图详情','/slideshow-details','a350090e-11ff-45d0-88ce-5c3c1b6f9ef4',NULL,'PAGE',NULL,0),('92f1711903424d18854db35d45f91c3c','2025-06-07 17:04:29.278132','2025-06-07 17:04:29.278132','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','评论','/post-comment','abbbf112468545d09d835a69cc9515fb',1,'PAGE',NULL,1),('9427abf49abe436dad289a2dd44086c1','2025-03-21 22:53:59.463960','2025-04-06 18:51:08.624706','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','公告管理','/notice','a350090e-11ff-45d0-88ce-5c3c1b6f9ef4',6,'PAGE',NULL,0),('95663185b0e1448ebc746471858ebfe0','2024-07-24 21:26:06.204920','2025-04-06 18:51:15.609821','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','轮播图','/slideshow','a350090e-11ff-45d0-88ce-5c3c1b6f9ef4',2,'PAGE',NULL,1),('a350090e-11ff-45d0-88ce-5c3c1b6f9ef4','2024-01-26 10:06:09.672544','2025-04-06 18:53:01.593518','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','系统管理','/system',NULL,0,'DIRECTORY','Setting',1),('a752db2fa060447ab09c8b14cbf0a65f','2025-03-21 22:54:14.377138','2025-04-06 18:51:03.235470','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','公告详情','/notice-details','a350090e-11ff-45d0-88ce-5c3c1b6f9ef4',0,'PAGE',NULL,0),('abbbf112468545d09d835a69cc9515fb','2025-06-07 17:03:58.246325','2025-06-07 17:12:53.227052','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','社区','/community',NULL,8,'DIRECTORY','Comment',1),('cf43dc7ef86f4b1284f8f342413d4d24','2025-06-07 17:04:12.528370','2025-06-07 17:04:12.528370','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','帖子','/post','abbbf112468545d09d835a69cc9515fb',0,'PAGE',NULL,1),('fcf86780-d447-4a60-96b8-4f03c4eddc92','2024-01-26 10:07:04.713450','2025-03-21 20:39:31.257713','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','字典','/dict','a350090e-11ff-45d0-88ce-5c3c1b6f9ef4',0,'PAGE','',0);
/*!40000 ALTER TABLE `menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notice`
--

DROP TABLE IF EXISTS `notice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notice` (
  `id` varchar(36) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `creator_id` varchar(36) NOT NULL,
  `editor_id` varchar(36) NOT NULL,
  `title` varchar(255) NOT NULL,
  `content` varchar(2000) NOT NULL,
  `active` tinyint(1) NOT NULL,
  `classroom_id` varchar(36) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='公告';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notice`
--

LOCK TABLES `notice` WRITE;
/*!40000 ALTER TABLE `notice` DISABLE KEYS */;
/*!40000 ALTER TABLE `notice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post`
--

DROP TABLE IF EXISTS `post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `post` (
  `id` varchar(36) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `creator_id` varchar(32) NOT NULL,
  `editor_id` varchar(32) NOT NULL,
  `pictures` json DEFAULT NULL,
  `content` varchar(2000) DEFAULT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='帖子';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post`
--

LOCK TABLES `post` WRITE;
/*!40000 ALTER TABLE `post` DISABLE KEYS */;
INSERT INTO `post` VALUES ('212a911116f6449bb92e68e1eb527080','2025-06-21 11:02:35.318988','2025-06-21 11:02:35.318988','5ee14b91481d4bc9a41a0c3d6c5b40c4','5ee14b91481d4bc9a41a0c3d6c5b40c4','[\"https://flash-help.oss-cn-beijing.aliyuncs.com/202506211102322vecKqOhm9sC126acfed4af648500ec9fc2a592b5bb2.png\"]','为什么这个代码跑不起来？',0),('790d3a69baec41ef8d0670fac9b395b7','2025-04-12 10:25:30.738502','2025-04-12 10:25:30.738502','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','[\"https://flash-help.oss-cn-beijing.aliyuncs.com/20250412102529HAeWPsyNawQp37b0bcee28b5cd20051b4d786d840b1d.png\"]','测试123',0);
/*!40000 ALTER TABLE `post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post_comment`
--

DROP TABLE IF EXISTS `post_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `post_comment` (
  `id` varchar(36) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `creator_id` varchar(32) NOT NULL,
  `editor_id` varchar(32) NOT NULL,
  `post_id` varchar(36) NOT NULL,
  `content` varchar(200) NOT NULL,
  `parent_id` varchar(36) DEFAULT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='帖子评论';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post_comment`
--

LOCK TABLES `post_comment` WRITE;
/*!40000 ALTER TABLE `post_comment` DISABLE KEYS */;
INSERT INTO `post_comment` VALUES ('05bc0c8e1b7743a886bf7e79744fdb32','2025-04-12 10:25:50.832545','2025-04-12 10:25:50.832545','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','790d3a69baec41ef8d0670fac9b395b7','哈哈哈哈哈',NULL,0),('5cfcc7c0369d4cc898dc7434fbee8e04','2025-06-21 11:03:21.721045','2025-06-21 11:03:21.721045','5ee14b91481d4bc9a41a0c3d6c5b40c4','5ee14b91481d4bc9a41a0c3d6c5b40c4','212a911116f6449bb92e68e1eb527080','哦哦哦，明白了谢谢你','939db5d816f14eb9a4a18f42d05d263a',0),('939db5d816f14eb9a4a18f42d05d263a','2025-06-21 11:03:02.120469','2025-06-21 11:03:02.120469','5ee14b91481d4bc9a41a0c3d6c5b40c4','5ee14b91481d4bc9a41a0c3d6c5b40c4','212a911116f6449bb92e68e1eb527080','mysql的密码可能配置错了',NULL,0);
/*!40000 ALTER TABLE `post_comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post_like`
--

DROP TABLE IF EXISTS `post_like`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `post_like` (
  `id` varchar(36) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `creator_id` varchar(32) NOT NULL,
  `editor_id` varchar(32) NOT NULL,
  `post_id` varchar(36) NOT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='帖子点赞';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post_like`
--

LOCK TABLES `post_like` WRITE;
/*!40000 ALTER TABLE `post_like` DISABLE KEYS */;
INSERT INTO `post_like` VALUES ('0ee7680d45934eb09892361919f11f77','2025-06-05 23:46:08.919022','2025-06-05 23:46:08.919022','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','790d3a69baec41ef8d0670fac9b395b7',0),('2f2777d2748b4da68223c73dfac9299e','2025-06-21 11:02:44.300084','2025-06-21 11:02:44.300084','5ee14b91481d4bc9a41a0c3d6c5b40c4','5ee14b91481d4bc9a41a0c3d6c5b40c4','212a911116f6449bb92e68e1eb527080',0);
/*!40000 ALTER TABLE `post_like` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` varchar(36) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `creator_id` varchar(36) NOT NULL,
  `editor_id` varchar(36) NOT NULL,
  `name` varchar(36) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `role_pk` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES ('5f785900-d317-4210-979d-d17a40ba8ecc','2024-01-15 14:22:57.274513','2025-06-07 17:11:39.989598','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','管理员');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_menu_rel`
--

DROP TABLE IF EXISTS `role_menu_rel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role_menu_rel` (
  `id` varchar(36) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `creator_id` varchar(36) NOT NULL,
  `editor_id` varchar(36) NOT NULL,
  `role_id` varchar(36) NOT NULL,
  `menu_id` varchar(36) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `role_id` (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_menu_rel`
--

LOCK TABLES `role_menu_rel` WRITE;
/*!40000 ALTER TABLE `role_menu_rel` DISABLE KEYS */;
INSERT INTO `role_menu_rel` VALUES ('01920113b8904fa2b8857620d8dd06b3','2025-04-10 22:38:41.309273','2025-06-07 17:11:40.020336','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','5f785900-d317-4210-979d-d17a40ba8ecc','95663185b0e1448ebc746471858ebfe0'),('0dea9143-96a7-4ada-9158-84df2d9a0d8a','2024-01-17 10:37:02.842166','2025-06-07 17:11:40.020336','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','5f785900-d317-4210-979d-d17a40ba8ecc','349b39a5-9500-4c22-9972-f22b935aba42'),('108b7aeb6f444d3ca755ba7637176ff5','2025-06-07 17:11:40.020336','2025-06-07 17:11:40.020336','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','5f785900-d317-4210-979d-d17a40ba8ecc','cf43dc7ef86f4b1284f8f342413d4d24'),('2a631d918c7d4aebbffb2f2290fa8abe','2025-06-07 17:11:40.020336','2025-06-07 17:11:40.020336','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','5f785900-d317-4210-979d-d17a40ba8ecc','1ffb0dc7920c4d8abf7b87acba894ad9'),('36090df96a144d189517f95ee54c4849','2025-06-07 17:11:40.020336','2025-06-07 17:11:40.020336','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','5f785900-d317-4210-979d-d17a40ba8ecc','5b074c46544749a597004f04a8d9a82d'),('4977d1b0-2e43-4457-9a9c-e99043d3b9ef','2024-01-17 09:13:36.304465','2025-06-07 17:11:40.020336','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','5f785900-d317-4210-979d-d17a40ba8ecc','00f2290a-cde5-4e95-9294-4e7599b1a3f5'),('569c7b5143a844a4a369b870057a5884','2025-06-07 17:11:40.020336','2025-06-07 17:11:40.020336','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','5f785900-d317-4210-979d-d17a40ba8ecc','787d129e4a7149f49f65194834289569'),('6759cc728d8646eca30b09d440bdcfea','2025-06-07 17:11:40.020336','2025-06-07 17:11:40.020336','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','5f785900-d317-4210-979d-d17a40ba8ecc','abbbf112468545d09d835a69cc9515fb'),('680532bf-c941-4cbc-88a1-47a55283f1a9','2024-01-16 15:51:42.853546','2024-01-16 15:51:42.853546','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','da8163bd-67c0-4263-b075-e39d7e595ca9','8ea32b3d-90e4-4d76-a777-413917166a32'),('7d0eb7c6035043abb23af98a6d3ca8d5','2025-06-07 17:11:40.020336','2025-06-07 17:11:40.020336','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','5f785900-d317-4210-979d-d17a40ba8ecc','036981ee5b084fc3a5d2db7226a764bb'),('7df2f23785bf47b49fff200ea3fab23a','2025-04-10 22:38:41.325677','2025-06-07 17:11:40.020336','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','5f785900-d317-4210-979d-d17a40ba8ecc','a752db2fa060447ab09c8b14cbf0a65f'),('9829466a7cd2437598baf83c08f64ddc','2025-04-10 22:38:41.325677','2025-06-07 17:11:40.020336','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','5f785900-d317-4210-979d-d17a40ba8ecc','9002b4faff164e2fb728afe89601a474'),('9c47cfc6-8333-4cac-bc0e-a027f5dbacd6','2024-01-17 09:13:40.638499','2025-06-07 17:11:40.020336','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','5f785900-d317-4210-979d-d17a40ba8ecc','8ea32b3d-90e4-4d76-a777-413917166a32'),('a2a38db103ab4197b5ce945b647b5227','2025-06-07 17:11:40.020336','2025-06-07 17:11:40.020336','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','5f785900-d317-4210-979d-d17a40ba8ecc','5059d3a529f64cc594a11818b6fbeb21'),('b40483601e244393a0fc512858ead02c','2025-04-10 22:38:41.325677','2025-06-07 17:11:40.020336','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','5f785900-d317-4210-979d-d17a40ba8ecc','9427abf49abe436dad289a2dd44086c1'),('b86d8926e4ea415591baa15fc95be3f6','2025-03-22 23:20:27.567390','2025-06-07 17:11:40.020336','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','5f785900-d317-4210-979d-d17a40ba8ecc','876c1d44f85c441d83377127124a4662'),('be1ad609-7c29-4f15-af1b-fcb2443e62c0','2024-01-26 10:07:13.130646','2025-06-07 17:11:40.020336','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','5f785900-d317-4210-979d-d17a40ba8ecc','a350090e-11ff-45d0-88ce-5c3c1b6f9ef4'),('c0966b54-bc59-4963-9c3d-d3d68a8b2ce0','2024-01-17 16:42:52.109438','2025-06-07 17:11:40.020336','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','5f785900-d317-4210-979d-d17a40ba8ecc','0c30e2f6-8cef-4ee6-999e-a18af97a3a26'),('c567bd4ba13d4bc0add1d7904a645971','2025-04-06 18:50:38.256889','2025-06-07 17:11:40.020336','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','5f785900-d317-4210-979d-d17a40ba8ecc','4a043c90ac3c48b28fcebefc096af141'),('c960432c-a191-4630-9902-540cefb992f2','2024-01-16 20:51:34.270707','2024-01-16 20:51:34.270707','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','da8163bd-67c0-4263-b075-e39d7e595ca9','00f2290a-cde5-4e95-9294-4e7599b1a3f5'),('d3f5aa75-dbf7-40b9-986c-dde2edf14b08','2024-01-16 20:51:45.829499','2024-01-16 20:51:45.829499','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','da8163bd-67c0-4263-b075-e39d7e595ca9','0c30e2f6-8cef-4ee6-999e-a18af97a3a26'),('e648d18814864a478e1a4240dac4ae8d','2025-06-07 17:11:40.020336','2025-06-07 17:11:40.020336','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','5f785900-d317-4210-979d-d17a40ba8ecc','92f1711903424d18854db35d45f91c3c');
/*!40000 ALTER TABLE `role_menu_rel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sign`
--

DROP TABLE IF EXISTS `sign`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sign` (
  `id` varchar(36) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `creator_id` varchar(32) NOT NULL,
  `editor_id` varchar(32) NOT NULL,
  `user_id` varchar(32) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='签到';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sign`
--

LOCK TABLES `sign` WRITE;
/*!40000 ALTER TABLE `sign` DISABLE KEYS */;
INSERT INTO `sign` VALUES ('168c8e8d08474669ba7f055334c18d7c','2025-06-05 22:34:58.159377','2025-06-05 22:34:58.159377','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7'),('9029fbf48f7e4302ba926ded2ec08bde','2025-06-21 11:04:44.223936','2025-06-21 11:04:44.223936','5ee14b91481d4bc9a41a0c3d6c5b40c4','5ee14b91481d4bc9a41a0c3d6c5b40c4','5ee14b91481d4bc9a41a0c3d6c5b40c4');
/*!40000 ALTER TABLE `sign` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `slideshow`
--

DROP TABLE IF EXISTS `slideshow`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `slideshow` (
  `id` varchar(36) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `creator_id` varchar(36) NOT NULL,
  `editor_id` varchar(36) NOT NULL,
  `picture` varchar(255) NOT NULL COMMENT '公告图片',
  `content` varchar(255) NOT NULL COMMENT '公告内容',
  `navigator_id` varchar(32) DEFAULT NULL,
  `navigator_type` varchar(32) DEFAULT NULL,
  `sort` int NOT NULL,
  `valid` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `slideshow`
--

LOCK TABLES `slideshow` WRITE;
/*!40000 ALTER TABLE `slideshow` DISABLE KEYS */;
INSERT INTO `slideshow` VALUES ('4d8ccd657f8248028961378f2b7d37ed','2025-03-22 23:25:51.749533','2025-03-22 23:25:51.749533','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','https://flash-help.oss-cn-beijing.aliyuncs.com/20250322232521background.jpg','哈哈哈哈',NULL,NULL,0,1);
/*!40000 ALTER TABLE `slideshow` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `task`
--

DROP TABLE IF EXISTS `task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `task` (
  `id` varchar(36) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `creator_id` varchar(36) NOT NULL,
  `editor_id` varchar(36) NOT NULL,
  `name` varchar(255) NOT NULL COMMENT '任务名称',
  `finish_time` datetime(6) DEFAULT NULL COMMENT '完成时间',
  `remind_time` datetime(6) DEFAULT NULL COMMENT '提醒时间',
  `files` json DEFAULT NULL COMMENT '附件',
  `user_id` varchar(36) NOT NULL COMMENT '用户id',
  `checked` tinyint(1) DEFAULT NULL COMMENT '是否完成',
  `listing_id` varchar(32) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='任务';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task`
--

LOCK TABLES `task` WRITE;
/*!40000 ALTER TABLE `task` DISABLE KEYS */;
INSERT INTO `task` VALUES ('1304cbaac9eb44eca219c7aaae1d6722','2025-06-21 10:32:38.312770','2025-06-21 10:48:26.088715','5ee14b91481d4bc9a41a0c3d6c5b40c4','5ee14b91481d4bc9a41a0c3d6c5b40c4','学习IO流','2025-06-23 10:30:00.000000','2025-06-21 10:30:00.000000','[]','5ee14b91481d4bc9a41a0c3d6c5b40c4',1,'ab9a47cd4bb8439495f6eabe4d6808f5'),('63a30403f9794f5e99dd45d23f64ed09','2025-06-21 10:49:58.429075','2025-06-21 10:49:58.429075','5ee14b91481d4bc9a41a0c3d6c5b40c4','5ee14b91481d4bc9a41a0c3d6c5b40c4','学习多线程','2025-06-28 10:45:00.000000','2025-06-25 10:45:00.000000','[]','5ee14b91481d4bc9a41a0c3d6c5b40c4',NULL,'ab9a47cd4bb8439495f6eabe4d6808f5'),('de177659bb3246f0aaa7d5e5eb94a16e','2025-06-05 21:17:11.681080','2025-06-05 21:43:34.073400','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','测试1','2025-06-05 21:15:00.000000','2025-06-05 21:15:00.000000','[{\"uid\": 0, \"url\": \"https://flash-help.oss-cn-beijing.aliyuncs.com/20250605211701QC4fuo48E23q9b924c22575497175368f987e4f2911e.png\", \"name\": \"http://tmp/QC4fuo48E23q9b924c22575497175368f987e4f2911e.png\", \"size\": null, \"type\": \"image\", \"status\": \"success\", \"percentage\": null}]','0f07d638f1bc401188d86dc650ab06a7',NULL,'7c870f93e70d46eabc6c4226f09dade5');
/*!40000 ALTER TABLE `task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `task_step`
--

DROP TABLE IF EXISTS `task_step`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `task_step` (
  `id` varchar(36) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `creator_id` varchar(36) NOT NULL,
  `editor_id` varchar(36) NOT NULL,
  `name` varchar(255) NOT NULL COMMENT '步骤名称',
  `checked` tinyint(1) DEFAULT NULL COMMENT '是否完成',
  `task_id` varchar(36) NOT NULL COMMENT '任务id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='任务步骤';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task_step`
--

LOCK TABLES `task_step` WRITE;
/*!40000 ALTER TABLE `task_step` DISABLE KEYS */;
INSERT INTO `task_step` VALUES ('107deda6b04845cc87605626d216785e','2025-06-21 10:48:26.122072','2025-06-21 10:48:26.122072','5ee14b91481d4bc9a41a0c3d6c5b40c4','5ee14b91481d4bc9a41a0c3d6c5b40c4','FileSteam实现文件独写',0,'1304cbaac9eb44eca219c7aaae1d6722'),('4cd09d42feca46fd88ab8876e87a6dff','2025-06-05 00:19:59.173911','2025-06-05 00:19:59.173911','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','测试111',1,'bfed67fa2d514b19a60eda8498848892'),('6417cb1eb7074d608f73e8e49814f4d6','2025-06-05 21:17:11.741771','2025-06-05 21:43:34.098812','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','测试步骤1',1,'de177659bb3246f0aaa7d5e5eb94a16e'),('6c34260d86a543cc92ddf8fa88fbe32e','2025-06-21 10:48:26.122072','2025-06-21 10:48:26.122072','5ee14b91481d4bc9a41a0c3d6c5b40c4','5ee14b91481d4bc9a41a0c3d6c5b40c4','ByteArraySteam实现读取二进制到内存再输出',0,'1304cbaac9eb44eca219c7aaae1d6722'),('7dffc00990b5490b971f18ddd259ec38','2025-06-21 10:49:58.437172','2025-06-21 10:49:58.437172','5ee14b91481d4bc9a41a0c3d6c5b40c4','5ee14b91481d4bc9a41a0c3d6c5b40c4','看一些多线程的面试题',0,'63a30403f9794f5e99dd45d23f64ed09'),('7fa67d88de1845b38a21992aabb83383','2025-06-05 21:43:27.432363','2025-06-05 21:43:34.099821','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','测试333',1,'de177659bb3246f0aaa7d5e5eb94a16e'),('8b876a3df5fa41a791aa20aa60861a57','2025-06-21 10:49:58.437172','2025-06-21 10:49:58.437172','5ee14b91481d4bc9a41a0c3d6c5b40c4','5ee14b91481d4bc9a41a0c3d6c5b40c4','学习Thread和线程池',0,'63a30403f9794f5e99dd45d23f64ed09'),('8dce54b355d94804ad5ce8daa299acf7','2025-06-05 21:17:11.741771','2025-06-05 21:43:34.099821','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','测试步骤2',1,'de177659bb3246f0aaa7d5e5eb94a16e'),('d88c37629f8c4783a22f7ce1d2c6866b','2025-06-21 10:49:58.437172','2025-06-21 10:49:58.437172','5ee14b91481d4bc9a41a0c3d6c5b40c4','5ee14b91481d4bc9a41a0c3d6c5b40c4','学习concurrent包相关的内容',0,'63a30403f9794f5e99dd45d23f64ed09');
/*!40000 ALTER TABLE `task_step` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` varchar(36) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `nickname` varchar(20) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `gender` varchar(36) DEFAULT NULL,
  `phone` varchar(20) NOT NULL,
  `password` varchar(100) NOT NULL,
  `status` varchar(32) NOT NULL COMMENT '用户状态',
  PRIMARY KEY (`id`),
  UNIQUE KEY `phone` (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('0f07d638f1bc401188d86dc650ab06a7','2024-01-10 10:48:02.758735','2025-04-06 19:25:23.841768','起凡','https://bottle-post.oss-cn-guangzhou.aliyuncs.com/20241212200253tmp_11d616da0b6525682e41821abad8cb86ba99319acd538c626eefe9cd8f981516.jpeg','MALE','13656987994','$2a$10$pl/GmO3mDaqWjBtmfXzppOFQwnW/jlinORR6.83Lo7QdTuU4uh5AG','NORMAL'),('35859366a401400c9c0557d9b97f249b','2024-12-12 21:28:11.887043','2025-03-16 22:29:53.981380','风吹未眠','https://bottle-post.oss-cn-guangzhou.aliyuncs.com/202412122128170I99WPW1qnPdf54faee9d132f1ff724064c72cd9aad9.jpeg','FEMALE','18050563383','$2a$10$o/8jLIWMe8zNzr1C7idjeeQCweLvQ8cSKMCfKPoR/dluLMl7TJOje','NORMAL'),('5ee14b91481d4bc9a41a0c3d6c5b40c4','2025-06-21 10:05:56.127101','2025-06-21 10:07:19.084910','起凡','https://flash-help.oss-cn-beijing.aliyuncs.com/20250621100714ZlTuopl7lC8r3e744c849f0d33763b27e96a9d09be98.jpeg','FEMALE','44666619350','123456','NORMAL');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role_rel`
--

DROP TABLE IF EXISTS `user_role_rel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_role_rel` (
  `id` varchar(36) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `creator_id` varchar(36) NOT NULL,
  `editor_id` varchar(36) NOT NULL,
  `role_id` varchar(36) NOT NULL,
  `user_id` varchar(36) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `role_id` (`role_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role_rel`
--

LOCK TABLES `user_role_rel` WRITE;
/*!40000 ALTER TABLE `user_role_rel` DISABLE KEYS */;
INSERT INTO `user_role_rel` VALUES ('c61c1c7f-e845-4e6d-93f4-26f9d76a82fa','2024-01-17 08:44:26.417028','2025-04-06 19:25:23.848278','0f07d638f1bc401188d86dc650ab06a7','0f07d638f1bc401188d86dc650ab06a7','5f785900-d317-4210-979d-d17a40ba8ecc','0f07d638f1bc401188d86dc650ab06a7');
/*!40000 ALTER TABLE `user_role_rel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_we_chat`
--

DROP TABLE IF EXISTS `user_we_chat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_we_chat` (
  `id` varchar(36) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `open_id` varchar(30) NOT NULL,
  `user_id` varchar(36) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `open_id` (`open_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_we_chat`
--

LOCK TABLES `user_we_chat` WRITE;
/*!40000 ALTER TABLE `user_we_chat` DISABLE KEYS */;
INSERT INTO `user_we_chat` VALUES ('5b7e9efd41bb4f83b4f5fd94b7f403b4','2025-06-21 10:05:56.151110','2025-06-21 10:05:56.151110','oEheF5USRu6Y3qWjpb3wJPBfuejw','5ee14b91481d4bc9a41a0c3d6c5b40c4');
/*!40000 ALTER TABLE `user_we_chat` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-06-21 11:06:18
