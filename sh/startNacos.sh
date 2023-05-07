#!/bin/bash
docker pull nacos/nacos-server:2.0.3
docker run -d --name nacos -p 8848:8848 --network mynet nacos/nacos-server:2.0.3 