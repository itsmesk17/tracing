package com.code2learn.opentelemetry.client;

import com.code2learn.opentelemetry.entity.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@Component
public class OrderClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderClient.class);

    private final RestTemplate restTemplate;

    @Autowired
    public OrderClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${orderClient.baseUrl}")
    private String baseUrl;


    @NewSpan(value = "updateOrder")
    public String updateOrder(Order order) {
        LOGGER.info("Updating Order status to shipped for Order Id {}", order.getId());
        order.setDate(new Date());
        order.setStatus("SHIPPED");
        String url = String.format("%s/%d/updateStatus/%s", baseUrl, order.getId(), order.getStatus());
        HttpEntity<Order> entity = new HttpEntity<>(order);

        ResponseEntity<String> orderUpdate = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        return orderUpdate.getBody();

    }
}
