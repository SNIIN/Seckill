#!/bin/bash

docker exec -i mysql bash <<EOF    
    echo -e "[client]\ndefault-character-set=utf8mb4"|tee -a /etc/my.cnf 
EOF

docker exec -i mysql mysql -uroot -prootqaq <<EOF
    source /mysq/databases/database.sql;
    source /mysq/order/t_order.sql; 
EOF