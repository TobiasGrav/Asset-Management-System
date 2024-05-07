#!/bin/bash

# Define log file path
LOG_FILE="/var/log/vminit.log"

# Redirect stdout and stderr to the log file
exec > >(tee -a "$LOG_FILE") 2>&1

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

sudo apt-get update
sudo apt-get install -y certbot

ACR_PASSWORD=$(sudo az keyvault secret show --name amsprojectacrpassword --vault-name ams-secret-key-vault --query value -o tsv)
MYSQL_PASSWORD=$(sudo az keyvault secret show --name mysqlpassword --vault-name ams-secret-key-vault --query value -o tsv)

sudo docker login -u amsprojectacr -p "${ACR_PASSWORD}" amsprojectacr.azurecr.io

sudo docker network create ams-network

sudo certbot certonly --standalone -d asset-management-system-6.norwayeast.cloudapp.azure.com --non-interactive --agree-tos --email tobiagra@stud.ntnu.no

#sudo docker pull certbot/certbot:latest
#sudo docker run -it -p 80:80 -v "/etc/letsencrypt:/etc/letsencrypt" -v "/var/lib/letsencrypt:/var/lib/letsencrypt" -v "/var/log/letsencrypt:/var/log/letsencrypt" -v "/etc/nginx:/etc/nginx" -v "/var/www/certbot:/var/www/certbot" certbot/certbot certonly --standalone -d asset-management-system-5.norwayeast.cloudapp.azure.com --non-interactive --agree-tos --email tobiagra@stud.ntnu.no
sudo docker pull amsprojectacr.azurecr.io/ams-backend:latest
sudo docker run -d --name ams-backend -p 8080:8080 -e DATABASE_URL="51.13.61.6" -e DATABASE_USERNAME=root -e DATABASE_PASSWORD="${MYSQL_PASSWORD}" --network ams-network amsprojectacr.azurecr.io/ams-backend:latest

sudo docker pull amsprojectacr.azurecr.io/ams-nginx:latest
sudo docker run -d --name ams-nginx -p 80:80 -p 443:443 -v "/etc/letsencrypt:/etc/letsencrypt" --network ams-network amsprojectacr.azurecr.io/ams-nginx:latest

