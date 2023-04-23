package com.codefarm;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class EventConsumer {

    public static void main(String[] args) {
        receiveEvent();
    }
    static void receiveEvent() {
        //1. config
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "codefarm");

        //2. consumer client
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);

        // 3. subscribe to the topics
        consumer.subscribe(Arrays.asList("my-first-topic"));

        while (true) {
            //4. poll for the message
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            //5. process the message

            System.out.println("Receiving messages -- ");
            for (ConsumerRecord<String, String> record : records) {
                System.out.println(record.key() + " ~ " + record.value());
            }
        }
    }
}
