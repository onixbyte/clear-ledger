package com.onixbyte.clearledger.data.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.onixbyte.clearledger.data.view.LedgerView;
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

    private LocalDateTime createdAt;

    public LedgerView toView() {
        return LedgerView.builder()
                .id(String.valueOf(getId()))
                .name(getName())
                .description(getDescription())
                .build();
    }

}
