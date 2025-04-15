package com.khalid.ecommerce_system.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;

import java.io.IOException;

public class HttpServletUtil {
    private HttpServletUtil() {}

    public static void doResponseJson(HttpServletResponse response, Object result) throws JsonProcessingException, IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(
                SingletonJacksonObjectMapper.getInstance().writeValueAsString(result)
        );
    }
}
