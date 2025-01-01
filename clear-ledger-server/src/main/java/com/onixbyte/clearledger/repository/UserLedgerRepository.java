package com.onixbyte.clearledger.repository;

import com.mybatisflex.core.BaseMapper;
import com.onixbyte.clearledger.data.entity.UserLedger;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserLedgerRepository extends BaseMapper<UserLedger> {
}
