FROM openjdk:8-jdk-alpine
RUN apk update && apk upgrade
RUN mkdir -p /usr/local/service
ADD target/discovery.jar /usr/local/service/
ADD docker/run.sh run.sh
RUN chmod +x run.sh
CMD /bin/sh run.sh