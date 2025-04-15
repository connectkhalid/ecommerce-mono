package com.khalid.ecommerce_system.service.impl;

import com.khalid.ecommerce_system.entity.DeliveryInfo;
import com.khalid.ecommerce_system.entity.OrderInfo;
import com.khalid.ecommerce_system.repository.DeliveryInfoRepository;
import com.khalid.ecommerce_system.repository.OrderInfoRepository;
import com.khalid.ecommerce_system.util.DateUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DeliveryServiceImpl {
    private final OrderInfoRepository orderRepository;
    private final DeliveryInfoRepository deliveryRepository;

    @Scheduled(cron = "0 0 0 * * *") // Every day at midnight
    @Transactional
    public void migrateDeliveredOrders() {
        List<OrderInfo> deliveredOrders = orderRepository.findByStatus("DELIVERED");

        List<DeliveryInfo> deliveries = deliveredOrders.stream().map(order -> {
            DeliveryInfo d = new DeliveryInfo();
            d.setOrderId(order.getId());
            d.setProductId(order.getProductInfoId());
            d.setBuyerId(order.getBuyer().getId());
            d.setPrice(order.getProductPricePerUnit());
            d.setQuantity(order.getQuantity());
            d.setOrderedAt(order.getCreatedDt().toLocalDateTime());
            d.setDeliveredAt(DateUtil.currentTime().toLocalDateTime());
            return d;
        }).collect(Collectors.toList());

        deliveryRepository.saveAll(deliveries);
        orderRepository.deleteAll(deliveredOrders);
    }
}
