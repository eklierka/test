# jenkins-example

docker volume create jenkins-data
docker volume create sonarqubedb-pgdata
docker volume create docker-registry-data
docker-compose up -d

http://localhost:9000

"Log in"

"Generate a token"

"Generate"

Copy generated token 

"Continue"

"Finish this tutorial"

http://localhost:9000/admin/settings?category=webhooks

URL: "http://jenkins:8080/sonarqube-webhook/"

http://localhost:8080

docker-compose exec jenkins cat /var/jenkins_home/secrets/initialAdminPassword

"Install suggested plugins"

"Create First Admin User"

http://localhost:8080/pluginManager/available
Filter: sonar
"SonarQube Scanner"
Filter: checkstyle
"Checkstyle"
"Install without restart"
"Restart Jenkins when installation is complete and no jobs are running"

http://localhost:8080/configure
"SonarQube servers"
"Enable injection of SonarQube server configuration as build environment variables"
"Add SonarQube"
Name: SonarQube Server
Server URL: http://sonarqube:9000
Server authentication token: ***

http://localhost:8080/credentials/store/system/domain/_/newCredentials
Kind: Username with password
ID: dockerregistry
Username: admin
Password: admin

http://localhost:8080/blue

"Create a new Pipeline"

"GitHub"

Follow the instructions to create personal access token in GitHub.

Select repository and "Create Pipeline"

http://localhost:18080/swagger-ui.html
http://localhost:18080/greeting
http://localhost:18080/greeting?name=User
