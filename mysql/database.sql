CREATE USER IF NOT EXISTS 'seckill'@'%' IDENTIFIED WITH mysql_native_password BY '123456';
GRANT ALL PRIVILEGES ON *.* TO 'seckill'@'%';
GRANT ALL PRIVILEGES ON *.* TO 'seckill'@'localhost';

CREATE DATABASE IF NOT EXISTS seckill_user CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
GRANT ALL PRIVILEGES ON seckill_user.* to 'seckill'@'%';
GRANT ALL PRIVILEGES ON seckill_user.* to 'seckill'@'localhost';

CREATE DATABASE IF NOT EXISTS seckill_goods CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
GRANT ALL PRIVILEGES ON seckill_goods.* to 'seckill'@'%';
GRANT ALL PRIVILEGES ON seckill_goods.* to 'seckill'@'localhost';

CREATE DATABASE IF NOT EXISTS seckill_order CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
GRANT ALL PRIVILEGES ON seckill_order.* to 'seckill'@'%';
GRANT ALL PRIVILEGES ON seckill_order.* to 'seckill'@'localhost';

FLUSH PRIVILEGES;


/*
navicat连接mqsql时候，报1251错时，修改密码加密方式：
ALTER USER 'seckill'@'%' IDENTIFIED WITH mysql_native_password BY '123456';
*/