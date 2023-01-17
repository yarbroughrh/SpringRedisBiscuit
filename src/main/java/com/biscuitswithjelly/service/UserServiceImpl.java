package com.biscuitswithjelly.service;

import com.biscuitswithjelly.model.Blog;
import com.biscuitswithjelly.model.User;
import com.biscuitswithjelly.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
//data models for the application, typically annotated with @Entity and are used to map the application's data to the database.
    @Autowired
    private UserDao userDao;

    @Override
    public boolean saveUser(User user, BindingResult bindingResult) {
        return userDao.saveUser(user, bindingResult);
    }

    @Override
    public List<User> fetchAllUser() {
        return userDao.fetchAllUser();
    }

    @Override
    public User fetchUserById(Long id) {
        return userDao.fetchUserById(id);
    }

    @Override
    public boolean deleteUser(Long id) {
        return userDao.deleteUser(id);
    }

    @Override
    public boolean updateUser(Long id, User user) {
        return userDao.updateUser(id, user);
    }

    @Override
    public List<Blog> fetchBlogsByUserId(Long id) {
        return userDao.fetchBlogsByUserId(id);
    }

    @Override
    public boolean saveBlog(Blog blog, Long userId) {
        return userDao.saveBlog(blog, userId);
    }

    @Override
    public boolean deleteBlog(Long id) {
        return userDao.deleteBlog(id);
    }
}
