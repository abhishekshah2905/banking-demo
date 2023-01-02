package com.poc.banking.users.model;

import com.poc.banking.accounts.model.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private UUID id;

    private String firstName;

    private String lastName;

    private List<Account> accounts;

}
