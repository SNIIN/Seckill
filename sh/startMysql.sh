#!/bin/bash
cwd=$(pwd)
if [ -n "$(docker ps -aqf name=mysql)" ]; then
    docker rm -f mysql
fi

docker run -d  --privileged=true --name mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=rootqaq -v $cwd/mysql:/mysq/databases -v $cwd/goods/sql:/mysq/goods -v $cwd/core/sql:/mysq/core -v $cwd/order/sql:/mysq/order mysql

