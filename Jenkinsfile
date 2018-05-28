pipeline {
  agent any
  stages {
    stage('Build and Test') {
      steps {
        sh 'cd sample-rest-service && ./gradlew clean build dockerPush -i'
      }
    }
  }
}