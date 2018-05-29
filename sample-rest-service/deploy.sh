#!/bin/bash

echo "Removing running Docker container"
docker rm -f ci-sample-rest-service || true

echo "Removing cached Docker image"
docker rmi localhost:5000/com.example/sample-rest-service

echo "Pulling latest Docker image"
docker pull localhost:5000/com.example/sample-rest-service

echo "Running new Docker container"
docker run -d --name ci-sample-rest-service -p 18080:8080 localhost:5000/com.example/sample-rest-service

echo "Wating for application to start"
grep -q "Started SampleRestServiceApplication" <(docker logs -f ci-sample-rest-service)
