package com.khalid.ecommerce_system.service.impl;

import com.khalid.ecommerce_system.constant.*;
import com.khalid.ecommerce_system.entity.AccountInfo;
import com.khalid.ecommerce_system.entity.OrderHistoryInfo;
import com.khalid.ecommerce_system.entity.OrderInfo;
import com.khalid.ecommerce_system.entity.ProductInfo;
import com.khalid.ecommerce_system.exception.AuthServiceException;
import com.khalid.ecommerce_system.exception.OrderServiceException;
import com.khalid.ecommerce_system.exception.ProductServiceException;
import com.khalid.ecommerce_system.form.validation.EnumUtility;
import com.khalid.ecommerce_system.repository.OrderHistoryInfoRepository;
import com.khalid.ecommerce_system.repository.OrderInfoRepository;
import com.khalid.ecommerce_system.service.OrderService;
import com.khalid.ecommerce_system.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderServiceImpl implements OrderService {
    private final ProductServiceImpl productService;
    private final AccountServiceImpl accountService;
    private final OrderInfoRepository orderRepository;
    private final OrderHistoryInfoRepository orderHistoryRepository;


    @Override
    public OrderResponse placeOrder(OrderItemInputParameter parameter)
            throws ProductServiceException, AuthServiceException {
        ProductInfo productInfo = productService.getProductInfoById(parameter.getProductId());
        AccountInfo currentUser = accountService.getCurrentUserAccountInfo();

        if (productInfo.getQty() < parameter.getQuantity()) {
            throw new ProductServiceException(
                    RestResponseMessage.ORDER_QUANTITY_EXCEED_STOCK,
                    RestResponseStatusCode.VALIDATION_ERROR_STATUS,
                    RestErrorMessageDetail.ORDER_QUANTITY_EXCEED_STOCK_ERROR_MESSAGE
            );
        }

        OrderInfo order = buildOrder(productInfo, currentUser, parameter);
        orderRepository.save(order);
        return buildOrderResponse(order);
    }

    @Override
    public OrderResponse editOrder(UpdateOrderInput parameter) throws OrderServiceException, ProductServiceException, AuthServiceException {

        OrderInfo orderInfo = getOrderDetailsById(parameter.getOrderId());
        AccountInfo accountInfo = accountService.getCurrentUserAccountInfo();

        isValidUser(accountInfo.getId(), orderInfo.getBuyer().getId());

        validateOrderIsEditable(orderInfo);
        validateQuantityAvailability(orderInfo.getProductInfoId(), parameter.getQuantity());

        orderInfo.setQuantity(parameter.getQuantity());
        orderInfo.setPaymentMethod(EnumUtility.getEnumValue(PaymentMethod.class, parameter.getPaymentMethod()));
        orderInfo.setUpdatedDt(new Timestamp(System.currentTimeMillis()));
        orderRepository.save(orderInfo);

        buildAndSaveOrderHistoryInfo(orderInfo, accountInfo);

        return buildOrderResponse(orderInfo);
    }

    @Override
    public List<OrderHistoryResponse> getOrderEditHistory(Long orderId) throws AuthServiceException, OrderServiceException {
        OrderInfo orderInfo = getOrderDetailsById(orderId);
        AccountInfo accountInfo = accountService.getCurrentUserAccountInfo();

        //Verifying if the current user is the owner or an admin
        validateAdminOrOwner(accountInfo.getId(), orderInfo.getBuyer().getId(), accountInfo.getRoleInfo().getRole());

        List<OrderHistoryInfo> orderHistoryInfoList = orderHistoryRepository.findByOrderId(orderId);
        return orderHistoryInfoList.stream()
                .map(this::buildOrderHistoryResponse)
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    public OrderResponse updateOrderStatus(ChangeOrderStatusRequest request) throws OrderServiceException, AuthServiceException {
        OrderInfo orderInfo = getOrderDetailsById(request.getOrderId());
        AccountInfo accountInfo = accountService.getCurrentUserAccountInfo();
        if (!accountInfo.getRoleInfo().getRole().equals(Role.ADMIN)) {
            throw new OrderServiceException(
                    RestResponseMessage.ACCESS_DENIED,
                    RestResponseStatusCode.NO_ACCESS_STATUS,
                    RestErrorMessageDetail.NO_PERMISSION_ERROR_MESSAGE);
        }

        orderInfo.setStatus(EnumUtility.getEnumValue(OrderStatus.class, request.getStatus()));
        if (orderInfo.getStatus() == OrderStatus.DELIVERED) {
            orderInfo.setDeliveredDt(DateUtil.currentTime());
        }
        orderInfo.setUpdatedDt(DateUtil.currentTime());
        orderRepository.save(orderInfo);

        buildAndSaveOrderHistoryInfo(orderInfo, accountInfo);
        return buildOrderResponse(orderInfo);
    }

    @Override
    public OrderResponse getOrderInfoById(Long id) throws OrderServiceException, AuthServiceException {
        OrderInfo orderInfo = getOrderDetailsById(id);
        AccountInfo accountInfo = accountService.getCurrentUserAccountInfo();

        //Verifying if the current user is the owner or an admin
        validateAdminOrOwner(accountInfo.getId(), orderInfo.getBuyer().getId(), accountInfo.getRoleInfo().getRole());
        return buildOrderResponse(orderInfo);
    }

    private void buildAndSaveOrderHistoryInfo(OrderInfo orderInfo, AccountInfo accountInfo) {
        OrderHistoryInfo orderHistoryInfo = new OrderHistoryInfo();
        orderHistoryInfo.setOrder_info(orderInfo);
        orderHistoryInfo.setUpdatedQuantity(orderInfo.getQuantity());
        orderHistoryInfo.setPaymentMethod(orderInfo.getPaymentMethod());
        orderHistoryInfo.setUpdatedStatus(orderInfo.getStatus());
        orderHistoryInfo.setUpdatedBy(accountInfo.getId());
        orderHistoryRepository.save(orderHistoryInfo);
    }

    //Utility Methods
    private OrderInfo buildOrder(ProductInfo product, AccountInfo buyer, OrderItemInputParameter param) {
        OrderInfo order = new OrderInfo();
        order.setProductInfoId(product.getId());
        order.setBuyer(buyer);
        order.setQuantity(param.getQuantity());
        order.setProductPricePerUnit(product.getPrice());
        order.setPaymentMethod(EnumUtility.getEnumValue(PaymentMethod.class, param.getPaymentMethod()));
        order.setStatus(OrderStatus.PENDING);
        order.setCreatedDt(new Timestamp(System.currentTimeMillis()));
        return order;
    }

    private OrderResponse buildOrderResponse(OrderInfo order) {
        return OrderResponse.builder()
                .id(order.getId())
                .productId(order.getProductInfoId())
                .quantity(order.getQuantity())
                .productPricePerUnit(order.getProductPricePerUnit())
                .totalAmount(order.getProductPricePerUnit().multiply(new java.math.BigDecimal(order.getQuantity())))
                .status(order.getStatus().toString())
                .createdDt(order.getCreatedDt())
                .updatedDt(order.getUpdatedDt())
                .buyerId(order.getBuyer().getId())
                .build();
    }

    private OrderHistoryResponse buildOrderHistoryResponse(OrderHistoryInfo orderHistoryInfo) {
        return OrderHistoryResponse.builder()
                .id(orderHistoryInfo.getId())
                .updatedQuantity(orderHistoryInfo.getUpdatedQuantity())
                .updatedStatus(orderHistoryInfo.getUpdatedStatus())
                .paymentMethod(orderHistoryInfo.getPaymentMethod().toString())
                .updatedDt(orderHistoryInfo.getUpdatedDt())
                .build();
    }

    private OrderInfo getOrderDetailsById(Long orderId) throws OrderServiceException {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderServiceException(
                        RestResponseMessage.ORDER_NOT_FOUND,
                        RestResponseStatusCode.NOT_FOUND_STATUS,
                        RestErrorMessageDetail.ORDER_NOT_FOUND_ERROR_MESSAGE
                ));
    }

    private void isValidUser(Long currentUserId, Long buyerId) throws OrderServiceException {
        if (!Objects.equals(currentUserId, buyerId)) {
            throw new OrderServiceException(
                    RestResponseMessage.ACCESS_DENIED,
                    RestResponseStatusCode.NO_ACCESS_STATUS,
                    RestErrorMessageDetail.ACCOUNT_INFORMATION_MISMATCH_ERROR_MESSAGE);
        }
    }

    private void validateAdminOrOwner(Long currentUserId, Long ownerId, Role currentUserRole) throws OrderServiceException {
        if (!currentUserId.equals(ownerId) && !Role.ADMIN.equals(currentUserRole)) {
            throw new OrderServiceException(
                    RestResponseMessage.ACCESS_DENIED,
                    RestResponseStatusCode.NO_ACCESS_STATUS,
                    RestErrorMessageDetail.NO_PERMISSION_ERROR_MESSAGE
            );
        }
    }

    private void validateOrderIsEditable(OrderInfo orderInfo) throws OrderServiceException {
        if (orderInfo.getStatus() != OrderStatus.PENDING) {
            throw new OrderServiceException(
                    RestResponseMessage.ORDER_INFO_NOT_EDITABLE,
                    RestResponseStatusCode.VALIDATION_ERROR_STATUS,
                    RestErrorMessageDetail.ORDER_INFO_NOT_EDITABLE_ERROR_MESSAGE
            );
        }
    }

    private void validateQuantityAvailability(Long productId, Integer requestedQty) throws ProductServiceException {
        ProductInfo productInfo = productService.getProductInfoById(productId);
        if (productInfo.getQty() < requestedQty) {
            throw new ProductServiceException(
                    RestResponseMessage.ORDER_QUANTITY_EXCEED_STOCK,
                    RestResponseStatusCode.VALIDATION_ERROR_STATUS,
                    RestErrorMessageDetail.ORDER_QUANTITY_EXCEED_STOCK_ERROR_MESSAGE
            );
        }
    }
}
