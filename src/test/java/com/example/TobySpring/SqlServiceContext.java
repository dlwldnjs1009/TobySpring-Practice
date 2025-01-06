package com.example.TobySpring;

import com.example.TobySpring.user.dao.UserDao;
import com.example.TobySpring.user.sqlservice.OxmSqlService;
import com.example.TobySpring.user.sqlservice.SimpleSqlService;
import com.example.TobySpring.user.sqlservice.SqlService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

public class SqlServiceContext {

    @Autowired
    SqlMapConfig sqlMapConfig;

    @Bean
    public SqlService sqlService() {
        SimpleSqlService sqlService = new SimpleSqlService();
        Map<String, String> sqlMap = new HashMap<>();
        sqlMap.put("userAdd", "insert into users(id, email, name, password, level, login, recommend) values(?,?,?,?,?,?,?)");
        sqlMap.put("userUpdate", "update users set email = ?, name = ?, password = ?, level = ?, login = ?, recommend = ? where id = ?");
        sqlMap.put("userGet", "select * from users where id = ?");
        sqlMap.put("userGetAll", "select * from users order by id");
        sqlMap.put("userDeleteAll", "delete from users");
        sqlMap.put("userGetCount", "select count(*) from users");
        sqlService.setSqlMap(sqlMap);
        return sqlService;
    }

//    @Bean
//    public SqlService sqlService() {
//        OxmSqlService sqlService = new OxmSqlService();
//        sqlService.setUnmarshaller(unmarshaller());
//        sqlService.setSqlRegistry(sqlRegistry());
//        sqlService.setSqlmap(new ClassPathResource(this.sqlMapConfig.getSqlMapResource()));
//        return sqlService;
//    }

    //    @Bean
//    public DataSource embeddedDatabase() {
//        return new EmbeddedDatabaseBuilder()
//            .setName("embeddedDatabase")
//            .setType(EmbeddedDatabaseType.HSQL)
//            .addScript("file:/Users/jiwon/Documents/coding/TobySpring/src/test/java/com/example/TobySpring/user/service/sqlRegistrySchema.sql")
//            .build();
//    }
//
//    @Bean
//    public SqlRegistry sqlRegistry() {
//        EmbeddedDbSqlRegistry sqlRegistry = new EmbeddedDbSqlRegistry();
//        sqlRegistry.setDataSource(embeddedDatabase());
//        return sqlRegistry;
//    }
}
