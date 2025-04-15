package com.khalid.ecommerce_system.api.pub;

import com.khalid.ecommerce_system.constant.RestResponseMessage;
import com.khalid.ecommerce_system.constant.RestResponseStatusCode;
import com.khalid.ecommerce_system.exception.AuthServiceException;
import com.khalid.ecommerce_system.service.JwtLoginService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import static com.khalid.ecommerce_system.constant.RestApiResponse.*;

@Slf4j
@RestController
@CrossOrigin
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthController {

    private final JwtLoginService jwtLoginService;

    @Data
    public static class JwtTokenRequest {
        @NotBlank(message = "field is mandatory")
        private String mailAddress;

        @NotBlank(message = "field is mandatory")
        private String password;
    }

    @PostMapping(value = "${jwt.get.token.uri}")
    public ResponseEntity<Object> createAuthenticationToken(@RequestBody @Valid JwtTokenRequest authenticationRequest) throws AuthenticationException, AuthServiceException{
         JwtLoginService.LoginResponseDTO loginResponse = jwtLoginService.loginWithJwtSecurity(authenticationRequest.getMailAddress().toLowerCase(), authenticationRequest.getPassword());
         return buildResponseWithDetails(RestResponseStatusCode.OK_STATUS, RestResponseMessage.LOGIN_SUCCESS, loginResponse);
    }

    @PutMapping(value = "${jwt.refresh.token.uri}")
    public ResponseEntity<Object> refreshAndGetAuthenticationToken() throws AuthServiceException {
        JwtLoginService.RefreshTokenResponse refreshTokenResponse = jwtLoginService.getRefreshToken();
        return buildResponseWithDetails(RestResponseStatusCode.OK_STATUS, RestResponseMessage.REFRESH_SUCCESS, refreshTokenResponse);
    }

    @DeleteMapping(value = "${jwt.logout.token.uri}")
    public ResponseEntity<Object> signOut() throws AuthServiceException {
        jwtLoginService.logoutWithJwtSecurity();
        return buildResponseWithoutDetails(RestResponseStatusCode.OK_STATUS, RestResponseMessage.LOGOUT_SUCCESS);
    }
}
