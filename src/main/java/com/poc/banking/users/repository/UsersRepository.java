package com.poc.banking.users.repository;

import com.poc.banking.users.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsersRepository extends JpaRepository<UserEntity, UUID>, JpaSpecificationExecutor<UserEntity> {

    default void delete(UserEntity entity) {
        entity.setDeleted(true);
    }

    Optional<UserEntity> findByUsername(final String username);

}
