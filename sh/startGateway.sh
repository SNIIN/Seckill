#!/bin/bash

docker service remove seckill-gateway
docker rmi gateway
cd ..
cd gateway
docker node update --label-add $2=gateway $1
mvn clean package -DskipTests -Dmaven.repo.local=/Seckill
docker build --build-arg JAR_FILE=gateway-0.0.1-SNAPSHOT.jar -t gateway .
docker service create -d --name seckill-gateway -p 8090:8090 --constraint node.labels.$2==gateway --network mynet gateway
