pipeline {
  agent any
  stages {
    stage('Build and Test') {
      agent any
      steps {
        sh 'cd sample-rest-service && ./gradlew clean build -i'
        junit '**/build/test-results/test/*.xml'
        checkstyle(pattern: '**/build/reports/checkstyle/*.xml')
        script {
          publishHTML(target: [allowMissing: false, alwaysLinkToLastBuild: false, keepAll: true, reportDir: 'sample-rest-service/build/reports/tests/test', reportFiles: 'index.html', reportName: 'Test Report'])
        }

        script {
          publishHTML(target: [allowMissing: false, alwaysLinkToLastBuild: false, keepAll: true, reportDir: 'sample-rest-service/build/reports/jacoco/test/html', reportFiles: 'index.html', reportName: 'Test Coverage Report'])
        }

      }
    }
    stage('Static Analysis') {
      agent any
      steps {
        script {
          withSonarQubeEnv('SonarQube Server') { sh 'cd sample-rest-service && ./gradlew sonar -i'  }
        }

        timeout(time: 5, unit: 'MINUTES') {
          waitForQualityGate true
        }

      }
    }
    stage('Publish Docker Image') {
      agent any
      steps {
        sh 'cd sample-rest-service && ./gradlew dockerPush'
      }
    }
    stage('Deploy') {
      agent any
      steps {
        timeout(time: 5, unit: 'MINUTES') {
          sh './sample-rest-service/deploy.sh'
        }

      }
    }
    stage('Test REST API') {
      agent any
      steps {
        sh 'cd sample-rest-service-tests && ./gradlew cleanTest test -i -DsampleRestService.baseUri=http://localhost:18080'
      }
    }
  }
}