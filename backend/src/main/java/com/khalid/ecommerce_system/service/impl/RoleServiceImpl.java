package com.khalid.ecommerce_system.service.impl;

import com.khalid.ecommerce_system.constant.RestErrorMessageDetail;
import com.khalid.ecommerce_system.constant.RestResponseMessage;
import com.khalid.ecommerce_system.constant.RestResponseStatusCode;
import com.khalid.ecommerce_system.constant.Role;
import com.khalid.ecommerce_system.entity.AccountInfo;
import com.khalid.ecommerce_system.entity.RoleInfo;
import com.khalid.ecommerce_system.exception.AuthServiceException;
import com.khalid.ecommerce_system.exception.RoleServiceException;
import com.khalid.ecommerce_system.form.validation.EnumUtility;
import com.khalid.ecommerce_system.repository.AccountInfoRepository;
import com.khalid.ecommerce_system.repository.RoleInfoRepository;
import com.khalid.ecommerce_system.service.AuthCommonService;
import com.khalid.ecommerce_system.service.RoleService;
import com.khalid.ecommerce_system.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service("RoleService")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RoleServiceImpl implements RoleService {

    private final RoleInfoRepository roleInfoRepository;
    private final AccountInfoRepository accountInfoRepository;
    private final AuthCommonService authCommonService;
    @Override
    public RoleRegisterResponse registerRole(RoleRegisterInputParameter parameter) throws AuthServiceException {
        AccountInfo accountInfo = accountInfoRepository.findByAccessKey(authCommonService.getAccessKey())
                .orElseThrow(() -> new AuthServiceException(RestResponseMessage.USER_NOT_FOUND,
                        RestResponseStatusCode.NOT_FOUND_STATUS, RestErrorMessageDetail.USER_NOT_FOUND_ERROR_MESSAGE));

        Role role = EnumUtility.getEnumValue(Role.class, parameter.getRoleName());

        RoleInfo roleInfo = new RoleInfo();
        roleInfo.setRoleCode(parameter.getRoleCode());
        roleInfo.setRole(role);
        roleInfo.setDeleteFlag(false);
        roleInfo.setCreatedDt(DateUtil.currentTime());
        roleInfo.setUpdatedDt(DateUtil.currentTime());
        roleInfoRepository.save(roleInfo);

        return RoleRegisterResponse.builder()
                .roleCode(parameter.getRoleCode())
                .roleName(parameter.getRoleName())
                .createdDt(roleInfo.getCreatedDt())
                .updatedDt(roleInfo.getUpdatedDt())
                .build();
    }
}
