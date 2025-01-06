package com.example.TobySpring.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.TobySpring.exception.SqlNotFoundException;
import com.example.TobySpring.exception.SqlUpdateFailureException;
import com.example.TobySpring.user.sqlservice.ConcurrentHashMapSqlRegistry;
import com.example.TobySpring.user.sqlservice.UpdatableSqlRegistry;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public abstract class AbstractUpdatableSqlRegistryTest {
    UpdatableSqlRegistry sqlRegistry;

    @BeforeEach
    public void setUp() {
        sqlRegistry = createUpdatableSqlRegistry();
        sqlRegistry.registerSql("KEY1", "SQL1");
        sqlRegistry.registerSql("KEY2", "SQL2");
        sqlRegistry.registerSql("KEY3", "SQL3");
    }

    abstract protected UpdatableSqlRegistry createUpdatableSqlRegistry();

    @Test
    public void find() {
        checkFindResult("SQL1", "SQL2", "SQL3");
    }

    protected void checkFindResult(String expected1, String expected2, String expected3) {
        assertThat(sqlRegistry.findSql("KEY1")).isEqualTo(expected1);
        assertThat(sqlRegistry.findSql("KEY2")).isEqualTo(expected2);
        assertThat(sqlRegistry.findSql("KEY3")).isEqualTo(expected3);
    }

    @Test
    public void unknownKey() {
        assertThatThrownBy(() -> sqlRegistry.findSql("SQL9999"))
            .isInstanceOf(SqlNotFoundException.class);
    }

    @Test
    public void updateSingle() throws SqlUpdateFailureException {
        sqlRegistry.updateSql("KEY2", "Modified2");
        checkFindResult("SQL1", "Modified2", "SQL3");
    }

    @Test
    public void updateMulti() throws SqlUpdateFailureException {
        Map<String, String> sqlmap = new HashMap<String, String>();
        sqlmap.put("KEY1", "Modified1");
        sqlmap.put("KEY3", "Modified3");

        sqlRegistry.updateSql(sqlmap);
        checkFindResult("Modified1", "SQL2", "Modified3");
    }

    @Test
    public void updateWithNotExistingKey() {
        assertThatThrownBy(() -> sqlRegistry.updateSql("SQL9999", "Modified2"))
            .isInstanceOf(SqlUpdateFailureException.class);
    }

}
