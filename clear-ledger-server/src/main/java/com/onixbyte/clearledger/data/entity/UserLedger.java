package com.onixbyte.clearledger.data.entity;

import com.mybatisflex.annotation.Table;

import java.time.LocalDateTime;

/**
 * Entity class representing the relationship between a user and a ledger in the database.
 * <p>
 * This class maps to the "user_ledgers" table and defines the association between users and
 * ledgers, including the user’s role and join timestamp. It provides a builder pattern
 * for construction.
 *
 * @author zihluwang
 */
@Table("user_ledgers")
public class UserLedger {

    /**
     * The identifier of the associated user.
     */
    private String userId;

    /**
     * The identifier of the associated ledger.
     */
    private String ledgerId;

    /**
     * The role of the user in the ledger.
     */
    private String role;

    /**
     * The timestamp when the user joined the ledger.
     */
    private LocalDateTime joinedAt;

    /**
     * Default constructor required by MyBatis-Flex.
     */
    public UserLedger() {
    }

    /**
     * Constructs a user-ledger relationship with the specified properties.
     *
     * @param userId the user identifier
     * @param ledgerId the ledger identifier
     * @param role the user's role
     * @param joinedAt the join timestamp
     */
    public UserLedger(String userId, String ledgerId, String role, LocalDateTime joinedAt) {
        this.userId = userId;
        this.ledgerId = ledgerId;
        this.role = role;
        this.joinedAt = joinedAt;
    }

    /**
     * Retrieves the user identifier associated with the ledger.
     *
     * @return the identifier of the associated user
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the user identifier associated with the ledger.
     *
     * @param userId the user identifier to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Retrieves the ledger identifier associated with the user.
     *
     * @return the identifier of the associated ledger
     */
    public String getLedgerId() {
        return ledgerId;
    }

    /**
     * Sets the ledger identifier associated with the user.
     *
     * @param ledgerId the ledger identifier to set
     */
    public void setLedgerId(String ledgerId) {
        this.ledgerId = ledgerId;
    }

    /**
     * Retrieves the user's role in the ledger.
     *
     * @return the role of the user in the ledger
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets the user's role in the ledger.
     *
     * @param role the role to set for the user in the ledger
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Retrieves the timestamp when the user joined the ledger.
     *
     * @return the timestamp when the user joined the ledger
     */
    public LocalDateTime getJoinedAt() {
        return joinedAt;
    }

    /**
     * Sets the timestamp when the user joined the ledger.
     *
     * @param joinedAt the join timestamp to set
     */
    public void setJoinedAt(LocalDateTime joinedAt) {
        this.joinedAt = joinedAt;
    }

    /**
     * Creates a new builder instance for constructing a {@link UserLedger} object.
     * <p>
     * This static method provides a convenient entry point to the builder pattern, returning a new
     * {@link UserLedgerBuilder} instance. The builder can be used to fluently set the properties
     * of a {@link ViewTransaction} before constructing the final object.
     *
     * @return a new {@link UserLedgerBuilder} instance
     */
    public static UserLedgerBuilder builder() {
        return new UserLedgerBuilder();
    }

    /**
     * Builder class for constructing {@link UserLedger} instances.
     * <p>
     * This class provides a fluent interface to set the properties of a {@link UserLedger} object,
     * facilitating flexible and readable construction.
     */
    public static class UserLedgerBuilder {
        private String userId;
        private String ledgerId;
        private String role;
        private LocalDateTime joinedAt;

        private UserLedgerBuilder() {
        }

        /**
         * Sets the user identifier.
         *
         * @param userId the user identifier
         * @return this builder instance
         */
        public UserLedgerBuilder userId(String userId) {
            this.userId = userId;
            return this;
        }

        /**
         * Sets the ledger identifier.
         *
         * @param ledgerId the ledger identifier
         * @return this builder instance
         */
        public UserLedgerBuilder ledgerId(String ledgerId) {
            this.ledgerId = ledgerId;
            return this;
        }

        /**
         * Sets the user's role in the ledger.
         *
         * @param role the user's role
         * @return this builder instance
         */
        public UserLedgerBuilder role(String role) {
            this.role = role;
            return this;
        }

        /**
         * Sets the timestamp when the user joined the ledger.
         *
         * @param joinedAt the join timestamp
         * @return this builder instance
         */
        public UserLedgerBuilder joinedAt(LocalDateTime joinedAt) {
            this.joinedAt = joinedAt;
            return this;
        }

        /**
         * Builds a {@link UserLedger} instance with the specified properties.
         *
         * @return a new {@link UserLedger} instance
         */
        public UserLedger build() {
            UserLedger userLedger = new UserLedger();
            userLedger.userId = this.userId;
            userLedger.ledgerId = this.ledgerId;
            userLedger.role = this.role;
            userLedger.joinedAt = this.joinedAt;
            return userLedger;
        }
    }
}