package com.biscuitswithjelly.service;

import com.biscuitswithjelly.model.Blog;
import com.biscuitswithjelly.model.User;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface UserService {

    boolean saveUser(User user, BindingResult bindingResult);

    List<User> fetchAllUser();

    User fetchUserById(Long id);

    boolean deleteUser(Long id);

    boolean updateUser(Long id, User user);

    List<Blog> fetchBlogsByUserId(Long id);

    boolean saveBlog(Blog blog, Long userId);

    boolean deleteBlog(Long id);
}