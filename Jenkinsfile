pipeline {
  agent any
  stages {
    stage('Build and Test') {
      steps {
        sh 'cd sample-rest-service && ./gradlew clean build dockerPush --info'
      }
    }
    stage('Static Analysis') {
      steps {
        withSonarQubeEnv('SonarQube') {
          // requires SonarQube Scanner for Gradle 2.1+
          // It's important to add --info because of SONARJNKNS-281
          sh 'cd sample-rest-service && ./gradlew --info sonarqube'
        }
      }
    }
  }
}