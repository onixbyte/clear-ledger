package com.onixbyte.clearledger.data.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.onixbyte.clearledger.data.view.LedgerView;

import java.time.LocalDateTime;

@Table("ledgers")
public class Ledger {

    @Id(keyType = KeyType.None)
    private Long id;

    private String name;

    private String description;

    private LocalDateTime createdAt;

    public LedgerView toView() {
        return LedgerView.builder()
                .id(String.valueOf(getId()))
                .name(getName())
                .description(getDescription())
                .build();
    }

    public Ledger() {
    }

    public Ledger(Long id, String name, String description, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public static LedgerBuilder builder() {
        return new LedgerBuilder();
    }

    public static class LedgerBuilder {
        private LedgerBuilder() {
        }

        private Long id;

        private String name;

        private String description;

        private LocalDateTime createdAt;

        public LedgerBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public LedgerBuilder name(String name) {
            this.name = name;
            return this;
        }

        public LedgerBuilder description(String description) {
            this.description = description;
            return this;
        }

        public LedgerBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Ledger build() {
            return new Ledger(id, name, description, createdAt);
        }
    }
}
