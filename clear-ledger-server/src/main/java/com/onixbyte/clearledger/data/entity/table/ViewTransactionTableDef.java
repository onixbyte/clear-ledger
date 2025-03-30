package com.onixbyte.clearledger.data.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

/**
 * Table definition for the "view_transactions" table in the database.
 * <p>
 * This class provides a MyBatis-Flex table definition for the "view_transactions" table, defining
 * query columns for use in database operations.
 *
 * @author zihluwang
 */
public class ViewTransactionTableDef extends TableDef {

    /**
     * Singleton instance of the view transaction table definition.
     */
    public static final ViewTransactionTableDef VIEW_TRANSACTION =
            new ViewTransactionTableDef();

    /**
     * Query column for the "id" field.
     */
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * Query column for the "ledger_id" field.
     */
    public final QueryColumn LEDGER_ID = new QueryColumn(this, "ledger_id");

    /**
     * Query column for the "user_id" field.
     */
    public final QueryColumn USER_ID = new QueryColumn(this, "user_id");

    /**
     * Query column for the "username" field.
     */
    public final QueryColumn USERNAME = new QueryColumn(this, "username");

    /**
     * Query column for the "amount" field.
     */
    public final QueryColumn AMOUNT = new QueryColumn(this, "amount");

    /**
     * Query column for the "description" field.
     */
    public final QueryColumn DESCRIPTION = new QueryColumn(this, "description");

    /**
     * Query column for the "transaction_date" field.
     */
    public final QueryColumn TRANSACTION_DATE = new QueryColumn(this, "transaction_date");

    /**
     * Query column for the "created_at" field.
     */
    public final QueryColumn CREATED_AT = new QueryColumn(this, "created_at");

    /**
     * Query column representing all fields in the table.
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * Default fields for queries, excluding specialised fields like logical deletes.
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, LEDGER_ID, USER_ID, USERNAME,
            AMOUNT, DESCRIPTION, TRANSACTION_DATE, CREATED_AT};

    /**
     * Constructs a table definition for the "view_transactions" table.
     */
    public ViewTransactionTableDef() {
        super("", "view_transactions");
    }

    /**
     * Constructs a table definition with schema, name, and alias (internal use).
     *
     * @param schema the database schema (empty by default)
     * @param name the table name
     * @param alias the table alias
     */
    private ViewTransactionTableDef(String schema, String name, String alias) {
        super(schema, name, alias);
    }

    /**
     * Creates an aliased version of this table definition.
     * <p>
     * This method generates a new table definition with the specified alias, caching the result for reuse.
     *
     * @param alias the alias to assign to the table
     * @return a new {@link ViewTransactionTableDef} instance with the specified alias
     */
    public ViewTransactionTableDef as(String alias) {
        var key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new ViewTransactionTableDef("", "view_transactions", alias));
    }
}