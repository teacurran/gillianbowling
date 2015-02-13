-- // create accounts table
-- Migration SQL that makes the change goes here.

CREATE TABLE `accounts` (
  `id` int(11) NOT NULL auto_increment,
  `username` varchar(200) default NULL,
  `password` varchar(200) default NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDb DEFAULT CHARSET=UTF8;


-- //@UNDO
-- SQL to undo the change goes here.

DROP TABLE `accounts`;


