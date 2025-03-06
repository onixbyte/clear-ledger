package com.onixbyte.clearledger.data.biz;

import java.time.LocalDateTime;

public record BizLedger(
        String id,
        String name,
        String description,
        String role,
        LocalDateTime joinedAt
) {

    public static BizLedgerBuilder builder() {
        return new BizLedgerBuilder();
    }

    public static class BizLedgerBuilder {
        private String id;
        private String name;
        private String description;
        private String role;
        private LocalDateTime joinedAt;

        public BizLedgerBuilder() {}

        public BizLedgerBuilder id(String id) {
            this.id = id;
            return this;
        }

        public BizLedgerBuilder name(String name) {
            this.name = name;
            return this;
        }

        public BizLedgerBuilder description(String description) {
            this.description = description;
            return this;
        }

        public BizLedgerBuilder role(String role) {
            this.role = role;
            return this;
        }

        public BizLedgerBuilder joinedAt(LocalDateTime joinedAt) {
            this.joinedAt = joinedAt;
            return this;
        }

        public BizLedger build() {
            return new BizLedger(id, name, description, role, joinedAt);
        }
    }
}

