# kafka cli basic commands (for windows machine, for mac just folder name change only)

## start zookeeper

.\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties

## start kafka server

.\bin\windows\kafka-server-start.bat .\config\server.properties

## create topic 

* with default configurations
` .\bin\windows\kafka-topics.bat --create --topic my-first-topic --bootstrap-server localhost:9092
 `
 
 * custom configurations
 
 .\bin\windows\kafka-topics.bat --create --topic my-second-topic --partitions 5 --replication-factor 1 --bootstrap-server localhost:9092

## list topics

 .\bin\windows\kafka-topics.bat --list --bootstrap-server localhost:9092


## describe topic

.\bin\windows\kafka-topics.bat --describe --topic my-first-topic --bootstrap-server localhost:9092


## produce events to topic

 .\bin\windows\kafka-console-producer.bat --topic my-first-topic --bootstrap-server localhost:9092

## consumer events from topic

* latest message only 

.\bin\windows\kafka-console-consumer.bat --topic my-first-topic  --bootstrap-server localhost:9092

* from beginning

.\bin\windows\kafka-console-consumer.bat --topic my-first-topic --from-beginning --bootstrap-server localhost:9092  

   

* from specific offset and partition

 .\bin\windows\kafka-console-consumer.bat --topic my-first-topic  --offset 30 --partition 0 --bootstrap-server localhost:9092
 
 
 ## kafka with raft
 
 * generate random-uuid
 .\bin\windows\kafka-storage.bat random-uuid
 * format the log dirs
 .\bin\windows\kafka-storage.bat format -t d1hZRwFeRuqyNTTwR9grGw -c .\config\kraft\server.properties
 * start the server 
 .\bin\windows\kafka-server-start.bat .\config\kraft\server.properties
 
