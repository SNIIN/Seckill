#/bin/bash

docker service remove  seckill-order
docker rmi order
cd ..
cd order
docker node update --label-add $2=order $1
mvn clean package -DskipTests -Dmaven.repo.local=/Seckill
docker build --build-arg JAR_FILE=order-0.0.1-SNAPSHOT.jar -t order .
docker service create -d --name seckill-order -p 8085:8085 --constraint node.labels.$2==order --network mynet order
