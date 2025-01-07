package com.onixbyte.clearledger.data.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

// Auto generate by mybatis-flex, do not modify it.
public class UserLedgerTableDef extends TableDef {

    public static final UserLedgerTableDef USER_LEDGER = new UserLedgerTableDef();

    public final QueryColumn ROLE = new QueryColumn(this, "role");

    public final QueryColumn USER_ID = new QueryColumn(this, "user_id");

    public final QueryColumn JOINED_AT = new QueryColumn(this, "joined_at");

    public final QueryColumn LEDGER_ID = new QueryColumn(this, "ledger_id");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ROLE, USER_ID, JOINED_AT, LEDGER_ID};

    public UserLedgerTableDef() {
        super("", "user_ledgers");
    }

    private UserLedgerTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public UserLedgerTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new UserLedgerTableDef("", "user_ledgers", alias));
    }

}
