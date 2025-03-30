package com.onixbyte.clearledger.data.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

/**
 * Table definition for the "users" table in the database.
 * <p>
 * This class provides a MyBatis-Flex table definition for the "users" table, defining query columns
 * for use in database operations.
 *
 * @author zihluwang
 */
public class UserTableDef extends TableDef {

    /**
     * Singleton instance of the user table definition.
     */
    public static final UserTableDef USER = new UserTableDef();

    /**
     * Query column for the "id" field.
     */
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * Query column for the "email" field.
     */
    public final QueryColumn EMAIL = new QueryColumn(this, "email");

    /**
     * Query column for the "password" field.
     */
    public final QueryColumn PASSWORD = new QueryColumn(this, "password");

    /**
     * Query column for the "username" field.
     */
    public final QueryColumn USERNAME = new QueryColumn(this, "username");

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
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, EMAIL, PASSWORD, USERNAME, CREATED_AT};

    /**
     * Constructs a table definition for the "users" table.
     */
    public UserTableDef() {
        super("", "users");
    }

    /**
     * Constructs a table definition with schema, name, and alias (internal use).
     *
     * @param schema the database schema (empty by default)
     * @param name the table name
     * @param alias the table alias
     */
    private UserTableDef(String schema, String name, String alias) {
        super(schema, name, alias);
    }

    /**
     * Creates an aliased version of this table definition.
     * <p>
     * This method generates a new table definition with the specified alias, caching the result for reuse.
     *
     * @param alias the alias to assign to the table
     * @return a new {@link UserTableDef} instance with the specified alias
     */
    public UserTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new UserTableDef("", "users", alias));
    }
}