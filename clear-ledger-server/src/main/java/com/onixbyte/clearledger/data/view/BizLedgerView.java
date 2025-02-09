package com.onixbyte.clearledger.data.view;

import java.time.LocalDateTime;

public record BizLedgerView(
        String id,
        String name,
        String description,
        String role,
        LocalDateTime joinedAt
) {

    public static BizLedgerViewBuilder builder() {
        return new BizLedgerViewBuilder();
    }

    public static class BizLedgerViewBuilder {
        private String id;
        private String name;
        private String description;
        private String role;
        private LocalDateTime joinedAt;

        private BizLedgerViewBuilder() {
        }

        public BizLedgerViewBuilder id(String id) {
            this.id = id;
            return this;
        }

        public BizLedgerViewBuilder name(String name) {
            this.name = name;
            return this;
        }

        public BizLedgerViewBuilder description(String description) {
            this.description = description;
            return this;
        }

        public BizLedgerViewBuilder role(String role) {
            this.role = role;
            return this;
        }

        public BizLedgerViewBuilder joinedAt(LocalDateTime joinedAt) {
            this.joinedAt = joinedAt;
            return this;
        }

        public BizLedgerView build() {
            return new BizLedgerView(id, name, description, role, joinedAt);
        }
    }

}
