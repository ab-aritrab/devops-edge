MAVE_VERSION="3.8.5"
sudo apt install wget
wget https://dlcdn.apache.org/maven/maven-3/$(MAVE_VERSION)/binaries/apache-maven-$(MAVE_VERSION)-bin.tar.gz
# from archive directory for older version
#wget https://archive.apache.org/dist/maven/maven-3/3.8.5/binaries/apache-maven-3.8.5-bin.tar.gz
tar xvf apache-maven-$(MAVE_VERSION)-bin.tar.gz
mkdir -p /opt/maven
mv apache-maven-3.8.3 /opt/maven/
vim /etc/profile.d/maven.sh 
------------------------
export M2_HOME=/opt/maven/apache-maven-3.8.3
export MAVEN_HOME=/opt/maven/apache-maven-3.8.3
export PATH=${M2_HOME}/bin:${PATH}
------------------------
chmod +x /etc/profile.d/maven.sh
sh /etc/profile.d/maven.sh

**********exit from root && open new terminal*************
mvn -version
echo $JAVA_HOME
-----------------------OUTPUT-------------------
[centos@ip-10-20-0-91 ~]$ echo $JAVA_HOME
/usr/lib/jvm/jdk-11.0.12
 
echo $MAVEN_HOME
-----------------------OUTPUT-------------------
[centos@ip-10-20-0-91 ~]$ echo $MAVEN_HOME
/opt/maven/apache-maven-3.8.3
