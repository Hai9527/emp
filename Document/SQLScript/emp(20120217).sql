-- phpMyAdmin SQL Dump
-- version 3.4.5
-- http://www.phpmyadmin.net
--
-- 主机: localhost
-- 生成日期: 2012 年 02 月 17 日 10:11
-- 服务器版本: 5.5.16
-- PHP 版本: 5.3.1

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- 数据库: `emp`
--

-- --------------------------------------------------------

--
-- 表的结构 `emp_account`
--

CREATE TABLE IF NOT EXISTS `emp_account` (
  `acid` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `acname` varchar(200) DEFAULT NULL,
  `acfname` varchar(200) DEFAULT NULL,
  `aclname` varchar(200) DEFAULT NULL,
  `acemail` varchar(200) DEFAULT NULL,
  `acpasswd` varchar(200) NOT NULL,
  `acphone` varchar(200) DEFAULT NULL,
  `acstatus` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`acid`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

--
-- 转存表中的数据 `emp_account`
--

INSERT INTO `emp_account` (`acid`, `acname`, `acfname`, `aclname`, `acemail`, `acpasswd`, `acphone`, `acstatus`) VALUES
(1, 'admin', 'admin', 'admin', 'admin@fmldigital.com', '123456', '13533030942', 1);

-- --------------------------------------------------------

--
-- 表的结构 `emp_administrator`
--

CREATE TABLE IF NOT EXISTS `emp_administrator` (
  `amid` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `amusername` varchar(200) DEFAULT NULL,
  `ampassword` varchar(200) DEFAULT NULL,
  `amstatus` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`amid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `emp_application`
--

CREATE TABLE IF NOT EXISTS `emp_application` (
  `aid` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `acid` int(11) DEFAULT NULL,
  `aname` varchar(200) DEFAULT NULL,
  `aicon` varchar(200) DEFAULT NULL,
  `adescription` text,
  `amarket_icon` varchar(200) DEFAULT NULL,
  `aindex_icon` varchar(200) DEFAULT NULL,
  `aindex_image` varchar(200) DEFAULT NULL,
  `aindex_content` text NOT NULL,
  `aload_icon` varchar(200) DEFAULT NULL,
  `aload_image` varchar(200) DEFAULT NULL,
  `acreate_time` datetime DEFAULT NULL,
  `aversion` varchar(10) NOT NULL,
  `aupdate_time` datetime DEFAULT NULL,
  `aapproved_time` datetime NOT NULL,
  `astatus` tinyint(4) NOT NULL DEFAULT '0' COMMENT '-1:拒绝; 0:草稿; 1:提交; 2:发布',
  `aonline_link` varchar(200) NOT NULL,
  `atemplate` int(11) NOT NULL,
  `akey` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`aid`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=36 ;

--
-- 转存表中的数据 `emp_application`
--

INSERT INTO `emp_application` (`aid`, `acid`, `aname`, `aicon`, `adescription`, `amarket_icon`, `aindex_icon`, `aindex_image`, `aindex_content`, `aload_icon`, `aload_image`, `acreate_time`, `aversion`, `aupdate_time`, `aapproved_time`, `astatus`, `aonline_link`, `atemplate`, `akey`) VALUES
(1, 2, 'aaaa', 'aaaaa', '啊啊啊啊啊啊啊', '1.jpg', 'aaaaaaaaaaa', 'aaaaaaaaaaaaaaa', 'aaaaaaaaaaaaaaaaaaa', 'aaaaaaaaaaaaa', 'images/uploads/camera.jpg', '2012-02-14 13:59:03', '', '2012-01-10 00:00:00', '0000-00-00 00:00:00', 0, '', 1, 'NWFkOGNkNDY0OGZjYWRmYTM5MGJkYjViMjI5Mzk1'),
(2, NULL, 'ffffffffff', 'bbbbbbbbbbbbbbbbbbbb', '', 'ggggggggggggggggg', 'jjjjjjjjjjjjjjjjjjjjjj', 'jjjjjlllllllllllllllllll', 'lllllllllllllllllllllll', 'yyyyyyyyyyyyyyyyyyy', 'iiiiiiiiiiiiiiiiiiiiiiiiii', '2012-02-13 12:02:26', '', '2012-01-10 00:00:00', '0000-00-00 00:00:00', 0, '', 2, 'MGNlNzFiNzhjNTQ1NTYzMGZkZDY0ODM2NDQ0NDA0'),
(3, NULL, '', NULL, NULL, '', NULL, NULL, '', NULL, NULL, '2012-02-06 14:46:26', '', NULL, '0000-00-00 00:00:00', 0, '', 0, 'YzFiMzk3ZTgyZjVkOTdkOTQzYjM4OGJmM2NhNDc3'),
(5, NULL, '', NULL, NULL, '', NULL, NULL, '', NULL, NULL, '2012-02-06 15:03:42', '', NULL, '0000-00-00 00:00:00', 0, '', 0, 'OTM2MDUxOTZjOGViMzEyMDhkNTY0ODg1ZTRlYTkx'),
(6, NULL, '', NULL, NULL, '', NULL, NULL, '', NULL, NULL, '2012-02-06 15:19:30', '', NULL, '0000-00-00 00:00:00', 0, '', 0, 'NTFhMjJhMjhiNzQ4ZWUwMTU5YWJkNDQyMTFhYzM4'),
(7, NULL, '', NULL, NULL, '', NULL, NULL, '', NULL, NULL, '2012-02-06 15:20:13', '', NULL, '0000-00-00 00:00:00', 0, '', 0, 'NGI5NmFmYmUzMGIzNWE3MzMyMzRkZDAxOWQxZDkx'),
(8, NULL, '', NULL, NULL, '', NULL, NULL, '', NULL, NULL, '2012-02-06 15:20:14', '', NULL, '0000-00-00 00:00:00', 0, '', 0, 'YTk1MzQ0NzdjMGEyYTU5ODBlZjZkZWI3Y2IwMzNi'),
(9, NULL, 'ggggg', NULL, NULL, 'FFFFFF', NULL, NULL, '', NULL, NULL, '2012-02-06 15:20:32', '', NULL, '0000-00-00 00:00:00', 0, '', 0, 'ZDQ3ZWQxMzEwMzM1ZTJmMzRhYzA5OTVjNzIzOTVh'),
(10, NULL, 'newapp', NULL, 'content test,&nbsp;content test,&nbsp;content test,&nbsp;content test,&nbsp;content test,&nbsp;content test,&nbsp;content test,&nbsp;', 'icon', NULL, NULL, '', NULL, NULL, NULL, '', NULL, '0000-00-00 00:00:00', 0, '', 0, 'NGIwMzUwM2YzNWJiYzdlZmNkYzlmMjNlYzUyNzAz'),
(12, NULL, 'jjjjjjjjjjjjj', NULL, 'jjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj', 'jjjjjjjjjjjjjjjjjjjjjjjjjjj', NULL, NULL, '', NULL, NULL, '2012-02-14 12:46:24', '', NULL, '0000-00-00 00:00:00', 0, '', 0, 'Mzg4MWMxMDc2YThjMjlkZWEwNDJmYjFlYzg2NzZm'),
(14, NULL, 'hhhhhhhhhh', NULL, 'hhhhhhhhhhhhhhhhhhhhhhhh', 'hhhhhhhhhhhh', NULL, NULL, '', NULL, NULL, '2012-02-06 16:11:20', '', NULL, '0000-00-00 00:00:00', 0, '', 0, 'MDg2ZmNmNThjY2VhNzBkMjMzNTcxM2RlODY3MmZi'),
(15, NULL, 'qqqqqqqq', NULL, 'qqqqqqqqqqqqqqqqqqqqqqqqq', 'qqqqqqqq', NULL, NULL, '', NULL, NULL, '2012-02-06 16:11:51', '', NULL, '0000-00-00 00:00:00', 0, '', 0, 'ZjkzMzE5OWYxOWRhNThhZjVlMzg2MDEyNjExOGY4'),
(17, NULL, 'addias', NULL, 'pumapumapuma<img src="/EMP_Phase1/Webservice/images/attached/image/20120203/20120203033200_32450.jpg" alt="" />', 'nike', NULL, NULL, '', NULL, NULL, '2012-02-06 17:12:51', '', NULL, '0000-00-00 00:00:00', 0, '', 0, 'YjAzOWY0ZTI1Njk2NzE4ZmZmZDdlZGJlMDhlNTBk'),
(18, NULL, 'nike', NULL, 'nike nike nike nike nike nike nike', 'nike', NULL, NULL, '', NULL, NULL, '2012-02-06 17:14:37', '', NULL, '0000-00-00 00:00:00', 0, '', 0, 'NjE3YTM4ZjFjYjZhZjRiOTE2MzVlODJiZjQ5YmEz'),
(23, NULL, 'test', NULL, 'test1<br />', 'test', NULL, NULL, '', NULL, NULL, '2012-02-10 12:08:13', '', NULL, '0000-00-00 00:00:00', 0, '', 0, 'OWM3MjdhNjRjNjNhZTU5MzY0MDVlZDFlNDVmYzkx'),
(25, NULL, 'and', NULL, 'and<br />', 'and', NULL, NULL, '', NULL, NULL, '2012-02-10 12:10:37', '', NULL, '0000-00-00 00:00:00', 0, '', 0, 'YjQzY2VlZTU3ZjM4ZGZhYmFiNGYyMGE5MGIzZGYz'),
(29, NULL, 'winwin', 'images/uploads/(13).png', 'wing', '24.jpg', NULL, NULL, '', NULL, NULL, '2012-02-14 15:57:31', '', NULL, '0000-00-00 00:00:00', 0, '', 0, 'M2U2MzQ2ODM2MWMzNmFjMTA5ODUxZTJhMjk5OTA1'),
(31, NULL, 'test8', 'images/uploads/(14).png', 'afdsfasdf<br />', NULL, NULL, NULL, '', NULL, 'images/uploads/suolong.jpg', '2012-02-16 15:16:23', '', NULL, '0000-00-00 00:00:00', 0, '', 0, 'NDQ0NGEzMGMyNjNkZWEwMzE0ZWY4ZjJmMjc3YWJj'),
(32, NULL, 'eason', 'images/uploads/(123).png', 'eason duo live', NULL, NULL, NULL, '', NULL, 'images/uploads/op2.jpg', '2012-02-16 15:53:34', '', NULL, '0000-00-00 00:00:00', 0, '', 0, 'YmNlZTM3NjBjNGM0ZjAxMDEyZTQxMDA4NTMwZWE4'),
(33, NULL, 'fmltest', 'images/uploads/op1.jpg', 'fmldesign', NULL, NULL, NULL, '', NULL, 'images/uploads/op2.jpg', '2012-02-16 17:23:02', '', NULL, '0000-00-00 00:00:00', 0, '', 0, 'NzYwZmE2MDZkOWVlYjg5OWU2NTkwNTAxOTU5Nzkw'),
(34, NULL, 'fml', 'images/uploads/(13).png', 'aaaaaaaaaaaaaaaaaaaaaaa<br />', NULL, NULL, NULL, '', NULL, 'images/uploads/op2.jpg', '2012-02-16 18:00:21', '', NULL, '0000-00-00 00:00:00', 0, '', 0, 'OGUxMmRiZTg5Mjc5ZmE0MTQ0OTc0YzVkYWY1ZTEy'),
(35, NULL, 'Example App', 'images/uploads/1.JPG', 'This is an example app for testing all the feature functions.', NULL, NULL, NULL, '', NULL, 'images/uploads/camera.jpg', '2012-02-17 10:04:01', '', NULL, '0000-00-00 00:00:00', 0, '', 0, 'NGVmZDAxOWYzODFjNTkwNDUxMzdmNDY3MGJhYWI1');

-- --------------------------------------------------------

--
-- 替换视图以便查看 `emp_app_tab`
--
CREATE TABLE IF NOT EXISTS `emp_app_tab` (
`aid` int(11) unsigned
,`acid` int(11)
,`aname` varchar(200)
,`aicon` varchar(200)
,`adescription` text
,`amarket_icon` varchar(200)
,`aindex_icon` varchar(200)
,`aindex_image` varchar(200)
,`aindex_content` text
,`aload_icon` varchar(200)
,`aload_image` varchar(200)
,`acreate_time` datetime
,`aversion` varchar(10)
,`aupdate_time` datetime
,`aapproved_time` datetime
,`astatus` tinyint(4)
,`aonline_link` varchar(200)
,`atemplate` int(11)
,`tname` varchar(200)
,`ticon` varchar(200)
,`fnid` int(11)
,`fnname` varchar(200)
,`fntitle` varchar(200)
,`tid` int(11) unsigned
,`tstatus` tinyint(4)
,`ticon_type` int(11)
,`torder` int(11)
,`akey` varchar(200)
,`tdefault` int(11)
);
-- --------------------------------------------------------

--
-- 表的结构 `emp_article`
--

CREATE TABLE IF NOT EXISTS `emp_article` (
  `artid` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `articon` varchar(200) DEFAULT NULL,
  `artname` varchar(200) DEFAULT NULL,
  `artshort_description` text,
  `artcontent` text,
  `artstatus` tinyint(4) DEFAULT NULL,
  `artcreate_time` datetime DEFAULT NULL,
  `tid` int(11) DEFAULT NULL,
  `artimage` varchar(200) NOT NULL,
  `aorder` int(11) NOT NULL,
  PRIMARY KEY (`artid`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=25 ;

--
-- 转存表中的数据 `emp_article`
--

INSERT INTO `emp_article` (`artid`, `articon`, `artname`, `artshort_description`, `artcontent`, `artstatus`, `artcreate_time`, `tid`, `artimage`, `aorder`) VALUES
(1, NULL, '海贼王》剧场版12月来袭 尾田荣一郎亲自出马', '新世界篇-漫画彩页', '时光网讯 《海贼王》2012年剧场版终于有了确定性进展，影片定于今年12月上映，而更令人兴奋的是，原作者尾田荣一郎将担当综合制作人，这也是他继十周年大作《强者天下》（Strong World ）后再度参与到剧场版当中。\r\n根据发行方东映消息，这次是路飞一行人进入新世界后的首部剧场版，他们要面对的是号称“史上最强敌人”的Z，展开殊死决斗。而制作方更是宣称，要制作出一部超越《强者天下》的电影。', 1, '2012-01-19 00:00:00', 4, 'http://202.134.127.70:8080/images/news/new1.jpg', 0),
(2, NULL, '《海贼王》新剧场版确定上映时间 路飞将遭遇最强之敌 ', '在去年年底宣布开始制作的《海贼王》新剧场版于日前又放出了一部分新情报。', '首先，这部作品预计将在2012年12月上映，而且同09年的《海贼王：STRONGWORLD》一样，原作者尾田荣一郎也将亲自参加制作，担当综合制作人这一职务。据朝日新闻报道，这次的《海贼王》新剧场版中，路飞一行人将遭遇到名为“Z”的最强敌人，路飞和“Z”将上映一场壮丽的战斗。', 1, '2012-01-21 00:00:00', 4, 'http://202.134.127.70:8080/images/news/new2.jpg', 0),
(3, NULL, '海贼王》剧场版12月来袭 尾田荣一郎亲自出马', '新世界篇-漫画彩页', '时光网讯 《海贼王》2012年剧场版终于有了确定性进展，影片定于今年12月上映，而更令人兴奋的是，原作者尾田荣一郎将担当综合制作人，这也是他继十周年大作《强者天下》（Strong World ）后再度参与到剧场版当中。\r\n根据发行方东映消息，这次是路飞一行人进入新世界后的首部剧场版，他们要面对的是号称“史上最强敌人”的Z，展开殊死决斗。而制作方更是宣称，要制作出一部超越《强者天下》的电影。', 1, '2012-01-19 00:00:00', 20, 'http://202.134.127.70:8080/images/news/new1.jpg', 0),
(4, NULL, '《海贼王》新剧场版确定上映时间 路飞将遭遇最强之敌 ', '在去年年底宣布开始制作的《海贼王》新剧场版于日前又放出了一部分新情报。', '首先，这部作品预计将在2012年12月上映，而且同09年的《海贼王：STRONGWORLD》一样，原作者尾田荣一郎也将亲自参加制作，担当综合制作人这一职务。据朝日新闻报道，这次的《海贼王》新剧场版中，路飞一行人将遭遇到名为“Z”的最强敌人，路飞和“Z”将上映一场壮丽的战斗。', 1, '2012-01-21 00:00:00', 20, 'http://202.134.127.70:8080/images/news/new2.jpg', 0),
(5, NULL, '海贼王》剧场版12月来袭 尾田荣一郎亲自出马', '新世界篇-漫画彩页', '时光网讯 《海贼王》2012年剧场版终于有了确定性进展，影片定于今年12月上映，而更令人兴奋的是，原作者尾田荣一郎将担当综合制作人，这也是他继十周年大作《强者天下》（Strong World ）后再度参与到剧场版当中。\r\n根据发行方东映消息，这次是路飞一行人进入新世界后的首部剧场版，他们要面对的是号称“史上最强敌人”的Z，展开殊死决斗。而制作方更是宣称，要制作出一部超越《强者天下》的电影。', 1, '2012-01-19 00:00:00', 20, 'http://202.134.127.70:8080/images/news/new1.jpg', 0),
(6, 'images/uploads/(14).png', '《海贼王》新剧场版确定上映时间 路飞将遭遇最强之敌1 ', '在去年年底宣布开始制作的《海贼王》新剧场版于日前又放出了一部分新情报。1', '首先，这部作品预计将在2012年12月上映，而且同09年的《海贼王：STRONGWORLD》一样，原作者尾田荣一郎也将亲自参加制作，担当综合制作人这一职务。据朝日新闻报道，这次的《海贼王》新剧场版中，路飞一行人将遭遇到名为“Z”的最强敌人，路飞和“Z”将上映一场壮丽的战斗。1', 1, '2012-01-21 00:00:00', 20, 'http://202.134.127.70:8080/images/news/new2.jpg', 0),
(22, 'images/uploads/bear.jpg', 'test', 'test', 'test', 0, '2012-02-16 09:43:13', 5, '', 0),
(23, 'images/uploads/op1.jpg', 'ooooooooooooooo', '<span style="background-color:#006600;color:#E53333;"></span><span style="color:#E53333;">ooooooooooooooooooo</span>', 'jjjjjjjjjjjjjjjjjjjjjjj', 1, '2012-02-16 17:26:04', 48, '', 0),
(24, 'images/uploads/busy.jpg', 'News Item 1', 'test', 'test test', 1, '2012-02-17 10:14:20', 58, '', 0);

-- --------------------------------------------------------

--
-- 表的结构 `emp_cast`
--

CREATE TABLE IF NOT EXISTS `emp_cast` (
  `cid` int(11) NOT NULL AUTO_INCREMENT,
  `cname` varchar(200) NOT NULL,
  `cicon` varchar(200) NOT NULL,
  `ccontent` text NOT NULL,
  `cstatus` tinyint(4) NOT NULL,
  `tid` int(11) NOT NULL,
  `clittle_icon` varchar(200) NOT NULL,
  `corder` int(11) NOT NULL,
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=21 ;

--
-- 转存表中的数据 `emp_cast`
--

INSERT INTO `emp_cast` (`cid`, `cname`, `cicon`, `ccontent`, `cstatus`, `tid`, `clittle_icon`, `corder`) VALUES
(1, 'MONKEY·D·LUFFY', 'http://202.134.127.70:8080/images/cast/lufei.jpg', '主角：“草帽小子” 蒙奇·D·路飞（MONKEY·D·LUFFY）\r\n年龄：19岁\r\n船长 MONKEY·D·LUFFY(20张)（出场时17岁）\r\n霸气：见闻色霸气武装色霸气霸王色霸气\r\n生日：5月5日（日本男孩节）\r\n身高：174cm\r\n星座：金牛座\r\n身份：草帽海贼团船长，草帽海贼团三大主力之一\r\n故乡：东海·风车村', 1, 1, 'http://202.134.127.70:8080/images/cast/lufei.jpg', 0),
(2, 'RORONOA· ZORO', 'http://202.134.127.70:8080/images/cast/suolong.jpg', '“海贼猎人”罗罗诺亚·索隆（RORONOA· ZORO）（动画第2集出现，第3集加入）\r\n年龄：21岁（出场时19岁）\r\n生日：11月11日（代表世界第一）\r\n身份：原东海海贼赏金猎人→草帽海贼团剑士，草帽海贼团三大主力之一。\r\n星座：天蝎座\r\n故乡：东海·霜月村\r\n特征：绿色头发，左耳戴三只黄色露珠耳环，绿色的肚兜 ，腰带上佩三把刀，左臂绑着黑色的头巾（戴上意味着认真作战，战斗力剧增），两年后左眼多了一道疤。超级路痴、讲义气、总是爱和山治唱反调，被山治叫做绿藻头，但关键时刻两人总是很意外的合拍', 1, 1, 'http://202.134.127.70:8080/images/cast/suolong.jpg', 0),
(3, 'NAMI', 'http://202.134.127.70:8080/images/cast/namei.jpg', '年龄：20岁（出场时18岁）\r\n生日：7月3日（日文7发音nana，3可发音mi，与nami接近）\r\n身份：原阿龙海贼团测量员（测量师）兼海贼小偷→草帽海贼团航海士\r\n星座：巨蟹座\r\n故乡：东海·可可亚西村\r\n特征：极好的身材，左臂有风车和橘子样的纹身（纪念其养父阿健（诺琪高说过阿健如父亲一般）和养母贝尔梅尔），左手手腕戴着一个手镯（姐姐诺琪高在娜美出海前送给她的），2年后多了耳环，波浪长发，三围也有改变。爱钱和橘子如命（不过为了友情主动舍弃金钱） ，对待其他船员的白痴举动总会显示出超级抓狂且暴力的可怕又可爱的形象。', 1, 1, 'http://202.134.127.70:8080/images/cast/namei.jpg', 0),
(4, 'MONKEY·D·LUFFY', 'http://202.134.127.70:8080/images/cast/lufei.jpg', '主角：“草帽小子” 蒙奇·D·路飞（MONKEY·D·LUFFY）\r\n年龄：19岁\r\n船长 MONKEY·D·LUFFY(20张)（出场时17岁）\r\n霸气：见闻色霸气武装色霸气霸王色霸气\r\n生日：5月5日（日本男孩节）\r\n身高：174cm\r\n星座：金牛座\r\n身份：草帽海贼团船长，草帽海贼团三大主力之一\r\n故乡：东海·风车村', 1, 21, 'http://202.134.127.70:8080/images/cast/lufei.jpg', 0),
(5, 'RORONOA· ZORO', 'http://202.134.127.70:8080/images/cast/suolong.jpg', '“海贼猎人”罗罗诺亚·索隆（RORONOA· ZORO）（动画第2集出现，第3集加入）\r\n年龄：21岁（出场时19岁）\r\n生日：11月11日（代表世界第一）\r\n身份：原东海海贼赏金猎人→草帽海贼团剑士，草帽海贼团三大主力之一。\r\n星座：天蝎座\r\n故乡：东海·霜月村\r\n特征：绿色头发，左耳戴三只黄色露珠耳环，绿色的肚兜 ，腰带上佩三把刀，左臂绑着黑色的头巾（戴上意味着认真作战，战斗力剧增），两年后左眼多了一道疤。超级路痴、讲义气、总是爱和山治唱反调，被山治叫做绿藻头，但关键时刻两人总是很意外的合拍', 1, 21, 'http://202.134.127.70:8080/images/cast/suolong.jpg', 0),
(6, 'NAMI', 'http://202.134.127.70:8080/images/cast/namei.jpg', '年龄：20岁（出场时18岁）\r\n生日：7月3日（日文7发音nana，3可发音mi，与nami接近）\r\n身份：原阿龙海贼团测量员（测量师）兼海贼小偷→草帽海贼团航海士\r\n星座：巨蟹座\r\n故乡：东海·可可亚西村\r\n特征：极好的身材，左臂有风车和橘子样的纹身（纪念其养父阿健（诺琪高说过阿健如父亲一般）和养母贝尔梅尔），左手手腕戴着一个手镯（姐姐诺琪高在娜美出海前送给她的），2年后多了耳环，波浪长发，三围也有改变。爱钱和橘子如命（不过为了友情主动舍弃金钱） ，对待其他船员的白痴举动总会显示出超级抓狂且暴力的可怕又可爱的形象。', 1, 21, 'http://202.134.127.70:8080/images/cast/namei.jpg', 0),
(20, 'Cast 1', 'images/uploads/bear.jpg', 'test', 1, 64, '', 0);

-- --------------------------------------------------------

--
-- 表的结构 `emp_download`
--

CREATE TABLE IF NOT EXISTS `emp_download` (
  `did` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `dname` varchar(200) DEFAULT NULL,
  `daddress` varchar(200) DEFAULT NULL,
  `dtype` tinyint(4) DEFAULT NULL,
  `aid` int(11) DEFAULT NULL,
  PRIMARY KEY (`did`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=3 ;

--
-- 转存表中的数据 `emp_download`
--

INSERT INTO `emp_download` (`did`, `dname`, `daddress`, `dtype`, `aid`) VALUES
(1, 'eeeeeeeee', 'lllllllllllllllll', 1, 1),
(2, 'rrrrrrrrrrrrrrrrrrrrr', 'gggggggggggggggggggggg', 0, 1);

-- --------------------------------------------------------

--
-- 表的结构 `emp_event`
--

CREATE TABLE IF NOT EXISTS `emp_event` (
  `eid` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `etitle` varchar(200) DEFAULT NULL,
  `edescription` text,
  `estart_time` datetime DEFAULT NULL,
  `eend_time` datetime DEFAULT NULL,
  `estatus` tinyint(4) DEFAULT NULL,
  `tid` int(11) DEFAULT NULL,
  `elocation` varchar(200) DEFAULT NULL,
  `eholster` varchar(200) DEFAULT NULL,
  `ephone` varchar(200) DEFAULT NULL,
  `email` varchar(200) DEFAULT NULL,
  `eimage` varchar(200) NOT NULL,
  `egaree` int(11) NOT NULL DEFAULT '0',
  `eunagree` int(11) NOT NULL DEFAULT '0',
  `emaybe` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`eid`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=17 ;

--
-- 转存表中的数据 `emp_event`
--

INSERT INTO `emp_event` (`eid`, `etitle`, `edescription`, `estart_time`, `eend_time`, `estatus`, `tid`, `elocation`, `eholster`, `ephone`, `email`, `eimage`, `egaree`, `eunagree`, `emaybe`) VALUES
(1, '海贼王的宝藏', '3D高清网游《极光世界》与你一起走进神秘的梦之海，探访海贼王的遗迹！在《极光世界》中从来都不缺少惊喜，特别是在广阔无边的深海地宫中，不但充满了各种神奇的宠物，而且还有着众多的神秘之地。其中，据闻是海贼王遗留下来的遗迹埋藏着秘宝，想发掘宝藏的各位赶快行动吧！', '2012-01-14 00:00:00', '2012-01-16 00:00:00', 1, 3, '在大通公园会场', NULL, NULL, NULL, 'http://202.134.127.70:8080/images/event/1.jpg', 0, 0, 0),
(2, '《海贼王》社交游戏', '《海贼王》目前是一款收集类的社交游戏，玩家可以选择漫画中的人物作为自己的形象，这款游戏在发布后4天就突破了100万用户。\r\n《One Piece Grand Collection》目前支持功能机及智能机版的Mobage平台', '2012-01-17 00:00:00', '2012-01-19 00:00:00', 1, 3, '在北海道会场', NULL, NULL, NULL, 'http://202.134.127.70:8080/images/event/2.jpg', 0, 0, 0),
(3, '海贼王雪雕隆重登场', '一年一度的北海道札幌冰雪节2012年已是第63届。今年的札幌冰雪节在2月6日至2月12日期间举行。札幌冰雪节在大通公园等会场举行，活动会场以大通会场为中心，共分为数个展区，海贼王和美食的俘虏的联合雪雕便将出现在“UHB家庭乐园”会场上。雪雕上的角色包括美食的俘虏中的阿虏、小松和泰利·克洛斯，以及 海贼王中的路飞与乔巴。雪雕高约15米，厚20米，宽24米。\r\n这么生动形象的巨幅雪雕，想必要花费组织方很多的心血吧！有机会见到的朋友可要分享一下现场照哦！ ', '2012-02-06 00:00:00', '2012-02-12 00:00:00', 1, 3, '在大通公园会场', NULL, NULL, NULL, 'http://202.134.127.70:8080/images/event/3.jpg', 0, 0, 0),
(4, '海贼王的宝藏', '3D高清网游《极光世界》与你一起走进神秘的梦之海，探访海贼王的遗迹！在《极光世界》中从来都不缺少惊喜，特别是在广阔无边的深海地宫中，不但充满了各种神奇的宠物，而且还有着众多的神秘之地。其中，据闻是海贼王遗留下来的遗迹埋藏着秘宝，想发掘宝藏的各位赶快行动吧！', '2012-01-14 00:00:00', '2012-01-16 00:00:00', 1, 25, '在大通公园会场', NULL, NULL, NULL, 'http://202.134.127.70:8080/images/event/1.jpg', 0, 0, 0),
(5, '《海贼王》社交游戏', '《海贼王》目前是一款收集类的社交游戏，玩家可以选择漫画中的人物作为自己的形象，这款游戏在发布后4天就突破了100万用户。\r\n《One Piece Grand Collection》目前支持功能机及智能机版的Mobage平台', '2012-01-17 00:00:00', '2012-01-19 00:00:00', 1, 3, '在北海道会场', NULL, NULL, NULL, 'http://202.134.127.70:8080/images/event/2.jpg', 0, 0, 0),
(6, '海贼王雪雕隆重登场', '一年一度的北海道札幌冰雪节2012年已是第63届。今年的札幌冰雪节在2月6日至2月12日期间举行。札幌冰雪节在大通公园等会场举行，活动会场以大通会场为中心，共分为数个展区，海贼王和美食的俘虏的联合雪雕便将出现在“UHB家庭乐园”会场上。雪雕上的角色包括美食的俘虏中的阿虏、小松和泰利·克洛斯，以及 海贼王中的路飞与乔巴。雪雕高约15米，厚20米，宽24米。\r\n这么生动形象的巨幅雪雕，想必要花费组织方很多的心血吧！有机会见到的朋友可要分享一下现场照哦！ ', '2012-02-06 00:00:00', '2012-02-12 00:00:00', 1, 25, '在大通公园会场', NULL, NULL, NULL, 'http://202.134.127.70:8080/images/event/3.jpg', 0, 0, 0),
(7, '红馆演出。', '香港著名填词人黄伟文(Wyman)的“Concert YY黄伟文作品展演唱会2012”，9日晚起一连6场在红馆演出。<br style="color:#333333;font-family:宋体;font-size:14px;line-height:24px;text-align:left;white-space:normal;background-color:#E7F0F7;" />\r\n<br style="color:#333333;font-family:宋体;font-size:14px;line-height:24px;text-align:left;white-space:normal;background-color:#E7F0F7;" />\r\n歌手陈奕迅、郑秀文、容祖儿、何韵诗、苏永康、薛凯琪、张敬轩、黄耀明、周柏豪、彭铃、傅佩嘉，与久违了的卢巧音和赵学而等，共同助阵演出黄伟文作品。<br style="color:#333333;font-family:宋体;font-size:14px;line-height:24px;text-align:left;white-space:normal;background-color:#E7F0F7;" />\r\n<br style="color:#333333;font-family:宋体;font-size:14px;line-height:24px;text-align:left;white-space:normal;background-color:#E7F0F7;" />\r\n其中，日前接受韧带接驳手术的陈奕迅拄着拐杖为好友黄伟文站台，并献唱了《浮夸》等五首歌，可谓倾情支持。来源新华娱乐)', '2012-02-14 17:57:00', '2012-02-29 17:57:00', 1, 25, '红馆演出。', NULL, NULL, NULL, '', 0, 0, 0),
(8, '红馆演出。', '香港著名填词人黄伟文(Wyman)的“Concert YY黄伟文作品展演唱会2012”，9日晚起一连6场在红馆演出。<br style="color:#333333;font-family:宋体;font-size:14px;line-height:24px;text-align:left;white-space:normal;background-color:#E7F0F7;" />\r\n<br style="color:#333333;font-family:宋体;font-size:14px;line-height:24px;text-align:left;white-space:normal;background-color:#E7F0F7;" />\r\n歌手陈奕迅、郑秀文、容祖儿、何韵诗、苏永康、薛凯琪、张敬轩、黄耀明、周柏豪、彭铃、傅佩嘉，与久违了的卢巧音和赵学而等，共同助阵演出黄伟文作品。<br style="color:#333333;font-family:宋体;font-size:14px;line-height:24px;text-align:left;white-space:normal;background-color:#E7F0F7;" />\r\n<br style="color:#333333;font-family:宋体;font-size:14px;line-height:24px;text-align:left;white-space:normal;background-color:#E7F0F7;" />\r\n其中，日前接受韧带接驳手术的陈奕迅拄着拐杖为好友黄伟文站台，并献唱了《浮夸》等五首歌，可谓倾情支持。来源新华娱乐)', '2012-02-14 17:57:00', '2012-02-29 17:57:00', 1, 7, '红馆演出。', NULL, NULL, NULL, '', 0, 0, 0),
(9, 'Wyman', '香港著名填词人黄伟文(Wyman)的“Concert YY黄伟文作品展演唱会2012”，9日晚起一连6场在红馆演出。<br style="color:#333333;font-family:宋体;font-size:14px;line-height:24px;text-align:left;white-space:normal;background-color:#E7F0F7;" />\r\n<br style="color:#333333;font-family:宋体;font-size:14px;line-height:24px;text-align:left;white-space:normal;background-color:#E7F0F7;" />\r\n歌手陈奕迅、郑秀文、容祖儿、何韵诗、苏永康、薛凯琪、张敬轩、黄耀明、周柏豪、彭铃、傅佩嘉，与久违了的卢巧音和赵学而等，共同助阵演出黄伟文作品。<br style="color:#333333;font-family:宋体;font-size:14px;line-height:24px;text-align:left;white-space:normal;background-color:#E7F0F7;" />\r\n<br style="color:#333333;font-family:宋体;font-size:14px;line-height:24px;text-align:left;white-space:normal;background-color:#E7F0F7;" />\r\n其中，日前接受韧带接驳手术的陈奕迅拄着拐杖为好友黄伟文站台，并献唱了《浮夸》等五首歌，可谓倾情支持。来源新华娱乐)', '2012-02-20 18:04:00', '2012-02-27 18:04:00', 1, 25, '红馆演出', NULL, NULL, NULL, '', 0, 0, 0),
(10, 'Wyman', '香港著名填词人黄伟文(Wyman)的“Concert YY黄伟文作品展演唱会2012”，9日晚起一连6场在红馆演出。<br style="color:#333333;font-family:宋体;font-size:14px;line-height:24px;text-align:left;white-space:normal;background-color:#E7F0F7;" />\r\n<br style="color:#333333;font-family:宋体;font-size:14px;line-height:24px;text-align:left;white-space:normal;background-color:#E7F0F7;" />\r\n歌手陈奕迅、郑秀文、容祖儿、何韵诗、苏永康、薛凯琪、张敬轩、黄耀明、周柏豪、彭铃、傅佩嘉，与久违了的卢巧音和赵学而等，共同助阵演出黄伟文作品。<br style="color:#333333;font-family:宋体;font-size:14px;line-height:24px;text-align:left;white-space:normal;background-color:#E7F0F7;" />\r\n<br style="color:#333333;font-family:宋体;font-size:14px;line-height:24px;text-align:left;white-space:normal;background-color:#E7F0F7;" />\r\n其中，日前接受韧带接驳手术的陈奕迅拄着拐杖为好友黄伟文站台，并献唱了《浮夸》等五首歌，可谓倾情支持。来源新华娱乐)', '2012-02-20 18:04:00', '2012-02-27 18:04:00', 1, 9, '红馆演出', NULL, NULL, NULL, '', 1, 1, 0),
(11, 'Wyman', '香港著名填词人黄伟文(Wyman)的“Concert YY黄伟文作品展演唱会2012”，9日晚起一连6场在红馆演出。<br style="color:#333333;font-family:宋体;font-size:14px;line-height:24px;text-align:left;white-space:normal;background-color:#E7F0F7;" />\r\n<br style="color:#333333;font-family:宋体;font-size:14px;line-height:24px;text-align:left;white-space:normal;background-color:#E7F0F7;" />\r\n歌手陈奕迅、郑秀文、容祖儿、何韵诗、苏永康、薛凯琪、张敬轩、黄耀明、周柏豪、彭铃、傅佩嘉，与久违了的卢巧音和赵学而等，共同助阵演出黄伟文作品。<br style="color:#333333;font-family:宋体;font-size:14px;line-height:24px;text-align:left;white-space:normal;background-color:#E7F0F7;" />\r\n<br style="color:#333333;font-family:宋体;font-size:14px;line-height:24px;text-align:left;white-space:normal;background-color:#E7F0F7;" />\r\n其中，日前接受韧带接驳手术的陈奕迅拄着拐杖为好友黄伟文站台，并献唱了《浮夸》等五首歌，可谓倾情支持。来源新华娱乐)', '2012-02-20 18:04:00', '2012-02-27 18:04:00', 1, 9, '红馆演出', NULL, NULL, NULL, '', 1, 1, 0),
(12, 'mmmmm', 'aaaaaaaaaaaaaaaaaaaaa', '2012-02-27 09:53:00', '2012-02-27 09:53:00', 1, 25, 'lllllllllllllllllll', NULL, NULL, NULL, '', 1, 1, 0),
(13, 'event1', 'event1event1event1event1<br />', '2012-02-16 10:27:00', '2012-02-17 10:27:00', 1, 25, 'guangzhou', 'hay', '13533030942', 'hay@fmldigital.com', '', 1, 1, 1),
(16, 'Event 1', '<p>\r\n	<em>test test &nbsp;</em><strong><span style="background-color:#FF9900;">test&nbsp;</span></strong>\r\n</p>\r\n<p>\r\n	<strong><span style="color:#006600;">test test test</span></strong>\r\n</p>', '2012-02-08 04:13:00', '2012-02-09 10:19:00', 1, 61, 'Tianhe Square', 'MR SMITH', '21312321', 'ewfwf', '', 0, 0, 0);

-- --------------------------------------------------------

--
-- 表的结构 `emp_form`
--

CREATE TABLE IF NOT EXISTS `emp_form` (
  `fid` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `fintroduction` text,
  `lmessage` text,
  `tid` int(11) DEFAULT NULL,
  PRIMARY KEY (`fid`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=6 ;

--
-- 转存表中的数据 `emp_form`
--

INSERT INTO `emp_form` (`fid`, `fintroduction`, `lmessage`, `tid`) VALUES
(1, 'ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff', 'fffffffffffffffffffffffffffffffffffffffff', 1),
(2, 'fffffffffffffffffffffffffffffff', 'fffffttttttttttttttttttttttttttttttttttt', 3),
(4, 'lllllllllll', 'yyyyyyyyyyyyyyyyyyyyyyyy', 75),
(5, 'wwwwwwwwwwwwwwwwwwwwwwwww', 'wwwwwwwwwwwwwwwwwwwwwwwwwwwww', 76);

-- --------------------------------------------------------

--
-- 表的结构 `emp_form_view`
--

CREATE TABLE IF NOT EXISTS `emp_form_view` (
  `fvid` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tid` int(11) DEFAULT NULL,
  `fv_name` varchar(200) NOT NULL,
  `fv_title` varchar(200) DEFAULT NULL,
  `fv_type` tinyint(4) DEFAULT NULL COMMENT '1:text;2:checkbox;3:radio;4:textarea;5:select;',
  `fv_value` varchar(200) DEFAULT NULL,
  `fv_required` tinyint(4) DEFAULT '0',
  `regular_expression` varchar(200) DEFAULT NULL,
  `prompting_message` varchar(200) DEFAULT NULL,
  `vis_hits` int(11) NOT NULL DEFAULT '0' COMMENT '0:打勾;1:没打勾;',
  PRIMARY KEY (`fvid`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=50 ;

--
-- 转存表中的数据 `emp_form_view`
--

INSERT INTO `emp_form_view` (`fvid`, `tid`, `fv_name`, `fv_title`, `fv_type`, `fv_value`, `fv_required`, `regular_expression`, `prompting_message`, `vis_hits`) VALUES
(1, 1, '', 'name', 1, '1', 1, '', '', 0),
(2, 1, '', 'description', 3, '1', 1, '', '', 0),
(39, 76, 'email', 'Email Address', 1, NULL, 1, NULL, 'please fill in the Email Address.', 1),
(40, 76, 'contact', 'Contact Name', 1, NULL, 1, NULL, 'please fill in the Contact Name.', 1),
(41, 76, 'address', 'Full Address', 1, NULL, 1, NULL, 'please fill in the Address', 1),
(42, 76, 'state', 'Province/State', 1, NULL, 0, NULL, 'please fill in the Province/State.', 1),
(43, 76, 'zip', 'Zip/postal Code', 1, NULL, 0, NULL, 'please fill in the Zip/postal Code.', 0),
(44, 76, 'format', 'Preferred Format', 3, '1', 0, NULL, 'please select the Preferred Format.', 1),
(45, 76, 'format', 'Preferred Format', 3, '2', 0, NULL, 'please select the Preferred Format.', 0),
(46, 76, 'format', 'Preferred Format', 3, '3', 0, NULL, 'please select the Preferred Format.', 0),
(47, 76, 'phone', 'Phone Number', 1, NULL, 1, NULL, 'please fill in your Phone Number.', 1),
(48, 76, 'city', 'City', 1, NULL, 0, NULL, 'please fill in the City.', 0),
(49, 76, 'country', 'Country', 1, NULL, 0, NULL, 'please fill in the Country.', 0);

-- --------------------------------------------------------

--
-- 表的结构 `emp_function`
--

CREATE TABLE IF NOT EXISTS `emp_function` (
  `fnid` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `fnname` varchar(200) DEFAULT NULL,
  `fntitle` varchar(200) NOT NULL,
  `fndescription` text NOT NULL,
  `fnicon` varchar(200) DEFAULT NULL,
  `fndefault` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`fnid`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=14 ;

--
-- 转存表中的数据 `emp_function`
--

INSERT INTO `emp_function` (`fnid`, `fnname`, `fntitle`, `fndescription`, `fnicon`, `fndefault`) VALUES
(1, 'home', 'Home', 'Upload an app loading image to welcome app users to your app.', 'images/icon/01.png', -1),
(2, 'article', 'News', 'Create specific article page detail to generate the article list. App users can click any article via the list to view details. This feature can be cast, news, and promotions, etc.', 'images/icon/02.png', 1),
(3, 'video', 'Video', 'Upload videos to your app, so your app users can play the video once they connect to internet, and they can also download it if it''s downloadable.', 'images/icon/03.png', 1),
(4, 'gallery', 'Photo Gallery', 'Easily upload photos to create a specific album or multiple albums as a photo gallery to your app. Images of a specific album can be set as downloadable or not.', 'images/icon/04.png', 1),
(5, 'event', 'Event', 'You can add the event details and provide the "RSVP" options for the app users to select. All the "RSVP" options from the app users can be tracked in the admin system.', 'images/icon/05.png', 1),
(6, 'music', 'Music', 'Upload audios to your app, so your app users can play the audio once they connect to internet, and they can also download it if it''s downloadable.', 'images/icon/06.png', 1),
(7, 'form', 'Form', 'You can select any of the form fields provided, enter the form introduction text, and set the "Thank You Message" to create your own form.', 'images/icon/07.png', 1),
(8, 'notification', 'Notification', 'You can add any notification targeted to your app users. App user who has this app will get the notification you add here once they connect to internet and they can share the notification to Weibo.', 'images/icon/08.png', 1),
(10, 'cast', 'Cast', 'You can add the event details and provide the "RSVP" options for the app users to select. All the "RSVP" options from the app users can be tracked in the admin system.', 'images/icon/09.png', 1),
(11, 'page', 'Page', 'There is only one HTML editor for this feature. You can add images or texts via the HTML editor and make them as the arrangement you''d like. All the entries in this editor will be customized to fit the Android interface automatically.', 'images/icon/10.png', 1);

-- --------------------------------------------------------

--
-- 表的结构 `emp_gallery`
--

CREATE TABLE IF NOT EXISTS `emp_gallery` (
  `gid` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `gname` varchar(200) NOT NULL,
  `gdescription` text,
  `gstatus` tinyint(4) DEFAULT NULL,
  `gdefaut_icon` varchar(200) DEFAULT NULL,
  `tid` int(11) DEFAULT NULL,
  `gcover` int(11) NOT NULL,
  `gis_download` int(11) NOT NULL DEFAULT '0',
  `gorder` int(11) NOT NULL,
  PRIMARY KEY (`gid`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=12 ;

--
-- 转存表中的数据 `emp_gallery`
--

INSERT INTO `emp_gallery` (`gid`, `gname`, `gdescription`, `gstatus`, `gdefaut_icon`, `tid`, `gcover`, `gis_download`, `gorder`) VALUES
(1, '路飞', '路飞是吃了橡胶果实的能力者，橡胶人，身体具有绝佳的弹性与延展性，可以抵挡绝大多数的物理攻击，对雷电攻击具有绝缘性.', 1, 'http://202.134.127.70:8080/images/gallery_list/1.jpg', 24, 1, 1, 0),
(2, 'Monkey·D·Luffy', '是日本超人气动漫 《海贼王》中的主角.', 1, 'http://202.134.127.70:8080/images/gallery_list/2.jpg', 1, 0, 0, 0),
(3, '路飞', '路飞是吃了橡胶果实的能力者，橡胶人，身体具有绝佳的弹性与延展性，可以抵挡绝大多数的物理攻击，对雷电攻击具有绝缘性.', 0, 'http://202.134.127.70:8080/images/gallery_list/1.jpg', 24, 1, 1, 0),
(4, 'Monkey·D·Luffy', '是日本超人气动漫 《海贼王》中的主角.', 1, 'http://202.134.127.70:8080/images/gallery_list/2.jpg', 24, 0, 0, 0),
(6, 'gallery2', NULL, 0, 'gallery2', 24, 0, 0, 0),
(7, 'gallery3', NULL, 1, 'images/uploads/(149).png', 24, 0, 1, 0),
(8, 'lllllllllllllllll', NULL, 1, 'images/uploads/op3.jpg', 49, 0, 1, 0),
(9, 'gallery4', NULL, 1, 'images/uploads/Copy of op3.jpg', 49, 0, 1, 0),
(10, 'haygallery', NULL, 1, 'images/uploads/Copy (2) of op3.jpg', 49, 0, 1, 0),
(11, '丁一宇', NULL, 1, 'images/uploads/090617--3.jpg', 49, 0, 1, 0);

-- --------------------------------------------------------

--
-- 替换视图以便查看 `emp_gallery_count`
--
CREATE TABLE IF NOT EXISTS `emp_gallery_count` (
`gid` int(11) unsigned
,`gname` varchar(200)
,`gdescription` text
,`gstatus` tinyint(4)
,`gdefaut_icon` varchar(200)
,`gis_download` int(11)
,`gorder` int(11)
,`tid` int(11)
,`gcount` bigint(21)
);
-- --------------------------------------------------------

--
-- 表的结构 `emp_gallery_photo`
--
-- 使用中(#1356 - View 'emp.emp_gallery_photo' references invalid table(s) or column(s) or function(s) or definer/invoker of view lack rights to use them)
-- 使用中 (#1356 - View 'emp.emp_gallery_photo' references invalid table(s) or column(s) or function(s) or definer/invoker of view lack rights to use them)

-- --------------------------------------------------------

--
-- 表的结构 `emp_general_image`
--

CREATE TABLE IF NOT EXISTS `emp_general_image` (
  `giid` int(11) NOT NULL,
  `giname` varchar(200) DEFAULT NULL,
  `giicon` varchar(200) DEFAULT NULL,
  `gitype` tinyint(4) DEFAULT NULL,
  `aid` int(11) DEFAULT NULL,
  PRIMARY KEY (`giid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `emp_homepage`
--

CREATE TABLE IF NOT EXISTS `emp_homepage` (
  `hid` int(11) NOT NULL AUTO_INCREMENT,
  `himage` varchar(200) NOT NULL,
  `hcontent` text NOT NULL,
  `htemplate` int(11) NOT NULL DEFAULT '0' COMMENT '0:default; 1:Left Column Text Area; 2:Top Text Area; 3:Right Column Text Area; 4Bottom Text Area',
  `tid` int(11) NOT NULL,
  PRIMARY KEY (`hid`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=7 ;

--
-- 转存表中的数据 `emp_homepage`
--

INSERT INTO `emp_homepage` (`hid`, `himage`, `hcontent`, `htemplate`, `tid`) VALUES
(1, 'http://202.134.127.70:8080/images/homepage/op3.jpg', '“ONE PIECE”在故事中为“一个大秘宝”之意.故事讲述了主人公路飞立志成为海盗王，为了寻找传说中的秘宝ONE PIECE而展开的凶险无比同时也波澜壮阔的冒险故事.曾经拥有世界上一切的“海贼王”哥尔·D·罗杰，在临死前留下了一句话，让全世界的人们，趋之若鹜，奔向大海：“想要我的财宝吗？想要的话可以给你，去伟大的航道找吧！我把一切都放在那里了！”于是所有的人们开始起航，趋之若鹜的奔向大海，世界迎来了“大海贼时代”。', 2, 1),
(2, '', '<p>曾经拥有世界上一切的“海贼王”哥尔·D·罗杰，在临死前留下了一句话，让全世界的人们，趋之若鹜，奔向大海：“想要我的财宝吗？想要的话可以给你，去伟大的航道找吧！我把一切都放在那里了！”于是所有的人们开始起航，趋之若鹜的奔向大海，世界迎来了“大海贼时代".</p>\r\n<h1>恶魔果实在《海贼王》里，被称作海上的秘宝，生长于大海深处，市场价至少有一亿贝利以上。普通的动物系比较便宜，它是海之恶魔的化身，食用后会得到不同的能力，但作为代价食用者都会被大海厌弃而成为旱鸭子，目前可知的恶魔果实能力有超人系，动物系（包括两种极其稀有的幻兽种跟古代种），自然系，恶魔果实能力不会重复，这也是它珍贵之处，另外当原果实拥有者死亡以后，这样的果实会继续生长出来.</h1>', 0, 10),
(4, 'images/uploads/suolong.jpg', 'aaaaaaaaaaaaaaaaaaaaaa<br />', 2, 37),
(5, 'images/uploads/namei.jpg', 'sssssssssssssssssssssssssssss<br />', 2, 40),
(6, 'images/uploads/alone.jpg', '<p>\r\n	text text\r\n</p>\r\n<p>\r\n	text text\r\n</p>', 1, 57);

-- --------------------------------------------------------

--
-- 表的结构 `emp_icon`
--

CREATE TABLE IF NOT EXISTS `emp_icon` (
  `iid` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `iname` varchar(200) DEFAULT NULL,
  `icon` varchar(200) DEFAULT NULL,
  `idefault` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`iid`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=61 ;

--
-- 转存表中的数据 `emp_icon`
--

INSERT INTO `emp_icon` (`iid`, `iname`, `icon`, `idefault`) VALUES
(1, '01', 'images/icon/01.png', 1),
(2, '02', 'images/icon/02.png', 1),
(3, '03', 'images/icon/03.png', 1),
(4, '04', 'images/icon/04.png', 1),
(5, '05', 'images/icon/05.png', 1),
(6, '06', 'images/icon/06.png', 1),
(7, '07', 'images/icon/07.png', 1),
(8, '08', 'images/icon/08.png', 1),
(9, '09', 'images/icon/09.png', 1),
(10, '10', 'images/icon/10.png', 1),
(11, '11', 'images/icon/11.png', 1),
(12, '12', 'images/icon/12.png', 1),
(13, '13', 'images/icon/13.png', 1),
(14, '14', 'images/icon/14.png', 1),
(15, '15', 'images/icon/15.png', 1),
(16, '16', 'images/icon/16.png', 1),
(17, '17', 'images/icon/17.png', 1),
(18, '18', 'images/icon/18.png', 1),
(19, '19', 'images/icon/19.png', 1),
(20, '20', 'images/icon/20.png', 1),
(21, '21', 'images/icon/21.png', 1),
(22, '22', 'images/icon/22.png', 1),
(23, '23', 'images/icon/23.png', 1),
(24, '24', 'images/icon/24.png', 1),
(25, '25', 'images/icon/25.png', 1),
(26, '26', 'images/icon/26.png', 1),
(27, '27', 'images/icon/27.png', 1),
(28, '28', 'images/icon/28.png', 1),
(29, '29', 'images/icon/29.png', 1),
(30, '30', 'images/icon/01.png', 1),
(31, '31', 'images/icon/02.png', 1),
(32, '32', 'images/icon/03.png', 1),
(33, '33', 'images/icon/04.png', 1),
(34, '34', 'images/icon/05.png', 1),
(35, '35', 'images/icon/06.png', 1),
(36, '36', 'images/icon/07.png', 1),
(37, '37', 'images/icon/08.png', 1),
(38, '38', 'images/icon/09.png', 1),
(39, '39', 'images/icon/10.png', 1),
(40, '40', 'images/icon/11.png', 1),
(41, '41', 'images/icon/12.png', 1),
(42, '42', 'images/icon/13.png', 1),
(43, '43', 'images/icon/14.png', 1),
(44, '44', 'images/icon/15.png', 1),
(45, '45', 'images/icon/16.png', 1),
(46, '46', 'images/icon/17.png', 1),
(47, '47', 'images/icon/18.png', 1),
(48, '48', 'images/icon/19.png', 1),
(49, '49', 'images/icon/20.png', 1),
(50, '50', 'images/icon/21.png', 1),
(51, '51', 'images/icon/22.png', 1),
(52, '52', 'images/icon/23.png', 1),
(53, '53', 'images/icon/24.png', 1),
(54, '54', 'images/icon/25.png', 1),
(55, '55', 'images/icon/26.png', 1),
(56, '56', 'images/icon/27.png', 1),
(57, '57', 'images/icon/28.png', 1),
(58, '58', 'images/icon/29.png', 1);

-- --------------------------------------------------------

--
-- 表的结构 `emp_list_form`
--

CREATE TABLE IF NOT EXISTS `emp_list_form` (
  `lid` int(11) unsigned NOT NULL DEFAULT '0',
  `fid` int(11) DEFAULT NULL,
  `lform_text` text,
  `mobile_id` varchar(200) DEFAULT NULL,
  `lpost_time` datetime DEFAULT NULL,
  PRIMARY KEY (`lid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `emp_music`
--

CREATE TABLE IF NOT EXISTS `emp_music` (
  `mid` int(11) NOT NULL AUTO_INCREMENT,
  `mname` varchar(200) NOT NULL,
  `micon` varchar(200) NOT NULL,
  `music` varchar(200) NOT NULL,
  `mstatus` tinyint(4) NOT NULL,
  `tid` tinyint(4) NOT NULL,
  `morder` int(11) NOT NULL DEFAULT '0',
  `mis_download` tinyint(4) NOT NULL DEFAULT '0',
  `mcaption` text NOT NULL,
  `msize` int(11) NOT NULL,
  `msinger` varchar(200) NOT NULL,
  PRIMARY KEY (`mid`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=23 ;

--
-- 转存表中的数据 `emp_music`
--

INSERT INTO `emp_music` (`mid`, `mname`, `micon`, `music`, `mstatus`, `tid`, `morder`, `mis_download`, `mcaption`, `msize`, `msinger`) VALUES
(1, 'Way Back Into Love', 'http://202.134.127.70:8080/music/music_icon/wbil.jpg', 'http://202.134.127.70:8080/music/Way Back Into Love.mp3', 1, 2, 0, 0, '电影《K歌情人》里的插曲.Hugh Grant and Drew Barrymore.', 0, ' Hugh Grant and Drew Barrymore'),
(2, 'Back to December', 'http://202.134.127.70:8080/music/music_icon/Taylor.jpg', 'http://202.134.127.70:8080/music/Back to December.mp3', 1, 2, 0, 0, 'Taylor Swift 新歌回忆旧情：致歉Taylor Lautner.', 0, 'Taylor Swift'),
(3, 'Poker Face', 'http://202.134.127.70:8080/music/music_icon/ladygaga.jpg', 'http://202.134.127.70:8080/music/poker face.mp3', 1, 2, 0, 0, 'GaGa表示:这是首关于女孩如何追求男孩的，以及你要如何不动声色，因为你一旦告诉他你有多喜欢他后，他就很快会跑掉的.', 0, 'laygaga'),
(4, 'Way Back Into Love', 'http://202.134.127.70:8080/music/music_icon/wbil.jpg', 'http://202.134.127.70:8080/music/Way Back Into Love.mp3', 1, 26, 0, 0, '电影《K歌情人》里的插曲.Hugh Grant and Drew Barrymore.', 0, ''),
(5, 'Back to December', 'http://202.134.127.70:8080/music/music_icon/Taylor.jpg', 'http://202.134.127.70:8080/music/Back to December.mp3', 1, 26, 0, 0, 'Taylor Swift 新歌回忆旧情：致歉Taylor Lautner.', 0, ''),
(6, 'Poker Face', 'http://202.134.127.70:8080/music/music_icon/ladygaga.jpg', 'http://202.134.127.70:8080/music/poker face.mp3', 1, 26, 0, 0, 'GaGa表示:这是首关于女孩如何追求男孩的，以及你要如何不动声色，因为你一旦告诉他你有多喜欢他后，他就很快会跑掉的.', 0, ''),
(7, 'our love', '', 'music', 1, 26, 0, 0, 'our loveour loveour loveour loveour loveour love', 0, ''),
(12, 'musicmusicmusic', 'aaaaaaaaaa', 'music', 1, 26, 0, 1, 'aaaaaaaa', 0, ''),
(18, 'music2', 'images/uploads/suolong.jpg', 'images/uploads/25.jpg', 1, 26, 0, 1, 'music2 music2 <br />\r\nmusic2 <br />\r\nmusic2 <br />\r\nmusic2 <br />\r\n<br />', 0, ''),
(19, 'music4', 'images/uploads/(13).png', 'images/uploads/(152).png', 1, 26, 0, 1, 'music4<br />', 0, ''),
(20, '紅玫瑰', '', '', 1, 46, 0, 0, '', 0, ''),
(21, '好久不見', '', '', 1, 46, 0, 0, '', 0, ''),
(22, 'test', 'images/uploads/5.jpg', '', 1, 62, 0, 1, 'test', 0, '');

-- --------------------------------------------------------

--
-- 表的结构 `emp_notification`
--

CREATE TABLE IF NOT EXISTS `emp_notification` (
  `nid` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tid` int(11) NOT NULL,
  `nsubject` varchar(200) DEFAULT NULL,
  `ncontent` text,
  `ncreate_time` datetime DEFAULT NULL,
  `nstatus` tinyint(4) DEFAULT NULL,
  `aid` int(11) DEFAULT NULL,
  PRIMARY KEY (`nid`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=6 ;

--
-- 转存表中的数据 `emp_notification`
--

INSERT INTO `emp_notification` (`nid`, `tid`, `nsubject`, `ncontent`, `ncreate_time`, `nstatus`, `aid`) VALUES
(1, 28, 'eeeeeeeeeee', 'bbbbbbbbbbbbbbbbbbbbbbbbbbKKKKKKKKKK', '2012-01-20 00:00:00', 1, 26),
(2, 28, 'nnnnnnnnnnnnnnnnnnnnnn', 'hhhhhhhhhhhhhhhhhhhhhhhhh', '2012-01-30 00:00:00', 1, 26),
(4, 28, 'eason', 'eason duo live', '2012-02-16 12:53:20', NULL, NULL),
(5, 63, '', '', '2012-02-17 10:26:55', NULL, NULL);

-- --------------------------------------------------------

--
-- 表的结构 `emp_photo`
--

CREATE TABLE IF NOT EXISTS `emp_photo` (
  `pid` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `pname` varchar(200) DEFAULT NULL,
  `photo` varchar(200) DEFAULT NULL,
  `picon` varchar(200) DEFAULT NULL,
  `gid` int(11) DEFAULT NULL,
  `p_description` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`pid`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=25 ;

--
-- 转存表中的数据 `emp_photo`
--

INSERT INTO `emp_photo` (`pid`, `pname`, `photo`, `picon`, `gid`, `p_description`) VALUES
(1, 'fffff', 'http://202.134.127.70:8080/images/gallery_list/photos/1.jpg', 'http://202.134.127.70:8080/images/gallery_list/photos/1.jpg', 1, '肉的味道（根据乔巴的鼻子判断）。  路飞2年后喜欢的食物：肉、以及所有能吃的东西和某些不能吃的东西（如盘子）.'),
(2, 'hhhhh', 'http://202.134.127.70:8080/images/gallery_list/photos/2.jpg', 'http://202.134.127.70:8080/images/gallery_list/photos/2.jpg', 1, '最珍惜的：草帽、伙伴（为了伙伴可以不惜一切，甚至生命）、亲人（例如艾斯）'),
(3, 'op', 'http://202.134.127.70:8080/images/gallery_list/photos/3.jpg', 'http://202.134.127.70:8080/images/gallery_list/photos/3.jpg', 1, '爱好：喜欢探险，感兴趣于新奇怪异的事物，酷爱机器、激光等高科技产品，总想要一个自己的铜像，越危险的地方越感兴趣.'),
(4, 'piece', 'http://202.134.127.70:8080/images/gallery_list/photos/4.jpg', 'http://202.134.127.70:8080/images/gallery_list/photos/4.jpg', 2, '我是要成为海贼王的男人！海贼王我当定了！'),
(5, 'images/uploads/Copy (3) of op3.jpg', 'images/uploads/Copy (3) of op3.jpg', NULL, 10, 'Desciption...'),
(6, 'images/uploads/Copy of op3.jpg', 'images/uploads/Copy of op3.jpg', NULL, 10, 'Desciption...'),
(7, 'images/uploads/op1.jpg', 'images/uploads/op1.jpg', NULL, 10, 'Desciption...'),
(8, 'images/uploads/op2.jpg', 'images/uploads/op2.jpg', NULL, 10, 'Desciption...'),
(9, 'images/uploads/op3.jpg', 'images/uploads/op3.jpg', NULL, 10, 'Desciption...'),
(15, '小宇', 'images/uploads/326229_246169962100937_234716566579610_748177_984037881_o.jpg', NULL, 11, '花美男'),
(16, '327743_254671737917426_234716566579610_775513_1964935208_o', 'images/uploads/327743_254671737917426_234716566579610_775513_1964935208_o.jpg', NULL, 11, ''),
(17, '3484995_980x1200_281', 'images/uploads/3484995_980x1200_281.jpg', NULL, 11, ''),
(18, '201110915410253', 'images/uploads/201110915410253.jpg', NULL, 11, ''),
(19, '5347476620110811151342053', 'images/uploads/5347476620110811151342053.jpg', NULL, 11, ''),
(20, '61606660201112051216212980565921306_005', 'images/uploads/61606660201112051216212980565921306_005.jpg', NULL, 11, ''),
(21, '61606660201112202025443065622936605_011', 'images/uploads/61606660201112202025443065622936605_011.jpg', NULL, 11, ''),
(22, '61606660201112202025443065622936605_015', 'images/uploads/61606660201112202025443065622936605_015.jpg', NULL, 11, ''),
(23, '61606660201112202025443065622936605_025', 'images/uploads/61606660201112202025443065622936605_025.jpg', NULL, 11, ''),
(24, '61606660201112202032183363170168641_002', 'images/uploads/61606660201112202032183363170168641_002.jpg', NULL, 11, '');

-- --------------------------------------------------------

--
-- 表的结构 `emp_record`
--

CREATE TABLE IF NOT EXISTS `emp_record` (
  `rid` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tid` int(11) DEFAULT NULL,
  `mobileid` varchar(200) DEFAULT NULL,
  `rpost_time` datetime DEFAULT NULL,
  PRIMARY KEY (`rid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `emp_tab`
--

CREATE TABLE IF NOT EXISTS `emp_tab` (
  `tid` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tname` varchar(200) DEFAULT NULL,
  `fnid` int(11) NOT NULL,
  `ticon` varchar(200) DEFAULT NULL,
  `ticon_type` int(11) NOT NULL COMMENT '0: default; 1: stock; 2: upload',
  `tstatus` tinyint(4) DEFAULT '0',
  `torder` int(11) NOT NULL DEFAULT '0',
  `aid` int(11) DEFAULT NULL,
  `tdefault` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`tid`,`fnid`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=77 ;

--
-- 转存表中的数据 `emp_tab`
--

INSERT INTO `emp_tab` (`tid`, `tname`, `fnid`, `ticon`, `ticon_type`, `tstatus`, `torder`, `aid`, `tdefault`) VALUES
(1, 'home', 11, 'http://202.134.127.70:8080/images/navigation/32_32/home.png', 0, 0, 11, 1, 0),
(2, 'music', 6, 'http://202.134.127.70:8080/images/navigation/32_32/gallery.png', 0, 1, 8, 1, 0),
(3, 'gallery', 4, 'http://202.134.127.70:8080/images/navigation/32_32/about.png', 0, 1, 9, 1, 0),
(4, 'form', 7, 'http://202.134.127.70:8080/images/navigation/32_32/video.png', 0, 1, 7, 1, 0),
(5, 'news', 2, 'http://202.134.127.70:8080/images/navigation/32_32/more.png', 0, 1, 10, 1, 0),
(6, 'event', 5, 'http://202.134.127.70:8080/images/navigation/32_32/more.png', 0, 0, 5, 1, 0),
(7, 'video', 3, 'http://202.134.127.70:8080/images/navigation/32_32/home.png', 0, 1, 6, 1, 0),
(8, 'cast', 10, 'http://202.134.127.70:8080/images/navigation/32_32/more.png', 0, 0, 4, 1, 0),
(10, 'about', 11, 'http://202.134.127.70:8080/images/navigation/32_32/more.png', 0, 0, 3, 1, 0),
(31, 'News', 2, 'images/navigation/32_32/about.png', 0, 1, 2, 1, 0),
(32, 'music', 6, 'images/navigation/32_32/about.png', 0, 1, 1, 1, 0),
(37, 'home', 11, 'images/icon/01.png', 1, 1, 0, 31, -1),
(38, 'testform', 7, 'images/icon/21.png', 1, 1, 5, 31, 0),
(39, 'testnew', 2, 'images/icon/11.png', 1, 1, 2, 31, 0),
(40, 'testpage', 11, 'images/icon/12.png', 1, 1, 4, 31, 0),
(41, 'testcast', 10, 'images/icon/03.png', 1, 1, 3, 31, 0),
(42, 'testvideo', 3, 'images/icon/18.png', 1, 1, 1, 31, 0),
(43, 'home', 11, NULL, 0, 1, 0, 32, -1),
(45, '吹神', 10, 'images/icon/23.png', 1, 1, 0, 32, 0),
(46, '所長專輯', 6, 'images/icon/09.png', 1, 1, 0, 32, 0),
(47, 'home', 11, 'images/icon/10.png', 0, 1, 0, 33, -1),
(48, 'mmmmm', 2, 'images/icon/08.png', 1, 1, 3, 33, 0),
(49, 'ooooo', 4, 'images/icon/12.png', 1, 1, 4, 33, 0),
(50, 'testmusic', 6, 'images/navigation/32_32/about.png', 0, 0, 0, 31, 0),
(51, 'home', 11, 'images/navigation/32_32/about.png', 0, 1, 0, 34, -1),
(52, 'sssss', 6, 'images/icon/01.png', 1, 1, 2, 33, 0),
(53, 'sssss', 6, 'images/icon/15.png', 1, 1, 1, 33, 0),
(54, 'news', 2, 'images/icon/02.png', 1, 1, 2, 34, 0),
(55, 'form', 7, 'images/navigation/32_32/about.png', 0, 1, 1, 34, 0),
(57, 'home', 11, 'images/icon/27.png', 1, 1, 0, 35, -1),
(58, 'News', 2, 'images/icon/07.png', 1, 1, 0, 35, 0),
(59, 'Video', 3, 'images/icon/14.png', 1, 1, 0, 35, 0),
(60, 'Gallery', 4, 'images/icon/16.png', 1, 1, 0, 35, 0),
(61, 'Event', 5, 'images/icon/05.png', 0, 1, 0, 35, 0),
(62, 'Music', 6, 'images/icon/06.png', 0, 1, 0, 35, 0),
(63, 'Notification', 8, 'images/icon/08.png', 0, 1, 0, 35, 0),
(64, 'Cast', 10, 'images/icon/07.png', 1, 1, 0, 35, 0),
(65, 'About', 11, 'images/icon/10.png', 0, 1, 0, 35, 0),
(66, 'test form', 7, 'images/icon/23.png', 1, 1, 0, 33, 0),
(73, 'pokka', 7, 'images/icon/07.png', 1, 1, 0, 33, 0),
(74, 'popo', 7, 'images/icon/24.png', 1, 1, 0, 33, 0),
(75, 'from5', 7, 'images/icon/06.png', 1, 1, 0, 33, 0),
(76, 'wingwing', 7, 'images/icon/24.png', 1, 1, 0, 33, 0);

-- --------------------------------------------------------

--
-- 表的结构 `emp_user`
--

CREATE TABLE IF NOT EXISTS `emp_user` (
  `uid` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `uname` varchar(200) DEFAULT NULL,
  `ufname` varchar(200) DEFAULT NULL,
  `ulname` varchar(200) DEFAULT NULL,
  `uemail` varchar(200) DEFAULT NULL,
  `uphone` varchar(200) DEFAULT NULL,
  `uactive` tinyint(4) DEFAULT NULL,
  `utype` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `emp_video`
--

CREATE TABLE IF NOT EXISTS `emp_video` (
  `vid` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `vname` varchar(200) DEFAULT NULL,
  `vicon` varchar(200) DEFAULT NULL,
  `video` varchar(200) DEFAULT NULL,
  `vcaption` text,
  `vstatus` tinyint(4) DEFAULT NULL,
  `tid` int(11) DEFAULT NULL,
  `vtime` tinyint(4) NOT NULL,
  `vis_download` int(11) NOT NULL DEFAULT '0',
  `vorder` int(11) NOT NULL,
  PRIMARY KEY (`vid`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=17 ;

--
-- 转存表中的数据 `emp_video`
--

INSERT INTO `emp_video` (`vid`, `vname`, `vicon`, `video`, `vcaption`, `vstatus`, `tid`, `vtime`, `vis_download`, `vorder`) VALUES
(1, 'one:我是路飞！将要成为海贼王的男人！', 'http://202.134.127.70:8080/images/video/op1.jpg', 'http://202.134.127.70:8080/video/The_witch_h264_1226.mp4', '我是路飞！将要成为海贼王的男人！\r\n拥有整个世界财富，名声，势力的海贼王哥尔·D·罗杰把所有财宝都放在“伟大的航路”里，于是所有的男子汉都航向那里追逐梦想，世界开始迎接大海贼时代的来临。为了实现承诺成为海贼王，路飞开始航行，并且救出了落入海贼手里的克比，两人一起旅行.', 1, 0, 0, 0, 0),
(2, 'two:胜利者是谁?恶魔果实的能力对决!', 'http://202.134.127.70:8080/images/video/op2.jpg', 'http://202.134.127.70:8080/video/The_witch_h264_1226.mp4', '胜利者是谁?恶魔果实的能力对决!\r\n巴基对香克斯似乎有着深仇大恨，原来他和香克斯本来是同船的海贼，但在香克斯不知情的情况下让他吃下了恶魔果实，路飞看着心爱的帽子被巴基弄坏，一气之下把巴基轰上了天，经过这一战，娜美似乎也决定要跟路飞同行了.', 1, 1, 0, 0, 0),
(3, 'three:豪杰的再会!他的名字是火拳艾斯', 'http://202.134.127.70:8080/images/video/op3.jpg', 'http://202.134.127.70:8080/video/The_witch_h264_1226.mp4', '鲁夫与斯摩格上校激烈缠斗时，白胡子海贼第二队队长艾斯竟出手帮助快招架不住的鲁夫，艾斯使出烧烧果实能力来对抗斯摩格的冒烟果实能力，原来波特卡斯D艾斯就是鲁夫的哥哥.', 1, 1, 0, 0, 0),
(7, 'one:我是路飞！将要成为海贼王的男人！', 'http://202.134.127.70:8080/images/video/op1.jpg', 'http://202.134.127.70:8080/video/The_witch_h264_1226.mp4', '我是路飞！将要成为海贼王的男人！\r\n拥有整个世界财富，名声，势力的海贼王哥尔·D·罗杰把所有财宝都放在“伟大的航路”里，于是所有的男子汉都航向那里追逐梦想，世界开始迎接大海贼时代的来临。为了实现承诺成为海贼王，路飞开始航行，并且救出了落入海贼手里的克比，两人一起旅行.', 1, 23, 0, 0, 0),
(8, 'two:胜利者是谁?恶魔果实的能力对决!', 'http://202.134.127.70:8080/images/video/op2.jpg', 'http://202.134.127.70:8080/video/The_witch_h264_1226.mp4', '胜利者是谁?恶魔果实的能力对决!\r\n巴基对香克斯似乎有着深仇大恨，原来他和香克斯本来是同船的海贼，但在香克斯不知情的情况下让他吃下了恶魔果实，路飞看着心爱的帽子被巴基弄坏，一气之下把巴基轰上了天，经过这一战，娜美似乎也决定要跟路飞同行了.', 1, 23, 0, 0, 0),
(9, 'three:豪杰的再会!他的名字是火拳艾斯', 'http://202.134.127.70:8080/images/video/op3.jpg', 'http://202.134.127.70:8080/video/The_witch_h264_1226.mp4', '鲁夫与斯摩格上校激烈缠斗时，白胡子海贼第二队队长艾斯竟出手帮助快招架不住的鲁夫，艾斯使出烧烧果实能力来对抗斯摩格的冒烟果实能力，原来波特卡斯D艾斯就是鲁夫的哥哥.', 1, 23, 0, 0, 0),
(10, 'one:我是路飞！将要成为海贼王的男人！', 'http://202.134.127.70:8080/images/video/op1.jpg', 'http://202.134.127.70:8080/video/The_witch_h264_1226.mp4', '我是路飞！将要成为海贼王的男人！\r\n拥有整个世界财富，名声，势力的海贼王哥尔·D·罗杰把所有财宝都放在“伟大的航路”里，于是所有的男子汉都航向那里追逐梦想，世界开始迎接大海贼时代的来临。为了实现承诺成为海贼王，路飞开始航行，并且救出了落入海贼手里的克比，两人一起旅行.', 1, 23, 0, 0, 0),
(11, 'two:胜利者是谁?恶魔果实的能力对决!', 'http://202.134.127.70:8080/images/video/op2.jpg', 'http://202.134.127.70:8080/video/The_witch_h264_1226.mp4', '胜利者是谁?恶魔果实的能力对决!\r\n巴基对香克斯似乎有着深仇大恨，原来他和香克斯本来是同船的海贼，但在香克斯不知情的情况下让他吃下了恶魔果实，路飞看着心爱的帽子被巴基弄坏，一气之下把巴基轰上了天，经过这一战，娜美似乎也决定要跟路飞同行了.', 1, 23, 0, 1, 0),
(14, 'video2', 'video2', 'video2', 'video2<br />', 0, 23, 0, 0, 0),
(15, 'video3', 'images/uploads/(149).png', 'images/uploads/(151).png', '', 0, 23, 0, 0, 0),
(16, 'test', 'images/uploads/9.jpg', '', 'test', 1, 59, 0, 0, 0);

-- --------------------------------------------------------

--
-- 视图结构 `emp_app_tab`
--
DROP TABLE IF EXISTS `emp_app_tab`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`%` SQL SECURITY DEFINER VIEW `emp_app_tab` AS select `emp_application`.`aid` AS `aid`,`emp_application`.`acid` AS `acid`,`emp_application`.`aname` AS `aname`,`emp_application`.`aicon` AS `aicon`,`emp_application`.`adescription` AS `adescription`,`emp_application`.`amarket_icon` AS `amarket_icon`,`emp_application`.`aindex_icon` AS `aindex_icon`,`emp_application`.`aindex_image` AS `aindex_image`,`emp_application`.`aindex_content` AS `aindex_content`,`emp_application`.`aload_icon` AS `aload_icon`,`emp_application`.`aload_image` AS `aload_image`,`emp_application`.`acreate_time` AS `acreate_time`,`emp_application`.`aversion` AS `aversion`,`emp_application`.`aupdate_time` AS `aupdate_time`,`emp_application`.`aapproved_time` AS `aapproved_time`,`emp_application`.`astatus` AS `astatus`,`emp_application`.`aonline_link` AS `aonline_link`,`emp_application`.`atemplate` AS `atemplate`,`emp_tab`.`tname` AS `tname`,`emp_tab`.`ticon` AS `ticon`,`emp_tab`.`fnid` AS `fnid`,`emp_function`.`fnname` AS `fnname`,`emp_function`.`fntitle` AS `fntitle`,`emp_tab`.`tid` AS `tid`,`emp_tab`.`tstatus` AS `tstatus`,`emp_tab`.`ticon_type` AS `ticon_type`,`emp_tab`.`torder` AS `torder`,`emp_application`.`akey` AS `akey`,`emp_tab`.`tdefault` AS `tdefault` from ((`emp_tab` left join `emp_application` on((`emp_application`.`aid` = `emp_tab`.`aid`))) left join `emp_function` on((`emp_function`.`fnid` = `emp_tab`.`fnid`)));

-- --------------------------------------------------------

--
-- 视图结构 `emp_gallery_count`
--
DROP TABLE IF EXISTS `emp_gallery_count`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`%` SQL SECURITY DEFINER VIEW `emp_gallery_count` AS select `emp_gallery`.`gid` AS `gid`,`emp_gallery`.`gname` AS `gname`,`emp_gallery`.`gdescription` AS `gdescription`,`emp_gallery`.`gstatus` AS `gstatus`,`emp_gallery`.`gdefaut_icon` AS `gdefaut_icon`,`emp_gallery`.`gis_download` AS `gis_download`,`emp_gallery`.`gorder` AS `gorder`,`emp_gallery`.`tid` AS `tid`,count(`emp_photo`.`pid`) AS `gcount` from (`emp_gallery` left join `emp_photo` on((`emp_gallery`.`gid` = `emp_photo`.`gid`))) group by `emp_gallery`.`gid`;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;


DROP TABLE IF EXISTS `emp_app_account`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`%` SQL SECURITY DEFINER VIEW `emp_app_tab` AS select `emp_application`.`aid` AS `aid`,`emp_application`.`acid` AS `acid`,`emp_application`.`aname` AS `aname`,`emp_application`.`amarket_icon` AS `amarket_icon`,`emp_application`.`acreate_time` AS `acreate_time`,`emp_application`.`asumbit_time` AS `asumbit_time`,`emp_application`.`aupdate_time` AS `aupdate_time`,`emp_application`.`aapproved_time` AS `aapproved_time`,`emp_application`.`astatus` AS `astatus`,`emp_application`.`aonline_link` AS `aonline_link`,`emp_account`.`acname` AS `acname`from ((`emp_account` left join `emp_application` on((`emp_application`.`acid` = `emp_account`.`acid`))));
