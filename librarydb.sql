/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80015
 Source Host           : localhost:3306
 Source Schema         : librarydb

 Target Server Type    : MySQL
 Target Server Version : 80015
 File Encoding         : 65001

 Date: 08/07/2019 17:49:26
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admintable
-- ----------------------------
DROP TABLE IF EXISTS `admintable`;
CREATE TABLE `admintable`  (
  `admin_username` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `admin_password` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `authority` int(2) NOT NULL,
  `admin_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `admin_dept` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`admin_username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admintable
-- ----------------------------
INSERT INTO `admintable` VALUES ('400001', '123', 1, 'xh', '计算机');
INSERT INTO `admintable` VALUES ('xh', '123', 1, '谢昊', '软件');

-- ----------------------------
-- Table structure for booktable
-- ----------------------------
DROP TABLE IF EXISTS `booktable`;
CREATE TABLE `booktable`  (
  `book_number` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `book_name` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `book_author` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `book_publishtime` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `book_amount` int(2) NOT NULL,
  `admin_username` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`book_number`) USING BTREE,
  INDEX `Iadmin_username`(`admin_username`) USING BTREE,
  INDEX `book_name`(`book_name`) USING BTREE,
  CONSTRAINT `admin_username` FOREIGN KEY (`admin_username`) REFERENCES `admintable` (`admin_username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of booktable
-- ----------------------------
INSERT INTO `booktable` VALUES ('0001', '幸运', 'DOC.A', '2010/03/10', 10, 'xh');
INSERT INTO `booktable` VALUES ('0002', '围城', 'MR.A', '2017/06/01', 2, 'xh');
INSERT INTO `booktable` VALUES ('0003', '老人与海', 'TOM', '2018/12/02', 3, 'xh');
INSERT INTO `booktable` VALUES ('0004', '讲不完的故事', 'LIGHT', '2006/01/01', 4, 'xh');
INSERT INTO `booktable` VALUES ('0005', '不教胡马度阴山', 'SUKA', '2008/06/06', 12, '400001');
INSERT INTO `booktable` VALUES ('0006', '围殴', 'JIM', '2009/11/08', 5, 'xh');
INSERT INTO `booktable` VALUES ('0008', '围城2', 'DUDE', '2019/03/27', 7, 'xh');
INSERT INTO `booktable` VALUES ('0009', '救我嘤嘤嘤', 'MR.Xie', '2019/07/06', 3, '400001');
INSERT INTO `booktable` VALUES ('0012', '数据库最好玩啦', 'Teacher', '2019/07/08', 20, '400001');
INSERT INTO `booktable` VALUES ('0021', '养生的智慧', 'Life', '2011/08/17', 5, 'xh');
INSERT INTO `booktable` VALUES ('0034', '星际旅行', 'GO', '2014/04/26', 9, '400001');
INSERT INTO `booktable` VALUES ('0066', '一个人的世界', 'MR.Lonely', '2015/02/12', 1, 'xh');
INSERT INTO `booktable` VALUES ('0072', '凑个长度', 'XieHao', '2019/09/03', 31, 'xh');
INSERT INTO `booktable` VALUES ('0089', '2012', 'MISS.Video', '2012/12/12', 12, 'xh');
INSERT INTO `booktable` VALUES ('0092', '爱你摸摸哒', 'Dr.Love', '2019/09/11', 99, '400001');
INSERT INTO `booktable` VALUES ('0098', 'Dream', 'Doc.Love', '2019/09/02', 100, 'xh');
INSERT INTO `booktable` VALUES ('0233', '啊呜啊呜', 'MIS.Wen', '2019/07/08', 2, 'xh');
INSERT INTO `booktable` VALUES ('0706', '学习与play', 'Heart', '2017/09/05', 15, '400001');
INSERT INTO `booktable` VALUES ('1020', '我想得优', 'MR.Xie', '2019/07/08', 1, 'xh');
INSERT INTO `booktable` VALUES ('2323', 'JustDoIt!', 'DoIt', '2001/01/18', 7, 'xh');

-- ----------------------------
-- Table structure for borrowtable
-- ----------------------------
DROP TABLE IF EXISTS `borrowtable`;
CREATE TABLE `borrowtable`  (
  `borrow_reader_username` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `borrow_book_number` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `borrow_book_name` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `borrow_time` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`borrow_reader_username`, `borrow_book_number`) USING BTREE,
  INDEX `borrow_reader_username`(`borrow_reader_username`) USING BTREE,
  INDEX `borrow_book_num`(`borrow_book_number`) USING BTREE,
  CONSTRAINT `borrow_book_num` FOREIGN KEY (`borrow_book_number`) REFERENCES `booktable` (`book_number`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `borrow_reader_username` FOREIGN KEY (`borrow_reader_username`) REFERENCES `readertable` (`reader_username`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of borrowtable
-- ----------------------------
INSERT INTO `borrowtable` VALUES ('300001', '0006', '围城', '2019-07-02');
INSERT INTO `borrowtable` VALUES ('300001', '0008', '围城2', '2019-07-08');
INSERT INTO `borrowtable` VALUES ('300002', '0003', '老人与海', '2019-07-08');

-- ----------------------------
-- Table structure for browserhistorytable
-- ----------------------------
DROP TABLE IF EXISTS `browserhistorytable`;
CREATE TABLE `browserhistorytable`  (
  `browser_reader_username` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `browser_bookname` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `browser_time` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  INDEX `browser_reader_username`(`browser_reader_username`) USING BTREE,
  INDEX `browser_bookname`(`browser_bookname`) USING BTREE,
  CONSTRAINT `browser_reader_username` FOREIGN KEY (`browser_reader_username`) REFERENCES `readertable` (`reader_username`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `browser_bookname` FOREIGN KEY (`browser_bookname`) REFERENCES `booktable` (`book_name`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of browserhistorytable
-- ----------------------------
INSERT INTO `browserhistorytable` VALUES ('xh', '幸运', '2019-07-08');
INSERT INTO `browserhistorytable` VALUES ('xh', '2012', '2019-07-08');
INSERT INTO `browserhistorytable` VALUES ('xh', '幸运', '2019-07-08');
INSERT INTO `browserhistorytable` VALUES ('xh', '数据库最好玩啦', '2019-07-08');
INSERT INTO `browserhistorytable` VALUES ('xh', '养生的智慧', '2019-07-08');
INSERT INTO `browserhistorytable` VALUES ('xh', '我想得优', '2019-07-08');
INSERT INTO `browserhistorytable` VALUES ('300001', '2012', '2019-07-08');
INSERT INTO `browserhistorytable` VALUES ('300001', '幸运', '2019-07-08');
INSERT INTO `browserhistorytable` VALUES ('300001', '数据库最好玩啦', '2019-07-08');
INSERT INTO `browserhistorytable` VALUES ('300001', '养生的智慧', '2019-07-08');
INSERT INTO `browserhistorytable` VALUES ('300001', '我想得优', '2019-07-08');

-- ----------------------------
-- Table structure for historytable
-- ----------------------------
DROP TABLE IF EXISTS `historytable`;
CREATE TABLE `historytable`  (
  `history_username` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `history_book_number` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `history_book_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `history_borrow_time` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  INDEX `history_username`(`history_username`) USING BTREE,
  INDEX `history_book_number`(`history_book_number`) USING BTREE,
  CONSTRAINT `history_username` FOREIGN KEY (`history_username`) REFERENCES `readertable` (`reader_username`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `history_book_number` FOREIGN KEY (`history_book_number`) REFERENCES `booktable` (`book_number`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of historytable
-- ----------------------------
INSERT INTO `historytable` VALUES ('300001', '0001', '幸运', '2019-07-08');
INSERT INTO `historytable` VALUES ('300001', '0002', '围城', '2014-01-03');
INSERT INTO `historytable` VALUES ('300001', '0003', '老人与海', '2014-01-03');
INSERT INTO `historytable` VALUES ('300001', '0006', '围城', '2019-07-06');
INSERT INTO `historytable` VALUES ('300001', '0008', '围城2', '2019-07-08');
INSERT INTO `historytable` VALUES ('300002', '0003', '老人与海', '2019-07-08');
INSERT INTO `historytable` VALUES ('xh', '0001', '幸运', '2019-07-08');
INSERT INTO `historytable` VALUES ('300002', '0006', '围殴', '2019-07-08');

-- ----------------------------
-- Table structure for readertable
-- ----------------------------
DROP TABLE IF EXISTS `readertable`;
CREATE TABLE `readertable`  (
  `reader_username` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `reader_password` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `reader_name` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `authority` int(2) NOT NULL,
  `reader_dept` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `reader_borrow` int(2) NULL DEFAULT NULL,
  `reader_degree` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`reader_username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of readertable
-- ----------------------------
INSERT INTO `readertable` VALUES ('1', '1', '谢昊', 2, '软件工程', 0, '本科');
INSERT INTO `readertable` VALUES ('300001', '123', '光影1', 2, '软件工程', 0, '本科');
INSERT INTO `readertable` VALUES ('300002', '1234', '张三', 2, '外国语', 0, '研究生');
INSERT INTO `readertable` VALUES ('300003', '1234', '李四', 2, '信息工程', 0, '本科');
INSERT INTO `readertable` VALUES ('300004', '123', '张si', 2, '计算机学院', 0, '本科');
INSERT INTO `readertable` VALUES ('xh', '123', '谢昊', 2, '软件工程', 0, '本科');

-- ----------------------------
-- Table structure for returntable
-- ----------------------------
DROP TABLE IF EXISTS `returntable`;
CREATE TABLE `returntable`  (
  `reader_username` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `borrow_book_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `return_time` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `return_money` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `return_overtime` int(2) NOT NULL,
  `borrow_time` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  INDEX `user_name`(`reader_username`) USING BTREE,
  INDEX `borrow_book_name`(`borrow_book_name`) USING BTREE,
  CONSTRAINT `user_name` FOREIGN KEY (`reader_username`) REFERENCES `readertable` (`reader_username`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `borrow_book_name` FOREIGN KEY (`borrow_book_name`) REFERENCES `booktable` (`book_name`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of returntable
-- ----------------------------
INSERT INTO `returntable` VALUES ('300001', '老人与海', '2019-07-08', '0', 0, '2019-07-08');
INSERT INTO `returntable` VALUES ('300002', '围殴', '2019-07-08', '0', 0, '2019-07-08');

-- ----------------------------
-- Triggers structure for table borrowtable
-- ----------------------------
DROP TRIGGER IF EXISTS `Trig_borrow_delete`;
delimiter ;;
CREATE TRIGGER `Trig_borrow_delete` AFTER DELETE ON `borrowtable` FOR EACH ROW BEGIN
	
	declare daytime int;
	select (TO_DAYS(str_to_date(old.borrow_time, '%Y-%m-%d'))-TO_DAYS(now())) into daytime;
	IF(daytime>30) THEN
	INSERT INTO returntable(reader_username,borrow_book_name,return_time,return_money,return_overtime,borrow_time)
	VALUES(old.borrow_reader_username,old.borrow_book_name,date_format(now(), '%Y-%m-%d'),(daytime-30)*0.1,daytime-30,old.borrow_time);
	ELSE
	INSERT INTO returntable(reader_username,borrow_book_name,return_time,return_money,return_overtime,borrow_time)
	VALUES(old.borrow_reader_username,old.borrow_book_name,date_format(now(), '%Y-%m-%d'),0,0,old.borrow_time);
	END IF;

END
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
