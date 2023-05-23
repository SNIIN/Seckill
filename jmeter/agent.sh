#!/bin/bash

if [ ! -d "apache-jmeter-5.5" ]; then
  tar -xvzf apache-jmeter-5.5.tgz
fi

cp rmi_keystore.jks apache-jmeter-5.5/bin
IP_ADDRESS=$(hostname -I | awk '{print $1}')
cd apache-jmeter-5.5/bin
./jmeter-server -Djava.rmi.server.hostname=$IP_ADDRESS
