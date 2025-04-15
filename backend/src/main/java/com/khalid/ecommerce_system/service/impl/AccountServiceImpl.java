package com.khalid.ecommerce_system.service.impl;

import com.khalid.ecommerce_system.constant.RestErrorMessageDetail;
import com.khalid.ecommerce_system.constant.RestResponseMessage;
import com.khalid.ecommerce_system.constant.RestResponseStatusCode;
import com.khalid.ecommerce_system.entity.AccountInfo;
import com.khalid.ecommerce_system.entity.RoleInfo;
import com.khalid.ecommerce_system.exception.AuthServiceException;
import com.khalid.ecommerce_system.exception.RoleServiceException;
import com.khalid.ecommerce_system.repository.AccountInfoRepository;
import com.khalid.ecommerce_system.repository.RoleInfoRepository;
import com.khalid.ecommerce_system.service.AccountService;
import com.khalid.ecommerce_system.service.AuthCommonService;
import com.khalid.ecommerce_system.util.DateUtil;
import com.khalid.ecommerce_system.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service("AccountService")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AccountServiceImpl implements AccountService {

     private final AccountInfoRepository accountInfoRepository;
     private final RoleInfoRepository roleInfoRepository;
     private final AuthCommonService authCommonService;

    @Override
    public AccountRegisterResponse registerAccount(AccountRegisterInputParameter parameter) throws RoleServiceException {
        RoleInfo roleInfo = roleInfoRepository.findByRoleCode(parameter.getRoleCode());
        if(Objects.isNull(roleInfo)){
            log.info("role nothing.");
            throw new RoleServiceException(RestResponseMessage.ROLE_NOT_FOUND,
                    RestResponseStatusCode.VALIDATION_ERROR_STATUS, RestErrorMessageDetail.BAD_CREDENTIALS_ERROR_MESSAGE);
        }
        if (roleInfo.getId() == 1) {
            log.info("Can not create admin account.");
            throw new RoleServiceException(
                    RestResponseMessage.ADMIN_ACCOUNT_CREATION_ERROR_MESSAGE,
                    RestResponseStatusCode.NOT_ALLOWED_STATUS,
                    RestErrorMessageDetail.ADMIN_ACCOUNT_CREATION_ERROR_MESSAGE);
        }
        AccountInfo isUserExists = accountInfoRepository.findByMailAddressAndDeleteFlgIsFalse(parameter.getMailAddress());

        if(isUserExists!=null){
            log.info("Account already exist with same email.");
            throw new RoleServiceException(RestResponseMessage.USER_ALREADY_EXISTS,
                    RestResponseStatusCode.ALREADY_EXISTS_STATUS,
                    RestErrorMessageDetail.USER_ALREADY_EXISTS_ERROR_DETAILS);
        }

        AccountInfo accountInfo = buildAccountInfo(parameter, roleInfo);
        accountInfoRepository.save(accountInfo);

        return buildAccountRegisterResponse(accountInfo);
    }

    @Override
    public AccountInfo getAccountInformationByEmail(String mailAddress) throws  AuthServiceException {
        AccountInfo accountInfo = accountInfoRepository.findByMailAddressAndDeleteFlgIsFalse(mailAddress);
        if(Objects.isNull(accountInfo)){
            log.info("account nothing.");
            throw new AuthServiceException(RestResponseMessage.WRONG_CREDENTIALS,
                    RestResponseStatusCode.VALIDATION_ERROR_STATUS, RestErrorMessageDetail.BAD_CREDENTIALS_ERROR_MESSAGE);
        }
        return accountInfo;
    }

    @Override
    public List<AccountRegisterResponse> getUserList() {
        return accountInfoRepository.findAll()
                .stream()
                .filter(accountInfo -> accountInfo.getId() != 1)
                .sorted(Comparator.comparing(AccountInfo::getId))
                .map(this::buildAccountRegisterResponse)
                .collect(Collectors.toList());
    }

    @Override
    public AccountRegisterResponse getUserInfo(long userId) throws AuthServiceException {
        AccountInfo currentUserAccountInfo = getCurrentUserAccountInfo();
        if (currentUserAccountInfo.getRoleInfoId() != 1 && currentUserAccountInfo.getId() != userId) {
            log.info("account nothing.");
            throw new AuthServiceException(RestResponseMessage.ACCESS_DENIED,
                    RestResponseStatusCode.VALIDATION_ERROR_STATUS, RestErrorMessageDetail.NO_PERMISSION_ERROR_MESSAGE);
        }

        Optional<AccountInfo> accountInfo = accountInfoRepository.findById(userId);
        if (accountInfo.isEmpty()) {
            log.info("account nothing.");
            throw new AuthServiceException(RestResponseMessage.WRONG_CREDENTIALS,
                    RestResponseStatusCode.VALIDATION_ERROR_STATUS, RestErrorMessageDetail.BAD_CREDENTIALS_ERROR_MESSAGE);
        }

        return buildAccountRegisterResponse(accountInfo.get());
    }

    @Override
    public AccountRegisterResponse getUserDetails() throws  AuthServiceException {
        return buildAccountRegisterResponse(getCurrentUserAccountInfo());
    }

    @Override
    public AccountRegisterResponse updateUserInfo(AccountUpdateInputParameter parameter) throws AuthServiceException {
        AccountInfo AccountInfo = getCurrentUserAccountInfo();

        AccountInfo.setFirstName(parameter.getFirstName());
        AccountInfo.setLastName(parameter.getLastName());
        AccountInfo.setPhoneNumber(parameter.getPhoneNumber());
        AccountInfo.setLocation(parameter.getLocation());

        accountInfoRepository.save(AccountInfo);
        return buildAccountRegisterResponse(AccountInfo);
    }

    //Utility Methods
    private AccountRegisterResponse buildAccountRegisterResponse(AccountInfo accountInfo) {
        return AccountRegisterResponse.builder()
                .userId(accountInfo.getId())
                .firstName(accountInfo.getFirstName())
                .lastName(accountInfo.getLastName())
                .mailAddress(accountInfo.getMailAddress())
                .password(accountInfo.getPassword())
                .phoneNumber(accountInfo.getPhoneNumber())
                .location(accountInfo.getLocation())
                .roleName(accountInfo.getRoleInfo().getRole().toString())
                .build();
    }
    private AccountInfo buildAccountInfo(AccountRegisterInputParameter parameter, RoleInfo roleInfo) {
        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setRoleInfo(roleInfo);
        accountInfo.setRoleInfoId(roleInfo.getId());
        accountInfo.setFirstName(parameter.getFirstName());
        accountInfo.setLastName(parameter.getLastName());
        accountInfo.setJoiningDt(DateUtil.currentTime());
        accountInfo.setMailAddress(parameter.getMailAddress());
        accountInfo.setPassword(StringUtil.encodeBCrypt(parameter.getPassword()));
        accountInfo.setPhoneNumber(parameter.getPhoneNumber());
        accountInfo.setLocation(parameter.getLocation());
        accountInfo.setDeleteFlg(false);
        accountInfo.setCreatedDt(DateUtil.currentTime());
        accountInfo.setUpdatedDt(DateUtil.currentTime());
        accountInfo.setLastLoginDt(DateUtil.currentTime());
        return accountInfo;
    }
    AccountInfo getCurrentUserAccountInfo() throws AuthServiceException {
        return accountInfoRepository.findByAccessKey(authCommonService.getAccessKey())
                .orElseThrow(() -> new AuthServiceException(
                        RestResponseMessage.USER_NOT_FOUND,
                        RestResponseStatusCode.NOT_FOUND_STATUS,
                        RestErrorMessageDetail.USER_NOT_FOUND_ERROR_MESSAGE));
    }

}
