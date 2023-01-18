package com.biscuitswithjelly.repository;

import com.biscuitswithjelly.model.Blog;
import com.biscuitswithjelly.model.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

import static io.vavr.API.println;


@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private RedisTemplate redisTemplate;

    private static final String KEY = "USER";


    @Override
    public boolean saveUser(@Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // handle validation errors
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                System.out.println(error.getField() + ": " + error.getDefaultMessage());
            }
            return false;
        }
        //passing in hash, hashkey, and hash value
        try {
            redisTemplate.opsForHash().put(KEY, user.getId().toString(), user);
            return true;
        } catch (Exception e) {
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

    @Override
    public List<Blog> fetchBlogsByUserId(Long id) {
        if (id == null || id < 0) {
            throw new IllegalArgumentException("Invalid id provided");
        }
        List<Blog> blogs = (List<Blog>) redisTemplate.opsForHash().get(KEY, id);
        return blogs;
    }

    @Override
    public boolean saveBlog(Blog blog, Long userId) {
        redisTemplate.opsForHash().put(KEY, userId, blog);
        return true;
    }

    @Override
    public boolean deleteBlog(Long id) {
        redisTemplate.opsForHash().delete(KEY, id);
        return true;
    }
}
