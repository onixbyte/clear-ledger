package com.onixbyte.clearledger.data.entity;

import com.mybatisflex.annotation.Table;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table("user_ledgers")
public class UserLedger {

    private Long userId;

    private Long ledgerId;

    private String role;

    private LocalDateTime joinedAt;

}
