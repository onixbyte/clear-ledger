package com.onixbyte.clearledger.data.dto;

import java.time.LocalDateTime;

/**
 * A data transfer object representing a ledger with business-specific details.
 * <p>
 * This record encapsulates information about a ledger, including its identifier, name,
 * description, user role, and the date and time the user joined.
 *
 * @param id          the unique identifier of the ledger
 * @param name        the name of the ledger
 * @param description the description of the ledger
 * @param role        the user's role in the ledger
 * @param joinedAt    the date and time the user joined the ledger
 * @author zihluwang
 */
public record BizLedger(
        String id,
        String name,
        String description,
        String role,
        LocalDateTime joinedAt
) {

    /**
     * Creates a new builder for constructing a {@link BizLedger} instance.
     *
     * @return a {@link BizLedgerBuilder} instance
     */
    public static BizLedgerBuilder builder() {
        return new BizLedgerBuilder();
    }

    /**
     * Builder class for constructing {@link BizLedger} instances.
     * <p>
     * This class provides a fluent interface to set the properties of a {@link BizLedger} object,
     * allowing for flexible and readable construction.
     */
    public static class BizLedgerBuilder {
        private String id;
        private String name;
        private String description;
        private String role;
        private LocalDateTime joinedAt;

        /**
         * Constructs an empty builder.
         */
        public BizLedgerBuilder() {
        }

        /**
         * Sets the identifier of the ledger.
         *
         * @param id the unique identifier of the ledger
         * @return this builder instance
         */
        public BizLedgerBuilder id(String id) {
            this.id = id;
            return this;
        }

        /**
         * Sets the name of the ledger.
         *
         * @param name the name of the ledger
         * @return this builder instance
         */
        public BizLedgerBuilder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * Sets the description of the ledger.
         *
         * @param description the description of the ledger
         * @return this builder instance
         */
        public BizLedgerBuilder description(String description) {
            this.description = description;
            return this;
        }

        /**
         * Sets the user's role in the ledger.
         *
         * @param role the role of the user within the ledger
         * @return this builder instance
         */
        public BizLedgerBuilder role(String role) {
            this.role = role;
            return this;
        }

        /**
         * Sets the date and time the user joined the ledger.
         *
         * @param joinedAt the date and time of joining
         * @return this builder instance
         */
        public BizLedgerBuilder joinedAt(LocalDateTime joinedAt) {
            this.joinedAt = joinedAt;
            return this;
        }

        /**
         * Builds a {@link BizLedger} instance with the specified properties.
         *
         * @return a new {@link BizLedger} instance
         */
        public BizLedger build() {
            return new BizLedger(id, name, description, role, joinedAt);
        }
    }
}