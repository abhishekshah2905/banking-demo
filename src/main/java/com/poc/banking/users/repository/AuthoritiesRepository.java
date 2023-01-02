package com.poc.banking.users.repository;

import com.poc.banking.users.entity.Authorities;
import com.poc.banking.users.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AuthoritiesRepository extends JpaRepository<Authorities, UUID> {

    default void delete(UserEntity entity) {
        entity.setDeleted(true);
    }

    Authorities findByAuthority(final String authority);

}
