package com.khalid.ecommerce_system.repository;

import com.khalid.ecommerce_system.entity.OrderInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderInfoRepository extends JpaRepository<OrderInfo, Long> {
    @Query(value = "SELECT * FROM order_info WHERE status = :delivered", nativeQuery = true)
    List<OrderInfo> findByStatus(String delivered);
}
