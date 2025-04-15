package com.khalid.ecommerce_system.repository;

import com.khalid.ecommerce_system.entity.OrderHistoryInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderHistoryInfoRepository extends JpaRepository<OrderHistoryInfo, Long>{
    @Query(value = "SELECT * FROM order_history_info WHERE order_id = :orderId", nativeQuery = true)
    List<OrderHistoryInfo> findByOrderId(Long orderId);
}
