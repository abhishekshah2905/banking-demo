package com.poc.banking.users.service;

import com.poc.banking.accounts.service.AccountService;
import com.poc.banking.admin.model.CreateUserCommand;
import com.poc.banking.admin.model.FindUserCommand;
import com.poc.banking.exceptions.NotFoundException;
import com.poc.banking.users.entity.UserEntity;
import com.poc.banking.users.model.User;
import com.poc.banking.users.repository.AuthoritiesRepository;
import com.poc.banking.users.repository.UserSpecification;
import com.poc.banking.users.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.poc.banking.util.AppConstants.USER_NOT_FOUND;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserSpecification userSpecification;

    private final UsersRepository usersRepository;

    private final AccountService accountService;

    private final PasswordEncoder passwordEncoder;

    private final AuthoritiesRepository authoritiesRepository;

    public List<User> getUsers(FindUserCommand command) {
        List<UserEntity> searchUsers;
        if (StringUtils.isEmpty(command.getQuery()) && CollectionUtils.isEmpty(command.getIds())) {
            searchUsers = usersRepository.findAll();
        } else {
            final Specification<UserEntity> userSpecifications = userSpecification.getUsers(command);
            searchUsers = usersRepository.findAll(userSpecifications);
        }
        return searchUsers.stream().map(entity ->
                new User(entity.getId(), entity.getFirstName(), entity.getLastName(), accountService.getAccounts(entity.getId()))
        ).collect(Collectors.toList());
    }

    public User getUser(final UUID userId) {
        log.trace("Get user [userId: {}]", userId);
        UserEntity entity = usersRepository.findById(userId).orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));

        return new User(
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                accountService.getAccounts(entity.getId()));
    }

    public void createUser(CreateUserCommand user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(user.getUserName());
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        userEntity.setAuthorities(user.getAuthorities().stream().map(authoritiesRepository::findByAuthority).collect(Collectors.toSet()));
        usersRepository.save(userEntity);
    }

}
