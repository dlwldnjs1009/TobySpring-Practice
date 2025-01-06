package com.example.TobySpring.user.sqlservice;

import com.example.TobySpring.exception.SqlRetrievalFailureException;

public interface SqlService {
    String getSql(String key) throws SqlRetrievalFailureException;

}
