package com.poc.banking.security.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Login response object containing the JWT
 */
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class LoginResult {

    @NonNull
    private String token;
}
