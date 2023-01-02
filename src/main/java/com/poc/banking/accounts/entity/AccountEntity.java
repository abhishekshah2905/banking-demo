package com.poc.banking.accounts.entity;

import com.poc.banking.accounts.model.AccountType;
import com.poc.banking.audit.AbstractAuditingEntity;
import com.poc.banking.transactions.entity.TransactionEntity;
import com.poc.banking.users.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Where(clause = "deleted=false")
public class AccountEntity extends AbstractAuditingEntity {

    private AccountType type = AccountType.CURRENT;

    @Column(name = "account_number", unique = true)
    private String accountNumber;

    private boolean deleted;

    @Column(name = "balance")
    private BigDecimal balance;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "bank_account_users",
            joinColumns = {@JoinColumn(name = "bank_account_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private Set<UserEntity> users = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_account_id", insertable = false, updatable = false)
    private Set<TransactionEntity> transactions = new HashSet<>();

    public void addUserToAccount(UserEntity users) {
        this.users.add(users);
        users.getAccounts().add(this);
    }

    public void removeUserFromAccount(UUID userId) {
        UserEntity user = this.users.stream().filter(t -> t.getId() == userId).findFirst().orElse(null);
        if (user != null) {
            this.users.remove(user);
            user.getAccounts().remove(this);
        }
    }

    @Transient
    public void delete() {
        this.deleted = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountEntity that = (AccountEntity) o;
        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
