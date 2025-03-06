package com.onixbyte.clearledger.data.entity;

import com.mybatisflex.annotation.Table;

import java.time.LocalDateTime;

@Table("user_ledgers")
public class UserLedger {

    private String userId;

    private String ledgerId;

    private String role;

    private LocalDateTime joinedAt;

    public UserLedger() {
    }

    public UserLedger(String userId, String ledgerId, String role, LocalDateTime joinedAt) {
        this.userId = userId;
        this.ledgerId = ledgerId;
        this.role = role;
        this.joinedAt = joinedAt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLedgerId() {
        return ledgerId;
    }

    public void setLedgerId(String ledgerId) {
        this.ledgerId = ledgerId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDateTime getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(LocalDateTime joinedAt) {
        this.joinedAt = joinedAt;
    }

    public static UserLedgerBuilder builder() {
        return new UserLedgerBuilder();
    }

    public static class UserLedgerBuilder {
        private String userId;
        private String ledgerId;
        private String role;
        private LocalDateTime joinedAt;

        private UserLedgerBuilder() {
        }

        public UserLedgerBuilder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public UserLedgerBuilder ledgerId(String ledgerId) {
            this.ledgerId = ledgerId;
            return this;
        }

        public UserLedgerBuilder role(String role) {
            this.role = role;
            return this;
        }

        public UserLedgerBuilder joinedAt(LocalDateTime joinedAt) {
            this.joinedAt = joinedAt;
            return this;
        }

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
