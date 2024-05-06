#!/bin/bash



# Function to install required packages and Docker
install_dependencies() {
    echo "Updating packages list..."
    sudo apt-get update -y

    echo "Installing required packages..."
    sudo apt-get install -y apt-transport-https ca-certificates curl software-properties-common git

    echo "Installing Docker..."
    curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
    sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable"
    sudo apt-get update -y
    sudo apt-get install -y docker-ce docker-ce-cli containerd.io

    #curl -sL https://aka.ms/InstallAzureCLIDeb | sudo bash;
}

store_secret_as_env_var(){
    echo "Setting secret with value as environment variable..."
    #sudo az login --identity --allow-no-subscription
    #export AcrPassword=$(sudo az keyvault secret show --name ContainerRepositoryPassword --vault-name spring-boot-key-vault --query value -o tsv)
}

# Function to clone repo, build and run Docker container
setup_application() {
    sudo docker login -u mngsysregistry -p ${ACRPASSWORD} mngsysregistry.azurecr.io

    sudo docker network create ams-network

    sudo docker pull certbot/certbot:latest
    sudo docker pull mngsysregistry.azurecr.io/nginx:latest
    sudo docker pull mngsysregistry.azurecr.io/ams-backend:latest

    sudo docker run -it --rm \
    -p 80:80 \
    -v "/etc/letsencrypt:/etc/letsencrypt" \
    -v "/var/lib/letsencrypt:/var/lib/letsencrypt" \
    -v "/var/log/letsencrypt:/var/log/letsencrypt" \
    -v "/etc/nginx:/etc/nginx" \
    -v "/var/www/certbot:/var/www/certbot" \
    certbot/certbot certonly --standalone -d asset-management-system-4.norwayeast.cloudapp.azure.com --agree-tos --email tobiagra@stud.ntnu.no

    sudo docker run --name nginx \
    -p 80:80 -p 443:443 \
    -v "/etc/letsencrypt:/etc/letsencrypt" \
    --network ams-network -d \
    mngsysregistry.azurecr.io/nginx:latest

    sudo docker run \
    --name ams-backend \
    -p 8080:8080 \
    -e DATABASE_URL=ams-db \
    -e DATABASE_USERNAME=backend \
    -e DATABASE_PASSWORD=root -d \
    --network ams-network mngsysregistry.azurecr.io/ams-backend:latest
}

# Log all output to a file
{
    store_secret_as_env_var
    install_dependencies
    setup_application
} > /var/log/my-custom-script.log 2>&1