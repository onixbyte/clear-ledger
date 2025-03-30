package com.onixbyte.clearledger.repository;

import com.mybatisflex.core.BaseMapper;
import com.onixbyte.clearledger.data.dto.BizLedger;
import com.onixbyte.clearledger.data.entity.UserLedger;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Repository interface for managing {@link UserLedger} entities.
 * <p>
 * Provides methods to perform CRUD operations and custom queries on the UserLedger table,
 * including retrieving user roles and associated ledger information.
 *
 * @author zihluwang
 */
@Mapper
public interface UserLedgerRepository extends BaseMapper<UserLedger> {

    /**
     * Retrieves the role of a user within a specific ledger.
     * <p>
     * Queries the database to fetch the role associated with the given user and ledger
     * identifiers.
     *
     * @param userId   the unique identifier of the user
     * @param ledgerId the unique identifier of the ledger
     * @return the user's role in the ledger, or null if no association exists
     */
    @Select("""
            select role
              from user_ledgers
             where user_id = #{userId}
               and ledger_id = #{ledgerId}
            """)
    String selectRole(@Param("userId") String userId, @Param("ledgerId") String ledgerId);

    /**
     * Retrieves a list of ledgers joined by a specific user.
     * <p>
     * Queries the database to fetch all ledgers associated with the given user, including
     * details such as ledger ID, name, description, role, and join date.
     *
     * @param userId the unique identifier of the user
     * @return a list of {@link BizLedger} objects representing the ledgers joined by the user
     */
    @Select("""
            select l.id, l.name, l.description, ul.role, ul.joined_at
            from user_ledgers ul
            left join ledgers l on ul.ledger_id = l.id
            where ul.user_id = #{userId}
            """)
    List<BizLedger> selectJoinedLedgers(@Param("userId") String userId);

    /**
     * Checks if a user has permission to write transactions in a specific ledger.
     * <p>
     * Queries the database to determine whether the user is associated with the given ledger,
     * implying write permission based on the presence of a record.
     *
     * @param userId   the unique identifier of the user
     * @param ledgerId the unique identifier of the ledger
     * @return true if the user can write transactions in the ledger, false otherwise
     */
    @Select("""
            select count(*)
            from user_ledgers
            where user_id = #{userId}
            and ledger_id = #{ledgerId}
            """)
    boolean canWriteTransaction(@Param("userId") String userId,
                                @Param("ledgerId") String ledgerId);
}