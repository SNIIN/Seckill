#!/bin/bash

docker exec -i mysql bash <<EOF
    echo -e "[client]\ndefault-character-set=utf8mb4"|tee -a /etc/my.cnf
EOF

docker exec -i mysql mysql -uroot -prootqaq <<EOF
    source /mysq/databases/database.sql;
    source /mysq/goods/t_goods.sql;
    source /mysq/goods/t_seckill_goods.sql;
EOF
