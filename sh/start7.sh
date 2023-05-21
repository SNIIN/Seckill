#!/bin/bash

./startNacos.sh $1 nacos
sleep 8
./startRedis.sh $1 redis
sleep 8
./startCore.sh $1 core
sleep 8
./startGoods.sh $1 goods 
sleep 8
./startOrder.sh $1 order
sleep 8
./startSeckill.sh $1 seckill
sleep 8
./startGateway.sh $1 gateway 
sleep 8