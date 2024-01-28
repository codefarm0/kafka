package com.codefarm.order.controller;

import com.codefarm.order.model.OrderRequest;
import com.codefarm.order.model.OrderResponse;
import com.codefarm.order.service.OrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/order")
    OrderResponse createOrder(@RequestBody OrderRequest orderRequest){
        return orderService.processOrder(orderRequest);
    }

    //demonstration for different usecases of event producer

    @PostMapping("/send/key")
    String sendMessageToTopicWithKey(@RequestBody String message){
        return orderService.sendMessageToTopicWithKey(message);
    }

    @PostMapping("/send/key/partition")
    String sendMessageToTopicWithKeyAndPartition(@RequestBody String message){
        return orderService.sendMessageToTopicWithKeyAndPartition(message);
    }

    @PostMapping("/send/key/partition/timestamp")
    String sendMessageToTopicWithKeyAndPartitionAndTimestamp(@RequestBody String message){
        return orderService.sendMessageToTopicWithKeyAndPartitionAndTimestamp(message);
    }

    @PostMapping("/send/producer/record")
    String sendMessageToTopicWithProducerRecord(@RequestBody String message){
        return orderService.sendMessageToTopicWithProducerRecord(message);
    }
    @PostMapping("/send/producer/record/headers")
    String sendMessageToTopicWithProducerRecordAndHeaders(@RequestBody String message){
        return orderService.sendMessageToTopicWithProducerRecordAndHeaders(message);
    }
    @PostMapping("/send/message")
    String sendMessageToTopicWithMessage(@RequestBody String message){
        return orderService.sendMessageToTopicWithMessage(message);
    }
}
