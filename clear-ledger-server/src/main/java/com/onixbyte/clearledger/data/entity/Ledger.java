package com.onixbyte.clearledger.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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
@Entity(name = "ledgers")
public class Ledger {

    @Id
    private Long id;

    private String name;

    private String description;

    private Long createdBy;

    private LocalDateTime createdAt;

    @ManyToMany(mappedBy = "ledgers")
    private Set<User> users;

}
