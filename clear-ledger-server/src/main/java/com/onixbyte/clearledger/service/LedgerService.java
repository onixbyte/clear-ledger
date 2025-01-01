package com.onixbyte.clearledger.service;

import com.onixbyte.clearledger.data.entity.Ledger;
import com.onixbyte.clearledger.data.entity.UserLedger;
import com.onixbyte.clearledger.holder.UserHolder;
import com.onixbyte.clearledger.repository.LedgerRepository;
import com.onixbyte.clearledger.repository.UserLedgerRepository;
import com.onixbyte.guid.GuidCreator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

/**
 * Service class for managing ledgers.
 *
 * @author zihluwang
 */
@Service
public class LedgerService {

    private final GuidCreator<Long> ledgerIdCreator;
    private final LedgerRepository ledgerRepository;
    private final UserLedgerRepository userLedgerRepository;

    public LedgerService(GuidCreator<Long> ledgerIdCreator, LedgerRepository ledgerRepository, UserLedgerRepository userLedgerRepository) {
        this.ledgerIdCreator = ledgerIdCreator;
        this.ledgerRepository = ledgerRepository;
        this.userLedgerRepository = userLedgerRepository;
    }

    /**
     * Create a ledger.
     *
     * @param name        the name of the ledger
     * @param description the description of the ledger
     * @return the created ledger
     */
    @Transactional
    public Ledger createLedger(String name, String description) {
        var currentUser = UserHolder.getCurrentUser();

        var ledger = Ledger.builder()
                .id(ledgerIdCreator.nextId()) // set ledger id
                .name(name) // set ledger name
                .description(description) // set ledger description
                .createdAt(LocalDateTime.now())
                .createdBy(1L)
                .build();

        CompletableFuture.runAsync(() -> ledgerRepository.insert(ledger));
        CompletableFuture.runAsync(() -> userLedgerRepository.insert(UserLedger.builder()
                .userId(currentUser.userId())
                .ledgerId(ledger.getId())
                .role("owner")
                .joinedAt(LocalDateTime.now())
                .build()));

        return ledger;
    }

}
