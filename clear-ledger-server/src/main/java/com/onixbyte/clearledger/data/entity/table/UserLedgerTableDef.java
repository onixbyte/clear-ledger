package com.onixbyte.clearledger.data.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

/**
 * Table definition for the "user_ledgers" table in the database.
 * <p>
 * This class provides a MyBatis-Flex table definition for the "user_ledgers" table, defining query
 * columns for use in database operations.
 *
 * @author zihluwang
 */
public class UserLedgerTableDef extends TableDef {

    /**
     * Singleton instance of the user-ledger table definition.
     */
    public static final UserLedgerTableDef USER_LEDGER = new UserLedgerTableDef();

    /**
     * Query column for the "role" field.
     */
    public final QueryColumn ROLE = new QueryColumn(this, "role");

    /**
     * Query column for the "user_id" field.
     */
    public final QueryColumn USER_ID = new QueryColumn(this, "user_id");

    /**
     * Query column for the "joined_at" field.
     */
    public final QueryColumn JOINED_AT = new QueryColumn(this, "joined_at");

    /**
     * Query column for the "ledger_id" field.
     */
    public final QueryColumn LEDGER_ID = new QueryColumn(this, "ledger_id");

    /**
     * Query column representing all fields in the table.
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * Default fields for queries, excluding specialised fields like logical deletes.
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ROLE, USER_ID, JOINED_AT, LEDGER_ID};

    /**
     * Constructs a table definition for the "user_ledgers" table.
     */
    public UserLedgerTableDef() {
        super("", "user_ledgers");
    }

    /**
     * Constructs a table definition with schema, name, and alias (internal use).
     *
     * @param schema the database schema (empty by default)
     * @param name the table name
     * @param alias the table alias
     */
    private UserLedgerTableDef(String schema, String name, String alias) {
        super(schema, name, alias);
    }

    /**
     * Creates an aliased version of this table definition.
     * <p>
     * This method generates a new table definition with the specified alias, caching the result
     * for reuse.
     *
     * @param alias the alias to assign to the table
     * @return a new {@link UserLedgerTableDef} instance with the specified alias
     */
    public UserLedgerTableDef as(String alias) {
        var key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new UserLedgerTableDef("", "user_ledgers", alias));
    }
}