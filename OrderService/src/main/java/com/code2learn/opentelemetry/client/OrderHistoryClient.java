package com.code2learn.opentelemetry.client;

import com.code2learn.opentelemetry.entity.Order;
import com.code2learn.opentelemetry.model.OrderHistory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class OrderHistoryClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderHistoryClient.class);

    private final RestTemplate restTemplate;

    @Autowired
    public OrderHistoryClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${orderHistoryClient.baseUrl}")
    private String baseUrl;

    public List<OrderHistory> getHistory(long orderId) {
        LOGGER.info("Fetching Order History Details With Order Id {}", orderId);
        String url = String.format("%s/%d", baseUrl, orderId);
        ResponseEntity<List<OrderHistory>> orderHistory = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<OrderHistory>>() {
        });
        return orderHistory.getBody();
    }

    public OrderHistory saveHistory(Order order) {
        LOGGER.info("Create a New Order History Details for Order Id {}", order.getId());
        String url = String.format("%s", baseUrl);

        OrderHistory history = new OrderHistory();
        history.setOrderId(order.getId());
        history.setDate(order.getDate());
        history.setStatus(order.getStatus());
        HttpEntity<OrderHistory> entity = new HttpEntity<>(history);

        ResponseEntity<OrderHistory> orderHistory = restTemplate.exchange(url, HttpMethod.POST, entity, OrderHistory.class);
        return orderHistory.getBody();

    }
}
