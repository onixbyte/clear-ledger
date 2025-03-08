package com.onixbyte.clearledger.controller;

import com.onixbyte.clearledger.data.dto.BizLedger;
import com.onixbyte.clearledger.data.request.CreateLedgerRequest;
import com.onixbyte.clearledger.data.entity.Ledger;
import com.onixbyte.clearledger.data.request.UpdateLedgerRequest;
import com.onixbyte.clearledger.exception.BizException;
import com.onixbyte.clearledger.service.LedgerService;
import com.onixbyte.guid.GuidCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(LedgerController.class);

    private final GuidCreator<String> ledgerIdCreator;
    private final LedgerService ledgerService;

    public LedgerController(GuidCreator<String> ledgerIdCreator,
                            LedgerService ledgerService) {
        this.ledgerIdCreator = ledgerIdCreator;
        this.ledgerService = ledgerService;
    }

    @PostMapping
    public BizLedger createLedger(@RequestBody CreateLedgerRequest request) {
        var ledger = Ledger.builder()
                .id(ledgerIdCreator.nextId())
                .name(request.name())
                .description(request.description())
                .createdAt(LocalDateTime.now())
                .build();

        return ledgerService.saveLedger(ledger);
    }

    @PostMapping("/join/{ledgerId:\\d+}")
    public BizLedger joinLedger(@PathVariable String ledgerId) {
        return ledgerService.joinLedger(ledgerId);
    }

    @DeleteMapping("/{ledgerId:\\d+}")
    public ResponseEntity<Void> deleteLedger(@PathVariable String ledgerId) {
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
     * Get joined ledgers.
     *
     * @return a ledger list
     */
    @GetMapping
    public List<BizLedger> getLedgers() {
        return ledgerService.getJoinedLedgers();
    }

    @DeleteMapping("/exit/{ledgerId:\\d+}")
    public ResponseEntity<Void> exitLedger(@PathVariable Long ledgerId) {
        ledgerService.exitLedger(ledgerId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/export/{ledgerId}")
    public ResponseEntity<Void> exportLedger(@PathVariable String ledgerId) {
        return ResponseEntity.noContent().build();
    }

}
