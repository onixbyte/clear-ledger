package com.onixbyte.clearledger.data.view;

import java.time.LocalDateTime;

public record LedgerView(
        String id,
        String name,
        String description
) {

    public static LedgerViewBuilder builder() {
        return new LedgerViewBuilder();
    }

    public static class LedgerViewBuilder {
        private String id;
        private String name;
        private String description;

        private LedgerViewBuilder() {
        }

        public LedgerViewBuilder id(String id) {
            this.id = id;
            return this;
        }

        public LedgerViewBuilder name(String name) {
            this.name = name;
            return this;
        }

        public LedgerViewBuilder description(String description) {
            this.description = description;
            return this;
        }

        public LedgerView build() {
            return new LedgerView(id, name, description);
        }
    }

}
