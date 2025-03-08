package com.onixbyte.clearledger.data.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

public class ViewTransactionTableDef extends TableDef {

    public static final ViewTransactionTableDef VIEW_TRANSACTION =
            new ViewTransactionTableDef();

    public final QueryColumn ID = new QueryColumn(this, "id");

    public final QueryColumn LEDGER_ID = new QueryColumn(this, "ledger_id");

    public final QueryColumn USER_ID = new QueryColumn(this, "user_id");

    public final QueryColumn USERNAME = new QueryColumn(this, "username");

    public final QueryColumn AMOUNT = new QueryColumn(this, "amount");

    public final QueryColumn DESCRIPTION = new QueryColumn(this, "description");

    public final QueryColumn TRANSACTION_DATE = new QueryColumn(this, "transaction_date");

    public final QueryColumn CREATED_AT = new QueryColumn(this, "created_at");

    /**
     * All fields.
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * Default fields, except fields like logic-delete and large, etc.
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, LEDGER_ID, USER_ID, USERNAME,
            AMOUNT, DESCRIPTION, TRANSACTION_DATE, CREATED_AT};

    public ViewTransactionTableDef() {
        super("", "view_transactions");
    }

    private ViewTransactionTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public ViewTransactionTableDef as(String alias) {
        var key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new ViewTransactionTableDef("", "view_transactions", alias));
    }
}
