package com.khalid.ecommerce_system.service;

import com.khalid.ecommerce_system.constant.OrderStatus;
import com.khalid.ecommerce_system.exception.AuthServiceException;
import com.khalid.ecommerce_system.exception.OrderServiceException;
import com.khalid.ecommerce_system.exception.ProductServiceException;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public interface OrderService {
    OrderResponse placeOrder(OrderItemInputParameter parameter) throws ProductServiceException, AuthServiceException;

    OrderResponse editOrder(UpdateOrderInput parameter) throws OrderServiceException, ProductServiceException, AuthServiceException;

    List<OrderHistoryResponse> getOrderEditHistory(Long orderId) throws AuthServiceException, OrderServiceException;

    OrderResponse updateOrderStatus(ChangeOrderStatusRequest request) throws OrderServiceException, AuthServiceException;

    OrderResponse getOrderInfoById(Long id) throws OrderServiceException, AuthServiceException;

    @Data
    @Builder
    class OrderResponse {
        private final Long id;
        private final Long productId;
        private final Integer quantity;
        private final BigDecimal productPricePerUnit;
        private final BigDecimal totalAmount;
        private final String status;
        private final Timestamp createdDt;
        private final Timestamp updatedDt;
        private final Long buyerId;
    }

    @Data
    @Builder
    class OrderHistoryResponse {
        private final Long id;
        private final Integer updatedQuantity;
        private final OrderStatus updatedStatus;
        private final String paymentMethod;
        private final Timestamp updatedDt;
    }

    @Data
    class OrderItemInputParameter {
        private final Long productId;
        private final Integer quantity;
        private final String paymentMethod;
    }
    @Data
    class UpdateOrderInput {
        private final Long orderId;
        private final Integer quantity;
        private final String paymentMethod;
    }
    @Data
    class ChangeOrderStatusRequest {
        private final Long orderId;
        private final String status;
    }
}
