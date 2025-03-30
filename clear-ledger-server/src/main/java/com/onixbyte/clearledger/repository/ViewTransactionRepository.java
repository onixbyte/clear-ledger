package com.onixbyte.clearledger.repository;

import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.query.QueryCondition;
import com.mybatisflex.core.query.QueryWrapper;
import com.onixbyte.clearledger.data.entity.ViewTransaction;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * Repository interface for managing {@link ViewTransaction} entities.
 * <p>
 * Provides methods to perform read-only operations on the ViewTransaction view using the base
 * functionality of {@link BaseMapper}. As this repository corresponds to a database view, insert,
 * update, and delete operations are not supported and will throw an
 * {@link UnsupportedOperationException} if invoked.
 *
 * @author zihluwang
 */
@Repository
public interface ViewTransactionRepository extends BaseMapper<ViewTransaction> {

    /**
     * Attempts to insert a {@link ViewTransaction} entity.
     * <p>
     * This operation is not supported as it corresponds to a database view.
     *
     * @param entity the entity to insert
     * @return does not return a value; always throws an exception
     * @throws UnsupportedOperationException as insert operations are not permitted on a view
     */
    @Override
    default int insert(ViewTransaction entity) {
        throw new UnsupportedOperationException();
    }

    /**
     * Attempts to insert a {@link ViewTransaction} entity with an option to ignore nulls.
     * <p>
     * This operation is not supported as it corresponds to a database view.
     *
     * @param viewTransaction the entity to insert
     * @param b               flag to indicate whether to ignore null values (not applicable)
     * @return does not return a value; always throws an exception
     * @throws UnsupportedOperationException as insert operations are not permitted on a view
     */
    @Override
    default int insert(ViewTransaction viewTransaction, boolean b) {
        throw new UnsupportedOperationException();
    }

    /**
     * Attempts to insert a batch of {@link ViewTransaction} entities.
     * <p>
     * This operation is not supported as it corresponds to a database view.
     *
     * @param collection the collection of entities to insert
     * @return does not return a value; always throws an exception
     * @throws UnsupportedOperationException as insert operations are not permitted on a view
     */
    @Override
    default int insertBatch(Collection<ViewTransaction> collection) {
        throw new UnsupportedOperationException();
    }

    /**
     * Attempts to insert a batch of {@link ViewTransaction} entities with a specified batch size.
     * <p>
     * This operation is not supported as it corresponds to a database view.
     *
     * @param entities the collection of entities to insert
     * @param size     the batch size (not applicable)
     * @return does not return a value; always throws an exception
     * @throws UnsupportedOperationException as insert operations are not permitted on a view
     */
    @Override
    default int insertBatch(Collection<ViewTransaction> entities, int size) {
        throw new UnsupportedOperationException();
    }

    /**
     * Attempts to insert a batch of {@link ViewTransaction} entities selectively.
     * <p>
     * This operation is not supported as it corresponds to a database view.
     *
     * @param entities the collection of entities to insert
     * @return does not return a value; always throws an exception
     * @throws UnsupportedOperationException as insert operations are not permitted on a view
     */
    @Override
    default int insertBatchSelective(Collection<ViewTransaction> entities) {
        throw new UnsupportedOperationException();
    }

    /**
     * Attempts to insert a batch of {@link ViewTransaction} entities selectively with a specified batch size.
     * <p>
     * This operation is not supported as it corresponds to a database view.
     *
     * @param entities the collection of entities to insert
     * @param size     the batch size (not applicable)
     * @return does not return a value; always throws an exception
     * @throws UnsupportedOperationException as insert operations are not permitted on a view
     */
    @Override
    default int insertBatchSelective(Collection<ViewTransaction> entities, int size) {
        throw new UnsupportedOperationException();
    }

    /**
     * Attempts to insert or update a {@link ViewTransaction} entity.
     * <p>
     * This operation is not supported as it corresponds to a database view.
     *
     * @param entity the entity to insert or update
     * @return does not return a value; always throws an exception
     * @throws UnsupportedOperationException as insert or update operations are not permitted on a view
     */
    @Override
    default int insertOrUpdate(ViewTransaction entity) {
        throw new UnsupportedOperationException();
    }

    /**
     * Attempts to insert or update a {@link ViewTransaction} entity with an option to ignore nulls.
     * <p>
     * This operation is not supported as it corresponds to a database view.
     *
     * @param entity      the entity to insert or update
     * @param ignoreNulls flag to indicate whether to ignore null values (not applicable)
     * @return does not return a value; always throws an exception
     * @throws UnsupportedOperationException as insert or update operations are not permitted on a view
     */
    @Override
    default int insertOrUpdate(ViewTransaction entity, boolean ignoreNulls) {
        throw new UnsupportedOperationException();
    }

