package com.onixbyte.clearledger.service;

import com.onixbyte.clearledger.data.entity.Ledger;
import com.onixbyte.clearledger.data.entity.UserLedger;
import com.onixbyte.clearledger.data.entity.table.LedgerTableDef;
import com.onixbyte.clearledger.exception.BizException;
import com.onixbyte.clearledger.holder.UserHolder;
import com.onixbyte.clearledger.repository.LedgerRepository;
import com.onixbyte.clearledger.repository.UserLedgerRepository;
import com.onixbyte.guid.GuidCreator;
import org.springframework.http.HttpStatus;
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
     * @return the created ledger
     */
    @Transactional
    public Ledger saveLedger(Ledger ledger) {
        var currentUser = UserHolder.getCurrentUser();

        if (isNameTaken(ledger.getName())) {
            throw new BizException(HttpStatus.CONFLICT, "Ledger name is taken.");
        }

        ledgerRepository.insert(ledger);
        userLedgerRepository.insert(UserLedger.builder()
                .userId(currentUser.id())
                .ledgerId(ledger.getId())
                .role("owner")
                .joinedAt(LocalDateTime.now())
                .build());

        return ledger;
    }

    public boolean isNameTaken(String name) {
        return ledgerRepository.selectCountByCondition(LedgerTableDef.LEDGER.NAME.eq(name)) != 0;
    }

}
