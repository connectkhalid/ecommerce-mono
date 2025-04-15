package com.khalid.ecommerce_system.interceptor;

import com.khalid.ecommerce_system.constant.Constants;
import com.khalid.ecommerce_system.service.ClientAuthService;
import com.khalid.ecommerce_system.util.HttpServletUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

public class ClientAuthInterceptor implements HandlerInterceptor {
    @Autowired private ClientAuthService clientAuthService;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        ClientAuthService.Result result = clientAuthService.verify(
                request.getHeader(Constants.HttpHeaders.HEADER_KEY_CLIENT_AUTH),
                request.getRequestURI());
        if (!result.getResultCode().equals(ClientAuthService.ResultCode.OK)) {
            HttpServletUtil.doResponseJson(response, result);
            return false;
        }
        return true;
    }
}
