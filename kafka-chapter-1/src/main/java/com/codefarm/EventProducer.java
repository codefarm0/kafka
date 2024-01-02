package com.codefarm;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class EventProducer {

    public static void main(String[] args) {
        new EventProducer().sendEvent("new akfa topc");
    }
    boolean sendEvent(String message){
        //1. configs - broker, serializer(key, value)
        Properties kafkaProducerConfig = new Properties();

        kafkaProducerConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        kafkaProducerConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        kafkaProducerConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        //2. record

        ProducerRecord<String, String> record = new ProducerRecord<>("my-first-topic111", message);
        //3. producer interface

        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(kafkaProducerConfig);
        // 4. send the record to cluster

        producer.send(record);

        /// 5. flush/close
        producer.flush();
        producer.close();

        return true;
    }


}