    /**
     * Attempts to insert or update a {@link ViewTransaction} entity selectively.
     * <p>
     * This operation is not supported as it corresponds to a database view.
     *
     * @param entity the entity to insert or update
     * @return does not return a value; always throws an exception
     * @throws UnsupportedOperationException as insert or update operations are not permitted on a view
     */
    @Override
    default int insertOrUpdateSelective(ViewTransaction entity) {
        throw new UnsupportedOperationException();
    }

    /**
     * Attempts to insert a {@link ViewTransaction} entity selectively.
     * <p>
     * This operation is not supported as it corresponds to a database view.
     *
     * @param entity the entity to insert
     * @return does not return a value; always throws an exception
     * @throws UnsupportedOperationException as insert operations are not permitted on a view
     */
    @Override
    default int insertSelective(ViewTransaction entity) {
        throw new UnsupportedOperationException();
    }

    /**
     * Attempts to insert a {@link ViewTransaction} entity selectively with a primary key.
     * <p>
     * This operation is not supported as it corresponds to a database view.
     *
     * @param entity the entity to insert
     * @return does not return a value; always throws an exception
     * @throws UnsupportedOperationException as insert operations are not permitted on a view
     */
    @Override
    default int insertSelectiveWithPk(ViewTransaction entity) {
        throw new UnsupportedOperationException();
    }

    /**
     * Attempts to insert a {@link ViewTransaction} entity with a primary key.
     * <p>
     * This operation is not supported as it corresponds to a database view.
     *
     * @param entity the entity to insert
     * @return does not return a value; always throws an exception
     * @throws UnsupportedOperationException as insert operations are not permitted on a view
     */
    @Override
    default int insertWithPk(ViewTransaction entity) {
        throw new UnsupportedOperationException();
    }

    /**
     * Attempts to insert a {@link ViewTransaction} entity with a primary key and an option to ignore nulls.
     * <p>
     * This operation is not supported as it corresponds to a database view.
     *
     * @param viewTransaction the entity to insert
     * @param b               flag to indicate whether to ignore null values (not applicable)
     * @return does not return a value; always throws an exception
     * @throws UnsupportedOperationException as insert operations are not permitted on a view
     */
    @Override
    default int insertWithPk(ViewTransaction viewTransaction, boolean b) {
        throw new UnsupportedOperationException();
    }

    /**
     * Attempts to update a {@link ViewTransaction} entity.
     * <p>
     * This operation is not supported as it corresponds to a database view.
     *
     * @param entity the entity to update
     * @return does not return a value; always throws an exception
     * @throws UnsupportedOperationException as update operations are not permitted on a view
     */
    @Override
    default int update(ViewTransaction entity) {
        throw new UnsupportedOperationException();
    }

    /**
     * Attempts to update a {@link ViewTransaction} entity with an option to ignore nulls.
     * <p>
     * This operation is not supported as it corresponds to a database view.
     *
     * @param viewTransaction the entity to update
     * @param b               flag to indicate whether to ignore null values (not applicable)
     * @return does not return a value; always throws an exception
     * @throws UnsupportedOperationException as update operations are not permitted on a view
     */
    @Override
    default int update(ViewTransaction viewTransaction, boolean b) {
        throw new UnsupportedOperationException();
    }

    /**
     * Attempts to update a {@link ViewTransaction} entity based on a query condition.
     * <p>
     * This operation is not supported as it corresponds to a database view.
     *
     * @param entity           the entity to update
     * @param whereConditions the conditions to determine which records to update
     * @return does not return a value; always throws an exception
     * @throws UnsupportedOperationException as update operations are not permitted on a view
     */
    @Override
    default int updateByCondition(ViewTransaction entity, QueryCondition whereConditions) {
        throw new UnsupportedOperationException();
    }

    /**
     * Attempts to update a {@link ViewTransaction} entity based on a query condition with an option to ignore nulls.
     * <p>
     * This operation is not supported as it corresponds to a database view.
     *
     * @param entity           the entity to update
     * @param ignoreNulls      flag to indicate whether to ignore null values (not applicable)
     * @param whereConditions the conditions to determine which records to update
     * @return does not return a value; always throws an exception
     * @throws UnsupportedOperationException as update operations are not permitted on a view
     */
    @Override
    default int updateByCondition(ViewTransaction entity, boolean ignoreNulls, QueryCondition whereConditions) {
        throw new UnsupportedOperationException();
    }

    /**
     * Attempts to update a {@link ViewTransaction} entity based on a map of conditions.
     * <p>
     * This operation is not supported as it corresponds to a database view.
     *
     * @param entity           the entity to update
     * @param whereConditions the map of conditions to determine which records to update
     * @return does not return a value; always throws an exception
     * @throws UnsupportedOperationException as update operations are not permitted on a view
     */
    @Override
    default int updateByMap(ViewTransaction entity, Map<String, Object> whereConditions) {
        throw new UnsupportedOperationException();
    }

