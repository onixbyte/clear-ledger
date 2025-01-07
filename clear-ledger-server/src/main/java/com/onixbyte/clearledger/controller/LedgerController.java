package com.onixbyte.clearledger.controller;

import com.onixbyte.clearledger.data.request.CreateLedgerRequest;
import com.onixbyte.clearledger.data.entity.Ledger;
import com.onixbyte.clearledger.data.view.LedgerView;
import com.onixbyte.clearledger.service.LedgerService;
import com.onixbyte.guid.GuidCreator;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    /**
     * Create ledger.
     */
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

}
