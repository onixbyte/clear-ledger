package com.onixbyte.clearledger.data.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.onixbyte.clearledger.data.response.TransactionResponse;

import java.time.LocalDateTime;

/**
 * Entity class representing a transaction in the database.
 * <p>
 * This class maps to the "transactions" table and defines the structure of a transaction, including
 * its identifier, ledger and user associations, amount, description, and timestamps. It provides a
 * builder pattern for construction and a method to convert to a {@link ViewTransaction}.
 *
 * @author zihluwang
 */
@Table("transactions")
public class Transaction {

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
    public Transaction() {
    }

    /**
     * Constructs a transaction with the specified properties.
     *
     * @param id              the unique identifier
     * @param ledgerId        the ledger identifier
     * @param userId          the user identifier
     * @param amount          the transaction amount
     * @param description     the transaction description
     * @param transactionDate the transaction date and time
     * @param createdAt       the creation timestamp
     */
    public Transaction(String id, String ledgerId, String userId, Integer amount, String description, LocalDateTime transactionDate, LocalDateTime createdAt) {
        this.id = id;
        this.ledgerId = ledgerId;
        this.userId = userId;
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
     * Creates a new builder for constructing a {@link Transaction} instance.
     *
     * @return a {@link TransactionBuilder} instance
     */
    public static TransactionBuilder builder() {
        return new TransactionBuilder();
    }

    /**
     * Builder class for constructing {@link Transaction} instances.
     * <p>
     * This class provides a fluent interface to set the properties of a {@link Transaction} object,
     * facilitating flexible and readable construction.
     */
    public static class TransactionBuilder {
        private String id;
        private String ledgerId;
        private String userId;
        private Integer amount;
        private String description;
        private LocalDateTime transactionDate;
        private LocalDateTime createdAt;

        private TransactionBuilder() {
        }

        /**
         * Sets the identifier of the transaction.
         *
         * @param id the unique identifier
         * @return this builder instance
         */
        public TransactionBuilder id(String id) {
            this.id = id;
            return this;
        }

        /**
         * Sets the ledger identifier of the transaction.
         *
         * @param ledgerId the ledger identifier
         * @return this builder instance
         */
        public TransactionBuilder ledgerId(String ledgerId) {
            this.ledgerId = ledgerId;
            return this;
        }

        /**
         * Sets the user identifier of the transaction.
         *
         * @param userId the user identifier
         * @return this builder instance
         */
        public TransactionBuilder userId(String userId) {
            this.userId = userId;
            return this;
        }

        /**
         * Sets the amount of the transaction.
         *
         * @param amount the transaction amount
         * @return this builder instance
         */
        public TransactionBuilder amount(Integer amount) {
            this.amount = amount;
            return this;
        }

        /**
         * Sets the description of the transaction.
         *
         * @param description the transaction description
         * @return this builder instance
         */
        public TransactionBuilder description(String description) {
            this.description = description;
            return this;
        }

        /**
         * Sets the date and time of the transaction.
         *
         * @param transactionDate the transaction date and time
         * @return this builder instance
         */
        public TransactionBuilder transactionDate(LocalDateTime transactionDate) {
            this.transactionDate = transactionDate;
            return this;
        }

        /**
         * Sets the creation timestamp of the transaction.
         *
         * @param createdAt the creation timestamp
         * @return this builder instance
         */
        public TransactionBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        /**
         * Builds a {@link Transaction} instance with the specified properties.
         *
         * @return a new {@link Transaction} instance
         */
        public Transaction build() {
            return new Transaction(id, ledgerId, userId, amount, description, transactionDate, createdAt);
        }
    }

    /**
     * Converts this {@link Transaction} instance into a {@link ViewTransaction} object.
     * <p>
     * This method transforms the current transaction entity into a view-specific representation by
     * incorporating the provided username and mapping all relevant fields. The resulting
     * {@link ViewTransaction} includes additional details suitable for display or reporting
     * purposes, constructed using the builder pattern.
     *
     * @param username the username associated with the user ID, to be included in the view
     * @return a new {@link ViewTransaction} instance containing the transaction details
     * and username
     */
    public ViewTransaction toView(String username) {
        return ViewTransaction.builder()
                .id(id)
                .userId(userId)
                .username(username)
                .ledgerId(ledgerId)
                .amount(amount)
                .description(description)
                .transactionDate(transactionDate)
                .createdAt(createdAt)
                .build();
    }
}
