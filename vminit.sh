#!/bin/bash

sudo apt-get update
sudo apt-get install -y ca-certificates curl 
sudo install -m 0755 -d /etc/apt/keyrings
sudo curl -fsSL https://download.docker.com/linux/ubuntu/gpg -o /etc/apt/keyrings/docker.asc
sudo chmod a+r /etc/apt/keyrings/docker.asc
echo \"deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.asc] https://download.docker.com/linux/ubuntu $(. /etc/os-release && echo $VERSION_CODENAME) stable\" | sudo tee /etc/apt/sources.list.d/docker.list
sudo apt-get update; ', 'sudo apt-get install -y docker-ce docker-ce-cli containerd.io
curl -sL https://aka.ms/InstallAzureCLIDeb | sudo bash;', 'sudo az login --identity --allow-no-subscription
export AcrPassword=$(sudo az keyvault secret show --name ContainerRepositoryPassword --vault-name spring-boot-key-vault --query value -o tsv)
#echo $AcrPassword | docker login -u privatespringbootappregistry --password-stdin privatespringbootappregistry.azurecr.io
#sudo docker pull privatespringbootappregistry.azurecr.io/spring-boot-app:', parameters('versionTag')
#sudo docker run -d -p 80:8080 privatespringbootappregistry.azurecr.io/spring-boot-app:', parameters('versionTag')