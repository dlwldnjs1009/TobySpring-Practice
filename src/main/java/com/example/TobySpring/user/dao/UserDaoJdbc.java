package com.example.TobySpring.user.dao;

import com.example.TobySpring.user.domain.Level;
import com.example.TobySpring.user.domain.User;
import com.example.TobySpring.user.sqlservice.SqlService;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoJdbc implements UserDao {

    private JdbcTemplate jdbcTemplate;
    private Map<String, String> sqlMap;

    @Autowired
    private SqlService sqlService;

    public void setSqlMap(Map<String, String> sqlMap) {
        this.sqlMap = sqlMap;
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

//    public UserDaoJdbc() {
//        this.jdbcTemplate = jdbcTemplate;
//    }

    private RowMapper<User> userMapper = new RowMapper<User>() {
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getString("id"));
            user.setEmail(rs.getString("email"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
            user.setLevel(Level.valueOf(rs.getInt("level")));
            user.setLogin(rs.getInt("login"));
            user.setRecommend(rs.getInt("recommend"));
            return user;
        }
    };

    public void add(final User user) throws DuplicateKeyException {
        this.jdbcTemplate.update(
                this.sqlService.getSql("userAdd"),
                user.getId(), user.getEmail(), user.getName(), user.getPassword(), user.getLevel().intValue(),
                user.getLogin(), user.getRecommend());

    }

    @Override
    public void update(User user) {
        this.jdbcTemplate.update(
                this.sqlService.getSql("userUpdate"),
          user.getEmail(), user.getName(), user.getPassword(), user.getLevel().intValue(), user.getLogin(), user.getRecommend(), user.getId());
    }

    public User get(String id) {
        return this.jdbcTemplate.queryForObject(this.sqlService.getSql("userGet"),
                new Object[]{id}, this.userMapper);
    }

    public List<User> getAll() {
        return this.jdbcTemplate.query(this.sqlService.getSql("userGetAll"),
                this.userMapper);
    }

    public void deleteAll() {
        this.jdbcTemplate.update(this.sqlService.getSql("userDeleteAll"));
    }

    public int getCount() {
        return this.jdbcTemplate.queryForObject(this.sqlService.getSql("userGetCount"), Integer.class);
    }
}
