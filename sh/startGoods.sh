#/bin/bash

docker service remove seckill-goods
docker rmi goods
cd ..
cd goods
docker node update --label-add $2=goods $1
mvn clean package -DskipTests -Dmaven.repo.local=/Seckill
docker build --build-arg JAR_FILE=goods-0.0.1-SNAPSHOT.jar -t goods .
docker service create --name seckill-goods -p 8083:8083 --constraint node.labels.$2==goods --network mynet goods
