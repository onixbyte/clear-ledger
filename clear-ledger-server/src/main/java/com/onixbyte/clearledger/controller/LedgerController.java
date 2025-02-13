package com.onixbyte.clearledger.controller;

import com.onixbyte.clearledger.data.request.CreateLedgerRequest;
import com.onixbyte.clearledger.data.entity.Ledger;
import com.onixbyte.clearledger.data.request.UpdateLedgerRequest;
import com.onixbyte.clearledger.data.view.BizLedgerView;
import com.onixbyte.clearledger.data.view.LedgerView;
import com.onixbyte.clearledger.exception.BizException;
import com.onixbyte.clearledger.service.LedgerService;
import com.onixbyte.guid.GuidCreator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

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

    @PatchMapping
    public ResponseEntity<Void> updateLedger(@RequestBody UpdateLedgerRequest request) {
        if (Objects.isNull(request.id())) {
            throw new BizException(HttpStatus.BAD_REQUEST, "Missing parameter id.");
        }

        var ledger = Ledger.builder()
                .id(request.id())
                .name(request.name())
                .description(request.description())
                .build();

        ledgerService.updateLedger(ledger);
        return ResponseEntity.accepted().build();
    }

    /**
     * Get ledgers.
     *
     * @param ledgerType ledger type flag, {@code 1} represents joined ledgers, {@code 2} represents
     *                   ledgers could be joined
     * @return a ledger list
     */
    @GetMapping
    public List<BizLedgerView> getLedgers(@RequestParam(required = false) Integer ledgerType) {
        if (Objects.isNull(ledgerType) || ledgerType == 1) {
            return ledgerService.getJoinedLedgers()
                    .stream()
                    .map((ledger) -> BizLedgerView.builder()
                            .id(String.valueOf(ledger.id()))
                            .name(ledger.name())
                            .description(ledger.description())
                            .role(ledger.role())
                            .joinedAt(ledger.joinedAt())
                            .build())
                    .toList();
        } else {
            return ledgerService.getLedgersCanJoin()
                    .stream()
                    .map((ledger) -> BizLedgerView.builder()
                            .id(String.valueOf(ledger.id()))
                            .name(ledger.name())
                            .description(ledger.description())
                            .role(ledger.role())
                            .joinedAt(ledger.joinedAt())
                            .build())
                    .toList();
        }
    }

    @DeleteMapping("/exit/{ledgerId:\\d+}")
    public ResponseEntity<Void> exitLedger(@PathVariable Long ledgerId) {
        ledgerService.exitLedger(ledgerId);
        return ResponseEntity.noContent().build();
    }

}
