package com.khalid.ecommerce_system.entity;

import com.khalid.ecommerce_system.constant.OrderStatus;
import com.khalid.ecommerce_system.constant.PaymentMethod;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "order_info")
@Data
public class OrderInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id", nullable = false)
    private Long productInfoId;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "product_price", nullable = false)
    private BigDecimal ProductPricePerUnit;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status = OrderStatus.PENDING;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod paymentMethod;

    @Column(name = "created_dt", nullable = false)
    private Timestamp createdDt;

    @Column(name = "updated_dt")
    private Timestamp updatedDt;

    @Column(name = "delivered_dt")
    private Timestamp deliveredDt;

    @OneToMany(mappedBy = "order_info", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderHistoryInfo> editHistory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private AccountInfo buyer;

    @PrePersist
    public void prePersist() {
        createdDt = new Timestamp(System.currentTimeMillis());
    }

    @PreUpdate
    public void preUpdate() {
        updatedDt = new Timestamp(System.currentTimeMillis());
    }
}
