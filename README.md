# Kafka_By_Sergey_Kargopolov
Kafka_By_Sergey_Kargopolov

## kafka setup for Window Link
https://www.confluent.io/blog/set-up-and-run-kafka-on-windows-linux-wsl-2/



## kafka configuration for Docker 
services:
  kafka-1:
    image: confluentinc/cp-kafka:latest
    container_name: kafka-1
    ports:
      - "9092:9092"
    environment:
      CLUSTER_ID: "WnLkTHhkQaiJbwP8FClPhw"
      KAFKA_NODE_ID: 1
      KAFKA_PROCESS_ROLES: broker,controller
      KAFKA_CONTROLLER_QUORUM_VOTERS: 1@kafka-1:9091,2@kafka-2:9091,3@kafka-3:9091
      KAFKA_LISTENERS: PLAINTEXT://:9090,CONTROLLER://:9091,EXTERNAL://:9092
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://kafka-1:9090, EXTERNAL://${HOSTNAME:-localhost}:9092
      KAFKA_CONTROLLER_LISTENER_NAMES: CONTROLLER
      KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: CONTROLLER:PLAINTEXT,PLAINTEXT:PLAINTEXT,EXTERNAL:PLAINTEXT
      KAFKA_INTER_BROKER_LISTENER_NAME: PLAINTEXT
      KAFKA_LOG_DIRS: /var/lib/kafka/data
    volumes:
      - D:/Bhaskar_Learning_and_Development/Kafka_By_Sergey_Kargopolov/volumes/kafka-1-data:/var/lib/kafka/data
    networks:
      - kafka-net
 
  kafka-2:
    image: confluentinc/cp-kafka:latest
    container_name: kafka-2
    ports:
      - "9094:9094"
    environment:
      CLUSTER_ID: "WnLkTHhkQaiJbwP8FClPhw"
      KAFKA_NODE_ID: 2
      KAFKA_PROCESS_ROLES: broker,controller
      KAFKA_CONTROLLER_QUORUM_VOTERS: 1@kafka-1:9091,2@kafka-2:9091,3@kafka-3:9091
      KAFKA_LISTENERS: PLAINTEXT://:9090,CONTROLLER://:9091,EXTERNAL://:9094
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://kafka-2:9090, EXTERNAL://${HOSTNAME:-localhost}:9094
      KAFKA_CONTROLLER_LISTENER_NAMES: CONTROLLER
      KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: CONTROLLER:PLAINTEXT,PLAINTEXT:PLAINTEXT,EXTERNAL:PLAINTEXT
      KAFKA_INTER_BROKER_LISTENER_NAME: PLAINTEXT
      KAFKA_LOG_DIRS: /var/lib/kafka/data
    volumes:
      - D:/Bhaskar_Learning_and_Development/Kafka_By_Sergey_Kargopolov/volumes/kafka-2-data:/var/lib/kafka/data
    networks:
      - kafka-net
 
  kafka-3:
    image: confluentinc/cp-kafka:latest
    container_name: kafka-3
    ports:
      - "9096:9096"
    environment:
      CLUSTER_ID: "WnLkTHhkQaiJbwP8FClPhw"
      KAFKA_NODE_ID: 3
      KAFKA_PROCESS_ROLES: broker,controller
      KAFKA_CONTROLLER_QUORUM_VOTERS: 1@kafka-1:9091,2@kafka-2:9091,3@kafka-3:9091
      KAFKA_LISTENERS: PLAINTEXT://:9090,CONTROLLER://:9091,EXTERNAL://:9096
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://kafka-3:9090, EXTERNAL://${HOSTNAME:-localhost}:9096
      KAFKA_CONTROLLER_LISTENER_NAMES: CONTROLLER
      KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: CONTROLLER:PLAINTEXT,PLAINTEXT:PLAINTEXT,EXTERNAL:PLAINTEXT
      KAFKA_INTER_BROKER_LISTENER_NAME: PLAINTEXT
      KAFKA_LOG_DIRS: /var/lib/kafka/data
    volumes:
      - D:/Bhaskar_Learning_and_Development/Kafka_By_Sergey_Kargopolov/volumes/kafka-3-data:/var/lib/kafka/data
    networks:
      - kafka-net
 
