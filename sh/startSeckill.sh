#/bin/bash

docker rm -f seckill
docker rmi seckill
cd ..
cd seckill
mvn clean package -DskipTests -Dmaven.repo.local=/Seckill
docker build --build-arg JAR_FILE=seckill-0.0.1-SNAPSHOT.jar -t seckill .
docker run -d --name seckill -p 8084:8084 --network mynet seckill