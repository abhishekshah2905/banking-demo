package com.poc.banking.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poc.banking.security.model.LoginRequest;
import com.poc.banking.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class AuthControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void adminLoginTest() throws Exception {
        final LoginRequest loginRequest = TestUtils.adminLoginRequest();
        mockMvc.perform(post("/login")
                        .content(objectMapper.writeValueAsString(loginRequest))
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void adminLoginTestFailWhenSuppliedWrongPassword() throws Exception {
        final LoginRequest loginRequest = TestUtils.adminLoginRequest();
        loginRequest.setPassword("wrong_password");
        mockMvc.perform(post("/login")
                        .content(objectMapper.writeValueAsString(loginRequest))
                        .contentType("application/json"))
                .andExpect(status().isUnauthorized());
    }

}