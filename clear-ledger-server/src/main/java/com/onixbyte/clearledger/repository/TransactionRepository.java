package com.onixbyte.clearledger.repository;

import com.mybatisflex.core.BaseMapper;
import com.onixbyte.clearledger.data.entity.Transaction;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TransactionRepository extends BaseMapper<Transaction> {
}
