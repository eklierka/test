#!/bin/bash

container_name=ci-sample-rest-service
if [ -z "$1" ]
then
  image_tag="latest"
else
  image_tag="$1"
fi
image_name="localhost:5000/com.example/sample-rest-service:$image_tag"

echo "Removing running Docker container '$container_name'"
docker rm -f $container_name || true

echo "Removing cached Docker image '$image_name'"
docker rmi $image_name

echo "Pulling latest Docker image '$image_name'"
docker pull $image_name

echo "Running new Docker container '$container_name' from image '$image_name'"
docker run -d --name $container_name -P $image_name

echo "Waiting for the application to start"
grep -q "Started SampleRestServiceApplication" <(docker logs -f $container_name)
