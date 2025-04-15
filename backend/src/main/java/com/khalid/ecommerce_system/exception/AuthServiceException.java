package com.khalid.ecommerce_system.exception;

public class AuthServiceException extends CustomGenericException{

    public AuthServiceException(String errorMessage, int httpStatus, String errorDetail) {
        super(errorMessage, httpStatus, errorDetail);
    }
}
