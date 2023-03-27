package com.code2learn.opentelemetry.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class Order {

    @JsonProperty("Id")
    private int id;

    @JsonProperty("date")
    private Date date;

    @JsonProperty("status")
    private String status;
}
