package com.khalid.ecommerce_system.util;

import com.khalid.ecommerce_system.entity.AccountInfo;
import com.khalid.ecommerce_system.repository.AccountInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ParameterCheckInDatabaseUtil {

    private final AccountInfoRepository accountInfoRepository;

    public boolean checkMailinDatabase(String mail) {
        AccountInfo accountInfo = accountInfoRepository.findByMailAddressAndDeleteFlgIsFalse(mail);
        if(!Objects.isNull(accountInfo)){
            log.error("Mail Address already in use AccountInfo.");
            return false;
        }
        return true;
    }
}
