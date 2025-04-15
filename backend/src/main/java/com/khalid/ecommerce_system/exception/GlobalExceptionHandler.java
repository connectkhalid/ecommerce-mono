package com.khalid.ecommerce_system.exception;

import com.khalid.ecommerce_system.constant.RestApiResponse;
import com.khalid.ecommerce_system.constant.RestResponseStatusCode;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;

import java.io.IOException;
import java.sql.SQLException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({
            AccountServiceException.class, AuthServiceException.class, RoleServiceException.class,ProductServiceException.class,
            FileUploadException.class, OrderServiceException.class
    })
    public ResponseEntity<Object> handleCustomExceptions(CustomGenericException ex) {
        return RestApiResponse.buildResponseWithError(ex.getErrorMessage(), ex.getHttpStatus(), ex.getErrorDetail());
    }

    @ExceptionHandler({
            NullPointerException.class, NumberFormatException.class, IllegalArgumentException.class,
            IllegalStateException.class, RuntimeException.class, ClassCastException.class,
            ClassNotFoundException.class, OutOfMemoryError.class, StackOverflowError.class,
            IOException.class, SQLException.class, BadCredentialsException.class, IllegalStateException.class,
            MethodArgumentNotValidException.class
    })
    public ResponseEntity<Object> handleCommonExceptions(Exception ex) {
        return RestApiResponse.buildResponseWithError(
                "An error occurred while processing your request.",
                RestResponseStatusCode.INTERNAL_ERROR_STATUS,
                ex.getClass().getSimpleName() + ": " + ex.getMessage());
    }

    @ExceptionHandler(MultipartException.class)
    void handleMultipartException(MultipartException ex, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value(),"Please select a file");
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public void handleConstraintViolationException(ConstraintViolationException ex,HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUnknownException(Exception ex) {
        return RestApiResponse.buildResponseWithError(
                "An unknown error occurred.",
                RestResponseStatusCode.INTERNAL_ERROR_STATUS,
                ex.getClass().getSimpleName() + ": " + ex.getMessage());
    }
}