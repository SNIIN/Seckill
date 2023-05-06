#!/bin/bash

cd ..
docker rm -f core
docker rmi core
mvn clean install -DskipTests -Dmaven.repo.local=/Seckill
cd core
docker build --build-arg JAR_FILE=core-0.0.1-SNAPSHOT-exec.jar -t core .
docker run -d --name core -p 8080:8080 --network mynet core