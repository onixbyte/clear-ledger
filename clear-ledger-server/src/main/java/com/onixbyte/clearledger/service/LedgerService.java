package com.onixbyte.clearledger.service;

import com.onixbyte.clearledger.data.biz.BizLedger;
import com.onixbyte.clearledger.data.entity.Ledger;
import com.onixbyte.clearledger.data.entity.UserLedger;
import com.onixbyte.clearledger.data.entity.table.LedgerTableDef;
import com.onixbyte.clearledger.data.entity.table.TransactionTableDef;
import com.onixbyte.clearledger.data.entity.table.UserLedgerTableDef;
import com.onixbyte.clearledger.exception.BizException;
import com.onixbyte.clearledger.holder.UserHolder;
import com.onixbyte.clearledger.repository.LedgerRepository;
import com.onixbyte.clearledger.repository.TransactionRepository;
import com.onixbyte.clearledger.repository.UserLedgerRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Service class for managing ledgers.
 *
 * @author zihluwang
 */
@Service
public class LedgerService {

    private final LedgerRepository ledgerRepository;
    private final UserLedgerRepository userLedgerRepository;
    private final TransactionRepository transactionRepository;

    public LedgerService(LedgerRepository ledgerRepository,
                         UserLedgerRepository userLedgerRepository,
                         TransactionRepository transactionRepository) {
        this.ledgerRepository = ledgerRepository;
        this.userLedgerRepository = userLedgerRepository;
        this.transactionRepository = transactionRepository;
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

        if (!canCreateOrJoinLedger(currentUser.id())) {
            throw new BizException(HttpStatus.CONFLICT, "You can only join at most 3 ledgers.");
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

    @Transactional
    public BizLedger joinLedger(Long ledgerId) {
        var currentUser = UserHolder.getCurrentUser();

        // check whether the ledger exists
        if (!hasLedger(ledgerId)) {
            throw new BizException(HttpStatus.NOT_FOUND, "No ledger with given ledger id.");
        }

        // validate user can create or join a ledger
        if (!canCreateOrJoinLedger(currentUser.id())) {
            throw new BizException(HttpStatus.CONFLICT, "You can only join at most 3 ledgers.");
        }

        // validate whether user is already join this ledger
        if (isLedgerJoined(ledgerId)) {
            throw new BizException(HttpStatus.CONFLICT, "You have already joined this ledger.");
        }

        var joinedAt = LocalDateTime.now();
        userLedgerRepository.insert(UserLedger.builder()
                .userId(currentUser.id())
                .ledgerId(ledgerId)
                .role("member")
                .joinedAt(joinedAt)
                .build());

        var ledger = ledgerRepository.selectOneByCondition(LedgerTableDef.LEDGER.ID.eq(ledgerId));
        return BizLedger.builder()
                .id(ledgerId)
                .name(ledger.getName())
                .description(ledger.getDescription())
                .role("member")
                .joinedAt(joinedAt)
                .build();
    }

    public boolean isNameTaken(String name) {
        return ledgerRepository.selectCountByCondition(LedgerTableDef.LEDGER.NAME.eq(name)) != 0;
    }

    public long countCreatedOrJoinedLedgers(Long userId) {
        return userLedgerRepository.selectCountByCondition(UserLedgerTableDef.USER_LEDGER.USER_ID.eq(userId));
    }

    public boolean canCreateOrJoinLedger(Long userId) {
        return countCreatedOrJoinedLedgers(userId) < 3;
    }

    public boolean isLedgerJoined(Long ledgerId) {
        var currentUser = UserHolder.getCurrentUser();

        return userLedgerRepository.selectCountByCondition(UserLedgerTableDef.USER_LEDGER.USER_ID.eq(currentUser.id())
                .and(UserLedgerTableDef.USER_LEDGER.LEDGER_ID.eq(ledgerId))) == 1;
    }

    public boolean hasLedger(Long ledgerId) {
        return ledgerRepository.selectCountByCondition(LedgerTableDef.LEDGER.ID.eq(ledgerId)) == 1;
    }

    @Transactional
    public void deleteLedger(Long ledgerId) {
        // check whether the ledger exists
        if (!hasLedger(ledgerId)) {
            throw new BizException(HttpStatus.NOT_FOUND, "No ledger with given ledger id.");
        }

        if (!canEdit(ledgerId)) {
            throw new BizException(HttpStatus.FORBIDDEN, "You can't delete a ledger which is not created by you.");
        }

        transactionRepository.deleteByCondition(TransactionTableDef.TRANSACTION.LEDGER_ID.eq(ledgerId));
        userLedgerRepository.deleteByCondition(UserLedgerTableDef.USER_LEDGER.LEDGER_ID.eq(ledgerId));
        ledgerRepository.deleteByCondition(LedgerTableDef.LEDGER.ID.eq(ledgerId));
    }

    public boolean canEdit(Long ledgerId) {
        var currentUser = UserHolder.getCurrentUser();
        return "owner".equals(userLedgerRepository.selectRole(currentUser.id(), ledgerId));
    }
}
