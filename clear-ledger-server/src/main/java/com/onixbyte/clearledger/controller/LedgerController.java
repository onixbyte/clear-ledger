package com.onixbyte.clearledger.controller;

import com.onixbyte.clearledger.data.request.CreateLedgerRequest;
import com.onixbyte.clearledger.data.entity.Ledger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Ledger entrypoint.
 *
 * @author zihluwang
 */
@RestController
@RequestMapping("/ledgers")
public class LedgerController {

    /**
     * Create ledger.
     */
    @PostMapping
    public Ledger createLedger(@RequestBody CreateLedgerRequest request) {
        return null;
    }

}
