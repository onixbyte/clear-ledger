package com.onixbyte.clearledger.data.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.onixbyte.clearledger.data.response.TransactionResponse;

import java.time.LocalDateTime;

/**
 * Entity class representing a view of a transaction in the database.
 * <p>
 * This class maps to the "view_transactions" table and defines a comprehensive view of a
 * transaction, including its identifier, ledger and user associations, username, amount,
 * description, and timestamps. It provides a builder pattern for construction and a method to
 * convert to a {@link TransactionResponse}.
 *
 * @author zihluwang
 * @author siujamo
 */
@Table("view_transactions")
public class ViewTransaction {

    /**
     * The unique identifier of the transaction.
     */
    @Id(keyType = KeyType.None)
    private String id;

    /**
     * The identifier of the associated ledger.
     */
    private String ledgerId;

    /**
     * The identifier of the associated user.
     */
    private String userId;

    /**
     * The username of the associated user.
     */
    private String username;

    /**
     * The amount of the transaction.
     */
    private Integer amount;

    /**
     * The description of the transaction.
     */
    private String description;

    /**
     * The date and time the transaction occurred.
     */
    private LocalDateTime transactionDate;

    /**
     * The timestamp when the transaction was created.
     */
    private LocalDateTime createdAt;

    /**
     * Default constructor required by MyBatis-Flex.
     */
    public ViewTransaction() {
    }

    /**
     * Constructs a view transaction with the specified properties.
     *
     * @param id the unique identifier
     * @param ledgerId the ledger identifier
     * @param userId the user identifier
     * @param username the username
     * @param amount the transaction amount
     * @param description the transaction description
     * @param transactionDate the transaction date and time
     * @param createdAt the creation timestamp
     */
    public ViewTransaction(String id,
                           String ledgerId,
                           String userId,
                           String username,
                           Integer amount,
                           String description,
                           LocalDateTime transactionDate,
                           LocalDateTime createdAt) {
        this.id = id;
        this.ledgerId = ledgerId;
        this.userId = userId;
        this.username = username;
        this.amount = amount;
        this.description = description;
        this.transactionDate = transactionDate;
        this.createdAt = createdAt;
    }

    /**
     * Retrieves the transaction's identifier.
     *
     * @return the unique identifier of the transaction
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the transaction's identifier.
     *
     * @param id the unique identifier to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Retrieves the ledger identifier associated with the transaction.
     *
     * @return the identifier of the associated ledger
     */
    public String getLedgerId() {
        return ledgerId;
    }

    /**
     * Sets the ledger identifier associated with the transaction.
     *
     * @param ledgerId the ledger identifier to set
     */
    public void setLedgerId(String ledgerId) {
        this.ledgerId = ledgerId;
    }

    /**
     * Retrieves the user identifier associated with the transaction.
     *
     * @return the identifier of the associated user
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the user identifier associated with the transaction.
     *
     * @param userId the user identifier to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Retrieves the username associated with the transaction.
     *
     * @return the username of the associated user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username associated with the transaction.
     *
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Retrieves the amount of the transaction.
     *
     * @return the transaction amount
     */
    public Integer getAmount() {
        return amount;
    }

    /**
     * Sets the amount of the transaction.
     *
     * @param amount the transaction amount to set
     */
    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    /**
     * Retrieves the description of the transaction.
     *
     * @return the description of the transaction
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the transaction.
     *
     * @param description the description to set for the transaction
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Retrieves the date and time the transaction occurred.
     *
     * @return the transaction date and time
     */
    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    /**
     * Sets the date and time the transaction occurred.
     *
     * @param transactionDate the transaction date and time to set
     */
    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    /**
     * Retrieves the creation timestamp of the transaction.
     *
     * @return the timestamp when the transaction was created
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the creation timestamp of the transaction.
     *
     * @param createdAt the timestamp to set for when the transaction was created
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Creates a new builder instance for constructing a {@link ViewTransaction} object.
     * <p>
     * This static method provides a convenient entry point to the builder pattern, returning a new
     * {@link ViewTransactionBuilder} instance. The builder can be used to fluently set the
     * properties of a {@link ViewTransaction} before constructing the final object.
     *
     * @return a new {@link ViewTransactionBuilder} instance
     */
    public static ViewTransactionBuilder builder() {
        return new ViewTransactionBuilder();
    }

    /**
     * Builder class for constructing {@link ViewTransaction} instances.
     * <p>
     * This class provides a fluent interface to set the properties of a {@link ViewTransaction}
     * object, facilitating flexible and readable construction.
     */
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

        /**
         * Sets the identifier of the transaction.
         *
         * @param id the unique identifier
         * @return this builder instance
         */
        public ViewTransactionBuilder id(String id) {
            this.id = id;
            return this;
        }

        /**
         * Sets the ledger identifier of the transaction.
         *
         * @param ledgerId the ledger identifier
         * @return this builder instance
         */
        public ViewTransactionBuilder ledgerId(String ledgerId) {
            this.ledgerId = ledgerId;
            return this;
        }

        /**
         * Sets the user identifier of the transaction.
         *
         * @param userId the user identifier
         * @return this builder instance
         */
        public ViewTransactionBuilder userId(String userId) {
            this.userId = userId;
            return this;
        }

        /**
         * Sets the username of the transaction.
         *
         * @param username the username
         * @return this builder instance
         */
        public ViewTransactionBuilder username(String username) {
            this.username = username;
            return this;
        }

        /**
         * Sets the amount of the transaction.
         *
         * @param amount the transaction amount
         * @return this builder instance
         */
        public ViewTransactionBuilder amount(Integer amount) {
            this.amount = amount;
            return this;
        }

        /**
         * Sets the description of the transaction.
         *
         * @param description the transaction description
         * @return this builder instance
         */
        public ViewTransactionBuilder description(String description) {
            this.description = description;
            return this;
        }

        /**
         * Sets the date and time of the transaction.
         *
         * @param transactionDate the transaction date and time
         * @return this builder instance
         */
        public ViewTransactionBuilder transactionDate(LocalDateTime transactionDate) {
            this.transactionDate = transactionDate;
            return this;
        }

        /**
         * Sets the creation timestamp of the transaction.
         *
         * @param createdAt the creation timestamp
         * @return this builder instance
         */
        public ViewTransactionBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        /**
         * Builds a {@link ViewTransaction} instance with the specified properties.
         *
         * @return a new {@link ViewTransaction} instance
         */
        public ViewTransaction build() {
            return new ViewTransaction(id, ledgerId, userId, username, amount, description, transactionDate, createdAt);
        }
    }

    /**
     * Converts this {@link ViewTransaction} to a {@link TransactionResponse}.
     * <p>
     * This method transforms the view transaction entity into a response object suitable for external communication.
     *
     * @return a {@link TransactionResponse} instance
     */
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