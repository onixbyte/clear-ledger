package com.onixbyte.clearledger.data.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.onixbyte.clearledger.data.dto.BizLedger;

import java.time.LocalDateTime;

/**
 * Entity class representing a ledger in the database.
 * <p>
 * This class maps to the "ledgers" table and defines the structure of a ledger, including its
 * identifier, name, description, and creation timestamp. It provides a builder pattern for
 * construction and a method to convert to a {@link BizLedger} DTO.
 *
 * @author zihluwang
 */
@Table("ledgers")
public class Ledger {

    /**
     * The unique identifier of the ledger.
     */
    @Id(keyType = KeyType.None)
    private String id;

    /**
     * The name of the ledger.
     */
    private String name;

    /**
     * The description of the ledger.
     */
    private String description;

    /**
     * The timestamp when the ledger was created.
     */
    private LocalDateTime createdAt;

    /**
     * Default constructor required by MyBatis-Flex.
     */
    public Ledger() {
    }

    /**
     * Constructs a ledger with the specified properties.
     *
     * @param id the unique identifier
     * @param name the name of the ledger
     * @param description the description of the ledger
     * @param createdAt the creation timestamp
     */
    public Ledger(String id, String name, String description, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
    }

    /**
     * Retrieves the ledger's identifier.
     *
     * @return the ledger ID
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the ledger's identifier.
     *
     * @param id the ledger ID
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Retrieves the ledger's name.
     *
     * @return the ledger name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the ledger's name.
     *
     * @param name the ledger name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the ledger's description.
     *
     * @return the ledger description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the ledger's description.
     *
     * @param description the ledger description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Retrieves the creation timestamp of the ledger.
     *
     * @return the creation timestamp
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the creation timestamp of the ledger.
     *
     * @param createdAt the creation timestamp
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Creates a new builder for constructing a {@link Ledger} instance.
     *
     * @return a {@link LedgerBuilder} instance
     */
    public static LedgerBuilder builder() {
        return new LedgerBuilder();
    }

    /**
     * Builder class for constructing {@link Ledger} instances.
     * <p>
     * This class provides a fluent interface to set the properties of a {@link Ledger} object,
     * facilitating flexible and readable construction.
     */
    public static class LedgerBuilder {
        private LedgerBuilder() {
        }

        private String id;
        private String name;
        private String description;
        private LocalDateTime createdAt;

        /**
         * Sets the identifier of the ledger.
         *
         * @param id the unique identifier
         * @return this builder instance
         */
        public LedgerBuilder id(String id) {
            this.id = id;
            return this;
        }

        /**
         * Sets the name of the ledger.
         *
         * @param name the name of the ledger
         * @return this builder instance
         */
        public LedgerBuilder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * Sets the description of the ledger.
         *
         * @param description the description of the ledger
         * @return this builder instance
         */
        public LedgerBuilder description(String description) {
            this.description = description;
            return this;
        }

        /**
         * Sets the creation timestamp of the ledger.
         *
         * @param createdAt the creation timestamp
         * @return this builder instance
         */
        public LedgerBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        /**
         * Builds a {@link Ledger} instance with the specified properties.
         *
         * @return a new {@link Ledger} instance
         */
        public Ledger build() {
            return new Ledger(id, name, description, createdAt);
        }
    }

    /**
     * Converts this {@link Ledger} to a {@link BizLedger} DTO.
     * <p>
     * This method transforms the ledger entity into a business-specific DTO, incorporating the user's
     * role and join timestamp.
     *
     * @param role the user's role in the ledger
     * @param joinedAt the timestamp when the user joined the ledger
     * @return a {@link BizLedger} instance
     */
    public BizLedger toBiz(String role, LocalDateTime joinedAt) {
        return BizLedger.builder()
                .id(String.valueOf(id))
                .name(name)
                .description(description)
                .role(role)
                .joinedAt(joinedAt)
                .build();
    }
}