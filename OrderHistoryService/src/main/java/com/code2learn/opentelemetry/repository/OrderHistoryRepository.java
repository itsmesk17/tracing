package com.code2learn.opentelemetry.repository;

import com.code2learn.opentelemetry.entity.OrderHistory;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Integer> {

    @NewSpan(value = "findAllByOrderId")
    public List<OrderHistory> findAllByOrderId(int orderId);
}
