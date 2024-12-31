package com.onixbyte.clearledger.data.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

// Auto generate by mybatis-flex, do not modify it.
public class LedgerTableDef extends TableDef {

    public static final LedgerTableDef LEDGER = new LedgerTableDef();

    public final QueryColumn ID = new QueryColumn(this, "id");

    public final QueryColumn NAME = new QueryColumn(this, "name");

    public final QueryColumn CREATED_AT = new QueryColumn(this, "created_at");

    public final QueryColumn CREATED_BY = new QueryColumn(this, "created_by");

    public final QueryColumn DESCRIPTION = new QueryColumn(this, "description");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, NAME, CREATED_AT, CREATED_BY, DESCRIPTION};

    public LedgerTableDef() {
        super("", "ledgers");
    }

    private LedgerTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public LedgerTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new LedgerTableDef("", "ledgers", alias));
    }

}
