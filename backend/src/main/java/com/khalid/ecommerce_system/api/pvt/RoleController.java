package com.khalid.ecommerce_system.api.pvt;

import com.khalid.ecommerce_system.constant.Constants;
import com.khalid.ecommerce_system.constant.RestResponseMessage;
import com.khalid.ecommerce_system.constant.RestResponseStatusCode;
import com.khalid.ecommerce_system.exception.AuthServiceException;
import com.khalid.ecommerce_system.exception.RoleServiceException;
import com.khalid.ecommerce_system.service.RoleService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.khalid.ecommerce_system.constant.RestApiResponse.buildResponseWithDetails;

@Slf4j
@RestController
@CrossOrigin
@Validated
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RoleController {
    public static final String API_PATH_ROLE_REGISTER = Constants.ApiPath.PUBLIC_API_PATH + "/register-role";

    private final RoleService roleService;

    @Data
    public static class RoleRegisterInput{
        @NotNull(message = "field is mandatory")
        long roleCode;
        @NotBlank(message = "field is mandatory")
        String roleName;
    }

    @PostMapping(path = API_PATH_ROLE_REGISTER,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> roleRegister(@Valid @RequestBody RoleController.RoleRegisterInput roleRegisterInput) throws RoleServiceException, AuthServiceException {
        RoleService.RoleRegisterInputParameter parameter = new RoleService.RoleRegisterInputParameter(
                roleRegisterInput.getRoleCode(),
                roleRegisterInput.roleName
        );

        RoleService.RoleRegisterResponse roleRegisterResponse = roleService.registerRole(parameter);
        return buildResponseWithDetails(RestResponseStatusCode.OK_STATUS, RestResponseMessage.CREATE_OK, roleRegisterResponse);
    }
}
