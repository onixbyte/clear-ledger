package com.onixbyte.clearledger.repository;

import com.mybatisflex.core.BaseMapper;
import com.onixbyte.clearledger.data.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * Repository interface for managing {@link User} entities.
 * <p>
 * Provides methods to perform CRUD operations on the User table using the base functionality
 * of {@link BaseMapper}.
 *
 * @author zihluwang
 */
@Mapper
public interface UserRepository extends BaseMapper<User> {
}