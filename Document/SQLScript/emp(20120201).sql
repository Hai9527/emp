-- phpMyAdmin SQL Dump
-- version 3.4.5
-- http://www.phpmyadmin.net
--
-- 主机: localhost
-- 生成日期: 2012 年 01 月 09 日 09:15
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

DROP TABLE IF EXISTS `emp_account`;
CREATE TABLE IF NOT EXISTS `emp_account` (
  `acid` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `acname` varchar(200) DEFAULT NULL,
  `acfname` varchar(200) DEFAULT NULL,
  `aclname` varchar(200) DEFAULT NULL,
  `acemail` varchar(200) DEFAULT NULL,
  `acphone` varchar(200) DEFAULT NULL,
  `acstatus` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`acid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `emp_administrator`
--

DROP TABLE IF EXISTS `emp_administrator`;
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

DROP TABLE IF EXISTS `emp_application`;
CREATE TABLE IF NOT EXISTS `emp_application` (
  `aid` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `acid` int(11) DEFAULT NULL,
  `aname` varchar(200) DEFAULT NULL,
  `aicon` varchar(200) DEFAULT NULL,
  `amarket_icon` varchar(200) DEFAULT NULL,
  `aindex_icon` varchar(200) DEFAULT NULL,
  `aindex_image` varchar(200) DEFAULT NULL,
  `aindex_content` text NOT NULL,
  `aload_icon` varchar(200) DEFAULT NULL,
  `aload_image` varchar(200) DEFAULT NULL,
  `acreate_time` datetime DEFAULT NULL,
  `aversion` varchar(10) NOT NULL,
  `aupdate_time` datetime DEFAULT NULL,
  PRIMARY KEY (`aid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `emp_article`
--

DROP TABLE IF EXISTS `emp_article`;
CREATE TABLE IF NOT EXISTS `emp_article` (
  `artid` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `artname` varchar(200) DEFAULT NULL,
  `artshort_description` text,
  `artcontent` text,
  `artstatus` tinyint(4) DEFAULT NULL,
  `artcreate_time` datetime DEFAULT NULL,
  `tid` int(11) DEFAULT NULL,
  PRIMARY KEY (`artid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `emp_download`
--

DROP TABLE IF EXISTS `emp_download`;
CREATE TABLE IF NOT EXISTS `emp_download` (
  `did` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `dname` varchar(200) DEFAULT NULL,
  `daddress` varchar(200) DEFAULT NULL,
  `dtype` tinyint(4) DEFAULT NULL,
  `aid` int(11) DEFAULT NULL,
  PRIMARY KEY (`did`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `emp_event`
--

DROP TABLE IF EXISTS `emp_event`;
CREATE TABLE IF NOT EXISTS `emp_event` (
  `eid` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `etitle` varchar(200) DEFAULT NULL,
  `edescription` text,
  `estart_time` datetime DEFAULT NULL,
  `eend_time` datetime DEFAULT NULL,
  `estatus` tinyint(4) DEFAULT NULL,
  `tid` int(11) DEFAULT NULL,
  PRIMARY KEY (`eid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `emp_form`
--

DROP TABLE IF EXISTS `emp_form`;
CREATE TABLE IF NOT EXISTS `emp_form` (
  `fid` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `acid` int(11) DEFAULT NULL,
  `field` varchar(200) DEFAULT NULL,
  `fintroduction` text,
  `lmessage` text,
  `tid` int(11) DEFAULT NULL,
  PRIMARY KEY (`fid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `emp_form_field`
--

DROP TABLE IF EXISTS `emp_form_field`;
CREATE TABLE IF NOT EXISTS `emp_form_field` (
  `ffid` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `ffname` varchar(200) DEFAULT NULL,
  `ffdefault` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`ffid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `emp_function`
--

DROP TABLE IF EXISTS `emp_function`;
CREATE TABLE IF NOT EXISTS `emp_function` (
  `fnid` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `fnname` varchar(200) DEFAULT NULL,
  `fnicon` varchar(200) DEFAULT NULL,
  `fndefault` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`fnid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `emp_gallery`
--

DROP TABLE IF EXISTS `emp_gallery`;
CREATE TABLE IF NOT EXISTS `emp_gallery` (
  `gid` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `gname` varchar(200) NOT NULL,
  `gdescription` text,
  `gstatus` tinyint(4) DEFAULT NULL,
  `gdefaut_icon` varchar(200) DEFAULT NULL,
  `tid` int(11) DEFAULT NULL,
  `gcover` int(11) NOT NULL,
  PRIMARY KEY (`gid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 替换视图以便查看 `emp_gallery_photo`
--
DROP VIEW IF EXISTS `emp_gallery_photo`;
CREATE TABLE IF NOT EXISTS `emp_gallery_photo` (
`gid` int(11) unsigned
,`gname` varchar(200)
,`gdescription` text
,`gstatus` tinyint(4)
,`gdefaut_icon` varchar(200)
,`tid` int(11)
,`gcover` int(11)
,`pid` int(11) unsigned
,`pname` varchar(200)
,`photo` varchar(200)
,`picon` varchar(200)
);
-- --------------------------------------------------------

--
-- 表的结构 `emp_icon`
--

DROP TABLE IF EXISTS `emp_icon`;
CREATE TABLE IF NOT EXISTS `emp_icon` (
  `iid` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `iname` varchar(200) DEFAULT NULL,
  `icon` varchar(200) DEFAULT NULL,
  `idefault` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`iid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `emp_list_form`
--

DROP TABLE IF EXISTS `emp_list_form`;
CREATE TABLE IF NOT EXISTS `emp_list_form` (
  `lid` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `lemail` varchar(200) DEFAULT NULL,
  `lpreferred` tinyint(4) DEFAULT NULL,
  `lcontact_name` varchar(200) DEFAULT NULL,
  `lphone` varchar(200) DEFAULT NULL,
  `lfirst_name` varchar(200) DEFAULT NULL,
  `lcity` varchar(200) DEFAULT NULL,
  `iaddress` varchar(200) DEFAULT NULL,
  `lstate` varchar(200) DEFAULT NULL,
  `lcountry` varchar(200) DEFAULT NULL,
  `lcode` int(11) DEFAULT NULL,
  `tid` int(11) DEFAULT NULL,
  PRIMARY KEY (`lid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `emp_notification`
--

DROP TABLE IF EXISTS `emp_notification`;
CREATE TABLE IF NOT EXISTS `emp_notification` (
  `nid` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `nsubject` varchar(200) DEFAULT NULL,
  `ncontent` text,
  `ncreate_time` datetime DEFAULT NULL,
  `nstatus` tinyint(4) DEFAULT NULL,
  `aid` int(11) DEFAULT NULL,
  PRIMARY KEY (`nid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `emp_photo`
--

DROP TABLE IF EXISTS `emp_photo`;
CREATE TABLE IF NOT EXISTS `emp_photo` (
  `pid` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `pname` varchar(200) DEFAULT NULL,
  `photo` varchar(200) DEFAULT NULL,
  `picon` varchar(200) DEFAULT NULL,
  `gid` int(11) DEFAULT NULL,
  PRIMARY KEY (`pid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `emp_record`
--

DROP TABLE IF EXISTS `emp_record`;
CREATE TABLE IF NOT EXISTS `emp_record` (
  `rid` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tid` int(11) DEFAULT NULL,
  `mobileid` varchar(200) DEFAULT NULL,
  `rpost_time` datetime DEFAULT NULL,
  PRIMARY KEY (`rid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `emp_tap`
--

DROP TABLE IF EXISTS `emp_tap`;
CREATE TABLE IF NOT EXISTS `emp_tap` (
  `tid` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tname` varchar(200) DEFAULT NULL,
  `fnid` int(11) NOT NULL,
  `ticon` varchar(200) DEFAULT NULL,
  `tcustomize_icon` varchar(200) DEFAULT NULL,
  `tstatus` tinyint(4) DEFAULT NULL,
  `order` int(11) NOT NULL DEFAULT '0',
  `aid` int(11) DEFAULT NULL,
  `iid` int(11) DEFAULT NULL,
  PRIMARY KEY (`tid`,`fnid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `emp_user`
--

DROP TABLE IF EXISTS `emp_user`;
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

DROP TABLE IF EXISTS `emp_video`;
CREATE TABLE IF NOT EXISTS `emp_video` (
  `vid` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `vname` varchar(200) DEFAULT NULL,
  `vicon` varchar(200) DEFAULT NULL,
  `video` varchar(200) DEFAULT NULL,
  `vcaption` text,
  `vstatus` tinyint(4) DEFAULT NULL,
  `tid` int(11) DEFAULT NULL,
  PRIMARY KEY (`vid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 视图结构 `emp_gallery_photo`
--
DROP TABLE IF EXISTS `emp_gallery_photo`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`%` SQL SECURITY DEFINER VIEW `emp_gallery_photo` AS select `emp_gallery`.`gid` AS `gid`,`emp_gallery`.`gname` AS `gname`,`emp_gallery`.`gdescription` AS `gdescription`,`emp_gallery`.`gstatus` AS `gstatus`,`emp_gallery`.`gdefaut_icon` AS `gdefaut_icon`,`emp_gallery`.`tid` AS `tid`,`emp_gallery`.`gcover` AS `gcover`,`emp_photo`.`pid` AS `pid`,`emp_photo`.`pname` AS `pname`,`emp_photo`.`photo` AS `photo`,`emp_photo`.`picon` AS `picon` from (`emp_photo` left join `emp_gallery` on((`emp_photo`.`gid` = `emp_gallery`.`gid`))) where 1;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
