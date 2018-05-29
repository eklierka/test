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

      }
    }
    stage('Deploy') {
      steps {
        timeout(time: 5, unit: 'MINUTES') {
          sh './sample-rest-service/deploy.sh'
        }

      }
    }
    stage('Test REST API') {
      steps {
        sh 'cd sample-rest-service-tests && ./gradlew cleanTest test -i -DsampleRestService.baseUri="http://$(ip route show | awk \\\'/default/ {print $3}\\\'):18080'
      }
    }
  }
}