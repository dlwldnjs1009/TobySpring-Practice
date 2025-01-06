//package com.example.TobySpring.user.sqlservice;
//
//import com.example.TobySpring.user.sqlservice.jaxb.SqlType;
//import com.example.TobySpring.user.sqlservice.jaxb.Sqlmap;
//import jakarta.xml.bind.JAXBContext;
//import jakarta.xml.bind.JAXBException;
//import jakarta.xml.bind.Unmarshaller;
//import java.io.InputStream;
//import java.util.HashMap;
//import java.util.Map;
//
//public class XmlSqlService implements SqlService, SqlRegistry, SqlReader {
//    private Map<String, String> sqlMap = new HashMap<String, String>();
//    private String sqlmapFile;
//    private SqlReader sqlReader;
//    private SqlRegistry sqlRegistry;
//
//    public void setSqlReader(SqlReader sqlReader) {
//        this.sqlReader = sqlReader;
//    }
//
//    public String sqlmapFile(String sqlmapFile) {
//        this.sqlmapFile = sqlmapFile;
//    }
//
//    public void setSqlRegistry(SqlRegistry sqlRegistry) {
//        this.sqlRegistry = sqlRegistry;
//    }
//
//    public void setSqlmapFile(String sqlmapFile) {
//        this.sqlmapFile = sqlmapFile;
//    }
//
//    public String findSql(String key) throws SqlNotFoundException {
//        String sql = sqlMap.get(key);
//        if (sql == null) {
//            throw new SqlNotFoundException(key + "에 대한 SQL을 찾을 수 없습니다.");
//        } else {
//            return sql;
//        }
//    }
//
//    public void registerSql(String key, String sql) {
//        sqlMap.put(key, sql);
//    }
//
//    public void read(SqlRegistry sqlRegistry) {
//        String contextPath = Sqlmap.class.getPackage().getName();
//        try {
//            JAXBContext context = JAXBContext.newInstance(contextPath);
//            Unmarshaller unmarshaller = context.createUnmarshaller();
//            InputStream is = getClass().getClassLoader().getResourceAsStream(
//                    "com/example/TobySpring/user/dao/sqlmap.xml");
//
//            Sqlmap sqlmap = (Sqlmap) unmarshaller.unmarshal(is);
//
//            for (SqlType sql : sqlmap.getSql()) {
//                sqlRegistry.registerSql(sql.getKey(), sql.getValue());
//            }
//        } catch (JAXBException e) {
//            throw new RuntimeException(e);
//        }
//        if (sqlmapFile == null) {
//            throw new IllegalArgumentException("sqlmapFile을 지정하십시오.");
//        }
//        this.sqlmapFile = sqlmapFile;
//        sqlReader.read(sqlRegistry);
//    }
//
//    public void loadSql(String sqlmapFile) {
//        this.sqlmapFile = sqlmapFile;
//        sqlReader.read(this);
//    }
//
//    public String getSql(String key) throws SqlRetrievalFaulureException {
////        try {
////            return this.sqlRegistry.findSql(key);
////        } catch (SqlNotFoundException e) {
////            throw new SqlRetrievalFaulureException(e);
////        }
//    }
//
//}
