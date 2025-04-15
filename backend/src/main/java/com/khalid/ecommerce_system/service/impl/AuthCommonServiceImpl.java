package com.khalid.ecommerce_system.service.impl;

import com.khalid.ecommerce_system.constant.Constants;
import com.khalid.ecommerce_system.service.AuthCommonService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;

@Service("AuthCommonService")
public class AuthCommonServiceImpl implements AuthCommonService {

    @Value("${jwt.http.request.header}")
    String tokenParamName;

    @Autowired(required = false)
    HttpServletRequest httpServletRequest;


    @Override
    public String getAccessKey() {
        String fetchedAccessKey;
        try{
            String accesskey = httpServletRequest.getHeader(tokenParamName);

            if (!accesskey.isEmpty() && accesskey.contains("Bearer")) {
                return null;
            }

            if (!StringUtils.hasText(accesskey)) {
                fetchedAccessKey = httpServletRequest.getParameter("accesskey");
            } else {
                fetchedAccessKey = accesskey;
            }

            if (!Objects.isNull(fetchedAccessKey)) {
                if (fetchedAccessKey.contains(Constants.Security.TOKEN_TYPE)) {
                    fetchedAccessKey = fetchedAccessKey.substring(Constants.Security.TOKEN_TYPE_LEN);
                }
            }
            return fetchedAccessKey;

        } catch (Throwable throwable){

        }
        return null;
    }
}
