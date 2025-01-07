package com.onixbyte.clearledger.controller;

import com.onixbyte.clearledger.data.request.CreateLedgerRequest;
import com.onixbyte.clearledger.data.entity.Ledger;
import com.onixbyte.clearledger.data.view.BizLedgerView;
import com.onixbyte.clearledger.data.view.LedgerView;
import com.onixbyte.clearledger.service.LedgerService;
import com.onixbyte.guid.GuidCreator;
import org.apache.ibatis.annotations.Delete;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * Ledger entrypoint.
 *
 * @author zihluwang
 */
@RestController
@RequestMapping("/ledgers")
public class LedgerController {

    private final GuidCreator<Long> ledgerIdCreator;
    private final LedgerService ledgerService;

    public LedgerController(GuidCreator<Long> ledgerIdCreator, LedgerService ledgerService) {
        this.ledgerIdCreator = ledgerIdCreator;
        this.ledgerService = ledgerService;
    }

    @PostMapping
    public LedgerView createLedger(@RequestBody CreateLedgerRequest request) {
        var ledger = Ledger.builder()
                .id(ledgerIdCreator.nextId())
                .name(request.name())
                .description(request.description())
                .createdAt(LocalDateTime.now())
                .build();

        return ledgerService.saveLedger(ledger).toView();
    }

    @PostMapping("/join/{ledgerId:\\d+}")
    public BizLedgerView joinLedger(@PathVariable Long ledgerId) {
        var bizLedger = ledgerService.joinLedger(ledgerId);
        return BizLedgerView.builder()
                .id(String.valueOf(bizLedger.id()))
                .name(bizLedger.name())
                .description(bizLedger.description())
                .role(bizLedger.role())
                .joinedAt(bizLedger.joinedAt())
                .build();
    }

    @DeleteMapping("/{ledgerId:\\d+}")
    public ResponseEntity<Void> deleteLedger(@PathVariable Long ledgerId) {
        ledgerService.deleteLedger(ledgerId);
        return ResponseEntity.noContent().build();
    }

}
