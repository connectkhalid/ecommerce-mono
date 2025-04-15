package com.khalid.ecommerce_system.security;


import com.khalid.ecommerce_system.entity.AccountInfo;
import com.khalid.ecommerce_system.repository.AccountInfoRepository;
import com.khalid.ecommerce_system.repository.RoleFeaturePermissionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Transactional
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JwtInMemoryUserDetailsService implements UserDetailsService {

    private final AccountInfoRepository accountInfoRepository;
    private final RoleFeaturePermissionRepository roleFeaturePermissionRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AccountInfo accountInfo = accountInfoRepository.findByMailAddressAndDeleteFlgIsFalse(username);

        if(Objects.isNull(accountInfo) )
            throw new UsernameNotFoundException(String.format("USER_NOT_FOUND '%s'.", username));

        if(!Objects.isNull(accountInfo)){
            List<SimpleGrantedAuthority> featurePermissionList = roleFeaturePermissionRepository
                    .findAllFeaturePermissionByRoleCode(accountInfo.getRoleInfo().getRoleCode())
                    .stream().map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
            return new JwtUserDetails(accountInfo.getId(), accountInfo.getMailAddress(),
                    accountInfo.getPassword(), featurePermissionList);
        }

        List<SimpleGrantedAuthority> featurePermissionList = roleFeaturePermissionRepository
                .findAllFeaturePermissionByRoleCode(accountInfo.getRoleInfo().getRoleCode())
                .stream().map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return new JwtUserDetails(accountInfo.getId(), accountInfo.getMailAddress(),
                accountInfo.getPassword(), featurePermissionList);
    }
}
