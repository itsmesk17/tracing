package com.code2learn.opentelemetry.listener;

import com.code2learn.opentelemetry.client.OrderClient;
import com.code2learn.opentelemetry.entity.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class ShipmentListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShipmentListener.class);

    private final OrderClient orderClient;

    @Value("${order.topic.name}")
    private String topicName;

    @Autowired
    public ShipmentListener(OrderClient orderClient) {
        this.orderClient = orderClient;
    }

    @KafkaListener(topics = "orderTopic", groupId = "shipmentGrp")
    public void listenWithHeaders(
            @Payload Order order,
            @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        LOGGER.info(
                "Received Order Id: " + order.getId()
                        + " from partition: " + partition);
        LOGGER.info(
                "Order Id: " + order.getId()
                        + " has been shipped");
        orderClient.updateOrder(order);
        LOGGER.info(
                "Status of Order Id: " + order.getId()
                        + " has been updated");
    }
}