    /**
     * Attempts to update a {@link ViewTransaction} entity based on a map of conditions with an option to ignore nulls.
     * <p>
     * This operation is not supported as it corresponds to a database view.
     *
     * @param entity           the entity to update
     * @param ignoreNulls      flag to indicate whether to ignore null values (not applicable)
     * @param whereConditions the map of conditions to determine which records to update
     * @return does not return a value; always throws an exception
     * @throws UnsupportedOperationException as update operations are not permitted on a view
     */
    @Override
    default int updateByMap(ViewTransaction entity, boolean ignoreNulls, Map<String, Object> whereConditions) {
        throw new UnsupportedOperationException();
    }

    /**
     * Attempts to update a {@link ViewTransaction} entity based on a query wrapper.
     * <p>
     * This operation is not supported as it corresponds to a database view.
     *
     * @param entity       the entity to update
     * @param queryWrapper the query wrapper defining the update conditions
     * @return does not return a value; always throws an exception
     * @throws UnsupportedOperationException as update operations are not permitted on a view
     */
    @Override
    default int updateByQuery(ViewTransaction entity, QueryWrapper queryWrapper) {
        throw new UnsupportedOperationException();
    }

    /**
     * Attempts to update a {@link ViewTransaction} entity based on a query wrapper with an option to ignore nulls.
     * <p>
     * This operation is not supported as it corresponds to a database view.
     *
     * @param viewTransaction the entity to update
     * @param b               flag to indicate whether to ignore null values (not applicable)
     * @param queryWrapper    the query wrapper defining the update conditions
     * @return does not return a value; always throws an exception
     * @throws UnsupportedOperationException as update operations are not permitted on a view
     */
    @Override
    default int updateByQuery(ViewTransaction viewTransaction, boolean b, QueryWrapper queryWrapper) {
        throw new UnsupportedOperationException();
    }

    /**
     * Attempts to delete a {@link ViewTransaction} entity.
     * <p>
     * This operation is not supported as it corresponds to a database view.
     *
     * @param entity the entity to delete
     * @return does not return a value; always throws an exception
     * @throws UnsupportedOperationException as delete operations are not permitted on a view
     */
    @Override
    default int delete(ViewTransaction entity) {
        throw new UnsupportedOperationException();
    }

    /**
     * Attempts to delete a batch of {@link ViewTransaction} entities by their identifiers.
     * <p>
     * This operation is not supported as it corresponds to a database view.
     *
     * @param collection the collection of identifiers for entities to delete
     * @return does not return a value; always throws an exception
     * @throws UnsupportedOperationException as delete operations are not permitted on a view
     */
    @Override
    default int deleteBatchByIds(Collection<? extends Serializable> collection) {
        throw new UnsupportedOperationException();
    }

    /**
     * Attempts to delete a batch of {@link ViewTransaction} entities by their identifiers with a specified batch size.
     * <p>
     * This operation is not supported as it corresponds to a database view.
     *
     * @param ids  the collection of identifiers for entities to delete
     * @param size the batch size (not applicable)
     * @return does not return a value; always throws an exception
     * @throws UnsupportedOperationException as delete operations are not permitted on a view
     */
    @Override
    default int deleteBatchByIds(Collection<? extends Serializable> ids, int size) {
        throw new UnsupportedOperationException();
    }

    /**
     * Attempts to delete {@link ViewTransaction} entities based on a query condition.
     * <p>
     * This operation is not supported as it corresponds to a database view.
     *
     * @param whereConditions the conditions to determine which records to delete
     * @return does not return a value; always throws an exception
     * @throws UnsupportedOperationException as delete operations are not permitted on a view
     */
    @Override
    default int deleteByCondition(QueryCondition whereConditions) {
        throw new UnsupportedOperationException();
    }

    /**
     * Attempts to delete a {@link ViewTransaction} entity by its identifier.
     * <p>
     * This operation is not supported as it corresponds to a database view.
     *
     * @param serializable the identifier of the entity to delete
     * @return does not return a value; always throws an exception
     * @throws UnsupportedOperationException as delete operations are not permitted on a view
     */
    @Override
    default int deleteById(Serializable serializable) {
        throw new UnsupportedOperationException();
    }

    /**
     * Attempts to delete {@link ViewTransaction} entities based on a map of conditions.
     * <p>
     * This operation is not supported as it corresponds to a database view.
     *
     * @param whereConditions the map of conditions to determine which records to delete
     * @return does not return a value; always throws an exception
     * @throws UnsupportedOperationException as delete operations are not permitted on a view
     */
    @Override
    default int deleteByMap(Map<String, Object> whereConditions) {
        throw new UnsupportedOperationException();
    }

    /**
     * Attempts to delete {@link ViewTransaction} entities based on a query wrapper.
     * <p>
     * This operation is not supported as it corresponds to a database view.
     *
     * @param queryWrapper the query wrapper defining the delete conditions
     * @return does not return a value; always throws an exception
     * @throws UnsupportedOperationException as delete operations are not permitted on a view
     */
    @Override
    default int deleteByQuery(QueryWrapper queryWrapper) {
        throw new UnsupportedOperationException();
    }
}