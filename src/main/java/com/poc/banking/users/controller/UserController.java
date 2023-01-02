package com.poc.banking.users.controller;

import com.poc.banking.security.SecurityUtil;
import com.poc.banking.users.model.User;
import com.poc.banking.users.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users/me")
    @Operation(summary = "Retrieve a user with provided identifier")
    @ApiResponse(responseCode = "200", description = "Users found.",
            content = {@Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = User.class))})
    @ApiResponse(responseCode = "404", description = "User not found.", content = @Content)
    public User getUser() {
        return userService.getUser(SecurityUtil.getUserId());
    }

}
