package com.onixbyte.clearledger.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "transactions")
public class Transaction {

    @Id
    private Long id;

    private Long ledgerId;

    private Long userId;

    private Integer amount;

    private String description;

    private LocalDateTime transactionDate;

    private LocalDateTime createdAt;

}
