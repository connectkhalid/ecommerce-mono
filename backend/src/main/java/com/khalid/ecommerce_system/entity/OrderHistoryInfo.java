package com.khalid.ecommerce_system.entity;

import com.khalid.ecommerce_system.constant.OrderStatus;
import com.khalid.ecommerce_system.constant.PaymentMethod;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "order_history_info")
@Data
public class OrderHistoryInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "updated_quantity")
    private Integer updatedQuantity;

    @Column(name = "updated_status")
    @Enumerated(EnumType.STRING)
    private OrderStatus updatedStatus;

    @Column(name = "payment_method")
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "updated_dt", nullable = false)
    private Timestamp updatedDt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private OrderInfo order_info;

    @PrePersist
    public void prePersist() {
        updatedDt = new Timestamp(System.currentTimeMillis());
    }
}

