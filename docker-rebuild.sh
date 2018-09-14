#!/bin/sh
# clean
docker rmi $(docker images | grep "^training")
docker rmi $(docker images | grep "<none>")
#build
docker build -t "training/configuration" configuration