CREATE USER IF NOT EXISTS 'seckill'@'%' IDENTIFIED BY '123456';
GRANT PROCESS ON *.* TO 'seckill'@'%';
GRANT PROCESS ON *.* TO 'seckill'@'localhost';

CREATE DATABASE IF NOT EXISTS seckill CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
GRANT ALL PRIVILEGES ON seckill.* to 'seckill'@'%';
GRANT ALL PRIVILEGES ON seckill.* to 'seckill'@'localhost';

FLUSH PRIVILEGES;


/*
navicat连接mqsql时候，报1251错时，修改密码加密方式：
ALTER USER 'seckill'@'%' IDENTIFIED WITH mysql_native_password BY '123456';
*/