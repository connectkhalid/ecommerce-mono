package com.khalid.ecommerce_system.exception;

public class FileUploadException extends CustomGenericException {
    public FileUploadException(String errorMessage, int httpStatus, String errorDetail) {
        super(errorMessage, httpStatus, errorDetail);
    }
}