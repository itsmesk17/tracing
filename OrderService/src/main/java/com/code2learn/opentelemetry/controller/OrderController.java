package com.code2learn.opentelemetry.controller;

import com.code2learn.opentelemetry.client.OrderHistoryClient;
import com.code2learn.opentelemetry.entity.Order;
import com.code2learn.opentelemetry.exception.OrderNotFoundException;
import com.code2learn.opentelemetry.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
public class OrderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    private final OrderHistoryClient orderHistoryClient;

    private final OrderRepository orderRepository;

    private final KafkaTemplate<String, Order> orderKafkaTemplate;

    @Value("${order.topic.name}")
    private String topicName;

    @Autowired
    public OrderController(OrderHistoryClient orderHistoryClient, OrderRepository orderRepository, KafkaTemplate<String, Order> orderKafkaTemplate) {
        this.orderHistoryClient = orderHistoryClient;
        this.orderRepository = orderRepository;
        this.orderKafkaTemplate = orderKafkaTemplate;
    }

    @PostMapping(path = "/order")
    public int createOrder(@RequestHeader Map<String, String> headers) {
        LOGGER.info("Create a New Order");
        Order order = orderRepository.save(new Order());
        orderHistoryClient.saveHistory(order);
        sendOrderForProcessing(order);
        return order.getId();
    }

    private void sendOrderForProcessing(Order order) {
        ListenableFuture<SendResult<String, Order>> future = orderKafkaTemplate.send(topicName, order);
        future.addCallback(new ListenableFutureCallback<SendResult<String, Order>>() {

            @Override
            public void onSuccess(SendResult<String, Order> result) {
                LOGGER.info("Sent order with Id=[" + result.getProducerRecord().value().getId() +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }
            @Override
            public void onFailure(Throwable ex) {
                LOGGER.error("Unable to send Order with Id=["
                        + order.getId() + "] due to : " + ex.getMessage(), ex);
            }
        });
    }

    @PostMapping(path = "/order/{id}/updateStatus/{status}")
    public String updateOrder(@PathVariable("id") int orderId, @PathVariable("status") String status, @RequestHeader Map<String, String> headers) {
        LOGGER.info("Update Order Status");
        Order order = new Order();
        order.setId(orderId);
        order.setStatus(status);
        order = orderRepository.save(order);
        orderHistoryClient.saveHistory(order);
        return order.getStatus();
    }

    @GetMapping(path = "/order/{id}")
    public com.code2learn.opentelemetry.model.Order getOrderDetails(@PathVariable("id") int orderId, @RequestHeader Map<String, String> headers) {
        LOGGER.info("Getting Oder and Order History Details With Order Id {}", orderId);
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        com.code2learn.opentelemetry.model.Order order = null;
        if (orderOptional.isPresent()) {
            order = new com.code2learn.opentelemetry.model.Order(orderOptional.get());
            order.setHistoryList(orderHistoryClient.getHistory(orderId));
        } else {
            throw new OrderNotFoundException(String.format("Order Id %s not found", orderId));
        }
        return order;
    }
}
