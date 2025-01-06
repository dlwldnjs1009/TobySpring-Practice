package com.example.TobySpring.user.sqlservice;

import com.example.TobySpring.exception.SqlNotFoundException;

public interface SqlRegistry {

    public void registerSql(String key, String sql);

    String findSql(String key) throws SqlNotFoundException;

}
