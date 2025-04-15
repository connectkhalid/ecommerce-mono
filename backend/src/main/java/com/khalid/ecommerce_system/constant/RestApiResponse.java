package com.khalid.ecommerce_system.constant;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Builder;
import lombok.Data;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

@Builder
@Data
public class RestApiResponse {

    @Builder
    @Data
    public static class ValidationError {
        String fieldName;
        String validationMessage;
    }

    private int statusCode;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object details;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object errors;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String error;

    public static void writeInvalidTokenErrorResponse(HttpServletResponse response, String msg, String msgCode, int status) throws IOException, JSONException {
        //added specific error handling in filter chain
        response.setStatus(status);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("statusCode", status);
        body.put("message", msgCode);
        body.put("error", msg);
        byte[] jsonBody = new ObjectMapper().writeValueAsBytes(Collections.synchronizedMap(body));
        response.getOutputStream().write(jsonBody);
    }

    public static ResponseEntity<Object> buildResponseWithError(String message, int statusCode, String description) {
        return new ResponseEntity<>(RestApiResponse.builder()
                .statusCode(statusCode)
                .message(message)
                .error(description)
                .build(),
                HttpStatus.valueOf(statusCode));
    }

    public static ResponseEntity<Object> buildResponseWithDetails(int statusCode, String message, Object details) {
        return new ResponseEntity<>(RestApiResponse.builder()
                .statusCode(statusCode)
                .message(message)
                .details(details)
                .build(),
                HttpStatus.valueOf(statusCode));
    }



    public static ResponseEntity<Object> buildResponseWithoutDetails(int statusCode, String message) {
        return new ResponseEntity<>(RestApiResponse.builder()
                .statusCode(statusCode)
                .message(message)
                .build(),
                HttpStatus.valueOf(statusCode));
    }

    public static void writeDynamicTokenResponse(HttpServletResponse response, String msgCode, int status) throws IOException, JSONException {
        //added specific error handling in filter chain
        response.setStatus(status);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("statusCode", status);
        body.put("message", msgCode);
        byte[] jsonBody = new ObjectMapper().writeValueAsBytes(Collections.synchronizedMap(body));
        response.getOutputStream().write(jsonBody);
    }
}
