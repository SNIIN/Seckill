#/bin/bash

docker service remove seckill-seckill
docker rmi seckill
cd ..
cd seckill
docker node update --label-add $2=seckill $1
mvn clean package -DskipTests -Dmaven.repo.local=/Seckill
docker build --build-arg JAR_FILE=seckill-0.0.1-SNAPSHOT.jar -t seckill .
docker service create -d --name seckill-seckill -p 8084:8084 --constraint node.labels.$2==seckill --network mynet seckill
