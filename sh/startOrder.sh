#/bin/bash

docker rm -f order
docker rmi order
cd ..
cd order
mvn clean package -DskipTests -Dmaven.repo.local=/Seckill
docker build --build-arg JAR_FILE=order-0.0.1-SNAPSHOT.jar -t order .
docker run -d --name order -p 8085:8085 --network mynet order