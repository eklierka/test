pipeline {
  agent any
  stages {
    stage('Build and Test') {
      steps {
        script {
          withCredentials([usernamePassword(credentialsId: 'docker-registry', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
            sh 'docker login -u $USERNAME -p $PASSWORD localhost:5000'
          }
        }

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
          env.SERVICE_VERSION = sh(script: 'cd sample-rest-service && ./gradlew properties -q | grep version | cut -d " " -f 2', returnStdout: true).trim()
        }

      }
    }
    stage('Deploy') {
      steps {
        timeout(time: 5, unit: 'MINUTES') {
          sh './sample-rest-service/deploy.sh $SERVICE_VERSION'
        }

        script {
          env.SERVICE_HOST = sh(script: 'ip route show | grep default | cut -d " " -f 3', returnStdout: true).trim()
        }

        script {
          env.SERVICE_PORT = sh(script: 'docker port ci-sample-rest-service | grep 8080/tcp | cut -d ":" -f 2', returnStdout: true).trim()
        }

      }
    }
    stage('Test REST API') {
      steps {
        sh 'cd sample-rest-service-tests && ./gradlew cleanTest test -i -DsampleRestService.baseUri="http://$SERVICE_HOST:$SERVICE_PORT"'
      }
    }
  }
}