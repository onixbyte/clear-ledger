package com.onixbyte.clearledger.repository;

import com.mybatisflex.core.BaseMapper;
import com.onixbyte.clearledger.data.biz.BizLedger;
import com.onixbyte.clearledger.data.entity.UserLedger;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserLedgerRepository extends BaseMapper<UserLedger> {

    @Select("""
            select role
              from user_ledgers
             where user_id = #{userId}
               and ledger_id = #{ledgerId}
            """)
    String selectRole(@Param("userId") String userId, @Param("ledgerId") String ledgerId);

    @Select("""
            select l.id, l.name, l.description, ul.role, ul.joined_at
            from user_ledgers ul
            left join ledgers l on ul.ledger_id = l.id
            where ul.user_id = #{userId}
            """)
    List<BizLedger> selectJoinedLedgers(@Param("userId") String userId);

}
