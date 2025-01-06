package com.example.TobySpring.user.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.TobySpring.AppContext;
import com.example.TobySpring.user.domain.Level;
import com.example.TobySpring.user.domain.User;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes=AppContext.class)
@ActiveProfiles("test")
public class UserDaoTest {

    @Autowired
    private UserDao dao;

    @Autowired
    private DataSource dataSource;

    @Autowired
    DefaultListableBeanFactory bf;

    private User user1;
    private User user2;
    private User user3;

    @BeforeEach
    public void setUp() {
        this.user1 = new User("jiwon1009","jiwon1009@gmail.com", "이지원", "wldnjs", Level.BASIC, 1, 0);
        this.user2 = new User("whiteship", "whiteship@gmail.com","백기선", "married", Level.SILVER, 55, 10);
        this.user3 = new User("bumjin", "bumjin@gmail.com","박범진", "springno3", Level.GOLD, 100, 40);
    }

    @Test
    public void duplicateKey() {
        dao.deleteAll();

        try {
            dao.add(user1);
            dao.add(user1);
        } catch (DuplicateKeyException e) {
            SQLException sqlException = (SQLException) e.getRootCause();
            SQLExceptionTranslator set = new SQLErrorCodeSQLExceptionTranslator(this.dataSource);
            assertThat(set.translate(null, null, sqlException)).isInstanceOf(
                    DuplicateKeyException.class);
        }
    }

    @Test
    public void update() {
        dao.deleteAll();

        dao.add(user1); // 수정할 사용자
        dao.add(user2); // 수정하지 않을 사용자

        user1.setName("이지원2");
        user1.setPassword("wldnjs2");
        user1.setLevel(Level.GOLD);
        user1.setLogin(1000);
        user1.setRecommend(999);

        dao.update(user1);

        User user1update = dao.get(user1.getId());
        checkSameUser(user1, user1update);
        User user2same = dao.get(user2.getId());
        checkSameUser(user2, user2same);
    }

    @Test
    public void addAndGet() throws SQLException, ClassNotFoundException {
        dao.deleteAll();
        assertThat(dao.getCount()).isEqualTo(0);

        dao.add(user1);
        dao.add(user2);
        assertThat(dao.getCount()).isEqualTo(2);

        User userget1 = dao.get(user1.getId());
        checkSameUser(userget1, user1);
        assertThat(userget1.getName()).isEqualTo(user1.getName());
        assertThat(userget1.getPassword()).isEqualTo(user1.getPassword());

        User userget2 = dao.get(user2.getId());
        checkSameUser(userget2, user2);
        assertThat(userget2.getName()).isEqualTo(user2.getName());
        assertThat(userget2.getPassword()).isEqualTo(user2.getPassword());
    }

    @Test
    public void count() throws SQLException {
        dao.deleteAll();
        assertThat(dao.getCount()).isEqualTo(0);

        dao.add(user1);
        assertThat(dao.getCount()).isEqualTo(1);

        dao.add(user2);
        assertThat(dao.getCount()).isEqualTo(2);

        dao.add(user3);
        assertThat(dao.getCount()).isEqualTo(3);
    }

    @Test
    public void getUserFailure() throws SQLException, ClassNotFoundException {
        dao.deleteAll();
        assertThat(dao.getCount()).isEqualTo(0);

        assertThatThrownBy(() -> dao.get("unknown_id"))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    public void getAll() throws SQLException {
        dao.deleteAll();

        List<User> users0 = dao.getAll();
        assertThat(users0.size()).isEqualTo(0);

        dao.add(user1);
        List<User> users1 = dao.getAll();
        assertThat(users1.size()).isEqualTo(1);
        checkSameUser(user1, users1.get(0));

        dao.add(user2);
        List<User> users2 = dao.getAll();
        assertThat(users2.size()).isEqualTo(2);
        checkSameUser(user1, users2.get(0));
        checkSameUser(user2, users2.get(1));

        dao.add(user3);
        List<User> users3 = dao.getAll();
        assertThat(users3.size()).isEqualTo(3);
        checkSameUser(user3, users3.get(0));
        checkSameUser(user1, users3.get(1));
        checkSameUser(user2, users3.get(2));
    }

    @Test
    public void beans() {
        for (String n : bf.getBeanDefinitionNames()) {
            System.out.println(n + "\t" + bf.getBean(n).getClass().getName());
        }
    }

    private void checkSameUser(User user1, User user2) {
        assertThat(user1.getId()).isEqualTo(user2.getId());
        assertThat(user1.getName()).isEqualTo(user2.getName());
        assertThat(user1.getPassword()).isEqualTo(user2.getPassword());
        assertThat(user1.getLevel()).isEqualTo(user2.getLevel());
        assertThat(user1.getLogin()).isEqualTo(user2.getLogin());
        assertThat(user1.getRecommend()).isEqualTo(user2.getRecommend());
    }
}
