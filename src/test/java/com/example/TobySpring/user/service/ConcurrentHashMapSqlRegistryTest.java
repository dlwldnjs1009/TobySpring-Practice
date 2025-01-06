package com.example.TobySpring.user.service;

import com.example.TobySpring.user.sqlservice.ConcurrentHashMapSqlRegistry;
import com.example.TobySpring.user.sqlservice.UpdatableSqlRegistry;

public class ConcurrentHashMapSqlRegistryTest extends AbstractUpdatableSqlRegistryTest {
    @Override
    protected UpdatableSqlRegistry createUpdatableSqlRegistry() {
        return new ConcurrentHashMapSqlRegistry();
    }

}
