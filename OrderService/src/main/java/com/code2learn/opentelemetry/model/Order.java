package com.code2learn.opentelemetry.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Order {

    @JsonProperty("orderHistoryId")
    private int id;

    @JsonProperty("date")
    private Date date;

    @JsonProperty("status")
    private String status;

    @JsonProperty("history")
    private List<OrderHistory> historyList;

    public Order() {

    }

    public Order(com.code2learn.opentelemetry.entity.Order order) {
        this.id = order.getId();
        this.date = order.getDate();
        this.status = order.getStatus();
    }
}
