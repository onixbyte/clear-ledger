package com.onixbyte.clearledger.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "users")
public class User {

    @Id
    private Long id;

    private String username;

    private String password;

    private String email;

    private LocalDateTime createdAt;

    @ManyToMany
    @JoinTable(
        name = "user_ledgers",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "ledger_id")
    )
    private Set<Ledger> ledgers;

}
