drop database if exists bk;
drop user if exists 'bi'@'localhost';
-- 支持emoji：需要mysql数据库参数： character_set_server=utf8mb4
create database bi default character set utf8mb4 collate utf8mb4_unicode_ci;
use bi;
create user 'bi'@'localhost' identified by 'Citic12345';
grant all privileges on bi.* to 'bi'@'localhost';
flush privileges;
