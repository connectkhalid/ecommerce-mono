package com.khalid.ecommerce_system.exception;

public class AccountServiceException extends CustomGenericException{
    public AccountServiceException(String errorMessage, int httpStatus, String errorDetail) {
        super(errorMessage, httpStatus, errorDetail);
    }
}
