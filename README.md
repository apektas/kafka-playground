
docker-compose -f docker-compose-core.yml -p core up -d  
docker-compose -f docker-compose-core.yml -p core down



# execute the kafka
docker-compose -f docker-compose-core.yml -p core up -d  

docker exec -it kafka bash
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic t-hello --partitions 1 --replication-factor 1
kafka-topics.sh --bootstrap-server localhost:9092 --list
kafka-topics.sh --bootstrap-server localhost:9092 --describe t-hello  

kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic t-hello 

# read from specific partition
kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic t-multi-partitions --offset earliest --partition 0

# add more partitions to topic

kafka-topics.sh --bootstrap-server localhost:9092 --alter --topic t-multi-partitions --partitions 3


# you can use conductor UI using specific docker-compose file under under conduktor-platform 
to access UI go to localhost:8080/admin 

CDK_ADMIN_EMAIL: "admin@conduktor.io"
CDK_ADMIN_PASSWORD: "admin"

# check conduktor related files following link.  
https://github.com/conduktor/kafka-beginners-course/tree/main/conduktor-platform

idempotent consume 

We can use KafkaListenerEndpointRegistry to stop/start consumer. Then use Scheduled(cron) cron expression 

