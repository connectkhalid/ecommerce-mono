package com.khalid.ecommerce_system.service;

import com.khalid.ecommerce_system.entity.AccountInfo;
import com.khalid.ecommerce_system.exception.AccountServiceException;
import com.khalid.ecommerce_system.exception.AuthServiceException;
import com.khalid.ecommerce_system.exception.RoleServiceException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AccountService {


    @Builder
    @Data
    class AccountRegisterResponse{
        private long userId;
        private String firstName;
        private String lastName;
        private String mailAddress;
        private String password;
        private String phoneNumber;
        private String location;
        private String roleName;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class AccountRegisterInputParameter{
        private String firstName;
        private String lastName;
        private String mailAddress;
        private String password;
        private String phoneNumber;
        private String location;
        private long roleCode;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class AccountUpdateInputParameter{
        private long userId;
        private String firstName;
        private String lastName;
        private String phoneNumber;
        private String location;
    }

    @Transactional(rollbackFor = Exception.class)
    AccountRegisterResponse registerAccount(AccountRegisterInputParameter parameter) throws AccountServiceException, RoleServiceException;
    AccountInfo getAccountInformationByEmail(String mailAddress) throws AccountServiceException, AuthServiceException;

    List<AccountRegisterResponse> getUserList() throws AccountServiceException;

    AccountRegisterResponse getUserInfo(long userId) throws AccountServiceException, AuthServiceException;

    AccountRegisterResponse getUserDetails() throws AuthServiceException;;

    AccountRegisterResponse updateUserInfo(AccountUpdateInputParameter parameter) throws AccountServiceException, AuthServiceException;
}
