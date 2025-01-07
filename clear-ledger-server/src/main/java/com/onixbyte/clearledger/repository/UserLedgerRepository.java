package com.onixbyte.clearledger.repository;

import com.mybatisflex.core.BaseMapper;
import com.onixbyte.clearledger.data.entity.UserLedger;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserLedgerRepository extends BaseMapper<UserLedger> {

    @Select("""
            select role
              from user_ledgers
             where user_id = #{userId}
               and ledger_id = #{ledgerId}
            """)
    String selectRole(@Param("userId") Long userId, @Param("ledgerId") Long ledgerId);

}
