package com.poc.banking.users.repository;

import com.poc.banking.admin.model.FindUserCommand;
import com.poc.banking.users.entity.UserEntity;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserSpecification {
    public Specification<UserEntity> getUsers(FindUserCommand command) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            final String searchQuery = command.getQuery();
            if (StringUtils.isNotBlank(searchQuery)) {
                final String lowerCaseSearchStr = searchQuery.toLowerCase();
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), "%" + lowerCaseSearchStr + "%"));
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), "%" + lowerCaseSearchStr + "%"));
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("username")), "%" + lowerCaseSearchStr + "%"));
            }
            if (!CollectionUtils.isEmpty(command.getIds())) {
                predicates.add(criteriaBuilder.in(root.get("id")).value(command.getIds()));
            }
            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        };
    }
}
