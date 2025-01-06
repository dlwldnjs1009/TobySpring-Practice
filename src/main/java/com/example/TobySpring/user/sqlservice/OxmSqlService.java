package com.example.TobySpring.user.sqlservice;

import com.example.TobySpring.exception.SqlNotFoundException;
import com.example.TobySpring.exception.SqlRetrievalFailureException;
import com.example.TobySpring.user.dao.UserDao;
import com.example.TobySpring.user.sqlservice.jaxb.SqlType;
import com.example.TobySpring.user.sqlservice.jaxb.Sqlmap;
import jakarta.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class OxmSqlService implements SqlService {

    private final BaseSqlService baseSqlService = new BaseSqlService();
    private final OxmSqlReader oxmSqlReader = new OxmSqlReader();
    private SqlRegistry sqlRegistry = new HashMapSqlRegistry();

    public void setSqlmap(Resource sqlmap) {
        this.oxmSqlReader.setSqlmap(sqlmap);
    }

    public void setSqlRegistry(SqlRegistry sqlRegistry) {
        this.sqlRegistry = sqlRegistry;
    }

    public void setUnmarshaller(Unmarshaller unmarshaller) {
        this.oxmSqlReader.setUnmarshaller(unmarshaller);
    }

    public void setSqlmapFile(String sqlmapFile) {
        this.oxmSqlReader.setSqlmapFile(sqlmapFile);
    }

    public void loadSql() {
        this.baseSqlService.setSqlReader(this.oxmSqlReader);
        this.baseSqlService.setSqlRegistry(this.sqlRegistry);

        this.baseSqlService.loadSql();
    }

    public String getSql(String key) throws SqlRetrievalFailureException {
        return this.baseSqlService.getSql(key);
    }

    private class OxmSqlReader implements SqlReader {

        private Resource sqlmap = new ClassPathResource("sqlmap.xml");
        private Unmarshaller unmarshaller;
        private final static String DEFAULT_SQLMAP_FILE = "sqlmap.xml";
        private String sqlmapFile = DEFAULT_SQLMAP_FILE;

        public void setSqlmap(Resource sqlmap) {
            this.sqlmap = sqlmap;
        }

        public void setUnmarshaller(Unmarshaller unmarshaller) {
            this.unmarshaller = unmarshaller;
        }

        public void setSqlmapFile(String sqlmapFile) {
            this.sqlmapFile = sqlmapFile;
        }

        public void read(SqlRegistry sqlRegistry) {
            try {
                Source source = new StreamSource(sqlmap.getInputStream());
                Sqlmap sqlmap = (Sqlmap) this.unmarshaller.unmarshal(source);
                for (SqlType sql : sqlmap.getSql()) {
                    sqlRegistry.registerSql(sql.getKey(), sql.getValue());
                }
            } catch (Exception e) {
                throw new IllegalArgumentException(this.sqlmap.getFilename() + "을 가져올 수 없습니다.", e);
            }
        }
    }
}
