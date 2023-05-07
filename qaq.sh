#!/bin/bash
sudo apt-get install wget apt-transport-https gnupg
wget -qO - https://adoptopenjdk.jfrog.io/adoptopenjdk/api/gpg/key/public | sudo apt-key add -
echo "deb https://adoptopenjdk.jfrog.io/adoptopenjdk/deb bionic main" | sudo tee /etc/apt/sources.list.d/adoptopenjdk.list
sudo apt-get update && sudo apt-get install adoptopenjdk-11-hotspot