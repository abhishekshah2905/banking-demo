package com.poc.banking.admin.model;

import lombok.*;

import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class FindUserCommand {

    @Singular
    private Set<UUID> ids;

    private String query;

}
