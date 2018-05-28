pipeline {
  agent any
  environment {
    SONARQUBE_TOKEN = credentials('sonarqube-token')
  }
  stages {
    stage('Build and Test') {
      steps {
        sh 'cd sample-rest-service && ./gradlew clean build dockerPush -i'
      }
    }
    stage('Static Analysis') {
      steps {
        sh 'cd sample-rest-service && ./gradlew sonarqube -Dsonar.host.url=http://sonarqube:9000 -Dsonar.login=$SONARQUBE_TOKEN'
      }
    }
  }
}