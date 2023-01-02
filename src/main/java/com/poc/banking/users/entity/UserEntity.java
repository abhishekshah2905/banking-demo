package com.poc.banking.users.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.poc.banking.accounts.entity.AccountEntity;
import com.poc.banking.audit.AbstractAuditingEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity(name = "users")
@Where(clause = "deleted=false")
public class UserEntity extends AbstractAuditingEntity {

    @NonNull
    @Column(unique = true)
    private String username;

    @NonNull
    private String password;

    private Boolean enabled = true;

    private String firstName;

    private String lastName;

    private boolean deleted;

    @Singular
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "users_authorities", joinColumns = {
            @JoinColumn(name = "users_id", referencedColumnName = "id")}, inverseJoinColumns = {
            @JoinColumn(name = "authorities_id", referencedColumnName = "id")})
    private Set<Authorities> authorities;


    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "users")
    @JsonIgnore
    @ToString.Exclude
    private Set<AccountEntity> accounts = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity user = (UserEntity) o;
        return getId().equals(user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
