/*
mysql8
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
*/
CREATE ROLE IF NOT EXISTS grant_role;
GRANT GRANT OPTION ON *.* TO grant_role;

CREATE USER IF NOT EXISTS 'seckill'@'%' IDENTIFIED WITH mysql_native_password BY '123456';
GRANT ALL PRIVILEGES ON *.* TO 'seckill'@'%' WITH GRANT OPTION;
GRANT grant_role to 'seckill'@'%' with ADMIN OPTION;

CREATE DATABASE IF NOT EXISTS seckill_user CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
GRANT ALL PRIVILEGES ON seckill_user.* to 'seckill'@'%';

CREATE DATABASE IF NOT EXISTS seckill_goods CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
GRANT ALL PRIVILEGES ON seckill_goods.* to 'seckill'@'%';

CREATE DATABASE IF NOT EXISTS seckill_order CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
GRANT ALL PRIVILEGES ON seckill_order.* to 'seckill'@'%';

FLUSH PRIVILEGES;


/*
navicat连接mqsql时候，报1251错时，修改密码加密方式：
ALTER USER 'seckill'@'%' IDENTIFIED WITH mysql_native_password BY '123456';
*/