networks:
  kafka-net:
    driver: bridge
	
	
########### docker compose command 
docker-compose -f docker-compose.yml --env-file environment.env up

##INSTALLING KAFKA IN WINDOWS
https://www.confluent.io/blog/set-up-and-run-kafka-on-windows-linux-wsl-2/


##configuring kafka server in WINDOWS
./kafka-storage.sh random-uuid

./kafka-storage.sh format -t IcZd1BNeSRO6DpCRla9BaA -c ../config/kraft/server.properties

mkdir -p /d/Bhaskar_Learning_and_Development/Kafka_By_Sergey_Kargopolov/kafka/logs

export LOG_DIR="D:/Bhaskar_Learning_and_Development/Kafka_By_Sergey_Kargopolov/kafka/logs"

./kafka-server-start.sh ../config/kraft/server.properties

## when multiple server not starting use this one 	
./kafka-storage.sh format -t TFBlg7pKSGSDohTR0J0EDQ -c ../config/kraft/server-1.properties
./kafka-storage.sh format -t TFBlg7pKSGSDohTR0J0EDQ -c ../config/kraft/server-2.properties
./kafka-storage.sh format -t TFBlg7pKSGSDohTR0J0EDQ -c ../config/kraft/server-3.properties

export LOG_DIR="D:/Bhaskar_Learning_and_Development/Kafka_By_Sergey_Kargopolov/KAFKA_BASIC/kafka/logs"
./kafka-server-start.sh ../config/kraft/server-1.properties
export LOG_DIR="D:/Bhaskar_Learning_and_Development/Kafka_By_Sergey_Kargopolov/KAFKA_BASIC/kafka/logs"
./kafka-server-start.sh ../config/kraft/server-2.properties
export LOG_DIR="D:/Bhaskar_Learning_and_Development/Kafka_By_Sergey_Kargopolov/KAFKA_BASIC/kafka/logs"
./kafka-server-start.sh ../config/kraft/server-3.properties


./kafka-server-stop.sh ../config/kraft/server-1.properties
./kafka-server-stop.sh ../config/kraft/server-2.properties
./kafka-server-stop.sh ../config/kraft/server-3.properties

## How to create Topic user Kafka server CLI
./kafka-topics.sh --create --topic topic1 --partitions 3 --replication-factor 3 --bootstrap-server [::1]:9092,[::1]:9094
./kafka-topics.sh --create --topic topic2 --partitions 3 --replication-factor 3 --bootstrap-server [::1]:9092,[::1]:9094
./kafka-topics.sh --create --topic insync-replicas-topic --bootstrap-server [::1]:9092 --partitions 3 -replication-factor 3 --config min.insync.r
eplicas=2

## List down topic create in brokers
./kafka-topics.sh --list --bootstrap-server [::1]:9092
./kafka-topics.sh --describe --bootstrap-server [::1]:9092

## delete kafka topic 
./kafka-topics.sh --delete --topic topic2 --bootstrap-server [::1]:9092

## Send a message without key to kafka topic  
./kafka-console-producer.sh --bootstrap-server [::1]:9092,[::1]:9094 --topic my-topic


## Send a message key value to kafka topic  
./kafka-console-producer.sh --bootstrap-server [::1]:9092,[::1]:9094 --topic my-topic --property "parse.key=true" --property "key.separator=:"


## consume message from beginning from kafka producer
./kafka-console-consumer.sh --topic my-topic --from-beginning --bootstrap-server [::1]:9092

## consume new message from kafka producer
./kafka-console-consumer.sh --topic my-topic  --bootstrap-server [::1]:9092

## consume message and display in key value pair
./kafka-console-consumer.sh --topic my-topic  --property print.key=true --property print.value=true --from-beginning --bootstrap-server [::1]:9092

## consume message and display in only key not value  pair
./kafka-console-consumer.sh --topic my-topic  --property print.key=true --property print.value=false --from-beginning --bootstrap-server [::1]:9092
