package com.onixbyte.clearledger.data.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table("ledgers")
public class Ledger {

    @Id(keyType = KeyType.None)
    private Long id;

    private String name;

    private String description;

    private Long createdBy;

    private LocalDateTime createdAt;

}
