package com.onixbyte.clearledger.repository;

import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.paginate.Page;
import com.onixbyte.clearledger.data.entity.Transaction;
import com.onixbyte.clearledger.data.entity.ViewTransaction;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TransactionRepository extends BaseMapper<Transaction> {

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
