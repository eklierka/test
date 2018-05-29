#!/bin/bash
docker rm -f ci-sample-rest-service || true
docker rmi localhost:5000/com.example/sample-rest-service
docker pull localhost:5000/com.example/sample-rest-service
docker run -d --name ci-sample-rest-service -p 18080:8080 localhost:5000/com.example/sample-rest-service
grep -q "Started SampleRestServiceApplication" <(docker logs -f ci-sample-rest-service)
