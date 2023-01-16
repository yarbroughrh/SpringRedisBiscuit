package com.biscuitswithjelly.repository;

import ch.qos.logback.classic.Logger;
import com.biscuitswithjelly.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static io.vavr.API.println;


@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private RedisTemplate redisTemplate;

    private static final String KEY = "USER";


    @Override
    public boolean saveUser(User user) {
        System.out.println("Saving user: " + user);
        try {
            redisTemplate.opsForHash().put(KEY, user.getId().toString(), user);
            System.out.println("User saved successfully: " + user);
            return true;
        } catch (Exception e) {
            System.out.println(user);
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<User> fetchAllUser() {
        List<User> users;
        users = redisTemplate.opsForHash().values(KEY);
        return  users;
    }

    @Override
    public User fetchUserById(Long id) {
        User user;
        user = (User) redisTemplate.opsForHash().get(KEY,id.toString());
        return user;
    }

    @Override
    public boolean deleteUser(Long id) {
        try {
            redisTemplate.opsForHash().delete(KEY,id.toString());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateUser(Long id, User user) {
        try {
            redisTemplate.opsForHash().put(KEY, id, user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
