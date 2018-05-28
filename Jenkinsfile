pipeline {
  agent any
  stages {
    stage('Build and Test') {
      steps {
        sh 'cd sample-rest-service && ./gradlew clean build dockerPush -i'
        junit '**/build/test-results/test/*.xml'
        script {
          publishHTML(target: [allowMissing: false, alwaysLinkToLastBuild: false, keepAll: true, reportDir: 'sample-rest-service/build/reports/tests/test', reportFiles: 'index.html', reportName: 'Test Report'])
        }

        script {
          publishHTML(target: [allowMissing: false, alwaysLinkToLastBuild: false, keepAll: true, reportDir: 'sample-rest-service/build/reports/jacoco/test/html', reportFiles: 'index.html', reportName: 'Test Coverage Report'])
        }

      }
    }
  }
}