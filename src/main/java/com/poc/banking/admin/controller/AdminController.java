package com.poc.banking.admin.controller;

import com.poc.banking.accounts.model.Account;
import com.poc.banking.accounts.service.AccountService;
import com.poc.banking.admin.model.CreateUserCommand;
import com.poc.banking.admin.model.FindUserCommand;
import com.poc.banking.users.model.User;
import com.poc.banking.users.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AdminController {

    private final AccountService accountService;

    private final UserService userService;

    @PostMapping("/admin/accounts")
    @Operation(summary = "Create new account")
    @ApiResponse(responseCode = "200", description = "Account created.",
            content = {@Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = Account.class))})
    @ApiResponse(responseCode = "404", description = "Account not found.", content = @Content)
    public Account createAccount(@Valid @NotNull @RequestBody Account account) {
        return accountService.createAccount(account);
    }

    @PostMapping("/admin/accounts/{accountId}/add-user/{userId}")
    @Operation(summary = "Add user in account")
    @ApiResponse(responseCode = "200", description = "User added successfully.",
            content = {@Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = Account.class))})
    @ApiResponse(responseCode = "404", description = "Account not found.", content = @Content)
    public void addUser(@PathVariable UUID accountId, @PathVariable UUID userId) {
        this.accountService.addUserIntoAccount(accountId, userId);
    }

    @DeleteMapping("/admin/accounts/{accountId}/remove-user/{userId}")
    @Operation(summary = "Remove user from account")
    @ApiResponse(responseCode = "200", description = "User removed successfully.",
            content = {@Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = Account.class))})
    @ApiResponse(responseCode = "404", description = "Account not found.", content = @Content)
    public void removeUser(@PathVariable UUID accountId, @PathVariable UUID userId) {
        this.accountService.removeUserFromAccount(accountId, userId);
    }

    @PostMapping("/admin/users")
    @Operation(summary = "Create new user")
    @ApiResponse(responseCode = "200", description = "User created.")
    public void createUser(@Valid @NotNull @RequestBody CreateUserCommand user) {
        userService.createUser(user);
    }

    @GetMapping("/users")
    @Operation(summary = "Retrieve a users.")
    @ApiResponse(responseCode = "200", description = "Users found.",
            content = {@Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = User.class))})
    public List<User> getUsers(@ParameterObject @Parameter(hidden = true) FindUserCommand command) {
        return userService.getUsers(command);
    }

}
