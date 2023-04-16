#!/bin/bash

docker exec -i --privileged mysql mysql -uroot -prootqaq <<EOF
    source /mysq/databases/database.sql;
    source /mysq/goods/t_goods.sql;
    source /mysq/goods/t_seckill_goods.sql;
EOF
