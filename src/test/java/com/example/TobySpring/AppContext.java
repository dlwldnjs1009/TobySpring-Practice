package com.example.TobySpring;

import com.example.TobySpring.user.dao.UserDao;
import com.example.TobySpring.user.service.UserService;
import com.example.TobySpring.user.service.UserServiceImpl;
import com.example.TobySpring.user.service.UserServiceTest.TestUserServiceImpl;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = "com.example.TobySpring.user.dao")
@Import(SqlServiceContext.class)
@PropertySource("/database.properties")
@EnableSqlService
public class AppContext implements SqlMapConfig {

    @Autowired
    Environment env;

    @Bean
    public DataSource dataSource() {
        SimpleDriverDataSource ds = new SimpleDriverDataSource();
        try {
            ds.setDriverClass((Class<? extends java.sql.Driver>) Class.forName(
                    env.getProperty("db.driverClass")));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        ds.setUrl(env.getProperty("db.url"));
        ds.setUsername(env.getProperty("db.username"));
        ds.setPassword(env.getProperty("db.password"));

        return ds;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        DataSourceTransactionManager tm = new DataSourceTransactionManager();
        tm.setDataSource(dataSource());
        return tm;
    }

    @Autowired
    UserDao userDao;

    @Bean
    public UserService userService() {
        UserServiceImpl service = new UserServiceImpl();
        service.setUserDao(this.userDao);
        return service;
    }

    @Override
    public Resource getSqlMapResource() {
        return new ClassPathResource("sqlmap.xml", UserDao.class);
    }

    @Configuration
    @Profile("test")
    public static class TestAppContext {

        @Bean
        public UserService testUserService() {
            return new TestUserServiceImpl();
        }
    }

//    @Configuration
//    @Profile("production")
//    public static class ProductionAppContext {
//        @Bean
//        public MailSender mailSender() {
//            JavaMAilSender mailSender = new JavaMAilSender();
//            mailSender.setHost("localhost");
//            return new DummyMailSender();
//        }
//    }

}
