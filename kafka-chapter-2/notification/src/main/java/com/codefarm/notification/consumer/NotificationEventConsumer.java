package com.codefarm.notification.consumer;

import com.codefarm.notification.model.OrderEvent;
import com.codefarm.notification.service.EmailNotificationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationEventConsumer {

    private ObjectMapper objectMapper;

    private EmailNotificationService emailNotificationService;

    private Logger log = LoggerFactory.getLogger(NotificationEventConsumer.class);

    public NotificationEventConsumer(ObjectMapper objectMapper, EmailNotificationService emailNotificationService) {
        this.objectMapper = objectMapper;
        this.emailNotificationService = emailNotificationService;
    }

    @KafkaListener(topics = "${codefarm.kafka.order.topic}")
    public void processEvent(ConsumerRecord<String, String> consumerRecord) {
        log.info("inside process Event of notificaiton");
        try {
            OrderEvent event = objectMapper.readValue(consumerRecord.value(), OrderEvent.class);
            log.info("sending email to - {}", event.getEmail());
            emailNotificationService.sendEmail(event.getEmail(), event.getSubject(), event.getMessage());
            log.info("sent email to - {}", event.getEmail());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
}
