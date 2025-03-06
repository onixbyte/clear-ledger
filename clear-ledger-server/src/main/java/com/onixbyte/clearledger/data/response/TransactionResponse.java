package com.onixbyte.clearledger.data.response;

import java.time.LocalDateTime;

public record TransactionResponse(
        String id,
        String ledgerId,
        String userId,
        String username,
        Integer amount,
        String description,
        LocalDateTime transactionDate
) {

    public static TransactionViewBuilder builder() {
        return new TransactionViewBuilder();
    }

    public static class TransactionViewBuilder {
        private String id;
        private String ledgerId;
        private String userId;
        private String username;
        private Integer amount;
        private String description;
        private LocalDateTime transactionDate;

        private TransactionViewBuilder() {
        }

        public TransactionViewBuilder id(String id) {
            this.id = id;
            return this;
        }

        public TransactionViewBuilder ledgerId(String ledgerId) {
            this.ledgerId = ledgerId;
            return this;
        }

        public TransactionViewBuilder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public TransactionViewBuilder username(String username) {
            this.username = username;
            return this;
        }

        public TransactionViewBuilder amount(Integer amount) {
            this.amount = amount;
            return this;
        }

        public TransactionViewBuilder description(String description) {
            this.description = description;
            return this;
        }

        public TransactionViewBuilder transactionDate(LocalDateTime transactionDate) {
            this.transactionDate = transactionDate;
            return this;
        }

        public TransactionResponse build() {
            return new TransactionResponse(id, ledgerId, userId, username, amount, description, transactionDate);
        }
    }

}
