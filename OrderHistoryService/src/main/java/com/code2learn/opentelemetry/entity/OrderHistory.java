package com.code2learn.opentelemetry.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "order_history")
@Data
public class OrderHistory {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @JsonProperty("Id")
    private int id;

    @JsonProperty("orderId")
    @Column(name = "ORDER_ID")
    private int orderId;

    @JsonProperty("date")
    @Column(name = "ORDER_DATE")
    private Date date = new Date();

    @JsonProperty("status")
    @Column(name = "STATUS")
    private String status;

}
