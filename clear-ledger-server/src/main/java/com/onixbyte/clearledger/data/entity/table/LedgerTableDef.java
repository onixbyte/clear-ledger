package com.onixbyte.clearledger.data.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

/**
 * Table definition for the "ledgers" table in the database.
 * <p>
 * This class provides a MyBatis-Flex table definition for the "ledgers" table, defining query
 * columns for use in database operations.
 *
 * @author zihluwang
 */
public class LedgerTableDef extends TableDef {

    /**
     * Singleton instance of the ledger table definition.
     */
    public static final LedgerTableDef LEDGER = new LedgerTableDef();

    /**
     * Query column for the "id" field.
     */
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * Query column for the "name" field.
     */
    public final QueryColumn NAME = new QueryColumn(this, "name");

    /**
     * Query column for the "created_at" field.
     */
    public final QueryColumn CREATED_AT = new QueryColumn(this, "created_at");

    /**
     * Query column for the "description" field.
     */
    public final QueryColumn DESCRIPTION = new QueryColumn(this, "description");

    /**
     * Query column representing all fields in the table.
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * Default fields for queries, excluding specialised fields like logical deletes.
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, NAME, CREATED_AT, DESCRIPTION};

    /**
     * Constructs a table definition for the "ledgers" table.
     */
    public LedgerTableDef() {
        super("", "ledgers");
    }

    /**
     * Constructs a table definition with schema, name, and alias (internal use).
     *
     * @param schema the database schema (empty by default)
     * @param name the table name
     * @param alias the table alias
     */
    private LedgerTableDef(String schema, String name, String alias) {
        super(schema, name, alias);
    }

    /**
     * Creates an aliased version of this table definition.
     * <p>
     * This method generates a new table definition with the specified alias, caching the result
     * for reuse.
     *
     * @param alias the alias to assign to the table
     * @return a new {@link LedgerTableDef} instance with the specified alias
     */
    public LedgerTableDef as(String alias) {
        var key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new LedgerTableDef("", "ledgers", alias));
    }
}