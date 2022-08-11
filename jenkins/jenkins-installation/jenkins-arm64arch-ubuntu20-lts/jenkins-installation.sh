#!bin/bash
#install prerequisites
apt update
apt install openjdk-11-jdk -y

#install jenkins
wget -q -O - https://pkg.jenkins.io/debian/jenkins.io.key | sudo apt-key add -
echo "deb https://pkg.jenkins.io/debian binary/" | sudo tee /etc/apt/sources.list.d/docker.list
apt update
apt-get install jenkins -y
systemctl start jenkins
#systemctl status jenkins
#Setting up jenkins (2.361) ...
#Created symlink /etc/systemd/system/multi-user.target.wants/jenkins.service → /lib/systemd/system/jenkins.service.


# get the secrets 
cat /var/lib/jenkins/secrets/initialAdminPassword > jenkins-secrets.txt

#post installation
#docker 
apt-get update -y
apt-get install \
    ca-certificates \
    curl \
    gnupg \
    lsb-release -y
mkdir -p /etc/apt/keyrings
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /etc/apt/keyrings/docker.gpg
echo \
  "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/ubuntu \
  $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
apt-get update -y
apt-get install docker-ce docker-ce-cli containerd.io docker-compose-plugin -y
chmod 777 /var/run/docker.sock

# optional
#chown -R jenkins:root /var/run/docker.sock
#chown -R jenkins:root /var/run/docker.sock

#GIT 
apt-get install git
apt-get update
git init
#whereis git



