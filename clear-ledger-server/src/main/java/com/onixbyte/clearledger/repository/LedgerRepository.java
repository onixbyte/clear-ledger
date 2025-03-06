package com.onixbyte.clearledger.repository;

import com.mybatisflex.core.BaseMapper;
import com.onixbyte.clearledger.data.entity.Ledger;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Repository interface for managing {@link Ledger} entities. Provides methods to perform CRUD operations and custom
 * queries on the Ledger table.
 *
 * @author zihluwang
 */
@Mapper
public interface LedgerRepository extends BaseMapper<Ledger> {

    @Select("""
            select count(*)
            from ledgers
            where id = #{ledgerId}
            """)
    boolean hasLedger(@Param("ledgerId") String ledgerId);

}
