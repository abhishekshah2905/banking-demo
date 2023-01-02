package com.poc.banking.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poc.banking.admin.model.CreateUserCommand;
import com.poc.banking.users.repository.UsersRepository;
import com.poc.banking.users.service.UserService;
import com.poc.banking.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static com.poc.banking.util.AppConstants.ROLE_USER;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class UserServiceTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UsersRepository userRepository;

    @Test
    void createUserTest() {
        CreateUserCommand createUserCommand = TestUtils.buildCreateUserCommand();
        Mockito.when(passwordEncoder.encode(createUserCommand.getPassword())).thenReturn(createUserCommand.getPassword());
        Mockito.when(authoritiesRepository.findByAuthority(ROLE_USER)).thenReturn(TestUtils.buildAuthoritiesEntity(ROLE_USER))
        Mockito.when(usersRepository.save(Mockito.any())).thenReturn("");
        this.userService.createUser(createUserCommand);
    }

}