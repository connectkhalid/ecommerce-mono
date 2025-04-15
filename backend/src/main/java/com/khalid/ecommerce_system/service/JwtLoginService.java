package com.khalid.ecommerce_system.service;

import com.khalid.ecommerce_system.exception.AuthServiceException;
import lombok.Builder;
import lombok.Data;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

public interface JwtLoginService {

    @Builder
    @Data
    class ExpiredTokenResponse {
        private String accessKey;
        private String mailAddress;
        private Long userId;
    }

    @Builder
    @Data
    class LoginResponse {
        private Long userId;
        private long roleId;
        private String firstName;
        private String lastName;
        private String mailAddress;
    }

    @Builder
    @Data
    public class LoginResponseDTO {
        private String accessKey;
        private LoginResponse loginResponse;
    }

    @Builder
    @Data
    class RefreshTokenResponse {
        private Long userId;
        private long roleId;
        private String accessKey;
        private String firstName;
        private String lastName;
        private Timestamp joiningDt;
        private String mailAddress;
        private String password;
        private Boolean deleteFlg;
        private long createdUserId;
    }

    LoginResponseDTO loginWithJwtSecurity(String mailAddress, String password) throws AuthServiceException;

   // @Transactional(rollbackFor = Exception.class)
    RefreshTokenResponse getRefreshToken() throws AuthServiceException;

    @Transactional(rollbackFor = Exception.class)
    void logoutWithJwtSecurity() throws AuthServiceException;
}
