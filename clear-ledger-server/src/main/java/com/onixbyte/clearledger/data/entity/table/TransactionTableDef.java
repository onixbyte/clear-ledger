package com.onixbyte.clearledger.data.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

// Auto generate by mybatis-flex, do not modify it.
public class TransactionTableDef extends TableDef {

    public static final TransactionTableDef TRANSACTION = new TransactionTableDef();

    public final QueryColumn ID = new QueryColumn(this, "id");

    public final QueryColumn AMOUNT = new QueryColumn(this, "amount");

    public final QueryColumn USER_ID = new QueryColumn(this, "user_id");

    public final QueryColumn LEDGER_ID = new QueryColumn(this, "ledger_id");

    public final QueryColumn CREATED_AT = new QueryColumn(this, "created_at");

    public final QueryColumn DESCRIPTION = new QueryColumn(this, "description");

    public final QueryColumn TRANSACTION_DATE = new QueryColumn(this, "transaction_date");

    /**
     * All fields.
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * Default fields, except fields like logic-delete and large, etc.
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, AMOUNT, USER_ID, LEDGER_ID, CREATED_AT, DESCRIPTION, TRANSACTION_DATE};

    public TransactionTableDef() {
        super("", "transactions");
    }

    private TransactionTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public TransactionTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new TransactionTableDef("", "transactions", alias));
    }

}
