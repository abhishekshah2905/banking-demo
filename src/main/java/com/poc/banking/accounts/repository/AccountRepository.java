package com.poc.banking.accounts.repository;

import com.poc.banking.accounts.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, UUID> {

    default void delete(AccountEntity entity) {
        entity.setDeleted(true);
    }

    List<AccountEntity> findByUsersId(final UUID userId);

    Optional<AccountEntity> findByIdAndUsersId(final UUID accountId, final UUID userId);

}
