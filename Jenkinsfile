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

      }
    }
    stage('Deploy') {
      steps {
        sh 'docker rm -f ci-sample-rest-service || true'
        sh 'docker pull localhost:5000/com.example/sample-rest-service'
        sh 'docker run -d --name ci-sample-rest-service -p 18080:8080 localhost:5000/com.example/sample-rest-service'
        sh 'timeout 180 grep -q "Started SampleRestServiceApplication" <(docker logs -f ci-sample-rest-service)'
      }
    }
    stage('Test REST API') {
      steps {
        sh 'cd sample-rest-service-tests && ./gradlew cleanTest test -i -DsampleRestService.baseUri=http://localhost:18080'
      }
    }
  }
}