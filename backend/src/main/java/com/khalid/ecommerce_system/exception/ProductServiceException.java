package com.khalid.ecommerce_system.exception;

public class ProductServiceException extends CustomGenericException{
    public ProductServiceException(String errorMessage, int httpStatus, String errorDetail) {
        super(errorMessage, httpStatus, errorDetail);
    }
}