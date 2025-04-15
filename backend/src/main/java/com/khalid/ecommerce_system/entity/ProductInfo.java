package com.khalid.ecommerce_system.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "product_info",  uniqueConstraints = @UniqueConstraint(columnNames = {"name", "id"}))
@Data
public class ProductInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer qty;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "created_dt", nullable = false)
    private Timestamp createdDt;

    @Column(name = "updated_dt")
    private Timestamp updatedDt;

    @Column(name = "delete_flg", columnDefinition = "tinyint(1) default 0")
    private Boolean deleteFlg;
}
