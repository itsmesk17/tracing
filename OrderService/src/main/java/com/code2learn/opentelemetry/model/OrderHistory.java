package com.code2learn.opentelemetry.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
public class OrderHistory {

    @JsonProperty("orderHistoryId")
    private int id;

    @JsonProperty("orderId")
    private int orderId;

    @JsonProperty("date")
    private Date date;

    @JsonProperty("status")
    private String status;

}
