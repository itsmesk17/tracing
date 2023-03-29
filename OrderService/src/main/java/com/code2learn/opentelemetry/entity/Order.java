package com.code2learn.opentelemetry.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "order_tbl")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @JsonProperty("Id")
    private int id;

    @JsonProperty("date")
    @Column(name = "DATE")
    private Date date = new Date();

    @JsonProperty("status")
    @Column(name = "STATUS")
    private String status = "NEW";

}
