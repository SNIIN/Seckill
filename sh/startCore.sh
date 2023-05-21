#!/bin/bash

cd ..
docker service remove seckill-core
docker rmi core
mvn clean install -DskipTests -Dmaven.repo.local=/Seckill
docker node update --label-add $2=core $1
cd core
docker build --build-arg JAR_FILE=core-0.0.1-SNAPSHOT-exec.jar -t core .
docker service create -d --name seckill-core -p 8080:8080 --constraint node.labels.$2==core --mount type=bind,source=/jmeter,target=/jmeter --network mynet core
