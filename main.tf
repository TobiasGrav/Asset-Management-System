terraform {
  required_providers {
    azurerm = {
      source = "hashicorp/azurerm"
      version = "3.101.0"
    }
  }


}

provider "azurerm" {
  features {
    
  }
}

# Create resource group
resource "azurerm_resource_group" "ubuntu-deployment-rg" {
    name        = "deploy-asset-management-system"
    location    = "norwayeast"
    tags        = {
        environment = "deploy"
    }
}

# Create virtual network
resource "azurerm_virtual_network" "deploy-vnet" {
  name                = "deploy-network"
  resource_group_name = azurerm_resource_group.ubuntu-deployment-rg.name
  location            = azurerm_resource_group.ubuntu-deployment-rg.location
  address_space       = ["10.123.0.0/16"]

  tags = {
    environment = "deploy"
  }
}
# Create subnet within virtual network. Best practice as a separate resource
resource "azurerm_subnet" "deploy-subnet" {
  name                 = "subnet1"
  resource_group_name  = azurerm_resource_group.ubuntu-deployment-rg.name
  virtual_network_name = azurerm_virtual_network.deploy-vnet.name
  address_prefixes     = ["10.123.1.0/24"]
}
# Create Network Security Group (NSG)
resource "azurerm_network_security_group" "deploy-nsg" {
  name                = "deploy-nsg"
  location            = azurerm_resource_group.ubuntu-deployment-rg.location
  resource_group_name = azurerm_resource_group.ubuntu-deployment-rg.name
  tags = {
    environment = "deploy"
  }
}

# Create Network Security Rule For HTTP
resource "azurerm_network_security_rule" "deploy-rule-mysql" {
  name                        = "deploy-rule-mysql"
  priority                    = 250
  direction                   = "Inbound"
  access                      = "Allow"
  protocol                    = "Tcp"
  source_port_range           = "*"
  destination_port_range      = "3306"
  source_address_prefix       = "*"
  destination_address_prefix  = "*"
  resource_group_name         = azurerm_resource_group.ubuntu-deployment-rg.name
  network_security_group_name = azurerm_network_security_group.deploy-nsg.name
}

# Create Network Security Rule For HTTP
resource "azurerm_network_security_rule" "deploy-rule-https" {
  name                        = "deploy-rule-https"
  priority                    = 200
  direction                   = "Inbound"
  access                      = "Allow"
  protocol                    = "Tcp"
  source_port_range           = "*"
  destination_port_range      = "443"
  source_address_prefix       = "*"
  destination_address_prefix  = "*"
  resource_group_name         = azurerm_resource_group.ubuntu-deployment-rg.name
  network_security_group_name = azurerm_network_security_group.deploy-nsg.name
}

# Create Network Security Rule For HTTP
resource "azurerm_network_security_rule" "deploy-rule-http" {
  name                        = "deploy-rule-http"
  priority                    = 150
  direction                   = "Inbound"
  access                      = "Allow"
  protocol                    = "Tcp"
  source_port_range           = "*"
  destination_port_range      = "80"
  source_address_prefix       = "*"
  destination_address_prefix  = "*"
  resource_group_name         = azurerm_resource_group.ubuntu-deployment-rg.name
  network_security_group_name = azurerm_network_security_group.deploy-nsg.name
}

# Create Network Security Rule For SSH
resource "azurerm_network_security_rule" "deploy-rule-ssh" {
  name                        = "deploy-rule-ssh"
  priority                    = 100
  direction                   = "Inbound"
  access                      = "Allow"
  protocol                    = "Tcp"
  source_port_range           = "*"
  destination_port_range      = "22"
  source_address_prefix       = "*"
  destination_address_prefix  = "*"
  resource_group_name         = azurerm_resource_group.ubuntu-deployment-rg.name
  network_security_group_name = azurerm_network_security_group.deploy-nsg.name
}

# Create the NSG association
resource "azurerm_subnet_network_security_group_association" "deploy-nsga" {
  subnet_id                 = azurerm_subnet.deploy-subnet.id
  network_security_group_id = azurerm_network_security_group.deploy-nsg.id
}
# Create public ip for linux VM
resource "azurerm_public_ip" "deploy-ip" {
  name                = "deploy-ip"
  resource_group_name = azurerm_resource_group.ubuntu-deployment-rg.name
  location            = azurerm_resource_group.ubuntu-deployment-rg.location
  allocation_method   = "Dynamic"
  domain_name_label = "asset-management-system-test"

  tags = {
    environment = "deploy"
  }
}
# Create Linux public Ip
resource "azurerm_network_interface" "deploy-nic" {
  name                = "deploy-nic"
  location            = azurerm_resource_group.ubuntu-deployment-rg.location
  resource_group_name = azurerm_resource_group.ubuntu-deployment-rg.name

  ip_configuration {
    name                          = "internal"
    subnet_id                     = azurerm_subnet.deploy-subnet.id
    private_ip_address_allocation = "Dynamic"
    public_ip_address_id          = azurerm_public_ip.deploy-ip.id
  }
  tags = {
    environment = "deploy"
  }
}

variable "rollback" {
  description = "Flag to determine if rollback should be executed"
  type        = bool
  default     = false
}

# Passes secret to rollback.tpl
data "template_file" "rollback" {
  template = file("${path.module}/rollback.tpl")
  vars = {
    jwt_secret_key = var.jwt_secret_key
    ACRPASSWORD = var.ACRPASSWORD
  }
}

# Holds on the jwt secret that is passed to main.tf
variable "jwt_secret_key" {
  type        = string
  description = "JWT secret key for authentication"
  default     = ""
  sensitive   = true
}

# Holds on the jwt secret that is passed to main.tf
variable "ACRPASSWORD" {
  type        = string
  description = "Password for azure container registry"
  default     = ""
  sensitive   = true
}

# Passes the jwt secret to the deploy.tpl
data "template_file" "deploy" {
  template = file("${path.module}/deploy.tpl")

  vars = {
    jwt_secret_key = var.jwt_secret_key
    ACRPASSWORD = var.ACRPASSWORD
  }
}

# Create Linux Virtual Machine
resource "azurerm_linux_virtual_machine" "deploy-vm" {
  name                  = "deploy-vm"
  resource_group_name   = azurerm_resource_group.ubuntu-deployment-rg.name
  location              = azurerm_resource_group.ubuntu-deployment-rg.location
  size                  = "Standard_D2s_v3"
  admin_username        = "adminuser"
  network_interface_ids = [azurerm_network_interface.deploy-nic.id]
 
  admin_ssh_key {
    username   = "adminuser"
    public_key = file("~/.ssh/id_rsa.pub")
  }
  os_disk {
    caching              = "ReadWrite"
    storage_account_type = "Standard_LRS"
  }

  source_image_reference {
    publisher = "Canonical"
    offer     = "UbuntuServer"
    sku       = "18_04-lts-gen2"
    version   = "latest"
  }

  # Add custom data
  custom_data = base64encode(var.rollback ? data.template_file.rollback.rendered : data.template_file.deploy.rendered)

}