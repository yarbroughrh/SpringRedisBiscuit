package com.biscuitswithjelly.controller;

import com.biscuitswithjelly.model.Blog;
import com.biscuitswithjelly.model.User;
import com.biscuitswithjelly.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
// handle incoming HTTP requests and map them to the appropriate service or repository methods. These classes are annotated with
// @Controller or @RestController and handle the incoming requests and return the appropriate response.
    @Autowired
    private UserService userService;

    @PostMapping("/user")
    public ResponseEntity<String> saveUser(@RequestBody User user, BindingResult bindingResult) {
        boolean result = userService.saveUser(user, bindingResult);
        if(result)
            return ResponseEntity.ok("User Created Successfully");
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    @PostMapping("/users/{userId}/blogs")
    public ResponseEntity<String> createBlog(@PathVariable Long userId, @Valid @RequestBody Blog blog, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>("Invalid data", HttpStatus.BAD_REQUEST);
        }
        if (userService.saveBlog(blog, userId)) {
            return new ResponseEntity<>("Blog created successfully", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Error creating blog", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/users/{userId}/blogs")
    public ResponseEntity<List<Blog>> getBlogsByUserId(@PathVariable Long userId) {
        List<Blog> blogs = userService.fetchBlogsByUserId(userId);
        if (blogs != null && !blogs.isEmpty()) {
            return new ResponseEntity<>(blogs, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/user")
    public ResponseEntity<List<User>> fetchAllUser() {
        List<User> users;
        users = userService.fetchAllUser();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> fetchUserById(@PathVariable("id") Long id) {
        User user;
        user = userService.fetchUserById(id);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long id) {
        boolean result = userService.deleteUser(id);
        if(result)
            return ResponseEntity.ok("User deleted Successfully");
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<String> updateUser(@PathVariable("id") Long id, @RequestBody User user) {
        boolean result = userService.updateUser(id,user);
        if(result)
            return ResponseEntity.ok("User Updated Successfully");
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
