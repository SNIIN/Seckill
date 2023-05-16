#!/bin/bash 
docker service rm redis
docker rmi redis 
docker node update --label-add $2=redis $1 
docker pull redis 
docker service create -d --name redis --constraint node.labels.$2==redis -p 6379:6379 --network mynet redis 
