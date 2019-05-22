package com.pomo.miaosha.service;

import com.pomo.miaosha.dao.UserDao;
import com.pomo.miaosha.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    UserDao userDao;

    public User getById(int id) {
        return userDao.getById(id);
    }

    @Transactional
    public boolean tx() {
        User user1 = new User();
        user1.setId(2);
        user1.setName("pomo2");
        userDao.insert(user1);

        User user2 = new User();
        user2.setId(3);
        user2.setName("pomo3");
        userDao.insert(user2);

        return true;
    }
}
