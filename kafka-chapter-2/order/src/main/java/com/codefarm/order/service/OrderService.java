package com.codefarm.order.service;

import com.codefarm.order.model.OrderEvent;
import com.codefarm.order.model.OrderRequest;
import com.codefarm.order.model.OrderResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalField;
import java.util.concurrent.CompletableFuture;

@Service
public class OrderService {

    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${codefarm.kafka.order.topic}")
    private String kafkaTopic;

    @Autowired
    private ObjectMapper objectMapper;

    private Logger log = LoggerFactory.getLogger(OrderService.class);

    public OrderService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public OrderResponse processOrder(OrderRequest orderRequest) {
        log.info("process order.. - {}", orderRequest.getId());
        OrderResponse orderResponse = new OrderResponse();

        if (orderRequest.getId().length() % 2 == 0) {
            orderResponse.setStatus("SUCCESS");
        } else {
            orderResponse.setStatus("FAIL");
        }

        OrderEvent orderEvent = getOrderEvent(orderRequest);

        //send order event to kafka topic
        try {
            log.info("sending event.. - {}", orderRequest.getId());
            kafkaTemplate.send(kafkaTopic, objectMapper.writeValueAsString(orderEvent));
            log.info("event sent.. - {}", orderRequest.getId());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return orderResponse;
    }

    private OrderEvent getOrderEvent(OrderRequest orderRequest) {
        //event preparation
        //email, subject, message
        OrderEvent orderEvent = new OrderEvent();
        String email = "testemail@codefarm.com";//its going to be fetched form user service
        orderEvent.setEmail(email);

        if (orderRequest.getId().length() % 2 == 0) {
            orderEvent.setSubject("Order Processing Status for OrderId - " + orderRequest.getId());
            orderEvent.setMessage("Your order has been successfully placed, click this link <> to get the correct status");
        } else {
            orderEvent.setSubject("Order Processing Status for OrderId - " + orderRequest.getId());
            orderEvent.setMessage("Your order has been Failed to process placed, click this link <> to get the correct status");
        }
        return orderEvent;
    }

    public String sendMessageToTopicWithKey(String message) {

        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send("partition-simulatio1n", String.valueOf(message.length()), message);

        future.thenAccept(sendResult ->{
            RecordMetadata recordMetadata = sendResult.getRecordMetadata();
            log.info("Send success, topic -{}, partition - {}, offset - {}", recordMetadata.topic(), recordMetadata.partition(), recordMetadata.offset());
        }).exceptionally(exception ->{
            log.error("failed to send");
            throw new RuntimeException("failed to send event..");
        });
        return "Success for sendMessageToTopicWithKey";
    }

    public String sendMessageToTopicWithKeyAndPartitionAndTimestamp(String message) {

        kafkaTemplate.send("partition-simulation", 0, LocalDate.now().minusDays(2).toEpochDay(), String.valueOf(message.length()), message);

        return "Success";
    }

    public String sendMessageToTopicWithKeyAndPartition(String message) {

        //kafkaTemplate.setDefaultTopic("order-events");
        kafkaTemplate.send( "my-topic",3, String.valueOf(message.length()), message);

        return "Success";
    }

    public String sendMessageToTopicWithProducerRecord(String message) {

        ProducerRecord<String, String> producerRecord = new ProducerRecord<>("partition-simulation", message);

        kafkaTemplate.send(producerRecord);

        return "Success";
    }

    public String sendMessageToTopicWithProducerRecordAndHeaders(String message) {

        ProducerRecord<String, String> producerRecord = new ProducerRecord<>("order-event", message);
        producerRecord.headers().add("mey-key", "my-value".getBytes());
        kafkaTemplate.send(producerRecord);

        return "Success";
    }

    public String sendMessageToTopicWithMessage(String message) {

        Message<String> msg = MessageBuilder.withPayload(message)
                .setHeader(KafkaHeaders.TOPIC, "order-event")
                .build();
        kafkaTemplate.send(msg);

        return "Success";
    }
}
