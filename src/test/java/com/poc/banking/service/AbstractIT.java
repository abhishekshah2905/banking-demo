package com.poc.banking.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poc.banking.security.model.LoginRequest;
import com.poc.banking.security.model.LoginResult;
import com.poc.banking.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class AbstractIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String adminToken;

    @BeforeEach
    public void beforeAll() throws Exception {
        final LoginRequest loginRequest = TestUtils.adminLoginRequest();
        final MvcResult loginResponse = mockMvc.perform(post("/login")
                        .content(objectMapper.writeValueAsString(loginRequest))
                        .contentType("application/json"))
                .andExpect(status().isOk()).andReturn();
        LoginResult loginResult = objectMapper.readValue(loginResponse.getResponse().getContentAsByteArray(), LoginResult.class);

        this.adminToken = loginResult.getToken();
    }

    public String getAdminToken() {
        return adminToken;
    }
}