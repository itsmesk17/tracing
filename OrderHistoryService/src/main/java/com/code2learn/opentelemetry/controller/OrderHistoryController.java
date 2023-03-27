package com.code2learn.opentelemetry.controller;

import com.code2learn.opentelemetry.repository.OrderHistoryRepository;
import com.code2learn.opentelemetry.entity.OrderHistory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class OrderHistoryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderHistoryController.class);

    private final OrderHistoryRepository orderHistoryRepository;

    @Autowired
    public OrderHistoryController(OrderHistoryRepository orderHistoryRepository) {
        this.orderHistoryRepository = orderHistoryRepository;
    }

    @GetMapping(path = "/orderHistory/{id}")
    public List<OrderHistory> getPrice(@PathVariable("id") int orderId, @RequestHeader Map<String, String> headers) {
        LOGGER.info("Getting Order History details for Order Id {}", orderId);
        return orderHistoryRepository.findAllByOrderId(orderId);
    }

    @PostMapping(path = "/orderHistory")
    public OrderHistory getPrice(@RequestBody OrderHistory orderHistory, @RequestHeader Map<String, String> headers) {
        LOGGER.info("Saving Order History details for Order Id {}", orderHistory.getOrderId());
        return orderHistoryRepository.save(orderHistory);
    }
}
