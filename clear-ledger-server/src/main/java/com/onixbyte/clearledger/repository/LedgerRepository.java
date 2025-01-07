package com.onixbyte.clearledger.repository;

import com.mybatisflex.core.BaseMapper;
import com.onixbyte.clearledger.data.biz.LedgerBizData;
import com.onixbyte.clearledger.data.entity.Ledger;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Repository interface for managing {@link Ledger} entities. Provides methods to perform CRUD operations and custom
 * queries on the Ledger table.
 *
 * @author zihluwang
 */
@Mapper
public interface LedgerRepository extends BaseMapper<Ledger> {

    /**
     * Query all the ledgers that the user has joined or created.
     *
     * @param userId the id of the user
     * @return all the ledgers the user has joined or created
     */
    @Select("""
            SELECT l.id, l.name, l.description, l.created_at, ul.role, ul.joined_at
            FROM ledgers l
            JOIN user_ledgers ul ON l.id = ul.ledger_id
            WHERE ul.user_id = #{userId}
            """)
    List<LedgerBizData> findLedgersByUserId(@Param("id") Long userId);

}
