#https://medium.com/@shaaslam/installing-apache-kafka-on-windows-495f6f2fd3c8
#!/bin/sh
docker run -p 2181:2181 -p 9092:9092 --env ADVERTISED_HOST=localhost --env ADVERTISED_PORT=9092 spotify/kafka