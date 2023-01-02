package com.poc.banking.util;

import com.poc.banking.users.entity.Authorities;
import com.poc.banking.users.entity.UserEntity;
import com.poc.banking.users.repository.AuthoritiesRepository;
import com.poc.banking.users.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final static String SYSTEM_GENERATED = "System Generated";

    boolean alreadySetup = false;

    @Autowired
    private UsersRepository userRepository;

    @Autowired
    private AuthoritiesRepository authoritiesRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup)
            return;

        createRoleIfNotFound("ROLE_ADMIN");
        createRoleIfNotFound("ROLE_ACCOUNT");
        createRoleIfNotFound("ROLE_USER");

        Authorities adminRole = authoritiesRepository.findByAuthority("ROLE_ADMIN");
        UserEntity user = new UserEntity();
        user.setFirstName("ADMIN");
        user.setLastName("ADMIN");
        user.setUsername("admin");
        user.setCreatedBy(SYSTEM_GENERATED);
        user.setLastModifiedBy(SYSTEM_GENERATED);
        user.setPassword(passwordEncoder.encode("admin"));
        user.setAuthorities(Set.of(adminRole));
        user.setEnabled(true);
        userRepository.save(user);

        alreadySetup = true;
    }

    @Transactional
    Authorities createRoleIfNotFound(String name) {
        Authorities authorities = authoritiesRepository.findByAuthority(name);
        if (authorities == null) {
            authorities = new Authorities();
            authorities.setAuthority(name);
            authorities.setCreatedBy(SYSTEM_GENERATED);
            authorities.setLastModifiedBy(SYSTEM_GENERATED);
            authoritiesRepository.save(authorities);
        }
        return authorities;
    }
}