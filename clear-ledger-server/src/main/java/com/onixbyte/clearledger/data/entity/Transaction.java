package com.onixbyte.clearledger.data.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.onixbyte.clearledger.data.response.TransactionResponse;

import java.time.LocalDateTime;

@Table("transactions")
public class Transaction {

    @Id(keyType = KeyType.None)
    private String id;

    private String ledgerId;

    private String userId;

    private Integer amount;

    private String description;

    private LocalDateTime transactionDate;

    private LocalDateTime createdAt;

    public Transaction() {
    }

    public Transaction(String id, String ledgerId, String userId, Integer amount, String description, LocalDateTime transactionDate, LocalDateTime createdAt) {
        this.id = id;
        this.ledgerId = ledgerId;
        this.userId = userId;
        this.amount = amount;
        this.description = description;
        this.transactionDate = transactionDate;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLedgerId() {
        return ledgerId;
    }

    public void setLedgerId(String ledgerId) {
        this.ledgerId = ledgerId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public static TransactionBuilder builder() {
        return new TransactionBuilder();
    }

    public static class TransactionBuilder {
        private String id;
        private String ledgerId;
        private String userId;
        private Integer amount;
        private String description;
        private LocalDateTime transactionDate;
        private LocalDateTime createdAt;

        private TransactionBuilder() {}

        public TransactionBuilder id(String id) {
            this.id = id;
            return this;
        }

        public TransactionBuilder ledgerId(String ledgerId) {
            this.ledgerId = ledgerId;
            return this;
        }

        public TransactionBuilder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public TransactionBuilder amount(Integer amount) {
            this.amount = amount;
            return this;
        }

        public TransactionBuilder description(String description) {
            this.description = description;
            return this;
        }

        public TransactionBuilder transactionDate(LocalDateTime transactionDate) {
            this.transactionDate = transactionDate;
            return this;
        }

        public TransactionBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Transaction build() {
            return new Transaction(id, ledgerId, userId, amount, description, transactionDate, createdAt);
        }
    }

    public TransactionResponse toResponse() {
        return TransactionResponse.builder()
                .id(String.valueOf(id))
                .ledgerId(String.valueOf(ledgerId))
                .userId(String.valueOf(userId))
                .amount(amount)
                .description(description)
                .transactionDate(transactionDate)
                .build();
    }
}
