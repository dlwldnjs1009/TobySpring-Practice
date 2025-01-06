package com.example.TobySpring.user.sqlservice;

import com.example.TobySpring.exception.SqlNotFoundException;
import com.example.TobySpring.exception.SqlRetrievalFailureException;

public class BaseSqlService implements SqlService {

    protected SqlReader sqlReader;
    protected SqlRegistry sqlRegistry;

    public void setSqlReader(SqlReader sqlReader) {
        this.sqlReader = sqlReader;
    }

    public void setSqlRegistry(SqlRegistry sqlRegistry) {
        this.sqlRegistry = sqlRegistry;
    }

    public void loadSql() {
        this.sqlReader.read(this.sqlRegistry);
    }

    public String getSql(String key) {
        try {
            return this.sqlRegistry.findSql(key);
        } catch (SqlNotFoundException e) {
            throw new SqlRetrievalFailureException(e);
        }
    }
}
