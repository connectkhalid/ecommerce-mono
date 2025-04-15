package com.khalid.ecommerce_system.api.pvt;


import com.khalid.ecommerce_system.constant.Constants;
import com.khalid.ecommerce_system.constant.RestResponseMessage;
import com.khalid.ecommerce_system.constant.RestResponseStatusCode;
import com.khalid.ecommerce_system.exception.AuthServiceException;
import com.khalid.ecommerce_system.exception.OrderServiceException;
import com.khalid.ecommerce_system.exception.ProductServiceException;
import com.khalid.ecommerce_system.service.OrderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.khalid.ecommerce_system.constant.RestApiResponse.buildResponseWithDetails;

@Slf4j
@RestController
@CrossOrigin
@Validated
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderController {

    public static final String API_PATH_PLACE_ORDER = Constants.ApiPath.PRIVATE_API_PATH + "/order/place";
    public static final String API_PATH_UPDATE_ORDER = Constants.ApiPath.PRIVATE_API_PATH + "/order/update";
    public static final String API_PATH_GET_ORDER_HISTORY = Constants.ApiPath.PRIVATE_API_PATH + "/order/history/{orderId}";

    public static final String API_PATH_CHANGE_ORDER_STATUS = Constants.ApiPath.PRIVATE_API_PATH + "/order/change-status";
    public static final String API_PATH_GET_ORDER_INFO_BY_ID = Constants.ApiPath.PRIVATE_API_PATH + "/order/info/{orderId}";


    private final OrderService orderService;

    // DTOs
    @Data
    public static class OrderItemInput {
        @NotNull(message = "Product ID is mandatory")
        private Long productId;

        @NotNull(message = "Quantity is mandatory")
        @Min(value = 1, message = "Quantity must be at least 1")
        private Integer quantity;

        @NotNull(message = "Payment Method is mandatory")
        private String paymentMethod;
    }

    @Data
    public static class UpdateOrderInput {
        @NotNull(message = "Order ID is mandatory")
        private Long orderId;

        @NotNull(message = "Product quantity is mandatory")
        private Integer quantity;

        @NotNull(message = "Payment Method is mandatory")
        private String paymentMethod;
    }

    @Data
    public static class ChangeOrderStatusRequest {
        @NotNull(message = "Order ID is required")
        private Long orderId;

        @NotNull(message = "Status is required")
        private String status;
    }

    // Buyer Endpoints
    @PreAuthorize("hasAuthority(@apiPermission.orderCreatePermission.code)")
    @PostMapping(path = API_PATH_PLACE_ORDER, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> placeOrder(@RequestBody @Valid OrderItemInput input)
            throws AuthServiceException, ProductServiceException {
        OrderService.OrderItemInputParameter parameter = new OrderService.OrderItemInputParameter(
                input.getProductId(),
                input.getQuantity(),
                input.getPaymentMethod());
        OrderService.OrderResponse response = orderService.placeOrder(parameter);
        return buildResponseWithDetails(RestResponseStatusCode.OK_STATUS, RestResponseMessage.CREATE_OK, response);
    }

    @PreAuthorize("hasAuthority(@apiPermission.orderUpdatePermission.code)")
    @PutMapping(path = API_PATH_UPDATE_ORDER, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> editOrder(@RequestBody @Valid UpdateOrderInput input)
            throws OrderServiceException, AuthServiceException, ProductServiceException {
        OrderService.UpdateOrderInput parameter = new OrderService.UpdateOrderInput(
                input.getOrderId(),
                input.getQuantity(),
                input.getPaymentMethod());
        OrderService.OrderResponse response = orderService.editOrder(parameter);
        return buildResponseWithDetails(RestResponseStatusCode.OK_STATUS, RestResponseMessage.UPDATE_OK, response);
    }
    @PreAuthorize("hasAuthority(@apiPermission.orderDetailsPermission.code)")
    @GetMapping(path = API_PATH_GET_ORDER_HISTORY, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getOrderEditHistoryForBuyer(@PathVariable Long orderId) throws OrderServiceException, AuthServiceException {
        List<OrderService.OrderHistoryResponse> history = orderService.getOrderEditHistory(orderId);
        return buildResponseWithDetails(RestResponseStatusCode.OK_STATUS, RestResponseMessage.FETCH_OK, history);
    }

    // Admin Endpoints
    @PreAuthorize("hasAuthority(@apiPermission.orderDeletePermission.code)")
    @PostMapping(path = API_PATH_CHANGE_ORDER_STATUS, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> changeOrderStatus(@RequestBody ChangeOrderStatusRequest request) throws OrderServiceException, AuthServiceException {
        OrderService.ChangeOrderStatusRequest changeOrderStatusRequest = new OrderService.ChangeOrderStatusRequest(
                request.getOrderId(),
                request.getStatus());
        OrderService.OrderResponse response = orderService.updateOrderStatus(changeOrderStatusRequest);
        return buildResponseWithDetails(RestResponseStatusCode.OK_STATUS, RestResponseMessage.UPDATE_OK, response);
    }


    @PreAuthorize("hasAuthority(@apiPermission.orderDetailsPermission.code)")
    @GetMapping(path = API_PATH_GET_ORDER_INFO_BY_ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getOrderDetailsById(@PathVariable Long orderId) throws OrderServiceException, AuthServiceException {
        OrderService.OrderResponse response = orderService.getOrderInfoById(orderId);
        return buildResponseWithDetails(RestResponseStatusCode.OK_STATUS, RestResponseMessage.FETCH_OK, response);
    }
}