pipeline {
  agent any
  stages {
    stage('Build and Test') {
      steps {
        script {
          withSonarQubeEnv('SonarQube Server') { sh 'cd sample-rest-service && ./gradlew clean build dockerPush sonar -i'  }
        }

        junit '**/build/test-results/test/*.xml'
        checkstyle(pattern: '**/build/reports/checkstyle/*.xml')
        script {
          publishHTML(target: [allowMissing: false, alwaysLinkToLastBuild: false, keepAll: true, reportDir: 'sample-rest-service/build/reports/tests/test', reportFiles: 'index.html', reportName: 'Test Report'])
        }

        script {
          publishHTML(target: [allowMissing: false, alwaysLinkToLastBuild: false, keepAll: true, reportDir: 'sample-rest-service/build/reports/jacoco/test/html', reportFiles: 'index.html', reportName: 'Test Coverage Report'])
        }

        timeout(time: 5, unit: 'MINUTES') {
          waitForQualityGate true
        }

        script {
          env.GRADLE_PROJECT_VERSION = sh(script: 'cd sample-rest-service && ./gradlew properties -q | grep version | cut -d " " -f 2', returnStdout: true).trim()
        }

      }
    }
    stage('Deploy') {
      steps {
        timeout(time: 5, unit: 'MINUTES') {
          sh './sample-rest-service/deploy.sh $GRADLE_PROJECT_VERSION'
        }

      }
    }
    stage('Test REST API') {
      steps {
        sh 'cd sample-rest-service-tests && ./gradlew cleanTest test -i -DsampleRestService.baseUri="http://$(ip route show | grep default | cut -d " " -f 3):18080"'
      }
    }
  }
}