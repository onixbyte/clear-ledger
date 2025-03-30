package com.onixbyte.clearledger.constant;

/**
 * Enumeration of identifier types used within the application.
 * <p>
 * This enum defines distinct types of identifiers for various entities, such as users, ledgers,
 * and transactions. Each type is associated with a unique code to facilitate identification and
 * categorisation within the system.
 *
 * @author zihluwang
 */
public enum IdType {

    /**
     * Identifier type for users, represented by the code "10".
     */
    USER("10"),

    /**
     * Identifier type for ledgers, represented by the code "20".
     */
    LEDGER("20"),

    /**
     * Identifier type for transactions, represented by the code "30".
     */
    TRANSACTION("30"),
    ;

    private final String code;

    /**
     * Constructs an identifier type with the specified code.
     *
     * @param code the unique code associated with this identifier type
     */
    IdType(String code) {
        this.code = code;
    }

    /**
     * Retrieves the code associated with this identifier type.
     *
     * @return the unique code for this type
     */
    public String getCode() {
        return code;
    }
}