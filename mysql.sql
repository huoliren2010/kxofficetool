create database officetool;
use officetool;
CREATE TABLE `user` (`id` int(11) NOT NULL AUTO_INCREMENT, `username`  varchar(20) NOT NULL, `password`  varchar(20) NOT NULL, `phonenumber`  varchar(11), PRIMARY KEY (`id`), UNIQUE (username)) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;