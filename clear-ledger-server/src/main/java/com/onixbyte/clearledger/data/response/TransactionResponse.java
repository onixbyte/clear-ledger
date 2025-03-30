package com.onixbyte.clearledger.data.response;

import java.time.LocalDateTime;

/**
 * A data transfer object for representing transaction responses.
 * <p>
 * This record encapsulates the response data for a transaction, including its identifier, ledger
 * and user associations, username, amount, description, and transaction date.
 *
 * @param id             the unique identifier of the transaction
 * @param ledgerId       the identifier of the associated ledger
 * @param userId         the identifier of the associated user
 * @param username       the username of the associated user
 * @param amount         the amount of the transaction
 * @param description    the description of the transaction
 * @param transactionDate the date and time the transaction occurred
 * @author zihluwang
 */
public record TransactionResponse(
        String id,
        String ledgerId,
        String userId,
        String username,
        Integer amount,
        String description,
        LocalDateTime transactionDate
) {

    /**
     * Creates a new builder instance for constructing a {@link TransactionResponse} object.
     * <p>
     * This static method provides an entry point to the builder pattern, returning a new
     * {@link TransactionViewBuilder} instance for fluently setting the properties of a transaction
     * response.
     *
     * @return a new {@link TransactionViewBuilder} instance
     */
    public static TransactionViewBuilder builder() {
        return new TransactionViewBuilder();
    }

    /**
     * Builder class for constructing {@link TransactionResponse} instances.
     * <p>
     * This class provides a fluent interface to set the properties of a {@link TransactionResponse}
     * object, ensuring flexible and readable construction.
     */
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

        /**
         * Sets the identifier of the transaction.
         *
         * @param id the unique identifier to set
         * @return this builder instance
         */
        public TransactionViewBuilder id(String id) {
            this.id = id;
            return this;
        }

        /**
         * Sets the ledger identifier of the transaction.
         *
         * @param ledgerId the ledger identifier to set
         * @return this builder instance
         */
        public TransactionViewBuilder ledgerId(String ledgerId) {
            this.ledgerId = ledgerId;
            return this;
        }

        /**
         * Sets the user identifier of the transaction.
         *
         * @param userId the user identifier to set
         * @return this builder instance
         */
        public TransactionViewBuilder userId(String userId) {
            this.userId = userId;
            return this;
        }

        /**
         * Sets the username of the transaction.
         *
         * @param username the username to set
         * @return this builder instance
         */
        public TransactionViewBuilder username(String username) {
            this.username = username;
            return this;
        }

        /**
         * Sets the amount of the transaction.
         *
         * @param amount the transaction amount to set
         * @return this builder instance
         */
        public TransactionViewBuilder amount(Integer amount) {
            this.amount = amount;
            return this;
        }

        /**
         * Sets the description of the transaction.
         *
         * @param description the description to set
         * @return this builder instance
         */
        public TransactionViewBuilder description(String description) {
            this.description = description;
            return this;
        }

        /**
         * Sets the date and time of the transaction.
         *
         * @param transactionDate the transaction date and time to set
         * @return this builder instance
         */
        public TransactionViewBuilder transactionDate(LocalDateTime transactionDate) {
            this.transactionDate = transactionDate;
            return this;
        }

        /**
         * Builds a {@link TransactionResponse} instance with the specified properties.
         *
         * @return a new {@link TransactionResponse} instance
         */
        public TransactionResponse build() {
            return new TransactionResponse(id, ledgerId, userId, username, amount, description, transactionDate);
        }
    }
}