package com.poc.banking.admin.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserCommand {

    private String userName;


    // TODO :: REMOVE ONCE WE HAVE EMAIL/SMS INTEGRATION. SO WE CAN GENERATE IT INTERNALLY
    private String password;

    private String firstName;

    private String lastName;

    private List<String> authorities;
}
