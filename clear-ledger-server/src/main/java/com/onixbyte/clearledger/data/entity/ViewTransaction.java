package com.onixbyte.clearledger.data.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.onixbyte.clearledger.data.response.TransactionResponse;

import java.time.LocalDateTime;

@Table("view_transactions")
public class ViewTransaction {

    @Id(keyType = KeyType.None)
    private String id;

    private String ledgerId;

    private String userId;

    private String username;

    private Integer amount;

    private String description;

    private LocalDateTime transactionDate;

    private LocalDateTime createdAt;

    public ViewTransaction() {
    }

    public ViewTransaction(String id, String ledgerId, String userId, String username, Integer amount, String description, LocalDateTime transactionDate, LocalDateTime createdAt) {
        this.id = id;
        this.ledgerId = ledgerId;
        this.userId = userId;
        this.username = username;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public static ViewTransactionBuilder builder() {
        return new ViewTransactionBuilder();
    }

    public static class ViewTransactionBuilder {
        private String id;
        private String ledgerId;
        private String userId;
        private String username;
        private Integer amount;
        private String description;
        private LocalDateTime transactionDate;
        private LocalDateTime createdAt;

        private ViewTransactionBuilder() {
        }

        public ViewTransactionBuilder id(String id) {
            this.id = id;
            return this;
        }

        public ViewTransactionBuilder ledgerId(String ledgerId) {
            this.ledgerId = ledgerId;
            return this;
        }

        public ViewTransactionBuilder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public ViewTransactionBuilder username(String username) {
            this.username = username;
            return this;
        }

        public ViewTransactionBuilder amount(Integer amount) {
            this.amount = amount;
            return this;
        }

        public ViewTransactionBuilder description(String description) {
            this.description = description;
            return this;
        }

        public ViewTransactionBuilder transactionDate(LocalDateTime transactionDate) {
            this.transactionDate = transactionDate;
            return this;
        }

        public ViewTransactionBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public ViewTransaction build() {
            return new ViewTransaction(id, ledgerId, userId, username, amount, description, transactionDate, createdAt);
        }
    }

    public TransactionResponse toResponse() {
        return TransactionResponse.builder()
                .id(String.valueOf(id))
                .ledgerId(String.valueOf(ledgerId))
                .userId(String.valueOf(userId))
                .username(username)
                .amount(amount)
                .description(description)
                .transactionDate(transactionDate)
                .build();
    }

}
