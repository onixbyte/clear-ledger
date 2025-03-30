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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Controller for managing ledger operations.
 * <p>
 * This class provides REST endpoints for creating, updating, deleting, and querying ledgers within
 * the application. It serves as the entry point for ledger-related actions and integrates with
 * {@link LedgerService} for business logic.
 *
 * @author zihluwang
 * @author siujamo
 */
@RestController
@RequestMapping("/ledgers")
public class LedgerController {

    private static final Logger log = LoggerFactory.getLogger(LedgerController.class);

    private final GuidCreator<String> ledgerIdCreator;
    private final LedgerService ledgerService;

    /**
     * Constructs a ledger controller with required dependencies.
     *
     * @param ledgerIdCreator the creator for generating unique ledger identifiers
     * @param ledgerService the service handling ledger operations
     */
    @Autowired
    public LedgerController(GuidCreator<String> ledgerIdCreator,
                            LedgerService ledgerService) {
        this.ledgerIdCreator = ledgerIdCreator;
        this.ledgerService = ledgerService;
    }

    /**
     * Creates a new ledger.
     * <p>
     * This endpoint creates a ledger based on the provided request, assigning it a unique ID and
     * saving it via the {@link LedgerService}.
     *
     * @param request the request containing the ledger's name and description
     * @return the created {@link BizLedger} object
     */
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

    /**
     * Allows a user to join an existing ledger.
     * <p>
     * This endpoint enables a user to join the specified ledger by its ID.
     *
     * @param ledgerId the ID of the ledger to join
     * @return the joined {@link BizLedger} object
     */
    @PostMapping("/join/{ledgerId:\\d+}")
    public BizLedger joinLedger(@PathVariable String ledgerId) {
        return ledgerService.joinLedger(ledgerId);
    }

    /**
     * Deletes a ledger by its ID.
     * <p>
     * This endpoint removes the specified ledger from the system.
     *
     * @param ledgerId the ID of the ledger to delete
     * @return a {@link ResponseEntity} with no content (HTTP 204) upon successful deletion
     */
    @DeleteMapping("/{ledgerId:\\d+}")
    public ResponseEntity<Void> deleteLedger(@PathVariable String ledgerId) {
        ledgerService.deleteLedger(ledgerId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Updates an existing ledger.
     * <p>
     * This endpoint updates the specified ledger's name and description based on the provided request.
     *
     * @param request the request containing the ledger's ID, name, and description
     * @return a {@link ResponseEntity} with HTTP 202 (Accepted) upon successful update
     * @throws BizException if the ledger ID is missing
     */
    @PatchMapping
    public ResponseEntity<Void> updateLedger(@RequestBody UpdateLedgerRequest request) {
        if (Objects.isNull(request.id())) {
            throw BizException.badRequest("Missing parameter id.");
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
     * Retrieves a list of joined ledgers.
     * <p>
     * This endpoint returns a list of ledgers the user has joined.
     *
     * @return a list of {@link BizLedger} objects representing joined ledgers
     */
    @GetMapping
    public List<BizLedger> getLedgers() {
        return ledgerService.getJoinedLedgers();
    }

    /**
     * Allows a user to exit a ledger.
     * <p>
     * This endpoint removes the user from the specified ledger.
     *
     * @param ledgerId the ID of the ledger to exit
     * @return a {@link ResponseEntity} with no content (HTTP 204) upon successful exit
     */
    @DeleteMapping("/exit/{ledgerId:\\d+}")
    public ResponseEntity<Void> exitLedger(@PathVariable Long ledgerId) {
        ledgerService.exitLedger(ledgerId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Exports a ledger (placeholder implementation).
     * <p>
     * This endpoint is intended to export the specified ledger's data, currently returning no content.
     *
     * @param ledgerId the ID of the ledger to export
     * @return a {@link ResponseEntity} with no content (HTTP 204)
     */
    @GetMapping("/export/{ledgerId}")
    public ResponseEntity<Void> exportLedger(@PathVariable String ledgerId) {
        return ResponseEntity.noContent().build();
    }
}