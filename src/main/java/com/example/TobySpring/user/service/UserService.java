package com.example.TobySpring.user.service;

import com.example.TobySpring.user.domain.User;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserService {

    void upgradeLevels();

    @Transactional(readOnly = true)
    User get(String id);

    @Transactional(readOnly = true)
    List<User> getAll();

    void deleteAll();

    void update(User user);

    void add(User user);

}
