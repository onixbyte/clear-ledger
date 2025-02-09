package com.onixbyte.clearledger.data.entity;

import com.mybatisflex.annotation.Table;

import java.time.LocalDateTime;

@Table("user_ledgers")
public class UserLedger {

    private Long userId;

    private Long ledgerId;

    private String role;

    private LocalDateTime joinedAt;

    public UserLedger() {
    }

    public UserLedger(Long userId, Long ledgerId, String role, LocalDateTime joinedAt) {
        this.userId = userId;
        this.ledgerId = ledgerId;
        this.role = role;
        this.joinedAt = joinedAt;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getLedgerId() {
        return ledgerId;
    }

    public void setLedgerId(Long ledgerId) {
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
        private Long userId;
        private Long ledgerId;
        private String role;
        private LocalDateTime joinedAt;

        private UserLedgerBuilder() {
        }

        public UserLedgerBuilder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public UserLedgerBuilder ledgerId(Long ledgerId) {
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
