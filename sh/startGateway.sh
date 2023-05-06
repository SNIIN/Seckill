#!/bin/bash

docker rm -f gateway
docker rmi gateway
cd ..
cd gateway
mvn clean package -DskipTests -Dmaven.repo.local=/Seckill
docker build --build-arg JAR_FILE=gateway-0.0.1-SNAPSHOT.jar -t gateway .
docker run -d --name gateway -p 8090:8090 --network mynet gateway