package com.onixbyte.clearledger.service;

import com.onixbyte.clearledger.data.dto.BizLedger;
import com.onixbyte.clearledger.data.entity.Ledger;
import com.onixbyte.clearledger.data.entity.UserLedger;
import com.onixbyte.clearledger.data.entity.table.LedgerTableDef;
import com.onixbyte.clearledger.data.entity.table.TransactionTableDef;
import com.onixbyte.clearledger.data.entity.table.UserLedgerTableDef;
import com.onixbyte.clearledger.exception.BizException;
import com.onixbyte.clearledger.security.UserHolder;
import com.onixbyte.clearledger.repository.LedgerRepository;
import com.onixbyte.clearledger.repository.TransactionRepository;
import com.onixbyte.clearledger.repository.UserLedgerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service class for managing ledgers.
 * <p>
 * Provides methods to create, join, update, delete, and query ledgers, as well as manage user
 * participation and ownership, with transactional support where applicable.
 *
 * @author zihluwang
 */
@Service
public class LedgerService {

    private static final Logger log = LoggerFactory.getLogger(LedgerService.class);

    private final String cacheTag = "ledger";

    private final LedgerRepository ledgerRepository;
    private final UserLedgerRepository userLedgerRepository;
    private final TransactionRepository transactionRepository;
    private final SerialService serialService;

    /**
     * Constructs a ledger service with required dependencies.
     *
     * @param ledgerRepository      the repository for ledger data operations
     * @param userLedgerRepository  the repository for user-ledger relationship data operations
     * @param transactionRepository the repository for transaction data operations
     * @param serialService         the service for managing serial numbers
     */
    @Autowired
    public LedgerService(LedgerRepository ledgerRepository,
                         UserLedgerRepository userLedgerRepository,
                         TransactionRepository transactionRepository,
                         SerialService serialService) {
        this.ledgerRepository = ledgerRepository;
        this.userLedgerRepository = userLedgerRepository;
        this.transactionRepository = transactionRepository;
        this.serialService = serialService;
    }

    /**
     * Creates a new ledger and associates it with the current user as the owner.
     *
     * @param ledger the {@link Ledger} entity to create
     * @return the created {@link BizLedger} with ownership details
     * @throws BizException if the ledger name is already taken or the user has reached the
     *                      join limit
     */
    @Transactional
    public BizLedger saveLedger(Ledger ledger) {
        var currentUser = UserHolder.getCurrentUser();

        if (isNameTaken(ledger.getName())) {
            throw BizException.conflict("账本名称已被使用");
        }

        if (!canCreateOrJoinLedger()) {
            throw BizException.conflict("您最多可以加入三个账本");
        }

        var userLedger = UserLedger.builder()
                .userId(currentUser.id())
                .ledgerId(ledger.getId())
                .role("owner")
                .joinedAt(LocalDateTime.now())
                .build();

        ledgerRepository.insert(ledger);
        userLedgerRepository.insert(userLedger);

        return ledger.toBiz(userLedger.getRole(), userLedger.getJoinedAt());
    }

    /**
     * Allows the current user to join an existing ledger as a member.
     *
     * @param ledgerId the ID of the ledger to join
     * @return the joined {@link BizLedger} with membership details
     * @throws BizException if the ledger does not exist, the user has reached the join limit, or
     *                      the user is already a member
     */
    @Transactional
    public BizLedger joinLedger(String ledgerId) {
        var currentUser = UserHolder.getCurrentUser();

        // check whether the ledger exists
        if (!hasLedger(ledgerId)) {
            throw BizException.notFound("无法根据指定的 ID 找到账本信息");
        }

        // validate user can create or join a ledger
        if (!canCreateOrJoinLedger()) {
            throw BizException.conflict("您最多只能加入 3 个账本");
        }

        // validate whether user is already join this ledger
        if (isLedgerJoined(ledgerId)) {
            throw BizException.conflict("您已经加入了这个账本");
        }

        var joinedAt = LocalDateTime.now();
        var userLedger = UserLedger.builder()
                .userId(currentUser.id())
                .ledgerId(ledgerId)
                .role("member")
                .joinedAt(joinedAt)
                .build();
        userLedgerRepository.insert(userLedger);

        var ledger = ledgerRepository.selectOneByCondition(LedgerTableDef.LEDGER.ID.eq(ledgerId));
        return BizLedger.builder()
                .id(ledgerId)
                .name(ledger.getName())
                .description(ledger.getDescription())
                .role(userLedger.getRole())
                .joinedAt(joinedAt)
                .build();
    }

    /**
     * Checks whether the specified ledger name is already in use.
     *
     * @param name the name of the ledger to check
     * @return {@code true} if the name is taken, otherwise {@code false}
     */
    public boolean isNameTaken(String name) {
        return ledgerRepository.selectCountByCondition(LedgerTableDef.LEDGER.NAME.eq(name)) != 0;
    }

    /**
     * Counts the number of ledgers the current user has created or joined.
     *
     * @return the number of ledgers the current user is associated with
     */
    public long countCreatedOrJoinedLedgers() {
        var currentUser = UserHolder.getCurrentUser();
        return userLedgerRepository.selectCountByCondition(UserLedgerTableDef.USER_LEDGER.USER_ID.eq(currentUser.id()));
    }

