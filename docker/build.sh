#!/bin/sh

mvn install
docker build -t i-userservice:latest -f docker/Dockerfile .
docker tag i-userservice:latest ishangaurav/i-userservice:latest
docker push ishangaurav/i-userservice:latest
