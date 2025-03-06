package com.onixbyte.clearledger.constant;

public enum IdType {

    USER("10"),
    LEDGER("20"),
    TRANSACTION("30"),
    ;

    private final String code;

    IdType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
