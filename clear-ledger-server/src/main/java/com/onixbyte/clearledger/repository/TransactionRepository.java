package com.onixbyte.clearledger.repository;

import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.paginate.Page;
import com.onixbyte.clearledger.data.entity.Transaction;
import com.onixbyte.clearledger.data.entity.ViewTransaction;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Repository interface for managing {@link Transaction} entities.
 * <p>
 * Provides methods to perform CRUD operations and custom queries on the Transaction table,
 * including paginated views of transaction data.
 *
 * @author zihluwang
 */
@Mapper
public interface TransactionRepository extends BaseMapper<Transaction> {

    /**
     * Retrieves a paginated list of transaction views for a specified ledger.
     * <p>
     * Queries the database to fetch a subset of transactions for a given ledger, including
     * user information, ordered by transaction ID, with pagination applied using offset and
     * page size.
     *
     * @param ledgerId the unique identifier of the ledger
     * @param offset   the number of records to skip before starting the result set
     * @param pageSize the maximum number of records to return
     * @return a list of {@link ViewTransaction} objects representing the paginated results
     */
    @Select("""
            select t.id, t.ledger_id, t.user_id, u.username, t.amount, t.description, t.transaction_date, t.created_at
            from transactions t
            left join users u on t.user_id = u.id
            where t.ledger_id = #{ledgerId}
            order by t.id
            offset #{offset} limit #{pageSize}
            """)
    List<ViewTransaction> selectPaginateViewTransactions(@Param("ledgerId") String ledgerId,
                                                         @Param("offset") Long offset,
                                                         @Param("pageSize") Long pageSize);
}