package com.poc.banking.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poc.banking.admin.model.CreateUserCommand;
import com.poc.banking.users.model.User;
import com.poc.banking.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AdminControllerIT extends AbstractIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createUserAccountTests() throws Exception {
        final CreateUserCommand createUserCommand = TestUtils.buildUserCommand();
        mockMvc.perform(post("/admin/users")
                        .header("Authorization", "Bearer " + getAdminToken())
                        .content(objectMapper.writeValueAsString(createUserCommand))
                        .contentType("application/json"))
                .andExpect(status().isOk());

        final MvcResult usersResponse = mockMvc.perform(get("/users")
                        .header("Authorization", "Bearer " + getAdminToken())
                        .param("query", createUserCommand.getUserName())
                        .contentType("application/json"))
                .andExpect(status().isOk()).andReturn();
        List<User> users = Arrays.asList(objectMapper.readValue(usersResponse.getResponse().getContentAsByteArray(), User[].class));
        Assertions.assertTrue(users.stream().allMatch(user -> user.getFirstName().equals(createUserCommand.getFirstName())));
    }

}