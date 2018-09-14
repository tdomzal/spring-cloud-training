#!/bin/sh
while ! `nc -z configuration_server $CONFIGURATION_SERVER_PORT`;
    do sleep $PING_TIMEOUT;echo "Waiting for configuration server to start...";done
java -jar -Dspring.cloud.config.uri=$CONFIGURATION_SERVER_URI /usr/local/service/discovery.jar