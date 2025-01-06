package com.example.TobySpring.user.service;

import static com.example.TobySpring.user.service.UserServiceImpl.MIN_LOGCOUNT_FOR_SILVER;
import static com.example.TobySpring.user.service.UserServiceImpl.MIN_RECCOMEND_FOR_GOLD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.TobySpring.AppContext;
import com.example.TobySpring.user.dao.UserDao;
import com.example.TobySpring.user.domain.Level;
import com.example.TobySpring.user.domain.User;
import com.example.TobySpring.exception.TestUserServiceException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.PlatformTransactionManager;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppContext.class)
@ActiveProfiles("test")
public class UserServiceTest {

    List<User> users;

    @Autowired
    UserDao userDao;

    @Autowired
    UserService userService;

    @Autowired
    UserService testUserService;

    @Autowired
    PlatformTransactionManager transactionManager;

    @Autowired
    ApplicationContext context;

    @BeforeEach
    public void setUp() {

        users = Arrays.asList(
                new User("jiwon1009", "5@gmail.com", "이지원", "p1", Level.BASIC,
                        MIN_LOGCOUNT_FOR_SILVER - 1, 0),
                new User("joytouch", "1@gamil.com", "강명성", "p2", Level.BASIC,
                        MIN_LOGCOUNT_FOR_SILVER, 0),
                new User("erwins", "2@gmail.com", "신승한", "p3", Level.SILVER, 60,
                        MIN_RECCOMEND_FOR_GOLD - 1),
                new User("madnite1", "3@gmail.com", "이상호", "p4", Level.SILVER, 60,
                        MIN_RECCOMEND_FOR_GOLD),
                new User("green", "4@gmail.com", "오민규", "p5", Level.GOLD, 100, Integer.MAX_VALUE)
        );
    }

    @Test
    public void add() {
        userDao.deleteAll();

        User userWithLevel = users.get(4);
        User userWithoutLevel = users.get(0);
        userWithoutLevel.setLevel(null);

        userService.add(userWithLevel);
        userService.add(userWithoutLevel);

        User userWithLevelRead = userDao.get(userWithLevel.getId());
        User userWithoutLevelRead = userDao.get(userWithoutLevel.getId());

        assertThat(userWithLevelRead.getLevel()).isEqualTo(userWithLevel.getLevel());
        assertThat(userWithoutLevelRead.getLevel()).isEqualTo(Level.BASIC);
    }

    @Test
    public void mockUpgradeLevels() throws Exception {
        UserServiceImpl userServiceImpl = new UserServiceImpl();

        UserDao mockUserDao = mock(UserDao.class);
        when(mockUserDao.getAll()).thenReturn(this.users);
        userServiceImpl.setUserDao(mockUserDao);

        userServiceImpl.upgradeLevels();

        verify(mockUserDao, times(2)).update(any(User.class));
        verify(mockUserDao).update(users.get(1));
        assertThat(users.get(1).getLevel()).isEqualTo(Level.SILVER);
        verify(mockUserDao).update(users.get(3));
        assertThat(users.get(3).getLevel()).isEqualTo(Level.GOLD);

    }

    @Test
    public void upgradeLevels() throws Exception {
        UserServiceImpl userServiceImpl = new UserServiceImpl(); // 고립된 테스트에서는 테스트 대상 오브젝트를 직접 생성하면 됨

        MockUserDao mockUserDao = new MockUserDao(this.users);
        userServiceImpl.setUserDao(mockUserDao);

        userServiceImpl.upgradeLevels();

        List<User> updated = mockUserDao.getUpdated();
        assertThat(updated).hasSize(2);
        checkUserAndLevel(updated.get(0), "joytouch", Level.SILVER);
        checkUserAndLevel(updated.get(1), "madnite1", Level.GOLD);

    }

    private void checkUserAndLevel(User updated, String expectedId, Level expectedLevel) {
        assertThat(updated.getId()).isEqualTo(expectedId);
        assertThat(updated.getLevel()).isEqualTo(expectedLevel);
    }

    static class MockUserDao implements UserDao {

        private List<User> users;
        private List<User> updated = new ArrayList<>();

        private MockUserDao(List<User> users) {
            this.users = users;
        }

        public List<User> getUpdated() {
            return updated;
        }

        @Override
        public List<User> getAll() {
            return this.users;
        }

        @Override
        public void update(User user) {
            updated.add(user);
        }

        @Override
        public void deleteAll() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(User user) {
            throw new UnsupportedOperationException();
        }

        @Override
        public User get(String id) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int getCount() {
            throw new UnsupportedOperationException();
        }
    }

//    @Test
//    @DisplayName("DefaultAdvisorAutoProxyCreator를 이용한 트랜잭션 테스트")
//    public void upgradeAllOrNothingStaticProxy() throws Exception {
//        TestUserServiceImpl testUserService = new TestUserServiceImpl(users.get(3).getId());
//        testUserService.setUserDao(userDao);
//
//        // 사용자 초기화
//        userDao.deleteAll();
//        for (User user : users) {
//            userDao.add(user);
//        }
//
//        // 트랜잭션 테스트 실행
//        try {
//            this.testUserService.upgradeLevels();
//            fail("TestUserServiceException expected");
//        } catch (TestUserServiceException e) {
//            // 예외 발생 시 롤백 확인
//        }
//
//        // 특정 사용자의 등급이 롤백되었는지 확인
//        checkLevelUpgraded(users.get(1), false);
//    }

    @Test
    @DisplayName("자동 생성된 프록시 확인")
    public void bean() {
        assertThat(testUserService).isInstanceOf(java.lang.reflect.Proxy.class);
    }

//    @Test
//    @DisplayName("읽기 전용 속성 테스트")
//    public void readOnlyTransactionAttribute() {
//        assertThatThrownBy(() -> {
//            testUserService.getAll();
//        }).isInstanceOf(TransientDataAccessResourceException.class);
//    }

    @Test
    public void transactionSync() {
        userService.deleteAll();
        userService.add(users.get(0));
        userService.add(users.get(1));

    }

    private void checkLevelUpgraded(User user, boolean upgraded) {
        User userUpdate = userDao.get(user.getId());
        if (upgraded) {
            assertThat(userUpdate.getLevel()).isEqualTo(user.getLevel().nextLevel());
        } else {
            assertThat(userUpdate.getLevel()).isEqualTo(user.getLevel());
        }
    }

    public static class TestUserServiceImpl extends UserServiceImpl {

        private String id = "madnite1";

        public TestUserServiceImpl() {
            this.id = id;
        }

        public List<User> getAll() {
            for (User user : super.getAll()) {
                super.update(user);
            }
            return null;
        }

        @Override
        protected void upgradeLevel(User user) {
            if (user.getId().equals(this.id)) {
                throw new TestUserServiceException();
            }
            {
                super.upgradeLevel(user);
            }
        }
    }
}
