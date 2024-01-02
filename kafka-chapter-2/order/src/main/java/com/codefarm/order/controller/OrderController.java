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
}
