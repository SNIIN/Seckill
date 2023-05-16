#!/bin/bash
docker swarm init 
docker network create -d overlay --attachable mynet