    /**
     * Determines whether the current user can create or join a ledger.
     * <p>
     * Users are limited to a maximum of three ledgers.
     *
     * @return {@code true} if the user can create or join a ledger, otherwise {@code false}
     */
    public boolean canCreateOrJoinLedger() {
        return countCreatedOrJoinedLedgers() < 3;
    }

    /**
     * Checks whether the current user has joined the specified ledger.
     *
     * @param ledgerId the ID of the ledger to check
     * @return {@code true} if the user has joined the ledger, otherwise {@code false}
     */
    public boolean isLedgerJoined(String ledgerId) {
        var currentUser = UserHolder.getCurrentUser();

        return userLedgerRepository.selectCountByCondition(UserLedgerTableDef.USER_LEDGER.USER_ID.eq(currentUser.id())
                .and(UserLedgerTableDef.USER_LEDGER.LEDGER_ID.eq(ledgerId))) == 1;
    }

    /**
     * Checks whether the specified ledger exists in the database.
     *
     * @param ledgerId the ID of the ledger to check
     * @return {@code true} if the ledger exists, otherwise {@code false}
     */
    public boolean hasLedger(String ledgerId) {
        return ledgerRepository.selectCountByCondition(LedgerTableDef.LEDGER.ID.eq(ledgerId)) == 1;
    }

    /**
     * Deletes the specified ledger and its associated data.
     * <p>
     * Removes the ledger, its transactions, and user associations if the current user is the owner.
     *
     * @param ledgerId the ID of the ledger to delete
     * @throws BizException if the ledger does not exist or the user lacks permission to delete it
     */
    @Transactional
    public void deleteLedger(String ledgerId) {
        // check whether the ledger exists
        if (!hasLedger(ledgerId)) {
            throw BizException.notFound("无法根据指定的 ID 找到账本");
        }

        // check whether this ledger can be edited by current user
        if (!canEdit(ledgerId)) {
            throw BizException.forbidden("您不能删除一个不是您创建的账本");
        }

        // perform deleting
        transactionRepository.deleteByCondition(TransactionTableDef.TRANSACTION.LEDGER_ID.eq(ledgerId));
        userLedgerRepository.deleteByCondition(UserLedgerTableDef.USER_LEDGER.LEDGER_ID.eq(ledgerId));
        ledgerRepository.deleteByCondition(LedgerTableDef.LEDGER.ID.eq(ledgerId));
    }

    /**
     * Determines whether the current user can edit or delete the specified ledger.
     * <p>
     * Only the owner of the ledger has edit and delete permissions.
     *
     * @param ledgerId the ID of the ledger to check
     * @return {@code true} if the user can edit the ledger, otherwise {@code false}
     */
    public boolean canEdit(String ledgerId) {
        var currentUser = UserHolder.getCurrentUser();
        return "owner".equals(userLedgerRepository.selectRole(currentUser.id(), ledgerId));
    }

    /**
     * Updates the specified ledger, ignoring null values.
     *
     * @param ledger the {@link Ledger} entity with updated values
     * @throws BizException if the user lacks permission or the new name is already taken
     */
    @Transactional
    public void updateLedger(Ledger ledger) {
        if (!canEdit(ledger.getId())) {
            throw BizException.forbidden("您不能编辑这个账本");
        }

        if (isNameTaken(ledger.getName())) {
            throw BizException.conflict("账本名称已被使用");
        }

        // perform update ledger
        ledgerRepository.update(ledger, true);
    }

    /**
     * Retrieves a list of ledgers the current user has joined.
     *
     * @return a list of {@link BizLedger} objects representing the user's joined ledgers
     */
    public List<BizLedger> getJoinedLedgers() {
        // get user information
        var user = UserHolder.getCurrentUser();

        // query ledgers
        return userLedgerRepository.selectJoinedLedgers(user.id());
    }

    /**
     * Retrieves a list of ledgers the current user can join.
     *
     * @return a list of {@link BizLedger} objects representing available ledgers
     * @throws BizException as this service is not yet implemented
     */
    public List<BizLedger> getLedgersCanJoin() {
        throw BizException.serviceUnavailable("该服务暂未实现，请耐心等候！");
    }

    /**
     * Allows the current user to exit the specified ledger.
     * <p>
     * Removes the user's association with the ledger if they are not the owner.
     *
     * @param ledgerId the ID of the ledger to exit
     * @throws BizException if the user is the owner or the exit operation fails
     */
    public void exitLedger(Long ledgerId) {
        // get user information
        var user = UserHolder.getCurrentUser();
        var table = UserLedgerTableDef.USER_LEDGER;
        var affectedRows = userLedgerRepository.deleteByCondition(table.USER_ID.eq(user.id()).and(table.LEDGER_ID.eq(ledgerId)).and(table.ROLE.ne("owner")));
        if (affectedRows == 0) {
            throw BizException.conflict("您不能退出自己创建的账本，如不需要该账本，请删除该账本");
        }
    }

    /**
     * Resets the serial number for ledger-related operations daily.
     * <p>
     * Scheduled to run at midnight every day to reset the serial counter.
     */
    @Scheduled(cron = "0 0 0 * * *")
    public void resetSerial() {
        log.info("Resetting ledger serial.");
        serialService.resetSerial(cacheTag);
        log.info("Ledger serial has been reset to 0.");
    }
}
