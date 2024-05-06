#!/bin/bash

sudo apt-get update
sudo apt-get install -y ca-certificates curl 
sudo install -m 0755 -d /etc/apt/keyrings
sudo curl -fsSL https://download.docker.com/linux/ubuntu/gpg -o /etc/apt/keyrings/docker.asc
sudo chmod a+r /etc/apt/keyrings/docker.asc
echo "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.asc] https://download.docker.com/linux/ubuntu $(. /etc/os-release && echo $VERSION_CODENAME) stable" | sudo tee /etc/apt/sources.list.d/docker.list
sudo apt-get update
sudo apt-get install -y docker-ce docker-ce-cli containerd.io
curl -sL https://aka.ms/InstallAzureCLIDeb | sudo bash
sudo az login --identity --allow-no-subscription

ACR_PASSWORD=$(sudo az keyvault secret show --name amsprojectacrpassword --vault-name ams-secret-key-vault --query value -o tsv)
MYSQL_PASSWORD=$(sudo az keyvault secret show --name mysqlpassword --vault-name ams-secret-key-vault --query value -o tsv)

sudo docker login -u amsprojectacr -p "${ACR_PASSWORD}" amsprojectacr.azurecr.io

sudo docker pull mysql/mysql-server:latest
sudo docker pull amsprojectacr.azurecr.io/ams-nginx:latest
sudo docker pull amsprojectacr.azurecr.io/ams-backend:latest

sudo docker network create ams-network

sudo docker run -it --rm 
   -p 80:80 
   -v "/etc/letsencrypt:/etc/letsencrypt" 
   certbot/certbot certonly --standalone -d asset-management-system-5.norwayeast.cloudapp.azure.com --agree-tos --email tobiagra@stud.ntnu.no; 

sudo docker run -d --name nginx \
   -p 80:80 \
   -p 443:443 \
   -v "/etc/letsencrypt:/etc/letsencrypt" \
   --network ams-network \
   amsprojectacr.azurecr.io/ams-nginx:latest

sudo docker run -d --name nginx \
   -p 8080:8080 \
   -e DATABASE_URL=ams-db \
   -e DATABASE_USERNAME=root \
   -e DATABASE_PASSWORD="${MYSQL_PASSWORD}" \
   --network ams-network \
   amsprojectacr.azurecr.io/ams-backend:latest