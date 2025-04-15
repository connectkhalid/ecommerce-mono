package com.khalid.ecommerce_system.exception;

public class OrderServiceException extends CustomGenericException{
    public OrderServiceException(String errorMessage, int httpStatus, String errorDetail) {
        super(errorMessage, httpStatus, errorDetail);
    }
}