#!/bin/bash
docker service rm nacos
docker rmi nacos/nacos-server:2.0.3
docker node update --label-add $2=nacos $1
docker pull nacos/nacos-server:2.0.3
docker service create -d --name nacos --constraint node.labels.$2==nacos -p 8848:8848 --network mynet -e MODE=standalone nacos/nacos-server:2.0.3 
