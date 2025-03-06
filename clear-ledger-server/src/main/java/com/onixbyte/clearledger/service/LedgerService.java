package com.onixbyte.clearledger.service;

import com.onixbyte.clearledger.data.biz.BizLedger;
import com.onixbyte.clearledger.data.entity.Ledger;
import com.onixbyte.clearledger.data.entity.UserLedger;
import com.onixbyte.clearledger.data.entity.table.LedgerTableDef;
import com.onixbyte.clearledger.data.entity.table.TransactionTableDef;
import com.onixbyte.clearledger.data.entity.table.UserLedgerTableDef;
import com.onixbyte.clearledger.exception.BizException;
import com.onixbyte.clearledger.exception.ServiceUnavailableException;
import com.onixbyte.clearledger.holder.UserHolder;
import com.onixbyte.clearledger.repository.LedgerRepository;
import com.onixbyte.clearledger.repository.TransactionRepository;
import com.onixbyte.clearledger.repository.UserLedgerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.lang.model.element.NestingKind;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Service class for managing ledgers.
 *
 * @author zihluwang
 */
@Service
public class LedgerService {

    private static final Logger log = LoggerFactory.getLogger(LedgerService.class);

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
            throw new BizException(HttpStatus.CONFLICT, "账本名称已被使用");
        }

        if (!canCreateOrJoinLedger()) {
            throw new BizException(HttpStatus.CONFLICT, "您最多可以加入三个账本");
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

    /**
     * Let the current user join the given ledger.
     *
     * @param ledgerId ledger id
     * @return joined ledger and role data
     */
    @Transactional
    public BizLedger joinLedger(String ledgerId) {
        var currentUser = UserHolder.getCurrentUser();

        // check whether the ledger exists
        if (!hasLedger(ledgerId)) {
            throw new BizException(HttpStatus.NOT_FOUND, "无法根据指定的 ID 找到账本信息");
        }

        // validate user can create or join a ledger
        if (!canCreateOrJoinLedger()) {
            throw new BizException(HttpStatus.CONFLICT, "您最多只能加入 3 个账本");
        }

        // validate whether user is already join this ledger
        if (isLedgerJoined(ledgerId)) {
            throw new BizException(HttpStatus.CONFLICT, "您已经加入了这个账本");
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

    /**
     * Check whether the ledger name has been taken.
     *
     * @param name name of the ledger
     * @return {@code true} if the name has been taken, otherwise {@code false}
     */
    public boolean isNameTaken(String name) {
        return ledgerRepository.selectCountByCondition(LedgerTableDef.LEDGER.NAME.eq(name)) != 0;
    }

    /**
     * Count ledgers the user created or joined.
     *
     * @return the count of ledgers the current user created or joined
     */
    public long countCreatedOrJoinedLedgers() {
        var currentUser = UserHolder.getCurrentUser();
        return userLedgerRepository.selectCountByCondition(UserLedgerTableDef.USER_LEDGER.USER_ID.eq(currentUser.id()));
    }

    /**
     * Check whether current user can create or join a ledger.
     *
     * @return {@code true} if current user can create or join a ledger, otherwise {@code false}
     */
    public boolean canCreateOrJoinLedger() {
        return countCreatedOrJoinedLedgers() < 3;
    }

    /**
     * Check whether current user has joined the given ledger.
     *
     * @param ledgerId ledger id
     * @return {@code true} if current user has joined the ledger
     */
    public boolean isLedgerJoined(String ledgerId) {
        var currentUser = UserHolder.getCurrentUser();

        return userLedgerRepository.selectCountByCondition(UserLedgerTableDef.USER_LEDGER.USER_ID.eq(currentUser.id())
                .and(UserLedgerTableDef.USER_LEDGER.LEDGER_ID.eq(ledgerId))) == 1;
    }

    /**
     * Check whether the given ledger exists in database.
     *
     * @param ledgerId ledger id
     * @return {@code true} if ledger exists, otherwise {@code false}
     */
    public boolean hasLedger(String ledgerId) {
        return ledgerRepository.selectCountByCondition(LedgerTableDef.LEDGER.ID.eq(ledgerId)) == 1;
    }

    /**
     * Delete the given ledger.
     *
     * @param ledgerId ledger id
     */
    @Transactional
    public void deleteLedger(String ledgerId) {
        // check whether the ledger exists
        if (!hasLedger(ledgerId)) {
            throw new BizException(HttpStatus.NOT_FOUND, "无法根据指定的 ID 找到账本");
        }

        // check whether this ledger can be edited by current user
        if (!canEdit(ledgerId)) {
            throw new BizException(HttpStatus.FORBIDDEN, "您不能删除一个不是您创建的账本");
        }

        // perform deleting
        transactionRepository.deleteByCondition(TransactionTableDef.TRANSACTION.LEDGER_ID.eq(ledgerId));
        userLedgerRepository.deleteByCondition(UserLedgerTableDef.USER_LEDGER.LEDGER_ID.eq(ledgerId));
        ledgerRepository.deleteByCondition(LedgerTableDef.LEDGER.ID.eq(ledgerId));
    }

    /**
     * Check whether a user can edit or delete the given ledger.
     *
     * @param ledgerId ledger id
     * @return {@code true} if user can edit this ledger, otherwise {@code false}
     */
    public boolean canEdit(String ledgerId) {
        var currentUser = UserHolder.getCurrentUser();
        return "owner".equals(userLedgerRepository.selectRole(currentUser.id(), ledgerId));
    }

    /**
     * Update ledger, ignore null values.
     *
     * @param ledger the ledger that will be updated
     */
    @Transactional
    public void updateLedger(Ledger ledger) {
        if (!canEdit(ledger.getId())) {
            throw new BizException(HttpStatus.FORBIDDEN, "您不能编辑这个账本");
        }

        // perform update ledger
        ledgerRepository.update(ledger, true);
    }

    /**
     * Get ledgers you have joined.
     *
     * @return ledgers you joined
     */
    public List<BizLedger> getJoinedLedgers() {
        // get user information
        var user = UserHolder.getCurrentUser();

        // query ledgers
        return userLedgerRepository.selectJoinedLedgers(user.id());
    }

    /**
     * Get ledgers user can join.
     *
     * @return ledgers user can join
     */
    public List<BizLedger> getLedgersCanJoin() {
        throw new ServiceUnavailableException("该服务暂未实现，请耐心等候！");
    }

    public void exitLedger(Long ledgerId) {
        // get user information
        var user = UserHolder.getCurrentUser();
        var table = UserLedgerTableDef.USER_LEDGER;
        var affectedRows = userLedgerRepository.deleteByCondition(table.USER_ID.eq(user.id()).and(table.LEDGER_ID.eq(ledgerId)).and(table.ROLE.ne("owner")));
        if (affectedRows == 0) {
            throw new BizException(HttpStatus.CONFLICT, "您不能退出自己创建的账本，如不需要该账本，请删除该账本");
        }
    }
}
