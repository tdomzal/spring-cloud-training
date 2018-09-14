#!/bin/sh
while ! `nc -z kafka_server 9092`;
    do sleep $PING_TIMEOUT;echo "Waiting for kafka server to start...";done
java -jar -Dspring.profiles.active=$DEFAULT_PROFILE /usr/local/service/configuration.jar
