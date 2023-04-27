#/bin/bash

docker rm -f goods
docker rmi goods
mvn clean install -DskipTests -Dmaven.repo.local=/Seckill
docker build --build-arg JAR_FILE=goods-0.0.1-SNAPSHOT.jar -t goods .
docker run -d --name goods -p 8083:8083 --network mynet goods